package OOP.Solution;

import OOP.Provided.CartelDeNachos;
import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;

import java.util.*;
import java.util.stream.Collectors;

public class CartelDeNachosImpl implements CartelDeNachos{

    private final HashSet<Profesor> profSet;
    private final HashSet<CasaDeBurrito> burritoSet;

    public CartelDeNachosImpl(){
        profSet = new HashSet<>();
        burritoSet = new HashSet<>();
    }


    public Profesor joinCartel(int id, String name)
            throws Profesor.ProfesorAlreadyInSystemException {
        Profesor newProf = new ProfesorImpl(id, name);
        if(profSet.contains(newProf)){
            throw new Profesor.ProfesorAlreadyInSystemException();
        } else {
            profSet.add(newProf);
        }
        return newProf;
    }

    public CasaDeBurrito addCasaDeBurrito(int id, String name, int dist, Set<String> menu)
            throws CasaDeBurrito.CasaDeBurritoAlreadyInSystemException {
        CasaDeBurritoImpl newBurrito = new CasaDeBurritoImpl(id, name, dist , menu);
        if(burritoSet.contains(newBurrito)){
            throw new CasaDeBurrito.CasaDeBurritoAlreadyInSystemException();
        } else {
            burritoSet.add(newBurrito);
        }
        return newBurrito;
    }

    public Collection<Profesor> registeredProfesores(){
//            HashSet<Profesor> copiedProfSet = (HashSet<Profesor>)profSet.clone();
//            for (Profesor prof : profSet){
//                copiedProfSet.add(prof);
//            }

        return new HashSet<>(profSet);
    }

    public Collection<CasaDeBurrito> registeredCasasDeBurrito(){
//        HashSet<CasaDeBurrito> copiedBurritoSet = (HashSet) burritoSet.clone();
//        for (CasaDeBurrito burrito : burritoSet){
//            copiedBurritoSet.add(burrito);
//        }
//        return copiedBurritoSet;
        return new HashSet<>(burritoSet);
    }

    public Profesor getProfesor(int id)
            throws Profesor.ProfesorNotInSystemException {
        for (Profesor prof : profSet) {
            if (prof.getId() == id) {
                return prof;
            }
        }
        throw new Profesor.ProfesorNotInSystemException();
    }

    public CasaDeBurrito getCasaDeBurrito(int id)
            throws CasaDeBurrito.CasaDeBurritoNotInSystemException {
        for (CasaDeBurrito burrito : burritoSet) {
            if (burrito.getId() == id) {
                return burrito;
            }
        }
        throw new CasaDeBurrito.CasaDeBurritoNotInSystemException();
    }

    public CartelDeNachos addConnection(Profesor p1, Profesor p2)
            throws Profesor.ProfesorNotInSystemException, Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException {
        if(p1.getId() == p2.getId()){
            throw new Profesor.SameProfesorException();
        }
        if(!profSet.contains(p1) || !profSet.contains(p2)){
            throw new Profesor.ProfesorNotInSystemException();
        }
        if (p1.getFriends().contains(p2) || p2.getFriends().contains(p1)) {
            throw new Profesor.ConnectionAlreadyExistsException();
        }
        p1.addFriend(p2);
        p2.addFriend(p1);
        return this;
    }

   public Collection<CasaDeBurrito> favoritesByRating(Profesor p)
            throws Profesor.ProfesorNotInSystemException {
        if(!profSet.contains(p)) throw new Profesor.ProfesorNotInSystemException();
        List<Profesor> friends = profSet.stream().sorted().filter((Profesor friend) -> p.getFriends().contains(friend)).collect(Collectors.toList());
        List<CasaDeBurrito> burritoColl = new ArrayList<>();
        friends.stream().forEach((friend) -> burritoColl.addAll(friend.favoritesByRating(0)));
        return burritoColl.stream().distinct().collect(Collectors.toList());

    }

