import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import java.awt.geom.*;

/** This magicSelectTool uses the ourTool interface. It magicSelects a region of connected color.
 *
 * It should work with all operating systems and hardware.
 * There are no variances and no security constraints.
 * uses its own inner class coords22.
 *
 * @author TerpPaint
 * @version 3.0
 */
public class magicSelectTool implements ourTool {


    /** x coord of initial click.
     */
    private int startX;

    /** y coord of initial click.
     */
    private int startY;

    /** x coord of release of click.
     */
    private int endX;

    /** y coord of release of click.
     */
    private int	   endY;

    /** x coordinate of current mouse position.
     */
    public int curX;

    /** y coordinate of current mouse position.
     */
    public int	curY;
    /** Used as a flag to determine if the mouse was dragged.
     */
    public boolean dragged = false;
    /** Used as a flag to determine if the mouse was moved.
     */
    public boolean moved = false;
    /** Used to set the fill type, ranging from 1-3.
     */
    private int fillType = 1;

    /** Holds the current image.
     */
    BufferedImage curImage;

	// 1 = border left_color, 2 = fill with right_color, 3 = solid left_color.
    /** Holds the backupImage, magicSelectedImage, pastedImage.
     */
    BufferedImage  backupImage;

    /** Holds the backupImage, magicSelectedImage, pastedImage.
     */
    BufferedImage magicSelectedImage;

    /** Holds the backupImage, magicSelectedImage, pastedImage.
     */
    BufferedImage pastedImage;

    /** Holds the Graphics2D.
     */
    Graphics2D g2D;
    /** Used as a flag to determine if it was magicSelected.
     */
    public boolean magicSelected = false;
    /** Used as a flag to determine if it was pasted.
     */
    public boolean pasted = false;
    /** Used as a flag to determine if it was moving.
     */
    public boolean moving = false;
    /** Holds the stroke that is magicSelected.
     */
    public BasicStroke magicSelectStroke;
   //Istvan phase 5
   /** Holds the stroke that is magicSelected in white.
	 */
    public BasicStroke magicSelectStrokeW;
//End Istvan phase 5
    /** Holds the pasted X.
     */
    private int pasteX;

    /** Holds the pasted Y.
     */
    private int	 pasteY;
    /** magicSelected free-hand area, closed polygon.
     */
    public Polygon cursorPolygon;
    /** Sets drawOpaque to true.
     */
    public boolean drawOpaque = true;

    /** Determines if it is on the canvas.
     */
    public int onCanvasFlag = 1;
    /** The new color to select the pixel with the colors wanted.
	*/
	static int select;

	/** The current position in the array.
	*/
	static int index;
	/** The current size of the array.
	*/
	static int size;

	/** The column number of pixels. Negative value is not allowed.
     */
    static int eastBound;

    /** The row number of pixels. Negative value is not allowed.
     */
    static int southBound;

    /** A coordinate array of the pixels used to keep track which part of the BufferedImage curImage is not colored.
     */
    coords2 [] pixels;

