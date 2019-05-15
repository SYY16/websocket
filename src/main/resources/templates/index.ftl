<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8">
    <title>登录</title>
    <script src="jquery-3.1.1.js" type="text/javascript" ></script>
</head>
<body>
<#if (users)??>
在线用户:
    <#list users  as item>
    <input type="button" value='${item}'  onclick="addName('${item}')">&nbsp;&nbsp;&nbsp;
    </#list>
<br><br><br>
选中的：<div  id='toName'></div>
<br><br><br>
</#if>

输入您的昵称<input type="text" id="username">
<input type="button" value="连接" onclick="connect()"><br>
填写要发送的内容<input type="text" id="writeMsg"><br>
<input type="button" value="发送" onclick="sendMsg()"><br><br><br><br><br><br><br><br>
接受的消息<div id="servermsg"></div>

<script type="text/javascript">
    var ws = null;
    function connect(){
        if($("#username").val()!=null&&$("#username").val()!=""){
            if ('WebSocket' in window){
                ws = new WebSocket("ws://localhost:8080/socketServer/"+$("#username").val());//对应后端地址
            }
            else if ('MozWebSocket' in window){
                ws = new MozWebSocket("ws://localhost:8080/socketServer/"+$("#username").val());
            }
            else{
                alert("该浏览器不支持websocket");
            }


            ws.onmessage = function(evt) {//接受消息
                var value=$("#servermsg").html();
                if(""==value){
                    $("#servermsg").html(evt.data);
                }else{
                    $("#servermsg").html($("#servermsg").html()+','+evt.data);
                }
            };

            ws.onclose = function(evt) {//断开链接时
                alert("连接中断");
            };

            ws.onopen = function(evt) {//链接时
                alert("连接成功。。。");
            };
        }else{
            alert("请输入您的昵称");
        }
    }

    function sendMsg() {
        var message=$("#writeMsg").val();
        var names=$("#toName").html();
        if(""==names){
           alert("接受者为空");
           return;
        }
        var msg=names+"-"+message;
        ws.send(msg);//发送消息到后端服务器
    }

    function addName(name) {
        var value=$("#toName").html();
        if(""==value){
            $("#toName").html(name);
        }else{
            $("#toName").html($("#toName").html()+','+name);
        }

    }
</script>
</body>
</html>