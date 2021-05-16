package OOP.Solution;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface Inject{}

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface Provides{}

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@interface Named{
    String name() default "";
}