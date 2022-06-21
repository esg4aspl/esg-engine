package tr.edu.iyte.esg.conversion.json;

import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import tr.edu.iyte.esg.model.ESG;
import tr.edu.iyte.esg.model.Edge;
import tr.edu.iyte.esg.model.EdgeSimple;
import tr.edu.iyte.esg.model.Event;
import tr.edu.iyte.esg.model.EventSimple;
import tr.edu.iyte.esg.model.Model;
import tr.edu.iyte.esg.model.Vertex;
import tr.edu.iyte.esg.model.VertexRefinedByDT;
import tr.edu.iyte.esg.model.VertexRefinedByESG;
import tr.edu.iyte.esg.model.comparators.VertexComparator;
import tr.edu.iyte.esg.model.decisiontable.Action;
import tr.edu.iyte.esg.model.decisiontable.BooleanResult;
import tr.edu.iyte.esg.model.decisiontable.Condition;
import tr.edu.iyte.esg.model.decisiontable.Connective;
import tr.edu.iyte.esg.model.decisiontable.DCResult;
import tr.edu.iyte.esg.model.decisiontable.DecisionTable;
import tr.edu.iyte.esg.model.decisiontable.DoubleVariable;
import tr.edu.iyte.esg.model.decisiontable.Expression;
import tr.edu.iyte.esg.model.decisiontable.IntVariable;
import tr.edu.iyte.esg.model.decisiontable.Rule;
import tr.edu.iyte.esg.model.decisiontable.StringVariable;
import tr.edu.iyte.esg.model.decisiontable.Variable;
import tr.edu.iyte.esg.model.VertexSimple;

public class JSONFileToESGConverter {

	private Model model;
	// private ESG ESG;

	public JSONFileToESGConverter() {
		model = new Model("The Model");

	}
	
