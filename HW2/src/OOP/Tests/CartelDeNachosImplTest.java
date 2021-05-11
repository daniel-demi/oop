package OOP.Tests;

import OOP.Provided.CartelDeNachos;
import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;
import OOP.Solution.CartelDeNachosImpl;
import OOP.Solution.CasaDeBurritoImpl;
import OOP.Solution.ProfesorImpl;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.*;

import static org.junit.Assert.*;

public class CartelDeNachosImplTest {
    private CartelDeNachos cartel;
    private Profesor prof1;
    private Profesor prof2;
    private Profesor prof3;
    private Profesor prof4;
    private Profesor prof5;
    String[] menu3_array = {"Dust", "Sorrow", "Difficult Homework"};
    private Set<String> menu3 = new HashSet<>(Arrays.asList(menu3_array));

    private CasaDeBurrito casa1, casa2, casa3;

    @Before
    public void setUp() throws Exception {
        cartel = new CartelDeNachosImpl();
        prof1 = new ProfesorImpl(1, "Charlie");
        prof2 = new ProfesorImpl(2, "Dee");
        prof3 = new ProfesorImpl(3, "Mac");
        prof4 = new ProfesorImpl(4, "Dennis");
        prof5 = new ProfesorImpl(5, "Frank");

        String[] menu1_array = {"Pizza", "Pasta", "Grappa"};
        Set<String> menu1 = new HashSet<>(Arrays.asList(menu1_array));
        String[] menu2_array = {"Cheeseburger", "Fries", "Coca-Cola"};
        Set<String> menu2 = new HashSet<>(Arrays.asList(menu2_array));



        casa1 = new CasaDeBurritoImpl(1, "Italian Casa", 40, menu1);
        casa2 = new CasaDeBurritoImpl(2, "American Casa", 30, menu2);
        casa3 = new CasaDeBurritoImpl(3, "Technion Student Casa", 60, menu3);

        assertEquals(cartel.joinCartel(1, "Charlie"), prof1);
        assertEquals(cartel.joinCartel(2, "Dee"), prof2);
        assertEquals(cartel.joinCartel(3, "Mac"), prof3);
        assertEquals(cartel.joinCartel(4, "Dennis"), prof4);
        assertEquals(cartel.joinCartel(5, "Frank"), prof5);
        assertEquals(cartel.addCasaDeBurrito(1, "Italian Casa", 40, menu1), casa1);
        assertEquals(cartel.addCasaDeBurrito(2, "American Casa", 30, menu2), casa2);
        assertEquals(cartel.addCasaDeBurrito(3, "Technion Student Casa", 60, menu3), casa3);

        cartel = new CartelDeNachosImpl();
        prof1 = cartel.joinCartel(1, "Charlie");
        prof2 = cartel.joinCartel(2, "Dee");
        prof3 = cartel.joinCartel(3, "Mac");
        prof4 = cartel.joinCartel(4, "Dennis");
        prof5 = cartel.joinCartel(5, "Frank");
        casa1 = cartel.addCasaDeBurrito(1, "Italian Casa", 40, menu1);
        casa2 = cartel.addCasaDeBurrito(2, "American Casa", 30, menu2);
        casa3 = cartel.addCasaDeBurrito(3, "Technion Student Casa", 60, menu3);
    }

    @Test
    public void joinCartel() {
        try {
            cartel.joinCartel(1, "Not Charlie");
            fail();
        } catch (Profesor.ProfesorAlreadyInSystemException ignored) {

        }
        try {
            cartel.joinCartel(1, "Not Charlie");
            fail();
        } catch (Profesor.ProfesorAlreadyInSystemException ignored) {

        }
        try {
            cartel.joinCartel(1, "Charlie");
            fail();
        } catch (Profesor.ProfesorAlreadyInSystemException ignored) {

        }
    }

    @Test
    public void addCasaDeBurrito() {
        try {
            cartel.addCasaDeBurrito(1, "Italian Casa", 40, null);
            fail();
        } catch (CasaDeBurrito.CasaDeBurritoAlreadyInSystemException ignored) {

        }
        try {
            cartel.addCasaDeBurrito(1, "Italian Casa", 40, null);
            fail();
        } catch (CasaDeBurrito.CasaDeBurritoAlreadyInSystemException ignored) {

        }
    }

