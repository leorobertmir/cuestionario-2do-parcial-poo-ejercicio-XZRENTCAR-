package com.rentas.xzrentcar;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class RentaController {

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private CheckBox cbxTienetc;

    @FXML
    private ComboBox<ModeloVehiculoEntity> cmbModelo;

    @FXML
    private DatePicker dpFeNacimiento;

    @FXML
    private TableView<RentaEntity> tblRenta;

    @FXML
    private TableColumn<RentaEntity, String> tcDni;

    @FXML
    private TableColumn<RentaEntity, Date> tcFeNacimiento;

    @FXML
    private TableColumn<RentaEntity, String> tcMarca;

    @FXML
    private TableColumn<RentaEntity, String> tcModelo;

    @FXML
    private TableColumn<RentaEntity, String> tcNombre;

    @FXML
    private TableColumn<RentaEntity, String> tcTiene;

    @FXML
    private TextField txtDNI;

    @FXML
    private TextField txtMarca;

    @FXML
    private TextField txtNombre;

    // Lista observable para almacenar los registros
    private ObservableList<RentaEntity> lstRenta = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        llenarComboModelo();

        // Configurar las columnas de la tabla
        tcDni.setCellValueFactory(new PropertyValueFactory<RentaEntity, String>("dni"));
        tcFeNacimiento.setCellValueFactory(new PropertyValueFactory<RentaEntity, Date>("feNacimiento"));
        tcMarca.setCellValueFactory(new PropertyValueFactory<RentaEntity, String>("marca"));
        tcModelo.setCellValueFactory(new PropertyValueFactory<RentaEntity, String>("modelo"));
        tcNombre.setCellValueFactory(new PropertyValueFactory<RentaEntity, String>("nombres"));
        tcTiene.setCellValueFactory(new PropertyValueFactory<RentaEntity, String>("tieneTc"));

        // Vincular la lista observable a la tabla
        tblRenta.setItems(lstRenta);

        RentaDAO objRenta = new RentaDAO(new ConectarBD());
        List<RentaEntity> lstRentaEntity;
        try {
            lstRentaEntity = objRenta.recuperarRegistro();
            lstRenta.addAll(lstRentaEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void llenarComboModelo() {
        List<ModeloVehiculoEntity> lstModelo = new ArrayList<ModeloVehiculoEntity>();
        ModeloVehiculoEntity objModeloVehiculo = new ModeloVehiculoEntity();
        objModeloVehiculo.setModelo("Moto");
        lstModelo.add(objModeloVehiculo);

        objModeloVehiculo = new ModeloVehiculoEntity();
        objModeloVehiculo.setModelo("SUV");
        lstModelo.add(objModeloVehiculo);

        objModeloVehiculo = new ModeloVehiculoEntity();
        objModeloVehiculo.setModelo("Sedan");
        lstModelo.add(objModeloVehiculo);

        objModeloVehiculo = new ModeloVehiculoEntity();
        objModeloVehiculo.setModelo("Camioneta");
        lstModelo.add(objModeloVehiculo);

        cmbModelo.getItems().addAll(lstModelo);
        cmbModelo.getSelectionModel().selectFirst();
    }

    @FXML
    void guardar(ActionEvent event) {
        // Validar campos obligatorios
        boolean esValido = validarCampos();
        if (!esValido) {
            return;
        }

        String dni = txtDNI.getText();
        String nombres = txtNombre.getText();
        String marca = txtMarca.getText();
        String modelo = cmbModelo.getSelectionModel().getSelectedItem().getModelo();
        Date feNacimiento = null;
        if (dpFeNacimiento.getValue() != null) {
            feNacimiento = new java.sql.Date(
                    java.util.Date
                            .from(dpFeNacimiento.getValue().atStartOfDay(java.time.ZoneId.systemDefault()).toInstant())
                            .getTime());
        }
        String tieneTc = cbxTienetc.isSelected() ? "Si" : "No";

        // Crear una nueva entidad de renta
        RentaEntity renta = new RentaEntity();
        renta.setDni(dni);
        renta.setNombres(nombres);
        renta.setMarca(marca);
        renta.setModelo(modelo);
        renta.setFeNacimiento(feNacimiento);
        renta.setTieneTc(tieneTc);

        // Agregar la entidad a la lista y actualizar la tabla
        lstRenta.add(renta);

        // Guardar Objeto creado en la base de datos
        InfoRentaModel objetoInfoRenta = new InfoRentaModel();
        objetoInfoRenta.setDni(dni);
        objetoInfoRenta.setNombres(nombres);
        objetoInfoRenta.setModelo(modelo);
        objetoInfoRenta.setMarca(marca);
        objetoInfoRenta.setFeNacimiento(feNacimiento);
        objetoInfoRenta.setTieneTC(tieneTc);
        objetoInfoRenta.setFeCreacion(java.sql.Date.valueOf(LocalDate.now()));
        objetoInfoRenta.setUsrCreacion("wgaibor");
        objetoInfoRenta.setEstado("Activo");

        RentaDAO rentaDAO = new RentaDAO(new ConectarBD());
        try {
            boolean seGuardo = rentaDAO.guardarRenta(objetoInfoRenta);
            if (seGuardo) {
                mostrarAlertaUsuario(AlertType.CONFIRMATION, "Guardar Registro",
                        "Se guardo el registro en la base de datos");
            } else {
                mostrarAlertaUsuario(AlertType.ERROR, "Guardar Registro", "No se pudo guardar el registro");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlertaUsuario(AlertType.ERROR, "Guardar Registro", "Error tecnico");
        }
        limpiarFormulario();
    }

    private boolean validarCampos() {
        if (!cbxTienetc.isSelected()) {
            mostrarAlertaUsuario(Alert.AlertType.ERROR, "Error", "Debe seleccionar si tiene tarjeta de crédito.");
            return false;
        } else if (!esMayorEdad(dpFeNacimiento.getValue())) {
            mostrarAlertaUsuario(Alert.AlertType.ERROR, "Error", "La persona es menor de edad");
            return false;
        }
        return true;
    }

    private boolean esMayorEdad(LocalDate feNacimiento) {
        LocalDate today = LocalDate.now(); // Get the current date

        // Calculate the period between today and the birth date
        Period agePeriod = Period.between(feNacimiento, today);

        // Check if the calculated age in years is greater than or equal to the required
        // age
        return agePeriod.getYears() >= 18;
    }

    private void mostrarAlertaUsuario(AlertType alerta, String titulo, String contenido) {
        Alert alert = new Alert(alerta);
        alert.setTitle(titulo);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    @FXML
    void limpiar(ActionEvent event) {
        limpiarFormulario();
    }

    void limpiarFormulario() {
        txtDNI.clear();
        txtNombre.clear();
        cmbModelo.getSelectionModel().clearSelection();
        txtMarca.clear();
        dpFeNacimiento.setValue(null);
        cbxTienetc.setSelected(false);
    }

}