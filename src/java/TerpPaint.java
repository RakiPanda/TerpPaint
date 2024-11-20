/*
 * TerpPaint.java
 *
 * Created on March 20, 2002, 5:43 PM
 */


/**
 *
 * @author Terp Paint
 * @version 2.0
 */
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/** This class basically controls every aspect of TerpPaint.  It controls
 * the drawing, position, image, paint, and text.
 * The state is enable.
 * OS is windows.
 * Implementation variances is allowed.
 * There are no security constraints.
 * There are no external secifications.
 */

public class TerpPaint extends javax.swing.JFrame {
    /**garbage collection thread.*/
    GarbageCollect gc;
    
    //added
    /** This is for the Layer Box.
     */
    public AllLayers alllayers;
    /** This is the current state of preferences.
     */
    public static prefsState myPrefs;

    //SplashWindow sw;
     /** java.awt.event.*
     */
    /** action_list
     */
    // ming 4.26
    /** This is the list indicating action history (actions that can be undone now) for every layer separately.
     */
    //ming 4.26 end
    public LinkedList action_list = new LinkedList();

    /** java.awt.event.*
     */
    /** redo_list
     */
    // ming 4.26
    /** This is the list indicating current undone history (actions that can be redone now) for every layer separately.
     */
    // ming 4.26 end
    public LinkedList redo_list = new LinkedList();
    //ming ends

    /** java.awt.event.*
     */
    /** active
     */
    /** This is a boolean value that is initialize to true, used in presentation() which is never called.
     */

    public static boolean active = true;



    /** java.awt.event.*
     */
    /** mt
     */
    /** This is a MediaTracker value, used in presentation() which is never called.
     */

    public static MediaTracker mt;

    /** java.awt.event.*
     */
    /** splashImBuff
     */
    /** This is a BufferedImage used for the splash screen in presentation() which is never called.
     *
     */

    public static BufferedImage splashImBuff;

    /** java.awt.Image.*
     */
    /** splashIm
     */
    /** This is a Image value.
     */

    public static Image splashIm;

    /** java.awt.event.*
     */
    /** resize
     */
    /** This is a boolean value that is initialize to false, indicates whether cursor is in the resize area and should change to resizing arrows.
     *
     */

    public boolean resize = false;



    /** java.awt.event.*
     */
    /** standalone
     */
    /** this is a boolean value
     */

    //public boolean standalone;

    /** java.awt.event.*
     */
    /** center
     */
    /** This is a main_canvas value, which holds the painting.
     */

    public main_canvas center;

    /** java.awt.event.*
     */
    /** grayscaleFlag
     */
    /** this is a int value that is initialize to 0, is 1 when grayscale is selected.
     */

    public static int grayscaleFlag = 0;

    /** java.awt.event.*
     */
    /** CHANGED
     */
    /** This is a boolean value that is initialize to false, never used.
     */

    public boolean CHANGED = false;

    /** java.awt.event.*
     */
    /** CURRENT_FILE
     */
    /** This is a string value that is initialize to "untitled", and is the name used in the title of window.
     */

    public String CURRENT_FILE = "UNTITLED";

    /** java.awt.event.*
     */
    /** theSound
     */
    /** This is a AudioClip value, not used.
     */

    public AudioClip theSound;

    /** java.awt.event.*
     */
    /** sw
     */
    /** This is a SplashWindow value for the splashscreen.
     */

    public static SplashWindow sw;

    /** java.awt.event.*
     */
    /** tk
     */
    /** This is a Toolkit value that is initialize by the function
     * getDefaultToolkit().
     */

    Toolkit tk = Toolkit.getDefaultToolkit();

    /** java.awt.event.*
     */
    /** pencilImg
     */
    /** This is an Image value that is initialize by getImage(), for cursor.
     */

    Image pencilImg = tk.getImage(getClass().getResource("/images/pencil.gif"));


    /** java.awt.event.*
     */
    /** bucketImg
     */
    /** This is an Image value that is initialize by getImage(), for cursor.
     */

    Image bucketImg = tk.getImage(getClass().getResource("/images/bucket.gif"));

    /** java.awt.event.*
     */
    /** dropperImg
     */
    /** This is an Image value that is initialize by getImage(), for cursor.
     */

    Image dropperImg = tk.getImage(getClass().getResource("/images/dropper.gif"));

    /** java.awt.event.*
     */
    /** eraserImg
     */
    /** This is an Image value that is initialize by getImage(), for cursor.
     */

     Image eraserImg = tk.getImage(getClass().getResource("/images/eraserSmall.gif"));

     /** This is an Image value that is initialize by getImage(), for cursor.
     */

    Image eraserImg2 = tk.getImage(getClass().getResource("/images/eraserSmall.gif"));

     /** This is an Image value that is initialize by getImage(), for cursor.
     */

    Image eraserImg3 = tk.getImage(getClass().getResource("/images/eraserSmall.gif"));

     /** This is an Image value that is initialize by getImage(), for cursor.
     */

    Image eraserImg4 = tk.getImage(getClass().getResource("/images/eraserSmall.gif"));


     /** image of eraser cursor in normal zoom.
     */
    Image eraserNormalImg = tk.getImage(getClass().getResource("/images/erasernormal.gif"));

    /** java.awt.event.*
     */
    /** zoomImg
     */
    /** This is an Image value that is initialize by getImage() for cursor.
     */

    Image zoomImg = tk.getImage(getClass().getResource("/images/zoom.gif"));

    /** java.awt.event.*
     */
    /** airbrushImg
     */
    /** This is an Image value that is initialize by getImage() for cursor.
     */

    Image airbrushImg = tk.getImage(getClass().getResource("/images/airbrush.gif"));

    /** java.awt.event.*
     */
    /** lassoImg
     */
    /** This is an Image value that is initialize by getImage() for cursor.
     */

    Image lassoImg = tk.getImage(getClass().getResource("/images/lasso.gif"));

    /** java.awt.event.*
     */
    /** brushImg
     */
    /** This is an Image value that is initialize by getImage() for cursor.
     */

    Image brushImg = tk.getImage(getClass().getResource("/images/brush.gif"));

    /** java.awt.event.*
     */
    /** defaultCursor
     */
    /** This is a Cursor value that is created by the constructor.
     */

    Cursor defaultCursor = new Cursor(Cursor.CROSSHAIR_CURSOR);

    /** java.awt.event.*
     */
    /** currentCursor
     */
    /** This is a Cursor value that is initialize defaultCursor.
     */

    Cursor currentCursor = defaultCursor;

    /** java.awt.event.*
     */
    /** MacFlag
     */
    /** This is a int value that is initialize to 0, = 1 when a mac computer.
     */

    public int MacFlag = 0;

    /** The x displacement of initial window opening, where 0 is the left most of screen.
     */
    public int locationWidth;
    /** The y displacement of initial window opening, where 0 is the topmost of screen.
     */
    public int locationHeight;
    /** The width of the TerpPaint window.
     */
    public int sizeWidth;
    /** The height of the TerpPaint window.
     */
    public int sizeHeight;

    /** The width of image in the clipboard, initialized to 0.
     */
    public int clipBoardWidth = 0;
    /** The height of the image in the clipboard, initialized to 0.
     */
    public int clipBoardHeight = 0;

    /** This boolean is true when some thing has been copied into the clipboard.
     */
    public boolean nonEmptyClipboard = false;

    /** This is a vector containing the 4 most recent Files.
     */
    public Vector recentFiles;

    /** The JMenuItem that corresponds to opening the first recent file.
     */
    public javax.swing.JMenuItem FileName1 = null;
    /** The JMenuItem that corresponds to opening the second recent file.
     */
    public javax.swing.JMenuItem FileName2 = null;
    /** The JMenuItem that corresponds to opening the third recent file.
     */
    public javax.swing.JMenuItem FileName3 = null;
    /** The JMenuItem that corresponds to opening the fourth recent file.
     */
    public javax.swing.JMenuItem FileName4 = null;
//Istvan
	/** This is the TerpPaintContainer that contains this TerpPaint object.
	*
	*/

    public TerpPaintContainer myContainer;
    /** This is the constructor of the TerpPaint class. It creates a new form TerpPaint. First, it display a picture showing who the authors who
     * create TerpPaint are. Then, it initializes the components need and then set up the board for drawing. It then creates the back up
     * thread and then, initializes the tools and borders. It should work with all operating systems and hardware. There are no variances
     * and no security constraints.
     * @param alone a boolean value.
     * @param inContainer a TerpPaintContainer object
     */
    public TerpPaint(boolean alone,TerpPaintContainer inContainer) {
		myContainer=inContainer;
		int tbx=1,cbx=1,sbx=1,lbx=1;
	if(System.getProperty("os.name").startsWith("Mac") == true){
	    MacFlag = 1;
	}
	if(alone){
	    splash();
	}
		int imageSizeWidth=500;
		int imageSizeHeight=500;
		
		
	try
	{
	    FileReader in = new FileReader( "windowRegistry.txt" );
	    BufferedReader d= new BufferedReader( in );
	    locationWidth = Integer.parseInt( d.readLine() );
	    locationHeight = Integer.parseInt(d.readLine() );
	    sizeWidth = Integer.parseInt(d.readLine());
	    sizeHeight = Integer.parseInt(d.readLine() );
	    imageSizeWidth = Integer.parseInt(d.readLine());
	    imageSizeHeight = Integer.parseInt(d.readLine() );
	    tbx = Integer.parseInt(d.readLine() );
	    cbx = Integer.parseInt(d.readLine() );
	    sbx = Integer.parseInt(d.readLine() );
	    lbx = Integer.parseInt(d.readLine() );
	    
	    
		System.out.println("wi "+sizeWidth + " he " + sizeHeight );
		System.out.println("lw "+locationWidth + " lh " + locationHeight );
		System.out.println("Toolbox :" + tbx + "Colorbox :" + cbx + "Statusbox :" + sbx + "Layerbox :" + lbx);
	    in.close();
	}
	catch( Exception e ) {}

	if( locationWidth == -1 ){

	    this.setSize(600,500);
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    double xMargin = (screenSize.getWidth()- this.getWidth()) / 2;
	    double yMargin = (screenSize.getHeight() - this.getHeight()) / 2;
	    this.setLocation((int)xMargin,(int)yMargin);

	    locationWidth = 600;
	    locationHeight = 500;

	    sizeWidth = (int) this.getSize().getWidth() - 63;
	    sizeHeight = (int) this.getSize().getHeight() - 85;

	System.out.println("w "+sizeWidth + " h " + sizeHeight );
	}
	else {

	    this.setSize( sizeWidth,sizeHeight );
	    this.setLocation(locationWidth, locationHeight);
	}
		myPrefs=new prefsState();
		this.setSize( sizeWidth,sizeHeight );		
	initComponents();
	centerSetup();

    //Begin backup thread
    //		AutoBackup a = new AutoBackup(this);
    ////// I'm making AutoBackup var belong to the class, so we can end the thread when we exit.

	a = new AutoBackup(this);
	a.setDaemon(true);
	a.start();
    //end of backup thread
    
    //Garbage Collection
    gc = new GarbageCollect();
    gc.setDaemon(true);
    gc.start();
    //end gc

	ourPrinter = new printer(this);

	toggleProperties();
    //	      line_properties.setVisible(true);

	pack();
	toolInits();
	borderInits();
	this.setSize( new Dimension( sizeWidth, sizeHeight) );
	this.setVisible(true);
		//Istvan phase 5
	//Cursor pencilCursor = tk.createCustomCursor(pencilImg,new Point(10,24),"Pencil Cursor");

	currentCursor = defaultCursor;
		//End Istvan phase 5


	colorTable.add( Color.red );
	colorTable.add( Color.green );
	colorTable.add( Color.blue );
	colorTable.add( Color.yellow );
	colorTable.add( Color.magenta );
	colorTable.add( Color.cyan );
	colorTable.add( Color.pink );
	colorTable.add( Color.white );
	colorTable.add( Color.lightGray );
	colorTable.add( Color.gray );
	colorTable.add( Color.darkGray );
	colorTable.add( Color.black );
	colorTable.add( new java.awt.Color(143, 0, 0) );
	colorTable.add( Color.orange );
	colorTable.add( Color.black );	  //left
	colorTable.add( Color.white );	  //right
	//new color add
		colorTable.add( new java.awt.Color(20, 20, 20) );
		colorTable.add( new java.awt.Color(40, 40, 40) );
		colorTable.add( new java.awt.Color(60, 60, 60) );
		colorTable.add( new java.awt.Color(80, 80, 80) );
		colorTable.add( new java.awt.Color(100, 100, 100) );
		colorTable.add( new java.awt.Color(120, 120, 120) );
		colorTable.add( new java.awt.Color(140, 140, 140) );
		colorTable.add( new java.awt.Color(160, 160, 160) );
		//new color added
	center.resizeImage( imageSizeWidth, imageSizeHeight );
	center.setZoom(1.0);
	center.repaint();

	//center.noChanges();



	System.out.println("after setCanvasSize() ");
	alllayers = new AllLayers(this, false, center);
	if(lbx == 1)
	{
		LayerBox.setSelected(true);
		alllayers.setVisible(true);
		System.out.println("Opening Layer Box....");
	}
	else
	{
		LayerBox.setSelected(false);
		alllayers.setVisible(false);
		System.out.println("Closing Layer Box....");
	}
	if(tbx == 1)
	{
		ToolBox.setSelected(true);
		shapes.setVisible(true);
		System.out.println("Opening Tool Box....");
	}
	else
	{
		ToolBox.setSelected(false);
		shapes.setVisible(false);
		System.out.println("Closing Tool Box....");
	}
	if(cbx == 1)
	{
		ColorBox.setSelected(true);
		color_menu.setVisible(true);
		current_colors.setVisible(true);
		System.out.println("Opening Color Box....");
	}
	else
	{
		ColorBox.setSelected(false);
		color_menu.setVisible(false);
		current_colors.setVisible(false);
		System.out.println("Closing Color Box....");
	}
	if(sbx == 1)
	{
		StatusBox.setSelected(true);
		status.setVisible(true);
		System.out.println("Opening Status Box....");
	}
	else
	{
		StatusBox.setSelected(false);
		status.setVisible(false);
		System.out.println("Closing Status Box....");
	}
	
	
       // center.addLayer();
    }

    /** This Method is called whenever an action is performed, it updates the Undo List with a new action. 
    */
    public void updateUndoList() {
	/*UndoList = new javax.swing.JMenu();
	UndoList.setText("Undo list");
	Edit.add(UndoList);
	*/

     /*	  int temp1 = action_list.size();
	if (action_list.get(temp1-2).equals(action_list.get(temp1-1))
	    && action_list.get(temp1-2).equals("Polygon"))
	action_list.removeLast();
       */
	UndoList.removeAll();

	    // ming 4.22
	//int max_undo_num = 20;//myPrefs.UndoLevel;
	int max_undo_num = myPrefs.UndoLevel;
	// ming 4.22 end

	// ming 4.26
	LinkedList cur_action_list = (LinkedList)(action_list.get(center.currentLayer));

	int cur_size = cur_action_list.size();
	if (cur_size>max_undo_num)
	    for (int j=0; j<cur_size-max_undo_num; j++)
		cur_action_list.removeFirst();
	// ming 4.12 end

	for (int i=0; i<cur_action_list.size(); i++)
	//for (int i=0; i<action_list.size()-center.place; i++)
	{
	    final int undo_index = i;
	    javax.swing.JMenuItem temp = new javax.swing.JMenuItem();
	    temp.setText(new Integer(i+1).toString() + ": " + cur_action_list.get(i).toString());
	    UndoList_items.add(temp);
	    UndoList.add(temp);
	    temp.addActionListener(new java.awt.event.ActionListener() {
		//public final int undo_index = i;
		public void actionPerformed(java.awt.event.ActionEvent evt) {
		    UndoListActionPerformed(evt, undo_index);
		}
	    });
	}
	// ming 4.26 end
    }
    /* ming */

    /** This method is called from within the constructor to
     * initialize the form.
     *
     */
    public void initComponents() {
	menu_container = new javax.swing.JMenuBar();

	File1 = new javax.swing.JMenu();
	New = new javax.swing.JMenuItem();
	System.out.println(System.getProperty("os.version"));
	Open = new javax.swing.JMenuItem();
	Save = new javax.swing.JMenuItem();
	SaveAs = new javax.swing.JMenuItem();
	jSeparator1 = new javax.swing.JSeparator();
	PrintPreview = new javax.swing.JMenuItem();
	PageSetup = new javax.swing.JMenuItem();
	Print = new javax.swing.JMenuItem();
	jSeparator7 = new javax.swing.JSeparator();
	Recent = new javax.swing.JMenuItem();
	jSeparator2 = new javax.swing.JSeparator();
	Exit = new javax.swing.JMenuItem();
	Close = new javax.swing.JMenuItem();
	//	  Send = new javax.swing.JMenuItem();
	Edit = new javax.swing.JMenu();
	Undo = new javax.swing.JMenuItem();
	//ming
	UndoList = new javax.swing.JMenu();
	Repeat = new javax.swing.JMenuItem();
	jSeparator3 = new javax.swing.JSeparator();
	Cut = new javax.swing.JMenuItem();
	Copy = new javax.swing.JMenuItem();
	Paste = new javax.swing.JMenuItem();
	ClearSelection = new javax.swing.JMenuItem();
	SelectAll = new javax.swing.JMenuItem();
	Import = new javax.swing.JMenuItem();
	Export = new javax.swing.JMenuItem();
	//added
	MyPreferences = new javax.swing.JMenuItem();
	jSeparator8 = new javax.swing.JSeparator();
	jSeparator4 = new javax.swing.JSeparator();
	View = new javax.swing.JMenu();
	ToolBox = new javax.swing.JCheckBoxMenuItem();
	ColorBox = new javax.swing.JCheckBoxMenuItem();
	StatusBox = new javax.swing.JCheckBoxMenuItem();
	//VERSION 3
	LayerBox = new javax.swing.JCheckBoxMenuItem();
	jSeparator5 = new javax.swing.JSeparator();
	Zoom = new javax.swing.JMenu();
	Normal = new javax.swing.JMenuItem();
	Large = new javax.swing.JMenuItem();
	jSeparator6 = new javax.swing.JSeparator();
	ViewBitmap = new javax.swing.JMenuItem();
	Image = new javax.swing.JMenu();
	// ming 4.24
	Filter = new javax.swing.JMenu();
	// ming 4.24 end
	FlipRotate = new javax.swing.JMenuItem();
	StretchSkew = new javax.swing.JMenuItem();
	/* ming */
	Brightness = new javax.swing.JMenuItem();
	Blur = new javax.swing.JMenuItem();
	Sharpen = new javax.swing.JMenuItem();
	Emboss = new javax.swing.JMenuItem();
	Edge = new javax.swing.JMenuItem();
	/* ming */
	InvertColors = new javax.swing.JMenuItem();
	SlideShow = new javax.swing.JMenuItem();
	// Ronald
	Animation = new javax.swing.JMenuItem();
	Attributes = new javax.swing.JMenuItem();
	ClearImage = new javax.swing.JMenuItem();
	DrawOpaque = new javax.swing.JCheckBoxMenuItem();
	Colors = new javax.swing.JMenu();
	EditColors = new javax.swing.JMenuItem();


	//MENU FOR LAYERS  #VERSION 3#
	jSeparator9 = new javax.swing.JSeparator();
	Layer = new javax.swing.JMenu();
	Add = new javax.swing.JMenuItem();
	Remove = new javax.swing.JMenuItem();
	Flatten = new javax.swing.JMenuItem();

	//TOOLBOX FOR LAYERS  #VERSION 3#











	Help = new javax.swing.JMenu();
	HelpTopics = new javax.swing.JMenuItem();
	About = new javax.swing.JMenuItem();

	west = new javax.swing.JPanel();
	shapes = new javax.swing.JPanel();
	line = new javax.swing.JButton();
	curve = new javax.swing.JButton();
	rounded_rectangle = new javax.swing.JButton();
	square = new javax.swing.JButton();
	polygon = new javax.swing.JButton();
	elipse = new javax.swing.JButton();
	selectall = new javax.swing.JButton();
	eraser = new javax.swing.JButton();
	paint = new javax.swing.JButton();
	medicine = new javax.swing.JButton();
	zoom = new javax.swing.JButton();
	pencil = new javax.swing.JButton();
	brush = new javax.swing.JButton();
	spray = new javax.swing.JButton();
	letter = new javax.swing.JButton();
	select = new javax.swing.JButton();
	//added
	magicSelect = new javax.swing.JButton();
	//end added
	properties = new javax.swing.JPanel();
	line_properties = new javax.swing.JPanel();
	line1 = new javax.swing.JButton();
	line2 = new javax.swing.JButton();
	line3 = new javax.swing.JButton();
	line4 = new javax.swing.JButton();
	line5 = new javax.swing.JButton();
	rect_properties = new javax.swing.JPanel();
	one = new javax.swing.JButton();
	two = new javax.swing.JButton();
	three = new javax.swing.JButton();
	spray_properties = new javax.swing.JPanel();
	spray1 = new javax.swing.JButton();
	spray2 = new javax.swing.JButton();
	brush_properties = new javax.swing.JPanel();
	dot3 = new javax.swing.JButton();
	dot2 = new javax.swing.JButton();
	dot1 = new javax.swing.JButton();
	square3 = new javax.swing.JButton();
	spray3 = new javax.swing.JButton();
	square2 = new javax.swing.JButton();
	square1 = new javax.swing.JButton();
	right3 = new javax.swing.JButton();
	right2 = new javax.swing.JButton();
	right1 = new javax.swing.JButton();
	left3 = new javax.swing.JButton();
	left2 = new javax.swing.JButton();
	left1 = new javax.swing.JButton();
	zoom_properties = new javax.swing.JPanel();
	zoom1 = new javax.swing.JButton();
	zoom2 = new javax.swing.JButton();
	zoom3 = new javax.swing.JButton();
	zoom4 = new javax.swing.JButton();
	paint_properties = new javax.swing.JPanel();
	paint1 = new javax.swing.JButton();
	paint2 = new javax.swing.JButton();
	paint3 = new javax.swing.JButton();
	paint4 = new javax.swing.JButton();
	none = new javax.swing.JPanel();
	nothing = new javax.swing.JButton();
	south = new javax.swing.JPanel();
	colors = new javax.swing.JPanel();
	color_menu = new javax.swing.JPanel();

	red = new javax.swing.JButton();
	green = new javax.swing.JButton();
	blue = new javax.swing.JButton();
	yellow = new javax.swing.JButton();
	magenta = new javax.swing.JButton();
	cyan = new javax.swing.JButton();
	pink = new javax.swing.JButton();
	white = new javax.swing.JButton();
	lgt_gray = new javax.swing.JButton();
	gray = new javax.swing.JButton();
	drk_gray = new javax.swing.JButton();
	black = new javax.swing.JButton();

	brown = new javax.swing.JButton();
	orange = new javax.swing.JButton();
	//new color add
	color1 = new javax.swing.JButton();
	color2 = new javax.swing.JButton();
	color3 = new javax.swing.JButton();
	color4 = new javax.swing.JButton();
	color5 = new javax.swing.JButton();
	color6 = new javax.swing.JButton();
	color7 = new javax.swing.JButton();
	//new color added
	current_colors = new javax.swing.JPanel();
	right = new javax.swing.JButton();
	left = new javax.swing.JButton();
	status = new javax.swing.JPanel();
	position = new javax.swing.JPanel();
	cordinates = new javax.swing.JLabel();


	File1.setText("File");
	File1.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		File1ActionPerformed(evt);
	    }
	});

	New.setText("New");
	New.setMnemonic(KeyEvent.VK_N);
	New.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
	New.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		NewActionPerformed(evt);
	    }
	});
	File1.add(New);

	Open.setText("Open");
	Open.setMnemonic(KeyEvent.VK_O);
	Open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
	Open.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		OpenActionPerformed(evt);
	    }
	});
	File1.add(Open);

	Save.setText("Save");
	Save.setMnemonic(KeyEvent.VK_S);
	Save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
	Save.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		SaveActionPerformed(evt);
	    }
	});
	File1.add(Save);

	SaveAs.setText("Save As...");
	SaveAs.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		SaveAsActionPerformed(evt);
	    }
	});
	File1.add(SaveAs);
	// ming 4.22
	Close.setText("Close");
	Close.setMnemonic(KeyEvent.VK_F3);
	Close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, ActionEvent.ALT_MASK));
	Close.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		CloseActionPerformed(evt);
	    }
	});
	File1.add(Close);
	// ming 4.22 end

	File1.add(jSeparator1);

	PrintPreview.setText("Print Preview");
	PrintPreview.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		PrintPreviewActionPerformed(evt);
	    }
	});
	File1.add(PrintPreview);

	PageSetup.setText("Page Setup");
	PageSetup.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		PageSetupActionPerformed(evt);
	    }
	});
	File1.add(PageSetup);

	Print.setText("Print");
	Print.setMnemonic(KeyEvent.VK_P);
	Print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
	Print.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		PrintActionPerformed(evt);
	    }
	});
	File1.add(Print);


//	  Recent.setText("Recent File");
	/*center.addLayer();
	Recent.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		RecentActionPerformed(evt);
	    }
	});
	*/
//	  File1.add(Recent);
	// begin of recentcenter.addLayer(); files
	try
	{


	    FileReader in = new FileReader( "recentFiles.txt" );
	    BufferedReader d= new BufferedReader( in );

	    String fn1 = d.readLine();
	    String fn2 = d.readLine();
	    String fn3 = d.readLine();
	    String fn4 = d.readLine();

	    in.close();
	    recentFiles = new Vector();

	    String negativeOne = "-1";

	    if( fn1.compareTo( negativeOne ) != 0 )
	    {

		File1.add( jSeparator7 );

		FileName1 = new javax.swing.JMenuItem();

		int length = fn1.length();
		if( length < 27 )
		{
		    FileName1.setText( "1  " +	fn1 );
		}
		else
		{
		    FileName1.setText( "1  " + fn1.substring(0, 9) + " ... " + fn1.substring( length-15, length ) );
		}
		File f = new File( fn1 );
		recentFiles.add( f );

		FileName1.addActionListener(new java.awt.event.ActionListener() {
		     public void actionPerformed(java.awt.event.ActionEvent evt) {
			 FileNameActionPerformed(evt, 1);
		     }
		});

		File1.add( FileName1 );

	    }

	    if( fn2.compareTo( negativeOne ) != 0 )
	    {
		FileName2 = new javax.swing.JMenuItem();

		int length = fn2.length();
		if( length < 27 )
		{
		    FileName2.setText( "2  " + fn2 );
		}
		else
		{
		    FileName2.setText( "2  " + fn2.substring(0, 9) + " ... " + fn2.substring( length-15, length ) );
		}
		File f = new File( fn2 );
		recentFiles.add( f );

		FileName2.addActionListener(new java.awt.event.ActionListener() {
		     public void actionPerformed(java.awt.event.ActionEvent evt) {
			    FileNameActionPerformed(evt, 2);
		     }
		});

		File1.add( FileName2 );

	    }

	    if( fn3.compareTo( negativeOne ) != 0 )
	    {
		FileName3 = new javax.swing.JMenuItem();

		int length = fn3.length();
		if( length < 27 )
		{
		    FileName3.setText( "3  " + fn3 );
		}
		else
		{
		    FileName3.setText( "3  " + fn3.substring(0, 9) + " ... " + fn3.substring( length-15, length ) );
		}
		File f = new File( fn3 );
		recentFiles.add( f );

		FileName3.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
			FileNameActionPerformed(evt, 3);
		    }
		});

		File1.add( FileName3 );

	    }

	    if( fn4.compareTo( negativeOne ) != 0 )
	    {
		FileName4 = new javax.swing.JMenuItem();

		int length = fn4.length();
		if( length < 27 )
		{
		    FileName4.setText( "4  " + fn4 );
		}
		else
		{
		    FileName4.setText( "4  " + fn4.substring(0, 9)+ " ... " + fn4.substring( length-15, length ) );
		}
		File f = new File( fn4 );
		recentFiles.add( f );

		FileName4.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
			FileNameActionPerformed(evt, 4);
		    }
		});

		File1.add( FileName4 );

	    }

	 }
	 catch( Exception e ) {}
	// end of adding recent files

	File1.add(jSeparator2);
	// ming 4.22
/*		Close.setText("Close");
	Close.setMnemonic(KeyEvent.VK_F3);
	Close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, ActionEvent.ALT_MASK));
	Close.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		CloseActionPerformed(evt);
	    }
	});
	File1.add(Close);*/
	// ming 4.22 end


	Exit.setText("Exit");
	Exit.setMnemonic(KeyEvent.VK_F4);
	Exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
	Exit.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		ExitActionPerformed(evt);
	    }
	});
	File1.add(Exit);

	/*
	Send.setText("Send");
	Send.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		SendActionPerformed(evt);
	    }
	});
	File1.add(Send);
	*/
	menu_container.add(File1);


	Edit.setText("編集");
	Edit.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		EditActionPerformed(evt);
	    }
	});

	Undo.setText("Undo");
	Undo.setMnemonic(KeyEvent.VK_Z);
	Undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
	Undo.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		UndoActionPerformed(evt);

	    }
	});

	Edit.add(Undo);
	 // ming 5.2
	UndoList.setText("Action History to Undo");
	// ming 5.2 end
	//Undo.setMnemonic(KeyEvent.VK_Z);
	//Undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
	/*UndoList.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		UndoListActionPerformed(evt);

	    }
	});*/
	Edit.add(UndoList);
	// ming
	Repeat.setText("Redo");
	Repeat.setMnemonic(KeyEvent.VK_Y);
	Repeat.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
	Repeat.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		RepeatActionPerformed(evt);
	    }
	});
	Edit.add(Repeat);
	Edit.add(jSeparator3);

	Cut.setText("Cut");
	Cut.setMnemonic(KeyEvent.VK_X);
	Cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
	Cut.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		CutActionPerformed(evt);
	    }
	});
	Edit.add(Cut);

	Copy.setText("Copy");
	Copy.setMnemonic(KeyEvent.VK_C);
	Copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
	Copy.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		CopyActionPerformed(evt);
	    }
	});

	Edit.add(Copy);

	Paste.setText("Paste");
	Paste.setMnemonic(KeyEvent.VK_V);
	Paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
	Paste.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		PasteActionPerformed(evt);
	    }
	});
	Edit.add(Paste);

	ClearSelection.setText("Clear Selection");
	ClearSelection.setMnemonic(KeyEvent.VK_DELETE);
	ClearSelection.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0));
	ClearSelection.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent evt) {
		ClearSelectionActionPerformed(evt);
	    }
	});
	Edit.add(ClearSelection);

	SelectAll.setText("Select All");
	SelectAll.setMnemonic(KeyEvent.VK_A);
	SelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
	SelectAll.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent evt) {
		SelectionAllActionPerformed(evt);
	    }
	});
	Edit.add(SelectAll);
	Edit.add(jSeparator8);

	Export.setText("Copy To");
	Export.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent evt) {
		ExportActionPerformed(evt);
	    }
	});
	Edit.add(Export);
//Istvan phase 5
	Import.setText("Import");
