package Tools;

import java.awt.image.BufferedImage;

public class Cropper 
{
	private Resizer resizer = new Resizer();
	
	public BufferedImage crop(BufferedImage img, int x, int y, int w, int h, int lw, int lh)
	{
		BufferedImage croppedImg = resizer.resize(img, lw, lh);
		croppedImg = croppedImg.getSubimage(x, y, w, h);
		
		return croppedImg;
	}
}
