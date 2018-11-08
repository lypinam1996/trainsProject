<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<body>
<h2>The XMLHttpRequest Object</h2>
<form action="">
    <select name="customers" onchange="showCustomer(this.value)">
        <option value="">Select a ticket:</option>
        <option value="1">1</option>
        <option value="2 ">2</option>
        <option value="3">3</option>
    </select>
</form>
<br>
<div id="txtHint">Customer info will be listed here...</div>

<script>
    function showCustomer(str) {
        var xhttp;
        if (str == "") {
            document.getElementById("txtHint").innerHTML = "";
            return;
        }
        xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                document.getElementById("txtHint").innerHTML = this.responseText;
            }
        };
        xhttp.open("GET", "/seeTicket/"+str, true);
        xhttp.send();
    }
</script>

</body>
</html>

