package di_t2_apphotel;

import entidades.Cliente;

import entidades.Reservahotelhabana;

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

import javafx.beans.value.ObservableValue;

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

import javafx.scene.control.ChoiceBox;

import javafx.scene.control.DatePicker;

import javafx.scene.control.Label;

import javafx.scene.control.RadioButton;

import javafx.scene.control.TextField;

import javafx.scene.control.Toggle;

import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;

import javafx.scene.input.KeyEvent;

import javafx.scene.layout.Pane;

import javafx.scene.paint.Color;

import javafx.scene.text.Font;

import javafx.stage.Stage;

import javax.persistence.EntityManager;

import javax.persistence.RollbackException;
import temporizador.Temporizador;

/**
 *
 * FXML Controller class
 *
 *
 *
 * @author usuario
 *
 */
public class FXMLSalonHabanaController implements Initializable {

    private Pane rootSalonHabana;
    private Pane rootHotelView;
    private EntityManager entityManager;
    @FXML
    private Button closeButton;
    @FXML
    private TextField textFieldDNI;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldDireccion;
    @FXML
    private TextField textFieldtlf;
    @FXML
    private RadioButton radioButtonBanquete;
    @FXML
    private RadioButton radioButtonJornada;
    @FXML
    private RadioButton radioButtonCongreso;
    @FXML
    private TextField numeroPersonas;
    @FXML
    private CheckBox checkBoxHabitaciones;
    @FXML
    private ChoiceBox<String> comboBoxCocina;
    @FXML
    private TextField numeroHabitaciones;
    @FXML
    private DatePicker dataPickerEvento;
    @FXML
    private TextField numeroDias;
    @FXML
    private Button botonLimpiar;
    @FXML
    private Button botonAceptar;
    @FXML
    private ToggleGroup regimenSalon;
    @FXML
    private Label etNumHabitaciones;

    @FXML
    private Label etNumDias;
    private boolean finish = false;
    private Cliente cliente;
    Reservahotelhabana reserva;
    private boolean nuevoCliente = true;
    private boolean telefonoCliente1;
    private boolean compTelefono;
    private boolean guardar = true;
    private int nPersonas;
    @FXML
    private Label labelTipo;
    @FXML
    private Label Labeltext;

