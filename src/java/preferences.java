/*

 * preferences.java

 *

 * Created on March 20, 2004, 2:48 PM

 */



/**

 *

 * @author  Terp Paint

 * @version 3.0

 */

import java.awt.*;

import java.awt.event.*;

import java.awt.Toolkit.*;

import java.awt.image.*;

import javax.swing.*;

import javax.swing.event.*;

import javax.swing.colorchooser.*;

import java.io.*;

import java.util.*;

import java.awt.geom.*;



/** preferences class is used set up the user preferences

 * There are two states of this class.	One is being shown but not clicked and one

 * is being shown and clicked.	Once clicked, there will be a dialog which asks you

 * how you want to set preferences.

 *

 * There are no OS dependencies and variances.	No security constraints or external

 * specifications.

 *

 * @author Terp Paint

 * @version 3.0

 */

public class preferences extends javax.swing.JDialog {

	/** Array of availiable transitions.
    */
	public String[] transitions;

	/** Array of languages.

    */
     public TreeSet lang_list;
     /** the maximum depth to which a person can undo.

    */

     public static final int MaxUndo=20;

    /** Creates new form preferences.

     *

     * This also initializes all the components and calls the parent's default

     * constructor.

     * There are no OS dependencies and variances.  No security constraints or external

     * specifications.

     * @param parent This parameter sends which frame is the parent.

     * @param modal This parameter could be a model, true or false.

     */

