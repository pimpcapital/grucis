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
    'BitmapGallery',
    'OffsetImageCanvas'
  ],
  name: 'GDE',
  autoCreateViewport: true

});