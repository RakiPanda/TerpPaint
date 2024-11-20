import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.SwingUtilities;

/** sprayTool is basically a tool which acts like a spraycan to spray onto the
 * image the selected color.  There are three sizes: small, medium, large.  These
 * sizes indicate how big of the diameter you want the spray to take up on the
 * image.

 * This tool works in all operating systems.
 * There are no variances known.
 * Also there are no security constraints known.
 * No references to external specifications.
 *
 * @author TerpPaint
 * @version 2.0
 */
public class sprayTool implements ourTool {
    /** This integer is initially set at 8.  This means the default setting for the
     * spray tool is the smallest diameter possible.
     */
    int sprayType = 8;
    /** This is a <I>BufferedImage</I>.	 Whatever is sprayed using this tool, it is then
     * buffered into the <I>BufferedImage</I>.
     */
    BufferedImage sprayImage;
    /** This is of class type Color.  The variable takes in which color will be sprayed
     * forth from the spray tool.
     *
     */
    Color theColor;

    /** Creates a sprayTool object.
     * It takes in no parameters or null arguments.  It does not return anything.
     * There are no algorithms of any kind and no variances and OS dependencies.
     * There should not be any exceptions or security constraints.
     */
    public sprayTool() {
	super();
    }

    /** Allows the user to click on the canvas, using the spray tool.
     * The MouseEvent holds the coordinates where the mouse was clicked, to begin
     * spraying.
     * Once the mouse is clicked, there is an algorithm which calculates using a random
     * variable where exactly to "spray".
     * There are no OS dependencies or variances.
     * No exceptions and security constraints.
     * @param mevt This variable knows which button was clicked on the mouse.  Left or right.
     * @param theCanvas This is the main canvas where the mouse will click.
     */
    public void clickAction(MouseEvent mevt, main_canvas theCanvas) {
	int x, y;
	int angle, length;
	double vector;

	sprayImage = theCanvas.getBufferedImage();
	theCanvas.setBufferedImage(sprayImage);
	if (SwingUtilities.isLeftMouseButton(mevt)) {
	    theColor = theCanvas.left_color; /*FAULT:: theColor = theCanvas.right_color; */
	} else {
	    theColor = theCanvas.right_color;
	}
	Random r = new Random();
	for (int count = 0; count < sprayType*Math.round(Math.log(sprayType/2));count++) {
	    //x = (int)Math.round(Math.random() * sprayType + Math.round(theCanvas.canvasScale(mevt.getX()) - sprayType / 2));
	    //y = (int)Math.round(Math.random() * sprayType + Math.round(theCanvas.canvasScale(mevt.getY()) - sprayType / 2));

	    angle = r.nextInt(360);
	    length = r.nextInt((int)(sprayType/2));
	    vector = angle*Math.PI*2/360;
	    x = (int)(theCanvas.canvasScale(mevt.getX())+length*Math.sin(vector));
	    y = (int)(theCanvas.canvasScale(mevt.getY())+length*Math.cos(vector));
	    if ((x >=0) && (x < sprayImage.getWidth()) && (y >= 0) && (y < sprayImage.getHeight())) {
		sprayImage.setRGB(x,y,theColor.getRGB());
	    }
	}
	sprayImage.flush();
	theCanvas.repaint();
    }

