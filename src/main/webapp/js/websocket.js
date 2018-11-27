var stompClient = null;

function getConnection() {
    var socket = new SockJS('/websocket3');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/greetings', function (greeting) {
            var username = frame.headers['user-name'];
            showGreeting(greeting.body, username);
        });
    });
}

function showGreeting(message, username) {
    var a;
    if (message.size != 0) {
        var myArr = JSON.parse(message);
        $(myArr).each(function (i, row) {
            if (row.name == username) {
               a=1;
            }
        });
    }
    if(a==1){
        $(".alert").toggleClass('in out');
    }
}

$(document).ready(function () {
    $.ajax("/session/current").then(function (json) {
        if (json.role == 'USER') {
            getConnection();
        }
    })
});
