/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package di_t2_apphotel;

import static di_t2_apphotel.HotelViewController.conexion;
import java.io.IOException;
import static java.lang.System.exit;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.persistence.EntityManager;

/**
 * FXML Controller class
 *
 * @author Alejandro
 */
public class Examen_UserPasswordController implements Initializable {
    private EntityManager entityManager;
    public static Connection conexion = null;
    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane anchor_imagen;
    @FXML
    private AnchorPane anchor_formulario;
    @FXML
    private Label login;
    @FXML
    private Label username;
    @FXML
    private Label password;
    @FXML
    private TextField username_tf;
    @FXML
    private PasswordField password_tf;
    @FXML
    private ImageView imageview;
    @FXML
    private Button enterID;
    @FXML
    private Button exitID;

    private String usuarioCorrecto = "admin";
    private String contraseñaCorrecta = "admin";

    Stage ventanaExamen;
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        

    }
    
    public void recibirVentana(Scene scene){
        ventanaExamen.setScene(scene);
    }
    

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    //GESTION ON KEYPRESSED DEL BOTÓN ENTER
    @FXML
    private void onKeyEnterPressed(KeyEvent event) throws IOException {
        gestionCredenciales();
    }
    
    //GESTION PULSACIÓN BOTON ENTER
    @FXML
    private void onActionEnter(ActionEvent event) throws IOException {
        
        gestionCredenciales();
        
    }    

    //GESTION ON KEYPRESSED DEL BOTÓN EXIT
    @FXML
    private void onKeyExitPressed(KeyEvent event) {
        exit(0);
    }    
    
    //GESTION BOTÓN ONEXIT
    @FXML
    private void onActionExit(ActionEvent event) {
        exit(0);
    }

    //GESTION DEL KeyPressedTextField DE CONTRASEÑA
    @FXML
    private void onKeyPressed(ActionEvent event) throws IOException {

        gestionCredenciales();
       
        
    }
    
        
    private void gestionCredenciales() throws IOException{
                if( username_tf.getText().equals(usuarioCorrecto) && password_tf.getText().equals(contraseñaCorrecta) ){
            
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HotelView.fxml"));
            Pane rootMain = fxmlLoader.load();
            Stage stage = (Stage) enterID.getScene().getWindow();
            stage.setTitle("Hotel");
            

            
            HotelViewController vistaHotel = (HotelViewController) fxmlLoader.getController();

            vistaHotel.setEntityManager(entityManager);

            //CON ESTE MÉTODO DEFINIDO ABAJO, LE PASAMOS LA CONEXIÓN A LA VENTANA PRINCIPAL
            //DEL HOTEL
            conectaBD();
            
            
            stage.setScene(new Scene(rootMain));
            stage.setResizable(false);
            
            stage.show();
            
            
            
            
        } else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Compruebe su nombre de usuario y contraseña. Si sigue sin poder iniciar sesión, póngase en contacto con su administrador de AppHotel");
                alert.showAndWait();
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
