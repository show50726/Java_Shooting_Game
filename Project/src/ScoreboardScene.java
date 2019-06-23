import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

//Scoreboard Scene
public class ScoreboardScene extends JFrame {
	int myScore;
	int[] ScoreRank = new int[5];           //Save the top 5 scores
	String filename = "score.txt";          //file of the score
	//JFrame frame;
	private JPanel contentPane;
	
    
    ScoreboardScene(int score){
    	myScore = score;
    	checkScoreboard();      
    	//Scoreboard Frame
    	setFont(new Font("Bahnschrift", Font.BOLD, 16));
		setTitle("Scoreboard");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 540, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		
		JLabel title = new JLabel("Score");
		title.setForeground(new Color(255, 255, 255));
		title.setFont(new Font("Algerian", Font.BOLD | Font.ITALIC, 35));
		title.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		title.setBounds(201, 126, 129, 71);
		contentPane.add(title);
		
		JLabel line = new JLabel("New label");
		line.setIcon(new ImageIcon(ScoreboardScene.class.getResource("/images/分隔線.png")));
		line.setBounds(79, 174, 364, 23);
		contentPane.add(line);
		
		JLabel line_2 = new JLabel("");
		line_2.setIcon(new ImageIcon(ScoreboardScene.class.getResource("/images/分隔線.png")));
		line_2.setBounds(79, 395, 364, 23);
		contentPane.add(line_2);
		
		JLabel score_1 = new JLabel(Integer.toString(ScoreRank[4]));    //fill different score to the scoreboard from greatest to least 
		score_1.setHorizontalAlignment(SwingConstants.CENTER); 
		score_1.setForeground(Color.WHITE);
		score_1.setFont(new Font("Algerian", Font.PLAIN, 22));
		score_1.setBounds(201, 186, 129, 39);
		contentPane.add(score_1);
		
		JLabel score_2 = new JLabel(Integer.toString(ScoreRank[3]));
		score_2.setHorizontalAlignment(SwingConstants.CENTER);
		score_2.setForeground(Color.WHITE);
		score_2.setFont(new Font("Algerian", Font.PLAIN, 22));
		score_2.setBounds(201, 229, 129, 39);
		contentPane.add(score_2);
		
		JLabel score_3 = new JLabel(Integer.toString(ScoreRank[2]));
		score_3.setHorizontalAlignment(SwingConstants.CENTER);
		score_3.setForeground(Color.WHITE);
		score_3.setFont(new Font("Algerian", Font.PLAIN, 22));
		score_3.setBounds(201, 272, 129, 39);
		contentPane.add(score_3);
		
		JLabel score_4 = new JLabel(Integer.toString(ScoreRank[1]));
		score_4.setHorizontalAlignment(SwingConstants.CENTER);
		score_4.setForeground(Color.WHITE);
		score_4.setFont(new Font("Algerian", Font.PLAIN, 22));
		score_4.setBounds(201, 315, 129, 39);
		contentPane.add(score_4);
		
		JLabel score_5 = new JLabel(Integer.toString(ScoreRank[0]));
		score_5.setHorizontalAlignment(SwingConstants.CENTER);
		score_5.setForeground(Color.WHITE);
		score_5.setFont(new Font("Algerian", Font.PLAIN, 22));
		score_5.setBounds(201, 358, 129, 39);
		contentPane.add(score_5);
		
		JLabel first = new JLabel("1");
		first.setHorizontalAlignment(SwingConstants.CENTER);
		first.setForeground(Color.WHITE);
		first.setFont(new Font("Algerian", Font.BOLD, 22));
		first.setBounds(122, 186, 30, 39);
		contentPane.add(first);
		
		JLabel second = new JLabel("2");
		second.setHorizontalAlignment(SwingConstants.CENTER);
		second.setForeground(Color.WHITE);
		second.setFont(new Font("Algerian", Font.BOLD, 22));
		second.setBounds(122, 229, 30, 39);
		contentPane.add(second);
		
		JLabel third = new JLabel("3");
		third.setHorizontalAlignment(SwingConstants.CENTER);
		third.setForeground(Color.WHITE);
		third.setFont(new Font("Algerian", Font.BOLD, 22));
		third.setBounds(122, 272, 30, 39);
		contentPane.add(third);
		
		JLabel forth = new JLabel("4");
		forth.setHorizontalAlignment(SwingConstants.CENTER);
		forth.setForeground(Color.WHITE);
		forth.setFont(new Font("Algerian", Font.BOLD, 22));
		forth.setBounds(122, 315, 30, 39);
		contentPane.add(forth);
		
		JLabel fifth = new JLabel("5");
		fifth.setHorizontalAlignment(SwingConstants.CENTER);
		fifth.setForeground(Color.WHITE);
		fifth.setFont(new Font("Algerian", Font.BOLD, 22));
		fifth.setBounds(122, 358, 30, 39);
		contentPane.add(fifth);
		
		JLabel mysocre = new JLabel(Integer.toString(myScore));
		mysocre.setHorizontalAlignment(SwingConstants.CENTER);
		mysocre.setForeground(Color.WHITE);
		mysocre.setFont(new Font("Algerian", Font.PLAIN, 22));
		mysocre.setBounds(205, 519, 129, 39);
		contentPane.add(mysocre);
		
		JLabel lblMyScore = new JLabel("My score:");
		lblMyScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblMyScore.setForeground(Color.WHITE);
		lblMyScore.setFont(new Font("Algerian", Font.BOLD, 22));
		lblMyScore.setBounds(79, 519, 155, 39);
		contentPane.add(lblMyScore);
		
		JLabel line3 = new JLabel("");
		line3.setIcon(new ImageIcon(ScoreboardScene.class.getResource("/images/分隔線.png")));
		line3.setBounds(83, 547, 364, 23);
		contentPane.add(line3);
		
		JLabel line4 = new JLabel("");
		line4.setIcon(new ImageIcon(ScoreboardScene.class.getResource("/images/分隔線.png")));
		line4.setBounds(83, 509, 364, 23);
		contentPane.add(line4);
		
		JLabel Back2 = new JLabel("");
		Back2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				new Main();
				setVisible(false);
				dispose();
			}
		});
		Back2.setIcon(new ImageIcon(Main.class.getResource("/images/BACK_RED.png")));
		Back2.setHorizontalAlignment(SwingConstants.CENTER);
		Back2.setBounds(106, 639, 302, 71);
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
		Back.setBounds(106, 639, 302, 71);
		contentPane.add(Back);
		
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
		GameStart2.setBounds(106, 573, 302, 71);
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
		GameStart.setBounds(106, 573, 302, 71);
		contentPane.add(GameStart);
		
		JLabel backgroundImg = new JLabel("");
		backgroundImg.setBounds(-235, -70, 1560, 925);
		backgroundImg.setIcon(new ImageIcon(ScoreboardScene.class.getResource("/images/圖片3.png")));
		contentPane.add(backgroundImg);
	}
    
    
    public void checkScoreboard() {
    	File ScoreFile = new File(filename);          
    	if(!ScoreFile.exists()){         //if the score.txt doesn't exist, create one
    		try {
				ScoreFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		ScoreRank[0] = myScore;           //fill player's score into the score array
    	}
    	else {
    		try (BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {  //if the file already exists, read the content

                // read line by line
                String line;
                int i = 0;
                while ((line = br.readLine()) != null) {
                    ScoreRank[i++] = Integer.parseInt(line);
                    System.out.println(i+ScoreRank[i-1]);
                }
                if(myScore>ScoreRank[4]) {        //if the player's score is greater than 5th score, replace it
                	ScoreRank[4] = myScore;
                }
                Arrays.sort(ScoreRank);       //sort the scores

            } catch (IOException e) {
                System.err.format("IOException: %s%n", e);
            }
    		
    	}
    	writeScore();         
    }
    
    public void writeScore() {         //write the scores into the scores.txt
    	PrintWriter writer;
		try {
			writer = new PrintWriter(filename);
			
			int i = 4;
			
	    	while(i >= 0) {
	    		writer.println(ScoreRank[i--]);
	    	}
	    	
			//setCanvas();
	    	writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
    
    public void setCanvas() {
    	int i = 4, rank = 1;
		System.out.println("Rank: ");
    	while(i >= 0) {
    		JLabel label = new JLabel(rank+". "+ScoreRank[i]+"\n");
    		label.setBounds(0, rank*20, 200, 50);
    		add(label);
    		System.out.println(ScoreRank[i--]+" "+rank);
    		rank++;
    	}
    }
}
