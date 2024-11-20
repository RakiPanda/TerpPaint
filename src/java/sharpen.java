/*
 * sharpen.java
 *
 * Created on March 27, 2002, 7:25 PM
 */

/**
 *
 * @author  Terp Paint
 * @version 2.0
 */
// ming 4.26
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
// ming 4.26 end
import java.awt.*;
import java.awt.event.*;
import java.awt.Toolkit.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.colorchooser.*;
import java.io.*;
import java.awt.geom.*;

/** The user can sharpen the image.
 * There are no OS/Hardware dependencies and no variances.  There is no need for any
 * security constraints and no references to external specifications.
 *
 * @author Terp Paint
 * @version 2.0
 */

public class sharpen extends javax.swing.JDialog {

    /** Creates new form sharpen.
     *
     * This also initializes all the components and calls the parent's default
     * constructor.
     * There are no OS dependencies and variances.  No security constraints or external
     * specifications.
     * @param parent This parameter sends which frame is the parent.
     * @param modal This parameter could be a model, true or false.
     */
    public sharpen(java.awt.Frame parent, boolean modal) {
	super(parent, modal);
	initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     *
     * This method initializes the entire form with buttons and new dialoge
     * boxes, etc.  It also sets up constraints.
     * There are no OS dependencies and variances.  No security constraints or external
     * specifications.
     */
    public void initComponents() {
	// ming 4.24
	int cur_layer = ((TerpPaint)this.getParent()).center.currentLayer;
	    LinkedList cur_action_list = (LinkedList)(((TerpPaint)this.getParent()).action_list.get(cur_layer));
	    LinkedList cur_redo_list = (LinkedList)(((TerpPaint)this.getParent()).redo_list.get(cur_layer));

	    int temp1 = cur_redo_list.size();
	    for (int i=0; i<temp1; i++)
		cur_redo_list.removeLast();


	if (((TerpPaint)this.getParent()).toolSelectall.getSelected() || ((TerpPaint)this.getParent()).toolSelect.getSelected() || ((TerpPaint)this.getParent()).toolMagicSelect.getSelected())
	{
	    cur_action_list.add("Region sharpen");
	    BufferedImage selected;
	    if (((TerpPaint)this.getParent()).currentTool == ((TerpPaint)this.getParent()).toolSelect) {
		selected = ((TerpPaint)this.getParent()).toolSelect.getCopyImage(((TerpPaint)this.getParent()).center);
	    } else if(((TerpPaint)this.getParent()).currentTool==((TerpPaint)this.getParent()).toolMagicSelect){
		selected = ((TerpPaint)this.getParent()).toolMagicSelect.getCopyImage(((TerpPaint)this.getParent()).center);
	    }else {
		selected = ((TerpPaint)this.getParent()).toolSelectall.getCopyImage(((TerpPaint)this.getParent()).center);
	    }

	    Kernel kernel = new Kernel(3, 3,
	    new float[] {
	    -1, -1, -1,
	    -1, 9, -1,
	    -1, -1, -1});
	    RenderingHints hints = new RenderingHints(RenderingHints.KEY_RENDERING,
						 RenderingHints.VALUE_RENDER_QUALITY);
	    BufferedImageOp op = new ConvolveOp(kernel,ConvolveOp.EDGE_NO_OP,hints);


	    selected = op.filter(selected, null);
	    if (((TerpPaint)this.getParent()).currentTool == ((TerpPaint)this.getParent()).toolSelect)
	    {
		((TerpPaint)this.getParent()).toolSelect.setPastedImage(selected, ((TerpPaint)this.getParent()).center, ((TerpPaint)this.getParent()).toolSelect.getStartX(), ((TerpPaint)this.getParent()).toolSelect.getStartY());
	      //  ((TerpPaint)this.getParent()).toolSelect.backupImage = ((TerpPaint)this.getParent()).center.getBufferedImage();
	    }
	    else if (((TerpPaint)this.getParent()).currentTool == ((TerpPaint)this.getParent()).toolSelectall)
	    {
		((TerpPaint)this.getParent()).toolSelectall.setPastedImage(selected, ((TerpPaint)this.getParent()).center, ((TerpPaint)this.getParent()).toolSelectall.getStartX(), ((TerpPaint)this.getParent()).toolSelectall.getStartY());
	     //	  ((TerpPaint)this.getParent()).toolSelectall.backupImage = ((TerpPaint)this.getParent()).center.getBufferedImage();
	    }
	    //	  else if (((TerpPaint)this.getParent()).currentTool == ((TerpPaint)this.getParent()).magicSelectTool)
	  //	  ((TerpPaint)this.getParent()).magicSelectTool.setPastedImage(selected, ((TerpPaint)this.getParent()).center, ((TerpPaint)this.getParent()).magicSelectTool.getStartX(), ((TerpPaint)this.getParent()).magicSelectTool.getStartY());
	    //BufferedImage curImage = ((TerpPaint)this.getParent()).center.getBufferedImage();
	    //((TerpPaint)this.getParent()).center.setBufferedImage(selected);//curImage);
	    //((TerpPaint)this.getParent()).center.repaint();
	    selected.flush();
	}
	else
	{
	    cur_action_list.add("Image sharpen");
	    // ming 4.24 end
	BufferedImage im = ((TerpPaint)this.getParent()).center.getBufferedImage();

     //	  int x = Integer.parseInt(sharpen_value.getText());

	Kernel kernel = new Kernel(3, 3,
	new float[] {
	    -1, -1, -1,
	    -1, 9, -1,
	    -1, -1, -1});
	  // ming 4.24
	    RenderingHints hints = new RenderingHints(RenderingHints.KEY_RENDERING,
						 RenderingHints.VALUE_RENDER_QUALITY);
	    BufferedImageOp op = new ConvolveOp(kernel,ConvolveOp.EDGE_NO_OP,hints);
	    // ming 4.24 end

       // for (int i=0; i<x; i++)
	    im = op.filter(im, null);


	Graphics2D gd = im.createGraphics();

	gd.drawImage(im,0,0,null);
	((TerpPaint)this.getParent()).center.setBufferedImage(im);
	 // ming 4.24
	im.flush();
	}
	// ming 4.24 end
	 // ming 4.26


		      // ming 4.26 end
	((TerpPaint)this.getParent()).updateUndoList();

     //	  this.closeDialog(new java.awt.event.WindowEvent(this, 0));

   //	  ok_action = false;
     /*	  ok_cancel = new javax.swing.JPanel();
	ok = new javax.swing.JButton();
	cancel = new javax.swing.JButton();
	center = new javax.swing.JPanel();
	sharpen_panel = new javax.swing.JPanel();
	sharpen_value = new javax.swing.JTextField();
	jLabel4 = new javax.swing.JLabel();
	jLabel7 = new javax.swing.JLabel();


	addWindowListener(new java.awt.event.WindowAdapter() {
	    public void windowClosing(java.awt.event.WindowEvent evt) {
		closeDialog(evt);
	    }
	});

	ok_cancel.setLayout(new java.awt.GridBagLayout());
	java.awt.GridBagConstraints gridBagConstraints1;

	ok.setText("OK");
	ok.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		okActionPerformed(evt);
	    }
	});

	gridBagConstraints1 = new java.awt.GridBagConstraints();
	ok_cancel.add(ok, gridBagConstraints1);

	cancel.setText("CANCEL");
	cancel.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		cancelActionPerformed(evt);
	    }
	});

	gridBagConstraints1 = new java.awt.GridBagConstraints();
	gridBagConstraints1.gridx = 0;
	gridBagConstraints1.gridy = 1;
	ok_cancel.add(cancel, gridBagConstraints1);

	getContentPane().add(ok_cancel, java.awt.BorderLayout.EAST);

	center.setLayout(new java.awt.BorderLayout());

	sharpen_panel.setLayout(new java.awt.GridBagLayout());
	java.awt.GridBagConstraints gridBagConstraints2;

	sharpen_panel.setBorder(new javax.swing.border.TitledBorder("SHARPEN"));
	sharpen_value.setColumns(10);
	sharpen_value.setText("1");
	sharpen_value.setMaximumSize(new java.awt.Dimension(1, 50));
	sharpen_value.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		sharpenActionPerformed(evt);
	    }
	});

	gridBagConstraints2 = new java.awt.GridBagConstraints();
	gridBagConstraints2.gridx = 1;
	gridBagConstraints2.gridy = 1;
	sharpen_panel.add(sharpen_value, gridBagConstraints2);


	jLabel4.setText("sharpen");
	gridBagConstraints2 = new java.awt.GridBagConstraints();
	gridBagConstraints2.gridx = 0;
	gridBagConstraints2.gridy = 1;
	sharpen_panel.add(jLabel4, gridBagConstraints2);

	jLabel7.setText("times");
	gridBagConstraints2 = new java.awt.GridBagConstraints();
	gridBagConstraints2.gridx = 2;
	gridBagConstraints2.gridy = 1;
	sharpen_panel.add(jLabel7, gridBagConstraints2);

	center.add(sharpen_panel, java.awt.BorderLayout.CENTER);


	getContentPane().add(center, java.awt.BorderLayout.CENTER);

	pack();*/
    }

    /** This method executes if the event is a CANCEL button, then it closes the dialog.
     * There are no OS dependencies and variances.  No security constraints or external
     * specifications.
     * @param evt The event of a mouse action.
     */
   /* public void cancelActionPerformed(java.awt.event.ActionEvent evt) {
	this.closeDialog(new java.awt.event.WindowEvent(this, 0));
    }

    /** This method executes if the event is a OK button.  Then it executes the performed action.
     * In this case, it will sharpen the picture
     * accordingly.  If user selected skew instead of sharpen, the picture will
     * basically just expand without the borders of the picture to expand.
     *
     * There are no state transitions and no security constraints or OS dependencies.
     *
     * @param evt The event of a mouse action.
     */
  /*  public void okActionPerformed(java.awt.event.ActionEvent evt) {
	BufferedImage im = ((TerpPaint)this.getParent()).center.getBufferedImage();

	int x = Integer.parseInt(sharpen_value.getText());

	Kernel kernel = new Kernel(3, 3,
	new float[] {
	    -1, -1, -1,
	    -1, 9, -1,
	    -1, -1, -1});
	BufferedImageOp op = new ConvolveOp(kernel);


	for (int i=0; i<x; i++)
	    im = op.filter(im, null);


	Graphics2D gd = im.createGraphics();

	gd.drawImage(im,0,0,null);
	((TerpPaint)this.getParent()).center.setBufferedImage(im);
	int temp1 = ((TerpPaint)this.getParent()).redo_list.size();
	for (int i=0; i<temp1; i++)
	    ((TerpPaint)this.getParent()).redo_list.removeLast();
	((TerpPaint)this.getParent()).action_list.add("sharpen");
	((TerpPaint)this.getParent()).updateUndoList();

	this.closeDialog(new java.awt.event.WindowEvent(this, 0));
    //	  ok_action = true;
    }


    /** This method is for skewing vertically.
     * There are no OS dependencies and variances.  No security constraints or external
     * specifications.
     * @param evt The event of an action.
     */	   /*
    public void sharpenActionPerformed(java.awt.event.ActionEvent evt) {
	// Add your handling code here:
    }


    /** Closes the dialog.
     * There are no OS dependencies and variances.  No security constraints or external
     * specifications.
     * @param evt The event of an action.
     *//*
    public void closeDialog(java.awt.event.WindowEvent evt) {
	setVisible(false);
	dispose();
    }
*/
    /** This method basically creates a new sharpen object and applies the image
     * into it.	 If there are arguments, it will take them as well.  It will then
     * display the results onto the main canvas.
     * There are no OS dependencies and variances.  No security constraints or external
     * specifications.
     * @param args The command line arguments
     */
    public static void main(String args[]) {
	new sharpen(new javax.swing.JFrame(), true).show();
    }


    // Variables declaration - do not modify
    /** Java swing GUI depicting the OK and CANCEL panel.
     */
    private javax.swing.JPanel ok_cancel;

    /** Java swing GUI depicting the OK button.
     */
    private javax.swing.JButton ok;

    /** Java swing GUI depicting the CANCEL button.
     */
    private javax.swing.JButton cancel;

    /** Java swing GUI depicting the CENTER button.
     */
    private javax.swing.JPanel center;

    /** Java swing GUI depicting the sharpen button.
     */
    private javax.swing.JPanel sharpen_panel;

    /** Java swing GUI depicting the textfield of the sharpen change ratio value.
     */
    private javax.swing.JTextField sharpen_value;


    /** Java swing GUI depicting the Label 4.
     *
     */
    private javax.swing.JLabel jLabel4;

    /** Java swing GUI depicting the Label 7.
     *
     */
    private javax.swing.JLabel jLabel7;



    // End of variables declaration

    /** The dialog is closed by OK
     */
   // public boolean ok_action;
}
