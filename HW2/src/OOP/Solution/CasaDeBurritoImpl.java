package OOP.Solution;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

public class CasaDeBurritoImpl implements CasaDeBurrito {

    private final int id;
    private final int dist;
    private final String name;
    private final Set<String> menu;
    private final HashMap<Profesor,Integer> ratings;

    public CasaDeBurritoImpl(int id, String name, int dist, Set<String> menu) {
        this.id = id;
        this.name = name;
        this.dist = dist;
        this.menu = menu;
        ratings = new HashMap<Profesor, Integer>();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int distance() {
        return dist;
    }

    @Override
    public boolean isRatedBy(Profesor p) {
        return ratings.containsKey(p);
    }

    @Override
    public CasaDeBurrito rate(Profesor p, int r) throws RateRangeException {
        if(r < 0 || r > 5) throw new RateRangeException();
        ratings.put(p, r);
        return this;
    }

    @Override
    public int numberOfRates() {
        return ratings.size();
    }

    @Override
    public double averageRating() {
        if (this.numberOfRates() == 0) return 0;
        double avg = 0;
        for (int rating :
                ratings.values()) {
            avg += rating;
        }
        avg /= this.numberOfRates();
        return avg;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if(!(o instanceof CasaDeBurrito)) return false;
        return this.id == ((CasaDeBurrito)o).getId();
    }

    @Override
    public String toString() {
        return    "CasaDeBurrito: " + name + ".\n"
                + "Id: " + id + ".\n"
                + "Distance: " + dist + ".\n"
                + "Menu: " + menu.stream().sorted().collect(Collectors.toList()).toString().replace("[", "").replace("]", "") + ".\n";
    }

    @Override
    public int compareTo(CasaDeBurrito o) {
        return this.id - o.getId();
    }
}
