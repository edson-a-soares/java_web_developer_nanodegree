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

$('#alertModal').on("response:show", function (event, errorPage) {
    event.preventDefault();
    event.stopPropagation();
    let html = $(errorPage);
    let message = html.filter('#resultMessageContent').html();

    $('#alertModalBody').html(message);
    $('#alertModal').modal('show');
});

$('body').on("page:reload", function (event) {
    event.preventDefault();
    event.stopPropagation();
    $(this).fadeOut(1500, function(){
        location.reload(true);
    });
});

const Repository = {

    targetResource : function (fragment) {
        this.endpoint = baseurl(fragment);
    },

    create : function (data, successCallback, failureCallback) {

        $.ajax({
            url: this.endpoint,
            type: 'post',
            crossDomain: true,
            data: $.param(data),
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            timeout: 10000,
            headers: {
                'X-CSRF-TOKEN': data._csrf,
            }

        }).done(function (response) {
            successCallback(response);

        }).fail(function (xhr) {
            failureCallback(xhr.responseText);

        });

    },

    edit : function (data, successCallback, failureCallback) {

        $.ajax({
            url: `${this.endpoint}/${data.resourceId}`,
            type: 'put',
            beforeSend: function() {},
            data: $.param(data),
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            timeout: 10000,
            headers: {
                'X-CSRF-TOKEN': data._csrf
            }

        }).done(function (response) {
            successCallback(response);

        }).fail(function (xhr) {
            failureCallback(xhr.responseText);

        });

    },

    _delete : function (data, successCallback, failureCallback) {

        $.ajax({
            url: `${this.endpoint}/${data.resourceId}`,
            type: 'delete',
            headers: {
                'X-CSRF-TOKEN': data._csrf,
            },
            timeout: 10000,
            crossDomain: true,

        }).done(function (response) {
            successCallback(response);

        }).fail(function (xhr) {
            failureCallback(xhr.responseText);

        });

    }

}
