/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package allan.clubedacomputacao.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Allan
 */
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nome;
    private String sobrenome;
    private String cidade;
    private String email;
    private String fone;
    private String sexo;
    private String dataNascimento;
    private String login;
    private String senha;
    private String caminhoAvatar;
    private final String userID;
    private boolean privilegioAdmin;
    private ArrayList<String> listaInteresses;
    private ArrayList<Usuario> listaSeguindo;

    //Construtor de Usuario
    public Usuario(String nome, String sobrenome, String cidade, String email,
            String fone, String sexo, String dataNascimento, String login, String senha,
            boolean privilegioAdmin, String userID, String caminhoAvatar, ArrayList<String> interesses) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cidade = cidade;
        this.email = email;
        this.fone = fone;
        this.sexo = sexo;
        this.caminhoAvatar = caminhoAvatar;
        this.dataNascimento = dataNascimento;
        this.login = login;
        this.senha = senha;
        this.privilegioAdmin = privilegioAdmin;
        this.userID = userID;
        listaInteresses = interesses;
        listaSeguindo = new ArrayList<>();
    }

    // Construtor de admin
    public Usuario(String nome, String sobrenome, String login, String senha, String userID) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.login = login;
        this.senha = senha;
        this.privilegioAdmin = true;
        this.userID = userID;
    }

    public void atualizarCadastro(String nome, String sobrenome, String cidade, String email,
            String fone, String sexo, String dataNascimento, String login, String senha, ArrayList<String> interesses) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cidade = cidade;
        this.email = email;
        this.fone = fone;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
        this.login = login;
        this.senha = senha;
        listaInteresses = interesses;
    }

    public ArrayList<String> getListaInteresses() {
        return listaInteresses;
    }

    public ArrayList<Usuario> getListaSeguindo() {
        return listaSeguindo;
    }

    public String getUserID() {
        return userID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCaminhoAvatar() {
        return caminhoAvatar;
    }

    public void setCaminhoAvatar(String caminhoAvatar) {
        this.caminhoAvatar = caminhoAvatar;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isPrivilegioAdmin() {
        return privilegioAdmin;
    }

    public void setPrivilegioAdmin(boolean privilegioAdmin) {
        this.privilegioAdmin = privilegioAdmin;
    }

    public boolean comparaID(Usuario usuario) {
        return usuario.getUserID().equals(this.userID);
    }

    public boolean estaSeguindo(Usuario user) {
        if(listaSeguindo != null){
            for (Usuario usuario : listaSeguindo) {
                if (usuario.comparaID(user)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean addSeguindo(Usuario usuarioParaAdicionar) {
        if (this.estaSeguindo(usuarioParaAdicionar)) {
            return false;
        } else {
            return listaSeguindo.add(usuarioParaAdicionar);
        }
    }

    public boolean removeSeguindo(Usuario usuarioParaRemover) {
        for (Usuario usuarioSeguido : listaSeguindo) {
            if (usuarioSeguido.comparaID(usuarioParaRemover)) {
                return listaSeguindo.remove(usuarioSeguido);
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String privilegio;
        if (this.privilegioAdmin) {
            privilegio = "Admin";
        } else {
            privilegio = "Comum";
        }
        return this.nome + " " + this.sobrenome + ", " + privilegio
                + ", ID " + this.userID;
    }
}
