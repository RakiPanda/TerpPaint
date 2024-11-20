/** ImageUtilities.java
 * Edited on Feb. 9, 2003
 *
 */

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

/** The ImageUtilities class provides the buffered images for the graphics.
 * All images are offered by an image object with components such as width, height, and color information.
 * The class ImageUtilities includes methods for waiting and obtaining the buffered images.
 *
 * There are no OS/Hardware dependencies and no variances.  There is no need for any
 * security constraints and no references to external specifications.
 *
 * @author Terp Paint
 * @version 2.0
 */
public class ImageUtilities{

    /** Constructs an ImageUtilities object. This is basically for JUnit testing.
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     */
    public ImageUtilities(){
	super();
    }

    /** Returns BufferedImage object that we look for.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     *
     * @param imageFile the string used to get an image to be passed for waiting for the image.
     * @param c contains the information of the image.
     * @return the bufferedImage for generating the graphics.
     */
    public static BufferedImage getBufferedImage(String imageFile, Component c){
	Image image = c.getToolkit().getImage(imageFile);
	waitForImage(image,c);
	BufferedImage bufferedImage = new BufferedImage(image.getWidth(c),image.getHeight(c),BufferedImage.TYPE_INT_RGB);
	Graphics2D g2d = bufferedImage.createGraphics();
	g2d.drawImage(image,0,0,c);
	return (bufferedImage);
    }

    /** Waits for the image.
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     *
     * @param image contains information about the image with its size.
     * @param c the components of the image.
     * @return true if the tracker waits for the image; false when any error occurs.
     */
    public static boolean waitForImage(Image image, Component c){
	MediaTracker tracker = new MediaTracker(c);
	tracker.addImage(image,0);
	try{
		tracker.waitForAll();
	}
	catch(InterruptedException e){}
	return(!tracker.isErrorAny());
    }
}
