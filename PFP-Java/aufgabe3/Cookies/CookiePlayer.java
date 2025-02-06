public class CookiePlayer {

	private String name;
	private CookieGame game;

	public CookiePlayer(String name, CookieGame game) {
		this.game = game;
		this.name = name;
	}

	public void grabCookie() {
		game.takeCookie(name);
		try {
			Thread.sleep((long) (1000 * Math.random()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

}
