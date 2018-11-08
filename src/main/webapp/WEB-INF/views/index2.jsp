<%@ page language="java" contentType="text/html" %>
<!DOCTYPE html>
<head>
    <title>Hello WebSocket</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.3.0/sockjs.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.js"></script>
    <script type="text/javascript">
        var stompClient = null;

        $(document).ready(function () {
           var socket = new SockJS('/gs-guide-websocket');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {
                // console.log('Connected: ' + frame);
                stompClient.subscribe('/topic/greetings', function (greeting) {
                    console.log("!!");
                    showGreeting(greeting.body);
                });
            });
        });

        function sendName() {
            stompClient.send("/app/hello", {}, JSON.stringify({'name': "111"}));
        }

        function showGreeting(message) {
            // console.log(message);
            $("#greetings").html("");
            $("#greetings").append("<tr><td>" + message + "</td></tr>");
        }

        setInterval(function () {
            sendName();
        }, 5000);

    </script>

</head>
<body>
<noscript><h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websocket relies on Javascript being
    enabled. Please enable
    Javascript and reload this page!</h2></noscript>
<div id="main-content" class="container">
    <div class="row">
        <div class="col-md-12">
            <table id="conversation" class="table table-striped">
                <thead>
                <tr>
                    <th>Greetings</th>
                </tr>
                </thead>
                <tbody id="greetings">
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>