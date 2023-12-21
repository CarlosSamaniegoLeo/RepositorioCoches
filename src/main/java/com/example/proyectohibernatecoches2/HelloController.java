package com.example.proyectohibernatecoches2;

import com.example.proyectohibernatecoches2.Util.AlertUtil;
import com.example.proyectohibernatecoches2.dao.CocheDao;
import com.example.proyectohibernatecoches2.dao.CocheDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HelloController {

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnMultas;

    @FXML
    private ComboBox<String> cbTipo;

    @FXML
    private TableColumn<Coche, String> colMarca;

    @FXML
    private TableColumn<Coche, String> colMatricula;

    @FXML
    private TableColumn<Coche, String> colModelo;

    @FXML
    private TableColumn<Coche, String> colTipo;

    @FXML
    private TableView<Coche> tblCoches;

    @FXML
    private TextField txtMarca;

    @FXML
    private TextField txtMatricula;

    @FXML
    private TextField txtModelo;

    private CocheDao cocheDao = new CocheDaoImpl();

    public List<Coche> obtenerTodosCoches() {
        return cocheDao.getAllCoches();
    }

    public Coche obtenerCochePorMatricula(String matricula) {
        return cocheDao.getCoche(matricula);
    }

    public static String matriculaSeleccionada = "";

    public static Coche cocheSeleccionado;

    @FXML
    void initialize() {
        limpiarCampos();
        inicializarTabla();
        cargarDatosTabla();

        ObservableList<String> tiposCoches = FXCollections.observableArrayList(
                "Automóviles de Pasajeros",
                "SUV (Vehículo Utilitario Deportivo)",
                "Camionetas y Pickups",
                "Furgonetas",
                "Crossovers",
                "Vehículos Eléctricos (EV)",
                "Híbridos",
                "Vehículos Todo Terreno (ATV) y Cuatrimotos",
                "Motocicletas y Scooters",
                "Bicicletas Eléctricas",
                "Autobuses y Autocares",
                "Ciclomotores y Motonetas"
        );
        cbTipo.setItems(tiposCoches);
    }

    private void inicializarTabla() {
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
    }

    private void cargarDatosTabla() {
        List<Coche> coches = cocheDao.getAllCoches();
        if (coches != null) {
            ObservableList<Coche> cochesObservable = FXCollections.observableArrayList(coches);
            tblCoches.setItems(cochesObservable);
        }
    }

    @FXML
    void eliminarCoche(ActionEvent event) {
        Coche cocheSeleccionado = tblCoches.getSelectionModel().getSelectedItem();
        if (cocheSeleccionado != null) {
            cocheDao.deleteCochebyMatricula(cocheSeleccionado.getMatricula());
            cargarDatosTabla();
            limpiarCampos();
            AlertUtil.mostrarCorrecto("Coche eliminado correctamente.");
        } else {
            AlertUtil.mostrarError("Por favor, selecciona un coche para eliminar.");
        }

    }

    @FXML
    void guardarCoche(ActionEvent event) {
        Coche coche = crearCoche();
        if (coche != null) {
            Coche cocheExistente = cocheDao.getCoche(coche.getMatricula());
            if (cocheExistente == null) {
                cocheDao.saveCoche(coche);
                cargarDatosTabla();
                limpiarCampos();
                AlertUtil.mostrarCorrecto("Coche guardado correctamente.");
            } else {
                AlertUtil.mostrarError("Ya existe un coche con esa matrícula.");
            }
        }
    }

    @FXML
    void modificarCoche(ActionEvent event) {
        Coche cocheSeleccionado = tblCoches.getSelectionModel().getSelectedItem();
        if (cocheSeleccionado != null) {
            Coche cocheModificado = crearCoche();
            if (cocheModificado != null) {
                // Validar si la matrícula ya existe para otro coche
                Coche cocheExistente = cocheDao.getCoche(cocheModificado.getMatricula());
                if (cocheExistente.getMatricula().equals(cocheSeleccionado.getMatricula())) {
                    cocheModificado.setMatricula(cocheSeleccionado.getMatricula());
                    cocheDao.updateCoche(cocheModificado);
                    cargarDatosTabla();
                    limpiarCampos();
                    AlertUtil.mostrarCorrecto("Coche modificado correctamente.");
                } else {
                    AlertUtil.mostrarError("Ya existe un coche con esa matrícula.");
                }
            }
        } else {
            AlertUtil.mostrarError("Por favor, selecciona un coche para modificar.");
        }
    }

    @FXML
    void limpiar(ActionEvent event) {
        limpiarCampos();
    }

    @FXML
    void seleccionarCoche(MouseEvent event) {
        Coche cocheSeleccionado = tblCoches.getSelectionModel().getSelectedItem();
        if (cocheSeleccionado != null) {
            cargarCocheSeleccionado(cocheSeleccionado);
        }
    }

    private void cargarCocheSeleccionado(Coche coche) {
        txtMatricula.setText(coche.getMatricula());
        txtMarca.setText(coche.getMarca());
        txtModelo.setText(coche.getModelo());
        cbTipo.getSelectionModel().select(coche.getTipo());
        matriculaSeleccionada = coche.getMatricula();
        cocheSeleccionado = coche;
    }

    private Coche crearCoche() {
        String matricula = txtMatricula.getText();
        String marca = txtMarca.getText();
        String modelo = txtModelo.getText();
        String tipo = cbTipo.getValue();

        if (matricula.isEmpty() || marca.isEmpty() || modelo.isEmpty() || tipo == null) {
            AlertUtil.mostrarError("Por favor, completa todos los campos.");
            return null;
        }

        return new Coche(matricula, marca, modelo, tipo);
    }

    private void limpiarCampos() {
        txtMatricula.clear();
        txtMarca.clear();
        txtModelo.clear();
        cbTipo.getSelectionModel().clearSelection();
    }

    @FXML
    void irMultas(ActionEvent event) throws IOException {
        if(matriculaSeleccionada.equals("")){
            AlertUtil.mostrarError("Debes introducir o seleccionar una matricula");
        }else{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("multas.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        Stage currentStage = (Stage) btnMultas.getScene().getWindow();
        currentStage.close();
        }
    }
}




