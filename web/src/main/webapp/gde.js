Ext.application({
  appFolder: 'gde',
  controllers: [],
  models: [
    'Adrn',
    'SprAdrn'
  ],
  stores: [
    'AdrnStore',
    'SprAdrnStore'
  ],
  views: [
    'AnimationGallery',
    'BitmapExportWindow',
    'BitmapGallery',
    'OffsetImageCanvas',
    'SpriteAnimationCanvas'
  ],
  name: 'GDE',
  autoCreateViewport: true,
  launch: function() {
    Ext.state.Manager.setProvider(new Ext.state.CookieProvider({
      expires: new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 7))
    }));
  }

});