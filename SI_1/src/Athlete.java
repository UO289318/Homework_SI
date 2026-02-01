public class Athlete {
    private String name;
    private String email;

    public Athlete(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Athlete: " + name + " (" + email + ")";
    }
}