package Tools;

import java.awt.image.BufferedImage;

public class MirrorFlipper
{
	public BufferedImage flip(BufferedImage img)
	{
		int width = img.getWidth();
		int height = img.getHeight();
		
		BufferedImage flippedImg = new BufferedImage(width, height, img.getType());
		
		int p;
		
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < height; j++)
			{
				p = img.getRGB(i,j);
				flippedImg.setRGB(width - i - 1, j, p);
			}
		}
		
		return flippedImg;
	}
}
