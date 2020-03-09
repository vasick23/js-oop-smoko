import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameBoard extends JPanel implements ActionListener {
	private static final long serialVersionUID = 10;
	private final int boardWidth = 300;
    private final int boardHeight = 300;
    private final int dotSize = 10;
    private final int allDots = 900;
    private final int randomPosition = 29;
    private final int delay = 140;
    private final int x[] = new int[allDots];
    private final int y[] = new int[allDots];
    private int dots;
    private int food_x;
    private int food_y;
    private int enemy_x;
    private int enemy_y;
    private int movingFood_x;
    private int movingFood_y;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;
    private int points;
    private Timer timer;
    public GameBoard() {  
        initGameBoard();
    }
    private void initGameBoard() {
        addKeyListener(new KeyPressedListener());
        setBackground(Color.black);
        setFocusable(true);
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        initGame();
    }
    private void checkFood() {
        if ((x[0] == food_x) && (y[0] == food_y)) {
            dots++;
            points = points + 10;
            locateEnemy();
            locateFood();
        }
    }
    private void checkEnemy() {
        if ((x[0] == enemy_x) && (y[0] == enemy_y)) {
            dots++;
            inGame = false;
        }
    }
    private void checkMovingFood() {
        if ((x[0] == movingFood_x) && (y[0] == movingFood_y)) {
            dots = dots + 2;
            points = points + 15;
            locateMovingFood();
        }
    }
    private void locateFood() {
        int r = (int) (Math.random() * randomPosition);
        food_x = ((r * dotSize));
        r = (int) (Math.random() * randomPosition);
        food_y = ((r * dotSize));
    }
    private void locateMovingFood() {
        int a = (int) (Math.random() * randomPosition);
        movingFood_x = ((a * dotSize));
        a = (int) (Math.random() * randomPosition);
        movingFood_y = ((a * dotSize));
    }
    private void locateEnemy() {
        int x = (int) (Math.random() * randomPosition);
        enemy_x = ((x * dotSize));
        x = (int) (Math.random() * randomPosition);
        enemy_y = ((x * dotSize));
    }
	 private void moveSnake() {
	        for (int z = dots; z > 0; z--) {
	            x[z] = x[(z - 1)];
	            y[z] = y[(z - 1)];
	        }
	        if (left) {
	            x[0] -= dotSize;
	        }
	        if (right) {
	            x[0] += dotSize;
	        }
	        if (up) {
	            y[0] -= dotSize;
	        }
	        if (down) {
	            y[0] += dotSize;
	        }
	    }
	    private void collision() {
	        for (int z = dots; z > 0; z--) {
	            if ((z > 1) && (x[0] == x[z]) && (y[0] == y[z])) {
	                inGame = false;
	            }
	        }
	        if (y[0] >= boardHeight) {
	            inGame = false;
	        }
	        if (y[0] < 0) {
	            inGame = false;
	        }
	        if (x[0] >= boardWidth) {
	            inGame = false;
	        }
	        if (x[0] < 0) {
	            inGame = false;
	        }
	        if (!inGame) {
	            timer.stop();
	        }
	    }
	 private class KeyPressedListener extends KeyAdapter {
	        @Override
	        public void keyPressed(KeyEvent e) {
	            int key = e.getKeyCode();
	            if ((key == KeyEvent.VK_LEFT) && (!right)) {
	                left = true;
	                up = false;
	                down = false;
	            }
	            if ((key == KeyEvent.VK_RIGHT) && (!left)) {
	                right = true;
	                up = false;
	                down = false;
	            }
	            if ((key == KeyEvent.VK_UP) && (!down)) {
	                up = true;
	                right = false;
	                left = false;
	            }
	            if ((key == KeyEvent.VK_DOWN) && (!up)) {
	                down = true;
	                right = false;
	                left = false;
	            }
	        }
	 }
	 @Override
	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        doDrawing(g);
	    }
	 private void doDrawing(Graphics g) {
	        if (inGame) {
	        	//MOVING FOOD//
	        	g.setColor(Color.WHITE);
		        g.fillOval(movingFood_x,movingFood_y,dotSize,dotSize);
	        	//ENEMY//
	        	g.setColor(Color.YELLOW);
		        g.fillRect(enemy_x,enemy_y,dotSize,dotSize);
	        	//FOOD//
	        	g.setColor(Color.BLUE);
		        g.fillRect(food_x,food_y,dotSize,dotSize);
	            for (int z = 0; z < dots; z++) {
	                if (z == 0) {
	                	//HEAD//
	                	g.setColor(Color.RED);
	        	        g.fillRect(x[z], y[z],dotSize,dotSize);
	                } else {
	                	//BODY//
	                	g.setColor(Color.GREEN);
	        	        g.fillOval(x[z], y[z],dotSize,dotSize);
	                }
	            }
	            Toolkit.getDefaultToolkit().sync();
	        } else {
	            gameOver(g);
	        }        
	    }
	 private void gameOver(Graphics g) {
	        String msg = "GameOver" + " " + points;
	        Font small = new Font("Helvetica", Font.BOLD, 14);
	        FontMetrics metr = getFontMetrics(small);
	        g.setColor(Color.white);
	        g.setFont(small);
	        g.drawString(msg, (boardWidth - metr.stringWidth(msg)) / 2, boardHeight / 2);
	    }
	 private void initGame() {
	        dots = 3;
	        for (int z = 0; z < dots; z++) {
	            x[z] = 50 - z * 10;
	            y[z] = 50;
	        }
	        locateFood();
	        locateEnemy();
	        locateMovingFood();
	        timer = new Timer(delay, this);
	        timer.start();
	    }
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(points >= 300) {
			inGame = false;
		}
		if (inGame) {
			checkMovingFood();
			checkEnemy();
            checkFood();
            collision();
            moveSnake();
        }
        repaint();
	}
}
