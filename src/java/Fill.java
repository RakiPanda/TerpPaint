/*
 * Fill.java
 *
 * Created on March 20, 2002, 5:43 PM
 */

/**
 *
 * @author  administrator
 * @version 2.0
 */

import java.awt.image.*;
import java.awt.*;

/**
 * Fill class is used to fill a color to the whole area of the main canvas. It
 * also consists of a helper class which is coords class. This class will try
 * and fill the whole area of the pixel with the colors wanted. There are no
 * OS/Hardware dependencies and no variances. There are no security constraints.
 * There are no references to external specifications.
 */
public class Fill {

	/**
	 * BufferImage that is going to be colored.
	 */
	BufferedImage bim;

	/**
	 * The original color that you want to change.
	 */
	static int old;

	/**
	 * The new color to fill the pixel with the colors wanted.
	 */
	static int fill;

	/**
	 * The current position in the array.
	 */
	static int index;

	/**
	 * The column number of pixels. Negative value is not allowed.
	 */
	static int eastBound;

	/**
	 * The row number of pixels. Negative value is not allowed.
	 */
	static int southBound;

	/**
	 * A coordinate array of the pixels used to keep track which part of the
	 * BufferedImage bim is not colored.
	 */
	coords[] pixels;

	/**
	 * The coordinates that is used to move around in the coord array of pixels
	 * and keep track which of the coords is not colored.
	 */
	coords cur;

	/**
	 * Constructor for the fill class. It gets the color wanted and the
	 * BufferedImage. It creates the coordinate array of pixels to start
	 * coloring. It also calls the function fillIt to color the BufferedImage.
	 * It will throw an exception if there is any.
	 * 
	 * @param in
	 *            BufferedImage we want to color.
	 * @param x1
	 *            The starting index for x.
	 * @param y1
	 *            The starting index for y.
	 * @param filler
	 *            The color that it is supposed to be used.
	 */
	public Fill(BufferedImage in, int x1, int y1, int filler) {
		fill = filler;
		bim = in;
		index = 0;
		old = in.getRGB(x1, y1);

		eastBound = in.getWidth();
		southBound = in.getHeight();

		pixels = new coords[eastBound * southBound];
		pixels[index] = new coords(x1, y1);
		bim.setRGB(x1, y1, fill);
		bim.flush();
		in.flush();
		try {
			fillIt();
		} catch (Exception e) {
		}
	}

	/**
	 * Returns the BufferedImage.
	 * 
	 * @return bim BufferedImage.
	 */
	public BufferedImage getBufferedImage() {
		bim.flush();
		return bim;
	}

	/**
	 * fillIt does the coloring. It checks South, North, East and West of the
	 * main canvas image. Then, if it is not colored, it will be colored. The
	 * function also runs until index (the total area of the bufferedImage) is
	 * -1, which means that the BufferedImage is colored.
	 */
	public void fillIt() {
		while (index != -1) {
			cur = pixels[index];
			--index;
			//System.err.println("CHECKING SOUTH");
			if (cur.y + 1 < bim.getHeight()
					&& bim.getRGB(cur.x, cur.y + 1) == old) {
				bim.setRGB(cur.x, cur.y + 1, fill);
				++index;
				pixels[index] = new coords(cur.x, cur.y + 1);
			}
			//System.err.println("CHECKING NORTH");
			if (cur.y - 1 >= 0 && bim.getRGB(cur.x, cur.y - 1) == old) {
				bim.setRGB(cur.x, cur.y - 1, fill);
				++index;
				pixels[index] = new coords(cur.x, cur.y - 1);
			}
			//System.err.println("CHECKING EAST");
			if (cur.x + 1 < bim.getWidth()
					&& bim.getRGB(cur.x + 1, cur.y) == old) {
				bim.setRGB(cur.x + 1, cur.y, fill);
				++index;
				pixels[index] = new coords(cur.x + 1, cur.y);
			}
			//System.err.println("CHECKING WEST");
			if (cur.x - 1 >= 0 && bim.getRGB(cur.x - 1, cur.y) == old) {
				bim.setRGB(cur.x - 1, cur.y, fill);
				++index;
				pixels[index] = new coords(cur.x - 1, cur.y);
			}
			//end of while
		}
		bim.flush();
	}
}

/**
 * coords subclass included in Fill class. It has an x and y coordinate which is
 * used as points to keep track where it is.
 */

class coords {
	/**
	 * X coordinate.
	 */
	public int x;

	/**
	 * Y coordinate.
	 */
	public int y;

	/**
	 * Constructor for class coords. It takes in two integers which is for the x
	 * and y coordinates and sets the x and y coordiantes to the new
	 * coordinates.
	 * 
	 * @param x1
	 *            used to be the new x coordinate
	 * @param y1
	 *            used to be the new y coordinate
	 */
	public coords(int x1, int y1) {
		x = x1;
		y = y1;
	}
}