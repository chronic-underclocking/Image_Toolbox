package Filters;

import java.awt.image.BufferedImage;

public class SmoothFilter implements Filter
{

	@Override
	public BufferedImage apply(BufferedImage img)
	{
		int width = img.getWidth();
	    	int height = img.getHeight();
	    
	    	BufferedImage result = new BufferedImage(width, height, img.getType());
	    
	    	int p01, p02, p03, p04, p05, p06, p07, p08, p09, p10, p11, p12, p13, p14;
	    	int p15, p16, p17, p18, p19, p20, p21, p22, p23, p24, p25;
	    	int A, R, G, B, p;
	    
	    	for (int i = 2; i < width - 2; i++) 
	    	{
	        	for (int j = 2; j < height - 2; j++) 
	        	{
				p01 = img.getRGB(i - 2, j - 2);
	            		p02 = img.getRGB(i - 2, j - 1);
	            		p03 = img.getRGB(i - 2, j);
	            		p04 = img.getRGB(i - 2, j + 1);
	            		p05 = img.getRGB(i - 2, j + 2);
	
	            		p06 = img.getRGB(i - 1, j - 2);
	            		p07 = img.getRGB(i - 1, j - 1);
	            		p08 = img.getRGB(i - 1, j);
	            		p09 = img.getRGB(i - 1, j + 1);
	            		p10 = img.getRGB(i - 1, j + 2);
	            
	            		p11 = img.getRGB(i, j - 2);
	            		p12 = img.getRGB(i, j - 1);
	            		p13 = img.getRGB(i, j);
	            		p14 = img.getRGB(i, j + 1);
	            		p15 = img.getRGB(i, j + 2);
	
	            		p16 = img.getRGB(i + 1, j - 2);
	            		p17 = img.getRGB(i + 1, j - 1);
	            		p18 = img.getRGB(i + 1, j);
	            		p19 = img.getRGB(i + 1, j + 1);
	            		p20 = img.getRGB(i + 1, j + 2);
	
	            		p21 = img.getRGB(i + 2, j - 2);
	            		p22 = img.getRGB(i + 2, j - 1);
	            		p23 = img.getRGB(i + 2, j);
	            		p24 = img.getRGB(i + 2, j + 1);
	            		p25 = img.getRGB(i + 2, j + 2);
	            
	            
	            		int pixelNeighborhood[][] = 
	            			{{p01, p02, p03, p04, p05},
	            	 		{p06, p07, p08, p09, p10},
	            	 		{p11, p12, p13, p14, p15},
	            	 		{p16, p17, p18, p19, p20},
	            	 		{p21, p22, p23, p24, p25}};
	
	            		A = 0;
	            		R = 0;
	            		G = 0;
	            		B = 0;
	            
	            		for(int k = 0; k < 5; k++)
	            		{
	            			for(int l = 0; l < 5; l++)
	            			{
	            				A += (pixelNeighborhood[k][l]>>24) & 0xff;
	            				R += (pixelNeighborhood[k][l]>>16) & 0xff;
	            				G += (pixelNeighborhood[k][l]>>8) & 0xff;
	            				B += (pixelNeighborhood[k][l]) & 0xff;
	            			}
	            		}	
	            
	            		A /= 25;
	            		R /= 25;
	            		G /= 25;
	            		B /= 25;
	            
	            		p = (A<<24) | (R<<16) | (G<<8) | B;
	            
	            		result.setRGB(i,j,p);
	        	}
	    	}
	    
	    	for (int i = 0; i < width; i++)
	    	{
	    		result.setRGB(i, 0, img.getRGB(i, 0));
	    		result.setRGB(i, height - 1, img.getRGB(i, height - 1));
	    	
	    		result.setRGB(i, 1, img.getRGB(i, 1));
	    		result.setRGB(i, height - 2, img.getRGB(i, height - 2));
	    	}
	    
	    	for (int i = 0; i < height; i++)
	    	{	
	    		result.setRGB(0, i, img.getRGB(0, i));
	    		result.setRGB(width - 1, i, img.getRGB(width - 1, i));
	    	
	    		result.setRGB(1, i, img.getRGB(1, i));
	    		result.setRGB(width - 2, i, img.getRGB(width - 2, i));
	    	}
	    
	    	return result;
	}
}	