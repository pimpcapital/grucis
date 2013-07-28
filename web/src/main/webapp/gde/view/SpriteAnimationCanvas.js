Ext.define('GDE.view.SpriteAnimationCanvas', {
  extend: 'Ext.ux.EaselPanel',
  alias: 'widget.sprite',
  bitmaps: [],
  adrns: {},
  initComponent: function () {
    var me = this;
    Ext.apply(me, {
      tbar: [
        {
          icon: 'resources/images/disk_blue.png',
          text: 'Download',
          itemId: 'download',
          disabled: true,
          handler: function() {
          }
        }
      ],

      drawPlan: [
        function () {
          var origin = Ext.ux.EaselPanelUtils.getOrigin(me.stage);
          Ext.ux.EaselPanelUtils.drawIsometricGrid(me.stage, origin, {
            stroke: '#c8c8c8',
            radius: Math.min(8, (me.stage.canvas.width - 64) / 128, (me.stage.canvas.height - 48) / 96),
            width: 64,
            height: 48
          });
        }
      ]


    });
    me.callParent(arguments);
  }
});