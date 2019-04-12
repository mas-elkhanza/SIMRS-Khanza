(function ($) {
	"use strict";
	
    $(document).on('click', '[ui-toggle-class]', function (e) {
        e.preventDefault();
        var $this = $(e.target);
        $this.attr('ui-toggle-class') || ($this = $this.closest('[ui-toggle-class]'));
		var classes = $this.attr('ui-toggle-class').split(','),
			targets = ($this.attr('ui-target') && $this.attr('ui-target').split(',')) || ($this.attr('target') && $this.attr('target').split(',')) || Array($this),
			key = 0;
		$.each(classes, function( index, value ) {
			var target = $( targets[(targets.length && key)] ),
                current = target.attr('ui-class'),
                _class = classes[index];

            (current != _class) && target.removeClass( target.attr('ui-class') );
			target.toggleClass(classes[index]);
			target.attr('ui-class', _class);
			key ++;
		});
		$this.toggleClass('active');

    });
})(jQuery);