    ObservableList<String> coci = FXCollections.observableArrayList("Bufé (Vegetariano o No)", "Carta", "Pedir cita con el chef", "No precisa");
    @FXML
    private Temporizador temporizador;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        comboBoxCocina.getItems().addAll(coci);
        comboBoxCocina.setValue("No precisa");
        final Tooltip tooltipDNI = new Tooltip();
        tooltipDNI.setText("Introduce DNI para comprobar tus datos.");
        textFieldDNI.setTooltip(tooltipDNI);
        final Tooltip tooltipNombre = new Tooltip();
        tooltipNombre.setText("Introduce tu nombre.");
        textFieldNombre.setTooltip(tooltipNombre);
        final Tooltip tooltipDireccion = new Tooltip();
        tooltipDireccion.setText("Introduce tu dirección de vivienda habitual");
        textFieldDireccion.setTooltip(tooltipDireccion);
        final Tooltip tooltipTlf = new Tooltip();
        tooltipTlf.setText("Introduce tu telefono movil");
        textFieldtlf.setTooltip(tooltipTlf);
        final Tooltip tooltipRegimen = new Tooltip();
        tooltipRegimen.setText("Introduce el tipo de evento");
        radioButtonBanquete.setTooltip(tooltipRegimen);
        radioButtonCongreso.setTooltip(tooltipRegimen);
        radioButtonJornada.setTooltip(tooltipRegimen);
        final Tooltip tooltipFecha = new Tooltip();
        tooltipFecha.setText("Introduce una fecha para el evento");
        dataPickerEvento.setTooltip(tooltipFecha);
        final Tooltip tooltipnumPers = new Tooltip();
        tooltipnumPers.setText("Introduce las personas que acudiran al evento");
        numeroPersonas.setTooltip(tooltipnumPers);
        final Tooltip tooltipHab = new Tooltip();
        tooltipHab.setText("Si eleige congreso tiene la opción de elegir habitaciones");
        checkBoxHabitaciones.setTooltip(tooltipHab);
        final Tooltip tooltipNumhab = new Tooltip();
        tooltipNumhab.setText("Introduce el numero de habitaciones");
        numeroHabitaciones.setTooltip(tooltipNumhab);
        final Tooltip tooltipDias = new Tooltip();
        tooltipDias.setText("Introduce el numero de dias que se alojaran en las habitaciones");
        numeroDias.setTooltip(tooltipDias);
        final Tooltip tooltipTipoCocina = new Tooltip();
        tooltipTipoCocina.setText("Introduce el tipo de cocina que desees");
        comboBoxCocina.setTooltip(tooltipTipoCocina);
        // Funcion para controlar el dni que es introducido 
        textFieldDNI.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent e) {
                String dniRegexp = "(([X-Z]{1})([-]?)(\\d{7})([-]?)([A-Z]{1}))|((\\d{8})([-]?)([A-Z]{1}))";
                Pattern pat = Pattern.compile(dniRegexp);
                Matcher mat = pat.matcher(textFieldDNI.getText());
                telefonoCliente1 = false;
                if (mat.matches()) {
                    String dni = textFieldDNI.getText();
                    cliente = entityManager.find(Cliente.class, dni);
                    if (cliente != null) {
                        textFieldNombre.setText(cliente.getNombre());
                        textFieldDireccion.setText(cliente.getDireccion());
                        if (cliente.getTelefono() == null) {
                            textFieldtlf.setDisable(false);
                            telefonoCliente1 = true;
                        } else {
                            textFieldtlf.setText(cliente.getTelefono());
                            telefonoCliente1 = false;
                        }
                        numeroPersonas.setDisable(false);
                        comboBoxCocina.setDisable(false);
                        radioButtonBanquete.setDisable(false);
                        radioButtonJornada.setDisable(false);
                        radioButtonCongreso.setDisable(false);
                        dataPickerEvento.setDisable(false);
                        botonAceptar.setDisable(false);
                        nuevoCliente = false;
                        dataPickerEvento.setValue(LocalDate.now());
                    } else {
                        dataPickerEvento.setValue(LocalDate.now());
                        textFieldNombre.setText("");
                        textFieldDireccion.setText("");
                        textFieldtlf.setText("");
                        textFieldNombre.setDisable(false);
                        textFieldDireccion.setDisable(false);
                        textFieldtlf.setDisable(false);
                        numeroPersonas.setDisable(false);
                        comboBoxCocina.setDisable(false);
                        radioButtonBanquete.setDisable(false);
                        radioButtonJornada.setDisable(false);
                        radioButtonCongreso.setDisable(false);
                        dataPickerEvento.setDisable(false);
                        botonAceptar.setDisable(false);
                        nuevoCliente = true;
                    }
                } else {
                    dataPickerEvento.setValue(LocalDate.now());
                    textFieldNombre.setText("");
                    textFieldDireccion.setText("");
                    textFieldtlf.setText("");
                    textFieldNombre.setDisable(true);
                    textFieldDireccion.setDisable(true);
                    textFieldtlf.setDisable(true);
                    numeroPersonas.setDisable(true);
                    comboBoxCocina.setDisable(true);
                    radioButtonBanquete.setDisable(true);
                    radioButtonJornada.setDisable(true);
                    radioButtonCongreso.setDisable(true);
                    //inicioRadioButton();
                    dataPickerEvento.setDisable(true);
                    botonAceptar.setDisable(true);
                }
            }
        });
        radioButtonCongreso.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                checkBoxHabitaciones.setDisable(false);
                labelTipo.setText("Tipo: Congreso");
                labelTipo.setTextFill(Color.RED);
                Labeltext.setTextFill(Color.RED);
            }
        });

        radioButtonBanquete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                checkBoxHabitaciones.setDisable(true);
                labelTipo.setText("Tipo: Banquete");
                labelTipo.setTextFill(Color.DARKCYAN);
                Labeltext.setTextFill(Color.DARKCYAN);
                numeroDias.setDisable(true);
                numeroHabitaciones.setDisable(true);
                numeroDias.setText("");
                numeroHabitaciones.setText("");
                checkBoxHabitaciones.setSelected(false);
            }
        });

        radioButtonJornada.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                checkBoxHabitaciones.setDisable(true);
                numeroDias.setDisable(true);
                numeroHabitaciones.setDisable(true);
                labelTipo.setText("Tipo: Jornada");
                labelTipo.setTextFill(Color.BLUEVIOLET);
                Labeltext.setTextFill(Color.BLUEVIOLET);
                numeroDias.setText("");
                numeroHabitaciones.setText("");
                checkBoxHabitaciones.setSelected(false);
            }

        });

        checkBoxHabitaciones.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (checkBoxHabitaciones.isSelected()) {
                    numeroDias.setDisable(false);
                    numeroHabitaciones.setDisable(false);
                } else {
                    numeroDias.setText("");
                    numeroHabitaciones.setText("");
                    numeroDias.setDisable(true);
                    numeroHabitaciones.setDisable(true);
                }
            }
        });

        temporizador.setTiempo(60, false);
        temporizador.setMouseTransparent(true);

        temporizador.addEventHandler(EventType.ROOT, eventHandler
                -> {
            if (!finish) {
                if (eventHandler.getSource().getClass() == temporizador.getClass()) {
                    alertWarning();
                }
            }
        });
    }

    public void alertWarning() {
        Alert warning = new Alert(AlertType.WARNING);
        warning.setContentText("El tiempo de sesion ha expirado");
        warning.show();
        botonAceptar.setDisable(true);
    }

    public void setRootSalonHabana(Pane rootSalonHabana) {
        this.rootSalonHabana = rootSalonHabana;
    }

    public void setCliente(EntityManager entityManager, Cliente cliente) {
        this.entityManager = entityManager;
        entityManager.getTransaction().begin();
        this.cliente = cliente;
    }

    public void setReservahotelhabana(EntityManager entityManager, Reservahotelhabana reserva) {
        this.entityManager = entityManager;
        entityManager.getTransaction().begin();
        this.reserva = reserva;
    }

    @FXML
    public void onActionButtonAceptar() {
        Cliente cliente2 = new Cliente();
        reserva = new Reservahotelhabana();
        boolean confimarNombre = false;
        boolean confimarDirec = false;
        boolean confimarTlf = false;
        boolean confimarEventos = false;
        boolean confimarNumPersonas = false;
        boolean confimarNumDias = false;
        boolean confimarNumHabitaciones = false;
        boolean confirmarFecha = false;
        guardar = true;
        //Compruebo nombre
        String tlfRegexp = "^(\\0034|34)?[6|7|8|9][0-9]{8}$";
        Pattern pat = Pattern.compile(tlfRegexp);
        Matcher mat = pat.matcher(textFieldtlf.getText());
        LocalDate today = LocalDate.now();

        //Compruebo nombre
        if (textFieldNombre.getText().equals("")) {
            confimarNombre = true;
            guardar = false;
        } //compruebo direccion
        if (textFieldDireccion.getText().equals("")) {
            confimarDirec = true;
            guardar = false;
        } //Comprueba numero tlf
        if (!mat.matches()) {
            confimarTlf = true;
            guardar = false;
        } //Compruebo si los radiobuttons estan seleccionados
        if (!radioButtonBanquete.isSelected() && !radioButtonCongreso.isSelected() && !radioButtonJornada.isSelected()) {
            confimarEventos = true;
            confimarNumPersonas = true;
            guardar = false;
        } //compruebo numero de personas de cada evento y si introduce un numero

        if (dataPickerEvento.getValue().isBefore(today)) {
            confirmarFecha = true;
            guardar = false;
        }

        if (radioButtonBanquete.isSelected()) {
            try {
                nPersonas = Integer.parseInt(numeroPersonas.getText());
                if (nPersonas > 100) {
                    confimarNumPersonas = true;
                    guardar = false;
                }
            } catch (Exception ex) {
                confimarNumPersonas = true;
                guardar = false;
            }
        }
        if (radioButtonJornada.isSelected()) {
            try {
                nPersonas = Integer.parseInt(numeroPersonas.getText());
                if (nPersonas > 50) {
                    confimarNumPersonas = true;
                    guardar = false;
                }
            } catch (Exception ex) {
                confimarNumPersonas = true;
                guardar = false;
            }
        }
        if (radioButtonCongreso.isSelected()) {
            try {
                nPersonas = Integer.parseInt(numeroPersonas.getText());
                if (nPersonas > 50) {
                    confimarNumPersonas = true;
                    guardar = false;
                }
                if (checkBoxHabitaciones.isSelected()) {
                    try {
                        nPersonas = Integer.parseInt(numeroDias.getText());
                    } catch (Exception ex) {
                        confimarNumDias = true;
                        guardar = false;
                    }
                    try {
                        nPersonas = Integer.parseInt(numeroHabitaciones.getText());
                    } catch (Exception ex) {
                        confimarNumHabitaciones = true;
                        guardar = false;
                    }
                }
            } catch (Exception ex) {
                confimarNumPersonas = true;
                guardar = false;
            }
        }

        try {
            if (guardar) {

                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION, "¿Está seguro de querer enviar los datos de la reserva?", ButtonType.YES, ButtonType.NO);
                alerta.setHeaderText("Finalizar reserva");
                Optional<ButtonType> result = alerta.showAndWait();
                if (result.get() == ButtonType.YES) {
                    cliente2.setDni(textFieldDNI.getText());
                    cliente2.setDireccion(textFieldDireccion.getText());
                    cliente2.setNombre(textFieldNombre.getText());
                    cliente2.setTelefono(textFieldtlf.getText());

                    if (nuevoCliente == true) {
                        cliente2.setProvincia("");
                        cliente2.setLocalidad("");
                    } else {
                        cliente.setTelefono(textFieldtlf.getText());
                    }

                    reserva.setDni(cliente2);
                    reserva.setTipoCocina(comboBoxCocina.getValue());

                    if (radioButtonBanquete.isSelected()) {
                        reserva.setEvento('B');
                    } else if (radioButtonJornada.isSelected()) {
                        reserva.setEvento('J');
                    } else {
                        reserva.setEvento('C');
                        if (checkBoxHabitaciones.isSelected()) {
                            reserva.setNecesita(true);
                            reserva.setNumHab(numeroHabitaciones.getText());
                            reserva.setNumDias(numeroDias.getText());
                        } else {
                            reserva.setNecesita(false);
                        }

                    }

                    if (dataPickerEvento.getValue() != null) {
                        LocalDate localDate = dataPickerEvento.getValue();
                        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
                        Instant instant = zonedDateTime.toInstant();
                        Date date = Date.from(instant);
                        reserva.setFechaEvento(date);
                    } else {
                        reserva.setFechaEvento(null);
                    }
                    reserva.setNumPers(numeroPersonas.getText());
                    // Crea el nuevo cliente si no existe
                    if (!nuevoCliente) {
                        setCliente(this.entityManager, cliente);
                        entityManager.merge(cliente);
                        entityManager.getTransaction().commit();
                    } else {
                        setCliente(this.entityManager, cliente2);
                        entityManager.persist(cliente2);
                        entityManager.getTransaction().commit();
                    }

                    setReservahotelhabana(this.entityManager, reserva);
                    entityManager.persist(reserva);
                    entityManager.getTransaction().commit();

                    // Alerta popup que aparecera cuando se haya completado la reserva 
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Se ha realizado la reserva correctamente");
                    alert.showAndWait();
                    temporizador.setTiempo(60, true);
                    limpiar();
                }
                //Stage stage = (Stage) closeButton.getScene().getWindow();
                //stage.close();

            } else {
                Alert alert;
                alert = new Alert(AlertType.INFORMATION, "Los siguientes datos no estan bien introducidos \n" + ((confimarNombre) ? "\n-Nombre" : "") + ((confimarDirec) ? "\n-Direccion" : "")
                        + ((confimarTlf) ? "\n-Telefono" : "") + ((confimarEventos) ? "\n-Eventos" : "") + ((confimarNumPersonas) ? "\n-Numero de Personas" : "") + ((confimarNumDias) ? "\n-Numero de dias" : "")
                        + ((confimarNumHabitaciones) ? "\n-Numero de habitaciones" : "") + ((confirmarFecha) ? "\n-Fecha" : ""));
                alert.showAndWait();
            }
        } catch (RollbackException ex) {
            // Los datos introducidos no cumplen los requisitos de la BD
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
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
            finish = true;
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        }
    }

    public void setEntityManager(EntityManager em) {
        this.entityManager = em;
    }

    @FXML
    private void onActionButtonLimpiar(ActionEvent event) {
        limpiar();
    }

    public void limpiar() {
        textFieldDNI.setText("");
        textFieldNombre.setText("");
        textFieldDireccion.setText("");
        textFieldtlf.setText("");
        textFieldtlf.setDisable(true);
        textFieldDireccion.setDisable(true);
        textFieldNombre.setDisable(true);
        checkBoxHabitaciones.setSelected(false);
        checkBoxHabitaciones.setDisable(true);
        numeroDias.setText("");
        numeroDias.setDisable(true);
        numeroHabitaciones.setText("");
        numeroHabitaciones.setDisable(true);
        try {
            regimenSalon.getSelectedToggle().setSelected(false);
        } catch (Exception Ex) {
        }
        radioButtonBanquete.setDisable(true);
        radioButtonCongreso.setDisable(true);
        radioButtonJornada.setDisable(true);
        dataPickerEvento.setDisable(true);
        numeroPersonas.setDisable(true);
        numeroPersonas.setText("");
        comboBoxCocina.setDisable(true);
        checkBoxHabitaciones.selectedProperty().set(false);
        dataPickerEvento.setValue(LocalDate.now());
        comboBoxCocina.setValue("no precisa");
    }
}
