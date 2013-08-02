Ext.define('Ext.ux.CanvasPanel', {
  extend: 'Ext.panel.Panel',
  alias: 'widget.canvas',
  html: '<canvas></canvas>',
  disableContextMenu: true,
  initComponent: function() {
    var me = this;
    me.callParent(arguments);
    me.on('render', function() {
      me.canvasEl = $(me.getEl().dom).find('canvas')[0];
      if(me.disableContextMenu) me.canvasEl.oncontextmenu = function() {return false};
    });
    me.on('resize', function(me, width, height, oldWidth, oldHeight) {
      me.canvasEl.width = width;
      me.canvasEl.height = height;
    });
  }
});