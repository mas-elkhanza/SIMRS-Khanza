(function ($) {
  "use strict";
  
    $(document).on('click', '#dayview', function() {
      $('.fullcalendar').fullCalendar('changeView', 'agendaDay')
    });

    $(document).on('click', '#weekview', function() {
      $('.fullcalendar').fullCalendar('changeView', 'agendaWeek')
    });

    $(document).on('click', '#monthview', function() {
      $('.fullcalendar').fullCalendar('changeView', 'month')
    });

    $(document).on('click', '#todayview', function() {
      $('.fullcalendar').fullCalendar('today')
    });

})(jQuery);
