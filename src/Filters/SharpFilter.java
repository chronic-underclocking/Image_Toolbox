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
	    
	    	int p1, p2, A1, A2, R1, R2, G1, G2, B1, B2, A, R, G, B, p;
	    
	    	for(int i = 0; i < width; i++)
	    	{
        		for(int j = 0; j < height; j++)
        		{
        			p1 = img.getRGB(i, j);
        			p2 = smoothenedImg.getRGB(i, j);
        		
        			A1 = (p1>>24) & 0xff;
        			R1 = (p1>>16) & 0xff;
        			G1 = (p1>>8) & 0xff;
        			B1 = (p1) & 0xff;
        		
        			A2 = (p2>>24) & 0xff;
        			R2 = (p2>>16) & 0xff;
        			G2 = (p2>>8) & 0xff;
        			B2 = (p2) & 0xff;
        		
        			A = A1*2 - A2;
        			R = R1*2 - R2;
        			G = G1*2 - G2;
        			B = B1*2 - B2;
        		
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
        		
        			p = (A<<24) | (R<<16) | (G<<8) | B;
        		
        			result.setRGB(i,j,p);
        		}
        	}
	    
	    	return result;
	}
	
}
