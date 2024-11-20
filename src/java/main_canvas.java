/*main_canvas.java*/
/**
 * main_canvas.java
 * edited on Feb. 10, 2003
 * @author TerpPaint
 * @version 2.0
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


/** The class main_canvas represents a blank rectangular area of the screen onto
 * which the application can draw or from which the application can trap input
 * events from the user.
 *
 * There are no OS/Hardware dependencies and no variances.  There is no need for any
 * security constraints and no references to external specifications.
 *
 * @author TerpSoft
 * @version 1.0
 */
public class main_canvas extends JPanel {


    //#VERSION 3.0#
    
    /** JLayeredPane object.
     */
    public JLayeredPane layeredPane;
    /** max_list_len that indicates the max list length.
     */
    public int max_list_len = 5;

    /** LinkedList object that contains the layers.
     *///#VERSION 3.0#
    public LinkedList layerList;
    /** Vector object holding the Layer names.
     */
    public Vector nameList;


    /** current layer.
     *///#VERSION 3.0#
    public int currentLayer = 0;
    // ming 4.26
    /** LinkedList object that contains the LinkedLists of BufferedImages for layers separately.
    */
    public LinkedList list;
    // ming 4.26 end

    /** BufferedImage object that represents main image.
     */
    public BufferedImage main_image = null;
   
    /** BufferedImage object that is for temporary storage.
     */
    public BufferedImage temp_x;
     /** Color object that represents left mouse click event color.
    */
    
    //public BufferedImage temp_x;
    
    public Color left_color;
    /** Color object that represents right mouse click event color.
    */
    public Color right_color;
    // ming 4.22
    /** Color scale or gray scale.
     */
    public boolean grayscale;
    // ming 4.22 end

    /** Stroke object that represents the current stroke.
     */
    public Stroke current_stroke;
    /** Font object that represents a current font style.
     */
    public Font current_font;
    /** Place[i] is the spot in the BufferedImage linked list for the ith layer.
     */
    // ming 4.26
    public Vector place_list;
    // ming 4.26 end
    //Shape s = new Shape();

    /** Default line false.
     * true when this tool is chosen.
     *
     */
    public boolean line = false;
    /** Default curve false.
     * true when this tool is chosen.
     */
    public boolean curve = false;
    /** Default rouned_rectangle false.
     * true when this tool is chosen.
     */
    public boolean rounded_rectangle = false;
     /** Default square false.
      * true when this tool is chosen.
      */
    public boolean square = false;
    /** Default polygon false.
     * true when this tool is chosen.
     */
    public boolean polygon = false;
    /** Default elipse false.
     * true when this tool is chosen.
     */
    public boolean elipse = false;
    /** Default selectall false.
     * true when this tool is chosen.
     */
    public boolean selectall = false;
    /** Default eraser false.
     * true when this tool is chosen.
     */
    public boolean eraser = false;
    /** Default paint false.
     * true when this tool is chosen.
     */
    public boolean paint = false;
    /** Default medicine false.
     * true when this tool is chosen.
     */
    public boolean medicine = false;
    /** Default zoom false.
     * true when this tool is chosen.
     */
    public boolean zoom = false;
    /** Default pencil false.
     * true when this tool is chosen.
     */
    public boolean pencil = false;
    /** Default brush false.
     * true when this tool is chosen.
     */
    public boolean brush = false;
    /** Default spray false.
     * true when this tool is chosen.
     */
    public boolean spray = false;
    /** Default letter false.
     * true when this tool is chosen.
     */
    public boolean letter = false;
    /** Default select false.
     * true when this tool is chosen.
     */
    public boolean select = false;
    /** Sets zoomFactor default 1.0. */
    public double zoomFactor = 1.0;
    /** Sets oldZoomFactor default 1.0. */

    static double oldZoomFactor = 1.0;

    /** Provides scrolling functionality for the image. Scrollars are shown only if they are required.
     */
    public JScrollPane pictureScrollPane = new JScrollPane();
    /** Image that can be scrolled by setting either the horizontal scroll bar or the vertical scroll bar.
     */
    public ScrollablePicture picture;
    /** Shows all image icons on TerpPaint. */
    public ImageIcon main_icon = null;


    /** width of the canvas.
     */
    public int widt =1;
    /** height of canvas.
     */
    public int heig =1;
    /** Graphics2D object which represents the canvas.
     */
    Graphics2D gd;
    //Graphics2D gd2;
    /** AffineTransform object for various transformations.
     */
    AffineTransform t;
    
    //int oldX;
    //int oldY;
    
    //Point old;

    /** Starts loading the image icons.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     *
     */

