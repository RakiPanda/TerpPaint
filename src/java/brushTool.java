/*
 * brushTool.java
 *
 * Created on February 10, 2003, 7:17 PM
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import java.awt.geom.*;

/**
 * This class is the Brush Tool. It controls what the brush does. It
 * controls the color, the position and the shape. The object state should be
 * enable. OS is windows. NO implementation variance is allowed. There are no
 * security constaints. There are no external secification.
 * 
 * @author TerpPaint
 * @version 2.0
 */

public class brushTool implements ourTool {
	/***************************************************************************
	 * java.awt.image.
	 */
	/**
	 * DOT1
	 */
	/**
	 * this is a int variable.
	 */

	public final int DOT1 = 1;

	/***************************************************************************
	 * java.awt.image.
	 */
	/**
	 * DOT2
	 */
	/**
	 * this is a int variable.
	 */

	public final int DOT2 = 2;

	/***************************************************************************
	 * java.awt.image.
	 */
	/**
	 * DOT3
	 */
	/**
	 * this is a int variable.
	 */

	public final int DOT3 = 3;

	/***************************************************************************
	 * java.awt.image.
	 */
	/**
	 * SQUARE1
	 */
	/**
	 * this is a int variable.
	 */

	public final int SQUARE1 = 4;

	/***************************************************************************
	 * java.awt.image.
	 */
	/**
	 * SQUARE2
	 */
	/**
	 * this is a int variable.
	 */

	public final int SQUARE2 = 5;

	/***************************************************************************
	 * java.awt.image.
	 */
	/**
	 * SQUARE3
	 */
	/**
	 * this is a int variable.
	 */

	public final int SQUARE3 = 6;

	/***************************************************************************
	 * java.awt.image.
	 */
	/**
	 * RIGHT1
	 */
	/**
	 * this is a int variable.
	 */

	public final int RIGHT1 = 7;

	/***************************************************************************
	 * java.awt.image.
	 */
	/**
	 * RIGHT2
	 */
	/**
	 * this is a int variable.
	 */
	public final int RIGHT2 = 8;

	/***************************************************************************
	 * java.awt.image.
	 */
	/**
	 * RIGHT3
	 */
	/**
	 * this is a int variable.
	 */
	public final int RIGHT3 = 9;

	/***************************************************************************
	 * java.awt.image.
	 */
	/**
	 * LEFT1
	 */
	/**
	 * this is a int variable.
	 */
	public final int LEFT1 = 10;

	/***************************************************************************
	 * java.awt.image.
	 */
	/**
	 * LEFT2
	 */
	/**
	 * this is a int variable.
	 */

	public final int LEFT2 = 11;

	/***************************************************************************
	 * java.awt.image.
	 */
	/**
	 * LEFT3
	 */
	/**
	 * this is a int variable.
	 */

	public final int LEFT3 = 12;

	/***************************************************************************
	 * java.awt.image.
	 */
	/**
	 * brushImage
	 */
	/**
	 * this is a BufferedImage variable.
	 */

	BufferedImage brushImage;

	/**
	 * Stores the type of the brush (1-12).
	 */
	public int brushType;

	/***************************************************************************
	 * java.awt.image.
	 */
	/**
	 * g2D
	 */
	/**
	 * this is a Graphics2D variable.
	 */

	Graphics2D g2D;

	/**
	 * Stores the previous x.
	 */
	private int prevX;

	/**
	 * Stores the previous y.
	 */
	private int prevY;

	/**
	 * Creates a brushTool with the brushType initialized to DOT1 by calling
	 * super() and setBrushType() to initialize brushType. OS is windows.
	 * Implementation variances are not allowed. There are no exceptions There
	 * are no security constaints.
	 */

	public brushTool() {
		super();
		brushType = DOT2;
		/*FAULT::brushType = DOT3;*/
		setBrushType(brushType);
	}

	/**
	 * This method controls the colors when you click left or right. It also controls the
	 * position of the brush and the color the user wants. The state transition
	 * varies and depends on the user. The argument values are MouseEvent,
	 * main_canvas. There are no pointers so NULL is not assigned. There is no
	 * return value. Depending on what the user click certain events are called.
	 * OS is windows. Implementation variances is not allowed. There are no
	 * exceptions. There are no security constraints.
	 * 
	 * @param mevt
	 *            This is a MouseEvent variable.
	 * @param theCanvas
	 *            this is a main_canvas variable
	 */

