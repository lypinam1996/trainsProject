$(function () {
    $.ajax({
        type: 'GET',
        url: '/restStations',
        dataType: 'json',
        async: true,
        success: function (result) {
            if (result.length != 0) {
                $('#lastStation').autocomplete({
                    source: result
                })
                $('#firstStation').autocomplete({
                    source: result
                })
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
        }
    });
});