import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class ScoreboardScene {
	int myScore;
	int[] ScoreRank = new int[5];
	String filename = "score.txt";
	JFrame frame;
	
    ScoreboardScene(){
    	frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Scoreboard");
        frame.setBounds(0, 0, PlayerController.SCREEN_WIDTH, PlayerController.SCREEN_HEIGHT);
        frame.setLayout(new BorderLayout());
        frame.setSize(PlayerController.SCREEN_WIDTH, PlayerController.SCREEN_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    ScoreboardScene(int score){
    	frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Scoreboard");
        frame.setBounds(0, 0, PlayerController.SCREEN_WIDTH, PlayerController.SCREEN_HEIGHT);
        frame.setLayout(new BorderLayout());
        frame.setSize(PlayerController.SCREEN_WIDTH, PlayerController.SCREEN_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        myScore = score;
        checkScoreboard();
    }
    
    public void checkScoreboard() {
    	File ScoreFile = new File(filename);
    	if(!ScoreFile.exists()){
    		try {
				ScoreFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		ScoreRank[0] = myScore;
    	}
    	else {
    		try (BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {

                // read line by line
                String line;
                int i = 0;
                while ((line = br.readLine()) != null) {
                    ScoreRank[i++] = Integer.parseInt(line);
                    System.out.println(i+ScoreRank[i-1]);
                }
                if(myScore>ScoreRank[4]) {
                	ScoreRank[4] = myScore;
                }
                Arrays.sort(ScoreRank);

            } catch (IOException e) {
                System.err.format("IOException: %s%n", e);
            }
    		
    	}
    	writeScore();
    }
    
    public void writeScore() {
    	PrintWriter writer;
		try {
			writer = new PrintWriter(filename);
			
			int i = 4;
			
	    	while(i >= 0) {
	    		writer.println(ScoreRank[i--]);
	    	}
	    	
			setCanvas();
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
    		frame.add(label);
    		System.out.println(ScoreRank[i--]+" "+rank);
    		rank++;
    	}
    }
}
