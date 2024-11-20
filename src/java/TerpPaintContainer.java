/*
 * TerpPaintContainer.java
 *
 * Created on February 8, 2003, 1:01 PM
 * Modified March,2004
 */

/**
 *
 * @author  Terp Paint
 * @version 3.0
 */
import java.io.*;

/** TerpPaintContainer is used to hold a TerpPaint class object.
 * It also implements TerpOfficeAppInterface.
 * There is no visible state for this class.
 * It works with all operating systems and hardware.
 * The OS is windows.
 * There is no implementation variances.
 * There is no security constaints.
 * There is no external secification.
 */
public class TerpPaintContainer implements TerpOfficeAppInterface {

    /** This is the TerpPaint object which is used to store a TerpPaint object that is passed from the constructor.
     */
    public TerpPaint newInstance;
    
    /** A TerpPaint object array to hold multiple instances of TerpPaint.
    */
    public TerpPaint [] Instances=new TerpPaint[5];
    /** Integer representing the Maximum size. */
	int maxSize=5;
	
	/** An integer that represents the number of instances in 'Instances'.
	*/
	public int size=0;
    /** This is a boolean which is used to store a boolean value which is passed from the constructor.
     */
    boolean standalone;

    /** Constructor of the TerpPaintContainer.
     * It creates new TerpPaintContainer object to operate the program.
     * It is called by main function in TerpPaint class to run the progam.
     * The OS is windows.
     * There is no implementation variances.
     * There is no security constaints.
     * There is no external secification.
     * @param alone Boolean value that indicates the program runs.
     */
    public TerpPaintContainer(boolean alone) {
	standalone = alone;
	newInstance = new TerpPaint(standalone,this);
	size=1;
	Instances[0]=newInstance;

    }
	/** Adds a new window
     *	This method closes Terp Paint main program.
     * The OS is windows.
     * There is no implementation variances.
     * There is no security constaints.
     * There is no external secification.
     */
     public void Add() {
		if(size==maxSize){
			TerpPaint [] temp=Instances;
			Instances=new TerpPaint[2*size];
			int i=0;
			while(i<size){
				Instances[i]=temp[i];
				i++;
			}
			maxSize=2*size;
		}
	newInstance=new TerpPaint(false,this);
	Instances[size]=newInstance;
	size++;
	}
	/** removes a TerpPaint object and returns true if there is only one left
	 * also if you remove the last one creates a new one and again returns true
     *	This method closes Terp Paint main program.
     * The OS is windows.
     * There is no implementation variances.
     * There is no security constaints.
     * There is no external secification.
     * @param removeMe a TerpPaint instance to remove
     * @return true if there is only one TerpPaint instance left false otherwise
     */
	public boolean Remove(TerpPaint removeMe) {
		System.out.println("removing ");
		int i=0;
		boolean retval=false;
		boolean moveDown=false;
		while(i<size){
			System.out.println("is it "+i+" ");
			if(Instances[i]==removeMe){
				//Instances[i].close();
				moveDown=true;
			}
			if(moveDown){
				Instances[i]=Instances[i+1];
			}
			i++;
		}
		size--;
		Instances[i]=null;
		if(size==1){
			retval=true;
		}
		else if(size==0){
			System.out.println("was last");
			Add();
			newInstance.show();
			retval=true;
		}
		return retval;
	}
    /** A TerpPaint object created in TerpPaintContainer class calls a dipose function from TerpPaint class.
     *	This method closes Terp Paint main program.
     * The OS is windows.
     * There is no implementation variances.
     * There is no security constaints.
     * There is no external secification.
     */
    public void close(){
	newInstance.dispose();
    }

