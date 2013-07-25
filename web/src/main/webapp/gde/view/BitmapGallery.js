Ext.define('GDE.view.BitmapGallery', {
  extend: 'Ext.panel.Panel',
  alias: 'widget.bitmap',
  title: 'Bitmap Gallery',
  layout: {type: 'hbox', align: 'stretch'},
  listeners: {
    activate: function(me) {
      me.down('grid').getStore().load({});
    }
  },
  items: [
    {
      xtype: 'grid',
      store: 'AdrnStore',
      minWidth: 320,
      flex: 1,
      sortableColumns: false,
      columns: [
        {text: 'ID', dataIndex: 'id', flex: 1},
        {text: 'Map Index', dataIndex: 'map', flex: 1},
        {text: 'Width', dataIndex: 'width', width: 80},
        {text: 'Height',  dataIndex: 'height', width: 80}
      ]
    }, {
      xtype: 'offsetimage',
      flex: 5
    }
  ]
});