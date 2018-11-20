var stompClient = null;

$(document).ready(function () {
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(greeting.body);
        });
    });

});

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': ""}));
}

function showGreeting(message) {
    if (message != "") {
        $(".alert").toggleClass('in out');
    }
}

setInterval(function () {
    sendName();
}, 3600000);

setTimeout(function () {
    sendName();
}, 1000);
