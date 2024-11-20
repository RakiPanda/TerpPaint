/*
 * about.java
 *
 * Created on April 5, 2003, 12:35 AM
 */

/**
 *
 * @author  yunpengl
 */
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.table.*;
import java.awt.geom.*;
/** This object displays the About window.
 */
public class about {

    /** Creates a new instance of about, creates a JOptionPane that is displayed.
     * There are no OS/Hardware dependencies and no variances.	There is no need for any security constraints and no references to external specifications.
     */
    public about() {
	String message =
	"<html><body bgcolor=ffffff><font size=2 color=black><p><font size=+1 type = arial color=red><b>TerpPaint</b></font> </p><p></p>"+
	"<p><p>Copyright &copy; 2001-2005 Terp Office "+
	"<p></p>"+
	"<b>Version 4.0</b>  <i>Matin Tamizi, David Ziskind, Mo Cheng,<br>Rob Wissman, Shih-Chieh Tao, Svetlana Rabinovich</i><br><br>"+
	"<b>Version 3.0</b>  <i>Ana Bento, James Lawless, Michael Min,<br>     Eli Kim, Kin-Pong Ma, Istvan Laszlo, Ming Luo</i><br><br>"+
	"<b>Version 2.0</b>  <i>Devmann Lee, Yunpeng Li, Eric Liu,<br>	   Mai Nguyen, Tu Nguyen, Won Sun Ouh, Shirin Parsee</i><br><br>"+
	"<b>Version 1.0 </b> <i>Jeffrey Lang, Mark Liu, Wai Liu, Yang Liu,<br>	   Thomas Locke, Greg Mudd, Marshall Mundy</i><br>"+
	//"<ul><li>Devmann Lee</li><li>Yunpeng Li</li><li>Eric Liu</li>"+
	//"<li>Mai Nguyen</li><li>Tu Nguyen</li>" +
	//"<li>Won Sun Ouh</li><li>Shirin Parsee</li></ul></p>"

	"</font></font></body>/html>";



	JOptionPane.showMessageDialog(null,message,
				      "About Terp Paint",
				      JOptionPane.INFORMATION_MESSAGE);
    }

}