    /** Allows the user to drag the spraying action. First, the mouse must click on the
     * canvas and call clickAction. Once the mouse is dragged, there is an algorithm
     * which calculates using a random variable where exactly to "spray".  It will
     * keep spraying each time the mouse moves (or being dragged).
     * There are no OS dependencies or variances.
     * No exceptions and security constraints.
     * @param mevt This variable knows which button was clicked on the mouse.  Left or right.
     * @param theCanvas This is the main canvas where the mouse will click.
     */
    public void dragAction(MouseEvent mevt, main_canvas theCanvas) {
	int x, y;
	int angle, length;
	double vector;

	if (SwingUtilities.isLeftMouseButton(mevt)) {
	    theColor = theCanvas.left_color; /*FAULT:: theColor = theCanvas.right_color; */
	} else {
	    theColor = theCanvas.right_color;
	}
	Random r = new Random();
	for (int count = 0; count < sprayType*Math.round(Math.log(sprayType/2));count++) {
	   // x = (int)Math.round(Math.random() * sprayType + Math.round(theCanvas.canvasScale(mevt.getX()) - sprayType / 2));
	    //y = (int)Math.round(Math.random() * sprayType + Math.round(theCanvas.canvasScale(mevt.getY()) - sprayType / 2));

	    angle = r.nextInt(360);
	    length = r.nextInt((int)(sprayType/2));
	    vector = angle*Math.PI*2/360;
	    x = (int)(theCanvas.canvasScale(mevt.getX())+length*Math.sin(vector));
	    y = (int)(theCanvas.canvasScale(mevt.getY())+length*Math.cos(vector));
	    if ((x >=0) && (x < sprayImage.getWidth()) && (y >= 0) && (y < sprayImage.getHeight())) {
		sprayImage.setRGB(x,y,theColor.getRGB());
	    }
	}
	
	int zoom = (int)theCanvas.getZoom();

	int viewX = (int)theCanvas.pictureScrollPane.getViewport().getViewPosition().getX();
	int viewY = (int)theCanvas.pictureScrollPane.getViewport().getViewPosition().getY();
	// ming 4.22
	if (zoom==0) zoom = 1;
	// ming 4.22 end

	int newx = ((mevt.getX()-viewX/zoom)*zoom);
	int newy = ((mevt.getY()-viewY/zoom)*zoom);
	//int newprevX = ((mevt.getX()-viewX)/zoom);
	//int newprevY = ((mevt.getY()-viewY)/zoom);
	/**System.out.println(prevX + " " + prevY + " " 
					   + x + " " + y + " " 
					   + newx + " " + newy);*/
	/**if (newprevX <= newx) {
		if (newprevX < 6) newprevX += 5;
		if (newprevY <= newy) {
			if (newprevY < 6) newprevY += 5;
			//theCanvas.repaint(0, 0, 1, 1);
			theCanvas.repaint(newprevX-5, newprevY-5, newx-newprevX+15, newy-newprevY+15);
		}
		else {
			if (newy < 6) newy += 5;
			//theCanvas.repaint(0, 0, 1, 1);
			theCanvas.repaint(newprevX-5, newy-5, newx-newprevX+15, newprevY-newy+15);
		}
	}
	else if (newprevY <= newy) {
		if (newprevY < 6) newprevY += 5;
		if (newx < 6) newx += 5;
		//theCanvas.repaint(0, 0, 1, 1);
		theCanvas.repaint(newx-5, newprevY-5, newprevX-newx+15, newy-newprevY+15);
	}
		else {
			if (newy < 6) newy += 5;
			if (newx < 6) newx += 5;
			//theCanvas.repaint(0, 0, 1, 1);
			theCanvas.repaint(newx-5, newy-5, newprevX-newx+15, newprevY-newy+15);
		}*/
	
	
	
	
	if (newx <= 10*zoom)
		if (newy <=10*zoom)
			theCanvas.repaint(0, 0, 20*zoom, 20*zoom);
	else { theCanvas.repaint(0, newy-10*zoom, 20*zoom, 20*zoom); }
	if (newy <= 10*zoom)
		theCanvas.repaint(newx-10*zoom, 0, 20*zoom, 20*zoom);
	else { theCanvas.repaint(newx-10*zoom, newy-10*zoom, 20*zoom, 20*zoom); }
    }

    /** Allows the user to release the mouse, and therefore stop spraying. The method
     * was unnecessary to implement, but was defined since sprayTool implements OurTool.
     * There are no OS dependencies or variances.
     * No exceptions and security constraints.
     * @param mevt This variable knows which button was clicked on the mouse.  Left or right.
     * @param theCanvas This is the main canvas where the mouse will click.
     */
    public void mouseReleaseAction(MouseEvent mevt, main_canvas theCanvas) {

    }

    /** Sets the diameter of the spray can to the int passed in.
     *
     * @param newSprayType This variable is the spray diameter you want.
     * @see #getSprayType
     */
    public void setSprayType(int newSprayType) {
	sprayType = newSprayType; /*FAULT:: sprayType = 5; */
    }

    /** Returns the spray type of the can as an int.
     * @return int representing the spray type
     * @see #setSprayType
     */
    public int getSprayType(){
    	return sprayType; /*FAULT:: return 5; */
    }
}
