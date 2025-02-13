/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package allan.clubedacomputacao;

import allan.clubedacomputacao.model.Usuario;
import allan.clubedacomputacao.modelcontrollers.ControllerUsuario;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Allan
 */
public class TelaAdminController implements Initializable {

    private final ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();

    @FXML
    private Label labelAdminLogado;
    @FXML
    private ToggleGroup radioButtonGroupSexo;
    @FXML
    private ToggleGroup radioButtonGroupPrivilegio;
    @FXML
    private TextField textFieldNome;
    @FXML
    private TextField textFieldSobrenome;
    @FXML
    private TextField textFieldCidade;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldFone;
    @FXML
    private DatePicker datePickerNascimento;
    @FXML
    private RadioButton radioButtonMasculino;
    @FXML
    private RadioButton radioButtonFeminino;
    @FXML
    private TextField textFieldLogin;
    @FXML
    private TextField textFieldSenha;
    @FXML
    private RadioButton radioButtonAdmin;
    @FXML
    private ImageView imageViewAvatar;
    @FXML
    private ImageView imageViewAvatar1;
    @FXML
    private RadioButton radioButtonAvatar1;
    @FXML
    private ToggleGroup radioButtonGroupAvatar;
    @FXML
    private ImageView imageViewAvatar2;
    @FXML
    private RadioButton radioButtonAvatar2;
    @FXML
    private ImageView imageViewAvatar3;
    @FXML
    private RadioButton radioButtonAvatar3;
    @FXML
    private ImageView imageViewAvatar4;
    @FXML
    private RadioButton radioButtonAvatar4;
    @FXML
    private ImageView imageViewAvatar5;
    @FXML
    private RadioButton radioButtonAvatar5;
    @FXML
    private ImageView imageViewAvatar6;
    @FXML
    private RadioButton radioButtonAvatar6;
    @FXML
    private Label labelPromptCriacaoUsuario;
    @FXML
    private ListView<Usuario> listViewUsuarios;
    @FXML
    private Label labelExclusaoId;
    @FXML
    private Label labelExclusaoNome;
    @FXML
    private Label labelExclusaoPrivilegio;
    @FXML
    private Label labelPromptExclusao;
    @FXML
    private ImageView imageViewExclusaoAvatar;
    @FXML
    private CheckBox checkBoxInteressePython;
    @FXML
    private CheckBox checkBoxInteresseRuby;
    @FXML
    private CheckBox checkBoxInteresseC;
    @FXML
    private CheckBox checkBoxInteresseJava;
    @FXML
    private CheckBox checkBoxInteresseSQL;
    @FXML
    private CheckBox checkBoxInteresseHTML;
    @FXML
    private CheckBox checkBoxInteressePHP;
    @FXML
    private Label labelExclusaoSenha;
    @FXML
    private Label labelExclusaoLogin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String nomeAdminLogado = ControllerUsuario.getUsuarioLogado().getNome();
        String sobrenomeAdminLogado = ControllerUsuario.getUsuarioLogado().getSobrenome();
        labelAdminLogado.setText(nomeAdminLogado + " " + sobrenomeAdminLogado);

        try {
            criaListaUsuarios();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        listViewUsuarios.setItems(listaUsuarios);
    }

    @FXML
    private void criarUsuario(ActionEvent event) throws ClassNotFoundException, IOException {
        String retornoVerificaFormulario = verificaFormulario();
        if (retornoVerificaFormulario.equals("Formulario OK")) {
            String nome = textFieldNome.getText();
            String sobrenome = textFieldSobrenome.getText();
            String fone = textFieldFone.getText();
            String cidade = textFieldCidade.getText();
            String email = textFieldEmail.getText();
            String login = textFieldLogin.getText();
            String senha = textFieldSenha.getText();
            String dataNascimento = datePickerNascimento.getEditor().getText();
            String sexo;
            String caminhoAvatar = null;
            boolean privilegio;

            if (radioButtonAdmin.isSelected()) {
                privilegio = true;
            } else {
                privilegio = false;
            }

            if (radioButtonMasculino.isSelected()) {
                sexo = "Masculino";
            } else {
                sexo = "Feminino";
            }

            if (radioButtonAvatar1.isSelected()) {
                caminhoAvatar = imageViewAvatar1.getImage().getUrl();
            } else if (radioButtonAvatar2.isSelected()) {
                caminhoAvatar = imageViewAvatar2.getImage().getUrl();
            } else if (radioButtonAvatar3.isSelected()) {
                caminhoAvatar = imageViewAvatar3.getImage().getUrl();
            } else if (radioButtonAvatar4.isSelected()) {
                caminhoAvatar = imageViewAvatar4.getImage().getUrl();
            } else if (radioButtonAvatar5.isSelected()) {
                caminhoAvatar = imageViewAvatar5.getImage().getUrl();
            } else if (radioButtonAvatar6.isSelected()) {
                caminhoAvatar = imageViewAvatar6.getImage().getUrl();
            }

            ArrayList<String> interesses = new ArrayList<>();
            if (checkBoxInteresseC.isSelected()) {
                interesses.add("C");
            }
            if (checkBoxInteresseJava.isSelected()) {
                interesses.add("Java");
            }
            if (checkBoxInteressePHP.isSelected()) {
                interesses.add("PHP");
            }
            if (checkBoxInteresseHTML.isSelected()) {
                interesses.add("HTML");
            }
            if (checkBoxInteresseRuby.isSelected()) {
                interesses.add("Ruby");
            }
            if (checkBoxInteressePython.isSelected()) {
                interesses.add("Python");
            }
            if (checkBoxInteresseSQL.isSelected()) {
                interesses.add("SQL");
            }

            ControllerUsuario controllerUsuario = new ControllerUsuario();
            controllerUsuario.criarUsuario(nome, sobrenome, cidade, email,
                    fone, sexo, dataNascimento, login, senha, privilegio, caminhoAvatar, interesses);

            labelPromptCriacaoUsuario.setText("Usuario criado com sucesso! (ID " + controllerUsuario.getUltimoUsuario().getUserID() + ")");
            limparCampos(event);
            listaUsuarios.add(controllerUsuario.getUltimoUsuario());
            listViewUsuarios.refresh();
        } else {
            labelPromptCriacaoUsuario.setText(retornoVerificaFormulario);
        }
    }

