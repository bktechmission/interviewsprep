package kdtree;

import java.util.List;

/**
 * KDNode class.
 */
class KDNode<T>
{
	
    /* key in KD Tree */
    private final HPoint m_key;
    /* value in KD Tree */
    private T m_value;
    private KDNode<T> m_left, m_right;
    private boolean m_deleted;

    private KDNode(HPoint key, T val)
    {

        m_key = key;
        m_value = val;
        m_left = null;
        m_right = null;
        m_deleted = false;
    }

    // Method ins translated from 352.ins.c of Gonnet & Baeza-Yates
    protected static <T> int edit(HPoint key, Editor<T> editor, KDNode<T> t, int lev, int k)
            throws KeyDuplicateException
    {
        KDNode<T> nextNode = null;
        int nextLev = (lev + 1) % k;
        synchronized (t)
        {
            if (key.equals(t.m_key))
            {
                boolean wasDeleted = t.m_deleted;
                t.m_value = editor.edit(t.m_deleted ? null : t.m_value);
                t.m_deleted = (t.m_value == null);

                if (t.m_deleted == wasDeleted)
                {
                    return 0;
                }
                /**  
                else if (wasDeleted)
                {
                    return -1;
                }
                **/
                return 1;
            }
            else if (key.getCoordinates()[lev] > t.m_key.getCoordinates()[lev])
            {
                nextNode = t.m_right;
                if (nextNode == null)
                {
                    t.m_right = create(key, editor);
                    return t.m_right.m_deleted ? 0 : 1;
                }
            }
            else
            {
                nextNode = t.m_left;
                if (nextNode == null)
                {
                    t.m_left = create(key, editor);
                    return t.m_left.m_deleted ? 0 : 1;
                }
            }
        }

        return edit(key, editor, nextNode, nextLev, k);
    }

    protected static <T> KDNode<T> create(HPoint key, Editor<T> editor)
            throws KeyDuplicateException
    {
        KDNode<T> t = new KDNode<T>(key, editor.edit(null));
        if (t.m_value == null)
        {
            t.m_deleted = true;
        }
        return t;
    }

    protected static <T> boolean del(KDNode<T> t)
    {
        synchronized (t)
        {
            if (!t.m_deleted)
            {
                t.m_deleted = true;
                return true;
            }
        }
        return false;
    }
    
    protected static <T> boolean replace(KDNode<T> t, Editor<T> editor)
    	throws KeyDuplicateException
    {
    	synchronized (t)
    	{
    		if (t.m_deleted || (t.m_value == null))
    		{
    			return false;
    		}
    		
    		t.m_value = editor.edit(t.m_value);
    		return true;			
    	}
    }

    protected static <T> KDNode<T> srch(HPoint key, KDNode<T> t, int k)
    {

        for (int lev = 0; t != null; lev = (lev + 1) % k)
        {

            if (!t.m_deleted && key.equals(t.m_key))
            {
                return t;
            }
            else if (key.getCoordinates()[lev] > t.m_key.getCoordinates()[lev])
            {
                t = t.m_right;
            }
            else
            {
                t = t.m_left;
            }
        }

        return null;
    }

    protected static <T> void rsearch(HPoint lowk, HPoint uppk, KDNode<T> t, int lev, int k,
            List<KDNode<T>> v, Checker<T> checker)
    {

        if (t == null)
        {
            return;
        }
        if (lowk.getCoordinates()[lev] <= t.m_key.getCoordinates()[lev])
        {
            rsearch(lowk, uppk, t.m_left, (lev + 1) % k, k, v, checker);
        }
        if (!t.m_deleted && ((checker == null) || checker.usable(t.m_value)))
        {
            int j = 0;
            while (j < k && lowk.getCoordinates()[j] <= t.m_key.getCoordinates()[j]
                    && uppk.getCoordinates()[j] >= t.m_key.getCoordinates()[j])
            {
                j++;
            }
            if (j == k)
            {
                v.add(t);
            }
        }
        if (uppk.getCoordinates()[lev] > t.m_key.getCoordinates()[lev])
        {
            rsearch(lowk, uppk, t.m_right, (lev + 1) % k, k, v, checker);
        }
    }