    public main_canvas() {
	// Start loading the image icon now.

	widt = 500;
	heig = 500;
	/*FAULT:: widt = 400;*/
	// ming 4.22
	grayscale =false;
	// ming 4.22 end


	System.out.println("preferred size" + getPreferredSize().toString() );



	//#Version 3.0#
	//layeredPane = new JLayeredPane();
	//layeredPane.setPreferredSize(new Dimension(widt, heig));
	//layeredPane.add(pictureScrollPane);
	//add(layeredPane);




	pictureScrollPane.setPreferredSize( new Dimension( widt, heig ) );
	list = new LinkedList();
	// ming 4.27
	LinkedList list0 = new LinkedList();
	list.add(list0);
	place_list = new Vector();
	place_list.add(new Integer(0));
	currentLayer = 0;
	// ming 4.27 end
	// VERSION 3
	layerList = new LinkedList();
	nameList = new Vector();
	clear();
	setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	add(pictureScrollPane);
	// ming 5.9
     //	  noChanges();
	// ming 5.9 end
	/**t = new AffineTransform();
	t.setToScale(zoomFactor,zoomFactor);
    //BufferedImage temp = new BufferedImage((int)(main_image.getWidth()*zoomFactor),
									   //(int)(main_image.getHeight()*zoomFactor), BufferedImage.TYPE_INT_RGB);
	temp_x = new BufferedImage((int)(main_image.getWidth()*zoomFactor),
			   (int)(main_image.getHeight()*zoomFactor), BufferedImage.TYPE_INT_RGB);
    gd = temp_x.createGraphics();
    
	main_icon = new ImageIcon((Image)temp_x);

    picture = new ScrollablePicture(main_icon, 1);

    int oldX = (int)pictureScrollPane.getViewport().getViewPosition().getX();
    int oldY = (int)pictureScrollPane.getViewport().getViewPosition().getY();
    Point old = new Point(oldX,oldY);
    pictureScrollPane.setViewportView(picture);
    pictureScrollPane.getViewport().setViewPosition(old);*/
    //pictureScrollPane.paintAll(gd);
    }


    /** main_canvas constructor that creates a canvas of the size according to params.
     * @param w width param
     * @param h height param
     */
    
    
    public main_canvas( int h, int w) {
    	// Start loading the image icon now.

    	widt = h;
    	heig = w;
    	/*FAULT::widt = w;*/
    	// ming 4.22
    	grayscale =false;
    	// ming 4.22 end


    	System.out.println("preferred size" + getPreferredSize().toString() );



    	//#Version 3.0#
    	//layeredPane = new JLayeredPane();
    	//layeredPane.setPreferredSize(new Dimension(widt, heig));
    	//layeredPane.add(pictureScrollPane);
    	//add(layeredPane);




    	pictureScrollPane.setPreferredSize( new Dimension( widt, heig ) );
    	list = new LinkedList();
    	// ming 4.27
    	LinkedList list0 = new LinkedList();
    	list.add(list0);
    	place_list = new Vector();
    	place_list.add(new Integer(0));
    	currentLayer = 0;
    	// ming 4.27 end
    	// VERSION 3
    	layerList = new LinkedList();
    	nameList = new Vector();
    	clear();
    	setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    	add(pictureScrollPane);
    	// ming 5.9
         //	  noChanges();
    	// ming 5.9 end
        }
   /* public main_canvas(int wid, int hei) {
	// Start loading the image icon now.

	System.out.println("wid " + wid + " height " + hei );

	widt = wid;
	heig = hei;
	// ming 4.22
	grayscale =false;
	// ming 4.22 end


	pictureScrollPane.setPreferredSize(new Dimension(wid, hei));
	// ming 4.26
	LinkedList list0 = new LinkedList();
	list.add(list0);
	place_list = new Vector();
	place_list.add(new Integer(0));
	currentLayer = 0;
	// ming 4.26 end
	// VERSION 3
	layerList = new LinkedList();
	nameList = new Vector();
	//main_image = new BufferedImage(wid,hei,BufferedImage.TYPE_INT_RGB);



	//#Version 3.0#
	//layeredPane = new JLayeredPane();
	//layeredPane.setPreferredSize(new Dimension(wid, hei));
	//layeredPane.add(pictureScrollPane);
	//add(layeredPane);





	clear();
	//repaint();

	System.out.print("afterclear\n");
	setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	add(pictureScrollPane);
	// ming 5.9
    //	  noChanges();
	// ming 5.9 end
    }*/







    /** This method responds to checkbox events.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     *
     * @param e provides the functionalities of ItemEvent
     */
    public void itemStateChanged(ItemEvent e) {
	picture.setMaxUnitIncrement(1);
	/*FAULT::picture.setMaxUnitIncrement(2);*/
    }
    /** Returns true if any changes occur more than once; otherwise false.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     *
     * @return true if any changes occur more than once; otherwise false.
     */
    public boolean madeChanges(TerpPaint temp) {
	// ming 4.26
    /*FAULT::list.add(new LinkedList());*/
    LinkedList cur_action_list = (LinkedList)(temp.action_list.get(currentLayer));

    int cur_size = cur_action_list.size();
    if(list.size()==0)
    	return false;
    else{
	//LinkedList cur_list = (LinkedList)(list.get(currentLayer));
	if(cur_size <1)
		return false;
	return true;}
	// ming 4.26 end
    }

