package definition;

public abstract class Statistic {

	private String titlebar = "======================";
	private String subtitlebar = "----------------------";
	
	protected String getTitle(String text){
		return "\n"+titlebar+text+titlebar+"\n";
	}
	
	protected String getSubTitle(String text){
		return "\n"+subtitlebar+text+subtitlebar+"\n";
	}
	
	public abstract String getStatistic();
	
	
}
