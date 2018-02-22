package kdtree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * KDTree is a class supporting KD-tree insertion, deletion, equality search, range search, and nearest neighbor(s)
 * using double-precision floating-point keys. Splitting dimension is chosen naively, by depth modulo K (number of
 * dimensions). Semantics are as follows: Two different keys containing identical numbers should retrieve the same value
 * from a given KDtree. Therefore keys are cloned when a node is inserted. As with HashMaps, values inserted into a
 * KD-tree are not cloned. Modifying a value between insertion and retrieval will therefore modify the value stored in
 * the tree.
 */
public class KDTree<T>
{
    // number of milliseconds
    private final long m_timeout;

    // K = number of dimensions
    private final int m_numDimensions;

    // root of KD-tree
    private KDNode<T> m_root;

    // count of nodes
    private int m_count;

    /**
     * Creates a KD-tree with specified number of dimensions.
     * 
     * @param k
     *            number of dimensions
     */
    public KDTree(int k)
    {
        this(k, 0);
    }

    public KDTree(int k, long timeout)
    {
        this.m_timeout = timeout;
        m_numDimensions = k;
        m_root = null;
    }

    public void insert(double[] key, T value) throws KeySizeException, KeyDuplicateException
    {
        this.edit(key, new Editor.Inserter<T>(value));
    }

    public void edit(double[] key, Editor<T> editor) throws KeySizeException, KeyDuplicateException
    {

        if (key.length != m_numDimensions)
        {
            throw new KeySizeException();
        }

        synchronized (this)
        {
            // the first insert has to be synchronized
            if (null == m_root)
            {
                m_root = KDNode.create(new HPoint(key), editor);
                m_count = m_root.isDeleted() ? 0 : 1;
                return;
            }
        }

        m_count += KDNode.edit(new HPoint(key), editor, m_root, 0, m_numDimensions);
    }

    public T search(double[] key) throws KeySizeException
    {

        if (key.length != m_numDimensions)
        {
            throw new KeySizeException();
        }

        KDNode<T> kd = KDNode.srch(new HPoint(key), m_root, m_numDimensions);

        return (kd == null ? null : kd.getValue());
    }

    public T delete(double[] key) throws KeySizeException, KeyMissingException, 
    	KeyDuplicateException
    {
        return delete(key, false);
    }
    
    public T delete (double[] key, T value) throws KeySizeException, KeyMissingException, 
    	KeyDuplicateException
    {
    	return delete(key, value, false);
    }

    public T delete(double[] key, boolean optional) throws KeySizeException, KeyMissingException, 
    	KeyDuplicateException
    {        
    	return delete(key, null, optional);
    }
    
    /**
     * Mark a node in the KDTree as deleted if no value is specified or if 
     * there is a node with a single value which matches the value specified.
     * If there is a node with multiple values (separated by a DELIMITER),
     * remove the value specified, it it exsits. In this case, the node is
     * not marked as deleted
     * 
     * @param key
     * 				The key to the node in the KDTree
     * @param value
     * 				The value to be deleted from the node
     * @param optional
     * 				Indicates if the delete is optional
     * @throws KeySizeException
     * @throws KeyMissingException
     * @throws KeyDuplicateException
     */
	@SuppressWarnings("unchecked")
	private T delete(double[] key, T value, boolean optional) throws KeySizeException, KeyMissingException, 
    	KeyDuplicateException
    {
    	/* Key size check */
    	if (key.length != m_numDimensions)
    	{
    		throw new KeySizeException();
    	}
    	
    	/* Search for the node containing the key */
    	KDNode<T> t = KDNode.srch(new HPoint(key), m_root, m_numDimensions);
    	
    	if (t == null)
    	{
    		if (!optional)
    		{
    			throw new KeyMissingException();
    		}
    		else
    		{
    			return null;
    		}
    	}
    	
    	/* Check if there are any existing values */
    	T existingValue = t.getValue();
    	
    	/* We assume that the multiple items are stored as strings
    	 * separated by a delimiter as defined by the editor
    	 */
    	String[] existingItems = ((String) existingValue).split("\\" + Editor.DELIMITER);
    	
    	/**
    	 * Two types of delete : 
    	 * 			1. Mark node as deleted if it contains only one value
    	 * 			2. Remove a value if the node contains multiple values
    	 * 			but do not mark the node as deleted
    	 */
    	if (value == null)
    	{
    		/* No value was specified, so mark a node
    		 * as deleted if it contains only one value
    		 */
    		if (existingItems.length > 1)
    		{
    			return null;
    		}
    		
    		if (KDNode.del(t))
    		{
    			m_count--;
    		}	
    		return existingValue;
    	}
    	else
    	{
    		/* Delete a given value */
    		if (existingItems.length <= 1)
    		{
    			/* If the node contains a single value, then
    			 * mark it is as deleted only if contains the
    			 * specified value 
    			 */
    			if (existingValue.equals(value))
    			{
    				if (KDNode.del(t))
    				{
    					m_count--;
    				}
    				return existingValue;
    			}
    			
    			return null;
    		}
    		
    		/* If the node contains multiple values, remove
    		 * the specified value if it exists. 
    		 */
    		
    		List<T> replacementItems 	= new ArrayList<T> ();
    		for (String existingItem : existingItems)
    		{
    			if (!existingItem.equals(value))
    			{
    				replacementItems.add((T) existingItem);
    			}
    		}
    		
    		/* If the value exists in the node the size of the
    		 * replacement items would be one less than the 
    		 * size of the existing items
    		 */
    		if (replacementItems.size() != (existingItems.length - 1))
    		{
    			return null;
    		}
    		
    		/* Create the replacement string */
    		String replacementString = "";
    		for (int i = 0; i < replacementItems.size() - 1; i++)
    		{
    			replacementString = replacementString + (String) replacementItems.get(i) + Editor.DELIMITER;
    		}
    		replacementString = replacementString + (String) replacementItems.get(replacementItems.size()-1);
    		
    		/* Try replacing the value in the node */
    		if (KDNode.replace(t, new Editor.Replacer<T> ((T) replacementString)))
    		{
    			return value;
    		}
    		else
    		{
    			return null;
    		}
    				
    	}
    	
    }

