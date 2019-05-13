package com.sj.websocket.Schedu;

import com.sj.websocket.service.SocketServer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Task {
    @Scheduled(cron="0 0/1 * * * ?")
    public void sendtoClient() {
        SocketServer.sendAll(System.currentTimeMillis()+"定时发送消息");
    }
}
