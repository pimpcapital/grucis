Ext.define('GDE.view.TileMapCanvas', {
  extend: 'Ext.ux.EaselPanel',
  alias: 'widget.tilemap',
  cache: {},
  tiles: [],
  children: [],
  tilesContainer: new createjs.Container(),
  objectsContainer: new createjs.Container(),
  initComponent: function () {
    var me = this;
    Ext.apply(me, {
      drawPlan: [
        function () {
          if(me.map) {
            me.unload();
            me.setReference();
            me.drawMap();
          }
        }
      ],

      setReference: function (reset) {
        if(reset || !me.reference) {
          me.reference = {
            x: Math.floor(me.canvasEl.width / 2),
            y: Math.floor(me.canvasEl.height / 2),
            south: Math.floor(me.map.south / 2),
            east: Math.floor(me.map.east / 2)
          }
        } else {
          me.reference.x = Math.min(me.canvasEl.width, me.reference.x);
          me.reference.y = Math.min(me.canvasEl.height, me.reference.y);
        }
        me.radius = Math.floor(Math.max(me.canvasEl.height / 48, me.canvasEl.width / 64));
      },

      loadAndDrawElement: function(south, east, id, index, z) {
        var queue = new createjs.LoadQueue(false);
        var manifest = [];
        manifest.push({
        src: 'api/bitmap/image/' + id + '.png',
          type: 'image',
          id: 'bitmap_' + id
        }, {
          src: 'api/bitmap/index/' + id + '.json',
          type: 'json',
          id: 'index_' + id
        });
        queue.addEventListener('fileload', function (payload) {
          var id = payload.item.id.split('_');
          var imageId = parseInt(id[1]);
          var image = me.cache[imageId];
          if(!image) {
            image = {};
            me.cache[imageId] = image;
          }
          image[id[0]] = payload.result;
        });
        queue.addEventListener('complete', function () {
          me.drawElement(south, east, id, index, z);
        });
        queue.loadManifest(manifest, true);
      },

      isInBuffer: function (x, y, width, height, regX, regY) {
        var left = x - regX;
        if(left > me.canvasEl.width + 128) return false;
        var right = left + width;
        if(right < -128) return false;
        var top = y - regY;
        if(top > me.canvasEl.height + 96) return false;
        var bottom = top + height;
        if(bottom < -96) return false;

        return true;
      },

      drawElement: function (south, east, id, index, z) {
        var element = me.cache[id];
        if(!element || !element.index || !element.bitmap) {
          me.loadAndDrawElement(south, east, id, index, z);
          return;
        }
        var xOffset = (south + east - me.reference.south - me.reference.east) * 32;
        var yOffset = (south - east - me.reference.south + me.reference.east) * 24;

        var x = me.reference.x + xOffset;
        var y = me.reference.y + yOffset;

        if(me.isInBuffer(x, y, element.bitmap.width, element.bitmap.height, element.index.regX, element.index.regY)) {
          var child = me.children[index];
          if(!child) {
            child = new createjs.Bitmap(element.bitmap);
            child.regX = element.index.regX;
            child.regY = element.index.regY;
            child.south = south;
            child.east = east;
            child.z = z;
            child.x = me.reference.x + xOffset;
            child.y = me.reference.y + yOffset;
            me.children[index] = child;
            me.stage.addChild(child);
//            me.stage.sortChildren(me.elementSorter);
            me.stage.update();
            child.addEventListener("mousedown", function (evt) {
              var offset = {x: evt.target.x - evt.stageX, y: evt.target.y - evt.stageY};
              var centerX = Math.floor(me.canvasEl.width / 2);
              var centerY = Math.floor(me.canvasEl.height / 2);
              evt.addEventListener("mousemove", function (ev) {
                var deltaX = ev.stageX + offset.x - ev.target.x;
                var deltaY = ev.stageY + offset.y - ev.target.y;
                Ext.Array.each(me.stage.children, function (obj) {
                  obj.x += deltaX;
                  obj.y += deltaY;
                });
                me.reference.x += deltaX;
                me.reference.y += deltaY;
                me.stage.update();


              });
              evt.addEventListener("mouseup", function (evt) {
                var load = false;
                while(me.reference.x < centerX - 32) {
                  me.reference.x += 64;
                  me.reference.south++;
                  me.reference.east++;
                  load = true
                }
                while(me.reference.x > centerX + 32) {
                  me.reference.x -= 64;
                  me.reference.south--;
                  me.reference.east--;
                  load = true
                }
                while(me.reference.y < centerY - 24) {
                  me.reference.y += 48;
                  me.reference.south++;
                  me.reference.east--;
                  load = true
                }
                while(me.reference.y > centerY + 24) {
                  me.reference.y -= 48;
                  me.reference.south--;
                  me.reference.east++;
                  load = true
                }
                if(load) me.drawMap();


                Ext.Array.every(me.children, function (child, index) {
                  if(!me.isInBuffer(child.x, child.y, child.image.width, child.image.height, child.regX, child.regY)) {
                    delete me.children[index];
                    me.stage.removeChild(child);
                  }
                  return true;
                });
              })
            });
          }
        }
      },

      elementSorter : function(a, b) {
        if(a.z < b.z) return -1;
        if(a.z > b.z) return 1;
        if(a.east > b.east) return -1;
        if(a.east < b.east) return 1;
        if(a.south > b.south) return 1;
        if(a.south < b.south) return -1;
        return 0;
      },

      drawMap: function () {
        var manifest = [];
        var tiles = [];
        var objects = [];
        var survivedTiles = [];
        var survivedObjects = [];

        var prepareManifest = function(id) {
          if(id != -1 && !me.cache[id]) {
            manifest.push({
              src: 'api/bitmap/image/' + id + '.png',
              type: 'image',
              id: 'bitmap_' + id
            }, {
              src: 'api/bitmap/index/' + id + '.json',
              type: 'json',
              id: 'index_' + id
            });
          }
        };

        var prepareTile = function(s, e, id) {

        };

        var prepareObject = function(s, e, id) {

        };

        for(var e = Math.min(me.map.east - 1, me.reference.east + me.radius); e >= Math.max(0, me.reference.east - me.radius); e--) {
          for(var s = Math.max(0, me.reference.south - me.radius); s <= Math.min(me.map.south - 1, me.reference.south + me.radius); s++) {
            var tileId = me.map.tiles[s][e];
            var objectId = me.map.objects[s][e];
            prepareManifest(tileId);
            prepareManifest(objectId);
            prepareTile(s, e, tileId);
            prepareObject(s, e, objectId);
          }
        }

        var render = function() {
          $.each(tiles, function(index, tile) {

          });

          $.each(objects, function(index, object) {

          });
        };

        if(manifest.length) {
          var queue = new createjs.LoadQueue(false);
          queue.addEventListener('fileload', function (payload) {
            var id = payload.item.id.split('_');
            var imageId = parseInt(id[1]);
            var image = me.cache[imageId];
            if(!image) {
              image = {};
              me.cache[imageId] = image;
            }
            image[id[0]] = payload.result;
          });
          queue.addEventListener('complete', function () {
            render();
          });
          queue.loadManifest(manifest, true);
        } else render();
      },

      unload: function (dropMap) {
        me.stage.removeAllChildren();
        me.children = [];
        if(dropMap) {
          delete me.total;
          delete me.map;
        }
      },

      loadMap: function (map) {
        var id = map.get('id');
        var url = 'api/map/map/' + id + '.json';
        var queue = new createjs.LoadQueue(false);
        me.unload(true);
        queue.addEventListener('fileload', function (payload) {
          me.map = payload.result;
          me.total = me.map.east * me.map.south;
          me.setReference(true);
          me.drawMap();
        });
        queue.loadFile(url);
      }
    });
    me.callParent(arguments);
  }
});