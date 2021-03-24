/**
 * Property of Enigio Time AB.
 * All rights reserved.
 *
 * $Id$
 */

package chess.web;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionListener implements HttpSessionListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(SessionListener.class);

	private static final Map<String,HttpSession> _allSessions = new TreeMap<>();
	public static final Map<String,HttpSession> allSessions = Collections.unmodifiableMap(_allSessions);

	@Override
	public void sessionCreated(HttpSessionEvent sessionEvent) {
		_allSessions.put(sessionEvent.getSession().getId(),sessionEvent.getSession());
		LOGGER.info("Session count:{}",_allSessions.size());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		_allSessions.remove(sessionEvent.getSession().getId());
		LOGGER.info("Session count:{}",_allSessions.size());
	}

	public static boolean existsUsername(String userName) {
		return allSessions.values().stream()
											.anyMatch(s -> userName.equalsIgnoreCase(SessionWrapper.getUsername(s)));
	}

}