    public preferences(java.awt.Frame parent, boolean modal) {

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

	prefsState thePrefs = ((prefsState)((TerpPaint)this.getParent()).myPrefs);

	ok_cancel = new javax.swing.JPanel();

	ok = new javax.swing.JButton();

	cancel = new javax.swing.JButton();

	center = new javax.swing.JPanel();

	top=new javax.swing.JPanel();

	bottom=new javax.swing.JPanel();

	prefs = new javax.swing.JPanel();

	undo = new javax.swing.JPanel();

	lang = new javax.swing.JPanel();

	tran = new javax.swing.JPanel();

	undoLevel = new javax.swing.JTextField();
//Istvan phase 5
	cycleNumber = new javax.swing.JTextField();

	jLabelCycle = new javax.swing.JLabel();
	jLabelCycle2 = new javax.swing.JLabel();
//End Istvan phase 5

	pasteOrImportTo = new javax.swing.ButtonGroup();

	newFiles = new javax.swing.ButtonGroup();

	imageSizes= new javax.swing.ButtonGroup();

	imageAttrib=new javax.swing.JPanel();

	imageAttrib2=new javax.swing.JPanel();

	langandtran=new javax.swing.JPanel();

	widthPanel=new javax.swing.JPanel();

	heightPanel=new javax.swing.JPanel();

	langs = new javax.swing.JComboBox();

	trans = new javax.swing.JComboBox();

	pastePrefs = new javax.swing.JPanel();

	filePrefs = new javax.swing.JPanel();

	anim = new javax.swing.JPanel();

	jLabel0 = new javax.swing.JLabel();

	jLabel1 = new javax.swing.JLabel();

	jLabel2 = new javax.swing.JLabel();

	jLabel3 = new javax.swing.JLabel();



	animInterval=new javax.swing.JTextField();

	width=new javax.swing.JTextField();

	height=new javax.swing.JTextField();

		toNewFileAndOpen=new javax.swing.JRadioButton();

		toNewFile=new javax.swing.JRadioButton();

		toNewLayer=new javax.swing.JRadioButton();

	    toCurrentLayer=new javax.swing.JRadioButton();

	createNew=new javax.swing.JRadioButton();

	onlyIfExists=new javax.swing.JRadioButton();

	lastSize=new javax.swing.JRadioButton();

	promptSize=new javax.swing.JRadioButton();

	fixedSize=new javax.swing.JRadioButton();



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







	center.setLayout(new java.awt.BorderLayout());



	undo.setLayout(new java.awt.GridBagLayout());

	java.awt.GridBagConstraints gridBagConstraints2;



	undo.setBorder(new javax.swing.border.TitledBorder("Undo Level"));

	undoLevel.setColumns(2);

	undoLevel.setText((new Integer(thePrefs.UndoLevel)).toString());

	undoLevel.setMaximumSize(new java.awt.Dimension(4, 1));

	undoLevel.addActionListener(new java.awt.event.ActionListener() {

	    public void actionPerformed(java.awt.event.ActionEvent evt) {

		undoLevelActionPerformed(evt);

	    }

	});



	gridBagConstraints2 = new java.awt.GridBagConstraints();

	gridBagConstraints2.gridx = 0;

	gridBagConstraints2.gridy = 1;

	undo.add(undoLevel, gridBagConstraints2);





	jLabel3.setText("Maximum is 20");

	gridBagConstraints2 = new java.awt.GridBagConstraints();

	gridBagConstraints2.gridx = 0;

	gridBagConstraints2.gridy = 2;

	undo.add(jLabel3, gridBagConstraints2);



	pastePrefs.setLayout(new java.awt.GridBagLayout());

	java.awt.GridBagConstraints gridBagConstraints3;



	pastePrefs.setBorder(new javax.swing.border.TitledBorder("Paste/Import To"));

		if(thePrefs.ToCurrentLayer)

		toCurrentLayer.setSelected(true);

	toCurrentLayer.setText("Current Layer");

	pasteOrImportTo.add(toCurrentLayer);

	toCurrentLayer.addActionListener(new java.awt.event.ActionListener() {

	    public void actionPerformed(java.awt.event.ActionEvent evt) {

		toCurrentLayerActionPerformed(evt);

	    }

	});



	gridBagConstraints3 = new java.awt.GridBagConstraints();

	gridBagConstraints3.insets = new java.awt.Insets(0, 10, 0, 0);

	pastePrefs.add(toCurrentLayer, gridBagConstraints3);



		if(thePrefs.ToNewLayer)

		toNewLayer.setSelected(true);

	toNewLayer.setText("New Layer");

	pasteOrImportTo.add(toNewLayer);

	toNewLayer.addActionListener(new java.awt.event.ActionListener() {

	    public void actionPerformed(java.awt.event.ActionEvent evt) {

		toNewLayerActionPerformed(evt);

	    }

	});



	gridBagConstraints3 = new java.awt.GridBagConstraints();

	gridBagConstraints3.insets = new java.awt.Insets(0, 10, 0, 0);

	pastePrefs.add(toNewLayer, gridBagConstraints3);



		if(thePrefs.ToNewFileAndWindow)

			toNewFileAndOpen.setSelected(true);

	toNewFileAndOpen.setText("New File and Window");

	pasteOrImportTo.add(toNewFileAndOpen);

	toNewFileAndOpen.addActionListener(new java.awt.event.ActionListener() {

	    public void actionPerformed(java.awt.event.ActionEvent evt) {

		toNewFileAndOpenActionPerformed(evt);

	    }

	});



	gridBagConstraints3 = new java.awt.GridBagConstraints();

	gridBagConstraints3.insets = new java.awt.Insets(0, 0, 0, 10);

	pastePrefs.add(toNewFileAndOpen, gridBagConstraints3);



		if(thePrefs.ToNewFile)

			toNewFile.setSelected(true);

	toNewFile.setText("New File");

	pasteOrImportTo.add(toNewFile);

	toNewFile.addActionListener(new java.awt.event.ActionListener() {

	    public void actionPerformed(java.awt.event.ActionEvent evt) {

		toNewFileActionPerformed(evt);

	    }

	});



	gridBagConstraints3 = new java.awt.GridBagConstraints();

	gridBagConstraints3.insets = new java.awt.Insets(0, 10, 0, 10);

	pastePrefs.add(toNewFile, gridBagConstraints3);







		filePrefs.setLayout(new java.awt.GridBagLayout());

	filePrefs.setBorder(new javax.swing.border.TitledBorder("Paste To A File"));



		if(thePrefs.CreateNew)

		createNew.setSelected(true);

	createNew.setText("by Creating the File");

	newFiles.add(createNew);

	createNew.addActionListener(new java.awt.event.ActionListener() {

	   public void actionPerformed(java.awt.event.ActionEvent evt) {

	      createNewActionPerformed(evt);

	   }

	});



	gridBagConstraints3 = new java.awt.GridBagConstraints();

	gridBagConstraints3.insets = new java.awt.Insets(0, 10, 0, 0);

	filePrefs.add(createNew, gridBagConstraints3);



		if(thePrefs.OnlyIfExists)

		onlyIfExists.setSelected(true);

	onlyIfExists.setText("Only if it Exists");

	newFiles.add(onlyIfExists);

	onlyIfExists.addActionListener(new java.awt.event.ActionListener() {

	  public void actionPerformed(java.awt.event.ActionEvent evt) {

	     onlyIfExistsActionPerformed(evt);

	  }

	});



	gridBagConstraints3 = new java.awt.GridBagConstraints();

	gridBagConstraints3.insets = new java.awt.Insets(0, 10, 0, 0);

	filePrefs.add(onlyIfExists, gridBagConstraints3);





		if (toNewFile.isSelected()||toNewFileAndOpen.isSelected()){

			createNew.setEnabled(true);

			onlyIfExists.setEnabled(true);

	    }

	    else{

			createNew.setEnabled(false);

			onlyIfExists.setEnabled(false);

		}



	anim.setLayout(new java.awt.GridBagLayout());



	anim.setBorder(new javax.swing.border.TitledBorder("Animation Interval"));

	animInterval.setColumns(4);

	animInterval.setText((new Double(thePrefs.Anim)).toString());

	animInterval.setMaximumSize(new java.awt.Dimension(4, 4));

	animInterval.addActionListener(new java.awt.event.ActionListener() {

	    public void actionPerformed(java.awt.event.ActionEvent evt) {

		animIntervalActionPerformed(evt);

	    }

	});



	gridBagConstraints2 = new java.awt.GridBagConstraints();

	gridBagConstraints2.gridx = 0;

	gridBagConstraints2.gridy = 1;

	anim.add(animInterval, gridBagConstraints2);



	jLabel2.setText("Enter in Milliseconds");

		gridBagConstraints2 = new java.awt.GridBagConstraints();

		gridBagConstraints2.gridx = 0;

		gridBagConstraints2.gridy = 2;

	anim.add(jLabel2, gridBagConstraints2);



//here

	langs.setMaximumRowCount(15);

		langs.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {

				//langsActionPerformed(evt);

			}

	});

	lang.setLayout(new java.awt.BorderLayout());

	lang.setBorder(new javax.swing.border.TitledBorder("Language"));

	lang.add(langs, java.awt.BorderLayout.CENTER);

	addLangs();


		trans.setMaximumRowCount(15);

		trans.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {

				//transActionPerformed(evt);

			}

	});

	tran.setLayout(new java.awt.BorderLayout());

	tran.setBorder(new javax.swing.border.TitledBorder("Transitions"));
