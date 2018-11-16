import java.awt.Canvas;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;
import java.awt.Graphics;
import java.lang.Runnable;
import java.lang.Thread;
import javax.swing.JFrame;
import javax.imageio.ImageIO;  //imports images
import java.io.IOException;

public class Game extends JFrame implements Runnable  //creates the game as an object of the JFrame class, implementing the Runnable interface.
{
	private Canvas canvas = new Canvas();  //Creates Canvas object called canvas
	private RenderHandler renderer;  //Creates rendering
   private BufferedImage testImage;

	public Game() throws IOException
	{
		//Make the program shutdown when we exit out.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Set the position and size of the frame.
		setBounds(0,0, 1008, 810);  //x,y,width,height

		//Put frame in the center of the screen.
		setLocationRelativeTo(null);

		//Add graphics compoent
		add(canvas);

		//Make our frame visible.
		setVisible(true);

		/**Create the object for buffer strategy, which prevents user from seeing objects 
      being drawn to the screen, and user only sees complete image*/
		canvas.createBufferStrategy(3);

		renderer = new RenderHandler(getWidth(), getHeight());
      
      testImage = loadImage("redBlock.png");

	}

	
	public void update() 
   {
      
	}

   private BufferedImage loadImage(String path) throws IOException
   {
      try
         {
            BufferedImage loadedImage = ImageIO.read(Game.class.getResource(path));
            BufferedImage formattedImage = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            formattedImage.getGraphics().drawImage(loadedImage, 0, 0, null);
            return formattedImage;
         }
         
      catch(IOException exception)
         {
            exception.printStackTrace(); 
            return null;        
         }
   }

	public void render() {
			BufferStrategy bufferStrategy = canvas.getBufferStrategy();
			Graphics graphics = bufferStrategy.getDrawGraphics();
			super.paint(graphics);
         
         for(int i = 0; i < (23*43); i = i + 23)
            {
               renderer.renderImage(testImage, i, 700, 1, 1);
               renderer.renderImage(testImage, i, 723, 1, 1);
               renderer.renderImage(testImage, i, 746, 1, 1);
			      renderer.render(graphics);

			      graphics.dispose();
			      bufferStrategy.show();
         }
	}

   //accounts for computer speed for maximum rendering while keeping the frames per second constant. (BufferStrategy)
	public void run() {
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		int i = 0;
		int x = 0;

		long lastTime = System.nanoTime(); //long 2^63
		double nanoSecondConversion = 1000000000.0 / 60; //60 frames per second
		double changeInSeconds = 0;

		while(true) {
			long now = System.nanoTime();

			changeInSeconds += (now - lastTime) / nanoSecondConversion;
			while(changeInSeconds >= 1) {
				update();
				changeInSeconds--;
			}

			render();
			lastTime = now;
		}

	}

	public static void main(String[] args) throws IOException
	{
      Game game = new Game();
		Thread gameThread = new Thread(game);
		gameThread.start();
	}

}