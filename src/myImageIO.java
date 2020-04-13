import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class myImageIO implements ImageIOInterface
{
	@Override
	public BufferedImage load(String l)
	{	
		BufferedImage img = null;
		try 
		{
	    		img = ImageIO.read(new File(l));
	    		return img;
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void save(BufferedImage im, String l, String name) 
	{
		try 
		{
			String format = l.substring(l.lastIndexOf("."), l.length());
			name = name + format;
			String adjustedL = l.substring(0, l.lastIndexOf("\\"));
			adjustedL = adjustedL + name;
		    	File outputfile = new File(adjustedL);
		    	format = format.substring(1);
		    	ImageIO.write(im, format, outputfile);
		} 
		catch (IOException e) 
		{
		    	e.printStackTrace();
		}
	}
	
	@Override
	public String fixBackslash(String location)
	{
		String fixedLocation = location.replaceAll("\\\\", "\\\\\\\\");
		return fixedLocation;
	}
	
	@Override
	public boolean validateType(String location)
	{
		try 
		{
			ImageIO.read(new File(location)).toString();
			return true;
		} 
		catch(Exception e) 
		{
			return false;
		}
	}
}