	public void clickAction(MouseEvent mevt, main_canvas theCanvas) {

		int x, y;

		x = theCanvas.canvasScale(mevt.getX());
		y = theCanvas.canvasScale(mevt.getY());
		brushImage = theCanvas.getBufferedImage();
		g2D = brushImage.createGraphics();
		theCanvas.setBufferedImage(brushImage);

		if (SwingUtilities.isLeftMouseButton(mevt)) {
			System.out.println("IN THE LEFT");
			System.out.println("being set to:" + theCanvas.left_color);
			g2D.setColor(theCanvas.left_color);
		} else {
			System.out.println("IN THE RIGHT");
			System.out.println("being set to:" + theCanvas.right_color);
			g2D.setColor(theCanvas.right_color);
			/*FAULT::g2D.setColor(theCanvas.left_color);*/

		}
		switch (brushType) {
		case DOT1:
			if (SwingUtilities.isLeftMouseButton(mevt)) {
				System.out.println("this is the left color rgb"
						+ theCanvas.left_color.getRGB());
				brushImage.setRGB(x, y, theCanvas.left_color.getRGB());
			} else {
				System.out.println("this is the right color rgb"
						+ theCanvas.right_color.getRGB());
				brushImage.setRGB(x, y, theCanvas.right_color.getRGB());
			}
			break;
		case LEFT1:
		case LEFT2:
		case LEFT3:
		case RIGHT1:
		case RIGHT2:
		case RIGHT3:
			g2D.draw(drawBrush(x, y));
			break;
		default:
			g2D.fill(drawBrush(x, y));
		}
		prevX = x;
		prevY = y;
		brushImage.flush();
	}

	/**
	 * This method controls the position or location of the brush as it is moved by the
	 * user. The state transition varies and depends on the user. The argument
	 * values are MouseEvent, main_canvas. There are no pointers so NULL is not
	 * assigned. There is no return value. Depending on what the user click
	 * certain events are called. OS is windows. Implementation variances is not
	 * allowed. There are no exceptions. There are no security constraints.
	 * 
	 * @param mevt
	 *            This is a MouseEvent variable.
	 * @param theCanvas
	 *            this is a main_canvas variable
	 */

