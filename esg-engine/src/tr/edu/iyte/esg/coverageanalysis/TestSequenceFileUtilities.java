package tr.edu.iyte.esg.coverageanalysis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

public class TestSequenceFileUtilities {
	
	public static void readTestSequencesWriteAnalysis(String testSeqFilePath, String coverageAnalysisFilePath) {
		//System.out.println(filePath);
		List<String> testSequencesList = TestSequenceCoverageAnalyser.testCasesFromFile(testSeqFilePath);
		int numberOfSequences = testSequencesList.size();
		int numberOfEvents = TestSequenceCoverageAnalyser.numberOfEvents(testSequencesList);
		//System.out.println(numberOfSequences);
		//System.out.println(numberOfEvents);
		
		File file = new File(coverageAnalysisFilePath);
		Writer writer = null;
		try {
			writer = new FileWriter(file, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter printWriter = new PrintWriter(writer);
		printWriter.println("Number of FCESs: " + numberOfSequences);
		printWriter.println("Number of events: " + numberOfEvents);
		printWriter.close();
		
	}

}
