
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

// 1 = border left_color, 2 = fill with right_color, 3 = solid left_color

/**
 * This is a class for the ellipse tool which is located at row 8 column 1 of
 * the drawing icon. It allows us to draw an ellipse in 3 ways, a left color
 * border with white background, a left color border with left background or a
 * left color border with right color background. It also allows us to draw the
 * ellipse horizontally or vertically according to the mouse button clicked.
 * 
 * It should work with all operating systems and hardware. There are no
 * variances and no security constraints.
 * 
 * @author TerpPaint
 * @version 2.0
 */
public class ellipseTool implements ourTool {

	/**
	 * Stores the starting x coordinate of the location where the mouse is
	 * clicked.
	 */
	public int startX;

	/**
	 * Stores the starting y coordinate of the location where the mouse is
	 * clicked.
	 */
	public int startY;

	/**
	 * Boolean which indicates whether the mouse has been dragged or not, once
	 * clicked.
	 */
	private boolean dragged = false;

	/**
	 * Represents which of the 3 ways the ellipse will be drawn: a left color
	 * border with white background, left color border with left background or a
	 * left color border with right color background.
	 */
	private int fillType = 1;

	/**
	 * Stores the current BufferedImage of the main_canvas. We are going to have
	 * a back up image so we don't destroy the current image. We are just
	 * modifying the back up image.
	 */
	private BufferedImage curImage;

	/**
	 * This is used to store the back up image that we want and then, we are
	 * going to modify this as we continue the drawing of the ellipse.
	 */
	private BufferedImage backupImage;
	/** This int holds the value of the Previous X coordinate. */
	int prevX = -1;
	/** This int holds the value of the Previous Y coordinate. */
	int prevY = -1;

	/**
	 * Allows the user to click on the cavas using the ellipseTool. It will
	 * start to draw an ellipse at the place where the mouse is clicked. It has
	 * 2 parameters which is a MouseEvent object and a main_canvas object. The
	 * MouseEvent object will determine which button is clicked and the
	 * main_canvas object is the place where the ellipse is drawn.
	 * 
	 * @param mevt
	 *            a MouseEvent object which is carried out when the mouse is
	 *            clicked.
	 * @param theCanvas
	 *            a main_canvas object which is the area where the graphic is
	 *            drawn.
	 */
	public void clickAction(MouseEvent mevt, main_canvas theCanvas) {
		dragged = false;
		startX = theCanvas.canvasScale(mevt.getX()); /*FAULT:: startX = 5; */
		startY = theCanvas.canvasScale(mevt.getY());
		//System.out.println("Ellipse--left mouse");
		backupImage = theCanvas.getBufferedImage();
		curImage = theCanvas.getBufferedImage();
		theCanvas.setBufferedImage(curImage);
		curImage.flush();
		backupImage.flush();
	}

