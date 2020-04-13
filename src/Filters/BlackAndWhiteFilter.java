package Filters;

import java.awt.image.BufferedImage;

public class BlackAndWhiteFilter implements Filter 
{
	@Override
	public BufferedImage apply(BufferedImage img) 
	{	
		int width = img.getWidth();
		int height = img.getHeight();
		int p,a,r,g,b,avg;
		
		BufferedImage result = new BufferedImage(width, height, img.getType());
		
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < height; j++)
			{
				p = img.getRGB(i, j);
				
				a = (p>>24)&0xff;
				r = (p>>16)&0xff;
				g = (p>>8)&0xff;
				b = p&0xff;
				
				avg = (r+g+b)/3;
				
				p = (a<<24) | (avg<<16) | (avg<<8) | avg;
				
				result.setRGB(i, j, p);
			}
		}
		
		return result;
	}
	
}
