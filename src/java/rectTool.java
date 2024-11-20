import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

/** A rectTool implements the operations performed by a rectangle.
 *
 * It holds the private fields int startX and startY, boolean dragged and int fillType.
 * The variable dragged is set to true only when the method dragAction is called
 * and is set to false otherwise.
 * The fillType is assigned as follows:
 * 1=set the color of the border.
 * 2=set the color of the border and the fill.
 * otherwise only set the color of the fill.
 *
 * It should work with all operating systems and hardware.
 * There are no variances and no security constraints.
 *
 * @author TerpPaint
 * @version 2.0
 */
public class rectTool implements ourTool {
    /** initialized in clickAction by calling the function getX() and getY()
     * on the MouseEvent mevt. (Initialized here since a rectangle is drawn
     * by first clicking the mouse)
     */
    public int startX;

    /** initialized in clickAction by calling the function getX() and getY()
     * on the MouseEvent mevt. (Initialized here since a rectangle is drawn
     * by first clicking the mouse)
     */
    public int startY;

    /** initialized to false and set to false in clickAction because the mouse
     * has not yet moved. Once the mouse has been clicked on and dragged, it
     * is then set to true.
     */
    private boolean dragged = false;
    /** initializes fillType to 1. Reset by calling the method setFillType and
     * passing a different integer value.
     */
    private int fillType = 1;
	// 1 = border left_color, 2 = fill with right_color, 3 = solid left_color
    /** Holds the current image and the saved backup image.
     */
    BufferedImage curImage;

    /** Holds the current image and the saved backup image.
     */
    BufferedImage backupImage;

	/** holds a Graphics2D used in the following methods.
	 */
    public Graphics2D g2D;
    
    /** Integer representing the previous x coordinate. */
    private int prevX = -1;
    /** Integer representing the previous y coordinate. */
    private int prevY = -1;
    //private int prevWidth = -1;
    //private int prevHeight = -1;

	/** Allows the user to click on the canvas, to draw a rectangle.
	 * Sets the dragged to be false and the backup Image and current Image
	 * to be the same because nothing has been drawn yet.
	 * @param mevt holds a MouseEvent to call the getX() and getY() to set
	 * the startX and startY, which are the coordinates that the mouse first
	 * clicks on
	 * @param theCanvas holds a main_canvas that calls the getBufferedImage to
	 * be set to the backup image and current image.
	 */
    public void clickAction(MouseEvent mevt, main_canvas theCanvas) {
	dragged = false;
	startX = mevt.getX();
	startY = mevt.getY(); /*FAULT:: startY = 16; */
	//System.out.println("Rectangle--left mouse");
	backupImage = theCanvas.getBufferedImage();
	curImage = theCanvas.getBufferedImage();
	theCanvas.setBufferedImage(curImage);
	g2D = curImage.createGraphics();
	theCanvas.repaint();
	curImage.flush();
	backupImage.flush();
    }

