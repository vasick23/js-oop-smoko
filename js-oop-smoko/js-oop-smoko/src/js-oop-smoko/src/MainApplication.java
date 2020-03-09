import java.awt.EventQueue;
import javax.swing.JFrame;
public class MainApplication extends JFrame {
	private static final long serialVersionUID = 10;
	public MainApplication() {   
        start();
    }
	 private void start() {
	        add(new GameBoard());     
	        setResizable(false);
	        pack();
	        setTitle("Snake");
	        setLocationRelativeTo(null);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    } 
	public static void main(String[] args) {
		  EventQueue.invokeLater(() -> {
	            JFrame ex = new MainApplication();
	            ex.setVisible(true);
	        });
	}
}