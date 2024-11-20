/*
 * TerpPaintFileFilter.java
 *
 * Created on February 8, 2003, 1:01 PM
 */

/**
 *
 * @author  Terp Paint
 * @version 2.0
 */

import java.io.File;
import java.util.Hashtable;
import java.util.Enumeration;
import javax.swing.*;
import javax.swing.filechooser.*;

/** TerpPaintFileFilter class creates a file filter. If no filters are added, then all
 * files are accepted. It should work with all operating systems and hardware.
 * The OS is windows.
 * There is no implementation variances.
 * There is no security constaints.
 * There is no external secification.
 */
public class TerpPaintFileFilter extends FileFilter {

    /** String representing unknown file type.
     */
    private static String TYPE_UNKNOWN = "Type Unknown";

    /** String representing hidden file.
     */
    private static String HIDDEN_FILE = "Hidden File";

    /** Hashtable which is used to store the filters.
     */
    private Hashtable filters = null;

    /** String which is used for the description of the file.
     */
    private String description = null;

    /** String for the full description of the file.
     */
    private String fullDescription = null;

    /** True if the description contains a extension and
     *is false if the description does not contain an extension.
     */
    private boolean useExtensionsInDescription = true;

    /** Constructor of TerpPaintFileFilter.
     * It initializes the filter hashtable and creates a new hashtable to filter the file.
     * The OS is windows.
     * There is no implementation variances.
     * There is no security constaints.
     * There is no external secification.
     */
    public TerpPaintFileFilter() {
	this.filters = new Hashtable();
    }

    /** This function creates a file filter that accepts files with the given extension.
     * Example: new ExampleFileFilter("jpg");
     * The OS is windows.
     * There is no implementation variances.
     * There is no security constaints.
     * There is no external secification.
     * @param extension String that describes the type of file extension.
     */
    public TerpPaintFileFilter(String extension) {
	this(extension,null);
    }

    /** This function creates a file filter that accepts the given file type.
     * Example: new ExampleFileFilter("jpg", "JPEG Image Images");
     *
     * Note that the "." before the extension is not needed. If
     * provided, it will be ignored.
     *
     * The OS is windows.
     * There is no implementation variances.
     * There is no security constaints.
     * There is no external secification.
     * @param extension String that represents the type of the image file.
     * @param description String used to for the description.
     */
    public TerpPaintFileFilter(String extension, String description) {
	this();
	if(extension!=null) addExtension(extension);
	if(description!=null) setDescription(description);
    }

    /** This function creates a file filter from the given string array.
     * Example: new ExampleFileFilter(String {"gif", "jpg"});
     *
     * Note that the "." before the extension is not needed adn
     * will be ignored.
     *
     * The OS is windows.
     * There is no implementation variances.
     * There is no security constaints.
     * There is no external secification.
     * @param filters an Array of String to be used for filters.
     */
    public TerpPaintFileFilter(String[] filters) {
	this(filters, null);
    }

    /** This function creates a file filter from the given string array and description.
     * Example: new ExampleFileFilter(String {"gif", "jpg"}, "Gif and JPG Images");
     *
     * Note that the "." before the extension is not needed and will be ignored.
     *
     * The OS is windows.
     * There is no implementation variances.
     * There is no security constaints.
     * There is no external secification.
     * @param filters an array of String to be used for filters.
     * @param description a String to be used for description of the file.
     */
    public TerpPaintFileFilter(String[] filters, String description) {
	this();
	for (int i = 0; i < filters.length; i++) {
	    // add filters one by one
	    addExtension(filters[i]);
	}
	if(description!=null) setDescription(description);
    }

