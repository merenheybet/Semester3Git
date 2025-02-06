import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GrabTheCookies{
	static class PlayerThread implements Runnable{
		private final CookiePlayer playerOfThread;
		private final CookieGame game;

		// The PlayerThread also require the game, so that it can know when the game is finished
		public PlayerThread(CookieGame game, CookiePlayer playerOfThread){
			this.playerOfThread = playerOfThread;
			this.game = game;
		}

		@Override
		// GrabCookies until the game is finished
		public void run() {
			while(!game.finished()){
				playerOfThread.grabCookie();
			}
		}
	}

	public static void startGame(String[] players) {
		//Create the game instance and create a threadPool with number of players
		CookieGame game = new CookieGame(players);
		ExecutorService exec = Executors.newFixedThreadPool(players.length);

		// Run the threads
		for (CookiePlayer player : game.getPlayers()){
			exec.execute(new PlayerThread(game, player));
		}

		exec.shutdown();
		// Normally all the threads should end themselves but even if they don't
		// all of them will be forcefully closed after 2 minutes.
		// Why 2 minutes, einfach nach Gefuehl.
		try{
			exec.awaitTermination(2, TimeUnit.MINUTES);
		}
		catch (Exception e){/* InterruptedException caught */}
	}
	
	public static void main(String[] args) throws Exception {
		String[] names = { "Ernie", "Bert", "Kr\u00FCmelmonster", "Samson" };
		startGame(names);
	}
}
