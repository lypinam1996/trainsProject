var stompClient = null;

function getConnection() {
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(greeting.body);
        });
    });
}

function showGreeting(message) {
    if (message != "") {
        $(".alert").toggleClass('in out');
    }
}

$(document).ready(function() {
    $.ajax("/session/current").then(function (json) {
        if(json.role=='USER'){
            getConnection();
        }
    })
});
