<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String socketPath = request.getServerName()+":"+request.getServerPort() + path + "/";
%>
<html>
<head>
    <title>Title</title>
</head>

<body>
<input type="button" value="链接服务器" onclick="link()">
<input type="button" value="断开服务器" onclick="close()">
</body>
<script type="text/javascript">
    var webSocket;
    function link() {
        webSocket = new WebSocket("ws://localhost:8080//ws");
        webSocket.onopen = function(event){
            alert("OK")
            console.log("连接成功");
            console.log(event);
        };
        webSocket.onerror = function(event){
            alert("ERROR")
            console.log("连接失败");
            console.log(event);
        };
        webSocket.onclose = function(event){
            console.log("Socket连接断开");
            console.log(event);
        };
        webSocket.onmessage = function(event){
            console.log(event.data);
        }
    }
    function close() {
            webSocket.onclose = function(event){
            console.log("Socket连接断开");
            console.log(event);
        };
    }
</script>
</html>
