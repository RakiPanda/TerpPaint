import javax.swing.text.EditorKit;

import java.net.URL;

import java.util.*;

import java.io.*;

import java.awt.*;

import javax.swing.event.*;

import javax.swing.*;

import javax.swing.text.html.*;

import java.awt.event.ActionEvent;

import java.awt.event.*;


/** This class extends JFrame, and takes an html document and makes a JFrame from it, with the text and images in the html document.
 */
public class HTMLDisplay extends JFrame {

    /** scrollPane holds the contents of the document in display.
     */
    JScrollPane scrollPane;

    /** Constructor that takes the file name and the title
     *
     * There are no OS/Hardware dependencies and no variances.	There is no need for any security constraints and no references to external specifications.
     *
     * @param s string of filename
     * @param t title of document
     */
    HTMLDisplay(String s, String t) {

	JEditorPane jep = new JEditorPane();
	jep.setEditable(false);
	EditorKit htmlKit = jep.getEditorKitForContentType("text/html");
	HTMLDocument doc = (HTMLDocument)
	htmlKit.createDefaultDocument();
	jep.setEditorKit(htmlKit);

	try {
	   URL u = new URL(s);
       //InputStream in = u.openStream();
       //jep.read(in, doc);
	   jep.setPage(u);
	}
	catch (Exception e) {
	   System.err.println(e);
	}

	scrollPane = new JScrollPane(jep);
     //JPanel f = new JPanel();

     //JFrame f = new JFrame(t);
     //f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

     //f.getContentPane().add(scrollPane);
  //   f.setSize(700, 700);
    // f.setVisible(true);
	}
	/*public static void main(String[] arg){
		new
HTMLDisplay("http://www.cs.umd.edu/~atif/GUITAR-distribution/manuals/JavaGUIRipper.htm",
"User Manual");
	}*/
    /** returns scrollpane
     * There are no OS/Hardware dependencies and no variances.	There is no need for any security constraints and no references to external specifications.
     * @return returns scrollpane
     */
    public JScrollPane getScrollPane()
    {

       return scrollPane;
    }



}


/*
public class HTMLDisplay extends JFrame {

    HTMLDisplay(String s, String t) {

    JEditorPane jep = new JEditorPane();
EditorKit htmlKit = jep.getEditorKitForContentType("text/html");
     HTMLDocument doc = (HTMLDocument) htmlKit.createDefaultDocument();

     jep.setEditable(false);






    try {

       URL u = new URL(s);

       //InputStream in = u.openStream();

       //jep.read(in, doc);

       jep.setPage(u);
     }

     catch (Exception e) {

	 jep.setEditorKit(htmlKit);
       System.err.println(e);

     }




     JScrollPane scrollPane = new JScrollPane(jep);

     JFrame f = new JFrame(t);

     f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

     f.getContentPane().add(scrollPane);

     f.setSize(700, 700);

     f.show();

    }

	public static void main(String[] arg){

		new HTMLDisplay("http://www.cs.umd.edu/~atif/GUITAR-distribution/manuals/JavaGUIRipper.htm", "User Manual");

	}



}
*/
