package tr.edu.iyte.esg.conversion;

public class EventNameModifier {

	private static final String space = " ";

	/*public static String modifyEventName(String eventName) {
		eventName.replaceAll("\n","");
		eventName.replaceAll("\r","");
		eventName.replaceAll("\r\n","");
		
		if (!eventName.contains(" ")) {

			return eventName;
		} else {
			String trimmed = eventName.trim();
			if (trimmed.contains(" ")) {
				String newEventName = "";
				String[] eventNameArray = trimmed.split("\\s+");

				for (int i = 0; i < eventNameArray.length - 1; i++) {
					newEventName += eventNameArray[i] + space;
				}
				newEventName += eventNameArray[eventNameArray.length - 1];

				return newEventName;
			} else {
				return trimmed;
			}

		}

	}*/
	
	public static String modifyEventName(String eventName) {
		eventName.replaceAll("\n","");
		eventName.replaceAll("\r","");
		eventName.replaceAll("\r\n","");
		
		if (!eventName.contains(" ")) {

			return eventName;
		} else {
			String trimmed = eventName.trim();
			if (trimmed.contains(" ")) {
//				System.out.println("eventName:" + eventName + " :" + eventName.length());
				String newEventName = "";
				String[] eventNameArray = trimmed.split("\\s+");

				for (int i = 0; i < eventNameArray.length - 1; i++) {
					newEventName += eventNameArray[i] + space;
				}
				newEventName += eventNameArray[eventNameArray.length - 1];
//				System.out.println("newEventName:"+ newEventName + " :" + newEventName.length());
				
				if(newEventName.contains(",")) {
//					System.out.println("newEventName:"+ newEventName + " :" + newEventName.length());
					String n_newEventName = "";
					String[] eventNameArray2 = newEventName.split(",[ ]*");
					//System.out.println(eventNameArray2.length);
					for (int j = 0; j < eventNameArray2.length - 1; j++) {
						n_newEventName += eventNameArray2[j] + ",";
					}
					n_newEventName += eventNameArray2[eventNameArray2.length - 1];
//					System.out.println("n_newEventName " + n_newEventName + " :" + n_newEventName.length());
					return n_newEventName;
				}
				
				return newEventName;
			} else {
				return trimmed;
			}

		}

	}
	
	

}
