package kdtree;

import java.util.PriorityQueue;

/**
 * A NearestNeighborList class.
 */
class NearestNeighborList<T>
{

    static class NeighborEntry<T> implements Comparable<NeighborEntry<T>>
    {
        final T m_data;
        final double m_value;

        public NeighborEntry(final T data, final double value)
        {
            this.m_data = data;
            this.m_value = value;
        }

        public int compareTo(NeighborEntry<T> t)
        {
            // note that the positions are reversed!
            return Double.compare(t.m_value, this.m_value);
        }
    };

    private PriorityQueue<NeighborEntry<T>> m_queue;
    private int m_capacity = 0;

    /**
     * constructor
     * 
     * @param capacity
     */
    public NearestNeighborList(int capacity)
    {
        m_capacity = capacity;
        m_queue = new java.util.PriorityQueue<NeighborEntry<T>>(m_capacity);
    }

    public double getMaxPriority()
    {
        NeighborEntry<T> p = m_queue.peek();
        return (p == null) ? Double.POSITIVE_INFINITY : p.m_value;
    }

    public boolean insert(T object, double priority)
    {
        if (isCapacityReached())
        {
            if (priority > getMaxPriority())
            {
                // do not insert - all elements in queue have lower priority
                return false;
            }
            m_queue.add(new NeighborEntry<T>(object, priority));
            // remove object with highest priority
            m_queue.poll();
        }
        else
        {
            m_queue.add(new NeighborEntry<T>(object, priority));
        }
        return true;
    }

    public boolean isCapacityReached()
    {
        return m_queue.size() >= m_capacity;
    }

    public T getHighest()
    {
        NeighborEntry<T> p = m_queue.peek();
        return (p == null) ? null : p.m_data;
    }

    public boolean isEmpty()
    {
        return m_queue.size() == 0;
    }

    public int getSize()
    {
        return m_queue.size();
    }

    public T removeHighest()
    {
        // remove object with highest priority
        NeighborEntry<T> p = m_queue.poll();
        return (p == null) ? null : p.m_data;
    }
}
