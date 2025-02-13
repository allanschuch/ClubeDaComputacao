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
import javafx.scene.control.CheckBox;

import javafx.scene.control.ChoiceBox;
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
public class TelaUsuarioPrincipalController implements Initializable {

    private final ObservableList<Usuario> listaUsuariosBusca = FXCollections.observableArrayList();
    private final ObservableList<String> listaInteressesChoiceBox = FXCollections.observableArrayList();
    private final ObservableList<Usuario> listaUsuariosSeguidos = FXCollections.observableArrayList();

    @FXML
    private TextField textFiedBuscaNome;
    @FXML
    private ChoiceBox<String> choiceBoxBuscaInteresse;
    @FXML
    private ListView<Usuario> listViewUsuariosBusca;
    @FXML
    private Label labelBuscaNome;
    @FXML
    private Label labelBuscaCidade;
    @FXML
    private Label labelBuscaNascimento;
    @FXML
    private Label labelBuscaSexo;
    @FXML
    private Label labelBuscaFone;
    @FXML
    private Label labelBuscaEmail;
    @FXML
    private Label labelPromptBusca;
    @FXML
    private Label labelUsuarioLogado;
    @FXML
    private ImageView imageViewBuscaAvatar;
    @FXML
    private ImageView imageViewUsuarioLogado;
    @FXML
    private Label labelBuscaInteresses;
    @FXML
    private Label labelPromptPerfisQueEuSigo;
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
    private ListView<Usuario> listViewUsuariosSeguidos;
    @FXML
    private TextField textFieldDadosNome;
    @FXML
    private TextField textFieldDadosSobrenome;
    @FXML
    private TextField textFieldDadosEmail;
    @FXML
    private TextField textFieldDadosFone;
    @FXML
    private TextField textFieldDadosCidade;
    @FXML
    private TextField textFieldDadosNascimento;
    @FXML
    private TextField textFieldDadosLogin;
    @FXML
    private TextField textFieldDadosSenha;
    @FXML
    private RadioButton radioButtonSexoMasculino;
    @FXML
    private ToggleGroup radioButtonGroupSexo;
    @FXML
    private RadioButton radioButtonSexoFeminino;
    @FXML
    private Label labelPromptDados;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Usuario usuarioLogado = ControllerUsuario.getUsuarioLogado();
        Image avatarUsuarioLogado = new Image(usuarioLogado.getCaminhoAvatar());
        imageViewUsuarioLogado.setImage(avatarUsuarioLogado);
        labelUsuarioLogado.setText(usuarioLogado.getNome() + " " + usuarioLogado.getSobrenome());

