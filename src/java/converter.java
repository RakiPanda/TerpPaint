// Copyright 1997, 1998 Carmen Delessio (carmen@blackdirt.com)
// Black Dirt Software http://www.blackdirt.com/graphics
// Free for non-commercial use

/*
 * converter.java
 *
 * Created on February 11, 2003, 8:44 AM
 */

/**
 *
 * @author Terp Paint
 * @version 2.0
 */

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/** This class initialize all the variables including the the integers,
 * pixels and so fourth.
 * The state is enable.
 * There are no OS/Hardware dependencies and no variances.  There is no need for any
 * security constraints and no references to external specifications.
 */
public class converter extends Canvas{

  //--- Private constants
  /** A size of bitmap file header to 14 as a final integer.
  */
  private final static int BITMAPFILEHEADER_SIZE = 14;

  /** A size of bipmap info header to 40 as a final integer.*/
  private final static int BITMAPINFOHEADER_SIZE = 40;

  //--- Private variable declaration
  //--- Bitmap file header
  /** Initialization of bitmap file header as an array of type byte with the size of 14. */
  private byte bitmapFileHeader [] = new byte [14];

  /** Initialization of bfType as an array of type byte with the value B and M casted by byte. */
  private byte bfType [] = {(byte)'B', (byte)'M'};

  /** Initialization of bitmap file size with the value 0.*/
  private int bfSize = 0;

  /** Initialization of bitmap file reserved1 with the value 0.*/
  private int bfReserved1 = 0;

  /** Initialization of bitmap file reserved2 with the value 0.*/
  private int bfReserved2 = 0;

  /** Initialization of bit map off bits by addition of BITMAPFILEHEADER and BITMAPINFOHEADER.*/
  private int bfOffBits = BITMAPFILEHEADER_SIZE + BITMAPINFOHEADER_SIZE;

  //--- Bitmap info header
  /** Initialization of bitmap info header as an array of type byte with the size of 40. */
  private byte bitmapInfoHeader [] = new byte [40];

  /** Initialization of bitmap info size by BITMAPINFOHEADER_SIZE.*/
  private int biSize = BITMAPINFOHEADER_SIZE;

  /** Initialization of bitmap info width with the size of 0.*/
  private int biWidth = 0;

  /** Initialization of bitmap info height with the size of 0.*/
  private int biHeight = 0;

  /** Initialization of bitmap info planes with the size of 1.*/
  private int biPlanes = 1;

  /** Initialization of bitmap info bit count with the size of 24.*/
  private int biBitCount = 24;

  /** Initialization of bitmap info compression with the size of 0.*/
  private int biCompression = 0;

  /** Initialization of bitmap info image size with the size of 0x030000.*/
  private int biSizeImage = 0x030000;

  /** Initialization of bitmap info x-pixel per meter with the size of 0x0.*/
  private int biXPelsPerMeter = 0x0;

  /** Initialization of bitmap info y-pixel per meter with the size of 0x0.*/
  private int biYPelsPerMeter = 0x0;

  /** Initialization of bitmap info color used indicator with the size of 0.*/
  private int biClrUsed = 0;

  /** Initialization of bitmap info important color indicator with the size of 0.*/
  private int biClrImportant = 0;

  /** Bitmap type with integer value. */
  private int type;

  //--- Bitmap raw data
  /** Bipmap integer array that creates an bitmap raw data.*/
  private int bitmap [];

  //--- File section
  /** FileOutputStream object that performs byte-based output stream. */
  private FileOutputStream fo;

  //--- Default constructor
  /** bytebuffer object that creates byte type array. */
  private byte[] bytebuffer;

  /** Integer value to be used as an index.*/
  private int i;

  /** Integer value to be used as an index.*/
  private int j;

  /** windowLong integer value to be used when converting occurs between long and integer.*/
  private int windowLong;

  /** windowInt short value to be used when converting occurs between long and integer.*/
  private short windowInt;

  /** DataInputStream object to be used with byte-based file stream. */
  private DataInputStream d;

  /** Integer value of the size of bitmap that describes the header in bytes. */
  int BMPsize;