    /** Allows the user to drag the mouse on the canvas and stretch the rectangle.
     * Always sets the flag dragged to true.
     * The variables x and y are initially set to the
     * values of the startX and startY. However, if the endX is moved to the
     * left (which would mean it is now less than the startX), then the variable
     * x is set to the endX. The same is done for the variable y. The width
     * is calculated to be the absolute value of the startX - endX. The height
     * is calculated to be the absolute value of the startY - endY.
     * @param mevt holds a MouseEvent to call the getX() and getY() for the
     * endX() and endY() variables.
     * @param theCanvas holds a main_canvas to drag the rectangle on.
     */
    public void dragAction(MouseEvent mevt, main_canvas theCanvas) {
	int endX, endY, x, y, width, height;

	g2D.drawImage(backupImage,null,0,0);
	dragged = true; /*FAULT:: dragged = false; */
	endX = mevt.getX();
	endY = mevt.getY();
	x = startX;
	y = startY;
	if (endX < startX) {
	   x = endX;
	}
	if (endY < startY) {
	   y = endY;
	}
	width = Math.abs(startX - endX);
	height = Math.abs(startY - endY);
	drawRectangle(x,y,width,height,mevt,g2D,theCanvas);
	int zoom = (int)theCanvas.getZoom();

	int viewX = (int)theCanvas.pictureScrollPane.getViewport().getViewPosition().getX();
	int viewY = (int)theCanvas.pictureScrollPane.getViewport().getViewPosition().getY();
	// ming 4.22
	if (zoom==0) zoom = 1;
	// ming 4.22 end

	int newx = ((startX-viewX/zoom)*zoom);
	int newy = ((startY-viewY/zoom)*zoom);
	int newendX = ((endX-viewX/zoom)*zoom);
	int newendY = ((endY-viewY/zoom)*zoom);
	width = Math.abs(newx - newendX);
	height = Math.abs(newy - newendY);
	prevX = ((prevX-viewX/zoom)*zoom);
	prevY = ((prevY-viewY/zoom)*zoom);
	int prevWidth = Math.abs(newx - prevX);
	int prevHeight = Math.abs(newy - prevY);
	if (prevX != -1) {
		if (newx < prevX) {
			prevX = newx;
		}
		if (newy < prevY) {
			prevY = newy;
		}
	theCanvas.repaint(prevX-zoom, prevY-zoom, prevWidth+4*zoom, prevHeight+4*zoom);
	}
	if (newx > newendX) {
	   newx = newendX;
	}
	if (newy > newendY) {
	   newy = newendY;
	}
	prevX = endX;
	prevY = endY;
	theCanvas.repaint(newx-zoom, newy-zoom, width+4*zoom, height+4*zoom);
    }

    /** Allows the user to release the mouse and stop drawing the rectangle, which
     * was created by dragging the mouse. Therefore, this is performed only if
     * dragged = true.
     * The x and y variables are initially set to startX and startY, however,
     * if the endX is now on the left of the startX (or is < than the startX,
     * then x is set to endX. Also, if the endY is now less than the startY,
     * the y is set to the endY.
     * The width is calculated as the absolute value of the startX-endX.
     * The height is calculated as the absolute value of the startY-endY.
     * Dragged is set to false.
     * @param mevt holds a MouseEvent to call the getX and getY to set the
     * endX and endY
     * @param theCanvas holds a main_canvas
     */
    public void mouseReleaseAction(MouseEvent mevt, main_canvas theCanvas) {
	int x, y, width, height, endX, endY;


	if (dragged) {
	    g2D.drawImage(backupImage,null,0,0);
	    endX = mevt.getX();
	    endY = mevt.getY();
	    x = startX;
	    y = startY;
	    if (endX < startX) {
		x = endX;
	    }
	    if (endY < startY) {
		y = endY;
	    }
	    width = Math.abs(startX - endX);
	    height = Math.abs(startY - endY);
	    drawRectangle(x,y,width,height,mevt,g2D,theCanvas);
	    /*int zoom = (int)theCanvas.getZoom();

		int viewX = (int)theCanvas.pictureScrollPane.getViewport().getViewPosition().getX();
		int viewY = (int)theCanvas.pictureScrollPane.getViewport().getViewPosition().getY();
		// ming 4.22
		if (zoom==0) zoom = 1;
		// ming 4.22 end

		int newx = ((startX-viewX)/zoom);
		int newy = ((startY-viewY)/zoom);
		int newendX = ((endX-viewX)/zoom);
		int newendY = ((endY-viewY)/zoom);
		width = Math.abs(newx - newendX);
		height = Math.abs(newy - newendY);
		if (newx > newendX) {
		   newx = newendX;
		}
		if (newy > newendY) {
		   newy = newendY;
		}
			
		theCanvas.repaint(newx,newy,width+2,height+2);*/
	    theCanvas.repaint();
	    dragged = false; /*FAULT:: dragged = true;*/
	    curImage.flush();
		backupImage.flush();
	    //System.out.println("Drew rectangle: "+x+","+y+","+width+","+height);
	}
	prevX = -1;
    }

