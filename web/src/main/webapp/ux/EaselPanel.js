Ext.define('Ext.ux.EaselPanel', {
  extend: 'Ext.ux.CanvasPanel',
  alias: 'widget.easel',
  initComponent: function() {
    var me = this;
    me.callParent(arguments);
    me.on('render', function() {
      me.stage = new createjs.Stage(me.canvasEl);
    });

    me.draw = function() {
      var text = new createjs.Text('Hello World', '50px Arial', '#black');
      text.x = (me.stage.canvas.width - text.getMeasuredWidth()) / 2;
      text.y = (me.stage.canvas.height - text.getMeasuredHeight()) / 2;
      me.stage.addChild(text);
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