    /** The coordinates that is used to move around in the coord array of pixels and keep track which of the coords2 is not colored.
     */
    coords2 cur;
	/** Creates a magicSelectTool and sets the Stroke.
     * It takes in no parameters or null arguments.  It does not return anything.
     * There are no algorithms of any kind and no variances and OS dependencies.
     * There should not be any exceptions or security constraints.
     */
    public magicSelectTool() {
	super();
	magicSelectStroke = new BasicStroke(1.f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,8.f,new float[]{6.f,6.f},0.f);
	//Istvan phase 5
	magicSelectStrokeW = new BasicStroke(1.f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,8.f,new float[]{6.f,6.f},6.f);
		//End Istvan phase 5
    }
    /** Allows the user to click on the canvas, and begin magicSelecting, as specified
     * by the coordinates passed in by MouseEvent.
     * Sets the flag magicSelected to true.
     *
     * @param mevt mouseEvent holds the coordinates where the mouse was clicked.
     * @param theCanvas the current main_canvas, which holds the BufferedImage.
     */
    public void clickAction(MouseEvent mevt, main_canvas theCanvas) {
	int x, y;
	int temp;

	Rectangle boundRect;
	if (pasted == false) {

	    System.out.println("magic Wand Click");
	    dragged = false;
	    if (magicSelected == true && onCanvasFlag == 1) {
				System.out.println("its already selected");
		x = theCanvas.canvasScale(mevt.getX());
		y = theCanvas.canvasScale(mevt.getY());
		/*if (endX < startX) {
		    temp = endX;
		    endX = startX;
		    startX = temp;
		}
		if (endY < startY) {
		    temp = endY;
		    endY = startY;
		    startY = temp;
		}*/
		//boundRect = new Rectangle(startX,startY,Math.abs(startX-endX),Math.abs(startY-endY));
		boundRect = cursorPolygon.getBounds();
		if (boundRect.contains(x,y)) {
					System.out.println("magic Wand Click in");
		    g2D.drawImage(backupImage,null,0,0);
		    pasted = true;
		    magicSelected = true;
		    curX = x;
		    curY = y;
		    //pastedImage = magicSelectedImage;
		    //g2D.setColor(theCanvas.right_color);
		    //g2D.fill(boundRect);
		    backupImage = theCanvas.getBufferedImage();
		    curImage = theCanvas.getBufferedImage();
		    cutBackground(backupImage,theCanvas.right_color);
		    cutBackground(curImage,theCanvas.right_color);
		    g2D = curImage.createGraphics();
		    myDrawImage(theCanvas.right_color);
		    theCanvas.setBufferedImage(curImage);
		} else {
		    g2D.drawImage(backupImage,null,0,0);
		    myDrawImage(theCanvas.right_color);
		    magicSelected = false;
		    pasted = false;
		    moved = false;
		    theCanvas.repaint();
		}
	    } else {
		backupImage = theCanvas.getBufferedImage();
		curImage = theCanvas.getBufferedImage();
		theCanvas.setBufferedImage(curImage);
		g2D = curImage.createGraphics();
	    }
	    if ((pasted == false) && (moved == false)) {
				dragged = true;
				cursorPolygon = new Polygon();
				startX = theCanvas.canvasScale(mevt.getX());
				startY = theCanvas.canvasScale(mevt.getY());

				index = 0;
				select = curImage.getRGB(startX,startY);

				eastBound = curImage.getWidth();
				southBound = curImage.getHeight();

				pixels = new coords2[eastBound*southBound];
				pixels[0] = new coords2(startX,startY);

				try{
					selectIt();
					setCursorPolygon();
				}catch(Exception e){
					System.err.println(e);}
				g2D.setColor(Color.black);
				g2D.setStroke(magicSelectStroke);
				g2D.draw(cursorPolygon);
				//Istvan phase 5
				g2D.setColor(Color.white);
				g2D.setStroke(magicSelectStrokeW);
				g2D.draw(cursorPolygon);
				//End Istvan phase 5
				//dragged=true;
				theCanvas.repaint();
			}
	} else {
	    x = theCanvas.canvasScale(mevt.getX());
	    y = theCanvas.canvasScale(mevt.getY());
	    boundRect = new Rectangle(pasteX,pasteY,pastedImage.getWidth(),pastedImage.getHeight());
	    if (!(boundRect.contains(x,y))) {
		pasted = false;
		magicSelected = false;
		g2D.drawImage(backupImage,null,0,0);
		myDrawImage(theCanvas.right_color);
		theCanvas.repaint();
		//g2D.drawImage(pastedImage,null,pasteX,pasteY);
	    } else {
		moved = true;
		curX = x;
		curY = y;
	    }
	}
    }

