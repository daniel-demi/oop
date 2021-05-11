package OOP.Tests;

import OOP.Provided.*;
import OOP.Solution.*;

import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws Exception {
        testPartA();
        System.out.println("partA test was successful");

        testPartB();
        System.out.println("partB test was successful");
    }

    private static void testPartA() throws Exception {

        Profesor p1 = new ProfesorImpl(1, "yoni");
        Profesor p2 = new ProfesorImpl(2, "itzik");


        Checker.check(p1.favorites().size() == 0);
        Checker.check(p1.getFriends().size() == 0);

        Set<String> menu1 = new HashSet<>(), menu2 = new HashSet<>(), menu3 = new HashSet<>(),
                menu4 = new HashSet<>(), menu5 = new HashSet<>(), menu6 = new HashSet<>();
        menu1.add("Hamburger");
        menu1.add("Fries");
        menu2.add("Steak");
        menu2.add("Fries");
        menu2.add("Orange Juice");
        menu3.add("aaa");
        menu3.add("bbb");
        menu3.add("a");
        menu3.add("cccc");
        menu4.add("w");
        menu5.add("sss");
        menu5.add("jjj");
        menu6.add("t");
        menu6.add("s");
        menu6.add("d");
        menu6.add("f");
        menu6.add("g");

        CasaDeBurrito s1 = new CasaDeBurritoImpl(4, "edd shiren", 2, menu1);
        CasaDeBurrito s2 = new CasaDeBurritoImpl(12,  "cristina aguilera", 3, menu2);
        CasaDeBurrito s3 = new CasaDeBurritoImpl(0, "michel jackson", 0, menu3);
        CasaDeBurrito s4 = new CasaDeBurritoImpl(6, "omer adam", 3, menu4);
        CasaDeBurrito s5 = new CasaDeBurritoImpl(33, "maluma", 2, menu5);
        CasaDeBurrito s6 = new CasaDeBurritoImpl(40, "zak efron", 2, menu6);

        Checker.check(s1.averageRating() == 0);

        /* 1 rest rated profesor */
        s1.rate(p1, 5);
        p1.favorite(s1);
        Checker.check(p1.favorites().size() == 1);
        Checker.check(s1.averageRating() == 5);

        /* check exceptions */
        int counter = 0;
        try {p1.favorite(s2);} catch (Profesor.UnratedFavoriteCasaDeBurritoException e) {counter++;}
        try {p1.favorite(s3);} catch (Profesor.UnratedFavoriteCasaDeBurritoException e) {counter++;}
        try {p1.favorite(s4);} catch (Profesor.UnratedFavoriteCasaDeBurritoException e) {counter++;}
        try {p1.favorite(s5);} catch (Profesor.UnratedFavoriteCasaDeBurritoException e) {counter++;}
        Checker.check(counter == 4);

        /* multiple favorites */
        s2.rate(p1, 3);
        s3.rate(p1, 5);
        s4.rate(p1, 4);
        s5.rate(p1, 4);
        s6.rate(p1, 5);

        p1.favorite(s2).favorite(s3).favorite(s4).favorite(s5).favorite(s6);


        Checker.check(p1.favorites().size() == 6);
        Checker.check(p1.favorites().contains((s1)));
        Checker.check(p1.favorites().contains((s2)));
        Checker.check(p1.favorites().contains((s3)));
        Checker.check(p1.favorites().contains((s4)));
        Checker.check(p1.favorites().contains((s5)));
        Checker.check(p1.favorites().contains((s6)));

        Checker.check(p1.favoritesByRating(4).toArray()[0].equals(s3));
        Checker.check(p1.favoritesByRating(4).toArray()[1].equals(s1));
        Checker.check(p1.favoritesByRating(4).toArray()[2].equals(s6));
        Checker.check(p1.favoritesByRating(4).toArray()[3].equals(s5));
        Checker.check(p1.favoritesByRating(4).toArray()[4].equals(s4));


        Checker.check(p1.favoritesByDist(2).toArray()[0].equals(s3));
        Checker.check(p1.favoritesByDist(2).toArray()[1].equals(s1));
        Checker.check(p1.favoritesByDist(2).toArray()[2].equals(s6));
        Checker.check(p1.favoritesByDist(2).toArray()[3].equals(s5));

        Checker.check(p1.equals(new ProfesorImpl(1, "yyy")));

        String toStringOutput = "Profesor: yoni.\n" +
                "Id: 1.\n" +
                "Favorites: cristina aguilera, edd shiren, maluma, michel jackson, omer adam, zak efron.\n";

        Checker.check(p1.toString().equals(toStringOutput));

        s1.rate(p2, 3);
        Checker.check(s1.averageRating() == 4);
        Checker.check(s1.numberOfRates() == 2);

        p1.addFriend(p2);
        p2.addFriend(p1);

        counter = 0;
        try {p1.addFriend(p2);} catch (Profesor.ConnectionAlreadyExistsException e) {counter++;}
        try {p1.addFriend(p1);} catch (Profesor.SameProfesorException e) {counter++;}
        Checker.check(counter == 2);
        Checker.check(!s2.isRatedBy(p2));

        Checker.check(s1.equals(new CasaDeBurritoImpl(4, "yyy", 5, menu2)));

        toStringOutput = "CasaDeBurrito: edd shiren.\n" +
                "Id: 4.\n" +
                "Distance: 2.\n" +
                "Menu: Fries, Hamburger.\n";

        Checker.check(s1.toString().equals(toStringOutput));
    }

    private static void testPartB() throws Exception {

        CartelDeNachos c = new CartelDeNachosImpl();



        Profesor p1 = c.joinCartel(1, "yoni");
        Profesor p2 = c.joinCartel(2, "itzik");
        Profesor p3 = c.joinCartel(3, "ssss");
        Profesor p4 = c.joinCartel(4, "hhhh");
        Profesor p5 = c.joinCartel(5, "bbbb");
        Profesor p6 = c.joinCartel(6, "aaaa");



        Checker.check(p1.favorites().size() == 0);
        Checker.check(p1.getFriends().size() == 0);

        Checker.check(c.registeredProfesores().contains(p1));
        Checker.check(c.registeredProfesores().contains(p2));
        Checker.check(!c.registeredProfesores().contains(new ProfesorImpl(7, "d")));

        Set<String> menu1 = new HashSet<>(), menu2 = new HashSet<>(), menu3 = new HashSet<>(),
                menu4 = new HashSet<>(), menu5 = new HashSet<>(), menu6 = new HashSet<>();
        menu1.add("Hamburger");
        menu1.add("Fries");
        menu2.add("Steak");
        menu2.add("Fries");
        menu2.add("Orange Juice");
        menu3.add("aaa");
        menu3.add("bbb");
        menu3.add("a");
        menu3.add("cccc");
        menu4.add("w");
        menu5.add("sss");
        menu5.add("jjj");
        menu6.add("t");
        menu6.add("s");
        menu6.add("d");
        menu6.add("f");
        menu6.add("g");

        CasaDeBurrito s1 = c.addCasaDeBurrito(1, "edd shiren", 2, menu1);
        CasaDeBurrito s2 = c.addCasaDeBurrito(2,  "cristina aguilera", 3, menu2);
        CasaDeBurrito s3 = c.addCasaDeBurrito(3, "michel jackson", 0, menu3);
        CasaDeBurrito s4 = c.addCasaDeBurrito(4, "omer adam", 3, menu4);
        CasaDeBurrito s5 = c.addCasaDeBurrito(5, "maluma", 3, menu5);
        CasaDeBurrito s6 = c.addCasaDeBurrito(6, "zak efron", 2, menu6);

        Checker.check(c.registeredCasasDeBurrito().contains(s1));
        Checker.check(c.registeredCasasDeBurrito().contains(s2));
        Checker.check(c.registeredCasasDeBurrito().contains(s3));
        Checker.check(c.registeredCasasDeBurrito().contains(s4));
        Checker.check(c.registeredCasasDeBurrito().contains(s5));
        Checker.check(c.registeredCasasDeBurrito().contains(s6));
        Checker.check(!c.registeredCasasDeBurrito().contains(new CasaDeBurritoImpl(7, "d", 4, menu1)));

        Checker.check(s1.averageRating() == 0);

        /* check exceptions for get functions, addConnection function and favoritesByRating, favoritesByDist. */
        int counter = 0;
        try {Checker.check(c.getProfesor(1).equals(p1));} catch (Profesor.ProfesorNotInSystemException e) {counter++;}
        try {c.getProfesor(7);} catch (Profesor.ProfesorNotInSystemException e) {counter++;}
        try {c.getCasaDeBurrito(7);} catch (CasaDeBurrito.CasaDeBurritoNotInSystemException e) {counter++;}
        try {Checker.check(c.getCasaDeBurrito(3).equals(s3));} catch (CasaDeBurrito.CasaDeBurritoNotInSystemException e) {counter++;}

        try {c.addConnection(p1, new ProfesorImpl(7, "hh"));} catch (Profesor.ProfesorNotInSystemException e) {counter++;}
        try {c.addConnection(p1, new ProfesorImpl(1, "hh"));} catch (Profesor.SameProfesorException e) {counter++;}
        try {c.addConnection(p1, p2).addConnection(p1, p2);} catch (Profesor.ConnectionAlreadyExistsException e) {counter++;}

        try {c.favoritesByRating(new ProfesorImpl(7, "dd"));} catch (Profesor.ProfesorNotInSystemException e) {counter++;}
        try {c.favoritesByDist(new ProfesorImpl(7, "dd"));} catch (Profesor.ProfesorNotInSystemException e) {counter++;}

        try {c.getRecommendation(new ProfesorImpl(7, "dd"), s1, 5);} catch (Profesor.ProfesorNotInSystemException e) {counter++;}
        try {c.getRecommendation(p1, new CasaDeBurritoImpl(7, "dd", 5, menu1), 5);} catch (CasaDeBurrito.CasaDeBurritoNotInSystemException e) {counter++;}
        try {c.getRecommendation(p1, s1, -6);} catch (CartelDeNachos.ImpossibleConnectionException e) {counter++;}


        Checker.check(counter == 10);

        c.addConnection(p1, p3).addConnection(p1, p5).addConnection(p1, p6);

        Checker.check(c.getMostPopularRestaurantsIds().toArray()[0].equals(1));
        Checker.check(c.getMostPopularRestaurantsIds().toArray()[1].equals(2));
        Checker.check(c.getMostPopularRestaurantsIds().toArray()[2].equals(3));
        Checker.check(c.getMostPopularRestaurantsIds().toArray()[3].equals(4));
        Checker.check(c.getMostPopularRestaurantsIds().toArray()[4].equals(5));
        Checker.check(c.getMostPopularRestaurantsIds().toArray()[5].equals(6));

        s6.rate(p1, 3);
        s1.rate(p2, 3);
        s1.rate(p3, 3);//average = 3
        s2.rate(p5, 4);//average = 4
        s3.rate(p5, 4);//average = 4
        s4.rate(p6, 5);//average = 5
        s5.rate(p6, 5);//average = 5
        s6.rate(p4, 5);

        p1.favorite(s6);
        p2.favorite(s1);
        p3.favorite(s1);
        p4.favorite(s6);
        p5.favorite(s2);
        p5.favorite(s3);
        p6.favorite(s4);
        p6.favorite(s5);

        c.addConnection(p3, p4);

        Checker.check(c.favoritesByRating(p1).toArray()[0].equals(s1));
        Checker.check(c.favoritesByRating(p1).toArray()[1].equals(s3));
        Checker.check(c.favoritesByRating(p1).toArray()[2].equals(s2));
        Checker.check(c.favoritesByRating(p1).toArray()[3].equals(s4));
        Checker.check(c.favoritesByRating(p1).toArray()[4].equals(s5));

        Checker.check(c.favoritesByDist(p1).toArray()[0].equals(s1));
        Checker.check(c.favoritesByDist(p1).toArray()[1].equals(s3));
        Checker.check(c.favoritesByDist(p1).toArray()[2].equals(s2));
        Checker.check(c.favoritesByDist(p1).toArray()[3].equals(s4));
        Checker.check(c.favoritesByDist(p1).toArray()[4].equals(s5));

        Checker.check(c.getRecommendation(p1, s6, 1));
        Checker.check(c.getRecommendation(p1, s1, 2));
        Checker.check(c.getRecommendation(p1, s6, 4));

        Checker.check(c.getMostPopularRestaurantsIds().toArray()[0].equals(6));
        Checker.check(c.getMostPopularRestaurantsIds().size() == 1);

        s1.rate(p5, 4);
        s1.rate(p6, 4);

        p5.favorite(s1);
        p6.favorite(s1);

        Checker.check(c.getMostPopularRestaurantsIds().toArray()[0].equals(1));
        Checker.check(c.getMostPopularRestaurantsIds().toArray()[1].equals(6));
        Checker.check(c.getMostPopularRestaurantsIds().size() == 2);

        String toStringOutput = "Registered profesores: 1, 2, 3, 4, 5, 6.\n" +
                "Registered casas de burrito: 1, 2, 3, 4, 5, 6.\n" +
                "Profesores:\n" +
                "1 -> [2, 3, 5, 6].\n" +
                "2 -> [1].\n" +
                "3 -> [1, 4].\n" +
                "4 -> [3].\n" +
                "5 -> [1].\n" +
                "6 -> [1].\n" +
                "End profesores.\n";

        Checker.check(c.toString().equals(toStringOutput));

    }

}
