package com.example.proyectohibernatecoches2;

import com.example.proyectohibernatecoches2.Util.AlertUtil;
import com.example.proyectohibernatecoches2.dao.MultaDao;
import com.example.proyectohibernatecoches2.dao.MultaDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class MultasController {

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnModificar;

    @FXML
    private TableColumn<?, ?> colFechaMulta;

    @FXML
    private TableColumn<?, ?> colIdMulta;

    @FXML
    private TableColumn<?, ?> colMatricula;

    @FXML
    private TableColumn<?, ?> colPrecioMuta;

    @FXML
    private DatePicker dateMulta;

    @FXML
    private TableView<Multas> tblMultas;

    @FXML
    private TextField txtIdMulta;

    @FXML
    private TextField txtMatricula;

    @FXML
    private TextField txtPrecioMulta;

    String matricula;

    MultaDao multaDao = new MultaDaoImpl();

    @FXML
    void initialize() {
        txtMatricula.setText(HelloController.matriculaSeleccionada);
        matricula = HelloController.matriculaSeleccionada;
        inicializarTabla();
        cargarMultas();

    }

    public TextField getMatricula() {
        return txtMatricula;
    }

    private void cargarMultas() {
        MultaDao multaDao = new MultaDaoImpl();
        List<Multas> multas = multaDao.getAllMultasByMatricula(matricula);

        ObservableList<Multas> multasObservable = FXCollections.observableArrayList(multas);
        tblMultas.setItems(multasObservable);
    }

    private void inicializarTabla() {
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("coche"));
        colIdMulta.setCellValueFactory(new PropertyValueFactory<>("id_multas"));
        colPrecioMuta.setCellValueFactory(new PropertyValueFactory<>("precio_multa"));
        colFechaMulta.setCellValueFactory(new PropertyValueFactory<>("fecha_multa"));
    }

    @FXML
    void eliminarMulta(ActionEvent event) {
        Multas multaSeleccionada = tblMultas.getSelectionModel().getSelectedItem();
        if (multaSeleccionada != null) {
            multaDao.deleteMulta(multaSeleccionada.getId_multas());
            initialize();
            AlertUtil.mostrarCorrecto("Coche eliminado correctamente.");
        } else {
            AlertUtil.mostrarError("Por favor, selecciona un coche para eliminar.");
        }
    }

    @FXML
    void guardarMulta(ActionEvent event) {

        if (matricula.isEmpty()) {
            AlertUtil.mostrarError("La matricula no es la del coche seleccionado.");
            return;
        }
        double importe = Double.parseDouble(txtPrecioMulta.getText());
        LocalDate fecha = dateMulta.getValue();

        // Crear un objeto de la clase Multa con los detalles obtenidos
        Multas multa = new Multas(importe, fecha, HelloController.cocheSeleccionado);

        // Guardar la multa en la base de datos
        multaDao.saveMulta(multa);

        limpiarCampos();

        // Mostrar un mensaje de éxito
        AlertUtil.mostrarCorrecto("Multa guardada correctamente.");
        initialize();
    }

    @FXML
    void limpiar(ActionEvent event) {
        // Limpiar los campos después de guardar la multa
        limpiarCampos();
    }

    void limpiarCampos(){
        dateMulta.setValue(null);
        txtPrecioMulta.clear();
    }

    @FXML
    void modificarMulta(ActionEvent event) {
        Multas multaSeleccionada = tblMultas.getSelectionModel().getSelectedItem();

        if (multaSeleccionada != null) {
            // Obtener los nuevos valores de los campos
            double nuevoPrecio = Double.parseDouble(txtPrecioMulta.getText());
            LocalDate nuevaFecha = dateMulta.getValue();

            // Actualizar la multa seleccionada con los nuevos valores
            multaSeleccionada.setPrecio_multa(nuevoPrecio);
            multaSeleccionada.setFecha_multa(nuevaFecha);

            // Guardar los cambios en la base de datos
            multaDao.updateMulta(multaSeleccionada);

            // Limpiar los campos y recargar la lista de multas
            limpiarCampos();
            cargarMultas();

            // Mostrar un mensaje de éxito
            AlertUtil.mostrarCorrecto("Multa modificada correctamente.");
        } else {
            AlertUtil.mostrarError("Por favor, selecciona una multa para modificar.");
        }
    }



    @FXML
    void seleccionarMulta(MouseEvent event) {
        Multas multaSeleccionada = tblMultas.getSelectionModel().getSelectedItem();
        if (multaSeleccionada != null) {
            cargarMultaSeleccionada(multaSeleccionada);
        }
    }

    private void cargarMultaSeleccionada(Multas multa) {
        txtPrecioMulta.setText(String.valueOf(multa.getPrecio_multa()));
        dateMulta.setValue(multa.getFecha_multa());
    }

}
