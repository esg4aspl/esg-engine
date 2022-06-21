package tr.edu.iyte.esg.applications;

import tr.edu.iyte.esg.model.*;
import tr.edu.iyte.esg.model.decisiontable.*;
import tr.edu.iyte.esg.testgeneration.TestSuite;
import tr.edu.iyte.esg.testgeneration.TestSuiteGenerator;

public class ESGWithDTValidationApp {

	static ESG esg;
	static DecisionTable decisionTable;
	static Condition condition0,condition1, condition2, condition3, condition4;
	static Rule rule0, rule1, rule2, rule3, rule4, rule5, rule6;
	static Action action0, action1;
	static Event event0, event1, event2, event3, event4;
	static Vertex vertex0, vertex1, vertex2, vertex3, vertex4;
	static Edge edge0, edge1, edge2, edge3, edge4,edge5;
	static Expression expr0, expr1, expr2, expr3, expr4, expr5, expr6; 
	
	public static void main(String[] args) {
		initializeESG();
		initializeDecisionTable();
		//System.out.println(decisionTable.toString());
		
		//System.out.println(esg.toString());
		
		TestSuiteGenerator testSuiteGenerator = new TestSuiteGenerator();
		TestSuite testSuite = testSuiteGenerator.generateTestSuiteESGWithDecisionTable(esg);
		System.out.println(testSuite.toString());
		

	}

	private static void initializeESG() {
		esg = new ESG(1, "ESG for Age Application");
		
		event0 = new EventSimple(esg.getNextEventID(), "[");
		esg.addEvent(event0);

		vertex0 = new VertexSimple(esg.getNextVertexID(), event0);
		esg.addVertex(vertex0);

		event1 = new EventSimple(esg.getNextEventID(), "Input Age Data");
		esg.addEvent(event1);
		
		decisionTable = new DecisionTable(esg.getNextDecisionTableID(), "Decision Table for Age Application");
		vertex1 = new VertexRefinedByDT(esg.getNextVertexID(), event1, decisionTable);
		esg.addVertex(vertex1);
		esg.addDecisionTable(vertex1, decisionTable);
		
		event2 = new EventSimple(esg.getNextEventID(), "Error/Warning");
		esg.addEvent(event2);
		
		vertex2 = new VertexSimple(esg.getNextVertexID(), event2);
		esg.addVertex(vertex2);
		
		event3 = new EventSimple(esg.getNextEventID(), "Calculate");
		esg.addEvent(event3);
		
		vertex3 = new VertexSimple(esg.getNextVertexID(), event3);
		esg.addVertex(vertex3);
		
		event4 = new EventSimple(esg.getNextEventID(), "]");
		esg.addEvent(event4);
		
		vertex4 = new VertexSimple(esg.getNextVertexID(), event4);
		esg.addVertex(vertex4);

		edge0 = new EdgeSimple(esg.getNextEdgeID(),vertex0, vertex1);
		esg.addEdge(edge0);		

		edge1 = new EdgeSimple(esg.getNextEdgeID(),vertex1, vertex2); 
		esg.addEdge(edge1);

		edge2 = new EdgeSimple(esg.getNextEdgeID(), vertex2, vertex1);
		esg.addEdge(edge2);		

		edge3 = new EdgeSimple(esg.getNextEdgeID(), vertex1, vertex3);
		esg.addEdge(edge3);		

		edge4 = new EdgeSimple(esg.getNextEdgeID(), vertex3, vertex1);
		esg.addEdge(edge4);	
		
		edge5 = new EdgeSimple(esg.getNextEdgeID(), vertex3, vertex4);
		esg.addEdge(edge5);		
	}	

	private static void initializeConditions() {
		
		expr0 = new Expression(decisionTable.getNextExpressionID(),"age is Type Of Integer");
		expr1 = new Expression(decisionTable.getNextExpressionID(),"age > 0");
		expr2 = new Expression(decisionTable.getNextExpressionID(),"age < 150");
		expr3 = new Expression(decisionTable.getNextExpressionID(),"biologicalStage = ADOLESCENCE");
		expr4 = new Expression(decisionTable.getNextExpressionID(),"age < adolescenceLB");
		expr5 = new Expression(decisionTable.getNextExpressionID(),"biologicalStage = ADULT");
		expr6 = new Expression(decisionTable.getNextExpressionID(),"age < adultLB");
		
		condition0 = new Condition(0);
		condition0.addExpression(expr0);
		
		condition1 = new Condition(1);
		condition1.addExpression(expr1);
		
		condition2 = new Condition(2);
		condition2.addExpression(expr2);
		
		condition3 = new Condition(3);
		condition3.addExpression(expr3);
		condition3.addExpression(expr4);
		
		condition4 = new Condition(4);
		condition4.addExpression(expr5);
		condition4.addExpression(expr6);
	}
	
	private static void initializeActions() {
		action0 = new Action(decisionTable.getNextActionID(),vertex2);
		action1 = new Action(decisionTable.getNextActionID(),vertex3);
	}
	
