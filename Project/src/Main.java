import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class Main extends JFrame {
	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Main();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setFont(new Font("Bahnschrift", Font.BOLD, 16));
		setTitle("Start");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 540, 700);
		setSize(PlayerController.SCREEN_WIDTH, PlayerController.SCREEN_HEIGHT-51);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setVisible(true);
		
		JLabel GameStart2 = new JLabel("");
		GameStart2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				new PlayerController();
				setVisible(false);
				dispose();
			}
		});
		GameStart2.setIcon(new ImageIcon(Main.class.getResource("/images/GAME START_RED.png")));
		GameStart2.setHorizontalAlignment(SwingConstants.CENTER);
		GameStart2.setBounds(106, 279, 302, 71);
		GameStart2.setVisible(false);
		contentPane.add(GameStart2);
		
		JLabel GameStart = new JLabel("");
		GameStart.setHorizontalAlignment(SwingConstants.CENTER);
		GameStart.setIcon(new ImageIcon(Main.class.getResource("/images/圖片1.png")));
		GameStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				GameStart.setVisible(false);
				GameStart2.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				GameStart.setVisible(true);
				GameStart2.setVisible(false);
			}
		});
		GameStart.setBounds(106, 279, 302, 71);
		contentPane.add(GameStart);
		
		JLabel Back2 = new JLabel("");
		Back2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.exit(0);
			}
		});
		Back2.setIcon(new ImageIcon(Main.class.getResource("/images/BACK_RED.png")));
		Back2.setHorizontalAlignment(SwingConstants.CENTER);
		Back2.setBounds(106, 354, 302, 71);
		Back2.setVisible(false);
		contentPane.add(Back2);
		
		JLabel Back = new JLabel("");
		Back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				Back.setVisible(false);
				Back2.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				Back2.setVisible(false);
				Back.setVisible(true);
			}
		});
		

		Back.setHorizontalAlignment(SwingConstants.CENTER);
		Back.setIcon(new ImageIcon(Main.class.getResource("/images/BACK.png")));
		Back.setBounds(106, 354, 302, 71);
		contentPane.add(Back);
		
		JLabel backgroundImg = new JLabel("");
		backgroundImg.setBounds(-375, -113, 1560, 925);
		backgroundImg.setIcon(new ImageIcon(Main.class.getResource("/images/圖片12.png")));
		contentPane.add(backgroundImg);
	}
}
