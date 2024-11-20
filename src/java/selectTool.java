import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/** This selectTool uses the ourTool interface. It selects a free-hand region.
 *
 * It should work with all operating systems and hardware.
 * There are no variances and no security constraints.
 *
 * @author TerpPaint
 * @version 2.0
 */
public class selectTool implements ourTool {

    /** Creates a selectTool and sets the Stroke.
     * It takes in no parameters or null arguments.  It does not return anything.
     * There are no algorithms of any kind and no variances and OS dependencies.
     * There should not be any exceptions or security constraints.
     */
    public selectTool() {
	super();
	selectStroke = new BasicStroke(1.f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,8.f,new float[]{6.f,6.f},0.f);
		//Istvan phase 5
		selectStrokeW = new BasicStroke(1.f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,8.f,new float[]{6.f,6.f},6.f);
		//End Istvan phase 5
     }

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
    private int curX;

    /** y coordinate of current mouse position.
     */
    private int	 curY;
    /** Used as a flag to determine if the mouse was dragged.
     */
    private boolean dragged = false;
    /** Used as a flag to determine if the mouse was moved.
     */
    private boolean moved = false;
    /** Used to set the fill type, ranging from 1-3.
     */
    private int fillType = 1;

    /** Holds the current image.
     */
    BufferedImage curImage;

	// 1 = border left_color, 2 = fill with right_color, 3 = solid left_color
    /** Holds the backupImage, selectedImage, pastedImage.
     */
    BufferedImage  backupImage;

    /** Holds the backupImage, selectedImage, pastedImage.
     */
    BufferedImage selectedImage;

    /** Holds the backupImage, selectedImage, pastedImage.
     */
    BufferedImage pastedImage;

    /** Holds the Graphics2D.
     */
    Graphics2D g2D;
    /** Used as a flag to determine if it was selected.
     */
    public boolean selected = false;
    /** Used as a flag to determine if it was pasted
=======
    private boolean selected = false;
    /** Used as a flag to determine if it was pasted.
>>>>>>> 1.5
     */

    public boolean pasted = false;
    /** Used as a flag to determine if it was moving
=======
    private boolean pasted = false;
    /** Used as a flag to determine if it was moving.
>>>>>>> 1.5
     */

    public boolean moving = false;
    /** Holds the stroke that is selected
=======
    private boolean moving = false;
    /** Holds the stroke that is selected.
>>>>>>> 1.5
     */
    private BasicStroke selectStroke;
    //Istvan phase 5
    /** Holds the stroke that is selected.
	     */
    private BasicStroke selectStrokeW;
    //End Istvan phase 5

    /** Holds the pasted X.
     */
    private int pasteX;

    /** Holds the pasted Y.
     */
    private int	 pasteY;
    //Istvan
    /** selected free-hand area, closed polygon.
     */
    public Polygon cursorPolygon;
    /** keeps a copy of the pre-transform cursor polygon.
     */
    public Polygon oldCursorPolygon;
    /** A polygon to hold the selection that was just copied.
     * at the moment, this member has absolutely no purpose.  Feel free to delete it in TerpPaint 5.0 if you
     * want.  I was going to use it to have terppaint remember the size of the selection when the user goes
     * to edit->copy so that when you paste, you can paste into a properly sized selection instead of a
     * rectangle.  But the pasting system is borked in some other file and there's no time to fix it before
     * submission.  If you want to spare it and go with my idea, kudos to you.
     */
    public Polygon copyPolygon;
    /** Sets drawOpaque to true.
     */
    public boolean drawOpaque = true;

    /** Determines if it is on the canvas.
     */
    public int onCanvasFlag = 1;

