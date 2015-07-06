package com.mhdanh.mytemplate.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.mhdanh.mytemplate.dao.AccountDao;
import com.mhdanh.mytemplate.dao.implement.AccountDaoImpl;
import com.mhdanh.mytemplate.domain.Account;


@ServerEndpoint("/ws/account")
public class AccountWebSocket {
	
	private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());
	
	@Named
	private AccountDao accountDao;

	// handle all message from client
	@OnMessage
	public void handleOnMsg(String msg, Session session) throws IOException, EncodeException {
		synchronized (clients) {
			for (Session client : clients) {
				System.out.println("accountDAO" + accountDao);
				List<Account> accounts = accountDao.getAll();
				Gson gson = new Gson();
				String strAccounts = gson.toJson(accounts);
				client.getBasicRemote().sendText(strAccounts);
				//client.getBasicRemote().sendObject(accounts.get(0));
			}
		}
	}

	// handle new connect from client
	@OnOpen
	public void handleOnOpen(Session session) {
		System.out.println("welcome e: " + session.getId());
		clients.add(session);
	}

	// handle client disconnect to server
	@OnClose
	public void handleOnClose(Session session) {
		clients.remove(session);
	}
}