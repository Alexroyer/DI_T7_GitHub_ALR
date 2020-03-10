package di_t2_apphotel;

import entidades.Cliente;
import entidades.Reserva;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import temporizador.Temporizador;

public class FXMLHabitacionesController implements Initializable {
    @FXML
    private TextField textFieldDNI;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldDireccion;
    @FXML
    private TextField textFieldLocalidad;
    @FXML
    private ComboBox<String> textFieldProvincia;
    @FXML
    private DatePicker datePickerLlegada;
    @FXML
    private DatePicker datePickerSalida;
    @FXML
    private ComboBox<String> comboBoxNumero;
    @FXML
    private ComboBox<String> comboBoxTipo;
    @FXML
    private RadioButton radioButtonAlojamiento;
    @FXML
    private RadioButton radioButtonMedia;
    @FXML
    private RadioButton radioButtonCompleta;
    @FXML
    private CheckBox checkBoxFumador;
    private AnchorPane rootHabitaciones;
    public Button closeButton;
    private Pane rootHotelView;
    private Cliente cliente;
    private Reserva reserva;
    private EntityManager entityManager;
    private boolean nuevoCliente;
    private boolean continuarAlojamiento, continuarTipo, continuarNumero;
    private static final char Desayuno = 'D';
    private static final char Media = 'M';
    private static final char Completa = 'C';
    
    private boolean finish=false;

    // Diferentes arrays para cada los datos de los comboBox
    ObservableList<String> num = FXCollections.observableArrayList("1", "2", "3", "4", "5");
    ObservableList<String> hab = FXCollections.observableArrayList("Individual", "Doble", "Suite");
    ObservableList<String> pro = FXCollections.observableArrayList("ÁLAVA", "ALBACETE", "ALICANTE", "ALMERIA", "AVILA", "Asturias", "BADAJOZ", "BALEARES", "BARCELONA", "Burgos", "Cáceres", "Cádiz", " Cantabria", " Castellón", "Ciudad Real", "Córdoba", "Cuenca", "Gerona", "Granada", "Guadalajara", "Guipúzcoa", "Huelva", "Huesca", "Islas Baleares", "Jaén", " La Coruña", "La Rioja", "Las Palmas", "León", "Lérida", "Lugo", "Madrid", "Málaga", "Murcia", "Navarra", "Orense", "Palencia", "Pontevedra", "Salamanca", "Segovia", "Sevilla", "Soria", "Tarragona", "Tenerife", "Teruel", "Toledo", "Valencia", "Valladolid", "Vizcaya", "Zamora", "Zaragoza");

    @FXML
    private ToggleGroup regimen;
    @FXML
    private Button botonLimpiar;
    @FXML
    private Button botonAceptar;
    @FXML
    private Label textFieldTexto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBoxNumero.getItems().addAll(num);
        comboBoxTipo.getItems().addAll(hab);
        textFieldProvincia.getItems().addAll(pro);

        datePickerSalida.setValue(LocalDate.now());
        datePickerLlegada.setValue(LocalDate.now());
        
        /* Tooltips */
        final Tooltip tooltipDNI = new Tooltip();
        tooltipDNI.setText("Introduce DNI para comprobar tus datos.");
        textFieldDNI.setTooltip(tooltipDNI);
        final Tooltip tooltipNombre = new Tooltip();
        tooltipNombre.setText("Introduce tu nombre.");
        textFieldNombre.setTooltip(tooltipNombre);
        final Tooltip tooltipDireccion = new Tooltip();
        tooltipDireccion.setText("Introduce tu dirección de vivienda habitual");
        textFieldDireccion.setTooltip(tooltipDireccion);
        final Tooltip tooltipLocalidad = new Tooltip();
        tooltipLocalidad.setText("Introduce tu localidad");
        textFieldLocalidad.setTooltip(tooltipLocalidad);
        final Tooltip tooltipProvincia = new Tooltip();
        tooltipProvincia.setText("Escoge tu provincia de residencia.");
        textFieldProvincia.setTooltip(tooltipProvincia);
        final Tooltip tooltipFllegada = new Tooltip();
        tooltipFllegada.setText("Introduce la fecha de llegada.");
        datePickerLlegada.setTooltip(tooltipFllegada);
        final Tooltip tooltipFsalida = new Tooltip();
        tooltipFsalida.setText("Introduce la fecha de salida.");
        datePickerSalida.setTooltip(tooltipFsalida);
        final Tooltip tooltipNumHab = new Tooltip();
        tooltipNumHab.setText("Elige el número de habitaciones.");
        comboBoxNumero.setTooltip(tooltipNumHab);
        final Tooltip tooltipTipoHab = new Tooltip();
        tooltipTipoHab.setText("Elige el tipo de habitaciones.");
        comboBoxTipo.setTooltip(tooltipTipoHab);
        final Tooltip tooltipRegimen = new Tooltip();
        tooltipRegimen.setText("Elige el régimen de alojamiento.");
        radioButtonAlojamiento.setTooltip(tooltipRegimen);
        radioButtonMedia.setTooltip(tooltipRegimen);
        radioButtonCompleta.setTooltip(tooltipRegimen);
        final Tooltip tooltipFumador = new Tooltip();
        tooltipFumador.setText("Indícanos si eres fumador.");
        checkBoxFumador.setTooltip(tooltipFumador);
        final Tooltip tooltipbtnLimpiar = new Tooltip();
        tooltipbtnLimpiar.setText("Borra todos los datos introducidos.");
        botonLimpiar.setTooltip(tooltipbtnLimpiar);
        final Tooltip tooltipbtnAceptar = new Tooltip();
        tooltipbtnAceptar.setText("Guarda los datos introducidos.");
        botonAceptar.setTooltip(tooltipbtnAceptar);
        final Tooltip tooltipbtnCancelar = new Tooltip();
        tooltipbtnCancelar.setText("Regresa a la ventana principal.");
        closeButton.setTooltip(tooltipbtnCancelar);