  /** Integer value of the preserved description of bitmap. */
  int BMPreserved;

  /** Integer value of bitmap image offset. */
  int BMPimageoffset;

  /** Integer value of bitmap header size. */
  int BMPheadersize;

  /** Integer value of bitmap width size in pixels. */
  int BMPwidth; // image width in pixels

  /** Integer value of bitmap height size in pixels. */
  // image height in pixels (if < 0, "top-down")
  int BMPheight;

  /** Short value of color planes, always 1. */
  short BMPplanes;

  /** Number of bits per pixel -  1, 4, 8, or 24 (no color map). */
  short BMPbitsPerPixel;

  /** Compression methods used: 0 (none), 1 (8-bit RLE), or 2 (4-bit RLE). */
  int BMPcompression;

  /** Size of bitmap in bytes (may be 0: if so, calculate). */
  int BMPsizeOfBitmap;

  /** Horizontal resolution, pixels/meter (may be 0). */
  int BMPhorzResolution;

  /** Vertical resolution, pixels/meter (may be 0). */
  int BMPvertResolution;

  /** Number of colors in palette (if 0, calculate). */
  int BMPcolorsUsed;

  /** Number of important colors (appear first in palette) (0 means all are important). */
  int BMPcolorsImportant;

  /** Color table array. */
  Color colorTable[];

  /** Array of pixels. */
  int pixels[];

  /** Color index. */
  int colorIndex;

  /** Indicator of bytes per line. */
  int bytesPerLine;

  /** Byte type array of scan line. */
  byte scanline[];

  /** Bitmap image representation. */
  Image BMPimage;

    /** This method sets up the image in the buffer. It stores the
     * image or drawing in the memory.
     * There is no state transition.
     * The argument value is FileInputStream.
     * There are no NULL value.
     * The return value is a BufferedImage.
     * The method stores the drawing in the buffer.
     * There are no OS/Hardware dependencies and no variances.
     * Implementation Variances are not allowed.
     * There is one exception.
     * There is no security constraints.
     * @param is This is a FileInputStream variable used to get data input stream
     * @return The return type is a BufferedImage variable
     */
  public BufferedImage FileToBufferedImage(FileInputStream is) //throws IOException
  {
	try {
	  d = new DataInputStream(is);
	  parseit();
	  BMPimage = createImage (new MemoryImageSource (BMPwidth,BMPheight, pixels, 0, BMPwidth));
	  is.close();
	} catch (IOException ex){
	  System.out.println("Error Creating Bitmap Image");
	}
	BufferedImage im = new BufferedImage(BMPimage.getWidth(this), BMPimage.getHeight(this),BufferedImage.TYPE_INT_RGB);
	Graphics2D g2d = im.createGraphics();
	g2d.drawImage(BMPimage, 0,0,this);
	return im;
       //BMPimage = new ImageIcon(BMPimage).getImage();
       //return new BufferedImage(BMPimage.getWidth(null),BMPimage.getHeight(null), BufferedImage.TYPE_INT_RGB);
  }

   /** This method sets up the image in the buffer. It stores the
     * image or drawing in the memory.
     * There is no state transition.
     * The argument value is FileInputStream, BufferedImage and a int.
     * There are no NULL value.
     * There are no return values.
     * The method stores the drawing in the buffer.
     * There are no OS/Hardware dependencies and no variances.
     * Implementation Variances are not allowed.
     * There is one exception.
     * There is no security constraints.
     * @param in_fo is a FileInputStream variable
     * @param parImage is a BufferedImage variable
     * @param type is a int variable
     */

  public void BufferedImageToFile (FileOutputStream in_fo, BufferedImage parImage, int type) {
      System.out.println("in image to file");
      this.type = type;
      fo = in_fo;
      int parWidth = parImage.getWidth();
      int parHeight = parImage.getHeight();
      try {
	save (parImage, parWidth, parHeight);
	fo.close ();
	parImage.flush();
      }
      catch (Exception saveEx) {
	saveEx.printStackTrace ();
      }
  }

