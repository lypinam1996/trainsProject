var stompClient = null;

function getConnection() {
    var socket = new SockJS('/websocket2');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/ticketDelete', function (greeting) {
            var username = frame.headers['user-name'];
            showGreeting(greeting.body, username);
        });
    });
}

function showGreeting(message, username) {
    var count = 0;
    if (message.size != 0) {
        var myArr = JSON.parse(message);
        $(myArr).each(function (i, row) {
            if (row.login == username) {
                count++;
                $("#table")
                    .find('.change')
                    .remove()
                    .end();
                $(myArr).each(function (i, row) {
                    $('#table').append('<tr class="change"/>');
                    $(row).each(function (j, col) {
                        $('#table').find('tr:last').append('<td>' + col.name + '</td>' +
                            '<td>' + col.surname + '</td>' +
                            '<td>' + col.patronymic + '</td>' +
                            '<td>' + col.dateOfBirth + '</td>' +
                            '<td>' + col.train + '</td>' +
                            '<td>' + col.firstStation + '</td>' +
                            '<td>' + col.lastStation + '</td>' +
                            '<td>' + col.departureDate + '</td>' +
                            '<td>' + col.departureTime + '</td>' +
                            '<td>' + col.arrivalTime + '</td>' +
                            '<td>' + col.journeyTime + '</td>' +
                            '<td>' + col.seat + '</td>' +
                            '<c:choose><c:when test="${role.title.equals(USER)}"><td><a href="/seeTicket/' + col.idTicket + '">Open </a></td></c:when></c:choose>'
                        );
                    });
                });
            }
        });
    }
    // if (count == 0) {
    //     $("#table")
    //         .find('.change')
    //         .remove()
    //         .end();
    // }
}

$(document).ready(function () {
    $.ajax("/session/current").then(function (json) {
        if (json.role == 'USER') {
            getConnection();
        }
    })
});