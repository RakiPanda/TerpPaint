import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import java.awt.geom.*;

/** This is a class for the curve tool which is located at row 6 column 2 of the drawing icon.
 * It allows us to use the mouse to draw a line into a curve shape and we can also drag the mouse
 * and move it around, changing the curve. The curve can also be any color chosen from the bottom of the TerpPaint.
 * Also we can specify the thickness of the curve.
 *
 * It should work with all operating systems and hardware.
 * There are no variances and no security constraints.
 *
 * @author TerpPaint
 * @version 2.0
 */
public class curveTool implements ourTool {

    /** This is used to store the starting location of the curve line for the x coordinate.
     */
    private int startX;

    /** This is used to store the starting location of the curve line for the y coordinate.
     */
    private int	 startY;
    /** This is a boolean used to indicate whether the mouse has been dragged or not.
     * It will return true if it has been dragged and false otherwise.
     */
    private boolean dragged = false;
    /** This is used to store the current Image that we have. We are going to have a back up image
     * so we don't destroy the current image. We are just modifying the back up image.
     */
    private BufferedImage curImage;

    /** This is used to store the back up image that we want and then, we are going to modify
     * this as we continue the drawing of the curve.
     */
    private BufferedImage backupImage;

    /** This is used as the line attributes for the Graphics2D. It is used to describe
     * the width of the pen.
     */
    private Stroke curveStroke;

    /** This is used to store the x coordinate of the place where the mouse is clicked.
     * It is used when a curved line is going to be drawn.
     */
    private int firstX;

    /** This is used to store the y coordinate of the place where the mouse is clicked.
     * It is used when a curved line is going to be drawn.
     */
    private int firstY;

    /** This is used as a backup for the integer firstX during state 2. During state 3,
     * it will store as an x coordinate of the place where the mouse is clicked.
     */
    private int secondX;

    /** This is used as a backup for the integer firstY during state 2. During state 3,
     * it will store as an y coordinate of the place where the mouse is clicked.
     */
    private int	   secondY;

    /** This is used to store the end of the line for the x coordinate.
     */
    private int lineEndX;

    /** This is used to store the end of the line for the y coordinate.
     */
    private int	 lineEndY;
    /** This is used as a flag, showing which state are you at.
     * The state are that you have already drawn a line, you already
     * make a line into a curve or you have nothing at the beginning.
     */
   public int clicks = 1;
    /** This is a 2D graphic which is used to draw the line.
     */
    private Graphics2D g2D;


    /** Creates a curveTool and initializes the curveStroke to 1.
     * It takes in no parameters or null arguments.  It does not return anything.
     * There are no algorithms of any kind and no variances and OS dependencies.
     * There should not be any exceptions or security constraints.
     */
    public curveTool() {
	super();
	curveStroke = new BasicStroke(1);
	/*FAULT::curveStroke = new BasicStroke(2);*/
    }

    /** Allows the user to click on the cavas using the curveTool. According to the
     * state in which it is in, for state 1, it wants to begin to draw a new line.
     * For state 2, a line is already drawn and so, a curve is going to be drawn from
     * the existing line when a mouse is clicked. For state 3, it will change the angle
     * of the curve. And after that, the curved line cannot be moved anymore. A new line
     * will be created when the mouse is clicked and dragged.
     * @param mevt a MouseEvent object which is carried out when the mouse is clicked. It will
     * give a new x and y coordinate and with the start and end coordinate, this will create
     * a new curved line.
     * @param theCanvas a main_canvas object which is the area where the graphic is drawn.
     */
    public void clickAction(MouseEvent mevt, main_canvas theCanvas) {
	if (clicks == 1) {
	    dragged = false;
	    startX = theCanvas.canvasScale(mevt.getX());
	    startY = theCanvas.canvasScale(mevt.getY());
	    backupImage = theCanvas.getBufferedImage();
	    curImage = theCanvas.getBufferedImage();
	    g2D = curImage.createGraphics();
	    theCanvas.setBufferedImage(curImage);
	    curImage.flush();

	} else if (clicks == 2) {
	    firstX = theCanvas.canvasScale(mevt.getX());
	    firstY = theCanvas.canvasScale(mevt.getY());
	    secondX = firstX;
	    secondY = firstY;
	    g2D.drawImage(backupImage,null,0,0);
	    drawCurve(mevt,g2D,theCanvas);
	    theCanvas.repaint();

	} else if (clicks == 3) {
	    secondX = theCanvas.canvasScale(mevt.getX());
	    secondY = theCanvas.canvasScale(mevt.getY());
	    g2D.drawImage(backupImage,null,0,0);
	    drawCurve(mevt,g2D,theCanvas);
	    theCanvas.repaint();
	    backupImage.flush();
	    /*FAULT::startX = startX+1;*/
	

	}
    }


