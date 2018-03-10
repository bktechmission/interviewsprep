package recursion;

import java.util.HashMap;
import java.util.Map;

public class FlattenDirectoryStructure {

	public static void main(String[] args) {

	}
	
	static HashMap<String, String> flattenDictionary(HashMap<String, Object> dict) {
		// your code goes here
		HashMap<String, String> resultMap = new HashMap<>();
		traverseNestMap(dict, resultMap, "");
		return resultMap;
	}

	/*
	 * dict = { "Key1" : "1", "Key2" : { "a" : "2", "b" : "3", "c" : { "d" : "3",
	 * "e" : { "" : "1" } } } }
	 */
	@SuppressWarnings("unchecked")
	static void traverseNestMap(HashMap<String, Object> dic, Map<String, String> resultMap, String prefix) {

		for (Map.Entry<String, Object> entry : dic.entrySet()) {
			String key = entry.getKey();
			Object val = entry.getValue();
			String prefixTemp = (prefix.length() == 0 ? "" : prefix + ((key == null || key.length() == 0) ? "" : "."));
			
			// case 1: will be it is a String to String match
			if (val instanceof Integer) {
				resultMap.put((prefixTemp + key), val.toString());
			} else if (val instanceof String) {
				resultMap.put((prefixTemp + key), (String) val);
			} 
			else {
				// case 2: will be it is a String to Map match
				traverseNestMap((HashMap<String,Object>)val, resultMap, prefixTemp + key);
			}
		}
	}
}