//Istvan phase 5
	tran.add(trans, java.awt.BorderLayout.WEST);

		cycleNumber.setColumns(2);

	cycleNumber.setText((new Integer(thePrefs.CycleNumber)).toString());

	cycleNumber.setMaximumSize(new java.awt.Dimension(4, 1));

	cycleNumber.addActionListener(new java.awt.event.ActionListener() {

	    public void actionPerformed(java.awt.event.ActionEvent evt) {

		cycleNumberActionPerformed(evt);

	    }

	});
	tran.add(cycleNumber,java.awt.BorderLayout.EAST);
	jLabelCycle.setText("Select a Transition and the number of loops");
		jLabelCycle2.setText("(use -1 for repeated looping)");
	tran.add(jLabelCycle,java.awt.BorderLayout.NORTH);
	tran.add(jLabelCycle2,java.awt.BorderLayout.SOUTH);
//End Istvan phase 5
	addTrans();
	int index=0;
	if(thePrefs.Transition.compareTo("Blur")==0)
	    index=1;

	trans.setSelectedIndex(index );


	imageAttrib.setLayout(new java.awt.BorderLayout());

		imageAttrib.setBorder(new javax.swing.border.TitledBorder("When creating a new file use:"));

		if(thePrefs.LastSize)

		lastSize.setSelected(true);

	lastSize.setText("Last Size");

	imageSizes.add(lastSize);

	lastSize.addActionListener(new java.awt.event.ActionListener() {

	   public void actionPerformed(java.awt.event.ActionEvent evt) {

	      lastSizeActionPerformed(evt);

	   }

	});

	imageAttrib.add(lastSize, java.awt.BorderLayout.WEST);



		if(thePrefs.PromptSize)

		promptSize.setSelected(true);

	promptSize.setText("Prompt");

	imageSizes.add(promptSize);

	promptSize.addActionListener(new java.awt.event.ActionListener() {

	   public void actionPerformed(java.awt.event.ActionEvent evt) {

	      promptSizeActionPerformed(evt);

	   }

	});

	imageAttrib.add(promptSize, java.awt.BorderLayout.CENTER);



		if(thePrefs.FixedSize)

		fixedSize.setSelected(true);

	fixedSize.setText("Fixed Size");

	imageSizes.add(fixedSize);

	fixedSize.addActionListener(new java.awt.event.ActionListener() {

	   public void actionPerformed(java.awt.event.ActionEvent evt) {

	      fixedSizeActionPerformed(evt);

	   }

	});





	imageAttrib.add(fixedSize, java.awt.BorderLayout.EAST);



		imageAttrib2.setLayout(new java.awt.BorderLayout());

		widthPanel.setLayout(new java.awt.BorderLayout());



		jLabel1.setText("Width");

	widthPanel.add(jLabel1, java.awt.BorderLayout.WEST);



		width.setColumns(3);

		width.setText((new Integer(thePrefs.Width)).toString());

	width.setMaximumSize(new java.awt.Dimension(4, 3));

	width.addActionListener(new java.awt.event.ActionListener() {

	    public void actionPerformed(java.awt.event.ActionEvent evt) {

		widthActionPerformed(evt);

	    }

	});





	widthPanel.add(width, java.awt.BorderLayout.EAST);





		jLabel0.setText("Height");

	heightPanel.add(jLabel0, java.awt.BorderLayout.WEST);



		height.setColumns(3);

		height.setText((new Integer(thePrefs.Height)).toString());

	height.setMaximumSize(new java.awt.Dimension(4, 3));

	height.addActionListener(new java.awt.event.ActionListener() {

	    public void actionPerformed(java.awt.event.ActionEvent evt) {

		heightActionPerformed(evt);

	    }

	});





	heightPanel.add(height, java.awt.BorderLayout.EAST);

	if(lastSize.isSelected()){

		width.setEnabled(false);

		jLabel1.setEnabled(false);

		height.setEnabled(false);

		jLabel0.setEnabled(false);

		}else{

			width.setEnabled(true);

			jLabel1.setEnabled(true);

			height.setEnabled(true);

		jLabel0.setEnabled(true);

		}



	imageAttrib2.add(widthPanel, java.awt.BorderLayout.WEST);

	imageAttrib2.add(heightPanel, java.awt.BorderLayout.EAST);

	imageAttrib.add(imageAttrib2, java.awt.BorderLayout.SOUTH);



		langandtran.setLayout(new java.awt.BorderLayout());

		langandtran.add(lang,java.awt.BorderLayout.WEST);

		langandtran.add(tran,java.awt.BorderLayout.EAST);

	top.add(undo, java.awt.BorderLayout.WEST);

	top.add(anim, java.awt.BorderLayout.CENTER);

	top.add(langandtran,java.awt.BorderLayout.EAST);



	center.add(pastePrefs, java.awt.BorderLayout.CENTER);



		bottom.add(filePrefs, java.awt.BorderLayout.WEST);

	bottom.add(imageAttrib, java.awt.BorderLayout.CENTER);

	bottom.add(ok_cancel, java.awt.BorderLayout.EAST);



		prefs.setLayout(new java.awt.BorderLayout());

	prefs.setBorder(new javax.swing.border.TitledBorder("Preferences"));

	prefs.add(top, java.awt.BorderLayout.NORTH);

		prefs.add(bottom, java.awt.BorderLayout.SOUTH);

		prefs.add(center, java.awt.BorderLayout.CENTER);



		getContentPane().add(prefs, java.awt.BorderLayout.CENTER);

	pack();

    }



    /** This method executes if the event is a CANCEL button, then it closes the dialog.

     * There are no OS dependencies and variances.  No security constraints or external

     * specifications.

     * @param evt The event of a mouse action.

     */

    public void cancelActionPerformed(java.awt.event.ActionEvent evt) {

	this.closeDialog(new java.awt.event.WindowEvent(this, 0));

    }



    /** This method executes if the event is a OK button.  Then it executes the performed action.

     * In this case, it will preferences the picture

     * accordingly.  If user selected undo instead of preferences, the picture will

     * basically just expand without the borders of the picture to expand.

     *

     * There are no state transitions and no security constraints or OS dependencies.

     *

     * @param evt The event of a mouse action.

     */

    public void okActionPerformed(java.awt.event.ActionEvent evt) {

    	boolean except = false;
	prefsState prefs = ((prefsState)((TerpPaint)this.getParent()).myPrefs);

	if(lastSize.isSelected()){

		prefs.LastSize=true;

		prefs.PromptSize=false;

		prefs.FixedSize=false;

		}else if(fixedSize.isSelected()){

			prefs.LastSize=false;

			prefs.PromptSize=false;

		prefs.FixedSize=true;

		}else{

			prefs.LastSize=false;

			prefs.PromptSize=true;

		prefs.FixedSize=false;

		}

	if(toCurrentLayer.isSelected()){

		prefs.ToCurrentLayer=true;

		prefs.ToNewLayer=false;

		prefs.ToNewFile=false;

		prefs.ToNewFileAndWindow=false;

		}else if(toNewLayer.isSelected()){

			prefs.ToCurrentLayer=false;

			prefs.ToNewLayer=true;

			prefs.ToNewFile=false;

		prefs.ToNewFileAndWindow=false;

		}else if(toNewFile.isSelected()){

			prefs.ToCurrentLayer=false;

			prefs.ToNewLayer=false;

			prefs.ToNewFile=true;

		prefs.ToNewFileAndWindow=false;

		}else{

			prefs.ToCurrentLayer=false;

			prefs.ToNewLayer=false;

			prefs.ToNewFile=false;

		prefs.ToNewFileAndWindow=true;

		}

		if(createNew.isSelected()){

		    prefs.CreateNew=true;

		    prefs.OnlyIfExists=false;

		}else{

			prefs.CreateNew=false;

		    prefs.OnlyIfExists=true;

		}
try{
		prefs.UndoLevel=(new Integer(undoLevel.getText())).intValue();
//Istvan phase 5
		prefs.CycleNumber=(new Integer(cycleNumber.getText())).intValue();
//End Istvan phase 5
		if(prefs.UndoLevel>MaxUndo)

			prefs.UndoLevel=MaxUndo;

		prefs.Width=(new Integer(width.getText())).intValue();

		prefs.Height=(new Integer(height.getText())).intValue();

		prefs.Anim=(new Double(animInterval.getText())).doubleValue();
		prefs.Transition=trans.getSelectedItem().toString();
		System.out.println(trans.getSelectedItem().toString());
}catch(Exception e){
	except = true;
}
if(prefs.UndoLevel <0 ||prefs.Width <0 || prefs.Height<0||prefs.CycleNumber<-1)
	except = true;
if(!except){
	try{

			FileWriter fos=new FileWriter("prefs.txt");

			BufferedWriter bw=new BufferedWriter(fos);

			PrintWriter pw=new PrintWriter(bw);

			pw.println(prefs.ToNewFile);

			pw.println(prefs.ToNewFileAndWindow);

			pw.println(prefs.ToNewLayer);

			pw.println(prefs.ToCurrentLayer);

			pw.println(prefs.OnlyIfExists);

			pw.println(prefs.CreateNew);

			pw.println(prefs.LastSize);

			pw.println(prefs.PromptSize);

			pw.println(prefs.FixedSize);

			pw.println(prefs.Anim);

			pw.println(prefs.Width);

			pw.println(prefs.Height);

			pw.println(prefs.UndoLevel);
	    pw.println(prefs.Transition);
	    //Istvan phase 5
	    pw.println(prefs.CycleNumber);
	    //	      System.out.println("in file" +prefs.Transition);
	    //End Istvan phase 5


			pw.flush();

			pw.close();

		}catch(Exception IOE){}
    
	this.closeDialog(new java.awt.event.WindowEvent(this, 0));
    }
    else{
    	except = false;
		JOptionPane.showMessageDialog(this,
			    "Please Enter Correct Format and Reasonable Values",
			    "Error",
			    JOptionPane.ERROR_MESSAGE);
    }
    	
    }



    /** This method is for when current layer is chosen.

     * Disables option to specify how to create new file.

     * There are no OS dependencies and variances.  No security constraints or external

     * specifications.

     * @param evt The event of a mouse action.

     */

    public void toCurrentLayerActionPerformed(java.awt.event.ActionEvent evt) {

	createNew.setEnabled(false);

		onlyIfExists.setEnabled(false);// Add your handling code here:

    }

    /** This method is for when new layer is chosen.

     * Disables option to specify how to create new file.

     * There are no OS dependencies and variances.  No security constraints or external

     * specifications.

     * @param evt The event of a  mouse action.

     */

    public void toNewLayerActionPerformed(java.awt.event.ActionEvent evt) {

	createNew.setEnabled(false);

		onlyIfExists.setEnabled(false);// Add your handling code here:

    }

    /** This method is for when to new File is chosen.

     * Enables option to specify how to create new file.

     * There are no OS dependencies and variances.  No security constraints or external

     * specifications.

     * @param evt The event of a  mouse action.

     */

    public void toNewFileActionPerformed(java.awt.event.ActionEvent evt) {

	createNew.setEnabled(true);

		onlyIfExists.setEnabled(true);// Add your handling code here:

    }

     /** This method is for when to new File and open is chosen.

     * Enables option to specify how to create new file.

     * There are no OS dependencies and variances.  No security constraints or external

     * specifications.

     * @param evt The event of a  mouse action.

     */

    public void toNewFileAndOpenActionPerformed(java.awt.event.ActionEvent evt) {

	createNew.setEnabled(true);

		onlyIfExists.setEnabled(true);// Add your handling code here:

    }
