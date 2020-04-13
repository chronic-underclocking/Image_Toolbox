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
	    
	    for (int i = 1; i < width - 1; i++) 
	    {
	        for (int j = 1; j < height - 1; j++) 
	        {
	
	            int p1 = gray(img.getRGB(i - 1, j - 1));
	            int p2 = gray(img.getRGB(i - 1, j));
	            int p3 = gray(img.getRGB(i - 1, j + 1));
	
	            int p4 = gray(img.getRGB(i, j - 1));
	            int p5 = gray(img.getRGB(i, j));
	            int p6 = gray(img.getRGB(i, j + 1));
	
	            int p7 = gray(img.getRGB(i + 1, j - 1));
	            int p8 = gray(img.getRGB(i + 1, j));
	            int p9 = gray(img.getRGB(i + 1, j + 1));
	            
	            int pixelNeighborhood[][] = 
	            	{{p1 ,        p2        ,   p3},
	            	 {p4 ,        p5        ,   p6},
	            	 {p7 ,        p8        ,   p9}};
	     
	
	            int gxsum = 0;
	            int gysum = 0;
	            
	            for(int k = 0; k < 3; k++)
	            {
	            	for(int l = 0; l < 3; l++)
	            	{
	            		gxsum += kernelx[k][l] * pixelNeighborhood[k][l]; 
	            		gysum += kernely[k][l] * pixelNeighborhood[k][l]; 
	            	}
	            }
	            
	            double gval = Math.sqrt((gxsum * gxsum) + (gysum * gysum));
	            int gradient = (int) gval;
	
	            if(maxG < gradient) 
	            {
	                maxG = gradient;
	            }
	
	            resultsArray[i][j] = gradient;
	        }
	    }
	
	    double scale = 255.0 / maxG;
	
	    for (int i = 1; i < width - 1; i++)
	    {
	        for (int j = 1; j < height - 1; j++) 
	        {
	            int color = resultsArray[i][j];
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