    /** Allows the user to drag the mouse and magicSelect more according to the
     * movement of the mouse. Can only be performed if the mouse was clicked
     * on the canvas and clickAction was called.
     * Sets the flag dragged to true.
     * @param mevt MouseEvent dragging
     * @param theCanvas the current main_canvas
     */
    public void dragAction(MouseEvent mevt, main_canvas theCanvas) {
		int x, y, width, height;

	System.out.println("mouse dragging in magicselectool");
	System.out.println("pasted:"+pasted+" and moved:"+moved);
	if ((pasted == false) && (moved == false)) {
	  //  System.out.println("pasted false moved false in drag");
	    //g2D.drawImage(backupImage,null,0,0);
	    dragged = true;
	   // x = theCanvas.canvasScale(mevt.getX());
	   // y = theCanvas.canvasScale(mevt.getY());
	   // cursorPolygon.addPoint(x,y);
	   // g2D.setColor(Color.black);
	   // g2D.setStroke(magicSelectStroke);
	   // g2D.draw(cursorPolygon);
	   // theCanvas.repaint();
	} else // ming 4.24
	    if (moved)
		// ming 4.24 end
	    {
	    //System.out.println("pasted true or moved true");
	    Double aDouble;
	    g2D.drawImage(backupImage,null,0,0);
	    x = theCanvas.canvasScale(mevt.getX());
	    y = theCanvas.canvasScale(mevt.getY());
	    //aDouble = new Double(x - (pastedImage.getWidth()/2));
	    //pasteX =	aDouble.intValue();
	    //aDouble = new Double(y - (pastedImage.getHeight()/2));
	    //pasteY = aDouble.intValue();
	    pasteX = pasteX + (x - curX);
	    pasteY = pasteY + (y - curY);
	    cursorPolygon.translate(x-curX,y-curY);
	    myDrawImage(theCanvas.right_color);
	    curX = x;
	    curY = y;
	    //g2D.drawImage(pastedImage,null,pasteX,pasteY);
	    g2D.setColor(Color.black);
	    g2D.setStroke(magicSelectStroke);
	    g2D.draw(new Rectangle(pasteX,pasteY,pastedImage.getWidth(),pastedImage.getHeight()));
	    //Istvan phase 5
	    g2D.setColor(Color.white);
			g2D.setStroke(magicSelectStrokeW);
			g2D.draw(new Rectangle(pasteX,pasteY,pastedImage.getWidth(),pastedImage.getHeight()));
	    //End Istvan phase 5
	    theCanvas.repaint();

	}

    }

