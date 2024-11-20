
import java.io.IOException;
import java.io.OutputStream;


/**
 * Adapted from Jef Poskanzer's Java port by way of J. M. G. Elliott.
 *
 * @author Jef Poskanzer
 * @version 4.0
 */
public class LZWEncoder {

	/** Integer representing End Of File value.*/
	public static final int EOF = -1;
	/** Integer representing image Width.*/
	public int imgW;
	/** Integer representing image Height.*/
	public int imgH;

	/** Byte array representing the pixel color values.*/

	private byte[] pixAry;

	/** Integer representing initial Code Size.*/

	private int initCodeSize;

	/** Integer representing remaining pixels.*/
	public int remaining;
	/** Integer representing current pixel.*/
	public int curPixel;


	// GIFCOMPR.C - GIF Image compression routines
	//
	// Lempel-Ziv compression based on 'compress'. GIF modifications by
	// David Rowley (mgardi@watdcsu.waterloo.edu)

	// General DEFINEs
	/** DEFINE BITS AS 12. */
	static final int BITS = 12;
	/** DEFINE HSIZE AS 5003. */
	static final int HSIZE = 5003; // 80% occupancy

	// GIF Image compression - modified 'compress'
	//
	// Based on: compress.c - File compression ala IEEE Computer, June 1984.
	//

	// By Authors: Spencer W. Thomas (decvax!harpo!utah-cs!utah-gr!thomas)
	//		Jim McKie (decvax!mcvax!jim)
	//		Steve Davies (decvax!vax135!petsd!peora!srd)
	//		Ken Turkowski (decvax!decwrl!turtlevax!ken)
	//		James A. Woods (decvax!ihnp4!ames!jaw)
	//		Joe Orost (decvax!vax135!petsd!joe)
	/** number of bits/code. */
	int n_bits;
	/** user settable max # bits/code. */
	int maxbits = BITS;
	/** maximum code, given n_bits. */
	int maxcode;
	/** should NEVER generate this code.*/
	int maxmaxcode = 1 << BITS;


	
	/** Integer Array of size HSIZE. */
	int[] htab = new int[HSIZE];
	/** Integer Array of size HSIZE. */
	int[] codetab = new int[HSIZE];

	/** for dynamic table sizing. */
	int hsize = HSIZE; 
	/** first unused entry. */
	int free_ent = 0; 

	/** block compression parameters -- after all codes are used up,
	* and compression rate changes, start over.
	*/
	boolean clear_flg = false;

	// Algorithm: use open addressing double hashing (no chaining) on the
	// prefix code / next character combination. We do a variant of Knuth's
	// algorithm D (vol. 3, sec. 6.4) along with G. Knott's relatively-prime
	// secondary probe. Here, the modular division first probe is gives way
	// to a faster exclusive-or manipulation. Also do block compression with
	// an adaptive reset, whereby the code table is cleared when the compression
	// ratio decreases, but after the table fills. The variable-length output
	// codes are re-sized at this point, and a special CLEAR code is generated
	// for the decompressor. Late addition: construct the table according to
	// file size for noticeable speed improvement on small files. Please direct
	// questions about this implementation to ames!jaw.

	/** Integer. */
	int g_init_bits;
	/** Integer representing the clear code. */
	int ClearCode;
	/** Integer representing the EOF code. */
	int EOFCode;

	// output
	//
	// Output the given code.
	// Inputs:
	//	code: A n_bits-bit integer. If == -1, then EOF. This assumes
	//		that n_bits =< wordsize - 1.
	// Outputs:
	//	Outputs code to the file.
	// Assumptions:
	//	Chars are 8 bits long.
	// Algorithm:
	//	Maintain a BITS character long buffer (so that 8 codes will
	// fit in it exactly). Use the VAX insv instruction to insert each
	// code in turn. When the buffer fills up empty it and start over.

	/** Integer representing the Current Accumulation. */
	int cur_accum = 0;

	/** Integer representing the Current Bits. */
	int cur_bits = 0;


	/** An array of hex masks. */
	int masks[] =
		{
			0x0000,
			0x0001,
			0x0003,
			0x0007,
			0x000F,
			0x001F,
			0x003F,
			0x007F,
			0x00FF,
			0x01FF,
			0x03FF,
			0x07FF,
			0x0FFF,
			0x1FFF,
			0x3FFF,
			0x7FFF,
			0xFFFF };

	/** Number of characters so far in this 'packet'. */
	int a_count;

	/** Define the storage for the packet accumulator. */
	byte[] accum = new byte[256];

	/** This is the constructor for the LZWEncoder class.
	* @param width int representing width
	* @param height int representing height
	* @param pixels byte array representing the picture color codes
	* @param color_depth int representing color depth
	*/
	
	LZWEncoder(int width, int height, byte[] pixels, int color_depth) {
		imgW = width; /*FAULT:: imgW = 20; */
		imgH = height;
		pixAry = pixels;
		initCodeSize = Math.max(2, color_depth);
	}

	/** Add a character to the end of the current packet, and if it is 254
	* characters, flush the packet to disk.
	* @param c a single byte
	* @param outs output stream
	* @throws IOException
	*/
	void char_out(byte c, OutputStream outs) throws IOException {
		accum[a_count++] = c; /*FAULT:: accum[a_count++] = 5;*/
		if (a_count >= 254)
			flush_char(outs);
	}

