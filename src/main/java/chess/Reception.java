package chess;

import java.util.List;
import java.util.stream.Collectors;

import chess.web.SessionListener;
import chess.web.SessionWrapper;

public final class Reception {

	public static List<Game> getGames() {
		return SessionListener.allSessions.values().stream().map(s -> SessionWrapper.getGame(s))
																			.filter(p -> p!=null).collect(Collectors.toList());
	}
}
