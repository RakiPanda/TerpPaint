/*
 * SlideShow.java
 *
 * Created on April 6, 2002, 7:00 PM
 */

/**
 *
 * @author Terp Paint
 * @version 2.0
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import com.sun.image.codec.jpeg.*;

/** This class supposively displays a slide show of different bitmaps or jpegs.
 * Unfortunately, it does not appear to be working at the moment.
 * State transitions are next and previous pictures.  Also there will be
 * a timer which forwards through the pictures by itself.
 *
 * There are currently no OS dependencies or variations.  There are no security
 * constraints and no external references.
 * @author Terp Paint
 * @version 2.0
 */
public class SlideShow extends javax.swing.JWindow{

    //public class viewBitmap extends javax.swing.JDialog {
    /** This field is of type BufferedImage which includes the main image.
     */
    BufferedImage main_image;

    /** This methods creates TerpPaint object to show all images that are created by features in TerpPaint class.
     */
    TerpPaint Parent;

    /** A counter variable for which picture in the slide show array. It is initially set to -1
     */
    int i = -1;

    /** This is of class File which contains the files in the current directory to
     * show a slide show on.
     */
    File files_in_directory;

    /** This string array creates a new string of size zero to store the list of all files
     * in the chosen directory. A primary purpose of this is to recognize the
     * file type extension.
     */
    String[] list = new String[0];

    /**
     * This vector only contains all images of the chosen directory. Any non image type files are
     * not added to list vector.
     */
    Vector list2 = new Vector();

    /**
     *	Describes the file name.
     */
    String filename;


    /** Constructs a SlidShow object. In constructor, it determines the type of all files
     * in the chosen directory. Only image files are added to the vector list to be shown
     * on a slideshow screen.
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     * @param parent Used to set the main screen not visilbe. Once a slideshow starts, Terp Paint main program screen is not visible.
     * @param modal Boolean variable.
     */
    public SlideShow(java.awt.Frame parent, boolean modal) {

	JFileChooser chooser = new JFileChooser();
	chooser.setDialogType( 2 );
//	chooser.setDialogTitle("Choose a folder");
	chooser.setDialogTitle("フォルダを選択してください");
	chooser.setFileSelectionMode( 1 );
	chooser.showDialog(this, "Ok" );

	//if( chooser.isDirectorySelectionEnabled() )
	//    System.out.print("AHOHSIHRIOHWRIOHWRI");

	File chosen = chooser.getSelectedFile();

	try
	{
	    filename = chosen.getAbsolutePath();
	    //System.out.println("filename" + filename);
	    String filename2 = chosen.getPath();
	    //System.out.println("filename2" + filename2);
	}
	catch(Exception e ){}

	main_image = new BufferedImage(5,5,5);

	try{
//		MediaTracker mt = new MediaTracker(this);
//		mt.addImage(main_image,0);
//		mt.waitForID(0);
		files_in_directory = new File(filename);
		list = files_in_directory.list();

		for( int j = 0 ; j < list.length; j++ )
		{
		    if( list[j].endsWith( ".jpg") ||
			list[j].endsWith( ".dib") ||
			list[j].endsWith( ".bmp") ||
			list[j].endsWith( ".gif") ||
			list[j].endsWith( ".jpeg") ||
			list[j].endsWith( ".JPG") ||
			list[j].endsWith( ".DIB") ||
			list[j].endsWith( ".BMP") ||
			list[j].endsWith( ".GIF") ||
			list[j].endsWith( ".JPEG") )
		    {
			list2.add( list[j] );
		    }
		}
		/*
		for( int k = 0; k < list2.size(); k++ )
		    System.out.println((String) list2.elementAt(k) );
		*/
       }catch(Exception e){}

       Parent = (TerpPaint)parent;
       Parent.setVisible( false);

       setVisible(true);
       initComponents();

       //System.out.print("after init");

       Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
       this.setSize((int)(screenSize.getWidth()),(int)(screenSize.getHeight()));
	//list2.removeElementAt(0) ;
    }

    /** This function returns the main image which is of type BufferedImage.
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     * @return This returns a BufferedImage.
     */
    public BufferedImage getImage(){
	return main_image;
    }

