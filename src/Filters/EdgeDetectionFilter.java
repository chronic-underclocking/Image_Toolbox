package Filters;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class EdgeDetectionFilter implements Filter
{
	public static int gray(int p)
	{
	    int r = (p >> 16) & 0xff;
	    int g = (p >> 8) & 0xff;
	    int b = (p) & 0xff;
	
	    int grayColor = (int)(0.2126 * r + 0.7152 * g + 0.0722 * b);
	    
	    return grayColor;
	}
	
	// Sobel Edge Detection Method. Good explanation video: https://www.youtube.com/watch?v=uihBwtPIBxM
	@Override
	public BufferedImage apply(BufferedImage img) 
	{	
		int width = img.getWidth();
	   	int height = img.getHeight();
	    
	    	BufferedImage result = new BufferedImage(width, height, img.getType());
	
	    	int[][] resultsArray = new int[width][height];
	    
	    	int maxG = -1;
	    
	    	int[][] kernelx =
	    		{{ -1      ,        0         ,          1},
	    	 	 { -2      ,        0         ,          2}, 
	    	 	 { -1      ,        0         ,          1}};
	    
	    	int[][] kernely =
	    		{{ -1     ,       -2         ,         -1},
	    	 	 { 0      ,        0         ,          0}, 
	    	 	 { 1      ,        2         ,          1}};

		int p1, p2, p3, p4, p5, p6, p7, p8 ,p9, gxsum, gysum, gradient;
		double gval;
	    
	    	for (int i = 1; i < width - 1; i++) 
	    	{
	        	for (int j = 1; j < height - 1; j++) 
        		{
	
		        	p1 = gray(img.getRGB(i - 1, j - 1));
				p2 = gray(img.getRGB(i - 1, j));
				p3 = gray(img.getRGB(i - 1, j + 1));
	
	            		p4 = gray(img.getRGB(i, j - 1));
	            		p5 = gray(img.getRGB(i, j));
	            		p6 = gray(img.getRGB(i, j + 1));
	
	            		p7 = gray(img.getRGB(i + 1, j - 1));
	            		p8 = gray(img.getRGB(i + 1, j));
	            		p9 = gray(img.getRGB(i + 1, j + 1));
	            
	            		int pixelNeighborhood[][] = 
	            			{{p1 ,        p2        ,   p3},
	            	 		 {p4 ,        p5        ,   p6},
	            	 		 {p7 ,        p8        ,   p9}};
	     
	
	            		gxsum = 0;
	            		gysum = 0;
	            
	            		for(int k = 0; k < 3; k++)
	            		{
	            			for(int l = 0; l < 3; l++)
	            			{
	            				gxsum += kernelx[k][l] * pixelNeighborhood[k][l]; 
	            				gysum += kernely[k][l] * pixelNeighborhood[k][l]; 
	            			}
	            		}
	            
	        	    	gval = Math.sqrt((gxsum * gxsum) + (gysum * gysum));
		            	gradient = (int) gval;
	
	            		if(maxG < gradient) 
	            		{
	                		maxG = gradient;
	            		}
	
	            		resultsArray[i][j] = gradient;
	        	}
	    	}
	
	    	double scale = 255.0 / maxG;
		int color;

	    	for (int i = 1; i < width - 1; i++)
	    	{
	        	for (int j = 1; j < height - 1; j++) 
	        	{
	            		color = resultsArray[i][j];
	            		color = (int)(color * scale);
	            		color = 0xff000000 | (color << 16) | (color << 8) | color;
	
	            		result.setRGB(i, j, color);
	       		}
	    	}
	    
	    	int black = Color.black.getRGB();
	    
	    	for (int i = 0; i < width; i++)
	    	{
	    		result.setRGB(i, 0, black);
	    		result.setRGB(i, height-1, black);
	    	}
	    
	    	for (int i = 0; i < height; i++)
	    	{
	    		result.setRGB(0, i, black);
	    		result.setRGB(width-1, i, black);
	    	}
	
	   	return result;
	}
	
}
