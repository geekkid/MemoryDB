package dev.memorydb;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Database {
	Map<String, String> database;
	Map<String, Set<String>> values;

	public Database() {
		database = new HashMap<String, String>();
		values = new HashMap<String, Set<String>>();
	}
	public String get(String key) {
		return database.get(key);
	}

	public void set(String key, String value) {
		database.put(key, value);
		if (!values.containsKey(value)) {
			values.put(value, new HashSet<String>());
		}
		values.get(value).add(key);
	}

	public void delete(String key) {
		String value = database.get(key);
		if (value == null)
			return;
		database.remove(key);
		values.get(value).remove(key);
	}

	public int count(String value) {
		if (values.containsKey(value))
			return values.get(value).size();
		return 0;
	}
	
	public Set<String> keys() {
		return database.keySet();
	}
	
	public Set<String> getValueKeys(String value) {
		return values.get(value);
	}
	
	public void setValueKeys(String value, Set<String> valueKeys) {
		values.put(value, valueKeys);
	}
}
