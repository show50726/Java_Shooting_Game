import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) {
	    JFrame frame = new JFrame();
	    JButton mybutton = new JButton("Start");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setTitle("Start");
	    frame.setBounds(0, 0, PlayerController.SCREEN_WIDTH, PlayerController.SCREEN_HEIGHT);
	    frame.setLayout(new BorderLayout());
	    frame.setSize(PlayerController.SCREEN_WIDTH, PlayerController.SCREEN_HEIGHT);
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
	    mybutton.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new PlayerController();
				frame.setVisible(false);
				frame.dispose();
				//frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}});
	    frame.add(mybutton);
	    
	}
}