   /** This method calls the parseit function and initialize it to 0
    * There is no state transition.
    * There are no argument.
    * There are no NULL values.
    * There are no return values.
    * The method calls the parseit function.
    * There are no OS/Hardware dependencies and no variances.
    * Implementation Variances are not allowed.
    * There is one exception.
    * There is no security constraints.
    * @throws IOException there is an exception
    */

  public synchronized void  parseit() throws IOException
  {
     parseit(0);
  }

 /** This method prints out the results of the image
 * There is no state transition.
 * The argument is an int.
 * There are no NULL values.
 * There are no return values.
 * The method calls all the print function.
 * There are no OS/Hardware dependencies and no variances.
 * Implementation Variances are not allowed.
 * There is one exception.
 * There is no security constraints.
 * @throws IOException there is an exception
 * @param typeFlag this is an int variable
 */

  public synchronized void  parseit(int typeFlag) throws IOException
  {
       byte[] f_long =new byte[4];
       byte[] f_int = new byte[2];
       byte[] parmBuffer;
       byte[] tempBuffer;
       short x;
       short y;
       short x2;
       short y2;
       short count = 0;
       //begin bitmap header

	if (typeFlag == 0) {  // read header - bitmapfile
	    tempBuffer = null;
	    tempBuffer = new byte[2];  //get BM

	    d.read(tempBuffer);
	    System.out.println ("tempBuffer " + new String(tempBuffer,0));
	    BMPsize = readLong(d); //key  4 bytes
	    System.out.println ("size " + BMPsize);

	    BMPreserved = readLong(d);
	    System.out.println ("reservede " + BMPreserved);

	    BMPimageoffset = readLong(d);
	    System.out.println ("offset " + BMPimageoffset);
	}

	System.out.println ("in bmpstream" );
	BMPheadersize = readLong(d);
	System.out.println ("BMPheadersize " + BMPheadersize);

	BMPwidth = readLong(d);
	System.out.println ("BMPwidth " + BMPwidth);

	BMPheight = readLong(d);
	System.out.println ("BMPheight " + BMPheight);

	BMPplanes = readInt(d);
	System.out.println ("BMPplanes " + BMPplanes);

	BMPbitsPerPixel = readInt(d);
	System.out.println ("BMPbitsPerPixel " + BMPbitsPerPixel);


	BMPcompression = readLong(d);
	System.out.println ("BMPcompression " + BMPcompression);

	BMPsizeOfBitmap = readLong(d);
	System.out.println ("BMPsizeOfBitmap " + BMPsizeOfBitmap);

	BMPhorzResolution = readLong(d);
	System.out.println ("BMPhorzResolution " + BMPhorzResolution);

	BMPvertResolution = readLong(d);
	System.out.println ("BMPvertResolution " + BMPvertResolution);

	BMPcolorsUsed = readLong(d);
	System.out.println ("BMPcolorsUsed " + BMPcolorsUsed);

	BMPcolorsImportant = readLong(d);
	System.out.println ("BMPcolorsImportant " + BMPcolorsImportant);


	pixels = new int [BMPwidth * (BMPheight +1)];

	if (BMPbitsPerPixel== 1){
	  colorTable = new Color[2];
	  for (i =0; i < 2; i++){
	    colorTable[i] = win2Color(readLong(d));
	  }
	  bytesPerLine = BMPwidth/8;   //width is # of pixels, twice as many bytes as pixles
				       // only used to read in scan lines
	  if (bytesPerLine * 8 < BMPwidth) {  // if pixel is on odd boundary
	      bytesPerLine++;
	  }

	  while (bytesPerLine % 4 != 0)
	     bytesPerLine++; // get even boundary, DWORD boundary

	  scanline = new byte[bytesPerLine]; // declare a buffer sufficient for 1 line

	  for (i = BMPheight -1; i >= 0; i--){	  // bottom up, start with last line
	    d.readFully(scanline,0, bytesPerLine);  // read in a line

	    for (j = 0; j < BMPwidth; j+=8){
	      colorIndex = (scanline[j/8])>>7 & 0x01;  // 1st 4 bits of byte shifted and masked
	      pixels[i*BMPwidth + j]= colorTable[colorIndex].getRGB();

	      colorIndex = (scanline[j/8])>>6 & 0x01;  // 1st 4 bits of byte shifted and masked
	      pixels[i*BMPwidth + j +1 ]= colorTable[colorIndex].getRGB();

	      colorIndex = (scanline[j/8])>>5 & 0x01;  // 1st 4 bits of byte shifted and masked
	      pixels[i*BMPwidth + j +2]= colorTable[colorIndex].getRGB();

	      colorIndex = (scanline[j/8])>>4 & 0x01;  // 1st 4 bits of byte shifted and masked
	      pixels[i*BMPwidth + j +3]= colorTable[colorIndex].getRGB();

	      colorIndex = (scanline[j/8])>>3 & 0x01;  // 1st 4 bits of byte shifted and masked
	      pixels[i*BMPwidth + j +4]= colorTable[colorIndex].getRGB();

	      colorIndex = (scanline[j/8])>>2 & 0x01;  // 1st 4 bits of byte shifted and masked
	      pixels[i*BMPwidth + j +5]= colorTable[colorIndex].getRGB();

	      colorIndex = (scanline[j/8])>>1 & 0x01;  // 1st 4 bits of byte shifted and masked
	      pixels[i*BMPwidth + j +6]= colorTable[colorIndex].getRGB();

	      colorIndex = (scanline[j/8]) & 0x01;  // 1st 4 bits of byte shifted and masked
	      pixels[i*BMPwidth + j +7]= colorTable[colorIndex].getRGB();

	    }
	  }
       } // if bpp = 1

	if (BMPbitsPerPixel== 4){
	  colorTable = new Color[16];
	  for (i =0; i < 16; i++){
	    colorTable[i] = win2Color(readLong(d));
	  }
	  bytesPerLine = BMPwidth/2;   //width is # of pixels, twice as many bytes as pixles
				       // only used to read in scan lines
	  if (bytesPerLine * 2 < BMPwidth) {  // if pixel is on odd boundary
	      bytesPerLine++;
	  }

	  while (bytesPerLine % 4 != 0)
	     bytesPerLine++; // get even boundary, DWORD boundary

	  scanline = new byte[bytesPerLine]; // declare a buffer sufficient for 1 line
	  try{
	  for (i = BMPheight -1; i >= 0; i--){	  // bottom up, start with last line
	    d.readFully(scanline,0, bytesPerLine);  // read in a line

	    for (j = 0; j < BMPwidth; j+=2){
	      colorIndex = (scanline[j/2]>>4) & 0x0F;  // 1st 4 bits of byte shifted and masked
	      pixels[i*BMPwidth + j]= colorTable[colorIndex].getRGB();
	      colorIndex = (scanline[j/2]) & 0x0F;  // 2nd 4 bits masked
	      pixels[i*BMPwidth + j + 1]= colorTable[colorIndex].getRGB();
	    }
	  }
	}catch(Exception e){System.out.println("HEY, WE're DONE");}
       } // if bpp = 4



	if (BMPbitsPerPixel== 8){
	  colorTable = new Color[256];
	  for (i =0; i < 256; i++){
	    colorTable[i] = win2Color(readLong(d));
	  }
	  bytesPerLine = BMPwidth;   //width is # of pixels, 1 pixels for each byte
	  while (bytesPerLine % 4 != 0)
	     bytesPerLine++; // get even boundary
	  scanline = new byte[bytesPerLine]; // declare a buffer sufficient for 1 line
	  System.out.println ("bytesPerLine " + bytesPerLine );

	  try{
	   for (i = BMPheight -1; i >= 0; i--){	   // bottom up, start with last line
	     d.readFully(scanline);  // read in a line
	     for (j = 0; j < BMPwidth; j++){
	       colorIndex = scanline[j] ;
	       if (colorIndex <0)
		 colorIndex +=256;
	       pixels[i*BMPwidth + j]= colorTable[colorIndex].getRGB();
	     }
	   }
	 }catch(Exception f){}
	} // if bpp = 8



	if (BMPbitsPerPixel== 24){
	System.out.println ("in bmpstream bpp =24 ");

	  int winBlue;
	  int winGreen;
	  int winRed;
	  bytesPerLine = 3 * BMPwidth;	 //width is # of pixels, 3 bytes for each pixel
	  while (bytesPerLine % 4 != 0)
	     bytesPerLine++; // get even boundary
	  scanline = new byte[bytesPerLine+4]; // declare a buffer sufficient for 1 line
	 try{
	  for (i = BMPheight -1; i >= 0; i--){	  // bottom up, start with last line
	    d.readFully(scanline,0, bytesPerLine);  // read in a line
	    for (j = 0; j < bytesPerLine; j+=3){ //work with 3 bytes at a time
	      //j
	      //j+1
	      //j+2
	      winBlue =	  (int) (scanline[j]) & 0xff;
	      winGreen = ((int) (scanline[j+1]) & 0xff) << 8;
	      winRed =	 ((int) (scanline[j+2]) & 0xff) << 16;
	      pixels[i*BMPwidth + j/3]= 0xff000000;
	      pixels[i*BMPwidth + j/3] |= winRed;
	      pixels[i*BMPwidth + j/3] |= winGreen;
	      pixels[i*BMPwidth + j/3] |= winBlue;
	    }
	 }
	}catch(Exception g){System.out.println("DONE WITH 24-bit");}
       } // if bpp = 24

  }


