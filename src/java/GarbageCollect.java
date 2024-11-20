

import java.util.*;
import java.io.*;

/**
 * This class does a save in certain iteration automatically. The state should
 * be hidden. The OS is windows. There is no implementation variances. There is
 * no security constaints. There is no external secification.
 */

public class GarbageCollect extends Thread {

	public void run() {

		System.out.println("Garbage truck is here");

		while (true) {

			try {
				System.out.println("Garbage Man was here");

				Thread.sleep(1000 * 30); 
				System.gc();
				

			} catch (Exception e) {
			}

		}

	}
}

