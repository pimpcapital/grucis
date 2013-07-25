Ext.define('Ext.ux.EaselPanelUtils', {
  statics: {
    getTicks: function(start, end, origin, interval) {
      var ticks = [];
      var t = origin;
      do {
        t -= interval;
        if(t < start) break;
        ticks.push(t);
      } while(true);
      t = origin;
      do {
        t += interval;
        if(t > end) break;
        ticks.push(t);
      } while(true);
      return Ext.Array.sort(ticks);
    },

    createHorizontalScale: function(stage, x, y, length, origin, options) {
      var g = new createjs.Graphics();
      g.setStrokeStyle(options.thickness);
      g.beginStroke(options.rgb);
      g.moveTo(x, y);
      g.lineTo(x + length, y);
      var minorTicks = this.getTicks(x, x + length, origin, options.minorTickInterval);
      var majorTicks = this.getTicks(x, x + length, origin, options.majorTickInterval);
      minorTicks = Ext.Array.difference(minorTicks, majorTicks);
      Ext.Array.each(minorTicks, function(t) {
        g.moveTo(t, y);
        g.lineTo(t, y - options.minorTickLength);
      });
      Ext.Array.each(majorTicks, function(t) {
        g.moveTo(t, y);
        g.lineTo(t, y - options.majorTickLength);
        var text = new createjs.Text(t - origin, options.font, options.rgb);
        text.x = t - (text.getMeasuredWidth() / 2);
        text.y = y + options.majorTickLength / 2;
        stage.addChild(text);
      });
      stage.addChild(new createjs.Shape(g));
    },

    createVerticalScale: function(stage, x, y, length, origin, options) {
      var g = new createjs.Graphics();
      g.setStrokeStyle(options.thickness);
      g.beginStroke(options.rgb);
      g.moveTo(x, y);
      g.lineTo(x, y + length);
      var minorTicks = this.getTicks(y, y + length, origin, options.minorTickInterval);
      var majorTicks = this.getTicks(y, y + length, origin, options.majorTickInterval);
      minorTicks = Ext.Array.difference(minorTicks, majorTicks);
      Ext.Array.each(minorTicks, function(t) {
        g.moveTo(x, t);
        g.lineTo(x + options.minorTickLength, t);
      });
      Ext.Array.each(majorTicks, function(t) {
        g.moveTo(x, t);
        g.lineTo(x + options.majorTickLength, t);
        var text = new createjs.Text(origin - t, options.font, options.rgb);
        text.x = x - (text.getMeasuredWidth() / 2) - options.majorTickLength;
        text.y = t - text.getMeasuredHeight() / 2 - 1;
        stage.addChild(text);
      });
      stage.addChild(new createjs.Shape(g));
    },

    drawScales: function(stage, options) {
      options = options || {};
      Ext.applyIf(options, {
        thickness: 1,
        minorTickInterval: 20,
        minorTickLength: 5,
        majorTickInterval: 100,
        majorTickLength: 15,
        rgb: createjs.Graphics.getRGB(0,0,0)
      });
      var middle = Math.floor(stage.canvas.height / 2);
      var center = Math.floor(stage.canvas.width / 2);
      var origin = new createjs.Text('O', 'italic 16px Arial', options.rgb);
      origin.x = center - 15;
      origin.y = middle + 2;
      stage.addChild(origin);
      this.createHorizontalScale(stage, 0, middle, stage.canvas.width, center, options);
      this.createVerticalScale(stage, center, 0, stage.canvas.height, middle, options);

    }
  }
});