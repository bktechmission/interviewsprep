package kdtree;

/**
 * A Editor interface to edit a kd tree.
 */
public interface Editor<T>
{
	public static final String DELIMITER = "|";
    /**
     * Returns a modified KD node if a new node is used to modify a kd tree.
     * 
     * @param current
     *            A current KD node
     * @return The updated KD node
     * @throws KeyDuplicateException
     *             Exception thrown if the current node already is present in the kd tree.
     */
    T edit(T current) throws KeyDuplicateException;

    abstract class BaseEditor<T> implements Editor<T>
    {
        final T m_val;

        public BaseEditor(T val)
        {
            this.m_val = val;
        }

        public abstract T edit(T current) throws KeyDuplicateException;
    }

    class Inserter<T> extends BaseEditor<T>
    {
        public Inserter(T val)
        {
            super(val);
        }

        public T edit(T current) throws KeyDuplicateException
        {
            if (current == null)
            {
                return this.m_val;
            }
            throw new KeyDuplicateException();
        }
    }

    class OptionalInserter<T> extends BaseEditor<T>
    {
        public OptionalInserter(T val)
        {
            super(val);
        }

        public T edit(T current)
        {

            return (current == null) ? this.m_val : current;
        }
    }

    /**
     * Merger Editor allows for a new node to have a same key as an existing node in the kd tree.
     * Instead of throwing a {@link KeyDuplicateException}, this editor merges the values by concatenating
     * the values with a delimiter. 
     * 
     * @author <a href="mailto:vnamuduri@expedia.com">Veeru Namuduri</a>
     */
    class Merger<T> extends BaseEditor<T>
    {
        public Merger(T val)
        {
            super(val);
        }

        @SuppressWarnings("unchecked")
        public T edit(T current)
        {
            return (T) ((current == null) ? this.m_val : current + DELIMITER + this.m_val);
        }
    }
    
    class Replacer<T> extends BaseEditor<T>
    {
        public Replacer(T val)
        {
            super(val);
        }
        
        public T edit(T current)
        {
            return this.m_val;
        }
    }   
}