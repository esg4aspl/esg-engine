package tr.edu.iyte.esg.coverageanalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class CoverageAnalysisReportGenerator {
	
	public static void compareCESCoverageAnalysisFiles(String coverageAnalysisPath, String TSDCoverageAnalysisPath,String reportFilePath) {
		CESReport engineReport = readCESCoverageAnalysis(coverageAnalysisPath);
		CESReport TSDReport = readCESCoverageAnalysis(TSDCoverageAnalysisPath);
		
		File reportFile = new File(reportFilePath);
		Writer fileWriter = null;
		try {
			fileWriter = new FileWriter(reportFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter printWriter = new PrintWriter(fileWriter);
		if(engineReport.getNumberOfCESs() == TSDReport.getNumberOfCESs()) {
			printWriter.println("The number of CESs is the same: " + engineReport.getNumberOfCESs());
		}else {
			printWriter.println("The number of CESs in FESG Engine: " + engineReport.getNumberOfCESs());
			printWriter.println("The number of CESs in TSD: " + TSDReport.getNumberOfCESs());
		}
		if(engineReport.getNumberOfEvents() == TSDReport.getNumberOfEvents()) {
			printWriter.println("The number of events is the same: " + engineReport.getNumberOfEvents());
		}else {
			printWriter.println("The number of events in FESG Engine: " + engineReport.getNumberOfEvents());
			printWriter.println("The number of events in TSD: " + TSDReport.getNumberOfEvents());
		}
		if(engineReport.getCoveragePercentage() == TSDReport.getCoveragePercentage()) {
			if(engineReport.getCoveragePercentage() == 100.0) {
				printWriter.println("The coverage is the same: " + engineReport.getCoveragePercentage());
			}else {
				if(engineReport.getNumberOfUncoveredEdges() == TSDReport.getNumberOfUncoveredEdges()) {
					printWriter.println("The number of uncovered edges is the same: " + engineReport.getNumberOfUncoveredEdges());
				}else {
					printWriter.println("The number of uncovered edges in FESG Engine: " + engineReport.getNumberOfUncoveredEdges());
					printWriter.println("The number of uncovered edges in TSD: " + TSDReport.getNumberOfUncoveredEdges());
				}
			}
		}else {
			printWriter.println("The coverage in FESG Engine: " + engineReport.getCoveragePercentage());
			printWriter.println("The number of uncovered edges in FESG Engine: " + engineReport.getNumberOfUncoveredEdges());
			printWriter.println("The coverage in TSD: " + TSDReport.getCoveragePercentage());
			printWriter.println("The number of uncovered edges in TSD: " + TSDReport.getNumberOfUncoveredEdges());
		}
		
		printWriter.close();
	}
	
	public static void compareFCESCoverageAnalysisFiles(String coverageAnalysisPath, String TSDCoverageAnalysisPath,String reportFilePath) {
		FCESReport engineReport = readFCESCoverageAnalysis(coverageAnalysisPath);
		FCESReport TSDReport = readFCESCoverageAnalysis(TSDCoverageAnalysisPath);
		
		File reportFile = new File(reportFilePath);
		Writer fileWriter = null;
		try {
			fileWriter = 
					new FileWriter(reportFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter printWriter = new PrintWriter(fileWriter);
		if(engineReport.getNumberOfFCESs() == TSDReport.getNumberOfFCESs()) {
			printWriter.println("The number of FCESs is the same: " + engineReport.getNumberOfFCESs());
		}else {
			printWriter.println("The number of FCESs in FESG Engine: " + engineReport.getNumberOfFCESs());
			printWriter.println("The number of FCESs in TSD: " + TSDReport.getNumberOfFCESs());
		}
		if(engineReport.getNumberOfEvents() == TSDReport.getNumberOfEvents()) {
			printWriter.println("The number of events is the same: " + engineReport.getNumberOfEvents());
		}else {
			printWriter.println("The number of events in FESG Engine: " + engineReport.getNumberOfEvents());
			printWriter.println("The number of events in TSD: " + TSDReport.getNumberOfEvents());
		}
		printWriter.close();
		
	}
	
	public static CESReport readCESCoverageAnalysis(String filePath) {
		
		File file = new File(filePath);
		CESReport CESReport = new CESReport();

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			try {
				while ((line = br.readLine()) != null) {
					if(line.startsWith("Number of CESs: ")) {
						String s1 = "Number of CESs: ";
						int l1 = s1.length();
						String ss = line.substring(l1, line.length());
						int numberOfCESs = Integer.parseInt(ss);
						CESReport.setNumberOfCESs(numberOfCESs);
					}
					if(line.startsWith("Number of events: ")) {
						String s1 = "Number of events: ";
						int l1 = s1.length();
						String ss = line.substring(l1, line.length());
						int numberOfEvents = Integer.parseInt(ss);
						CESReport.setNumberOfEvents(numberOfEvents);
					}
					if(line.endsWith("Number of uncovered edges")) {
						String space = " ";
						int indexOfFirstSpace = line.indexOf(space);
						String ss = line.substring(0, indexOfFirstSpace);
						int numberOfUncoveredEdges = Integer.parseInt(ss);
						CESReport.setNumberOfUncoveredEdges(numberOfUncoveredEdges);
					}
					if(line.startsWith("Coverage: ")) {
						String s1 = "Coverage: ";
						int l1 = s1.length();
						String ss = line.substring(l1, line.length());
						double coverage = Double.parseDouble(ss);
						CESReport.setCoveragePercentage(coverage);
					}
					
				}
			} catch (IOException e) {

				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		return CESReport;
	}
	
	public static FCESReport readFCESCoverageAnalysis(String filePath) {
		
		File file = new File(filePath);
		FCESReport FCESReport = new FCESReport();

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			try {
				while ((line = br.readLine()) != null) {
					if(line.startsWith("Number of FCESs: ")) {
						String s1 = "Number of FCESs: ";
						int l1 = s1.length();
						String ss = line.substring(l1, line.length());
						int numberOfFCESs = Integer.parseInt(ss);
						FCESReport.setNumberOfFCESs(numberOfFCESs);
					}
					if(line.startsWith("Number of events: ")) {
						String s1 = "Number of events: ";
						int l1 = s1.length();
						String ss = line.substring(l1, line.length());
						int numberOfEvents = Integer.parseInt(ss);
						FCESReport.setNumberOfEvents(numberOfEvents);
					}

					
				}
			} catch (IOException e) {

				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e1) {

			e1.printStackTrace();
		}

		return FCESReport;
	}

}
