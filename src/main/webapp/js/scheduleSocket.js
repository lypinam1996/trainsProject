var stompClient = null;

function getConnection() {
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/socket/schedule', function (greeting) {
            showGreeting(greeting.body);
        });
    });
}

function showGreeting(message) {
    if (message.size != 0) {
        var result = JSON.parse(message);
        $("#table")
            .find('.change')
            .remove()
            .end();
        $(result).each(function (i, row) {
            $('#table').append('<tr class="change"/>');
            $(row).each(function (j, col) {
                $('#table').find('tr:last').append('<td>' + col.train + '</td>' +
                    '<td>' + col.firstStation + '</td>' +
                    '<td>' + col.lastStation + '</td>' +
                    '<td>' + col.departureTime + '</td>');
            });
        });
    }
}

$(document).ready(function () {
    getConnection();
});
