package homework;

import java.util.*;
import giis.demo.util.ApplicationException;
import giis.demo.util.Database;

/**
 * Acceso a los datos de competiciones e inscripciones.
 * * <br/>Utiliza los DTOs definidos externamente (CompetitionDTO y AthleteDTO)
 * para mapear los resultados de la base de datos.
 */
public class Model {
    private static final String MSG_DATOS_OBLIGATORIOS = "El DNI y el Nombre son obligatorios";
    private static final String MSG_ATLETA_YA_INSCRITO = "Este atleta ya está inscrito en esta competición";
    private static final String MSG_COMPETICION_NO_SELECCIONADA = "Debe seleccionar una competición";

    private Database db = new Database();

    // SQL para obtener la lista de competiciones futuras
    public static final String SQL_LISTA_COMPETICIONES = 
            "SELECT id, name, date, description FROM Competitions " +
            "WHERE date >= CURRENT_DATE ORDER BY date";

    // SQL para obtener los atletas inscritos (Join entre Atletas e Inscripciones)
    public static final String SQL_LISTA_INSCRITOS = 
            "SELECT a.dni, a.name, r.registration_date as date " +
            "FROM Athletes a " +
            "JOIN Registrations r ON a.id = r.athlete_id " +
            "WHERE r.competition_id = ? " +
            "ORDER BY a.name";

    // Consultas auxiliares para la lógica transaccional de inscripción
    public static final String SQL_CHECK_DUPLICADO = 
            "SELECT COUNT(*) FROM Registrations r " +
            "JOIN Athletes a ON r.athlete_id = a.id " +
            "WHERE a.dni = ? AND r.competition_id = ?";

    public static final String SQL_GET_ATLETA_ID = "SELECT id FROM Athletes WHERE dni = ?";
    public static final String SQL_INSERT_ATLETA = "INSERT INTO Athletes (dni, name, email) VALUES (?, ?, 'no-email@test.com')";
    public static final String SQL_INSERT_INSCRIPCION = "INSERT INTO Registrations (competition_id, athlete_id, registration_date) VALUES (?, ?, CURRENT_DATE)";

    /**
     * Obtiene la lista de competiciones futuras disponibles.
     * Usa la clase externa CompetitionDTO.class para el mapeo.
     */
    public List<CompetitionDTO> getListaCompeticiones() {
        return db.executeQueryPojo(CompetitionDTO.class, SQL_LISTA_COMPETICIONES);
    }

    /**
     * Obtiene la lista de atletas inscritos para una competición dada.
     * Usa la clase externa AthleteDTO.class para el mapeo.
     */
    public List<AthleteDTO> getListaAtletasInscritos(int idCompeticion) {
        validateCondition(idCompeticion > 0, MSG_COMPETICION_NO_SELECCIONADA);
        return db.executeQueryPojo(AthleteDTO.class, SQL_LISTA_INSCRITOS, idCompeticion);
    }

    /**
     * Realiza la inscripción de un atleta.
     * Incluye lógica de negocio: validación de duplicados y creación automática del atleta si no existe.
     */
    public void inscribirAtleta(int idCompeticion, String dni, String nombre) {
        validateNotNull(dni, MSG_DATOS_OBLIGATORIOS);
        validateNotNull(nombre, MSG_DATOS_OBLIGATORIOS);
        validateCondition(!dni.trim().isEmpty() && !nombre.trim().isEmpty(), MSG_DATOS_OBLIGATORIOS);

        // Validación de negocio: comprobar si ya está inscrito
        validateNoDuplicado(idCompeticion, dni);

        // Lógica transaccional: Obtener o crear atleta e inscribir
        int idAtleta = getOrCreateAtleta(dni, nombre);
        
        // Ejecución de la inscripción
        db.executeUpdate(SQL_INSERT_INSCRIPCION, idCompeticion, idAtleta);
    }

    /**
     * Valida que el atleta no esté ya inscrito en la competición indicada.
     * Causa excepcion ApplicationException si ya existe.
     */
    private void validateNoDuplicado(int idCompeticion, String dni) {
        List<Object[]> rows = db.executeQueryArray(SQL_CHECK_DUPLICADO, dni, idCompeticion);
        // SQLite/JDBC suele devolver Long o Integer, casteamos a Number para ser seguros
        int count = ((Number) rows.get(0)[0]).intValue();
        validateCondition(count == 0, MSG_ATLETA_YA_INSCRITO);
    }

    /**
     * Método auxiliar para obtener el ID de un atleta existente o crearlo si es nuevo.
     * Se realiza en pasos separados para simular una transacción lógica.
     */
    private int getOrCreateAtleta(String dni, String nombre) {
        List<Object[]> rows = db.executeQueryArray(SQL_GET_ATLETA_ID, dni);
        
        if (rows.isEmpty()) {
            // No existe, lo insertamos
            db.executeUpdate(SQL_INSERT_ATLETA, dni, nombre);
            // Recuperamos el ID recién creado
            rows = db.executeQueryArray(SQL_GET_ATLETA_ID, dni);
        }
        
        return ((Number) rows.get(0)[0]).intValue();
    }

    /* De uso general para validacion de objetos */
    private void validateNotNull(Object obj, String message) {
        if (obj == null)
            throw new ApplicationException(message);
    }

    private void validateCondition(boolean condition, String message) {
        if (!condition)
            throw new ApplicationException(message);
    }
}