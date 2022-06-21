package tr.edu.iyte.esg.applications;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import tr.edu.iyte.esg.conversion.json.JSONFileToGuardConditionConverter;
import tr.edu.iyte.esg.conversion.mxe.MXEFiletoESGConverter;
import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.guardcondition.GuardCondition;
import tr.edu.iyte.esg.model.guardcondition.GuardConditionApplier;
import tr.edu.iyte.esg.model.validation.ESGValidator;

public class GuardConditionApp {

	public static void main(String[] args) {
		
		String ESGfileName = "files/MXEFiles/DooApplication.mxe";
		ESG ESG = null;
		try {
			ESG = MXEFiletoESGConverter.parseMXEFileForESGSimpleCreation(ESGfileName);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(ESG.toString());
		
		String guardConditionFileName = "files/JSONFiles/DoorApplicationGuardConditionResults.json";
		
		List<GuardCondition> guardConditionList = new LinkedList<GuardCondition>();
		try {
			guardConditionList = JSONFileToGuardConditionConverter.parseJSONFileForGuardConditionCreation(guardConditionFileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		for(GuardCondition guardCondition : guardConditionList) {
			System.out.println(guardCondition.toString());
			
		}
		GuardConditionApplier guardConditionApplier = new GuardConditionApplier();
		guardConditionApplier.applyGuardCondition(ESG, guardConditionList);
		
		System.out.println("AFTER GUARD CONDITION APPLYING");
		
		ESGValidator ESGValidator = new ESGValidator();
		ESGValidator.validate(ESG);
	
	}

}