    /** setCursorPolygon determines the boundary of all the pixels.
	*/
	public void setCursorPolygon(){
		//int k=0;
		//while (k<size){
		//	System.err.println(pixels[k].x+" "+pixels[k].y);
		//	k++;
		//}
		int[] top;
		int[] bot;
		int minX=eastBound+1;
		int maxX=-1;
		int yUp=-1;
		int yDown=southBound+1;
		int j=1;
		while(j<size){//set up the limits
			if(pixels[j].x<minX){
				minX=pixels[j].x;
			}
			if(pixels[j].x>maxX){
				maxX=pixels[j].x;
			}
			if(pixels[j].y<yDown){
				yDown=pixels[j].y;
			}
			if(pixels[j].y>yUp){
				yUp=pixels[j].y;
			}
			j++;
		}
		//System.err.println("maxX "+maxX+" minX "+minX+"maxY "+yUp+"minY "+yDown);
		//System.err.println("done1");
		top = new int[maxX-minX+1];
		bot = new int[maxX-minX+1];
		int currX=minX;
		int maxY;
		int minY;
		while(currX<=maxX){
			j=1;
			maxY=yDown;
			minY=yUp;
			while(j<size){
				if(pixels[j].x==currX){
					if(pixels[j].y<minY){
						minY=pixels[j].y;
					}
					if(pixels[j].y>maxY){
						maxY=pixels[j].y;
					}
				}
				j++;
			}
			top[currX-minX]=minY;
			bot[currX-minX]=maxY;
			currX++;
		}
		//System.err.println("done2");
		currX=minX;
		while(currX<=maxX){
			cursorPolygon.addPoint(currX,top[currX-minX]);
			currX++;
		}
		currX--;
		//System.err.println("done3");
		while(currX>=minX){
			//System.err.println(currX);
			cursorPolygon.addPoint(currX,bot[currX-minX]);
			--currX;
		}
		cursorPolygon.addPoint(currX+1,top[0]);
		//cursorPolygon.addPoint(currX,top[1]);
		//System.err.println("done4");
		//System.err.println("cursor polygon has");
		//int l=0;
		//while (l<cursorPolygon.npoints){
		//	System.err.println(cursorPolygon.xpoints[l]+" "+cursorPolygon.ypoints[l]);
		//	l++;
		//}

    }
	/** selectIt does the coloring. It checks South, North, East and West of the main canvas image.
     * Then, if it is not colored, it will be colored.
     * The function also runs until index (the total area of the bufferedImage) is -1, which means that the
     * BufferedImage is colored.
     */
    public void selectIt(){
		System.err.println("selecting");
	boolean added=true;
	size=1;
	//int i=0;

		//while (i<3){
		while(index!=-1){
			added=false;

	    cur = pixels[index];
			//System.err.println("process "+index);
			//System.out.println("process "+index);
	    //System.err.println("coords2 "+cur.x+" "+cur.y);

	    //System.err.println("CHECKING SOUTH");
	    if(cur.y+1 < curImage.getHeight() && curImage.getRGB(cur.x, cur.y+1) == select){
		index++;
		size++;
		curImage.setRGB(cur.x, cur.y+1,select+1);
		//System.out.println("adding at "+index+" "+(cur.x)+" "+(cur.y+1));
		//System.out.println("so size is "+size);
		pixels[size-1] = new coords2(cur.x,cur.y+1);

		added=true;
	    }
	    //System.err.println("CHECKING NORTH");
	    if(cur.y-1 >= 0 && curImage.getRGB(cur.x, cur.y-1) == select){
		//cursorPolygon.addPoint(cur.x,cur.y-1);
		index++;
				size++;
		curImage.setRGB(cur.x, cur.y-1,select+1);
		//System.out.println("adding at "+index+" "+(cur.x)+" "+(cur.y-1));
		//System.out.println("so size is "+size);
		pixels[size-1] = new coords2(cur.x,cur.y-1);
		added=true;
	    }
	    //System.err.println("CHECKING EAST");
	    if(cur.x+1 < curImage.getWidth() && curImage.getRGB(cur.x+1, cur.y) == select){
				index++;
		size++;
		curImage.setRGB(cur.x+1, cur.y,select+1);
		//System.out.println("adding at "+index+" "+(cur.x+1)+" "+cur.y);
		//System.out.println("so size is "+size);
		//cursorPolygon.addPoint(cur.x+1,cur.y);

		pixels[size-1] = new coords2(cur.x+1,cur.y);
		added=true;
	    }
	    //System.err.println("CHECKING WEST");
	    if(cur.x-1 >= 0 && curImage.getRGB(cur.x-1, cur.y) == select){
				index++;
		size++;
		curImage.setRGB(cur.x-1, cur.y,select+1);
		//System.out.println("adding at "+index+" "+(cur.x-1)+" "+cur.y);
		//System.out.println("so size is "+size);
		//cursorPolygon.addPoint(cur.x-1,cur.y);
		pixels[size-1] = new coords2(cur.x-1,cur.y);
		added=true;
	    }
			if (added)
				index=size-1;
			index--;

			//end of while

	}
		int c=0;
		while (c<size){
			curImage.setRGB(pixels[c].x, pixels[c].y,select);
			c++;
		}
		//magicSelected=true;
		//dragged = true;
	System.err.println("selected");
    }
    /** Allows the user to release the mouse and therefore stop magicSelecting. Can only be
     * performed if the mouse was clicked on the canvas and clickAction was called.
     * The magicSelected region is determined by the coordinates of the MouseEvent.
     * Sets the flag dragged to false.
     * @param mevt MouseEvent of release
     * @param theCanvas current main_canvas
     */
    public void mouseReleaseAction(MouseEvent mevt, main_canvas theCanvas) {
	int x, y, width, height;
	Double xDub, yDub, widthDub, heightDub;

	if ((pasted == false) && (magicSelected == false)){
	    moved = false;
	    if (dragged) {
		g2D.drawImage(backupImage,null,0,0);
		x = theCanvas.canvasScale(mevt.getX());
		y = theCanvas.canvasScale(mevt.getY());
		cursorPolygon.addPoint(x,y);
		Rectangle theBounds = cursorPolygon.getBounds();
		xDub = new Double(theBounds.getX());
		yDub = new Double(theBounds.getY());
		widthDub = new Double(theBounds.getWidth());
		heightDub = new Double(theBounds.getHeight());

		magicSelectedImage = theCanvas.getCopy(curImage).getSubimage(xDub.intValue(),yDub.intValue(),widthDub.intValue(),heightDub.intValue());



		g2D.setColor(Color.black);
		g2D.setStroke(magicSelectStroke);
		g2D.draw(theBounds);
		//Istvan phase 5
		g2D.setColor(Color.white);
				g2D.setStroke(magicSelectStrokeW);
		g2D.draw(theBounds);
		//End Istvan phase 5
		theCanvas.repaint();
		dragged = false;
		magicSelected = true;
		startX = xDub.intValue();
		startY = yDub.intValue();
		pasteX = startX;
		pasteY = startY;
		endX = xDub.intValue() + widthDub.intValue();
		endY = yDub.intValue() + heightDub.intValue();

		if(magicSelectedImage!=null)
		magicSelectedImage = pasteBackground(magicSelectedImage, theCanvas.right_color);


		pastedImage = magicSelectedImage;
	    }
	} else {
	    Double aDouble;
	    g2D.drawImage(backupImage,null,0,0);
	    x = theCanvas.canvasScale(mevt.getX());
	    y = theCanvas.canvasScale(mevt.getY());
	    /*aDouble = new Double(x - (pastedImage.getWidth()/2));
	    pasteX =  aDouble.intValue();
	    aDouble = new Double(y - (pastedImage.getHeight()/2));
	    pasteY = aDouble.intValue();*/
	    pasteX = pasteX + (x - curX);
	    pasteY = pasteY + (y - curY);
	    cursorPolygon.translate(x-curX,y-curY);
	    myDrawImage(theCanvas.right_color);
	    curX = x;
	    curY = y;
	    //g2D.drawImage(pastedImage,null,pasteX,pasteY);
	    g2D.setColor(Color.black);
	    g2D.setStroke(magicSelectStroke);
	    g2D.draw(new Rectangle(pasteX,pasteY,pastedImage.getWidth(),pastedImage.getHeight()));
	    //Istvan phase 5
	    g2D.setColor(Color.white);
	    g2D.setStroke(magicSelectStrokeW);
	    g2D.draw(new Rectangle(pasteX,pasteY,pastedImage.getWidth(),pastedImage.getHeight()));
	    //End Istvan phase 5
	    theCanvas.repaint();
	}
    }

