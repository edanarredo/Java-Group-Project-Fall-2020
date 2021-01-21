package project1;

import java.util.Scanner;

/**
 * Provides functionality for interacting with the user, main class
 * 
 * @author Tesic
 * @author Tarek
 * @author Himaja
 * @author Scott
 * @author Daniel
 */
public class RatingStatsApp {

	// Used to read from System's standard input
	static final Scanner CONSOLE_INPUT = new Scanner(System.in);

	public static void main(final String[] args) {

		String selection = "0";

		try {

			DatasetHandler dh = new DatasetHandler();
			String setup = "Loading the datasets from:\n\n" + "\t data folder: " + dh.getFolderPath() + "\n"
					+ "\t datasets available: " + dh.getDataSets() + " \n";
			dh.printDB();
			System.out.println(setup);

			String welcomeMessage = "Choose one of the following functions:\n\n"
					+ "\t 1. Display computed statistics for specific dataID. \n"
					+ "\t 2. Add new collection and compute statistics.\n" + "\t 0. Exit program.\n";
			System.out.println(welcomeMessage);
			String newDataID = "";
			selection = CONSOLE_INPUT.nextLine().strip();

			while (!selection.contains("0")) {
				boolean found = false;
				int dbSize = dh.getDataSets();
				if (selection.contains("1")) {
					if (dbSize < 1) {
						System.out.println("There is no data to select from, select another option\n");

					} else {
						System.out.println("Please enter dataID from the list: \n");
						newDataID = CONSOLE_INPUT.nextLine().strip();
						if (!(dh.checkID(newDataID))) {
							System.out.print("dataID not in the current database, select another option \n");
						} else {
							found = true;
						}
					}
					// end 1
				} else if (selection.contains("2")) {

					System.out.println("Please enter new unique dataID: \n");
					newDataID = CONSOLE_INPUT.nextLine().strip();
					if (!(dh.checkID(newDataID))) {
						System.out.println("For new " + newDataID + " collection, what is the source file name?\n");
						final String input = CONSOLE_INPUT.nextLine().strip();
						boolean check = dh.addCollection(newDataID, input);
						System.out.println("Collection " + newDataID + " added\n");
					} else {
						System.out.println(newDataID + " is in the current database, displaying existing statistics.\n");
					}
					found = true;
					// end 2
				} else if (selection.contains("h")) {
					System.out.println(welcomeMessage);
				} // end selection

				if (found) {
					final String processStats = newDataID + ": statistics are already computed and saved \n"
							+ "Choose one of the following functions:\n\n" + "\t 3. Use existing stat data.\n"
							+ "\t 4. Process statistics again, I have new data.\n";

					// load ratings and statistics
					Dataset d = dh.setCollection(newDataID);
					String rc = "3";
					int stats = d.statsExist();
					if (stats > 0) {
						System.out.println(processStats);
						rc = CONSOLE_INPUT.nextLine().strip();
					}

					if (rc.contains("4") || (stats < 1)) {
						d.computeStats();
					}

					// this blocks processes computed statistics in dh
					int k = 3;
					// prints report to file and console
					dh.printReport(newDataID, k);
					// if stats were computed again, save them.
					dh.saveStats(newDataID);
				} // end if found

				System.out.println("Please enter 0 to exit or 'h' to start again.\n");
				selection = CONSOLE_INPUT.nextLine().strip();
			} // end while

			dh.saveDB();
		} catch (Exception e) {
			System.out.println("Dataset path not found, exiting. ");
		}
		System.out.println("Goodbye!");
	}// end mail
}// end class
