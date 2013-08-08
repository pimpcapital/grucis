Ext.define('GDE.view.SpriteAnimationCanvas', {
  extend: 'Ext.ux.EaselPanel',
  alias: 'widget.spriteanimation',
  path: [],
  initComponent: function () {
    var me = this;
    var origin, radius;
    createjs.Ticker.setFPS(50);
    createjs.LoadQueue.LOAD_TIMEOUT = 30000;
    var tileIndicator = new createjs.Bitmap('api/bitmap/image/8828.png');
    tileIndicator.regX = 32;
    tileIndicator.regY = 24;
    Ext.apply(me, {

      walk: function() {
        if(!me.tick) {
          var next = me.path.shift();
          if(!next) {
            me.animation.action = 'stand';
            me.animation.gotoAndPlay(me.animation.direction + '_' + me.animation.action);
          } else {
            var steps;
            if(next.x > me.animation.south && next.y > me.animation.east || next.x < me.animation.south && next.y < me.animation.east) {
              steps = 24;
            } else if(next.x == me.animation.south || next.y == me.animation.east) {
              steps = 15;
            } else {
              steps = 18;
            }
            var nextTile = me.grid[next.x][next.y];
            var xStep = (nextTile.x - me.animation.x) / steps;
            var yStep = (nextTile.y - me.animation.y) / steps;
            var count = 0;
            me.animation.direction = Ext.ux.PathUtils.getDirection({x: me.animation.south, y: me.animation.east}, {x: nextTile.south, y: nextTile.east});
            me.animation.action = 'walk';
            me.animation.gotoAndPlay(me.animation.direction + '_' + me.animation.action);
            me.animation.south = nextTile.south;
            me.animation.east = nextTile.east;
            me.tick = function() {
              me.animation.x += xStep;
              me.animation.y += yStep;
              count++;
              if(count == steps) {
                me.animation.x = nextTile.x;
                me.animation.y = nextTile.y;
                createjs.Ticker.removeEventListener('tick', me.tick);
                delete me.tick;
                me.walk();
              }
            };
            createjs.Ticker.addEventListener('tick', me.tick);
          }
        }
      },

      drawPlan: [
        function () {
          origin = Ext.ux.EaselPanelUtils.getOrigin(me.stage);
          radius = Math.floor(Math.min(8, (me.stage.canvas.width - 64) / 128, (me.stage.canvas.height - 48) / 96));
          Ext.applyIf(origin, {
            south: radius,
            east: radius
          });
          me.grid = Ext.ux.EaselPanelUtils.drawIsometricGrid(me.stage, origin, {
            stroke: '#c8c8c8',
            fill: '#2b2b2b',
            radius: radius,
            width: 64,
            height: 48,
            listeners: {
              mouseover: function(evt){
                var tile = evt.target;
                tileIndicator.x = tile.x;
                tileIndicator.y = tile.y;
                me.stage.update();
              },
              mousedown: function(evt) {
                if(me.animation) {
                  var animation = me.animation;
                  if(evt.nativeEvent.button == 2) {
                    var direction = Ext.ux.PathUtils.getDirection({x: animation.south, y: animation.east}, {x: evt.target.south, y: evt.target.east});
                    var action = animation.action;
                    if(!me.tick) animation.gotoAndPlay(direction + '_' + action);
                    else {
                      me.path = [];
                      me.animation.direction = direction;
                    }
                  } else if(evt.nativeEvent.button == 0) {
                    me.path = Ext.ux.PathUtils.getPath({x: animation.south, y: animation.east}, {x: evt.target.south, y: evt.target.east});
                    me.walk();
                  }
                }
              }
            }
          });
        },

        function() {
          me.stage.addChild(tileIndicator);
        },

        function() {
          if(me.animation) {
            me.placeAnimation(me.animation);
          }
        }
      ],

      placeAnimation: function(animation) {
        animation.x = origin.x;
        animation.y = origin.y;
        animation.south = radius;
        animation.east = radius;
        animation.action = 'attack';
        animation.direction = 'south';
        animation.gotoAndPlay(animation.direction + '_' + animation.action);
        me.stage.addChild(animation);
      },

      unload: function() {
        createjs.Ticker.removeAllListeners();
        me.stage.removeChild(me.animation);
        delete me.spradrn;
        if(me.tick) {
          createjs.Ticker.removeEventListener('tick', me.tick);
          delete me.tick;
        }
      },

      loadAnimation: function(spradrn, keepPrevious) {
        var name = spradrn.get('id');
        var spriteUrl = 'api/animation/sprite/' + name + '.png';
        var indexUrl = 'api/animation/index/' + name + '.json';
        var queue = new createjs.LoadQueue(false);
        if(!keepPrevious) me.unload();
        var sprite, index;
        queue.addEventListener('fileload', function(payload) {
          if(payload.item.id == 'sprite') sprite = payload.result;
          else index = payload.result;
        });
        queue.addEventListener('complete', function() {
          Ext.applyIf(index, {
            images: [sprite]
          });
          var spriteSheet = new createjs.SpriteSheet(index);
          var animation = new createjs.BitmapAnimation(spriteSheet);
          animation.name = name;
          me.animation = animation;
          me.spradrn = spradrn;
          me.placeAnimation(animation);
        });
        queue.loadManifest([
          {src: spriteUrl, type: 'image', id: 'sprite'},
          {src: indexUrl, type: 'json', id: 'index'}
        ], true);
        createjs.Ticker.addEventListener("tick", me.stage);
      }


    });
    me.callParent(arguments);
  }
});