        inicializaChoiceBoxInteresses();
        atualizaListaUsuariosSeguidos();
        atualizarDados();
    }

    @FXML
    private void buscarPorNome(ActionEvent event) throws IOException, ClassNotFoundException {
        listaUsuariosBusca.clear();
        criarListaUsuariosBuscaPorNome(textFiedBuscaNome.getText());
        listViewUsuariosBusca.setItems(listaUsuariosBusca);
        labelPromptPerfisQueEuSigo.setText("");
        labelPromptDados.setText("");
    }

    private void criarListaUsuariosBuscaPorNome(String nomeBuscado) throws IOException, ClassNotFoundException {
        ControllerUsuario controller = new ControllerUsuario();
        ArrayList<Usuario> usuarios = controller.getUsuariosComNome(nomeBuscado);
        for (Usuario usuario : usuarios) {
            listaUsuariosBusca.add(usuario);
        }
    }

    private void criarListaUsuariosBuscaPorInteresse(String interesseBuscado) throws IOException, ClassNotFoundException {
        ControllerUsuario controller = new ControllerUsuario();
        ArrayList<Usuario> usuarios = controller.getUsuariosComInteresse(interesseBuscado);
        for (Usuario usuario : usuarios) {
            listaUsuariosBusca.add(usuario);
        }
    }

    private void atualizaListaUsuariosSeguidos() {
        listaUsuariosSeguidos.clear();
        ArrayList<Usuario> usuariosSeguidos = ControllerUsuario.getUsuarioLogado().getListaSeguindo();
        for (Usuario usuario : usuariosSeguidos) {
            listaUsuariosSeguidos.add(usuario);
        }
        listViewUsuariosSeguidos.setItems(listaUsuariosSeguidos);
        listViewUsuariosSeguidos.refresh();
    }

    private void inicializaChoiceBoxInteresses() {
        listaInteressesChoiceBox.add("PHP");
        listaInteressesChoiceBox.add("HTML");
        listaInteressesChoiceBox.add("Python");
        listaInteressesChoiceBox.add("Java");
        listaInteressesChoiceBox.add("C");
        listaInteressesChoiceBox.add("Ruby");
        listaInteressesChoiceBox.add("SQL");

        choiceBoxBuscaInteresse.setItems(listaInteressesChoiceBox);
        choiceBoxBuscaInteresse.setValue("PHP");
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        ControllerUsuario.setUsuarioLogado(null);
        App.setRoot("telaLogin");
    }

    @FXML
    private void visualizarInfos(ActionEvent event) {
        Usuario usuarioSelecionado = listViewUsuariosBusca.getSelectionModel().getSelectedItem();
        if(usuarioSelecionado == null){
            return;
        }

        String interessesUsuario = "Interesses: ";
        for (String interesse : usuarioSelecionado.getListaInteresses()) {
            interessesUsuario = interessesUsuario.concat(interesse);
            if (usuarioSelecionado.getListaInteresses().lastIndexOf(interesse) //verifica se é o último interesse na lista
                    == usuarioSelecionado.getListaInteresses().size() - 1) {
                interessesUsuario = interessesUsuario.concat(".");
            } else {
                interessesUsuario = interessesUsuario.concat(", ");
            }
        }

        Image avatarBusca = new Image(usuarioSelecionado.getCaminhoAvatar());
        imageViewBuscaAvatar.setImage(avatarBusca);
        labelBuscaCidade.setText("Cidade: " + usuarioSelecionado.getCidade());
        labelBuscaFone.setText("Fone: " + usuarioSelecionado.getFone());
        labelBuscaEmail.setText("Email: " + usuarioSelecionado.getEmail());
        labelBuscaNascimento.setText("Nasc.: " + usuarioSelecionado.getDataNascimento());
        labelBuscaNome.setText("Nome: " + usuarioSelecionado.getNome() + " " + usuarioSelecionado.getSobrenome());
        labelBuscaSexo.setText("Sexo: " + usuarioSelecionado.getSexo());
        labelBuscaInteresses.setText(interessesUsuario);
        labelPromptBusca.setText("");
        labelPromptPerfisQueEuSigo.setText("");
        labelPromptDados.setText("");
    }

    @FXML
    private void buscaPorInteresse(ActionEvent event) throws IOException, ClassNotFoundException {
        listaUsuariosBusca.clear();
        criarListaUsuariosBuscaPorInteresse(choiceBoxBuscaInteresse.getSelectionModel().getSelectedItem());
        listViewUsuariosBusca.setItems(listaUsuariosBusca);
        labelPromptPerfisQueEuSigo.setText("");
        labelPromptDados.setText("");
    }

    @FXML
    private void seguirUsuario(ActionEvent event) throws ClassNotFoundException, IOException {
        Usuario usuarioParaAdicionar = listViewUsuariosBusca.getSelectionModel().getSelectedItem();
        if(usuarioParaAdicionar == null){
            return;
        }
        ControllerUsuario controller = new ControllerUsuario();
        if (controller.addListaSeguindo(usuarioParaAdicionar)) {
            labelPromptBusca.setText("Usuario " + usuarioParaAdicionar.getNome() + " "
                    + usuarioParaAdicionar.getSobrenome() + " seguido com sucesso");
        } else {
            labelPromptBusca.setText("Voce já segue o usuario " + usuarioParaAdicionar.getNome() + " "
                    + usuarioParaAdicionar.getSobrenome());
        }
        atualizaListaUsuariosSeguidos();
        labelPromptDados.setText("");
        labelPromptPerfisQueEuSigo.setText("");
    }

    @FXML
    private void pararDeSeguir(ActionEvent event) throws IOException, ClassNotFoundException {
        Usuario usuarioParaRemover = listViewUsuariosSeguidos.getSelectionModel().getSelectedItem();
        if(usuarioParaRemover == null){
            return;
        }
        ControllerUsuario controller = new ControllerUsuario();
        if (controller.removeListaSeguindo(usuarioParaRemover)) {
            labelPromptPerfisQueEuSigo.setText("Usuario " + usuarioParaRemover.getNome() + " "
                    + usuarioParaRemover.getSobrenome() + " removido dos perfis seguidos");
        } else {
            labelPromptPerfisQueEuSigo.setText("Não foi possível remover " + usuarioParaRemover.getNome() + " "
                    + usuarioParaRemover.getSobrenome() + " dos perfis seguidos");
        }
        atualizaListaUsuariosSeguidos();
        labelPromptDados.setText("");
        labelPromptBusca.setText("");
    }

    @FXML
    private void acessarTelaPostTimeline(ActionEvent event) throws IOException {
        App.setRoot("telaUsuarioPostTimeline");
    }

    @FXML
    private void salvarAlterações(ActionEvent event) throws ClassNotFoundException, IOException {
        String retornoVerificaFormulario = verificaFormulario();
        if (retornoVerificaFormulario.equals("Formulario OK")) {
            String nome = textFieldDadosNome.getText();
            String sobrenome = textFieldDadosSobrenome.getText();
            String fone = textFieldDadosFone.getText();
            String cidade = textFieldDadosCidade.getText();
            String email = textFieldDadosEmail.getText();
            String login = textFieldDadosLogin.getText();
            String senha = textFieldDadosSenha.getText();
            String dataNascimento = textFieldDadosNascimento.getText();
            String sexo;

            if (radioButtonSexoMasculino.isSelected()) {
                sexo = "Masculino";
            } else {
                sexo = "Feminino";
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
            controllerUsuario.atualizaCadastro(nome, sobrenome, cidade, email, fone, sexo, dataNascimento, login, senha, interesses);
            labelPromptDados.setText("Dados atualizados com sucesso");
            atualizarDados();
        } else {
            labelPromptDados.setText(retornoVerificaFormulario);
        }
    }

    private String verificaFormulario() throws ClassNotFoundException, IOException {
        if (textFieldDadosCidade.getText().equals("")) {
            return "Não deixe nenhum campo vazio";
        } else if (textFieldDadosEmail.getText().equals("")) {
            return "Não deixe nenhum campo vazio";
        } else if (textFieldDadosFone.getText().equals("")) {
            return "Não deixe nenhum campo vazio";
        } else if (textFieldDadosLogin.getText().equals("")) {
            return "Não deixe nenhum campo vazio";
        } else if (textFieldDadosSenha.getText().equals("")) {
            return "Não deixe nenhum campo vazio";
        } else if (textFieldDadosNome.getText().equals("")) {
            return "Não deixe nenhum campo vazio";
        } else if (textFieldDadosSobrenome.getText().equals("")) {
            return "Não deixe nenhum campo vazio";
        } else if (textFieldDadosNascimento.getText().equals("")) {
            return "Não deixe nenhum campo vazio";
        }

        if (!checkBoxInteresseC.isSelected() && !checkBoxInteresseJava.isSelected() && !checkBoxInteressePHP.isSelected()
                && !checkBoxInteresseHTML.isSelected() && !checkBoxInteresseSQL.isSelected() && !checkBoxInteressePython.isSelected()
                && !checkBoxInteresseRuby.isSelected()) {
            return "Selecione pelo menos um interesse";
        }

        if (textFieldDadosLogin.getText().equals(ControllerUsuario.getUsuarioLogado().getLogin())) {
            return "Formulario OK";
        } else if (verificaLoginExistente()) {
            return "Este login já existe. Insira outro por favor";
        }
        return "Formulario OK";
    }

    private boolean verificaLoginExistente() throws ClassNotFoundException, IOException {
        ControllerUsuario controllerUsuario = new ControllerUsuario();
        return controllerUsuario.LoginExiste(textFieldDadosLogin.getText());
    }

    private void atualizarDados() {
        Usuario usuarioLogado = ControllerUsuario.getUsuarioLogado();
        textFieldDadosCidade.setText(usuarioLogado.getCidade());
        textFieldDadosEmail.setText(usuarioLogado.getEmail());
        textFieldDadosFone.setText(usuarioLogado.getFone());
        textFieldDadosLogin.setText(usuarioLogado.getLogin());
        textFieldDadosNascimento.setText(usuarioLogado.getDataNascimento());
        textFieldDadosNome.setText(usuarioLogado.getNome());
        textFieldDadosSenha.setText(usuarioLogado.getSenha());
        textFieldDadosSobrenome.setText(usuarioLogado.getSobrenome());

        if (usuarioLogado.getSexo().equals("Masculino")) {
            radioButtonSexoMasculino.setSelected(true);
        } else {
            radioButtonSexoFeminino.setSelected(true);
        }

        ArrayList<String> interesses = usuarioLogado.getListaInteresses();

        if (interesses.contains("PHP")) {
            checkBoxInteressePHP.setSelected(true);
        } else {
            checkBoxInteressePHP.setSelected(false);
        }
        if (interesses.contains("HTML")) {
            checkBoxInteresseHTML.setSelected(true);
        } else {
            checkBoxInteresseHTML.setSelected(false);
        }
        if (interesses.contains("C")) {
            checkBoxInteresseC.setSelected(true);
        } else {
            checkBoxInteresseC.setSelected(false);
        }
        if (interesses.contains("SQL")) {
            checkBoxInteresseSQL.setSelected(true);
        } else {
            checkBoxInteresseSQL.setSelected(false);
        }
        if (interesses.contains("Python")) {
            checkBoxInteressePython.setSelected(true);
        } else {
            checkBoxInteressePython.setSelected(false);
        }
        if (interesses.contains("Java")) {
            checkBoxInteresseJava.setSelected(true);
        } else {
            checkBoxInteresseJava.setSelected(false);
        }
        if (interesses.contains("Ruby")) {
            checkBoxInteresseRuby.setSelected(true);
        } else {
            checkBoxInteresseRuby.setSelected(false);
        }

    }

    @FXML
    private void restaurar(ActionEvent event) {
        atualizarDados();
        labelPromptDados.setText("Dados restaurados. Suas alterações não foram salvas");
    }
}
