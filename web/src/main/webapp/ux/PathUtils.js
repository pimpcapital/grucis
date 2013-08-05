Ext.define('Ext.ux.PathUtils', {
  statics: {
    getDirection: function(from, to) {
      var x = to.x - from.x;
      var y = to.y - from.y;
      var atan = Math.atan2(y, x);
      if(atan > Math.PI * 7 / 8) {
        return 'north';
      } if(atan > Math.PI * 5 / 8) {
        return 'northeast';
      } if(atan > Math.PI * 3 / 8) {
        return 'east';
      } if(atan > Math.PI / 8) {
        return 'southeast';
      } if(atan > -Math.PI / 8) {
        return 'south';
      } if(atan > -Math.PI * 3 / 8) {
        return 'southwest';
      } if(atan > -Math.PI * 5 / 8) {
        return 'west';
      } if(atan > -Math.PI * 7 / 8) {
        return 'northwest';
      }
      return 'north';
    },

    getPath: function(from, to) {
      var ret = [];
      var x = from.x;
      var y = from.y;
      while(x != to.x || y != to.y) {
        if(to.x > x) x++;
        else if(to.x < x) x--;
        if(to.y > y) y++;
        else if(to.y < y) y--;
        ret.push({x: x, y: y});
      }
      return ret;
    }
  }
});