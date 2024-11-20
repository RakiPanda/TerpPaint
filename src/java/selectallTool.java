import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

/** The class selectallTool uses the ourTool interface. It selects a rectangular region.
 * See also selectTool
 *
 * It should work with all operating systems and hardware.
 * There are no variances and no security constraints.
 *
 * @author TerpPaint
 * @version 2.0
 */
public class selectallTool implements ourTool {
     /** Holds the starting X,Y and ending X,Y.
     */
    private int startX;

    /** Holds the starting X,Y and ending X,Y.
     */
    private int startY;

    /** Holds the starting X,Y and ending X,Y.
     */
    private int endX;

    /** Holds the starting X,Y and ending X,Y.
     */
    private int	 endY;
    /** Used as a flag to determine if the mouse was dragged.
     */
    private boolean dragged = false;
    /** Flag to determine is it has moved.
     */
    private boolean moved = false;
    
    /** 1 = border left_color, 2 = fill with right_color, 3 = solid left_color.
     */
    private int fillType = 1;
	// 1 = border left_color, 2 = fill with right_color, 3 = solid left_color.
    /** Holds the current, backup, selected and pasted image.
     */
    BufferedImage curImage;

    /** Holds the current, backup, selected and pasted image.
     */
    BufferedImage backupImage;

    /** Holds the current, backup, selected and pasted image.
     */
    BufferedImage selectedImage;

    /** Holds the current, backup, selected and pasted image.
     */
    BufferedImage  pastedImage;

    /** Holds the temp image.
     */
    BufferedImage tempImage;

    /**Holds a Graphics2D.
     */
    private Graphics2D g2D;

    /**Holds a Graphics2D.
     */
    private Graphics2D g2D_temp;

    /** Flag to determine is it has been selected.
     */

    public boolean selected = false;
    /** Flag to determine is it has been pasted

    private boolean selected = false;
    /** Flag to determine is it has been pasted.

     */

    public boolean pasted = false;
    /** Flag to determine if it is moving

    private boolean pasted = false;
    /** Flag to determine if it is moving.

     */

    public boolean moving = false;
    /** Holds the stroke that is selected

    private boolean moving = false;
    /** Holds the stroke that is selected.

     */
    private BasicStroke selectStroke;
    //Istvan phase 5
    /** Holds the stroke that is selected for the white part.
	*/
    private BasicStroke selectStrokeW;
    //End Istvan phase 5

    /** x coord of where to be pasted.
     */
    private int pasteX;

    /** y of where to be pasted.
     */
    private int	 pasteY;
    /** Flag used to determine if drawOpaque is on or not.
     */
    public boolean drawOpaque = true;
    /** Determines if it is on the canvas.
     */
    public int onCanvasFlag = 1;

    /** Action name.
     */
    public String action_name = new String();

    /** Creates a selectallTool and sets the Stroke.
     * It takes in no parameters or null arguments.  It does not return anything.
     * There are no algorithms of any kind and no variances and OS dependencies.
     * There should not be any exceptions or security constraints.
     */
    public selectallTool() {
	super();
	selectStroke = new BasicStroke(1.f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,8.f,new float[]{6.f,6.f},0.f);
	//Istvan phase 5
	selectStrokeW = new BasicStroke(1.f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,8.f,new float[]{6.f,6.f},6.f);
		//End Istvan phase 5
    }

