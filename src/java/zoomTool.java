import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

/** The zoomTool is used to control and adjust the canvas's zooming size.
 * It records the current canvas's zooming size and adjusts the size by listening for a MouseEvent.
 * The state of zoomTool depends on the current canvas's zooming size.
 *
 * It should work with all operating systems and hardware.
 * There are no variances and no security constraints.
 *
 * @author TerpPaint
 * @version 2.0
 */
public class zoomTool implements ourTool {
    /** The current zooming size of the canvas.	 Default value is 1.
     */
    double theZoom;
    /** The x of a mouse click.
     */
    int x;
    /** The y of a mouse click.
     */
    int y;
    /** Whether the user dragged or not. if true the user has dragged and so done a box zoom.
     */
    boolean dragged;

	/** Holds the current, backup, selected and pasted image.
     */
    BufferedImage backupImage;
    /** Holds the current, backup, selected and pasted image.
	     */
    BufferedImage curImage;
    /**Holds a Graphics2D.
	     */
    public Graphics2D g2D;
	/** Holds the stroke that is selected.
     */
    public BasicStroke selectStroke;
    /** Creates a zoomTool object and sets the zoom to 1.
     * It takes in no parameters or null arguments.  It does not return anything.
     * There are no algorithms of any kind and no variances and OS dependencies.
     * There should not be any exceptions or security constraints.
     */
    public zoomTool() {
	super();
	theZoom = 1; /*FAULT::   theZoom = 2;*/ 
	selectStroke = new BasicStroke(1.f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,8.f,new float[]{6.f,6.f},0.f);

    }

    /** Adjusts the canvas's zooming size by listening for the MouseEvent.
     * If the left mouse button is clicked and the current canvas's zooming size
     * is less than 8.0, the new zooming size will be the twice size of the current
     * size.  If the right mouse button is clicked and the current canvas' zooming
     * size is greater than 1.0, the new zooming size will be the half size of the current size.
     * In addition, the new zooming size will be recorded as the state of zoomTool.
     *
     * @param mevt the mouse event which will determine the zooming size.
     * @param theCanvas the reference of main_canvas whose zooming size will be adjusted.
     */
    public void clickAction(MouseEvent mevt, main_canvas theCanvas) {
	theZoom=theCanvas.getZoom();
		dragged=false;
		x=(int)(mevt.getX()); /*FAULT::   x = 25;*/
	y=(int)(mevt.getY());
	System.out.println("click at x "+x+" y "+y);
	backupImage = theCanvas.getBufferedImage();
		curImage = theCanvas.getBufferedImage();


		// ming 4.12
		//theCanvas.setBufferedImage(curImage);
		// ming 4.12 end
	g2D = curImage.createGraphics();
	// ming 4.12
	//if (SwingUtilities.isLeftMouseButton(mevt) || theCanvas.zoomFactor>1)
	// ming 4.12 end
		theCanvas.repaint();
    }

    /** Allows the user to drag the mouse, but is unnecessary for zooming. It was
     * defined because zoomTool implements OurTool.
     * @param mevt mouse event.
     * @param theCanvas the reference of main_canvas.
     */
    public void dragAction(MouseEvent mevt, main_canvas theCanvas) {
		 g2D.drawImage(backupImage,null,0,0);
	 //dragged = true;
	 int endX = mevt.getX();
	 int endY = mevt.getY();
	 int myX = x;
	 int myY = y;
	 if (endX < x) {
	     myX = endX;
	 }
	 if (endY < y) {
	     myY = endY;
	 }
	 int width = Math.abs(x - endX);
	 int height = Math.abs(y - endY);
	 g2D.setColor(Color.black);
	 g2D.setStroke(selectStroke); /*FAULT::   g2D.setStroke(null);*/
	 
	 g2D.draw(new Rectangle(x,y,width,height));
	 //theCanvas.setBufferedImage(curImage);
	 // ming 4.12
	//if (SwingUtilities.isLeftMouseButton(mevt) || theCanvas.zoomFactor>1)
	// ming 4.12 end
	 theCanvas.repaint();
    }

