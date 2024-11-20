import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

/** This is a class for the eraser tool which is located at row 2 column 1 of the drawing icon.
 * It allows the user to erase what has been drawn.
 *
 * It should work with all operating systems and hardware.
 * There are no variances and no security constraints.
 *
 * @author TerpPaint
 * @version 2.0
 */
public class eraserTool implements ourTool {
    /** Holds the graphic that is actually used to erase.
     */
    Graphics2D g2D;
    /** Holds the BufferedImage that we use to create the graphic to do the erasing.
     */
    BufferedImage eraseImage;
    /** Holds the size of the eraser. It is initialized to 6 at the constructor.
     */
    public int eraserSize;

   /** used to initialize the start of the x and y values.
     */
    int prevX;

    /** used to initialize the start of the x and y values.
     */
    int prevY;


    /** Creates an eraserTool and initializes its size to 6.
     * It takes in no parameters or null arguments.  It does not return anything.
     * There are no algorithms of any kind and no variances and OS dependencies.
     * There should not be any exceptions or security constraints.
     */
    public eraserTool() {
	super();
	eraserSize = 6; /*FAULT:: eraserSize = 7; */
    }

    /** Allows the user to click on the canvas using the eraserTool.
     * It will get the BufferedImage from the canvas and prepare to draw
     * a graphic onto it. It has 2 parameters. The MouseEvent holds the button that
     * was clicked on (either left or right) and gets the coordinate
     * of where the mouse is clicked. The second parameter is the main_canvas which is the area
     * that can be erased.
     * @param mevt A MouseEvent object for showing which button in the mouse is clicked and the
     * coordinate of where the mouse is clicked.
     * @param theCanvas A main_canvas object which is the area that we are going to erase.
     */
    public void clickAction(MouseEvent mevt, main_canvas theCanvas) {
	int x, y;

	eraseImage = theCanvas.getBufferedImage();
	theCanvas.setBufferedImage(eraseImage);
	g2D = eraseImage.createGraphics();
	x = theCanvas.canvasScale(mevt.getX());
	y = theCanvas.canvasScale(mevt.getY());
	if (SwingUtilities.isLeftMouseButton(mevt)) {
		g2D.setColor(theCanvas.right_color); /*FAULT:: g2D.setColor(theCanvas.left_color); */
	} else {
	    g2D.setColor(theCanvas.left_color);
	}
	g2D.fill(getEraserShape(x,y));

	 prevX = x;
	prevY = y;
	eraseImage.flush();
    }

    /** Allows the user to drag the mouse using the eraserTool, and therefore,
     * allows the user to erase until the mouse is released.
     * It has 2 parameters. The first one is a MouseEvent and it is to check which button
     * in the mouse is clicked and to get the coordinate of where the mouse is clicked.
     * The second parameter is the canvas which is the area that we are going to erase.
     * @param mevt A MouseEvent object for showing which button in the mouse is clicked and the
     * coordinate of where the mouse is clicked.
     * @param theCanvas A main_canvas object which is the area that we are going to erase.
     */
    public void dragAction(MouseEvent mevt, main_canvas theCanvas) {
	/*int x, y;

	x = theCanvas.canvasScale(mevt.getX());
	y = theCanvas.canvasScale(mevt.getY());
	if (SwingUtilities.isLeftMouseButton(mevt)) {
	    g2D.setColor(theCanvas.right_color);
	} else {
	    g2D.setColor(theCanvas.left_color);
	}
	g2D.fill(getEraserShape(x,y));
	theCanvas.repaint();
	*/
	 int x, y;
	x = theCanvas.canvasScale(mevt.getX());
	y = theCanvas.canvasScale(mevt.getY());
	if (SwingUtilities.isLeftMouseButton(mevt)) {
	    //pencilImage.setRGB(x,y,theCanvas.left_color.getRGB());
		g2D.setColor(theCanvas.right_color); /*FAULT:: g2D.setColor(theCanvas.left_color);*/
	    g2D.setStroke(new BasicStroke(getEraserSize()));
	    g2D.drawLine(prevX,prevY,x,y);
	} else {
	    //pencilImage.setRGB(x,y,theCanvas.right_color.getRGB());
	    g2D.setColor(theCanvas.left_color);
	    g2D.setStroke(new BasicStroke(getEraserSize()));
	    g2D.drawLine(prevX,prevY,x,y);
	}
       g2D.fill(getEraserShape(x,y));
    int zoom = (int)theCanvas.getZoom();

   	int viewX = (int)theCanvas.pictureScrollPane.getViewport().getViewPosition().getX();
   	int viewY = (int)theCanvas.pictureScrollPane.getViewport().getViewPosition().getY();
   	// ming 4.22
   	if (zoom==0) zoom = 1;
   	// ming 4.22 end

   	int newx = ((x-viewX/zoom)*zoom);
   	int newy = ((y-viewY/zoom)*zoom);
   	int newprevX = ((prevX-viewX/zoom)*zoom);
   	int newprevY = ((prevY-viewY/zoom)*zoom);
   	System.out.println(prevX + " " + prevY + " " 
   					   + x + " " + y + " " 
   					   + newx + " " + newy);
   	if (newprevX <= newx) {
   		if (newprevX <=5*zoom) newprevX= 5*zoom;	
   		if (newprevY <= newy) {
   			if (newprevY <= 5*zoom) newprevY= 5*zoom;
   			//theCanvas.repaint(0, 0, 1, 1);
   			theCanvas.repaint(newprevX-5*zoom, newprevY-5*zoom, newx-newprevX+15*zoom, newy-newprevY+15*zoom);
   		}
   		else {
   			if (newy <= 5*zoom) newy = 5*zoom;
   			//theCanvas.repaint(0, 0, 1, 1);
   			theCanvas.repaint(newprevX-5*zoom, newy-5*zoom, newx-newprevX+15*zoom, newprevY-newy+15*zoom);
   		}
   	}
   	else if (newprevY <= newy) {
   		if (newx <= 5*zoom) newx = 5*zoom;
   		if (newprevY <= 5*zoom) newprevY = 5*zoom;
   		//theCanvas.repaint(0, 0, 1, 1);
   			theCanvas.repaint(newx-5*zoom, newprevY-5*zoom, newprevX-newx+15*zoom, newy-newprevY+15*zoom);
   	}
   		else {
   			if (newx <= 5*zoom) newx = 5*zoom;
   			if (newy <= 5*zoom) newy = 5*zoom;
   			//theCanvas.repaint(0, 0, 1, 1);
   			theCanvas.repaint(newx-5*zoom, newy-5*zoom, newprevX-newx+15*zoom, newprevY-newy+15*zoom);
   		}
	prevX = x;
	prevY = y;
	//theCanvas.repaint();
    }