    /** Allows the user to click on the canvas and select all of the image.
     * A click in valid x,y coordinates initializes everything.
     * A click on invalid areas does nothing.
     * @param mevt MosueEvent upon initial click
     * @param theCanvas current main_canvas
     */
    public void clickAction(MouseEvent mevt, main_canvas theCanvas) {
	action_name = new String();
 //	  selected = false;

	int x, y;
	int temp;
//System.out.println(pasted);
	Rectangle boundRect;
	if (pasted == false) {
System.out.println("inside first if");
	    dragged = false;
	    if (selected == true && onCanvasFlag == 1) {
System.out.println("inside 2nd if");
		x = theCanvas.canvasScale(mevt.getX());
		y = theCanvas.canvasScale(mevt.getY());
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
		boundRect = new Rectangle(startX,startY,Math.abs(startX-endX),Math.abs(startY-endY));
		if (boundRect.contains(x,y)) {
		    g2D.drawImage(backupImage,null,0,0);
		    pasted = true;
		    selected = true;
		    pasteX = startX;
		    pasteY = startY;
		    pastedImage = selectedImage;
		    // ming 5.2
		    // consider move as cut or move as copy
		    backupImage = theCanvas.getBufferedImage();
		    curImage = theCanvas.getBufferedImage();
		    g2D = curImage.createGraphics();
		    theCanvas.setBufferedImage(curImage);
		    if (SwingUtilities.isLeftMouseButton(mevt))
		    {
			g2D.setColor(theCanvas.right_color);
			g2D.fill(boundRect);
		    }
		    backupImage = theCanvas.getBufferedImage();
		    curImage = theCanvas.getBufferedImage();
		    g2D = curImage.createGraphics();
		    // ming 5.2
		    action_name = "Move";
		    theCanvas.setBufferedImage(curImage);
		    // ming 5.2 end
		} else {
		    g2D.drawImage(backupImage,null,0,0);
		    selected = false;
		}
	    } else {
System.out.println("inside else 2");
		// selected ==false
		backupImage = theCanvas.getBufferedImage();
		curImage = theCanvas.getBufferedImage();
		action_name = "Select";
		theCanvas.setBufferedImage(curImage);
		g2D = curImage.createGraphics();
	    }
	    startX = theCanvas.canvasScale(mevt.getX());
	    startY = theCanvas.canvasScale(mevt.getY());
	    theCanvas.repaint();
	} else {
System.out.println("inside 3rd if");
	    // pasted == true
	    x = theCanvas.canvasScale(mevt.getX());
	    y = theCanvas.canvasScale(mevt.getY());
	    boundRect = new Rectangle(pasteX,pasteY,pastedImage.getWidth(),pastedImage.getHeight());
	    if (!(boundRect.contains(x,y))) {
		 // ming 5.2
		pasted = false;
		selected = false;
	       // deSelect(theCanvas);

		curImage = backupImage;
		g2D = curImage.createGraphics();
		myDrawImage(theCanvas.right_color);
		action_name = "Select";
		theCanvas.setBufferedImage(curImage);
		backupImage = theCanvas.getBufferedImage();

	 //	  g2D.drawImage(backupImage,null,0,0);

		startX = x;
		startY = y;
		// ming 5.2 end
	     //	  myDrawImage(theCanvas.right_color);
	    }
	    // ming 5.2
	    //else {
	      //  moved = true;
	    //}
	    // ming 5.2 end
	    theCanvas.repaint();
	}
    }

    /** Selects a rectangle according to movement of mouse, as specified by the
     * coordinates of MouseEvent.
     * @param mevt MosueEvent dragging mouse
     * @param theCanvas the current main_canvas
     */
    public void dragAction(MouseEvent mevt, main_canvas theCanvas) {

	int x, y, width, height;
	boolean outofbound = false;
	if ((pasted == false) && (moved == false)) {
	    g2D.drawImage(backupImage,null,0,0);
	    dragged = true;
	    endX = mevt.getX();
	    endY = mevt.getY();
	    if (endX > theCanvas.widt) {
			endX = theCanvas.widt-1;
			outofbound = true;
	    }
		if (endY > theCanvas.heig) {
			endY = theCanvas.heig-1;
			outofbound = true;
		}
	    x = startX;
	    y = startY;
	    if (endX < startX) {
		x = endX;
	    }
	    if (endY < startY) {
		y = endY;
	    }
	    width = Math.abs(startX - endX);
	    height = Math.abs(startY - endY);
	    g2D.setColor(Color.black);
	    g2D.setStroke(selectStroke);
	    g2D.draw(new Rectangle(x,y,width,height));
	    //Istvan phase 5
	    g2D.setColor(Color.white);
			g2D.setStroke(selectStrokeW);
	    g2D.draw(new Rectangle(x,y,width,height));
	    //End Istvan phase 5
	    theCanvas.repaint();
	} else
	    {
	    Double aDouble;
	    g2D.drawImage(backupImage,null,0,0);
	    x = theCanvas.canvasScale(mevt.getX());
	    y = theCanvas.canvasScale(mevt.getY());
	    aDouble = new Double(x - (pastedImage.getWidth()/2));
	    pasteX =  aDouble.intValue();
	    aDouble = new Double(y - (pastedImage.getHeight()/2));
	    pasteY = aDouble.intValue();
	     // ming 5.2
	    startX = pasteX;
	    startY = pasteY;
		// ming 5.2 end
//	      g2D.drawImage(pastedImage,null,pasteX,pasteY);

	    myDrawImage(theCanvas.right_color);
	    g2D.setColor(Color.black);
	    g2D.setStroke(selectStroke);
	    g2D.draw(new Rectangle(pasteX,pasteY,pastedImage.getWidth(),pastedImage.getHeight()));
	     //Istvan phase 5
	    g2D.setColor(Color.white);
			g2D.setStroke(selectStrokeW);
	    g2D.draw(new Rectangle(pasteX,pasteY,pastedImage.getWidth(),pastedImage.getHeight()));
	    //End Istvan phase 5
	    theCanvas.repaint();
	}
	if (outofbound == true) {
		outofbound = false;
		mouseReleaseAction(mevt, theCanvas);
	}
    }