  /** Sets windows RGB color value.
   * There are no OS/Hardware dependencies and no variances.
   * There are no security constraints.
   * There are no references to external specifications.
   * @param colorValue used to set windows RGB color value.
   * @return new Color RGB value.
   */
    public Color win2Color( int colorValue) {

       // windows does it backwards
       int rgbBlue   = 16711680; // ff0000
       int rgbGreen  =	  65280; // 00ff00
       int rgbRed    =	    255; // 0000ff

       int javaBlue ;
       int javaGreen;
       int javaRed  ;

       javaRed = (int)((colorValue & rgbBlue)/ 65536);
       javaGreen = (int)((colorValue & rgbGreen)/ 256);
       javaBlue = (int)((colorValue & rgbRed));

       return( new Color (javaRed , javaGreen, javaBlue));
    }


  /** Reads DataInputStream and converts it to int value.
   * There are no OS/Hardware dependencies and no variances.
   * There are no security constraints.
   * There are no references to external specifications.
   * @param d DataInputStream.
   * @return fliplong/99 returns integer value if no error occurs, otherwise it returns 99.
   */

   public int readLong( DataInputStream d){
       byte[] longBuf =new byte[4];

	try{
	    d.readFully(longBuf);
	    return flipLong(longBuf);
	}catch(IOException e){
	    System.err.println(e);
	    return 99;
	}
   }

