function baseurl(fragment) {
    const loc = window.location;
    return loc.origin + fragment;
}

$(document).ready(function(){
    const _location = window.location;
    $('.nav-tabs a[href="' + _location.hash + '"]').tab('show');
});

$('.nav-link').click(function (event) {
    event.preventDefault();
    window.location.hash = $(event.target).attr("href");
});

$('#alertModal').on("response:modal", function (event, errorPage) {
    event.preventDefault();
    let html = $(errorPage);
    let message = html.filter('#resultMessageContent').html();

    $('#alertModalBody').html(message);
    $('#alertModal').modal('show');
});

$('body').on("page:reload", function (event) {
    event.preventDefault();
    $(this).fadeOut(1000, function(){
        location.reload(true);
    });
});
