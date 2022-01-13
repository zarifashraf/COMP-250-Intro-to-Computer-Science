package FinalProject_Template;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;


public class MyHashTable<K,V> implements Iterable<HashPair<K,V>>{
  
	private int numEntries;
	private int numBuckets; 
    private static final double MAX_LOAD_FACTOR = 0.75;
    private ArrayList<LinkedList<HashPair<K,V>>> buckets; 
    
    
    public MyHashTable(int initialCapacity) {

        this.numEntries = 0;
    	this.numBuckets = initialCapacity;
    	
    	if (this.numBuckets <= 0) {
    		this.numBuckets = 1;
    	}
    	
    	buckets = new ArrayList<>(this.numBuckets);
    	
    	for (int i = 0; i < numBuckets; i++) {
    		buckets.add(new LinkedList<HashPair<K,V>>());
    	}
    }
    
    public int size() {
        return this.numEntries;
    }
    
    public boolean isEmpty() {
        return this.numEntries == 0;
    }
    
    public int numBuckets() {
        return this.numBuckets;
    }
    
    
    public ArrayList<LinkedList<HashPair<K,V> > > getBuckets(){
        return this.buckets;
    }
    

    public int hashFunction(K key) {
        int hashValue = Math.abs(key.hashCode())%this.numBuckets;
        return hashValue;
    }
    
