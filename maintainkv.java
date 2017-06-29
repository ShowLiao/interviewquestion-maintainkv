package kvstore;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map.Entry;
import java.util.Stack;


/*
 * assume : a key only has a value
 */
public class maintainkv {

	private HashMap<Integer, ArrayList<String>> hMap= null;	//store datas
	private Stack<Integer> store = null;		//store action for rollback
	
	public maintainkv() {
		
		hMap = new HashMap<Integer, ArrayList<String>>();
		store = new Stack<>();
		
	}
	
	public void begin() {
		
	}
	
	public void add(int key, String val) {
		
		ArrayList<String> str = null;
		
		if (null == hMap.get(key)) {
			
			str = new ArrayList<String>();
			str.add(val);
			
			hMap.put(key, str);
			
		} else {
			
			str = hMap.get(key);
			str.add(val);
			
		}
		
		store.add(key);
		
		return;
		
	}
	
	public void rollback() {
		
		ArrayList<String> list = null;
		
//		remove each action in the stack with "key"	
		while (!store.isEmpty()) {
			
			int key = store.pop();
			list = hMap.get(key);
			
			if (list != null) {
			
				int size = list.size();
				String str = list.get(size-1);	//because array index start from 0
				
				if ( null == str) {
					
					hMap.remove(key);		//nothing in the map, so remove it
					
				} else {
					
					//put rest of val into an array and put back to the hMap
					ArrayList<String> arr = new ArrayList<String>();
					
					for (int i=0; i<size-1; i++)
						arr.add(list.get(i));
					
					if (0 == arr.size())
						hMap.remove(key);
					else
						hMap.put(key, arr);
					
				}
			}
			
		}
	}
	
	public void commit() {
		
		ArrayList<String> list = null;
		 
		while (!store.isEmpty()) {
			
			int key = store.pop();
			list = hMap.get(key);
			
			if (list != null) {
				
				int size = list.size();
				String str = list.get(size-1);	//only care last one action.
				
				//if delete is last action then remove the key from hMap
				if ( null == str) {
					
					hMap.remove(key);
					
				} else {
					
					//only keep last val in the list
					list.clear();
					list.add(str);
					
					hMap.put(key, list);
				}
			}
		}
	}
	
	public void delete(int key) {
		
		this.add(key, null);
		store.add(key);
		
	}
	
	public void printMap() {
		
		for (Entry<Integer, ArrayList<String>> entry : hMap.entrySet()) {
		
			System.out.println("Key:" + entry.getKey() + "-Value:" + java.util.Arrays.asList(entry.getValue()));
			
		}
	}
	
}
