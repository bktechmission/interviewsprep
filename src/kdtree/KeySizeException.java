package kdtree;
/**
 * KeySizeException is thrown when a KdTree method is invoked on a key whose size (array length) mismatches the one used
 * in the that KdTree's constructor
 */
public class KeySizeException extends KDException
{
    private static final long serialVersionUID = -6964877309246436991L;

    protected KeySizeException()
    {
        super("Key size mismatch");
    }

}
