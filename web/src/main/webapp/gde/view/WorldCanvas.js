Ext.define('GDE.view.WorldCanvas', {
  extend: 'Ext.ux.EaselPanel',
  alias: 'widget.world',
  initComponent: function () {
    var me = this;
    var cache = [];
    var tiles = [];
    var objects = [];
    var tileContainer = new createjs.Container();
    var objectContainer = new createjs.Container();

    Ext.apply(me, {
      
      listeners: {
        afterrender: function() {
          me.stage.addChild(tileContainer);
          me.stage.addChild(objectContainer);
        }
      },

      findFocusTile: function() {
        return me.focusTile = {
          x: Math.floor(me.canvasEl.width / 2),
          y: Math.floor(me.canvasEl.height / 2),
          south: Math.floor(me.map.south / 2),
          east: Math.floor(me.map.east / 2)
        }
      },

      findIsometricArea: function() {
        var focus = me.focusTile || me.findFocusTile();
        var columns = Math.ceil(me.canvasEl.width / (TILE_WIDTH / 2));
        var rows = Math.ceil(me.canvasEl.height / (TILE_HEIGHT / 2));
        return me.area = {
          south: focus.south - Math.ceil((columns + rows) / 4),
          east: focus.east - Math.ceil((columns - rows) / 4),
          columns: columns,
          rows: rows
        }
      },

      findIsometricGrid: function() {
        var area = me.area || me.findIsometricArea();
        me.grid = [];
        for(var r = 0; r < area.rows; r++) {
          var rowOffset = r / 2;
          var south = area.south + Math.ceil(rowOffset);
          var east = area.east - Math.floor(rowOffset);
          for(var c = 0; c < area.columns; c++) {
            if(south >= 0 && south < me.map.south && east >= 0 && east < me.map.east) me.grid.push(south * me.map.east + east);
            south++;
            east++;
          }
        }
        return me.grid;
      },

      drawElement: function(element, index, container, array) {
        var south = Math.floor(index / me.map.east);
        var east = index % me.map.east;
        var ret = new createjs.Bitmap(element.image);
        ret.south = south;
        ret.east = east;
        var focus = me.focusTile || me.findFocusTile();
        ret.x = focus.x + (south + east - focus.south - focus.east) * 32;
        ret.y = focus.y + (south - east - focus.south + focus.east) * 24;
        var property = element.property;
        ret.regX = property.regX;
        ret.regY = property.regY;
        container.addChild(ret);
        array[index] = ret;
        me.stage.update();
        return ret;
      },

      loadAndDrawElement: function(id, index, container, array) {
        if(id == -1) return;
        if(cache[id]) {
          if(cache[id].renderQueue) cache[id].renderQueue.push({index: index, container: container, array: array});
          else me.drawElement(cache[id], index, container, array);
        }
        else {
          cache[id] = {
            renderQueue: [{index: index, container: container, array: array}]
          };
          var queue = new createjs.LoadQueue(false);
          var manifest = [];
          manifest.push({
            src: 'api/bitmap/image/' + id + '.png',
            type: 'image',
            id: 'image'
          }, {
            src: 'api/bitmap/index/' + id + '.json',
            type: 'json',
            id: 'property'
          });
          queue.addEventListener('fileload', function (payload) {
            var element = cache[id];
            element[payload.item.id] = payload.result;
          });
          queue.addEventListener('complete', function () {
            $.each(cache[id].renderQueue, function(index, render) {
              me.drawElement(cache[id], render.index, render.container, render.array);
            });
            delete cache[id].renderQueue;
          });
          queue.loadManifest(manifest, true);
        }
      },

      drawMap: function() {
        if(!me.map) return;
        me.stage.addChild(tileContainer);
        me.stage.addChild(objectContainer);
        var grid = me.findIsometricGrid();
        var i = 0;
        while(i < grid.length) {
          var index = grid[i++];
          var tile = me.map.tiles[index];
          var object = me.map.objects[index];
          me.loadAndDrawElement(tile, index, tileContainer, tiles);
          me.loadAndDrawElement(object, index, objectContainer, objects);
        }
      },

      clearMap: function() {
        me.stage.removeAllChildren();
        tileContainer.removeAllChildren();
        tiles = [];
        objectContainer.removeAllChildren();
        objects = [];
        delete me.focusTile;
        delete me.area;
        delete me.grid;
        me.stage.update();
      },

      dropMap: function() {
        me.clearMap();
        delete me.map;
      },

      loadMap: function(id) {
        me.dropMap();
        var url = 'api/map/map/' + id + '.json';
        var queue = new createjs.LoadQueue(false);
        queue.addEventListener('fileload', function (payload) {
          me.map = payload.result;
          me.drawMap();
        });
        queue.loadFile(url);
      },

      drawPlan: [
        function() {
          me.clearMap();
          me.drawMap();
        }
      ]
    });
    me.callParent(arguments);
  }
});