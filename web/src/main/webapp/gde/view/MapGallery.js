Ext.define('GDE.view.MapGallery', {
  extend: 'Ext.panel.Panel',
  alias: 'widget.map',
  title: 'Map Gallery',
  layout: {type: 'hbox', align: 'stretch'},
  initComponent: function() {
    var me = this;
    Ext.applyIf(me, {
      items: [
        {
          xtype: 'grid',
          store: 'MapStore',
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
            {text: 'Name', dataIndex: 'name', flex: 1},
            {text: 'East', dataIndex: 'east', width: 80},
            {text: 'South',  dataIndex: 'south', width: 80}
          ],
          listeners: {
            beforerender: function(grid) {
              grid.getStore().load({});
            },
            selectionchange: function(sm, selected) {
              me.down('tilemap').loadMap(selected[0]);
            }
          }
        }, {
          xtype: 'tilemap',
          flex: 5
        }
      ]
    });
    me.callParent(arguments);
  }
});