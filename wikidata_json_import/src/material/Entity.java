package material;

import java.util.ArrayList;
import java.util.Iterator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.JsonFileRead;

/*
 * @author JohnsonFu
 * 
 * @date 11-1-2017
 */
public class Entity {
	public String entity_id, entity_type;// entity_list;
	// fingerprint building
	public ArrayList<Labels> labelsArrayList = new ArrayList<Labels>();// lables_list;
	public ArrayList<Aliases> aliasesArrayList = new ArrayList<Aliases>();// aliases_list;
	public ArrayList<Descriptions> descriptionsArrayList = new ArrayList<Descriptions>();// description_list;
	public ArrayList<Properties> propertiesArrayList = new ArrayList<Properties>();// properties_list;
	//
	/*
	 * String property_id, property_type;// property_list; String string_type,
	 * string_value;// property_string_list; String numeric_id;//
	 * property_entity; String latitude, longitude, altitude;//
	 * property_globecoordinate String amount;// property_quantity; String
	 * time;// property_time;
	 */

	public static boolean isEntity(String jsonString) {
		if (jsonString.charAt(0) == '{')
			return true;
		return false;
	}

	public boolean extract(String jsonString) {
		try {
			JSONObject line = JSONObject.fromString(jsonString);
			entity_id = line.getString("id");
			entity_type = line.getString("type");
			// modified_time = line.getString("modified");
			// labels extract
			Labels tempLabels = new Labels();
			if (line.getJSONObject("labels").has("en")) {
				tempLabels.setAll("en", line.getJSONObject("labels").getJSONObject("en").getString("value"));
				labelsArrayList.add(tempLabels);
			}

			if (line.getJSONObject("labels").has("zh-cn")) {
				tempLabels = new Labels();
				tempLabels.setAll("zh-cn", line.getJSONObject("labels").getJSONObject("zh-cn").getString("value"));
				labelsArrayList.add(tempLabels);
			}
			// aliases extract
			Aliases tempAliases;
			JSONArray transArray;
			if (line.getJSONObject("aliases").has("en")) {
				transArray = line.getJSONObject("aliases").getJSONArray("en");
				for (int i = 0; i < transArray.length(); i++) {
					tempAliases = new Aliases();
					tempAliases.setAll(transArray.getJSONObject(i).getString("language"),
							transArray.getJSONObject(i).getString("value"));
					aliasesArrayList.add(tempAliases);
				}
			}

			if (line.getJSONObject("aliases").has("zh-cn")) {
				transArray = line.getJSONObject("aliases").getJSONArray("zh-cn");
				for (int i = 0; i < transArray.length(); i++) {
					tempAliases = new Aliases();
					tempAliases.setAll(transArray.getJSONObject(i).getString("language"),
							transArray.getJSONObject(i).getString("value"));
					aliasesArrayList.add(tempAliases);
				}
			}
			// descriptions extract
			Descriptions tempDesriptions;
			tempDesriptions = new Descriptions();
			if (line.getJSONObject("descriptions").has("en")) {
				tempDesriptions.setAll(line.getJSONObject("descriptions").getJSONObject("en").getString("language"),
						line.getJSONObject("descriptions").getJSONObject("en").getString("value"));
			}
			descriptionsArrayList.add(tempDesriptions);
			if (line.getJSONObject("descriptions").has("zh-cn")) {
				tempDesriptions = new Descriptions();
				tempDesriptions.setAll(line.getJSONObject("descriptions").getJSONObject("zh-cn").getString("language"),
						line.getJSONObject("descriptions").getJSONObject("zh-cn").getString("value"));
				descriptionsArrayList.add(tempDesriptions);
			}
			// property extract
			Iterator iterator = line.getJSONObject("claims").keys();
			String tempKey;// claim's son ,unknown properties
			JSONArray propertyJsonArray;
			JSONObject propertyJsonObject;
			Properties temProperties;
			String propertyID, valueType, value;
			while (iterator.hasNext()) {
				tempKey = (String) iterator.next();// iterator.next()=P1151(property_id
													// has multiple mainSnak son
													// )
				propertyJsonArray = line.getJSONObject("claims").getJSONArray(tempKey);
				for (int i = 0; i < propertyJsonArray.length(); i++) {
					propertyJsonObject = propertyJsonArray.getJSONObject(i);
					// propertyType = propertyJsonObject.getString("type");//
					// propertiesType
					propertyID = propertyJsonObject.getJSONObject("mainsnak").getString("property");// propertyID
					valueType = propertyJsonObject.getJSONObject("mainsnak").getString("datatype");// valueType
					if (valueType.equals("wikibase-item")) {
						if (propertyJsonObject.getJSONObject("mainsnak").has("datavalue")) {
							value = propertyJsonObject.getJSONObject("mainsnak").getJSONObject("datavalue")
									.getJSONObject("value").getString("id");
						} else {
							value = "unknown";
						}

					} else if (valueType.equals("globe-coordinate")) {
						if (propertyJsonObject.getJSONObject("mainsnak").has("datavalue")) {
							value = "latitude:" + propertyJsonObject.getJSONObject("mainsnak")
									.getJSONObject("datavalue").getJSONObject("value").getString("latitude");
							value = value + "longitude:" + propertyJsonObject.getJSONObject("mainsnak")
									.getJSONObject("datavalue").getJSONObject("value").getString("longitude");
							value = value + "altitude:" + propertyJsonObject.getJSONObject("mainsnak")
									.getJSONObject("datavalue").getJSONObject("value").getString("altitude");
						} else {
							value = "unknown";
						}

					} else if (valueType.equals("quantity")) {
						if (propertyJsonObject.getJSONObject("mainsnak").has("datavalue")) {
							value = propertyJsonObject.getJSONObject("mainsnak").getJSONObject("datavalue")
									.getJSONObject("value").getString("amount");
						} else {
							value = "unknown";
						}

					} else if (valueType.equals("time")) {
						if (propertyJsonObject.getJSONObject("mainsnak").has("datavalue")) {
							value = propertyJsonObject.getJSONObject("mainsnak").getJSONObject("datavalue")
									.getJSONObject("value").getString("time");
						} else {
							value = "unknown";
						}

					} else if (valueType.equals("wikibase-property")) {
						if (propertyJsonObject.getJSONObject("mainsnak").has("datavalue")) {
							value = propertyJsonObject.getJSONObject("mainsnak").getJSONObject("datavalue")
									.getJSONObject("value").getString("id");
						} else {
							value = "unknown";
						}

					} else if (valueType.equals("monolingualtext")) {
						if (propertyJsonObject.getJSONObject("mainsnak").has("datavalue")) {
							value = propertyJsonObject.getJSONObject("mainsnak").getJSONObject("datavalue")
									.getJSONObject("value").getString("text");
						} else {
							value = "unknown";
						}

					} else {
						if (propertyJsonObject.getJSONObject("mainsnak").has("datavalue")) {
							value = propertyJsonObject.getJSONObject("mainsnak").getJSONObject("datavalue")
									.getString("value");
						} else {
							value = "unknown";
						}

					} // value
					temProperties = new Properties();
					if (!value.equals("unknown")) {
						temProperties.setAll(propertyID, valueType, value);
						propertiesArrayList.add(temProperties);
					}

				}

			}
			return true;
		} catch (Exception exception) {
			return false;
		}
	}

}