//End Istvan phase 5
	Import.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent evt) {
		ImportActionPerformed(evt);
	    }
	});
	Edit.add(Import);

	//added

		MyPreferences.setText("Preferences");
		MyPreferences.setMnemonic(KeyEvent.VK_J);
		MyPreferences.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, ActionEvent.CTRL_MASK));
		MyPreferences.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		       PreferencesActionPerformed(evt);
	    }
	});
	Edit.add(MyPreferences);
	//end added

	Layer.setText("Layer");
	Add.setText("Add Layer");
	Add.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		AddActionPerformed(evt);
	    }
	});
	Layer.add(Add);

	// ming 4.27
	Remove.setText("Remove Layer by Name");
	// ming 4.27 end
	Remove.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		RemoveActionPerformed(evt);
	    }
	});
	Layer.add(Remove);

	Flatten.setText("Flatten Layer");
	Flatten.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		FlattenActionPerformed(evt);
	    }
	});
	Layer.add(Flatten);
	Edit.add(jSeparator9);
	Edit.add(Layer);







	menu_container.add(Edit);

	View.setText("View");
	ToolBox.setText("Tool Box");
	ToolBox.setMnemonic(KeyEvent.VK_T);
	ToolBox.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
	ToolBox.setSelected(true);
	ToolBox.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		ToolBoxActionPerformed(evt);
	    }
	});
	View.add(ToolBox);

	ColorBox.setText("Color Box");
	ColorBox.setMnemonic(KeyEvent.VK_L);
	ColorBox.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
	ColorBox.setSelected(true);
	ColorBox.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		ColorBoxActionPerformed(evt);
	    }
	});
	View.add(ColorBox);

	StatusBox.setText("StatusBox");
	StatusBox.setSelected(true);
	StatusBox.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		StatusBoxActionPerformed(evt);
	    }
	});
	View.add(StatusBox);

	LayerBox.setText("LayerBox");
	LayerBox.setSelected(true);
	LayerBox.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		LayerBoxActionPerformed(evt);
	    }
	});
	View.add(LayerBox);
	View.add(jSeparator5);

	Zoom.setText("Zoom");
	Normal.setText("Normal");
	Normal.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		NormalActionPerformed(evt);
	    }
	});
	Zoom.add(Normal);

	Large.setText("Large");
	Large.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		LargeActionPerformed(evt);
	    }
	});
	Zoom.add(Large);

	View.add(Zoom);
	ViewBitmap.setText("View Bitmap");
	ViewBitmap.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		ViewBitmapActionPerformed(evt);
	    }
	});

	SlideShow.setText("Slide Show");
	SlideShow.setMnemonic(KeyEvent.VK_S);
	SlideShow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
	SlideShow.addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent evt){
		SlideShowActionPerformed(evt);
	    }
	});

	View.add(SlideShow);

	View.add(ViewBitmap);
	//Ronald
	Animation.setText("Animation");
	Animation.addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent evt){
		AnimationActionPerformed(evt);
	    }
	});

	View.add(Animation);
	//Ronald
	menu_container.add(View);

	Image.setText("Image");
	FlipRotate.setText("Flip/Rotate");
	FlipRotate.setMnemonic(KeyEvent.VK_R);
	FlipRotate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
	FlipRotate.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		FlipRotateActionPerformed(evt);
	    }
	});
	Image.add(FlipRotate);

	StretchSkew.setText("Stretch/Skew");
	StretchSkew.setMnemonic(KeyEvent.VK_W);
	StretchSkew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
	StretchSkew.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		StretchSkewActionPerformed(evt);
	    }
	});
	Image.add(StretchSkew);


	InvertColors.setText("Invert Colors");
	InvertColors.setMnemonic(KeyEvent.VK_I);
	InvertColors.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
	InvertColors.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		InvertColorsActionPerformed(evt);
	    }
	});
	Image.add(InvertColors);

	Attributes.setText("Attributes");
	Attributes.setMnemonic(KeyEvent.VK_E);
	Attributes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
	Attributes.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		AttributesActionPerformed(evt);
	    }
	});
	Image.add(Attributes);

	//ClearImage.setText("Clear Image   CTRL+SHIFT+N");
	//ClearImage.setMnemonic(KeyEvent.VK_N);
	//ClearImage.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK + ActionEvent.SHIFT_MASK));
	//	  ClearImage.addActionListener(new java.awt.event.ActionListener() {
	//	      public void actionPerformed(java.awt.event.ActionEvent evt) {
	//		  ClearImageActionPerformed(evt);
	//	      }
	//	  });

	//	  Image.add(ClearImage);

	DrawOpaque.setText("Draw Opaque");
	DrawOpaque.setSelected(true);
	DrawOpaque.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		DrawOpaqueActionPerformed(evt);
	    }
	});
	Image.add(DrawOpaque);
	menu_container.add(Image);

		// ming 4.24

	Brightness.setText("Brightness");
	Brightness.setMnemonic(KeyEvent.VK_B);
	Brightness.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
	Brightness.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		BrightnessActionPerformed(evt);
	    }
	});
	Filter.add(Brightness);

	Blur.setText("Blur");
	Blur.setMnemonic(KeyEvent.VK_L);
	Blur.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK));
	Blur.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		BlurActionPerformed(evt);
	    }
	});
	Filter.add(Blur);

	Sharpen.setText("Sharpen");
	Sharpen.setMnemonic(KeyEvent.VK_H);
	Sharpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
	Sharpen.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		SharpenActionPerformed(evt);
	    }
	});
	Filter.add(Sharpen);

	Emboss.setText("Emboss");
	Emboss.setMnemonic(KeyEvent.VK_M);
	Emboss.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
	Emboss.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		EmbossActionPerformed(evt);
	    }
	});
	Filter.add(Emboss);
	Edge.setText("Edge");
	Edge.setMnemonic(KeyEvent.VK_D);
	Edge.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
	Edge.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		EdgeActionPerformed(evt);
	    }
	});
	Filter.add(Edge);
	Filter.setText("Filter");
	menu_container.add(Filter);
	// ming 4.24 end


	Colors.setText("Colors");
	EditColors.setText("Edit Colors...");
	EditColors.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		EditColorsActionPerformed(evt, left);
	    }
	});
	Colors.add(EditColors);
	menu_container.add(Colors);

	Help.setText("Help");
	HelpTopics.setText("Help Topics");
	HelpTopics.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpTopicsActionPerformed(evt);
	    }
	});
	Help.add(HelpTopics);

	About.setText("About Terp Paint");
	About.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		AboutActionPerformed(evt);
	    }
	});
	Help.add(About);
	menu_container.add(Help);

	setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
	setTitle("TerpPaint - "+CURRENT_FILE);
	setBackground(Color.white);
	setForeground(Color.white);
	setName("frame");
	addWindowListener(new java.awt.event.WindowAdapter() {
	    public void windowClosing(java.awt.event.WindowEvent evt) {
		//TODO: saveChanges
		exitForm(evt);
	    }
	});

	west.setLayout(new java.awt.BorderLayout());
	west.setBackground(java.awt.Color.lightGray);
	// 500 is variablee
	west.setMinimumSize(new java.awt.Dimension(50, sizeHeight));
	west.setPreferredSize(new java.awt.Dimension(50, sizeHeight));
	west.setOpaque(false);
	shapes.setLayout(new java.awt.GridBagLayout());
	java.awt.GridBagConstraints gridBagConstraints1;

	line.setBackground(java.awt.Color.lightGray);
	line.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/line_16_16.gif")));
	line.setToolTipText("Line");
	line.setActionCommand("line");
	line.setMargin(new java.awt.Insets(0, 0, 0, 0));
	line.setMaximumSize(new java.awt.Dimension(24, 24));
	line.setMinimumSize(new java.awt.Dimension(24, 24));
	line.setPreferredSize(new java.awt.Dimension(24, 24));
	line.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		lineActionPerformed(evt);
	    }
	});

	gridBagConstraints1 = new java.awt.GridBagConstraints();
	gridBagConstraints1.gridx = 0;
	gridBagConstraints1.gridy = 5;
	shapes.add(line, gridBagConstraints1);

	curve.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/curve_16_16.gif")));
	curve.setActionCommand("line");
	curve.setToolTipText("Curve");
	curve.setMargin(new java.awt.Insets(0, 0, 0, 0));
	curve.setMaximumSize(new java.awt.Dimension(24, 24));
	curve.setMinimumSize(new java.awt.Dimension(24, 24));
	curve.setPreferredSize(new java.awt.Dimension(24, 24));
	curve.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		curveActionPerformed(evt);
	    }
	});

	gridBagConstraints1 = new java.awt.GridBagConstraints();
	gridBagConstraints1.gridx = 1;
	gridBagConstraints1.gridy = 5;
	shapes.add(curve, gridBagConstraints1);

	rounded_rectangle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/rounded_16_16.gif")));
	rounded_rectangle.setActionCommand("line");
	rounded_rectangle.setToolTipText("Rounded Rectangle");
	rounded_rectangle.setMargin(new java.awt.Insets(0, 0, 0, 0));
	rounded_rectangle.setMaximumSize(new java.awt.Dimension(24, 24));
	rounded_rectangle.setMinimumSize(new java.awt.Dimension(24, 24));
	rounded_rectangle.setPreferredSize(new java.awt.Dimension(24, 24));
	rounded_rectangle.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		rounded_rectangleActionPerformed(evt);
	    }
	});

	gridBagConstraints1 = new java.awt.GridBagConstraints();
	gridBagConstraints1.gridx = 1;
	gridBagConstraints1.gridy = 7;
	shapes.add(rounded_rectangle, gridBagConstraints1);

	square.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/square_16_16.gif")));
	square.setActionCommand("line");
	square.setToolTipText("Rectangle");
	square.setMargin(new java.awt.Insets(0, 0, 0, 0));
	square.setMaximumSize(new java.awt.Dimension(24, 24));
	square.setMinimumSize(new java.awt.Dimension(24, 24));
	square.setPreferredSize(new java.awt.Dimension(24, 24));
	square.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		squareActionPerformed(evt);
	    }
	});

	gridBagConstraints1 = new java.awt.GridBagConstraints();
	gridBagConstraints1.gridx = 0;
	gridBagConstraints1.gridy = 6;
	shapes.add(square, gridBagConstraints1);

	polygon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/polygon_16_16.gif")));
	polygon.setActionCommand("line");
	polygon.setToolTipText("Polygon");
	polygon.setMargin(new java.awt.Insets(0, 0, 0, 0));
	polygon.setMaximumSize(new java.awt.Dimension(24, 24));
	polygon.setMinimumSize(new java.awt.Dimension(24, 24));
	polygon.setPreferredSize(new java.awt.Dimension(24, 24));
	polygon.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		polygonActionPerformed(evt);
	    }
	});

	gridBagConstraints1 = new java.awt.GridBagConstraints();
	gridBagConstraints1.gridx = 1;
	gridBagConstraints1.gridy = 6;
	shapes.add(polygon, gridBagConstraints1);

	elipse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/elipse_16_16.gif")));
	elipse.setActionCommand("line");
	elipse.setToolTipText("Ellipse");
	elipse.setMargin(new java.awt.Insets(0, 0, 0, 0));
	elipse.setMaximumSize(new java.awt.Dimension(24, 24));
	elipse.setMinimumSize(new java.awt.Dimension(24, 24));
	elipse.setPreferredSize(new java.awt.Dimension(24, 24));
	elipse.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		elipseActionPerformed(evt);
	    }
	});

	gridBagConstraints1 = new java.awt.GridBagConstraints();
	gridBagConstraints1.gridx = 0;
	gridBagConstraints1.gridy = 7;
	shapes.add(elipse, gridBagConstraints1);

	selectall.setBackground(java.awt.Color.lightGray);
	selectall.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/selectall_16_16.gif")));
	selectall.setActionCommand("line");
	selectall.setToolTipText("Select");
	selectall.setMargin(new java.awt.Insets(0, 0, 0, 0));
	selectall.setMaximumSize(new java.awt.Dimension(24, 24));
	selectall.setMinimumSize(new java.awt.Dimension(24, 24));
	selectall.setPreferredSize(new java.awt.Dimension(24, 24));
//Istvan phase 5
	selectall.setSelected(true);
//End Istvan phase 5
	selectall.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		selectallActionPerformed(evt);
	    }
	});

	gridBagConstraints1 = new java.awt.GridBagConstraints();
	gridBagConstraints1.gridx = 1;
	gridBagConstraints1.gridy = 0;
	shapes.add(selectall, gridBagConstraints1);

	eraser.setBackground(java.awt.Color.lightGray);
	eraser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eraser_16_16.gif")));
	eraser.setActionCommand("line");
	eraser.setToolTipText("Eraser");
	eraser.setMargin(new java.awt.Insets(0, 0, 0, 0));
	eraser.setMaximumSize(new java.awt.Dimension(24, 24));
	eraser.setMinimumSize(new java.awt.Dimension(24, 24));
	eraser.setPreferredSize(new java.awt.Dimension(24, 24));
	eraser.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		eraserActionPerformed(evt);
	    }
	});

	gridBagConstraints1 = new java.awt.GridBagConstraints();
	gridBagConstraints1.gridx = 0;
	gridBagConstraints1.gridy = 1;
	shapes.add(eraser, gridBagConstraints1);

	paint.setBackground(java.awt.Color.lightGray);
	paint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/paint_16_16.gif")));
	paint.setActionCommand("line");
	paint.setToolTipText("Fill With Color");
	paint.setMargin(new java.awt.Insets(0, 0, 0, 0));
	paint.setMaximumSize(new java.awt.Dimension(24, 24));
	paint.setMinimumSize(new java.awt.Dimension(24, 24));
	paint.setPreferredSize(new java.awt.Dimension(24, 24));
	paint.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		paintActionPerformed(evt);
	    }
	});

	gridBagConstraints1 = new java.awt.GridBagConstraints();
	gridBagConstraints1.gridx = 1;
	gridBagConstraints1.gridy = 1;
	shapes.add(paint, gridBagConstraints1);

	medicine.setBackground(java.awt.Color.lightGray);
	medicine.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/medicine_16_16.gif")));
	medicine.setActionCommand("line");
	medicine.setToolTipText("Pick Color");
	medicine.setMargin(new java.awt.Insets(0, 0, 0, 0));
	medicine.setMaximumSize(new java.awt.Dimension(24, 24));
	medicine.setMinimumSize(new java.awt.Dimension(24, 24));
	medicine.setPreferredSize(new java.awt.Dimension(24, 24));
	medicine.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		medicineActionPerformed(evt);
	    }
	});

	gridBagConstraints1 = new java.awt.GridBagConstraints();
	gridBagConstraints1.gridx = 0;
	gridBagConstraints1.gridy = 2;
	shapes.add(medicine, gridBagConstraints1);

	zoom.setBackground(java.awt.Color.lightGray);
	zoom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/zoom_16_16.gif")));
	zoom.setActionCommand("line");
	zoom.setToolTipText("Magnifier");
	zoom.setMargin(new java.awt.Insets(0, 0, 0, 0));
	zoom.setMaximumSize(new java.awt.Dimension(24, 24));
	zoom.setMinimumSize(new java.awt.Dimension(24, 24));
	zoom.setPreferredSize(new java.awt.Dimension(24, 24));
	zoom.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		zoomActionPerformed(evt);
	    }
	});

	gridBagConstraints1 = new java.awt.GridBagConstraints();
	gridBagConstraints1.gridx = 1;
	gridBagConstraints1.gridy = 2;
	shapes.add(zoom, gridBagConstraints1);

	pencil.setBackground(java.awt.Color.lightGray);
	pencil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pencil_16_16.gif")));
	pencil.setActionCommand("line");
	pencil.setToolTipText("Pencil");
	pencil.setMargin(new java.awt.Insets(0, 0, 0, 0));
	pencil.setMaximumSize(new java.awt.Dimension(24, 24));
	pencil.setMinimumSize(new java.awt.Dimension(24, 24));
	pencil.setPreferredSize(new java.awt.Dimension(24, 24));
  //Istvan phase 5
	pencil.setSelected(false);
  //End Istvan phase 5
	pencil.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		pencilActionPerformed(evt);
	    }
	});

	gridBagConstraints1 = new java.awt.GridBagConstraints();
	gridBagConstraints1.gridx = 0;
	gridBagConstraints1.gridy = 3;
	shapes.add(pencil, gridBagConstraints1);

	brush.setBackground(java.awt.Color.lightGray);
	brush.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/brush_16_16.gif")));
	brush.setActionCommand("line");
	brush.setToolTipText("Brush");
	brush.setMargin(new java.awt.Insets(0, 0, 0, 0));
	brush.setMaximumSize(new java.awt.Dimension(24, 24));
	brush.setMinimumSize(new java.awt.Dimension(24, 24));
	brush.setPreferredSize(new java.awt.Dimension(24, 24));
	brush.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		brushActionPerformed(evt);
	    }
	});

	gridBagConstraints1 = new java.awt.GridBagConstraints();
	gridBagConstraints1.gridx = 1;
	gridBagConstraints1.gridy = 3;
	shapes.add(brush, gridBagConstraints1);

	spray.setBackground(java.awt.Color.lightGray);
	spray.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/spray_16_16.gif")));
	spray.setActionCommand("line");
	spray.setToolTipText("Airbrush");
	spray.setMargin(new java.awt.Insets(0, 0, 0, 0));
	spray.setMaximumSize(new java.awt.Dimension(24, 24));
	spray.setMinimumSize(new java.awt.Dimension(24, 24));
	spray.setPreferredSize(new java.awt.Dimension(24, 24));
	spray.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		sprayActionPerformed(evt);
	    }
	});

	gridBagConstraints1 = new java.awt.GridBagConstraints();
	gridBagConstraints1.gridx = 0;
	gridBagConstraints1.gridy = 4;
	shapes.add(spray, gridBagConstraints1);

	letter.setBackground(java.awt.Color.lightGray);
	letter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/letter_16_16.gif")));
	letter.setActionCommand("line");
	letter.setToolTipText("Text");
	letter.setMargin(new java.awt.Insets(0, 0, 0, 0));
	letter.setMaximumSize(new java.awt.Dimension(24, 24));
	letter.setMinimumSize(new java.awt.Dimension(24, 24));
	letter.setPreferredSize(new java.awt.Dimension(24, 24));
	letter.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		letterActionPerformed(evt);
	    }
	});

	gridBagConstraints1 = new java.awt.GridBagConstraints();
	gridBagConstraints1.gridx = 1;
	gridBagConstraints1.gridy = 4;
	shapes.add(letter, gridBagConstraints1);

	select.setBackground(java.awt.Color.lightGray);
	select.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/select_16_16.gif")));
	select.setActionCommand("line");
	select.setToolTipText("Free-Form Select");
	select.setAlignmentY(0.0F);
	select.setMargin(new java.awt.Insets(0, 0, 0, 0));
	select.setMaximumSize(new java.awt.Dimension(24, 24));
	select.setMinimumSize(new java.awt.Dimension(24, 24));
	select.setPreferredSize(new java.awt.Dimension(24, 24));
	select.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		selectActionPerformed(evt);
	    }
	});

	gridBagConstraints1 = new java.awt.GridBagConstraints();
	gridBagConstraints1.gridx = 0;
	gridBagConstraints1.gridy = 0;
	shapes.add(select, gridBagConstraints1);

	//added
		magicSelect.setBackground(java.awt.Color.lightGray);
	//new color add
	magicSelect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/magic2.gif")));
	//new color added
	magicSelect.setActionCommand("line");
	magicSelect.setToolTipText("Magic Wand Select");
	magicSelect.setAlignmentY(0.0F);
	magicSelect.setMargin(new java.awt.Insets(0, 0, 0, 0));
	magicSelect.setMaximumSize(new java.awt.Dimension(24, 24));
	magicSelect.setMinimumSize(new java.awt.Dimension(24, 24));
	magicSelect.setPreferredSize(new java.awt.Dimension(24, 24));
	magicSelect.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		magicSelectActionPerformed(evt);
	    }
	});

	gridBagConstraints1 = new java.awt.GridBagConstraints();
	gridBagConstraints1.gridx = 0;
	gridBagConstraints1.gridy = 8;
	shapes.add(magicSelect, gridBagConstraints1);
	//end added

	west.add(shapes, java.awt.BorderLayout.NORTH);

	line_properties.setLayout(new java.awt.GridBagLayout());
	java.awt.GridBagConstraints gridBagConstraints2;

	line1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/line1_16_16.gif")));
	line1.setMaximumSize(new java.awt.Dimension(16, 16));
	line1.setMinimumSize(new java.awt.Dimension(16, 16));
	line1.setPreferredSize(new java.awt.Dimension(48, 16));
	line1.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		line1ActionPerformed(evt);
	    }
	});

	gridBagConstraints2 = new java.awt.GridBagConstraints();
	line_properties.add(line1, gridBagConstraints2);

	line2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/line2_16_16.gif")));
	line2.setMaximumSize(new java.awt.Dimension(16, 16));
	line2.setMinimumSize(new java.awt.Dimension(16, 16));
	line2.setPreferredSize(new java.awt.Dimension(48, 16));
	line2.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		line2ActionPerformed(evt);
	    }
	});

	gridBagConstraints2 = new java.awt.GridBagConstraints();
	gridBagConstraints2.gridx = 0;
	gridBagConstraints2.gridy = 1;
	line_properties.add(line2, gridBagConstraints2);

	line3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/line3_16_16.gif")));
	line3.setMaximumSize(new java.awt.Dimension(16, 16));
	line3.setMinimumSize(new java.awt.Dimension(16, 16));
	line3.setPreferredSize(new java.awt.Dimension(48, 16));
	line3.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		line3ActionPerformed(evt);
	    }
	});

	gridBagConstraints2 = new java.awt.GridBagConstraints();
	gridBagConstraints2.gridx = 0;
	gridBagConstraints2.gridy = 2;
	line_properties.add(line3, gridBagConstraints2);

	line4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/line4_16_16.gif")));
	line4.setMaximumSize(new java.awt.Dimension(16, 16));
	line4.setMinimumSize(new java.awt.Dimension(16, 16));
	line4.setPreferredSize(new java.awt.Dimension(48, 16));
	line4.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		line4ActionPerformed(evt);
	    }
	});

	gridBagConstraints2 = new java.awt.GridBagConstraints();
	gridBagConstraints2.gridx = 0;
	gridBagConstraints2.gridy = 3;
	line_properties.add(line4, gridBagConstraints2);

	line5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/line5_16_16.gif")));
	line5.setMaximumSize(new java.awt.Dimension(16, 16));
	line5.setMinimumSize(new java.awt.Dimension(16, 16));
	line5.setPreferredSize(new java.awt.Dimension(48, 16));
	line5.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		line5ActionPerformed(evt);
	    }
	});

	gridBagConstraints2 = new java.awt.GridBagConstraints();
	gridBagConstraints2.gridx = 0;
	gridBagConstraints2.gridy = 4;
	line_properties.add(line5, gridBagConstraints2);

	properties.add(line_properties);

	rect_properties.setLayout(new java.awt.GridBagLayout());
	java.awt.GridBagConstraints gridBagConstraints3;

	one.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/rect1_16_16.gif")));
	one.setPreferredSize(new java.awt.Dimension(48, 26));
	one.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		oneActionPerformed(evt);
	    }
	});


	gridBagConstraints3 = new java.awt.GridBagConstraints();
	rect_properties.add(one, gridBagConstraints3);

	two.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/rect2_16_16.gif")));
	two.setPreferredSize(new java.awt.Dimension(48, 26));
	two.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		twoActionPerformed(evt);
	    }
	});

	gridBagConstraints3 = new java.awt.GridBagConstraints();
	gridBagConstraints3.gridx = 0;
	gridBagConstraints3.gridy = 1;
	rect_properties.add(two, gridBagConstraints3);

	three.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/rect3_16_16.gif")));
	three.setPreferredSize(new java.awt.Dimension(48, 26));
	three.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		threeActionPerformed(evt);
	    }
	});

	gridBagConstraints3 = new java.awt.GridBagConstraints();
	gridBagConstraints3.gridx = 0;
	gridBagConstraints3.gridy = 2;
	rect_properties.add(three, gridBagConstraints3);

	properties.add(rect_properties);

	spray_properties.setLayout(new java.awt.GridBagLayout());
	java.awt.GridBagConstraints gridBagConstraints4;

	spray1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/spray1_16_16.gif")));
	spray1.setPreferredSize(new java.awt.Dimension(48, 26));
	spray1.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		spray1ActionPerformed(evt);
	    }
	});

	gridBagConstraints4 = new java.awt.GridBagConstraints();
	spray_properties.add(spray1, gridBagConstraints4);

	spray2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/spray2_16_16.gif")));
	spray2.setPreferredSize(new java.awt.Dimension(48, 26));
	spray2.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		spray2ActionPerformed(evt);
	    }
	});

	gridBagConstraints4 = new java.awt.GridBagConstraints();
	gridBagConstraints4.gridx = 0;
	gridBagConstraints4.gridy = 1;
	spray_properties.add(spray2, gridBagConstraints4);

	spray3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/spray3_16_16.gif")));
	spray3.setPreferredSize(new java.awt.Dimension(48, 26));
	spray3.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		spray3ActionPerformed(evt);
	    }
	});

	gridBagConstraints4 = new java.awt.GridBagConstraints();
	gridBagConstraints4.gridx = 0;
	gridBagConstraints4.gridy = 2;
	spray_properties.add(spray3, gridBagConstraints4);

	properties.add(spray_properties);

	brush_properties.setLayout(new java.awt.GridBagLayout());
	java.awt.GridBagConstraints gridBagConstraints5;
	//	dot3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/b1.gif")));

	dot3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/t1_point3_16_16.gif")));
	//     dot3.setMinimumSize(new java.awt.Dimension(12, 12));
	dot3.setPreferredSize(new java.awt.Dimension(16, 32));
	dot3.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		dot3ActionPerformed(evt);
	    }
	});

	gridBagConstraints5 = new java.awt.GridBagConstraints();
	gridBagConstraints5.gridx = 0;
	gridBagConstraints5.gridy = 0;
	brush_properties.add(dot3, gridBagConstraints5);

	dot2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/t1_point2_16_16.gif")));
	//	dot2.setMinimumSize(new java.awt.Dimension(16, 16));
	dot2.setPreferredSize(new java.awt.Dimension(16, 32));
	dot2.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		dot2ActionPerformed(evt);
	    }
	});

	gridBagConstraints5 = new java.awt.GridBagConstraints();
	gridBagConstraints5.gridx = 1;
	gridBagConstraints5.gridy = 0;
	brush_properties.add(dot2, gridBagConstraints5);

	dot1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/t1_point1_16_16.gif")));
	//	dot1.setMinimumSize(new java.awt.Dimension(16, 16));
	dot1.setPreferredSize(new java.awt.Dimension(16, 32));
	dot1.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		dot1ActionPerformed(evt);
	    }
	});


	gridBagConstraints5 = new java.awt.GridBagConstraints();
	gridBagConstraints5.gridx = 2;
	gridBagConstraints5.gridy = 0;
	brush_properties.add(dot1, gridBagConstraints5);

	square3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/t1_square3_16_16.gif")));
	//	  square3.setMinimumSize(new java.awt.Dimension(16, 16));
	square3.setPreferredSize(new java.awt.Dimension(16, 32));
	square3.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		square3ActionPerformed(evt);
	    }
	});

	gridBagConstraints5 = new java.awt.GridBagConstraints();
	gridBagConstraints5.gridx = 0;
	gridBagConstraints5.gridy = 1;
	brush_properties.add(square3, gridBagConstraints5);

	square2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/t1_square2_16_16.gif")));
	//	  square2.setMinimumSize(new java.awt.Dimension(16, 16));
	square2.setPreferredSize(new java.awt.Dimension(16, 32));
	square2.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		square2ActionPerformed(evt);
	    }
	});

	gridBagConstraints5 = new java.awt.GridBagConstraints();
	gridBagConstraints5.gridx = 1;
	gridBagConstraints5.gridy = 1;
	brush_properties.add(square2, gridBagConstraints5);

	square1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/t1_square1_16_16.gif")));
	//	  square1.setMinimumSize(new java.awt.Dimension(16, 16));
	square1.setPreferredSize(new java.awt.Dimension(16, 32));
	square1.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		square1ActionPerformed(evt);
	    }
	});

	gridBagConstraints5 = new java.awt.GridBagConstraints();
	gridBagConstraints5.gridx = 2;
	gridBagConstraints5.gridy = 1;
	brush_properties.add(square1, gridBagConstraints5);

	right3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/t1_right3_16_16.gif")));
	//	  right3.setMinimumSize(new java.awt.Dimension(16, 16));
	right3.setPreferredSize(new java.awt.Dimension(16, 32));
	right3.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		right3ActionPerformed(evt);
	    }
	});

	gridBagConstraints5 = new java.awt.GridBagConstraints();
	gridBagConstraints5.gridx = 0;
	gridBagConstraints5.gridy = 2;
	brush_properties.add(right3, gridBagConstraints5);

	right2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/t1_right2_16_16.gif")));
	//	  right2.setMinimumSize(new java.awt.Dimension(16, 16));
	right2.setPreferredSize(new java.awt.Dimension(16, 32));
	right2.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		right2ActionPerformed(evt);
	    }
	});

	gridBagConstraints5 = new java.awt.GridBagConstraints();
	gridBagConstraints5.gridx = 1;
	gridBagConstraints5.gridy = 2;
	brush_properties.add(right2, gridBagConstraints5);

	right1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/t1_right1_16_16.gif")));
	//	  right1.setMinimumSize(new java.awt.Dimension(16, 16));
	right1.setPreferredSize(new java.awt.Dimension(16, 32));
	right1.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		right1ActionPerformed(evt);
	    }
	});

	gridBagConstraints5 = new java.awt.GridBagConstraints();
	gridBagConstraints5.gridx = 2;
	gridBagConstraints5.gridy = 2;
	brush_properties.add(right1, gridBagConstraints5);

	left3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/t1_left3_16_16.gif")));
	//	  left3.setMinimumSize(new java.awt.Dimension(16, 16));
	left3.setPreferredSize(new java.awt.Dimension(16, 32));
	left3.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		left3ActionPerformed(evt);
	    }
	});

	gridBagConstraints5 = new java.awt.GridBagConstraints();
	gridBagConstraints5.gridx = 0;
	gridBagConstraints5.gridy = 3;
	brush_properties.add(left3, gridBagConstraints5);

	left2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/t1_left2_16_16.gif")));
	//	  left2.setMinimumSize(new java.awt.Dimension(16, 16));
	left2.setPreferredSize(new java.awt.Dimension(16, 32));
	left2.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		left2ActionPerformed(evt);
	    }
	});

	gridBagConstraints5 = new java.awt.GridBagConstraints();
	gridBagConstraints5.gridx = 1;
	gridBagConstraints5.gridy = 3;
	brush_properties.add(left2, gridBagConstraints5);

	left1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/t1_left1_16_16.gif")));
	//	  left1.setMinimumSize(new java.awt.Dimension(16, 16));
	left1.setPreferredSize(new java.awt.Dimension(16, 32));
	left1.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		left1ActionPerformed(evt);
	    }
	});

	gridBagConstraints5 = new java.awt.GridBagConstraints();
	gridBagConstraints5.gridx = 2;
	gridBagConstraints5.gridy = 3;
	brush_properties.add(left1, gridBagConstraints5);

	properties.add(brush_properties);

	zoom_properties.setLayout(new java.awt.GridBagLayout());
	java.awt.GridBagConstraints gridBagConstraints6;

	zoom1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/1_16_16.gif")));
	zoom1.setPreferredSize(new java.awt.Dimension(48, 16));
	zoom1.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		zoom1ActionPerformed(evt);
	    }
	});

	gridBagConstraints6 = new java.awt.GridBagConstraints();
	zoom_properties.add(zoom1, gridBagConstraints6);

	zoom2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/2_16_16.gif")));
	zoom2.setPreferredSize(new java.awt.Dimension(48, 16));
	zoom2.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		zoom2ActionPerformed(evt);
	    }
	});

	gridBagConstraints6 = new java.awt.GridBagConstraints();
	gridBagConstraints6.gridx = 0;
	gridBagConstraints6.gridy = 1;
	zoom_properties.add(zoom2, gridBagConstraints6);

	// ming 4.22
	zoom3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/4_16_16.gif")));
	// ming 4.22 end
	zoom3.setPreferredSize(new java.awt.Dimension(48, 16));
	zoom3.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		zoom3ActionPerformed(evt);
	    }
	});

	gridBagConstraints6 = new java.awt.GridBagConstraints();
	gridBagConstraints6.gridx = 0;
	gridBagConstraints6.gridy = 2;
	zoom_properties.add(zoom3, gridBagConstraints6);

	zoom4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/8_16_16.gif")));
	zoom4.setPreferredSize(new java.awt.Dimension(48, 16));
	zoom4.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		zoom4ActionPerformed(evt);
	    }
	});

	gridBagConstraints6 = new java.awt.GridBagConstraints();
	gridBagConstraints6.gridx = 0;
	gridBagConstraints6.gridy = 3;
	zoom_properties.add(zoom4, gridBagConstraints6);

	properties.add(zoom_properties);

	paint_properties.setLayout(new java.awt.GridLayout(1, 0));

	paint1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/line1_16_16.gif")));
	paint1.setPreferredSize(new java.awt.Dimension(48, 16));
	paint_properties.add(paint1);

	paint2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/line2_16_16.gif")));
	paint2.setPreferredSize(new java.awt.Dimension(48, 16));
	paint_properties.add(paint2);

	paint3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/line3_16_16.gif")));
	paint3.setPreferredSize(new java.awt.Dimension(48, 16));
	paint_properties.add(paint3);

	paint4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/line4_16_16.gif")));
	paint4.setPreferredSize(new java.awt.Dimension(48, 16));
	paint_properties.add(paint4);

	properties.add(paint_properties);

	none.setMinimumSize(new java.awt.Dimension(48, 64));
	none.setPreferredSize(new java.awt.Dimension(48, 64));
	nothing.setMargin(new java.awt.Insets(0, 0, 0, 0));
	nothing.setMaximumSize(new java.awt.Dimension(48, 64));
	nothing.setMinimumSize(new java.awt.Dimension(48, 64));
	nothing.setPreferredSize(new java.awt.Dimension(48, 60));
	none.add(nothing);


	properties.add(none);

	west.add(properties, java.awt.BorderLayout.SOUTH);
	//this.requestFocus();
	//addKeyListener(new KeyHandler());
	getContentPane().add(west, java.awt.BorderLayout.WEST);

	south.setLayout(new java.awt.BorderLayout());


	// 600 is variable.

	south.setBackground(java.awt.Color.lightGray);
	south.setMinimumSize(new java.awt.Dimension(sizeWidth, 32));
	south.setPreferredSize(new java.awt.Dimension(sizeWidth, 32));
	south.setOpaque(false);
	south.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mousePressed(java.awt.event.MouseEvent evt) {
		southMousePressed(evt);
	    }
	});

	south.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
	    public void mouseMoved(java.awt.event.MouseEvent evt) {
		southMouseMoved(evt);
	    }
	});

	colors.setLayout(new java.awt.GridBagLayout());
	java.awt.GridBagConstraints gridBagConstraints7;

	color_menu.setLayout(new java.awt.GridBagLayout());
	java.awt.GridBagConstraints gridBagConstraints8;

	red.setBackground(java.awt.Color.red);
	red.setIcon(new javax.swing.ImageIcon(""));
	red.setActionCommand("line");
	red.setMargin(new java.awt.Insets(0, 0, 0, 0));
	red.setMaximumSize(new java.awt.Dimension(16, 16));
	red.setMinimumSize(new java.awt.Dimension(16, 16));
	red.setPreferredSize(new java.awt.Dimension(16, 16));
	red.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mouseClicked(java.awt.event.MouseEvent evt) {
		redMouseClicked(evt);
	    }
	});

	gridBagConstraints8 = new java.awt.GridBagConstraints();
	gridBagConstraints8.gridx = 2;
	gridBagConstraints8.gridy = 1;
	color_menu.add(red, gridBagConstraints8);


	green.setBackground(java.awt.Color.green);
	green.setIcon(new javax.swing.ImageIcon(""));
	green.setActionCommand("line");
	green.setMargin(new java.awt.Insets(0, 0, 0, 0));
	green.setMaximumSize(new java.awt.Dimension(16, 16));
	green.setMinimumSize(new java.awt.Dimension(16, 16));
	green.setPreferredSize(new java.awt.Dimension(16, 16));
	green.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mouseClicked(java.awt.event.MouseEvent evt) {
		greenMouseClicked(evt);
	    }
	});

	gridBagConstraints8 = new java.awt.GridBagConstraints();
	gridBagConstraints8.gridx = 3;
	gridBagConstraints8.gridy = 0;
	color_menu.add(green, gridBagConstraints8);

	blue.setBackground(java.awt.Color.blue);
	blue.setIcon(new javax.swing.ImageIcon(""));
	blue.setActionCommand("line");
	blue.setMargin(new java.awt.Insets(0, 0, 0, 0));
	blue.setMaximumSize(new java.awt.Dimension(16, 16));
	blue.setMinimumSize(new java.awt.Dimension(16, 16));
	blue.setPreferredSize(new java.awt.Dimension(16, 16));
	blue.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mouseClicked(java.awt.event.MouseEvent evt) {
		blueMouseClicked(evt);
	    }
	});

	gridBagConstraints8 = new java.awt.GridBagConstraints();
	gridBagConstraints8.gridx = 6;
	gridBagConstraints8.gridy = 1;
	color_menu.add(blue, gridBagConstraints8);

	yellow.setBackground(java.awt.Color.yellow);
	yellow.setIcon(new javax.swing.ImageIcon(""));
	yellow.setActionCommand("line");
	yellow.setMargin(new java.awt.Insets(0, 0, 0, 0));
	yellow.setMaximumSize(new java.awt.Dimension(16, 16));
	yellow.setMinimumSize(new java.awt.Dimension(16, 16));
	yellow.setPreferredSize(new java.awt.Dimension(16, 16));
	yellow.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mouseClicked(java.awt.event.MouseEvent evt) {
		yellowMouseClicked(evt);
	    }
	});

	gridBagConstraints8 = new java.awt.GridBagConstraints();
	gridBagConstraints8.gridx = 3;
	gridBagConstraints8.gridy = 1;
	color_menu.add(yellow, gridBagConstraints8);

	magenta.setBackground(java.awt.Color.magenta);
	magenta.setIcon(new javax.swing.ImageIcon(""));
	magenta.setActionCommand("line");
	magenta.setMargin(new java.awt.Insets(0, 0, 0, 0));
	magenta.setMaximumSize(new java.awt.Dimension(16, 16));
	magenta.setMinimumSize(new java.awt.Dimension(16, 16));
	magenta.setPreferredSize(new java.awt.Dimension(16, 16));
	magenta.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mouseClicked(java.awt.event.MouseEvent evt) {
		magentaMouseClicked(evt);
	    }
	});

	gridBagConstraints8 = new java.awt.GridBagConstraints();
	gridBagConstraints8.gridx = 6;
	gridBagConstraints8.gridy = 0;
	color_menu.add(magenta, gridBagConstraints8);

	cyan.setBackground(java.awt.Color.cyan);
	cyan.setIcon(new javax.swing.ImageIcon(""));
	cyan.setActionCommand("line");
	cyan.setMargin(new java.awt.Insets(0, 0, 0, 0));
	cyan.setMaximumSize(new java.awt.Dimension(16, 16));
	cyan.setMinimumSize(new java.awt.Dimension(16, 16));
	cyan.setPreferredSize(new java.awt.Dimension(16, 16));
	cyan.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mouseClicked(java.awt.event.MouseEvent evt) {
		cyanMouseClicked(evt);
	    }
	});

	gridBagConstraints8 = new java.awt.GridBagConstraints();
	gridBagConstraints8.gridx = 5;
	gridBagConstraints8.gridy = 1;
	color_menu.add(cyan, gridBagConstraints8);

	pink.setBackground(java.awt.Color.pink);
	pink.setIcon(new javax.swing.ImageIcon(""));
	pink.setActionCommand("line");
	pink.setMargin(new java.awt.Insets(0, 0, 0, 0));
	pink.setMaximumSize(new java.awt.Dimension(16, 16));
	pink.setMinimumSize(new java.awt.Dimension(16, 16));
	pink.setPreferredSize(new java.awt.Dimension(16, 16));
	pink.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mouseClicked(java.awt.event.MouseEvent evt) {
		pinkMouseClicked(evt);
	    }
	});

	gridBagConstraints8 = new java.awt.GridBagConstraints();
	gridBagConstraints8.gridx = 4;
	gridBagConstraints8.gridy = 1;
	color_menu.add(pink, gridBagConstraints8);

	white.setBackground(java.awt.Color.white);
	white.setIcon(new javax.swing.ImageIcon(""));
	white.setActionCommand("line");
	white.setMargin(new java.awt.Insets(0, 0, 0, 0));
	white.setMaximumSize(new java.awt.Dimension(16, 16));
	white.setMinimumSize(new java.awt.Dimension(16, 16));
	white.setPreferredSize(new java.awt.Dimension(16, 16));
	white.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mouseClicked(java.awt.event.MouseEvent evt) {
		whiteMouseClicked(evt);
	    }
	});

	gridBagConstraints8 = new java.awt.GridBagConstraints();

	gridBagConstraints8.gridx = 0;
	gridBagConstraints8.gridy = 1;
	color_menu.add(white, gridBagConstraints8);

	lgt_gray.setBackground(java.awt.Color.lightGray);
	lgt_gray.setIcon(new javax.swing.ImageIcon(""));
	lgt_gray.setActionCommand("line");
	lgt_gray.setMargin(new java.awt.Insets(0, 0, 0, 0));
	lgt_gray.setMaximumSize(new java.awt.Dimension(16, 16));
	lgt_gray.setMinimumSize(new java.awt.Dimension(16, 16));
	lgt_gray.setPreferredSize(new java.awt.Dimension(16, 16));
	lgt_gray.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		lgt_grayActionPerformed(evt);
	    }
	});

	lgt_gray.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mouseClicked(java.awt.event.MouseEvent evt) {
		lgt_grayMouseClicked(evt);
	    }
	});


	gridBagConstraints8 = new java.awt.GridBagConstraints();
	gridBagConstraints8.gridx = 1;
	gridBagConstraints8.gridy = 1;
	color_menu.add(lgt_gray, gridBagConstraints8);

	gray.setBackground(java.awt.Color.gray);
	gray.setIcon(new javax.swing.ImageIcon(""));
	gray.setActionCommand("line");
	gray.setMargin(new java.awt.Insets(0, 0, 0, 0));
	gray.setMaximumSize(new java.awt.Dimension(16, 16));
	gray.setMinimumSize(new java.awt.Dimension(16, 16));
	gray.setPreferredSize(new java.awt.Dimension(16, 16));
	gray.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mouseClicked(java.awt.event.MouseEvent evt) {
		grayMouseClicked(evt);
	    }
	});

	gridBagConstraints8 = new java.awt.GridBagConstraints();
	gridBagConstraints8.gridx = 1;
	gridBagConstraints8.gridy = 0;
	color_menu.add(gray, gridBagConstraints8);

	drk_gray.setBackground(java.awt.Color.darkGray);
	drk_gray.setIcon(new javax.swing.ImageIcon(""));
	drk_gray.setActionCommand("line");
	drk_gray.setMargin(new java.awt.Insets(0, 0, 0, 0));
	drk_gray.setMaximumSize(new java.awt.Dimension(16, 16));
	drk_gray.setMinimumSize(new java.awt.Dimension(16, 16));
	drk_gray.setPreferredSize(new java.awt.Dimension(16, 16));
	drk_gray.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mouseClicked(java.awt.event.MouseEvent evt) {
		drk_grayMouseClicked(evt);
	    }
	});

	gridBagConstraints8 = new java.awt.GridBagConstraints();
	gridBagConstraints8.gridx = 0;
	gridBagConstraints8.gridy = 0;
	color_menu.add(drk_gray, gridBagConstraints8);

	black.setBackground(java.awt.Color.black);
	black.setIcon(new javax.swing.ImageIcon(""));
	black.setActionCommand("line");
	black.setMargin(new java.awt.Insets(0, 0, 0, 0));
	black.setMaximumSize(new java.awt.Dimension(16, 16));
	black.setMinimumSize(new java.awt.Dimension(16, 16));
	black.setPreferredSize(new java.awt.Dimension(16, 16));
	black.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mouseClicked(java.awt.event.MouseEvent evt) {
		blackMouseClicked(evt);
	    }
	});

	gridBagConstraints8 = new java.awt.GridBagConstraints();
	gridBagConstraints8.gridx = 5;
	gridBagConstraints8.gridy = 0;
	color_menu.add(black, gridBagConstraints8);

	brown.setBackground(new java.awt.Color(143, 0, 0));
	brown.setIcon(new javax.swing.ImageIcon(""));
	brown.setActionCommand("line");
	brown.setMargin(new java.awt.Insets(0, 0, 0, 0));
	brown.setMaximumSize(new java.awt.Dimension(16, 16));
	brown.setMinimumSize(new java.awt.Dimension(16, 16));
	brown.setPreferredSize(new java.awt.Dimension(16, 16));
	brown.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mouseClicked(java.awt.event.MouseEvent evt) {
		brownMouseClicked(evt);
	    }
	});

	gridBagConstraints8 = new java.awt.GridBagConstraints();
	gridBagConstraints8.gridx = 2;
	gridBagConstraints8.gridy = 0;
	color_menu.add(brown, gridBagConstraints8);

	//orange.setBackground(java.awt.Color.orange);
	orange.setBackground(new java.awt.Color(255, 150, 0));
	orange.setIcon(new javax.swing.ImageIcon(""));
	orange.setActionCommand("line");
	orange.setMargin(new java.awt.Insets(0, 0, 0, 0));
	orange.setMaximumSize(new java.awt.Dimension(16, 16));
	orange.setMinimumSize(new java.awt.Dimension(16, 16));
	orange.setPreferredSize(new java.awt.Dimension(16, 16));
	orange.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mouseClicked(java.awt.event.MouseEvent evt) {
		orangeMouseClicked(evt);
	    }
	});

	gridBagConstraints8 = new java.awt.GridBagConstraints();
	gridBagConstraints8.gridx = 4;
	gridBagConstraints8.gridy = 0;
	color_menu.add(orange, gridBagConstraints8);
