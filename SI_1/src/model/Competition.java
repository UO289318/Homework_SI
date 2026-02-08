package model;

public class Competition {
    private int id;
    private String name;

    public Competition(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public int getId() { return id; }
    public String getName() { return name; }
    @Override
    public String toString() { return name; } // Importante para que el ComboBox muestre el nombre
}