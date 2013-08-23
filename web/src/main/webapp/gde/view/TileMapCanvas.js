Ext.define('GDE.view.TileMapCanvas', {
  extend: 'Ext.ux.EaselPanel',
  alias: 'widget.tilemap',
  cache: {},
  children: [],
  initComponent: function () {
    var me = this;
    Ext.apply(me, {
      drawPlan: [
        function () {
          if(me.map) {
            me.setReference();
            me.loadAndDraw();
          }
        }
      ],

      setReference: function () {
        if(!me.reference) {
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
        me.radius = Math.ceil(me.canvasEl.width / 64 + me.canvasEl.height / 48);
      },

      loadAndDraw: function () {
        var load = [];
        for(var s = Math.max(0, me.reference.south - me.radius); s <= Math.min(me.map.south - 1, me.reference.south + me.radius); s++) {
          for(var e = Math.max(0, me.reference.east - me.radius); e <= Math.min(me.map.east - 1, me.reference.east + me.radius); e++) {
            var tile = me.map.tiles[s][e];
            var object = me.map.objects[s][e];
            if(tile != -1 && !me.cache[tile]) Ext.Array.include(load, tile);
            if(object != -1 && !me.cache[object]) Ext.Array.include(load, object);
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
            var image = me.cache[imageId];
            if(!image) {
              image = {};
              me.cache[imageId] = image;
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

      isInBuffer: function (x, y, elementIndex) {
        var width = elementIndex.width;
        var height = elementIndex.height;
        var regX = elementIndex.regX;
        var regY = elementIndex.regY;

        var left = x - regX;
        if(left < -me.canvasEl.width) return false;
        var right = left + width;
        if(right > me.canvasEl.width * 2) return false;
        var top = y - regY;
        if(top < -me.canvasEl.height) return false;
        var bottom = top + height;
        if(bottom > me.canvasEl * 2) return false;

        return true;
      },

      drawElement: function (south, east, id, index, z) {
        var element = me.cache[id];
        var xOffset = (south + east - me.reference.south - me.reference.east) * 32;
        var yOffset = (south - east - me.reference.south + me.reference.east) * 24;

        var child = me.children[index];
        var elementIndex = element.index;
        var x = me.reference.x + xOffset;
        var y = me.reference.y + yOffset;
        if(me.isInBuffer(x, y, elementIndex)) {
          if(!child) {
            child = new createjs.Bitmap(element.bitmap);
            child.regX = elementIndex.regX;
            child.regY = elementIndex.regY;
            child.south = south;
            child.east = east;
            child.z = z;
            child.x = me.reference.x + xOffset;
            child.y = me.reference.y + yOffset;
            child.update = new Date().getMilliseconds();
            me.children[index] = child;
            me.stage.addChild(child);
            child.addEventListener("mousedown", function (evt) {
              var offset = {x: evt.target.x - evt.stageX, y: evt.target.y - evt.stageY};
              me.reference.south = evt.target.south;
              me.reference.east = evt.target.east;
              evt.addEventListener("mousemove", function (ev) {
                var deltaX = ev.stageX + offset.x - ev.target.x;
                var deltaY = ev.stageY + offset.y - ev.target.y;
                me.offset.x += deltaX;
                me.offset.y += deltaY;
                Ext.Array.each(me.stage.children, function (obj) {
                  obj.x += deltaX;
                  obj.y += deltaY;
                });
                me.reference.x += evt.target.x;
                me.reference.y = evt.target.y;
                me.stage.update();
              });
              evt.addEventListener("mouseup", function(evt) {
                me.loadAndDraw();
              })
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
          if(a.east > b.east) return -1;
          if(a.east < b.east) return 1;
          if(a.south > b.south) return 1;
          if(a.south < b.south) return -1;
          return a.update - b.update;
        };
        me.stage.sortChildren(sortFn);
      },

      drawMap: function () {
        for(var e = Math.min(me.map.east - 1, me.reference.east + me.radius); e >= Math.max(0, me.reference.east - me.radius); e--) {
          for(var s = Math.max(0, me.reference.south - me.radius); s <= Math.min(me.map.south - 1, me.reference.south + me.radius); s++) {
            var index = e * me.map.south + s;
            var tileId = me.map.tiles[s][e];
            if(tileId != -1) me.drawElement(s, e, tileId, index, 0);
            var objectId = me.map.objects[s][e];
            if(objectId != -1) me.drawElement(s, e, objectId, me.total + index, 1);
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
          me.setReference();
          me.loadAndDraw();
        });
        queue.loadFile(url);
      }
    });
    me.callParent(arguments);
  }
});