    /** This function returns the value for startY.
     * @return int representing startY
     */
    public int getStartY(){
    	/*FAULT::startY = startY+1;*/
    	return startY;}
    /** This function returns the value for startX.
     * @return int representing startX
     */
    public int getStartX(){
    	/*FAULT::startX = startX +1;*/
    	return startX;}
    /** This function returns the value for firstX.
     * @return int representing firstX
     */
    public int getfirstX(){
    	/*FAULT::firstX = firstX+1;*/
    	return firstX;}
    /** This function returns the value for firstY.
     * @return int representing firstY
     */
    public int getfirstY(){
    	/*FAULT::firstY= firstY+1;*/
    	return firstY;}
    /** This function returns the value for secondX.
     * @return int representing secondX
     */
    public int getSecondX(){
    	/*FAULT::secondX = secondX+1;*/
    	return secondX;}
    /** This function returns the value for secondY.
     * @return int representing secondY
     */
    public int getSecondY(){
    	/*FAULT::secondY = secondY+1;*/
    	return secondY;}
    /** This function returns the current state of the flag dragged. If the mouse
     * was clicked on and dragged, then it returns true. Otherwise, returns false.
     * @return boolean
     */
    public boolean getDragged(){
    	/*FAULT::dragged = !dragged;*/
    	return dragged;}
    /** Allows the user to drag the mouse using the curveTool. According to the state you
     * are in, for state 1, when the mouse a dragged, it will draw a straight line. For state 2,
     * the line will be curved and the curved line will be moving around. For state 3, the curved
     * line will move little bit from the existing shape. And after that, the curved line cannot
     * be moved anymore. A new line will be created when the mouse is clicked and dragged.
     * @param mevt a MouseEvent object which is carried out when the mouse is dragged. It will
     * give a new x and y coordinate and with the start and end coordinate, this will create
     * a new curved line. Note: this action can only be invoked once the user clicks on the
     * cavas, and clickAction is called.
     * @param theCanvas a main_canvas object which is the area where the graphic is drawn.
     */
    public void dragAction(MouseEvent mevt, main_canvas theCanvas) {
	dragged = true;
	if (clicks == 1) {
	    g2D.drawImage(backupImage,null,0,0);
	    drawLine(mevt,g2D,theCanvas);
	    theCanvas.repaint();
	} else if (clicks == 2) {
	    firstX = theCanvas.canvasScale(mevt.getX());
	    firstY = theCanvas.canvasScale(mevt.getY());
	    secondX = firstX;
	    /*FAULT::secondX = firstX+1;*/
	    secondY = firstY;
	    g2D.drawImage(backupImage,null,0,0);
	    drawCurve(mevt,g2D,theCanvas);
	    theCanvas.repaint();
	} else if (clicks == 3) {
	    secondX = theCanvas.canvasScale(mevt.getX());
	    secondY = theCanvas.canvasScale(mevt.getY());
	    g2D.drawImage(backupImage,null,0,0);
	    drawCurve(mevt,g2D,theCanvas);
	    theCanvas.repaint();
	}
    }