    /** Return true if this file should be shown in the directory pane,
     * false if it shouldn't.
     *
     * Files that begin with "." are ignored.
     *
     *
     * The OS is windows.
     * There is no implementation variances.
     * There is no security constaints.
     * There is no external secification.
     * @param f File object that contains image.
     * @return return true if f is in directory or if the extension of the file is not null
     * and the hashtable filter is not null, else return false.
     */
    public boolean accept(File f) {
	if(f != null) {
	    if(f.isDirectory()) {
		return true;
	    }
	    String extension = getExtension(f);
	    if(extension != null && filters.get(getExtension(f)) != null) {
		return true;
	    };
	}
	return false;
    }

    /** This method returns a String object of the extension portion of the file's name.
     *
     *
     * The OS is windows.
     * There is no implementation variances.
     * There is no security constaints.
     * There is no external secification.
     * @param f a File that contains the image.
     * @return return the file extension and if it is null, return null.
     */
     public String getExtension(File f) {
	if(f != null) {
	    String filename = f.getName();
	    int i = filename.lastIndexOf('.');
	    if(i>0 && i<filename.length()-1) {
		return filename.substring(i+1).toLowerCase();
	    };
	}
	return null;
    }

     /** Adds a filetype "dot" extension to filter against.
      *
      * For example: the following code will create a filter that filters
      * out all files except those that end in ".jpg" and ".tif":
      *
      * ExampleFileFilter filter = new ExampleFileFilter();
      * filter.addExtension("jpg");
      * filter.addExtension("tif");
      * The OS is windows.
      * There is no implementation variances.
      * There is no security constaints.
      * There is no external secification.
      * Note that the "." before the extension is not needed and will be ignored.
      * @param extension a String to be added to the end of file as a representation of the file extension.
      */
    public void addExtension(String extension) {
	if(filters == null) {
	    filters = new Hashtable(5);
	}
	filters.put(extension.toLowerCase(), this);
	fullDescription = null;
    }


    /** This function returns a String of the human readable description of this filter. For
     * example: "JPEG and GIF Image Files (*.jpg, *.gif)".
     * The OS is windows.
     * There is no implementation variances.
     * There is no security constaints.
     * There is no external secification.
     * @return returns the full description of the file.
     * @see #setDescription
     */
    public String getDescription() {
	if(fullDescription == null) {
	    if(description == null || isExtensionListInDescription()) {
		fullDescription = description==null ? "(" : description + " (";
		// build the description from the extension list
		Enumeration extensions = filters.keys();
		if(extensions != null) {
			if (extensions.hasMoreElements()) 
				fullDescription += "." + (String) extensions.nextElement();
		    while (extensions.hasMoreElements()) {
			fullDescription += ", " + (String) extensions.nextElement();
		    }
		}
		fullDescription += ")";
	    } else {
		fullDescription = description;
	    }
	}
	return fullDescription;
    }

    /** Sets the human readable description of this filter. For
     * example: filter.setDescription("Gif and JPG Images");
     * The OS is windows.
     * There is no implementation variances.
     * There is no security constaints.
     * There is no external secification.
     * @param description a String used to describe the file information.
     * @see #getDescription
     */
    public void setDescription(String description) {
	this.description = description;
	fullDescription = null;
    }

    /** This function determines whether the extension list (.jpg, .gif, etc) should
     * show up in the human readable description.
     *
     * Only relevent if a description was provided in the constructor
     * or using setDescription();
     * The OS is windows.
     * There is no implementation variances.
     * There is no security constaints.
     * There is no external secification.
     * @param b a boolean value determines true or false of useExtensionInDescription status.
     */
    public void setExtensionListInDescription(boolean b) {
	useExtensionsInDescription = b;
	fullDescription = null;
    }

    /** This function returns whether the extension list (.jpg, .gif, etc) should
     * show up in the human readable description. It returns a boolean value.
     *
     * Only relevent if a description was provided in the constructor
     * or using setDescription();
     * The OS is windows.
     * There is no implementation variances.
     * There is no security constaints.
     * There is no external secification.
     * @return returns a boolean value which is the private bool useExtensionsInDescription.
     */
    public boolean isExtensionListInDescription() {
	return useExtensionsInDescription;
    }
}