	private static void initializeRules() {
		Variable variable0 = new StringVariable(decisionTable.getNextVariableID(),"age");
		((StringVariable)variable0).setValue("A");
		Variable variable1 = new StringVariable(decisionTable.getNextVariableID(),"biologicalStage");
		((StringVariable)variable1).setValue("ADULT");
		rule0 = new Rule(0);
		rule0.addVariable(variable0);
		rule0.addVariable(variable1);
		rule0.put(condition0,new BooleanResult(false));
		rule0.put(condition1, new DCResult("-"));
		rule0.put(condition2, new DCResult("-"));
		rule0.put(condition3, new DCResult("-"));
		rule0.put(condition4, new DCResult("-"));
	
		
		Variable variable2 = new IntVariable(decisionTable.getNextVariableID(),"age");
		((IntVariable)variable2).setValue(-1);
		Variable variable3 = new StringVariable(decisionTable.getNextVariableID(),"biologicalStage");
		((StringVariable)variable3).setValue("ADOLESCENCE");
		rule1 = new Rule(1);
		rule1.addVariable(variable2);
		rule1.addVariable(variable3);
		rule1.put(condition0,new BooleanResult(true));
		rule1.put(condition1, new BooleanResult(false));
		rule1.put(condition2, new DCResult("-"));
		rule1.put(condition3, new DCResult("-"));
		rule1.put(condition4, new DCResult("-"));
		
		
		Variable variable4 = new IntVariable(decisionTable.getNextVariableID(),"age");
		((IntVariable)variable4).setValue(200);
		Variable variable5 = new StringVariable(decisionTable.getNextVariableID(),"biologicalStage");
		((StringVariable)variable5).setValue("ADULT");
		rule2 = new Rule(2);
		rule2.addVariable(variable4);
		rule2.addVariable(variable5);
		rule2.put(condition0,new BooleanResult(true));
		rule2.put(condition1, new BooleanResult(true));
		rule2.put(condition2, new BooleanResult(false));
		rule2.put(condition3, new DCResult("-"));
		rule2.put(condition4, new DCResult("-"));
		
		Variable variable6 = new IntVariable(decisionTable.getNextVariableID(),"age");
		((IntVariable)variable6).setValue(18);
		Variable variable7 = new StringVariable(decisionTable.getNextVariableID(),"biologicalStage");
		((StringVariable)variable7).setValue("ADOLESCENCE");
		rule3 = new Rule(3);
		rule3.addVariable(variable6);
		rule3.addVariable(variable7);
		rule3.put(condition0, new BooleanResult(true));
		rule3.put(condition1, new BooleanResult(true));
		rule3.put(condition2, new BooleanResult(true));
		rule3.put(condition3, new BooleanResult(false));
		rule3.put(condition4, new DCResult("-"));
		
		Variable variable8 = new IntVariable(decisionTable.getNextVariableID(),"age");
		((IntVariable)variable8).setValue(7);
		Variable variable9 = new StringVariable(decisionTable.getNextVariableID(),"biologicalStage");
		((StringVariable)variable9).setValue("ADOLESCENCE");
		rule4 = new Rule(4);
		rule4.addVariable(variable8);
		rule4.addVariable(variable9);
		rule4.put(condition0, new BooleanResult(true));
		rule4.put(condition1, new BooleanResult(true));
		rule4.put(condition2, new BooleanResult(true));
		rule4.put(condition3, new BooleanResult(true));
		rule4.put(condition4, new DCResult("-"));
		
		Variable variable10 = new IntVariable(decisionTable.getNextVariableID(),"age");
		((IntVariable)variable10).setValue(25);
		Variable variable11 = new StringVariable(decisionTable.getNextVariableID(),"biologicalStage");
		((StringVariable)variable11).setValue("ADULT");
		rule5 = new Rule(5);
		rule5.addVariable(variable10);
		rule5.addVariable(variable11);
		rule5.put(condition0, new BooleanResult(true));
		rule5.put(condition1, new BooleanResult(true));
		rule5.put(condition2, new BooleanResult(true));
		rule5.put(condition3, new DCResult("-"));
		rule5.put(condition4, new BooleanResult(false));
		
		Variable variable12 = new IntVariable(decisionTable.getNextVariableID(),"age");
		((IntVariable)variable12).setValue(18);
		Variable variable13 = new StringVariable(decisionTable.getNextVariableID(),"biologicalStage");
		((StringVariable)variable13).setValue("ADULT");
		rule6 = new Rule(6);
		rule6.addVariable(variable12);
		rule6.addVariable(variable13);
		rule6.put(condition0, new BooleanResult(true));
		rule6.put(condition1, new BooleanResult(true));
		rule6.put(condition2, new BooleanResult(true));
		rule6.put(condition3, new DCResult("-"));
		rule6.put(condition4, new BooleanResult(true));
	}
	
	
	private static void initializeDecisionTable() {
		
		initializeConditions();
		initializeActions();
		initializeRules();
		
		decisionTable.addCondition(condition0);
		decisionTable.addCondition(condition1);
		decisionTable.addCondition(condition2);
		decisionTable.addCondition(condition3);
		decisionTable.addCondition(condition4);
		
		decisionTable.addAction(action0);
		decisionTable.addAction(action1);
		
		decisionTable.addRule(rule0);
		decisionTable.addRule(rule1);
		decisionTable.addRule(rule2);
		decisionTable.addRule(rule3);
		decisionTable.addRule(rule4);
		decisionTable.addRule(rule5);
		decisionTable.addRule(rule6);
	
		
		decisionTable.put(rule0, action0);
		decisionTable.put(rule1, action0);
		decisionTable.put(rule2, action0);
		decisionTable.put(rule3, action0);
		decisionTable.put(rule4, action1);
		decisionTable.put(rule5, action0);
		decisionTable.put(rule6, action1);
	}
	
	

}