    @Test
    public void registeredProfesores() {
        Collection<Profesor> profs = cartel.registeredProfesores();
        assertEquals(new HashSet<>(profs), new HashSet<Profesor>(Arrays.asList(prof1, prof2, prof3, prof4, prof5)));
        profs.remove(prof1);
        profs = cartel.registeredProfesores();
        assertEquals(new HashSet<>(profs), new HashSet<Profesor>(Arrays.asList(prof1, prof2, prof3, prof4, prof5)));

        // do not create a deep copy, only shallow copy
        for (Profesor p : profs) {
            try {
                casa1.rate(p, 3);
                p.favorite(casa1);
            } catch (CasaDeBurrito.RateRangeException | Profesor.UnratedFavoriteCasaDeBurritoException e) {
                fail();
            }
        } // so changes made to the members of the set should actually affect cartel
        profs = cartel.registeredProfesores();
        for (Profesor p : profs) {
            assertEquals(new HashSet<>(p.favorites()), new HashSet<>(Collections.singletonList(casa1)));
        }

    }

    @Test
    public void registeredCasasDeBurrito() {
        Collection<CasaDeBurrito> casas = cartel.registeredCasasDeBurrito();
        assertEquals(new HashSet<>(casas), new HashSet<CasaDeBurrito>(Arrays.asList(casa1, casa2, casa3)));
        casas.remove(casa1);
        casas = cartel.registeredCasasDeBurrito();
        assertEquals(new HashSet<>(casas), new HashSet<CasaDeBurrito>(Arrays.asList(casa1, casa2, casa3)));

        // do not create a deep copy, only shallow copy
        for (CasaDeBurrito c : casas) {
            try {
                c.rate(prof1, 3);
            } catch (CasaDeBurrito.RateRangeException e) {
                fail();
            }
        } // so changes made to the members of the set should actually affect cartel
        casas = cartel.registeredCasasDeBurrito();
        for (CasaDeBurrito c : casas) {
            assertEquals(c.averageRating(), 3, 0);
        }
    }

    @Test
    public void getProfesor() {
        try {
            assertEquals(cartel.getProfesor(1), prof1);
            assertEquals(cartel.getProfesor(2), prof2);
            assertEquals(cartel.getProfesor(3), prof3);
            assertEquals(cartel.getProfesor(4), prof4);
            assertEquals(cartel.getProfesor(5), prof5);
        } catch (Profesor.ProfesorNotInSystemException e) {
            fail();
        }

        try {
            cartel.getProfesor(6);
            fail();
        } catch (Profesor.ProfesorNotInSystemException ignored) {

        }

        // check that this is a reference and not a copy
        try {
            Profesor p = cartel.getProfesor(1);
            p.addFriend(prof2);
            p = cartel.getProfesor(1);
            assertEquals(new HashSet<>(p.getFriends()), new HashSet<Profesor>(Collections.singletonList(prof2)));
        } catch (Profesor.ProfesorNotInSystemException |
                Profesor.ConnectionAlreadyExistsException |
                Profesor.SameProfesorException e) {
            fail();
        }
    }

    @Test
    public void getCasaDeBurrito() {
        try {
            assertEquals(cartel.getCasaDeBurrito(1), casa1);
            assertEquals(cartel.getCasaDeBurrito(2), casa2);
            assertEquals(cartel.getCasaDeBurrito(3), casa3);

        } catch (CasaDeBurrito.CasaDeBurritoNotInSystemException e) {
            fail();
        }

        try {
            cartel.getCasaDeBurrito(4);
            fail();
        } catch (CasaDeBurrito.CasaDeBurritoNotInSystemException ignored) {
        }

        // check that this is a reference and not a copy
        try {
            CasaDeBurrito c = cartel.getCasaDeBurrito(1);
            c.rate(prof1, 3);
            assertEquals(c.averageRating(), 3, 0);

        } catch (CasaDeBurrito.CasaDeBurritoNotInSystemException | CasaDeBurrito.RateRangeException e) {
            fail();
        }
    }

