package Tools;

import java.awt.image.BufferedImage;

public class Rotator 
{
	public BufferedImage rotate(BufferedImage img, boolean orientationRight)
	{
		int width  = img.getWidth();
		int height = img.getHeight();
		BufferedImage rotatedImage = new BufferedImage(height, width, img.getType());
		
		if(orientationRight == true)
		{
			for(int i=0; i < width; i++)
			{
				for(int j=0; j < height; j++)
				{
					rotatedImage.setRGB(height-1-j, i, img.getRGB(i,j));
				}
			}
		}
		
		else
		{
			for(int i=0; i < width; i++)
			{
				for(int j=0; j < height; j++)
				{
					rotatedImage.setRGB(j, width-1-i, img.getRGB(i,j));
				}
			}
		}
		
	    	return rotatedImage;
	}
}
