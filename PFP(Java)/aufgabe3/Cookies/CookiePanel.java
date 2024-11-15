import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayDeque;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class CookiePanel extends JPanel {

	private final static long serialVersionUID = 1L;
	private ArrayDeque<JLabel> myCookies = new ArrayDeque<>();
	private String name = "";
	final static byte[] cookiePic = { -119, 80, 78, 71, 13, 10, 26, 10, 0, 0,
			0, 13, 73, 72, 68, 82, 0, 0, 0, 37, 0, 0, 0, 26, 8, 6, 0, 0, 0, 48,
			-78, -107, 123, 0, 0, 0, -52, 73, 68, 65, 84, 120, -38, -27, -41,
			-63, 13, -126, 64, 16, 5, -48, -23, 98, 75, -16, 72, 5, -74, -32,
			-115, -93, 87, 26, -16, 100, 98, 9, 118, 96, 7, -36, -87, -64, 18,
			40, 104, -56, 74, -26, -14, -109, -17, -57, 4, 13, -125, -121, 127,
			96, -61, -18, -68, -28, 39, 27, 48, 119, -73, -83, -59, 118, -125,
			122, 94, -52, -33, -91, 111, -25, -100, -101, 57, -1, -125, 82, 67,
			21, 18, -77, 63, 20, 30, 54, -34, -53, 42, 81, -75, -26, 65, 49,
			68, 28, -122, -21, 88, 39, -82, 51, 28, -85, 51, 15, -118, 13, 81,
			117, -32, -5, 108, -1, -11, 104, -81, -28, 71, 97, 125, -8, -84,
			106, 89, -102, -4, -88, -31, 86, -68, -122, 93, 126, 75, 107, 101,
			53, -58, 57, 49, 39, 47, -22, -47, -103, -41, 32, -18, -45, 43,
			-127, -43, 21, -25, -58, -100, -68, -88, -45, -63, -68, 6, 113,
			-86, 86, -60, 35, 2, 49, 49, 39, 47, -118, -31, 24, 82, 5, -9, 51,
			76, 94, 20, -30, 24, 82, 5, -9, -81, -14, -27, -71, 73, -108, 66,
			-86, -4, -28, 111, -26, -37, -88, 9, 64, 24, 83, -82, 58, -110,
			116, 83, 0, 0, 0, 0, 73, 69, 78, 68, -82, 66, 96, -126 };

	public CookiePanel(int nCookies, int width, int height, Color color) {
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setPreferredSize(new Dimension(width, height));
		if (color != null)
			setBackground(color);
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		for (int i = 0; i < nCookies; i++) {
			JLabel c = createCookie();
			add(c);
			myCookies.add(c);
		}
		this.validate();
	}

	public int numCookies() {
		return myCookies.size();
	}

	public void setName(String name) {
		this.name = name;
	}

	public JLabel removeCookie() {
		assert !myCookies.isEmpty();

		JLabel c = myCookies.removeLast();
		this.remove(c);
		scheduleRepaint();
		return c;
	}

	public void addCookie(JLabel c) {
		myCookies.addLast(c);
		add(c);
		scheduleRepaint();
	}

	@Override
	public void paint(java.awt.Graphics g) {
		super.paint(g);
		Font f = getFont().deriveFont(Font.BOLD, 36);
		g.setFont(f);
		int width = g.getFontMetrics().stringWidth(name);
		g.drawString(name, getWidth() - width - 5, getHeight() - 10);
	}

	public static JLabel createCookie() {
		return new JLabel(new ImageIcon(cookiePic));
	}

	private void scheduleRepaint() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				CookiePanel.this.revalidate();
				CookiePanel.this.repaint();
			}
		});
	}

}