//new color add
	color1.setBackground(new java.awt.Color(20, 20, 20));
	color1.setIcon(new javax.swing.ImageIcon(""));
	color1.setActionCommand("line");
	color1.setMargin(new java.awt.Insets(0, 0, 0, 0));
	color1.setMaximumSize(new java.awt.Dimension(16, 16));
	color1.setMinimumSize(new java.awt.Dimension(16, 16));
	color1.setPreferredSize(new java.awt.Dimension(16, 16));
	color1.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mouseClicked(java.awt.event.MouseEvent evt) {
		color1MouseClicked(evt);
	    }
	});

	gridBagConstraints8 = new java.awt.GridBagConstraints();
	gridBagConstraints8.gridx = 7;
	gridBagConstraints8.gridy = 0;
	color_menu.add(color1, gridBagConstraints8);

		color2.setBackground(new java.awt.Color(40, 40, 40));
	color2.setIcon(new javax.swing.ImageIcon(""));
	color2.setActionCommand("line");
	color2.setMargin(new java.awt.Insets(0, 0, 0, 0));
	color2.setMaximumSize(new java.awt.Dimension(16, 16));
	color2.setMinimumSize(new java.awt.Dimension(16, 16));
	color2.setPreferredSize(new java.awt.Dimension(16, 16));
	color2.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mouseClicked(java.awt.event.MouseEvent evt) {
		color2MouseClicked(evt);
	    }
	});

	gridBagConstraints8 = new java.awt.GridBagConstraints();
	gridBagConstraints8.gridx = 7;
	gridBagConstraints8.gridy = 1;
	color_menu.add(color2, gridBagConstraints8);

		color3.setBackground(new java.awt.Color(60, 60, 60));
	color3.setIcon(new javax.swing.ImageIcon(""));
	color3.setActionCommand("line");
	color3.setMargin(new java.awt.Insets(0, 0, 0, 0));
	color3.setMaximumSize(new java.awt.Dimension(16, 16));
	color3.setMinimumSize(new java.awt.Dimension(16, 16));
	color3.setPreferredSize(new java.awt.Dimension(16, 16));
	color3.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mouseClicked(java.awt.event.MouseEvent evt) {
		color3MouseClicked(evt);
	    }
	});

	gridBagConstraints8 = new java.awt.GridBagConstraints();
	gridBagConstraints8.gridx = 8;
	gridBagConstraints8.gridy = 0;
	color_menu.add(color3, gridBagConstraints8);

		color4.setBackground(new java.awt.Color(80, 80, 80));
	color4.setIcon(new javax.swing.ImageIcon(""));
	color4.setActionCommand("line");
	color4.setMargin(new java.awt.Insets(0, 0, 0, 0));
	color4.setMaximumSize(new java.awt.Dimension(16, 16));
	color4.setMinimumSize(new java.awt.Dimension(16, 16));
	color4.setPreferredSize(new java.awt.Dimension(16, 16));
	color4.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mouseClicked(java.awt.event.MouseEvent evt) {
		color4MouseClicked(evt);
	    }
	});

	gridBagConstraints8 = new java.awt.GridBagConstraints();
	gridBagConstraints8.gridx = 8;
	gridBagConstraints8.gridy = 1;
	color_menu.add(color4, gridBagConstraints8);

		color5.setBackground(new java.awt.Color(100, 100, 100));
	color5.setIcon(new javax.swing.ImageIcon(""));
	color5.setActionCommand("line");
	color5.setMargin(new java.awt.Insets(0, 0, 0, 0));
	color5.setMaximumSize(new java.awt.Dimension(16, 16));
	color5.setMinimumSize(new java.awt.Dimension(16, 16));
	color5.setPreferredSize(new java.awt.Dimension(16, 16));
	color5.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mouseClicked(java.awt.event.MouseEvent evt) {
		color5MouseClicked(evt);
	    }
	});

	gridBagConstraints8 = new java.awt.GridBagConstraints();
	gridBagConstraints8.gridx = 9;
	gridBagConstraints8.gridy = 0;
	color_menu.add(color5, gridBagConstraints8);

		color6.setBackground(new java.awt.Color(120, 120, 120));
	color6.setIcon(new javax.swing.ImageIcon(""));
	color6.setActionCommand("line");
	color6.setMargin(new java.awt.Insets(0, 0, 0, 0));
	color6.setMaximumSize(new java.awt.Dimension(16, 16));
	color6.setMinimumSize(new java.awt.Dimension(16, 16));
	color6.setPreferredSize(new java.awt.Dimension(16, 16));
	color6.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mouseClicked(java.awt.event.MouseEvent evt) {
		color6MouseClicked(evt);
	    }
	});

	gridBagConstraints8 = new java.awt.GridBagConstraints();
	gridBagConstraints8.gridx = 9;
	gridBagConstraints8.gridy = 1;
	color_menu.add(color6, gridBagConstraints8);

		color7.setBackground(new java.awt.Color(140, 140, 140));
	color7.setIcon(new javax.swing.ImageIcon(""));
	color7.setActionCommand("line");
	color7.setMargin(new java.awt.Insets(0, 0, 0, 0));
	color7.setMaximumSize(new java.awt.Dimension(16, 16));
	color7.setMinimumSize(new java.awt.Dimension(16, 16));
	color7.setPreferredSize(new java.awt.Dimension(16, 16));
	color7.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mouseClicked(java.awt.event.MouseEvent evt) {
		color7MouseClicked(evt);
	    }
	});

	gridBagConstraints8 = new java.awt.GridBagConstraints();
	gridBagConstraints8.gridx = 10;
	gridBagConstraints8.gridy = 0;
	color_menu.add(color7, gridBagConstraints8);

//new color added

	gridBagConstraints7 = new java.awt.GridBagConstraints();
	colors.add(color_menu, gridBagConstraints7);

	current_colors.setLayout(new java.awt.GridBagLayout());
	java.awt.GridBagConstraints gridBagConstraints9;

	right.setBackground(java.awt.Color.white);
	right.setIcon(new javax.swing.ImageIcon(""));
	right.setActionCommand("line");
	right.setMargin(new java.awt.Insets(0, 0, 0, 0));
	right.setMaximumSize(new java.awt.Dimension(32, 32));
	right.setMinimumSize(new java.awt.Dimension(32, 32));
	right.setPreferredSize(new java.awt.Dimension(32, 32));
	gridBagConstraints9 = new java.awt.GridBagConstraints();
	gridBagConstraints9.gridx = 1;
	gridBagConstraints9.gridy = 0;
	current_colors.add(right, gridBagConstraints9);

	left.setBackground(java.awt.Color.black);
	left.setIcon(new javax.swing.ImageIcon(""));
	left.setActionCommand("line");
	left.setMargin(new java.awt.Insets(0, 0, 0, 0));
	left.setMaximumSize(new java.awt.Dimension(32, 32));
	left.setMinimumSize(new java.awt.Dimension(32, 32));
	left.setPreferredSize(new java.awt.Dimension(32, 32));
	gridBagConstraints9 = new java.awt.GridBagConstraints();
	gridBagConstraints9.gridx = 0;
	gridBagConstraints9.gridy = 0;
	current_colors.add(left, gridBagConstraints9);

	gridBagConstraints7 = new java.awt.GridBagConstraints();
	colors.add(current_colors, gridBagConstraints7);

	south.add(colors, java.awt.BorderLayout.WEST);

	status.setLayout(new java.awt.GridBagLayout());
	java.awt.GridBagConstraints gridBagConstraints10;

	position.setLayout(new java.awt.GridBagLayout());
	java.awt.GridBagConstraints gridBagConstraints11;

	cordinates.setText("( 0 , 0 )");
	cordinates.setMaximumSize(new java.awt.Dimension(100, 16));
	cordinates.setMinimumSize(new java.awt.Dimension(100, 16));
	cordinates.setPreferredSize(new java.awt.Dimension(100, 16));
	gridBagConstraints11 = new java.awt.GridBagConstraints();
	gridBagConstraints11.gridx = 3;
	gridBagConstraints11.gridy = 0;
	gridBagConstraints11.insets = new java.awt.Insets(0, 5, 0, 5);
	gridBagConstraints11.anchor = java.awt.GridBagConstraints.EAST;
	position.add(cordinates, gridBagConstraints11);

	gridBagConstraints10 = new java.awt.GridBagConstraints();
	gridBagConstraints10.gridx = 4;
	gridBagConstraints10.gridy = 0;
	status.add(position, gridBagConstraints10);
	Image main_image = tk.getImage(getClass().getResource("/images/mushroom.gif"));
	setIconImage(main_image);
	south.add(status, java.awt.BorderLayout.EAST);
	getContentPane().add(south, java.awt.BorderLayout.SOUTH);
	setJMenuBar(menu_container);

	// ming 4.26
	LinkedList list0 = new LinkedList();
	action_list.add(list0);
	LinkedList list1 = new LinkedList();
	redo_list.add(list1);
	// ming 4.26 end

    }

    /** HelpTopicsActionPerformed displays Help window
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent object from mouseclick
     */
    public void HelpTopicsActionPerformed(java.awt.event.ActionEvent evt){
    //String str = new String( "file:\\" + System.getProperty("user.dir") + "\\" + "help\\TerpPaintHelpTopics.html");
//     String str = new String( "file:\\" + "C:\\Documents and Settings\\wsouh\\Desktop\\2.0\\terppaintfile\\help" + "\\" + "TerpPaintHelpTopics.html");

    //System.out.println("str "+str);

	HelpWindow helpWindow = new HelpWindow( this );
	helpWindow.setVisible( true);


  //   HTMLDisplay helpdisplay = new HTMLDisplay( str, "TerpPaint Help" );
     //	  helpdisplay.setVisible( true );
    // System.out.println("help topics");
    }

    /** AboutActionPerformed displays a window with info about terppaint
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent object from mouseclick
     */
    public void AboutActionPerformed(java.awt.event.ActionEvent evt){
	about a = new about();
	System.out.println("about terp paint");
    }

    /** PrintPreviewActionPerformed shows print preview in new window
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent object from mouseclick
     */
    public void PrintPreviewActionPerformed(java.awt.event.ActionEvent evt){
	ourPrinter.setImage(center.getBufferedImage() ); /*FAULT:: ourPrinter.setImage(null); */
	ourPrinter.preview();
	System.out.println("about terp paint");
    }

    /** PageSetupActionPerformed shows page setup window through use of printer object
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent object from mouseclick
     */
    public void PageSetupActionPerformed(java.awt.event.ActionEvent evt){

	ourPrinter.pageSetup();
    }


//Istvan, needs layer call
    /** ImportActionPerformed performs "Paste From"
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent object from mouseclick
     */
    public void ImportActionPerformed(java.awt.event.ActionEvent evt){
		//System.out.println("importing");
		if(myPrefs.ToCurrentLayer){
		}
		else if(myPrefs.ToNewLayer){
			//add new layer here
		}
		else if(myPrefs.ToNewFile){//does nothing in the case of import, it makes no sense to use this here
		//as it would amount to having copied the file
		}else if(myPrefs.ToNewFileAndWindow){//this is basically just opening the file
			myPrefs.ToCurrentLayer=true;
			myPrefs.ToNewFileAndWindow=false;
			System.out.println(myContainer.size);
			NewActionPerformed(evt);
			System.out.println(myContainer.size);
			myContainer.Instances[myContainer.size-1].myPrefs.ToCurrentLayer=true;
			myContainer.Instances[myContainer.size-1].myPrefs.ToNewFileAndWindow=false;
			myContainer.Instances[myContainer.size-1].nonEmptyClipboard=true;
			if (currentTool == toolSelect)
				myContainer.Instances[myContainer.size-1].currentTool=myContainer.Instances[myContainer.size-1].toolSelect;
			else if (currentTool == toolSelectall)
				myContainer.Instances[myContainer.size-1].currentTool=myContainer.Instances[myContainer.size-1].toolSelectall;
			else if (currentTool == toolMagicSelect)
				myContainer.Instances[myContainer.size-1].currentTool=myContainer.Instances[myContainer.size-1].toolMagicSelect;
			myContainer.Instances[myContainer.size-1].ImportActionPerformed(evt);
			myContainer.Instances[myContainer.size-1].myPrefs.ToCurrentLayer=false;
			myContainer.Instances[myContainer.size-1].myPrefs.ToNewFileAndWindow=true;
			myPrefs.ToCurrentLayer=false;
			myPrefs.ToNewFileAndWindow=true;
			return;
		}
	JFileChooser chooser = new JFileChooser();
	chooser.setDialogType( 2 );
	chooser.setDialogTitle("Choose a file to import");
	chooser.setFileSelectionMode( 0 );
	TerpPaintFileFilter bmp = new TerpPaintFileFilter(new String[]{"bmp", "dib"}, "Monochrome Bitmap");
	TerpPaintFileFilter jpeg = new TerpPaintFileFilter(new String[]{"jpeg", "jpg"}, "JPEG Image");
	TerpPaintFileFilter gif = new TerpPaintFileFilter(new String("gif"), "GIF Image");
	TerpPaintFileFilter tpt = new TerpPaintFileFilter(new String("tpt"), "TerpPaint Picture");
	chooser.addChoosableFileFilter(tpt);
	chooser.addChoosableFileFilter(gif);
	chooser.addChoosableFileFilter(jpeg);
	chooser.addChoosableFileFilter(bmp);
	chooser.showDialog(this, "Ok" );

	File chosen = chooser.getSelectedFile();
	String importfile = chosen.getAbsolutePath();

	if(chosen == null){
	    return;
	}
	else{
	    if(chooser.getFileFilter() == bmp || chosen.getName().endsWith(".bmp") || chosen.getName().endsWith(".dib")){
		try{
		    FileInputStream fis = new FileInputStream(chosen);
		    converter newImage = new converter();
//		      center.noChanges();


		    BufferedImage selected = newImage.FileToBufferedImage(fis);
		    fis.close();

		    clipBoardWidth = selected.getWidth();
		    clipBoardHeight = selected.getHeight();

		    UMClipObj ourImage = new UMClipObj(selected);
		    Clipboard clip = getToolkit().getSystemClipboard();
		    clip.setContents(ourImage,ourImage);

		    //UMClipObj ourImage = new UMClipObj(selected);
		    //UMClipObj ourImage = (UMClipObj)clip.getContents(this);
		    Transferable obj = clip.getContents(this);
		    BufferedImage copiedImage = new BufferedImage(10,10,BufferedImage.TYPE_INT_RGB);
		    try {

			copiedImage = (BufferedImage)(obj.getTransferData( (DataFlavor) DataFlavor.imageFlavor));
			//copiedImage = (BufferedImage)obj.getTransferData(UMClipObj.imageFlavor);
			BufferedImage img = center.getBufferedImage();
			Graphics2D g2d = img.createGraphics();
			//g2d.drawImage(copiedImage,0,0,copiedImage.getWidth(),copiedImage.getHeight(),this);
			//center.setBufferedImage(img);
		    } catch (Exception e) { }
		    //operation.setText("selectall");
		    toggleProperties();
		    none.setVisible(true);
		    curButton.setBorder(BorderFactory.createRaisedBevelBorder());
		    curButton = selectall;
		    curButton.setBorder(BorderFactory.createLoweredBevelBorder());
		    currentTool = toolSelectall;
		    currentCursor = defaultCursor;

		    //	      center.repaint();
		    // ming 4.24
		    toolSelectall.setPastedImage(copiedImage,center,0,0);
		    // ming 4.24 end
		    center.repaint();
		}catch(Exception fisError){}
	    }

	    else if(chooser.getFileFilter() == jpeg || chosen.getName().endsWith(".jpeg") || chosen.getName().endsWith(".jpg")){
		try{
		    FileInputStream fis = new FileInputStream(chosen);
		    JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(fis);
		    //	   center.noChanges();
		    BufferedImage selected = decoder.decodeAsBufferedImage();
		    fis.close();
		    clipBoardWidth = selected.getWidth();
		    clipBoardHeight = selected.getHeight();

		    UMClipObj ourImage = new UMClipObj(selected);
		    Clipboard clip = getToolkit().getSystemClipboard();
		    clip.setContents(ourImage,ourImage);

		    //UMClipObj ourImage = new UMClipObj(selected);

		    //UMClipObj ourImage = (UMClipObj)clip.getContents(this);
		    Transferable obj = clip.getContents(this);
		    BufferedImage copiedImage = new BufferedImage(10,10,BufferedImage.TYPE_INT_RGB);
		    try {
			copiedImage = (BufferedImage)(obj.getTransferData( (DataFlavor) DataFlavor.imageFlavor));
			//copiedImage = (BufferedImage)obj.getTransferData(UMClipObj.imageFlavor);
			BufferedImage img = center.getBufferedImage();
			Graphics2D g2d = img.createGraphics();
			//g2d.drawImage(copiedImage,0,0,copiedImage.getWidth(),copiedImage.getHeight(),this);
			//center.setBufferedImage(img);
		    } catch (Exception e) { }
		    //operation.setText("selectall");
		    toggleProperties();
		    none.setVisible(true);
		    curButton.setBorder(BorderFactory.createRaisedBevelBorder());
		    curButton = selectall;
		    curButton.setBorder(BorderFactory.createLoweredBevelBorder());
		    currentTool = toolSelectall;
		    currentCursor = defaultCursor;

		    //	      center.repaint();
		    // ming 4.24
		    toolSelectall.setPastedImage(copiedImage,center,0,0);
		    // ming 4.24 end
		    center.repaint();
		    } catch(Exception fisError){}
	    }

	    else if(chooser.getFileFilter() == gif || chosen.getName().endsWith(".gif")){
		try{
		    BufferedImage selected =
		    ImageUtilities.getBufferedImage(chosen.getPath(),this);

		    clipBoardWidth = selected.getWidth();
		    clipBoardHeight = selected.getHeight();

		    UMClipObj ourImage = new UMClipObj(selected);
		    Clipboard clip = getToolkit().getSystemClipboard();
		    clip.setContents(ourImage,ourImage);

		    //UMClipObj ourImage = new UMClipObj(selected);

		    //UMClipObj ourImage = (UMClipObj)clip.getContents(this);
		    Transferable obj = clip.getContents(this);
		    BufferedImage copiedImage = new BufferedImage(10,10,BufferedImage.TYPE_INT_RGB);
		    try {
			copiedImage = (BufferedImage)(obj.getTransferData( (DataFlavor) DataFlavor.imageFlavor));
			//copiedImage = (BufferedImage)obj.getTransferData(UMClipObj.imageFlavor);
			BufferedImage img = center.getBufferedImage();
			Graphics2D g2d = img.createGraphics();
			//g2d.drawImage(copiedImage,0,0,copiedImage.getWidth(),copiedImage.getHeight(),this);
			//center.setBufferedImage(img);
		    } catch (Exception e) { }
		    //operation.setText("selectall");
		    toggleProperties();
		    none.setVisible(true);
		    curButton.setBorder(BorderFactory.createRaisedBevelBorder());
		    curButton = selectall;
		    curButton.setBorder(BorderFactory.createLoweredBevelBorder());
		    currentTool = toolSelectall;
		    currentCursor = defaultCursor;

		    //	      center.repaint();
		    // ming 4.24
		    toolSelectall.setPastedImage(copiedImage,center,0,0);
		    // ming 4.24 end
		    center.repaint();

		    }catch(Exception e){
			e.printStackTrace();
		    }
	    }
	    else if(chooser.getFileFilter() == tpt || chosen.getName().endsWith(".tpt")){
		try{//needs tpt format
		    BufferedImage selected =
		    ImageUtilities.getBufferedImage(chosen.getPath(),this);

		    clipBoardWidth = selected.getWidth();
		    clipBoardHeight = selected.getHeight();

		    UMClipObj ourImage = new UMClipObj(selected);
		    Clipboard clip = getToolkit().getSystemClipboard();
		    clip.setContents(ourImage,ourImage);

		    //UMClipObj ourImage = new UMClipObj(selected);

		    //UMClipObj ourImage = (UMClipObj)clip.getContents(this);
		    Transferable obj = clip.getContents(this);
		    BufferedImage copiedImage = new BufferedImage(10,10,BufferedImage.TYPE_INT_RGB);
		    try {
			copiedImage = (BufferedImage)(obj.getTransferData( (DataFlavor) DataFlavor.imageFlavor));
			//copiedImage = (BufferedImage)obj.getTransferData(UMClipObj.imageFlavor);
			BufferedImage img = center.getBufferedImage();
			Graphics2D g2d = img.createGraphics();
			//g2d.drawImage(copiedImage,0,0,copiedImage.getWidth(),copiedImage.getHeight(),this);
			//center.setBufferedImage(img);
		    } catch (Exception e) { }
		    //operation.setText("selectall");
		    toggleProperties();
		    none.setVisible(true);
		    curButton.setBorder(BorderFactory.createRaisedBevelBorder());
		    curButton = selectall;
		    curButton.setBorder(BorderFactory.createLoweredBevelBorder());
		    currentTool = toolSelectall;
		    currentCursor = defaultCursor;

		    //	      center.repaint();
		    // ming 4.24
		    toolSelect.setPastedImage(copiedImage,center,0,0);
		    // ming 4.24 end
		    center.repaint();

		    }catch(Exception e){
			e.printStackTrace();
		    }
	    }else{
		//This is not a valid file
	    }
	}

	System.out.println("import image");
    }
//Istvan needs layer call
    /** ExportActionPerformed performs "Copy To"
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent object from mouseclick
     */
    public void ExportActionPerformed(java.awt.event.ActionEvent evt){

		if( !toolSelect.isSelected() && !toolSelectall.isSelected()&&!toolMagicSelect.isSelected() )
	{
	    return;
	}
	else
	{//note can't really make animation out of one frame on clipboard so no gifa
	    JFileChooser chooser = new JFileChooser();
	    TerpPaintFileFilter bmp = new TerpPaintFileFilter(new String[]{"bmp", "dib"}, "Monochrome Bitmap");
	    TerpPaintFileFilter jpeg = new TerpPaintFileFilter(new String[]{"jpeg", "jpg"}, "JPEG Image");
	    TerpPaintFileFilter gif = new TerpPaintFileFilter(new String("gif"), "GIF Image");
			TerpPaintFileFilter tpt = new TerpPaintFileFilter(new String("tpt"), "TerpPaint Picture");
			chooser.addChoosableFileFilter(tpt);
	    chooser.addChoosableFileFilter(gif);
	    chooser.addChoosableFileFilter(jpeg);
	    chooser.addChoosableFileFilter(bmp);

	    if(CURRENT_FILE.endsWith(".jpg") || CURRENT_FILE.endsWith(".jpeg"))
		chooser.setFileFilter(jpeg);
	    else if(CURRENT_FILE.endsWith(".gif"))
		chooser.setFileFilter(gif);
	    else if(CURRENT_FILE.endsWith(".tpt"))
		chooser.setFileFilter(tpt);
	    else
		chooser.setFileFilter(bmp);

	    chooser.setDialogTitle("Choose a file to export to");
	    chooser.showSaveDialog(this);
	    File saveFile = chooser.getSelectedFile();

	    if(saveFile == null){
		return;
	    }
	    else{
		//Add the extension to the filename if it isn't already there
		if(chooser.getFileFilter() == bmp){
		      if(!saveFile.getName().endsWith(".bmp")){
			//They don't have the extension
		       saveFile = new File(saveFile.getAbsolutePath()+".bmp");
		      }
		      if(myPrefs.OnlyIfExists){
						try{
				FileInputStream fis = new FileInputStream(saveFile.getAbsolutePath());
				fis.close();
			} catch(Exception fisError){//no file
			return;}
					  }
		      try{
			FileOutputStream fos = new FileOutputStream(saveFile.getAbsolutePath());
			converter newImage = new converter();
			if( currentTool == toolSelect )
			    newImage.BufferedImageToFile(fos, toolSelect.getCopyImage(center), 0);
			if( currentTool == toolSelectall )
			    newImage.BufferedImageToFile(fos, toolSelectall.getCopyImage(center), 0);
			if( currentTool == toolMagicSelect )
			    newImage.BufferedImageToFile(fos, toolMagicSelect.getCopyImage(center), 0);
			fos.flush();
			fos.close();
		      } catch(Exception fosError){}
		}
				if(chooser.getFileFilter() == tpt){
		      if(!saveFile.getName().endsWith(".tpt")){
			//They don't have the extension
		       saveFile = new File(saveFile.getAbsolutePath()+".tpt");
		      }
		      if(myPrefs.OnlyIfExists){
						try{
				FileInputStream fis = new FileInputStream(saveFile.getAbsolutePath());
				fis.close();
			} catch(Exception fisError){//no file
			return;}
					  }
		      try{
		       //save as tpt
		      } catch(Exception fosError){}
		}
		if(chooser.getFileFilter() == jpeg){
		    if((!saveFile.getName().endsWith(".jpeg"))
			&&(!saveFile.getName().endsWith(".jpg"))){
			//They don't have the extension
			  saveFile = new File(saveFile.getAbsolutePath()+".jpeg");
		    }
					if(myPrefs.OnlyIfExists){
						try{
				FileInputStream fis = new FileInputStream(saveFile.getAbsolutePath());
				fis.close();
			} catch(Exception fisError){//no file
			return;}
					}
		    try{
			FileOutputStream fos = new FileOutputStream(saveFile.getAbsolutePath());
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
			if( currentTool == toolSelect )
			   encoder.encode(toolSelect.getCopyImage(center));
			if( currentTool == toolSelectall )
			   encoder.encode(toolSelectall.getCopyImage(center));
						if( currentTool == toolMagicSelect )
			   encoder.encode(toolMagicSelect.getCopyImage(center));
			fos.flush();
			fos.close();
		    }
		    catch(Exception fosError){}
		}

		if(chooser.getFileFilter() == gif){
		    if(!saveFile.getName().endsWith(".gif")){
		    //They don't have the extension
		    saveFile = new File(saveFile.getAbsolutePath()+".gif");
		    }
					if(myPrefs.OnlyIfExists){
						try{
				FileInputStream fis = new FileInputStream(saveFile.getAbsolutePath());
				fis.close();
			} catch(Exception fisError){//no file
			return;}
					}
		   try{
			FileOutputStream fos = new FileOutputStream(saveFile.getAbsolutePath());
						/*Ana*/
						// Note: Even though it is called AnimatedGifEncoder, it also encodes regular gif files.
			AnimatedGifEncoder encoder = new AnimatedGifEncoder();
			encoder.start(fos);
			if( currentTool == toolSelect )
			    encoder.addFrame(toolSelect.getCopyImage(center));
			if( currentTool == toolSelectall )
			    encoder.addFrame(toolSelectall.getCopyImage(center));
						if( currentTool == toolMagicSelect )
			    encoder.addFrame(toolMagicSelect.getCopyImage(center));
			encoder.finish();
			/*End Ana*/
			fos.close();
		    }
		    catch(Exception fosError){}
		}

		//	    CURRENT_FILE = saveFile.getName();
		//	    this.setTitle("TerpPaint - "+saveFile.getName());
	     }
	}
    }

    /** This function is called if it wants to display a animation of layers. It
     * has a parameter of an ActionEvent.  It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt an ActionEvent object.
     */
    public void AnimationActionPerformed(java.awt.event.ActionEvent evt) {
      if (center.layerList.size()==0) // add the current buffered image
	center.layerList.add(center.getBufferedImage() );
      else // update the last buffered image
	center.layerList.set(center.currentLayer,center.getBufferedImage());
      System.out.println(center.layerList.size()+"size" + " anim "+ ((int)myPrefs.Anim));
      System.out.println(center.currentLayer+" center.currentLayer");
      LogoAnimator animation = new LogoAnimator();// Ronald phase 5
      //Ronald 5.2   // create LogoAnimator
      animation.runAnimation(this, center.layerList, (int)myPrefs.Anim, myPrefs.Transition, myPrefs.CycleNumber);
    }
    //Ronald phase 4

//Ronald ends phase 4
    /** This function is called if it wants to display a slide show of different bitmaps or jpegs. It
     * has a parameter of an ActionEvent.  It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt an ActionEvent object.
     */
    public void SlideShowActionPerformed(java.awt.event.ActionEvent evt) {
      //	this.setVisible(false);
      //	setVisible(false);
      //  SlideProjector m = new SlideProjector();
      SlideShow m = new SlideShow(this,true);


      //	m.play();
      //	this.setVisible(true);
    }

    /** This function will perform 2 operation. It will do the clear action and draw opaque action.
     * It takes in an ActionEvent object as a parameter.  It should work with all operating systems
     * and hardware. There are no variances and no security constraints.
     * @param evt an ActionEvent obj.
     */
    public void CustomActionPerformed(java.awt.event.ActionEvent evt) {
	// Add your handling code here:
    }