    public T nearest(double[] key) throws KeySizeException
    {

        List<T> nbrs = nearest(key, 1, null);
        return nbrs.get(0);
    }

    public List<T> nearest(double[] key, int n) throws KeySizeException, IllegalArgumentException
    {
        return nearest(key, n, null);
    }

    public List<T> nearestEuclidean(double[] key, double dist) throws KeySizeException
    {
        return nearestDistance(key, dist, new EuclideanDistance());
    }

    public List<T> nearestRadial(double[] key, double dist) throws KeySizeException
    {
        return nearestRadial(key, dist, null);
    }

    public List<T> nearestRadial(double[] key, double dist, Checker<T> checker)
            throws KeySizeException
    {
        return nearestDistance(key, dist, new RadialDistance(), checker);
    }

    public List<T> nearest(double[] key, int n, Checker<T> checker) throws KeySizeException,
            IllegalArgumentException
    {

        if (n <= 0)
        {
            return new LinkedList<T>();
        }

        NearestNeighborList<KDNode<T>> nnl = getnbrs(key, n, checker);

        n = nnl.getSize();
        Stack<T> nbrs = new Stack<T>();

        for (int i = 0; i < n; ++i)
        {
            KDNode<T> kd = nnl.removeHighest();
            nbrs.push(kd.getValue());
        }

        return nbrs;
    }

    public List<T> range(double[] lowk, double[] uppk) throws KeySizeException
    {
        return range(lowk, uppk, null);
    }

    public List<T> range(double[] lowk, double[] uppk, Checker<T> checker) throws KeySizeException
    {

        if (lowk.length != uppk.length)
        {
            throw new KeySizeException();
        }

        else if (lowk.length != m_numDimensions)
        {
            throw new KeySizeException();
        }

        else
        {
            List<KDNode<T>> found = new LinkedList<KDNode<T>>();
            KDNode.rsearch(new HPoint(lowk), new HPoint(uppk), m_root, 0, m_numDimensions, found,
                    checker);
            List<T> o = new LinkedList<T>();
            for (KDNode<T> node : found)
            {
                o.add(node.getValue());
            }
            return o;
        }
    }

    public int size()
    { /* added by MSL */
        return m_count;
    }

    public String toString()
    {
        return m_root.toString(0);
    }
    
    private NearestNeighborList<KDNode<T>> getnbrs(double[] key, int n, Checker<T> checker)
            throws KeySizeException
    {

        if (key.length != m_numDimensions)
        {
            throw new KeySizeException();
        }

        NearestNeighborList<KDNode<T>> nnl = new NearestNeighborList<KDNode<T>>(n);

        // initial call is with infinite hyper-rectangle and max distance
        HRect hr = HRect.infiniteHRect(key.length);
        double maxDistSqd = Double.MAX_VALUE;
        HPoint keyp = new HPoint(key);

        if (m_count > 0)
        {
            long timeout = (this.m_timeout > 0) ? (System.currentTimeMillis() + this.m_timeout) : 0;
            KDNode.nnbr(m_root, keyp, hr, maxDistSqd, 0, m_numDimensions, nnl, checker, timeout);
        }

        return nnl;

    }

    private List<T> nearestDistance(double[] key, double dist, DistanceMetric metric)
            throws KeySizeException
    {
        return nearestDistance(key, dist, metric, null);
    }

    private List<T> nearestDistance(double[] key, double dist, DistanceMetric metric,
            Checker<T> checker)
            throws KeySizeException
    {

        NearestNeighborList<KDNode<T>> nnl = getnbrs(key, m_count, checker);
        int n = nnl.getSize();
        Stack<T> nbrs = new Stack<T>();

        for (int i = 0; i < n; ++i)
        {
            KDNode<T> kd = nnl.removeHighest();
            // HPoint p = kd.k;
            if (metric.distance(kd.getKey().getCoordinates(), key) < dist)
            {
                nbrs.push(kd.getValue());
            }
        }

        return nbrs;
    }

}
