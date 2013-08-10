Ext.define('GDE.model.Map', {
  extend: 'Ext.data.Model',
  fields: [
    {name: 'id', type: 'int'},
    {name: 'name', type: 'string'},
    {name: 'east', type: 'int'},
    {name: 'south', type: 'int'}
  ],
  proxy: {
    type: 'rest',
    url: '/api/map/maps',
    reader: {
      root: 'views',
      totalProperty: 'total'
    }
  }
});