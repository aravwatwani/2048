import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class BoardGUI extends JPanel implements KeyListener, ActionListener {
	protected Tile[][] b;
	protected Board data;
	protected Color[] colors;
	protected int speed = 60;
	protected boolean draw;
	Timer t; // used for bot

	public BoardGUI() {
		draw = true;
		b = new Tile[4][4];
		colors = new Color[11];
		t = new Timer(1000 / speed, this);
		setup(new int[][] {});
	}

	public BoardGUI(boolean draw) {
		this.draw = draw;
		b = new Tile[4][4];
		colors = new Color[11];
		t = new Timer(1000 / speed, this);
		setup(new int[][] {});
	}

	public BoardGUI(int[][] d) {
		draw = true;
		b = new Tile[4][4];
		colors = new Color[11];
		t = new Timer(1000 / speed, this);
		setup(d);
	}

	public void start() {
		t.start();
	}

	/*
	 * public void prepopulate(int[][] d) { data.populate(d); update(); }
	 */

	public void setup(int[][] d) {
		data = new Board();
		data.populate(d);
		if (!draw)
			return;
		JFrame frame = new JFrame("2048");
		frame.setSize(400, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(this);

		colors[0] = new Color(0xc7b9ab);
		colors[1] = new Color(0xeaded5);
		colors[2] = new Color(0xe9dbc0);
		colors[3] = new Color(0xefa76b);
		colors[4] = new Color(0xf59563);
		colors[5] = new Color(0xf67c5f);
		colors[6] = new Color(0xf65e3b);
		colors[7] = new Color(0xedcf72);
		colors[8] = new Color(0xedcc61);
		colors[9] = new Color(0xedcc61);
		colors[10] = new Color(0xf3c92f);

		Font bigFont = new Font("Helvetica Neue", Font.ITALIC, 55);
		GridLayout g = new GridLayout(4, 4);
		frame.setLayout(g);

		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b[0].length; j++) {
				b[i][j] = new Tile();
				b[i][j].setSize(100, 100); // Tile.setHorizontalAlignment(JTextField.CENTER);
				b[i][j].setFont(bigFont);
				b[i][j].setHorizontalAlignment(JTextField.CENTER);
				b[i][j].setBackground(colors[b[i][j].cindex]);
				frame.add(b[i][j]);
			}
		}

		update();
		frame.setVisible(true);

	}

	public void update() {
		if (!draw)
			return;
		for (int r = 0; r < 4; r++) {
			for (int c = 0; c < 4; c++) {
				b[r][c].setValue(data.getBoard()[r][c]);
				b[r][c].setBackground(colors[b[r][c].cindex]);
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		/* call the helper methods for the Board object data */

		switch (arg0.getKeyCode()) {
			case 39:
				data.right();
				break;
			case 37:
				data.left();
				break;
			case 38:
				data.up();
				break;
			case 40:
				data.down();
				break;
		}

		data.populateOne();
		update();
		/** reset the game if all tiles are populated **/
		if (data.gameOver()) {
			data = new Board();
			update();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		// bot algorithm

		// every move call populate and update
		// data.populateOne();
		// update();

	}

}
