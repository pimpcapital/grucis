Ext.define('Ext.ux.CanvasPanel', {
  extend: 'Ext.panel.Panel',
  alias: 'widget.canvas',
  html: '<canvas></canvas>',
  initComponent: function() {
    var me = this;
    me.callParent(arguments);
    me.on('render', function() {
      me.canvasEl = $(me.getEl().dom).find('canvas')[0];
    });
    me.on('resize', function(me, width, height, oldWidth, oldHeight) {
      me.stage.canvas.width = width;
      me.stage.canvas.height = height;
    });
  }
});