    /** This method sets linked list to size 0, so that undo and redo are disabled.
    * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     */
    public void noChanges() {
	//list = new LinkedList();
	// ming 4.26
	LinkedList cur_list = (LinkedList)(list.get(currentLayer));
	int cur_size = cur_list.size();
	for (int i=0; i<cur_size; i++)
	    cur_list.removeFirst();
	
	place_list.set(currentLayer, new Integer(0));
	/*FAULT::cur_list.add(new LinkedList());*/
	// ming 4.26 end
    }

    /** This method sets the main image with the default size and color.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     *
     */
    public void clear(){
	System.out.println("clear ");

	LinkedList cur_list = (LinkedList)(list.get(currentLayer));
	Integer p = (Integer)(place_list.get(currentLayer));
	int place = p.intValue();
	System.out.println(cur_list.size());
	if(place != 0){
	    for(int i = 0; i < place; ++i){
		// ming 4.4
		if (cur_list.size()>0){
		// ming 4.4 end
			/*FAULT::cur_list.add(new Object());*/
			cur_list.removeFirst();}
	    }
	}
	place = 0;
	if(cur_list.size() == max_list_len+1)
	    cur_list.remove(max_list_len);

	if(main_image != null){
	    main_image = new BufferedImage(main_image.getWidth(),main_image.getHeight(),BufferedImage.TYPE_INT_RGB);
	}
	else{
		System.out.println("imagenull");
	    main_image = new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
	}

//	  if( main_image != null )
//	  {
	Graphics2D g2d = main_image.createGraphics();
	g2d.setColor(Color.white);
	g2d.fillRect(0,0,main_image.getWidth(),main_image.getHeight());
	BufferedImage tempx = main_image.getSubimage(0,0,main_image.getWidth(), main_image.getHeight());
	cur_list.add(tempx);
	   //this.repaint();
	System.out.println(cur_list.size());
	place_list.set(currentLayer, new Integer(place));

	tempx.flush();
//	  }
    }

    /** This method sets the characteristics of the rectangle's border or the lines for any other shape.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     *
     * @param in the border size of the line.
     */
    public void setStroke(int in){
	current_stroke = new BasicStroke(in);
	/*FAULT::current_stroke = new BasicStroke(1);*/
    }
    /** This method sets a current font style.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     *
     * @param f contains the specified font, style and size.
     */
    public void setFont(Font f){
	current_font = f;
	/*FAULT::current_font = new Font("helvetica", 0, 11);*/
    }
    /** This method creates and draws the graphics and adds to the image list.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     *
     * @param s specifies the bounding of the image to display.
     */
    public void drawIt(Shape s){

	// ming 4.26
	LinkedList cur_list = (LinkedList)(list.get(currentLayer));
	Integer p = (Integer)(place_list.get(currentLayer));
	int place = p.intValue();
	if(place != 0){

	    for(int i = 0; i < place; ++i){
		// ming 4.4
	    	System.out.println("2");
		if (cur_list.size()>0){
		// ming 4.4 end
			System.out.println("3");
			/*FAULT::cur_list.add(new Object());*/
		cur_list.removeFirst();}
	    }
	}
	place = 0;
	if(cur_list.size() == max_list_len+1)
	{System.out.println("1");
	    cur_list.remove(max_list_len);
	}

	Graphics2D g2D = this.main_image.createGraphics();
	g2D.draw(s);
	BufferedImage tempx = main_image.getSubimage(0,0,main_image.getWidth(), main_image.getHeight());
	cur_list.add(0,tempx);
	place_list.set(currentLayer, new Integer(place));
	tempx.flush();
	// ming 4.26 end
	//repaint();
    }
    /** This method resizes the image.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     *
     * @param width a new width size of the image.
     * @param height a new height size of the image.
     */
    public void resizeImage(int width, int height){
	// ming 4.26
	LinkedList cur_list = (LinkedList)(list.get(currentLayer));
	Integer p = (Integer)(place_list.get(currentLayer));
	int place = p.intValue();
	// ming 4.27

	/*FAULT::width = height;*/

	if(place != 0){
	    for(int i = 0; i < place; ++i){
		// ming 4.4
		if (cur_list.size()>0)
		// ming 4.4 end
		cur_list.removeFirst();
	    }
	}
	place = 0;
	// ming 4.27 end
	if(cur_list.size() == max_list_len+1)
	   cur_list.remove(max_list_len);

	System.out.println("Resizing image");
	BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	/*FAULT::temp = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);*/
	Graphics2D helper = temp.createGraphics();
	//helper.
	System.out.println("wid "+ width + " heig " + height );


	helper.setColor(Color.white);
	helper.fillRect(0,0,width, height);
	helper.drawImage(main_image,null,0,0);

	main_image = getCopy(temp);
	widt = main_image.getWidth();
	heig = main_image.getHeight();
	// ming 4.4

	cur_list.add(0,temp);
	// ming 4.4 end

	//repaint();
	System.out.println("resize img ");
	System.out.println(cur_list.size());
	place_list.set(currentLayer, new Integer(place));
	
	temp.flush();
	setZoom(getZoom());
	// ming 4.26 end
    }



