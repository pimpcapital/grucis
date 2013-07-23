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
      items: [
        {xtype: 'bitmap'}
      ]
    }
  ]
});