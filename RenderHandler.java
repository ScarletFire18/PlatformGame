import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;


public class RenderHandler 
   {
	   private BufferedImage view;
	   private int[] pixels;

	   public RenderHandler(int width, int height) 
	      {
		      //Create a BufferedImage that will represent our view.
		      view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		      //Create an array for pixels
		      pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
      
            //Arrangement of pixels
           /** for (int heightIndex = 0; heightIndex < height; heightIndex++) 
               {
			         int randomPixel = (int)(Math.random() * 0xFFFFFF);
               
                  for(int widthIndex = 0; widthIndex < width; widthIndex++) 
                     {
                        pixels[heightIndex * width + widthIndex] = randomPixel;
		               }

	            }*/
           }

      //Renders array of pixels to the screen
	   public void render(Graphics graphics)
	      {
		      graphics.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
	      }
      
      //Render an image to the array of pixels   
      public void renderImage(BufferedImage image, int xPosition, int yPosition, int xZoom, int yZoom)
         {
            int[] imagePixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
            
            for(int y = 0; y < image.getHeight(); y++)
               for(int x = 0; x < image.getWidth(); x++)
                  for(int yZoomPosition = 0; yZoomPosition < yZoom; yZoomPosition++)
                     for(int xZoomPosition = 0; xZoomPosition < xZoom; xZoomPosition++)
               {
                  setPixel(imagePixels[x + y * image.getWidth()], ((x * xZoom) + xPosition + xZoomPosition), ((y * yZoom) + yPosition + yZoomPosition));
                  pixels[((x * xZoom) + xPosition + xZoomPosition) + ((y * yZoom) + yPosition + yZoomPosition) * view.getWidth()] = imagePixels[x + y * image.getWidth()];
               }
         }
      
      private void setPixel(int pixel, int x, int y)
         {
            int pixelIndex = x + y * view.getWidth();
            if(pixels.length > pixelIndex)
               pixels[pixelIndex] = pixel;
         }
   }