    /** Allows the user to release the mouse using curveTool. According to the state you
     * are in, for state 1, the line is drawn. Then it turns from state 1 to state 2. In state 2,
     * it draws the curve line and turns from state 2 to state 3. Then, in state 3, it also draws
     * the curved line but then it turns from state 3 back to state 1. So, we cannot modify the
     * existing curve line.
     * @param mevt a MouseEvent object which is carried out when the mouse is released. It will
     * give a new x and y coordinate and with the start and end coordinate, this will create
     * a new curved line. Note: this action can only be invoked once the user clicks on the
     * cavas, and clickAction is called.
     * @param theCanvas a main_canvas object which is the area where the graphic is drawn.
     */
    public void mouseReleaseAction(MouseEvent mevt, main_canvas theCanvas) {
	System.out.println("clicks: "+clicks);
	if (dragged) {
	    dragged = false;
	    if (clicks == 1) {
		g2D.drawImage(backupImage,null,0,0);
		lineEndX = theCanvas.canvasScale(mevt.getX());
		lineEndY = theCanvas.canvasScale(mevt.getY());
		drawLine(mevt,g2D,theCanvas);
		theCanvas.repaint();
		clicks = 2;
	    } else if (clicks == 2) {
		g2D.drawImage(backupImage,null,0,0);
		firstX = theCanvas.canvasScale(mevt.getX());
		firstY = theCanvas.canvasScale(mevt.getY());
		secondX = firstX;
		secondY = firstY;
		drawCurve(mevt,g2D,theCanvas);
		theCanvas.repaint();
		clicks = 3;
	    } else if (clicks == 3) {
		g2D.drawImage(backupImage,null,0,0);
		secondX = theCanvas.canvasScale(mevt.getX());
		secondY = theCanvas.canvasScale(mevt.getY());
		drawCurve(mevt,g2D,theCanvas);
		theCanvas.repaint();
		clicks = 1;
		/*FAULT::clicks = 2;*/
	    }
	}

    }

    /** Draws a curve once the mouse has been clicked more than once. Also, it
     * will change the color you have stored in your left mouse button if you
     * click the left button and it will change the color you have stored in your
     * right mouse button if you click on your right button. It sets the width of
     * the line and then, it starts to draw the curved line.
     * @param mevt a MouseEvent object which is used to determine which mouse button is clicked.
     * @param g2D a Graphic object which is used to set the color and draw the line.
     * @param theCanvas a main_canvas object which is the area where the graphic is drawn.
     */
    public void drawCurve(MouseEvent mevt, Graphics2D g2D, main_canvas theCanvas) {
	if (SwingUtilities.isLeftMouseButton(mevt)) {
	    g2D.setColor(theCanvas.left_color);
	} else {
	    g2D.setColor(theCanvas.right_color);
	    /*FAULT::g2D.setColor(theCanvas.left_color);*/
	}
	g2D.setStroke(curveStroke);
	g2D.draw(new CubicCurve2D.Double(startX,startY,firstX,firstY,secondX,secondY,lineEndX,lineEndY));
    }

    /** Draws one straight line once the user clicks on the canvas.
     * Also, it will change the color you have stored in your left mouse
     * button if you click the left button and it will change the color you have
     * stored in your right mouse button if you click on your right button. It sets
     * the width of the line and then, it starts to draw the straight line.
     * @param mevt a MouseEvent object which is used to determine which mouse button is clicked.
     * @param g2D a Graphic object which is used to set the color and draw the line.
     * @param theCanvas a main_canvas object which is the area where the graphic is drawn.
     */
    public void drawLine(MouseEvent mevt, Graphics2D g2D, main_canvas theCanvas) {
	int endX, endY;

	endX = theCanvas.canvasScale(mevt.getX());
	endY = theCanvas.canvasScale(mevt.getY());
	if (SwingUtilities.isLeftMouseButton(mevt)) {
	    g2D.setColor(theCanvas.left_color);

	} else {
	       //System.out.println("this is the cloorr:"+theCanvas.right_color);
	    g2D.setColor(theCanvas.right_color);
	    /*FAULT::g2D.setColor(Color.BLACK);*/
	}
	//System.out.println("this is the cloorr:"+theCanvas.right_color);
	g2D.setStroke(curveStroke);
      // System.out.println("this is the cloorr:"+theCanvas.right_color);
	g2D.draw(new Line2D.Double(startX,startY,endX,endY));
       // System.out.println("this is the cloorr:"+theCanvas.right_color);
    }