    /** Allows the user to release the mouse, but is unnecessary for zooming. It was
     * defined because zoomTool implements OurTool.
     * @param mevt mouse event
     * @param theCanvas the reference of main_canvas
     */
    public void mouseReleaseAction(MouseEvent mevt, main_canvas theCanvas) {

	// ming 4.12
	//theCanvas.setBufferedImage(backupImage);
	// ming 4.12 end
	int endX=(int)(mevt.getX());
		int endY=(int)(mevt.getY());
		if(endX!=x&&endY!=y)
			dragged=true;
	if(!dragged){
			double currentZoom = theCanvas.getZoom();
		if (SwingUtilities.isLeftMouseButton(mevt)) {
		if( currentZoom < 8.0 ){
		theCanvas.setZoom(currentZoom*2.0);
		theCanvas.setOldZoom(currentZoom);
		}
		} else {
		if( currentZoom > 1.0 ){ 
			theCanvas.setZoom(currentZoom*0.5);
			theCanvas.setOldZoom(currentZoom);
		}
		}
		theZoom = theCanvas.getZoom();
       //Istvan phase 5
		//Point focus=new Point((int)(x),(int)(y));
		Point focus=new Point((int)(x*theZoom),(int)(y*theZoom));
			theCanvas.pictureScrollPane.getViewport().setExtentSize(new Dimension(theCanvas.getBufferedImage().getWidth(),theCanvas.getBufferedImage().getHeight()));
			theCanvas.pictureScrollPane.getViewport().setViewSize(new Dimension((int)theZoom,(int)theZoom));
			//theCanvas.repaint();
			//theCanvas.pictureScrollPane.getViewport().setViewPosition(theCanvas.pictureScrollPane.getViewport().toViewCoordinates(new Point(0,0)));
			theCanvas.pictureScrollPane.getViewport().setViewPosition(theCanvas.pictureScrollPane.getViewport().toViewCoordinates(focus));

			System.out.println(x+" "+y+" is "+theCanvas.pictureScrollPane.getViewport().toViewCoordinates(focus));

	//End Istvan phase 5
		// ming 4.12
		//if ((SwingUtilities.isLeftMouseButton(mevt) || theCanvas.zoomFactor>=1) && theCanvas.main_image.getWidth()*theCanvas.zoomFactor*theCanvas.main_image.getHeight()*theCanvas.zoomFactor<12000000)
		// ming 4.12 end
		theCanvas.repaint();
	  }else {//dragged
		theZoom=theCanvas.getZoom();
		int centerX=(x+endX)/2;
		int centerY=(y+endY)/2;
		double zoomFactorX=(endX-x);
		if (zoomFactorX<0)
			zoomFactorX=(x-endX);
		double zoomFactorY=(endY-y);
		if (zoomFactorY<0)
			zoomFactorY=(y-endY);
		double width=theCanvas.pictureScrollPane.getViewport().getExtentSize().getWidth();
		double height=theCanvas.pictureScrollPane.getViewport().getExtentSize().getHeight();
		//System.out.println(width+" "+height);
		double zoomFactor=width/zoomFactorX;
		double check=height/zoomFactorY;
		if (check<zoomFactor)//zoomFactor should acoomodate the largest dimension of the box selected
			zoomFactor=check;
		double currentZoom = theCanvas.getZoom();
		zoomFactor=(int)zoomFactor;
		theCanvas.setZoom(zoomFactor);
		System.out.println("zoomFactor to: "+zoomFactor);
		theCanvas.setOldZoom(currentZoom);
		theZoom=theCanvas.getZoom();
		int myX=x;
		if (endX<x)
			myX=endX;
		int myY=y;
		if (endY<y)
			myY=endY;
		//double xFact=(double)myX/(double)theCanvas.getBufferedImage().getWidth();
		//double yFact=(double)myY/(double)theCanvas.getBufferedImage().getHeight();
		//myX=(int)(xFact*theCanvas.pictureScrollPane.getViewport().getExtentSize().getWidth());
		//myY=(int)(yFact*theCanvas.pictureScrollPane.getViewport().getExtentSize().getHeight());

		//System.out.println(x+" "+y+" "+endX+" "+endY+" "+myX+" "+myY+" "+(int)(myX*theZoom)+" "+(int)(myY*theZoom));
		Point focus=new Point((int)(myX*theZoom),(int)(myY*theZoom));
		theCanvas.pictureScrollPane.getViewport().setExtentSize(new Dimension(theCanvas.getBufferedImage().getWidth(),theCanvas.getBufferedImage().getHeight()));
		theCanvas.pictureScrollPane.getViewport().setViewSize(new Dimension((int)zoomFactorX,(int)zoomFactorY));
		//theCanvas.repaint();
		//theCanvas.pictureScrollPane.getViewport().setViewPosition(theCanvas.pictureScrollPane.getViewport().toViewCoordinates(new Point(0,0)));
		theCanvas.pictureScrollPane.getViewport().setViewPosition(theCanvas.pictureScrollPane.getViewport().toViewCoordinates(focus));

	    //theCanvas.pictureScrollPane.getViewport().setViewPosition(focus);
	    System.out.println(theCanvas.pictureScrollPane.getViewport().getViewPosition().getX()+" "+theCanvas.pictureScrollPane.getViewport().getViewPosition().getY());
	    // ming 4.12
	    if (SwingUtilities.isLeftMouseButton(mevt) || theCanvas.zoomFactor>=1)
	    {//Istvan phase 5 I bumped down the number for cutting off zoom
		if (theCanvas.main_image.getWidth()*theCanvas.zoomFactor*theCanvas.main_image.getHeight()*theCanvas.zoomFactor<10000000)
	       // ming 4.12 end
		     theCanvas.repaint();
		else theCanvas.zoomFactor = theCanvas.oldZoomFactor;
	    }
	  }

    }

    /** Returns the value of theZoom, the state recorded by zoomTool, as an int.
     * There are no OS dependencies and variances.  No security constraints.
     * @return theZoom
     */
    public int getZoom() { 
    	return (int)theZoom; /*FAULT::   return (int)theZoom*2;*/
    }

}