    /** Allows the user to release the mouse and stop selecting.
     * Sets the flag dragged to false and slected to true.
     * @param mevt MouseEvent release of click
     * @param theCanvas the current main_canvas
     */
    public void mouseReleaseAction(MouseEvent mevt, main_canvas theCanvas) {
    System.out.println("bcd");
	int x, y, width, height;
	//x = theCanvas.canvasScale(mevt.getX());
	//y = theCanvas.canvasScale(mevt.getY());
	/**if (x > theCanvas.widt || y > theCanvas.heig) {
		deSelect(theCanvas);
		return;
	}*/
	if (pasted == false) {
		System.out.println("fjeoiafeajafo;iejafe;oaijfea;e");
	    moved = false;
	    if (dragged) {
		g2D.drawImage(backupImage,null,0,0);
		endX = mevt.getX();
		endY = mevt.getY();
		if (endX > theCanvas.widt)
			endX = theCanvas.widt-1;
		if (endY > theCanvas.heig)
			endY = theCanvas.heig-1;
		x = startX;
		y = startY;
		if (endX < startX) {
		    x = endX;
		}
		if (endY < startY) {
		    y = endY;
		}
		width = Math.abs(startX - endX);
		height = Math.abs(startY - endY);
		System.out.println("X:"+x+" and width:"+width);
		System.out.println("Y:"+y+" and height:"+height);
		selectedImage = theCanvas.getCopy(curImage).getSubimage(x,y,width,height);
		pastedImage = selectedImage;
		g2D.setColor(Color.black);
		g2D.setStroke(selectStroke);
		g2D.draw(new Rectangle(x,y,width,height));
		//Istvan phase 5
				g2D.setColor(Color.white);
				g2D.setStroke(selectStrokeW);
				g2D.draw(new Rectangle(x,y,width,height));
		//End Istvan phase 5
		pasteX = startX;
		pasteY = startY;
		theCanvas.repaint();
		dragged = false;
		selected = true;
	    }
	} else {
	    Double aDouble;
	    g2D.drawImage(backupImage,null,0,0);
	    x = theCanvas.canvasScale(mevt.getX());
	    y = theCanvas.canvasScale(mevt.getY());
	    aDouble = new Double(x - (pastedImage.getWidth()/2));
	    pasteX =  aDouble.intValue();
	    aDouble = new Double(y - (pastedImage.getHeight()/2));
	    pasteY = aDouble.intValue();
	    // ming 5.2
       //     g2D.drawImage(pastedImage,null,pasteX,pasteY);
	//    g2D = backupImage.createGraphics();
	//    theCanvas.setBufferedImage(backupImage);
	    // ming 5.2 end
	    myDrawImage(theCanvas.right_color);
	//    tempImage = curImage;
       //     g2D_temp = tempImage.createGraphics();
	 //   g2D_temp.drawImage(backupImage,null,0,0);
	   // g2D_temp.drawImage(pastedImage,null,pasteX,pasteY);

	    g2D.setColor(Color.black);
	    g2D.setStroke(selectStroke);
	    g2D.draw(new Rectangle(pasteX,pasteY,pastedImage.getWidth(),pastedImage.getHeight()));
	    //Istvan phase 5
	    g2D.setColor(Color.white);
			g2D.setStroke(selectStrokeW);
	    g2D.draw(new Rectangle(pasteX,pasteY,pastedImage.getWidth(),pastedImage.getHeight()));
	    //End Istvan phase 5


	    theCanvas.repaint();

	    dragged = false;
	    selected = true;

	}
    }