    @Test
    public void addConnection() {
        try {
            cartel.addConnection(prof1, prof2).addConnection(prof2, prof3);
        } catch (Profesor.SameProfesorException |
                Profesor.ConnectionAlreadyExistsException |
                Profesor.ProfesorNotInSystemException e) {
            fail();
        }

        try {
            cartel.addConnection(prof1, prof2);
            fail();
        } catch (Profesor.SameProfesorException | Profesor.ProfesorNotInSystemException e) {
            fail();
        } catch (Profesor.ConnectionAlreadyExistsException ignored) {

        }

        try {
            cartel.addConnection(prof2, prof1);
            fail();
        } catch (Profesor.SameProfesorException | Profesor.ProfesorNotInSystemException e) {
            fail();
        } catch (Profesor.ConnectionAlreadyExistsException ignored) {

        }

        Profesor prof6 = new ProfesorImpl(6, "Charlie");
        Profesor prof7 = new ProfesorImpl(7, "Charlie");
        try {
            cartel.addConnection(prof6, prof1);
            fail();
        } catch (Profesor.ProfesorNotInSystemException ignored) {

        } catch (Profesor.ConnectionAlreadyExistsException | Profesor.SameProfesorException e) {
           fail();
        }
        try {
            cartel.addConnection(prof1, prof6);
            fail();
        } catch (Profesor.ProfesorNotInSystemException ignored) {

        } catch (Profesor.ConnectionAlreadyExistsException | Profesor.SameProfesorException e) {
            fail();
        }
        try {
            cartel.addConnection(prof7, prof6);
            fail();
        } catch (Profesor.ProfesorNotInSystemException ignored) {

        } catch (Profesor.ConnectionAlreadyExistsException | Profesor.SameProfesorException e) {
            fail();
        }

        try {
            cartel.addConnection(prof1, prof1);
            fail();
        } catch (Profesor.ProfesorNotInSystemException | Profesor.ConnectionAlreadyExistsException e) {
            fail();
        } catch (Profesor.SameProfesorException ignored) {

        }
    }



    @Test
    public void favoritesByRating2() {

        CartelDeNachos cartel = new CartelDeNachosImpl();

        String[] menu1_array = {"Pizza", "Pasta", "Grappa"};
        Set<String> menu1 = new HashSet<>(Arrays.asList(menu1_array));
        String[] menu2_array = {"Cheeseburger", "Fries", "Coca-Cola"};
        Set<String> menu2 = new HashSet<>(Arrays.asList(menu2_array));
        String[] menu3_array = {"Dust", "Sorrow", "Difficult Homework"};
        Set<String> menu3 = new HashSet<>(Arrays.asList(menu3_array));



        //CasaDeBurrito casa5 = new CasaDeBurritoImpl(5, "Casa5", 60, menu3);
        /*
        prof2 friends:
        prof1:
            casa1 (Italian) 4 (4)
            casa3 (Technion) 4 (4)
        prof3:
            casa1 (Italian) 4 (4)
            casa2 (American) 3 (3)
            casa4 (casa4) 5 (5)

        OUTPUT:
            casa1
            casa3
            casa4
            casa2
         */
        try{
            CasaDeBurrito casa1 = cartel.addCasaDeBurrito(1, "Italian Casa", 60, menu1);
            CasaDeBurrito casa2 = cartel.addCasaDeBurrito(2, "American Casa", 30, menu2);
            CasaDeBurrito casa3 = cartel.addCasaDeBurrito(3, "Technion Student Casa", 60, menu3);
            CasaDeBurrito casa4 =cartel.addCasaDeBurrito(4, "Casa4", 60, menu3);
            Profesor prof1 = cartel.joinCartel(1, "Charlie");
            Profesor prof2 = cartel.joinCartel(2, "Dee");
            Profesor prof3 = cartel.joinCartel(3, "Mac");
            casa1.rate(prof1,4);
            casa3.rate(prof1,4);
            casa4.rate(prof3,5);
            casa1.rate(prof3,4);
            casa2.rate(prof3,3);
            prof1.favorite(casa1);
            prof1.favorite(casa3);
            prof3.favorite(casa1);
            prof3.favorite(casa2);
            prof3.favorite(casa4);
            cartel.addConnection(prof2,prof1);
            cartel.addConnection(prof2,prof3);
            ArrayList<CasaDeBurrito> clist=new ArrayList<>();
            clist.add(casa1);
            clist.add(casa3);
            clist.add(casa4);
            clist.add(casa2);
            assertEquals(clist,cartel.favoritesByRating(prof2));
        }
        catch (Profesor.UnratedFavoriteCasaDeBurritoException e)
        {
            fail();
        }catch(Profesor.ProfesorNotInSystemException e){
            fail();
        }catch (Profesor.ProfesorAlreadyInSystemException e){
            fail();
        }
        catch(CasaDeBurrito.RateRangeException e){
            fail();
        }
        catch (Profesor.ConnectionAlreadyExistsException e){
            fail();
        }
        catch(Profesor.SameProfesorException e){
            fail();
        }
        catch (CasaDeBurrito.CasaDeBurritoAlreadyInSystemException e){
            fail();
        }
    }



