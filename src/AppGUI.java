import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Filters.BlackAndWhiteFilter;
import Filters.EdgeDetectionFilter;
import Filters.SharpFilter;
import Filters.SmoothFilter;
import Tools.ASCIIWriter;
import Tools.Cropper;
import Tools.MirrorFlipper;
import Tools.Resizer;
import Tools.Rotator;
import javax.swing.JButton;

public class AppGUI 
{

	private JFrame frmImageToolbox;
	private BufferedImage img;
	private String location;
	
	private myImageIO imgIO = new myImageIO();
	private ImageStorage storage = new ImageStorage();
	private Rotator rotator = new Rotator();
	private Resizer resizer = new Resizer();
	private MirrorFlipper mirror = new MirrorFlipper();
	private Cropper cropper = new Cropper();
	private ASCIIWriter ASCIIW = new ASCIIWriter();
	private BlackAndWhiteFilter BWFilter = new BlackAndWhiteFilter();
	private EdgeDetectionFilter EDFilter = new EdgeDetectionFilter();
	private SmoothFilter SmoothenFilter = new SmoothFilter();
	private SharpFilter SharpenFilter = new SharpFilter();
	
	private final int PANEL_WIDTH = 1141;
	private final int PANEL_HEIGHT = 642;
	
	private boolean cropHintShown = false;
	private boolean smoothHintShown = false;
	private boolean sharpHintShown = false;
	private int ClickX = 0;
	private int ClickY = 0;
	private int newClickX = 10;
	private int newClickY = 10;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					AppGUI window = new AppGUI();
					window.frmImageToolbox.setVisible(true);
				} 
				
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AppGUI() 
	{
		initialize();
	}
	
	/**
	 * Display the image after adjusting size
	 */
	private void display(JPanel p, JLabel l)
	{
		int iconWidth = img.getWidth();
		int iconHeight = img.getHeight();
		while(iconWidth > PANEL_WIDTH || iconHeight > PANEL_HEIGHT)
		{
			iconWidth /= 1.1;
			iconHeight /= 1.1;
		}
		
		l.setIcon(new ImageIcon(img.getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH)));
		l.setBounds(12, 0, iconWidth, iconHeight);
		
		p.revalidate();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frmImageToolbox = new JFrame();
		frmImageToolbox.setTitle("Image Toolbox");
		frmImageToolbox.setBounds(100, 100, 1183, 715);
		frmImageToolbox.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel picLabel = new JLabel();
		picLabel.addMouseListener(new MouseAdapter() 
		{
			public void mousePressed(MouseEvent me) 
	        	{
				ClickX = newClickX;
				ClickY = newClickY;
				newClickX = me.getX();
				newClickY = me.getY();
			}
	     	});
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 0, PANEL_WIDTH, PANEL_HEIGHT);
		panel.add(picLabel);
		frmImageToolbox.getContentPane().add(panel);
		
		JMenuBar menuBar = new JMenuBar();
		frmImageToolbox.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmLoad = new JMenuItem("Load");
		mntmLoad.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{

				JFileChooser fileChooser = new JFileChooser();
		        	int returnValue = fileChooser.showOpenDialog(null);
		        	if (returnValue == JFileChooser.APPROVE_OPTION) 
		        	{
		        		location = fileChooser.getSelectedFile().getAbsolutePath();
		        		location = imgIO.fixBackslash(location);
		        	
		        		if(imgIO.validateType(location))
		        		{
		        			img = imgIO.load(location);
		        			storage.clear();
		        			storage.add(img);
		        			display(panel, picLabel);
		        		}
		        	
		        		else
			        	{
		        			JOptionPane.showMessageDialog(frmImageToolbox, "Unsupported file type!", "Error", JOptionPane.INFORMATION_MESSAGE);
		        		}
		  	    	}
			}
		});
		mnFile.add(mntmLoad);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(img != null)
				{
					String name = JOptionPane.showInputDialog(frmImageToolbox, "Enter new name:");
					if(name != null)
					{
						imgIO.save(img, location, name);
						JOptionPane.showMessageDialog(frmImageToolbox, "Image saved to the same folder.", "Message", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				else
				{
					JOptionPane.showMessageDialog(frmImageToolbox, "Load an image first!", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		mnFile.add(mntmSave);
		
		JMenuItem mntmAsciiArt = new JMenuItem("ASCII Art");
		mnFile.add(mntmAsciiArt);
		mntmAsciiArt.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(img != null)
				{
					JOptionPane.showMessageDialog(frmImageToolbox, "- Zoom out to see the result.\n- Looks best on a black backgorund with white letters.\n"
							+ "- Temporarily inverting your colors from the control panel is recommended.\n- Text file saved to the same folder.", "Message", JOptionPane.INFORMATION_MESSAGE);
					ASCIIW.WriteTXT(img, location);
				}
				else
				{
					JOptionPane.showMessageDialog(frmImageToolbox, "Load an image first!", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		JMenu mnTools = new JMenu("Tools");
		menuBar.add(mnTools);
		
		JMenuItem mntmRotateLeft = new JMenuItem("Rotate left");
		mntmRotateLeft.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(img != null)
				{
					img = rotator.rotate(img, false);
					storage.add(img);
					display(panel, picLabel);
				}
				else
				{
					JOptionPane.showMessageDialog(frmImageToolbox, "Load an image first!", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		mnTools.add(mntmRotateLeft);
		
		JMenuItem mntmRotateRight = new JMenuItem("Rotate right");
		mntmRotateRight.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(img != null)
				{
					img = rotator.rotate(img, true);
					storage.add(img);
					display(panel, picLabel);
				}
				else
				{
					JOptionPane.showMessageDialog(frmImageToolbox, "Load an image first!", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		mnTools.add(mntmRotateRight);
		
		JMenuItem mntmMirror = new JMenuItem("Mirror");
		mntmMirror.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(img != null)
				{
					img = mirror.flip(img);
					storage.add(img);
					display(panel, picLabel);
				}
				else
				{
					JOptionPane.showMessageDialog(frmImageToolbox, "Load an image first!", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		mnTools.add(mntmMirror);
		
		JMenuItem mntmCrop = new JMenuItem("Crop");
		mntmCrop.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(img != null)
				{
					if(cropHintShown == false)
					{
						JOptionPane.showMessageDialog(frmImageToolbox, "- Image will be cropped using your last two image clicks.\n"
							+ "- First click will be the upper left point of the cropped section.\n- Second click will be the lower right point."
							+ "\n- Click undo to try again.", "Message", JOptionPane.INFORMATION_MESSAGE);
						cropHintShown = true;
					}
					int width = Math.abs(newClickX - ClickX);
					int height = Math.abs(newClickY - ClickY);
					int labelWidth = picLabel.getWidth();
					int labelHeight = picLabel.getHeight();
					img = cropper.crop(img, ClickX, ClickY, width, height, labelWidth, labelHeight);
					storage.add(img);
					display(panel, picLabel);
				}
				else
				{
					JOptionPane.showMessageDialog(frmImageToolbox, "Load an image first!", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		mnTools.add(mntmCrop);
		
		JMenuItem mntmResize = new JMenuItem("Resize");
		mntmResize.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(img != null)
				{
					try
					{
						int newWidth = Integer.parseInt(JOptionPane.showInputDialog(frmImageToolbox, "Enter new width:"));
						int newHeight = Integer.parseInt(JOptionPane.showInputDialog(frmImageToolbox, "Enter new height:"));
						img = resizer.resize(img, newWidth, newHeight);
						storage.add(img);
						display(panel, picLabel);
					}
					catch(NumberFormatException nfe)
					{
						JOptionPane.showMessageDialog(frmImageToolbox, "Please enter a valid number!", "Error", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				else
				{
					JOptionPane.showMessageDialog(frmImageToolbox, "Load an image first!", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		mnTools.add(mntmResize);
		
		JMenu mnFilters = new JMenu("Filters");
		menuBar.add(mnFilters);
		
		JMenuItem mntmBlackAndWhite = new JMenuItem("Black and White");
		mntmBlackAndWhite.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(img != null)
				{
					img = BWFilter.apply(img);
					storage.add(img);
					display(panel, picLabel);
				}
				else
				{
					JOptionPane.showMessageDialog(frmImageToolbox, "Load an image first!", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		mnFilters.add(mntmBlackAndWhite);
		
		JMenuItem mntmEdgeDetection = new JMenuItem("Edge Detection");
		mntmEdgeDetection.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(img != null)
				{
					img = EDFilter.apply(img);
					storage.add(img);
					display(panel, picLabel);
				}
				else
				{
					JOptionPane.showMessageDialog(frmImageToolbox, "Load an image first!", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		mnFilters.add(mntmEdgeDetection);
		
		JMenuItem mntmSharpen = new JMenuItem("Sharp");
		mntmSharpen.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(img != null)
				{
					img = SharpenFilter.apply(img);
					storage.add(img);
					display(panel, picLabel);
					if(sharpHintShown == false)
					{
						JOptionPane.showMessageDialog(frmImageToolbox, "This filter's effect can be amplified by repeatedly applying it!", "Message", JOptionPane.INFORMATION_MESSAGE);
						sharpHintShown = true;
					}
				}
				else
				{
					JOptionPane.showMessageDialog(frmImageToolbox, "Load an image first!", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		mnFilters.add(mntmSharpen);
		
		JMenuItem mntmSmooth = new JMenuItem("Smooth");
		mntmSmooth.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(img != null)
				{
					img = SmoothenFilter.apply(img);
					storage.add(img);
					display(panel, picLabel);
					if(smoothHintShown == false)
					{
						JOptionPane.showMessageDialog(frmImageToolbox, "This filter's effect can be amplified by repeatedly applying it!", "Message", JOptionPane.INFORMATION_MESSAGE);
						smoothHintShown = true;
					}
				}
				else
				{
					JOptionPane.showMessageDialog(frmImageToolbox, "Load an image first!", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		mnFilters.add(mntmSmooth);
		
		JButton btnUndo = new JButton("Undo");
		btnUndo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(storage.getimgCount() >= 2)
				{
					img = storage.undo();
					display(panel, picLabel);
				}
			}
		});
		btnUndo.setFocusPainted(false);
		menuBar.add(btnUndo);
		frmImageToolbox.getContentPane().setLayout(null);
	}
}
