Ext.define('GDE.view.BitmapGallery', {
  extend: 'Ext.panel.Panel',
  alias: 'widget.bitmap',
  title: 'Bitmap Gallery',
  layout: {type: 'hbox', align: 'stretch'},
  initComponent: function() {
    var me = this;
    Ext.applyIf(me, {
      items: [
        {
          xtype: 'grid',
          store: 'AdrnStore',
          minWidth: 320,
          flex: 1,
          sortableColumns: false,
          tbar: [
            {
              text: 'Export',
              handler: function() {
                Ext.createByAlias('widget.bitmapexport');
              }
            }
          ],
          columns: [
            {text: 'ID', dataIndex: 'id', flex: 1},
            {text: 'Map Index', dataIndex: 'map', flex: 1},
            {text: 'Width', dataIndex: 'width', width: 80},
            {text: 'Height',  dataIndex: 'height', width: 80}
          ],
          listeners: {
            beforerender: function(grid) {
              grid.getStore().load({});
            },
            selectionchange: function(sm, selected) {
              if(selected.length == 1) {
                me.down('offsetimage').loadImage(selected[0]);
              }
            }
          }
        }, {
          xtype: 'offsetimage',
          flex: 5
        }
      ]
    });
    me.callParent(arguments);
  }
});