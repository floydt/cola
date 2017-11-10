package cola;

import java.util.NoSuchElementException;

public interface COLAQuery<K extends Comparable<K>, V>  {

    /**
     * Searches the value corresponding to the key with a bottom up algorithm, i.e.,
     * first looking in the largest array, then the second largest, ...,
     * and lastly in the smallest array.
     *
     * @param key The key of the element
     * @throws NoSuchElementException if the key if key is not present
     * @return the value corresponding to the key;
     */
    V searchElementBottomUp(K key) throws NoSuchElementException;

    /**
     * Searches the value corresponding to the key with a top down algorithm, i.e.,
     * first looking in the smallest array, then the second smallest, ...,
     * and lastly in the largest array.
     *
     * @param key The key of the element
     * @throws NoSuchElementException if the key if key is not present
     * @return the value corresponding to the key;
     */
    V searchElementTopDown(K key) throws NoSuchElementException;
}