    /** This method diplays image files at the center of the a slide show screen.
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     * @param g Image graphics to be shown.
     */
    public void paint(Graphics g) {
	BufferedImage im = main_image;
	AffineTransform t = new AffineTransform();

	int x = (this.getWidth() - im.getWidth())/2;
	int y = (this.getHeight() - im.getHeight())/2;
	Graphics2D g2d = (Graphics2D)g;
	//g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	//g2d.setBackground(Color.black);
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	double scalex  = (double)screenSize.getWidth() / (double)im.getWidth();
	System.out.println(scalex+" "+(double)screenSize.getWidth()+" "+(double)im.getWidth());
	double scaley =	 (double)screenSize.getHeight() / (double)im.getHeight();

	double centerX = (double)screenSize.getWidth() / 2 ;
	double centerY = (double)screenSize.getHeight() / 2 ;


	t.setToTranslation( (int)(centerX - ( im.getWidth( null ) / 2 ) ) , (int)(centerY - ( im.getHeight( null ) / 2 )  )) ;

	if( (double)im.getHeight() != 5.0 ) {
	    g2d.drawImage(im,t,null);
	}
	im.flush();
    }

    /** This method is called in SlideShow constructor to initialize the slideshow.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     * This initializes all the components in the current window.
     */
    public void initComponents() {

	addKeyListener(new java.awt.event.KeyAdapter(){
		public void keyPressed(java.awt.event.KeyEvent evt) {
		    formKeyPressed(evt);
		}
	});

	addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mouseClicked(java.awt.event.MouseEvent evt) {
		formMouseClicked(evt);
	    }
	});

	addWindowListener(new java.awt.event.WindowAdapter() {
	    public void windowClosing(java.awt.event.WindowEvent evt) {
		closeDialog(evt);
	    }
	});

	pack();
    }

    /** This method is used to exit slideshow screen anytime the user wants to finish it by
     * hitting escape key.
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     * @param evt Indicator of the escape key.
     */
    public void formKeyPressed(java.awt.event.KeyEvent evt) {

	System.out.println("HmmWTF??!?!!?");
	if(( evt.getKeyCode() == 27 )) {
	    System.out.println("OMG ESCAPE!!") ;
	    setVisible(false);
	    Parent.setVisible(true);
	    dispose();
	}
    }

    /** This method figures out which mouse event was clicked and then shows the
     * appropriate image in the queue.
     *
     * There are three states in play.	One is on the current pictures, one is
     * the next image and the last one is of the previous image.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     * @param evt This parameter contains which mouse button was clicked.
     */
    public void formMouseClicked(java.awt.event.MouseEvent evt) {
	//System.out.println("getbutton " +evt.getButton());

	if((evt.getButton() == 1 ) && evt.getClickCount()==1){
	    //System.out.println("mouse 1");
	    //if((evt.getModifiers() & evt.BUTTON1_MASK) == evt.BUTTON1_MASK){

	    if(this.nextImage() == false){
		setVisible(false);
		Parent.setVisible(true);
		dispose();
	    }
	}

	else if ((evt.getButton()==3 && evt.getClickCount()==2)){
	    //System.out.println("mouse 3");
	    setVisible(false);
	    Parent.setVisible(true);
	    dispose();
	}
	else if ((evt.getButton() == 3)){
	    //System.out.println("moust 2");
	    this.previousImage();
	}

    }

    /** This closes the slideshow automatically when finishing showing all images at the end.
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     * @param evt Indicator of the end of images shown.
     */
    public void closeDialog(java.awt.event.WindowEvent evt) {
	setVisible(false);
	Parent.setVisible(true);
	dispose();
    }

    /** Shows the next image continuously by mouse clicking.
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     * @return Returns true or false for the next image.
     */
    public boolean nextImage(){

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	if(i < list2.size()-1){
	    try{
		i++;

		// Showing jpg images
		 if(((String) list2.elementAt(i)).endsWith( "g") ||((String) list2.elementAt(i)).endsWith( "G") ) // jpgs
		 {

		    JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(new FileInputStream(filename+"/"+ ((String) list2.elementAt(i) )));
		    main_image = decoder.decodeAsBufferedImage();
		 }

		// Showing gif images
		 else if (((String) list2.elementAt(i)).endsWith( "f") || ((String) list2.elementAt(i)).endsWith( "F")) // gifs
		 {
		    ImageIcon icon = new ImageIcon(filename+"/"+((String) list2.elementAt(i) ));
		    Image image = icon.getImage();
		    main_image = new BufferedImage( image.getWidth( null ), image.getHeight( null ), BufferedImage.TYPE_INT_RGB);
		    Graphics g = main_image.getGraphics();
		    g.drawImage(image, 0,0, null) ;
		    // g.drawImage(image, (int)(centerX - ( image.getWidth( null ) / 2 ) ) , (int)(centerY - ( image.getHeight( null ) / 2 )  ),
		    // (int)(image.getWidth(null)), (int)(image.getHeight(null)), Color.black, null );
		 }

		// Showing bmp images
		 else
		 {
		    FileInputStream fis = new FileInputStream( filename+"/"+ (String) list2.elementAt( i) );
		    converter newImage = new converter();
		    main_image = newImage.FileToBufferedImage( fis );
		 }

	     }catch(Exception e){}

	     setBackground(Color.black) ;

	     repaint() ;
	    // repaint(200,200 , main_image.getWidth(), main_image.getHeight() ) ;

	    //repaint((int)(centerX - ( main_image.getWidth( null ) / 2 ) ) , (int)(centerY - ( main_image.getHeight( null ) / 2 )) , main_image.getWidth(), main_image.getHeight() ) ;

	    //setLocation(800,600);
	    //repaint();

	     return true;
	  }
	  else{
	    return false;
	  }
    }

    /** Shows the previous image by mouse clicking.
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     * @return Returns true or false for the previous image.
     */
    public boolean previousImage(){

	System.out.println("in previous image");

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	if(i > 0){
	    try{
		i--;

		// Showing jpg images
		 if(((String) list2.elementAt(i)).endsWith( "g") || ((String) list2.elementAt(i)).endsWith( "G") ) // jpgs
		 {

			JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(new FileInputStream(filename+"/"+((String) list2.elementAt(i) )));
			main_image = decoder.decodeAsBufferedImage();
		  }

		// Showing gif images
		  else if (((String) list2.elementAt(i)).endsWith( "f") || ((String) list2.elementAt(i)).endsWith( "F") ) // gifs
		  {
		       ImageIcon icon = new ImageIcon(filename+"/"+((String) list2.elementAt(i) ));
		       Image image = icon.getImage();
		       main_image = new BufferedImage( image.getWidth( null ), image.getHeight( null ), BufferedImage.TYPE_INT_RGB);
		       Graphics g = main_image.getGraphics();

		       g.drawImage(image, 0, 0, null) ;
		       //g.drawImage(image, (int)(centerX - ( image.getWidth( null ) / 2 ) ) , (int)(centerY - ( image.getHeight( null ) / 2 )	),
		       //int)(image.getWidth(null)), (int)(image.getHeight(null)), Color.black,null  );
		  }

		  // Showing bmp images
		   else
		   {
			FileInputStream fis = new FileInputStream( filename+"/"+ (String) list2.elementAt( i) );
			converter newImage = new converter();
			main_image = newImage.FileToBufferedImage( fis );
		    }
	    }catch(Exception e){}
	     repaint();
	     return true;
	}
	else{
	    return false;
	}
    }

    /** This method creates a delay for each picture while it scrolls through
     * the entire presentation.	 It will call nextImage and see if it is true.
     * Until it is false, it will keep displaying the next image.
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     */
    public void play(){
	try{
		//while(nextImage() == true){
		//	Thread.sleep(10*1000);
		//}
	}
	catch(Exception e){}
    }

    /** This is the main which should create an array of pictures in the presentation.
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     * @param args These are the command line arguments
     */
    public static void main(String args[]) {
	//new viewBitmap(new javax.swing.JFrame(), true).show();
    }
}

