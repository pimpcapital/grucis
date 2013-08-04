Ext.define('GDE.view.SpriteAnimationCanvas', {
  extend: 'Ext.ux.EaselPanel',
  alias: 'widget.spriteanimation',
  animations: [],
  spradrns: {},
  initComponent: function () {
    var me = this;
    var origin, radius;
    createjs.Ticker.setFPS(50);
    Ext.apply(me, {
      drawPlan: [
        function () {
          origin = Ext.ux.EaselPanelUtils.getOrigin(me.stage);
          radius = Math.floor(Math.min(8, (me.stage.canvas.width - 64) / 128, (me.stage.canvas.height - 48) / 96));
          Ext.applyIf(origin, {
            south: radius,
            east: radius
          });
          Ext.ux.EaselPanelUtils.drawIsometricGrid(me.stage, origin, {
            stroke: '#c8c8c8',
            fill: '#2b2b2b',
            radius: radius,
            width: 64,
            height: 48,
            listeners: {
              mousedown: function(evt) {
                Ext.Array.each(me.animations, function(animation) {
                  var south = evt.target.south - animation.south;
                  var east = evt.target.east - animation.east;
                  var atan = Math.atan2(east, south);
                  var direction;
                  if(atan > Math.PI * 7 / 8) {
                    direction = 'north';
                  } else if(atan > Math.PI * 5 / 8) {
                    direction = 'northeast';
                  } else if(atan > Math.PI * 3 / 8) {
                    direction = 'east';
                  } else if(atan > Math.PI / 8) {
                    direction = 'southeast';
                  } else if(atan > -Math.PI / 8) {
                    direction = 'south';
                  } else if(atan > -Math.PI * 3 / 8) {
                    direction = 'southwest';
                  } else if(atan > -Math.PI * 5 / 8) {
                    direction = 'west';
                  } else if(atan > -Math.PI * 7 / 8) {
                    direction = 'northwest';
                  } else {
                    direction = 'north';
                  }
                  if(evt.nativeEvent.button == 2) {
                    var action = animation.action;
                    animation.gotoAndPlay(direction + '_' + action);
                  } else {

                  }
                });
              }
            }
          });
        },

        function() {
          Ext.Array.each(me.animations, function(animation) {
            me.placeAnimation(animation);
          });
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
        Ext.Array.each(me.animations, function(animation) {
          me.stage.removeChild(animation);
        });
        me.animations = [];
        me.spradrns = {};
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
          me.animations.push(animation);
          me.spradrns[name] = spradrn;
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