/** This method is for the cycle Number.

     * There are no OS dependencies and variances.  No security constraints or external

     * specifications.

     * @param evt The event of a  mouse action.

     */

    public void cycleNumberActionPerformed(java.awt.event.ActionEvent evt) {

	// Add your handling code here:

    }
    /** This method is for the undo level.

     * There are no OS dependencies and variances.  No security constraints or external

     * specifications.

     * @param evt The event of a  mouse action.

     */

    public void undoLevelActionPerformed(java.awt.event.ActionEvent evt) {

	// Add your handling code here:

    }

	/** This method is for the create new action.

     * There are no OS dependencies and variances.  No security constraints or external

     * specifications.

     * @param evt The event of a mouse action.

     */

    public void createNewActionPerformed(java.awt.event.ActionEvent evt) {

	// Add your handling code here:

    }

	/** This method is for the only if exists action.

     * There are no OS dependencies and variances.  No security constraints or external

     * specifications.

     * @param evt The event of a mouse action.

     */

    public void onlyIfExistsActionPerformed(java.awt.event.ActionEvent evt) {

	// Add your handling code here:

    }

	/** This method is for the animation interval action.

     * There are no OS dependencies and variances.  No security constraints or external

     * specifications.

     * @param evt The event of a mouse action.

     */

    public void animIntervalActionPerformed(java.awt.event.ActionEvent evt) {

	// Add your handling code here:

    }

	/** This method is for the last size action.

	 * Disables option to specify the size.

     * There are no OS dependencies and variances.  No security constraints or external

     * specifications.

     * @param evt The event of a mouse action.

     */

    public void lastSizeActionPerformed(java.awt.event.ActionEvent evt) {

	width.setEnabled(false);

		jLabel1.setEnabled(false);

		height.setEnabled(false);

	jLabel0.setEnabled(false);

    }

	/** This method is for the prompt size action.

	 * Disables option to specify the size.

     * There are no OS dependencies and variances.  No security constraints or external

     * specifications.

     * @param evt The event of a mouse action.

     */

    public void promptSizeActionPerformed(java.awt.event.ActionEvent evt) {

	width.setEnabled(false);

		jLabel1.setEnabled(false);

		height.setEnabled(false);

	jLabel0.setEnabled(false);

    }

	/** This method is for the fixed size action.

	 * Enables option to specify the size.

     * There are no OS dependencies and variances.  No security constraints or external

     * specifications.

     * @param evt The event of a mouse action.

     */

    public void fixedSizeActionPerformed(java.awt.event.ActionEvent evt) {

	width.setEnabled(true);

		jLabel1.setEnabled(true);

		height.setEnabled(true);

	jLabel0.setEnabled(true);

    }

	/** This method is for the width action.

     * There are no OS dependencies and variances.  No security constraints or external

     * specifications.

     * @param evt The event of a mouse action.

     */

    public void widthActionPerformed(java.awt.event.ActionEvent evt) {

	// Add your handling code here:

	}

	/** This method is for the height action.

     * There are no OS dependencies and variances.  No security constraints or external

     * specifications.

     * @param evt The event of a mouse action.

     */

    public void heightActionPerformed(java.awt.event.ActionEvent evt) {

	// Add your handling code here:

    }

    /** Closes the dialog.

     * There are no OS dependencies and variances.  No security constraints or external

     * specifications.

     * @param evt The event of an action.

     */

    public void closeDialog(java.awt.event.WindowEvent evt) {

	setVisible(false);

	dispose();

    }

