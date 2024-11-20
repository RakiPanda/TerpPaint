
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.geom.*;
import java.awt.image.*;

/**
 * A polygonTool implements the operations performed by a polygon.
 * 
 * It holds the fields int lastX, lastY, curX, curY, Graphics2D polyG2D,
 * BufferedImage polyImage, backupImage, boolean dragged, Polygon, curPolygon,
 * private int fillType. The variable dragged is set to true only when the
 * method dragAction is called and is set to false otherwise. The fillType is
 * assigned as follows: 1=set the color of the border. 2=set the color of the
 * border and the fill. otherwise only set the color of the fill.
 * 
 * It should work with all operating systems and hardware. There are no
 * variances and no security constraints.
 * 
 * @author TerpPaint
 * @version 2.0
 */
public class polygonTool implements ourTool {
	/**
	 * used in clickAction and mouseReleaseAction.
	 */
	int lastX;

	/**
	 * used in clickAction and mouseReleaseAction.
	 */
	int lastY;

	/**
	 * used in clickAction and mouseReleaseAction.
	 */
	int curX;

	/**
	 * used in clickAction and mouseReleaseAction.
	 */
	int curY;

	/**
	 * used to create a polygon.
	 */
	Graphics2D polyG2D;

	/**
	 * a BufferedImage with width set to 200 and height set to 200.
	 */
	BufferedImage polyImage = new BufferedImage(200, 200,
			BufferedImage.TYPE_INT_RGB);

	/**
	 * a BufferedImage with width set to 200 and height set to 200.
	 */
	BufferedImage backupImage = new BufferedImage(200, 200,
			BufferedImage.TYPE_INT_RGB);

	/**
	 * initialized to false, but set to true when dragAction is called.
	 */
	public boolean dragged = false;

	/**
	 * used within methods clickAction, mouseReleaseAction and deSelect.
	 */
	Polygon curPolygon;

	/**
	 * Initialized to 1 as default. The following reflects modifications made
	 * when changing fillType in setFillType: 1-set the color of the border.
	 * 2-set the color of the border and the fill. Otherwise set the color of
	 * the fill.
	 */
	private int fillType = 1;

	/**
	 * Creates a polygonTool object. It takes in no parameters or null
	 * arguments. It does not return anything. There are no algorithms of any
	 * kind and no variances and OS dependencies. There should not be any
	 * exceptions or security constraints.
	 */
	polygonTool() {
		super();
	}

	/**
	 * allows the user to click on the canvas, using the polygonTool. If the
	 * evt's click count is less than 2 and dragAction has not been called, then
	 * lastX and lastY are set and these are used to addPoints within the
	 * curPolygon. The image of the polygon is backed up into backupImage.
	 * 
	 * Otherwise, if the dragAction has been called, then curX and curY are set
	 * and that coordinate is added to curPolygon. If the leftMouseButton is
	 * clicked, then the color is set to the left color. Otherwise, it is set to
	 * the right color. If the fill type is 1, then the border is drawn. If the
	 * fill type is 2 and the leftMouseButton is used, then the border is set to
	 * the left color and the fill is set to the right color. Otherwise, the
	 * border is set to the right color and the fill is set to the left color.
	 * If the fill is not 1 or 2, then only the fill color is set.
	 * 
	 * @param evt
	 *            holds a MouseEvent to set the lastX and lastY fields.
	 * @param theCanvas
	 *            holds a main_canvas
	 */
	public void clickAction(MouseEvent evt, main_canvas theCanvas) {
		//System.out.println("click");
		if (evt.getClickCount() < 2) {
			//System.out.println("click<2");
			if (!(dragged)) {

				lastX = theCanvas.canvasScale(evt.getX());
				lastY = theCanvas.canvasScale(evt.getY());
				curPolygon = new Polygon();
				curPolygon.addPoint(lastX, lastY);
				polyImage = theCanvas.getBufferedImage();
				polyG2D = polyImage.createGraphics();
				theCanvas.setBufferedImage(polyImage);
			}
			backupImage = theCanvas.getCopy(polyImage);

		} else {//System.out.println("click>2");
			if (dragged) {
				curX = theCanvas.canvasScale(evt.getX());
				curY = theCanvas.canvasScale(evt.getY());
				curPolygon.addPoint(curX, curY);
				dragged = false; /*FAULT:: dragged = true; */
				if (SwingUtilities.isLeftMouseButton(evt)) {
					polyG2D.setColor(theCanvas.left_color);
				} else {
					polyG2D.setColor(theCanvas.right_color);
				}
				if (fillType == 1) {
					polyG2D.draw(curPolygon);
					// theCanvas.setBufferedImage(polyImage);
				} else if (fillType == 2) {
					if (SwingUtilities.isLeftMouseButton(evt)) {
						polyG2D.setColor(theCanvas.right_color);
						polyG2D.fill(curPolygon);
						polyG2D.setColor(theCanvas.left_color);
						polyG2D.draw(curPolygon);
						//theCanvas.setBufferedImage(polyImage);
					} else {
						polyG2D.setColor(theCanvas.left_color);
						polyG2D.fill(curPolygon);
						polyG2D.setColor(theCanvas.right_color);
						polyG2D.draw(curPolygon);
						//theCanvas.setBufferedImage(polyImage);
					}

				} else {
					polyG2D.fill(curPolygon);
					//theCanvas.setBufferedImage(polyImage);
				}
			}
			theCanvas.repaint();	
		}
		polyImage.flush();
	}

