package project1;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;


/**
 * Ratings Summary supporting inner and outer statistics of the review
 *  
 * @author Tesic
 * @author Tarek
 * @author Himaja
 * @author Scott
 * @author Daniel
 */

public class RatingSummary extends AbstractRatingSummary {

	/**
	 * Constructor.
	 * 
	 * @param inNodeID
	 * @param inDegree
	 * @param inList
	 */
	public RatingSummary(final String inNodeID, final long inDegree, final List inList) {
		super(inNodeID, inDegree, inList);
	}

	/**
	 * Constructor.
	 * 
	 * @param inNodeID
	 * @param inDegree
	 */
	public RatingSummary(final String inNodeID, final long inDegree) {
		super(inNodeID, inDegree);
	}

	/**
	 * Constructor.
	 * 
	 * @param id            product/review id
	 * @param degree        number of times reviewed
	 * @param productAvg    average rating of the product
	 * @param productStDev  standard deviation of the product's rating
	 * @param reviewerAvg   average rating of the reviewer
	 * @param reviewerStDev standard deviation of the reviewer's ratings
	 */
	public RatingSummary(final String id, final long degree, final float productAvg, final float productStDev,
			final float reviewerAvg, final float reviewerStDev) {
		
		super(id, degree); 
		super.setList(this.createList(productAvg, productStDev, reviewerAvg, reviewerStDev));
	}

	/**
	 * Constructor.
	 * 
	 * @param id
	 * @param degree
	 * @param rawRatings
	 */
	public RatingSummary(final String id, final List<Rating> rawRatings) {
		super(id);
		super.setDegree(rawRatings);
	}

	/**
	 * Create a newList using createList() and pass that into super.setList()
	 */
	public void setList() {
		super.setList(createList());
	}

	/**
	 * Create a new list using createList() and parameters and passing that to
	 * super.setList()
	 * 
	 * @param productAvg
	 * @param productStDev
	 * @param reviewerAvg
	 * @param reviewerStDev
	 */
	public void setList(float productAvg, float productStDev, float reviewerAvg, float reviewerStDev) {
		super.setList(createList(productAvg, productStDev, reviewerAvg, reviewerStDev));
	}

	@Override
	public List<Float> createList() {
		return new ArrayList<Float>();
	}

	/**
	 * Create a list containing each of the parameters as values.
	 * 
	 * @param productAvg
	 * @param productStDev
	 * @param reviewerAvg
	 * @param reviewerStDev
	 * @return newList
	 */
	public List<Float> createList(float productAvg, float productStDev, float reviewerAvg, float reviewerStDev) {
		List<Float> newList = createList();		
		newList.add(productAvg);
		newList.add(productStDev);
		newList.add(reviewerAvg);
		newList.add(reviewerStDev);

		return newList;
	}

	/**
	 * Prints RatingSummary object as form Id,degree,product avg,product
	 * st.dev,reviewer avg,reviewer st.dev\n
	 * @return String with NodeID, degree, product avg, product st.dev, reviewer avg, reviewer st.dev
	 */
	@Override
	public String toString() {
		
		return getNodeID() + ", " + getDegree() + ", " + printStats();

	}
	/**
	 * Prints computed statistics
	 * @return String stat
	 */

	public String printStats() {
		
		String stat = "";
		if (getList().isEmpty()) {
			stat = "product avg,product st.dev,reviewer avg,reviewer st.dev\n";
		}
		for (final Float s : getList()) {
			stat = s + "\n";

		}
		System.out.println(stat);
		return stat;
	}

	/**
	 * collect the list that keeps statistics 
	 * Make sure the object was initialized
	 * 
	 * @param rawRatings
	 */
	@Override
	public void collectStats(final List<Rating> rawRatings) {
		if (rawRatings != null) {
			collectProductStats(rawRatings);
			collectReviewerStats(rawRatings);
		}
	}

	/**
	 * Collects product stats for nodeID -- never call this function directly, only
	 * through collectStats
	 * 
	 * @param rawRatings
	 */
	public void collectProductStats(final List<Rating> rawRatings) {
		
		//check for rows containing product ID and filtering out corresponding rating
		for (final Rating pStat : rawRatings) {
			if (super.getNodeID().equals(pStat.getProductID())) {
				rawRatings.get(2);
			}
		}

	}

	/**
	 * Collects reviewer stats for nodeID -- never call this function directly, only
	 * through collectStats
	 * 
	 * @param rawRatings
	 */
	public void collectReviewerStats(final List<Rating> rawRatings) {
		
		//check for rows containing reviewer ID and filtering out corresponding rating
		for (final Rating rStat : rawRatings) {
			if (super.getNodeID().equals(rStat.getReviewerID())) {
				rawRatings.get(2);
			}
		}

	}

	////////// Statistics block

	/**
	 * @return sort by biggest difference between product and review average in
	 *         collection
	 */

	public Float avgScore(){
		
		List<Float> statsList = super.getList();

		Float reviewerAvg = statsList.get(2);
		Float productAvg = statsList.get(0);

		// Computation
		return (reviewerAvg - productAvg);

	}
	
	/**
	 * @return sort by biggest difference between product and review st.dev. in
	 *         collection
	 */
	public Float stDevScore() {

		List<Float> statsList = super.getList();

		//Making variables for readability
		Float reviewerStDev = statsList.get(3);
		Float productStDev = statsList.get(1);

		// Computation
		return reviewerStDev - productStDev;

	}

	/**
	 * @return summary of statistics as key to sorting the rating summaries
	 */
	public Float sortStats() {
		
		Collections.sort(createList());
		Float maxScore = avgScore() + stDevScore();
		return maxScore;
		
	}
}
