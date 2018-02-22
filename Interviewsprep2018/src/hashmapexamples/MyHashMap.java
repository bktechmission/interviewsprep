package hashmapexamples;

public class MyHashMap<K, V> 
{
	// for simplicity size is taken as 2^4 The default initial capacity - MUST be a power of two.
    private final int SIZE = 16;
    private Entry[]  table = new Entry[SIZE];
 
    /**
     * User defined simple Map data structure
     * with key and value.
     * This is also used as linked list in case multiple
     * key-value pairs lead to the same bucket with same
     * hashcodes and different keys (collisions) using
     * pointer 'next'.
     */
    static class Entry<K, V> 
    {
        final K key;
        V value;
        Entry<K, V>  next;
 
        Entry(K k, V v) 
        {
            key = k;
            value = v;
        }
 
        public V getValue() 
        {
            return value;
        }
 
        public void setValue(V value) 
        {
            this.value = value;
        }
 
        public K getKey() 
        {
            return key;
        }
    }
 
    /**
     * Returns the entry associated with the specified key in the
     * HashMap.  Returns null if the HashMap contains no mapping
     * for the key.
     */

    public Entry get(K key) 
    {
        int indexHash = indexFor(hash(key.hashCode()),SIZE);

        Entry e = table[indexHash];
 
        // if bucket is found then traverse through the linked list and
        // see if element is present
        while(e != null) {
            if(e.key.equals(key)) {
                return e;
            }
            e = e.next;
        }
        return null;
    }
 
    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old
     * value is replaced.
     */

    public void put(K key, V val) 
    {
        int indexHash = indexFor(hash(key.hashCode()),SIZE);
        
        Entry e = table[indexHash];
        
        if(e != null) {
            // it means we are trying to insert duplicate
            // key-value pair, hence overwrite the current
            // pair with the old pair
            if(e.key.equals(key)) {
                e.value = val;
            } else {
                // traverse to the end of the list and insert new element
                // in the same bucket
                while(e.next != null) {
                    e = e.next;
                }
                Entry entryInOldBucket = new Entry(key, val);
                e.next = entryInOldBucket;
            }
        } else {
            // new element in the map, hence creating new bucket
            Entry entryInNewBucket = new Entry(key, val);
            table[indexHash] = entryInNewBucket;
        }
    }
 

    int hash(int h) 
    {
    	// This function ensures that hashCodes that differ only by
    	// constant multiples at each bit position have a bounded
    	// number of collisions (approximately 8 at default load factor). 20,12,7,4
    	h ^= (h >>> 20) ^ (h >>> 12);
    	return h ^ (h >>> 7) ^ (h >>> 4);
    }
 
    /**
     * Returns index for hash code h.
     */

    int indexFor(int h, int length) 
    {
    	return h & (length-1); // same as h%length if length is power of 2
    }
    
    
    public static void main(String[] args)
    {
    	MyHashMap<String, Integer> map = new MyHashMap<String, Integer>();
    	map.put("1",1);
    	map.put("2",2);
    	map.put("3",3);
    	map.put("1",6);
    	
    	System.out.println(map.get("1").value);
    	
    }
}
