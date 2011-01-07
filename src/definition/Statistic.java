package definition;

/**
 * Abstract class which provides the minimal methods
 * for a Statistic
 * 
 * @author  BFH-Boys
 */
public abstract class Statistic {

	private String titlebar = "======================";
	private String subtitlebar = "----------------------";
	
	/**
	 * Returns a formated title
	 * @param text Text for the title
	 * @return Title Returns a formated title
	 */
	protected String getTitle(String text){
		return "\n"+titlebar+text+titlebar+"\n";
	}
	
	/**
	 * Returns a formated title
	 * @param text Text for the subtitle
	 * @return Title Returns a formated title
	 */
	protected String getSubTitle(String text){
		return "\n"+subtitlebar+text+subtitlebar+"\n";
	}
	
	/**
	 *  
	 * @return Returns a summary of the statistics
	 */
	public abstract String getStatistic();
	
	
}