    /** Sets the fillType to the int passed in. Default is 1.
     * @param theFillType determines which way to fill the rectangle.
     * 1 = border left_color
     * 2 = fill with right_color
     * 3 = solid left_color
     * @see #getFillType
     */
    public void setFillType(int theFillType) { 
    	fillType = theFillType; /*FAULT:: fillType = 5; */
    }

    /** Returns the fill type of the rectangle as an int.
     * @return int corresponding to the fill type
     * @see #setFillType
     */
    public int getFillType(){
    	return fillType; /*FAULT: return 5; */
    }

    /** Draws the rectangle and sets the color of the border and the fill.
     * If fillType==1, and the LeftMouseButton is used, it sets the field
     * g2D to the left color. Otherwise it is set to the right color.
     *
     * If fillType==2, and the LeftMouseButton is used, it sets the field
     * g2D to the right color. Otherwise it is set to the left color. It fills
     * the g2D with the color that it was set to. To set the border, if the
     * leftMouseButton is used, then the color of the border is set to the left color.
     * If the rightMouseButton is used, then the color is set to the right color.
     *
     * If the fill type is not 1 or 2, then it proceeds to the final else. If the
     * LeftMouseButton is used, the fill color is set to the left color. Otherwise,
     * it is set to the right color. Notice that the border is not set in this case.
     * @param x used to initialize the starting x coordinate of the rectangle.
     * @param y used to initialize the starting y coordinate of the rectangle
     * @param width used to set the width of the rectangle
     * @param height used to set the height of the rectangle
     * @param mevt holds a MouseEvent
     * @param g2D holds a Graphics2D
     * @param theCanvas holds a main_canvas
     */
     public void drawRectangle(int x, int y, int width, int height, MouseEvent mevt,Graphics2D g2D,main_canvas theCanvas) {
	if (fillType == 1) {
		if (SwingUtilities.isLeftMouseButton(mevt)) {
		    System.out.println("draw rect-in left");
		    g2D.setColor(theCanvas.left_color);
		} else {
		    System.out.println("draw rect-in right");
		    g2D.setColor(theCanvas.right_color); /*FAULT:: g2D.setColor(theCanvas.left_color); */
		    System.out.println("this is set to:"+g2D.getColor());
		}
		g2D.draw(new Rectangle(x,y,width,height));
	    } else if (fillType == 2) {
		if (SwingUtilities.isLeftMouseButton(mevt)) {
		    g2D.setColor(theCanvas.right_color);
		} else {
		    g2D.setColor(theCanvas.left_color);
		}
		g2D.fill(new Rectangle(x,y,width,height));
		if (SwingUtilities.isLeftMouseButton(mevt)) {
		    g2D.setColor(theCanvas.left_color);
		} else {
		    g2D.setColor(theCanvas.right_color);
		}
		g2D.draw(new Rectangle(x,y,width,height));
	    } else {
		if (SwingUtilities.isLeftMouseButton(mevt)) {
		    g2D.setColor(theCanvas.left_color);
		} else {
		    g2D.setColor(theCanvas.right_color);
		}
		g2D.fill(new Rectangle(x,y,width,height));
	    }
     }

    /** Returns the current state of the flag dragged. If dragAction was called,
     * it returns true. Otherwise, it returns false.
     * @return a boolean corresponding to the flag dragged
     */
    public boolean getDragged(){
    	return dragged; /*FAULT:: return !dragged; */
    }
    /** Returns the Color corresponding to the Graphics2D g2D.
     * @return Color corresponding to that of the Graphics2D
     */
    public Color getG2dColor(){
    	return g2D.getColor(); /*FAULT:: return null; */
    }
}
