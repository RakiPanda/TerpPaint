/**
 * letterTool.java
 * edited on Feb. 10, 2003
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import java.awt.geom.*;

 /** The letterTool class represents letter tools. All texts are implemented by
  * mouse events with x-coordinates and	 y-coordinates in parent frame.
  * The class letterTool includes methods for clicking, dragging, and releasing the mouse.
  *
  * There are no OS/Hardware dependencies and no variances.  There is no need for any
  * security constraints and no references to external specifications.
  *
  * @author TerpPaint
  * @version 2.0
  */
    public class letterTool implements ourTool {
    /**
     * A main frame that generates the letter tool frame.
     */

    public java.awt.Frame parentFrame;


    /** Holds the size of the current Font.
     */
    public static int currentFont = 0;
    /** Holds the state of the font's boldness.
     */
    public static boolean boldness = false;
    /** Holds the state of the font's underlineness.
     */
    public static boolean underlineness = false;
    /** Holds the state of the font's italicness.
     */
    public static boolean italicness = false;
    /** Holds the state of the font's current size.
     */
    public static int currentSize = 0;

    /** The dialog is closed by OK.
     */
    public boolean ok_action;

    /** Creates a letterTool and initializes a newly created java.awt.Frame object
     * so that it represents a copy of the argument java.awt.Frame
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     *
     * @param parent frame to initialize the letter tool
     */
    public letterTool(java.awt.Frame parent) {
	super();
	parentFrame = parent;
	ok_action = false;
    }

    /** Allows the user to click the mouse using the letterTool.
     * The parameters passed in are a MouseEvent, which holds the coordinates of
     * where the mouse was clicked, and the main_canvas, where it will be drawn.
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     *
     * @param mevt contains information about the mouse event including x-coordinates and y-coordinates
     * @param theCanvas main canvas
     */
    public void clickAction(MouseEvent mevt, main_canvas theCanvas) {
	int x = mevt.getX();
	int y = mevt.getY();

	Text editor = new Text(parentFrame, true, x, y );
//	  currentFont = editor.getSelectedIndex();

	editor.setVisible(true);
	if (editor.ok_action)
	    ok_action = true;
    }

    /** Allows the user to drag the mouse, using the letter Tool. Since this is
     * the letterTool and dragging is unnecessary, this method was not implemented.
     * However, it was created because it implements OurTool.
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     *
     * @param mevt object contains information about the mouse event including x-coordinates and y-coordinates
     * @param theCanvas main canvas
     */
    public void dragAction(MouseEvent mevt, main_canvas theCanvas) {

    }

    /** Allows the user to release the mouse, using the letter Tool. Since this is
     * the letterTool and releasing the mouse is unnecessary, this method was not
     * implemented. However, it was created because it implements OurTool.
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     *
     * @param mevt object contains information about the mouse event including x-coordinates and y-coordinates
     * @param theCanvas main canvas
     */
    public void mouseReleaseAction(MouseEvent mevt, main_canvas theCanvas) {

    }

}