//Istvan
    /** This function is called when the user wants to clear the painting. It has a parameter ActionEvent object.  It should
     * work with all operating systems and hardware. There are no variances and no
     * security constraints.
     * @param evt an ActionEvent obj.
     */
    public void ClearSelectionActionPerformed(ActionEvent evt) {
	// ming 4.26
			LinkedList cur_action_list = (LinkedList)(action_list.get(center.currentLayer));
			LinkedList cur_redo_list = (LinkedList)(redo_list.get(center.currentLayer));
	if (curButton == select) {
	    toolSelect.clear(center);
	    /* ming */

	    int temp = cur_redo_list.size();
	    for (int i=0; i<temp; i++)
		cur_redo_list.removeLast();
	    cur_action_list.add(new String("Clear selection"));
	    updateUndoList();
	   // initComponents();
	    /* ming */
	} else if (curButton == selectall) {
	    toolSelectall.clear(center);
	    /* ming */
	    int temp = cur_redo_list.size();
	    for (int i=0; i<temp; i++)
		cur_redo_list.removeLast();
	    cur_action_list.add(new String("Clear selection"));
	    updateUndoList();
	   // initComponents();
	    /* ming */
	}else if (curButton == magicSelect) {
	    toolMagicSelect.clear(center);
	    /* ming */
	    int temp = cur_redo_list.size();
	    for (int i=0; i<temp; i++)
		cur_redo_list.removeLast();
	    cur_action_list.add(new String("Clear selection"));
	    updateUndoList();
	   // initComponents();
	    /* ming */
	}
	center.repaint();
    }

    /** This function will draw an Opaque. It has a parameter ActionEvent obj.
     * It should work with all operating systems and hardware. There are no variances
     * and no security constraints.
     * @param evt an ActionEvent obj.
     */
    public void DrawOpaqueActionPerformed(ActionEvent evt) {
	toolSelect.setOpaque(DrawOpaque.getState()); /*FAULT:: toolSelect.drawOpaque = false; */
	toolSelectall.setOpaque(DrawOpaque.getState());
	toolMagicSelect.setOpaque(DrawOpaque.getState());
    }

    /** This function will zoom you to the smallest available view and then set the
     * background to light gray. Then, the view of the zoom is kept and saved. It has
     * a parameter ActionEvent object.	It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt an ActionEvent obj.
     */
    public void NormalActionPerformed(java.awt.event.ActionEvent evt) {
	zoom1ActionPerformed(evt);
	curZoom.setBackground(Color.lightGray);
	curZoom = zoom1; /*FAULT:: curZoom = zoom3; */
	curZoom.setBackground(propColor);
    }

    /** This function will zoom you to the second largest available view and then set the
     * background to light gray. Then, the view of the zoom is kept and saved. It has
     * a parameter ActionEvent object.	It should work with all operating systems and
     * hardware. There are no variances and no security constraints.
     * @param evt an ActionEvent obj.
     */
    public void LargeActionPerformed(java.awt.event.ActionEvent evt) {
	zoom3ActionPerformed(evt);
	curZoom.setBackground(Color.lightGray);
	curZoom = zoom3; /*FAULT:: curZoom = zoom1; */
	curZoom.setBackground(propColor);
    }

    /** This function allows you to save the painting that you have created. It calls the save function.
     * It has a parameter of an ActionEvent object.  It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt an ActionEvent obj.
     */
    public void SaveActionPerformed(java.awt.event.ActionEvent evt) {
	//if (center.madeChanges()){
	    if (filename.compareTo("null") != 0)
		saveFast();
	    else
		save();
	//}

	/*
	if(center.madeChanges() && filename.compareTo("null") != 0 )
	    saveFast();
	else if (!center.madeChanges() && filename.compareTo("null") != 0 ) {}
	else
	    save();
	 */
	//center.noChanges();

    }

    /** This function allows you to spray like a can. This function
     * set the spray size to 23. It has an ActionEvent object as a parameter.
     * It should work with all operating systems and hardware. There are no variances
     * and no security constraints.
     * @param evt an ActionEvent obj.
     */
    public void spray3ActionPerformed(java.awt.event.ActionEvent evt) {
	toolSpray.setSprayType(23);
	curSpray.setBackground(Color.lightGray);
	curSpray = spray3; /*FAULT:: curSpray = spray2; */
	curSpray.setBackground(propColor);
    }

    /** This function allows you to spray like a can. This function
     * set the spray size to 8.It has an ActionEvent object as a parameter.
     * It should work with all operating systems and hardware. There are no
     * variances and no security constraints.
     * @param evt an ActionEvent obj.
     */
    public void spray1ActionPerformed(java.awt.event.ActionEvent evt) {
	toolSpray.setSprayType(8);
	curSpray.setBackground(Color.lightGray);
	curSpray = spray1; /*FAULT:: curSpray = spray2; */
	curSpray.setBackground(propColor);
    }

    /** This function allows you to see the drawing you have as a wallpaper on your computer.
     * The drawing will cover the whole screen of your computer. It has an ActionEvent object
     * as a parameter.	It should work with all operating systems and hardware. There are no variances
     * and no security constraints.
     * @param evt an ActionEvent obj.
     */
    public void ViewBitmapActionPerformed(java.awt.event.ActionEvent evt) {
	// Add your handling code here:
	viewBitmap view = new viewBitmap(this, true);
	view.setVisible(true);
    }


    /** This function allows you to do the pasting action. It has an ActionEvent object
     * as a parameter.	It should work with all operating systems and hardware. There
     * are no variances and no security constraints.
     * @param evt an ActionEvent obj.
     */
    public void PasteActionPerformed(java.awt.event.ActionEvent evt)
    {
	System.out.println("pasting");

	/* Get the clipboard from the platform */
	Clipboard clipbrd = getToolkit().getSystemClipboard();
	Transferable clip = clipbrd.getContents(this);
	
	/* Create a buffered image from the clip.  Throws exception if clip is not 
	 * transferrable to an image
	 */
	BufferedImage copiedImage =
	    new BufferedImage(10,10,BufferedImage.TYPE_INT_RGB);
	try {
	    copiedImage =
		(BufferedImage)clip.getTransferData(DataFlavor.imageFlavor);
	} catch (Exception e) {
	    System.err.println(e);
	    return;
	}

	if (myPrefs.ToCurrentLayer) {
	    selectallActionPerformed(evt);
	    toolSelectall.setPastedImage(copiedImage, center, 0, 0);
	}
	else if (myPrefs.ToNewLayer) {
	    AddActionPerformed(evt);
	    center.setBufferedImage(copiedImage);
	}
	else if(myPrefs.ToNewFile) {
	    saveImage(copiedImage);
	}
	else { //if myPrefs.toNewFileAndWindow
	    NewActionPerformed(evt);
	    myContainer.newInstance.center.setBufferedImage(copiedImage);
	}

	/* Update redo and undo lists if main_canvas was modified */
	if (myPrefs.ToCurrentLayer || myPrefs.ToNewFileAndWindow) {
	    LinkedList cur_action_list = (LinkedList)(action_list.get(center.currentLayer));
	    LinkedList cur_redo_list = (LinkedList)(redo_list.get(center.currentLayer));
	    int redo_s = cur_redo_list.size();
	    for (int i=0; i<redo_s; i++)
		cur_redo_list.removeLast();
	    
	    if (cur_action_list.size()>0 && cur_action_list.getLast().equals("Select"))
		cur_action_list.removeLast();
	    
	    cur_action_list.add(new String("Paste"));
	    updateUndoList();
	}
    }

    /** This function changes all the colors to the buttons to grayscale if true, to color if false. It also initializes the big left and
     * right button which we will use to paint. It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param toggle boolean
     */
    public void changeToGray( boolean toggle ){
	if( toggle ) {

	    changeButtonGray(red); /**FAULT:: changeButtonGray(brown); */
	    changeButtonGray(green);
	    changeButtonGray(blue);
	    changeButtonGray(yellow);
	    changeButtonGray(magenta);
	    changeButtonGray(cyan);
	    changeButtonGray(pink);
	    changeButtonGray(white);
	    changeButtonGray(lgt_gray);
	    changeButtonGray(gray);
	    changeButtonGray(drk_gray);
	    changeButtonGray(black);
	    changeButtonGray(brown);
	    changeButtonGray(orange);
  //new color add

	    changeButtonGray(white);
			changeButtonGray(lgt_gray);
			changeButtonGray(gray);
			changeButtonGray(drk_gray);
			changeButtonGray(black);
			changeButtonGray(brown);
	    changeButtonGray(orange);
    //new color added
		changeButtonGray(left);
	    changeButtonGray(right);
	    //****************** CHANGES HERE
	    center.setLeftColor(left.getBackground());
	    center.setRightColor(right.getBackground());

	}
	else {
	    red.setBackground( (Color) colorTable.elementAt( 0 ) );
	    green.setBackground( (Color) colorTable.elementAt( 1 ) );
	    blue.setBackground( (Color) colorTable.elementAt( 2 ) );
	    yellow.setBackground( (Color) colorTable.elementAt( 3 ) );
	    magenta.setBackground( (Color) colorTable.elementAt( 4 ) );
	    cyan.setBackground( (Color) colorTable.elementAt( 5 ) );
	    pink.setBackground( (Color) colorTable.elementAt( 6 ) );
	    white.setBackground( (Color) colorTable.elementAt( 7 ) );
	    lgt_gray.setBackground( (Color) colorTable.elementAt( 8 ) );
	    gray.setBackground( (Color) colorTable.elementAt( 9 ) );
	    drk_gray.setBackground( (Color) colorTable.elementAt( 10 ) );
	    black.setBackground( (Color) colorTable.elementAt( 11 ) );
	    brown.setBackground( (Color) colorTable.elementAt( 12 ) );
	    orange.setBackground( (Color) colorTable.elementAt( 13 ) );
 //new color add
			white.setBackground( (Color) colorTable.elementAt( 14 ) );
	     lgt_gray.setBackground( (Color) colorTable.elementAt(15 ) );
	     gray.setBackground( (Color) colorTable.elementAt( 16 ) );
	     drk_gray.setBackground( (Color) colorTable.elementAt( 17 ) );
	     black.setBackground( (Color) colorTable.elementAt( 18 ) );
	     brown.setBackground( (Color) colorTable.elementAt( 19 ) );
	     orange.setBackground( (Color) colorTable.elementAt( 20 ) );


	     left.setBackground( (Color) colorTable.elementAt( 21 ) );
	     right.setBackground( (Color) colorTable.elementAt( 22 ) );
	     //new color added
	}
    }

    /** This function turns a button gray
     * It has a button object as a parameter, which is to be colored.
     * It should work with all operating systems and hardware. There are no variances
     * and no security constraints.
     * @param in a JButton to be colored.
     */
    public void changeButtonGray(JButton in){
	Color back = in.getBackground();
	int avg = (back.getRed() + back.getGreen() + back.getBlue())/3;
	Color newBack = new Color(avg,avg,avg);
	in.setBackground(newBack);
    }
//Istvan
    /** This function allows you to execute a maximize or minimize the
     * painting window. It allows you to change the unit to cm, inches or
     * pixels. You can also have a choice of color or just black and white.
     * It has an ActionEvent object as a parameter. It should work with all
     * operating systems and hardware. There are no variances and no security constraints.
     * @param evt an ActionEvent obj.
     */

    public void AttributesActionPerformed(java.awt.event.ActionEvent evt) {
	// Add your handling code here:
		if (curButton == select) {
	    toolSelect.deSelect(center);
	} else if (curButton == selectall) {
	    toolSelectall.deSelect(center); /**fault toolSelectall.selectall(center);*/
	} else if (curButton == magicSelect) {
	    toolMagicSelect.deSelect(center);
	} else if (curButton == curve) {
	    toolCurve.deSelect(center);
	} else if (curButton == polygon) {
	    toolPolygon.deSelect(center);
	}
	attributes attrib = new attributes(this, true);
	attrib.setLocation( (int) this.getLocation().getX() + 100, (int) this.getLocation().getY()+100 );
	attrib.setTitle("Attributes");
	attrib.setVisible(true);
		try
		{
		     FileWriter out = new FileWriter("windowRegistry.txt");
		     BufferedWriter b = new BufferedWriter( out );


		     Double xd = new Double( this.getLocation().getX() );
		     Double yd = new Double(this.getLocation().getY()) ;
			 Double wd = new Double(this.getSize().getWidth());
		     Double hd = new Double(this.getSize().getHeight());
		     Double iwd = new Double(center.widt);
			 Double ihd = new Double(center.heig);


		     b.write( xd.intValue() + "\n" +
			    yd.intValue() + "\n" +
					wd.intValue() + "\n" +
					hd.intValue() + "\n"+
					iwd.intValue() + "\n" +
					ihd.intValue() + "\n" );
			 b.close();
		 }
	 catch( Exception e ) {}
       // ming 4.22
       // ming 5.7
	if (!attrib.ok_action)
		{
		     JDialog my_message = new JDialog();
			    my_message.setLocation( (int) my_message.getLocation().getX()+300, (int) my_message.getLocation().getY()+215);
			    my_message.setSize(300, 100);
			    my_message.setTitle("Help Message");
			    JLabel NameLabel = new JLabel("   The picture can not be changed too big, :)");
			    my_message.getContentPane().add(NameLabel);
			    my_message.setVisible(true);
	}
	// ming 5.7 end
	// ming 4.22 end
	//initComponents();
	/* ming */

    }

    /** This function allows you to flip and rotate your drawing. You can flip
     * horizontally, vertically or rotate by the degree of angle you want.
     * It has an ActionEvent object as a parameter. It should work with all
     * operating systems and hardware. There are no variances and no security
     * constraints.
     * @param evt an ActionEvent obj.
     */
    public void FlipRotateActionPerformed(java.awt.event.ActionEvent evt) {
	// Add your handling code here:
		if (curButton == select) {
	    toolSelect.deSelect(center);
	} else if (curButton == selectall) {
	    toolSelectall.deSelect(center);
	} else if (curButton == magicSelect) {
	    toolMagicSelect.deSelect(center);
	} else if (curButton == curve) {
	    toolCurve.deSelect(center);
	} else if (curButton == polygon) {
	    toolPolygon.deSelect(center);
	}
	rotate rot = new rotate(this, true);
	rot.setVisible(true);
    }

//Istvan
    /** this function allows you to do the cut action. It has a parameter
     * of an ActionEvent object.  It should work with all operating systems
     * and hardware. There are no variances and no security constraints.
     * @param evt an ActionEvent obj.
     */
    public void CutActionPerformed(java.awt.event.ActionEvent evt) {
	BufferedImage selected;
	// ming 4.26
			LinkedList cur_action_list = (LinkedList)(action_list.get(center.currentLayer));
			LinkedList cur_redo_list = (LinkedList)(redo_list.get(center.currentLayer));

		if ((currentTool == toolSelect) || (currentTool == toolSelectall)||(currentTool==toolMagicSelect)) {
	    if (currentTool == toolSelect) {
		selected = toolSelect.getCutImage(center);
	    } else if(currentTool==toolMagicSelect){
		selected = toolMagicSelect.getCutImage(center);
	    }else {
		selected = toolSelectall.getCutImage(center);
	    }
	    clipBoardWidth = selected.getWidth();
	    clipBoardHeight = selected.getHeight();
	    UMClipObj ourImage = new UMClipObj(selected);
	    Clipboard clip = getToolkit().getSystemClipboard();
	    clip.setContents(ourImage,ourImage);

	    nonEmptyClipboard = true;
	    /* ming */
	    int temp = cur_redo_list.size();
	    for (int i=0; i<temp; i++)
		cur_redo_list.removeLast();
	    // ming 5.2
	    if (cur_action_list.size()>0 && cur_action_list.getLast().equals("Select"))
		cur_action_list.removeLast();
	    cur_action_list.add(new String("Cut"));
	    //cur_action_list.add(new String("Cut to clipboard"));
	    // ming 5.2 end
	    updateUndoList();
	    //initComponents();
	    /* ming */
	}

    }
//Istvan
    /** This function allows you to copy the drawing that you have. It has an
     *
     * ActionEvent object as a parameter. It should work with all operating
     * systems and hardware. There are no variances and no security constraints.
     * @param evt an ActionEvent obj.
     */
    public void CopyActionPerformed(java.awt.event.ActionEvent evt) {
	// Add your handling code here:
	BufferedImage selected;

		if ((currentTool == toolSelect) || (currentTool == toolSelectall)||(currentTool==toolMagicSelect)) {
	    if (currentTool == toolSelect) {
		selected = toolSelect.getCopyImage(center);
	    } else if(currentTool==toolMagicSelect){
		selected = toolMagicSelect.getCopyImage(center);
	    }else {
		selected = toolSelectall.getCopyImage(center);
	    }
	    clipBoardWidth = selected.getWidth();
	    clipBoardHeight = selected.getHeight();
	    UMClipObj ourImage = new UMClipObj(selected);
	    Clipboard clip = getToolkit().getSystemClipboard();
	    clip.setContents(ourImage,ourImage);

	    nonEmptyClipboard = true;
	    /* ming */
	    /*int temp = redo_list.size();
	    for (int i=0; i<temp; i++)
		redo_list.removeLast();
	    action_list.add(new String("Copy"));
	    updateUndoList();
	    //initComponents();*/
	    /* ming */
	}

    }








    //#VERSION 3#
	/** This function adds a new layer
     *
     * ActionEvent object as a parameter. It should work with all operating
     * systems and hardware. There are no variances and no security constraints.
     * @param evt an ActionEvent obj.
     */

    public void AddActionPerformed(java.awt.event.ActionEvent evt) {
      alllayers.addActionPerformed(evt);
      // ming 4.26
    //	center.setBufferedImage(center.getBufferedImage());
      // ming 4.26 end
    }
	/** This function removes a layer
     *
     * ActionEvent object as a parameter. It should work with all operating
     * systems and hardware. There are no variances and no security constraints.
     * @param evt an ActionEvent obj.
     */

    public void RemoveActionPerformed(java.awt.event.ActionEvent evt) {
	alllayers.removeActionPerformed(evt);
    }
    /** This function flatten all layers
     *
     * ActionEvent object as a parameter. It should work with all operating
     * systems and hardware. There are no variances and no security constraints.
     * @param evt an ActionEvent obj.
     */

    public void FlattenActionPerformed(java.awt.event.ActionEvent evt) {
	alllayers.flattenActionPerformed(evt); // Ronald 5.5
    }

















    /** This function allows you to zoom at 8X.	 This is the biggest
     * zoom capability that this class has. It has an ActionEvent
     * object as a parameter.  It should work with all operating systems
     * and hardware. There are no variances and no security constraints.
     * @param evt an ActionEvent obj.
     */
    public void zoom4ActionPerformed(java.awt.event.ActionEvent evt) {
	center.setOldZoom(center.getZoom());
	center.setZoom(8);
	curZoom.setBackground(Color.lightGray);
	curZoom = zoom4;
	curZoom.setBackground(propColor);
	center.repaint();
    }

    /** This function allows you to zoom at 4X.	 This is the second
     * biggest zoom capability that this class has. It has an ActionEvent
     * object as a parameter.  It should work with all operating systems
     * and hardware. There are no variances and no security constraints.
     * @param evt an ActionEvent obj.
     */
    public void zoom3ActionPerformed(java.awt.event.ActionEvent evt) {
	center.setOldZoom(center.getZoom());
	center.setZoom(4);
	curZoom.setBackground(Color.lightGray);
	curZoom = zoom3; /*FAULT:: curZoom = zoom4; */
	curZoom.setBackground(propColor);
	center.repaint();
    }

    /** This function allows you to zoom at 2X.	 This is the second
     * smallest zoom capability that this class has. It has an ActionEvent
     * object as a parameter.  It should work with all operating systems and
     * hardware. There are no variances and no security constraints.
     * @param evt an ActionEvent obj.
     */
    public void zoom2ActionPerformed(java.awt.event.ActionEvent evt) {
	center.setOldZoom(center.getZoom());
	center.setZoom(2);
	curZoom.setBackground(Color.lightGray);
	curZoom = zoom2; /*FAULT:: curZoom = zoom4; */
	curZoom.setBackground(propColor);
	center.repaint();
    }

    /** This function allows you to zoom at 1X which is the normal rate.
     * This is the smallest zoom capability that this class has. It has an
     * ActionEvent object as a parameter.  It should work with all operating
     * systems and hardware. There are no variances and no security constraints.
     * @param evt an ActionEvent obj.
     */
    public void zoom1ActionPerformed(java.awt.event.ActionEvent evt) {
	center.setOldZoom(center.getZoom());
	center.setZoom(1);
	curZoom.setBackground(Color.lightGray);
	curZoom = zoom1; /*FAULT:: curZoom = zoom4; */
	curZoom.setBackground(propColor);
	center.repaint();
    }

    /** This function will execute if you click on the brush icon and
     * then another icon which is at row 4 column 1 at the
     * style icon which is right above the color icon. It has an
     * ActionEvent object as a parameter. It should work with all
     * operating systems and hardware. There are no variances and no
     * security constraints.
     * @param evt an ActionEvent obj.
     */
    public void left1ActionPerformed(java.awt.event.ActionEvent evt) {
	toolBrush.setBrushType(toolBrush.LEFT1);
	curBrush.setBackground(Color.lightGray);
	curBrush = left1; /*FAULT: curBrush = left2; */
	curBrush.setBackground(propColor);
    }

    /** This function will execute if you click on the brush icon and
     * then another icon which is at row 4 column 2 at the
     * style icon which is right above the color icon. It has an
     * ActionEvent object as a parameter. It should work with all
     * operating systems and hardware. There are no variances and no
     * security constraints.
     * @param evt an ActionEvent obj.
     */
    public void left2ActionPerformed(java.awt.event.ActionEvent evt) {
	toolBrush.setBrushType(toolBrush.LEFT2);
	curBrush.setBackground(Color.lightGray);
	curBrush = left2; /*FAULT: curBrush = left3; */
	curBrush.setBackground(propColor);
    }

    /** This method is called by actionPerformed.
     * This function will execute if you click on the brush icon and
     * then another icon which is at row 4 column 3 at the
     * style icon which is right above the color icon.
     * It calls the methods:
     * setBrushType(int)
     * setBackground(Color)
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void left3ActionPerformed(java.awt.event.ActionEvent evt)
    {
	toolBrush.setBrushType(toolBrush.LEFT3);
	curBrush.setBackground(Color.lightGray);
	curBrush = left3; /*FAULT: curBrush = left2; */
	curBrush.setBackground(propColor);
    }

    /** This method sets the brushTool onto the icon at row 3 column1
     * and is called by actionPerformed.
     * It calls the methods:
     * setBrushType(int)
     * setBackground(Color)
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void right1ActionPerformed(java.awt.event.ActionEvent evt)
    {
	toolBrush.setBrushType(toolBrush.RIGHT1);
	curBrush.setBackground(Color.lightGray);
	curBrush = right1; /*FAULT: curBrush = right2; */
	curBrush.setBackground(propColor);
    }
    /** This method sets the brushTool onto the icon at row 3 column2
     * and is called by actionPerformed.
     * It calls the methods:
     * setBrushType(int)
     * setBackground(Color)
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     *
     */
    public void right2ActionPerformed(java.awt.event.ActionEvent evt)
    {
	toolBrush.setBrushType(toolBrush.RIGHT2);
	curBrush.setBackground(Color.lightGray);
	curBrush = right2; /*FAULT: curBrush = right3; */
	curBrush.setBackground(propColor);
    }
    /** This method sets the brushTool onto the icon at row 3 column3
     * and is called by actionPerformed.
     * It calls the methods:
     * setBrushType(int)
     * setBackground(Color)
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void right3ActionPerformed(java.awt.event.ActionEvent evt)
    {
	toolBrush.setBrushType(toolBrush.RIGHT3);
	curBrush.setBackground(Color.lightGray);
	curBrush = right3; /*FAULT: curBrush = right2; */
	curBrush.setBackground(propColor);
    }
    /** This method is called by actionPerformed.
     * It sets the square size of a brushTool to 1.
     * It calls the methods:
     * setBrushType(int)
     * setBackground(Color)
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void square1ActionPerformed(java.awt.event.ActionEvent evt)
    {
	toolBrush.setBrushType(toolBrush.SQUARE1);
	curBrush.setBackground(Color.lightGray);
	curBrush = square1; /*FAULT: curBrush = square2; */
	curBrush.setBackground(propColor);
    }
    /** This method sets the square size of a brushTool to 2 and
     * is called by actionPerformed.
     * It calls the methods:
     * setBrushType(int)
     * setBackground(Color)
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void square2ActionPerformed(java.awt.event.ActionEvent evt)
    {
	toolBrush.setBrushType(toolBrush.SQUARE2);
	curBrush.setBackground(Color.lightGray);
	curBrush = square2; /*FAULT: curBrush = square3; */
	curBrush.setBackground(propColor);
    }

    /** This method sets the square size of a brushTool to 3 and
     * is called by actionPerformed.
     * It calls the methods:
     * setBrushType(int)
     * setBackground(Color)
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void square3ActionPerformed(java.awt.event.ActionEvent evt)
    {
	toolBrush.setBrushType(toolBrush.SQUARE3);
	curBrush.setBackground(Color.lightGray);
	curBrush = square3; /*FAULT: curBrush = square2; */
	curBrush.setBackground(propColor);
    }

    /** This method sets the dot size of a brushTool to 1 and
     * is called by actionPerformed.
     * It calls the methods:
     * setBrushType(int)
     * setBackground(Color)
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void dot1ActionPerformed(java.awt.event.ActionEvent evt)
    {
	    //	      clearBrushes();
	toolBrush.setBrushType(toolBrush.DOT1);
	curBrush.setBackground(Color.lightGray);
	curBrush = dot1; /*FAULT: curBrush = dot2; */
	curBrush.setBackground(propColor);
	    //	      dot1.setBackgrounc(propColor);
    }

    /** This method sets the dot size of a brushTool to 2 and
     * is called by actionPerformed.
     * It calls the methods:
     * setBrushType(int)
     * setBackground(Color)
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void dot2ActionPerformed(java.awt.event.ActionEvent evt)
    {
	toolBrush.setBrushType(toolBrush.DOT2);
	curBrush.setBackground(Color.lightGray);
	curBrush = dot2; /*FAULT: curBrush = dot3; */
	curBrush.setBackground(propColor);
    }

    /** This method sets the dot size of a brushTool to 3 and
     * is called by actionPerformed.
     * It calls the methods:
     * setBrushType(int)
     * setBackground(Color)
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void dot3ActionPerformed(java.awt.event.ActionEvent evt)
    {
	toolBrush.setBrushType(toolBrush.DOT3);
	curBrush.setBackground(Color.lightGray);
	curBrush = dot3; /*FAULT: curBrush = dot2; */
	curBrush.setBackground(propColor);
    }

    /** This method sets the lineStroke of a lineTool to size of 5 and
     * is called by actionPerformed.
     * It calls the methods:
     * setLineStroke(int)
     * setBackground(Color)
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void line5ActionPerformed(java.awt.event.ActionEvent evt)
    {
	clearLine();
	if (currentTool == toolLine) {
	    toolLine.setLineStroke(5);
	    curLine = line5; /*FAULT: curLine = line2; */
	} else if (currentTool == toolCurve) {
	    toolCurve.setCurveStroke(5);
	    curCurve = line5;
	}
	line5.setBackground(propColor);
    }

    /** This method sets the lineStroke of a lineTool to size of 4 and
     * is called by actionPerformed.
     * It calls the methods:
     * setLineStroke(int)
     * setBackground(Color)
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     *
     * @param evt ActionEvent
     */
    public void line4ActionPerformed(java.awt.event.ActionEvent evt)
    {
	clearLine();
	if (currentTool == toolLine) {
	    toolLine.setLineStroke(4);
	    curLine = line4; /*FAULT: curLine = line2; */
	} else if (currentTool == toolCurve) {
	    toolCurve.setCurveStroke(4);
	    curCurve = line4;
	}
	line4.setBackground(propColor);
    }

    /** This method sets the lineStroke of a lineTool to size of 3 and
     * is called by actionPerformed.
     * It calls the methods:
     * setLineStroke(int)
     * setBackground(Color)
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void line3ActionPerformed(java.awt.event.ActionEvent evt)
    {
	clearLine();
	if (currentTool == toolLine) {
	    toolLine.setLineStroke(3);
	    curLine = line3; /*FAULT: curLine = line2; */
	} else if (currentTool == toolCurve) {
	    toolCurve.setCurveStroke(3);
	    curCurve = line3;
	}
	line3.setBackground(propColor);
    }

    /** This method sets the lineStroke of a lineTool to size of 2 and
     * is called by actionPerformed.
     * It calls the methods:
     * setLineStroke(int)
     * setBackground(Color)
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void line2ActionPerformed(java.awt.event.ActionEvent evt)
    {
	clearLine();
	if (currentTool == toolLine) {
	    toolLine.setLineStroke(2);
	    curLine = line2; /*FAULT: curLine = line3; */
	} else if (currentTool == toolCurve) {
	    toolCurve.setCurveStroke(2);
	    curCurve = line2;
	}
	line2.setBackground(propColor);
    }
    /** This method sets the lineStroke of a lineTool to size of 1 and
     * is called by actionPerformed.
     * It calls the methods:
     * setLineStroke(int)
     * setBackground(Color)
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void line1ActionPerformed(java.awt.event.ActionEvent evt)
    {
	clearLine();
	if (currentTool == toolLine) {
	    toolLine.setLineStroke(1);
	    curLine = line1; /*FAULT: curLine = line2; */
	} else if (currentTool == toolCurve) {
	    toolCurve.setCurveStroke(1);
	    curCurve = line1;
	}
	line1.setBackground(propColor);
    }

    /** This method sets the fill type to three, which sets the fill
     * and is called by actionPerformed.
     * It calls the methods:
     * setFillType(int)
     * setBackground(Color)
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void threeActionPerformed(java.awt.event.ActionEvent evt)
    {
	clearRect();
	if (currentTool == toolRect) {
	    toolRect.setFillType(3);
	    curSquare = three;
	} else if (currentTool == toolRoundedRect) {
	    toolRoundedRect.setFillType(3);
	    curRoundRect = three;
	} else if (currentTool == toolEllipse) {
	    toolEllipse.setFillType(3);
	    curEllipse = three;
	} else if (currentTool == toolPolygon) {
	    toolPolygon.setFillType(3);
	    curPolygon = three;
	}
	three.setBackground(propColor);

    }

    /** This method sets the fill type to two, which sets the border and the
     * fill and is called by actionPerformed.
     * It calls the methods:
     * setFillType(int)
     * setBackground(Color)
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void twoActionPerformed(java.awt.event.ActionEvent evt)
    {
	clearRect();
	if (currentTool == toolRect) {
	    toolRect.setFillType(2);
	    curSquare = two;
	} else if (currentTool == toolRoundedRect) {
	    toolRoundedRect.setFillType(2);
	    curRoundRect = two;
	} else if (currentTool == toolEllipse) {
	    toolEllipse.setFillType(2);
	    curEllipse = two;
	} else if (currentTool == toolPolygon) {
	    toolPolygon.setFillType(2);
	    curPolygon = two;
	}
	two.setBackground(propColor);
    }

    /** This method sets the fill type to one, which sets the border and
     * is called by actionPerformed.
     * It calls the methods:
     * setFillType(int)
     * setBackground(Color)
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void oneActionPerformed(java.awt.event.ActionEvent evt)
    {
	clearRect();
	if (currentTool == toolRect) {
	    toolRect.setFillType(1);
	    curSquare = one;
	} else if (currentTool == toolRoundedRect) {
	    toolRoundedRect.setFillType(1);
	    curRoundRect = one;
	} else if (currentTool == toolEllipse) {
	    toolEllipse.setFillType(1);
	    curEllipse = one;
	} else if (currentTool == toolPolygon) {
	    toolPolygon.setFillType(1);
	    curPolygon = one;
	}
	one.setBackground(propColor);
    }

    /** This method Sends the images to an email address (mail object)
     * and is called by actionPerformed.
     * It calls the methods:
     * deSelect()
     * setVisible(boolean) to a mail object.
     * @param evt ActionEvent
     */

    /*
    public void SendActionPerformed(java.awt.event.ActionEvent evt)
	{	if (curButton == select) {
		toolSelect.deSelect(center);
	} else if (curButton == magicSelect) {
	    toolMagicSelect.deSelect(center);
	} else if (curButton == selectall) {
		toolSelectall.deSelect(center);
	} else if (curButton == curve) {
		toolCurve.deSelect(center);
	} else if (curButton == polygon) {
		toolPolygon.deSelect(center);
	}
	mail m = new mail(this, true);
	m.setVisible(true);
    }
    */
//Istvan
    /** This method clears the screen and is called by
     * actionPerformed. It should clear according to the
     * color chosen by the right click.
     * It calls the methods:
     * deSelect()
     * clear()
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void ClearImageActionPerformed(java.awt.event.ActionEvent evt)
    {
		if (curButton == select) {
	    toolSelect.deSelect(center);
	} else if (curButton == selectall) {
	    toolSelectall.deSelect(center);
	} else if (curButton == magicSelect) {
	    toolMagicSelect.deSelect(center);
	} else if (curButton == curve) {
	    toolCurve.deSelect(center);
	} else if (curButton == polygon) {
	    toolPolygon.deSelect(center);
	}
	center.clear();
    }

    /** This method is called by actionPerformed.
     * No code.
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void lgt_grayActionPerformed(java.awt.event.ActionEvent evt)
    {
	    // Add your handling code here:
    }

    /** This method resets the background color to
     * that chosen by user and is called by actionPerformed.
     * It calls the methods:
     * setLeftColor(Color)
     * setBackground(Color)
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param in The current right color button.
     * @param evt ActionEvent
     */
    public void EditColorsActionPerformed(java.awt.event.ActionEvent evt, JButton in)
    {
	    //	      Color othercolor = left.getBackground();
	    //		  Color newColor = JColorChooser.showDialog(this,"Choose Background Color",null);

	    //		  System.out.println("LEFT" +left.getBackground());
	    //		  System.out.println("OLD LEFT"+othercolor.toString());
	    //		  System.out.println("newcolor"+newColor.toString());


	Color newColor = JColorChooser.showDialog(this, "Choose Color", in.getBackground());

//new color add
	if( newColor == null )
	   return;
	in.setBackground(newColor);
//new color added

	if (grayscaleFlag == 1)
	    changeButtonGray(in);

	left.setBackground(in.getBackground());
	currentColor.setBackground( in.getBackground() );
	center.setLeftColor(in.getBackground());
 //new color add
		colorTable.setElementAt( in.getBackground(), 21);
//new color added

	if( currentColor == red )
	    colorTable.setElementAt( in.getBackground(), 0 );
	if( currentColor == green )
	    colorTable.setElementAt( in.getBackground(), 1 );
	if( currentColor == blue )
	    colorTable.setElementAt( in.getBackground(), 2 );
	if( currentColor == yellow )
	    colorTable.setElementAt( in.getBackground(), 3 );
	if( currentColor == magenta )
	    colorTable.setElementAt( in.getBackground(), 4 );
	if( currentColor == cyan )
	    colorTable.setElementAt( in.getBackground(), 5 );
	if( currentColor == pink )
	    colorTable.setElementAt( in.getBackground(), 6 );
	if( currentColor == white )
	    colorTable.setElementAt( in.getBackground(), 7 );
	if( currentColor == lgt_gray )
	    colorTable.setElementAt( in.getBackground(), 8 );
	if( currentColor == gray )
	    colorTable.setElementAt( in.getBackground(), 9 );
	if( currentColor == drk_gray )
	    colorTable.setElementAt( in.getBackground(), 10 );
	if( currentColor == black )
	    colorTable.setElementAt( in.getBackground(), 11 );
	if( currentColor == brown )
	    colorTable.setElementAt( in.getBackground(), 12 );
	if( currentColor == orange )
	    colorTable.setElementAt( in.getBackground(), 13 );

//new color add
		if( currentColor == color1 )
	    colorTable.setElementAt( in.getBackground(), 14 );
	if( currentColor == color2 )
	    colorTable.setElementAt( in.getBackground(), 15 );
	if( currentColor == color3 )
	    colorTable.setElementAt( in.getBackground(), 16 );
	if( currentColor == color4 )
	    colorTable.setElementAt( in.getBackground(), 17 );
	if( currentColor == color5 )
	    colorTable.setElementAt( in.getBackground(), 18 );
	if( currentColor == color6 )
	    colorTable.setElementAt( in.getBackground(), 19 );
	if( currentColor == color7 )
	    colorTable.setElementAt( in.getBackground(), 20 );


	colorTable.setElementAt( in.getBackground(), 21 );
 //new color added
	    /*
	    if( newColor == null )
	    {
		System.out.println("no color chosen");
	    }
	    else
		left.setBackground(newColor);

	    if (grayscaleFlag == 1){
		changeButtonGray(left);
	    }
	    center.setLeftColor(left.getBackground());
	     */
    }

    // VERSION 3
    /** If the layer box is visible, it makes it invisible.
     * Otherwise if it is not visible, it will make it visible.
     * It calls the methods:
     * setVisible(boolean)
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void LayerBoxActionPerformed(java.awt.event.ActionEvent evt)
    {
	if(alllayers.isVisible()){
	    alllayers.setVisible(false);
	}
	else{
	    alllayers.setVisible(true);
	}
    }

    /** If the status of the box is visible, it makes it invisible.
     * Otherwise if it is not visible, it will make it visible.
     * It calls the methods:
     * setVisible(boolean)
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void StatusBoxActionPerformed(java.awt.event.ActionEvent evt)
    {
	if(this.status.isVisible()){
	    status.setVisible(false);
	}
	else{
	    status.setVisible(true);
	}
    }
//Istvan
    /** This method prints to the printer and is called
     * by actionPerformed.
     * It calls the methods:
     * deSelect()
     * setPrintable(printer)
     * print()
     * Has 2 states, open and closed. Attempting to write to a
     * closed stream will result in a java.io.IOException being thrown.
     * Some security constraints may exist.
     * @param evt ActionEvent
     */
    public void PrintActionPerformed(java.awt.event.ActionEvent evt)
    {
		if (curButton == select) {
	     toolSelect.deSelect(center);
	 } else if (curButton == magicSelect) {
	     toolMagicSelect.deSelect(center);
	 } else if (curButton == selectall) {
	     toolSelectall.deSelect(center);
	 } else if (curButton == curve) {
	     toolCurve.deSelect(center);
	 } else if (curButton == polygon) {
	     toolPolygon.deSelect(center);
	}

	ourPrinter.setImage( center.getBufferedImage() );
	ourPrinter.printTo();
    }
