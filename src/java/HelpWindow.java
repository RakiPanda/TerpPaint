/*
 * HelpWindow.java
 *
 * Created on April 6, 2003, 1:31 PM
 */

/**
 *
 * @author  mjnguyen
 */


import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Toolkit.*;
import java.awt.Image.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.colorchooser.*;
import java.io.*;
import java.awt.image.*;
import java.awt.print.*;
import java.awt.datatransfer.Clipboard.*;
import java.awt.datatransfer.*;
import com.sun.image.codec.jpeg.*;
import java.applet.*;
import javax.swing.text.EditorKit;


/** This class is the JFrame that allow navigation through help menus.
 */
public class HelpWindow extends JFrame {

    /** Creates a new instance of HelpWindow, creates JFrame the same size as TerpPaint program
     * There are no OS/Hardware dependencies and no variances.	There is no need for any security constraints and no references to external specifications.
     * @param TerpPaintProgram TerpPaint
     */
    public HelpWindow(JFrame TerpPaintProgram) {
	setLocation( (int) TerpPaintProgram.getLocation().getX() + 100, (int) TerpPaintProgram.getLocation().getY()+100 );
	setTitle( "TerpPaint Help" );
	/*FAULT::setTitle("nohelp");*/
	setSize( new Dimension(500, 400) );

	file2 = new File( "windowRegistry.txt");
	System.out.println(file2.getAbsolutePath());
	file = new File( file2.getAbsoluteFile().getParent() );

	System.out.println(file.getAbsolutePath());

	menuBar = new javax.swing.JMenuBar();


	terpPaintHelp = new javax.swing.JMenu();
	terpPaintHelp.setText("Contents");




	terpPaintOverview = new javax.swing.JMenuItem();
	terpPaintOverview.setText( "Overview" );

	terpPaintOverview.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "terpPaintOverview.html");
	    }
	});

	//action listener.

	terpPaintHelp.add( terpPaintOverview );





	createPictures = new javax.swing.JMenu();
	createPictures.setText("Create Pictures");

	// action listener.

	drawLine = new javax.swing.JMenuItem();
	drawLine.setText( "To draw a straight line" );

	drawLine.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "drawLine.htm");  /*FAULT:: */
	    
	    }
	});

	createPictures.add( drawLine );


	drawPencil = new javax.swing.JMenuItem();
	drawPencil.setText( "To draw a free-form line" );

	drawPencil.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
	    	
		HelpMenuActionPerformed(evt, "drawPencil.html");  /*FAULT:: */
	    }
	});

	createPictures.add( drawPencil);




       drawCurve = new javax.swing.JMenuItem();
       drawCurve.setText( "To draw a curved line" );

	drawCurve.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "drawCurve.html");  /*FAULT:: */
	    }
	});

	createPictures.add( drawCurve );

	drawEllipse = new javax.swing.JMenuItem();
	drawEllipse.setText( "To draw an ellipse or circle" );

	drawEllipse.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "drawEllipse.html");  /*FAULT:: */
	    }
	});

	createPictures.add( drawEllipse );
	drawRect = new javax.swing.JMenuItem();
	drawRect.setText( "To draw a rectangle or square" );

	drawRect.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "drawRect.html");  /*FAULT:: */
	    }
	});

	createPictures.add( drawRect );
	drawPoly = new javax.swing.JMenuItem();
	drawPoly.setText( "To draw a polygon" );

	drawPoly.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "drawPoly.html");  /*FAULT:: */
	    }
	});

	createPictures.add( drawPoly );
	text = new javax.swing.JMenuItem();
	text.setText( "To type and format text" );

	text.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "text.html");  /*FAULT:: */
	    }
	});

	createPictures.add( text );

	eraser = new javax.swing.JMenuItem();
	eraser.setText( "To erase a small area" );

	eraser.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "eraser.html");  /*FAULT:: */
	    }
	});

	createPictures.add( eraser);
	eraseselect = new javax.swing.JMenuItem();
	eraseselect.setText( "To erase a large area" );

	eraseselect.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "eraseselect.html");  /*FAULT:: */
	    }
	});

	createPictures.add( eraseselect);
	eraseimage = new javax.swing.JMenuItem();
	eraseimage.setText( "To erase an entire image" );

	eraseimage.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "eraserimage.html");  /*FAULT:: */
	    }
	});

	createPictures.add( eraseimage);
	stretchskew = new javax.swing.JMenuItem();
	stretchskew.setText( "To stretch or skew the image" );

	 stretchskew.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "stretchskew.html");  /*FAULT:: */
	    }
	});

	createPictures.add(  stretchskew);
	imagesize = new javax.swing.JMenuItem();
	imagesize.setText( "To change the size of your picture" );

	imagesize.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "imagesize.html");  /*FAULT:: */
	    }
	});

	createPictures.add( imagesize);



	terpPaintHelp.add( createPictures );


	workWithColor = new javax.swing.JMenu();
	workWithColor.setText("Work with Colors");


	setColors = new javax.swing.JMenuItem();
	setColors.setText( "To set the default foreground and background colors" );

	setColors.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "setColors.html");  /*FAULT:: */
	    }
	});

	workWithColor.add(setColors);

	fillArea = new javax.swing.JMenuItem();
	fillArea.setText( "To fill an area or object with color" );

	fillArea.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "fillArea.html");   /*FAULT:: */   
	    }
	});

	workWithColor.add(fillArea);
	paintBrush = new javax.swing.JMenuItem();
	paintBrush.setText( "To paint with a brush" );

	paintBrush.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "paintBrush.html");   /*FAULT:: */
	    }
	});

	workWithColor.add(paintBrush);
	spray = new javax.swing.JMenuItem();
	spray.setText( "To create an airbrush effect" );

	spray.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "spray.html");  /*FAULT:: */
	    }
	});

	workWithColor.add(spray);
	customColors = new javax.swing.JMenuItem();
	customColors.setText( "To create custom colors" );

	customColors.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "customColors.html");  /*FAULT:: */
	    }
	});

	workWithColor.add(customColors);
	bw = new javax.swing.JMenuItem();
	bw.setText( "To use black and white instead of color" );

	bw.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "bw.html");  /*FAULT:: */
	    }
	});

	workWithColor.add(bw);
	copyColor = new javax.swing.JMenuItem();
	copyColor.setText( "To copy color from one area or object to another" );

	copyColor.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "copyColor.html");  /*FAULT:: */
	    }
	});

	workWithColor.add(copyColor);

	magicWand = new javax.swing.JMenuItem();
	magicWand.setText( "To select an area of a single color (Magic Wand Tool)" );

	magicWand.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "magicWand.html");   /*FAULT:: */
	    }
	});

	workWithColor.add(magicWand);




	// action listener

	terpPaintHelp.add( workWithColor );

	workWithPictures = new javax.swing.JMenu("Work with Pictures");
	// action listener

	pastefrom = new javax.swing.JMenuItem();
	pastefrom.setText( "To insert a bitmap into the current picture" );

	pastefrom.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "pastefrom.html");   /*FAULT:: */
	    }
	});

	workWithPictures.add(pastefrom);

	fliprotate = new javax.swing.JMenuItem();
	fliprotate.setText( "To flip or rotate a picture" );

	fliprotate.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "fliprotate.html");  /*FAULT:: */
	    }
	});

	workWithPictures.add(fliprotate);

	filter = new javax.swing.JMenuItem();
	filter.setText( "To apply image filters" );

	filter.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "filter.html");   /*FAULT:: */
	    }
	});

	workWithPictures.add(filter);

	copypaste = new javax.swing.JMenuItem();
	copypaste.setText( "To copy and paste part of a picture" );

	copypaste.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "copypaste.html");  /*FAULT:: */
	    }
	});

	workWithPictures.add(copypaste);

	select = new javax.swing.JMenuItem();
	select.setText( "To select part of a picture" );

	select.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "select.html");   /*FAULT:: */
	    }
	});

	workWithPictures.add(select);

	move = new javax.swing.JMenuItem();
	move.setText( "To move part of a picture" );

	move.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "move.html");   /*FAULT:: */
	    }
	});

	workWithPictures.add(move);

	copymove = new javax.swing.JMenuItem();
	copymove.setText( "To copy-move part of a picture" );

	copymove.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "copymove.html");   /*FAULT:: */
	    }
	});

	workWithPictures.add(copymove);

	zoomDrag = new javax.swing.JMenuItem();
	zoomDrag.setText( "To zoom in on part of a picture" );

	zoomDrag.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "zoomDrag.html");   /*FAULT:: */
	    }
	});

	workWithPictures.add(zoomDrag);

	zoom = new javax.swing.JMenuItem();
	zoom.setText( "To change the zoom" );

	zoom.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "zoom.html");   /*FAULT:: */
	    }
	});

	workWithPictures.add(zoom);

	slideshow = new javax.swing.JMenuItem();
	slideshow.setText( "To view a slide show" );

	slideshow.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "slideshow.htm");   /*FAULT:: */
	    }
	});

	workWithPictures.add(slideshow);



	terpPaintHelp.add( workWithPictures );


	workWithLayers = new javax.swing.JMenu("Work with Layers");
	// action listener

	addLayer = new javax.swing.JMenuItem();
	addLayer.setText( "To add a layer" );

	addLayer.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "addLayer.html");   /*FAULT:: */
	    }
	});

	workWithLayers.add(addLayer);

	animate = new javax.swing.JMenuItem();
	animate.setText( "To view an animation of the layers" );

	animate.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "animate.html");   /*FAULT:: */
	    }
	});

	workWithLayers.add(animate);

	changeName = new javax.swing.JMenuItem();
	changeName.setText( "To change the name of a layer" );

	changeName.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "changeName.html");   /*FAULT:: */
	    }
	});

	workWithLayers.add(changeName);

	removeLayer = new javax.swing.JMenuItem();
	removeLayer.setText( "To remove a layer by name" );

	removeLayer.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "removeLayer.html");   /*FAULT:: */
	    }
	});

	workWithLayers.add(removeLayer);

	removeCurrent = new javax.swing.JMenuItem();
	removeCurrent.setText( "To remove the current layer" );

	removeCurrent.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "removeCurrent.html");   /*FAULT:: */
	    }
	});

	workWithLayers.add(removeCurrent);

	flatten = new javax.swing.JMenuItem();
	flatten.setText( "To flatten the layers" );

	flatten.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "flatten.html");  /*FAULT:: */
	    }
	});

	workWithLayers.add(flatten);

	moveUp = new javax.swing.JMenuItem();
	moveUp.setText( "To move a layer up" );

	moveUp.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "moveUp.html");   /*FAULT:: */
	    }
	});

	workWithLayers.add(moveUp);

	moveDown = new javax.swing.JMenuItem();
	moveDown.setText( "To move a layer down" );

	moveDown.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "moveDown.html");   /*FAULT:: */
	    }
	});

	workWithLayers.add(moveDown);

	moveFront = new javax.swing.JMenuItem();
	moveFront.setText( "To move a layer to the front" );

	moveFront.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "moveFront.html");   /*FAULT:: */
	    }
	});

	workWithLayers.add(moveFront);

	moveBack = new javax.swing.JMenuItem();
	moveBack.setText( "To move a layer to the back" );

	moveBack.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "moveBack.html");  /*FAULT:: */
	    }
	});

	workWithLayers.add(moveBack);



	terpPaintHelp.add( workWithLayers );


	setPreferences = new javax.swing.JMenu("Set Preferences");
	// action listener

	undo = new javax.swing.JMenuItem();
	undo.setText( "To set the undo level preference" );

	undo.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "undo.html");   /*FAULT:: */
	    }
	});

	setPreferences.add(undo);

	interval = new javax.swing.JMenuItem();
	interval.setText( "To set the animation interval" );

	interval.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "interval.html");   /*FAULT:: */
	    }
	});

	setPreferences.add(interval);

	language = new javax.swing.JMenuItem();
	language.setText( "To set the language preference" );

	language.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "language.html");   /*FAULT:: */
	    }
	});

	setPreferences.add(language);

	transitions = new javax.swing.JMenuItem();
	transitions.setText( "To rcustomize animation transitions" );

	transitions.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "transitions.html");   /*FAULT:: */
	    }
	});

	setPreferences.add(transitions);

	loop = new javax.swing.JMenuItem();
	loop.setText( "To set the number of loops for animation" );

	loop.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "loop.html");  /*FAULT:: */
	    }
	});

	setPreferences.add(loop);

	pasteImport = new javax.swing.JMenuItem();
	pasteImport.setText( "To set the Paste/Import To location preference" );

	pasteImport.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "pasteImport.html");   /*FAULT:: */
	    }
	});

	setPreferences.add(pasteImport);

	pasteFile = new javax.swing.JMenuItem();
	pasteFile.setText( "To set the file creation preference on Paste To a File" );

	pasteFile.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "pasteFile.html");  /*FAULT:: */
	    }
	});

	setPreferences.add(pasteFile);

	newSize = new javax.swing.JMenuItem();
	newSize.setText( "To set the starting size when a new file is opened" );

	newSize.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		HelpMenuActionPerformed(evt, "newSize.html");  /*FAULT:: */
	    }
	});

	setPreferences.add(newSize);


	terpPaintHelp.add( setPreferences );


	String filename = new String("file://"+ file.separator +
				     file.getAbsolutePath() +
				     file.separator + "help" + file.separator +
				     "welcome.htm") ;

	fileshown = new HTMLDisplay( filename, "TerpPaint Help Welcome");
	try
	{


	    //this.getContentPane().add(fileshown);
	    this.getContentPane().add( fileshown.getScrollPane() );

	}
	catch( Exception e ){}


	menuBar.add(terpPaintHelp) ;

	setJMenuBar( menuBar ) ;


	//pack() ;

	setVisible( true ) ;

    };


    /** This function creates the HTMLDisplay for the file selected through the menu selection.
     * There are no OS/Hardware dependencies and no variances.	There is no need for any security constraints and no references to external specifications.
     * @param evt ActionEvent obj
     * @param filen filename of the html to display
     */
    public void HelpMenuActionPerformed( java.awt.event.ActionEvent evt, String filen )
    {

    	
    	
	String filename = new String("file://" + file.separator +
				     file.getAbsolutePath() + file.separator +
				     "help" + file.separator + filen) ;
	System.out.println(" FILENAME " + filename ) ;
	try
	{

	   fileshown = new HTMLDisplay( filename, "TerpPaint Help Welcome");

	    //this.getContentPane().add(fileshown);
	    this.setContentPane( fileshown.getScrollPane() );

	    //pack() ;

	    setVisible(true);
	    /*FAULT::setVisible(false);*/

	}
	catch( Exception e ){}



    }


    /** File that contains the filepath of the html file to be decoded.
     */
    File file;
    /** File that contains the parent path of file to be opened.
     */
    File file2;

    /** The menubar for helpwindow.
     */
    JMenuBar menuBar;
    /** JMenu titled TerpPaint Help.
     */
    JMenu terpPaintHelp;
    /** JMenuItem to see the terppaint overview.
     */
    JMenuItem terpPaintOverview;
    /** JMenu titled TerpPaint Help.
     */
    JMenu createPictures;
    /** JMenu titled Work with Color.
     */
    JMenu workWithColor;
    /** JMenu titled Work With Pictures.
     */
    JMenu workWithPictures;
    /** JMenu titled Work With Layers.
     */
    JMenu workWithLayers;
    /** JMenu titled Set Preferences.
     */
    JMenu setPreferences;


    /** JMenuItem to see the line tool help.
     */
    JMenuItem drawLine;
    /** JMenuItem to see the pencil tool help.
     */
    JMenuItem drawPencil;
    /** JMenuItem to see the curve tool help.
     */
    JMenuItem drawCurve;
    /** JMenuItem to see the ellipse tool help.
     */
    JMenuItem drawEllipse;
    /** JMenuItem to see the rect tool help.
     */
    JMenuItem drawRect;
    /** JMenuItem to see the poly tool help.
     */
    JMenuItem drawPoly;
    /** JMenuItem to see the text tool help.
     */
    JMenuItem text;
    /** JMenuItem to see the eraser tool help.
     */
    JMenuItem eraser;
    /** JMenuItem to see the selection tool help.
     */
    JMenuItem eraseselect;
    /** JMenuItem to see the selectall function help.
     */
    JMenuItem eraseimage;
    /** JMenuItem to see the stretch/skew fucntion help.
     */
    JMenuItem stretchskew;
    /** JMenuItem to see the resize help.
     */
    JMenuItem imagesize;

    /** JMenuItem to see the colors help.
     */
    JMenuItem setColors;
    /** JMenuItem to see the fill tool help.
     */
    JMenuItem fillArea;
    /** JMenuItem to see the brush tool help.
     */
    JMenuItem paintBrush;
    /** JMenuItem to see the spray tool help.
     */
    JMenuItem spray;
    /** JMenuItem to see the edit colors help.
     */
    JMenuItem customColors;
    /** JMenuItem to see the grayscale help.
     */
    JMenuItem bw;
    /** JMenuItem to see the medicine tool help.
     */
    JMenuItem copyColor;
    /** JMenuItem to see the magic wand tool help.
     */
    JMenuItem magicWand;

    /** JMenuItem to see the paste help.
     */
    JMenuItem pastefrom;
    /** JMenuItem to see the flip/rotate help.
     */
    JMenuItem fliprotate;
    /** JMenuItem to see the filter help.
     */
    JMenuItem filter;
    /** JMenuItem to see the copy/paste help.
     */
    JMenuItem copypaste;
    /** JMenuItem to see the selctall and select tool help.
     */
    JMenuItem select;
    /** JMenuItem to see the move selection help.
     */
    JMenuItem move;
    /** JMenuItem to see the copy-move selection help.
     */
    JMenuItem copymove;
    /** JMenuItem to see the zoom tool drag help.
     */
    JMenuItem zoomDrag;
    /** JMenuItem to see the zoom tool click help.
     */
    JMenuItem zoom;
    /** JMenuItem to see the slideshow help.
     */
    JMenuItem slideshow;

    /** JMenuItem to see the add layer help.
     */
    JMenuItem addLayer;
    /** JMenuItem to see the layer animation help.
     */
    JMenuItem animate;
    /** JMenuItem to see the change layer name help.
     */
    JMenuItem changeName;
    /** JMenuItem to see the remove layer by name help.
     */
    JMenuItem removeLayer;
    /** JMenuItem to see the remove current layer help.
     */
    JMenuItem removeCurrent;
    /** JMenuItem to see the flatten layers help.
     */
    JMenuItem flatten;
    /** JMenuItem to see the move layer up help.
     */
    JMenuItem moveUp;
    /** JMenuItem to see the move layer down help.
     */
    JMenuItem moveDown;
    /** JMenuItem to see the move to front help.
     */
    JMenuItem moveFront;
    /** JMenuItem to see the move to back help.
     */
    JMenuItem moveBack;

    /** JMenuItem to see the undo level preferences help.
     */
    JMenuItem undo;
    /** JMenuItem to see the animation interval preferences help.
     */
    JMenuItem interval;
    /** JMenuItem to see the language preferences help.
     */
    JMenuItem language;
    /** JMenuItem to see the animation transitions preferences help.
     */
    JMenuItem transitions;
    /** JMenuItem to see the animation looping preferences help.
     */
    JMenuItem loop;
    /** JMenuItem to see the paste/import to preferences help.
     */
    JMenuItem pasteImport;
    /** JMenuItem to see the file creation on paste to preferences help.
     */
    JMenuItem pasteFile;
    /** JMenuItem to see the new file size preferences help.
     */
    JMenuItem newSize;



    /** HTMLDisplay of the fileshown.
     */
    HTMLDisplay fileshown;




}
