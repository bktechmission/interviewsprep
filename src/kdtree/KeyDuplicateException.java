package kdtree;

/**
 * KeyDuplicateException is thrown when the KdTree.insert method is invoked on a key already in the KdTree.
 */
public class KeyDuplicateException extends KDException
{
        
    private static final long serialVersionUID = 2168214179860973855L;

    protected KeyDuplicateException()
    {
        super("Key already in tree");
    }

}
