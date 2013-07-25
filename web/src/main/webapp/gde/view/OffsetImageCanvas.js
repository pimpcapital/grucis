Ext.define('GDE.view.OffsetImageCanvas', {
  extend: 'Ext.ux.EaselPanel',
  alias: 'widget.offsetimage',

  initComponent: function() {
    var me = this;
    me.callParent(arguments);

    me.draw = function() {
      Ext.ux.EaselPanelUtils.drawScales(me.stage, {
        rgb: '#c8c8c8'
      });
    };
  }
});