/** Detects and adds all transitions to families JComboBox.

 */

	public void addTrans(){

	transitions=new String[2];

	int index=0;

	transitions[0]="(none)";
	transitions[1]="Blur";

	int numTrans=2;

	TreeSet mySet=new TreeSet();

	try{

		while(index<numTrans){

				if (!mySet.contains(transitions[index]))

					mySet.add(transitions[index]);

				index++;

			}

		}catch(ArrayIndexOutOfBoundsException aoobe){}

		Iterator myIterator=mySet.iterator();

		while(myIterator.hasNext()){

			trans.addItem(myIterator.next());

		}



	}

/** Detects and adds all local platform's Languages to families JComboBox. */

	public void addLangs(){

	Locale[] locales=Locale.getAvailableLocales();

	int index=0;
	int StrIndex=0;

	lang_list=new TreeSet();

	boolean oob=false;
	while(!oob){

			if (!lang_list.contains(locales[index].getISO3Language())){

				lang_list.add(locales[index].getISO3Language());

			}
			index++;
			try{
				if(locales[index+1]!=null){
					//just wnated to check for outofbounds
				}
			}catch(ArrayIndexOutOfBoundsException aoobe){
			oob=true;
			}

		}


		Iterator myIterator=lang_list.iterator();

		while(myIterator.hasNext()){

			langs.addItem(myIterator.next());

		}



		//langs.addItem("English");

		//langs.addItem("Spanish");

	}

    /** This method basically creates a new STRETCH object and applies the image

     * into it.	 If there are arguments, it will take them as well.  It will then

     * display the results onto the main canvas.

     * There are no OS dependencies and variances.  No security constraints or external

     * specifications.

     * @param args The command line arguments

     */

    public static void main(String args[]) {

	new preferences(new javax.swing.JFrame(), true).show();

    }





    // Variables declaration - do not modify

    /** Java swing GUI depicting the OK and CANCEL panel.

     */

    public javax.swing.JPanel ok_cancel;



    /** Java swing GUI depicting the OK button.

     */

    public javax.swing.JButton ok;



    /** Java swing GUI depicting the CANCEL button.

     */

    public javax.swing.JButton cancel;



    /** Java swing GUI depicting the CENTER panel.

     */

    public javax.swing.JPanel center;

    /** Java swing GUI depicting the top panel.

	     */

    public javax.swing.JPanel top;

    /** Java swing GUI depicting the bottom panel.

	     */

    public javax.swing.JPanel bottom;

    /** Java swing GUI depicting the main panel.

    */

    public javax.swing.JPanel prefs;

    /** Java swing GUI depicting the langauges panel.

	    */

    public javax.swing.JPanel lang;

    /** Java swing GUI depicting the transitions panel.

		    */

    public javax.swing.JPanel tran;

    /** Java swing GUI depicting the image size panel.

	    */

    public javax.swing.JPanel imageAttrib;



    /** Java swing GUI depicting the undo panel.

     */

    public javax.swing.JPanel undo;



    /** Java swing GUI depicting the textfield of the undo level.

     */

    public javax.swing.JTextField undoLevel;

    /** Java swing GUI depicting the Label 0.

     */

    public javax.swing.JLabel jLabel0;



    /** Java swing GUI depicting the Label 1.

     */

    public javax.swing.JLabel jLabel1;

    /** Java swing GUI depicting the Label 2.

     */

    public javax.swing.JLabel jLabel2;



    /** Java swing GUI depicting the Label 3.

     */

    public javax.swing.JLabel jLabel3;
