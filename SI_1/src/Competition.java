import java.util.ArrayList;
import java.util.List;

public class Competition {
    private String name;
    private List<Athlete> registeredAthletes;

    public Competition(String name) {
        this.name = name;
        this.registeredAthletes = new ArrayList<>();
    }

    public void registerAthlete(Athlete athlete) {
        this.registeredAthletes.add(athlete);
    }

    public List<Athlete> getRegisteredAthletes() {
        return registeredAthletes;
    }

    public String getName() {
        return name;
    }
}