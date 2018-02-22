package tests;

class CustomHashMap<K,V>{
	private static final int SIZE = 1<<4;
	private Entry<K,V> table[] = new Entry[SIZE];
	transient volatile int modCount;
	transient int size;
	
	static class Entry<K,V>{
		final K key;
		final int hash;
		
		V value;
		
		Entry<K,V> next;
		
		Entry(K key,V val, int hash, Entry<K,V> n)
		{
			this.key = key;
			this.value = val;
			this.hash = hash;
			this.next = n;
		}
		K getKey()
		{
			return key;
		}
		V getValue()
		{
			return value;
		}
		
		int getHash()
		{
			return hash;
		}
	}
	
	private int getSupplementalHash(int h)
	{
		// mingle higher order bits with lower ones
		h^=(h>>>20)^(h>>>12);
		return h^(h>>>7)^(h>>>4);
		
	}
	
	public int size() {
        return size;
    }
	
	
	private int bucket(int hash)
	{
		return hash&(table.length-1);
	}
	
	public boolean isEmpty() {
        return size == 0;
    }
	
	public V put(K key, V value)
	{	
		int userHash = key.hashCode();
		int hash = getSupplementalHash(userHash);
		// get the bucket for this hash
		int bucket = bucket(hash);
		Entry<K,V> entry = table[bucket];
		
		while(entry!=null)
		{
			if(entry.hash==hash && (entry.key==key || entry.key.equals(key)))
			{
				V oldVal = entry.value;
				entry.value = value;
				return oldVal;
			}
			entry=entry.next;
		}
		modCount++;
		addEntry(key,value,hash,bucket);
		return null;
	}
	
	public V get(Object key)
	{
		int hash = key==null?0:getSupplementalHash(key.hashCode());
		int bucket = bucket(hash);
		Entry<K,V> entry = table[bucket];
		while(entry!=null)
		{
			if(entry.hash==hash && (entry.key==key || entry.key.equals(key)))
			{
				return entry.value;
			}
			entry = entry.next;
		}
		return null;
	}
	
	public Entry<K,V> getEntry(K key)
	{
		int hash = key==null?0:getSupplementalHash(key.hashCode());
		int bucket = bucket(hash);
		Entry<K,V> entry = table[bucket];
		
		while(entry!=null)
		{
			if(entry.hash==hash && (entry.key==key || entry.key.equals(key)))
			{
				return entry;
			}
			entry = entry.next;
		}
		return null;
	}
	
	public boolean containsKey(Object key)
	{
		return get(key)!=null;
	}
	
	public boolean containsValue(Object value)
	{
		Entry<K,V>[] tab = table;
		for(int i=0;i<tab.length;i++)
		{
			for(Entry<K,V> e = tab[i];e!=null;e=e.next)
			{
				if(value.equals(e.value))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	// pre-pend at start always
	private void addEntry(K key, V value, int hash, int bucket)
	{
		Entry<K,V> e = table[bucket];
		table[bucket] = new Entry<K,V>(key, value, hash, e);
		size++;
		
		
	}

}
