/**
 * letterTool.java
 * edited on Feb. 10, 2003
 */

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

/**
 * The class lineTool represents the mouse events for drawing lines, setting the
 * line stroke. The lineTool class includes methods for clicking, dragging and
 * releasing the mouse, for drawing the lines, and for setting line stroke with
 * basic stroke.
 * 
 * There are no OS/Hardware dependencies and no variances. There is no need for
 * any security constraints and no references to external specifications.
 * 
 * @author TerpPaint
 * @version 2.0
 */
public class lineTool implements ourTool {

	/**
	 * The x-coordinate of the starting point of the line.
	 */
	private int startX;

	/**
	 * The y-coordinate of the starting point of the line.
	 */
	private int startY;

	/**
	 * The status of the drag event. Default false
	 */
    public boolean dragged = false;

	/**
	 * BufferedImage object that represents the current image.
	 */
	private BufferedImage curImage;

	/**
	 * BufferedImage object that represents the backup image.
	 */
	private BufferedImage backupImage;

	/**
	 * Stroke object that represents the the width of the line to be filled.
	 */
	private Stroke lineStroke;

	/**
	 * Stores the previous x coord.
	 */
	int prevX = -1;
	/**
	 * Stores the previous y coord.
	 */
	int prevY = -1;

	/**
	 * Creates a lineTool and initializes the lineStroke to 1.f to represent a
	 * basic Stroke. There are no OS/Hardware dependencies and no variances.
	 * There is no need for any security constraints and no references to
	 * external specifications.
	 *  
	 */
	public lineTool() {

		super();
		lineStroke = new BasicStroke(1.f);
	}

	/**
	 * Allows the user to click on the canvas using the lineTool. The parameters
	 * passed in are a MouseEvent, which holds the coordinates of where the
	 * mouse was clicked, and a main_canvas which holds the bufferedImage. There
	 * are no OS/Hardware dependencies and no variances. There is no need for
	 * any security constraints and no references to external specifications.
	 * 
	 * @param mevt
	 *            contains information about the mouse event including
	 *            x-coordinates and y-coordinates.
	 * @param theCanvas
	 *            includes contents of bufferedImage object for current and
	 *            backup images.
	 */
	public void clickAction(MouseEvent mevt, main_canvas theCanvas) {
		dragged = false;
		/*FAULT::dragged = true;*/
		startX = theCanvas.canvasScale(mevt.getX());
		startY = theCanvas.canvasScale(mevt.getY());
		System.out.println("Line");
		backupImage = theCanvas.getBufferedImage();
		System.out.println("Linea");
		curImage = theCanvas.getBufferedImage();
		System.out.println("Lineb");
		theCanvas.setBufferedImage(curImage);
		System.out.println("Linec");
	}

	/**
	 * Allows the user to drag the mouse using the lineTool, once the mouse has
	 * been clicked and clickAction has been called. The parameters passed in
	 * are a MouseEvent, which holds the coordinates of where the mouse was
	 * clicked, and a main_canvas which holds the bufferedImage. Sets the
	 * boolean dragged to true. There are no OS/Hardware dependencies and no
	 * variances. There is no need for any security constraints and no
	 * references to external specifications.
	 * 
	 * @param mevt
	 *            contains information of the mouse event including
	 *            x-coordinates and y-coordinates.
	 * @param theCanvas
	 *            provides the contents of the main canvas to draw the line.
	 */
	public void dragAction(MouseEvent mevt, main_canvas theCanvas) {
		dragged = true;
		
		curImage.createGraphics().drawImage(backupImage, null, 0, 0);
		drawLine(mevt, curImage.createGraphics(), theCanvas);
		int zoom = (int) theCanvas.getZoom();

		int viewX = (int) theCanvas.pictureScrollPane.getViewport()
				.getViewPosition().getX();
		int viewY = (int) theCanvas.pictureScrollPane.getViewport()
				.getViewPosition().getY();
		// ming 4.22
		if (zoom == 0)
			zoom = 1;
		// ming 4.22 end

		int newx = ((startX - viewX / zoom) * zoom);
		int newy = ((startY - viewY / zoom) * zoom);
		int newendX = ((mevt.getX() - viewX / zoom) * zoom);
		int newendY = ((mevt.getY() - viewY / zoom) * zoom);
		int width = Math.abs(newx - newendX);
		int height = Math.abs(newy - newendY);
		prevX = ((prevX - viewX / zoom) * zoom);
		prevY = ((prevY - viewY / zoom) * zoom);
		int prevWidth = Math.abs(newx - prevX);
		int prevHeight = Math.abs(newy - prevY);
		if (prevX != -1) {
			//System.out.println(newx+" "+newy);
			if (newx < prevX) {
				prevX = newx;
			}
			if (newy < prevY) {
				prevY = newy;
			}
			theCanvas.repaint(prevX - 3 * zoom, prevY - 3 * zoom, prevWidth + 8
					* zoom, prevHeight + 8 * zoom);
		}
		if (newx > newendX) {
			newx = newendX;
		}
		if (newy > newendY) {
			newy = newendY;
		}
		prevX = mevt.getX();
		prevY = mevt.getY();
		theCanvas.repaint(newx - 3 * zoom, newy - 3 * zoom, width + 8 * zoom,
				height + 8 * zoom);
		//theCanvas.repaint();
		/*FAULT::dragged = false;*/
	}