//Istvan
    /** This method creates a new workarea.
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void NewActionPerformed(java.awt.event.ActionEvent evt) {
		if (curButton == select) {
		    toolSelect.deSelect(center);
		} else if (curButton == magicSelect) {
		    toolMagicSelect.deSelect(center);
		} else if (curButton == selectall) {
		    toolSelectall.deSelect(center);
		} else if (curButton == curve) {
		    toolCurve.deSelect(center);
		} else if (curButton == polygon) {
		    toolPolygon.deSelect(center);
		}
			myContainer.Add();

		if(myPrefs.LastSize){
				myContainer.launch(null);
				return;
			}

			else if(myPrefs.PromptSize){
				myContainer.launchPrompt(null);
			    return;
			}
			else if(myPrefs.FixedSize){
				myContainer.launchFixed(null);
				return;
			}
	center.setBufferedImage(center.getBufferedImage());
	/* ming */
	// ming 4.26
	    action_list = new LinkedList();
	    redo_list = new LinkedList();
	    LinkedList list0 = new LinkedList();
	    action_list.add(list0);
	    LinkedList list1 = new LinkedList();
	    redo_list.add(list1);
	    // ming 4.26 end
	updateUndoList();
	/* ming */
    }


    /** This function creates the canvas_size dialog box, to set the size when requesting a new canvas
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     */
    public void setCanvasSize()
    {
	canvas_size canvsz = new canvas_size(this, true);
	canvsz.setLocation( (int) this.getLocation().getX() + 100, (int) this.getLocation().getY()+100 );
	canvsz.setVisible(true);
    }

    /** There is no code in this one.
     * @param evt ActionEvent
     */
    public void EditActionPerformed(java.awt.event.ActionEvent evt) {
	// Add your handling code here:
    }

    /** This function performs the 'Edit -> Redo' capabilities of TerpPaint.
     * 1. Deselect any selections
     * 2. call redo().
     * 3. update canvas
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     *
     * @param evt ActionEvent
     */
    public void RepeatActionPerformed(java.awt.event.ActionEvent evt) {
		//Istvan
		if (curButton == select) {
	    toolSelect.deSelect(center);
	} else if (curButton == magicSelect) {
	    toolMagicSelect.deSelect(center);
	} else if (curButton == selectall) {
	    toolSelectall.deSelect(center);
	} else if (curButton == curve) {
	    toolCurve.deSelect(center);
	} else if (curButton == polygon) {
	    toolPolygon.deSelect(center);
	}
	if(center.redo()){ // Ronald 5.2
	/* ming */
	// ming 4.22
	LinkedList cur_action_list = (LinkedList)(action_list.get(center.currentLayer));
	LinkedList cur_redo_list = (LinkedList)(redo_list.get(center.currentLayer));

	cur_action_list.add(cur_redo_list.getLast());
	// ming 4.22
	if (cur_action_list.getLast().equals("Change to gray scale"))
		changeToGray( true );
	// ming 5.2
	if (cur_action_list.getLast().equals("Region brightness change") || cur_action_list.getLast().equals("Region blur") || cur_action_list.getLast().equals("Region sharpen") || cur_action_list.getLast().equals("Region edge detection") || cur_action_list.getLast().equals("Region emboss") || cur_action_list.getLast().equals("Cut")|| cur_action_list.getLast().equals("Move") || cur_action_list.getLast().equals("Paste") || cur_action_list.getLast().equals("Writing text"))
		center.redo();
	    // ming 5.2 end
	// ming 4.22 end

	cur_redo_list.removeLast();
	updateUndoList();
	/* ming */
	center.repaint();
	// ming 4.26 end
	}// Ronald 5.2
    }

    /** This function performs the 'Edit -> Undo' capabilities of TerpPaint.
     * 1. Deselect any selections
     * 2. call undo().
     * 3. update canvas
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void UndoActionPerformed(java.awt.event.ActionEvent evt) {
       //Istvan
       if (curButton == select) {
	    toolSelect.deSelect(center);
	} else if (curButton == magicSelect) {
	    toolMagicSelect.deSelect(center);
	} else if (curButton == selectall) {
	    toolSelectall.deSelect(center);
	} else if (curButton == curve) {
	    toolCurve.deSelect(center);
	} else if (curButton == polygon) {
	    toolPolygon.deSelect(center);
	}
	// ming 4.4
       // ming 4.26
	LinkedList cur_action_list = (LinkedList)(action_list.get(center.currentLayer));
	LinkedList cur_redo_list = (LinkedList)(redo_list.get(center.currentLayer));

	if (cur_action_list.size()>0)
	{
	    // ming 4.27
	    if (cur_action_list.getLast().equals("Change to gray scale"))
		changeToGray( false );

		// ming 4.27 end
	    center.undo();
	    // ming 5.2
	    if (cur_action_list.getLast().equals("Region brightness change") || cur_action_list.getLast().equals("Region blur") || cur_action_list.getLast().equals("Region sharpen") || cur_action_list.getLast().equals("Region edge detection") || cur_action_list.getLast().equals("Region emboss") || cur_action_list.getLast().equals("Cut") || cur_action_list.getLast().equals("Move") || cur_action_list.getLast().equals("Paste") || cur_action_list.getLast().equals("Writing text"))
		center.undo();
	    if (cur_action_list.getLast().equals("Change size")) {
	    	center.setZoom(center.getZoom());
	    }
	    // ming 5.2 end
	    cur_redo_list.add(cur_action_list.getLast());
	    cur_action_list.removeLast();

	    updateUndoList();
	}
	// ming 4.26 end
	//center.repaint();
	// ming 4.4 end
//	  center.undo();
//	  /* ming */
//	  redo_list.add(action_list.getLast());
//	  action_list.removeLast();
//	  updateUndoList();
//	 /* ming */
//	  center.repaint();
    }
     /* ming */
    /** This function performs the 'Edit -> UndoList->xxx' capabilities of TerpPaint.
     * 1. Deselect any selections
     * 2. call undo() some times.
     * 3. update canvas
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     * @param i an int specifying which undo action in the list
     */
    public void UndoListActionPerformed(java.awt.event.ActionEvent evt, int i) {
	//Istvan
	if (curButton == select) {
	    toolSelect.deSelect(center);
	} else if (curButton == magicSelect) {
	    toolMagicSelect.deSelect(center);
	} else if (curButton == selectall) {
	    toolSelectall.deSelect(center);
	} else if (curButton == curve) {
	    toolCurve.deSelect(center);
	} else if (curButton == polygon) {
	    toolPolygon.deSelect(center);
	}
	// ming 4.26
	LinkedList cur_action_list = (LinkedList)(action_list.get(center.currentLayer));
	LinkedList cur_redo_list = (LinkedList)(redo_list.get(center.currentLayer));

	int temp = cur_action_list.size();
	for (int j=0; j<temp-i; j++)
	{
	    center.undo();
	    cur_redo_list.add(cur_action_list.getLast());
	    // ming 4.22
	    if (cur_action_list.getLast().equals("Change to gray scale"))
		changeToGray( false );
		// ming 4.22 end
	    // ming 5.2
	    if (cur_action_list.getLast().equals("Region brightness change") || cur_action_list.getLast().equals("Region blur") || cur_action_list.getLast().equals("Region sharpen") || cur_action_list.getLast().equals("Region edge detection") || cur_action_list.getLast().equals("Region emboss") || cur_action_list.getLast().equals("Cut")|| cur_action_list.getLast().equals("Move") || cur_action_list.getLast().equals("Paste") || cur_action_list.getLast().equals("Writing text"))
		center.undo();
	    // ming 5.2 end

	    cur_action_list.removeLast();
	}
	//action_list.removeRange(i,temp-1);
	updateUndoList();
	center.repaint();
    }
    /* ming */


    /** This function selects the entire canvas.
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent Object
     */
    public void SelectionAllActionPerformed( java.awt.event.ActionEvent evt) {
	toggleProperties();
	none.setVisible(true);
	//Istvan
	if (curButton == select) {
	      toolSelect.deSelect(center);
	} else if (curButton == magicSelect) {
	    toolMagicSelect.deSelect(center);
	} else if (curButton == selectall) {
	      toolSelectall.deSelect(center);
	} else if (curButton == curve) {
	      toolCurve.deSelect(center);
	} else if (curButton == polygon) {
	      toolPolygon.deSelect(center);
	}
	curButton.setBorder(BorderFactory.createRaisedBevelBorder());
	curButton = selectall;
	curButton.setBorder(BorderFactory.createLoweredBevelBorder());
	currentCursor = defaultCursor;
	currentTool = toolSelectall;
	toolSelectall.selectAll( center );

    }

    /** Initializes sprayTool upon event
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void spray2ActionPerformed(java.awt.event.ActionEvent evt) {
	toolSpray.setSprayType(15);
	curSpray.setBackground(Color.lightGray);
	curSpray = spray2; /*FAULT:: curSpray = spray3; */
	curSpray.setBackground(propColor);
    }

    /** no code here.
     * @param evt MouseEvent
     */
    public void southMouseMoved(java.awt.event.MouseEvent evt) {
	// Add your handling code here:
    }

    /** no code here.
     * @param evt MouseEvent
     */
    public void southMousePressed(java.awt.event.MouseEvent evt) {
	// Add your handling code here:
    }

    /** no codehere.
     * @param evt ActionEvent
     */
    public void File1ActionPerformed(java.awt.event.ActionEvent evt) {
	// Add your handling code here:
    }

    /** Hides all JDialog dialog boxes
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     */
    public void toggleProperties(){
	line_properties.setVisible(false);
	rect_properties.setVisible(false);
	spray_properties.setVisible(false);
	brush_properties.setVisible(false);
	zoom_properties.setVisible(false);
	paint_properties.setVisible(false);
	none.setVisible(false);
    }

    /** Creates a new canvas, and initializes all settings, namely the scrollable picture and background colors
     * Also there are some methods defined within method:
     * void mousedPressed( MouseEvent )
     * void mouseReleased( MouseEvent )
     * void mouseDragged( MouseEvent )
     * void mouseMoved( MouseEvent )
     *
     * These methods define the behavior concerning mouse events on the canvas and the currentTool
     *
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     */
    public void centerSetup(){
	center = new main_canvas();
	System.out.println(center.widt + center.heig);
	getContentPane().add(center, java.awt.BorderLayout.CENTER);
	center.setBackground(Color.gray);
	center.requestFocus();
	//center.addKeyListener(new KeyHandler(this));
	center.pictureScrollPane.addMouseListener(new java.awt.event.MouseAdapter() {

	    //************************MOUSE PRESSED
	    public void mousePressed(java.awt.event.MouseEvent evt) {
		int viewX = (int)center.pictureScrollPane.getViewport().getViewPosition().getX();
		int viewY = (int)center.pictureScrollPane.getViewport().getViewPosition().getY();
		int w = center.widt;
		int h = center.heig;
		int zoom = (int)center.getZoom();

		// ming 4.26
			LinkedList cur_action_list = (LinkedList)(action_list.get(center.currentLayer));
			LinkedList cur_redo_list = (LinkedList)(redo_list.get(center.currentLayer));

		if(center.getCursor().getType() == Cursor.S_RESIZE_CURSOR){
		    resize = true;
		}
		else if(center.getCursor().getType() == Cursor.E_RESIZE_CURSOR){
		    resize = true;
		}
		else if(center.getCursor().getType() == Cursor.SE_RESIZE_CURSOR){
		    resize = true;
		}
		else{
		    //****************THIS IS A NORMAL CLICK
		    // ming 4.22
		    if (zoom==0) zoom = 1;
		    // ming 4.22 end
		    int newX = ((evt.getX()+viewX)/zoom);
		    int newY = ((evt.getY()+viewY)/zoom);
		    if((newX < w) && (newY < h) &&(newX >= 0) && (newY >= 0)
			&&(center.getCursor().getType() == currentCursor.getType())){

			MouseEvent newEvt = new MouseEvent((Component)evt.getSource(),evt.getID(),
			evt.getWhen(),evt.getModifiers(),
			newX,newY,evt.getClickCount(),false);

			if( !resize ) //added this if to try to fix bug
			{
			    currentTool.clickAction(newEvt, center);
			    /* ming */
			    if (currentTool == toolLetter && toolLetter.ok_action)
			    {
				int temp = cur_redo_list.size();
				for (int i=0; i<temp; i++)
				    cur_redo_list.removeLast();
				cur_action_list.add(new String("Writing text"));
				//cur_action_list.add(new String("Post-processing of writing text"));
				updateUndoList();
			    }
			    /* ming */
			}

/*
			if (currentTool == toolZoom) {
			    int temp = toolZoom.getZoom();
			    curZoom.setBackground(Color.lightGray);
			    switch (temp) {
				case 1 : zoom1.setBackground(propColor);
				curZoom = zoom1;
				break;
				case 2 : zoom2.setBackground(propColor);
				curZoom = zoom2;
				break;
				case 4 : zoom3.setBackground(propColor);
				curZoom = zoom3;
				break;
				case 8 : zoom4.setBackground(propColor);
				curZoom = zoom4;
				break;
			    }
			}*/

// ming 5.7
if (currentTool == toolZoom) {
    int temp = toolZoom.getZoom();
    curZoom.setBackground(Color.lightGray);
    if (SwingUtilities.isLeftMouseButton(evt)){
	switch (temp) {
	    case 1 :

if(center.main_image.getWidth()*2*center.main_image.getHeight()*2<9000000){
		    zoom2.setBackground(propColor);
		    System.out.println("zoom 1 to 2");
		    curZoom = zoom2;
		}else
		{
		    curZoom.setBackground(propColor);
		    JDialog my_message = new JDialog();
		    my_message.setLocation( (int) my_message.getLocation().getX()+300, (int) my_message.getLocation().getY()+215);
		    my_message.setSize(300, 100);
		    my_message.setTitle("Help Message");
		    JLabel NameLabel = new JLabel("   The picture can not be zoomed too much, :)");
		    my_message.getContentPane().add(NameLabel);
		    my_message.setVisible(true);
		}
		break;
	    case 2 :

if(center.main_image.getWidth()*4*center.main_image.getHeight()*4<9000000){
		    zoom3.setBackground(propColor);
		    System.out.println("zoom 2 to 4");
		    curZoom = zoom3;
		}else
		{
		    curZoom.setBackground(propColor);
		    JDialog my_message = new JDialog();
		    my_message.setLocation( (int) my_message.getLocation().getX()+300, (int) my_message.getLocation().getY()+215);
		    my_message.setSize(300, 100);
		    my_message.setTitle("Help Message");
		    JLabel NameLabel = new JLabel("   The picture can not be zoomed too much, :)");
		    my_message.getContentPane().add(NameLabel);
		    my_message.setVisible(true);
		}
		break;
	    case 4 :

if(center.main_image.getWidth()*8*center.main_image.getHeight()*8<9000000){
		    zoom4.setBackground(propColor);
		    System.out.println("zoom 4 to 8");
		    curZoom = zoom4;
		}else
		    {
		    curZoom.setBackground(propColor);
		    JDialog my_message = new JDialog();
		    my_message.setLocation( (int) my_message.getLocation().getX()+300, (int) my_message.getLocation().getY()+215);
		    my_message.setSize(300, 100);
		    my_message.setTitle("Help Message");
		    JLabel NameLabel = new JLabel("   The picture can not be zoomed too much, :)");
		    my_message.getContentPane().add(NameLabel);
		    my_message.setVisible(true);
		}
		break;
	    case 8 :

		zoom4.setBackground(propColor);
		System.out.println("hold 8");
		curZoom = zoom4;
		if(center.main_image.getWidth()*8*center.main_image.getHeight()*8>=9000000){
		    curZoom.setBackground(propColor);
		    JDialog my_message = new JDialog();
		    my_message.setLocation( (int) my_message.getLocation().getX()+300, (int) my_message.getLocation().getY()+215);
		    my_message.setSize(300, 100);
		    my_message.setTitle("Help Message");
		    JLabel NameLabel = new JLabel("   The picture can not be zoomed too much, :)");
		    my_message.getContentPane().add(NameLabel);
		    my_message.setVisible(true);
		}
		break;

	}// ming 5.7 end
    }else{
	switch (temp) {
	    case 1 :
		zoom1.setBackground(propColor);
		System.out.println("hold 1");
		curZoom = zoom1;
		break;
	    case 2 :
		zoom1.setBackground(propColor);
		System.out.println("unzoom 2 to 1");
		curZoom = zoom1;
		break;
	    case 4 :
		zoom2.setBackground(propColor);
		System.out.println("unzoom 4 to 2");
		curZoom = zoom2;
		break;
	    case 8 :
		zoom3.setBackground(propColor);
		System.out.println("unzoom 8 to 4");
		curZoom = zoom3;
		break;
	}
    }
}


			if (currentTool == toolMedicine) {
			    Color theColor = toolMedicine.getMyColor();
			    if (SwingUtilities.isLeftMouseButton(evt)) {
				left.setBackground(theColor);
				center.setLeftColor(theColor);
			    } else {
				right.setBackground(theColor);
				center.setRightColor(theColor);
			    }
			}
		    }
		}
	    }

	    //************************MOUSE RELEASED
	    public void mouseReleased(java.awt.event.MouseEvent evt) {
		int viewX = (int)center.pictureScrollPane.getViewport().getViewPosition().getX();
		int viewY = (int)center.pictureScrollPane.getViewport().getViewPosition().getY();
		int w = center.widt;
		int h = center.heig;
		int zoom = (int)center.getZoom();

		// ming 4.26
			LinkedList cur_action_list = (LinkedList)(action_list.get(center.currentLayer));
			LinkedList cur_redo_list = (LinkedList)(redo_list.get(center.currentLayer));

		if(resize == true){
		    resize = false;
		    //************I MADE CHANGES IN HERE
		    // ming 4.24
		    int temp = cur_redo_list.size();
		    for (int i=0; i<temp; i++)
			cur_redo_list.removeLast();
		    cur_action_list.add(new String("Change size"));
		    updateUndoList();
		    // ming 4.24 end
		    if(center.getCursor().getType() == Cursor.S_RESIZE_CURSOR){
			center.resizeImage(w, (evt.getY()/zoom));
		    }
		    else if(center.getCursor().getType() == Cursor.E_RESIZE_CURSOR){
			center.resizeImage((evt.getX()/zoom), h);
		    }
		    else if(center.getCursor().getType() == Cursor.SE_RESIZE_CURSOR){
			center.resizeImage((evt.getX()/zoom), (evt.getY()/zoom));
		    }
		    center.repaint();
		}
		else{
		     // ming 4.22
		    if (zoom==0) zoom = 1;
		    // ming 4.22 end
		    int newX = ((evt.getX()+viewX)/zoom);
		    int newY = ((evt.getY()+viewY)/zoom);

		    if((newX < w)
		    &&(newY < h)
		    &&(newX >= 0)
		    &&(newY >= 0)
		    &&(center.getCursor().getType() == currentCursor.getType())
		    ){
			MouseEvent newEvt = new MouseEvent((Component)evt.getSource(),evt.getID(),
			evt.getWhen(),evt.getModifiers(),
			newX,newY,evt.getClickCount(),false);
			currentTool.mouseReleaseAction(newEvt, center);


			 /* ming */
			if (curButton == pencil)
			{
			    int temp = cur_redo_list.size();
			    for (int i=0; i<temp; i++)
				cur_redo_list.removeLast();
			    cur_action_list.add(new String("Pencil"));

			    updateUndoList();
			  //  initComponents();
			    //curButton = pencil;
			}
			else if (curButton == line)
			{
			    int temp = cur_redo_list.size();
			    for (int i=0; i<temp; i++)
				cur_redo_list.removeLast();
			    cur_action_list.add(new String("Line"));
			    // ming 4.26 end
			    updateUndoList();
			  //  initComponents();
		      //      curButton = line;
			}
			else if (curButton == curve)
			{
			    if (cur_action_list.size()==0 || !cur_action_list.getLast().equals("Curve"))
			    {
				int temp = cur_redo_list.size();
				for (int i=0; i<temp; i++)
				    cur_redo_list.removeLast();
				cur_action_list.add(new String("Curve"));

				updateUndoList();
			    }
			 //   initComponents();
			//    curButton = curve;
			}
			else if (curButton == rounded_rectangle)
			{
			    int temp = cur_redo_list.size();
			    for (int i=0; i<temp; i++)
				cur_redo_list.removeLast();
			    cur_action_list.add(new String("Rounded rectangle"));

			    updateUndoList();
			//    initComponents();
			  //  curButton = rounded_rectangle;
			}
			else if (curButton == square)
			{
			    int temp = cur_redo_list.size();
			    for (int i=0; i<temp; i++)
				cur_redo_list.removeLast();
			    cur_action_list.add(new String("Square"));
			    updateUndoList();
		       //     initComponents();
			   // curButton = square;
			}
			else if (curButton == polygon)
			{
			    if (cur_action_list.size()==0 || !cur_action_list.getLast().equals("Polygon"))
			    {
				int temp = cur_redo_list.size();
				for (int i=0; i<temp; i++)
				    cur_redo_list.removeLast();
				 cur_action_list.add(new String("Polygon"));
				 updateUndoList();
			    }
			//    initComponents();
			   // curButton = polygon;
			}
			else if (curButton == elipse)
			{
			    int temp = cur_redo_list.size();
			    for (int i=0; i<temp; i++)
				cur_redo_list.removeLast();
			    cur_action_list.add(new String("Ellipse"));

			    updateUndoList();
		       //     initComponents();
			  //  curButton = elipse;
			}
			else if (curButton == eraser)
			{
			    int temp = cur_redo_list.size();
			    for (int i=0; i<temp; i++)
				cur_redo_list.removeLast();
			    cur_action_list.add(new String("Eraser"));
			    updateUndoList();
		       //     initComponents();
			  //  curButton = eraser;
			}
			else if (curButton == paint)
			{
			    int temp = cur_redo_list.size();
			    for (int i=0; i<temp; i++)
				cur_redo_list.removeLast();
			    cur_action_list.add(new String("Fill"));
			    updateUndoList();
			 //   initComponents();
			  //  curButton = paint;
			}
			else if (curButton == brush)
			{
			    int temp = cur_redo_list.size();
			    for (int i=0; i<temp; i++)
				cur_redo_list.removeLast();
			    cur_action_list.add(new String("Brush"));
			    updateUndoList();
			  //  initComponents();
			  //  curButton = brush;
			}
			else if (curButton == spray)
			{
			    int temp = cur_redo_list.size();
			    for (int i=0; i<temp; i++)
				cur_redo_list.removeLast();
			    cur_action_list.add(new String("Airbrush"));
			    updateUndoList();
			  //  initComponents();
			  //  curButton = spray;
			}
		 /*	  else if (curButton == letter)
			{
			    cur_action_list.add(new String("Wrting letter"));
			    updateUndoList();
			  //  initComponents();
			    //curButton = letter;
			}*/
			else if (curButton == select)
			{

			    if (cur_action_list.size()==0 || !cur_action_list.getLast().equals("Free form Select"))
			    {
				int temp = cur_redo_list.size();
				for (int i=0; i<temp; i++)
				    cur_redo_list.removeLast();
				cur_action_list.add(new String("Free form Select"));
				updateUndoList();
			    }
			//    initComponents();
			   // curButton = select;
			}
			else if (curButton == selectall)
			{
			    // ming 5.2
			    if (toolSelectall.action_name.equals("Move") || toolSelectall.action_name.equals("Select"))
			    {
				if (cur_action_list.size()>0 && cur_action_list.getLast().equals("Select"))
				    cur_action_list.removeLast();
			     //	  if (toolSelectall.action_name.equals("Move") &&

				    int temp = cur_redo_list.size();
				    for (int i=0; i<temp; i++)
					cur_redo_list.removeLast();
				    cur_action_list.add(toolSelectall.action_name);
				    updateUndoList();
			    }
			    // ming 5.2 end
			//    initComponents();
			   // curButton = select;
			}
		    /*	  if (currentTool == toolLetter && toolLetter.ok_action)
			{
			    cur_action_list.add(new String("Writing text"));
			    cur_action_list.add(new String("Post-processing of writing text"));
			    updateUndoList();
			}*/
			/* ming */
		   }
		}
	    }
	});

	center.pictureScrollPane.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {

	    //************************MOUSE DRAGGED
	    public void mouseDragged(java.awt.event.MouseEvent evt) {
		int viewX = (int)center.pictureScrollPane.getViewport().getViewPosition().getX();
		int viewY = (int)center.pictureScrollPane.getViewport().getViewPosition().getY();
		int w = center.widt;
		int h = center.heig;
		int zoom = (int)center.getZoom();
		// ming 4.22
		if (zoom==0) zoom = 1;
		// ming 4.22 end

		int newX = ((evt.getX()+viewX)/zoom);
		int newY = ((evt.getY()+viewY)/zoom);

		if((newX < w)
		&&(newY < h)
		&&(newX >= 0)
		&&(newY >= 0)
		&&(center.getCursor().getType() == currentCursor.getType())
		){
		    MouseEvent newEvt = new MouseEvent((Component)evt.getSource(),evt.getID(),
		    evt.getWhen(),evt.getModifiers(),
		    newX,newY,evt.getClickCount(),false);
		    //		 currentTool.dragAction(newEvt, center);
		    cordinates.setText("("+newX+", "+newY+")");

		}
		MouseEvent newEvt = new MouseEvent((Component)evt.getSource(),evt.getID(),
		evt.getWhen(),evt.getModifiers(),
		newX,newY,evt.getClickCount(),false);
		if( !resize ) //added this if to try to fix bug
		    currentTool.dragAction(newEvt, center);
	    }

	    //************************MOUSE MOVED
	    public void mouseMoved(java.awt.event.MouseEvent evt) {
		int w = center.widt;
		int h = center.heig;
		int zoom = (int)center.getZoom();

		int viewX = (int)center.pictureScrollPane.getViewport().getViewPosition().getX();
		int viewY = (int)center.pictureScrollPane.getViewport().getViewPosition().getY();
		// ming 4.22
		if (zoom==0) zoom = 1;
		// ming 4.22 end

		int newX = ((evt.getX()+viewX)/zoom);
		int newY = ((evt.getY()+viewY)/zoom);

		if((newX < w)
		&&(newY < h)
		&&(newX >= 0)
		&&(newY >= 0)
		){
		    cordinates.setText("("+newX+", "+newY+")");
		}

		if((evt.getX() > ((w*zoom)-3))
		&&(evt.getY() > ((h*zoom)-3))
		&&(evt.getX() < ((w*zoom)+3))
		&&(evt.getY() < ((h*zoom)+3))
		){

		    //		      System.out.println("SE");
		    center.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));

		}
		else if( (evt.getX() > ((w*zoom)-3))
		&&(evt.getX() < ((w*zoom)+3))
		&&(evt.getY() < ((h*zoom)+3))
		) {
		    //		     System.out.println("E");
		    center.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
		}
		else if( (evt.getY() > ((h*zoom)-3))
		&&(evt.getY() < ((h*zoom)+3))
		&&(evt.getX() < ((w*zoom)+3))
		) {
		    //				    System.out.println("S");
		    
		    center.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
		}
		else{
		    if ((evt.getX() >= center.pictureScrollPane.getViewport().getWidth())
		    || (evt.getY() >= center.pictureScrollPane.getViewport().getHeight())
		    || (evt.getX() <= 0)
		    || (evt.getY() <= 0)
		    || (evt.getX() > (w*zoom))
		    || (evt.getY() > (h*zoom))
		    ){
			center.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		    }
		    else
			center.setCursor(currentCursor);
		}
	    }
	});
    }
//Istvan
    /** This function performs the stretching/skewing capabilities of TerpPaint.
     * 1. Deselect any selections
     * 2. create new stretch object (dialog box pops up)
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void StretchSkewActionPerformed(java.awt.event.ActionEvent evt) {
	if (curButton == select) {
	    toolSelect.deSelect(center);
	} else if (curButton == selectall) {
	    toolSelectall.deSelect(center);
	} else if (curButton == magicSelect) {
	    toolMagicSelect.deSelect(center);
	} else if (curButton == curve) {
	    toolCurve.deSelect(center);
	} else if (curButton == polygon) {
	    toolPolygon.deSelect(center);
	}
	stretch str = new stretch(this, true);
	str.setVisible(true);
       // ming 5.7
		   if (!str.ok_action)
		   {
		       JDialog my_message = new JDialog();
			       my_message.setLocation( (int) my_message.getLocation().getX()+300, (int) my_message.getLocation().getY()+215);
			       my_message.setSize(300, 100);
			       my_message.setTitle("Help Message");
			       JLabel NameLabel = new JLabel("	 The picture can not be stretched too big, :)");
			       my_message.getContentPane().add(NameLabel);
			       my_message.setVisible(true);
		   }
	// ming 5.7 end
    }
    /* ming */
    /** This function performs the brightness change capabilities of TerpPaint.
     * 1. Deselect any selections
     * 2. create new brightness-changed object (dialog box pops up)
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void BrightnessActionPerformed(java.awt.event.ActionEvent evt) {
	//Istvan
	// ming 4.24
	/*
	if (curButton == select) {
	    toolSelect.deSelect(center);
	} else if (curButton == magicSelect) {
	    toolMagicSelect.deSelect(center);
	} else if (curButton == selectall) {
	    toolSelectall.deSelect(center);
	} else*/
	// ming 4.24 end
	if (curButton == curve) {
	    toolCurve.deSelect(center);
	} else if (curButton == polygon) {
	    toolPolygon.deSelect(center);
	}
	brightness bri = new brightness(this, true);
	bri.setVisible(true);
    }

    /** This function performs the blur capabilities of TerpPaint.
     * 1. Deselect any selections
     * 2. create new blurred object (dialog box pops up)
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void BlurActionPerformed(java.awt.event.ActionEvent evt) {
	//Istvan
	// ming 4.24
	/*
	if (curButton == select) {
	    toolSelect.deSelect(center);
	} else if (curButton == magicSelect) {
	    toolMagicSelect.deSelect(center);
	} else if (curButton == selectall) {
	    toolSelectall.deSelect(center);
	} else*/
	// ming 4.24 end
	if (curButton == curve) {
	    toolCurve.deSelect(center);
	} else if (curButton == polygon) {
	    toolPolygon.deSelect(center);
	}
	blur blu = new blur(this, true);
	blu.setVisible(true);
    }

    /** This function performs the sharpen capabilities of TerpPaint.
     * 1. Deselect any selections
     * 2. create new sharpenned object (dialog box pops up)
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void SharpenActionPerformed(java.awt.event.ActionEvent evt) {
	//Istvan
	// ming 4.24
	/*
	if (curButton == select) {
	    toolSelect.deSelect(center);
	} else if (curButton == magicSelect) {
	    toolMagicSelect.deSelect(center);
	} else if (curButton == selectall) {
	    toolSelectall.deSelect(center);
	} else*/
	// ming 4.24 end
	if (curButton == curve) {
	    toolCurve.deSelect(center);
	} else if (curButton == polygon) {
	    toolPolygon.deSelect(center);
	}
	sharpen sha = new sharpen(this, true);