    @Test
    public void favoritesByDist() {

        CartelDeNachos cartel = new CartelDeNachosImpl();
        Profesor prof1 ;
        Profesor prof2 ;
        Profesor prof3 ;

        String[] menu1_array = {"Pizza", "Pasta", "Grappa"};
        Set<String> menu1 = new HashSet<>(Arrays.asList(menu1_array));
        String[] menu2_array = {"Cheeseburger", "Fries", "Coca-Cola"};
        Set<String> menu2 = new HashSet<>(Arrays.asList(menu2_array));
        String[] menu3_array = {"Dust", "Sorrow", "Difficult Homework"};
        Set<String> menu3 = new HashSet<>(Arrays.asList(menu3_array));





        //CasaDeBurrito casa5 = new CasaDeBurritoImpl(5, "Casa5", 60, menu3);
        /*
        prof2 friends:
        prof1:
            casa1 (Italian) 60 -> 4 -> 1
            casa3 (Technion) 60 -> 4 -> 3
        prof3:
            casa1 (Italian) 60 -> 4 -> 1
            casa2 (American) 30 -> 3 -> 2
            casa4 (casa4) 60 -> 5 -> 4

        OUTPUT:
            casa1
            casa3
            casa2
            casa4
         */
        try{
            CasaDeBurrito  casa1 =cartel.addCasaDeBurrito(1, "Italian Casa", 60, menu1);
            CasaDeBurrito  casa3 = cartel.addCasaDeBurrito(3, "Technion Student Casa", 60, menu3);
            CasaDeBurrito  casa2 =cartel.addCasaDeBurrito(2, "American Casa", 30, menu2);
            CasaDeBurrito  casa4 = cartel.addCasaDeBurrito(4, "Casa4", 60, menu3);
            prof1 =cartel.joinCartel(1, "Charlie");
            prof2 =cartel.joinCartel(2, "Dee");
            prof3 =cartel.joinCartel(3, "Mac");

            casa3.rate(prof1,4);
            casa1.rate(prof1,4);
            casa4.rate(prof3,5);
            casa1.rate(prof3,4);
            casa2.rate(prof3,3);
            prof1.favorite(casa1);
            prof1.favorite(casa3);
            prof3.favorite(casa1);
            prof3.favorite(casa2);
            prof3.favorite(casa4);
            cartel.addConnection(prof2,prof1);
            cartel.addConnection(prof2,prof3);
            ArrayList<CasaDeBurrito> clist=new ArrayList<>();
            clist.add(casa1);
            clist.add(casa3);
            clist.add(casa2);
            clist.add(casa4);
            assertEquals(clist,cartel.favoritesByDist(prof2));
        }
        catch (Profesor.UnratedFavoriteCasaDeBurritoException e)
        {
            fail();
        }catch(Profesor.ProfesorNotInSystemException e){
            fail();
        }catch (Profesor.ProfesorAlreadyInSystemException e){
            fail();
        }
        catch(CasaDeBurrito.RateRangeException e){
            fail();
        }
        catch (Profesor.ConnectionAlreadyExistsException e){
            fail();
        }
        catch(Profesor.SameProfesorException e){
            fail();
        }
        catch (CasaDeBurrito.CasaDeBurritoAlreadyInSystemException e){
            fail();
        }
    }

