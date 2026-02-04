package homework;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import giis.demo.util.ApplicationException;
import giis.demo.util.SwingUtil;

/**
 * Controlador para la funcionalidad de registro y visualización de atletas.
 * Sigue fielmente la estructura del ejemplo CarrerasController.
 */
public class RegistrationController {

    private Model model;
    private View view;
    private String lastSelectedKey = ""; // recuerda la última fila seleccionada para restaurarla cuando cambie la tabla

    public RegistrationController(Model m, View v) {
        this.model = m;
        this.view = v;
        // no hay inicializacion especifica del modelo, solo de la vista
        this.initView();
    }

    /**
     * Inicializacion del controlador: anyade los manejadores de eventos a los objetos del UI.
     */
    public void initController() {
        // PESTAÑA ATLETA: Botón de Registro
        // Usamos exceptionWrapper para que capture errores (como duplicados) automáticamente
        view.getBtnRegister().addActionListener(e -> SwingUtil.exceptionWrapper(() -> registerAthlete()));

        // PESTAÑA ORGANIZADOR: Botón de Actualizar Lista
        view.getBtnRefresh().addActionListener(e -> SwingUtil.exceptionWrapper(() -> getListaAtletas()));

        // Evento extra: Al cambiar el combo de filtro, actualizamos la lista automáticamente (mejora UX)
        view.getCmbCompetitionsFilter().addActionListener(e -> SwingUtil.exceptionWrapper(() -> getListaAtletas()));

        // Mouse listener para la tabla (igual que en el ejemplo)
        view.getTableAthletes().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                // no usa mouseClicked porque al establecer seleccion simple en la tabla
                // el usuario podria arrastrar el raton por varias filas e interesa solo la ultima
                SwingUtil.exceptionWrapper(() -> updateDetail());
            }
        });
    }

    public void initView() {
        // Carga los datos iniciales de las competiciones en los desplegables
        this.loadCompetitions();
        
        // Abre la ventana (sustituye al main generado por WindowBuilder)
        view.getFrame().setVisible(true); 
    }

    /**
     * Carga las competiciones en los comboboxes (Equivalente a la carga de datos iniciales)
     */
    private void loadCompetitions() {
        // Llamada al método corregido del modelo
        List<CompetitionDTO> competiciones = model.getListaCompeticiones();
        
        // Convertimos a array para el modelo del combo
        CompetitionDTO[] array = competiciones.toArray(new CompetitionDTO[0]);
        
        // Asignamos el modelo a los combos de la vista
        ComboBoxModel<Object> modelRegistro = new DefaultComboBoxModel<>(array);
        ComboBoxModel<Object> modelFiltro = new DefaultComboBoxModel<>(array);
        
        view.getCmbCompetitions().setModel(modelRegistro);
        view.getCmbCompetitionsFilter().setModel(modelFiltro);
        
        // Seleccionamos el primero por defecto si hay datos
        if (array.length > 0) {
            view.getCmbCompetitions().setSelectedIndex(0);
            view.getCmbCompetitionsFilter().setSelectedIndex(0);
        }
    }

    /**
     * La obtencion de la lista de atletas solo necesita obtener la lista de objetos del modelo 
     * y usar metodo de SwingUtil para crear un tablemodel que se asigna finalmente a la tabla.
     */
    public void getListaAtletas() {
        CompetitionDTO selectedComp = (CompetitionDTO) view.getCmbCompetitionsFilter().getSelectedItem();
        // Si no hay competición seleccionada, limpiamos la tabla
        if (selectedComp == null) {
            view.getTableAthletes().setModel(new DefaultTableModel());
            return;
        }

        // Obtiene la lista del modelo (usando el nombre corregido del método)
        List<AthleteDTO> atletas = model.getListaAtletasInscritos(selectedComp.getId());
        
        // Convierte a TableModel usando los nombres de las propiedades del DTO
        // IMPORTANTE: "dni", "name", "date" deben coincidir con los getters de AthleteDTO
        TableModel tmodel = SwingUtil.getTableModelFromPojos(atletas, new String[] { "dni", "name", "date" });
        view.getTableAthletes().setModel(tmodel);
        SwingUtil.autoAdjustColumns(view.getTableAthletes());

        // Como se guarda la clave del ultimo elemento seleccionado, restaura la seleccion
        this.restoreDetail();
    }

    /**
     * Restaura la informacion del detalle (selección) para visualizar los valores correspondientes
     * a la ultima clave almacenada.
     */
    public void restoreDetail() {
        // Utiliza la ultimo valor de la clave (que se reiniciara si ya no existe en la tabla)
        this.lastSelectedKey = SwingUtil.selectAndGetSelectedKey(view.getTableAthletes(), this.lastSelectedKey);
        
        // Si hay clave para seleccionar en la tabla actualiza el detalle
        if (!"".equals(this.lastSelectedKey)) {
            this.updateDetail();
        }
    }

    /**
     * Al seleccionar un item de la tabla actualiza la clave seleccionada.
     */
    public void updateDetail() {
        // Obtiene la clave seleccinada y la guarda para recordar la seleccion en futuras interacciones
        this.lastSelectedKey = SwingUtil.getSelectedKey(view.getTableAthletes());
    }

    /**
     * Método específico para la Historia de Usuario 1 (Inscripción).
     * Se invoca desde el ActionListener del botón de registro.
     */
    public void registerAthlete() {
        CompetitionDTO selectedComp = (CompetitionDTO) view.getCmbCompetitions().getSelectedItem();
        String dni = view.getTxtDni().getText();
        String name = view.getTxtName().getText();

        if (selectedComp == null) {
            throw new ApplicationException("Debe seleccionar una competición");
        }
        
        // Llamada al modelo usando el nombre corregido 'inscribirAtleta'
        model.inscribirAtleta(selectedComp.getId(), dni, name);

        // Feedback al usuario
        JOptionPane.showMessageDialog(view.getFrame(), 
                "Inscripción realizada con éxito", 
                "Info", JOptionPane.INFORMATION_MESSAGE);
        
        // Limpiamos campos
        view.getTxtDni().setText("");
        view.getTxtName().setText("");
        
        // Si estamos viendo la misma competición en la pestaña de organizador, refrescamos la lista
        getListaAtletas(); 
    }
}