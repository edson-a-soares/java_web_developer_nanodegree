const credentials = $.extend(Repository, {

    add : function (credential) {
        if (credential.hasOwnProperty("resourceId") && $.trim(credential.resourceId) !== "") {
            this.edit(credential,
                function (response) {
                    $('#credentialModal').trigger("modal:close");
                    $('#alertModal').trigger("response:show", response);
                    $('.alert-close').click(function () {
                        $('body').trigger("page:reload");
                    });

                },
                function (response) {
                    $('#credentialModal').trigger("modal:close");
                    $('#alertModal').trigger("response:show", response);

                }
            );

        } else {
            this.create(credential,
                function (response) {
                    $('#credentialModal').trigger("modal:close");
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

    decryptPassword : function (credential, callback) {

        $.ajax({
            url: `${this.endpoint}/${credential.resourceId}`,
            type: 'get',
            headers: {
                'X-CSRF-TOKEN': credential._csrf,
            },
            timeout: 10000,
            crossDomain: true,

        }).done(function (response) {
            callback(response);

        }).fail(function (xhr) {
            callback("Error decrypting password.");

        });

    },

    remove : function (credential) {
        this._delete(credential,
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

function addCredentialModal() {
    $('#credential-id').val('');
    $('#credential-url').val('');
    $('#credential-username').val('');
    $('#credential-password').val('');
    $('#credentialModal').modal('show');
}

function editCredentialModal(credentialId, url, username) {
    const data = {
        _csrf : $('input[name ="_csrf"]').val(),
        resourceId : credentialId
    }

    // Handle password decrypting ...
    const fragment = $('#credentials-form').attr('action');
    credentials.targetResource(fragment);
    credentials.decryptPassword(data, function (decryptedPassword) {
        $('#credential-password').val(decryptedPassword ? decryptedPassword : '');
    });
    $('#credential-url').val(url ? url : '');
    $('#credential-username').val(username ? username : '');
    $('#credential-id').val(credentialId ? credentialId : '');
    $('#credentialModal').modal('show');
}

function deleteCredential(credentialId) {
    const data = {
        _csrf : $('input[name ="_csrf"]').val(),
        resourceId : credentialId
    }

    const fragment = $('#credentials-form').attr('action');
    credentials.targetResource(fragment);
    credentials.remove(data);
}

$('#credentialModal')
    .on("modal:close", function (event) {
        event.preventDefault();
        $(this).modal('toggle');

    })
    .on("credentials:add", function (event, data, fragment) {
        event.preventDefault();
        credentials.targetResource(fragment);
        credentials.add(data);

    });

$('#save-credential').click(function () {
    const form = $('#credentials-form');
    const data = form.serializeArray();

    let object = {};
    $.map(data, function(value, index) {
            object[ data[index].name ] = data[index].value;
        }
    );

    object._csrf = $('input[name ="_csrf"]').val()
    $('#credentialModal').trigger("credentials:add", [object, form.attr('action')]);
});
