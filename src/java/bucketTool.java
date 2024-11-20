/*
 * bucketTool.java
 *
 * Created on February 10, 2003, 8:24 PM
 */



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import java.awt.geom.*;

/** This class is for the bucket option which is coloring the canvas
 * with a specific color.
 * The state should be enable.
 * OS is windows.
 * Implementation variances are not allowed.
 * There are no security constraints.
 * There are no external specifications.
 *
 *@author TerpPaint
 *@version 2.0
 */
public class bucketTool implements ourTool {

    /** java.awt.image.*
     */
    /** imgWidth and imgHeight
     */
    /** These variable are ints that stores in the width and height.
    */

    int imgWidth;

    /** These variable are ints that stores in the width and height.
     */
    int imgHeight;

    /** Creates a bucketTool with no parameters.
     * There are no null values.
     * There are no return values.
     * The constructor initialize the variable.
     * OS is windows.
     * Implementation variances are not allowed.
     * There are no exception.
     * There are no security constraints.
     *
     *
     */
    public bucketTool() {
	super();
    }

    /** Controls what the user clicks as specified by the MouseEvent and
     * main_canvas passed in. If the user clicks on the left button, the left
     * color on the pallete will be used to color with. Otherwise, if the
     * user clicks on the right button, the right color on the pallete will be
     * used.
     * The state transition depends on the user.
     * The arguments are MouseEvent and main_canvas.
     * There are no return values.
     * The method controls the click action of the user.
     * OS is windows.
     * Implementation variances is not allowed.
     * There is no exception.
     * There is no security constraints.
     * @param mevt this is a MouseEvent
     * @param theCanvas this is a main_canvas variable
     */
    public void clickAction(MouseEvent mevt, main_canvas theCanvas) {

	System.out.println("The Bucket tool has been clicked");
	int x, y, newRGBValue, boundRGB;

	x = theCanvas.canvasScale(mevt.getX());
	y = theCanvas.canvasScale(mevt.getY());
	//theImage = theCanvas.getBufferedImage();

// trying to debug bucket right and left click colors.
	int fill;
	if( mevt.getButton() == 1 )
	    fill = theCanvas.left_color.getRGB();
	else if( mevt.getButton() == 3 )
	    fill = theCanvas.right_color.getRGB();
	else  // middle-click isn't supposed to do anything.
	    return;


	Color newC = new Color(fill);
	System.err.println("The new color is " +newC.getRed()+" "+newC.getGreen()+" "+newC.getBlue());
	Fill f = new Fill(theCanvas.getBufferedImage(),x,y,fill);
	theCanvas.setBufferedImage(f.getBufferedImage());
	System.gc();
	/*if (SwingUtilities.isLeftMouseButton(mevt)) {
	    newRGBValue = theCanvas.left_color.getRGB();
	} else {
	    newRGBValue = theCanvas.right_color.getRGB();
	}
	boundRGB = theImage.getRGB(x,y);
	imgWidth = theImage.getWidth();
	imgHeight = theImage.getHeight();
	pour(theImage,boundRGB,newRGBValue,x,y);
	theCanvas.repaint();*/
    }

    /** Should allow the user to drag the bucket as specified by the MouseEvent
     * and main_canvas passed in. However, since it is the bucketTool, dragging
     * is unnecessary. The function was created because it implements OurTool.
     * The state transition depends on the user.
     * The arguments are MouseEvent and main_canvas.
     * There are no return values.
     * The method controls the click action of the user.
     * OS is windows.
     * Implementation variances is not allowed.
     * There is no exception.
     * There is no security constraints.
     * @param mevt this is a MouseEvent
     * @param theCanvas this is a main_canvas variable
     */

    public void dragAction(MouseEvent mevt, main_canvas theCanvas) {

    }

    /** Should allow the user to release the mouse on the bucket as specified
     * by the MouseEvent and main_canvas passed in. However, since it is the
     * bucketTool, releasing the mouse is unnecessary. The function was created
     * because it implements OurTool.
     * The state transition depends on the user.
     * The arguments are MouseEvent and main_canvas.
     * There are no return values.
     * The method controls the click action of the user.
     * OS is windows.
     * Implementation variances is not allowed.
     * There is no exception.
     * There is no security constraints.
     * @param mevt this is a MouseEvent
     * @param theCanvas this is a main_canvas variable
     */

    public void mouseReleaseAction(MouseEvent mevt, main_canvas theCanvas) {

    }

    /** Recursive function that fills the same color area surrounding (x,y).
     * Calls itself on its pixels to the north, south, east and west.
     * The state transition depends on the user.
     * The arguments are MouseEvent and main_canvas.
     * There are no return values.
     * OS is windows.
     * Implementation variances is not allowed.
     * There is no exception.
     * There is no security constraints.
     *
     * @param theImage BufferedImage
     * @param boundRGB int
     * @param newRGBValue int
     * @param x int representing the x coordinate
     * @param y int representing the y coordinate
     * @deprecated As of TerpPaint 4 pour is deprecated please use the Fill Class instead
     * @see Fill#Fill(BufferedImage, int, int, int)
     */
    public void pour(BufferedImage theImage,int boundRGB, int newRGBValue, int x, int y) {
	int curRGB;

	if ((x >= 0) && (x < imgWidth) && (y >= 0) && (y < imgHeight)) {
	    curRGB = theImage.getRGB(x,y);
	    if (curRGB == boundRGB) {
		theImage.setRGB(x,y,newRGBValue);
		pour(theImage,boundRGB,newRGBValue,x-1,y);
		pour(theImage,boundRGB,newRGBValue,x,y-1);
		pour(theImage,boundRGB,newRGBValue,x+1,y);
		pour(theImage,boundRGB,newRGBValue,x,y+1);
	    }
	}
    }
}
