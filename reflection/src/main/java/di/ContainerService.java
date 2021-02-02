package di;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

//12. 나만의 DI 프레임워크 만들기

public class ContainerService {

    public static <T> T getObject(Class<T> classType){
        T instance = createInstance(classType);

        Arrays.stream(classType.getDeclaredFields()).forEach(f -> {
            if(f.getAnnotation(Inject.class) != null){
                f.setAccessible(true);
                try {
                    f.set(instance, createInstance(f.getType()));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return instance;
    }

    private static <T> T createInstance(Class<T> classType){
        try {
            return classType.getConstructor(null).newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
           throw new RuntimeException(e);
        }
    }

}
