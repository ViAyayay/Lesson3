package task_1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WithArrayWorking<T> {

    public void swapElement(T[] array, int firstElement, int secondElement) {
        T storage = array[firstElement];
        array[firstElement] = array[secondElement];
        array[secondElement] = storage;
    }

    public List getList(T[] array) {
        return new ArrayList(Arrays.asList(array));
    }
}
