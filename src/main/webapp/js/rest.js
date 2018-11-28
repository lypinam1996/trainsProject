var RestGet = function () {
    $.ajax({
        type: 'GET',
        url: '/getTrackIndicator',
        dataType: 'json',
        async: true,
        success: function (result) {
            if (result.length!=0) {
                $("#table")
                    .find('.change')
                    .remove()
                    .end();
                $(result).each(function (i, row) {
                    $('#table').append('<tr class="change"/>');
                    $(row).each(function (j, col) {
                        $('#table').find('tr:last').append(
                            '<td id="'+col.idSchedule+'">' + col.train + '</td>' +
                            '<td>' + col.firstStation + '</td>' +
                            '<td>' + col.lastStation + '</td>' +
                            '<td>' + col.departureTime + '</td>');
                    });
                });
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.status + ' ' + jqXHR.responseText);
        }
    });
}
setInterval(function () {
    RestGet();
}, 3000);

$( document ).ready(function() {
    RestGet();
});