//	  sha.setVisible(true);
    }

    /** This function performs the emboss capabilities of TerpPaint.
     * 1. Deselect any selections
     * 2. create new embossed object (dialog box pops up)
     * It should work with all operating  systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */

    public void EmbossActionPerformed(java.awt.event.ActionEvent evt) {
	//Istvan
	// ming 4.24
	/*
	if (curButton == select) {
	    toolSelect.deSelect(center);
	} else if (curButton == magicSelect) {
	    toolMagicSelect.deSelect(center);
	} else if (curButton == selectall) {
	    toolSelectall.deSelect(center);
	} else*/
	// ming 4.24 end
	if (curButton == curve) {
	    toolCurve.deSelect(center);
	} else if (curButton == polygon) {
	    toolPolygon.deSelect(center);
	}
	emboss emb = new emboss(this, true);
	emb.setVisible(true);
    }
     /** This function performs the edge detection capabilities of TerpPaint.
     * 1. Deselect any selections
     * 2. create new edge image (dialog box pops up)
     * It should work with all operating  systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */

    public void EdgeActionPerformed(java.awt.event.ActionEvent evt) {
	//Istvan
	// ming 4.24
	/*
	if (curButton == select) {
	    toolSelect.deSelect(center);
	} else if (curButton == magicSelect) {
	    toolMagicSelect.deSelect(center);
	} else if (curButton == selectall) {
	    toolSelectall.deSelect(center);
	} else*/
	// ming 4.24 end
	if (curButton == curve) {
	    toolCurve.deSelect(center);
	} else if (curButton == polygon) {
	    toolPolygon.deSelect(center);
	}
	edge emb = new edge(this, true);
     //	  emb.setVisible(true);
    }

    /* ming */

    //Istvan
	/** This function opens the preferences dialog box.
     * 1. Deselect any selections
     * 2. create new preferences object (dialog box pops up)
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void PreferencesActionPerformed(java.awt.event.ActionEvent evt) {
	if (curButton == select) {
	    toolSelect.deSelect(center);
	} else if (curButton == magicSelect) {
	    toolMagicSelect.deSelect(center);
	} else if (curButton == selectall) {
	    toolSelectall.deSelect(center);
	} else if (curButton == curve) {
	    toolCurve.deSelect(center);
	} else if (curButton == polygon) {
	    toolPolygon.deSelect(center);
	}
	preferences pref = new preferences(this, true);
	pref.setVisible(true);
	// ming 4.22
	updateUndoList();
	// ming 4.22 end
    }

    /** Displays color_menu panel if not already visibile, closes panel if is visibile.
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void ColorBoxActionPerformed(java.awt.event.ActionEvent evt) {
	if(color_menu.isVisible()){
	    color_menu.setVisible(false);
	    current_colors.setVisible(false);
	}
	else {
	    color_menu.setVisible(true);
	    current_colors.setVisible(true);
	}
    }

    /** Displays toolbox panel if not already visibile, closes panel if is visibile.
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void ToolBoxActionPerformed(java.awt.event.ActionEvent evt) {
	if(shapes.isVisible()){
	    shapes.setVisible(false);
	    this.toggleProperties();
	}
	else{
	    shapes.setVisible(true);
	    //TODO: check for current tool, and make that menu active here
	}
    }
//Istvan
    /** This function performs the saving operation
     * 1. Deselect any selections
     * 2. calls save()
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void SaveAsActionPerformed(java.awt.event.ActionEvent evt) {
	if (curButton == select) {
	    toolSelect.deSelect(center);
	} else if (curButton == magicSelect) {
	    toolMagicSelect.deSelect(center);
	} else if (curButton == selectall) {
	    toolSelectall.deSelect(center);
	} else if (curButton == curve) {
	    toolCurve.deSelect(center);
	} else if (curButton == polygon) {
	    toolPolygon.deSelect(center);
	}
	save();
	// ming 4.24
	center.noChanges();
	// ming 4.24 end
    }
//Istvan
    /** This function performs the saving operation
     * 1. Deselect any selections
     * 2. calls close()
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void CloseActionPerformed(java.awt.event.ActionEvent evt) {
	if (curButton == select) {
	    toolSelect.deSelect(center);
	} else if (curButton == magicSelect) {
	    toolMagicSelect.deSelect(center);
	} else if (curButton == selectall) {
	    toolSelectall.deSelect(center);
	} else if (curButton == curve) {
	    toolCurve.deSelect(center);
	} else if (curButton == polygon) {
	    toolPolygon.deSelect(center);
	}
	try
		{
		     FileWriter out = new FileWriter("windowRegistry.txt");
		     BufferedWriter b = new BufferedWriter( out );


		     Double xd = new Double( this.getLocation().getX() );
		     Double yd = new Double(this.getLocation().getY()) ;
			Double wd = new Double(this.getSize().getWidth());
		     Double hd = new Double(this.getSize().getHeight());
		     Double iwd = new Double(center.widt);
			Double ihd = new Double(center.heig);

		     b.write( xd.intValue() + "\n" +
			    yd.intValue() + "\n" +
					wd.intValue() + "\n" +
					hd.intValue() + "\n"+
					iwd.intValue() + "\n" +
					ihd.intValue() + "\n" );
			 b.close();
		 }
	 catch( Exception e ) {}
	close();
    }
//Istvan
    /** This function performs the saving operation
     * 1. Deselect any selections
     * 2. calls exit()
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void ExitActionPerformed(java.awt.event.ActionEvent evt) {
	if (curButton == select) {
	    toolSelect.deSelect(center);
	} else if (curButton == magicSelect) {
	    toolMagicSelect.deSelect(center);
	} else if (curButton == selectall) {
	    toolSelectall.deSelect(center);
	} else if (curButton == curve) {
	    toolCurve.deSelect(center);
	} else if (curButton == polygon) {
	    toolPolygon.deSelect(center);
	}
	try
		{
		     FileWriter out = new FileWriter("windowRegistry.txt");
		     BufferedWriter b = new BufferedWriter( out );


			Double xd = new Double( this.getLocation().getX() );
		     Double yd = new Double(this.getLocation().getY()) ;
			Double wd = new Double(this.getSize().getWidth());
		     Double hd = new Double(this.getSize().getHeight());
		     Double iwd = new Double(center.widt);
			Double ihd = new Double(center.heig);
			if(myPrefs.FixedSize){
				iwd = new Double(myPrefs.Width);
				ihd = new Double(myPrefs.Height);
			}
			//layer box status
			int lbx;
			if(alllayers.isVisible() == true )
				lbx = 1;
			else
				lbx = 0;
			//tool box status
			int tbx;
			if(shapes.isVisible() == true )
				tbx = 1;
			else
				tbx = 0;
			//status box status
			int sbx;
			if(status.isVisible() == true )
				sbx = 1;
			else
				sbx = 0;
			//color box status
			int cbx;
			if(color_menu.isVisible() == true )
				cbx = 1;
			else
				cbx = 0;
				
		     b.write( xd.intValue() + "\n" +
			    yd.intValue() + "\n" +
					wd.intValue() + "\n" +
					hd.intValue() + "\n"+
					iwd.intValue() + "\n" +
					ihd.intValue() + "\n" +
					tbx + "\n" +
					cbx + "\n" +
					sbx + "\n" +
					lbx + "\n");
			 b.close();
		 }
	 catch( Exception e ) {}
	exit();
    }
//Istvan, needs tpt format
    /** This function performs the saving operation
     * 1. Deselect any selections
     * 2. if there is something open, offer option to save it.
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void OpenActionPerformed(java.awt.event.ActionEvent evt) {
	if (curButton == select) {
	    toolSelect.deSelect(center);
	} else if (curButton == magicSelect) {
	    toolMagicSelect.deSelect(center);
	} else if (curButton == selectall) {
	    toolSelectall.deSelect(center);
	} else if (curButton == curve) {
	    toolCurve.deSelect(center);
	} else if (curButton == polygon) {
	    toolPolygon.deSelect(center);
	}

	if(center.madeChanges(this)){
	    System.out.println("MADE CHANGES");
	    saveChanges save = new saveChanges(this, true,CURRENT_FILE);
	    save.setVisible(true);
	    if(save.CHOICE == choice.CANCEL){
		System.out.println("CANCELLING");
		return;
	    }
	    else if(save.CHOICE == choice.YES){
		if (filename.compareTo("null") != 0)
		    saveFast();
		else
		    save();
	    }
	    else if(save.CHOICE == choice.NO){
		System.out.println("NO");
	    }
	}

		JFileChooser chooser = new JFileChooser();
	TerpPaintFileFilter bmp = new TerpPaintFileFilter(new String[]{"bmp", "dib"}, "Monochrome Bitmap");
	TerpPaintFileFilter jpeg = new TerpPaintFileFilter(new String[]{"jpeg", "jpg"}, "JPEG Image");
	TerpPaintFileFilter gif = new TerpPaintFileFilter(new String("gif"), "GIF Image");
	TerpPaintFileFilter tpt = new TerpPaintFileFilter(new String("tpt"), "TerpPaint Picture");
	TerpPaintFileFilter gifa = new TerpPaintFileFilter(new String("gif"), "GIF animated Image");
	chooser.addChoosableFileFilter(tpt);
	chooser.addChoosableFileFilter(gifa);
	chooser.addChoosableFileFilter(gif);
	chooser.addChoosableFileFilter(jpeg);
	chooser.addChoosableFileFilter(bmp);
	chooser.showOpenDialog(this);
	File chosen = chooser.getSelectedFile();

	// ming 4.12
	//if(chooser.CANCEL_OPTION==1 || chosen == null){
		if(chosen == null){
	// ming 4.12 end
	    return;
	}
	else{
	    filename = chosen.getAbsolutePath();
	    if(chooser.getFileFilter() == bmp || chosen.getName().endsWith(".bmp") || chosen.getName().endsWith(".dib")){
		try{
		    FileInputStream fis = new FileInputStream(chosen);
		    converter newImage = new converter();
		    // ming 4.24
		    center.noChanges();
		    // ming 4.24 end
		    // ming 4.26
		     LinkedList cur_action_list = (LinkedList)(action_list.get(center.currentLayer));
		       LinkedList cur_redo_list = (LinkedList)(redo_list.get(center.currentLayer));
		       int cur_size = cur_action_list.size();
		       for (int i=0; i<cur_size; i++)
			   cur_action_list.removeFirst();
		       cur_size = cur_redo_list.size();
		       for (int i=0; i<cur_size; i++)
			   cur_redo_list.removeFirst();
		    // ming 4.26 end
		    center.setBufferedImage(newImage.FileToBufferedImage(fis));
		    fis.close();
		}
		catch(Exception fisError){}
	    }
	    else if(chooser.getFileFilter() == jpeg || chosen.getName().endsWith(".jpeg") || chosen.getName().endsWith(".jpg")){
		try{
		    FileInputStream fis = new FileInputStream(chosen);
		    JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(fis);
		    // ming 4.24
		    center.noChanges();
		    // ming 4.24 end
		    // ming 4.26
		     LinkedList cur_action_list = (LinkedList)(action_list.get(center.currentLayer));
		   LinkedList cur_redo_list = (LinkedList)(redo_list.get(center.currentLayer));
		   int cur_size = cur_action_list.size();
		   for (int i=0; i<cur_size; i++)
		       cur_action_list.removeFirst();
		   cur_size = cur_redo_list.size();
		   for (int i=0; i<cur_size; i++)
		       cur_redo_list.removeFirst();
		    // ming 4.26 end
		    center.setBufferedImage(decoder.decodeAsBufferedImage());
		    fis.close();
		}
		catch(Exception fisError){}
	    }
	    else if(chooser.getFileFilter() == gif || chosen.getName().endsWith(".gif")){
		try{

		    BufferedImage imageB = ImageUtilities.getBufferedImage(chosen.getPath(),this);
		    // ming 4.24
		    center.noChanges();
		    // ming 4.24 end
		    // ming 4.26
		     LinkedList cur_action_list = (LinkedList)(action_list.get(center.currentLayer));
		       LinkedList cur_redo_list = (LinkedList)(redo_list.get(center.currentLayer));
		       int cur_size = cur_action_list.size();
		       for (int i=0; i<cur_size; i++)
			   cur_action_list.removeFirst();
		       cur_size = cur_redo_list.size();
		       for (int i=0; i<cur_size; i++)
			   cur_redo_list.removeFirst();
		    // ming 4.26 end
		    center.setBufferedImage(imageB);
		}catch(Exception e){
		    e.printStackTrace();
		}
	    }
	    else if(chooser.getFileFilter() == tpt || chosen.getName().endsWith(".tpt")){
//Istvan 5.7pm
				try{
				FileInputStream fis = new FileInputStream(chosen);
		    DataInputStream reader = new DataInputStream(fis);
		    String in = new String();
		    in = reader.readLine();
		    String newName = new String();
		    int len=chosen.getAbsolutePath().length();
		    String name=chosen.getAbsolutePath().substring(0,len-4);

		    for (int i = 1; i <= Double.parseDouble(in); i++)
				{//5630
						try{
							//System.out.println(name+"  "+(String)(name+i+".jpg"));
							FileInputStream subPic = new FileInputStream((String)(name+i+".jpg"));
							JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(subPic);


							center.setBufferedImage(decoder.decodeAsBufferedImage());

							//alllayers.center.repaint();
							//alllayers.center.addLayer();
							//alllayers.newLayer();
							if(i < Double.parseDouble(in))
								alllayers.addActionPerformed(evt);
							//wrong from here
							//Layer temporary = (Layer) alllayers.holder.elementAt(i-1);
							//temporary.layerCanvas.setBufferedImage(decoder.decodeAsBufferedImage());
							//fis.close();
							subPic.close();
						}catch(Exception e){System.out.println(e);}

				}
		    reader.close();

		}catch(Exception e){
		    e.printStackTrace();
		}
		//End Istvan 5.7pm
	    }else if(chooser.getFileFilter() == gifa || chosen.getName().endsWith(".gif")){
		try{
			/*Ana*/
			FileInputStream fis = new FileInputStream(chosen);
					GifDecoder d = new GifDecoder();
					d.read(fis);

					// ming 4.24
					center.noChanges();
					// ming 4.24 end

					int n = d.getFrameCount();
					// Set the first lane as the current frame
					center.setBufferedImage(d.getFrame(0));
					for (int i = 0; i < n; i++) {
			center.layerList.add(d.getFrame(i));
		    }
		    /*End Ana*/
		}catch(Exception e){
		    e.printStackTrace();
		}
	    }else{
		int caonima = 1;
		//This is not a valid file
	    }
	    setTitle("TerpPaint - "+chosen.getName());
	    CURRENT_FILE = chosen.getName();
	    /* ming */

	    updateUndoList();
	    /* ming */
	    // ming 4.12
	    center.zoomFactor = 1;
	    center.oldZoomFactor = 1;
	    // ming 4.12 end
	}
	int filenum = -1;
	// ming 4.12
	if (recentFiles!=null)
	{
	// ming 4.12 end
	for( int i = 0; i < recentFiles.size(); i ++ )
	{
	    if( chosen.getAbsolutePath().compareTo(((File) recentFiles.elementAt( i )).getAbsolutePath() ) == 0 )
		filenum = i;
	}
	if( filenum != -1 )	// already is a recent document
	{
	    recentFiles.removeElementAt( filenum );
	    recentFiles.insertElementAt( chosen, 0 );
	}
	else		// add to front of recentFiles
	{
	    recentFiles.insertElementAt( chosen, 0 );
	    if( recentFiles.size() > 4 )
		recentFiles.setSize( 4 );

	}
	// ming 4.12
	}
	// ming 4.12 end
	//center.noChanges();
	// ming 4.12
	if (recentFiles!=null)
	// ming 4.12 end
	    updateFileMenu();
    }

/** This method is used to interact with TerpManager, It takes a File object which is an image and returns it in BufferedImage form.
* @param f File object to convert
* @return image as a BufferedImage
*/

public static BufferedImage getIcon(File f)
{
	BufferedImage bi= new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
	if(f.getName().endsWith(".bmp") || f.getName().endsWith(".dib"))
	{
		try
		{
			FileInputStream fis = new FileInputStream(f);
			converter newImage = new converter();
			
		    	bi=newImage.FileToBufferedImage(fis);
		    	fis.close();
		}
		catch(Exception fisError){}
	}
	else if(f.getName().endsWith(".jpeg") || f.getName().endsWith(".jpg"))
	{
		try
		{
			FileInputStream fis = new FileInputStream(f);
			JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(fis);
			
			bi=decoder.decodeAsBufferedImage();
			System.out.println(f);
			
			
			fis.close();
		}
		catch(Exception fisError){}
	}
	else if(f.getName().endsWith(".gif"))
	{
		try
		{
			FileInputStream fis = new FileInputStream(f);
			GifDecoder gd = new GifDecoder();
			gd.read(fis);
			
			bi=gd.getImage();
		}
		catch(Exception fisError){}
	}
	else if(f.getName().endsWith(".tpt"))
	{
		try{
			FileInputStream fis = new FileInputStream(f);
			DataInputStream reader = new DataInputStream(fis);
			String in = new String();
			in = reader.readLine();
			String newName = new String();
			int len=f.getAbsolutePath().length();
			String name=f.getAbsolutePath().substring(0,len-4);
	    		for (int i = 1; i <= Double.parseDouble(in); i++)
			{//5630
				try{
					//System.out.println(name+"  "+(String)(name+i+".jpg"));
					FileInputStream subPic = new FileInputStream((String)(name+i+".jpg"));
					JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(subPic);


					bi=decoder.decodeAsBufferedImage();
					subPic.close();
					break;
				}catch(Exception e){System.out.println(e);}

			}
		    	reader.close();

		}catch(Exception e){
		    	e.printStackTrace();
		}
	}
	return bi;
}  
//Istvan
    /** This function performs the inverting colors operation.
     * 1. Deselect any selections
     * 2. call invertColors().
     * 3. update canvas
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void InvertColorsActionPerformed(java.awt.event.ActionEvent evt) {
	// Add your handling code here:
	if (curButton == select) {
	    toolSelect.deSelect(center);
	} else if (curButton == magicSelect) {
	    toolMagicSelect.deSelect(center);
	} else if (curButton == selectall) {
	    toolSelectall.deSelect(center);
	} else if (curButton == curve) {
	    toolCurve.deSelect(center);
	} else if (curButton == polygon) {
	    toolPolygon.deSelect(center);
	}
	center.invertColors(this);
	center.repaint();
	updateUndoList();
    }

    /** no code -- may be same as code found in centerSetup().
     * @param evt MosueEvent
     */
    public void canvasMouseReleased(java.awt.event.MouseEvent evt){

    }

    /** no code -- may be same as found in centerSetup().
     * @param evt MouseEvent
     */
    public void canvasMousePressed(java.awt.event.MouseEvent evt) {

    }
//Istvan
    /** Initializes selectTool (polygonal selection tool)
     * 1. Deselect everything
     * 2. Press in button
     * 3. Initialize tool and mouse cursor
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent panel button press
     */
    public void selectActionPerformed(java.awt.event.ActionEvent evt) {
	toggleProperties();
	none.setVisible(true);
	if (curButton == select) {
	    toolSelect.deSelect(center);
	} else if (curButton == magicSelect) {
	    toolMagicSelect.deSelect(center);
	} else if (curButton == selectall) {
	    toolSelectall.deSelect(center);
	} else if (curButton == curve) {
	    toolCurve.deSelect(center);
	} else if (curButton == polygon) {
	    toolPolygon.deSelect(center);
	}
	// to make it look like a button is pressed
	curButton.setBorder(BorderFactory.createRaisedBevelBorder());
	curButton = select;
	curButton.setBorder(BorderFactory.createLoweredBevelBorder());
	currentTool = toolSelect;
	if(MacFlag == 1)
	    currentCursor = defaultCursor;
	else{
	    Cursor lassoCursor = tk.createCustomCursor(lassoImg,new Point(15,15),"Lasso Cursor");
	    currentCursor = lassoCursor;
	}
    }

    //Istvan
	/** Initializes magicSelectTool (magic wand selection tool)
     * 1. Deselect everything
     * 2. Press in button
     * 3. Initialize tool and mouse cursor
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent panel button press
     */
    public void magicSelectActionPerformed(java.awt.event.ActionEvent evt) {
	toggleProperties();
	none.setVisible(true);
	if (curButton == select) {
	    toolSelect.deSelect(center);
	} else if (curButton == magicSelect) {
	    toolMagicSelect.deSelect(center);
	} else if (curButton == selectall) {
	    toolSelectall.deSelect(center);
	} else if (curButton == curve) {
	    toolCurve.deSelect(center);
	} else if (curButton == polygon) {
	    toolPolygon.deSelect(center);
	}
	// to make it look like a button is pressed
	curButton.setBorder(BorderFactory.createRaisedBevelBorder());
	curButton = magicSelect;
	curButton.setBorder(BorderFactory.createLoweredBevelBorder());
	currentTool = toolMagicSelect;
	if(MacFlag == 1)
	    currentCursor = defaultCursor;
	else{
	    Cursor lassoCursor = tk.createCustomCursor(lassoImg,new Point(15,15),"Lasso Cursor");
	    currentCursor = lassoCursor;
	}
    }
    //end added


 //istvan
    /** Initializes textwriting
     * 1. Deselect everything
     * 2. Press in button
     * 3. Initialize tool and mouse cursor
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent panel button press
     */
    public void letterActionPerformed(java.awt.event.ActionEvent evt) {
	toggleProperties();
	none.setVisible(true);
	if (curButton == select) {
	    toolSelect.deSelect(center);
	} else if (curButton == magicSelect) {
	    toolMagicSelect.deSelect(center);
	} else if (curButton == selectall) {
	    toolSelectall.deSelect(center);
	} else if (curButton == curve) {
	    toolCurve.deSelect(center);
	} else if (curButton == polygon) {
	    toolPolygon.deSelect(center);
	}
	curButton.setBorder(BorderFactory.createRaisedBevelBorder());
	curButton = letter;
	curButton.setBorder(BorderFactory.createLoweredBevelBorder());
	currentTool = toolLetter;
	if(MacFlag == 1)
	    currentCursor = defaultCursor;
	else{
	    Cursor letterCursor = new Cursor(Cursor.TEXT_CURSOR);
	    currentCursor = letterCursor;
	}
    }
//Istvan
    /** Initializes sprayTool
     * 1. Deselect everything
     * 2. Press in button
     * 3. Initialize tool and mouse cursor
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent panel button press
     */
    public void sprayActionPerformed(java.awt.event.ActionEvent evt) {
	toggleProperties();
	spray_properties.setVisible(true);
	if (curButton == select) {
	    toolSelect.deSelect(center);
	} else if (curButton == magicSelect) {
	    toolMagicSelect.deSelect(center);
	} else if (curButton == selectall) {
	    toolSelectall.deSelect(center);
	} else if (curButton == curve) {
	    toolCurve.deSelect(center);
	} else if (curButton == polygon) {
	    toolPolygon.deSelect(center);
	}
	curButton.setBorder(BorderFactory.createRaisedBevelBorder());
	curButton = spray;
	curButton.setBorder(BorderFactory.createLoweredBevelBorder());
	currentTool = toolSpray;
	if(MacFlag == 1)
	    currentCursor = defaultCursor;
	else{
	    Cursor airbrushCursor = tk.createCustomCursor(airbrushImg,new Point(9,24),"Airbrush Cursor");
	    currentCursor = airbrushCursor;
	}
    }
//Istvan
    /** Initializes brushTool
     * 1. Deselect everything
     * 2. Press in button
     * 3. Initialize tool and mouse cursor
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent panel button press
     */
    public void brushActionPerformed(java.awt.event.ActionEvent evt) {
	toggleProperties();
	brush_properties.setVisible(true);
	if (curButton == select) {
	    toolSelect.deSelect(center);
	} else if (curButton == magicSelect) {
	    toolMagicSelect.deSelect(center);
	} else if (curButton == selectall) {
	    toolSelectall.deSelect(center);
	} else if (curButton == curve) {
	    toolCurve.deSelect(center);
	} else if (curButton == polygon) {
	    toolPolygon.deSelect(center);
	}
	curButton.setBorder(BorderFactory.createRaisedBevelBorder());
	curButton = brush;
	curButton.setBorder(BorderFactory.createLoweredBevelBorder());
	currentTool = toolBrush;
	if(MacFlag == 1)
	    currentCursor = defaultCursor;
	else{
/*Ronald 5.2*/	Cursor brushCursor = tk.createCustomCursor(brushImg,new Point(3,23),"Brush Cursor");
	    currentCursor = brushCursor;
	}
    }
//Istvan
    /** Initializes pencilTool
     * 1. Deselect everything
     * 2. Press in button
     * 3. Initialize tool and mouse cursor
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent panel button press
     */
    public void pencilActionPerformed(java.awt.event.ActionEvent evt) {
	toggleProperties();
	none.setVisible(true);
	if (curButton == select) {
	    toolSelect.deSelect(center);
	} else if (curButton == magicSelect) {
	    toolMagicSelect.deSelect(center);
	} else if (curButton == selectall) {
	    toolSelectall.deSelect(center);
	} else if (curButton == curve) {
	    toolCurve.deSelect(center);
	} else if (curButton == polygon) {
	    toolPolygon.deSelect(center);
	}
	curButton.setBorder(BorderFactory.createRaisedBevelBorder());
	curButton = pencil;
	curButton.setBorder(BorderFactory.createLoweredBevelBorder());
	currentTool = toolPencil;
	if(MacFlag == 1)
	    currentCursor = defaultCursor;
	else{
	    Cursor pencilCursor = tk.createCustomCursor(pencilImg,new Point(10,24),"Pencil Cursor");
	    currentCursor = pencilCursor;
	}
    }
//Istvan
    /** Initializes zoomTool
     * 1. Deselect everything
     * 2. Press in button
     * 3. Initialize tool and mouse cursor
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent panel button press
     */
    public void zoomActionPerformed(java.awt.event.ActionEvent evt) {
	toggleProperties();
	zoom_properties.setVisible(true);
	if (curButton == select) {
	    toolSelect.deSelect(center);
	} else if (curButton == magicSelect) {
	    toolMagicSelect.deSelect(center);
	} else if (curButton == selectall) {
	    toolSelectall.deSelect(center);
	} else if (curButton == curve) {
	    toolCurve.deSelect(center);
	} else if (curButton == polygon) {
	    toolPolygon.deSelect(center);
	}
	curButton.setBorder(BorderFactory.createRaisedBevelBorder());
	curButton = zoom;
	curButton.setBorder(BorderFactory.createLoweredBevelBorder());
	currentTool = toolZoom;
	if(MacFlag == 1)
	    currentCursor = defaultCursor;
	else{
	    Cursor zoomCursor = tk.createCustomCursor(zoomImg,new Point(15,15),"Zoom Cursor");
	    currentCursor = zoomCursor;
	}
    }
//Istvan
    /** Initializes medicineTool
     * 1. Deselect everything
     * 2. Press in button
     * 3. Initialize tool and mouse cursor
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent
     */
    public void medicineActionPerformed(java.awt.event.ActionEvent evt) {
	toggleProperties();
	none.setVisible(true);
	if (curButton == select) {
	    toolSelect.deSelect(center);
	} else if (curButton == magicSelect) {
	    toolMagicSelect.deSelect(center);
	} else if (curButton == selectall) {
	    toolSelectall.deSelect(center);
	} else if (curButton == curve) {
	    toolCurve.deSelect(center);
	} else if (curButton == polygon) {
	    toolPolygon.deSelect(center);
	}
	curButton.setBorder(BorderFactory.createRaisedBevelBorder());
	curButton = medicine;
	curButton.setBorder(BorderFactory.createLoweredBevelBorder());
	currentTool = toolMedicine;
	if(MacFlag == 1)
	    currentCursor = defaultCursor;
	else{
	    Cursor dropperCursor = tk.createCustomCursor(dropperImg,new Point(7,24),"Dropper Cursor");
	    currentCursor = dropperCursor;
	}
    }

//Istvan
    /** Deselects other tools and sets current tool to toolBucket.  Sets current button to paint button and sets current cursor to bucketCursor.
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     * @param evt ActionEvent reference
     */
  public void paintActionPerformed (java.awt.event.ActionEvent evt) {
      toggleProperties();
      none.setVisible(true);
      if (curButton == select) {
	  toolSelect.deSelect(center);
      } else if (curButton == magicSelect) {
	   toolMagicSelect.deSelect(center);
      } else if (curButton == selectall) {
	  toolSelectall.deSelect(center);
      } else if (curButton == curve) {
	  toolCurve.deSelect(center);
      } else if (curButton == polygon) {
	  toolPolygon.deSelect(center);
      }
      curButton.setBorder(BorderFactory.createRaisedBevelBorder());
      curButton = paint;
      curButton.setBorder(BorderFactory.createLoweredBevelBorder());
      currentTool = toolBucket;
      if(MacFlag == 1)
	  currentCursor = defaultCursor;
      else{
	  Cursor bucketCursor = tk.createCustomCursor(bucketImg,new Point(8,22),"Bucket Cursor");
	  currentCursor = bucketCursor;
      }
  }
//Istvan
  /** Deselects other tools and changes current tool to tool Changes current cursor to eraserCursor and changes current button to eraser button.
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @param evt ActionEvent reference
   */
  public void eraserActionPerformed (java.awt.event.ActionEvent evt) {
      toggleProperties();
      none.setVisible(true);
      if (curButton == select) {
	  toolSelect.deSelect(center);
      } else if (curButton == magicSelect) {
	  toolMagicSelect.deSelect(center);
      } else if (curButton == selectall) {
	  toolSelectall.deSelect(center);
      } else if (curButton == curve) {
	  toolCurve.deSelect(center);
      } else if (curButton == polygon) {
	  toolPolygon.deSelect(center);
      }
      curButton.setBorder(BorderFactory.createRaisedBevelBorder());
      curButton = eraser;
      curButton.setBorder(BorderFactory.createLoweredBevelBorder());
      currentTool = toolEraser;
      if(MacFlag == 1) {
	  currentCursor = defaultCursor;
      }
      else{
	    if(curZoom==zoom1){
	      Cursor eraserCursor = tk.createCustomCursor(eraserImg,new Point(15,15),"Eraser Cursor 1");
	      currentCursor = eraserCursor;
	      System.out.println("Orginial eraser");
	  }
	  else if(curZoom==zoom2){
	      Cursor eraserCursor = tk.createCustomCursor(eraserImg2,new Point(15,15),"Eraser Cursor 2");
	      currentCursor = eraserCursor;
	      System.out.println("Orginial eraser");
	  }
	  else if(curZoom==zoom3){
	      Cursor eraserCursor = tk.createCustomCursor(eraserImg3,new Point(15,15),"Eraser Cursor 3");
	      currentCursor = eraserCursor;
	      System.out.println("Orginial eraser");
	  }
	  else if(curZoom==zoom4){
	      Cursor eraserCursor = tk.createCustomCursor(eraserImg4,new Point(15,15),"Eraser Cursor 4");
	      currentCursor = eraserCursor;
	      System.out.println("Orginial eraser");
	  }
      }
  }
//modified
  /** Deselects other tools and changes current tool to toolSelectall.	Changes current button to selectall button and changes current cursor to defaultCursor.
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @param evt ActionEvent reference
   */
  public void selectallActionPerformed (java.awt.event.ActionEvent evt) {
      toggleProperties();
      none.setVisible(true);
      if (curButton == select) {
	  toolSelect.deSelect(center);
      } else if (curButton == magicSelect) {
	  toolMagicSelect.deSelect(center);
      } else if (curButton == selectall) {
	  toolSelectall.deSelect(center);
      } else if (curButton == curve) {
	  toolCurve.deSelect(center);
      } else if (curButton == polygon) {
	  toolPolygon.deSelect(center);
      }
      curButton.setBorder(BorderFactory.createRaisedBevelBorder());
      curButton = selectall;
      curButton.setBorder(BorderFactory.createLoweredBevelBorder());
      currentTool = toolSelectall;
      currentCursor = defaultCursor;
	// ming 4.12
      /*
	int temp = redo_list.size();
	    for (int i=0; i<temp; i++)
		redo_list.removeLast();
	action_list.add(new String("Select all"));
	updateUndoList();
	//initComponents();*/
	// ming 4.12 end

  }
//modified
  /** Deselects other tools and changes current tool to toolEllipse.  Changes current button to elipse button and changes current cursor to defaultCursor.  Also sets current elippse button background color.
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @param evt ActionEvent reference
   */
  public void elipseActionPerformed (java.awt.event.ActionEvent evt) {	   toggleProperties();
      rect_properties.setVisible(true);
      if (curButton == select) {
	  toolSelect.deSelect(center);
      } else if (curButton == magicSelect) {
	  toolMagicSelect.deSelect(center);
      } else if (curButton == selectall) {
	  toolSelectall.deSelect(center);
      } else if (curButton == curve) {
	  toolCurve.deSelect(center);
      } else if (curButton == polygon) {
	  toolPolygon.deSelect(center);
      }
      curButton.setBorder(BorderFactory.createRaisedBevelBorder());
      curButton = elipse;
      curButton.setBorder(BorderFactory.createLoweredBevelBorder());
      currentTool = toolEllipse;
      currentCursor = defaultCursor;
      clearRect();
      curEllipse.setBackground(propColor);
  }
//modified
  /** Deselects other tools and changes current tool to toolPolygon.  Changes current button to polygon button and changes current cursor to defaultCursor.  Also sets current polygon button background color.
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @param evt ActionEvent reference
   */
  public void polygonActionPerformed (java.awt.event.ActionEvent evt) {	    toggleProperties();
      rect_properties.setVisible(true);
      if (curButton == select) {
	  toolSelect.deSelect(center);
      } else if (curButton == magicSelect) {
	  toolMagicSelect.deSelect(center);
      } else if (curButton == selectall) {
	  toolSelectall.deSelect(center);
      } else if (curButton == curve) {
	  toolCurve.deSelect(center);
      } else if (curButton == polygon) {
	  toolPolygon.deSelect(center);
      }
      curButton.setBorder(BorderFactory.createRaisedBevelBorder());
      curButton = polygon;
      curButton.setBorder(BorderFactory.createLoweredBevelBorder());
      currentTool = toolPolygon;
      currentCursor = defaultCursor;
      clearRect();
      curPolygon.setBackground(propColor);
  }
//modified
  /** Deselects other tools and changes current tool to toolRect.  Changes current button to square button and changes current cursor to default cursor.  Also sets current square button background color.
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @param evt ActionEvent reference
   */
  public void squareActionPerformed (java.awt.event.ActionEvent evt) {
      toggleProperties();
      rect_properties.setVisible(true);
      if (curButton == select) {
	  toolSelect.deSelect(center);
      } else if (curButton == magicSelect) {
	  toolMagicSelect.deSelect(center);
      } else if (curButton == selectall) {
	  toolSelectall.deSelect(center);
      } else if (curButton == curve) {
	  toolCurve.deSelect(center);
      } else if (curButton == polygon) {
	  toolPolygon.deSelect(center);
      }
      currentTool = toolRect;
      curButton.setBorder(BorderFactory.createRaisedBevelBorder());
      curButton = square;
      curButton.setBorder(BorderFactory.createLoweredBevelBorder());
      currentCursor = defaultCursor;
      clearRect();
      curSquare.setBackground(propColor);
  }
//modified
  /** Deselects other tools and changes current tool to toolCurve.  Changes current button to curve button and changes current cursor to defaultCursor.	 Also sets current curve button background color.
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @param evt ActionEvent reference
   */
  public void curveActionPerformed (java.awt.event.ActionEvent evt) {	   toggleProperties();
      line_properties.setVisible(true);
      if (curButton == select) {
	  toolSelect.deSelect(center);
      } else if (curButton == magicSelect) {
	  toolMagicSelect.deSelect(center);
      } else if (curButton == selectall) {
	  toolSelectall.deSelect(center);
      } else if (curButton == curve) {
	  toolCurve.deSelect(center);
      } else if (curButton == polygon) {
	  toolPolygon.deSelect(center);
      }
      curButton.setBorder(BorderFactory.createRaisedBevelBorder());
      curButton = curve;
      curButton.setBorder(BorderFactory.createLoweredBevelBorder());
      currentTool = toolCurve;
      currentCursor = defaultCursor;

      clearLine();
      curCurve.setBackground(propColor);
  }





  /** Prints "CANVAS MOUSE MOVED".
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @param evt MouseEvent reference
   */
  public void canvasMouseMoved (java.awt.event.MouseEvent evt) {
      System.out.println("CANVAS MOUSE MOVED");
  }

  /** Sets orange as the active color by mouse click.
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @param evt MouseEvent reference
   */
  public void orangeMouseClicked (java.awt.event.MouseEvent evt) {
      setActiveColors(evt, orange);
      currentColor = orange;
      //center.setColor(orange.getBackground());
  }
//new color add
 /** Sets color1 as the active color by mouse click.
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @param evt MouseEvent reference
   */
  public void color1MouseClicked (java.awt.event.MouseEvent evt) {
      setActiveColors(evt, color1);
      currentColor = color1; /*FAULT:: currentColor = color2; */
      //center.setColor(orange.getBackground());
  }
 /** Sets color2 as the active color by mouse click.
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @param evt MouseEvent reference
   */
  public void color2MouseClicked (java.awt.event.MouseEvent evt) {
      setActiveColors(evt, color2);
      currentColor = color2; /*FAULT:: currentColor = color3; */
      //center.setColor(colors2.getBackground());
  }
 /** Sets color3 as the active color by mouse click.
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @param evt MouseEvent reference
   */
  public void color3MouseClicked (java.awt.event.MouseEvent evt) {
      setActiveColors(evt, color3);
      currentColor = color3; /*FAULT:: currentColor = color2; */
      //center.setColor(colors3.getBackground());
  }
 /** Sets color4 as the active color by mouse click.
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @param evt MouseEvent reference
   */
  public void color4MouseClicked (java.awt.event.MouseEvent evt) {
      setActiveColors(evt, color4);
      currentColor = color4; /*FAULT:: currentColor = color2; */
      //center.setColor(colors4.getBackground());
  }
 /** Sets color5 as the active color by mouse click.
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @param evt MouseEvent reference
   */
  public void color5MouseClicked (java.awt.event.MouseEvent evt) {
      setActiveColors(evt, color5);
      currentColor = color5; /*FAULT:: currentColor = color2; */
      //center.setColor(colors5.getBackground());
  }
 /** Sets color6 as the active color by mouse click.
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @param evt MouseEvent reference
   */
  public void color6MouseClicked (java.awt.event.MouseEvent evt) {
      setActiveColors(evt, color6);
      currentColor = color6; /*FAULT:: currentColor = color2; */
      //center.setColor(colors6.getBackground());
  }
 /** Sets color7 as the active color by mouse click.
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @param evt MouseEvent reference
   */
  public void color7MouseClicked (java.awt.event.MouseEvent evt) {
      setActiveColors(evt, color7);
      currentColor = color7; /*FAULT:: currentColor = color2; */
      //center.setColor(colors7.getBackground());
  }

//new color added
  /** Sets brown as the active color by mouse click.
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @param evt MouseEvent reference
   */
  public void brownMouseClicked (java.awt.event.MouseEvent evt) {
      setActiveColors(evt, brown);
      currentColor = brown; /**FAULT:: currentColor = red; */
      //center.setColor(brown.getBackground());
  }

  /** Sets black as the active color by mouse click.
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @param evt MouseEvent reference
   */
  public void blackMouseClicked (java.awt.event.MouseEvent evt) {
      setActiveColors(evt, black);
      currentColor =  black;
      //center.setColor(black.getBackground());
  }

  /** Sets dark gray as the active color by mouse click.
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @param evt MouseEvent reference
   */
  public void drk_grayMouseClicked (java.awt.event.MouseEvent evt) {
      setActiveColors(evt, drk_gray);
      currentColor = drk_gray;
      //center.setColor(drk_gray.getBackground());
  }

  /** Sets gray as the active color by mouse click.
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @param evt MouseEvent reference
   */
  public void grayMouseClicked (java.awt.event.MouseEvent evt) {
      setActiveColors(evt, gray);
      currentColor = gray;
      //center.setColor(gray.getBackground());
  }

  /** Sets light gray as the active color by mouse click.
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @param evt MouseEvent reference
   */
  public void lgt_grayMouseClicked (java.awt.event.MouseEvent evt) {
      setActiveColors(evt, lgt_gray);
      currentColor = lgt_gray;
      //center.setColor(lgt_gray.getBackground());
  }

  /** Sets white as the active color by mouse click.
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @param evt MouseEvent reference
   */
  public void whiteMouseClicked (java.awt.event.MouseEvent evt) {
      setActiveColors(evt, white);
      currentColor = white;
      //center.setColor(white.getBackground());
  }
  /** Sets pink as the active color by mouse click.
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @param evt MouseEvent reference
   */
  public void pinkMouseClicked (java.awt.event.MouseEvent evt) {
      setActiveColors(evt, pink);
      currentColor = pink;
      //center.setColor(pink.getBackground());
  }
  /** Sets cyan as the active color by mouse click.
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @param evt MouseEvent reference
   */
  public void cyanMouseClicked (java.awt.event.MouseEvent evt) {
      setActiveColors(evt, cyan);
      currentColor = cyan;
      //center.setColor(cyan.getBackground());
  }

  /** Sets magenta as the active color by mouse click.
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @param evt MouseEvent reference
   */
  public void magentaMouseClicked (java.awt.event.MouseEvent evt) {
      setActiveColors(evt, magenta);
      currentColor = magenta;
      //center.setColor(magenta.getBackground());
  }

  /** Sets yellow as the active color by mouse click.
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @param evt MouseEvent reference
   */
  public void yellowMouseClicked (java.awt.event.MouseEvent evt) {
      setActiveColors(evt, yellow);
      currentColor = yellow;
      //center.setColor(yellow.getBackground());
  }

  /** Sets blue as the active color by mouse click.
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @param evt MouseEvent reference
   */
  public void blueMouseClicked (java.awt.event.MouseEvent evt) {
      setActiveColors(evt, blue);
      currentColor = blue;
      //center.setColor(blue.getBackground());
  }

  /** Sets green as the active color by mouse click.
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @param evt MouseEvent reference
   */
  public void greenMouseClicked (java.awt.event.MouseEvent evt) {
      setActiveColors(evt, green);
      currentColor = green;
      //center.setColor(green.getBackground());
  }


  /** Sets red as the active color by mouse click.
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @param evt MouseEvent reference
   */
  public void redMouseClicked (java.awt.event.MouseEvent evt) {
      setActiveColors(evt, red);
      currentColor = red;
  }
  /** Returns left button background color.
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @return Color object
   */
  public Color getCurrentColor(){
      return left.getBackground();
  }


  /** Sets current active color.
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @param evt MouseEvent reference
   * @param in JButton reference
   */
  public void setActiveColors(MouseEvent evt, JButton in){
      if(evt.getClickCount() == 2){

	  Color newColor = JColorChooser.showDialog(this, "Choose Background Color", in.getBackground());
	  if( newColor != null )
	      in.setBackground(newColor);

	  if (grayscaleFlag == 1){
	      changeButtonGray(in);
	  }
      }

      if( evt.getButton() == 1 ) {
	  left.setBackground(in.getBackground());
	  center.setLeftColor(in.getBackground());
      }

      if( evt.getButton() == 3 ) {
	  right.setBackground(in.getBackground());
	  center.setRightColor(in.getBackground());
      }

      //	    else if((evt.getModifiers() & evt.BUTTON3_MASK) == evt.BUTTON3_MASK){
      //	    right.setBackground(in.getBackground());
      //	    center.setRightColor(in.getBackground());
      //	}
      //	else{//left mouse button pressed
      //	    left.setBackground(in.getBackground());
      //	    center.setLeftColor(in.getBackground());
      //	}

      if( in == red )
	  colorTable.setElementAt( in.getBackground(), 0 );
      if( in == green )
	  colorTable.setElementAt( in.getBackground(), 1 );
      if( in == blue )
	  colorTable.setElementAt( in.getBackground(), 2 );
      if( in == yellow )
	  colorTable.setElementAt( in.getBackground(), 3 );
      if( in == magenta )
	  colorTable.setElementAt( in.getBackground(), 4 );
      if( in == cyan )
	  colorTable.setElementAt( in.getBackground(), 5 );
      if( in == pink )
	  colorTable.setElementAt( in.getBackground(), 6 );
      if( in == white )
	  colorTable.setElementAt( in.getBackground(), 7 );
      if( in == lgt_gray )
	  colorTable.setElementAt( in.getBackground(), 8 );
      if( in == gray )
	  colorTable.setElementAt( in.getBackground(), 9 );
      if( in == drk_gray )
	  colorTable.setElementAt( in.getBackground(), 10 );
      if( in == black )
	  colorTable.setElementAt( in.getBackground(), 11 );
      if( in == brown )
	  colorTable.setElementAt( in.getBackground(), 12 );
      if( in == orange )
	  colorTable.setElementAt( in.getBackground(), 13 );
//new color add
	  if( in == white )
	  colorTable.setElementAt( in.getBackground(), 14 );
      if( in == lgt_gray )
	  colorTable.setElementAt( in.getBackground(), 15 );
      if( in == gray )
	  colorTable.setElementAt( in.getBackground(), 16 );
      if( in == drk_gray )
	  colorTable.setElementAt( in.getBackground(), 17 );
      if( in == black )
	  colorTable.setElementAt( in.getBackground(), 18 );
      if( in == brown )
	  colorTable.setElementAt( in.getBackground(), 19 );
      if( in == orange )
	  colorTable.setElementAt( in.getBackground(), 20 );



      if( evt.getButton() == 1 )
	  colorTable.setElementAt( in.getBackground(), 21 );
      if( evt.getButton() == 3 )
	  colorTable.setElementAt( in.getBackground(), 22 );
//new color added

  }