	/**
	 * Allows the user to release the mouse and stop drawing the line, once the
	 * mouse has been clicked and clickAction has been called. Sets the boolean
	 * dragged to false. There are no OS/Hardware dependencies and no variances.
	 * There is no need for any security constraints and no references to
	 * external specifications.
	 * 
	 * @param mevt
	 *            is used to draw line.
	 * @param theCanvas
	 *            is used to the current image of the line.
	 */
	public void mouseReleaseAction(MouseEvent mevt, main_canvas theCanvas) {
		if (dragged) {
			curImage.createGraphics().drawImage(backupImage, null, 0, 0);
			drawLine(mevt, curImage.createGraphics(), theCanvas);
			/*
			 * int zoom = (int)theCanvas.getZoom();
			 * 
			 * int viewX =
			 * (int)theCanvas.pictureScrollPane.getViewport().getViewPosition().getX();
			 * int viewY =
			 * (int)theCanvas.pictureScrollPane.getViewport().getViewPosition().getY(); //
			 * ming 4.22 if (zoom==0) zoom = 1; // ming 4.22 end
			 * 
			 * int newx = ((startX-viewX)/zoom); int newy =
			 * ((startY-viewY)/zoom); int newendX = ((mevt.getX()-viewX)/zoom);
			 * int newendY = ((mevt.getY()-viewY)/zoom); int width =
			 * Math.abs(newx - newendX); int height = Math.abs(newy - newendY);
			 * if (newx > newendX) { newx = newendX; } if (newy > newendY) {
			 * newy = newendY; }
			 * 
			 * theCanvas.repaint(newx,newy,width+2,height+2);
			 */
			theCanvas.repaint();
			dragged = false;
		}
		
		/*FAULT::dragged = true;*/
	}

	/**
	 * Draws the line as specified by the parameters passed in. The MouseEvent
	 * holds which button on the mouse was clicked on by the user and colors the
	 * line accordingly. There are no OS/Hardware dependencies and no variances.
	 * There is no need for any security constraints and no references to
	 * external specifications.
	 * 
	 * @param mevt
	 *            contains the x-coordinate and y-coordinate of the end point.
	 * @param g2D
	 *            sets the color of the line depending on the mouse button
	 *            action. Sets the left color if left button event occurs, else
	 *            right color. Also sets stroke.
	 * @param theCanvas
	 *            contains contents of the main canvas.
	 */
	public void drawLine(MouseEvent mevt, Graphics2D g2D, main_canvas theCanvas) {
		int endX, endY;

		endX = theCanvas.canvasScale(mevt.getX());
		endY = theCanvas.canvasScale(mevt.getY());
		if (SwingUtilities.isLeftMouseButton(mevt)) {
			System.out.println("left");
			g2D.setColor(theCanvas.left_color);
		} 
		else {
			System.out.println("right");
			g2D.setColor(theCanvas.right_color);
		}
		g2D.setStroke(lineStroke);
		g2D.draw(new Line2D.Double(startX, startY, endX, endY));
		/*FAULT::lineStroke = new BasicStroke(2);*/
		
	}

	/**
	 * Sets the size of the lineStroke as specified by the int passed in.
	 * 
	 * There are no OS/Hardware dependencies and no variances. There is no need
	 * for any security constraints and no references to external
	 * specifications.
	 * 
	 * @param size
	 *            specifies the width of the line for basicStroke how the line
	 *            ends, hoe lines join together.
	 * @see #getLineStroke
	 */
	public void setLineStroke(int size) {
		/*FAULT::size = 3;*/
		lineStroke = new BasicStroke(size);
	}

	/**
	 * Returns a boolean reflecting the state of the flag, dragged.
	 * 
	 * @return boolean, dragged
	 * @see #setDragged
	 */
	public boolean getDragged() {
		//dragged = !dragged;
		return dragged;
	}

	/**
	 * Returns the Stroke of the line.
	 * 
	 * @return Stroke
	 * @see #setLineStroke
	 */
	public Stroke getLineStroke() {
		//lineStroke = new BasicStroke(1);
		return lineStroke;
	}
	/** This is a mutator to set the state of variable 'dragged' to true or false.
	 * @param a boolean true to set dragged, false if not
	 * @see #getDragged
	 */
	
	public void setDragged(boolean a){
		dragged = a;
	}

}