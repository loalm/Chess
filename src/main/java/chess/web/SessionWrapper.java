/**
 * Property of Enigio Time AB.
 * All rights reserved.
 *
 * $Id$
 */

package chess.web;

import javax.servlet.http.HttpSession;

import chess.Color;
import chess.Game;

public final class SessionWrapper {

	enum Keys {
		USERNAME, GAME, MY_COLOR;
	}

	public static final int MIN_LEN_USERNAME = 3;

	private SessionWrapper() {
	}

	public static void joinGame(HttpSession session,long gameCreated) {
		Game joinedGame = Game.get(gameCreated);
		joinedGame.setBlackPlayer(getUsername(session));
		setGame(session,joinedGame);
		session.setAttribute(Keys.MY_COLOR.name(),Color.BLACK);
	}

	public static Game getGame(HttpSession session) {
		return (Game) session.getAttribute(Keys.GAME.name());
	}

	public static void createGame(HttpSession session) {
		Game newGame = new Game(SessionWrapper.getUsername(session));
		setGame(session,newGame);
		session.setAttribute(Keys.MY_COLOR.name(),Color.WHITE);
	}

	public static Color getMyColor(HttpSession session) {
		return (Color) session.getAttribute(Keys.MY_COLOR.name());
	}

	private static void setGame(HttpSession session,Game game) {
		System.out.println("setGame("+game);
		if (getGame(session)!=null) {
			throw new IllegalStateException();
		}
		session.setAttribute(Keys.GAME.name(),game);
	}

	public static String getUsername(HttpSession session) {
		return (String) session.getAttribute(Keys.USERNAME.name());
	}

	public static void setUsername(HttpSession session,String userName) {
		if (getUsername(session)!=null) {
			throw new IllegalStateException("We already have a username for you:'"+getUsername(session)
					+"'.");
		}
		if (SessionListener.existsUsername(userName)) {
			throw new IllegalArgumentException("Sorry, user "+userName+" already exists.");
		}
		if (userName==null||userName.length()<MIN_LEN_USERNAME) {
			throw new IllegalArgumentException("Sorry, user name must be at least "+MIN_LEN_USERNAME
					+" characters.");
		}
		session.setAttribute(Keys.USERNAME.name(),userName);
	}

	public static Color tellNextColor(HttpSession session) {
		Game game = getGame(session);
		return game==null ? null : game.getNextColor();
	}

}
