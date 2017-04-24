/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercici1u3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Eric
 */
public class fitxerUsuaris {

    private File file = new File("dadesUsuari.txt");
    private SecretKey key;
    private String cadena_iv = "0123456789ABCDEF";
    private static final byte[] IV_PARAM = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F};

    /**
     * Aquest metode li arriba una contrasenya i un tamany i la genera en AES i
     * SHA-256.
     *
     * @param contrasenya
     * @param tamany
     * @return
     * @throws UnsupportedEncodingException
     */
    public SecretKey generarClau(String contrasenya) throws UnsupportedEncodingException {
        //Mira si el tamany de la contrasenya es el correcte
        try {
            //Passa la contrasenya a bytes
            byte[] data = contrasenya.getBytes("UTF-8");
            //Declarem el MessageDigest a SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            //Passem la contrasenya a bytes amb el Digest
            byte[] hash = md.digest(data);
            //Generem la clau secreta amb AES
            key = new SecretKeySpec(hash, "AES");
            System.out.println("Generiacio de clau creada.");
        } catch (NoSuchAlgorithmException ex) {
            System.err.println("Generador no disponible.");
        }
        return key;
    }

    /**
     * Aquest metode xifra el fitxer amb la contrasenya per tal de no poder
     * obtenir la informacio si no disposem de la clau.
     *
     * @param fitxer
     * @param clau
     * @throws FileNotFoundException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IOException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public void xifrarFitxer(String fitxer, SecretKey clau) throws FileNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        FileInputStream fis = new FileInputStream(fitxer);
        FileOutputStream fos = new FileOutputStream(new File("fitxerEncriptatUsuaris.txt"));
        Cipher ci = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(cadena_iv.getBytes());
        ci.init(Cipher.ENCRYPT_MODE, clau, iv);
        byte[] buffer = new byte[1000];
        int bytes;
        while ((bytes = fis.read(buffer, 0, buffer.length)) != -1) {
            byte[] buffer2 = ci.update(buffer, 0, bytes);
            fos.write(buffer2);
        }
        System.out.println("Fitxer xifrat correctament");
        fos.write(ci.doFinal());
        fis.close();
        fos.close();
    }

    /**
     * Aquest metode li arriba un fitxer encriptat, una clau i una ruta per
     * desxifrar el fitxer. Fa basicament lo mateix que lo altre pero invertit,
     * encomptes de Encrypt fa Decrypt.
     *
     * @param fitxerEncriptat
     * @param clau
     * @param fitxerDesencriptat
     * @throws FileNotFoundException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IOException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public void desxifrarFitxer(String fitxerEncriptat, SecretKey clau, String fitxerDesencriptat) throws FileNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        FileInputStream fis = new FileInputStream(fitxerEncriptat);
        FileOutputStream fos = new FileOutputStream(new File(fitxerDesencriptat));
        Cipher ci = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(cadena_iv.getBytes());
        ci.init(Cipher.DECRYPT_MODE, clau, iv);
        byte[] buffer = new byte[1000];
        int bytes;
        while ((bytes = fis.read(buffer, 0, buffer.length)) != -1) {
            byte[] buffer2 = ci.update(buffer, 0, bytes);
            fos.write(buffer2);
        }
        System.out.println("Fitxer desxifrat correctament");
        fos.write(ci.doFinal());
        fis.close();
        fos.close();
    }
    /**
     * Metode per afegir usuaris al fitxer.
     * @param usu
     * @throws IOException 
     */
    public void crearUsuariFitxer(Usuari usu) throws IOException{
        FileWriter fw = new FileWriter(file, true);
        fw.write(usu.getNom() + ":" + usu.getCognoms() + ":" + usu.getDni() + ":" + usu.getUsuari() + ":" + usu.getPassword() + "          ");
        fw.close();
    }

}
