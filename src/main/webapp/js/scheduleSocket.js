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
        var id = $(this).attr("id");
        if (result.idSchedule == id) {
            $("#id")
                .remove()
                .end();
        }
        $('#table').append('<tr class="change"/>');
        $('#table').find('tr:last').append('<td>' + result.train + '</td>' +
            '<td>' + result.firstStation + '</td>' +
            '<td>' + result.lastStation + '</td>' +
            '<td>' + result.departureTime + '</td>');
    }
}

$(document).ready(function () {
    getConnection();
});