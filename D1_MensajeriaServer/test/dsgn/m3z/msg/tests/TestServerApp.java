/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsgn.m3z.msg.tests;

import dsgn.m3z.msg.model.Mail;
import dsgn.m3z.msg.model.Server;
import dsgn.m3z.msg.model.User;

/**
 *
 * @author M3z
 */
public class TestServerApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Server n = new Server();
//        System.out.println("Agregamos clientes");
        System.out.println("ServerCreated");
        User a = new User("m3", "1");
        
        System.out.printf("User creado?: %b\n", a);
        Mail e1 = new Mail("m3", "Javi", "Hola puto", "Saludo");
        Mail e2 = new Mail("m3", "Javi", "Adios puto", "Despedida");
        a.addMail(e1);
        a.addMail(e2);
        n.addUser(a);
        n.addUser("Javi", "1");
        n.connect(3333);
//        descomentar las dos lineas anteriores y hasta aquí para verificar el NEW y el LOGIN
        
//        n.addUser(new User("Javi", "pc15"));
//        User bach = new User("Bacho", "pc8");
//        n.addUser(bach);
        // hasta aquí para verificar el SEND
        
//        Mail e1 = new Mail("Bacho", "Javi", "Hola puto", "Saludo");
//        Mail e2 = new Mail("Bacho", "Javi", "Adios puto", "Despedida");
//        bach.addMail(e1);
//        bach.addMail(e2);
        // hasta aquí para verificar el MSG

//        System.out.println("ServerCreated");
//        n.connect(3333);     
       
        
    }
    
}
