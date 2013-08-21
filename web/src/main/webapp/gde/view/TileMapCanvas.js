Ext.define('GDE.view.TileMapCanvas', {
  extend: 'Ext.ux.EaselPanel',
  alias: 'widget.tilemap',
  imageCache: {},
  children: [],
  offset: {x: 0, y: 0},
  initComponent: function () {
    var me = this;
    Ext.apply(me, {
      drawPlan: [
        function () {
          me.setRadius();
          if(me.map) {
            me.stage.removeAllChildren();
            me.children = [];
            me.offset = {x: 0, y: 0};
            me.loadAndDraw();
          }
        }
      ],

      setRadius: function () {
        var width = me.canvasEl.width;
        var height = me.canvasEl.height;
        me.centerPix = {x: Math.floor(width / 2), y: Math.floor(height / 2)};
        me.radius = Math.ceil(Math.sqrt(Math.pow(width / 2, 2) + Math.pow(height / 2, 2)) / 80);
      },

      loadAndDraw: function () {
        var load = [];
        for(var s = Math.max(0, me.center.south - me.radius); s <= Math.min(me.map.south - 1, me.center.south + me.radius); s++) {
          for(var e = Math.max(0, me.center.east - me.radius); e <= Math.min(me.map.east - 1, me.center.east + me.radius); e++) {
            var tile = me.map.tiles[s][e];
            var object = me.map.objects[s][e];
            if(tile != -1 && !me.imageCache[tile]) Ext.Array.include(load, tile);
            if(object != -1 && !me.imageCache[object]) Ext.Array.include(load, object);
          }
        }
        if(load.length) {
          var queue = new createjs.LoadQueue(false);
          var manifest = [];
          Ext.Array.each(load, function (id) {
            manifest.push({
              src: 'api/bitmap/image/' + id + '.png',
              type: 'image',
              id: 'bitmap_' + id
            }, {
              src: 'api/bitmap/index/' + id + '.json',
              type: 'json',
              id: 'index_' + id
            });
          });
          queue.addEventListener('fileload', function (payload) {
            var id = payload.item.id.split('_');
            var imageId = parseInt(id[1]);
            var image = me.imageCache[imageId];
            if(!image) {
              image = {};
              me.imageCache[imageId] = image;
            }
            image[id[0]] = payload.result;
          });
          queue.addEventListener('complete', function () {
            me.drawMap();
          });
          queue.loadManifest(manifest, true);
        } else {
          me.drawMap();
        }

      },

      isImageVisible: function (xOffset, yOffset, index) {
        if(xOffset > 0) {
          if(xOffset - index.regX > me.canvasEl.width / 2 + 128) return false;
        } else {
          if(-xOffset - (index.width - index.regX) > me.canvasEl.width / 2 + 128) return false;
        }
        if(yOffset > 0) {
          if(yOffset - index.regY > me.canvasEl.height / 2 + 96) return false;
        } else {
          if(-yOffset - (index.height - index.regY) > me.canvasEl.height / 2 + 96) return false;
        }
        return true;
      },

      drawElement: function (south, east, element, index, z) {
        if(!element) return;
        var xOffset = (south + east - me.center.south - me.center.east) * 32;
        var yOffset = (south - east - me.center.south + me.center.east) * 24;

        var child = me.children[index];
        var elementIndex = element.index;
        if(me.isImageVisible(xOffset, yOffset, element.index)) {
          if(!child) {
            child = new createjs.Bitmap(element.bitmap);
            child.regX = elementIndex.regX;
            child.regY = elementIndex.regY;
            child.south = south;
            child.east = east;
            child.z = z;
            child.x = me.centerPix.x + xOffset + me.offset.x;
            child.y = me.centerPix.y + yOffset + me.offset.y;
            child.update = new Date().getMilliseconds();
            me.children[index] = child;
            me.stage.addChild(child);
            child.addEventListener("mousedown", function (evt) {
              console.log('south: ' + evt.target.south + ', east: ' + evt.target.east);
              var offset = {x: evt.target.x - evt.stageX, y: evt.target.y - evt.stageY};
              evt.addEventListener("mousemove", function (ev) {
                var deltaX = ev.stageX + offset.x - ev.target.x;
                var deltaY = ev.stageY + offset.y - ev.target.y;
                me.offset.x += deltaX;
                me.offset.y += deltaY;
                Ext.Array.each(me.stage.children, function(obj) {
                  obj.x += deltaX;
                  obj.y += deltaY;
                });
                me.stage.update();

//                var load = false;
//                while(me.offset.x > 32) {
//                  me.center.south++;
//                  me.center.east++;
//                  me.offset.x -= 32;
//                  load = true;
//                }
//                while(me.offset.x < -32) {
//                  me.center.south--;
//                  me.center.east--;
//                  me.offset.x += 32;
//                  load = true;
//                }
//                while(me.offset.y > 24) {
//                  me.center.south--;
//                  me.center.east++;
//                  me.offset.y -= 24;
//                  load = true;
//                }
//                while(me.offset.y < -24) {
//                  me.center.south++;
//                  me.center.east--;
//                  me.offset.y += 24;
//                  load = true;
//                }
//                if(load) me.loadAndDraw();

              });
            });
          }
        } else {
          if(child) {
            delete me.children[index];
            me.stage.removeChild(child);
          }
        }
      },

      sortElements: function () {
        var sortFn = function (a, b) {
          if(a.z < b.z) return -1;
          if(a.z > b.z) return 1;
          if(a.south > b.south) return 1;
          if(a.south < b.south) return -1;
          if(a.east > b.east) return -1;
          if(a.east < b.east) return 1;
          return a.update - b.update;
        };
        me.stage.sortChildren(sortFn);
      },

      drawMap: function () {
        for(var e = Math.min(me.map.east - 1, me.center.east + me.radius); e >= Math.max(0, me.center.east - me.radius); e--) {
          for(var s = Math.max(0, me.center.south - me.radius); s <= Math.min(me.map.south - 1, me.center.south + me.radius); s++) {
            var index = e * me.map.south + s;
            var tileId = me.map.tiles[s][e];
            var objectId = me.map.objects[s][e];

            me.drawElement(s, e, me.imageCache[tileId], index, 0);
            me.drawElement(s, e, me.imageCache[objectId], me.total + index, 1);
          }
        }
        me.sortElements();
        me.stage.update();
      },

      unload: function () {
        me.stage.removeAllChildren();
        me.children = [];
        me.offset = {x: 0, y: 0};
        delete me.center;
        delete me.map;
        delete me.total;
      },

      loadMap: function (map) {
        var id = map.get('id');
        var url = 'api/map/map/' + id + '.json';
        var queue = new createjs.LoadQueue(false);
        me.unload();
        queue.addEventListener('fileload', function (payload) {
          me.map = payload.result;
          me.total = me.map.east * me.map.south;
          me.center = {south: Math.floor(me.map.south / 2), east: Math.floor(me.map.east / 2)};
          me.loadAndDraw();
        });
        queue.loadFile(url);
      }
    });
    me.callParent(arguments);
  }
});