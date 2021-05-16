package OOP.Solution;

import OOP.Provided.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;


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
        Object[] parameters = constructParameters(actualConstructor.getParameters());
        Method[] methods = actualClass.getMethods();

        try {
            return actualConstructor.newInstance(parameters);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Object[] constructParameters(Parameter[] parameters) throws MultipleInjectConstructorsException, NoSuitableProviderFoundException, NoConstructorFoundException, MultipleProvidersException, MultipleAnnotationOnParameterException {
        List<Object> paramInst = new ArrayList<>();
        for(Parameter p : parameters) {
            Annotation[] annotations = p.getAnnotations();
            if(annotations.length == 0) {
                paramInst.add(construct(p.getClass()));
            } else if (annotations.length > 1) {
                throw new MultipleAnnotationOnParameterException();
            } else {
                if(p.isAnnotationPresent(Named.class)) {
                    if(nameBindings.containsKey(p.getAnnotation(Named.class).name())) {
                        paramInst.add(construct(nameBindings.get(p.getAnnotation(Named.class).name())));
                        continue;
                    }
                }
                Method provider = null;
                for(Class<?> currentClass = getClass(); currentClass != Injector.class; currentClass = currentClass.getSuperclass()) {
                    Method[] methods = currentClass.getMethods();
                    for(Method m : methods) {
                        if(m.isAnnotationPresent(annotations[0].getClass()) && m.isAnnotationPresent(Provides.class)) {
                            if(m.getReturnType() == p.getType()) {
                                if(provider != null) throw new MultipleProvidersException();
                                provider = m;
                            }
                        }
                    }
                }
                if(provider == null) throw new NoSuitableProviderFoundException();
                provider.setAccessible(true);
                try {
                    paramInst.add(provider.invoke(this.getClass()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return paramInst.toArray();
    }
}


