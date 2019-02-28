/* Inline dependency:
 * jQuery resize event - v1.1 - 3/14/2010
 * http://benalman.com/projects/jquery-resize-plugin/
 *
 * Copyright (c) 2010 "Cowboy" Ben Alman
 * Dual licensed under the MIT and GPL licenses.
 * http://benalman.com/about/license/
 */
(function($,e,t){"$:nomunge";var i=[],n=$.resize=$.extend($.resize,{}),a,r=false,s="setTimeout",u="resize",m=u+"-special-event",o="pendingDelay",l="activeDelay",f="throttleWindow";n[o]=200;n[l]=20;n[f]=true;$.event.special[u]={setup:function(){if(!n[f]&&this[s]){return false}var e=$(this);i.push(this);e.data(m,{w:e.width(),h:e.height()});if(i.length===1){a=t;h()}},teardown:function(){if(!n[f]&&this[s]){return false}var e=$(this);for(var t=i.length-1;t>=0;t--){if(i[t]==this){i.splice(t,1);break}}e.removeData(m);if(!i.length){if(r){cancelAnimationFrame(a)}else{clearTimeout(a)}a=null}},add:function(e){if(!n[f]&&this[s]){return false}var i;function a(e,n,a){var r=$(this),s=r.data(m)||{};s.w=n!==t?n:r.width();s.h=a!==t?a:r.height();i.apply(this,arguments)}if($.isFunction(e)){i=e;return a}else{i=e.handler;e.handler=a}}};function h(t){if(r===true){r=t||1}for(var s=i.length-1;s>=0;s--){var l=$(i[s]);if(l[0]==e||l.is(":visible")){var f=l.width(),c=l.height(),d=l.data(m);if(d&&(f!==d.w||c!==d.h)){l.trigger(u,[d.w=f,d.h=c]);r=t||true}}else{d=l.data(m);d.w=0;d.h=0}}if(a!==null){if(r&&(t==null||t-r<1e3)){a=e.requestAnimationFrame(h)}else{a=setTimeout(h,n[o]);r=false}}}if(!e.requestAnimationFrame){e.requestAnimationFrame=function(){return e.webkitRequestAnimationFrame||e.mozRequestAnimationFrame||e.oRequestAnimationFrame||e.msRequestAnimationFrame||function(t,i){return e.setTimeout(function(){t((new Date).getTime())},n[l])}}()}if(!e.cancelAnimationFrame){e.cancelAnimationFrame=function(){return e.webkitCancelRequestAnimationFrame||e.mozCancelRequestAnimationFrame||e.oCancelRequestAnimationFrame||e.msCancelRequestAnimationFrame||clearTimeout}()}})(jQuery,this);

;(function($,echarts){
    'use strict';

    var Chart = function (element, options) {
        this.chart      = null;
        this.options    = null;
        this.$element   = null;

        this.init(element, options);
    };

    Chart.VERSION  = '1.0.0';

    Chart.TRANSITION_DURATION = 150;

    Chart.DEFAULTS = {
        showLoading: true,
        loadingOption : {
            text : "loading...",
            effect : 'whirling',
            textStyle : {
                fontSize : 20
            }
        },
        onClick: function () {null;},
        theme  : theme,
        option : {},
        url    : "",
        data   : {}
    };

    Chart.prototype.init = function (element, options) {
        this.$element  = $(element);
        this.options   = options.series ? options : this.getOptions(options);

        this.chart     = echarts.init(this.$element.get(0),this.options.theme);
        var that = this;
        this.$element.resize(function(e){
            setTimeout(function () {
                that.chart.resize();
            }, 200);
        });
    };

    Chart.prototype.getDefaults = function () {
        return Chart.DEFAULTS;
    };

    Chart.prototype.getOptions = function (options) {
        options = $.extend({}, this.getDefaults(), this.$element.data(), options);

        return options
    };

    Chart.prototype.getDelegateOptions = function () {
        var options  = {};
        var defaults = this.getDefaults();

        this._options && $.each(this._options, function (key, value) {
            if (defaults[key] != value) options[key] = value
        });

        return options
    };

    Chart.prototype.draw = function () {
        var that = this;
        var opt = this.options;
        opt.showLoading ? this.chart.showLoading(opt.loadingOption) : null;

        if (opt.series){
            this.setOption(opt);
            return;
        }

        if (opt.url) {
            $.get(opt.url, opt.data, function(json){
                if (typeof opt.option == 'function') {
                    var option = opt.option.call(this,json,that.chart);
                    that.setOption(option);
                }
            });
        } else {
            var option = typeof opt.option == 'function' ?
                opt.option.call(this,that.chart) :
                opt.option;
            this.setOption(option);
        }
    };

    Chart.prototype.setOption = function(option) {
        var fun = this.options.onClick;
        if (fun instanceof Function) {
            this.chart.on('click', this.options.onClick);
        }
        
        this.chart.setOption(option);
        this.chart.hideLoading();
    };

    function Plugin(option) {
        return this.each(function () {
            var $this   = $(this);
            var data    = $this.data('jquery.echarts');
            var options = $.extend({}, Chart.DEFAULTS, $this.data(), typeof option == 'object' && option);

            if (!data) $this.data('jquery.echarts', (data = new Chart(this, options)));

            data.draw();
        })
    }

    var old = $.fn.chart;

    $.fn.chart = Plugin;
    $.fn.chart.Constructor = Chart;

    // chart NO CONFLICT
    // ===================

    $.fn.chart.noConflict = function () {
        $.fn.chart = old;
        return this;
    }
})(jQuery,echarts);
