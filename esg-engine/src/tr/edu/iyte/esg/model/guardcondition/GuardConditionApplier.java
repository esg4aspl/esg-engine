package tr.edu.iyte.esg.model.guardcondition;

import java.util.List;
import java.util.Map;

import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Edge;

public class GuardConditionApplier {

	public GuardConditionApplier() {

	}

	public void applyGuardCondition(ESG ESG, List<GuardCondition> guardConditionList) {

		for (GuardCondition guardCondition : guardConditionList) {
			for (Map.Entry<String, List<String>> edgeToBeRemoved : guardCondition.getEdgesToBeRemoved().entrySet()) {
				String source = edgeToBeRemoved.getKey();
				List<String> targetList = edgeToBeRemoved.getValue();

				for (String target : targetList) {
					Edge edge = ESG.getEdgeBySourceEventNameTargetEventName(source, target);
					ESG.removeEdge(edge);
				}
			}
		}
	}

}