    /** Allows the user to release the mouse, and therefore stop the erasing process.
     * It has 2 parameters. The first one is a MouseEvent and it
     * is to check which button in the mouse is clicked and to get the coordinate of where
     * the mouse is clicked. The second parameter is the canvas which is the area that we
     * are going to erase.
     * @param mevt A MouseEvent object for showing which button in the mouse is clicked and the
     * coordinate of where the mouse is clicked.
     * @param theCanvas A main_canvas object which is the area that we are going to erase.
     */
    public void mouseReleaseAction(MouseEvent mevt, main_canvas theCanvas) {
     /*	  int x, y;

	x = theCanvas.canvasScale(mevt.getX());
	y = theCanvas.canvasScale(mevt.getY());
	if (SwingUtilities.isLeftMouseButton(mevt)) {
	    g2D.setColor(theCanvas.right_color);
	} else {
	    g2D.setColor(theCanvas.left_color);
	}
	g2D.fill(getEraserShape(x,y));
	theCanvas.repaint();
       */

	 int x, y;
	x = theCanvas.canvasScale(mevt.getX());
	y = theCanvas.canvasScale(mevt.getY());
	if (SwingUtilities.isLeftMouseButton(mevt)) {
	    //pencilImage.setRGB(x,y,theCanvas.left_color.getRGB());
		g2D.setColor(theCanvas.right_color); /*FAULT:: g2D.setColor(theCanvas.left_color);*/
	    g2D.setStroke(new BasicStroke(getEraserSize()));
	    g2D.drawLine(prevX,prevY,x,y);
	} else {
	    //pencilImage.setRGB(x,y,theCanvas.right_color.getRGB());
	    g2D.setColor(theCanvas.left_color);
	    g2D.setStroke(new BasicStroke(getEraserSize()));
	    g2D.drawLine(prevX,prevY,x,y);
	}
	 g2D.fill(getEraserShape(x,y));
	theCanvas.repaint();




    }

    /** This function returns the color set to the private data
     * member g2d.
     * @return Color representing g2d
     */
    public Color getG2dColor(){
    	return g2D.getColor(); /*FAULT:: return null; */
    }

    /** Returns the Shape of the eraserTool, as specified by the x and y values
     * passed in. It will return a Rectangle object which is the eraser. It has
     * 2 int parameters which is for the coordinate of x and y.
     * @param x for x coordinate.
     * @param y for y coordinate.
     * @return Returns a Rectangular object which is the eraser.
     */
    public Shape getEraserShape(int x, int y) {
	Rectangle eraser;
	Double theSize = new Double(eraserSize/2);
	eraser = new Rectangle(x-theSize.intValue(),y-theSize.intValue(),eraserSize,eraserSize); /*FAULT:: eraser = new Rectangle(x-theSize.intValue(),y-theSize.intValue(),eraserSize,5); */
	return eraser;
    }

    /** Sets the size of the eraser, as specified by the int parameter passed in.
     * @param newSize int value.
     * @see #getEraserSize
     */
    public void setEraserSize(int newSize) { 
    	eraserSize = newSize; /*FAULT:: eraserSize = 10; */
    }

    /** Returns the size of the eraser as an int.
     * @return int eraserSize
     * @see #setEraserSize
     */
    public int getEraserSize(){
    	return eraserSize; /*FAULT:: return 5; */
    };
}
