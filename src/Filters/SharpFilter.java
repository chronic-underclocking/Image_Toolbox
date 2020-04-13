package Filters;

import java.awt.image.BufferedImage;

public class SharpFilter implements Filter
{
	private SmoothFilter SmoothenFilter = new SmoothFilter();
	
	@Override
	public BufferedImage apply(BufferedImage img) // Unsharp masking technique
	{
		int width = img.getWidth();
	    	int height = img.getHeight();
	    
	    	BufferedImage result = new BufferedImage(width, height, img.getType());
	    	BufferedImage smoothenedImg = SmoothenFilter.apply(img);
	    
	    	for(int i = 0; i < width; i++)
        	{
        		for(int j = 0; j < height; j++)
        		{
        			int p1 = img.getRGB(i, j);
        			int p2 = smoothenedImg.getRGB(i, j);
        		
        			int A1 = (p1>>24) & 0xff;
        			int R1 = (p1>>16) & 0xff;
        			int G1 = (p1>>8) & 0xff;
        			int B1 = (p1) & 0xff;
        		
        			int A2 = (p2>>24) & 0xff;
        			int R2 = (p2>>16) & 0xff;
        			int G2 = (p2>>8) & 0xff;
        			int B2 = (p2) & 0xff;
        		
        			int A = A1*2 - A2;
        			int R = R1*2 - R2;
        			int G = G1*2 - G2;
        			int B = B1*2 - B2;
        		
        			if(R > 255)
        			{ 
        				R=255;
        			}
			    	else if(R < 0)
			    	{
			    		R=0;
			    	}
			     
			    	if(G > 255) 
			    	{
			    		G = 255;
			    	}
			    	else if(G < 0) 
			    	{
			    		G = 0;
			    	}
			     
			    	if(B > 255)
			    	{
			    		B = 255;
			    	}
			    	else if(B < 0) 
			    	{	
			    		B = 0;
			    	}
        		
        			int p = (A<<24) | (R<<16) | (G<<8) | B;
        		
        			result.setRGB(i,j,p);
        		}
        	}
	    
		return result;
	}
	
}
