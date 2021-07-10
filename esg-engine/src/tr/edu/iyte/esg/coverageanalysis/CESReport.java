package tr.edu.iyte.esg.coverageanalysis;

public class CESReport {
	private int numberOfCESs;
	private int numberOfEvents;
	private double coveragePercentage;
	private int numberOfUncoveredEdges;
	
	public CESReport() {
		
	}
	
	public CESReport(int numberOfCESs, int numberOfEvents, double coveragePercentage, int numberOfUncoveredEdges) {
		setNumberOfCESs(numberOfCESs);
		setNumberOfEvents(numberOfEvents);
		setNumberOfUncoveredEdges(numberOfUncoveredEdges);
		setCoveragePercentage(coveragePercentage);
	}
	
	public int getNumberOfCESs() {
		return numberOfCESs;
	}
	public void setNumberOfCESs(int numberOfCESs) {
		this.numberOfCESs = numberOfCESs;
	}
	public int getNumberOfEvents() {
		return numberOfEvents;
	}
	public void setNumberOfEvents(int numberOfEvents) {
		this.numberOfEvents = numberOfEvents;
	}
	public double getCoveragePercentage() {
		return coveragePercentage;
	}
	public void setCoveragePercentage(double coveragePercentage) {
		this.coveragePercentage = coveragePercentage;
	}
	public int getNumberOfUncoveredEdges() {
		return numberOfUncoveredEdges;
	}
	public void setNumberOfUncoveredEdges(int numberOfUncoveredEdges) {
		this.numberOfUncoveredEdges = numberOfUncoveredEdges;
	}
	
}
