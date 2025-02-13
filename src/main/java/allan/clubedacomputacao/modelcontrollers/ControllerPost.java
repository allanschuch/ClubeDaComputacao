/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package allan.clubedacomputacao.modelcontrollers;

import allan.clubedacomputacao.model.GeradorID;
import allan.clubedacomputacao.model.Post;
import allan.clubedacomputacao.model.Usuario;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author Allan
 */
public final class ControllerPost {

    private ArrayList<Post> listaPosts = new ArrayList<>();

    public ControllerPost() throws ClassNotFoundException, IOException {
        try {
            desserializaPosts();
        } catch (IOException ex) {
            serializaPosts();
        }
    }

    public void serializaPosts() throws IOException {
        FileOutputStream fileOut = new FileOutputStream("src/main/dados/posts.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(this.listaPosts);
        out.close();
        fileOut.close();
    }

    public void desserializaPosts() throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream("src/main/dados/posts.ser");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        this.listaPosts = (ArrayList<Post>) in.readObject();
        in.close();
        fileIn.close();
    }

    public void novoPost(String conteudo) throws IOException, ClassNotFoundException {
        String postID = String.valueOf(gerarID());
        String autorID = ControllerUsuario.getUsuarioLogado().getUserID();
        String nomeAutor = ControllerUsuario.getUsuarioLogado().getNome().concat(" ").concat(ControllerUsuario.getUsuarioLogado().getSobrenome());
        Post novoPost = new Post(conteudo, autorID, postID, nomeAutor);
        listaPosts.add(novoPost);
        serializaPosts();
    }

    public Post getUltimoPost() throws IOException, ClassNotFoundException {
        desserializaPosts();
        return listaPosts.get(listaPosts.size() - 1);
    }

    public static int gerarID() throws ClassNotFoundException, IOException {
        GeradorID gerador = new GeradorID();
        return gerador.getContadorID();
    }

    public ArrayList<Post> getPosts(String autorID) throws IOException, ClassNotFoundException {
        desserializaPosts();
        ArrayList<Post> postsUsuario = new ArrayList<>();
        for (Post post : listaPosts) {
            if (post.getAutorID().equals(autorID)) {
                postsUsuario.add(post);
            }
        }
        return postsUsuario;
    }

    public ArrayList<Post> getFeedOrdenado() throws ClassNotFoundException, IOException {
        desserializaPosts();
        ArrayList<Post> feed = new ArrayList<>();

        for (Usuario usuario : ControllerUsuario.getUsuarioLogado().getListaSeguindo()) {
            for (Post post : getPosts(usuario.getUserID())) {
                boolean add = feed.add(post);
            }
        }

        feed.sort(null);
        return feed;
    }

    public void excluirTodosPosts(Usuario usuarioRemover) throws IOException, ClassNotFoundException {
        desserializaPosts();
        ArrayList<Post> listaPostsParaRemover = new ArrayList<>();
        for (Post post : listaPosts) {
            if (post.getAutorID().equals(usuarioRemover.getUserID())) {
                listaPostsParaRemover.add(post);
            }
        }

        for (Post post : listaPostsParaRemover) {
            excluirPost(post);
        }
    }

    public void excluirPost(Post postRemover) throws IOException, ClassNotFoundException {
        desserializaPosts();
        for (Post post : listaPosts) {
            if (post.getPostID().equals(postRemover.getPostID())) {
                postRemover = post;
            }
        }
        listaPosts.remove(postRemover);
        serializaPosts();
    }
}
