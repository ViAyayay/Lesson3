package task_1;

public class Orange extends Fruit {
    public final float weight;

    public Orange() {
        weight = 1.5f;
    }

    @Override
    public float getWeight() {
        return weight;
    }
}
