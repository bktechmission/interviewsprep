package kdtree;
/**
 * Key-size mismatch exception
 */
public class KeyMissingException extends KDException
{

    private static final long serialVersionUID = 2190303227865724082L;

    public KeyMissingException()
    {
        super("Key not found");
    }
}
