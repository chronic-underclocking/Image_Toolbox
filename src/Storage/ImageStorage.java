package Storage;

import java.awt.image.BufferedImage;

public class ImageStorage 
{
	private BufferedImage[] history = new BufferedImage[1000];
	private int imgCount = 0;
	
	public void add(BufferedImage img)
	{
		history[imgCount] = img;
		imgCount++;
	}
	
	public void clear()
	{
		imgCount = 0;
	}
	
	public int getimgCount()
	{
		return imgCount;
	}
	
	public BufferedImage undo()
	{
		if(imgCount > 1)
		{
			imgCount--;
			return history[imgCount - 1];
		}
		
		else
		{
			return null;
		}
	}
}
