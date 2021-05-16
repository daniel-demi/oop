package OOP.Solution;

import OOP.Provided.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import

public class Injector {
    private final Map<Class<?>, Class<?>> classBindings;
    private final Map<Class<?>, Object> objectBindings;
    private final Map<Class<?>, Supplier<?>> supplierBindings;
    private final Map<String, Class<?>> nameBindings;

    public Injector() {
        classBindings = new HashMap<>();
        objectBindings = new HashMap<>();
        supplierBindings = new HashMap<>();
        nameBindings = new HashMap<>();
    }

    public void bind(Class<?> clazz1, Class<?> clazz2) throws IllegalBindException {
        Class<?> c = clazz2.getSuperclass();
        while(c != clazz1 && c != null) {
            c = c.getSuperclass();
        }
        if(c == null) throw new IllegalBindException();
        classBindings.put(clazz1, clazz2);
    }

    public void bindToInstance(Class<?> clazz, Object obj) throws IllegalBindException {
        if (!clazz.isInstance(obj)) throw new IllegalBindException();
        objectBindings.put(clazz, obj);
    }

    public void bindToSupplier(Class<?> clazz, Supplier<?> supplier) {
        supplierBindings.put(clazz, supplier);
    }

    public void bindByName(String s, Class<?> clazz) {
        nameBindings.put(s, clazz);
    }

    public Object construct(Class<?> clazz) throws MultipleInjectConstructorsException, NoConstructorFoundException, NoSuitableProviderFoundException, MultipleProvidersException, MultipleAnnotationOnParameterException {
        Class<?> actualClass = classBindings.get(clazz);
        Constructor<?>[] constructors = actualClass.getConstructors();
        Constructor<?> actualConstructor = null;
        for (Constructor<?> constructor : constructors) {
            if (constructor.isAnnotationPresent(Inject.class)) {
                if (actualConstructor != null && actualConstructor.isAnnotationPresent(Inject.class))
                    throw new MultipleInjectConstructorsException();
                actualConstructor = constructor;
            } else {
                if (constructor.getParameters().length == 0 && actualConstructor == null) {
                    actualConstructor = constructor;
                }
            }
        }
        if(actualConstructor == null) throw new NoConstructorFoundException();
        Parameter[] parameters = actualConstructor.getParameters();
        Method[] methods = actualClass.getMethods();
        for (Parameter p : parameters) {
            if(p.isAnnotationPresent(Provides.class)) {
                for(Method m : methods) {
                    if(m.isAnnotationPresent(Provides.) && m.getReturnType() == p.getType())
                }
            }
        }

        return null;
    }
}