        // Funcion para controlar el deni que es introducido 
        textFieldDNI.setOnKeyPressed(new EventHandler<KeyEvent>() {
            
            @Override
            public void handle(KeyEvent e) {

                String dniRegexp = "(([X-Z]{1})([-]?)(\\d{7})([-]?)([A-Z]{1}))|((\\d{8})([-]?)([A-Z]{1}))";
                Pattern pat = Pattern.compile(dniRegexp);
                Matcher mat = pat.matcher(textFieldDNI.getText());

                if (mat.matches()){
                    String dni=textFieldDNI.getText();
                    Cliente cliente = entityManager.find(Cliente.class,dni);

                    if(cliente != null) {
                        textFieldNombre.setText(cliente.getNombre());
                        textFieldDireccion.setText(cliente.getDireccion());
                        textFieldLocalidad.setText(cliente.getLocalidad());
                        textFieldProvincia.setValue(cliente.getProvincia());
                        cliente.setTelefono(null);
                        checkBoxFumador.setDisable(false);
                        datePickerLlegada.setDisable(false);
                        datePickerSalida.setDisable(false);
                        checkBoxFumador.setDisable(false);
                        botonAceptar.setDisable(false);
                        comboBoxNumero.setDisable(false);
                        comboBoxTipo.setDisable(false);
                        radioButtonAlojamiento.setDisable(false);
                        radioButtonMedia.setDisable(false);
                        radioButtonCompleta.setDisable(false);
                        nuevoCliente = false;
                        comboBoxNumero.setValue("1");
                        comboBoxTipo.setValue("individual");
                    } else {
                        textFieldNombre.setText("");
                        textFieldDireccion.setText("");
                        textFieldLocalidad.setText("");
                        textFieldProvincia.setValue("");
                        textFieldTexto.setText("");
                        textFieldNombre.setDisable(false);
                        textFieldDireccion.setDisable(false);
                        textFieldLocalidad.setDisable(false);
                        textFieldProvincia.setDisable(false);
                        datePickerSalida.setDisable(false);
                        datePickerLlegada.setDisable(false);
                        comboBoxTipo.setDisable(false);
                        checkBoxFumador.setDisable(false);
                        botonAceptar.setDisable(false);
                        comboBoxNumero.setDisable(false);
                        comboBoxTipo.setDisable(false);
                        radioButtonAlojamiento.setDisable(false);
                        radioButtonMedia.setDisable(false);
                        radioButtonCompleta.setDisable(false);
                        nuevoCliente = true;
                        comboBoxNumero.setValue("1");
                        comboBoxTipo.setValue("individual");
                    }
                } else {
                    textFieldNombre.setText("");
                    textFieldDireccion.setText("");
                    textFieldLocalidad.setText("");
                    textFieldProvincia.setValue("");
                    textFieldTexto.setText("");
                    textFieldNombre.setDisable(true);
                    textFieldDireccion.setDisable(true);
                    textFieldLocalidad.setDisable(true);
                    textFieldProvincia.setDisable(true);
                    datePickerSalida.setDisable(true);
                    datePickerLlegada.setDisable(true);
                    checkBoxFumador.setDisable(true);
                    botonAceptar.setDisable(true);
                    comboBoxNumero.setDisable(true);
                    comboBoxTipo.setDisable(true);
                    radioButtonAlojamiento.setDisable(true);
                    radioButtonMedia.setDisable(true);
                    radioButtonCompleta.setDisable(true);
                    comboBoxNumero.setValue("1");
                    comboBoxTipo.setValue("individual");
               }
            }
        });
        
        
        datePickerLlegada.setOnAction(event -> {
            LocalDate today = LocalDate.now();

            if(datePickerLlegada.getValue().isBefore(today)){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText("Seleccione una fecha igual o posterior a hoy.");
                alert.showAndWait();
            }
        }); 

        