	/** Clear out the hash table
	* table clear for block compress.
	* @param outs OutputStream object
	* @throws IOException
	*/
	void cl_block(OutputStream outs) throws IOException {
		cl_hash(hsize);
		free_ent = ClearCode + 2;
		clear_flg = true; /*FAULT:: clear_flg = false; */

		output(ClearCode, outs);
	}

	/** Reset code table.
	* @param hsize an int representing the hash table size
	*/
	void cl_hash(int hsize) {
		for (int i = 0; i < hsize; ++i)
			htab[i] = -1; /*FAULT htab[i] = -2; */
	}
	
	/** Compress code table.
	* @param outs OutputStream object
	* @param init_bits int representing the init bits
	* @throws IOException
	*/
	void compress(int init_bits, OutputStream outs) throws IOException {
		int fcode;
		int i /* = 0 */;
		int c;
		int ent;
		int disp;
		int hsize_reg;
		int hshift;

		// Set up the globals: g_init_bits - initial number of bits
		g_init_bits = init_bits;

		// Set up the necessary values
		clear_flg = false;
		n_bits = g_init_bits;
		maxcode = MAXCODE(n_bits);

		ClearCode = 1 << (init_bits - 1);
		EOFCode = ClearCode + 1;
		free_ent = ClearCode + 2;

		a_count = 0; // clear packet

		ent = nextPixel();

		hshift = 0;
		for (fcode = hsize; fcode < 65536; fcode *= 2)
			++hshift;
		hshift = 8 - hshift; // set hash code range bound

		hsize_reg = hsize;
		cl_hash(hsize_reg); // clear hash table

		output(ClearCode, outs);

		outer_loop: while ((c = nextPixel()) != EOF) {
			fcode = (c << maxbits) + ent;
			i = (c << hshift) ^ ent; // xor hashing

			if (htab[i] == fcode) {
				ent = codetab[i];
				continue;
			} else if (htab[i] >= 0) // non-empty slot
			{
				disp = hsize_reg - i; // secondary hash (after G. Knott)
				if (i == 0)
					disp = 1;
				do {
					if ((i -= disp) < 0)
						i += hsize_reg;

					if (htab[i] == fcode) {
						ent = codetab[i];
						continue outer_loop;
					}
				} while (htab[i] >= 0);
			}
			output(ent, outs);
			ent = c;
			if (free_ent < maxmaxcode) {
				codetab[i] = free_ent++; // code -> hashtable
				htab[i] = fcode;
			} else
				cl_block(outs);
		}
		// Put out the final code.
		output(ent, outs);
		output(EOFCode, outs);
	}

	/** This method does the encoding and sends to an output stream.
	* @param os OutputStream object
	* @throws IOException
	*/
	void encode(OutputStream os) throws IOException {
		os.write(initCodeSize); // write "initial code size" byte

		remaining = imgW * imgH; // reset navigation variables
		curPixel = 0; /*FAULT:: curPixel = 60; */

		compress(initCodeSize + 1, os); // compress and write the pixel data

		os.write(0); // write block terminator
	}

	/** Flush the packet to disk, and reset the accumulator.
	* @param outs OutputStream object
	* @throws IOException
	*/
	void flush_char(OutputStream outs) throws IOException {
		if (a_count > 0) {
			outs.write(a_count);
			outs.write(accum, 0, a_count);
			a_count = 0; /*FAULT:: a_count = 1; */
		}
	}
	/** This method returns the Max Code given the number of bits.
	* @param n_bits integer representing the number of bits
	* @return the maxcode as an integer
	*/
	final int MAXCODE(int n_bits) {
		return (1 << n_bits) - 1; /*FAULT:: return 5; */
	}

	//----------------------------------------------------------------------------
	/** Return the next pixel from the image.
	* @return the next pixel from the image
	*/
	public int nextPixel() {

	/** Return the next pixel from the image.
	* @return the next pixel from the image
	*/
	

		if (remaining == 0)
			return EOF;

		--remaining; /*FAULT:: remaining++; */

		byte pix = pixAry[curPixel++];

		return pix & 0xff;
	}
	/** This method produces the output.
	* @param code an integer representing the code
	* @param outs OutputStream object
	* @throws IOException
	*/
	void output(int code, OutputStream outs) throws IOException {
		cur_accum &= masks[cur_bits];

		if (cur_bits > 0)
			cur_accum |= (code << cur_bits);
		else
			cur_accum = code;

		cur_bits += n_bits; /*FAULT:: cur_bits = 0; */
		while (cur_bits >= 8) {
			char_out((byte) (cur_accum & 0xff), outs);
			cur_accum >>= 8;
			cur_bits -= 8;
		}

		// If the next entry is going to be too big for the code size,
		// then increase it, if possible.
		if (free_ent > maxcode || clear_flg) {
			if (clear_flg) {
				maxcode = MAXCODE(n_bits = g_init_bits);
				clear_flg = false;
			} else {
				++n_bits;
				if (n_bits == maxbits)
					maxcode = maxmaxcode;
				else
					maxcode = MAXCODE(n_bits);
			}
		}

		if (code == EOFCode) {
			System.out.println("plz");
			// At EOF, write the rest of the buffer.
			while (cur_bits > 0) {
				char_out((byte) (cur_accum & 0xff), outs);
				cur_accum >>= 8;
				cur_bits -= 8;
			}

			flush_char(outs);
		}
	}
}