//Istvan phase 5
    /** Java swing GUI depicting the cycle number label.

	     */

    public javax.swing.JLabel jLabelCycle;
    /** Java swing GUI depicting the cycle number parameter definition.

		     */

    public javax.swing.JLabel jLabelCycle2;
//End Istvan phase 5


    /** Java swing GUI depicting the Paste/Import To panel.

     */

    public javax.swing.JPanel pastePrefs;

    /** Java swing GUI depicting the File panel.

	     */

    public javax.swing.JPanel filePrefs;



    /** Java swing GUI depicting the JPanel of the animation interval.

     */

     public javax.swing.JPanel anim;

     /** Java swing GUI depicting the JPanel of the width.

	      */

     public javax.swing.JPanel widthPanel;

     /** Java swing GUI depicting the JPanel of the height.

	      */

     public javax.swing.JPanel heightPanel;

     /** Java swing GUI depicting the JPanel of image size for new files.

	      */

     public javax.swing.JPanel imageAttrib2;

     /** Java swing GUI depicting the JPanel of languages and transitions.

		  */

     public javax.swing.JPanel langandtran;

     /** Java swing GUI depicting the JComboBox of the languages.

     */

     public javax.swing.JComboBox langs;

/** Java swing GUI depicting the JComboBox of the transitions.

     */

     public javax.swing.JComboBox trans;



     /** Java swing GUI depicting the text field of the animation interval.

     */

    public javax.swing.JTextField animInterval;



    /** Java swing GUI depicting the text field of the width of new images.

     */

    public javax.swing.JTextField width;



    /** Java swing GUI depicting the text field of the height of new images.

     */

    public javax.swing.JTextField height;
