package tr.edu.iyte.esg.applications;

import tr.edu.iyte.esg.coverageanalysis.CoverageAnalysisReportGenerator;

public class SystematicTestingReportGenerationApp {

	public static void main(String[] args) {
		String mainFolder = "files/SystematicTesting/";
		String testSuiteType = "CES";
		String subFolder = testSuiteType + "s/";
		String name = "VariantESG";

		for (int numberOfVertices = 6; numberOfVertices <= 6; numberOfVertices++) {
			String variantFolder = mainFolder + "VariantESG_" + numberOfVertices + "Vertex/";
			String coverageAnalysisFolder = variantFolder + "CoverageAnalysis/" + subFolder;
			String TSDFolderPath = variantFolder + "TSD/";
			String TSDCoverageAnalysisFolder = TSDFolderPath + "CoverageAnalysis/" + subFolder;
			String reportFolderName = variantFolder + "ReportFiles/";

			for (int variantID = 1; variantID <= 1; variantID++) {
				String fileName = name + variantID;

				for (int coverageLength = 2; coverageLength <= 3; coverageLength++) {
					String coverageAnalysisFilePath = coverageAnalysisFolder + testSuiteType + "_Coverage_" + fileName
							+ "_length" + coverageLength + ".txt";
					String TSDCoverageAnalysisFilePath = TSDCoverageAnalysisFolder + testSuiteType + "_Coverage_"
							+ fileName + "_length" + coverageLength + ".txt";
					String reportFilePath = reportFolderName + testSuiteType + "Reports/" + fileName + "_length"
							+ coverageLength + ".txt";
					// System.out.println(coverageAnalysisFilePath);
					// System.out.println(TSDcoverageAnalysisFilePath);
					// System.out.println(reportFilePath);
					CoverageAnalysisReportGenerator.compareCESCoverageAnalysisFiles(coverageAnalysisFilePath,
							TSDCoverageAnalysisFilePath, reportFilePath);
					// CoverageAnalysisReportGenerator.compareFCESCoverageAnalysisFiles(coverageAnalysisFilePath,
					// TSDCoverageAnalysisFilePath, reportFilePath);
				}

			}
		}

	}

}
