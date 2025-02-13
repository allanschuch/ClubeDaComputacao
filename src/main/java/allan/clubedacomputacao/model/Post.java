/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package allan.clubedacomputacao.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Allan
 */
public class Post implements Serializable, Comparable<Post> {
    
    private static final long serialVersionUID = 2L;
    private String conteudo;
    private String timestamp;
    private String autorID;
    private String postID;
    private String nomeAutor;

    public Post(String conteudo, String autorID, String postID, String nomeAutor) {
        this.conteudo = conteudo;
        this.autorID = autorID;
        this.postID = postID;
        this.nomeAutor = nomeAutor;
        SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy '-' HH:mm:ss");
        this.timestamp = data.format(new Date());
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getAutorID() {
        return autorID;
    }

    public String getPostID() {
        return postID;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    @Override
    public int compareTo(Post outroPost) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy '-' HH:mm:ss");
        Date dataPost = null;
        Date dataOutroPost = null;
        try {
            dataPost = sdf.parse(timestamp);
            dataOutroPost = sdf.parse(outroPost.getTimestamp());
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        
        return dataPost.compareTo(dataOutroPost);
        }
    
    @Override
    public String toString(){
        return "Post ID " + postID + ", por " + nomeAutor + ", em " + timestamp;
    }
}
