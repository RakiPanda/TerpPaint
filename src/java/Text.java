/*
 * Text.java
 *
 * Created on April 7, 2002, 10:19 AM
 */


//import java.lang.*;
//import java.awt.*;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


/** Initializes the GUI components of a text editor window and provides text field,
    *underline, bold, or italics options and font styles and size.
    *There are no OS/Hardware dependencies and no variances.
    *There are no security constraints.
    *There are no references to external specifications.
    *
    *@author TerpPaint
    *@version 2.0
 */
public class Text extends javax.swing.JDialog {
    /** Array of fonts.
     *There are no OS/Hardware dependencies and no variances.
     *There are no security constraints.
     *There are no references to external specifications.
     */
public Font[] font_list;
/** Array of families.
  *There are no OS/Hardware dependencies and no variances.
     *There are no security constraints.
     *There are no references to external specifications.
 */
public String[] family_list;

/** X coordinate specifying where the text string to be drawn.
     *There are no OS/Hardware dependencies and no variances.
     *There are no security constraints.
     *There are no references to external specifications.
 */
public static int x;

/** Y coordinate specifying where the text string to be drawn.
      *There are no OS/Hardware dependencies and no variances.
     *There are no security constraints.
     *There are no references to external specifications.
 */
public static int y;

/** holds the index of the font, initialized to 0.
 */
public static int fontIndex = 0;

/** The dialog is closed by OK.
     */
public boolean ok_action;

/** Current font.
      *There are no OS/Hardware dependencies and no variances.
     *There are no security constraints.
     *There are no references to external specifications.
 */
OurFont myFont = new OurFont();
    /** Creates new Text form of 375 X 50.
     * @param parent reference of java.awt.Frame, refers to the underlying Frame
     * @param modal modal flag
     * @param x1 x coordinate specifying where the text string to be drawn
     * @param y1 y coordinate specifying where the text string to be drawn
     */
    public Text(java.awt.Frame parent, boolean modal, int x1, int y1 ) {
	super(parent, modal);
	x = x1; /*FAULT:: x = y1; */
	y = y1;

	this.setTitle("Text");


	this.setSize(375,50);
	initComponents();
	addSizes();
	addFamilies();
	families.setSelectedIndex( letterTool.currentFont );
	underline.setSelected( letterTool.underlineness );
	bold.setSelected( letterTool.boldness );
	italics.setSelected( letterTool.italicness );
	size.setSelectedIndex( letterTool.currentSize );

	ok_action = false;
    }



    /** Returns the size of one of the different font types which is chosen by user.
     * @return the size of the certain type of font.
     *
     */
    public String getFamiliesSize(){
	return (String)families.getItemAt(0); /*Fault:: return "xx"; */
    }

    /** Returns the current font type.
     * @return font current font type
     */
    public String getMyFont(){
	return myFont.font; /*Fault:: return "xx"; */
    }
    /** Returns the size of the current font type.
     * @return size the current font size
     */
    public int getMyFontSize(){
	return myFont.size; /*Fault:: return 5; */
    }
    /** Changes the texts to italics.
     * @return italics-style text string is returned.
     * @see #setItalics
     */
    public String getItalics(){
	return italics.getText(); /*FAULT:: return "y"; */
    }

    /** Changes texts to a bold style.
     * @return bold-style text string is returned.
     * @see #setBold
     */
    public String getBold(){
	return bold.getText(); /*Fault:: return "y"; */
    }

    /** This adds the underline under the text.
     * @return underlined text string is returned.
      * @see #setUnderline
     */
    public String getUnderline(){
	return underline.getText(); /*FAULT:: return "y"; */
    }
    /** This is performed when all work or user request is done by clicking ok option by the user.
     * @return actual work or user request will be done.
      * @see #setOk
     */
    public String getOk(){
	return Ok.getText(); /*FAULT:: return "y"; */
    }

    /** This returns the size at index i.
     *@param i the indicator of the item.
     * @return the size of index.
     */
    public Integer getSizeIndex(int i){
	return (Integer)size.getItemAt(i); /*FAULT:: return (Integer) size.getItemAt(0); */
    }

    /** Sets the text italics.
    * @see #getItalics
     */
    public void setItalics(){
	bold.setSelected(false);
	underline.setSelected(false);
	Ok.setSelected(false);
	italics.setSelected(true); /*FAULT:: italics.setSelected(false); */
    }

    /** Takes ok action and disables all other options.
     * @see #getOk
     */
    public void setOk(){
	underline.setSelected(false);
	bold.setSelected(false);
	italics.setSelected(false);
	Ok.setSelected(true); /*FAULT:: Ok.setSelected(false); */
    }
    /** Sets the text to bold and disables all other options.
    * @see #getBold
     */
    public void setBold(){
	underline.setSelected(false);
	Ok.setSelected(false); /*FAULT:: Ok.setSelected(true); */
	italics.setSelected(false);
	bold.setSelected(true);
    }
    /** Sets the text with underline and disables all other options.
    * @see #getUnderline
     */
    public void setUnderline(){
	italics.setSelected(false);
	Ok.setSelected(false); /*FAULT:: Ok.setSelected(true); */
	bold.setSelected(false);
	underline.setSelected(true);
    }


