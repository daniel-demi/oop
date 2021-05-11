package OOP.Solution;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProfesorImpl implements Profesor {

    private final int id;
    private final String name;
    private final HashSet<CasaDeBurrito> favorites;
    private final HashSet<Profesor> friends;

    public ProfesorImpl(int id, String name){
        this.id = id;
        this.name = name;
        favorites = new HashSet<>();
        friends = new HashSet<>();
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public Profesor favorite(CasaDeBurrito c) throws UnratedFavoriteCasaDeBurritoException {
        if(!c.isRatedBy(this)) throw new UnratedFavoriteCasaDeBurritoException();
        favorites.add(c);
        return this;
    }

    @Override
    public Collection<CasaDeBurrito> favorites() {
        return new HashSet<>(favorites);
    }

    @Override
    public Profesor addFriend(Profesor p) throws SameProfesorException, ConnectionAlreadyExistsException {
        if(p == this) throw new SameProfesorException();
        if(friends.contains(p)) throw new ConnectionAlreadyExistsException();
        friends.add(p);
        return this;
    }

    @Override
    public Set<Profesor> getFriends() {
        return new HashSet<>(friends);
    }

    @Override
    public Set<Profesor> filteredFriends(Predicate<Profesor> p) {
        return friends.stream().filter(p).collect(Collectors.toSet());
    }

    @Override
    public Collection<CasaDeBurrito> filterAndSortFavorites(Comparator<CasaDeBurrito> comp, Predicate<CasaDeBurrito> p) {
        return favorites.stream().filter(p).sorted(comp).collect(Collectors.toList());
    }

    @Override
    public Collection<CasaDeBurrito> favoritesByRating(int rLimit) {
        return this.filterAndSortFavorites((CasaDeBurrito c1, CasaDeBurrito c2) -> {
            double diff = c1.averageRating() - c2.averageRating();
            if (0 < diff) return -1;
            if (diff < 0) return 1;
            int distDiff = c1.distance() - c2.distance();
            if(distDiff != 0) return distDiff;
            return c1.getId() - c2.getId();
        }, (CasaDeBurrito c) -> c.averageRating() >= rLimit);
    }

    @Override
    public Collection<CasaDeBurrito> favoritesByDist(int dLimit) {
        return this.filterAndSortFavorites((CasaDeBurrito c1, CasaDeBurrito c2) -> {
            int diff = c1.distance() - c2.distance();
            if (diff != 0) return diff;
            double ratingDiff = c1.averageRating() - c2.averageRating();
            if(ratingDiff > 0) return -1;
            if(ratingDiff < 0) return 1;
            return c1.getId() - c2.getId();
        }, (CasaDeBurrito c) -> c.distance() <= dLimit);
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if(!(o instanceof Profesor)) return false;
        return id == ((Profesor)o).getId();
    }

    @Override
    public String toString() {
        return    "Profesor: " + name + ".\n"
                + "Id: " + id + ".\n"
                + "Favorites: " + favorites.stream().map(CasaDeBurrito::getName).sorted().collect(Collectors.toList()).toString().replace("[", "").replace("]", "") + ".\n";
    }

    @Override
    public int compareTo(Profesor o) {
        return this.id - o.getId();
    }
}