        datePickerSalida.setOnAction(event -> {
            if (datePickerSalida.getValue()!=null && datePickerLlegada.getValue()!=null){
                LocalDate localDate = datePickerLlegada.getValue();
                ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
                Instant instant = zonedDateTime.toInstant();
                Date llegada = Date.from(instant);

                localDate = datePickerSalida.getValue();
                zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
                instant = zonedDateTime.toInstant();
                Date salida = Date.from(instant);

                if (salida.before(llegada)){
                    datePickerSalida.setValue(null);
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setHeaderText("La fecha de salida tiene que ser posterior a la fecha de entrada.");
                    alert.showAndWait();
                }
            }
        }); 
        
    }
        
    public void setCliente(EntityManager entityManager, Cliente cliente) {
        this.entityManager = entityManager;
        entityManager.getTransaction().begin();
        this.cliente = cliente;
    }

    public void setReserva(EntityManager entityManager, Reserva reserva) {
        this.entityManager = entityManager;
        entityManager.getTransaction().begin();
        this.reserva = reserva;
    }

    @FXML
    private void onActionButtonLimpiar(ActionEvent event) {
        limpiar();
    }
    
    public void limpiar(){
        textFieldDNI.setText("");
        textFieldNombre.setText("");
        textFieldDireccion.setText("");
        textFieldLocalidad.setText("");
        textFieldProvincia.setValue("");
        textFieldTexto.setText("");
        
        datePickerLlegada.setValue(LocalDate.now());
        datePickerSalida.setValue(LocalDate.now());
        datePickerLlegada.setDisable(true);
        datePickerSalida.setDisable(true);
        comboBoxTipo.setDisable(true);
        comboBoxNumero.setDisable(true);
        checkBoxFumador.selectedProperty().set(false);
        radioButtonAlojamiento.selectedProperty().set(false);
        radioButtonCompleta.selectedProperty().set(false);
        radioButtonMedia.selectedProperty().set(false);
    }

    @FXML
    private void onActionButtonAceptar(ActionEvent event) {

        cliente = new Cliente();
        reserva = new Reserva();
        
        Date date=null;
        
        boolean confirmarNombre=false;
        boolean confirmarDirec=false;
        boolean confirmarLocalidad=false;
        boolean confirmarProvincia=false;
        boolean confirmarFechaEntrada=false;
        boolean confirmarFechaSalida=false;        
        boolean confirmarHabitaciones=false;
        boolean confirmarTipoHab=false;
        boolean confirmarRegimen=false;

        try {
            cliente.setDni(textFieldDNI.getText());
            cliente.setDireccion(textFieldDireccion.getText());
            cliente.setLocalidad(textFieldLocalidad.getText());
            cliente.setNombre(textFieldNombre.getText());
            cliente.setProvincia(textFieldProvincia.getValue());
            cliente.setTelefono(null);

            if(textFieldDireccion.getText().equals("")){
                System.out.println("di_t2_apphotel.FXMLHabitacionesController.onActionButtonAceptar()");
                confirmarDirec=true;
                continuarNumero = false;
            }
            
            if(textFieldNombre.getText().equals("")){
                confirmarNombre=true;
                continuarNumero = false;
            }
            
            // Fecha llegada
            if (datePickerLlegada.getValue() != null) {
                LocalDate localDate = datePickerLlegada.getValue();
                ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
                Instant instant = zonedDateTime.toInstant();
                date = Date.from(instant);
                reserva.setFechaEntrada(date);
            } else {
                confirmarFechaEntrada=true;
                continuarNumero = false;
                reserva.setFechaEntrada(null);
            }

            // Fecha salida
            if (datePickerSalida.getValue() != null) {
                LocalDate localDate = datePickerSalida.getValue();
                ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
                Instant instant = zonedDateTime.toInstant();
                Date date1 = Date.from(instant);
                if(date!=null&&date.compareTo(date1)>0){
                    confirmarFechaSalida=true;
                    continuarNumero = false;
                }else
                    reserva.setFechaSalida(date1);
            } else {
                confirmarFechaSalida=true;
                continuarNumero = false;
                reserva.setFechaSalida(null);
            }

            // Checkbox fumador
            if (checkBoxFumador.isSelected()) {
                reserva.setFumador(true);
            } else {
                reserva.setFumador(false);
            }

            // Regimen
            if (radioButtonAlojamiento.isSelected()) {
                reserva.setRegimen(Desayuno);
                continuarAlojamiento = true;
            } else if (radioButtonMedia.isSelected()) {
                reserva.setRegimen(Media);
                continuarAlojamiento = true;
            } else if (radioButtonCompleta.isSelected()) {
                reserva.setRegimen(Completa);
                continuarAlojamiento = true;
            } else {
                confirmarRegimen=true;
                continuarAlojamiento = false;
            }

            
            // Aladir daros a la reserva
            if(comboBoxTipo.getValue() == "Individual") {
                reserva.setTipoHab(comboBoxTipo.getValue());
                continuarTipo = true;
            } else if(comboBoxTipo.getValue() == "Doble") {
                reserva.setTipoHab(comboBoxTipo.getValue());
                continuarTipo = true;
            } else if(comboBoxTipo.getValue() == "Suite"){
                reserva.setTipoHab(comboBoxTipo.getValue());
                continuarTipo = true;
            } else {
                confirmarTipoHab=true;
                continuarTipo = false;
            }
            
            
            if(comboBoxNumero.getValue() == null) {
                confirmarHabitaciones=true;
                continuarNumero = false;
            } else if (Integer.parseInt(comboBoxNumero.getValue()) > 0 && Integer.parseInt(comboBoxNumero.getValue()) <= 50) {
                reserva.setNumHab(Integer.parseInt(comboBoxNumero.getValue()));
                continuarNumero = true;
            }
            
            
            reserva.setDni(cliente);
            
            if(continuarAlojamiento && continuarTipo && continuarNumero) {
                // Crea el nuevo cliente si no existe
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION, "¿Está seguro de querer enviar los datos de la reserva?",ButtonType.YES, ButtonType.NO);
                alerta.setHeaderText("Finalizar reserva");
                Optional<ButtonType> result = alerta.showAndWait();
                if(result.get() == ButtonType.YES){
                    if (nuevoCliente) {
                        setCliente(this.entityManager, cliente);
                        entityManager.persist(cliente);
                        entityManager.getTransaction().commit();
                    }

                    // Crea la reserva
                    setReserva(this.entityManager, reserva);
                    entityManager.persist(reserva);
                    entityManager.getTransaction().commit();

                     // Alerta popup que aparecera cuando se haya completado la reserva 
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setHeaderText("Se ha realizado la reserva correctamente");
                    alert.showAndWait();
                    limpiar();
                }
            } else{
                Alert alert;
                alert= new Alert(AlertType.INFORMATION,"Los siguientes datos no estan bien introducidos \n"+((confirmarNombre)?"\n-Nombre":"")+((confirmarDirec)?"\n-Direccion":"")+
                        ((confirmarLocalidad)?"\n-Localidad":"")+((confirmarProvincia)?"\n-Provincia":"")+((confirmarFechaEntrada)?"\n-Fecha de entrada":"")+((confirmarFechaSalida)?"\n-Fecha salida":"")+
                        ((confirmarRegimen)?"\n-Regimen":"")+((confirmarHabitaciones)?"\n-Numero de Habitaciones":"")+((confirmarTipoHab)?"\n-Tipo de Habitaciones":""));
                alert.showAndWait();
            }

        } catch (RollbackException ex) {
            // Los datos introducidos no cumplen los requisitos de la BD
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText("No se han podido guardar los cambios. "
                    + "Compruebe que los datos cumplen los requisitos");
            alert.setContentText(ex.getLocalizedMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void onActionButtonCancelar(ActionEvent event) {
        Alert alerta_cancelarHabitaciones = new Alert(Alert.AlertType.CONFIRMATION, "¿Está seguro de querer salir de la reserva de habitaciones? Los datos se perderán.",ButtonType.YES, ButtonType.NO);
        alerta_cancelarHabitaciones.setHeaderText("Cancelar Reserva");
        Optional<ButtonType> result = alerta_cancelarHabitaciones.showAndWait();
        
        if (result.get() == ButtonType.YES){
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        }
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @FXML
    private void keyPressed(MouseEvent event) {
    }

}
