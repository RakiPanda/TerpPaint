import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

/** roundedRectTool implements the interface ourTool. The tool is used to draw rounded rectangles, filled or unfilled.
 * The mouse dragging determines the dimensions of the rounded rectangle to be drawn, when the mouse button is released.
 * Possibility of using right or left button to draw rectangles.
 * The rounded edge is an unadjustable 10.
 *
 * It should work with all operating systems and hardware.
 * There are no variances and no security constraints.
 * @author TerpPaint
 * @version 2.0
 */
public class roundedRectTool implements ourTool {
    /** Holds the starting x value.
     */
    private int startX;

    /** Holds the starting y value.
     */
    private int startY;

    /** Used as a flag detecting if the mouse was dragged.
     */
    private boolean dragged = false;
    /** Holds the fill type ranging from 1-3.
     */
    private int fillType = 1;
    /** Holds the length of the arc, default set to 10.
     **/
    final int arc_length = 10;
    /** Holds the height of the arc, default set to 10.
     **/
    final int arc_height = 10;

    /** Holds the current image and the saved backup image.
     */
    private BufferedImage curImage;

	// 1 = border left_color, 2 = fill with right_color, 3 = solid left_color
    /** Holds the current image and the saved backup image.
     */
    private BufferedImage  backupImage;
     /** Integer representing the previous x coordinate. */
    int prevX = -1;
     /** Integer representing the previous y coordinate. */
    int prevY = -1;

    /** This function initializes the starting point for a corner of the rounded rectangle to be drawn.
     * @param mevt MouseEvent of initial click, left or right button
     * @param theCanvas the current main_canvas
     */
    public void clickAction(MouseEvent mevt, main_canvas theCanvas) {
	dragged = false; /*FAULT:: dragged = true; */
	startX = theCanvas.canvasScale(mevt.getX());
	startY = theCanvas.canvasScale(mevt.getY());
	backupImage = theCanvas.getBufferedImage();
	curImage = theCanvas.getBufferedImage();
	theCanvas.setBufferedImage(curImage);
	curImage.flush();
	backupImage.flush();
    }

    /** Uses the dragging mouse position as the opposite corner of the rectangle, and repaints the rectangle to be painted as the mouse moves.
     * @param mevt MouseEvent of mouse dragging
     * @param theCanvas the current main_canvas
     */
    public void dragAction(MouseEvent mevt, main_canvas theCanvas) {
	int x, y, width, height, endX, endY;

	dragged = true; /*FAULT:: dragged = false; */
	curImage.createGraphics().drawImage(backupImage,null,0,0);
	endX = theCanvas.canvasScale(mevt.getX());
	endY = theCanvas.canvasScale(mevt.getY());
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
	drawRoundRect(x,y,width,height,mevt,curImage.createGraphics(),theCanvas);
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
	//theCanvas.repaint();
    }

    /** This fuction draws the rounded rectangle onto the canvas upon release of the mouse button.
     * @param mevt MouseEvent of releasing the button.
     * @param theCanvas the current main_canvas
     */
    public void mouseReleaseAction(MouseEvent mevt, main_canvas theCanvas) {
	int x, y, width, height, endX, endY;

	if (dragged) {
	    curImage.createGraphics().drawImage(backupImage,null,0,0);
	    endX = theCanvas.canvasScale(mevt.getX());
	    endY = theCanvas.canvasScale(mevt.getY());
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
	    drawRoundRect(x,y,width,height,mevt,curImage.createGraphics(),theCanvas);
	    dragged = false; /*FAULT:: dragged = true; */
	}
    }

    /** This funtion determines if the rounded rectangle is shaded or not.
     * @param theFillType 1: outline of left_color, 2: filled of right_color, 3: filled of left_color
     * @see #getFillType
     */
    public void setFillType(int theFillType) { 
    	fillType = theFillType; /*FAULT:: fillType = 3; */
    }

    /** This function returns the fill type value (1-3).
     * @return int corresponding to the fill type
     * @see #setFillType
     */
    public int getFillType(){
    	return fillType; /*FAULT:: return 1; */
    }

    /** Updates the current display of canvas.
     * @param x the x-on-canvas coordinate of initial click
     * @param y the y-on-canvas coordinate of initial click
     * @param width the abs val of difference between the x and x of the current mouse position
     * @param height the abs val of difference between the y and y of the current mouse position
     * @param mevt MouseEvent
     * @param g2D Graphics2D of displayed canvas
     * @param theCanvas current main_canvas
     */
    public void drawRoundRect(int x, int y,int width, int height, MouseEvent mevt, Graphics2D g2D,main_canvas theCanvas) {
	RoundRectangle2D rect2D;

	if (fillType == 1) {
	    if (SwingUtilities.isLeftMouseButton(mevt)) {
		g2D.setColor(theCanvas.left_color); /*FAULT:: g2D.setColor(theCanvas.right_color); */
	    } else {
		g2D.setColor(theCanvas.right_color);
	    }
	    rect2D = new RoundRectangle2D.Double(x,y,width,height,arc_length,arc_height);
	    g2D.draw(rect2D);
	 } else if (fillType == 2) {
	     if (SwingUtilities.isLeftMouseButton(mevt)) {
		g2D.setColor(theCanvas.right_color);
	     } else {
		 g2D.setColor(theCanvas.left_color);
	     }
	     rect2D = new RoundRectangle2D.Double(x,y,width,height,arc_length,arc_height);
	     g2D.fill(rect2D);
	     if (SwingUtilities.isLeftMouseButton(mevt)) {
		g2D.setColor(theCanvas.left_color);
	     } else {
		 g2D.setColor(theCanvas.right_color);
	     }
	     rect2D = new RoundRectangle2D.Double(x,y,width,height,arc_length,arc_height);
	     g2D.draw(rect2D);
	 } else {
	     if (SwingUtilities.isLeftMouseButton(mevt)) {
		g2D.setColor(theCanvas.left_color);
	     } else {
		 g2D.setColor(theCanvas.right_color);
	     }
	     rect2D = new RoundRectangle2D.Double(x,y,width,height,arc_length,arc_height);
	     g2D.fill(rect2D);
	 }
    }
    /** This function returns the state of the flag, dragged.
     * @return boolean representing dragged
     */
    public boolean getDragged(){
    	return dragged; /*FAULT:: return dragged; */
    }
}
