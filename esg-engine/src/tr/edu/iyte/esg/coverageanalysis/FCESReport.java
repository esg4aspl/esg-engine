package tr.edu.iyte.esg.coverageanalysis;

public class FCESReport {
	
	private int numberOfFCESs;
	private int numberOfEvents;
	
	public FCESReport() {
		
	}
	
	public FCESReport(int numberOfCESs, int numberOfEvents) {
		setNumberOfFCESs(numberOfCESs);
		setNumberOfEvents(numberOfEvents);

	}
	
	public int getNumberOfEvents() {
		return numberOfEvents;
	}
	public void setNumberOfEvents(int numberOfEvents) {
		this.numberOfEvents = numberOfEvents;
	}
	public int getNumberOfFCESs() {
		return numberOfFCESs;
	}
	public void setNumberOfFCESs(int numberOfFCESs) {
		this.numberOfFCESs = numberOfFCESs;
	}

}
