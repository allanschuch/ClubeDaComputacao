/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package allan.clubedacomputacao.modelcontrollers;

import allan.clubedacomputacao.model.GeradorID;
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
public final class ControllerUsuario {
    
    private static Usuario usuarioLogado;
    private ArrayList<Usuario> listaUsuarios = new ArrayList<>();
    
    public ControllerUsuario() throws ClassNotFoundException, IOException {
        try {
            desserializaUsuarios();
        } catch (IOException ex) {
            criarPrimeiroAdmin();
        }
    }
    
    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
    
    public static void setUsuarioLogado(Usuario usuario) {
        usuarioLogado = usuario;
    }
    
    public static int gerarID() throws ClassNotFoundException, IOException {
        GeradorID gerador = new GeradorID();
        return gerador.getContadorID();
    }
    
    public void serializaUsuarios() throws IOException, ClassNotFoundException {
        FileOutputStream fileOut = new FileOutputStream("src/main/dados/usuarios.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(this.listaUsuarios);
        out.close();
        fileOut.close();
        setUsuarioLogado(getReferenciadaLista(usuarioLogado));
    }
    
    public void desserializaUsuarios() throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream("src/main/dados/usuarios.ser");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        this.listaUsuarios = (ArrayList<Usuario>) in.readObject();
        in.close();
        fileIn.close();
    }
    
    private Usuario getReferenciadaLista(Usuario user) throws IOException, ClassNotFoundException {

        //Retorna uma referencia a um usuário da lista de usuários "oficial" com o mesmo userID
        desserializaUsuarios();
        for (Usuario usuario : listaUsuarios) {
            if (usuario.comparaID(user)) {
                return usuario;
            }
        }
        return null;
    }
    
    public boolean addListaSeguindo(Usuario usuarioParaAdicionar) throws IOException, ClassNotFoundException {
        desserializaUsuarios();
        
        usuarioParaAdicionar = getReferenciadaLista(usuarioParaAdicionar);
        Usuario usuarioCorrente = getReferenciadaLista(usuarioLogado);
        
        boolean add = usuarioCorrente.addSeguindo(usuarioParaAdicionar);
        serializaUsuarios();
        return add;
    }
    
    public boolean removeListaSeguindo(Usuario usuarioParaRemover) throws IOException, ClassNotFoundException {
        desserializaUsuarios();
        
        usuarioParaRemover = getReferenciadaLista(usuarioParaRemover);
        Usuario usuarioCorrente = getReferenciadaLista(usuarioLogado);
        
        boolean remove = usuarioCorrente.removeSeguindo(usuarioParaRemover);
        serializaUsuarios();
        return remove;
    }
    
    public String checarLogin(String login, String senha) throws IOException, ClassNotFoundException {
        desserializaUsuarios();
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getLogin().equals(login)) {
                if (usuario.getSenha().equals(senha)) {
                    setUsuarioLogado(usuario);
                    return "Login aprovado";
                } else {
                    return "Senha incorreta para este usuário";
                }
            }
        }
        return "Login não cadastrado";
    }
    
    public ArrayList<Usuario> getListaUsuarios() throws IOException, ClassNotFoundException {
        desserializaUsuarios();
        return listaUsuarios;
    }
    
    public void criarUsuario(String nome, String sobrenome, String cidade,
            String email, String fone, String sexo, String dataNascimento, String login,
            String senha, boolean privilegioAdmin, String caminhoAvatar, ArrayList<String> interesses) throws ClassNotFoundException, IOException {
        String userID = String.valueOf(gerarID());
        Usuario novoUsuario = new Usuario(nome, sobrenome, cidade, email, fone, sexo,
                dataNascimento, login, senha, privilegioAdmin, userID, caminhoAvatar, interesses);
        this.listaUsuarios.add(novoUsuario);
        serializaUsuarios();
    }
    
    public ArrayList<Usuario> getUsuariosComNome(String nomeBuscado) throws IOException, ClassNotFoundException {
        desserializaUsuarios();
        ArrayList<Usuario> usuariosComNome = new ArrayList<>();
        for (Usuario usuario : listaUsuarios) {
            if (!usuario.isPrivilegioAdmin() && !usuario.comparaID(ControllerUsuario.usuarioLogado)) {
                if (usuario.getNome().equals(nomeBuscado)
                        || usuario.getSobrenome().equals(nomeBuscado)) {
                    usuariosComNome.add(usuario);
                }
            }
        }
        return usuariosComNome;
    }
    
    public ArrayList<Usuario> getUsuariosComInteresse(String interesseBuscado) throws IOException, ClassNotFoundException {
        desserializaUsuarios();
        ArrayList<Usuario> usuariosComInteresse = new ArrayList<>();
        for (Usuario usuario : listaUsuarios) {
            if (!usuario.isPrivilegioAdmin() && !usuario.comparaID(ControllerUsuario.usuarioLogado)) {
                if (usuario.getListaInteresses().contains(interesseBuscado)) {
                    usuariosComInteresse.add(usuario);
                }
            }
        }
        return usuariosComInteresse;
    }
    
    public void criarPrimeiroAdmin() throws IOException, ClassNotFoundException {
        String userID = String.valueOf(gerarID());
        Usuario primeiroAdmin = new Usuario("Admin", "Padrao", "admin", "UFP31", userID);
        this.listaUsuarios.add(primeiroAdmin);
        serializaUsuarios();
    }
    
    public Usuario getUltimoUsuario() throws IOException, ClassNotFoundException {
        desserializaUsuarios();
        int ultimoIndice = listaUsuarios.size() - 1;
        return listaUsuarios.get(ultimoIndice);
    }
    
    public Usuario getUsuario(String userID) throws IOException, ClassNotFoundException {
        desserializaUsuarios();
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getUserID().equals(userID)) {
                return usuario;
            }
        }
        return null;
    }
    
    public boolean removeUsuario(Usuario usuarioRemover) throws IOException, ClassNotFoundException {
        desserializaUsuarios();
        for (Usuario usuario : listaUsuarios) {
            if (usuario.comparaID(usuarioRemover)) {
                usuarioRemover = usuario;
            }
        }
        removeDeTodasListasSeguidos(usuarioRemover);
        apagaTodosOsPosts(usuarioRemover);
        boolean remove = listaUsuarios.remove(usuarioRemover);
        serializaUsuarios();
        return remove;
    }
    
    private void removeDeTodasListasSeguidos(Usuario usuarioRemover) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.estaSeguindo(usuarioRemover)) {
                usuario.removeSeguindo(usuarioRemover);
            }
        }
    }
    
    private void apagaTodosOsPosts(Usuario usuarioRemoverPosts) throws ClassNotFoundException, IOException {
        ControllerPost controllerPost = new ControllerPost();
        controllerPost.excluirTodosPosts(usuarioRemoverPosts);
    }
    
    public boolean LoginExiste(String login) throws IOException, ClassNotFoundException {
        desserializaUsuarios();
        return listaUsuarios.stream().anyMatch(usuario -> (usuario.getLogin().equals(login)));
    }
    
    public void atualizaCadastro(String nome, String sobrenome, String cidade, String email,
            String fone, String sexo, String dataNascimento, String login, String senha, ArrayList<String> interesses) throws IOException, ClassNotFoundException {
        desserializaUsuarios();
        for (Usuario usuario : listaUsuarios) {
            if (usuario.comparaID(usuarioLogado)) {
                usuario.atualizarCadastro(nome, sobrenome, cidade, email, fone, sexo, dataNascimento, login, senha, interesses);
            }
        }
        serializaUsuarios();
    }
}
