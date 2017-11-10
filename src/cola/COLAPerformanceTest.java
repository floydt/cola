package cola;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class COLAPerformanceTest {

    public static void main(String[] args) {
        // Setup
        COLAImpl<Integer, String> cola = new COLAImpl<>();
        HashMap<Integer, String> hashMap = new HashMap<>();
        String val = "Hallo";
        int howMany = 5000000;

        System.out.println("Number of (k,v) pairs " + howMany);
        System.out.println("Inserting into hashmap and cola");

        /**
         * Inserting 5.000.000 elements into the HashMap takes approx. 2049 ms
         * whereas it takes approx 5814 ms for the COLA
         *
         * The COLA however has a lot of merging operations which consist of:
         * Creating new arrays, copying values from one array to another and sorting these newly merged arrays.
         * These steps make it a lot slower than the HashMap.
         */
        long t1 = System.nanoTime();
        for (int i = 0; i < howMany; i++) {
            hashMap.put(i, val);
        }
        long t2 = System.nanoTime();
        for (int i = 0; i < howMany; i++) {
            cola.insertElement(i, val);
        }
        long t3 = System.nanoTime();
        System.nanoTime();

        System.out.println();
        System.out.println("Results: ");
        System.out.println("Inserting " + howMany + " elements into a HashMap took " + (t2-t1)/1000000 +" ms");
        System.out.println("Inserting " + howMany + " elements into a COLA took " + (t3-t2)/1000000 +" ms");

        ExecutorService executor = Executors.newFixedThreadPool(3);

        /**
         * Lookup Times for 5.000.000 elements each:
         * HashMap: 759 ms
         * COLA Bottom Up: 1894 ms
         * COLA Top Down: 5991 ms
         *
         * COLA Bottom up is faster because the majority of results will be in last few subarrays
         * so that the probability of finding a result in the first few iterations is much higher
         * than if we search through all subarrays starting with the first one.
         *
         */
        Runnable task1 = () -> {
            System.out.println("HM test started!");
            long g1 = System.nanoTime();
            for (int i = 0; i < howMany; i++) {
                hashMap.get(i);
            }
            long g2 = System.nanoTime();
            System.out.println("HM finished after " + (g2-g1)/1000000 + " ms");
        };
        Runnable task2 = () -> {
            System.out.println("COLA TD test started!");
            long g3 = System.nanoTime();
            for (int i = 0; i < howMany; i++) {
                cola.searchElementTopDown(i);
            }
            long g4 = System.nanoTime();
            System.out.println("COLA TD finished after " + (g4-g3)/1000000 + " ms");
        };
        Runnable task3 = () -> {
            System.out.println("COLA BU test started!");
            long g5 = System.nanoTime();
            for (int i = 0; i < howMany; i++) {
                cola.searchElementBottomUp(i);
            }
            long g6 = System.nanoTime();
            System.out.println("COLA BU finished after " + (g6-g5)/1000000 + " ms");
        };

        executor.execute(task1);
        executor.execute(task2);
        executor.execute(task3);

        executor.shutdown();
    }

}
