package task_1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Box <T extends Fruit> {
    public List<T> box;

    public Box() {
        box = new ArrayList<>();
    }

    public Box(ArrayList<T> box) {
        this.box = box;
    }

    public Box(T[] box) {
        this.box = new ArrayList(Arrays.asList(box));
    }

    public void addFruit(T fruit){
        box.add(fruit);
    }

    public float getWeight(){
        float sum = 0;
        for(T list: box) {
            sum += list.getWeight();
        }
        return sum;
    }

    public boolean compare(Box<?> anotherBox){
        return Math.abs(this.getWeight() - anotherBox.getWeight()) < 0.0001;
    }

    public void pourIntoBox(Box<T> anotherBox){
        anotherBox.box.addAll(box);
        box.clear();
    }

}
