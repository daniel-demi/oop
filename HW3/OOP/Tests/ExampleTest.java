package OOP.Tests;

import OOP.Solution.Injector;
import OOP.Solution.Inject;
import OOP.Solution.Named;
import OOP.Solution.Provides;
import jdk.jfr.Name;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class ExampleTest {

    static class Superclazz {
        public String toString(){
            return "Wee";
        }
    }

    static class Midclazz extends Superclazz {
        public String toString(){
            return "WOO";
        }
    }

    static class Subclazz extends Midclazz {
        @Inject
        public Subclazz() {}    // This constructor should be called because it is injected

        public Subclazz(Object o) {}    // This one exists but isn't injected

        public String toString() {
            return "WOW";
        }

        @Inject
        private String message;

        @Inject
        public void printMessage(){
            System.out.println(message);
        }
    }

    public static void main(String[] args) {

        Injector i = new Injector() {
            @Provides
            @Message
            // Now if someone will request a @Message they will receive this message
            public String provideMessage(Superclazz sc, @Number int x) {
                return sc.toString() + x;
            }

            @Provides
            @Number
            public int provideNumber() {
                return 42;
            }


        };
        try {
            i.bindToSupplier(String.class, ()->"bla bla bla");
            i.bind(Superclazz.class, Subclazz.class);
            Object o = i.construct(Superclazz.class);
            i.bindToInstance(Subclazz.class, o);
            System.out.println(o);      // Should be instance of Subclazz
            ((Subclazz)o).printMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }



        try {
            MessagePrinter mp = (MessagePrinter)i.construct(MessagePrinter.class);
            mp.print();
        } catch (Exception e) {
            e.printStackTrace();
        }
        new MessagePrinter("@34");
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.PARAMETER, ElementType.METHOD})
    @interface Message {}

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.PARAMETER, ElementType.METHOD})
    @interface Number {}

    static class MessagePrinter {
        private final String message;
        // This constructor is chosen because it is injected
        // In addition, there's a @Message parameter therefore the injector
        // searches for a suitable @Provider.
        // It will find the provideMessage() method on lines 36-41 and call it
        // Later, when print() is called, MessagePrinter will print "Hello, world!"
        @Inject
        public MessagePrinter(@Message String message) {
            this.message = message;
        }
        public void print() {
            System.out.println(this.message);
        }
    }
}
