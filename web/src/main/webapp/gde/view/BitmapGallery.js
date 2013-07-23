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
      flex: 1,
      columns: [
        {text: 'ID', dataIndex: 'id'},
        {text: 'Width', dataIndex: 'width'},
        {text: 'Height',  dataIndex: 'height'}
      ]
    }
  ]
});