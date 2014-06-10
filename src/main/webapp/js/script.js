$(function () {

  $('.removeFromClipboard').submit(function (e) {
    e.preventDefault();
    var form = $(this);
    $.ajax({
      type: form.attr('method'),
      url: form.attr('action'),
      data: form.serialize(),
      success: function (data) {
        form.closest('tr').children('td, th').animate({padding: 0}).wrapInner('<div />').children().slideUp(function () {
          $(this).closest('tr').remove();
        });
      }
    });
  });

  $('.datepicker').datepicker({
    format: "yyyy-mm-dd",
    weekStart: 1,
    todayBtn: "linked"
  });

});