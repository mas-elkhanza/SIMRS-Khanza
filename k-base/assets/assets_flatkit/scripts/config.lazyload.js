// lazyload config
var MODULE_CONFIG = {
    easyPieChart:   [ 'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/jquery.easy-pie-chart/dist/jquery.easypiechart.fill.js' ],
    sparkline:      [ 'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/jquery.sparkline/dist/jquery.sparkline.retina.js' ],
    plot:           [ 'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/flot/jquery.flot.js',
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/flot/jquery.flot.resize.js',
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/flot/jquery.flot.pie.js',
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/flot.tooltip/js/jquery.flot.tooltip.min.js',
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/flot-spline/js/jquery.flot.spline.min.js',
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/flot.orderbars/js/jquery.flot.orderBars.js'],
    vectorMap:      [ 'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/bower-jvectormap/jquery-jvectormap-1.2.2.min.js',
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/bower-jvectormap/jquery-jvectormap.css', 
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/bower-jvectormap/jquery-jvectormap-world-mill-en.js',
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/bower-jvectormap/jquery-jvectormap-us-aea-en.js' ],
    dataTable:      [
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/datatables/media/js/jquery.dataTables.min.js',
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/plugins/integration/bootstrap/3/dataTables.bootstrap.js',
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/plugins/integration/bootstrap/3/dataTables.bootstrap.css'],
    footable:       [
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/footable/dist/footable.all.min.js',
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/footable/css/footable.core.css'
                    ],
    screenfull:     [
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/screenfull/dist/screenfull.min.js'
                    ],
    sortable:       [
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/html.sortable/dist/html.sortable.min.js'
                    ],
    nestable:       [
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/nestable/jquery.nestable.css',
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/nestable/jquery.nestable.js'
                    ],
    summernote:     [
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/summernote/dist/summernote.css',
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/summernote/dist/summernote.js'
                    ],
    parsley:        [
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/parsleyjs/dist/parsley.css',
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/parsleyjs/dist/parsley.min.js'
                    ],
    select2:        [
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/select2/dist/css/select2.min.css',
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/select2-bootstrap-theme/dist/select2-bootstrap.min.css',
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/select2-bootstrap-theme/dist/select2-bootstrap.4.css',
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/select2/dist/js/select2.min.js'
                    ],
    datetimepicker: [
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.css',
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.dark.css',
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/js/moment/moment.js',
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js'
                    ],
    chart:          [
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/js/echarts/build/dist/echarts-all.js',
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/js/echarts/build/dist/theme.js',
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/js/echarts/build/dist/jquery.echarts.js'
                    ],
    bootstrapWizard:[
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/twitter-bootstrap-wizard/jquery.bootstrap.wizard.min.js'
                    ],
    fullCalendar:   [
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/moment/moment.js',
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/fullcalendar/dist/fullcalendar.min.js',
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/fullcalendar/dist/fullcalendar.css',
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/jquery/fullcalendar/dist/fullcalendar.theme.css',
                      'http://172.16.200.2/k-base/assets/assets_flatkit/scripts/plugins/calendar.js'
                    ],
    dropzone:       [
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/js/dropzone/dist/min/dropzone.min.js',
                      'http://172.16.200.2/k-base/assets/assets_flatkit/libs/js/dropzone/dist/min/dropzone.min.css'
                    ]
  };
