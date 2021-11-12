package com.jackchen;

import java.io.IOException;
import java.util.Vector;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * 
* @ClassName: OnlineServer 
* @Description: 直播的转发服务器，基于websocket协议
* @author 猿说教育-Jame
* @date 2017年10月12日 下午9:04:39 
*
 */
//指定一个URI客户端可以通过这个URI来连接到我们的webSocket 类似servletmapping
@ServerEndpoint("/onlineServer")
public class OnlineServer {
	//当一个客户端访问onlineServer连接的时候，创建一个OnlineServer的实例
	private Session session;//当前的会话对象 通过session 服务器可以向客户端主动发送消息
	//建立一个静态的集合来存储所有客户端的实例
	public static Vector<OnlineServer> clients = new Vector<OnlineServer>();
	/**
	 * 
	* @Title: onOpen 
	* @Description: 当客户端与服务器建立连接的时候触发方法
	* @param session    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@OnOpen
	public void onOpen(Session session){
		this.session = session;
		clients.add(this);//将当前客户端加入客户端列表
		System.out.println("新连接连接！！");
	}
	@OnClose
	public void onClose(){
		clients.remove(this);
		System.out.println("有一个连接！！");
	}
	//当客户端向服务器发送消息的时候触发的事件
	@OnMessage
	public void onMessage(String message,Session session){
		//群发消息 广播消息
		for(OnlineServer client : clients){
			try {
				//这是往客户端发送消息
				client.session.getBasicRemote().sendText(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
	}
	
}
