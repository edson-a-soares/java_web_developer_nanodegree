const Files = function(fragment) {

    let endpoint = baseurl(fragment);

    function add (data) {

        $.ajax({
            url: endpoint,
            type: 'post',
            cache: false,
            crossDomain: true,
            processData: false,
            contentType: false,
            enctype: 'multipart/form-data',
            data: new FormData(data.form),
            headers: {
                'X-CSRF-TOKEN': data._csrf,
            }

        }).done(function (response) {
            $('#alertModal').trigger("response:show", response);
            $('.alert-close').click(function () {
                 $('body').trigger("page:reload");
            });

        }).fail(function (xhr) {
            $('#alertModal').trigger("response:show", xhr.responseText);

        });

    }

    function remove (data) {

        $.ajax({
            url: `${endpoint}/${data.fileId}`,
            type: 'delete',
            headers: {
                'X-CSRF-TOKEN': data._csrf,
            },
            timeout: 10000,
            crossDomain: true

        }).done(function (response) {
            $('#alertModal').trigger("response:show", response);
            $('.alert-close').click(function () {
                $('body').trigger("page:reload");
            });

        }).fail(function (xhr) {
            $('#alertModal').trigger("response:show", xhr.responseText);

        });

    }

    return {
        store : function (data) {
            add(data);
        },

        remove : function(data) {
            remove(data)
        },

    }

}

function deleteFile(fileId) {
    const data = {
        _csrf : $('input[name ="_csrf"]').val(),
        fileId : fileId
    }

    const fragment = $('#files-form').attr('action');
    const files = Files(fragment);
    files.remove(data);
}

$('#files-form')
    .on("files:add", function (event, data, fragment) {
        event.preventDefault();
        const files = Files(fragment);
        files.store(data);
    });

$('#upload-file').click(function () {
    const form = $('#files-form');
    const data = {
        _csrf : $('input[name ="_csrf"]').val(),
        form : form[0]
    }

    form.trigger("files:add", [data, form.attr('action')]);
});
