package Task_7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public abstract class TestStarter {

    public static void start(Class testClass) throws InvocationTargetException, IllegalAccessException {
        doTests(testClass);
    }

    public static void start(String testClassName) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        doTests(Class.forName(testClassName));
    }


    private static void doTests(Class testClass) throws InvocationTargetException, IllegalAccessException {
        Method[] allMethods = testClass.getDeclaredMethods();
        Method beforeSuiteMethod = null;
        Method afterSuiteMethod = null;
        List<Method> startMethods = new ArrayList<>(allMethods.length);

        for (Method m: allMethods){
            m.setAccessible(true);
            if(m.getAnnotation(BeforeSuite.class) != null){
                if (beforeSuiteMethod != null){
                    throw new RuntimeException("you must have only one @BeforeSuite");
                }else {
                    beforeSuiteMethod = m;
                    continue;
                }
            }
            if(m.getAnnotation(AfterSuit.class) != null){
                if (afterSuiteMethod!= null){
                    throw new RuntimeException("you must have only one @AfterSuit");
                }else {
                    afterSuiteMethod = m;
                    continue;
                }
            }
            if(m.getAnnotation(Test.class)!=null){
                if (startMethods.size() == 0){
                    startMethods.add(m);
                    continue;
                }
                for (int i = 0; i <= startMethods.size(); i++) {
                    if(i==startMethods.size()){
                        startMethods.add(m);
                        break;
                    }

                    if(m.getAnnotation(Test.class).priority()<=startMethods.get(i).getAnnotation(Test.class).priority()){
                        startMethods.add(i, m);
                        break;
                    }
                }
            }
        }
        if(beforeSuiteMethod==null || afterSuiteMethod==null) throw new RuntimeException("you must have @AfterSuit and @BeforeSuite");

        beforeSuiteMethod.invoke(testClass);
        for (Method m: startMethods){
            m.invoke(testClass);
        }
        afterSuiteMethod.invoke(testClass);
    }

}