    public Collection<CasaDeBurrito> favoritesByDist(Profesor p)
            throws Profesor.ProfesorNotInSystemException {
        if(!profSet.contains(p)) throw new Profesor.ProfesorNotInSystemException();
        List<Profesor> friends = profSet.stream().sorted().filter((Profesor friend) -> p.getFriends().contains(friend)).collect(Collectors.toList());
        List<CasaDeBurrito> burritoColl = new ArrayList<>();
        friends.stream().forEach((friend) -> burritoColl.addAll(friend.favoritesByDist(Integer.MAX_VALUE)));
        return burritoColl.stream().distinct().collect(Collectors.toList());
    }

    public boolean getRecommendation(Profesor p, CasaDeBurrito c, int t)
            throws Profesor.ProfesorNotInSystemException, CasaDeBurrito.CasaDeBurritoNotInSystemException, ImpossibleConnectionException{
        if(!profSet.contains(p)) throw new Profesor.ProfesorNotInSystemException();
        if(!burritoSet.contains(c)) throw new CasaDeBurrito.CasaDeBurritoNotInSystemException();
        if(t < 0) throw new ImpossibleConnectionException();

        return getRecommendationAux(p, c, t, new HashSet<>());


    }

    private boolean getRecommendationAux(Profesor p, CasaDeBurrito c, int t, Set<Profesor> hist) {
        if(p.favorites().contains(c)) return true;
        if(t == 0) {
            return false;
        }
        Set<Profesor> friends = p.getFriends();
        friends.removeAll(hist);
        if(friends.size() == 0) return false;
        boolean res = false;
        for (Profesor friend: p.getFriends()) {
            if(!hist.contains(friend) && profSet.contains(friend)) {
                Set<Profesor> newHist = new HashSet<>(hist);
                newHist.add(friend);
                res = res || getRecommendationAux(friend, c, t - 1, newHist);
            }
        }
        return res;
    }

    public List<Integer> getMostPopularRestaurantsIds(){
        // calculate scores into map
        Map<Integer, Integer> scoreBoard = new HashMap<>();
        burritoSet.stream().forEach((c) -> scoreBoard.put(c.getId(), 0)); // init
        for(Profesor p : profSet) {
            for(Profesor f : p.getFriends()) {
                /*for(CasaDeBurrito c : f.favorites().stream().filter((CasaDeBurrito c1) -> burritoSet.contains(c1)).collect(Collectors.toSet())) {
                    scoreBoard.put(c.getId(), scoreBoard.get(c.getId()) + 1);
                }*/
                f.favorites().stream().filter((CasaDeBurrito c1) -> burritoSet.contains(c1)).forEach((CasaDeBurrito c) -> scoreBoard.put(c.getId(), (scoreBoard.get(c.getId()) + 1)));
            }
        }
        // maxValue = map.values.max; // maximum score
        // return map.keys.filter(map.get(key) == maxValue)
        if(scoreBoard.size() > 0){
            int maxValue = scoreBoard.values().stream().max(Integer::compare).get();
            return scoreBoard.keySet().stream().filter((x) -> scoreBoard.get(x) == maxValue).sorted().collect(Collectors.toList());
        }
        List<Integer> burritosIds = new LinkedList<>();
        for(CasaDeBurrito c : burritoSet){
            burritosIds.add(c.getId());
        }
        return burritosIds.stream().sorted().collect(Collectors.toList());
    }

    public String toString() {
        List<Integer> profIds = new ArrayList<>();
        profSet.stream().sorted().forEach((p) -> profIds.add(p.getId()));
        List<Integer> burritoIds = new ArrayList<>();
        burritoSet.stream().sorted().forEach((p) -> burritoIds.add(p.getId()));
        String res = "Registered profesores: ";
        res += profIds.toString().replace("[", "").replace("]", "") + ".\n";
        res += "Registered casas de burrito: ";
        res += burritoIds.toString().replace("[", "").replace("]", "") + ".\n";
        res += "Profesores:\n";
        List<Profesor> sortedProfSet = new ArrayList<>();
        profSet.stream().sorted().forEach(sortedProfSet::add);
        for(Profesor p : sortedProfSet){
            List<Integer> friendIds = new ArrayList<>();
            p.getFriends().stream().sorted().forEach((f) -> friendIds.add(f.getId()));
            res = res + p.getId() + " -> " + friendIds.stream().sorted().collect(Collectors.toList()) + ".\n";
        }
        res = res + "End profesores.\n";
        return res;
    }
}