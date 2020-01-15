var RestGet = function () {
    $.ajax({
        type: 'GET',
        url: '/getTrackIndicator',
        dataType: 'json',
        async: true,
        success: function (result) {
            if (result.length != 0) {
                sort(result);
                $("#table")
                    .find('.change')
                    .remove()
                    .end();
                $(result).each(function (i, row) {
                    $('#table').append('<tr class="change"/>');
                    $(row).each(function (j, col) {
                        var time = col.departureTime;
                        time = time.substring(0,5);
                        $('#table').find('tr:last').append(
                            '<td id="' + col.idSchedule + '">' + col.train + '</td>' +
                            '<td>' + col.firstStation + '</td>' +
                            '<td>' + col.lastStation + '</td>' +
                            '<td>' + time + '</td>');
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
}, 20000);

$(document).ready(function () {
    RestGet();
    getConnection();
});

var stompClient = null;

function getConnection() {
    var socket = new SockJS('websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/schedule', function (greeting) {
            showGreeting(greeting.body);
        });
    });
}

function showGreeting(message) {
    if (message.size != 0) {
        if($.isNumeric(message)){
            $('.change').each(function () {
                var id = $(this).find('td').attr('id');
                if (id == message) {
                    $(this).remove().end();
                }
            });
        }
        else {
            var result = JSON.parse(message);
            $('.change').each(function () {
                var id = $(this).find('td').attr('id');
                if (id == result.idSchedule) {
                    $(this).remove().end();
                }
            });
            var time = result.departureTime;
            time = time.substring(0,5);
            $('#table').append('<tr class="change"/>');
            $('#table').find('tr:last').append('<td id="' + result.idSchedule + '">' + result.train + '</td>' +
                '<td>' + result.firstStation + '</td>' +
                '<td>' + result.lastStation + '</td>' +
                '<td>' + time + '</td>');
            RestGet();
        }

    }
}

var sort = function (result) {
    for (i = result.length - 1; i > 0; i--) {
        for (j = 0; j < i; j++) {
            var date1 = new Date();
            date1.setHours(result[j].departureTime.substring(0, 2));
            date1.setMinutes(result[j].departureTime.substring(3, 5));
            var date2 = new Date();
            date2.setHours(result[j + 1].departureTime.substring(0, 2));
            date2.setMinutes(result[j + 1].departureTime.substring(3, 5));
            if (date1 > date2) {
                var tmp = result[j];
                result[j] = result[j + 1];
                result[j + 1] = tmp;
            }
        }
    }
}