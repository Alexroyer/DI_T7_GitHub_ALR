package di_t2_apphotel;


import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Main extends Application {
    
    private EntityManagerFactory emf;
    private EntityManager em;
    
    @Override
    public void start(Stage stage) throws Exception {
        
        
        
        // INICIO LOGGIN
        
        
        StackPane rootMain = new StackPane();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Examen_UserPassword.fxml"));
        Parent rootHotelView = fxmlLoader.load();
        rootMain.getChildren().add(rootHotelView);
        
        
        emf = Persistence.createEntityManagerFactory("AppHotelPU");
        em = emf.createEntityManager();
            
        // Cargamos el hotelView y le pasamos el entityManager
        Examen_UserPasswordController vistaUsuarioContrasena = (Examen_UserPasswordController)fxmlLoader.getController();
        
        
        
        vistaUsuarioContrasena.setEntityManager(em);
        
        Scene scene = new Scene(rootMain);
        stage.setTitle("Nehalem Hotel");
        stage.setScene(scene);
        stage.show();
        
        //FIN LOGGIN
    }


    public static void main(String[] args) {
        launch(args);
    }
    
}