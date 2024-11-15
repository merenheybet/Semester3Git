import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class CookieGame {

	private HashMap<String, CookiePanel> player2Panel = new HashMap<>();
	private ArrayList<CookiePlayer> players = new ArrayList<>();
	private JFrame frmGrabTheCookies;
	private int numPlayers;
	private int totalCookies = 88;

	private CookiePanel cookies = new CookiePanel(88, 310, 300, null);
	private CookiePanel[] playerPanels = {
			new CookiePanel(0, 900, 150, Color.BLUE),
			new CookiePanel(0, 300, 300, Color.GREEN),
			new CookiePanel(0, 300, 300, Color.RED),
			new CookiePanel(0, 900, 150, Color.YELLOW) };

	public CookieGame(String[] names) {
		if (names.length < 1 || names.length > 4) {
			System.err.println("We need 1-4 players!");
			System.exit(1);
		}

		int i = 0;
		for (String name : names) {
			CookiePlayer p = new CookiePlayer(name, this);
			players.add(p);
			player2Panel.put(name, playerPanels[i]);
			playerPanels[i].setName(name);
			i++;
		}

		numPlayers = names.length;

		initialize();
	}

	public synchronized boolean finished() {
		return cookies.numCookies() == 0;
	}

	public synchronized void takeCookie(String player) {
		if (finished())
			return;
		JLabel c = cookies.removeCookie();
		player2Panel.get(player).addCookie(c);

		// check for a winner
		if (finished()) {
			int max = -1;
			String winner = null;
			for (CookiePlayer p : players) {
				int cookie = player2Panel.get(p.getName()).numCookies();
				if (cookie == max) {
					System.err.println("Draw! Bake some more cookies!");
					for (int i = 0; i < 20; i++) {
						cookies.addCookie(CookiePanel.createCookie());
						totalCookies++;
					}
					return;
				}
				if (cookie > max) {
					max = cookie;
					winner = p.getName();
				}
			}

			System.out.println("The winner is: " + winner);
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					frmGrabTheCookies.dispose();
				}
			});

			// Sanity check:
			int cookieSum = 0;
			for (CookiePlayer p : players) {
				cookieSum += player2Panel.get(p.getName()).numCookies();
			}
			if (cookieSum != totalCookies) {
				System.err.format("OH NOES: %d of %d cookies were eaten!%n",
						cookieSum, totalCookies);
			}
		}
	}

	public Collection<CookiePlayer> getPlayers() {
		return players;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Show GUI
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					frmGrabTheCookies = new JFrame();
					frmGrabTheCookies.setResizable(false);
					frmGrabTheCookies.setTitle("Grab the Cookies");
					frmGrabTheCookies
							.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frmGrabTheCookies.getContentPane().setLayout(
							new BorderLayout(10, 10));

					frmGrabTheCookies.getContentPane().add(cookies,
							BorderLayout.CENTER);

					frmGrabTheCookies.getContentPane().add(playerPanels[0],
							BorderLayout.NORTH);

					if (numPlayers > 1)
						frmGrabTheCookies.getContentPane().add(playerPanels[1],
								BorderLayout.WEST);

					if (numPlayers > 2)
						frmGrabTheCookies.getContentPane().add(playerPanels[2],
								BorderLayout.EAST);

					if (numPlayers > 3)
						frmGrabTheCookies.getContentPane().add(playerPanels[3],
								BorderLayout.SOUTH);

					frmGrabTheCookies.pack();
					frmGrabTheCookies.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
