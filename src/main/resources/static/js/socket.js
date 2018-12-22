var websocket;

function connection() {
    websocket = new WebSocket("ws:" + location.host + "/api/socket")
    websocket.onerror = function () {
        setMessageInnerHTML("WebSocket连接发生错误");
    };

    //连接成功建立的回调方法
    websocket.onopen = function () {
        setMessageInnerHTML("WebSocket连接成功");
        document.getElementById("connect").disabled = true;
    }

    //接收到消息的回调方法
    websocket.onmessage = function (event) {
        setMessageInnerHTML(event.data);
    }

    //连接关闭的回调方法
    websocket.onclose = function () {
        setMessageInnerHTML("WebSocket连接关闭");
        document.getElementById("connect").disabled = false;
    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        closeWebSocket();
    }
}

//将消息显示在网页上
function setMessageInnerHTML(innerHTML) {
    var message = document.createElement("div");
    message.setAttribute("class", "message")
    message.innerText = innerHTML;
    document.getElementById('message').appendChild(message);
    setTimeout(function () {
        document.getElementById('message').removeChild(message);
    }, 10000)
}

//关闭WebSocket连接
function closeWebSocket() {
    websocket.close();
}

//发送消息
function send() {
    var message = document.getElementById('text').value;
    websocket.send(message);
}