  /** Reads DataInputStream and converts it to long value.
   * There are no OS/Hardware dependencies and no variances.
   * There are no security constraints.
   * There are no references to external specifications.
   * @param d DataInputStream.
   * @return flipint/99 returns short value when no error occurs, otherwise it returns 99.
   */
   public short readInt( DataInputStream d){
	byte[] intBuf =new byte[2];

	try{
	    d.readFully(intBuf);
	    return flipInt(intBuf);
	 }  catch(IOException e){
	    System.err.println(e);
	    return 99;
	 }
   }

  /** Reads byte array object and converts it to int value.
   * There are no OS/Hardware dependencies and no variances.
   * There are no security constraints.
   * There are no references to external specifications.
   * @param byteFlip byte type array.
   * @return readInt/0 returns integer value when no error occurs, otherwise it returns o.
   */
   public int flipLong( byte[] byteFlip){
	 DataInputStream dl;
	 ByteArrayInputStream  b_in;
	 byte[] bytebuffer;
	 bytebuffer = new byte[4];
	 bytebuffer[0] = byteFlip[3];
	 bytebuffer[1] = byteFlip[2];
	 bytebuffer[2] = byteFlip[1];
	 bytebuffer[3] = byteFlip[0];

	 b_in = new ByteArrayInputStream(bytebuffer);
	 dl = new DataInputStream(b_in);
	 try{
	   return dl.readInt();
	 }
	  catch(IOException e){System.err.println(e);}
	    return 0;
  }

