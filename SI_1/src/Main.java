import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Creamos competiciones
        Competition marathon = new Competition("Gran Maratón 2026");
        
        // Story 1: Atleta se registra
        Athlete a1 = new Athlete("Juan Pérez", "juan@mail.com");
        marathon.registerAthlete(a1);
        marathon.registerAthlete(new Athlete("Maria Garcia", "maria@mail.com"));

        // Story 2: Organizador ve la lista
        System.out.println("Lista de inscritos en: " + marathon.getName());
        for (Athlete a : marathon.getRegisteredAthletes()) {
            System.out.println("- " + a);
        }
    }
}