    /** This method resizes the image.
    * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     * @param width new width
     * @param height new height
     */
    public void sizeImage(int width, int height){
	// ming 4.26
    widt = width;
    heig = height;
	LinkedList cur_list = (LinkedList)(list.get(currentLayer));
	Integer p = (Integer)(place_list.get(currentLayer));
	int place = p.intValue();
	if(place != 0){
	    for(int i = 0; i < place; ++i){
		// ming 4.4
		if (list.size()>0){
		// ming 4.4 end
			//cur_list.add(new Object());
		cur_list.removeFirst();}
	    }
	}
	place = 0;
	if(cur_list.size() == max_list_len+1)
	    cur_list.remove(max_list_len);
	System.out.println("sizing image");
	BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	/*FAULT::temp = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);*/
//	  Graphics2D helper = temp.createGraphics();
	//helper.
	System.out.println("wid "+ width + " heig " + height );


//	  helper.setColor(Color.white);
//	  helper.fillRect(0,0,width, height);
//	  helper.drawImage(temp,null,0,0);
//	  helper.drawImage(main_image,null,0,0);

	main_image = getCopy(temp);
	widt = main_image.getWidth();
	heig = main_image.getHeight();
//	  list.add(0,temp);
       // repaint();
	place_list.set(currentLayer, new Integer(place));
	// ming 4.26 end
	temp.flush();
	setZoom(getZoom());
    }






    /** This method sets the main image.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     *
     * @param in contains information of buffered image.
     * @see #getBufferedImage
     */
    public void setBufferedImage(BufferedImage in) {
	// ming 4.26
	LinkedList cur_list = (LinkedList)(list.get(currentLayer));
	Integer p = (Integer)(place_list.get(currentLayer));
	int place = p.intValue();
	if(place != 0){
	    for(int i = 0; i < place; ++i){
		// ming 4.4
		if (cur_list.size()>0){
			/*FAULT::cur_list.add(new Object());*/
		// ming 4.4 end
		cur_list.removeFirst();}
	    }
	}
	place = 0;
	if(cur_list.size() == max_list_len+1)
	   cur_list.remove(max_list_len);
	main_image = in;
	widt = in.getWidth();
	heig = in.getHeight();
	cur_list.add(0,main_image);
	in.flush();
	System.gc();
	repaint();
	place_list.set(currentLayer, new Integer(place));
	setZoom(getZoom());
	// ming 4.26 end
    }
    /** Returns the main image.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     *
     * @return the buffereImage object.
     * @see #setBufferedImage
     */
    public BufferedImage getBufferedImage(){
    	/*FAULT::if(true) return null;*/
    	return getCopy(main_image);
	
    }
    /** Returns a copy of main image object.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     *
     * @param in contains information of buffered image.
     * @return the copy of buffereImage object.
     */
    public BufferedImage getCopy(BufferedImage in){
       //widt = in.getWidth();
       //heig = in.getHeight();
       BufferedImage temp = new BufferedImage(in.getWidth(), in.getHeight(),BufferedImage.TYPE_INT_RGB);
       Graphics2D g2d = temp.createGraphics();
       g2d.drawImage(in,null,0,0);
       /*FAULT::if(true) return null;*/
       return temp;
    }
    /** This method sets the left mouse button color.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     *
     * @param in the color to be the left_color.
     */
    public void setLeftColor(Color in){
	left_color = in;
    }
    /** This method sets the right mouse button color.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     *
     * @param in the color to be the right_color.
     */
    public void setRightColor(Color in) {
	right_color = in;
    }

    //***************************BEGINNING OF PAINT METHOD
    /** This method sets the main image scale and draw image if the main image is not null.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     *
     * @param g supports Graphics2D object to provide the functionality of it.
     */
    public void paint(Graphics g) {
		// ming 4.22
		// System.out.println("****Current height is"+main_image.getHeight()+";
		// Current width is"+main_image.getWidth()+"; Current zoomfactor
		// is"+zoomFactor+"; Current multiply
		// is"+(int)(main_image.getWidth()*zoomFactor*main_image.getHeight()*zoomFactor));

		if (main_image != null
				&& (int) (main_image.getWidth() * zoomFactor
						* main_image.getHeight() * zoomFactor) < 12000000) {

			// ming 4.22 end
			if (main_image != null) {
				gd.drawImage(main_image, t, null);
				pictureScrollPane.paintAll(g); //g2d
			}
			// ming 4.22
		} else {
			zoomFactor = oldZoomFactor;
			/*FAULT::zoomFactor = 2;*/
			
		}
		// ming 4.22 end
	}