    protected static <T> void nnbr(KDNode<T> kd, HPoint target, HRect hr, double maxDistSqd,
            int lev, int k, NearestNeighborList<KDNode<T>> nnl, Checker<T> checker, long timeout)
    {

        // 1. if kd is empty then set dist-sqd to infinity and exit.
        if (kd == null)
        {
            return;
        }

        if ((timeout > 0) && (timeout < System.currentTimeMillis()))
        {
            return;
        }
        // 2. s := split field of kd
        int s = lev % k;

        // 3. pivot := dom-elt field of kd
        HPoint pivot = kd.m_key;
        double pivotToTarget = HPoint.sqrdist(pivot, target);

        // 4. Cut hr into to sub-hyperrectangles left-hr and right-hr.
        // The cut plane is through pivot and perpendicular to the s
        // dimension.
        HRect leftHRect = hr; // optimize by not cloning
        HRect rightHRect = HRect.getHRect(hr);
        leftHRect.getMax().getCoordinates()[s] = pivot.getCoordinates()[s];
        rightHRect.getMin().getCoordinates()[s] = pivot.getCoordinates()[s];

        // 5. target-in-left := target_s <= pivot_s
        boolean targetInLeft = target.getCoordinates()[s] < pivot.getCoordinates()[s];

        KDNode<T> nearerKDNode;
        HRect nearerHRect;
        KDNode<T> furtherKDNode;
        HRect furtherHRect;

        // 6. if target-in-left then
        // 6.1. nearer-kd := left field of kd and nearer-hr := left-hr
        // 6.2. further-kd := right field of kd and further-hr := right-hr
        if (targetInLeft)
        {
            nearerKDNode = kd.m_left;
            nearerHRect = leftHRect;
            furtherKDNode = kd.m_right;
            furtherHRect = rightHRect;
        }
        //
        // 7. if not target-in-left then
        // 7.1. nearer-kd := right field of kd and nearer-hr := right-hr
        // 7.2. further-kd := left field of kd and further-hr := left-hr
        else
        {
            nearerKDNode = kd.m_right;
            nearerHRect = rightHRect;
            furtherKDNode = kd.m_left;
            furtherHRect = leftHRect;
        }

        // 8. Recursively call Nearest Neighbor with paramters
        // (nearer-kd, target, nearer-hr, max-dist-sqd), storing the
        // results in nearest and dist-sqd
        nnbr(nearerKDNode, target, nearerHRect, maxDistSqd, lev + 1, k, nnl, checker, timeout);

        // KDNode<T> nearest = nnl.getHighest();
        double distSqd;

        if (!nnl.isCapacityReached())
        {
            distSqd = Double.MAX_VALUE;
        }
        else
        {
            distSqd = nnl.getMaxPriority();
        }

        // 9. max-dist-sqd := minimum of max-dist-sqd and dist-sqd
        maxDistSqd = Math.min(maxDistSqd, distSqd);

        // 10. A nearer point could only lie in further-kd if there were some
        // part of further-hr within distance max-dist-sqd of
        // target.
        HPoint closest = furtherHRect.closest(target);
        if (HPoint.sqrdist(closest, target) < maxDistSqd)
        {

            // 10.1 if (pivot-target)^2 < dist-sqd then
            if (pivotToTarget < distSqd)
            {

                // 10.1.1 nearest := (pivot, range-elt field of kd)
                // nearest = kd;

                // 10.1.2 dist-sqd = (pivot-target)^2
                distSqd = pivotToTarget;

                // add to nnl
                if (!kd.m_deleted && ((checker == null) || checker.usable(kd.m_value)))
                {
                    nnl.insert(kd, distSqd);
                }

                // 10.1.3 max-dist-sqd = dist-sqd
                // max_dist_sqd = dist_sqd;
                if (nnl.isCapacityReached())
                {
                    maxDistSqd = nnl.getMaxPriority();
                }
                else
                {
                    maxDistSqd = Double.MAX_VALUE;
                }
            }

            // 10.2 Recursively call Nearest Neighbor with parameters
            // (further-kd, target, further-hr, max-dist_sqd),
            // storing results in temp-nearest and temp-dist-sqd
            nnbr(furtherKDNode, target, furtherHRect, maxDistSqd, lev + 1, k, nnl, checker, timeout);
        }
    }

    protected String toString(int depth)
    {
        String s = m_key + "  " + m_value + (m_deleted ? "*" : "");
        if (m_left != null)
        {
            s = s + "\n" + pad(depth) + "L " + m_left.toString(depth + 1);
        }
        if (m_right != null)
        {
            s = s + "\n" + pad(depth) + "R " + m_right.toString(depth + 1);
        }
        return s;
    }

    private static String pad(int n)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; ++i)
        {
            sb.append(' ');
        }

        return sb.toString();
    }

    public T getValue()
    {
        return m_value;
    }

    public void setValue(T value)
    {
        m_value = value;
    }

    public KDNode<T> getLeft()
    {
        return m_left;
    }

    public void setLeft(KDNode<T> left)
    {
        m_left = left;
    }

    public KDNode<T> getRight()
    {
        return m_right;
    }

    public void setRight(KDNode<T> right)
    {
        m_right = right;
    }

    public boolean isDeleted()
    {
        return m_deleted;
    }

    public void setDeleted(boolean deleted)
    {
        m_deleted = deleted;
    }

    public HPoint getKey()
    {
        return m_key;
    }

    // private static void hrcopy(HRect hr_src, HRect hr_dst)
    // {
    // hpcopy(hr_src.getMin(), hr_dst.getMin());
    // hpcopy(hr_src.getMax(), hr_dst.getMax());
    // }

    // private static void hpcopy(HPoint hp_src, HPoint hp_dst)
    // {
    // for (int i = 0; i < hp_dst.getCoordinates().length; ++i)
    // {
    // hp_dst.getCoordinates()[i] = hp_src.getCoordinates()[i];
    // }
    // }
}
