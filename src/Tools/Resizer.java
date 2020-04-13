package Tools;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Resizer 
{
	public BufferedImage resize(BufferedImage img, int w, int h)
	{
		Image resizedImage;
		
		resizedImage = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		
		BufferedImage returnBuffer = new BufferedImage(resizedImage.getWidth(null), resizedImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics g = returnBuffer.getGraphics();
		g.drawImage(resizedImage, 0, 0, null);
		g.dispose();
		
		return returnBuffer;
	}
}
