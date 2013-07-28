Ext.define('GDE.view.Viewport', {
  extend: 'Ext.container.Viewport',
  layout: {type: 'vbox', align: 'stretch'},
  items: [
    {
      xtype: 'panel',
      html: 'Grucis Dev Environment',
      minHeight: '50'
    }, {
      xtype: 'tabpanel',
      flex: 1,
      activeTab: Ext.History.getToken(),
      listeners: {
        tabchange: function(tabpanel, newTab) {
          Ext.History.add(newTab.getId(), true)
        }
      },
      items: [
        {xtype: 'bitmap', id: 'bitmap'},
        {xtype: 'animation', id: 'animation'}
      ]
    }
  ]
});