package chess;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import chess.pieces.Piece;
import chess.web.SessionWrapper;

public class Game {

	public static final String KEY = "theGame";

	private static final Map<Long,Game> allGames = new TreeMap<>();

	private final Board theBoard = new Board();

	public final long created = System.currentTimeMillis();
	public long lastMoveTime = 0;

	private final String whiteUsername;
	private String blackUsername = "";

	private long whiteTime = 0,blackTime = 0;
	
	public long getWhiteTime() {
		long wt = whiteTime;
		if(Color.WHITE==getNextColor()) {
			wt += (System.currentTimeMillis()-lastMoveTime);
		}
		return wt;
	}

	public long getBlackTime() {
		long bt = blackTime;
		if(Color.BLACK==getNextColor()) {
			bt += (System.currentTimeMillis()-lastMoveTime);
		}
		return bt;
	}

	private boolean hasStarted = false;

	private String getUsername(Color color) {
		return color.isWhite() ? whiteUsername : blackUsername;
	}

	public void setBlackPlayer(String username) {
		blackUsername = username;
	}

	public boolean isReady() {
		return blackUsername.length()>0;
	}

	public Game(String whiteUsername) {
		this.whiteUsername = whiteUsername;
		allGames.put(created,this);
	}

	public void join(String blackUsername) {
		this.blackUsername = blackUsername;
	}

	public static void main(String[] args) {
		Board myBoard = new Board();
		myBoard.reset();
		System.out.println(myBoard);
	}

	public void move(String from,String to) {
		theBoard.move(Pos.get(from),Pos.get(to));
		final long now = System.currentTimeMillis();
		if (lastMoveTime!=0) {
			if (getNextColor().isWhite()) {
				blackTime += (now-lastMoveTime);
			} else {
				whiteTime += (now-lastMoveTime);
			}
		}
		lastMoveTime = now;
		hasStarted = true;
	}

	public String toHTML1(Color myColor) {
		String s = "<table border='1'>";
		for (byte row = 1;row<=8;row++) {
			s += "<tr height=30><th>"+(9-row)+"</th>";
			for (char col:Board.allCols) {
				Pos pos = Pos.get(col,(byte) (9-row));
				Piece thePiece = theBoard.get(pos);
				String jsMethod = "selectDest('"+pos+"')";
				if (thePiece==null) {
					s += "<td onclick=\""+jsMethod+"\">"+"</td>";
				} else {
					if (thePiece.color==getNextColor()&&getNextColor()==myColor) {
						jsMethod = "selectPiece(this,'"+pos+"')";
					}
					s += "<td onclick=\""+jsMethod+"\">"+thePiece.toHTML()+"</td>";
				}
			}
			s += "</tr>";
		}
		s += "<tr align=center><th></th><th>A</th><th>B</th><th>C</th><th>D</th><th>E</th><th>F</th><th>G</th><th>H</th></tr>";
		// s += "<tr><th></th><th colspan=8>Next Move: "+nextColor+"</th></tr>";
		s += "<tr><th></th><th colspan='4'>White</th><th colspan='4'>Black</th></tr>";
		if (Color.WHITE==getNextColor()&&hasStarted) {
			s += "<tr><th></th><th colspan='4'><span id='time'>"+getWhiteTime()/1000
					+"</span> s. </th><th colspan='4'>"+blackTime/1000+" s. </th></tr>";
		} else if (Color.BLACK==getNextColor()&&hasStarted) {
			s += "<tr><th></th><th colspan='4'>"+whiteTime/1000
					+" s. </th><th colspan='4'><span id='time'>"+getBlackTime()/1000+"</span> s. </th></tr>";
		}
		s += "<tr><th></th><th colspan='4'>"+theBoard.whiteScore+"p </th><th colspan='4'>"
				+theBoard.blackScore+"p </th></tr>";
		s += "<tr><th></th><th colspan=8>Possible: "+theBoard.getAvailableMoves()+"</th></tr>";
		s += "<tr><th></th><th colspan=8>Log: "+theBoard.movesDone.values()+"</th></tr>";
		s += "</table>";
		return s;
	}

	public String toHTML(HttpSession session) {
		final Color myColor = SessionWrapper.getMyColor(session);
		String html = "";
		final boolean youWait = myColor!=theBoard.getNextColor()||!isReady();
		if (!isReady()) {
			html += "Waiting for challenger..";
		} else {
			html += "vs. "+getUsername(myColor.inverse())+"<br>";
			html += youWait ? "WAITING" : "YOUR TURN";
		}
		html += "<br>"+toHTML1(myColor)+"<br>";
		if (youWait) {
			String fun = "reloadIfOpponentMoved(\""+getNextColor()+"\")";
			html += "<script>setInterval('"+fun+"',1000)</script>";
		}
		return html;
	}

	@Override
	public String toString() {
		return "white: "+whiteUsername+" black:"+blackUsername;
	}

	public static Game get(long gameCreated) {
		return allGames.get(gameCreated);
	}

	/**
	 * @return
	 */
	public Color getNextColor() {
		return isReady() ? theBoard.getNextColor() : null;
	}

}
