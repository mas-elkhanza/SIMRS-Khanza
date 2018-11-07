(function ($) {
	"use strict";
  	$.extend( jQuery.easing,{
	    def: 'easeOutQuad',
	    easeInOutExpo: function (x, t, b, c, d) {
	        if (t==0) return b;
	        if (t==d) return b+c;
	        if ((t/=d/2) < 1) return c/2 * Math.pow(2, 10 * (t - 1)) + b;
	        return c/2 * (-Math.pow(2, -10 * --t) + 2) + b;
	    }
	});

	$(document).on('click', '[ui-scroll-to]', function (e) {
		e.preventDefault();
		var target = $('#'+$(this).attr('ui-scroll-to'));
		$('html,body').animate({
          scrollTop: target.offset().top
        }, 600, 'easeInOutExpo');
	});
})(jQuery);