    /** Sets the width of the curveStroke as specified by the int, size, passed in.
     * @param size an int value.
     * @see #getCurveStroke
     */
    public void setCurveStroke(int size) { 
    	curveStroke = new BasicStroke(size);
    	/*FAULT::curveStroke = new BasicStroke(size+1);*/
    	}



    /** Returns the curveStroke of the current curveTool.
     * @return Stroke which was set by size passed in
     * @see #setCurveStroke
     */
    public Stroke getCurveStroke(){
    	/*FAULT::curveStroke = new BasicStroke(5);*/
    	return curveStroke;
    }

     /** Sets the width of the curved line and will draw the the curved line
      * if the state is not state 1. Then, it will set the state to state 1 again after it draws.
      * @param theCanvas a main_canvas object which is the area where the graphic is drawn.
      */
    public void deSelect(main_canvas theCanvas) {
	if (clicks != 1) {
		g2D.setStroke(curveStroke);
		g2D.draw(new CubicCurve2D.Double(startX,startY,firstX,firstY,secondX,secondY,lineEndX,lineEndY));
			clicks = 1;
			/*FAULT::clicks = 2;*/
			theCanvas.repaint();
		}
    }

    /** Sets the number of clicks, between 1 and 3.
     * @param i an integer that represents the number of clicks
     * @see #getClicks
     */
    public void setClicks(int i){
    	clicks = i;
    }
    
    /** Returns the number of clicks, between 1 and 3.
     * @return int representing clicks
     * @see #setClicks
     */
    public int getClicks(){
    	/*FAULT::clicks = clicks+1;*/
	return clicks;
    }

     /** Returns the Color of the Graphics2D, g2d.
     * @return Color representing the g2d
     */
   // public Color getG2dColor(){
     //	  System.out.println("here is the color:"+g2D.getColor());
       // return g2D.getColor();
    //}
    public Color getG2dColor(){
    	/*FAULT::if(true) return Color.black;*/
    	return g2D.getColor();
    	}
}

/*
public class lineTool implements ourTool {
    private int startX, startY;
    private boolean dragged = false;
    private BufferedImage curImage, backupImage;
    private Stroke lineStroke;

    public lineTool() {
	super();
	lineStroke = new BasicStroke(1.f);
    }

    public void clickAction(MouseEvent mevt,main_canvas theCanvas) {
	dragged = false;
	startX = theCanvas.canvasScale(mevt.getX());
	startY = theCanvas.canvasScale(mevt.getY());
	System.out.println("Line");
	backupImage = theCanvas.getBufferedImage();
	curImage = theCanvas.getBufferedImage();
	theCanvas.setBufferedImage(curImage);
    }

    public void dragAction(MouseEvent mevt,main_canvas theCanvas) {
	dragged = true;
	curImage.createGraphics().drawImage(backupImage,null,0,0);
	drawLine(mevt,curImage.createGraphics(),theCanvas);
	theCanvas.repaint();
    }

    public void mouseReleaseAction(MouseEvent mevt,main_canvas theCanvas) {
	if (dragged) {
	    curImage.createGraphics().drawImage(backupImage,null,0,0);
	    drawLine(mevt,curImage.createGraphics(),theCanvas);
	    theCanvas.repaint();
	    dragged = false;
	}
    }

    public void drawLine(MouseEvent mevt, Graphics2D g2D, main_canvas theCanvas) {
	int endX, endY;

	endX = theCanvas.canvasScale(mevt.getX());
	endY = theCanvas.canvasScale(mevt.getY());
	if (SwingUtilities.isLeftMouseButton(mevt)) {
	    g2D.setColor(theCanvas.left_color);
	} else {
	    g2D.setColor(theCanvas.right_color);
	}
	g2D.setStroke(lineStroke);
	g2D.draw(new Line2D.Double(startX,startY,endX,endY));
    }

   public void setLineStroke(int size) {
       lineStroke = new BasicStroke(size);
   }

}*/
