package project1;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Dataset Object
 * 
 * @author Tesic
 * @author Tarek
 * @author Himaja
 * @author Scott
 * @author Daniel
 */
public class Dataset {

	/**
	 * 
	 * @param dataId
	 * @param nameOfFile
	 * @param numberOfRatings
	 */
	public Dataset(String dataId, Path nameOfFile, long numberOfRatings) {
		this.dataId = dataId;
		this.rawFile = nameOfFile;
		this.numberOfRatings = numberOfRatings;
		this.ratingList = new ArrayList<Rating>();
		this.ratingStat = new ArrayList<AbstractRatingSummary>();
	}

	/**
	 * 
	 * @param dataId
	 * @param inRawFile
	 * @throws InvalidDataPath
	 * @throws IOException
	 */
	public Dataset(String dataId, Path inRawFile) throws IOException {

		this.dataId = dataId;
		this.rawFile = inRawFile;
		this.numberOfRatings = Files.lines(inRawFile).count();
		this.ratingList = new ArrayList<Rating>();
		this.ratingStat = new ArrayList<AbstractRatingSummary>();
	}

	/**
	 * 
	 * @return number of ratings
	 * @throws InvalidDataPath
	 * @throws IOException
	 */
	public int loadRatings() throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(this.getRawFile().toFile()));
		String line;
		while ((line = br.readLine()) != null) {
			String[] tempArr = line.split(DataAnalysis.DELIMITER);
			Rating r = new Rating(tempArr[0],tempArr[1], Float.parseFloat(tempArr[2]));
			this.ratingList.add(r);
		}
	
		br.close();
		
		return this.ratingList.size();
	}

	/**
	 * 
	 * @param inStatPath
	 * @return
	 * @throws IOException
	 */
	public int loadStats(Path inStatPath) throws IOException {
		// load stats if file exists
		BufferedReader brs = new BufferedReader(new FileReader(inStatPath.toFile()));
		String line;
		// reading first line with the column name
		brs.readLine();

		while ((line = brs.readLine()) != null) {

			final String[] tempArr = line.split(DataAnalysis.DELIMITER);
			final int len = tempArr.length;

			if (len > 5) {
				RatingSummary rs = new RatingSummary(tempArr[0], Long.getLong(tempArr[1]));
				rs.createList(Float.valueOf(tempArr[2]), Float.valueOf(tempArr[3]), Float.valueOf(tempArr[4]),
						Float.valueOf(tempArr[5]));
				this.ratingStat.add(rs);
			}
		}
		brs.close();
		return this.ratingStat.size();
	}

	/**
	 * Create a RatingSummary object for each reviewerID 
	 * available in ratingList and add them to ratingStat
	 * 
	 * @return computed
	 */
	public boolean computeStats() {

		boolean computed = true;
	
		if (ratingList == null) computed = false;
		
		// Make a stream w/ ratingList. For each rating (row in dataset file), 
		// get its reviewerID & make a list with these values. 
		List<Rating> ListOfReviewerIDs = ratingList;
		ListOfReviewerIDs
			.stream()
			.map(rating -> rating.getReviewerID()) 
			.collect(Collectors.toList()) 		
			.forEach(reviewerID -> ratingStat.add(new RatingSummary(reviewerID, ratingList)));
		
		return computed;
	}
	/**
	 * Prints a string of stats
	 * @return statString
	 */
	public String saveStats() {

		String statString = "";
		// writing a rating summary in each line
		for (AbstractRatingSummary rs : this.getRatingStat()) {
			statString += rs.toString();
		}
		return statString;
	}
	/**
	 * Getter method for dataID
	 * @return dataID
	 */

	public String getDataId() {
		return dataId;
	}
	/**
	 * Setter method for dataID
	 * @param dataId
	 */

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	/**
	 * Getter method for rawFile
	 * @return rawFile
	 */

	public Path getRawFile() {
		return this.rawFile;
	}
	/**
	 * Getter method for numerOfRatings
	 * @return numberOfRatings
	 */

	public long getNumberOfRatings() {
		return numberOfRatings;
	}
	/**
	 * Setter method for numerOfRatings
	 * @param numberOfRatings
	 */

	public void setNumberOfRatings(long numberOfRatings) {
		this.numberOfRatings = numberOfRatings;
	}
	/**
	 * Method for statsExist
	 * @return size of ratingStat
	 */

	public int statsExist() {
		return ratingStat.size();
	}
	/**
	 * Getter method for List<Rating> ratingList
	 * @return ratingList
	 */

	public List<Rating> getRatingList() {
		return this.ratingList;
	}
	/**
	 * Getter method for ratingStat
	 * @return ratingStat
	 */

	public List<AbstractRatingSummary> getRatingStat() {
		return this.ratingStat;
	}
	/**
	 * Setter method for ratingSummary
	 * @param ratingSummary
	 */

	public void setRatingSummary(List<AbstractRatingSummary> ratingSummary) {
		this.ratingStat = ratingSummary;
	}

	@Override
	public String toString() {
		// "dataID,RAW_FILE,RATINGS_NO,STAT_FILE\n";
		return (this.getDataId() + "," + this.getRawFile().toString() + "," + this.getNumberOfRatings() + ",");
	}

	// the name of the data set file (video, music, small, test, etc.)
	private String dataId;

	// path to file (team, we won't need this)
	private Path rawFile;

	// number of rows in data set
	private long numberOfRatings;

	// column values of each row (reviewerID, productID, productRating)
	private List<Rating> ratingList; 

	// the values from RatingSummary (productAvg, productStDev, reviewerAvg, reviewerStDev)
	private List<AbstractRatingSummary> ratingStat; 
}