	//***************************END OF PAINT METHOD

    /** This method cancels the last drawing action.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     *
     */
    public void undo(){
	// ming 4.26
	LinkedList cur_list = (LinkedList)(list.get(currentLayer));
	Integer p = (Integer)(place_list.get(currentLayer));
	int place = p.intValue();
	System.out.println("UNDO" + cur_list.size() + " " + place);
	if(place+1 <=cur_list.size()-1){
		System.out.println("momotaro");
	    ++place;
	   // resizeImage(((BufferedImage)cur_list.get(place)).getWidth(),((BufferedImage)cur_list.get(place)).getHeight());
	    main_image = (BufferedImage)cur_list.get(place);
	    setZoom(getZoom());
	}
	place_list.set(currentLayer, new Integer(place));
	// ming 4.26 end
	repaint(); // Ronald 5.2\
	/*FAULT::place_list.set(0, new Integer(9));*/
    }
    /** This method repeats the last drawing action.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     *
     * @return true if redo is performed, false if not performed
     */
    public boolean redo(){ // Ronald 5.2
	// ming 4.26
	LinkedList cur_list = (LinkedList)(list.get(currentLayer));
	Integer p = (Integer)(place_list.get(currentLayer));
	int place = p.intValue();
	System.out.println("REDO" + cur_list.size() + " " + place);
	if(place >0){System.out.println("true");
		--place;
		main_image = (BufferedImage)cur_list.get(place);
		setZoom(getZoom());
		/*FAULT::main_image = null;*/
		//} // Ronald 5.2
		place_list.set(currentLayer, new Integer(place));
		// ming 4.26 end
		repaint();// Ronald 5.2
		return true; // Ronald 5.2
	}// Ronald 5.2
	return false; // Ronald 5.2
    }

    /** This method returns the coordinate.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     *
     * @param coord the scale of the coordinate.
     * @return the integer coordinate.
     */
    public int canvasScale(int coord) {
    	/*FAULT::if(true) return 0;*/
    	return coord; }

    /** This method sets the zoomFactor.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     *
     * @param factor the size of the zoom factor.
     * @see #getZoom
     */
    public void setZoom(double factor){
	zoomFactor = factor;
    t = new AffineTransform();
	t.setToScale(zoomFactor,zoomFactor);
	if ((int)(main_image.getWidth()*zoomFactor) > 0 & (int)(main_image.getHeight()*zoomFactor) > 0)
	temp_x = new BufferedImage((int)(main_image.getWidth()*zoomFactor),
			   (int)(main_image.getHeight()*zoomFactor), BufferedImage.TYPE_INT_RGB);
    //BufferedImage temp = new BufferedImage((int)(main_image.getWidth()*zoomFactor),
									   //(int)(main_image.getHeight()*zoomFactor), BufferedImage.TYPE_INT_RGB);
    gd = temp_x.createGraphics();
	main_icon = new ImageIcon((Image)temp_x);

    picture = new ScrollablePicture(main_icon, 1);

    int oldX = (int)pictureScrollPane.getViewport().getViewPosition().getX();
    int oldY = (int)pictureScrollPane.getViewport().getViewPosition().getY();
    Point old = new Point(oldX,oldY);
    pictureScrollPane.setViewportView(picture);
    pictureScrollPane.getViewport().setViewPosition(old);
    //pictureScrollPane.paintAll(gd);
    }
    /** This method sets the size of the old zoom factor.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     *
     * @param factor the size of the zoom to be the old zoom factor.
     * @see #getOldZoom
     */
    public void setOldZoom(double factor){
	oldZoomFactor = factor;
    }
    /** Returns the size of the zoom factor.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     *
     * @return the size of the zoom factor.
     * @see #setZoom
     */
    public double getZoom(){
	return(zoomFactor);
    }
    /** Returns the size of the old zoom factor.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     *
     * @return the size of the old zoom factor.
     * @see #setOldZoom
     */
    public double getOldZoom(){
	return(oldZoomFactor);
    }
    // ming 4.22
  /** Returns the grayscale or colorscale
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     *
     * @return the size of the zoom factor.
     */
    public boolean getGrayscale(){
    	/*FAULT::grayscale = true;*/
	return(grayscale);
    }
    // ming 4.22 end

