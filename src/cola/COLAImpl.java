package cola;

import jdbm.helper.Tuple;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class COLAImpl<k extends Comparable<k>,v> implements COLAInsert<k,v>, COLAQuery<k,v>{
    private ArrayList<ArrayList<Tuple<k,v>>> arrays;

    public COLAImpl(){
        this.arrays = new ArrayList<>();
    }

    /**
     * Inserts a new element into this COLA (main memory) data structure
     *
     * @param key   The key of the element
     * @param value The value of the element
     */
    @Override
    public void insertElement(k key, v value) {
        Tuple<k,v> tuple = new Tuple<>(key,value);
        // TODO We might have to check for already existing keys I guess...
        // Instantiate array
        ArrayList<Tuple<k,v>> newArr = new ArrayList<>(1);
        // Insert element
        newArr.add(0, tuple);
        arrays.add(0, newArr);
        merge();
    }
    

    /**
     * Checks wether Arrays with same length exist, if so, merges them and checks again (cascading)
     */
    private void merge(){
        while (arrays.size() > 1 && arrays.get(0).size() == arrays.get(1).size()){
            arrays.get(1).addAll(arrays.get(0));
            // TODO use last merge sort step to speed up from (n*log(n)) to (log(n))!
            arrays.get(1).sort(Comparator.comparing(Tuple::getKey));
            arrays.remove(0);
        }
    }
    

    /**
     * Searches the value corresponding to the key with a bottom up algorithm, i.e.,
     * first looking in the largest array, then the second largest, ...,
     * and lastly in the smallest array.
     *
     * @param key The key of the element
     * @return the value corresponding to the key;
     * @throws NoSuchElementException if the key if key is not present
     */
    @Override
    public v searchElementBottomUp(k key) throws NoSuchElementException {
        int j;
        for (int i = arrays.size()-1; i >= 0; i--) {
            ArrayList<Tuple<k,v>> arrayList;
            arrayList = arrays.get(i);
            if ((j = binSearch(key, arrayList)) != -1){
                return arrayList.get(j).getValue();
            }
        }
        throw new NoSuchElementException("Can't find element with key = " + key);
    }

    /**
     * Searches the value corresponding to the key with a top down algorithm, i.e.,
     * first looking in the smallest array, then the second smallest, ...,
     * and lastly in the largest array.
     *
     * @param key The key of the element
     * @return the value corresponding to the key;
     * @throws NoSuchElementException if the key if key is not present
     */
    @Override
    public v searchElementTopDown(k key) throws NoSuchElementException {
        int i;
        for (ArrayList<Tuple<k,v>> arrayList : arrays){
            if ((i = binSearch(key, arrayList)) != -1){
                return arrayList.get(i).getValue();
            }

        }
        throw new NoSuchElementException("Can't find element with key = " + key);
    }

    /**
     * Simple binary search for a tuple in an array given a key
     * @param key The key of the element we are looking for
     * @param list The list in which we hope to find the key
     * @return Index of the key in the given array or -1 if the search failed
     * TODO use Java.Collections Binsearch
     */
    private int binSearch(k key, ArrayList<Tuple<k,v>> list){
        int l = 0;
        int r = list.size()-1;
        int middle;
        while (!(l > r)){
            middle = (int) Math.floor((l+r)/2);
            int val = list.get(middle).getKey().compareTo(key);
            if (val < 0){
                l = middle + 1;
            }
            if (val > 0){
                r = middle - 1;
            }
            if (list.get(middle).getKey().equals(key)){
                return middle;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        int i = 1;
        for (ArrayList<Tuple<k,v>> subList: arrays) {
            System.out.print("Ebene: " + i++ + "\n");
            for (Tuple<k,v> tuple: subList) {
                System.out.print("(" + tuple.getKey() +", " + tuple.getValue() +") ");
            }
            System.out.println();
        }
        return super.toString();
    }
}
