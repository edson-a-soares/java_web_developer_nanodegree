const notes = $.extend(Repository, {

    add : function (note) {
        if (note.hasOwnProperty("resourceId") && $.trim(note.resourceId) !== "") {
            this.edit(note,
                function (response) {
                    $('#noteModal').trigger("modal:close");
                    $('#alertModal').trigger("response:show", response);
                    $('.alert-close').click(function () {
                        $('body').trigger("page:reload");
                    });

                },
                function (response) {
                    $('#noteModal').trigger("modal:close");
                    $('#alertModal').trigger("response:show", response);

                }
            );

        } else {
            this.create(note,
                function (response) {
                    $('#noteModal').trigger("modal:close");
                    $('#alertModal').trigger("response:show", response);
                    $('.alert-close').click(function () {
                        $('body').trigger("page:reload");
                    });

                },
                function (response) {
                    $('#noteModal').trigger("modal:close");
                    $('#alertModal').trigger("response:show", response);

                }
            );

        }

    },

    remove : function (note) {
        this._delete(note,
            function (response) {
                $('#noteModal').trigger("modal:close");
                $('#alertModal').trigger("response:show", response);
                $('.alert-close').click(function () {
                    $('body').trigger("page:reload");
                });

            },
            function (response) {
                $('#noteModal').trigger("modal:close");
                $('#alertModal').trigger("response:show", response);

            }
        );
    }

});

// For opening the note modal
function showNoteModal(noteId, noteTitle, noteDescription) {
    $('#note-id').val(noteId ? noteId : '');
    $('#note-title').val(noteTitle ? noteTitle : '');
    $('#note-description').val(noteDescription ? noteDescription : '');
    $('#noteModal').modal('show');
}

function deleteNote(noteId) {
    const data = {
        _csrf : $('input[name ="_csrf"]').val(),
        resourceId : noteId
    }

    const fragment = $('#notes-form').attr('action');
    notes.targetResource(fragment);
    notes.remove(data);

}

$('#noteModal')
    .on("modal:close", function (event) {
        event.preventDefault();
        $(this).modal('toggle');

    })
    .on("notes:add", function (event, data, fragment) {
        event.preventDefault();
        notes.targetResource(fragment);
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
    $('#noteModal').trigger("notes:add", [object, form.attr('action')]);
});
