package cola;

public interface COLAInsert<K extends Comparable<K>, V>  {

    /**
     * Inserts a new element into this COLA (main memory) data structure
     *
     * @param key The key of the element
     * @param value The value of the element
     */
    void insertElement(K key, V value);
}