//Istvan phase 5
   /** Java swing GUI depicting the text field of the number of cycles used when animating.

	*/

    public javax.swing.JTextField cycleNumber;
//End Istvan phase 5

	/** Java swing GUI depicting the button for the option to paste to a new window.

     */

    public javax.swing.JRadioButton toNewFileAndOpen;

    /** Java swing GUI depicting the button for the option to paste to a new file.

     */

    public javax.swing.JRadioButton toNewFile;

    /** Java swing GUI depicting the button for the option to paste to a new layer of current file.

     */

    public javax.swing.JRadioButton toNewLayer;

    /** Java swing GUI depicting the button for the option to paste to the current layer of current file.

	*/

    public javax.swing.JRadioButton toCurrentLayer;

    /** Java swing GUI depicting the button for the option to create a new file for paste to File.

	*/

    public javax.swing.JRadioButton createNew;

    /** Java swing GUI depicting the button for the option to use last size for new files.

	*/

    public javax.swing.JRadioButton lastSize;

    /** Java swing GUI depicting the button for the option to prompt for size of new files.

	*/

    public javax.swing.JRadioButton promptSize;

    /** Java swing GUI depicting the button for the option to use specified size for new files.

	*/

    public javax.swing.JRadioButton fixedSize;

    /** Java swing GUI depicting the button for the option to only pasteTo exiting files.

	*/

    public javax.swing.JRadioButton onlyIfExists;

    /** Java swing GUI depicting the button group for new Files inpasting.

	*/

    public javax.swing.ButtonGroup newFiles;

    /** Java swing GUI depicting the button group for size creation.

	*/

    public javax.swing.ButtonGroup imageSizes;

    /** Java swing GUI depicting the button group for where to paste.

	*/

    public javax.swing.ButtonGroup pasteOrImportTo;

    // End of variables declaration



}
