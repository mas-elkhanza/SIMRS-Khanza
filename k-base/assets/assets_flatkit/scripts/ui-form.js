(function ($) {
	"use strict";

	$('input, textarea').each(function(){
		$(this).val() ? $(this).addClass('has-value') : $(this).removeClass('has-value');
	});
	$(document).on('blur', 'input, textarea', function(e){
		$(this).val() ? $(this).addClass('has-value') : $(this).removeClass('has-value');
	});

})(jQuery);
