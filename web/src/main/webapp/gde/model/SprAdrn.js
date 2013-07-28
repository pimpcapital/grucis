Ext.define('GDE.model.SprAdrn', {
  extend: 'Ext.data.Model',
  fields: [
    {name: 'id', type: 'int'},
    {name: 'address', type: 'int'},
    {name: 'actions', type: 'int'},
    {name: 'sound', type: 'int'}
  ],
  proxy: {
    type: 'rest',
    url: '/api/animation/spradrn',
    reader: {
      root: 'views',
      totalProperty: 'total'
    }
  }
});