import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

/** A pencilTool implements the operations performed by a pencil.
 *
 * It holds the fields int prevX and prevY, BufferedImage pencilImage and
 * Graphics2D g2D.
 * The variables prevX and prevY retain the values of x and y at the end of the
 * methods clickAction and DragAction.
 *
 * It should work with all operating systems and hardware.
 * There are no variances and no security constraints.
 *
 * @author TerpPaint
 * @version 2.0
 */
public class pencilTool implements ourTool {
    /** holds a BufferedImage.
     */
    BufferedImage pencilImage;
    /** holds a Graphics2D.
     */
    Graphics2D g2D;
    /** used to initialize the start of the x and y values.
     */
    int prevX;

    /** used to initialize the start of the x and y values.
     */
    int prevY;

    /** Creates a pencilTool object.
     * It takes in no parameters or null arguments.  It does not return anything.
     * There are no algorithms of any kind and no variances and OS dependencies.
     * There should not be any exceptions or security constraints.
     */
    public pencilTool() {
	super();
    }

    /** Allows the user to click the mouse, using the pencilTool.
     * Sets pencilImage and the x and y values to those passed in by MouseEvent.
     * If the LeftMouseButton is used, then a pixel is set to pencilImage with the
     * specified RGB value of the left color.
     * Otherwise, it is set with the right color.
     * The prevX and prevY are initialized to the x and y values respectively.
     * @param mevt holds a MouseEvent to call getX() and getY() to set the
     * x and y values
     * @param theCanvas holds a main_canvas to retrieve the mevt's x and y values.
     */
    public void clickAction(MouseEvent mevt, main_canvas theCanvas) {
	int x, y;

	pencilImage = theCanvas.getBufferedImage();
	g2D = pencilImage.createGraphics();
	theCanvas.setBufferedImage(pencilImage);
	x = theCanvas.canvasScale(mevt.getX());
	y = theCanvas.canvasScale(mevt.getY());
	if (SwingUtilities.isLeftMouseButton(mevt)) {
	    pencilImage.setRGB(x,y,theCanvas.left_color.getRGB());
	} else {
	    pencilImage.setRGB(x,y,theCanvas.right_color.getRGB());
	}
	prevX = x; /*FAULT:: prevX = 77; */
	prevY = y;
	pencilImage.flush();
	
    }

    /** Returns the value of the data member prevX.
     * @return int
     */
    public int getPrevX(){
    	return prevX; /*FAULT:: return 100; */
    }

    /** Returns the value of the data member prevY.
     * @return int prevY
     */
    public int getPrevY(){
    	return prevY; /*FAULT:: return 100; */
    }

    /** Allows the user to drag the mouse, using the pencil tool once the mouse has been clicked.
     * If the leftMouseButton is used, the g2D's color is set to the left color,
     * otherwise it is set to the right color.
     * setStroke sets the the Stroke for the Graphic2D context and the Stroke object
     * passed in is to be used to stroke a Shape during the rendering process.
     * It resets the prevX and prevY to reflect the x and y values.
     * @param mevt holds a MouseEvent to call getX() and getY() to set the x and y values.
     * @param theCanvas holds a main_canvas to retrieve the mevt's x and y values and
     * left and right colors.
     */
    public void dragAction(MouseEvent mevt, main_canvas theCanvas) {
	int x, y;
	x = theCanvas.canvasScale(mevt.getX());
	y = theCanvas.canvasScale(mevt.getY());
	if (SwingUtilities.isLeftMouseButton(mevt)) {
	    //pencilImage.setRGB(x,y,theCanvas.left_color.getRGB());
	    g2D.setColor(theCanvas.left_color);
	    g2D.setStroke(new BasicStroke(1));
	    g2D.drawLine(prevX,prevY,x,y);
	} else {
	    //pencilImage.setRGB(x,y,theCanvas.right_color.getRGB());
	    g2D.setColor(theCanvas.right_color);
	    g2D.setStroke(new BasicStroke(1));
	    g2D.drawLine(prevX,prevY,x,y);
	}
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
	System.out.println(newprevX + " " + newprevY + " " 
					   + x + " " + y + " " 
					   + newx + " " + newy + " "
					   + mevt.getX() + " " + mevt.getY());
	if (newprevX <= newx)
		if (newprevY <= newy)
			//theCanvas.repaint(0, 0, 1, 1);
			theCanvas.repaint(newprevX, newprevY, newx-newprevX+(2*zoom), newy-newprevY+(2*zoom));
		else {
			//theCanvas.repaint(0, 0, 1, 1);
			theCanvas.repaint(newprevX, newy, newx-newprevX+(2*zoom), newprevY-newy+(2*zoom));
		}
	else if (newprevY <= newy)
		//theCanvas.repaint(0, 0, 1, 1);
			theCanvas.repaint(newx, newprevY, newprevX-newx+(2*zoom), newy-newprevY+(2*zoom));
		else {
			//theCanvas.repaint(0, 0, 1, 1);
			theCanvas.repaint(newx, newy, newprevX-newx+(2*zoom), newprevY-newy+(2*zoom));
		}
	prevX = x; /*FAULT:: prevX = 200; */
	prevY = y;
	//theCanvas.repaint();
    }

    /** Allows the user to release the mouse, using the pencilTool, once the mouse
     * has been clicked on and clickAction has been called.
     * If the leftMouseButton is used, the g2D's color is set to the left color,
     * otherwise it is set to the right color.
     * setStroke sets the the Stroke for the Graphic2D context and the Stroke object
     * passed in is to be used to stroke a Shape during the rendering process.
     * It resets the prevX and prevY to reflect the x and y values.
     * NOTE: it seems to be a direct copy of the method dragAction except that the
     * prevX and prevY values are not reset.
     * @param mevt mevt holds a MouseEvent to call getX() and getY() to set the x and y values.
     * @param theCanvas holds a main_canvas to retrieve the mevt's x and y values and left
     * and right colors.
     */
    public void mouseReleaseAction(MouseEvent mevt, main_canvas theCanvas) {
	int x, y;
	x = theCanvas.canvasScale(mevt.getX());
	y = theCanvas.canvasScale(mevt.getY());
	if (SwingUtilities.isLeftMouseButton(mevt)) {
	    //pencilImage.setRGB(x,y,theCanvas.left_color.getRGB());
		g2D.setColor(theCanvas.left_color); /*FAULT:: g2D.setColor(theCanvas.right_color); */
	    g2D.setStroke(new BasicStroke(1));
	    g2D.drawLine(prevX,prevY,x,y);
	} else {
	    //pencilImage.setRGB(x,y,theCanvas.right_color.getRGB());
	    g2D.setColor(theCanvas.right_color);
	    g2D.setStroke(new BasicStroke(1));
	    g2D.drawLine(prevX,prevY,x,y);
	}
	theCanvas.repaint();
    }
    /** Returns the Stroke of g2d.
     * @return Stroke
     */
    public Stroke getStroke(){
    	return g2D.getStroke(); /*FAULT:: return null; */
    }
}
