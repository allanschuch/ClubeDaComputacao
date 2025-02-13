/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package allan.clubedacomputacao;

import allan.clubedacomputacao.model.Usuario;
import allan.clubedacomputacao.modelcontrollers.ControllerUsuario;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Allan
 */
public class TelaLoginController implements Initializable {

    @FXML
    private TextField textFieldLogin;
    @FXML
    private Label labelPromptLogin;
    @FXML
    private Button buttonEntrar;
    @FXML
    private PasswordField passwordFieldSenha;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void fazerLogin(ActionEvent event) throws IOException, ClassNotFoundException {
        ControllerUsuario controllerUsuario = new ControllerUsuario();
        String retornoLogin;
        String login = textFieldLogin.getText();
        String senha = passwordFieldSenha.getText();
        retornoLogin = controllerUsuario.checarLogin(login, senha);
        labelPromptLogin.setText(retornoLogin);
        if (retornoLogin.equals("Login aprovado")) {
            if (ControllerUsuario.getUsuarioLogado().isPrivilegioAdmin()) {
                App.setRoot("telaAdmin");
            } else {
                App.setRoot("telaUsuarioPrincipal");
            }
        }
    }

    @FXML
    private void sair(ActionEvent event) {
        Platform.exit();
    }

}
