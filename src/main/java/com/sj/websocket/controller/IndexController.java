package com.sj.websocket.controller;

import com.sj.websocket.service.SocketServer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
	@RequestMapping("index")
	public String sockettest(Model model){
		model.addAttribute("users", SocketServer.getOnlineName());
		return "index";
	}
	
	@RequestMapping("admin")
	public String tongji(Model model){
		model.addAttribute("num", SocketServer.getOnlineNum());
		model.addAttribute("users", SocketServer.getOnlineUsers());
		return "admin";
	}
	
	@RequestMapping("sendmsg")
	@ResponseBody
	public String sendmsg(HttpServletRequest request){
		String username = request.getParameter("username");
		String msg = request.getParameter("msg");
		SocketServer.sendMessage(msg, username);
		return "success";
	}
	
	@RequestMapping("sendAll")
	@ResponseBody
	public String sendAll(HttpServletRequest request){
		String msg = request.getParameter("msg");
		SocketServer.sendAll(msg);
		return "success";
	}
}
