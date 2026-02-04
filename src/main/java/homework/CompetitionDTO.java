package homework;

/**
 * Cada una de las filas que muestran al usuario las competiciones disponibles
 * IMPORTANTE: Cuando se usan los componentes de Apache Commons DbUtils debe
 * mantenerse de forma estricta el convenio de capitalización de Java:
 * - Capitalizar todas las palabras que forman un identificador 
 * excepto la primera letra de nombres de métodos y variables.
 * - No utilizar subrayados
 * Seguir tambien estos mismos criterios en los nombres de tablas y campos de la BD
 */
public class CompetitionDTO {
    private int id;
    private String name;
    private String date;
    private String description;

    public CompetitionDTO() {}

    public CompetitionDTO(int rowId, String rowName, String rowDate, String rowDescription) {
        this.id = rowId;
        this.name = rowName;
        this.date = rowDate;
        this.description = rowDescription;
    }

    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public String getDate() { return this.date; }
    public String getDescription() { return this.description; }
    
    public void setId(int value) { this.id = value; }
    public void setName(String value) { this.name = value; }
    public void setDate(String value) { this.date = value; }
    public void setDescription(String value) { this.description = value; }

    /**
     * Método toString añadido para permitir que el JComboBox muestre 
     * el nombre amigable de la competición automáticamente.
     */
    @Override
    public String toString() {
        return this.name + " (" + this.date + ")";
    }
}