    private void criaListaUsuarios() throws IOException, ClassNotFoundException {
        ControllerUsuario controller = new ControllerUsuario();
        ArrayList<Usuario> usuarios = controller.getListaUsuarios();
        for (Usuario usuario : usuarios) {
            listaUsuarios.add(usuario);
        }
    }

    private String verificaFormulario() throws ClassNotFoundException, IOException {
        if (textFieldCidade.getText().equals("")) {
            return "Preencha completamente o formulário";
        } else if (textFieldEmail.getText().equals("")) {
            return "Preencha completamente o formulário";
        } else if (textFieldFone.getText().equals("")) {
            return "Preencha completamente o formulário";
        } else if (textFieldLogin.getText().equals("")) {
            return "Preencha completamente o formulário";
        } else if (textFieldSenha.getText().equals("")) {
            return "Preencha completamente o formulário";
        } else if (textFieldNome.getText().equals("")) {
            return "Preencha completamente o formulário";
        } else if (textFieldSobrenome.getText().equals("")) {
            return "Preencha completamente o formulário";
        }

        if (!checkBoxInteresseC.isSelected() && !checkBoxInteresseJava.isSelected() && !checkBoxInteressePHP.isSelected()
                && !checkBoxInteresseHTML.isSelected() && !checkBoxInteresseSQL.isSelected() && !checkBoxInteressePython.isSelected()
                && !checkBoxInteresseRuby.isSelected()) {
            return "Selecione pelo menos um interesse";
        }

        if (verificaLoginExistente()) {
            return "Este login já existe. Insira outro por favor";
        }
        return "Formulario OK";
    }

    private boolean verificaLoginExistente() throws ClassNotFoundException, IOException {
        ControllerUsuario controllerUsuario = new ControllerUsuario();
        return controllerUsuario.LoginExiste(textFieldLogin.getText());
    }

    @FXML
    private void limparCampos(ActionEvent event) {
        textFieldCidade.setText("");
        textFieldFone.setText("");
        textFieldEmail.setText("");
        textFieldLogin.setText("");
        textFieldNome.setText("");
        textFieldSenha.setText("");
        textFieldSobrenome.setText("");
        checkBoxInteresseC.setSelected(false);
        checkBoxInteresseJava.setSelected(false);
        checkBoxInteressePython.setSelected(false);
        checkBoxInteressePHP.setSelected(false);
        checkBoxInteresseSQL.setSelected(false);
        checkBoxInteresseRuby.setSelected(false);
        checkBoxInteresseHTML.setSelected(false);
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        ControllerUsuario.setUsuarioLogado(null);
        App.setRoot("telaLogin");
    }

    @FXML
    private void excluirUsuario(ActionEvent event) throws IOException, ClassNotFoundException {
        Usuario usuarioSelecionado = listViewUsuarios.getSelectionModel().getSelectedItem();
        if (usuarioSelecionado.getUserID().equals("1")) {
            labelPromptExclusao.setText("Impossível excluir o Admin Padrao");
            return;
        }
        if (usuarioSelecionado.comparaID(ControllerUsuario.getUsuarioLogado())) {
            labelPromptExclusao.setText("Impossível excluir a si próprio");
            return;
        }
        ControllerUsuario controller = new ControllerUsuario();
        if (controller.removeUsuario(usuarioSelecionado)) {
            labelPromptExclusao.setText("Usuario ID " + usuarioSelecionado.getUserID() + " removido com sucesso");
            listaUsuarios.remove(usuarioSelecionado);
            listViewUsuarios.refresh();
            limparCamposExclusao();
        } else {
            labelPromptExclusao.setText("Nao foi possível remover usuario ID " + usuarioSelecionado.getUserID());
        }
    }

    @FXML
    private void exibirInfos(ActionEvent event) {
        Usuario usuarioSelecionado = listViewUsuarios.getSelectionModel().getSelectedItem();
        if (usuarioSelecionado.getUserID().equals("1")) {
            labelPromptExclusao.setText("Impossível selecionar o Admin Padrao");
            return;
        }
        String privilegio;
        Image avatar = new Image(usuarioSelecionado.getCaminhoAvatar());

        if (usuarioSelecionado.isPrivilegioAdmin()) {
            privilegio = "Admin";
        } else {
            privilegio = "Comum";
        }
        labelExclusaoId.setText("Usuario ID " + usuarioSelecionado.getUserID());
        labelExclusaoPrivilegio.setText("Privilégio: " + privilegio);
        labelExclusaoNome.setText("Nome: " + usuarioSelecionado.getNome() + " "
                + usuarioSelecionado.getSobrenome());
        labelExclusaoSenha.setText("Senha: " + usuarioSelecionado.getSenha());
        labelExclusaoLogin.setText("Login: " + usuarioSelecionado.getLogin());

        imageViewExclusaoAvatar.setImage(avatar);
        labelPromptExclusao.setText("");
    }

    private void limparCamposExclusao() {
        imageViewExclusaoAvatar.setImage(null);
        labelExclusaoId.setText("");
        labelExclusaoNome.setText("");
        labelExclusaoPrivilegio.setText("");
        labelExclusaoLogin.setText("");
        labelExclusaoSenha.setText("");
    }

}
