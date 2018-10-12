(function ($) {
	"use strict";
  	
	uiLoad.load(MODULE_CONFIG.screenfull);
	$(document).on('click', '[ui-fullscreen], [data-ui-fullscreen]', function (e) {
		e.preventDefault();
		if (screenfull.enabled) {
		  screenfull.toggle();
		}
	});
})(jQuery);
