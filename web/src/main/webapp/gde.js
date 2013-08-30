var TILE_WIDTH = 64;
var TILE_HEIGHT = 48;

Ext.application({
  appFolder: 'gde',
  controllers: [],
  models: [
    'Adrn',
    'Map',
    'SprAdrn'
  ],
  stores: [
    'AdrnStore',
    'MapStore',
    'SprAdrnStore'
  ],
  views: [
    'AnimationGallery',
    'BitmapExportWindow',
    'BitmapGallery',
    'MapGallery',
    'OffsetImageCanvas',
    'SpriteAnimationCanvas',
    'TileMapCanvas'
  ],
  name: 'GDE',
  autoCreateViewport: true,
  launch: function() {
    Ext.state.Manager.setProvider(new Ext.state.CookieProvider({
      expires: new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 7))
    }));

    Ext.Ajax.timeout = 300000;
  }

});