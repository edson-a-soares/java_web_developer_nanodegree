const Notes = function(fragment) {

    let endpoint = baseurl(fragment);

    function create (data) {
        const defer = $.Deferred();

        $.ajax({
            url: endpoint,
            type: 'post',
            crossDomain: true,
            data: $.param(data),
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            beforeSend: function() {},
            timeout: 10000,
            headers: {
                'X-CSRF-TOKEN': data._csrf,
            }

        }).done(function (response) {
            $('#noteModal').trigger("modal:close");
            $('#alertModal').trigger("response:show", response);
            $('.alert-close').click(function () {
                $('body').trigger("page:reload");
            });

        }).fail(function (xhr) {
            $('#noteModal').trigger("modal:close");
            $('#alertModal').trigger("response:show", xhr.responseText);

        });

        return defer.promise();
    }

    function edit (data) {
        const defer = $.Deferred();

        $.ajax({
            url: `${endpoint}/${data.noteId}`,
            type: 'put',
            beforeSend: function() {},
            data: $.param(data),
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            timeout: 10000,
            headers: {
                'X-CSRF-TOKEN': data._csrf
            }

        }).done(function (response) {
            $('#noteModal').trigger("modal:close");
            $('#alertModal').trigger("response:show", response);
            $('.alert-close').click(function () {
                $('body').trigger("page:reload");
            });

        }).fail(function (xhr) {
            $('#noteModal').trigger("modal:close");
            $('#alertModal').trigger("response:show", xhr.responseText);

        });

        return defer.promise();
    }

    function remove (data) {
        const defer = $.Deferred();

        $.ajax({
            url: `${endpoint}/${data.noteId}`,
            type: 'delete',
            headers: {
                'X-CSRF-TOKEN': data._csrf,
            },
            timeout: 10000,
            crossDomain: true,
            beforeSend: function() {}

        }).done(function (response) {
            $('#noteModal').trigger("modal:close");
            $('#alertModal').trigger("response:show", response);
            $('.alert-close').click(function () {
                $('body').trigger("page:reload");
            });

        }).fail(function (xhr) {
            $('#noteModal').trigger("modal:close");
            $('#alertModal').trigger("response:show", xhr.responseText);

        });

        return defer.promise();
    }

    return {
        add : function (data) {
            if (data.hasOwnProperty("noteId") && $.trim(data.noteId) !== "")
                edit(data);
            else
                create(data);

        },

        remove : function(data) {
            remove(data)
        }
    }

}

// For opening the note modal
function showNoteModal(noteId, noteTitle, noteDescription) {

    $('#note-id').val(noteId ? noteId : '');
    $('#note-title').val(noteTitle ? noteTitle : '');
    $('#note-description').val(noteDescription ? noteDescription : '');

    $('#noteModal')
        .modal('show')
        .on("modal:close", function (event) {
            event.preventDefault();
            $(this).modal('toggle');

        })
        .on("note:add", function (event, data, fragment) {
            event.preventDefault();
            const notes = Notes(fragment);
            notes.add(data);

        });

    $('#save-note').click(function () {
        const form = $('#notes-form');
        const data = form.serializeArray();

        let object = {};
        $.map(data, function(value, index) {
                object[ data[index].name ] = data[index].value;
            }
        );

        object._csrf = $('input[name ="_csrf"]').val();
        $('#noteModal').trigger("note:add", [object, form.attr('action')]);
    });

}

function deleteNote(noteId) {

    const data = {
        _csrf : $('input[name ="_csrf"]').val(),
        noteId : noteId
    }

    const fragment = $('#notes-form').attr('action');
    const notes = Notes(fragment);
    notes.remove(data);

}


