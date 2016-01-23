package dev.memorydb;

import java.util.HashSet;
import java.util.Set;

public class Transaction {

	private Database db;
	private Transaction parentTransaction;
	private Set<String> deletedKeys;

	public Transaction(Transaction parentTransaction) {
		db = new Database();
		this.parentTransaction = parentTransaction;
		deletedKeys = new HashSet<String>();
	}

	public String get(String key) {
		// check to see if a is not in the delete list
		if (deletedKeys.contains(key) && parentTransaction != null)
			return null;
		if (db.get(key) == null && parentTransaction != null)
			return parentTransaction.get(key);
		return db.get(key);
	}

	public void set(String key, String value) {
		String oldValue = this.get(key);
		deletedKeys.remove(key);
		Set<String> valueKeys = clone(getValueKeys(value));
		db.set(key, value);
		if (valueKeys != null) {
			valueKeys.add(key);
			db.setValueKeys(value, valueKeys);
		}
		if (oldValue == null)
			return;
		Set<String> oldValueKeys = clone(getValueKeys(oldValue));
		if (oldValueKeys != null)
			oldValueKeys.remove(key);
		db.setValueKeys(oldValue, oldValueKeys);
	}

	public void delete(String key) {
		// TODO
		String value = db.get(key);
		if (value == null && parentTransaction != null)
			value = parentTransaction.get(key);
		if (value == null)
			return;
		if (parentTransaction == null)
			db.delete(key);
		else {
			deletedKeys.add(key);
			Set<String> valueKeys = clone(getValueKeys(value));
			if (valueKeys == null)
				valueKeys = new HashSet<String>(); // should never be executed
			valueKeys.remove(key);
			db.setValueKeys(value, valueKeys);
		}
	}

	public Set<String> getValueKeys(String value) {
		Set<String> valueKeys = db.getValueKeys(value);
		if (valueKeys != null)
			return valueKeys;
		if (parentTransaction != null) {
			return parentTransaction.getValueKeys(value);
		}
		return null;
	}

	public int count(String value) {
		// get all key sets from the hierarchy

		Set<String> valueKeys = db.getValueKeys(value);

		if (valueKeys == null && parentTransaction != null) {
			return parentTransaction.getValueKeys(value).size();
		}
		if (valueKeys != null)
			return valueKeys.size();
		return 0;
	}

	public void commit() {
		for (String key : db.keys()) {
			parentTransaction.set(key, db.get(key));
		}

		for (String deleteKey : deletedKeys) {
			parentTransaction.delete(deleteKey);
		}

	}

	public void rollback() {

	}

	private Set<String> clone(Set<String> sourceSet) {
		if (sourceSet == null)
			return null;
		Set<String> clonedSet = new HashSet<String>();
		clonedSet.addAll(sourceSet);
		return clonedSet;
	}
}
