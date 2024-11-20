/*
 * ScrollablePicture.java
 *
 * Created on April 6, 2002, 7:00 PM
 */

/**
 *
 * @author  Terp Paint
 * @version 2.0
 */
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

/** ScrollablePicture extends JLabel and uses the Scrollable interface.
 * ScrollablePicture the picture being scrolled on.
 * It is the full image, but only a portion will be shown on the display,
 * depending on the size of the cnavas.
 * There are currently no OS dependencies or variations.  There are no security
 * constraints and no external references.
 */
public class ScrollablePicture extends JLabel implements Scrollable {

    /** The size of scrolling increment
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     */
    public int maxUnitIncrement = 1;

    /** Constructor that initializes the image icon and
     * the maximum unit number of scrolling increment.
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     * @param i Image to be scrolled
     * @param m The amount of increment of scrolling unit
     */
    public ScrollablePicture(ImageIcon i, int m) {
	super(i);
	maxUnitIncrement = m; /*FAULT:: maxUnitIncrement = 2; */
    }

    /** Returns the default preferred size as determined by the layout manager.
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     * @return Dimension that described the preferred size
     */
    public Dimension getPreferredScrollableViewportSize() {
	return getPreferredSize(); /*FAULT:: return null; */
    }

    /** Returns the amount by which the picture can be scrolled in pixels, given direction.
     * Calculates scrollable distance including when the canvas cannot be scrolled the maxUnitIncrement due to current scroll position.
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     * @param visibleRect Rectangle which is the portion in display
     * @param orientation Integer that represents either horizontal or vertical
     * @param direction Integer that represents either positive or negative scroll
     * @return integer distance of scroll
     */
    public int getScrollableUnitIncrement(Rectangle visibleRect,
					  int orientation,
					  int direction) {
	//Get the current position.
	int currentPosition = 0;
	if (orientation == SwingConstants.HORIZONTAL)
	    currentPosition = visibleRect.x;
	else
	    currentPosition = visibleRect.y;

	//Return the number of pixels between currentPosition
	//and the nearest tick mark in the indicated direction.
	if (direction < 0) {
	    int newPosition = currentPosition -
			     (currentPosition / maxUnitIncrement) *
			      maxUnitIncrement;
	    return (newPosition == 0) ? maxUnitIncrement : newPosition;
	} else {
	    return ((currentPosition / maxUnitIncrement) + 1) * maxUnitIncrement - currentPosition; /*FAULT:: return 2; */
	}
    }

    /** Returns the width-maxUnitIncrement or height-maxUnitIncrement according to orientation.
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     * @param visibleRect Rectangle that represents either portion in the display
     * @param orientation Integer that represents either horizontal or vertical
     * @param direction Integer that represents either positive or negative scroll
     * @return width or height - maxUnitIncrement
     */
    public int getScrollableBlockIncrement(Rectangle visibleRect,
					   int orientation,
					   int direction) {
	if (orientation == SwingConstants.HORIZONTAL)
	    return visibleRect.width - maxUnitIncrement; /*FAULT:: return 5; */
	else //VERTICAL
	    return visibleRect.height - maxUnitIncrement;
    }

    /** Returns false any time this method is called.
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     * @return false
     */
    public boolean getScrollableTracksViewportWidth() {
	return false; /*FAULT: return true; */
    }

    /** Returns false any time this method is called.
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     * @return false
     */
    public boolean getScrollableTracksViewportHeight() {
	return false; /*FAULT: return true; */
    }

    /** Sets maxUnitIncrement to the paramater passed.
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     * @param pixels Integer to set maxUnitIncrement
     * @see #getMaxUnitIncrement
     */
    public void setMaxUnitIncrement(int pixels) {
	maxUnitIncrement = pixels; /*FAULT:: maxUnitIncrement = 5; */
    }
    /** Returns the value of maxUnitIncrement.
     * There are no OS/Hardware dependencies and no variances.	There is no need for any
     * security constraints and no references to external specifications.
     * @return maxUnitIncrement The size of maxUnitIncrement value
     * @see #setMaxUnitIncrement
     */
    public int getMaxUnitIncrement(){
	return maxUnitIncrement; /*FAULT:: return 5; */
    }
}
