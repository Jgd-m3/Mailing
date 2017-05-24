/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsgn.m3z.msg.tests;

import dsgn.m3z.msg.model.Mail;
import dsgn.m3z.msg.model.User;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

/**
 *
 * @author M3z
 */
public class TestClientApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException {
        
        
        try {
            Socket tCliSock = new Socket("127.0.0.1", 3333);
            
            DataOutputStream dos = new DataOutputStream(tCliSock.getOutputStream());
            DataInputStream dis = new DataInputStream(tCliSock.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(tCliSock.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(tCliSock.getInputStream());

            // probamos el BYE - Verified
//            dos.writeUTF("BYE");
//            System.out.println("Aqui la respuesta: " + dis.readUTF());
            
            // probamos el Registrar User - Verified
            dos.writeUTF("NEW");
            User u1 = new User("Javi", "pc15");
            oos.writeObject(u1);
            System.out.println("Aqui la respuesta: " + dis.readUTF());
            dos.writeUTF("NEW");
            User u2 = new User("Bacho", "pc8");
            oos.writeObject(u2);
            System.out.println("Aqui la respuesta: " + dis.readUTF());

            //verificamos el login user 
            dos.writeUTF("LOGIN");
            dos.writeUTF("Javi");
            dos.writeUTF("pc15");
            System.out.println("Aqui la respuesta: " + dis.readUTF());

            //verificamos el Send - verified
            Mail e1 = new Mail("Bacho", "Javi", "Hola puto", "Saludo");
//            User a1 = new User("Pepe", "pedro");
            Mail e2 = new Mail("Bacho", "Javi", "Adios puto", "Despedida");
            dos.writeUTF("SEND");
            oos.writeObject(e1);
            System.out.println("Aquí la respuesta: " + dis.readUTF());
//            oos.writeObject(a1);
            dos.writeUTF("SEND");
            oos.writeObject(e2);
            System.out.println("Aquí la respuesta: " + dis.readUTF());
            
            // logueando a bacho
            dos.writeUTF("LOGIN");
            dos.writeUTF("Bacho");
            dos.writeUTF("pc8");
            System.out.println("Aqui la respuesta: " + dis.readUTF());
            
            //verificamos el MSG -                                                  NO VERIFICADO OJO
            dos.writeUTF("MSG");
            int num = dis.readInt();
            LinkedList<Mail> mails = new LinkedList<>();
            Mail[] correosBacho = new Mail[2];
            for (int i = 0; i < num; i++) {
//                correosBacho[i] = (Mail) ois.readObject();
//                System.out.println(correosBacho[i]);
//                dos.writeBoolean(true);
                mails.add((Mail) ois.readObject());
                System.out.println(mails.get(i));
            }
            System.out.println("Aqui la respuesta, correos recuperados: " + num);
            
        }  
        catch(UnknownHostException e){
            System.out.println("HostDesconocido Except");
        }
        catch(StreamCorruptedException e){
            System.out.println("StreamCorrupted Exception");
        }
        catch(IOException e){
            System.out.println("Otra IOException");
        }
        
        // bucle infinito
//        while(true){
//        }
//        } catch (IOException ex) {
//        }
    }
    
}
