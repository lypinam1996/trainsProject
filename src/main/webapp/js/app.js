var stompClient = null;

$(document).ready(function () {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
});

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': "111"}));
}

function showGreeting(message) {
    $("#greetings").html("");
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

setInterval(function () {
    sendName();
}, 5000);