//Istvan
  /** Deselects other tools and changes current button to rounded-rectangle button.  Sets current tool to toolRoundedRect.
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @param evt ActionEvent reference
   */
  public void rounded_rectangleActionPerformed (java.awt.event.ActionEvent evt) {
      toggleProperties();
      rect_properties.setVisible(true);
      if (curButton == select) {
	  toolSelect.deSelect(center);
      } else if (curButton == magicSelect) {
	    toolMagicSelect.deSelect(center);
	} else if (curButton == selectall) {
	  toolSelectall.deSelect(center);
      } else if (curButton == curve) {
	  toolCurve.deSelect(center);
      } else if (curButton == polygon) {
	  toolPolygon.deSelect(center);
      }
      clearRect();
      curSquare.setBackground(propColor);
      curButton.setBorder(BorderFactory.createRaisedBevelBorder());
      curButton = rounded_rectangle;
      curButton.setBorder(BorderFactory.createLoweredBevelBorder());
      currentTool = toolRoundedRect;
      clearRect();
      curRoundRect.setBackground(propColor);
  }
//Istvan
  /** This method basically executes code when the user clicks on the LINE tool.
   *
   * <CODE>toggleProperties()</CODE> basically hides the properties of whatever tool was previously
   * on the small part of the GUI right below the buttons on the left side.
   *
   * <CODE>line_properties.setVisible( true )</CODE> shows the line properties.	 In this case, it shows
   * the five types of lines (narrow to wide) the line tool has on the left border of
   * the GUI.
   *
   * There are no OS/Hardware dependencies.  No variances and no security constraints.
   * There are also no references to external specifications.
   *
   * @param evt This is the parameter for the action event.
   */
  public void lineActionPerformed(java.awt.event.ActionEvent evt) {
      toggleProperties();
      line_properties.setVisible(true);
      if (curButton == select) {
	  toolSelect.deSelect(center);
      } else if (curButton == magicSelect) {
	    toolMagicSelect.deSelect(center);
	} else if (curButton == selectall) {
	  toolSelectall.deSelect(center);
      } else if (curButton == curve) {
	  toolCurve.deSelect(center);
      } else if (curButton == polygon) {
	  toolPolygon.deSelect(center);
      }
      clearLine();
      curLine.setBackground(propColor);
      curButton.setBorder(BorderFactory.createRaisedBevelBorder());
      curButton = line;
      curButton.setBorder(BorderFactory.createLoweredBevelBorder());
      currentTool = toolLine;
      currentCursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
      clearLine();
      curLine.setBackground(propColor);
  }

  /** This method exits the Application entirely.  All it does is calls <CODE>exit()</CODE>.
   *
   * <B>NOTE: THIS MIGHT BE WHERE THE MEMORY LEAK IS!  BEWARE!</B>
   *
   * No variances and no security constraints.
   * There are also no references to external specifications.
   *
   * As for OS/Hardware dependencies, it depends on how the operating system handles
   * the exit code.
   *
   * @param evt This is the parameter for the window event.
   */
  public void exitForm(java.awt.event.WindowEvent evt) {
      //	a.destroy();
      try
		{
		     FileWriter out = new FileWriter("windowRegistry.txt");
		     BufferedWriter b = new BufferedWriter( out );


			Double xd = new Double( this.getLocation().getX() );
		     Double yd = new Double(this.getLocation().getY()) ;
			Double wd = new Double(this.getSize().getWidth());
		     Double hd = new Double(this.getSize().getHeight());
		     Double iwd = new Double(center.widt);
			Double ihd = new Double(center.heig);
			if(myPrefs.FixedSize){
				iwd = new Double(myPrefs.Width);
				ihd = new Double(myPrefs.Height);
			}
			//layer box status
			int lbx;
			if(alllayers.isVisible() == true )
				lbx = 1;
			else
				lbx = 0;
			//tool box status
			int tbx;
			if(shapes.isVisible() == true )
				tbx = 1;
			else
				tbx = 0;
			//status box status
			int sbx;
			if(status.isVisible() == true )
				sbx = 1;
			else
				sbx = 0;
			//color box status
			int cbx;
			if(color_menu.isVisible() == true )
				cbx = 1;
			else
				cbx = 0;
				
		     b.write( xd.intValue() + "\n" +
			    yd.intValue() + "\n" +
					wd.intValue() + "\n" +
					hd.intValue() + "\n"+
					iwd.intValue() + "\n" +
					ihd.intValue() + "\n" +
					tbx + "\n" +
					cbx + "\n" +
					sbx + "\n" +
					lbx + "\n");
			 b.close();
		 }
	 catch( Exception e ) {}
      
      exit();
  }

  /** This is a big method which saves the current image in the main canvas into a file.
   * This method can save the image in the following three formats: JPEG, GIF, and
   * Monochrome BMP.  This method also adds an extension while saving if the filename
   * the user inputed does not have an extension.  For example, if a user saves a jpeg
   * as "Image", the following code will rename it to "Image.jpg".
   *
   * As for OS/Hardware dependencies, saving relies heavily on the current computer's
   * media.  If there is no hard drive, it must save to other media.  There are no
   * known variances in the saving process unless it is of an entirely different
   * filesystem.  This is usually handled by the operating system anyways.  As for
   * security constraints, this save code might allow public users to save onto public
   * directories.  But that is a big MIGHT.  There are no references to any external
   * specifications at this time.
   *
   */
    public void save(){
	saveImage(center.getBufferedImage());
    }

    /** Called by save().
    * @param img BufferedImage object that is to be saved
    * @see #save
    */
    public void saveImage(BufferedImage img)
    {
	  //Istvan phase 5.7pm
	  JFileChooser chooser = new JFileChooser();
      TerpPaintFileFilter bmp = new TerpPaintFileFilter(new String[]{"bmp", "dib"}, "Monochrome Bitmap");
      TerpPaintFileFilter jpeg = new TerpPaintFileFilter(new String[]{"jpeg", "jpg"}, "JPEG Image");
      TerpPaintFileFilter gif = new TerpPaintFileFilter(new String("gif"), "GIF Image");
	  TerpPaintFileFilter gifa = new TerpPaintFileFilter(new String("gifa"), "GIF animated Image");
      TerpPaintFileFilter tpt = new TerpPaintFileFilter(new String("tpt"), "TerpPaint Picture");
      chooser.addChoosableFileFilter(tpt);
      chooser.addChoosableFileFilter(gif);
      chooser.addChoosableFileFilter(gifa);
      chooser.addChoosableFileFilter(jpeg);
      chooser.addChoosableFileFilter(bmp);


      if(CURRENT_FILE.endsWith(".jpg") || CURRENT_FILE.endsWith(".jpeg"))
	  chooser.setFileFilter(jpeg);
      else if(CURRENT_FILE.endsWith(".gif"))
	  chooser.setFileFilter(gif);
      else if(CURRENT_FILE.endsWith(".gifa"))
	chooser.setFileFilter(gifa);
      else if(CURRENT_FILE.endsWith(".tpt"))
	chooser.setFileFilter(tpt);
      else
	  chooser.setFileFilter(bmp);

      chooser.showSaveDialog(this);
      File saveFile = chooser.getSelectedFile();

      if(saveFile == null){
	  return;
      }
      else{//used filters or quotes
	  if(saveFile.getName().startsWith("\"")&&saveFile.getName().endsWith("\"")){
		      //save with quotes
		      int endPath=saveFile.getAbsolutePath().indexOf('\"');
		      int endFile=saveFile.getAbsolutePath().lastIndexOf('\"');
		      String myAbsPath=saveFile.getAbsolutePath().substring(0,endPath);
		      myAbsPath=myAbsPath.concat(saveFile.getAbsolutePath().substring(endPath+1,endFile));
			  System.out.println(myAbsPath);
			  saveFile = new File(myAbsPath);
			  if(saveFile.getName().endsWith(".jpg") || saveFile.getName().endsWith(".jpeg"))
			       chooser.setFileFilter(jpeg);
			  else if(saveFile.getName().endsWith(".gif"))
			       chooser.setFileFilter(gif);
			  else if(saveFile.getName().endsWith(".gifa"))
				   chooser.setFileFilter(gifa);
			  else if(saveFile.getName().endsWith(".tpt"))
				   chooser.setFileFilter(tpt);
			  else
			   chooser.setFileFilter(bmp);
		  }
	  //Add the extension to the filename if it isn't already there
	  if(chooser.getFileFilter() == bmp){
	      if(!saveFile.getName().endsWith(".bmp")){
		  //They don't have the extension
		  saveFile = new File(saveFile.getAbsolutePath()+".bmp");
	      }
	      try{
		  FileOutputStream fos = new FileOutputStream(saveFile.getAbsolutePath());
		  converter newImage = new converter();
		  newImage.BufferedImageToFile(fos, img, 0);
		  fos.flush();
		  fos.close();
	      }
	      catch(Exception fosError){
	      	JOptionPane.showMessageDialog(this,
			    "Bad File Name",
			    "Error",
			    JOptionPane.ERROR_MESSAGE);
}
	  }

	  else if(chooser.getFileFilter() == jpeg){
	      if((!saveFile.getName().endsWith(".jpeg"))
	      &&(!saveFile.getName().endsWith(".jpg"))){
		  //They don't have the extension
		  saveFile = new File(saveFile.getAbsolutePath()+".jpeg");
	      }
	      try{
		  FileOutputStream fos = new FileOutputStream(saveFile.getAbsolutePath());
		  JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
		  encoder.encode(img);
		  fos.flush();
		  fos.close();
	      }
	      catch(Exception fosError){
	      	JOptionPane.showMessageDialog(this,
				    "Bad File Name",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);}
	  }



		else if(chooser.getFileFilter() == tpt){
	      if(!saveFile.getName().endsWith(".tpt")){
		  //They don't have the extension
		  saveFile = new File(saveFile.getAbsolutePath()+".tpt");
	      }
		/*try{
		  FileOutputStream fos = new FileOutputStream(saveFile.getAbsolutePath());
		  JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
		  encoder.encode(img);
		  fos.flush();
		  fos.close();
	      }
	      catch(Exception fosError){}
		*/
		try{
					FileOutputStream fos = new FileOutputStream(saveFile.getAbsolutePath());
			String newName = new String();
			PrintStream p;
				p = new PrintStream(fos);
			p.println(alllayers.holder.size());
			int number = 1;
			int len=saveFile.getAbsolutePath().length();
			String myfilename=saveFile.getAbsolutePath().substring(0,len-4);

			for (int i = 0; i < alllayers.holder.size(); i++)
					{
					newName = myfilename + number+".jpg";
					FileOutputStream sub = new FileOutputStream(newName);
					JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(sub);
					//Layer tempor = (Layer) center.layerList.get(i);
					if(i!=center.currentLayer)
						encoder.encode((BufferedImage)center.layerList.get(i));
					else
						encoder.encode(img);
					sub.flush();
					number++;
					sub.close();
					}
		  p.close();
		  //save as tpt
	      }
	      catch(Exception fosError){
	      	JOptionPane.showMessageDialog(this,
				    "Bad File Name",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);}
	  }
	  else if(chooser.getFileFilter() == gifa){
	      if(saveFile.getName().endsWith(".gifa")){
		  String temp=saveFile.getAbsolutePath();
		  int tempLen=temp.length();
		  saveFile = new File(saveFile.getAbsolutePath().substring(0,tempLen-1));
	      }
	      else if(!saveFile.getName().endsWith(".gifa")){
		  //They don't have the extension
		  saveFile = new File(saveFile.getAbsolutePath()+".gif");
	      }
	      try{
		  FileOutputStream fos = new FileOutputStream(saveFile.getAbsolutePath());
		  AnimatedGifEncoder encoder = new AnimatedGifEncoder();
		  if ( myPrefs.CycleNumber == -1 ) {
		  	encoder.repeat = 0;  // 0 is the setting for infinite looping
		  }
		  encoder.start(fos);
		  encoder.setDelay((int)myPrefs.Anim);
		  
		  // check transition method and save a normal transition or a blur transition
		  // TRANSITION: none
//		  if ( myPrefs.Transition.equals("none") ) {
			  for (int i = 0; i < center.layerList.size(); i++) {
				if (i != center.currentLayer)
					encoder.addFrame((BufferedImage) center.layerList.get(i));
				else
					encoder.addFrame(img);
				
				Thread.sleep(5); // sleep for 5 ms so that frames don't get skipped
								 // when the file saves.
			  }
//		  }
		  // TRANSITION: Blur
//		  if ( myPrefs.Transition.equals("Blur") ) {
		  	
//		  }
		  
		  // clean up
		  encoder.finish();
		  fos.close();
	      }
	      catch(Exception fosError){
	      	JOptionPane.showMessageDialog(this,
				    "Bad File Name",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);}
	  }
		else if(chooser.getFileFilter() == gif){
	      if(!saveFile.getName().endsWith(".gif")){
		  //They don't have the extension
		  saveFile = new File(saveFile.getAbsolutePath()+".gif");
	      }
	      try{
		  FileOutputStream fos = new FileOutputStream(saveFile.getAbsolutePath());
		  /*Ana*/
		  // Note: Even though it is called AnimatedGifEncoder, it also encodes regular gif files.
		  AnimatedGifEncoder encoder = new AnimatedGifEncoder();
		  encoder.start(fos);
		  encoder.addFrame(img);
		  encoder.finish();
		  /*End Ana*/
		  fos.close();
	      }
	      catch(Exception fosError){
	      	
	      	JOptionPane.showMessageDialog(this,
				    "Bad File Name",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);}
	  }

	  CURRENT_FILE = saveFile.getName();
	  this.setTitle("TerpPaint - "+saveFile.getName());
	  filename=saveFile.getAbsolutePath();
	  /* ming */
	  //center.noChanges();
	  // ming 4.26
	     LinkedList cur_action_list = (LinkedList)(action_list.get(center.currentLayer));
	   LinkedList cur_redo_list = (LinkedList)(redo_list.get(center.currentLayer));
	   int cur_size = cur_action_list.size();
	   for (int i=0; i<cur_size; i++)
	       cur_action_list.removeFirst();
	   cur_size = cur_redo_list.size();
	   for (int i=0; i<cur_size; i++)
	       cur_redo_list.removeFirst();
	    // ming 4.26 end
	  updateUndoList();
	  /* ming */
      }
      //center.noChanges();
	int filenum = -1;
	for( int i = 0; i < recentFiles.size(); i ++ )
	{
	    if( saveFile.getAbsolutePath().compareTo(((File) recentFiles.elementAt( i )).getAbsolutePath() ) == 0 )
		filenum = i;
	}
	if( filenum != -1 )	// already is a recent document
	{
	    recentFiles.removeElementAt( filenum );
	    recentFiles.insertElementAt( saveFile, 0 );

	}
	else				// add to front of recentFiles
	{
	    recentFiles.insertElementAt( saveFile, 0 );
	    if( recentFiles.size() > 4 )
		recentFiles.setSize( 4 );

	}

	updateFileMenu();
    }
//End Istvan phase 5.7pm
  /** Allows saving without the dialog box, only called when a currently existing file is open.
   * As long as the file has write permissions, there are no OS/Hardware dependencies.	No variances and no security constraints.
   * There are also no references to external specifications.
   *
   */
  public void saveFast(){
//Istvan phase 5.7pm
	System.out.println("fastSave");
	File saveFile = new File( filename );

      if(saveFile == null){System.out.println("null");
	  return;
      }
      else{


	if(saveFile.getName().endsWith(".bmp")){System.out.println("bmp save");



	  try{
	      FileOutputStream fos = new FileOutputStream(saveFile.getAbsolutePath());
	      converter newImage = new converter();
	      newImage.BufferedImageToFile(fos, center.getBufferedImage(), 0);
	      fos.flush();
	      fos.close();
	  }
	  catch(Exception fosError){}
		}

	  else if((saveFile.getName().endsWith(".jpeg"))
	  ||(saveFile.getName().endsWith(".jpg"))){System.out.println("jpg save");

			  try{
				  FileOutputStream fos = new FileOutputStream(saveFile.getAbsolutePath());
				  JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
				  encoder.encode(center.getBufferedImage());
				  fos.flush();
				  fos.close();
			  }
			  catch(Exception fosError){}
		  }

	  else if(saveFile.getName().endsWith(".gif")){System.out.println("gif save");


			  try{
				  FileOutputStream fos = new FileOutputStream(saveFile.getAbsolutePath());
				  /*Ana*/
				  // Note: Even though it is called AnimatedGifEncoder, it also encodes regular gif files.
				  AnimatedGifEncoder encoder = new AnimatedGifEncoder();
				  encoder.start(fos);
				  encoder.setDelay((int)myPrefs.Anim);
		  for (int i = 0; i < center.layerList.size(); i++) {
					  if(i!=center.currentLayer)
						encoder.addFrame((BufferedImage)center.layerList.get(i));
					else
					encoder.addFrame(center.getBufferedImage());
				  }
				  encoder.finish();
				  /*End Ana*/
				  fos.close();
			  }

			  catch(Exception fosError){}
		  }
		 else if(saveFile.getName().endsWith(".tpt")){System.out.println("tpt save");
		   try{
			FileOutputStream fos = new FileOutputStream(saveFile.getAbsolutePath());
		    String newName = new String();
		    PrintStream p;
		    p = new PrintStream(fos);
		    p.println(alllayers.holder.size());
		    int number = 1;
		    int len=saveFile.getAbsolutePath().length();
		    String myfilename=saveFile.getAbsolutePath().substring(0,len-4);

		    for (int i = 0; i < alllayers.holder.size(); i++)
			{
				newName = myfilename + number+".jpg";
				FileOutputStream sub = new FileOutputStream(newName);
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(sub);
				//Layer tempor = (Layer) center.layerList.get(i);
				if(i!=center.currentLayer)
				    encoder.encode((BufferedImage)center.layerList.get(i));
				else
				    encoder.encode(center.getBufferedImage());
				sub.flush();
				number++;
				sub.close();
			}
		    p.close();
		  //save as tpt
		  }
		  catch(Exception fosError){}
	 }
   }
//End Istvan phase 5.7pm
      /* ming */
//	center.noChanges();
      /* ming */

  }
   /** This method closes the window.  If the user made changes after saving or did
   * not even save at all, it will display a dialog asking if the user wishes to
   * save the image before exiting.
   *
   * No variances and no security constraints.
   * There are also no references to external specifications.
   *
   * As for OS/Hardware dependencies, it depends on how the operating system handles
   * the exit code.
   */
  public void close(){
      //theSound.stop();
      //theSound = null;
      System.out.println("HERE in exit");
      if(center.madeChanges(this)){
	  System.out.println("MADE CHANGES");
	  saveChanges save = new saveChanges(this, true,CURRENT_FILE);
	  save.setVisible(true);
	  if(save.CHOICE == choice.CANCEL){
	      System.out.println("CANCELLING");
	      return;
	  }
	  else if(save.CHOICE == choice.YES){
	      save();
	  }
	  else if(save.CHOICE == choice.NO){
	      System.out.println("NO");

	  }
      }
      try
      {
	  FileWriter out = new FileWriter("windowRegistry.txt");
	  BufferedWriter b = new BufferedWriter( out );


	  Double xd = new Double( this.getLocation().getX() );
	  Double yd = new Double(this.getLocation().getY()) ;
		  Double wd = new Double(this.getSize().getWidth());
	  Double hd = new Double(this.getSize().getHeight());
	  Double iwd = new Double(center.widt);
		  Double ihd = new Double(center.heig);

	  b.write( xd.intValue() + "\n" +
	    yd.intValue() + "\n" +
			wd.intValue() + "\n" +
			hd.intValue() + "\n"+
			iwd.intValue() + "\n" +
			ihd.intValue() + "\n" );
	  b.close();
      }
      catch( Exception e ) {}

      try
      {
	  FileWriter out2 = new FileWriter("recentFiles.txt");
	  BufferedWriter b2 = new BufferedWriter( out2 );

	  for( int i = 0; i < recentFiles.size() ; i++ )
	  {
	      b2.write( ((File) recentFiles.elementAt( i )).getAbsolutePath() + "\n" );
	  }

	  for( int i = recentFiles.size(); i < 4; i++ )
	  {
	      b2.write( "-1\n" );
	  }

	  b2.close();
      }
      catch( Exception e ) {}

      //		if (standalone == true){
      System.out.println("Closing");
      //System.exit(0);////////////////////////////////////////////////
      //	}
      //	else{
		  //Istvan
		myContainer.Remove(this);
		this.dispose();
      //	}
  }
  /** This method exits the program.  If the user made changes after saving or did
   * not even save at all, it will display a dialog asking if the user wishes to
   * save the image before exiting.
   *
   * No variances and no security constraints.
   * There are also no references to external specifications.
   *
   * As for OS/Hardware dependencies, it depends on how the operating system handles
   * the exit code.
   */
  public void exit(){
      //theSound.stop();
      //theSound = null;
      System.out.println("HERE in exit");
      if(center.madeChanges(this)){
	  System.out.println("MADE CHANGES");
	  saveChanges save = new saveChanges(this, true,CURRENT_FILE);
	  save.setVisible(true);
	  if(save.CHOICE == choice.CANCEL){
	      System.out.println("CANCELLING");
	      return;
	  }
	  else if(save.CHOICE == choice.YES){
	      save();
	  }
	  else if(save.CHOICE == choice.NO){
	      System.out.println("NO");

	  }
      }
      try
      {
	  FileWriter out = new FileWriter("recentFiles.txt");
	  BufferedWriter b = new BufferedWriter( out );


	  Double xd = new Double( this.getLocation().getX() );
	  Double yd = new Double(this.getLocation().getY()) ;
		  Double wd = new Double(this.getSize().getWidth());
	  Double hd = new Double(this.getSize().getHeight());
	  Double iwd = new Double(center.widt);
		  Double ihd = new Double(center.heig);

	  b.write( xd.intValue() + "\n" +
	    yd.intValue() + "\n" +
			wd.intValue() + "\n" +
			hd.intValue() + "\n"+
			iwd.intValue() + "\n" +
			ihd.intValue() + "\n" );
	  b.close();
      }
      catch( Exception e ) {}

      try
      {
	  FileWriter out2 = new FileWriter( "recentFiles.txt");
	  BufferedWriter b2 = new BufferedWriter( out2 );

	  for( int i = 0; i < recentFiles.size() ; i++ )
	  {
	      b2.write( ((File) recentFiles.elementAt( i )).getAbsolutePath() + "\n" );
	  }

	  for( int i = recentFiles.size(); i < 4; i++ )
	  {
	      b2.write( "-1\n" );
	  }

	  b2.close();
      }
      catch( Exception e ) {}

      //		if (standalone == true){
      System.out.println("EXITING");
      System.exit(0);////////////////////////////////////////////////
      //	}
      //	else{
      //	    this.dispose();
      //	}
  }

  /** This method opens up the universal Color Chooser.	 The color chooser is basically
   * a way of choosing which color you want specifically.  This is probably a 16-bit
   * color configuration.  The color chooser opens via swing.
   *
   * Once the user selects which color, it will set the foreground color to that new
   * color.
   *
   * The colors available might be OS dependent.  Variances depend on how the monitor
   * and videocard chooses to display ASCII values of color.  One color usually looks
   * different from one computer to another.  There are no known security constraints.
   * There are no known references to any external specification except for the
   * operating system.
   *
   */
  public void addColorChooser(){
      final javax.swing.JColorChooser tcc = new javax.swing.JColorChooser(shapes.getForeground());
      tcc.getSelectionModel().addChangeListener(new javax.swing.event.ChangeListener() {
	  public void stateChanged(javax.swing.event.ChangeEvent e) {
	      java.awt.Color newColor = tcc.getColor();
	      shapes.setForeground(newColor);
	  }
      }
      );
      //.add(tcc, java.awt.BorderLayout.EAST);
  }

  /* for recent files */
  /** This function is called to update the recentfiles in the 'file menu'
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   */
   public void updateFileMenu()
   {
       int numFiles = recentFiles.size();

       if( FileName1 != null && numFiles >= 1 )
       {
	   String fn = ((File) recentFiles.elementAt( 0 )).getAbsolutePath();
	   int length = fn.length();

	   if( length < 27 )
	   {
	       FileName1.setText( "1  " + fn );
	   }
	   else
	   {
	       FileName1.setText( "1  " + fn.substring(0, 9) + " ... " + fn.substring( length-15, length ) );
	   }
       }

       if( FileName2 != null && numFiles >= 2 )
       {
	   String fn = ((File) recentFiles.elementAt( 1 )).getAbsolutePath();
	   int length = fn.length();

	   if( length < 27 )
	   {
	       FileName2.setText( "2  " + fn );
	   }
	   else
	   {
	       FileName2.setText( "2  " + fn.substring(0, 9) + " ... " + fn.substring( length-15, length ) );
	   }

       }

       if( FileName3 != null && numFiles >= 3 )
       {
	   String fn = ((File) recentFiles.elementAt( 2 )).getAbsolutePath();
	   int length = fn.length();

	   if( length < 27 )
	   {
	       FileName3.setText( "3  " + fn );
	   }
	   else
	   {
	       FileName3.setText( "3  " + fn.substring(0, 9) + " ... " + fn.substring( length-15, length ) );
	   }

       }

       if( FileName4 != null && numFiles >= 4 )
       {
	   String fn = ((File) recentFiles.elementAt( 3 )).getAbsolutePath();
	   int length = fn.length();

	   if( length < 27 )
	   {
	       FileName4.setText( "4  " + fn );
	   }
	   else
	   {
	       FileName4.setText( "4  " + fn.substring(0, 9) + " ... " + fn.substring( length-15, length ) );
	   }

       }


       if( FileName1 == null && numFiles >= 1 )
       {
	   File1.insertSeparator( File1.getItemCount()-2 );


	   FileName1 = new javax.swing.JMenuItem();
	   String fn = ((File) recentFiles.elementAt( 0 )).getAbsolutePath();

	   int length = fn.length();
	   if( length < 27 )
	   {
	       FileName1.setText( "1  " + fn );
	   }
	   else
	   {
	       FileName1.setText( "1  " + fn.substring(0, 9) + " ... " + fn.substring( length-15, length ) );
	   }

	   FileName1.addActionListener(new java.awt.event.ActionListener() {
	       public void actionPerformed(java.awt.event.ActionEvent evt) {
		   FileNameActionPerformed(evt, 1);
	       }
	   });

	   File1.insert( FileName1, File1.getItemCount() - 2 );

       }

       if( FileName2 == null && numFiles >= 2 )
       {
	   FileName2 = new javax.swing.JMenuItem();
	   String fn = ((File) recentFiles.elementAt( 1 )).getAbsolutePath();

	   int length = fn.length();
	   if( length < 27 )
	   {
	       FileName2.setText( "2  " + fn );
	   }
	   else
	   {
	       FileName2.setText( "2  " + fn.substring(0, 9) + " ... " + fn.substring( length-15, length ) );
	   }

	   FileName2.addActionListener(new java.awt.event.ActionListener() {
	       public void actionPerformed(java.awt.event.ActionEvent evt) {
		   FileNameActionPerformed(evt, 2);
	       }
	   });

	   File1.insert( FileName2, File1.getItemCount() - 2 );

       }

       if( FileName3 == null && numFiles >= 3 )
       {
	   FileName3 = new javax.swing.JMenuItem();
	   String fn = ((File) recentFiles.elementAt( 2 )).getAbsolutePath();

	   int length = fn.length();
	   if( length < 27 )
	   {
	       FileName3.setText( "3  " + fn );
	   }
	   else
	   {
	       FileName3.setText( "3  " + fn.substring(0, 9) + " ... " + fn.substring( length-15, length ) );
	   }

	   FileName3.addActionListener(new java.awt.event.ActionListener() {
	       public void actionPerformed(java.awt.event.ActionEvent evt) {
		   FileNameActionPerformed(evt, 3);
	       }
	   });

	   File1.insert( FileName3, File1.getItemCount() - 2 );

       }

       if( FileName4 == null && numFiles >= 4 )
       {
	   FileName4 = new javax.swing.JMenuItem();
	   String fn = ((File) recentFiles.elementAt( 3 )).getAbsolutePath();

	   int length = fn.length();
	   if( length < 27 )
	   {
	       FileName4.setText( "4  " + fn );
	   }
	   else
	   {
	       FileName4.setText( "4  " + fn.substring(0, 9) + " ... " + fn.substring( length-15, length ) );
	   }

	   FileName4.addActionListener(new java.awt.event.ActionListener() {
	       public void actionPerformed(java.awt.event.ActionEvent evt) {
		   FileNameActionPerformed(evt, 4);
	       }
	   });

	   File1.insert( FileName4, File1.getItemCount() - 2 );

       }

   }

   /* for recent files */
   /** This function opens the appropriate recentfile, according to the param passed
    * It should work with all operating systems and hardware.
    * There are no variances and no security constraints.
    * @param evt ActionEvent object
    * @param filenum int for which recent file is to be opened
    */
   public void FileNameActionPerformed(java.awt.event.ActionEvent evt, int filenum )
   {
 //Istvan
	   if (curButton == select) {
	   toolSelect.deSelect(center);
       } else if (curButton == magicSelect) {
	    toolMagicSelect.deSelect(center);
	} else if (curButton == selectall) {
	   toolSelectall.deSelect(center);
       } else if (curButton == curve) {
	   toolCurve.deSelect(center);
       } else if (curButton == polygon) {
	   toolPolygon.deSelect(center);
       }
       if(center.madeChanges(this)){
	   System.out.println("MADE CHANGES");
	   saveChanges save = new saveChanges(this, true,CURRENT_FILE);
	   save.setVisible(true);
	   if(save.CHOICE == choice.CANCEL){
	       System.out.println("CANCELLING");
	       return;
	   }
	   else if(save.CHOICE == choice.YES){
	       save();
	   }
	   else if(save.CHOICE == choice.NO){
	       System.out.println("NO");
	   }
       }

       File chosen = (File) recentFiles.elementAt( filenum-1 );

       filename = chosen.getAbsolutePath();

       if(chosen == null){
	   return;
       }
       else{
	   if( chosen.getName().endsWith(".bmp") ||chosen.getName().endsWith(".dib"))
	   {
	       try{
		   FileInputStream fis = new FileInputStream(chosen);
		   converter newImage = new converter();
		   // ming 4.24
		    center.noChanges();
		    // ming 4.24 end
		   center.setBufferedImage(newImage.FileToBufferedImage(fis));
		   fis.close();
	       }
	       catch(Exception fisError){}
	   }
	   else if( chosen.getName().endsWith(".jpeg") || chosen.getName().endsWith(".jpg"))
	   {
		try{
		   FileInputStream fis = new FileInputStream(chosen);
		   JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(fis);
		   // ming 4.24
		    center.noChanges();
		    // ming 4.24 end
		   center.setBufferedImage(decoder.decodeAsBufferedImage());
		   fis.close();
	       }
	       catch(Exception fisError){}
	   }
	   else if(chosen.getName().endsWith(".gif")){
	       try{
		   BufferedImage imageB = ImageUtilities.getBufferedImage(chosen.getPath(),this);
		   // ming 4.24
		    center.noChanges();
		    // ming 4.24 end
		   center.setBufferedImage(imageB);
	       }catch(Exception e){
		   e.printStackTrace();
	       }
	   }else if(chosen.getName().endsWith(".tpt")){
//Istvan 5.7pm
				try{
				FileInputStream fis = new FileInputStream(chosen);
		    DataInputStream reader = new DataInputStream(fis);
		    String in = new String();
		    in = reader.readLine();
		    String newName = new String();
		    int len=chosen.getAbsolutePath().length();
		    String name=chosen.getAbsolutePath().substring(0,len-4);

		    for (int i = 1; i <= Double.parseDouble(in); i++)
				{//5630
						try{
							System.out.println(name+"  "+(String)(name+i+".jpg"));
							FileInputStream subPic = new FileInputStream((String)(name+i+".jpg"));
							JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(subPic);


							center.setBufferedImage(decoder.decodeAsBufferedImage());

							//alllayers.center.repaint();
							//alllayers.center.addLayer();
							//alllayers.newLayer();
							if(i < Double.parseDouble(in))
								alllayers.addActionPerformed(evt);
							//wrong from here
							//Layer temporary = (Layer) alllayers.holder.elementAt(i-1);
							//temporary.layerCanvas.setBufferedImage(decoder.decodeAsBufferedImage());
							//fis.close();
							subPic.close();
						}catch(Exception e){System.out.println(e);}

				}
		    reader.close();

		}catch(Exception e){
		    e.printStackTrace();
		}
		//End Istvan 5.7pm
	    }
	   else{
	       //This is not a valid file
	   }
	   setTitle("TerpPaint - "+chosen.getName());
	   CURRENT_FILE = chosen.getName();
	    // ming 4.4
	  //center.noChanges();
	  // ming 4.26
	   LinkedList cur_action_list = (LinkedList)(action_list.get(center.currentLayer));
	   LinkedList cur_redo_list = (LinkedList)(redo_list.get(center.currentLayer));
	   int cur_size = cur_action_list.size();
	   for (int i=0; i<cur_size; i++)
	       cur_action_list.removeFirst();
	   cur_size = cur_redo_list.size();
	   for (int i=0; i<cur_size; i++)
	       cur_redo_list.removeFirst();

	    // ming 4.26 end
	  updateUndoList();
	  // ming 4.4 end
       }

       recentFiles.removeElementAt( filenum - 1 );
       recentFiles.insertElementAt( chosen, 0 );

       updateFileMenu();

  }



   /** This method officially launches the Paint program.  It first creates a new
    * "TerpPaintContainer" class.  Then it launches it by passing a NULL pointer.
    * It should work with all operating systems and hardware.
    * There are no variances and no security constraints.
    * @param args This is the the command line arguments parameter
    */
  public static void main(String args[]) {
	TerpPaintContainer fc = new TerpPaintContainer(true);
     	if(myPrefs.LastSize){
		fc.launch(null);
	}
	else if(myPrefs.PromptSize){
		fc.launchPrompt(null);
	}
	else if(myPrefs.FixedSize){
		fc.launchFixed(null);
	}
  }
  // Variables declaration - do not modify

  /** AutoBackup object.
   */
  public AutoBackup a;

  /** This vector holds all the current colors in the color toolbox.
   */
  public Vector colorTable = new Vector();

  /** this Jbutton holds the button of the current right-color.
   */
  public JButton currentColor;






  /** String for filename of current open file.
   */
  String filename = "null";

  /** Java swing GUI depicting the menu bar called menu_container.
   */
  public javax.swing.JMenuBar menu_container;
  /** Java swing GUI depicting the menu called File1.
   */
  public javax.swing.JMenu File1;
  /** Java swing GUI depicting the menu called Help.
   */
  public javax.swing.JMenu Help;
  /** Java swing GUI depicting the menu item called HelpTopics.
   */
  public javax.swing.JMenuItem HelpTopics;
  /** Java swing GUI depicting the menu item called About.
   */
  public javax.swing.JMenuItem About;

  /** Java swing GUI depicting the menu item called New.
   */
  public javax.swing.JMenuItem New;
  /** Java swing GUI depicting the menu item called Open.
   */
  public javax.swing.JMenuItem Open;
  /** Java swing GUI depicting the menu item called Save.
   */
  public javax.swing.JMenuItem Save;
  /** Java swing GUI depicting the menu item called SaveAs.
   */

  public javax.swing.JMenuItem SaveAs;
  /** Java swing GUI depicting the separator called jSeparator1.
   */
  public javax.swing.JSeparator jSeparator1;
  /** Java swing GUI depicting the menu item called PrintPreview.
   */
  public javax.swing.JMenuItem PrintPreview;
  /** Java swing GUI depicting the menu item called PageSetup.
   */
  public javax.swing.JMenuItem PageSetup;
  /** Java swing GUI depicting the menu item called Print.
   */
  public javax.swing.JMenuItem Print;
  /** Java swing GUI depicting the separator called jSeparator7.
   */
  public javax.swing.JSeparator jSeparator7;
    /** Java swing GUI depicting the menu item called Recent File.
   */
  public javax.swing.JMenuItem Recent;

  /** Java swing GUI depicting the separator called jSeparator2.
   */
  public javax.swing.JSeparator jSeparator2;
  /** Java swing GUI depicting the menu item called Close.
  */
  public javax.swing.JMenuItem Close;
  /** Java swing GUI depicting the menu item called Exit.
   */
  public javax.swing.JMenuItem Exit;
  /** Java swing GUI depicting the menu item called Send.
   */
  //	public javax.swing.JMenuItem Send;
  /** Java swing GUI depicting the menu called Edit.
   */
  public javax.swing.JMenu Edit;
  /** Java swing GUI depicting the menu item called Undo.
   */
  public javax.swing.JMenuItem Undo;
  //ming
  /** Java swing GUI depicting the Undolist menu.
   */
  public javax.swing.JMenu UndoList;
  //public javax.swing.JMenuItem Undotry;
  
  /** Linked List of items in undolist.
  */
  public LinkedList UndoList_items = new LinkedList();

   //version 3

  /** Java swing GUI depicting the menu item called Layer.
   */  public javax.swing.JMenu Layer;






  //#VERSION 3#
  
  /** Java swing GUI depicting the JMenuItem called Add.
  */
  public javax.swing.JMenuItem Add;
  /** Java swing GUI depicting the JMenuItem called Remove.
  */
  public javax.swing.JMenuItem Remove;
  /** Java swing GUI depicting the JMenuItem called Flatten.
  */
  public javax.swing.JMenuItem Flatten;
  /** Java swing GUI depicting a JSeperator.
  */
  public javax.swing.JSeparator jSeparator9;









  /** Java swing GUI depicting the menu item called Repeat.
   */
  public javax.swing.JMenuItem Repeat;
  /** Java swing GUI depicting the separator called jSeparator3.
   */
  public javax.swing.JSeparator jSeparator3;
  /** Java swing GUI depicting the menu item Cut.
   */
  public javax.swing.JMenuItem Cut;
  /** Java swing GUI depicting the menu item called Copy.
   */
  public javax.swing.JMenuItem Copy;
  /** Java swing GUI depicting the menu item called Paste.
   */
  public javax.swing.JMenuItem Paste;
  /** Java swing GUI depicting the menu item called ClearSelection.
   */
  public javax.swing.JMenuItem ClearSelection;
  /** Java swing GUI depicting the menu item called SelectAll.
   */
  public javax.swing.JMenuItem SelectAll;
   /** Java swing GUI depicting the menu item called Import.
   */
  public javax.swing.JMenuItem Import;
  /** Java swing GUI depicting the menu item called Export.
   */
  public javax.swing.JMenuItem Export;

  /** Java swing GUI depicting the separator called jSeparator4.
   */
  public javax.swing.JSeparator jSeparator4;
  /** Java swing GUI depicting the menu item called CopyTo.
   */
  public javax.swing.JMenuItem CopyTo;
  /** Java swing GUI depicting the menu item called PasteFrom.
   */
  public javax.swing.JMenuItem PasteFrom;

  /** Java swing GUI depicting the menu item Preferenes.
   */
  public javax.swing.JMenuItem MyPreferences;


  /** Java swing GUI depicting the separator called jSeparator8.
   */
  public javax.swing.JSeparator jSeparator8;
  /** Java swing GUI depicting the menu View.
   */
  public javax.swing.JMenu View;
  /** Java swing GUI depicting the check box menu item called ToolBox.
   */
  public javax.swing.JCheckBoxMenuItem ToolBox;
  /** Java swing GUI depicting the check box menu item called ColorBox.
   */
  public javax.swing.JCheckBoxMenuItem ColorBox;
  /** Java swing GUI depicting the check box menu item called StatusBox.
   */
  public javax.swing.JCheckBoxMenuItem StatusBox;
  //VERSION 3
  /** Java swing GUI depicting the Layer box menu item called LayerBox.
   */
  public javax.swing.JCheckBoxMenuItem LayerBox;
  /** Java swing GUI depicting the menu item called TextToolBox.
   */
  public javax.swing.JMenuItem TextToolBox;
  /** Java swing GUI depicting the separator called jSeparator5.
   */
  public javax.swing.JSeparator jSeparator5;
  /** Java swing GUI depicting the menu called Zoom.
   */
  public javax.swing.JMenu Zoom;
  /** Java swing GUI depicting the menu item called Normal.
   */
  public javax.swing.JMenuItem Normal;
  /** Java swing GUI depicting the menu item called Large.
   */
  public javax.swing.JMenuItem Large;
  /** Java swing GUI depicting the menu item called Custom.
   */
  public javax.swing.JMenuItem Custom;
  /** Another separator in GUI.
   */
  public javax.swing.JSeparator jSeparator6;
  /** Java swing GUI depicting the menu item called ShowGrid.
   */
  public javax.swing.JMenuItem ShowGrid;
  /** Java swing GUI depicting the menu item called ShowThumbnail.
   */
  public javax.swing.JMenuItem ShowThumbnail;
  /** Java swing GUI depicting the menu item called ViewBitmap.
   */
  public javax.swing.JMenuItem ViewBitmap;
  /** Java swing GUI depicting the menu item called SlideShow.
   */
  public javax.swing.JMenuItem SlideShow;
  //Ronald
    /** Java swing GUI depicting the menu item called Animation.
   */
  public javax.swing.JMenuItem Animation;
  /** Java swing GUI depicting the menu called Image.
   */

  public javax.swing.JMenu Image;
  // ming 4.24
  /** Java swing GUI depicting the menu called Filter.
   */

  public javax.swing.JMenu Filter;
  // ming 4.24 end
  /** Java swing GUI depicting the menu item FlipRotate.
   */
  public javax.swing.JMenuItem FlipRotate;
  /** Java swing GUI depicting the menu item called StretchSkew.
   */
  public javax.swing.JMenuItem StretchSkew;
  /* ming */
   /** Java swing GUI depicting the menu item called Brightness.
   */
  public javax.swing.JMenuItem Brightness;
   /** Java swing GUI depicting the menu item called Blur.
   */
  public javax.swing.JMenuItem Blur;
   /** Java swing GUI depicting the menu item called Sharpen.
   */
  public javax.swing.JMenuItem Sharpen;
   /** Java swing GUI depicting the menu item called Emboss.
   */
  public javax.swing.JMenuItem Emboss;
  /** Java swing GUI depicting the menu item called Edge.
   */
  public javax.swing.JMenuItem Edge;
  /* ming */
  /** Java swing GUI depicting the menu item called InvertColors.
   */
  public javax.swing.JMenuItem InvertColors;
  /** Java swing GUI depicting the menu item called Attributes.
   */
  public javax.swing.JMenuItem Attributes;
  /** Java swing GUI depicting the menu item called ClearImage.
   */
  public javax.swing.JMenuItem ClearImage;
  /** Java swing GUI depicting the check box menu item called DrawOpaque.
   */
  public javax.swing.JCheckBoxMenuItem DrawOpaque;
  /** Java swing GUI depicting the menu called Colors.
   *
   */
  public javax.swing.JMenu Colors;
  /** Java swing GUI depicting the menu item called EditColors.
   */
  public javax.swing.JMenuItem EditColors;
  /** Java swing GUI depicting the panel called west. will be on the left.
   */
  public javax.swing.JPanel west;
  /** Java swing GUI depicting the panel called shapes.
   */
  public javax.swing.JPanel shapes;
  /** Java swing GUI depicting the button called line.
   */
  public javax.swing.JButton line;
  /** Java swing GUI depicting the button called curve.
   */
  public javax.swing.JButton curve;
  /** Java swing GUI depicting the button called rounded rectangle.
   */
  public javax.swing.JButton rounded_rectangle;
  /** Java swing GUI depicting the button called square.
   */
  public javax.swing.JButton square;
  /** Java swing GUI depicting the button called polygon.
   */
  public javax.swing.JButton polygon;
  /** Java swing GUI depicting the button called elipse.
   */
  public javax.swing.JButton elipse;
  /** Java swing GUI depicting the button called selectall.
   */
  public javax.swing.JButton selectall;
  /** Java swing GUI depicting the button called eraser.
   */
  public javax.swing.JButton eraser;
  /** Java swing GUI depicting the button called paint.
   */
  public javax.swing.JButton paint;
  /** Java swing GUI depicting the button called medicine.
   */
  public javax.swing.JButton medicine;
  /** Java swing GUI depicting the button called zoom.
   */
  public javax.swing.JButton zoom;
  /** Java swing GUI depicting the button called pencil.
   */
  public javax.swing.JButton pencil;
  /** Java swing GUI depicting the button called brush.
   */
  public javax.swing.JButton brush;
  /** Java swing GUI depicting the button called spray.
   */
  public javax.swing.JButton spray;
  /** Java swing GUI depicting the button called letter.
   */
  public javax.swing.JButton letter;
  /** Java swing GUI depicting the button called select.
   */
  public javax.swing.JButton select;

  //added
  /** Java swing GUI depicting the button called magicSelect.
     */
  public javax.swing.JButton magicSelect;