  /** Reads byte array object and converts it to short value.
   * There are no OS/Hardware dependencies and no variances.
   * There are no security constraints.
   * There are no references to external specifications.
   * @param byteFlip byte type array.
   * @return readInt/0 returns short value when no error occurs, otherwise it returns o.
   */
   public short flipInt( byte[] byteFlip){
	 DataInputStream d;
	 ByteArrayOutputStream b_out;
	 ByteArrayInputStream  b_in;
	 byte[] bytebuffer;

	 bytebuffer = new byte[2];
	 bytebuffer[0] = byteFlip[1];
	 bytebuffer[1] = byteFlip[0];

	 b_in = new ByteArrayInputStream(bytebuffer);
	 d = new DataInputStream(b_in);
	 try{
	   return d.readShort();
	 }
	 catch(IOException e){ System.err.println(e);}
	    return 0;

  }


  /**  The saveMethod is the main method of the process. This method
   *  will call the convertImage method to convert the memory image to
   *  a byte array; method writeBitmapFileHeader creates and writes
   *  the bitmap file header; writeBitmapInfoHeader creates the
   *  information header; and writeBitmap writes the image.
   * There are no OS/Hardware dependencies and no variances.
   * There are no security constraints.
   * There are no references to external specifications.
   *@param parImage Image object.
   *@param parWidth int value that determines the size of width.
   *@param parHeight int value that determines the size of height.
   */
  public void save (Image parImage, int parWidth, int parHeight) {
     try {
	convertImage (parImage, parWidth, parHeight);
	writeBitmapFileHeader ();
	writeBitmapInfoHeader ();
	writeBitmap ();
     }
     catch (Exception saveEx) {
	saveEx.printStackTrace ();
     }
  }

  /** convertImage converts the memory image to the bitmap format (BRG).
   * It also computes some information for the bitmap info header.
   * There are no OS/Hardware dependencies and no variances.
   * There are no security constraints.
   * There are no references to external specifications.
   *@param parImage Image object.
   *@param parWidth int value that determines the size of width.
   *@param parHeight int value that determines the size of height.
   *@return true/false returns true when image is successfully converted, otherwise false.
   */
  public boolean convertImage (Image parImage, int parWidth, int parHeight) {
     int pad;
     bitmap = new int [parWidth * parHeight];
     PixelGrabber pg = new PixelGrabber (parImage, 0, 0, parWidth, parHeight,
					 bitmap, 0, parWidth);
     try {
	pg.grabPixels ();
     }
     catch (InterruptedException e) {
	e.printStackTrace ();
	return (false);
     }
     pad = (4 - ((parWidth * 3) % 4)) * parHeight;
     biSizeImage = ((parWidth * parHeight) * 3) + pad;
     bfSize = biSizeImage + BITMAPFILEHEADER_SIZE + BITMAPINFOHEADER_SIZE;
     biWidth = parWidth;
     biHeight = parHeight;
     return (true);
  }

  /**
   * writeBitmap converts the image returned from the pixel grabber to
   * the format required. Remember: scan lines are inverted in
   * a bitmap file.
   * There are no OS/Hardware dependencies and no variances.
   * There are no security constraints.
   * There are no references to external specifications.
   * Each scan line must be padded to an even 4-byte boundary.
   */
  public void writeBitmap () {
      int size;
      int value;
      int j;
      int i;
      int rowCount;
      int rowIndex;
      int lastRowIndex;
      int pad;
      int padCount;
      byte rgb [] = new byte [3];
      size = (biWidth * biHeight) - 1;
      pad = 4 - ((biWidth * 3) % 4);
      if (pad == 4)   // <==== Bug correction
	 pad = 0;     // <==== Bug correction
      rowCount = 1;
      padCount = 0;
      rowIndex = size - biWidth;
      lastRowIndex = rowIndex;
      try {
	 for (j = 0; j < size; j++) {
	    value = bitmap [rowIndex];
	    rgb [0] = (byte) (value & 0xFF);
	    rgb [1] = (byte) ((value >> 8) & 0xFF);
	    rgb [2] = (byte) ((value >>	 16) & 0xFF);
	    fo.write (rgb);
	    if (rowCount == biWidth) {
	       padCount += pad;
	       for (i = 1; i <= pad; i++) {
		  fo.write (0x00);
	       }
	       rowCount = 1;
	       rowIndex = lastRowIndex - biWidth;
	       lastRowIndex = rowIndex;
	    }
	    else
	       rowCount++;
	    rowIndex++;
	 }
	 //--- Update the size of the file
	 bfSize += padCount - pad;
	 biSizeImage += padCount - pad;
      }
      catch (Exception wb) {
	 wb.printStackTrace ();
      }
   }

