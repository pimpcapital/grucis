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
      g.setStrokeStyle(options.thickness)
        .beginStroke(options.rgb)
        .moveTo(x, y)
        .lineTo(x + length, y);
      var minorTicks = this.getTicks(x, x + length, origin, options.minorTickInterval);
      var majorTicks = this.getTicks(x, x + length, origin, options.majorTickInterval);
      minorTicks = Ext.Array.difference(minorTicks, majorTicks);
      Ext.Array.each(minorTicks, function(t) {
        g.moveTo(t, y)
          .lineTo(t, y - options.minorTickLength);
      });
      Ext.Array.each(majorTicks, function(t) {
        g.moveTo(t, y)
          .lineTo(t, y - options.majorTickLength);
        var text = new createjs.Text(t - origin, options.font, options.rgb);
        text.x = t - (text.getMeasuredWidth() / 2);
        text.y = y + options.majorTickLength / 2;
        stage.addChild(text);
      });
      stage.addChild(new createjs.Shape(g));
    },

    createVerticalScale: function(stage, x, y, length, origin, options) {
      var g = new createjs.Graphics();
      g.setStrokeStyle(options.thickness)
        .beginStroke(options.rgb)
        .moveTo(x, y)
        .lineTo(x, y + length);
      var minorTicks = this.getTicks(y, y + length, origin, options.minorTickInterval);
      var majorTicks = this.getTicks(y, y + length, origin, options.majorTickInterval);
      minorTicks = Ext.Array.difference(minorTicks, majorTicks);
      Ext.Array.each(minorTicks, function(t) {
        g.moveTo(x, t)
          .lineTo(x + options.minorTickLength, t);
      });
      Ext.Array.each(majorTicks, function(t) {
        g.moveTo(x, t)
          .lineTo(x + options.majorTickLength, t);
        var text = new createjs.Text(origin - t, options.font, options.rgb);
        text.x = x - (text.getMeasuredWidth() / 2) - options.majorTickLength;
        text.y = t - text.getMeasuredHeight() / 2;
        stage.addChild(text);
      });
      stage.addChild(new createjs.Shape(g));
    },

    getOrigin: function(stage) {
      return {
        x: Math.floor(stage.canvas.width / 2),
        y: Math.floor(stage.canvas.height / 2)
      };
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
      var origin = this.getOrigin(stage);
      var originText = new createjs.Text('O', 'italic 16px Arial', options.rgb);
      originText.x = origin.x - 15;
      originText.y = origin.y + 2;
      stage.addChild(originText);
      this.createHorizontalScale(stage, 0, origin.y, stage.canvas.width, origin.x, options);
      this.createVerticalScale(stage, origin.x, 0, stage.canvas.height, origin.y, options);
    },

    drawIsometricTile: function(stage, x, y, south, east, options) {
      var g = new createjs.Graphics();
      var left = -options.width / 2;
      var right = options.width / 2;
      var top = -options.height / 2;
      var bottom = options.height / 2;
      g.beginFill(options.fill)
        .beginStroke(options.stroke)
        .moveTo(left, 0)
        .lineTo(0, bottom)
        .lineTo(right, 0)
        .lineTo(0, top)
        .closePath();
      var shape = new createjs.Shape(g);
      if(options.listeners) {
        Ext.iterate(options.listeners, function(type, fn) {
          shape.addEventListener(type, fn);
        });
      }
      shape.x = x;
      shape.y = y;
      shape.east = east;
      shape.south = south;
      stage.addChild(shape);
    },

    drawIsometricGrid: function(stage, origin, options) {
      var me = this;
      var xStep = options.width / 2;
      var yStep = options.height / 2;
      me.drawIsometricTile(stage, origin.x, origin.y, origin.south, origin.east, options);
      for(var i = 0; i <= options.radius; i++) {
        var x = origin.x - i * options.width;
        var y = origin.y;
        var south = origin.south - i;
        var east = origin.east - i;
        var j;
        for(j = 0; j < i * 2; j++) {
          me.drawIsometricTile(stage, x, y, south, east, options);
          x += xStep;
          y += yStep;
          south++;
        }
        for(j = 0; j < i * 2; j++) {
          me.drawIsometricTile(stage, x, y, south, east, options);
          x += xStep;
          y -= yStep;
          east++;
        }
        for(j = 0; j < i * 2; j++) {
          me.drawIsometricTile(stage, x, y, south, east, options);
          x -= xStep;
          y -= yStep;
          south--;
        }
        for(j = 0; j < i * 2; j++) {
          me.drawIsometricTile(stage, x, y, south, east, options);
          x -= xStep;
          y += yStep;
          east--;
        }
      }

    }
  }
});