    //beginning of invertColors()
    /** This method inverts the colors for drawing with the graphics context.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     */
    public void invertColors(TerpPaint tp){

	int w = main_image.getWidth();
	int h = main_image.getHeight();
	int x = main_image.getMinX();
	int y = main_image.getMinY();
	BufferedImage im = getBufferedImage();
	
	int cur_layer = currentLayer;
	LinkedList cur_action_list = (LinkedList)tp.action_list.get(cur_layer);
	LinkedList cur_redo_list =(LinkedList)tp.redo_list.get(cur_layer);

	int temp1 = cur_redo_list.size();
	for (int i = 0; i < temp1; i++)
		cur_redo_list.removeLast();
	cur_action_list.add("Invert Colors");
	
	int[] pixels = new int[w * h];
	PixelGrabber pg = new PixelGrabber((Image)main_image, x, y, w, h, pixels, 0, w);
	try
	{
	    pg.grabPixels();
	} catch (InterruptedException e)
	{
	    System.err.println("interrupted waiting for pixels!");
	    return;
	}

	int[] rgbArray = new int[w * h];
	for (int j = 0; j < h; j++)
	{
	    for (int i = 0; i < w; i++)
	    {
		int pixel = pixels[j * w + i];
		//int alpha = (pixel >> 24) & 0xff;
		int red = (pixel >> 16) & 0xff;
		int green = (pixel >> 8) & 0xff;
		int blue = (pixel ) & 0xff;

		red = Math.abs((red - 255));
		green = Math.abs((green - 255));
		blue = Math.abs((blue - 255));
		Color newColor = new Color(red,green,blue);

		im.setRGB(x+i,y+j,newColor.getRGB());
	    }
	}
	setBufferedImage(im);
	//repaint();
    }
    //end of invertColors()
    
    /**
     * This method used to move a layer up in the layer stack.
     * @param number an int to represent layer number to move 
     */
    public void moveUp(int number) {
	if (layerList.size()==0) {
		System.out.println("upsize0");
	  // add the current layer if it is the first time a new layer is requested
	   layerList.add(main_image);
	   return;
	}
	else if (layerList.size() !=1){System.out.println("upsize1");
	  // update the current buffered image
	  layerList.set(currentLayer,main_image);

	  BufferedImage tempor = (BufferedImage) layerList.get(number);
	  layerList.set(number, layerList.get(number - 1));
	  layerList.set(number - 1, tempor);
	  String tempName = (String) nameList.get(number);
	  nameList.set(number, nameList.get(number - 1));
	  nameList.set(number - 1, tempName);
	  // ming 4.27
	  LinkedList temp_undolist = (LinkedList) list.get(number);
	  list.set(number, list.get(number - 1));
	  list.set(number - 1, temp_undolist);
	  Integer tempPlace = (Integer) place_list.get(number);
	  place_list.set(number, place_list.get(number - 1));
	  place_list.set(number - 1, tempPlace);
	  // ming 4.27 end

	  if (number !=0)
	    currentLayer = number-1;
	  tempor.flush();
	}
    }
   /**
     * This method used to move a layer down in the layer stack.
     * @param number an int to represent layer number to move 
     */
    public void moveDown(int number) {
	if (layerList.size()==0) {
	  // add the current layer if it is the first time a new layer is requested
	   layerList.add(main_image);
	   return;
	}
	else if (layerList.size() !=1){
	  // update the current buffered image
	  layerList.set(currentLayer,main_image);
	  BufferedImage tempor = (BufferedImage) layerList.get(number);
	  layerList.set(number, layerList.get(number + 1));
	  layerList.set(number + 1, tempor);
	  String tempName = (String) nameList.get(number);
	  nameList.set(number, nameList.get(number + 1));
	  nameList.set(number + 1, tempName);
	  // ming 4.27
	  LinkedList temp_undolist = (LinkedList) list.get(number);
	  list.set(number, list.get(number + 1));
	  list.set(number + 1, temp_undolist);
	  Integer tempPlace = (Integer) place_list.get(number);
	  place_list.set(number, place_list.get(number + 1));
	  place_list.set(number + 1, tempPlace);
	  // ming 4.27 end
	  if (number !=layerList.size()-1)
	    currentLayer = number+1;
	  tempor.flush();
	}
    }
    /**
     * This method used to move a layer to the top of the layer stack.
     * @param number an int to represent layer number to move 
     */
    public void moveFront(int number) {
	if (layerList.size()==0) {
	  // add the current layer if it is the first time a new layer is requested
	   layerList.add(main_image);
	}
	else if (layerList.size() !=1){
	  // update the current buffered image
	  layerList.set(currentLayer,main_image);
	  /*
	  BufferedImage tempor = (BufferedImage) layerList.get(number);
	  layerList.set(number, layerList.get(0));
	  layerList.set(number, tempor);   */

	  Object temp = layerList.remove(number);
	  layerList.add(0, temp);

	  Object tempName = nameList.remove(number);
	  nameList.add(0, tempName);

	  // ming 4.27
	  Object temp_undo = list.remove(number);
	  list.add(0, temp_undo);
	  Object temp_place = place_list.remove(number);
	  place_list.add(0, temp_place);
	  // ming 4.27 end

	  currentLayer = 0;
	}
    }
    /**
     * This method used to move a layer to the bottom of the layer stack.
     * @param number an int to represent layer number to move 
     */
    public void moveBottom(int number) {
	if (layerList.size()==0) {
	  // add the current layer if it is the first time a new layer is requested
	   layerList.add(main_image);
	}
	else if (layerList.size() !=1){
	  // update the current buffered image
	  layerList.set(currentLayer,main_image);

	  Object temp = layerList.remove(number);
	  layerList.add(temp);

	  Object tempName = nameList.remove(number);
	  nameList.add(tempName);

	  // ming 4.27
	  Object temp_undo = list.remove(number);
	  list.add(list.size()-1,temp_undo);
	  Object temp_place = place_list.remove(number);
	  place_list.add(place_list.size()-1,temp_place);
	  // ming 4.27 end

	  currentLayer = layerList.size()-1;
	}
    }


