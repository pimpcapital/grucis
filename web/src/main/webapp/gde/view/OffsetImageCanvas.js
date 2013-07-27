Ext.define('GDE.view.OffsetImageCanvas', {
  extend: 'Ext.ux.EaselPanel',
  alias: 'widget.offsetimage',
  bitmaps: [],
  adrns: {},
  initComponent: function () {
    var me = this;
    Ext.apply(me, {
      drawPlan: [
        function () {
          Ext.ux.EaselPanelUtils.drawScales(me.stage, {
            rgb: '#c8c8c8'
          });
        },

        function() {
          Ext.Array.each(me.bitmaps, function(bitmap) {
            me.placeImage(bitmap);
          });
        }
      ],

      placeImage: function(bitmap) {
        var origin = Ext.ux.EaselPanelUtils.getOrigin(me.stage);
        var adrn = me.adrns[bitmap.name];
        bitmap.x = origin.x + adrn.get('xOffset');
        bitmap.y = origin.y + adrn.get('yOffset');
        me.stage.addChild(bitmap);
        me.stage.update();
      },

      loadImage: function (adrn, keepPrevious) {
        var name = adrn.get('id') + '.png';
        var url = 'api/bitmap/image/' + name;
        var queue = new createjs.LoadQueue(true);
        if(!keepPrevious) {
          Ext.Array.each(me.bitmaps, function(bitmap) {
            me.stage.removeChild(bitmap);
          });
          me.bitmaps = [];
          me.adrns = {};
        }
        queue.addEventListener('fileload', function(payload) {
          var bitmap = new createjs.Bitmap(payload.result);
          bitmap.name = name;
          me.bitmaps.push(bitmap);
          me.adrns[name] = adrn;
          me.placeImage(bitmap);
        });
        queue.loadFile(url);
      }
    });
    me.callParent(arguments);
  }
});