    /** Allows the user to select the entire main_canvas.
     * It draws a rectangle around the entire BufferedImage and sets the
     * flag dragged to false, selected to true, and pasted to false.
     * The starting coordinates of the selection are at 0, 0.
     * The ending coordinates of the selection are at the width and height of the
     * image.
     * @param theCanvas main_canvas object
     */
 public void selectAll( main_canvas theCanvas )
    {

    pasteX = 0;
    pasteY = 0;

			selectedImage = theCanvas.getBufferedImage();
			backupImage = theCanvas.getBufferedImage();
			curImage = theCanvas.getBufferedImage();
			pastedImage = theCanvas.getBufferedImage();
			tempImage = theCanvas.getBufferedImage();
	    g2D = curImage.createGraphics();
      g2D.setColor(Color.black);
      g2D.setStroke(selectStroke);
      g2D.draw(
	  new
Rectangle(0,0,selectedImage.getWidth()-1, selectedImage.getHeight()-1));
      //Istvan phase 5
	    g2D.setColor(Color.white);
			g2D.setStroke(selectStrokeW);
	    g2D.draw(new
Rectangle(0,0,selectedImage.getWidth()-1, selectedImage.getHeight()-1));
	    //End Istvan phase 5
theCanvas.setBufferedImage( curImage );

theCanvas.repaint();
			dragged = false;
      selected = true;
      pasted = false;

			startX = 0;
			startY = 0;
	endX = selectedImage.getWidth();
	endY = selectedImage.getHeight();

    }







    /** Returns the selected rectangle.
     * @param theCanvas the current main_canvas
     * @return BufferedImage of selected rectangle image
     */
    public BufferedImage getCopyImage(main_canvas theCanvas) {
	if ((pasted == true) || (selected == true)){
	    g2D.drawImage(backupImage,null,0,0);
	    //g2D.drawImage(pastedImage,null,pasteX,pasteY);
	    myDrawImage(theCanvas.right_color);
	    g2D.setColor(Color.black);
	    g2D.setStroke(selectStroke);
	    g2D.draw(new Rectangle(pasteX,pasteY,pastedImage.getWidth(),pastedImage.getHeight()));
	    //Istvan phase 5
	    g2D.setColor(Color.white);
	    g2D.setStroke(selectStrokeW);
	    g2D.draw(new Rectangle(pasteX,pasteY,pastedImage.getWidth(),pastedImage.getHeight()));
	    //End Istvan phase 5
	    theCanvas.repaint();
	    return pastedImage;
	}/*
	if (selected == true) {
	    g2D.drawImage(backupImage,null,0,0);
	    return selectedImage;
	}*/
	else {
	    return new BufferedImage(10,10,BufferedImage.TYPE_INT_RGB);
	}
    }

    /** Pastes image into selected area but does not update canvas -- see setPastedImage.
     * @param rightColor Color of right-click
     */
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

    /** Copies selected image to be cut, and removes.
     * @param theCanvas the current main_canvas
     * @return BufferedImage of selected portion of canvas
     */
    public BufferedImage getCutImage(main_canvas theCanvas) {
	int temp;
	if (pasted == true) {
	    g2D.drawImage(backupImage,null,0,0);
	    //g2D.setColor(theCanvas.right_color);
	    //g2D.fill(new Rectangle(pasteX,pasteY,pastedImage.getWidth(),pastedImage.getHeight()));
	    //backupImage = theCanvas.getBufferedImage();
	    //curImage = theCanvas.getBufferedImage();
	    //g2D = curImage.createGraphics();
	    //theCanvas.setBufferedImage(curImage);
	    selected = false;
	    pasted = false;
	    moved = false;
	    theCanvas.repaint();
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
	    g2D.setColor(theCanvas.right_color);
	    g2D.fill(new Rectangle(startX,startY,Math.abs(startX-endX),Math.abs(startY-endY)));
	    backupImage = theCanvas.getBufferedImage();
	    curImage = theCanvas.getBufferedImage();
	    g2D = curImage.createGraphics();
	    theCanvas.setBufferedImage(curImage);
	    theCanvas.repaint();
	    selected = false;
	    pasted = false;
	    moved = false;
	    return selectedImage;
	} else {
	    return new BufferedImage(10,10,BufferedImage.TYPE_INT_RGB);
	}
    }