     /** This method changes to another layer.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     * @param correct correct layer to be displayed
     */ // VERSION 3
    public void changeLayer(String correct){
	if (layerList.size()==0) {
	  // add the current layer if it is the first time a new layer is requested
	   layerList.add(main_image);
	   //since there is only one layer, there is no need to change anything
	   return;
	}
	else
	  // update the current buffered image
	  layerList.set(currentLayer,main_image);
	for (int i = 0; i < nameList.size(); i++)
	{
	    if (((String) nameList.elementAt(i)) == correct){
		currentLayer = i;}
	}
	main_image = (BufferedImage)layerList.get(currentLayer);
	widt = main_image.getWidth();
	heig = main_image.getHeight();
	/*FAULT::widt = main_image.getWidth()-1;*/
	repaint();

    }
     /** This method adds a new layer.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     *
     */ // VERSION 3
// Ronald update

    public void addLayer(){
	//store previous layer into the linked list
	if (layerList.size()==0) {System.out.println("if");
	  // add the current layer too if it is the first time a new layer is requested
	   layerList.add(main_image);
	   // ming 4.26
	   main_image = new BufferedImage(main_image.getWidth(),main_image.getHeight(),BufferedImage.TYPE_INT_RGB);
	   widt = main_image.getWidth();
	   heig = main_image.getHeight();
	    //layerList.add(main_image);
	    Graphics2D g2d = main_image.createGraphics();
	    g2d.setColor(Color.white);
	    g2d.fillRect(0,0,main_image.getWidth(),main_image.getHeight());
	    BufferedImage tempx = main_image.getSubimage(0,0,main_image.getWidth(), main_image.getHeight());
	    LinkedList list0 = new LinkedList();
	    list0.add(tempx);
	    list.add(list0);
	    place_list.add(new Integer(0));
	    tempx.flush();
	    // ming 4.26 end
	}
	else
	  // update the current buffered image
	  layerList.set(currentLayer,main_image);
	//make a new layer
	main_image = new BufferedImage(main_image.getWidth(),main_image.getHeight(),BufferedImage.TYPE_INT_RGB);
	//layerList.add(main_image);
	Graphics2D g2d = main_image.createGraphics();
	g2d.setColor(Color.white);
	g2d.fillRect(0,0,main_image.getWidth(),main_image.getHeight());
	BufferedImage tempx = main_image.getSubimage(0,0,main_image.getWidth(), main_image.getHeight());
	layerList.add(tempx);
	currentLayer = layerList.size()-1;

	    LinkedList list0 = new LinkedList();
	    list0.add(tempx);
	    list.add(list0);
	    place_list.add(new Integer(0));
	    tempx.flush();
	    /*FAULT::layerList.remove(0);*/
    }
// Ronald 5.2 starts
     /** This method resets the layer.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     *
     */

