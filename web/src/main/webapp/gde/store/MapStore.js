Ext.define('GDE.store.MapStore', {
  extend: 'Ext.data.Store',
  model: 'GDE.model.Map',
  buffered: true,
  pageSize: 200

});