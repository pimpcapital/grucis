Ext.define('GDE.view.BitmapExportWindow', {
  extend: 'Ext.window.Window',
  alias: 'widget.bitmapexport',
  layout: {type: 'vbox', align: 'stretch'},
  autoShow: true,
  closeAction: 'destroy',
  width: 1000,
  height: 800,
  bodyPadding: 20,
  listeners: {
    afterrender: function (me) {
      Ext.Ajax.request({
        url: 'api/bitmap/export/setting',
        success: function (res) {
          var setting = Ext.decode(res.responseText);
          me.down('#path').setValue(setting.path);
          me.down('#format').setValue(setting.format);
          me.down('#total').setValue(setting.total);
        }
      });
    }
  },
  defaultType: 'container',
  initComponent: function () {
    var me = this;
    var runner = new Ext.util.TaskRunner();
    Ext.apply(me, {
      items: [
        {
          layout: {type: 'vbox', align: 'stretch'},
          defaultType: 'displayfield',
          items: [
            {
              itemId: 'path',
              fieldLabel: 'Path'
            },
            {
              itemId: 'format',
              fieldLabel: 'Format'
            },
            {
              itemId: 'total',
              fieldLabel: 'Total'
            }
          ]
        },
        {
          xtype: 'progressbar',
          animate: true,
          margin: '15 5 10 5'
        },
        {
          layout: {type: 'hbox', align: 'stretch'},
          flex: 1,
          items: [
            {
              xtype: 'grid',
              width: 200,
              store: {
                fields: [
                  {name: 'source', type: 'string'},
                  {name: 'message', type: 'string'}
                ]
              },
              columns: [
                {text: 'Source', dataIndex: 'source', flex: 1}
              ],
              listeners: {
                selectionchange: function(sm, selected) {
                  if(selected.length == 1) {
                    me.down('textarea').setValue(selected[0].get('message'));
                  }
                }
              }
            },
            {
              xtype: 'textarea',
              disabled: true,
              margin: '0 0 0 10',
              flex: 1
            }
          ]
        }
      ],
      buttons: [
        {
          text: 'Cancel'
        },
        {
          text: 'Export',
          handler: function () {
            Ext.Ajax.request({
              url: 'api/bitmap/export/start',
              method: 'POST',
              success: function () {
                var task = Ext.TaskManager.start({
                  run: function () {
                    Ext.Ajax.request({
                      url: 'api/bitmap/export/update',
                      success: function (res) {
                        var progress = Ext.decode(res.responseText);
                        me.down('progressbar').updateProgress(progress.percent, progress.current);
                        me.down('grid').store.loadData(progress.errors);
                        if(progress.finished) {
                          runner.stop(task);
                        }
                      },
                      failure: function() {
                        me.down('progressbar').updateText('ERROR: Server is currently unavailable');
                        runner.stop(task);
                      }
                    });
                  },
                  interval: 2000
                });
                runner.start(task);
              }
            });
          }
        }
      ]
    });
    me.callParent(arguments);
  }

});