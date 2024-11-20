import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.datatransfer.*;
import java.util.*;
import java.awt.image.*;

/** UMClipObj.java is used to implement clipboard functions.
 *There are no OS/Hardware dependencies and no variances.
 *There are no security constraints.
 *There are no references to external specifications.
 *
 * @author TerpPaint
 * @version 4.0
 */

public class UMClipObj implements Transferable, ClipboardOwner
{
    /** This function has no purpose.
     */
    public void main(){}

    /** Array containing the supported DataFlavors. Add or remove as
    * necessary.
    */
    private static final DataFlavor[] flavors = {
	DataFlavor.stringFlavor,
	DataFlavor.imageFlavor,
	DataFlavor.plainTextFlavor
    };

    // The following variables will store the clipboard data
    /** String to store the plain text flavor value.*/
    public String plainTextFlavorValue;
    /** String to store the string flavor value.*/
    public String stringFlavorValue;
    /** String to store the image flavor value.*/ 
    public Image imageFlavorValue;

    /** Constructor. No actions taken.
    */
    UMClipObj(){ }
    /** A constructor taking 2 strings.
    * @param stringText String representing the string text
    * @param plainText String representing the plain text
    */
    UMClipObj(String stringText, String plainText){

	this.plainTextFlavorValue = plainText; /*FAULT::   this.plainTextFlavorValue = stringText;*/ 
	this.stringFlavorValue = stringText;
      //this.imageFlavorValue = imageObject;
    }
	/**
	 * Constructs a UMClipObj object.
	 *
	 * @param imageObject  an Image object representing the image to be constructed with
	 */
    UMClipObj(Image imageObject){
	this.imageFlavorValue = imageObject; /*FAULT::   this.imageFlavorValue = null;*/ 
    }


    /** Returns copy of supported DataFlavors array.
     * @return a DataFlavor array copy of supported DataFlavors
    */
    public DataFlavor[] getTransferDataFlavors() {
	return (DataFlavor[])flavors.clone(); /*Fault:: return null; */

    }


    public boolean isDataFlavorSupported(DataFlavor flavor) {
	for (int i = 0; i < flavors.length; i++) {
	     if (flavor.equals(flavors[i])) {
		   return true; /*FAULT::  return false;*/ 
	     }
	}
	return false;
    }

    /** Returns the clipboard data depending on which DataFlavor is
	requested. */
    public Object getTransferData(DataFlavor flavor)
    throws UnsupportedFlavorException, IOException
    {
	if (flavor.equals(DataFlavor.stringFlavor)) {

	// Insert processing for stringFlavor request here
	    return (Object)stringFlavorValue; /*FAULT::  return (Object)imageFlavorValue;*/

	} else if (flavor.equals(DataFlavor.plainTextFlavor)) {

	    // Insert processing for plainTextFlavor request here
	    return (Object)plainTextFlavorValue;

	} else if (flavor.equals(DataFlavor.imageFlavor)) {

	    // Insert processing for imageFlavor request here
	    return (Object)imageFlavorValue;

	} else {

	    System.out.println("Exception: Attempting to access clipboard with unsupported DataFlavor");
	    throw new UnsupportedFlavorException(flavor);

	}
    }

    public void lostOwnership(Clipboard clipboard,
	Transferable transferable)
	{
	System.out.println ("Lost Clipboard ownership");
	}

	}
	/**** End UMClipObj.java ****/





/**** The following clipboard functions can be implemented anywhere ****/
/*********************************************************************************
private void Copy()
{


String copy_text = new String("this text will be copied to the clipboard");
BufferedImage copy_image = <<<some buffered image object>>>

UMClipObj clip_obj;
clip_obj = new UMClipObj(copy_text, copy_text, copy_image);

Clipboard clipboard;
clipboard=Toolkit.getDefaultToolkit().getSystemClipboard();

try {

clipboard.setContents(clip_obj, this);

}catch (Exception e){

}
}


public void Paste(){

String fromClip = null;
Transferable transferable = null;
Object clipboardContents = null;

clipboard=Toolkit.getDefaultToolkit().getSystemClipboard();

if (clipboard == null){
System.out.println("Clipboard is null");
return;
}
try {
clipboardContents = clipboard.getContents(this);

if (clipboardContents != null){

transferable = clipboard.getContents(this);

if (transferable.isDataFlavorSupported( <<< desired DataFlavor >>> )){

fromClipboard = transferable.getTransferData( <<< desired DataFlavor >>>);

} else
System.out.println("Clipboard data is unknown type");

}
} catch(Exception e){

}


}
*************************************************************************************/