    /** Copies the magicSelected portion of the canvas, but does not alter canvas.
     * Can only be performed if a portion was pasted or magicSelected.
     * @param theCanvas the current main_canvas
     * @return BufferedImage of magicSelected portion of the canvas
     */
    public BufferedImage getCopyImage(main_canvas theCanvas) {
	if ((pasted == true) || (magicSelected == true)){
	    g2D.drawImage(backupImage,null,0,0);
	    myDrawImage(theCanvas.right_color);
	    //g2D.drawImage(pastedImage,null,pasteX,pasteY);
	    g2D.setColor(Color.black);
	    g2D.setStroke(magicSelectStroke);
	    g2D.draw(new Rectangle(pasteX,pasteY,pastedImage.getWidth(),pastedImage.getHeight()));
	    //Istvan phase 5
	    g2D.setColor(Color.white);
	    g2D.setStroke(magicSelectStrokeW);
	    g2D.draw(new Rectangle(pasteX,pasteY,pastedImage.getWidth(),pastedImage.getHeight()));
	    //End Istvan phase 5
	    theCanvas.repaint();
	    return pastedImage;
	}
	/*if (magicSelected == true) {
	    g2D.drawImage(backupImage,null,0,0);
	    return magicSelectedImage;
	}*/ else {
	    return new BufferedImage(10,10,BufferedImage.TYPE_INT_RGB);
	}
    }

