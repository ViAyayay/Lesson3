package task_1;

import java.util.List;

/**
 1. Написать метод, который меняет два элемента массива местами.(массив может быть любого ссылочного типа);
 2. Написать метод, который преобразует массив в ArrayList;
 3. Большая задача:
    a. Есть классы Fruit -> Apple, Orange;(больше фруктов не надо)
    b. Класс Box в который можно складывать фрукты, коробки условно сортируются по типу фрукта, поэтому в одну коробку нельзя сложить и яблоки, и апельсины;
    c. Для хранения фруктов внутри коробки можете использовать ArrayList;
    d. Сделать метод getWeight() который высчитывает вес коробки, зная количество фруктов и вес одного фрукта(вес яблока - 1.0f, апельсина - 1.5f, не важно в каких это единицах);
    e. Внутри класса коробка сделать метод compare, который позволяет сравнить текущую коробку с той, которую подадут в compare в качестве параметра, true - если их веса равны, false в противном случае(коробки с яблоками мы можем сравнивать с коробками с апельсинами);
    f. Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую коробку(помним про сортировку фруктов, нельзя яблоки высыпать в коробку с апельсинами), соответственно в текущей коробке фруктов не остается, а в другую перекидываются объекты, которые были в этой коробке;
    g. Не забываем про метод добавления фрукта в коробку.
 */

public class Main {

    public static void main(String[] args) {
	testTask();
    }

    private static void testTask() {
        WithArrayWorking workerWithArray = new WithArrayWorking();
        Object[] array = getArray();

        workerWithArray.swapElement(array, 1, 2); //1

        List list = workerWithArray.getList(array); //2

        Box<Orange> firstBox = new Box(new Orange[]{
                new Orange(),
                new Orange(),
                new Orange(),
        });
        Box<Orange> secondBox = new Box<>(new Orange[]{
                new Orange(),
                new Orange(),
        });
        Box<Apple> thirdBox = new Box<>(new Apple[]{
                new Apple(),
                new Apple(),
                new Apple(),
                new Apple(),
                new Apple(),
                new Apple(),
                new Apple(),
                new Apple(),
                new Apple(),
        });

        System.out.println(firstBox.getWeight()); //3d
        firstBox.addFruit(new Orange()); //3g

        secondBox.pourIntoBox(firstBox); //3f

        System.out.println(firstBox.getWeight());
        System.out.println(secondBox.getWeight());

        System.out.println(thirdBox.compare(firstBox)? "вес одинаковый" : "вес отличается");//3e
    }

    private static Object[] getArray() {
        return new String[]{"first", "second", "third", "fourth"};
    }

}
