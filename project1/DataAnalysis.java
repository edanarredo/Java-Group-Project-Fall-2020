package project1;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Inventory of the datasets in DATA_FILE_FOLDER, kept in DATA_FILE_NAME
 * 
 * @author Tesic
 * @author Tarek
 * @author Himaja
 * @author Scott
 * @author Daniel
 */
public class DataAnalysis {

	/**
	 * 
	 * @return reference to list sorted by degree
	 */
	public static List<AbstractRatingSummary> sortByDegree(List<AbstractRatingSummary> inList) {

		// sorting reviewer by ratings of products differs from respective average
		// product ratings to most
		Collections.sort(inList, new Comparator<AbstractRatingSummary>() {
			public int compare(AbstractRatingSummary r1, AbstractRatingSummary r2) {
				return Long.compare(r1.getDegree(), r2.getDegree());
			}
		});
		return inList;
	}

	/**
	 * 
	 * @return reference to list sorted by product avg and review avg differ the
	 *         most
	 */
	public static List sortByAvgDiff(List<AbstractRatingSummary> inList) {
		// implement method!!!
		Collections.sort(inList, new Comparator<AbstractRatingSummary>() {
			public int compare(AbstractRatingSummary r1, AbstractRatingSummary r2) {
				return Float.compare(r1.avgScore(), r2.avgScore());
			}
		});
		return inList;
	}

	/**
	 * 
	 * @return reference to list sorted by product and review variance differ the
	 *         most
	 */
	public static List sortByStDevDiff(List<AbstractRatingSummary> inList) {
		
		Collections.sort(inList, new Comparator<AbstractRatingSummary>() {
			public int compare(AbstractRatingSummary r1, AbstractRatingSummary r2) {
				return Float.compare(r1.stDevScore(), r2.stDevScore());
			}
		});
		return inList;
	}

	/**
	 * 
	 * @param k top K to be saved in the report
	 * @return string object with full report
	 */
	public static String printReport(List<AbstractRatingSummary> inList, int k) {

		// filtering the reviewers
		List<AbstractRatingSummary> reviewers = inList.stream().filter(rs -> rs.getNodeID().startsWith("A"))
				.collect(Collectors.toList());

		int counter = reviewers.size();

		String separator = "--------------------------------------------------\n";
		String reportPrint = separator + "Highest " + Math.min(counter, k) + " number of reviews per reviewer\n"
				+ separator + DataAnalysis.SUMMARY_HEADER;

		sortByDegree(reviewers);
		for (AbstractRatingSummary rr : reviewers.subList((counter > k) ? counter - k : 0, counter)) {
			reportPrint += rr.toString();
		}

		reportPrint += separator + "Highest " + Math.min(counter, k)
				+ " rating discrepancies per reviewer (wrt other reviewers)\n" + separator + DataAnalysis.SUMMARY_HEADER;
		sortByAvgDiff(reviewers);
		for (AbstractRatingSummary rr : reviewers.subList((counter > k) ? counter - k : 0, counter)) {
			reportPrint += rr.toString();
		}

		reportPrint += separator + "Highest " + Math.min(counter, k)
				+ " rating variation per reviewer (wrt other reviewers)\n" + separator + DataAnalysis.SUMMARY_HEADER;
		sortByStDevDiff(reviewers);
		for (AbstractRatingSummary rr : reviewers.subList((counter > k) ? counter - k : 0, counter)) {
			reportPrint += rr.toString();
		}

		// filtering the products
		List<AbstractRatingSummary> products = inList.stream().filter(rs -> rs.getNodeID().startsWith("B"))
				.collect(Collectors.toList());

		counter = products.size();

		reportPrint += separator + "Highest " + Math.min(counter, k) + " number of reviews per product\n" + separator
				+ DataAnalysis.SUMMARY_HEADER;
		sortByDegree(products);
		for (AbstractRatingSummary rr : products.subList((counter > k) ? counter - k : 0, counter)) {
			reportPrint += rr.toString();
		}

		reportPrint += separator + "Highest " + Math.min(counter, k)
				+ " rating discrepancies per product (wrt other products)\n" + separator + DataAnalysis.SUMMARY_HEADER;
		sortByAvgDiff(products);
		for (AbstractRatingSummary rr : products.subList((counter > k) ? counter - k : 0, counter)) {
			reportPrint += rr.toString();
		}

		reportPrint += separator + "Highest " + Math.min(counter, k)
				+ " rating variation per reviewer (wrt other reviewers)\n" + separator + DataAnalysis.SUMMARY_HEADER;
		sortByStDevDiff(products);
		for (AbstractRatingSummary rr : products.subList((counter > k) ? counter - k : 0, counter)) {
			reportPrint += rr.toString();
		}

		return reportPrint;
	}

	/**
	 * The file name of where the database is going to be saved.
	 */
	public static final String DELIMITER = ",";
	public static final String DB_FOLDER = "data";
	public static final String DB_FILENAME = "data.csv";
	public static final String STAT_FILE_TEMPLATE = "ratingSummary_<dataID>.csv";
	public static final String REPORT_FILE_TEMPLATE = "report_<dataID>.csv";
	public static final String RESULTS_FILE_TEMPLATE = "results_<dataID>.csv";
	public static final String SUMMARY_HEADER = "Id,degree,product avg,product st.dev,reviewer avg,reviewer st.dev\n";
}