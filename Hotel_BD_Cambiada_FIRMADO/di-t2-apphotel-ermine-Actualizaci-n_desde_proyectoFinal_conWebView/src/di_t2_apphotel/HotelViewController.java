package di_t2_apphotel;


import webviewsample.Browser;



import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;



public class HotelViewController implements Initializable {

    @FXML
    private Menu menuCli;
    @FXML
    private MenuItem submenuCli;
    @FXML
    private Menu menuRes;
    @FXML
    private MenuItem submenuHab;
    @FXML
    private MenuItem submenuSal;
    @FXML
    private Menu menuAyu;
    @FXML
    private MenuItem submenuAyu;

    private EntityManager entityManager;
    public static Connection conexion = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conectaBD();
        // TODO
    }

    @FXML
    private void onActionButtonHabitaciones(ActionEvent event) {

        try {
            // Cargar la vista de detalle
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLHabitaciones.fxml"));
            Pane rootMain = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Habitaciones");

            // Cargamos la ventana de reserva de habitaciones y le pasamos la conexion de la base de datos para que pueda darle uso
            FXMLHabitacionesController habitacionesController = (FXMLHabitacionesController) fxmlLoader.getController();
            habitacionesController.setEntityManager(entityManager);

            //Configura la ventana
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(rootMain));
            stage.setResizable(false);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(HotelViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onActionButtonSalon(ActionEvent event) {

        try {
            // Cargar la vista de detalle
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLSalonHabana.fxml"));
            Pane rootMain = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Salon Habana");

            // Cargamos la ventana de reserva de habitaciones y le pasamos la conexion de la base de datos para que pueda darle uso
            FXMLSalonHabanaController salonController = (FXMLSalonHabanaController) fxmlLoader.getController();
            salonController.setEntityManager(entityManager);

            //Configura la ventana
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(rootMain));
            stage.setResizable(false);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(HotelViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private Scene scene;
    public Stage CreaEscenaWebViewSample(){
        // create scene
        Stage stage = new Stage();
        stage.setTitle("Ayuda APP Hotel");
        scene = new Scene(new webviewsample.Browser(), 950, 500, Color.web("#666970"));
        stage.setScene(scene);

        

        return stage;
    }
    
    @FXML
    private void cargarHelp(ActionEvent event) {
        System.out.println("Cargando WebView");
        
        Stage escenarioWebView = CreaEscenaWebViewSample();
        escenarioWebView.setScene(scene);
        escenarioWebView.show();
        
    }

    @FXML
    private void listadoClientes(ActionEvent event) {
        try {
            JasperReport jr = (JasperReport) JRLoader.loadObject(HotelViewController.class.getResource("Clientes.jasper"));

            JasperPrint jp = (JasperPrint) JasperFillManager.fillReport(jr, null, conexion);
            JasperViewer.viewReport(jp);
        } catch (JRException ex) {
            System.out.println("Error al recuperar el jasper");
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    @FXML
    private void ListadoReservaHabitaciones(ActionEvent event) {
        try {
            JasperReport jr = (JasperReport) JRLoader.loadObject(HotelViewController.class.getResource("ReservaHabitaciones.jasper"));

            JasperPrint jp = (JasperPrint) JasperFillManager.fillReport(jr, null, conexion);
            JasperViewer.viewReport(jp);
        } catch (JRException ex) {
            System.out.println("Error al recuperar el jasper");
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    @FXML
    private void listadoReservaSalonHabana(ActionEvent event) {
        try {
            JasperReport jr = (JasperReport) JRLoader.loadObject(HotelViewController.class.getResource("Listado_ReservasSalon.jasper"));

            JasperPrint jp = (JasperPrint) JasperFillManager.fillReport(jr, null, conexion);
            JasperViewer.viewReport(jp);
        } catch (JRException ex) {
            System.out.println("Error al recuperar el jasper");
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void conectaBD() {
        //Establecemos conexión con la BD
        String baseDatos = "jdbc:hsqldb:hsql://localhost:9001/xdb";
        String usuario = "sa";
        String clave = "";
        try {
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
            conexion = DriverManager.getConnection(baseDatos, usuario, clave);
        } catch (ClassNotFoundException cnfe) {
            System.err.println("Fallo al cargar JDBC");
            System.exit(1);
        } catch (SQLException sqle) {
            System.err.println("No se pudo conectar a BD");
            System.exit(1);
        } catch (java.lang.InstantiationException sqlex) {
            System.err.println("Imposible Conectar");
            System.exit(1);
        } catch (Exception ex) {
            System.err.println("Imposible Conectar");
            System.exit(1);
        }
    }
}