	/**
	 * Allows the user to draw an ellipse and drag it to the preferred size. It
	 * will set the boolean dragged to true. It will draw horizontally or
	 * veritically according to which of the mouse button is clicked
	 * respectively. It has 2 parameters which is a MouseEvent object and a
	 * main_canvas object. The MouseEvent object will determine which button is
	 * clicked and the main_canvas object is the place where the ellipse is
	 * drawn.
	 * 
	 * @param mevt
	 *            This MouseEvent object determines which of the button is
	 *            clicked and will draw the ellipse horizontally or vertically.
	 * @param theCanvas
	 *            This is the area where the ellipse is drawn.
	 */
	public void dragAction(MouseEvent mevt, main_canvas theCanvas) {
		dragged = true; /*FAULT:: dragged = false; */
		curImage.createGraphics().drawImage(backupImage, null, 0, 0);
		drawEllipse(mevt, curImage.createGraphics(), theCanvas);
		//Istvan Phase 5
		BasicStroke boxStroke = new BasicStroke(1.f, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_BEVEL, 8.f, new float[] { 6.f, 6.f }, 0.f);
		BasicStroke boxStrokeW = new BasicStroke(1.f, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_BEVEL, 8.f, new float[] { 6.f, 6.f }, 6.f);
		int x, y, width, height;
		x = startX;
		y = startY;
		int endX = mevt.getX();
		int endY = mevt.getY();
		if (endX < startX) {
			x = endX;
		}
		if (endY < startY) {
			y = endY;
		}
		width = Math.abs(startX - endX);
		height = Math.abs(startY - endY);
		Graphics2D g2D = curImage.createGraphics();
		g2D.setColor(Color.black);
		g2D.setStroke(boxStroke);
		g2D.draw(new Rectangle(x, y, width, height));
		g2D.setColor(Color.white);
		g2D.setStroke(boxStrokeW);
		g2D.draw(new Rectangle(x, y, width, height));
		//End Istvan phase 5
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

	/**
	 * Returns a boolean reflecting the current state of the private data member
	 * dragged.
	 * 
	 * @return a boolean, dragged.
	 */
	public boolean getDragged() {
		return dragged; /*FAULT:: return !dragged; */
	}

	/**
	 * Allows the user to release the mouse, once it has been clicked by the
	 * user, to stop enlarging or shrinking the ellipse. It has 2 parameters
	 * which is a MouseEvent object and a main_canvas object. The MouseEvent
	 * object will determine which button is clicked and the main_canvas object
	 * is the place where the ellipse is drawn. Sets the boolean dragged to
	 * false.
	 * 
	 * @param mevt
	 *            This MouseEvent object determines which of the button is
	 *            clicked and will draw the ellipse horizontally or vertically.
	 * @param theCanvas
	 *            This is the area where the ellipse is drawn.
	 */
	public void mouseReleaseAction(MouseEvent mevt, main_canvas theCanvas) {

		if (dragged) {
			curImage.createGraphics().drawImage(backupImage, null, 0, 0);
			drawEllipse(mevt, curImage.createGraphics(), theCanvas);

			theCanvas.repaint();
			dragged = false; /*FAULT:: dragged = true; */
			//System.out.println("Ellipse: "+x+","+y+","+width+","+height);
		}
	}

	/**
	 * Sets the fillType of ellipse, specified by the int passed in: a left
	 * color border with white background, a left color border with left
	 * background or a left color border with right color background. It has an
	 * integer parameter which is used to set to the wanted type.
	 * 
	 * @param theFillType
	 *            an int value.
	 * @see #getFillType
	 */
	public void setFillType(int theFillType) {
		fillType = theFillType; /*FAULT:: fillType = 10; */
	}

	/**
	 * Returns the fillType of the ellipseTool as an int.
	 * 
	 * @return int representing the fill type
	 * @see #setFillType
	 */
	public int getFillType() {
		return fillType; /*FAULT:: return 10; */
	}

	/**
	 * Performs the drawing of the ellipse. This method is called in either
	 * dragAction or mouseReleasedAction. It will draw the ellipse according to
	 * which button the mouse clicked and also, according to which 3 types of
	 * ellipses it wants. It has 3 parameters which a MouseEvent object used to
	 * determine which mouse button is clicked, a Graphics2D object to draw the
	 * ellipse and a main_canvas object which is the area where the ellipse is
	 * going to be drawn.
	 * 
	 * @param mevt
	 *            This MouseEvent object determines which of the button is
	 *            clicked and will draw the ellipse horizontally or vertically.
	 * @param g2D
	 *            a Graphic objct to draw the ellipse.
	 * @param theCanvas
	 *            This is the area where the ellipse is drawn.
	 */
	public void drawEllipse(MouseEvent mevt, Graphics2D g2D,
			main_canvas theCanvas) {
		int endX, endY, x, y, width, height;

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
		if (fillType == 1) {
			if (SwingUtilities.isLeftMouseButton(mevt)) {
				g2D.setColor(theCanvas.left_color); /*FAULT:: g2D.setColor(theCanvas.right_color); */
			} else {
				g2D.setColor(theCanvas.right_color);
			}
			g2D.draw(new Ellipse2D.Double(x, y, width, height));
		} else if (fillType == 2) {
			if (SwingUtilities.isLeftMouseButton(mevt)) {
				g2D.setColor(theCanvas.right_color);
			} else {
				g2D.setColor(theCanvas.left_color);
			}
			g2D.fill(new Ellipse2D.Double(x, y, width, height));
			if (SwingUtilities.isLeftMouseButton(mevt)) {
				g2D.setColor(theCanvas.left_color);
			} else {
				g2D.setColor(theCanvas.right_color);
			}
			g2D.draw(new Ellipse2D.Double(x, y, width, height));
		} else {
			if (SwingUtilities.isLeftMouseButton(mevt)) {
				g2D.setColor(theCanvas.left_color);
			} else {
				g2D.setColor(theCanvas.right_color);
			}
			g2D.fill(new Ellipse2D.Double(x, y, width, height));
		}
	}
	//public Color getG2dColor(){ return g2D.getColor();}
}