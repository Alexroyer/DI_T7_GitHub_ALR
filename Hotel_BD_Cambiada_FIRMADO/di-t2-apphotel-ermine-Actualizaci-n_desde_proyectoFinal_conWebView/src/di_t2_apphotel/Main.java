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
        
        // Cargamos la ventana con su fxml
        StackPane rootMain = new StackPane();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HotelView.fxml"));
        Parent rootHotelView = fxmlLoader.load();
        rootMain.getChildren().add(rootHotelView);
        
        // Cargamos la base de datos y la guardamos en la variable 'em'
        emf = Persistence.createEntityManagerFactory("AppHotelPU");
        em = emf.createEntityManager();
            
        // Cargamos el hotelView y le pasamos el entityManager
        HotelViewController hotelView = (HotelViewController)fxmlLoader.getController();
        hotelView.setEntityManager(em);
        
        Scene scene = new Scene(rootMain);
        stage.setTitle("Nehalem Hotel");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
    
}