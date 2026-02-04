package homework;

/**
 * Cada una de las filas que muestran al usuario los atletas inscritos
 * IMPORTANTE: Cuando se usan los componentes de Apache Commons DbUtils debe
 * mantenerse de forma estricta el convenio de capitalización de Java:
 * - Capitalizar todas las palabras que forman un identificador 
 * excepto la primera letra de nombres de métodos y variables.
 * - No utilizar subrayados
 * Seguir tambien estos mismos criterios en los nombres de tablas y campos de la BD
 */
public class AthleteDTO {
    private String dni;
    private String name;
    private String date; // Fecha de inscripción

    public AthleteDTO() {}

    public AthleteDTO(String rowDni, String rowName, String rowDate) {
        this.dni = rowDni;
        this.name = rowName;
        this.date = rowDate;
    }

    public String getDni() { return this.dni; }
    public String getName() { return this.name; }
    public String getDate() { return this.date; }
    
    public void setDni(String value) { this.dni = value; }
    public void setName(String value) { this.name = value; }
    public void setDate(String value) { this.date = value; }
}