package kdtree;

/**
 * Implementations of this interface should provide the ability for a kNN search method to evaluate if a KD node can be
 * returned for a query.
 */
public interface Checker<T>
{
    /**
     * Evaluates if a KD node is a valid node per conditions of the Checker.
     * 
     * @param v
     *          KD node
     * @return
     *          True if KD node is valid, false if the node should be rejected from the results.
     */
    boolean usable(T v);
}
