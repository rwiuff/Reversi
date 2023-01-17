package advancedreversi;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;


//conclusion 
//step 1 I try to use Thread to manipulate the Timer with the game but the problem is 
//Thread.sleep will take more time then 1 seconds
//wI try to slove the round trip time of Thread,sleep using Systeme.nanoTime
//but the problem is the same as the first one

//step 2 I try to use Animationtimer but we can't find how to change the player
//

//final step I use TimeLine to manipulate the Timer with the game
//

class ReversiTimer extends BorderPane {
	   private Text tTime;
	 
	   Controller ctr;
	
	   private Timeline timeline;
	   private int time = 300;
	   private int player;
	   
	   public ReversiTimer(int player,Controller ctr) {
	  
		   this.player = player;
		   this.ctr = ctr;

	      // set up the time display
	      tTime = new Text();
	      tTime.setFont(Font.font("SansSerif", 20));
	      javafx.scene.paint.Color paint = new javafx.scene.paint.Color(0.8941, 0.8235, 0.6745, 1.0);
	      tTime.setFill(paint);
	      setTime();

	      // lay out the UI
	      HBox buttonBox = new HBox(10);

	      buttonBox.setAlignment(Pos.CENTER);

	      setCenter(tTime);
	      setBottom(buttonBox);
	      setMargin(tTime, new Insets(20, 50, 20, 50));
	   }

	   public void start() {
	      KeyFrame kf = new KeyFrame(Duration.millis(1000), e -> {
	    	  if (time > 0)
	    		 time--;
	    	  else {
	    		  if (player == 1)
	    		  ctr.label.setText("Game is over!\n"+"Player 2  wins!");
	    		  else 
	    		  ctr.label.setText("Game is over!\n"+"Player 1  wins!");
	    	  }
	         setTime();
	      });

	      timeline = new Timeline(kf);
	      timeline.setCycleCount(Timeline.INDEFINITE);
	      timeline.play();
	   }

	   public void pause() {
	      timeline.pause();
	   }

	   public void resume() {
	      timeline.play();
	   }

	   public void clear() {
	      timeline.stop();
	      time = 300;
	    
	      setTime();
	   }

	   public void setTime() {
	      // flip 60 seconds over to a minute
		 
	    
	      // ensure that values < 10 are padded with a 0

	      tTime.setText( String.format("%d:%02d Player %d", time / 60, time % 60,player));
	   }
	   
	   public boolean timeout() {
		   if (time == 0) return true;
		   else return false;
	   }
}