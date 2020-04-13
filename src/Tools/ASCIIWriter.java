package Tools;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ASCIIWriter 
{
	private String characters = "`^\\\",::;Il!i~+_-?][}{1)(|\\\\/tfjrxnnuvczXYUJCLQ0OZmwqpdbkhao**#MW&8%B@$$";
	private Map<Integer, Character> mapping = new HashMap<Integer, Character>();
	private Resizer resizer = new Resizer();
	private Rotator rotator = new Rotator();
	
	public ASCIIWriter()
	{
		for(int i = 0; i < 25501; i++)
		{
			mapping.put(i/100, characters.charAt((int)(i/364)));
		}
	}
	
	public void WriteTXT(BufferedImage img, String location)
	{
		String adjustedLocation = location;
		adjustedLocation = adjustedLocation.substring(0,adjustedLocation.lastIndexOf("."));
		adjustedLocation = adjustedLocation + ".txt";
		
		int width = img.getWidth();
		int height = img.getHeight();
		
		BufferedImage adjustedImg = new BufferedImage(width, height, img.getType());
		
		for(int i = 0; i < height; i++)
		{
			for(int j = 0; j < width; j++)
			{
				adjustedImg.setRGB(j, i, img.getRGB(j, i));
			}
		}
		
		if(width >= height)
		{
			int ratio = (int) Math.ceil((double) width / height);

			while(height > 150)
			{
				height /= 1.1;
				width = height * ratio;
			}
		}
		
		else
		{
			int ratio = (int) Math.ceil((double) height / width);

			while(width > 257)
			{
				width /= 1.1;
				height = width * ratio;
			}
		}
		
		adjustedImg = resizer.resize(adjustedImg, width, height);
		adjustedImg = rotator.rotate(adjustedImg, false);
		
		width = adjustedImg.getWidth();
		height = adjustedImg.getHeight();

		try 
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(adjustedLocation, false));
			
			int p,r,g,b,avg;
			
			for(int i = 0; i < width; i++)
			{
				for(int j = height - 1; j >= 0; j--)
				{
					p = adjustedImg.getRGB(i, j);
			
					r = (p>>16)&0xff;
					g = (p>>8)&0xff;
					b = p&0xff;
					
					avg = (r+g+b)/3;
					
					writer.write(mapping.get(avg));
					writer.write(mapping.get(avg));
					writer.write(mapping.get(avg));
				}
				writer.newLine();
			}
			writer.close();
			
			File file = new File(adjustedLocation); 
			Desktop desktop = Desktop.getDesktop();    
			desktop.open(file);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
}