    /** Cuts magicSelected portion of image from the canvas.
     * If a portion was pasted, it is retrieved, and all flags are set to false.
     * If magicSelected is true, then the image is drawn.
     * @param theCanvas current main_canvas
     * @return BufferedImage is the magicSelected portion of image that is cut
     */
    public BufferedImage getCutImage(main_canvas theCanvas) {
	int temp;
	if (pasted == true) {
	    g2D.drawImage(backupImage,null,0,0);
	    //g2D.setColor(theCanvas.right_color);
	    //g2D.fill(new Rectangle(pasteX,pasteY,pastedImage.getWidth(),pastedImage.getHeight()));
	    backupImage = theCanvas.getBufferedImage();
	    curImage = theCanvas.getBufferedImage();
	    g2D = curImage.createGraphics();
	    theCanvas.setBufferedImage(curImage);
	    theCanvas.repaint();
	    pasted = false;
	    magicSelected = false;
	    moved = false;
	    return pastedImage;
	}
	if (magicSelected == true) {
	    g2D.drawImage(backupImage,null,0,0);
	    if (endX < startX) {
		temp = endX;
		endX = startX;
		startX = temp;
	    }
	    if (endY < startY) {
		temp = endY;
		endY = startY;
		startY = temp;
	    }
	    //g2D.setColor(theCanvas.right_color);
	    //g2D.fill(new Rectangle(startX,startY,Math.abs(startX-endX),Math.abs(startY-endY)));
	    backupImage = theCanvas.getBufferedImage();
	    curImage = theCanvas.getBufferedImage();
	    cutBackground(backupImage,theCanvas.right_color);
	    cutBackground(curImage,theCanvas.right_color);
	    g2D = curImage.createGraphics();
	    theCanvas.setBufferedImage(curImage);
	    theCanvas.repaint();
	    pasted = false;
	    magicSelected = false;
	    moved = false;
	    return magicSelectedImage;
	} else {
	    return new BufferedImage(10,10,BufferedImage.TYPE_INT_RGB);
	}
    }


    /** Pastes image to canvas at appropriate x and y.
     * It preserves opaqueness.
     * It sets the flags pasted and magicSelected to true.
     * @param theImage BufferedImage - the portion of image magicSelected
     * @param theCanvas current main_canvas
     * @param x an int representing the 'X' paste location
     * @param y an int representing the 'Y' paste location
     */
    // ming 4.24
    public void setPastedImage(BufferedImage theImage, main_canvas theCanvas, int x, int y) {
    // ming 4.24 end
	int temp;
	// ming 4.24
	pasteX = x;
	pasteY = y;
	// ming 4.24 end

	if (pasted == true) {
	    //System.out.println("pasted true in setpasted in magicSelectool");
	    g2D.drawImage(theImage,null,0,0);
	    //g2D.drawImage(backupImage,null,0,0);
	    //g2D.drawImage(magicSelectedImage,null,pasteX,pasteY);
	    myDrawImage(theCanvas.right_color);
	} else if (magicSelected == true){
	    System.out.println("magicSelected true in setpasted in magicSelectool");
	    g2D.drawImage(backupImage,null,0,0);
	}
	pastedImage = theImage;
	curImage = theCanvas.getBufferedImage();
	backupImage = theCanvas.getBufferedImage();
	g2D = curImage.createGraphics();
	//g2D.drawImage(pastedImage,null,0,0);

	System.out.println("HERE IS THE COLOR:"+theCanvas.right_color);
	myDrawImage(theCanvas.right_color);
	g2D.draw(cursorPolygon);
	g2D.setColor(Color.black);
	g2D.setStroke(magicSelectStroke);

	g2D.draw(new Rectangle(pasteX,pasteY,pastedImage.getWidth(),pastedImage.getHeight()));
		//Istvan phase 5
		g2D.setColor(Color.white);
	g2D.setStroke(magicSelectStrokeW);

	g2D.draw(new Rectangle(pasteX,pasteY,pastedImage.getWidth(),pastedImage.getHeight()));
		//End Istvan phase 5
	theCanvas.setBufferedImage(curImage);
	pasted = true;
	magicSelected = true;
	theCanvas.repaint();

	System.out.println("setpasted image in magicSelectool");
    }



