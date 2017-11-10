package cola;

public class COLATest {

    public static void main(String[] args) {
        COLAImpl<Integer, String> cola = new COLAImpl<>();

        cola.insertElement(1, "Hello");
        //cola.toString();
        cola.insertElement(2, "World");
        //cola.toString();
        cola.insertElement(3, "how");
        cola.insertElement(4, "are");
        cola.insertElement(5, "you");
        cola.insertElement(6, "doing");
        cola.insertElement(7, "???");

        cola.toString();

        System.out.println(cola.searchElementBottomUp(2));
        System.out.println(cola.searchElementTopDown(2));
        System.out.println();
        System.out.println(cola.searchElementBottomUp(4));
        System.out.println(cola.searchElementTopDown(5));


    }
}