    public void resetLayer(){
	//make a new layer
	main_image = new BufferedImage(main_image.getWidth(),main_image.getHeight(),BufferedImage.TYPE_INT_RGB);
	widt = main_image.getWidth();
	heig = main_image.getHeight();
	//layerList.add(main_image);
	Graphics2D g2d = main_image.createGraphics();
	g2d.setColor(Color.white);
	g2d.fillRect(0,0,main_image.getWidth(),main_image.getHeight());
	BufferedImage tempx = main_image.getSubimage(0,0,main_image.getWidth(), main_image.getHeight());
	if (layerList.size()==0)
	  layerList.add(tempx);
	else
	  layerList.set(0,tempx);
	repaint();
	    LinkedList list0 = new LinkedList();
	    list0.add(tempx);
	    list.add(list0);
	    place_list.add(new Integer(0));

	    tempx.flush();
	   /*FAULT:: layerList.remove(0);*/
    }// Ronald 5.2 ends
     /** This method removes a layer.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     * @param index index of the layer to be removed
     */ // VERSION 3
    public void removeLayer(int index){
	layerList.set(currentLayer,main_image);
	layerList.remove(index);
	if(nameList.size()>index)
		nameList.removeElementAt(index);
	// ming 4.27
	if(list.size()>index)
		list.remove(index);
	if(place_list.size()>index)
		place_list.remove(index);
	// ming 4.27 end
	main_image = (BufferedImage)layerList.get(0);
	widt = main_image.getWidth();
	heig = main_image.getHeight();
	currentLayer=0;
	repaint();
    }
      // Ronald 5.5 starts
     /** This method flattens all layers into one.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     * @param window Shows the progress while flattening
     */ // VERSION 3
    public void flattenLayer(JDialog window){
	//store previous layer into the linked list
	if (layerList.size()==0) {
	  // add the current layer too if it is the first time a new layer is requested
	   layerList.add(main_image);
	   // ming 4.26
	   main_image = new BufferedImage(main_image.getWidth(),main_image.getHeight(),BufferedImage.TYPE_INT_RGB);
	   widt = main_image.getWidth();
	   heig = main_image.getHeight();
	    //layerList.add(main_image);
	    Graphics2D g2d = main_image.createGraphics();
	    g2d.setColor(Color.white);
	    g2d.fillRect(0,0,main_image.getWidth(),main_image.getHeight());
	    BufferedImage tempx = main_image.getSubimage(0,0,main_image.getWidth(), main_image.getHeight());
	    LinkedList list0 = new LinkedList();
	    list0.add(tempx);
	    list.add(list0);
	    place_list.add(new Integer(0));
	    tempx.flush();
	    // ming 4.26 end
	}
	else
	  // update the current buffered image
	  layerList.set(currentLayer,main_image);
	// find the max width and height among all layers
	int maxW, maxH;
	maxW=maxH=0;
	for (int i=0; i<layerList.size(); i++){
	    if ( ((BufferedImage)layerList.get(i)).getHeight() > maxH)
		maxH = ((BufferedImage)layerList.get(i)).getHeight();
	    if ( ((BufferedImage)layerList.get(i)).getWidth() > maxW)
		maxW = ((BufferedImage)layerList.get(i)).getWidth();
	}
	BufferedImage temp = new BufferedImage(maxW,maxH,BufferedImage.TYPE_INT_RGB);
	Graphics2D g2d = temp.createGraphics();
	g2d.setColor(Color.white);
	g2d.fillRect(0,0,maxW,maxH);
	BufferedImage flattenImage = temp.getSubimage(0,0,maxW, maxH);
	// calculate total pixels to be processed
      int totalPixels=0;
      for (int i =0;i<layerList.size();i++){
	  totalPixels +=
	  ((((BufferedImage)layerList.get(i)).getHeight())*(((BufferedImage)layerList.get(i)).getWidth()));
      }
      window.setTitle("Please wait");//Ronald phase 5
      window.setSize(250,100);
      window.setLocation(400, 300);
      window.setVisible( true );   // display window
      int counter=0;
	for (int i = layerList.size()- 1; i >= 0; i--){
	    BufferedImage currImage = (BufferedImage) layerList.get(i);
	    for (int w=0; w < currImage.getWidth(); w++){
		for (int h=0; h < currImage.getHeight(); h++){
     //		      System.out.println("w"+w+" h"+h+" pix"+currImage.getRGB(w,h));
		    if (currImage.getRGB(w,h) != -1)
			flattenImage.setRGB(w,h,currImage.getRGB(w,h));
		    counter++;
     //		      System.out.println("counter"+counter+" total"+totalPixels);
		}
		window.setTitle("Please wait....... "+(counter*100/totalPixels)+"% done");
	    }
	    currImage.flush();
	}
      window.setVisible( false );
	layerList.set(0,flattenImage);
	main_image = flattenImage;
	widt = main_image.getWidth();
	heig = main_image.getHeight();
	currentLayer=0;
	repaint();
	temp.flush();
	flattenImage.flush();
//	  currentLayer = 0;
//	      LinkedList list0 = new LinkedList();
//	      list0.add(tempx);
//	      list.add(list0);
//	      place_list.add(new Integer(0));

       // repaint();
    }
      // Ronald 5.5 ends
    /** Creates the main drawing frame.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     *
     * @param s the command line argument.
     */
    public static void main(String s[]) {
	JFrame frame = new JFrame("ScrollDemo");
	frame.addWindowListener(new WindowAdapter() {
	    public void windowClosing(WindowEvent e) {
		System.exit(0);
	    }
	});

	frame.setContentPane(new main_canvas());
	frame.pack();
	frame.setVisible(true);
    }
}
