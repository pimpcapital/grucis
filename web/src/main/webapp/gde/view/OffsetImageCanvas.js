Ext.define('GDE.view.OffsetImageCanvas', {
  extend: 'Ext.ux.EaselPanel',
  alias: 'widget.offsetimage',
  bitmaps: [],
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
        bitmap.x = origin.x;
        bitmap.y = origin.y;
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
        }
        queue.addEventListener('fileload', function(payload) {
          var bitmap = new createjs.Bitmap(payload.result);
          bitmap.name = name;
          me.bitmaps.push(bitmap);
          me.placeImage(bitmap);
        });
        queue.loadFile(url);
      }
    });
    me.callParent(arguments);
  }
});