    /** Allows the user to click on the canvas, and begin selecting, as specified
     * by the coordinates passed in by MouseEvent.
     * Sets the flag selected to true.
     *
     * @param mevt mouseEvent holds the coordinates where the mouse was clicked.
     * @param theCanvas the current main_canvas, which holds the BufferedImage.
     */
    public void clickAction(MouseEvent mevt, main_canvas theCanvas) {
	int x, y;
	int temp;

	Rectangle boundRect;
	if (pasted == false) {

	    System.out.println("free-form click");
	    dragged = false;
	    if (selected == true && onCanvasFlag == 1) {
		x = mevt.getX();
		y = mevt.getY();
		if (cursorPolygon.contains(x,y)) {  // you clicked within your selection
			oldCursorPolygon = new Polygon(cursorPolygon.xpoints, cursorPolygon.ypoints, cursorPolygon.npoints);
		    g2D.drawImage(backupImage,null,0,0);
		    pasted = true;
		    selected = true;
		    curX = x;
		    curY = y;
		    backupImage = theCanvas.getBufferedImage();
		    curImage = theCanvas.getBufferedImage();
		    cutBackground(backupImage,theCanvas.right_color);
		    cutBackground(curImage,theCanvas.right_color);
		    g2D = curImage.createGraphics();
		    myDrawImage(theCanvas.right_color);
		    theCanvas.setBufferedImage(curImage);
		    moved = true;
		} else {  // you clicked outside the selection!
		    deSelect(theCanvas);
			/*g2D.drawImage(backupImage,null,0,0);
		    myDrawImage(theCanvas.right_color);
		    selected = false;
		    pasted = false;
		    moved = false;
		    theCanvas.repaint();*/
		}
	    } else {  // the first click to begin a selection
		backupImage = theCanvas.getBufferedImage();
		curImage = theCanvas.getBufferedImage();
		theCanvas.setBufferedImage(curImage);
		g2D = curImage.createGraphics();
		cursorPolygon = new Polygon();
	    startX = mevt.getX();
	    startY = mevt.getY();
	    cursorPolygon.addPoint(startX,startY); //adds the first point of the polygon?
	    }
	} else {
	    x = mevt.getX();
	    y = mevt.getY();
	    boundRect = new Rectangle(pasteX,pasteY,pastedImage.getWidth(),pastedImage.getHeight());
	    if (!(boundRect.contains(x,y))) {
		pasted = false;
		selected = false;
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

    /** Allows the user to drag the mouse and select more according to the
     * movement of the mouse. Can only be performed if the mouse was clicked
     * on the canvas and clickAction was called.
     * Sets the flag dragged to true.
     * @param mevt MouseEvent dragging
     * @param theCanvas the current main_canvas
     */
    public void dragAction(MouseEvent mevt, main_canvas theCanvas) {
	int x, y, width, height;

	System.out.println("mouse draggin in selectool");
	System.out.println("pasted:"+pasted+" and moved:"+moved);
	if ((pasted == false) && (moved == false)) {
	  //  System.out.println("pasted false moved false in drag");
	    g2D.drawImage(backupImage,null,0,0);
	    dragged = true;
	    x = mevt.getX();
	    y = mevt.getY();
	    cursorPolygon.addPoint(x,y);
	    g2D.setColor(Color.black);
	    g2D.setStroke(selectStroke);
	    g2D.draw(cursorPolygon);
	    //Istvan phase 5
	    g2D.setColor(Color.white);
			g2D.setStroke(selectStrokeW);
	    g2D.draw(cursorPolygon);
	    //End Istvan phase 5
	    Rectangle paintArea = cursorPolygon.getBounds();
	    paintArea.setSize(paintArea.width + 2, paintArea.height + 2);
	    theCanvas.repaint(paintArea);
	} else
	// ming 4.24
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
	    g2D.setStroke(selectStroke);
	    g2D.draw(cursorPolygon);
	    //Istvan phase 5
	    g2D.setColor(Color.white);
			g2D.setStroke(selectStrokeW);
			g2D.draw(cursorPolygon);
	    //End Istvan phase 5
	}
	theCanvas.repaint();
    }

    /** Allows the user to release the mouse and therefore stop selecting. Can only be
     * performed if the mouse was clicked on the canvas and clickAction was called.
     * The selected region is determined by the coordinates of the MouseEvent.
     * Sets the flag dragged to false.
     * @param mevt MouseEvent of release
     * @param theCanvas current main_canvas
     */
    public void mouseReleaseAction(MouseEvent mevt, main_canvas theCanvas) {
	int x, y, width, height;
	Double xDub, yDub, widthDub, heightDub;

	if ((pasted == false) && (selected == false)){
	    moved = false;
	    if (dragged) {
			g2D.drawImage(backupImage,null,0,0);
			x = mevt.getX();
			y = mevt.getY();
			cursorPolygon.addPoint(x,y);
			Rectangle theBounds = cursorPolygon.getBounds();
			xDub = new Double(theBounds.getX());
			yDub = new Double(theBounds.getY());
			widthDub = new Double(theBounds.getWidth());
			heightDub = new Double(theBounds.getHeight());
	
			selectedImage = theCanvas.getCopy(curImage).getSubimage(xDub.intValue(),yDub.intValue(),widthDub.intValue(),heightDub.intValue());
	
	
	
			g2D.setColor(Color.black);
			g2D.setStroke(selectStroke);
			g2D.draw(cursorPolygon);
			//Istvan phase 5
			g2D.setColor(Color.white);
					g2D.setStroke(selectStrokeW);
			g2D.draw(cursorPolygon);
			//End Istvan phase 5
		    theBounds.setSize(theBounds.width + 2, theBounds.height + 2);
		    theCanvas.repaint(theBounds);
			dragged = false;
			selected = true;
			startX = xDub.intValue();
			startY = yDub.intValue();
			pasteX = startX;
			pasteY = startY;
			endX = xDub.intValue() + widthDub.intValue();
			endY = yDub.intValue() + heightDub.intValue();
	
			if(selectedImage!=null)
				selectedImage = pasteBackground(selectedImage, theCanvas.right_color);
	
	
			pastedImage = selectedImage;
	    }
	} else {
	    Double aDouble;
	    g2D.drawImage(backupImage,null,0,0);
	    x = mevt.getX();
	    y = mevt.getY();
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
	    g2D.setStroke(selectStroke);
	    g2D.draw(cursorPolygon);
	    //Istvan phase 5
	    g2D.setColor(Color.white);
	    g2D.setStroke(selectStrokeW);
	    g2D.draw(cursorPolygon);
	    //End Istvan phase 5
	    theCanvas.repaint();
	}
    }

    /** Copies the selected portion of the canvas, but does not alter canvas.
     * Can only be performed if a portion was pasted or selected.
     * @param theCanvas the current main_canvas
     * @return BufferedImage of selected portion of the canvas
     */
    public BufferedImage getCopyImage(main_canvas theCanvas) {
	if ((pasted == true) || (selected == true)){
	    g2D.drawImage(backupImage,null,0,0);
	    myDrawImage(theCanvas.right_color);
	    //g2D.drawImage(pastedImage,null,pasteX,pasteY);
	    g2D.setColor(Color.black);
	    g2D.setStroke(selectStroke);
	    g2D.draw(cursorPolygon);
	    //Istvan phase 5
	    g2D.setColor(Color.white);
	    g2D.setStroke(selectStrokeW);
	    g2D.draw(cursorPolygon);
	    //End Istvan phase 5
	    theCanvas.repaint();
	    copyPolygon = new Polygon(cursorPolygon.xpoints, cursorPolygon.ypoints, cursorPolygon.npoints);
	    return pastedImage;
	}
	/*if (selected == true) {
	    g2D.drawImage(backupImage,null,0,0);
	    return selectedImage;
	}*/ else {
	    return new BufferedImage(10,10,BufferedImage.TYPE_INT_RGB);
	}
    }

    /** Cuts selected portion of image from the canvas.
     * If a portion was pasted, it is retrieved, and all flags are set to false.
     * If selected is true, then the image is drawn.
     * @param theCanvas current main_canvas
     * @return BufferedImage is the selected portion of image that is cut
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
	    selected = false;
	    moved = false;
	    copyPolygon = new Polygon(cursorPolygon.xpoints, cursorPolygon.ypoints, cursorPolygon.npoints);
	    return pastedImage;
	}
	if (selected == true) {
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
	    selected = false;
	    moved = false;
	    copyPolygon = new Polygon(cursorPolygon.xpoints, cursorPolygon.ypoints, cursorPolygon.npoints);
	    return selectedImage;
	} else {
	    return new BufferedImage(10,10,BufferedImage.TYPE_INT_RGB);
	}
    }


    /** Pastes image to canvas at appropriate x and y.
     * It preserves opaqueness.
     * It sets the flags pasted and selected to true.
     * @param theImage BufferedImage - the portion of image selected
     * @param theCanvas current main_canvas
     * @param x an int representing the x coordinate
     * @param y an int representing the y coordinate
     */
    // ming 4.24
    public void setPastedImage(BufferedImage theImage, main_canvas theCanvas, int x, int y) {
    	deSelect(theCanvas);
    	cursorPolygon = new Polygon(copyPolygon.xpoints, copyPolygon.ypoints, copyPolygon.npoints);
    	
    	
    	
    	// ming 4.24 end
	int temp;
	// ming 4.24
	pasteX = x;
	pasteY = y;
	// ming 4.24 end

	if (pasted == true) {
	    //System.out.println("pasted true in setpasted in selectool");
	    g2D.drawImage(theImage,null,0,0);
	    //g2D.drawImage(backupImage,null,0,0);
	    //g2D.drawImage(selectedImage,null,pasteX,pasteY);
	    myDrawImage(theCanvas.right_color);
	} else if (selected == true){
	    System.out.println("selected true in setpasted in selectool");
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
	g2D.setStroke(selectStroke);

	g2D.draw(new Rectangle(pasteX,pasteY,pastedImage.getWidth(),pastedImage.getHeight()));
		//Istvan phase 5
		g2D.setColor(Color.white);
	g2D.setStroke(selectStrokeW);

	g2D.draw(new Rectangle(pasteX,pasteY,pastedImage.getWidth(),pastedImage.getHeight()));
		//End Istvan phase 5
	theCanvas.setBufferedImage(curImage);
	pasted = true;
	selected = true;
	theCanvas.repaint();

	System.out.println("setpasted image in selectool");
    }



    /** Resets canvas to backup image and resets all data members and flags to false.
     * @param theCanvas main_canvas
     */
    public void deSelect(main_canvas theCanvas) {
	System.out.println("deselect in selectool ");
	/*
	if(backupImage!= null){
		g2D.drawImage(backupImage,null,0,0);
		myDrawImage(theCanvas.right_color);
	}
	//g2D.drawImage(pastedImage,null,pasteX,pasteY);

	selected = false;
	moved = false;
	pasted = false;
	theCanvas.repaint();
	 */
  //----------------------------------------------------------------
		if (selected == true) {
		g2D.drawImage(backupImage,null,0,0);
		if (drawOpaque == false) {
			myDrawImage(theCanvas.right_color);
		} else {
			g2D.drawImage(pastedImage,null,pasteX,pasteY);
		}
		selected = false;
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

    /** Clears the selected area on the main_canvas.
     * Sets the flags selected, moved, and pasted to false.
     * @param theCanvas main_canvas
     */
    public void clear(main_canvas theCanvas) {
	if (selected = true) {
		//g2D.setColor(theCanvas.right_color);
		//g2D.fill(new Rectangle(pasteX,pasteY,pasteX+selectedImage.getWidth(),pasteY+selectedImage.getHeight()));
		g2D.drawImage(backupImage,null,0,0);
		cutBackground(curImage,theCanvas.right_color);
		selected = false;
		moved = false;
		pasted = false;
	} else if (pasted == true) {
		g2D.drawImage(backupImage,null,0,0);
		selected = false;
		moved = false;
		pasted = false;
	}
	theCanvas.repaint();
    }

    /** To paste an image into the selected rectangle but does not update canvas -- see setPastedImage
     * 2 cases, if it is opaque or not.
     *
     * @param rightColor Color, the right-click color
     */
    public void myDrawImage(Color rightColor) {
	System.out.println("original myDrawImage in selectTool");

	if (drawOpaque == true) {
	    //System.out.println("drawOpaque true");
	    //System.out.println("----------------------------------selectedimages width:"+selectedImage.getWidth());
	    //System.out.println("("+cursorPolygon.getBounds().getX()+","+cursorPolygon.getBounds().getY()+")");

		//System.out.println("----------------------------------selectedimages width:"+selectedImage.getWidth());
		int deltaX = (int) (cursorPolygon.getBounds().getX() - oldCursorPolygon.getBounds().getX());
		int deltaY = (int) (cursorPolygon.getBounds().getY() - oldCursorPolygon.getBounds().getY());
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
						if (oldCursorPolygon.contains(theX - deltaX, theY - deltaY))
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
    /** RightColor is painted over selected area to be cut.
     * @param cuttingImg BufferedImage of selected image
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

      /** RightColor is painted over selected area to be cut.
     * @param cuttingImg BufferedImage of selected image
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
    /** Returns the current state of the flag selected.
     * @return boolean representing the state of the flag selected
     */
    public boolean getSelected(){return selected;}
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
    /** Returns the current state of the flag selected.
     * @return boolean representing the state of the flag selected
     */
    public boolean isSelected(){ return selected; }

}