    /** This function launches the program and sets a new main canvase image to the newInstance object,
     * which is from the TerpPaint class. It throws an exception if there is any.
     * The OS is windows.
     * There is no implementation variances.
     * There is no security constaints.
     * There is no external secification.
     * @param file FileInputStream object used to set the new main canvas image.
     */
    public void launch(File file){
		if ( file != null ){
			try{
				FileInputStream fis = new FileInputStream(file);
				converter newImage = new converter();
				newInstance.center.setBufferedImage(newImage.FileToBufferedImage(fis));
			}
			catch(Exception fisError){}
		}
		newInstance.show();
    }
	/** This function launches the program and sets a new main canvase image to the newInstance object,
     * which is from the TerpPaint class. It throws an exception if there is any.
     * This size is determined by prompting the user
     * The OS is windows.
     * There is no implementation variances.
     * There is no security constaints.
     * There is no external secification.
     * @param file FileInputStream object used to set the new main canvas image.
     */
    public void launchPrompt(File file){
	if ( file != null ){
	    try{
		FileInputStream fis = new FileInputStream(file);
		converter newImage = new converter();
		newInstance.center.setBufferedImage(newImage.FileToBufferedImage(fis));
	    }
	    catch(Exception fisError){}
	}
	canvas_size canvsz = new canvas_size(newInstance, true);
	canvsz.setLocation( (int) newInstance.getLocation().getX() + 100, (int) newInstance.getLocation().getY()+100 );
	
	canvsz.setVisible(true);

	newInstance.show();
	newInstance.center.setZoom(newInstance.center.getZoom());
		try{
			  FileWriter out = new FileWriter("windowRegistry.txt");
			  BufferedWriter b = new BufferedWriter( out );


			  Double xd = new Double( newInstance.getLocation().getX() );
			  Double yd = new Double(newInstance.getLocation().getY()) ;
			  Double wd = new Double(newInstance.getSize().getWidth());
			  Double hd = new Double(newInstance.getSize().getHeight());
			  Double iwd = new Double(newInstance.center.widt);
			  Double ihd = new Double(newInstance.center.heig);

			  b.write( xd.intValue() + "\n" +
			    yd.intValue() + "\n" +
					wd.intValue() + "\n" +
					hd.intValue() + "\n"+
					iwd.intValue() + "\n" +
					ihd.intValue() + "\n" );
			  b.close();
		}
	catch( Exception e ) {}
    }
    /** This function launches the program and sets a new main canvase image to the newInstance object,
	     * which is from the TerpPaint class. It throws an exception if there is any.
	     * The size is determined by the specified size in preferences
	     * The OS is windows.
	     * There is no implementation variances.
	     * There is no security constaints.
	     * There is no external secification.
	     * @param file FileInputStream object used to set the new main canvas image.
	     */
	    public void launchFixed(File file){
		if ( file != null ){
		    try{
			FileInputStream fis = new FileInputStream(file);
			converter newImage = new converter();
			newInstance.center.setBufferedImage(newImage.FileToBufferedImage(fis));
		    }
		    catch(Exception fisError){}
		}
		newInstance.center.resizeImage(newInstance.myPrefs.Width, newInstance.myPrefs.Height );
		newInstance.show();
			try
		      {
			  FileWriter out = new FileWriter("windowRegistry.txt");
			  BufferedWriter b = new BufferedWriter( out );


			  Double xd = new Double( newInstance.getLocation().getX() );
			  Double yd = new Double(newInstance.getLocation().getY()) ;
			  Double wd = new Double(newInstance.getSize().getWidth());
			  Double hd = new Double(newInstance.getSize().getHeight());
			  Double iwd = new Double(newInstance.center.widt);
			  Double ihd = new Double(newInstance.center.heig);

			  b.write( xd.intValue() + "\n" +
				   yd.intValue() + "\n" +
				   wd.intValue() + "\n" +
				   hd.intValue() + "\n"+
				   iwd.intValue() + "\n" +
				   ihd.intValue() + "\n" );
			  b.close();
		      }
		catch( Exception e ) {}

    }
    /** The TerpPaint object, newInstance created in TerpPaintContainer class, calls the isDisplayable function
     * from the TerpPaint class. It returns a boolean value, which comes from the isDisplayable function.
     * The OS is windows.
     * There is no implementation variances.
     * There is no security constaints.
     * There is no external secification.
     * @return True boolean value if it is displayable, otherwise false.
     */
    public boolean alive(){
	return newInstance.isDisplayable();
    }
}