    @Test
    public void getRecommendation() throws Profesor.UnratedFavoriteCasaDeBurritoException, Profesor.ProfesorNotInSystemException, CasaDeBurrito.CasaDeBurritoNotInSystemException, CartelDeNachos.ImpossibleConnectionException, CasaDeBurrito.RateRangeException, Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException, CasaDeBurrito.CasaDeBurritoAlreadyInSystemException {
        Profesor prof6 = new ProfesorImpl(6, "Not Exist");
        CasaDeBurrito casa4 = new CasaDeBurritoImpl(4, "Technion Student Casa", 60, menu3);
        try {
            cartel.getRecommendation(prof6 ,casa1 , 0);
            fail();
        }
        catch (Profesor.ProfesorNotInSystemException ignored) {
        }
        catch (Exception e){
            fail();
        }

        try {
            cartel.getRecommendation(prof1 ,casa4 , 0);
            fail();
        }
        catch (CasaDeBurrito.CasaDeBurritoNotInSystemException ignored) {
        }
        catch (Exception e){
            fail();
        }

        try {
            cartel.getRecommendation(prof1 ,casa3 , -1);
            fail();
        }
        catch (CartelDeNachos.ImpossibleConnectionException ignored) {
        }
        catch (Exception e){
            fail();
        }
        casa1.rate(prof1, 1);
        prof1.favorite(casa1);
        assertTrue( cartel.getRecommendation(prof1,casa1,0));
        assertTrue( cartel.getRecommendation(prof1,casa1,1));

        casa1.rate(prof2,2);
        casa2.rate(prof2,2);
        prof2.favorite(casa2);
        assertFalse( cartel.getRecommendation(prof1,casa2,1));

        cartel.addConnection(prof1,prof2);
        assertTrue( cartel.getRecommendation(prof1,casa1,1));
        assertTrue( cartel.getRecommendation(prof1,casa2,1));
        assertTrue( cartel.getRecommendation(prof2,casa1,1));

        casa3.rate(prof3 , 3);
        prof3.favorite(casa3);
        assertFalse( cartel.getRecommendation(prof1,casa3,1));

        cartel.addConnection(prof2,prof3);
        assertTrue( cartel.getRecommendation(prof2,casa3,1));
        assertTrue( cartel.getRecommendation(prof1,casa3,2));
        assertTrue( cartel.getRecommendation(prof1,casa3,30));
        assertTrue( cartel.getRecommendation(prof3,casa1,2));
        assertFalse( cartel.getRecommendation(prof3,casa1,1));
        assertFalse( cartel.getRecommendation(prof1,casa3,1));
        assertFalse( cartel.getRecommendation(prof1,casa3,0));

        casa3.rate(prof3 , 3);
        prof3.favorite(casa3);

        casa4 = cartel.addCasaDeBurrito(4, "Italian Casa", 40, menu3);
        casa4.rate(prof2,5);
        prof2.favorite(casa4);
        assertTrue( cartel.getRecommendation(prof1,casa4,1));
        assertTrue( cartel.getRecommendation(prof3,casa4,1));
        assertFalse( cartel.getRecommendation(prof1,casa4,0));
        assertFalse( cartel.getRecommendation(prof3,casa4,0));
    }

