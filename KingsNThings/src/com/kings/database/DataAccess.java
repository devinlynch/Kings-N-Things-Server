package com.kings.database;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.proxy.HibernateProxy;

import com.kings.model.Game;
import com.kings.model.GameChatMessage;
import com.kings.model.SentMessage;
import com.kings.model.User;

public class DataAccess {
	private static SessionFactory sessionFactory;
	private static Configuration config;
	
	@SuppressWarnings("deprecation")
	public static void configure() {
		if(sessionFactory== null) {
			if(config == null) {
				config = new Configuration().configure(); 
				addClassMappings();
			}
			
			sessionFactory = config.buildSessionFactory();
		}
	}
	
	public Session getSession() {
		configure();
		return sessionFactory.getCurrentSession();
	}
	
	public static void addClassMappings() {
		config.addClass(SentMessage.class);
		config.addClass(User.class);
		config.addClass(Game.class);
		config.addClass(GameChatMessage.class);
	}
	
	public void beginTransaction() {
		getSession().beginTransaction();
	}
	
	public void commit() {
		getSession().getTransaction().commit();
	}
	
	public boolean isTransactionActive() {
		return getSession().getTransaction() != null && getSession().getTransaction().isActive();
	}
	
	public void rollback() {
		getSession().getTransaction().rollback();
	}
	
	public void save(Object o) {
		getSession().save(o);
	}
	
	public static void main(String[] args) {
		DataAccess access = new DataAccess();
		access.beginTransaction();
		
		access.commit();
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T initializeAndUnproxy(T entity) {
	    if (entity == null) {
	        throw new 
	           NullPointerException("Entity passed for initialization is null");
	    }

	    Hibernate.initialize(entity);
	    if (entity instanceof HibernateProxy) {
	        entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer()
	                .getImplementation();
	    }
	    return entity;
	}
	
	@SuppressWarnings("unchecked")
	public <T>T get(Class<T> type, String id) {
		return (T) getSession().get(type, id);
	}
	
	public User getUserByUsername(String username) {
		return (User) getSession()
			.createQuery("from User where username = :un")
			.setParameter("un", username)
			.uniqueResult();
	}
	
	public Game getGameByGameLobbyId(String gameLobbyId) {
		return (Game) getSession()
				.createQuery("from Game where createdFromGameLobbyId = :un")
				.setParameter("un", gameLobbyId)
				.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<GameChatMessage> getChatMessagesAfterId(Game game, String lastId) {
		return (List<GameChatMessage>) getSession()
				.createQuery("from GameChatMessage where gameChatMessageId > :lid and game = :game")
				.setParameter("lid", lastId)
				.setParameter("game", game)
				.list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<SentMessage> getQueuedMessagesForUser(String userId) {
		return (List<SentMessage>) getSession()
				.createQuery("from SentMessage where sentToUser.userId = :user and queued = 1 order by sent_date")
				.setParameter("user", userId)
				.list();
	}
	
	public SentMessage getQueuedMessageForUser(String userId, String messageId) {
		return (SentMessage) getSession()
				.createQuery("from SentMessage where sentToUser.userId = :user and messageId = :mid and queued = 1")
				.setParameter("user", userId)
				.setParameter("mid", messageId)
				.uniqueResult();
	}
	
}