    /** updates canvas with pasted image. Preserves opaqueness.
     * @param theImage BufferedImage to be pasted
     * @param theCanvas the current main_canvas
     * @param x an int representing the x coordinate
     * @param y  an int representing the y coordinate
     */
    // ming 4.24
    public void setPastedImage(BufferedImage theImage, main_canvas theCanvas, int x, int y) {
	// ming 4.24 end
	int temp;
// ming 4.24
	//pasteX=x;
	//pasteY=y;
	// ming 4.24 end
  //	  selectedImage = theImage;
	System.out.println("pastx " + pasteX );
	System.out.println("pasty " + pasteY );
	// ming 5.2
	if (pasted == true) {
	    System.out.println("pasted true in setpasted in select ALL");
	    g2D.drawImage(backupImage,null,0,0);
	    myDrawImage(theCanvas.right_color);
	    theCanvas.setBufferedImage(curImage);
	    backupImage = theCanvas.getBufferedImage();
	    //g2D.drawImage(theImage,null,0,0);
	  //  g2D.drawImage(selectedImage,null,0,0);
	    //myDrawImage(theCanvas.right_color);
	} else if (selected == true){
	    System.out.println("selected true in setpasted in select ALL");
	    g2D.drawImage(backupImage,null,0,0);
	    System.out.println("pasted isn't true");
	}
//	      g2D.drawImage(theImage,null,0,0);

	pastedImage = theImage;

   //	  curImage = theCanvas.getBufferedImage();
    //	  backupImage = theCanvas.getBufferedImage();
     //	  g2D = curImage.createGraphics();
//	  g2D.drawImage(pastedImage,null,0,0);
	pasteX=x;
	pasteY=y;
	myDrawImage(theCanvas.right_color);

	g2D.setColor(Color.black);
	g2D.setStroke(selectStroke);
	// ming 4.24
	g2D.draw(new Rectangle(x,y,pastedImage.getWidth(),pastedImage.getHeight()));
	// ming 4.24 end
	//Istvan phase 5
	g2D.setColor(Color.white);
		g2D.setStroke(selectStrokeW);

		g2D.draw(new Rectangle(x,y,pastedImage.getWidth(),pastedImage.getHeight()));
	//End Istvan phase 5
	theCanvas.setBufferedImage(curImage);
	// ming 5.2 end
	pasted = true;
	selected = true;
	theCanvas.repaint();

	System.out.println("after repaint");
    }

    /** This function returns the current state of the flag pasted.
     * @return boolean representing the flag pasted.
     */
    public boolean getPasted(){return pasted;}

    /** Resets canvas to backup image and resets all data members and flags to original values.
     * @param theCanvas main_canvas
     */
    public void deSelect(main_canvas theCanvas) {
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

    /** This function returns the current state of the flag selected.
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


    /** sets the opaque flag.
     * @param value boolean opaqueness passed in
     * @see #getOpaque
     */
    public void setOpaque(boolean value) { drawOpaque = value; }

    /** This function returns true if it is set to opaque, false
     * otherwise.
     * @return boolean representing opaque
     * @see #setOpaque
     */
    public boolean getOpaque(){return drawOpaque;}

    /** clears the selected portion of image.
     * @param theCanvas the current main_canvas
     */
    public void clear(main_canvas theCanvas) {
	int temp;
	if (selected == true) {
	    System.out.println("IN TRUEEEEEEEEEEEEEEEEEE");
	    if(selectedImage==null){
		System.out.println("DAMN ITS NULL");
	    }
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
	    g2D.setColor(theCanvas.right_color);
	    g2D.fill(new Rectangle(startX,startY,Math.abs(startX-endX),
				   Math.abs(startY-endY)));

	    selected = false;
	    moved = false;
	    pasted = false;
	} else if (pasted == true) {
	    System.out.println("IN ELSEEEEEEEEEEEEEEEEEEEEEE");
	    g2D.drawImage(backupImage,null,0,0);
	    selected = false;
	    moved = false;
	    pasted = false;
	}
	System.out.println("end");
	theCanvas.repaint();
    }

   /** This function returns the Color of the g2d.
    * @return Color representing that of g2D
    */
   public Color getG2dColor(){return g2D.getColor();}
   /** This function returns the current state of the flag dragged.
    * @return boolean representing the flag dragged
    */
   public boolean getDragged(){return dragged;}
   
    /** This function the state of anything being selected.
    * @return boolean true if area is selected, false if not.
    */
   public boolean isSelected(){ return selected; }

}