  /**
   * writeBitmapFileHeader writes the bitmap file header to the file.
   * There are no OS/Hardware dependencies and no variances.
   * There are no security constraints.
   * There are no references to external specifications.
   */
  public void writeBitmapFileHeader () {
     try {
	fo.write (bfType);
	fo.write (intToDWord (bfSize));
	fo.write (intToWord (bfReserved1));
	fo.write (intToWord (bfReserved2));
	fo.write (intToDWord (bfOffBits));
     }
     catch (Exception wbfh) {
	wbfh.printStackTrace ();
     }
  }

  /**
   * writeBitmapInfoHeader writes the bitmap information header
   * to the file.
   * There are no OS/Hardware dependencies and no variances.
   * There are no security constraints.
   * There are no references to external specifications.
   */
  public void writeBitmapInfoHeader () {
     try {
	fo.write (intToDWord (biSize));
	fo.write (intToDWord (biWidth));
	fo.write (intToDWord (biHeight));
	fo.write (intToWord (biPlanes));
	fo.write (intToWord (biBitCount));
	fo.write (intToDWord (biCompression));
	fo.write (intToDWord (biSizeImage));
	fo.write (intToDWord (biXPelsPerMeter));
	fo.write (intToDWord (biYPelsPerMeter));
	fo.write (intToDWord (biClrUsed));
	fo.write (intToDWord (biClrImportant));
     }
     catch (Exception wbih) {
	wbih.printStackTrace ();
     }
  }

  /**
   * intToWord converts an int to a word, where the return
   * value is stored in a 2-byte array.
   * There are no OS/Hardware dependencies and no variances.
   * There are no security constraints.
   * There are no references to external specifications.
   *@param parValue resets the value of the elements in the byte array.
   *@return retValue returns a newly updated byte array.
   */
  public byte [] intToWord (int parValue) {
     byte retValue [] = new byte [2];
     retValue [0] = (byte) (parValue & 0x00FF);
     retValue [1] = (byte) ((parValue >>  8) & 0x00FF);
     return (retValue);
  }

  /**
   * intToDWord converts an int to a double word, where the return
   * value is stored in a 4-byte array.
   * There are no OS/Hardware dependencies and no variances.
   * There are no security constraints.
   * There are no references to external specifications.
   *@param parValue resets the value of elements in the byte array.
   *@return retValue returns a newly updated byte array.
   */
  public byte [] intToDWord (int parValue) {
     byte retValue [] = new byte [4];
     retValue [0] = (byte) (parValue & 0x00FF);
     retValue [1] = (byte) ((parValue >>  8) & 0x000000FF);
     retValue [2] = (byte) ((parValue >>  16) & 0x000000FF);
     retValue [3] = (byte) ((parValue >>  24) & 0x000000FF);
     return (retValue);
  }

  /** This method is the main function that calls all the other ones
   * There is no state transition.
   * The argument is a string variable.
   * NULL value can be assigned.
   * main function does everything.
   * There are no OS/Hardware dependencies and no variances.
   * Implementation variances is not allowed.
   * There is no exception.
   * There is no security constraints.
   * @param args This is a string variable
   */
  public static void main(String [] args){
      try{
	  System.out.println("Beginning");
	  converter convert = new converter();
	  FileInputStream fin = new FileInputStream(new File("c:\\1.bmp"));
	  FileOutputStream fout = new FileOutputStream(new File("c:\\output.bmp"));
	  //convert.BufferedImageToFile(fout, convert.FileToBufferedImage(fin),0);
	  System.out.println("Ending");
      }catch(Exception e){
	  System.out.println("File not there");
      }
  }
}