	public void dragAction(MouseEvent mevt, main_canvas theCanvas) {

		int x, y;
		int rise, run;
		double tempY;

		x = theCanvas.canvasScale(mevt.getX());
		y = theCanvas.canvasScale(mevt.getY());
		//g2D = brushImage.createGraphics();
		if (SwingUtilities.isLeftMouseButton(mevt)) {
			g2D.setColor(theCanvas.left_color);
			/*FAULT::g2D.setColor(theCanvas.right_color);*/
		} else {
			g2D.setColor(theCanvas.right_color); 
		}
		/*
		 * switch (brushType) { case DOT1: case LEFT1: case LEFT2: case LEFT3:
		 * case RIGHT1: case RIGHT2: case RIGHT3: g2D.draw(drawBrush(x,y));
		 * break; default: g2D.fill(drawBrush(x,y)); }
		 */
		drawStuff(mevt, x, y, theCanvas);
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
			if (newprevX < 5*zoom) newprevX = 5*zoom;
			if (newprevY <= newy) {
				if (newprevY < 5*zoom) newprevY = 5*zoom;
				//theCanvas.repaint(0, 0, 1, 1);
				theCanvas.repaint(newprevX-5*zoom, newprevY-5*zoom, newx-newprevX+15*zoom, newy-newprevY+15*zoom);
			}
			else {
				if (newy < 5*zoom) newy = 5*zoom;
				//theCanvas.repaint(0, 0, 1, 1);
				theCanvas.repaint(newprevX-5*zoom, newy-5*zoom, newx-newprevX+15*zoom, newprevY-newy+15*zoom);
			}
		}
		else if (newprevY <= newy) {
			if (newprevY < 5*zoom) newprevY = 5*zoom;
			if (newx < 5*zoom) newx = 5*zoom;
			//theCanvas.repaint(0, 0, 1, 1);
			theCanvas.repaint(newx-5*zoom, newprevY-5*zoom, newprevX-newx+15*zoom, newy-newprevY+15*zoom);
		}
			else {
				if (newy < 5*zoom) newy = 5*zoom;
				if (newx < 5*zoom) newx = 5*zoom;
				//theCanvas.repaint(0, 0, 1, 1);
				theCanvas.repaint(newx-5*zoom, newy-5*zoom, newprevX-newx+15*zoom, newprevY-newy+15*zoom);
			}
		//theCanvas.repaint();
		prevX = x;
		prevY = y;
	}

	/**
	 * Depending on where the user releases the mouse, this method stores the position
	 * or location of the brush. The state transition varies and depends on the
	 * user. The argument values are MouseEvent, main_canvas. There are no
	 * pointers so NULL is not assigned. There is no return value. Depending on
	 * what the user click certain events are called. OS is windows.
	 * Implementation variances is not allowed. There are no exceptions. There
	 * are no security constraints.
	 * 
	 * @param mevt
	 *            This is a MouseEvent variable.
	 * @param theCanvas
	 *            this is a main_canvas variable
	 */

	public void mouseReleaseAction(MouseEvent mevt, main_canvas theCanvas) {

		int x, y;

		x = theCanvas.canvasScale(mevt.getX());
		y = theCanvas.canvasScale(mevt.getY());

		//g2D = brushImage.createGraphics();
		if (SwingUtilities.isLeftMouseButton(mevt)) {
			g2D.setColor(theCanvas.left_color);
			/*FAULT::g2D.setColor(theCanvas.right_color);*/
		} else {

			//try
			//{
			g2D.setColor(theCanvas.right_color);
			//}
			//catch (Exception e)
			//{
			//  e.printStackTrace();
			//}

		}
		/*
		 * switch (brushType) { case DOT1: case LEFT1: case LEFT2: case LEFT3:
		 * case RIGHT1: case RIGHT2: case RIGHT3: g2D.draw(drawBrush(x,y));
		 * break; default: g2D.fill(drawBrush(x,y)); }
		 */

		drawStuff(mevt, x, y, theCanvas);
		theCanvas.repaint();
	}

	/**
	 * This method controls all the other methods in order to draw what the user
	 * intends to do. The state transition varies and depends on the user. The
	 * argument values are MouseEvent, main_canvas. The argument values are
	 * ints. There are no pointers so NULL is not assigned. There is no return
	 * value. Depending on what the user click certain events are called. OS is
	 * windows. Implementation variances is not allowed. There are no
	 * exceptions. There are no security constraints.
	 * 
	 * @param mevt
	 *            This is a MouseEvent variable.
	 * @param theCanvas
	 *            this is a main_canvas variable
	 * @param x
	 *            this is a int variable
	 * @param y
	 *            this is a int variable
	 */

	public void drawStuff(MouseEvent mevt, int x, int y, main_canvas theCanvas) {
		// Algorithm provided by Po-Han Lin
		// http://www.edepot.com

		boolean yLonger = false;
		int incrementVal, endVal;

		int shortLen = y - prevY;
		int longLen = x - prevX;
		if (Math.abs(shortLen) > Math.abs(longLen)) {
			int swap = shortLen;
			shortLen = longLen;
			longLen = swap;
			yLonger = true;
		}

		endVal = longLen;
		if (longLen < 0) {
			incrementVal = -1;
			longLen = -longLen;
		} else
			incrementVal = 1;

		double decInc;
		if (longLen == 0)
			decInc = (double) shortLen;
		else
			decInc = ((double) shortLen / (double) longLen);
		double j = 0.0;
		if (yLonger) {
			for (int i = 0; i != endVal; i += incrementVal) {
				switch (brushType) {
				/*case DOT1:
					if (SwingUtilities.isLeftMouseButton(mevt)) {
						brushImage.setRGB(prevX + (int) j, prevY + i,
								theCanvas.left_color.getRGB());
					} else {
						brushImage.setRGB(prevX + (int) j, prevY + i,
								theCanvas.right_color.getRGB());
					}
					break;*/
				case LEFT1:
				case LEFT2:
				case LEFT3:
				case RIGHT1:
				case RIGHT2:
				case RIGHT3:
					g2D.draw(drawBrush(prevX + (int) j, prevY + i));
					break;
				default:
					g2D.fill(drawBrush(prevX + (int) j, prevY + i));
				}
				j += decInc;
			}
		} else {
			for (int i = 0; i != endVal; i += incrementVal) {
				switch (brushType) {
				/*case DOT1:
					if (SwingUtilities.isLeftMouseButton(mevt)) {
						brushImage.setRGB(prevX + i, prevY + (int) j,
								theCanvas.left_color.getRGB());
					} else {
						brushImage.setRGB(prevX + i, prevY + (int) j,
								theCanvas.right_color.getRGB());
					}
					break;*/
				case LEFT1:
				case LEFT2:
				case LEFT3:
				case RIGHT1:
				case RIGHT2:
				case RIGHT3:
					g2D.draw(drawBrush(prevX + i, prevY + (int) j));
					break;
				default:
					g2D.fill(drawBrush(prevX + i, prevY + (int) j));
				}
				j += decInc;
			}
		}
	}

	/**
	 * This method sets the brushtype as specified by the int, type, that is passed in.
	 * There are no pointers so NULL is not assigned. There is no return value.
	 * Depending on what the user click certain events are called. OS is
	 * windows. Implementation variances is not allowed. There are no
	 * exceptions. There are no security constraints.
	 * 
	 * @param type  This is a int variable that represents the brush type.
	 * @see #getBrushType
	 */

	public void setBrushType(int type) {
		brushType = type;
		/*FAULT::brushType = 2;*/
	}

	/**
	 * This method returns the brushType as an int. OS is windows. Implementation
	 * variances is not allowed. There are no exceptions. There are no security
	 * constraints.
	 * 
	 * @see #setBrushType
	 * @return int referring to brushType
	 */
	public int getBrushType() {
		/*FAULT::if(true) return 2;*/
		return brushType;
	}

	/**
	 * Draws the brush strokes on the canvas according to the width and height
	 * of the x and y ints passed in. The brush strokes are drawn according to
	 * the brush shape selected by the user. For example, if the first dot was
	 * selected, the stroke of the brush would look like a dot, and be of the
	 * smallest possible width. by the user, the brush strokes are drawn The
	 * state transition varies and depends on the user. The argument values are
	 * ints. There are no pointers so NULL is not assigned. There is no return
	 * value. Depending on what the user click certain events are called. OS is
	 * windows. Implementation variances is not allowed. There are no
	 * exceptions. There are no security constraints.
	 * 
	 * @param x
	 *            This is a int variable.
	 * @param y
	 *            this is a int variable.
	 * @return returns a Shape variable
	 */

	public Shape drawBrush(int x, int y) {
		Shape brushShape;

		switch (brushType) {
		case DOT1 : brushShape = new Ellipse2D.Double(x,y,1,1);
			  break;
		/*case DOT1:
			brushShape = new Ellipse2D.Double(x - 1, y -1, 2, 2);
			break;*/
		case DOT2:
			brushShape = new Ellipse2D.Double(x - 2, y - 2, 4, 4);
			break;
		case DOT3:
			brushShape = new Ellipse2D.Double(x - 4, y - 4, 8, 8);
			break;
		case SQUARE1:
			brushShape = new Rectangle(x - 1, y - 1, 2, 2);
			break;
		case SQUARE2:
			brushShape = new Rectangle(x - 2, y - 2, 5, 5);
			break;
		case SQUARE3:
			brushShape = new Rectangle(x - 4, y - 4, 8, 8);
			/*FAULT::brushShape = new Rectangle(x - 4, y - 4, 8, 7);*/
			break;
		case RIGHT1:
			brushShape = new Line2D.Double(x, y + 1, x + 1, y);
			break;
		case RIGHT2:
			brushShape = new Line2D.Double(x - 2, y + 2, x + 2, y - 2);
			break;
		case RIGHT3:
			brushShape = new Line2D.Double(x - 4, y + 4, x + 4, y - 4);
			break;
		case LEFT1:
			brushShape = new Line2D.Double(x + 1, y + 1, x, y);
			break;
		case LEFT2:
			brushShape = new Line2D.Double(x - 2, y - 2, x + 2, y + 2);
			break;
		case LEFT3:
			brushShape = new Line2D.Double(x - 4, y - 4, x + 4, y + 4);
			break;
		default:
			brushShape = new Rectangle(x - 5, y - 5, x + 5, y + 5);
		}
		return brushShape;
	}

	/**
	 * This method returns the Color of g2d.
	 * 
	 * @return Color representing the g2d
	 */
	public Color getG2dColor() {
		/*FAULT::if(true) return Color.blue;*/
		return g2D.getColor();
	}
}

