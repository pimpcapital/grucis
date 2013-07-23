Ext.define('GDE.view.Viewport', {
  extend: 'Ext.container.Viewport',
  layout: 'border',
  items: [
    {
      xtype: 'panel',
      region: 'north',
      html: 'Grucis Dev Environment',
      minHeight: '50'
    }, {
      xtype: 'tabpanel',
      region: 'center',
      items: [
        {xtype: 'bitmap'}
      ]
    }
  ]
});