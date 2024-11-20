/*
 * TerpOfficeAppInterface.java
 *
 * Created on March 25, 2002, 4:26 PM
 */

/**
 *
 * @author  Terp Paint
 * @version 2.0
 */

import java.io.*;

/** TerpOffice application interface, specifies three methods to be implemented.
 */
public interface TerpOfficeAppInterface {
    /** Closes the application.
     */
    public void close();

    /** Launches the specified file.
     * @param file the file to be launched
     */
    public void launch(File file);

    /** Determines if the application is still alive.
     * @return boolean flag
     */
    public boolean alive();
}

