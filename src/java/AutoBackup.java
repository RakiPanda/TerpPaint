/*
 * AutoBackup.java
 *
 * Created on February 10, 2003, 6:57 PM
 */

/**
 *
 * @author Terp Paint
 * @version 2.0
 */

import java.util.*;
import java.io.*;

/** This class does a save in certain iteration automatically.
 * The state should be hidden.
 * The OS is windows.
 * There is no implementation variances.
 * There is no security constaints.
 * There is no external secification.
 */

public class AutoBackup extends Thread{
    /** TerpPaint object creation.
    */
    TerpPaint TerpPaint;

    /** This is a copy construction of AutoBackUp object.
     * The arguement value should be a TerpPaint object.
     * Null value can not be assign.
     * There is no return value.
     * This creates a new TerpPaint object and copy the old one into it.
     * OS is windows.
     * There are no implementation variances.
     * Exception is only TerpPaint variables can be pass in.
     * There are no security constraints.
     * @param in This is a TerpPaint variable
     */
public AutoBackup(TerpPaint in){
	TerpPaint = in;
}
//BEGIN AUTO BACKUP
/** This method tests out the class.
 * This method does not have any argument.
 * It does not have NULL values.
 * There is no return values.
 * The methods runs and test the threads.
 * OS is windows.
 * There are no implementation variances.
 * There is a exception e.
 * There are no security constaints.
 */
public void run(){

	int cnt = 0;
	System.out.println("autobackup started");

	while(true){

		try{

			//Thread.sleep(1000); // strictly for testing purpose remove after phase 1
			Thread.sleep(1000 * 60* 4); //commented out for testing purpose only

			File chosen = new File("bk.bmp"); //taken out the increment for saving space

			chosen.createNewFile();

			FileOutputStream fis = new FileOutputStream(chosen);

			converter newImage = new converter();

			newImage.BufferedImageToFile(fis, TerpPaint.center.getBufferedImage(), 0);

			cnt++;
			System.out.println("file backed up");

		}
		catch(Exception e){}

	}

}
}
//END AUTO BACKUP