    /** Initalizes all the components constructing Text's GUI and registers
     * event handling methods to relating components
     * Called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    public void initComponents() {
	greg = new javax.swing.JList();
	textPanel = new javax.swing.JPanel();
	//Istvan phase 5
	text = new javax.swing.JTextField();
	//End Istvan phase 5
	optionsPanel = new javax.swing.JPanel();
	size = new javax.swing.JComboBox();
	families = new javax.swing.JComboBox();
	bold = new javax.swing.JRadioButton();
	italics = new javax.swing.JRadioButton();
	underline = new javax.swing.JRadioButton();
	okPanel = new javax.swing.JPanel();
	Ok = new javax.swing.JButton();
	Cancel = new javax.swing.JButton();


	addWindowListener(new java.awt.event.WindowAdapter() {
	    public void windowClosing(java.awt.event.WindowEvent evt) {
		closeDialog(evt);
	    }
	});

	getContentPane().add(greg, java.awt.BorderLayout.NORTH);
//Istvan phase 5
	text.setColumns(30);
	text.setMinimumSize(new java.awt.Dimension(30, 20));
	text.setMaximumSize(new java.awt.Dimension(30, 20));
	text.setPreferredSize(new java.awt.Dimension(30, 20));
	textPanel.add(text);
//End Istvan phase 5
	getContentPane().add(textPanel, java.awt.BorderLayout.NORTH);

	optionsPanel.setLayout(new java.awt.GridBagLayout());
	java.awt.GridBagConstraints gridBagConstraints1;

	size.setMaximumRowCount(15);
	size.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		sizeActionPerformed(evt);
	    }
	});

	gridBagConstraints1 = new java.awt.GridBagConstraints();
	gridBagConstraints1.gridx = 1;
	gridBagConstraints1.gridy = 1;
	optionsPanel.add(size, gridBagConstraints1);

	families.setMaximumRowCount(15);
//	  families.setSelectedIndex( letterTool.currentFont );
	families.addActionListener(new java.awt.event.ActionListener() {


	    public void actionPerformed(java.awt.event.ActionEvent evt) {

		familiesActionPerformed(evt);
	    }
	});

	gridBagConstraints1 = new java.awt.GridBagConstraints();
	gridBagConstraints1.gridx = 0;
	gridBagConstraints1.gridy = 1;
	optionsPanel.add(families, gridBagConstraints1);

	bold.setText("bold");
	gridBagConstraints1 = new java.awt.GridBagConstraints();
	gridBagConstraints1.gridx = 1;
	gridBagConstraints1.gridy = 0;
	optionsPanel.add(bold, gridBagConstraints1);

	italics.setText("italics");
	gridBagConstraints1 = new java.awt.GridBagConstraints();
	optionsPanel.add(italics, gridBagConstraints1);

	underline.setText("underline");
	gridBagConstraints1 = new java.awt.GridBagConstraints();
	gridBagConstraints1.gridx = 0;
	gridBagConstraints1.gridy = 0;
	optionsPanel.add(underline, gridBagConstraints1);

	getContentPane().add(optionsPanel, java.awt.BorderLayout.SOUTH);

	Ok.setText("Ok"); /*FAULT:: Ok.setText("okk"); */
	Ok.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		OkActionPerformed(evt);
	    }
	});

	okPanel.add(Ok);

	Cancel.setText("Cancel");
	Cancel.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		 setVisible(false);
					dispose();
	    }
	});

	okPanel.add(Cancel);


	getContentPane().add(okPanel, java.awt.BorderLayout.EAST);

	pack();
    }

    /** Collects all the options specified the user once ok option is chosen
     * and draws text string onto the canvas image.
     * @param evt java.awt.event.ActionEvent
     */
    public void OkActionPerformed(java.awt.event.ActionEvent evt) {
	// Add your handling code here:

	letterTool.currentFont = families.getSelectedIndex();
	letterTool.currentSize = size.getSelectedIndex();
	letterTool.boldness = bold.isSelected();
	letterTool.italicness = italics.isSelected(); /*FAULT:: letterTool.italicness = !italics.isSelected(); */
	letterTool.underlineness = underline.isSelected();



	BufferedImage im = ((TerpPaint)this.getParent()).center.getBufferedImage();
	Graphics2D g2d = im.createGraphics();

	g2d.setPaint(((TerpPaint)this.getParent()).center.left_color);
	int style = Font.PLAIN;
	if (bold.isSelected())
	    style += Font.BOLD;
	if (italics.isSelected())
	    style += Font.ITALIC;
	Font newFont = new Font(myFont.font,style,myFont.size);
	g2d.setFont(newFont);
	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	String myText = text.getText();

	g2d.drawString(myText,x,y);
	((TerpPaint)this.getParent()).center.setBufferedImage(im);
	if (underline.isSelected()){
	    /*AttributedString newString = new AttributedString(myText);
	    newString.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON, 0, myText.length());
	    g2d.draw*/
	    im = ((TerpPaint)this.getParent()).center.getBufferedImage();
	    g2d = im.createGraphics();
	    g2d.setPaint(((TerpPaint)this.getParent()).center.left_color);
	    //STUFF I JUST ADDED
	    FontRenderContext rend = new FontRenderContext(new AffineTransform(),true,true);

	    if( newFont.getStringBounds(myText,rend).getMaxX() > 0 )
	    g2d.drawLine(x,y+1,(int)(x+newFont.getStringBounds(myText,rend).getMaxX()),y+1);
// +1 removed from before ,y
	    //END OF STUFF I JUST ADDED
	    ((TerpPaint)this.getParent()).center.setBufferedImage(im);
	}
	((TerpPaint)this.getParent()).center.setBufferedImage(im);
	this.setVisible(false);
	dispose();
	ok_action = true;
	im.flush();
    }

    /**Sets the current font as the selected font when a font among the family of fonts is selected.
     * @param evt java.awt.event.ActionEvent
     */
    public void familiesActionPerformed(java.awt.event.ActionEvent evt) {
	myFont.font = (String)families.getSelectedItem(); /*FAULT:: myFont.font = "xyz"; */
    }

    /**Sets the current font size as the selected size when the size option is selected.
     * @param evt java.awt.event.ActionEvent
     */
    public void sizeActionPerformed(java.awt.event.ActionEvent evt) {
	myFont.size = ((Integer)size.getSelectedItem()).intValue(); /*FAULT:: myFont.size = 5; */
    }

    /** Closes the dialog window.
     * @param evt java.awt.event.WindowEvent
     */
    public void closeDialog(java.awt.event.WindowEvent evt) {
	setVisible(false);
	dispose();
    }

    /** Adds a group of different Integer sizes to size JComboBox.
     */
    /*public static void main(String args[]) {
	new Text(new javax.swing.JFrame(), true).show();
    }*/

