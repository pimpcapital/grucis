Ext.define('GDE.view.AnimationGallery', {
  extend: 'Ext.panel.Panel',
  alias: 'widget.animation',
  title: 'Animation Gallery',
  layout: {type: 'hbox', align: 'stretch'},
  initComponent: function() {
    var me = this;
    Ext.applyIf(me, {
      items: [
        {
          xtype: 'grid',
          store: 'SprAdrnStore',
          minWidth: 320,
          flex: 1,
          sortableColumns: false,
          columns: [
            {text: 'ID', dataIndex: 'id', flex: 1},
            {text: 'Action', dataIndex: 'actions', flex: 1}
          ],
          listeners: {
            beforerender: function(grid) {
              grid.getStore().load({});
            },
            selectionchange: function(sm, selected) {
              if(selected.length == 1) {
                me.down('spriteanimation').loadAnimation(selected[0]);
              }
            }
          }
        }, {
          xtype: 'spriteanimation',
          flex: 5
        }
      ]
    });
    me.callParent(arguments);
  }
});