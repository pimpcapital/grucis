Ext.define('GDE.view.SpriteAnimationCanvas', {
  extend: 'Ext.ux.EaselPanel',
  alias: 'widget.spriteanimation',
  animations: [],
  spradrns: {},
  initComponent: function () {
    var me = this;
    createjs.Ticker.setFPS(50);
    Ext.apply(me, {
      drawPlan: [
        function () {
          var origin = Ext.ux.EaselPanelUtils.getOrigin(me.stage);
          var radius = Math.floor(Math.min(8, (me.stage.canvas.width - 64) / 128, (me.stage.canvas.height - 48) / 96));
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
                console.log('south: ' + evt.target.south + ' east: ' + evt.target.east);
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
        var origin = Ext.ux.EaselPanelUtils.getOrigin(me.stage);
        animation.x = origin.x;
        animation.y = origin.y;
        animation.gotoAndPlay("south_attack");
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