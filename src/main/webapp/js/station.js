$(function () {
    $.ajax({
        type: 'GET',
        url: '/restStations',
        dataType: 'json',
        async: true,
        success: function (result) {
            console.log("!");
            if (result.length != 0) {
                $('#stationName').autocomplete({
                    source: result
                })
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
        }
    });
});