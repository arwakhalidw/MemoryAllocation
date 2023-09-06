
import java.io.*;
import java.util.*;

public class mainmenu {

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		fixedMemory[] fixedArray = null;
		String chart = "[ ";
		int M, size;
		String approach;

		System.out.println("enter the number of partitions of memory: ");
		M = in.nextInt();
		fixedArray = new fixedMemory[M];

		for (int i = 0; i < M; i++) {
			fixedArray[i] = new fixedMemory();
			System.out.println("enter the size of partition " + (i + 1) + "  in KB: ");
			size = in.nextInt();
			fixedArray[i].setPsize(size);
			if (i == 0) {
				fixedArray[i].setSaddress(0);
				fixedArray[i].setEaddress(fixedArray[i].getPsize() - 1);
			} else {
				fixedArray[i].setSaddress(fixedArray[i - 1].getEaddress() + 1);
				fixedArray[i].setEaddress(fixedArray[i].getSaddress() + fixedArray[i].getPsize() - 1);
			}
		}

		System.out.print("enter the desired approach (first fit (F), best fit (B), worst fit (W): ");
		approach = in.next(); // ask what approach when assigning processes later

		int choice;
		while (true) {
			int count = 0;
			System.out.println(
					"Choose a number:\n1# allocate a block of memory. \n2# de-allocate a block of memory. \n3# Report detailed information about regions of free and allocated memory blocks. \n4# Exit the program.");
			choice = in.nextInt();

			for (int i = 0; i < fixedArray.length; i++) {
				if (fixedArray[i].getMstatus().equalsIgnoreCase("free"))
					count++;
			}
			switch (choice) {
			case 1:// check which approach and if there is enough memory
				if (count > 0) { // to check if memory full or not
					System.out.println("enter process ID and the size of the process");
					String id = in.next();
					int S = in.nextInt();
					if (approach.equalsIgnoreCase("F"))
						firstFit(id, S, fixedArray, chart);
					else if (approach.equalsIgnoreCase("B"))
						bestFit(id, S, fixedArray, chart);
					else if (approach.equalsIgnoreCase("W"))
						worstFit(id, S, fixedArray, chart);
				} // if count
				if (count == 0)
					System.out.println("oops the memory is full try to deallocate!\n");
				break;
			case 2: // deallocate
				System.out.println("enter process ID to deallocate");
				String id = in.next();
				deallocateProcess(id, fixedArray, chart);
				break;

			case 3: // report
				writereport(fixedArray);
				break;

			case 4:
				System.out.println("program terminated.");
				System.exit(0);

			}// switch
		} // while
	}// main

	// blocks as First fit algorithm
	static void firstFit(String id, int size, fixedMemory array[], String chart) {
		for (int i = 0; i < array.length; i++)
			if (array[i].getMstatus().equalsIgnoreCase("free"))
				if (array[i].getPsize() >= size) {
					array[i].setPvalue(id);
					array[i].setMstatus("allocated");
					array[i].setFsize(array[i].getPsize() - size);

					for (int j = 0; j < array.length; j++) {
						if (array[j].getMstatus().equalsIgnoreCase("free"))
							chart = chart + " H " + " | ";
						else
							chart = chart + array[j].getPvalue() + " | ";
						if (j + 1 == array.length)
							chart = chart + "]";
					}
					System.out.println(chart);
					return;
				}
		System.out.println("insuffisent memory \n");
	}// first fit

	// blocks as best fit algorithm
	static void bestFit(String id, int size, fixedMemory array[], String chart) {
		int small = -1;
		for (int i = 0; i < array.length; i++) {
			if (array[i].getMstatus().equalsIgnoreCase("free") && array[i].getPsize() >= size) {
				if (small == -1)
					small = i;
				else if (array[small].getPsize() > array[i].getPsize())
					small = i;
			}
		}
		if (small != -1) {
			array[small].setPvalue(id);
			array[small].setMstatus("allocated");
			array[small].setFsize(array[small].getPsize() - size);

			for (int j = 0; j < array.length; j++) {
				if (array[j].getMstatus().equalsIgnoreCase("free"))
					chart = chart + " H " + " | ";
				else
					chart = chart + array[j].getPvalue() + " | ";
				if (j + 1 == array.length)
					chart = chart + "]";
			}
			System.out.println(chart);
			return;
		}

		System.out.println("insuffisent memory \n");

	}// best fit

	// blocks as worst fit algorithm
	static void worstFit(String id, int size, fixedMemory array[], String chart) {
		int large = -1;
		for (int i = 0; i < array.length; i++) {
			if (array[i].getMstatus().equalsIgnoreCase("free") && array[i].getPsize() >= size) {
				if (large == -1)
					large = i;
				else if (array[large].getPsize() < array[i].getPsize())
					large = i;
			}
		}
		if (large != -1) {
			array[large].setPvalue(id);
			array[large].setMstatus("allocated");
			array[large].setFsize(array[large].getPsize() - size);

			for (int j = 0; j < array.length; j++) {
				if (array[j].getMstatus().equalsIgnoreCase("free"))
					chart = chart + " H " + " | ";
				else
					chart = chart + array[j].getPvalue() + " | ";
				if (j + 1 == array.length)
					chart = chart + "]";
			}
			System.out.println(chart);
			return;
		}

		System.out.println("insuffisent memory \n");

	}// worst fit

	// deallocation method start here:
	static void deallocateProcess(String process_deallocateID, fixedMemory array[],  String chart) {
		boolean isThere = false;
		for (int i = 0; i < array.length; i++) {
			if (!(array[i].getPvalue().equalsIgnoreCase("null"))
					&& array[i].getPvalue().equalsIgnoreCase(process_deallocateID)) {
				array[i].setPvalue("null");
				array[i].setMstatus("free");
				array[i].setFsize(-1);
				isThere = true; // to let the user know that is found
			}
		} // end for loop
		if (!isThere) {
			System.out.println("process : " + process_deallocateID + " isn't in the memory");
		}
		for (int j = 0; j < array.length; j++) {
				if (array[j].getMstatus().equalsIgnoreCase("free"))
					chart = chart + " H " + " | ";
				else
					chart = chart + array[j].getPvalue() + " | ";
				if (j + 1 == array.length)
					chart = chart + "]";
			}
			System.out.println(chart);
	}// end deallocated method

	// report method
	static void writereport(fixedMemory[] fixedArray) {
		try {
			FileWriter writer = new FileWriter("Report.txt");
			writer.write("report information: \n\n");

			for (int i = 0; i < fixedArray.length; i++) {
				writer.write("PID: " + fixedArray[i].getPvalue() + " \n");
				System.out.print("PID: " + fixedArray[i].getPvalue() + " \n");
				writer.write("status: " + fixedArray[i].getMstatus() + "\n");
				System.out.print("status: " + fixedArray[i].getMstatus() + "\n");
				writer.write("size: " + fixedArray[i].getPsize() + "KB\n");
				System.out.print("size: " + fixedArray[i].getPsize() + "KB\n");
				writer.write("Start address: " + fixedArray[i].getSaddress() + "\n");
				System.out.print("Start address: " + fixedArray[i].getSaddress() + "\n");
				writer.write("end address: " + fixedArray[i].getEaddress() + "\n");
				System.out.print("end address: " + fixedArray[i].getEaddress() + "\n");
				writer.write("fragment size: " + fixedArray[i].getFsize()+ "KB\n" + " \n\n");
				System.out.print("fragment size: " + fixedArray[i].getFsize()+ "KB\n" + " \n\n");
			}
			writer.flush();
			

		} catch (Exception e) {
			System.out.println("An error occurred while writing to theآ file.");
		}
	}// end report method

}// class