	/**
	 * Allows the user to drag the mouse on the canvas, using the polygonTool.
	 * It sets the field dragged to true. If the LeftMouseButton is used, it
	 * sets the color to the left color. Otherwise it is set to the right color.
	 * 
	 * @param evt
	 *            holds a MouseEvent to determine which side was clicked.
	 * @param theCanvas
	 *            holds a main_canvas to set the colors.
	 */
	public void dragAction(MouseEvent evt, main_canvas theCanvas) {
		//System.out.println("dragger");
		dragged = true; /*FAULT:: dragged = false; */
		polyG2D.drawImage(backupImage, null, 0, 0);
		if (SwingUtilities.isLeftMouseButton(evt)) {
			polyG2D.setColor(theCanvas.left_color);
		} else {
			polyG2D.setColor(theCanvas.right_color);
		}
		polyG2D.drawLine(lastX, lastY, evt.getX(), evt.getY());
		theCanvas.repaint();
	}

	/**
	 * Allows the user to release the mouse, and the method dragAction was
	 * called (and therefore dragged was set to true), it sets the curX and curY
	 * and adds a point to curPolygon with those coordinates. If the
	 * leftMouseButton is used, it sets the color to the left. Otherwise, it
	 * sets the color to the right. It resets the fields lastX and lastY to
	 * those of curX and curY.
	 * 
	 * @param evt
	 *            holds a MouseEvent to determine which side was clicked.
	 * @param theCanvas
	 *            determines which side to color.
	 */
	public void mouseReleaseAction(MouseEvent evt, main_canvas theCanvas) {System.out.println("release");
		//polyG2D.drawImage(backupImage, null, 0, 0);
		if (dragged) {
			curX = theCanvas.canvasScale(evt.getX());
			curY = theCanvas.canvasScale(evt.getY());
			curPolygon.addPoint(curX, curY);
			if (SwingUtilities.isLeftMouseButton(evt)) {
				polyG2D.setColor(theCanvas.left_color);
			} else {
				polyG2D.setColor(theCanvas.right_color);
			}
			polyG2D.draw(new Line2D.Double(lastX, lastY, curX, curY));
			theCanvas.repaint();
			lastX = curX; /*FAULT:: lastX = 5; */
			lastY = curY;
		}
	}

	/**
	 * Sets fillType to the int passed in.
	 * 
	 * @param theFillType
	 *            holds an integer corresponding to the values given above.
	 * @see #getFillType
	 */
	public void setFillType(int theFillType) {
		fillType = theFillType; /*FAULT:: fillType = 3; */
	}

	/**
	 * Returns the fill type, which should range from 1-3.
	 * 
	 * @return an int
	 * @see #setFillType
	 */
	public int getFillType() {
		return fillType; /*FAULT:: return 3; */
	}

	/**
	 * Allows for the deselecting of the polygon. Can only deselect if the
	 * dragAction was called (and now dragged is set to true). Therefore: If the
	 * fillType is 1, then it draws the polygon with the border. If the fillType
	 * is 2, and the leftmouseButton is used, the border is used with the left
	 * color and the fill is used with the right color. Otherwise, the fill is
	 * used as the left color and the border as the right. If the fillType is
	 * not 1 or 2, then the fill color is set. Dragged is set to false.
	 * 
	 * @param theCanvas
	 *            holds a main_canvas used to set the colors.
	 */
	public void deSelect(main_canvas theCanvas) {
		System.out.println("deseletct");
		if (dragged == true) {
			if (fillType == 1) {
				polyG2D.draw(curPolygon);
				// theCanvas.setBufferedImage(polyImage);
			} else if (fillType == 2) {
				if (polyG2D.getColor() == theCanvas.left_color) {
					polyG2D.setColor(theCanvas.right_color);
					polyG2D.fill(curPolygon);
					polyG2D.setColor(theCanvas.left_color);
					polyG2D.draw(curPolygon);
					//theCanvas.setBufferedImage(polyImage);
				} else {
					polyG2D.setColor(theCanvas.left_color);
					polyG2D.fill(curPolygon);
					polyG2D.setColor(theCanvas.right_color);
					polyG2D.draw(curPolygon);
				}
			} else {
				polyG2D.fill(curPolygon);
			}
			theCanvas.repaint();
			dragged = false; /*FAULT:: dragged = true; */
		}
	}

	/**
	 * Returns the current state of the flag dragged.
	 * 
	 * @return a boolean corresponding to the flag dragged
	 */
	public boolean getDragged() {
		return dragged; /*FAULT:: return !dragged; */
	}

	/**
	 * Returns a Color corresponding to the private Graphics2D.
	 * 
	 * @return Color corresponding to that of the Graphics2D
	 */
	public Color getG2dColor() {
		return polyG2D.getColor(); /*FAULT:: return null; */
	}
}