//end added

  /** Java swing GUI depicting the panel called properties.
   */
  public javax.swing.JPanel properties;
  /** Java swing GUI depicting the panel called line properties.
   */
  public javax.swing.JPanel line_properties;
  /** Java swing GUI depicting the button for line1 width.
   */
  public javax.swing.JButton line1;
  /** Java swing GUI depicting the button for line2 width.
   */
  public javax.swing.JButton line2;
  /** Java swing GUI depicting the button for line3 width.
   */
  public javax.swing.JButton line3;
  /** Java swing GUI depicting the button for line4 width.
   */
  public javax.swing.JButton line4;
  /** Java swing GUI depicting the button for line5 width.
   */
  public javax.swing.JButton line5;
  /** Java swing GUI depicting the panel for rectangle properties.
   */
  public javax.swing.JPanel rect_properties;
  /** Java swing GUI depicting the rectangle button type one.
   */

  public javax.swing.JButton one;
  /** Java swing GUI depicting the rectangle button type two.
   */
  public javax.swing.JButton two;
  /** Java swing GUI depicting the rectangle button type three.
   */
  public javax.swing.JButton three;
  /** Java swing GUI depicting the panel for spray properties.
   */
  public javax.swing.JPanel spray_properties;
  /** Java swing GUI depicting the spray width 1.
   */
  public javax.swing.JButton spray1;
  /** Java swing GUI depicting the spray width 3.
   */
  public javax.swing.JButton spray2;
  /** Java swing GUI depicting the width 3.
   */
  public javax.swing.JButton spray3;
  /** Java swing GUI depicting the panel for brush properties.
   */
  public javax.swing.JPanel brush_properties;
  /** Java swing GUI depicting the brush dot width biggest.
   */
  public javax.swing.JButton dot3;
  /** Java swing GUI depicting the brush dot width medium.
   */
  public javax.swing.JButton dot2;
  /** Java swing GUI depicting the brush dot width smallest.
   */
  public javax.swing.JButton dot1;
  /** Java swing GUI depicting the brush square width largest.
   */
  public javax.swing.JButton square3;
  /** Java swing GUI depicting the brush square width medium.
   */
  public javax.swing.JButton square2;
  /** Java swing GUI depicting the brush square width smallest.
   */
  public javax.swing.JButton square1;
  /** Java swing GUI depicting the brush right-hand width largest.
   */
  public javax.swing.JButton right3;
  /** Java swing GUI depicting the brush right-hand width medium.
   */
  public javax.swing.JButton right2;
  /** Java swing GUI depicting the brush right-hand width smallest.
   */
  public javax.swing.JButton right1;
  /** Java swing GUI depicting the brush left-hand width largest.
   */
  public javax.swing.JButton left3;
  /** Java swing GUI depicting the brush left-hand width medium.
   */
  public javax.swing.JButton left2;
  /** Java swing GUI depicting the brush left-hand width smallest.
   */
  public javax.swing.JButton left1;
  /** Java swing GUI depicting the panel zoom properties.
   */
  public javax.swing.JPanel zoom_properties;
  /** Java swing GUI depicting the zoom level 1.
   */
  public javax.swing.JButton zoom1;
  /** Java swing GUI depicting the zoom level 2.
   */
  public javax.swing.JButton zoom2;
  /** Java swing GUI depicting the level 3 zoom.
   */
  public javax.swing.JButton zoom3;
  /** Java swing GUI depicting the level 4 zoom.
   */
  public javax.swing.JButton zoom4;
  /** Java swing GUI depicting the panel paint properties.
   */
  public javax.swing.JPanel paint_properties;
  /** Java swing GUI depicting the paint brush width 1.
   */
  public javax.swing.JButton paint1;
  /** Java swing GUI depicting the paint brush width 2.
   */
  public javax.swing.JButton paint2;
  /** Java swing GUI depicting the paint brush width 3.
   */
  public javax.swing.JButton paint3;
  /** Java swing GUI depicting the paint brush width 4.
   */
  public javax.swing.JButton paint4;
  /** Java swing GUI depicting the panel called none blank space, filler.
   */
  public javax.swing.JPanel none;
  /** Java swing GUI depicting the button called nothing for blank spavce, filler.
   */
  public javax.swing.JButton nothing;
  /** Java swing GUI depicting the panel called south.
   */
  public javax.swing.JPanel south;
  /** Java swing GUI depicting the panel called colors.
   */
  public javax.swing.JPanel colors;
  /** Java swing GUI depicting the panel for the color menu.
   */
  public javax.swing.JPanel color_menu;
  /** Java swing GUI depicting the red button.
   */
  public javax.swing.JButton red;
  /** Java swing GUI depicting the green button.
   */
  public javax.swing.JButton green;
  /** Java swing GUI depicting the blue button.
   */
  public javax.swing.JButton blue;
  /** Java swing GUI depicting the yellow button.
   */
  public javax.swing.JButton yellow;
  /** Java swing GUI depicting the magenta button.
   */
  public javax.swing.JButton magenta;
  /** Java swing GUI depicting the cyan button.
   */
  public javax.swing.JButton cyan;
  /** Java swing GUI depicting the pink button.
   */
  public javax.swing.JButton pink;
  /** Java swing GUI depicting the white button.
   */
  public javax.swing.JButton white;
  /** Java swing GUI depicting the light gray button.
   */
  public javax.swing.JButton lgt_gray;
  /** Java swing GUI depicting the gray button.
   */
  public javax.swing.JButton gray;
  /** Java swing GUI depicting the dark gray button.
   */
  public javax.swing.JButton drk_gray;
  /** Java swing GUI depicting the black button.
   */
  public javax.swing.JButton black;
  /** Java swing GUI depicting the brown button.
   */
  public javax.swing.JButton brown;
  /** Java swing GUI depicting the orange button.
   */
  public javax.swing.JButton orange;
//new color add
  /** Java swing GUI depicting the color1 button.
  */
  public javax.swing.JButton color1;
  /** Java swing GUI depicting the color2 button.
     */
  public javax.swing.JButton color2;
  /** Java swing GUI depicting the color3 button.
     */
  public javax.swing.JButton color3;
  /** Java swing GUI depicting the color4 button.
     */
  public javax.swing.JButton color4;
  /** Java swing GUI depicting the color5 button.
     */
  public javax.swing.JButton color5;
  /** Java swing GUI depicting the color6 button.
     */
  public javax.swing.JButton color6;
  /** Java swing GUI depicting the color7 button.
     */
//new color added
  public javax.swing.JButton color7;
  /** Java swing GUI depicting the panel for the current colors.
   */
  public javax.swing.JPanel current_colors;
  /** Java swing GUI depicting the current right color.
   */
  public javax.swing.JButton right;
  /** Java swing GUI depicting the current left colorh.
   */
  public javax.swing.JButton left;
  /** Java swing GUI depicting the panel for status.
   */
  public javax.swing.JPanel status;
  /** Java swing GUI depicting the panel for position.
   */
  public javax.swing.JPanel position;
  /** Java swing GUI depicting the panel for the coordinates.
   */
  public javax.swing.JLabel cordinates;
  //public javax.swing.JScrollPane center;
  // End of variables declaration
  //TOMS CODE:
  //Beginning of Tool Stuff

  /** This creates a new tool from ourTool which holds the current tool.
   */
  public ourTool currentTool;

  /** This is the currently selected button variable.
   */
  public JButton curButton;

  /** Creates a rounded rectangle instantiation.
   */
  public roundedRectTool toolRoundedRect;
  /** Instantiates the tool rectangle.
   */
  public rectTool toolRect;
  /** Instantiates the tool elipse.
   */
  public ellipseTool toolEllipse;
  /** Instantiates the tool polygon.
   */
  public polygonTool toolPolygon;
  /** Instantiates the tool line.
   */
  public lineTool toolLine;
  /** Instantiates the tool pencil.
   */
  public pencilTool toolPencil;
  /** Instantiates the tool brush.
   */
  public brushTool toolBrush;
  /** Instantiates the tool eraser.
   */
  public eraserTool toolEraser;
  /** Instantiates the tool select.
   */
  public selectTool toolSelect;

  //added
  /** Instantiates the tool magicSelect.
  */
  public magicSelectTool toolMagicSelect;
  //end added

  /** Instantiates the tool spray.
   */
  public sprayTool toolSpray;
  /** Instantiates the tool medicine.
   */
  public medicineTool toolMedicine;
  /** Instantiates the tool selectall.
   */
  public selectallTool toolSelectall;
  /** Instantiates the tool bucket.
   */
  public bucketTool toolBucket;
  /** Instantiates the tool curve.
   *
   */
  public curveTool toolCurve;
  /** Instantiates the tool letter.
   */
  public letterTool toolLetter;
  /** Instantiates the tool zoom.
   */
  public zoomTool toolZoom;

  /** The variable for the current selected button for spray.
   */
  public JButton curSpray;
  /** The variable for the current selected button for line.
   */
  public JButton curLine;
  /** The variable for the current selected button for curve.
   */
  public JButton curCurve;
  /** The variable for the current selected button for brush.
   */
  public JButton curBrush;
  /** The variable for the current selected button for zoom.
   */
  public JButton curZoom;
  /** The variable for the current selected button for elipse.
   */
  public JButton curEllipse;
  /** The variable for the current selected button for polygon.
   */
  public JButton curPolygon;
  /** The variable for the current selected button for square.
   */
  public JButton curSquare;
  /** The variable for the current selected button for round rectangle.
   */
  public JButton curRoundRect;

  /** The variable for the color that is used to highlight the tool property.
   */
  public Color propColor;

  /** printer object used for printing.
   */
  public printer ourPrinter;


  /** This method is code to display a presentation of the features of this PAINT
   * program.  It looks inside the directory of ./presentation first.
   *
   * There are no known OS/Hardware dependencies.  There are no known variances.
   * Security constraints may include potentially hazardous files inside the
   * ./presentation folder.  There are no references to any external specifications.
   *
   * This function is never called.
   * @param time This is the parameter for the amount of time the images are presented.	 Basically
   * a counter.
   * @param click This is a boolean value of whether or not the mouse has been clicked.
   */
  public void presentation(int time, boolean click){
      /*
      try{
	  File files_in_directory = new File("./presentation");
	  String[] list = files_in_directory.list();
	  for(int i = 0; i < list.length; i++){
	      mt = new MediaTracker(this);
	      System.out.println(list[i]);
	      JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(new FileInputStream("./presentation/"+list[i]));
	      splashImBuff = decoder.decodeAsBufferedImage();
	      int width = splashImBuff.getWidth();
	      int height = splashImBuff.getHeight();
	      splashIm = splashImBuff;
	      mt.addImage(splashIm,0);
	      mt.waitForID(0);
	      sw = new SplashWindow(this,splashIm,width,height);
	      sw.addMouseListener(new java.awt.event.MouseAdapter() {
		  public void mouseClicked(java.awt.event.MouseEvent evt) {
		      mt = null;
		      sw.dispose();
		      active = false;
		  }
	      });
	      try {
		  int k = 0;
		  if(click == false){
		      while(k < (500*time) && active == true){
			  Thread.sleep(100);
			  k+=100;
		      }
		  }
		  else{
		      while(active == true){
			  Thread.sleep(100);
		      }
		  }
		  active = true;

	      } catch(InterruptedException ie){}
	      mt = null;
	      sw.dispose();
	  }
      }
      catch(Exception e){e.printStackTrace();}
      */
  }

  /** This method instantiates all the tools and sets the default values of each tool.
   * It also sets the default color of the tools.
   *
   * There are no OS/Hardware dependencies or variances.  There are no security
   * constraints.  There are no references to any external specifications.
   *
   */
  public void toolInits() {
      propColor = new Color(Color.red.getRGB());
      //Istvan phase 5
      curButton = selectall;
      //End Istvan phase 5
      center.setLeftColor(left.getBackground());
      center.setRightColor(right.getBackground());

      toolRect = new rectTool();
      toolRoundedRect = new roundedRectTool();
      toolEllipse = new ellipseTool();
      toolPolygon = new polygonTool();
      toolLine = new lineTool();
      toolPencil = new pencilTool();
      toolBrush = new brushTool();
      toolEraser = new eraserTool();
      toolSelect = new selectTool();
      toolSpray = new sprayTool();
      toolMedicine = new medicineTool();
      toolSelectall = new selectallTool();
      toolBucket = new bucketTool();
      toolCurve = new curveTool();
      //added
	  toolMagicSelect = new magicSelectTool();
      //end added
      toolLetter = new letterTool(this);
      toolZoom = new zoomTool();
      //Istvan phase 5
      currentTool = toolSelectall;
		//End Istvan phase 5
      curSpray = spray1;
      curBrush = dot2;
      curZoom = zoom1;
      curEllipse = one;
      curPolygon = one;
      curSquare = one;
      curCurve = line1;
      curLine = line1;
      curRoundRect = one;
      curLine.setBackground(propColor);
      curBrush.setBackground(propColor);
      curZoom.setBackground(propColor);
      curSpray.setBackground(propColor);
  }

  /** This method initializes the rectangle tool to use the default color of
   * lightGray.
   *
   * <CODE>one</CODE> prints an outline of a rectangle.
   * <CODE>two</CODE> prints a solid rectangle.
   * <CODE>three</CODE> prints a solid rectangle with a border of the other color.
   *
   * There are no OS/Hardware dependencies or variances.  There are no security
   * constraints.  There are no references to any external specifications.
   *
   */
  public void clearRect() {
      one.setBackground(Color.lightGray);
      two.setBackground(Color.lightGray);
      three.setBackground(Color.lightGray);
  }

  /** This method sets all sizes of the line tool to the default color of
   * lightGray.
   *
   * There are no OS/Hardware dependencies or variances.  There are no security
   * constraints.  There are no references to any external specifications.
   *
   */
  public void clearLine() {
      line1.setBackground(Color.lightGray);
      line2.setBackground(Color.lightGray);
      line3.setBackground(Color.lightGray);
      line4.setBackground(Color.lightGray);
      line5.setBackground(Color.lightGray);
  }


  /** This method displays the beginning SPLASH screen image.  The filename is
   * "./images/paint_splash_4.jpg" so whatever you make paint_splash_4.jpg, it will display it.
   *
   * As you can see, there is a <CODE>thread.sleep(10000)</CODE> which waits TEN
   * SECONDS before the splash screen closes.
   *
   * Do note that this method calls yet another method called <B>SplashWindow</B> which
   * actually creates a window of size X, Y.
   *
   * There are no OS/Hardware dependencies or variances.  There are no security
   * constraints.  There are no references to any external specifications.
   */
  public void splash(){
      MediaTracker mt = new MediaTracker(this);
      try{
		  //Istvan phase 5
	  File greg = new File("./images/paint_splash_4.jpg");
	  //End Istvan phase 5
	  JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(new FileInputStream(greg));
	  splashIm = decoder.decodeAsBufferedImage();

	  mt.addImage(splashIm,0);
      }catch(Exception e){}
      try {
	  mt.waitForID(0);
      } catch(InterruptedException ie){}

      sw = new SplashWindow(this,splashIm,500,350);

      try {
	  Thread.sleep(4000);
      } catch(InterruptedException ie){}

      sw.dispose();
      mt = null;
  }

  /** This method basically creates the three dimensional look of the tools.  It makes
   * them look really like buttons!  This is due to the fact that javax.swing.JButton
   * contains a function called setBorder.  Then it calls .createLoweredBevelBorder for
   * the overall 3d-Effect.  Neato!
   *
   * There are no OS/Hardware dependencies or variances.  There are no security
   * constraints.  There are no references to any external specifications.
   *
   */
  public void borderInits() {
      line.setBorder(BorderFactory.createRaisedBevelBorder());
      square.setBorder(BorderFactory.createRaisedBevelBorder());
      curve.setBorder(BorderFactory.createRaisedBevelBorder());
      rounded_rectangle.setBorder(BorderFactory.createRaisedBevelBorder());
      polygon.setBorder(BorderFactory.createRaisedBevelBorder());
      elipse.setBorder(BorderFactory.createRaisedBevelBorder());
      selectall.setBorder(BorderFactory.createLoweredBevelBorder());
      eraser.setBorder(BorderFactory.createRaisedBevelBorder());
      paint.setBorder(BorderFactory.createRaisedBevelBorder());
      medicine.setBorder(BorderFactory.createRaisedBevelBorder());
      pencil.setBorder(BorderFactory.createRaisedBevelBorder());
      brush.setBorder(BorderFactory.createRaisedBevelBorder());
      spray.setBorder(BorderFactory.createRaisedBevelBorder());
      letter.setBorder(BorderFactory.createRaisedBevelBorder());
      select.setBorder(BorderFactory.createRaisedBevelBorder());
      zoom.setBorder(BorderFactory.createRaisedBevelBorder());
      //istvan new
      magicSelect.setBorder(BorderFactory.createRaisedBevelBorder());
  }

  /** returns red JButton
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @return red JButton
   */
  public JButton getRed() {
      return red;
  }

  /** returns JButton brown
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @return brown JButton
   */
  public JButton getBrown() {
      return red;
  }

  /** returns the paintproperties JPanel
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @return paintproperties Jpanel
   */
  public JPanel getPaintProperties() {
      return paint_properties;
  }

  /** returns lineproperties jpanel
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @return lineproperties JPanel
   */
  public JPanel getLineProperties() {
      return line_properties;
  }

  /** return right button Color
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @return Color right button
   */
  public Color getRight(){
      return right.getBackground();
  }

  /** returns JButton one, for rectangle drawing mode 1
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @return JButton one
   */
  public JButton getOne() {
      return one;
  }

  /** returns JButton two, for rectangle drawing mode 2
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @return JButton two
   */
  public JButton getTwo() {
      return two;
  }

  /** returns JButton three, for rectangle drawing mode 3
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @return JButton three
   */
  public JButton getThree() {
      return three;
  }

  /** returns jbutton line1, width 1
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @return JButton line1, width 1
   */
  public JButton getLine1() {
      return line1;
  }

  /** returns jbutton line2, width 2
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @return JButton line2, width 2
   */
  public JButton getLine2() {
      return line2;
  }

  /** returns jbutton line3, width 3
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @return JButton line3, width 3
   */
  public JButton getLine3() {
      return line3;
  }

  /** returns jbutton line4, width 4
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @return JButton line4, width 4
   */
  public JButton getLine4() {
      return line4;
  }

  /** returns jbutton line5, width 5
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @return JButton line5, width 5
   */
  public JButton getLine5() {
      return line5;
  }
  //added
  /** returns the current state of the preferences
   * It should work with all operating systems and hardware.
   * There are no variances and no security constraints.
   * @return prefsState myPrefs
   */
  public prefsState getPrefs() {
      return myPrefs;
  }

}

/** This method basically uses a customized mouse pointer for use in PAINT.
 * It looks in "/images/myPencil.gif".	It seems to be missing a period in front
 * of /images so therefore that might be a bug.
 *
 * There are no OS/Hardware dependencies or variances.	There are no security
 * constraints.	 There are no references to any external specifications.
 * @author TerpSoft
 * @version 1.0
 */
class myCursors{
    /** This is the class type Toolkit.	 It initializes it to get the default Toolkit.
     *
     * What is a Toolkit you may ask?  It is from java.awt.  The official explanation is:
     * "This class is the abstract superclass of all actual implementations of the Abstract Window Toolkit."
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     */
    Toolkit tk = Toolkit.getDefaultToolkit();
    /** This creates a variable of class Image called img.  This image is a representation
     * of the "/images/myPencil.gif" file.
     * It should work with all operating systems and hardware.
     * There are no variances and no security constraints.
     */
    Image img = tk.getImage(getClass().getResource("/images/myPencil.gif"));
    /** This field is a creation of a custom cursor to be used in PAINT.  It is
     * entitled "OUR CURSOR" for now.
     *
     */
    public Cursor pencilCursor = tk.createCustomCursor(img,new Point(10,10),"OUR CURSOR");
}

/** This class basically is an extention of the Window class.  It will create a
 * new window just for a splash screen at the beginning of the program.
 *
 * There are no OS/Hardware dependencies or variances.	There are no security
 * constraints.	 There are no references to any external specifications.
 *
 * @author TerpSoft
 * @version 1.0
 */
class SplashWindow extends Window {
    /** This field is the splash screen image named splashIm of class type Image.
     *
     */
    Image splashIm;

    /** This is a variable of type int which will be the horizontal resolution of the
     * current desktop.
     *
     *
     */
    int horizontal;

    /** This is a variable of type int which will be the vertical resolution of the
     * current desktop.
     *
     */
    int	 vertical;
    /** This method is inside of another class.	 This method basically initializes the splash
     * window which is created when the program starts.	 It sets the location of this
     * splash screen in the middle of the screen.  It then makes it visible.
     *
     * There are no OS/Hardware dependencies or variances.  There are no security
     * constraints.  There are no references to any external specifications.
     *
     * @param parent This parameter is of type Frame.
     * @param splashIm This parameter is of type Image, and contains the current splash image.
     * @param horizontal This parameter is of type int, containing the horizontal pixel resolution of the current desktop.
     * @param vertical This parameter is of type int, containing the vertical resolution of the current desktop.
     */
    SplashWindow(Frame parent, Image splashIm, int horizontal, int vertical) {
	super(parent);
	this.horizontal = horizontal;
	this.vertical = vertical;
	this.splashIm = splashIm;
	setSize(horizontal,vertical);

	/* Center the window */
	Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
	Rectangle winDim = getBounds();
	setLocation((screenDim.width - winDim.width) / 2,
	(screenDim.height - winDim.height) / 2);
	setVisible(true);
    }

    /** This method will actually "paint" the splash image into the window which was opened
     * for the splash screen.  That is why the parameter is of type Graphics.
     *
     * There are no OS/Hardware dependencies and no variances.	There are no security
     * constraints and no references to any external specifications.
     *
     * @param g This parameter is of type Graphics.
     */
    public void paint(Graphics g) {
	if (splashIm != null) {
	    g.drawImage(splashIm,0,0,this);
	}
    }
}

/** This class provides the preferences settings.
 *
 * There are no OS/Hardware dependencies or variances.	There are no security
 * constraints.	 There are no references to any external specifications.
 * @author TerpPaint
 * @version 3.0
 */

//added
class prefsState{
    /** This is the boolean state of copying to a new file.
    *	If true the paste to will copy to new files.
    */
    public boolean ToNewFile;
    /** This is the boolean state of copying to a new file and window.
	*	If true the paste to will copy to new files and open them in a window.
    */
    public boolean ToNewFileAndWindow;
    /** This is the boolean state of copying to a new layer.
	*	If true the paste to will create a new layer and then paste into that layer.
    */
    public boolean ToNewLayer;
    /** This is the boolean state of copying to the current layer.
	*	If true the paste to will paste into the current layer.
    */
    public boolean ToCurrentLayer;
    /** This is the boolean state of the new File copy.
	*	If true the paste to will paste to Files only if they exist.
    */
    public boolean OnlyIfExists;
    /** This is the boolean state of the new File copy.
	*   If true the paste to will paste to Files and create them if necessary.
	*/
    public boolean CreateNew;
    /** This is the boolean state of the new File creation.
	*   If true new files will use as their initial size the size of
	*   the last file saved.
	*/
    public boolean PromptSize;
    /** This is the boolean state of the new File creation.
	*   If true new files will ask for the size at creation.
	*/
    public boolean LastSize;
	/** This is the boolean state of the new File creation.
	*   If true new files will use as their initial size the size specified
	*   in Width and Height.
	*/
    public boolean FixedSize;
    /** This is the value for the animation interval.
	*/
	public double Anim;
    /** This is value for the width of a fixed size image.
	*/
	public int Width;
    /** This is value for the height of a fixed size image.
	*/
	public int Height;
	/** This is value for the undo level.
	*/
	// ming 4.12
	public int UndoLevel = 20 ;
	// ming 4.12 end
	//ronald phase 4
	/** This is value for the transition.
	*/
	public String Transition;
	/** This is value for the number of loops.
	*/
	public int CycleNumber;
	/** The constructor.
	*/
	prefsState(){
		//create a file stream and read it in
	  try{
	  	FileReader fis = new FileReader("prefs.txt");
		BufferedReader br=new BufferedReader(fis);

		String pref=br.readLine();
		if (pref.compareTo("true")==0)
			ToNewFile = true;
		else
			ToNewFile = false;
		
		pref=br.readLine();
		if (pref.compareTo("true")==0)
			ToNewFileAndWindow = true;
		else
			ToNewFileAndWindow = false;
		
		pref=br.readLine();
		if (pref.compareTo("true")==0)
			ToNewLayer = true;
		else
			ToNewLayer = false;
		
		pref=br.readLine();
		if (pref.compareTo("true")==0)
			ToCurrentLayer = true;
		else
			ToCurrentLayer = false;
		
		pref=br.readLine();
		if (pref.compareTo("true")==0)
			OnlyIfExists = true;
		else
			OnlyIfExists = false;
		
		pref=br.readLine();
		if (pref.compareTo("true")==0)
			CreateNew = true;
		else
			CreateNew = false;
		
		pref=br.readLine();
		if (pref.compareTo("true")==0)
			LastSize = true;
		else
			LastSize = false;
		
		pref=br.readLine();
		if (pref.compareTo("true")==0)
			PromptSize = true;
		else
			PromptSize = false;
		
		pref=br.readLine();
		if (pref.compareTo("true")==0)
			FixedSize = true;
		else
			FixedSize = false;
		
		pref=br.readLine();
		Anim=(new Double(pref)).doubleValue();
		
		pref=br.readLine();
		Width=(new Integer(pref)).intValue();
	
		pref=br.readLine();
		Height=(new Integer(pref)).intValue();
		
		pref=br.readLine();
		UndoLevel=(new Integer(pref)).intValue();

		pref=br.readLine();
		Transition=pref;

		pref=br.readLine();
		CycleNumber=(new Integer(pref)).intValue();

		br.close();
      }catch (Exception IOE){}
	}

}



