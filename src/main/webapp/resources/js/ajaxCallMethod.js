function ajaxGetCallApi(url, data) {
    return $.ajax({
        type: 'GET',
        url: url,
        success: function (vehicles) {
        },
        error: function () {
            alert('error!');
        }
    })

}

function ajaxPostCallApi(url, data) {
    return $.ajax({
        type: 'POST',
        url: url,
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function () {
        },
        error: function () {
            alert("error!");
        }
    });
}

function ajaxPutCallApi(url, data) {
    return $.ajax({
        type: 'PUT',
        url: url,
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function () {
        },
        error: function () {
            alert("error!");
        }
    });
}

function ajaxActivateCallApi(url, data) {
    return $.ajax({
        type: 'PUT',
        url: url,
        success: function () {
        },
        error: function () {
            alert("error!");
        }
    });
}

function ajaxDeleteCallApi(url, data) {
    return $.ajax({
        type: 'DELETE',
        url: url,
        success: function () {
        },
        error: function () {
            alert("error!");
        }
    });
}