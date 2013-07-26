Ext.define('Ext.ux.EaselPanel', {
  extend: 'Ext.ux.CanvasPanel',
  alias: 'widget.easel',
  drawPlan: [],
  initComponent: function() {
    var me = this;
    me.callParent(arguments);
    me.on('render', function() {
      me.stage = new createjs.Stage(me.canvasEl);
    });

    me.draw = function() {
      Ext.Array.each(me.drawPlan, function(fn) {
        return fn();
      })
    };

    me.resize = function(width, height, oldWidth, oldHeight) {
      me.stage.removeAllChildren();
      me.draw();
      me.stage.update();
    };

    me.on('resize', function(me, width, height, oldWidth, oldHeight) {
      me.stage.canvas.width = width;
      me.stage.canvas.height = height;
      me.resize(width, height, oldWidth, oldHeight);
    });
  }

});