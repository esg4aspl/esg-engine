package tr.edu.iyte.esg.applications;

import tr.edu.iyte.esg.conversion.dot.DOTFileToESGConverter;
import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.testgeneration.TestSuite;
import tr.edu.iyte.esg.testgeneration.TestSuiteGenerator;
import tr.edu.iyte.esg.coverageanalysis.TestSequenceCoverageAnalyser;
import tr.edu.iyte.esg.coverageanalysis.TestSequenceFileUtilities;

public class SystematicTestingFileOperationsApp {

	public static void main(String[] args) {
		String mainFolder = "files/SystematicTesting/";

		int coverageLength = 1;
		String testSuiteType = "CES";

		TestSuiteGenerator testSuiteGenerator = new TestSuiteGenerator();
		for (int numberOfVertices = 6; numberOfVertices <= 6; numberOfVertices++) {
			String variantFolder = mainFolder + "VariantESG_" + numberOfVertices + "Vertex/";
			String dotFileFolder = "DOTFiles/";
			String subFolder = testSuiteType + "s/";
			String testSeqFolder = variantFolder + "TestSequences/" + subFolder;
			String coverageAnalysisFolder = variantFolder + "CoverageAnalysis/" + subFolder;
			String TSDFolderPath = variantFolder + "TSD/";
			String TSDTestSeqFolder = TSDFolderPath + "TestSequences/" + subFolder;
			String TSDCoverageAnalysisFolder = TSDFolderPath + "CoverageAnalysis/" + subFolder;
			String name = "VariantESG";
			for (int variantID = 1; variantID <= 1; variantID++) {
				String fileName = name + variantID;
				String dotFilePath = variantFolder + dotFileFolder + fileName + ".dot";
				// System.out.println(dotFilePath);
				String testSeqfilePath = testSeqFolder + testSuiteType + "_" + fileName + "_length" + coverageLength
						+ ".txt";
				System.out.println(testSeqfilePath);
				String coverageAnalysisFilePath = coverageAnalysisFolder + testSuiteType + "_Coverage_" + fileName
						+ "_length" + coverageLength + ".txt";
				// System.out.println(coverageAnalysisFilePath);
				String mxeFilePath = TSDFolderPath + "MXEFiles/" + fileName + ".mxe";
				// System.out.println(mxeFilePath);
				String TSDTestSeqFilePath = TSDTestSeqFolder + testSuiteType + "_" + fileName + "_length"
						+ coverageLength + ".txt";
				// System.out.println(TSDTestSeqFilePath);
				String TSDcoverageAnalysisFilePath = TSDCoverageAnalysisFolder + testSuiteType + "_Coverage_" + fileName
						+ "_length" + coverageLength + ".txt";

				// System.out.println(TSDcoverageAnalysisFilePath);

				ESG ESG = DOTFileToESGConverter.parseDOTFileForESGCreation(dotFilePath);
				// System.out.println(ESG.toString());

				TestSuite positiveTestSuite = testSuiteGenerator.generateTestSuite(coverageLength, ESG);
				System.out.println(positiveTestSuite.toString());

				/*
				 * TestSuite negativeTestSuite =
				 * testSuiteGenerator.generateNegativeTestSuite(coverageLength, ESG); try {
				 * EventSequenceUtilties.esgEventSequenceSetFileWriter(negativeTestSuite.
				 * getCompleteEventSequences(), testSeqfilePath); } catch (IOException e) { //
				 * TODO Auto-generated catch block e.printStackTrace();
				 */
				TestSequenceCoverageAnalyser.analyseCoverage(coverageLength, ESG,
						positiveTestSuite.getCompleteEventSequences(), coverageAnalysisFilePath);
				TestSequenceCoverageAnalyser.analyseCoverageFromFile(coverageLength, mxeFilePath, TSDTestSeqFilePath,
						TSDcoverageAnalysisFilePath);
				TestSequenceFileUtilities.readTestSequencesWriteAnalysis(TSDTestSeqFilePath,
						TSDcoverageAnalysisFilePath);
			}
		}
	}
}
