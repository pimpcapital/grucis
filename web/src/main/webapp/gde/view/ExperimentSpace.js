Ext.define('GDE.view.ExperimentSpace', {
  extend: 'Ext.panel.Panel',
  alias: 'widget.experiment',
  title: 'Experiment Space',
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
              me.down('world').loadMap(selected[0].get('id'));
            }
          }
        }, {
          xtype: 'world',
          flex: 5
        }, {

        }
      ]
    });
    me.callParent(arguments);
  }
});