    /** Resets canvas to backup image and resets all data members and flags to false.
     * @param theCanvas main_canvas
     */
    public void deSelect(main_canvas theCanvas) {
	System.out.println("demagicSelect in magicSelectool ");
	/*
	if(backupImage!= null){
		g2D.drawImage(backupImage,null,0,0);
		myDrawImage(theCanvas.right_color);
	}
	//g2D.drawImage(pastedImage,null,pasteX,pasteY);

	magicSelected = false;
	moved = false;
	pasted = false;
	theCanvas.repaint();
	 */
  //----------------------------------------------------------------
		if (magicSelected == true) {
		g2D.drawImage(backupImage,null,0,0);
		if (drawOpaque == false) {
			myDrawImage(theCanvas.right_color);
		} else {
			g2D.drawImage(pastedImage,null,pasteX,pasteY);
		}
		magicSelected = false;
		pasted = false;
		moved = false;
		theCanvas.repaint();
	}
    }

    /** Sets the opaque value to the boolean passed in.
     * @param value boolean
     * @see #getOpaque
     */
    public void setOpaque(boolean value) { drawOpaque = value; }

    /** Clears the magicSelected area on the main_canvas.
     * Sets the flags magicSelected, moved, and pasted to false.
     * @param theCanvas main_canvas
     */
    public void clear(main_canvas theCanvas) {
	if (magicSelected = true) {
		//g2D.setColor(theCanvas.right_color);
		//g2D.fill(new Rectangle(pasteX,pasteY,pasteX+magicSelectedImage.getWidth(),pasteY+magicSelectedImage.getHeight()));
		g2D.drawImage(backupImage,null,0,0);
		cutBackground(curImage,theCanvas.right_color);
		magicSelected = false;
		moved = false;
		pasted = false;
	} else if (pasted == true) {
		g2D.drawImage(backupImage,null,0,0);
		magicSelected = false;
		moved = false;
		pasted = false;
	}
	theCanvas.repaint();
    }

    /** To paste an image into the magicSelected rectangle but does not update canvas -- see setPastedImage
     * 2 cases, if it is opaque or not.
     *
     * @param rightColor Color, the right-click color
     */

    public void myDrawImage(Color rightColor) {
       // System.out.println("original myDrawImage in magicSelectTool");
	if (drawOpaque == true) {
	    //System.out.println("drawOpaque true");
	    //System.out.println("----------------------------------magicSelectedimages width:"+magicSelectedImage.getWidth());
	    //System.out.println("("+cursorPolygon.getBounds().getX()+","+cursorPolygon.getBounds().getY()+")");
			g2D.drawImage(pastedImage,null,pasteX,pasteY);
		//System.out.println("----------------------------------magicSelectedimages width:"+magicSelectedImage.getWidth());
		for (int count1X = 0;count1X < pastedImage.getWidth();count1X++) {
		   // System.out.println("outer for loop");

			for (int count1Y = 0;count1Y < pastedImage.getHeight(); count1Y++) {
			    //System.out.println("inner for loop");
				try {
				    //System.out.println("try pasteX "+pasteX+" count1X " +count1X + " pasteY "+pasteY+" countlY "+count1Y);
					//if (cursorPolygon.contains(pasteX+count1X,pasteY+count1Y)) {
					//	System.out.println("if in try");
						int theX = pasteX+count1X;
						int theY = pasteY+count1Y;
				//		System.out.println("Polygon contains ("+theX+","+theY+")");
						curImage.setRGB(pasteX+count1X,pasteY+count1Y,pastedImage.getRGB(count1X,count1Y));
						//g2D.draw(cursorPolygon);
						//g2D.drawImage(pastedImage,null,pasteX,pasteY);
					//}
				} catch(Exception e) { System.out.println("Exception in opaque"); }
			}
		}

	} else {
	    System.out.println("drawOpaque false");
		System.out.println("("+cursorPolygon.getBounds().getX()+","+cursorPolygon.getBounds().getY()+")");

		System.out.println("pastedImages width"+pastedImage.getWidth()+" and height:"+pastedImage.getHeight());
		for (int countX = 0;countX < pastedImage.getWidth(); countX++) {
			for (int countY = 0;countY < pastedImage.getHeight();countY++) {
				try {

					if ((cursorPolygon.contains(pasteX+countX,pasteY+countY)) && (pastedImage.getRGB(countX,countY) != rightColor.getRGB())) {

					    System.out.println("INSIDE THIS IF");
					    curImage.setRGB(pasteX+countX,pasteY+countY,pastedImage.getRGB(countX,countY));
					}
				} catch(Exception e) { System.out.println("Exception in not-opaque"); }
			}
		}
	}
    }

