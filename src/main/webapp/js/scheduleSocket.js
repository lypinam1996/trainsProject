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
        var result = JSON.parse(message);
        $('.change').each(function () {
            var id = $(this).find('td').attr('id');
            if (id == result.idSchedule) {
                $(this).remove().end();
            }
        });
        $('#table').append('<tr class="change"/>');
        $('#table').find('tr:last').append('<td id="' + result.idSchedule + '">' + result.train + '</td>' +
            '<td>' + result.firstStation + '</td>' +
            '<td>' + result.lastStation + '</td>' +
            '<td>' + result.departureTime + '</td>');
    }
}

$(document).ready(function () {
    getConnection();
});