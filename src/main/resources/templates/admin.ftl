<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>登录</title>
    <script src="jquery-3.1.1.js" type="text/javascript" ></script>
</head>
<body>
<body>
当前在线人数总计:<div id="sum">${num}</div> <br>
具体人员为：<div id="users">${users}</div> <br>
发送消息：请输入推送内容<input type="text" id="msg">  <br>
请输入发送人昵称<input type="text" id="username">  <br>
<input type="button" value="发送" onclick="sendMsg()"></input>        <br>
<input type="button" value="全部发送" onclick="sendAll()"></input>
<script type="text/javascript">
    function sendMsg(){
        var user = $("#username").val();
        var msg = $("#msg").val();
        if(msg!=null&&msg!=""&&user!=null&&user!=""){
            $.getJSON( '/sendmsg?username='+user+'&msg='+msg, function(data) {
                alert(data);
            });
        }else{
            alert("请填写要发送的用户昵称或者发送内容");
        }
    }

    function sendAll(){
        var msg = $("#msg").val();
        if(msg!=null&&msg!=""){
            $.getJSON( '/sendAll?msg='+msg, function(data) {
                alert(data);
            });
        }else{
            alert("请填写要发送的内容");
        }
    }
</script>
</body>
</html>