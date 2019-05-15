package com.sj.websocket.service;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ServerEndpoint(value = "/socketServer/{userid}")
@Component
public class SocketServer {

	private Session session;
	private static Map<String,Session> sessionPool = new HashMap<>();
	private static Map<String,String> sessionIds = new HashMap<>();

	/**
	 * 客户初次链接时会给客户端创建一个session，这个session不是我们平常用的httpsession
	 * @param session
	 * @param userid
	 */
	@OnOpen
	public void open(Session session, @PathParam(value="userid")String userid){
		this.session = session;
		sessionPool.put(userid, session);
		sessionIds.put(session.getId(), userid);
	}

	/**
	 * 客户端发送消息时触发
	 * @param message
	 */
	@OnMessage
	public void onMessage(String message){
		System.out.println("当前发送人sessionid为"+session.getId()+"发送内容为"+message);
		String[] msg = message.split("-");
		String[] list=msg[0].split(",");
		String name=sessionIds.get(this.session.getId());
		message=msg[1];
		for(int i=0;i<list.length;i++){
			String key = list[i];
			if(key.equals(name)){
				System.out.println("本人");
			}else{
				sendMessage(name+"发送："+message, key);
			}
		}


	}

	/**
	 * 客户端端口链接时触发
	 */
	@OnClose
	public void onClose(){
		sessionPool.remove(sessionIds.get(session.getId()));
		sessionIds.remove(session.getId());
	}

	/**
	 * 发生异常错误时触发
	 * @param session
	 * @param error
	 */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

	/**
	 * 发送消息到客户端
	 * @param message
	 * @param userId
	 */
	public static void sendMessage(String message,String userId){
		Session s = sessionPool.get(userId);
		if(s!=null){
			try {
				s.getBasicRemote().sendText(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 在线数量
	 * @return
	 */
	public static int getOnlineNum(){
		return sessionPool.size();
	}

	/**
	 * 在线用户名称 所有人
	 * @return
	 */
	public static String getOnlineUsers(){
		StringBuffer users = new StringBuffer();
	    for (String key : sessionIds.keySet()) {
		   users.append(sessionIds.get(key)+",");
		}
	    return users.toString();
	}
	/**
	 * 在线用户名称 所有人 返回集合
	 * @return
	 */
	public static List<String> getOnlineName(){
		List<String> list = new ArrayList<>();
		for (String key : sessionIds.keySet()) {
			list.add(sessionIds.get(key));
		}
		return list;
	}




	/**
	 * 给所有用户发送
	 * @param msg
	 */
	public static void sendAll(String msg) {
		System.out.println(new Date() + "               "+msg);
		for (String key : sessionIds.keySet()) {
			sendMessage(msg, sessionIds.get(key));
	    }
	}



}
