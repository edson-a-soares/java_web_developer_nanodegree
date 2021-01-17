const Credentials = function(fragment) {

    let endpoint = baseurl(fragment);

    function create (data) {
        const defer = $.Deferred();

        $.ajax({
            url: endpoint,
            type: 'post',
            crossDomain: true,
            data: $.param(data),
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            timeout: 10000,
            headers: {
                'X-CSRF-TOKEN': data._csrf,
            }

        }).done(function (response) {
            $('#credentialModal').trigger("modal:close");
            $('#alertModal').trigger("response:show", response);
            $('.alert-close').click(function () {
                 $('body').trigger("page:reload");
            });

        }).fail(function (xhr) {
            $('#credentialModal').trigger("modal:close");
            $('#alertModal').trigger("response:show", xhr.responseText);

        });

        return defer.promise();
    }

    function edit (data) {
        const defer = $.Deferred();

        console.log(data);

        $.ajax({
            url: `${endpoint}/${data.credentialId}`,
            type: 'put',
            beforeSend: function() {},
            data: $.param(data),
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            timeout: 10000,
            headers: {
                'X-CSRF-TOKEN': data._csrf
            }

        }).done(function (response) {
            $('#credentialModal').trigger("modal:close");
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
            url: `${endpoint}/${data.credentialId}`,
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

    function decrypt (data, callback) {
        const defer = $.Deferred();

        $.ajax({
            url: `${endpoint}/${data.credentialId}`,
            type: 'get',
            headers: {
                'X-CSRF-TOKEN': data._csrf,
            },
            timeout: 10000,
            crossDomain: true,

        }).done(function (response) {
            callback(response);

        }).fail(function (xhr) {
            callback("Error decrypting password.");

        });

        return defer.promise();
    }

    return {
        add : function (data) {
            if (data.hasOwnProperty("credentialId") && $.trim(data.credentialId) !== "")
                edit(data);
            else
                create(data);

        },

        remove : function(data) {
            remove(data)
        },

        decryptPassword : function(data, callback) {
            decrypt(data, callback);
        }

    }

}

function addCredentialModal() {
    $('#credential-id').val('');
    $('#credential-url').val('');
    $('#credential-username').val('');
    $('#credential-password').val('');

    $('#credentialModal')
        .modal('show');
}

function editCredentialModal(credentialId, url, username, password) {

    const data = {
        _csrf : $('input[name ="_csrf"]').val(),
        credentialId : credentialId
    }

    // Handle password decrypting ...
    const fragment = $('#credentials-form').attr('action');
    const credentials = Credentials(fragment);
    credentials.decryptPassword(data, function (decryptedPassword) {
        $('#credential-password').val(decryptedPassword ? decryptedPassword : '');
    });
    $('#credential-url').val(url ? url : '');
    $('#credential-username').val(username ? username : '');
    $('#credential-id').val(credentialId ? credentialId : '');

    $('#credentialModal')
        .modal('show');

}

function deleteCredential(credentialId) {
    const data = {
        _csrf : $('input[name ="_csrf"]').val(),
        credentialId : credentialId
    }

    const fragment = $('#credentials-form').attr('action');
    const credentials = Credentials(fragment);
    credentials.remove(data);
}

$('#credentialModal')
    .on("modal:close", function (event) {
        event.preventDefault();
        $(this).modal('toggle');
    })
    .on("credentials:add", function (event, data, fragment) {
        event.preventDefault();
        const credentials = Credentials(fragment);
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

    $('#credentialModal').trigger("credentials:add", [object, form.attr('action')]);
});