    @Test
    public void getMostPopularRestaurantsIds() throws Profesor.UnratedFavoriteCasaDeBurritoException, CasaDeBurrito.RateRangeException, Profesor.ProfesorNotInSystemException, Profesor.SameProfesorException, Profesor.ConnectionAlreadyExistsException {

        /** example from the exercis **
         * the profesors joined the cartel already in the setup
         */
        assertTrue(cartel.getMostPopularRestaurantsIds().size() == 3);
        Iterator<Integer> iter = cartel.getMostPopularRestaurantsIds().iterator();
        assertEquals(1, (int)iter.next() );
        assertEquals(2, (int)iter.next() );
        assertEquals(3, (int)iter.next() );

        casa1.rate(prof1, 1);
        prof1.favorite(casa1);
        casa2.rate(prof1, 1);
        prof1.favorite(casa2);
        assertTrue(cartel.getMostPopularRestaurantsIds().size() == 3);
        iter = cartel.getMostPopularRestaurantsIds().iterator();
        assertEquals(1, (int)iter.next() );
        assertEquals(2, (int)iter.next() );
        assertEquals(3, (int)iter.next() );


        casa1.rate(prof2, 2);
        prof2.favorite(casa1);
        casa2.rate(prof2, 2);
        prof2.favorite(casa2);
        assertTrue(cartel.getMostPopularRestaurantsIds().size() == 3);
        iter = cartel.getMostPopularRestaurantsIds().iterator();
        assertEquals(1, (int)iter.next() );
        assertEquals(2, (int)iter.next() );
        assertEquals(3, (int)iter.next() );


        casa3.rate(prof3, 3);
        prof3.favorite(casa3);
        casa1.rate(prof3, 3);
        prof3.favorite(casa1);

        cartel.addConnection(prof1,prof3);
        cartel.addConnection(prof1,prof2);
        assertEquals(1  ,cartel.getMostPopularRestaurantsIds().size() );
        iter = cartel.getMostPopularRestaurantsIds().iterator();

        assertEquals(1, (int)iter.next() );

        /** Done exercise **/

        casa2.rate(prof4, 1);
        prof4.favorite(casa2);
        cartel.addConnection(prof4,prof2);
        assertEquals(2  ,cartel.getMostPopularRestaurantsIds().size() );
        iter = cartel.getMostPopularRestaurantsIds().iterator();
        assertEquals(1, (int)iter.next() );
        assertEquals(2, (int)iter.next() );

        casa2.rate(prof5, 1);
        prof5.favorite(casa2);
        cartel.addConnection(prof5,prof4);
        assertEquals(1  ,cartel.getMostPopularRestaurantsIds().size() );
        iter = cartel.getMostPopularRestaurantsIds().iterator();
        assertEquals(2, (int)iter.next() );
    }

    @Test
    public void toStringTest() {
        CartelDeNachos cartel = new CartelDeNachosImpl();


        String[] menu1_array = {"Pizza", "Pasta", "Grappa"};
        Set<String> menu1 = new HashSet<>(Arrays.asList(menu1_array));
        String[] menu2_array = {"Cheeseburger", "Fries", "Coca-Cola"};
        Set<String> menu2 = new HashSet<>(Arrays.asList(menu2_array));
        String[] menu3_array = {"Dust", "Sorrow", "Difficult Homework"};
        Set<String> menu3 = new HashSet<>(Arrays.asList(menu3_array));


        String myString="Registered profesores: 1, 2, 3.\nRegistered casas de burrito: 1, 2, 3, 4.\nProfesores:\n1 -> [2].\n2 -> [1, 3].\n3 -> [2].\nEnd profesores.\n";

        /*
                Registered profesores: 1, 2, 3.
                Registered casas de burrito: 1, 2, 3, 4.
                Profesores:
                1 -> [2].
                2 -> [1, 3].
                3 -> [2].
                End profesores.

         */
        try{
            cartel.addCasaDeBurrito(3, "Technion Student Casa", 60, menu3);
            cartel.addCasaDeBurrito(2, "American Casa", 30, menu2);

            cartel.addCasaDeBurrito(4, "Casa4", 60, menu3);
            Profesor prof1 = cartel.joinCartel(1, "Charlie");
            Profesor prof2 = cartel.joinCartel(2, "Dee");
            Profesor prof3 = cartel.joinCartel(3, "Mac");
            cartel.addCasaDeBurrito(1, "Italian Casa", 60, menu1);
            cartel.addConnection(prof2,prof1);
            cartel.addConnection(prof2,prof3);
            assertEquals(cartel.toString(),myString);
        }
        catch(Profesor.ProfesorNotInSystemException e){
            fail();
        }catch (Profesor.ProfesorAlreadyInSystemException e){
            fail();
        }

        catch (Profesor.ConnectionAlreadyExistsException e){
            fail();
        }
        catch(Profesor.SameProfesorException e){
            fail();
        }
        catch (CasaDeBurrito.CasaDeBurritoAlreadyInSystemException e){
            fail();
        }
    }
}