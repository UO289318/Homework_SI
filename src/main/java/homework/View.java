package homework;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;

/**
 * Vista de la pantalla de registro de inscripciones.
 * <br/>Sigue el patrón de CarrerasView pero adaptado a las dos historias de usuario
 * mediante el uso de pestañas (JTabbedPane).
 */
public class View {

    private JFrame frame;
    
    // Componentes Pestaña 1 (Atleta)
    private JComboBox<Object> cmbCompetitions;
    private JTextField txtDni;
    private JTextField txtName;
    private JButton btnRegister;

    // Componentes Pestaña 2 (Organizador)
    private JComboBox<Object> cmbCompetitionsFilter;
    private JButton btnRefresh;
    private JTable tabAthletes;

    /**
     * Create the application.
     */
    public View() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Inscripción de Carreras");
        frame.setName("InscripcionCarreras");
        frame.setBounds(0, 0, 600, 400);
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        // Usamos un panel de pestañas para separar las historias de usuario
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

        // --- PESTAÑA 1: ATLETA ---
        JPanel panelAtleta = new JPanel();
        tabbedPane.addTab("Atleta: Inscripción", null, panelAtleta, null);
        panelAtleta.setLayout(new GridLayout(0, 1, 10, 10)); // Grid vertical simple
        
        // Panel auxiliar para agrupar selección de carrera
        JPanel panelCarrera = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCarrera.add(new JLabel("Selecciona Competición:"));
        cmbCompetitions = new JComboBox<>();
        // Asignamos nombre para testing automatico
        cmbCompetitions.setName("cmbCompetitions"); 
        cmbCompetitions.setPreferredSize(new java.awt.Dimension(300, 25));
        panelCarrera.add(cmbCompetitions);
        panelAtleta.add(panelCarrera);

        // Panel auxiliar para DNI
        JPanel panelDni = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelDni.add(new JLabel("DNI del Atleta:           ")); // Espacios para alinear simple
        txtDni = new JTextField();
        txtDni.setName("txtDni");
        txtDni.setColumns(15);
        panelDni.add(txtDni);
        panelAtleta.add(panelDni);

        // Panel auxiliar para Nombre
        JPanel panelNombre = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelNombre.add(new JLabel("Nombre Completo:      "));
        txtName = new JTextField();
        txtName.setName("txtName");
        txtName.setColumns(25);
        panelNombre.add(txtName);
        panelAtleta.add(panelNombre);

        // Botón de registro
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnRegister = new JButton("Realizar Inscripción");
        btnRegister.setName("btnRegister");
        panelBoton.add(btnRegister);
        panelAtleta.add(panelBoton);

        // --- PESTAÑA 2: ORGANIZADOR ---
        JPanel panelOrganizador = new JPanel();
        tabbedPane.addTab("Organizador: Listados", null, panelOrganizador, null);
        panelOrganizador.setLayout(new BorderLayout(0, 0));

        // Panel superior con filtros
        JPanel panelFilter = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFilter.add(new JLabel("Ver inscritos en: "));
        
        cmbCompetitionsFilter = new JComboBox<>();
        cmbCompetitionsFilter.setName("cmbCompetitionsFilter");
        cmbCompetitionsFilter.setPreferredSize(new java.awt.Dimension(250, 25));
        panelFilter.add(cmbCompetitionsFilter);
        
        btnRefresh = new JButton("Actualizar Lista");
        btnRefresh.setName("btnRefresh");
        panelFilter.add(btnRefresh);
        
        panelOrganizador.add(panelFilter, BorderLayout.NORTH);

        // Tabla central con Scroll
        tabAthletes = new JTable();
        tabAthletes.setName("tabAthletes");
        tabAthletes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabAthletes.setDefaultEditor(Object.class, null); // readonly
        JScrollPane tablePanel = new JScrollPane(tabAthletes);
        panelOrganizador.add(tablePanel, BorderLayout.CENTER);
    }

    // Getters y Setters para acceso desde el controlador
    
    public JFrame getFrame() { return this.frame; }
    
    // Métodos para la Pestaña de Atleta
    public JComboBox<Object> getCmbCompetitions() { return this.cmbCompetitions; }
    public JTextField getTxtDni() { return this.txtDni; }
    public JTextField getTxtName() { return this.txtName; }
    public JButton getBtnRegister() { return this.btnRegister; }

    // Métodos para la Pestaña de Organizador
    public JComboBox<Object> getCmbCompetitionsFilter() { return this.cmbCompetitionsFilter; }
    public JButton getBtnRefresh() { return this.btnRefresh; }
    public JTable getTableAthletes() { return this.tabAthletes; }
}