    public V put(K key, V value) {
        
    	int hashidentifier;
    	hashidentifier = this.hashFunction(key); 
        
    	for (HashPair<K, V> addit: this.buckets.get(hashidentifier)) {

    		if (addit.getKey().equals(key)) {
    			
    			V returnvalue;
    			
    			returnvalue = addit.getValue();
    			addit.setValue(value);
    			
    			if ((double)(size()/numBuckets()) > MAX_LOAD_FACTOR) {
    				this.rehash();
    			}
    	   
    			return returnvalue;
    		}
        }	

        buckets.get(hashidentifier).add(new HashPair<>(key, value));
    	this.numEntries = this.numEntries + 1;
    	
    	if ((double)(size()/numBuckets()) > MAX_LOAD_FACTOR) {
    		this.rehash();
    		}        
    	return null;
    }
    
    
    public V get(K key) {
        
        for (HashPair<K, V> getit: buckets.get(hashFunction(key))) {
    		
    		if (getit.getKey().equals(key)) {
    			
    			return getit.getValue();
    		}	
    	}
    	
    	return null;
    }
    
    
    public V remove(K key) {
        int hashidentifier;
        hashidentifier = this.hashFunction(key);
        
        for (int z = 0; z < buckets.get(hashidentifier).size(); z++) {
    	
    		if (buckets.get(hashidentifier).get(z).getKey().equals(key)) {
    			
    			V tmp;
    			tmp = buckets.get(hashidentifier).get(z).getValue();
    			buckets.get(hashidentifier).remove(z);
                this.numEntries = this.numEntries - 1;
                
    			return tmp;	
    		}	
    	}
    	
    	return null;
    }
    
    
    public void rehash() {

        this.numBuckets = this.numBuckets * 2;
    	numEntries = 0;
    	
    	ArrayList<LinkedList<HashPair<K,V>>> store = buckets;
    	
    	this.buckets = new ArrayList<LinkedList<HashPair<K,V>>>(this.numBuckets);
		for (int i = 0; i < this.numBuckets; i++) { 
			this.buckets.add(new LinkedList<HashPair<K,V>>());

    	}
    	
    	for (LinkedList<HashPair<K, V>> linkin: store) {
    		
    		for (HashPair<K, V> hashin: linkin) {
    			
    			put(hashin.getKey(), hashin.getValue());
    		}
    	}
    }
    
    
    public ArrayList<K> keys() {

        ArrayList<K> keysArrayList= new ArrayList<>();
    	
    	for(int z = 0; z < buckets.size(); z++) {
    		
    		for (HashPair<K, V> keyshash: buckets.get(z)) {
    			
    			keysArrayList.add(keyshash.getKey());
    		}
    	}
    	
    	return keysArrayList;
    }
    
    
    public ArrayList<V> values() {
		
		ArrayList<V> valuesArrayList = new ArrayList<V>(this.numEntries);
		
		MyHashTable<V, K> valuesTable = new MyHashTable<V, K>(this.numBuckets);
		
		for (K thekey : this.keys()) { 
			valuesTable.put(this.get(thekey), thekey); 
		}
		
		
		ArrayList<LinkedList<HashPair<V,K>>> bucketArray = valuesTable.getBuckets(); 
		LinkedList<HashPair<V,K>> thebucket;
		
		
		for (V value : valuesTable.keys()) { 
	
			thebucket = bucketArray.get(valuesTable.hashFunction(value));
			

			if (thebucket.size() == 0) {
				continue;
			}
			
			else if (thebucket.size() == 1) {
				valuesArrayList.add(value);
				continue;
			}

			else {
				valuesArrayList.add(value);
				
				for (int z = 0; z < thebucket.size(); z++) { 
					if (thebucket.get(z).getKey().equals(value)) {
						thebucket.remove(z); 
						z = z-1;
					}
				}
			}
		}
	
		return valuesArrayList;
	}

    
    public static <K, V extends Comparable<V>> ArrayList<K> slowSort (MyHashTable<K, V> results) {
        ArrayList<K> sortedResults = new ArrayList<>();
        for (HashPair<K, V> entry : results) {
			V element = entry.getValue();
			K toAdd = entry.getKey();
			int i = sortedResults.size() - 1;
			V toCompare = null;
        	while (i >= 0) {
        		toCompare = results.get(sortedResults.get(i));
        		if (element.compareTo(toCompare) <= 0 )
        			break;
        		i--;
        	}
        	sortedResults.add(i+1, toAdd);
        }
        return sortedResults;
    }
    
    
    public static <K, V extends Comparable<V>> ArrayList<K> fastSort(MyHashTable<K, V> results) {
		
    	ArrayList<K> sortedResults = results.keys();
		
		if (sortedResults.size() > 1) {
			asapsort(results, sortedResults, 0, sortedResults.size() - 1);
		}
		return sortedResults;
	}
	
	
	private static <K, V extends Comparable<V>> void asapsort(MyHashTable<K, V> thehashtable, ArrayList<K> sortedResults, int leftindex, int rightindex) {
	
		if (leftindex >= rightindex)
			return;
		else {
		
			V thepivot;
			int divider;
			
			thepivot = thehashtable.get(sortedResults.get(rightindex));
			divider = leftindex - 1;
			
			for (int z = leftindex; z < rightindex; z++) {
				
				if (thehashtable.get(sortedResults.get(z)).compareTo(thepivot) > 0) {
					divider = divider + 1;
					
					K tmp;
					tmp = sortedResults.get(divider);
					sortedResults.set(divider, sortedResults.get(z));
					sortedResults.set(z, tmp);
				}
			}
			
			K tmp;
			tmp = sortedResults.get(rightindex);
			sortedResults.set(rightindex, sortedResults.get(divider + 1));
			sortedResults.set(divider + 1, tmp);
			
			int pivotpoint;
			pivotpoint = divider + 1;
			asapsort(thehashtable, sortedResults, leftindex, divider);
			asapsort(thehashtable, sortedResults, (divider + 2), rightindex);
		}
	}

    
	@Override
	public MyHashIterator iterator() {
        return new MyHashIterator();
    }   
    
    private class MyHashIterator implements Iterator<HashPair<K,V>> {
        
        ArrayList<HashPair<K, V>> iteratorlist  = new ArrayList<>();
    	
    	Iterator iterator;
    	
        private MyHashIterator() {
            
            for(LinkedList<HashPair<K, V>> linker: buckets) {
        		
        		for (HashPair<K, V> hasher: linker) {
        			
        			iteratorlist.add(hasher);
        		}
        	}
        	
        	iterator = iteratorlist.iterator();
        }
        
        @Override
        public HashPair<K,V> next() {
            
            return (HashPair<K, V>) iterator.next();
        }
        
       
        @Override
        public boolean hasNext() {
            
            return iterator.hasNext();
        }
        
      }
}