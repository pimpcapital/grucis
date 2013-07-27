Ext.define('GDE.model.Adrn', {
  extend: 'Ext.data.Model',
  fields: [
    {name: 'id', type: 'int'},
    {name: 'width', type: 'int'},
    {name: 'height', type: 'int'},
    {name: 'xOffset', type: 'int'},
    {name: 'yOffset', type: 'int'},
    {name: 'map', type: 'int'}
  ],
  proxy: {
    type: 'rest',
    url: '/api/bitmap/adrns',
    reader: {
      root: 'views',
      totalProperty: 'total'
    }
  }
});