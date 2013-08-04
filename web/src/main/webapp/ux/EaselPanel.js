Ext.define('Ext.ux.EaselPanel', {
  extend: 'Ext.ux.CanvasPanel',
  alias: 'widget.easel',
  drawPlan: [],
  initComponent: function() {
    var me = this;
    me.callParent(arguments);
    me.on('render', function() {
      me.stage = new createjs.Stage(me.canvasEl);
      me.stage.enableMouseOver();
    });

    me.draw = function() {
      Ext.Array.each(me.drawPlan, function(fn) {
        return fn();
      })
    };

    me.redraw = function() {
      me.stage.removeAllChildren();
      me.stage.clear();
      me.draw();
      me.stage.update();
    };

    me.resize = function(width, height, oldWidth, oldHeight) {
      me.redraw();
    };

    me.on('resize', function(me, width, height, oldWidth, oldHeight) {
      me.resize(width, height, oldWidth, oldHeight);
    });
  }

});