package Task_7;

import java.lang.reflect.InvocationTargetException;

public class ToTestClass {

    public static void main(String[] args) {
        ToTestClass test = new ToTestClass();
        try {
            TestStarter.start(test.getClass());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @BeforeSuite
    private static void testFirst(){
        System.out.println("Start");
    }

    @Test
    private static int secondMethod(){
        System.out.println("second");
        return 2;
    }

    @Test
    private static void thirdMethod(){
        System.out.println("third");
    }

    @Test(priority = 8)
    private static void fourthMethod(){
        System.out.println("fourth");
    }

    @Test
    private static void fifthMethod(){
        System.out.println("fifth");
    }

    @Test(priority = 3)
    private static void sixthMethod(){
        System.out.println("sixth");
    }

    @Test(priority = 4)
    private static void seventhMethod(){
        System.out.println("seventh");
    }


    @AfterSuit
    private static void testLast(){
        System.out.println("end");
    }

}
