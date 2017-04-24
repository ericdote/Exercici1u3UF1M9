/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercici1u3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author Eric
 */
public class Exercici1u3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, FileNotFoundException, NoSuchAlgorithmException, NoSuchAlgorithmException, NoSuchAlgorithmException, NoSuchAlgorithmException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        //Creamos usuarios
        Usuari u = new Usuari("54545454X", "Eric", "Dote Sillero");
        Usuari u2 = new Usuari("12121212Y", "Pepe", "Lopez Gertrudis");
        //Declaramos la SecreyKey
        SecretKey key;
        //Seteamos usuario y password
        u.setUsuari("Rovx");
        u.setPassword("HolaKase");
        u2.setUsuari("Lacoste");
        u2.setPassword("1234");
        //Añadimos los usuarios.
        fitxerUsuaris fu = new fitxerUsuaris();
        fu.crearUsuariFitxer(u);
        fu.crearUsuariFitxer(u2);
        //Generamos la clave.
        key = fu.generarClau("hola");
        //Ciframos el fichero.
        fu.xifrarFitxer("dadesUsuari.txt", key);
        //Desciframos el fichoer.
        fu.desxifrarFitxer("fitxerEncriptatUsuaris.txt", key, "fitxerDesencriptat.txt");
        
        
    }
    
}
