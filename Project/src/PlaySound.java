import java.io.FileInputStream;
import java.io.IOException;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class PlaySound implements Runnable {
	FileInputStream sound;
	
	public PlaySound(String sound) {
		try {
			this.sound = new FileInputStream(sound);           //load the .wav file
		}catch (Exception e){
			e.printStackTrace();
		}
		play();            //play the sound
	}
	
	public void play() 
    {
        Thread t = new Thread(this);           //create new thread to play (or we cannot play two sound at the same time)
        t.start();
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ToPlay();
	}

	@SuppressWarnings("restriction")
	private void ToPlay(){
		// TODO Auto-generated method stub
		try {
			AudioStream s=new AudioStream(sound);
			AudioPlayer.player.start(s);
		}catch (Exception e){
			e.printStackTrace();
		}
		
	}
}
