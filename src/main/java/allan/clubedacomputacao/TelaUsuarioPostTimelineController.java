/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package allan.clubedacomputacao;

import allan.clubedacomputacao.model.Post;
import allan.clubedacomputacao.model.Usuario;
import allan.clubedacomputacao.modelcontrollers.ControllerPost;
import allan.clubedacomputacao.modelcontrollers.ControllerUsuario;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Allan
 */
public class TelaUsuarioPostTimelineController implements Initializable {

    private final ObservableList<Post> listaMeusPosts = FXCollections.observableArrayList();
    private final ObservableList<Post> listaFeed = FXCollections.observableArrayList();

    @FXML
    private ListView<Post> listViewMeusPosts;
    @FXML
    private Label labelPromptMeusPosts;
    @FXML
    private TextArea textAreaCriarPost;
    @FXML
    private Label labelPromptCriarPost;
    @FXML
    private ListView<Post> listViewFeed;
    @FXML
    private Label labelExibicaoNome;
    @FXML
    private Label labelExibicaoData;
    @FXML
    private Label labelExibicaoHora;
    @FXML
    private ImageView imageViewExibicaoAvatar;
    @FXML
    private Label labelExibicaoTituloAutor;
    @FXML
    private TextArea textAreaExibicaoPost;
    @FXML
    private Label labelExibicaoTituloPost;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            atualizaListaMeusPosts();
        } catch (ClassNotFoundException | IOException ex) {
            ex.printStackTrace();
        }

        try {
            atualizaListaFeed();
        } catch (ClassNotFoundException | IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void limparTextAreaCriarPost(ActionEvent event) {
        textAreaCriarPost.clear();
        labelPromptCriarPost.setText("");
        labelPromptMeusPosts.setText("");
    }

    @FXML
    private void limparExibicaoPost(ActionEvent event) {
        labelExibicaoData.setText("");
        labelExibicaoHora.setText("");
        labelExibicaoNome.setText("");
        labelExibicaoTituloAutor.setText("");
        labelExibicaoTituloPost.setVisible(false);
        textAreaExibicaoPost.setVisible(false);
        imageViewExibicaoAvatar.setImage(null);
        labelPromptCriarPost.setText("");
    }

    @FXML
    private void voltarTelaPerfil(ActionEvent event) throws IOException {
        App.setRoot("telaUsuarioPrincipal");
    }

    @FXML
    private void criarPost(ActionEvent event) throws ClassNotFoundException, IOException {
        String conteudo = textAreaCriarPost.getText();
        if (conteudo.length() > 280) {
            labelPromptCriarPost.setText("Conteudo ultrapassou o limite de 280 caracteres");
            return;
        }
        if (conteudo.isBlank()) {
            labelPromptCriarPost.setText("Não é possível publicar um post vazio");
            return;
        }

        ControllerPost controllerPost = new ControllerPost();
        controllerPost.novoPost(conteudo);
        labelPromptCriarPost.setText("Post criado com sucesso (ID " + controllerPost.getUltimoPost().getPostID() + ")");
        atualizaListaMeusPosts();
        textAreaCriarPost.clear();
    }

    private void atualizaListaMeusPosts() throws ClassNotFoundException, IOException {
        listaMeusPosts.clear();
        ControllerPost controllerPost = new ControllerPost();
        ArrayList<Post> meusPosts = controllerPost.getPosts(ControllerUsuario.getUsuarioLogado().getUserID());

        for (Post post : meusPosts) {
            listaMeusPosts.add(post);
        }
        listViewMeusPosts.setItems(listaMeusPosts);
        listViewMeusPosts.refresh();
    }

    private void atualizaListaFeed() throws ClassNotFoundException, IOException {
        listaFeed.clear();
        ControllerPost controllerPost = new ControllerPost();
        ArrayList<Post> feed = controllerPost.getFeedOrdenado();

        for (Post post : feed) {
            listaFeed.add(post);
        }
        listViewFeed.setItems(listaFeed);
        listViewFeed.refresh();
    }

    @FXML
    private void visualizarMeuPost(ActionEvent event) throws ClassNotFoundException, IOException {
        visualizarPost(listViewMeusPosts.getSelectionModel().getSelectedItem());
        labelPromptCriarPost.setText("");
        labelPromptMeusPosts.setText("");
    }

    @FXML
    private void visualizarPostFeed(ActionEvent event) throws ClassNotFoundException, IOException {
        visualizarPost(listViewFeed.getSelectionModel().getSelectedItem());
        labelPromptCriarPost.setText("");
        labelPromptMeusPosts.setText("");
    }

    private void visualizarPost(Post post) throws ClassNotFoundException, IOException {
        if(post == null){
            return;
        }
        ControllerUsuario controllerUsuario = new ControllerUsuario();
        Usuario autorDoPost = controllerUsuario.getUsuario(post.getAutorID());
        if (autorDoPost.getSexo().equals("Masculino")) {
            labelExibicaoTituloAutor.setText("Autor");
        } else {
            labelExibicaoTituloAutor.setText("Autora");
        }

        SimpleDateFormat dia = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatoTimestamp = new SimpleDateFormat("dd/MM/yyyy '-' HH:mm:ss");
        Date dataPost = null;
        try {
            dataPost = formatoTimestamp.parse(post.getTimestamp());
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        Image avatarAutor = new Image(autorDoPost.getCaminhoAvatar());

        textAreaExibicaoPost.setText(post.getConteudo());
        textAreaExibicaoPost.setVisible(true);
        labelExibicaoTituloPost.setVisible(true);
        labelExibicaoNome.setText(post.getNomeAutor());
        labelExibicaoData.setText("Publicado em: " + dia.format(dataPost));
        labelExibicaoHora.setText("às " + hora.format(dataPost));
        imageViewExibicaoAvatar.setImage(avatarAutor);
    }

    @FXML
    private void excluirPost(ActionEvent event) throws IOException, ClassNotFoundException {
        Post postRemover = listViewMeusPosts.getSelectionModel().getSelectedItem();
        if(postRemover == null){
            return;
        }
        ControllerPost controllerPost = new ControllerPost();
        controllerPost.excluirPost(postRemover);

        labelPromptCriarPost.setText("");
        labelPromptMeusPosts.setText("Poost ID (" + postRemover.getPostID() + ") removido com sucesso");
        atualizaListaMeusPosts();
        limparExibicaoPost(event);
    }
}