public void addSizes(){
    size.addItem(new Integer(8));
    size.addItem(new Integer(9));
    size.addItem(new Integer(10));
    size.addItem(new Integer(11));
    size.addItem(new Integer(12));
    size.addItem(new Integer(14));
    size.addItem(new Integer(16));
    size.addItem(new Integer(18));
    size.addItem(new Integer(20));
    size.addItem(new Integer(22));
    size.addItem(new Integer(24));
    size.addItem(new Integer(26));
    size.addItem(new Integer(28));
    size.addItem(new Integer(36));
    size.addItem(new Integer(48));
    size.addItem(new Integer(72)); /*FAULT:: System.out.println("fault"); */
}

/** Detects and adds all local platform's font families to families JComboBox.
 */
public void addFamilies(){
	GraphicsEnvironment env =
      GraphicsEnvironment.
      getLocalGraphicsEnvironment();
    family_list = env.getAvailableFontFamilyNames(); /*FAULT:: family_list = new String[10]; */
   for(int i = 0; i < family_list.length; i++){
       families.addItem(family_list[i]);
   }



}
// Variables declaration - do not modify
/** java swing GUI depicting the JList called greg.*/
private javax.swing.JList greg;
/** java swing GUI depicting the JPanel called textPanel.*/
private javax.swing.JPanel textPanel;
//Istvan phase 5
/** java swing GUI depicting the JTextField text.*/
private javax.swing.JTextField text;
//End Istvan phase 5
/** java swing GUI depicting the JPanel called optionsPanel.*/
private javax.swing.JPanel optionsPanel;
/** java swing GUI depicting the JComboBox size.*/
public javax.swing.JComboBox size;
/** java swing GUI depicting the JComboBox families.*/
private javax.swing.JComboBox families;
/** java swing GUI depicting the JRadioButton called bold.*/
private javax.swing.JRadioButton bold;
/** java swing GUI depicting the JRadioButton called italics.*/
public javax.swing.JRadioButton italics;
/** java swing GUI depicting the JRadioButton called underline.*/
private javax.swing.JRadioButton underline;
/** java swing GUI depicting the JPanel called okPanel.*/
private javax.swing.JPanel okPanel;
/** java swing GUI depicting the Jbutton called Ok.*/
public javax.swing.JButton Ok;
// End of variables declaration
/** java swing GUI depicting the Jbutton called Cancel.*/
private javax.swing.JButton Cancel;
// End of variables declaration

}
