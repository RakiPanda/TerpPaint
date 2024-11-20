/**
 * medicine.java
 * edited on Feb. 10, 2003
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import java.awt.geom.*;

/** The medicineTool class handles the image with the mouse events and color.
 * The class medicineTool includes methods for clicking, dragging, and releasing
 * the mouse, and for obtaining the color of the image.
 *
 * There are no OS/Hardware dependencies and no variances.  There is no need for any
 * security constraints and no references to external specifications.
 *
 * @author TerpPaint
 * @version 2.0
 */
public class medicineTool implements ourTool {
    /** The Color object that represents the image with the specified color.
     */
    Color myColor;

    /** Creates a medinceTool object.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     *
     */
    public medicineTool() {
	super();
    }

    /** Presents the mouse event when a mouse button is pressed on a component
     * without moving the mouse cursor.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     *
     * @param mevt contains information about the mouse event with x-coordinates and y-coordinates.
     * @param theCanvas contains contents of the bufferedImage with its x-coordinate and y-coordinate.
     */
    public void clickAction(MouseEvent mevt, main_canvas theCanvas) {
	int x, y;
	//int [] colorArray = new int[3];
	BufferedImage theImage;
	//Raster myRaster;

	theImage = theCanvas.getBufferedImage();
	//myRaster = theImage.getData();
	x = theCanvas.canvasScale(mevt.getX());
	y = theCanvas.canvasScale(mevt.getY());
	myColor = new Color(theImage.getRGB(x,y));



	System.out.println("in click action");
	System.out.println("x:"+x+" y:"+y);
	theImage.flush();
    }

    /** Allows the user to drag the mouse, using the medicineTool. This method
     * was not implemented because it is unnecessary for a medicineTool to be dragged.
     * However, it was created because medicineTool implements OurTool.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     * @param mevt contains information about the mouse event.
     * @param theCanvas contains contents of the main canvas image.
     */
    public void dragAction(MouseEvent mevt, main_canvas theCanvas) {

    }

    /** Allows the user to release the mouse, using the medicineTool. This method
     * was not implemented because it is unnecessary for a medicineTool to be released,
     * since it is only needed to be clicked once. However, it was created because
     * medicineTool implements OurTool.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     * @param mevt contains information of the mouse events.
     * @param theCanvas provides contents of the main canvas image.
     */
    public void mouseReleaseAction(MouseEvent mevt, main_canvas theCanvas) {

    }

    /** Returns the Color of medicineTool.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     * @return the color field.
     */
    public Color getMyColor() { return myColor; }

}
