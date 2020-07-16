package task_1;

public class Apple extends Fruit {
    public final float weight;

    public Apple() {
        weight = 1.0f;
    }

    @Override
    public float getWeight() {
        return weight;
    }
}
