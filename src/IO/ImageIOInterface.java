package IO;

import java.awt.image.BufferedImage;

interface ImageIOInterface 
{
	
	BufferedImage load(String l);
	void save(BufferedImage im, String l, String n);
	boolean validateType(String l);
	String fixBackslash(String l);
}