	/**
	 * Parse the given file to create a simple ESG
	 * 
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 */
	public static ESG parseJSONFileForESGCreation(String fileName) {
		JSONTokener tokener = null;
		try {
			tokener = new JSONTokener(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parseJSONObjectForESGSimpleCreation((JSONObject) tokener.nextValue());
	}
	
	/**
	 * Parse the given file to create an ESG with Decision Table
	 * 
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 */
	public static ESG parseJSONFileForESGWithDTCreation(String fileName) {
		JSONTokener tokener = null;
		try {
			tokener = new JSONTokener(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parseJSONObjectForESGWithDTCreation((JSONObject) tokener.nextValue());
	}
	
	/**
	 * Parse the given file to create a model with a list of ESGs
	 * 
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 */
	public Model parseJSONFileForModelCreation(String fileName) throws FileNotFoundException {
		ESG ESG = parseJSONFileForESGSimpleCreation(fileName);
		model.addESG(ESG);
		return model;
	}
	
	/**
	 * Parse the given file to create a model with a list of ESGs with DT
	 * 
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 */
	public Model parseJSONFileForModelCreationWithDT(String fileName) throws FileNotFoundException {
		ESG ESG = parseJSONFileForESGWithDTCreation(fileName);
		model.addESG(ESG);
		return model;
	}
	
	/**
	 * Parse the given file to create a simple ESG
	 * 
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 */
	public static ESG parseJSONFileForESGSimpleCreation(String fileName) throws FileNotFoundException {
		JSONTokener tokener = new JSONTokener(new FileReader(fileName));
		ESG ESG = new ESG(-1, "temp name");

		try {
			JSONObject esg = (JSONObject) tokener.nextValue();
			int ID = esg.getInt("ID");
			String name = esg.getString("name");
			ESG = new ESG(ID, name);

			JSONArray JSONVertices = esg.getJSONArray("vertices");
			createESGEvents(ESG, JSONVertices);
			createESGVertices(ESG, JSONVertices);

			JSONArray JSONEdges = esg.getJSONArray("edges");
			createESGEdges(ESG, JSONEdges);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ESG;
	}

	/**
	 * Parse the given json object to create a simple ESG
	 *
	 * @param esgJsonObject
	 * @return ESG object
	 */
	public static ESG parseJSONObjectForESGSimpleCreation(JSONObject esgJsonObject) {
		ESG ESG = new ESG(-1, "temp name");

		try {
			int ID = esgJsonObject.getInt("ID");
			String name = esgJsonObject.getString("name");
			ESG = new ESG(ID, name);

			JSONArray JSONVertices = esgJsonObject.getJSONArray("vertices");
			createESGEvents(ESG, JSONVertices);
			createESGVertices(ESG, JSONVertices);

			JSONArray JSONEdges = esgJsonObject.getJSONArray("edges");
			createESGEdges(ESG, JSONEdges);
			
			if(esgJsonObject.has("decisionTables")) {
				JSONArray JSONDecisionTables = esgJsonObject.getJSONArray("decisionTables");
				createESGDecisionTables(ESG, JSONDecisionTables);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ESG;
	}
	
	
	/**
	 * Parse the given json object to create an ESG with Decision Table
	 *
	 * @param esgJsonObject
	 * @return ESG object
	 */
	public static ESG parseJSONObjectForESGWithDTCreation(JSONObject esgJsonObject) {
		ESG ESG = new ESG(-1, "temp name");

		try {
			int ID = esgJsonObject.getInt("ID");
			String name = esgJsonObject.getString("name");
			ESG = new ESG(ID, name);

			JSONArray JSONVertices = esgJsonObject.getJSONArray("vertices");
			createESGEvents(ESG, JSONVertices);
			createESGVertices(ESG, JSONVertices);

			JSONArray JSONEdges = esgJsonObject.getJSONArray("edges");
			createESGEdges(ESG, JSONEdges);
			
			if(esgJsonObject.has("decisionTables")) {
				JSONArray JSONDecisionTables = esgJsonObject.getJSONArray("decisionTables");
				createESGDecisionTables(ESG, JSONDecisionTables);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ESG;
	}


	/**
	 * Creates new events from given JSON array for vertices Adds each new event to
	 * the ESG event list In JSON file, events are kept in JSON Objects of vertices
	 * 
	 * @param ESG
	 * @param JSONVertices
	 */
	protected static void createESGEvents(ESG ESG, JSONArray JSONVertices) {

		try {
			for (int i = 0; i < JSONVertices.length(); i++) {
				JSONObject JSONVertex = JSONVertices.getJSONObject(i);
				String JSONEvent = JSONVertex.getString("event");
				Event event = new EventSimple(ESG.getNextEventID(), JSONEvent);
				

				/**
				 * Event is added to ESG event list
				 */
				ESG.addEvent(event);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Creates new vertices from given JSON array for vertices Adds each new vertex
	 * to the ESG vertex list
	 * 
	 * @param ESG
	 * @param JSONVertices
	 */
	protected static void createESGVertices(ESG ESG, JSONArray JSONVertices) {

		try {
			for (int i = 0; i < JSONVertices.length(); i++) {
				JSONObject JSONVertex = JSONVertices.getJSONObject(i);

				String JSONEvent = JSONVertex.getString("event");
				Event event = eventLookUp(ESG, JSONEvent);
				
				int vertexID = JSONVertex.getInt("ID");
				boolean isWithDecisionTable = false;
				boolean isRefinedVertex = false;
				try {
					isWithDecisionTable = JSONVertex.getBoolean("decisionTable");
				}catch(JSONException exception){
					
				}
				try {
					isRefinedVertex = JSONVertex.getBoolean("isRefinedVertex");
				}catch(JSONException exception){
					
				}
				Vertex vertex = null;
				if(!isWithDecisionTable && !isRefinedVertex) {
					String color = "black";
					if(JSONVertex.has("color")) {
						color = JSONVertex.getString("color");
					}
					vertex = new VertexSimple(vertexID, event, color);
				}else if(isWithDecisionTable){
					vertex = new VertexRefinedByDT(vertexID, event);
				}else if(isRefinedVertex) {
					try {
						JSONObject subESGObject = JSONVertex.getJSONObject("subESG");
						ESG subESG = parseJSONObjectForESGSimpleCreation(subESGObject);
						vertex = new VertexRefinedByESG(vertexID, event, subESG);
					}catch(JSONException exception){
						
					}
				}
				
				/**
				 * Vertex is added to ESG vertex list
				 */
				ESG.addVertex(vertex);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}


	/**
	 * Creates new edges from given JSON array for edges Adds each new edge to the
	 * ESG edge list
	 * 
	 * @param ESG
	 * @param JSONedges
	 */
	protected static void createESGEdges(ESG ESG, JSONArray JSONedges) {

		try {
			for (int i = 0; i < JSONedges.length(); i++) {
				JSONObject JSONEdge = JSONedges.getJSONObject(i);

				int JSONSource = JSONEdge.getInt("source");
				//System.out.println("source" + JSONSource);
				int JSONTarget = JSONEdge.getInt("target");
				//System.out.println("target" + JSONTarget);
				Vertex source = vertexLookUp(ESG, JSONSource);
				Vertex target = vertexLookUp(ESG, JSONTarget);
				
				String color = "black";
				if(JSONEdge.has("color")) {
					color = JSONEdge.getString("color");
				}

				Edge edge = new EdgeSimple(ESG.getNextEdgeID(), source, target, color);
				
				/**
				 * Edge is added to ESG edge list
				 */
				ESG.addEdge(edge);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Creates new decision tables from given JSON array 
	 * ESG decision table list
	 * 
	 * @param ESG
	 * @param JSONDecisionTables
	 */
	protected static void createESGDecisionTables(ESG ESG, JSONArray JSONDecisionTables) {

		try {
			for (int i = 0; i < JSONDecisionTables.length(); i++) {
				JSONObject JSONDecisionTable = JSONDecisionTables.getJSONObject(i);
				
				int ID = JSONDecisionTable.getInt("ID");
				String name = JSONDecisionTable.getString("name");
				DecisionTable decisionTable = new DecisionTable(ID, name);
				
				JSONArray conditions = JSONDecisionTable.getJSONArray("conditions");
				createDecisionTableConditions(decisionTable, conditions);
				
				JSONArray actions = JSONDecisionTable.getJSONArray("actions");
				createDecisionTableActions(ESG,decisionTable, actions);
				
				JSONArray rules = JSONDecisionTable.getJSONArray("rules");
				createDecisionTableRules(decisionTable, rules);
				
				int vertexID = JSONDecisionTable.getInt("vertexID");
				Vertex vertex = vertexLookUp(ESG, vertexID);
				((VertexRefinedByDT)vertex).setDecisionTable(decisionTable);
				ESG.addDecisionTable(vertex, decisionTable);
				
				createRulesActionEdges(ESG, decisionTable, vertex);		
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Creates Rule's action edges 
	 */
	private static void createRulesActionEdges(ESG ESG, DecisionTable dt, Vertex source) {
		List<Rule> rList = dt.getRuleList();
		for(Rule r: dt.getRuleList()) {
			Set<Action> actions = dt.getAction(r);
			for(Action a: actions) {
				if(a != null) {
					Edge edge = new EdgeSimple(999, source, a.getActionEvent());
					ESG.addEdge(edge);
				}
			}
		}
	}
	
	/**
	 * Creates conditions from given JSON array 
	 * 
	 * @param ESG
	 * @param conditions
	 */
	private static void createDecisionTableConditions(DecisionTable decisionTable, JSONArray conditions) {

		try {
			for (int i = 0; i < conditions.length(); i++) {
				JSONObject JSONCondition = conditions.getJSONObject(i);
				int ID = JSONCondition.getInt("ID");
				Condition condition = new Condition(ID);
				JSONArray expressions = JSONCondition.getJSONArray("expressions");
				createConditionExpressions(condition, expressions);
				decisionTable.addCondition(condition);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
	
	private static void createConditionExpressions(Condition condition, JSONArray expressions) {

		try {
			for (int i = 0; i < expressions.length(); i++) {
				JSONObject JSONExpression = expressions.getJSONObject(i);
				addEvaluable(JSONExpression, condition);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
	
	private static void addEvaluable(JSONObject JSONExpression, Condition condition) {
		try {
			int ID = JSONExpression.getInt("ID");
			String exp = JSONExpression.getString("expression");
			Expression expression = new Expression(ID, exp);
			condition.addEvaluable(expression);
		} catch (JSONException e) {
			try {
				String connectiveType = JSONExpression.getString("connective");
				Connective connective = new Connective(connectiveType);
				condition.addEvaluable(connective);
			}  catch (JSONException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * Creates actions from given JSON array 
	 * 
	 * @param ESG
	 * @param actions
	 */
	private static void createDecisionTableActions(ESG ESG,DecisionTable decisionTable, JSONArray actions) {

		try {
			for (int i = 0; i < actions.length(); i++) {
				JSONObject JSONAction = actions.getJSONObject(i);
				int ID = JSONAction.getInt("ID");
				int actionEventID = JSONAction.getInt("actionEvent");
				
				Vertex actionEvent = vertexLookUp(ESG, actionEventID);
				Action action = new Action(ID, actionEvent);
				decisionTable.addAction(action);			
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Creates rules from given JSON array 
	 * 
	 * @param ESG
	 * @param rules
	 */
	private static void createDecisionTableRules(DecisionTable decisionTable, JSONArray rules) {

		try {
			for (int i = 0; i < rules.length(); i++) {
				JSONObject JSONRule = rules.getJSONObject(i);
				int ID = JSONRule.getInt("ID");
				Rule rule = new Rule(ID);
				decisionTable.addRule(rule);
				
				JSONArray variables = JSONRule.getJSONArray("variables");
				createRuleVariables(rule, variables);
				
				JSONArray conditionResults = JSONRule.getJSONArray("conditionResults");
				createRuleConditionResults(decisionTable, rule, conditionResults);
				
				JSONArray actionIDs = JSONRule.getJSONArray("actionIDs");
				
				for(int j = 0; j < actionIDs.length(); j++) {
					int actionID = actionIDs.getInt(j);
					Action action = actionLookUp(decisionTable, actionID);
					decisionTable.put(rule, action);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
	
	private static void createRuleVariables(Rule rule, JSONArray variables) {

		try {
			for (int i = 0; i < variables.length(); i++) {
				JSONObject JSONVariable = variables.getJSONObject(i);
				int ID = JSONVariable.getInt("ID");
				String name = JSONVariable.getString("name");
				String type = JSONVariable.getString("type");
				Variable variable = null;
				if (type.contentEquals("String")) {
					String value = JSONVariable.getString("value");
					variable = new StringVariable(ID, name);
					((StringVariable) variable).setValue(value);
					rule.addVariable(variable);
				} else if (type.contentEquals("Integer")) {
					int value = JSONVariable.getInt("value");
					variable = new IntVariable(ID, name);
					((IntVariable) variable).setValue(value);
					rule.addVariable(variable);
				} else if (type.contentEquals("Double")) {
					double value = JSONVariable.getDouble("value");
					variable = new DoubleVariable(ID, name);
					((DoubleVariable) variable).setValue(value);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private static void createRuleConditionResults(DecisionTable decisionTable, Rule rule, JSONArray conditionResults) {

		try {
			for (int i = 0; i < conditionResults.length(); i++) {
				JSONObject JSONConditionResult = conditionResults.getJSONObject(i);
				int conditionID = JSONConditionResult.getInt("condition");
				Condition condition = conditionLookUp(decisionTable, conditionID);
				boolean booleanResult = true;
				String DCResult = "-";
				try {
					booleanResult = JSONConditionResult.getBoolean("result");
					rule.put(condition, new BooleanResult(booleanResult));
				}catch(JSONException exception) {
					
				}
				try {
					DCResult = JSONConditionResult.getString("result");
					rule.put(condition, new DCResult(DCResult));
				}catch(JSONException exception) {
					
				}
				
									

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
	
	private static Condition conditionLookUp(DecisionTable decisionTable, int ID) {
		Condition c = new Condition(-1);
		
		for(Condition condition : decisionTable.getConditionList()) {
			if(condition.getID() == ID) {
				c = condition;
				break;
			}
		}
		
		return c;
	}
	
	private static Action actionLookUp(DecisionTable decisionTable, int ID) {
		Action a = null;
		
		for(Action action : decisionTable.getActionList()) {
			if(action.getID() == ID) {
				a = action;
				break;
			}
		}
		
		return a;
	}

	/**
	 * ESG event list is searched for a specific JSON Event of a vertex
	 * 
	 * @param ESG
	 * @param JSONEvent
	 * @return
	 */
	protected static Event eventLookUp(ESG ESG, String JSONEvent) {
		Event event = null;

		for (Event e : ESG.getEventList()) {
			if (e.getName().equals(JSONEvent)) {
				event = e;
			}
		}
		return event;
	}

	/**
	 * ESG vertex list is searched for a specific JSON ID of a vertex Binary Search
	 * is used
	 * 
	 * @param ESG
	 * @param JSONID
	 * @return
	 */
	protected static Vertex vertexLookUp(ESG ESG, int JSONID) {
		Vertex vertex = null;

		Vertex key = new VertexSimple(JSONID, new EventSimple(JSONID, "key"));

		VertexComparator vertexComparator = new VertexComparator();
		Collections.sort(ESG.getVertexList(), vertexComparator);
		int index = Collections.binarySearch(ESG.getVertexList(), key, vertexComparator);

		vertex = ESG.getVertexList().get(index);

		return vertex;
	}
	

	


}

