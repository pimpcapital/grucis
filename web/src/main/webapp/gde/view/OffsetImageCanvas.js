Ext.define('GDE.view.OffsetImageCanvas', {
  extend: 'Ext.ux.EaselPanel',
  alias: 'widget.offsetimage',
  initComponent: function () {
    var me = this;
    Ext.apply(me, {
      drawPlan: [
        function () {
          Ext.ux.EaselPanelUtils.drawScales(me.stage, {
            rgb: '#c8c8c8'
          });
        }
      ],
      loadImage: function(adrn) {

      }
    });
    me.callParent(arguments);
  }
});