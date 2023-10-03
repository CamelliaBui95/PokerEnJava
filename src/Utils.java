public class Utils {
    public static int indexOf(int[] array, int instance) {
        int counter = 0;
        boolean hasFound = array[counter] == instance;

        while(!hasFound && ++counter < array.length) {
            if(array[counter] == instance)
                hasFound = true;
        }

        return hasFound ? counter : -1;
    }

    public static int indexOf(String[] array, String instance) {
        int counter = 0;
        boolean hasFound = array[counter].equals(instance);

        while(!hasFound && ++counter < array.length) {
            if(array[counter].equals(instance))
                hasFound = true;
        }

        return hasFound ? counter : -1;
    }
}
