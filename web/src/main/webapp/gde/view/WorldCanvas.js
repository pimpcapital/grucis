Ext.define('GDE.view.WorldCanvas', {
  extend: 'Ext.ux.EaselPanel',
  alias: 'widget.world',
  initComponent: function () {
    var me = this;
    var canvas = me.canvasEl;
    var stage = me.stage;
    var cache = [];
    var tiles = [];
    var objects = [];
    var tileContainer = new createjs.Container();
    var objectContainer = new createjs.Container();
    stage.addChild(tileContainer);
    stage.addChild(objectContainer);
    Ext.apply(me, {
      drawPlan: [
        me.redrawMap
      ],

      findCenter: function() {
        return me.center = {
          x: Math.floor(canvas.width / 2),
          y: Math.floor(canvas.height / 2),
          south: Math.floor(me.map.south / 2),
          east: Math.floor(me.map.east / 2)
        }
      },

      findIsometricArea: function() {
        var center = me.center || me.findCenter();
        var columns = Math.ceil(canvas.width / (TILE_WIDTH / 2));
        var rows = Math.ceil(canvas.height / (TILE_HEIGHT / 2));
        return me.area = {
          south: center.south + Math.ceil((columns - rows) / 4),
          east: center.east + Math.ceil((columns + rows) / 4),
          columns: columns,
          rows: rows
        }
      },

      findIsometricGrid: function() {
        var area = me.area || me.findIsometricArea();
        me.grid = [];
        for(var r = 0; r < area.rows; r++) {
          var rowOffset = r % 2;
          var south = area.south + Math.ceil(rowOffset);
          var east = area.east - Math.floor(rowOffset);
          for(var c = 0; c < area.columns; c++) {
            me.grid.push(south * me.map.east + east);
            south--;
            east--;
          }
        }
        return me.grid;
      },

      drawElement: function(element, south, east, container, array) {
        var bitmap = new createjs.Bitmap(element.image);
        bitmap.south = south;
        bitmap.east = east;
        var center = me.center || me.findCenter();
        bitmap.x = center.x + (south + east - center.south - center.east) * 32;
        bitmap.y = center.y + (south - east - center.south + center.east) * 24;
        var index = element.index;
        bitmap.regX = index.regX;
        bitmap.regY = index.regY;
        container.addChild(bitmap);
        array[south * me.map.east + east] = bitmap;
        return bitmap;
      },

      loadAndDrawElement: function(id, south, east, container, array) {
        if(id == -1) return;
        if(cache[id]) me.drawElement(cache[id], south, east, container, array);
        else {
          var queue = new createjs.LoadQueue(false);
          var manifest = [];
          manifest.push({
            src: 'api/bitmap/image/' + id + '.png',
            type: 'image',
            id: 'image'
          }, {
            src: 'api/bitmap/index/' + id + '.json',
            type: 'json',
            id: 'index'
          });
          queue.addEventListener('fileload', function (payload) {
            var element = cache[id];
            if(!element) {
              element = {};
              cache[id] = element;
            }
            element[payload.item.id] = payload.result;
          });
          queue.addEventListener('complete', function () {
            me.loadAndDrawElement(id, south, east, container, array);
          });
          queue.loadManifest(manifest, true);
        }
      },

      drawMap: function() {
        if(!me.map) return;
        var grid = me.findIsometricGrid();
        var i = 0;
        while(i < grid.length) {
          var index = grid[i++];
          var south = index / me.map.east;
          var east = index % me.map.east;
          if(south < 0 || south >= me.map.south || east < 0 ||east >= me.map.east) continue;
          var tile = me.map.tiles[south][east];
          var object = me.map.objects[south][east];
          me.loadAndDrawElement(tile, south, east, tileContainer, tiles);
          me.loadAndDrawElement(object, south, east, objectContainer, objects);
        }
      },

      clearMap: function() {
        tileContainer.removeAllChildren();
        tiles = [];
        objectContainer.removeAllChildren();
        objects = [];
        delete me.center;
        delete me.area;
        delete me.grid;
        stage.update();
      },

      dropMap: function() {
        me.clearMap();
        delete me.map;
        delete me.total;
      },

      redrawMap: function() {
        me.clearMap();
        me.drawMap();
      },

      loadMap: function(id) {
        me.dropMap();
        var url = 'api/map/map/' + id + '.json';
        var queue = new createjs.LoadQueue(false);
        queue.addEventListener('fileload', function (payload) {
          me.map = payload.result;
          me.total = me.map.east * me.map.south;
          me.drawMap();
        });
        queue.loadFile(url);
      }
    });
    me.callParent(arguments);
  }
});