    /*
       public void myDrawImage(Color rightColor) {

	if (drawOpaque == true) {
		g2D.drawImage(pastedImage,null,pasteX,pasteY);
	} else {
		for (int countX = 0;countX < pastedImage.getWidth(); countX++) {
			for (int countY = 0;countY < pastedImage.getHeight(); countY++) {
				if (rightColor.getRGB() != pastedImage.getRGB(countX,countY)) {
					try {
						curImage.setRGB(pasteX+countX,pasteY+countY,pastedImage.getRGB(countX,countY));
					} catch (Exception e) {}
				}
			}
		}
	}
    }
     */
    /** RightColor is painted over magicSelected area to be cut.
     * @param cuttingImg BufferedImage of magicSelected image
     * @param rightColor Color that would be painted if right click draw.
     */
    public void cutBackground(BufferedImage cuttingImg,Color rightColor) {
	for (int countX = 0;countX < pastedImage.getWidth();countX++) {
		for (int countY = 0; countY < pastedImage.getHeight();countY++) {
			try {
				if (cursorPolygon.contains(pasteX+countX,pasteY+countY)) {
					cuttingImg.setRGB(pasteX+countX,pasteY+countY,rightColor.getRGB());
				}
			} catch(Exception e) {}
		}
	}
    }

      /** RightColor is painted over magicSelected area to be cut.
     * @param cuttingImg BufferedImage of magicSelected image
     * @param rightColor Color that would be painted if right click draw.
     * @return a BufferedImage object
     */
    public BufferedImage pasteBackground(BufferedImage cuttingImg,Color rightColor) {
	for (int countX = 0;countX < cuttingImg.getWidth();countX++) {
		for (int countY = 0; countY < cuttingImg.getHeight();countY++) {
			try {
				if (!cursorPolygon.contains(pasteX+countX,pasteY+countY)) {
					cuttingImg.setRGB(countX,countY,rightColor.getRGB());
				}
			} catch(Exception e) {}
		}
	}
	return cuttingImg;
    }


    /** Returns the Color of the g2d.
    * @return Color representing that of g2D
    */
   public Color getG2dColor(){return g2D.getColor();}
   /** Returns the current state of the flag dragged.
    * @return boolean representing the flag dragged
    */
   public boolean getDragged(){return dragged;}
    /** Returns the current state of the flag magicSelected.
     * @return boolean representing the state of the flag magicSelected
     */
    public boolean getSelected(){return magicSelected;}
    // ming 4.24
    /** This function returns the current X-position of subimage selected.
     * @return boolean representing the state of the flag selected
     */
    public int getStartX(){return startX;}
    // ming 4.24 end

    // ming 4.24
    /** This function returns the current Y-position of subimage selected.
     * @return boolean representing the state of the flag selected
     */
    public int getStartY(){return startY;}
    // ming 4.24 end
     /** Returns the current state of the flag pasted.
     * @return boolean representing the flag pasted.
     */
    public boolean getPasted(){return pasted;}
     /** Returns true if it is set to opaque, false
     * otherwise.
     * @return boolean representing opaque
     * @see #setOpaque
     */
    public boolean getOpaque(){return drawOpaque;}
    /** Returns the current state of the flag magicSelected.
     * @return boolean representing the state of the flag magicSelected
     */
    public boolean isSelected(){ return magicSelected; }

}
/** coords2 subclass included in Fill class.
 * It has an x and y coordinate which is used as points to keep track where it is.
 */
class coords2{
    /** X coordinate.
     */
    public int x;

    /** Y coordinate.
     */
    public int y;

    /** Constructor for class coords2.
     * It takes in two integers which is for the x and y coordinates and
     * sets the x and y coordiantes to the new coordinates.
     * @param x1 used to be the new x coordinate
     * @param y1 used to be the new y coordinate
     */
    public coords2(int x1, int y1){
	x = x1;
	y = y1;
    }

}
