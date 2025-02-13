/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package allan.clubedacomputacao.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author Allan
 */
public final class GeradorID implements Serializable {

    private static final long serialVersionUID = 3L;
    private int contadorID = 0;

    public GeradorID() throws IOException, ClassNotFoundException {
        try {
            desserializaGeradorID();
        } catch (IOException ex) {
            serializaGeradorID();
        }
    }

    public int getContadorID() throws IOException {
        contadorID++;
        serializaGeradorID();
        return contadorID;
    }

    public void serializaGeradorID() throws IOException {
        FileOutputStream fileOut = new FileOutputStream("src/main/dados/geradorID.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(this.contadorID);
        out.close();
        fileOut.close();
    }

    public void desserializaGeradorID() throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream("src/main/dados/geradorID.ser");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        this.contadorID = (int) in.readObject();
        in.close();
        fileIn.close();
    }
}
