/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsgn.m3z.msg.tests;

import dsgn.m3z.msg.model.Server;
import dsgn.m3z.msg.model.User;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author M3z
 */
public class JTestServer {
    
    static Server server1;
    Server server2;
    User u1;
    User u2;
    User u3;
    public JTestServer() {
        
        server1 = new Server();
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("¡¡¡OJO!!! HAY QUE DESCOMENTAR LOS SETTERS CREADOS PARA EL TEST ANTES");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        u1  = new User ("Edi", "pc3");
        u2  = new User ("Javi", "javi atras atras", "pc15");
        u3  = new User ("Bachazo");        
        
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void testConstructor(){
        System.out.println(server1.toString());
    }
    
    @Test
    public void testAddUser(){
        
        assertTrue("add u1", server1.addUser(u1));
        assertTrue("add u2", server1.addUser(u2));
        assertTrue("add u3", server1.addUser(u3));
        assertTrue("add nick+pass", server1.addUser("cristian", "Pc2"));
        assertFalse("add u1 por segunda vez", server1.addUser(u1));

    }
    
    @Test
    public void testRemoveUser(){
        
        server1.addUser("cristian", "Pc2");
        server1.addUser(u1);
        server1.addUser(u2);
        server1.addUser(u3);
        User u4 = new User("Bachazo", "pc142");
       
        assertTrue("remove u1", server1.removeUser(u1));
        assertFalse("remove CrisTian", server1.removeUser("CrisTian"));
        assertTrue("remove cristian", server1.removeUser("cristian"));
        assertTrue("remove u2 por string", server1.removeUser("Javi"));  
        assertTrue("remove u3", server1.removeUser(u4));
    }
    
    @Test
    public void testIsRegisterUser(){
        
        server1.addUser(u1);
        server1.addUser(u2);
        server1.addUser(u3);
        User u4 = new User("Bachazo", "pc142");

        assertTrue("isReg u1", server1.isRegisterUser(u1));
        assertFalse("isReg JaVi", server1.isRegisterUser("JaVi"));
        assertTrue("isReg Javi", server1.isRegisterUser("Javi"));    
        assertTrue("isReg u3", server1.isRegisterUser(u4));
    }
    
    @Test
    public void testIsValidKeyUser(){
        
        server1.addUser(u1);
        server1.addUser(u2);
        server1.addUser("Bachazo", "pc142");
        
        assertTrue("isVerif u1", server1.isValidKeyUser(u1));
        assertFalse("isVerif Javi/lala", server1.isValidKeyUser("Javi", "lala"));
        assertTrue("isVerif Javi/pc15", server1.isValidKeyUser("Javi", "pc15"));  
        assertFalse("isVerif u3 Bachazo/[random]", server1.isValidKeyUser(u3));
        assertTrue("isVerif Bachazo/pc142", server1.isValidKeyUser("Bachazo", "pc142"));
        
    }
    
    @Test
    public void testSaveUsers(){
        
        server1.addUser(u1);
        server1.addUser(u2);
        User u4 = new User("Bachazo", "pc142");
        server1.addUser(u4);
        server1.addUser(new User("Mecawen", "terquedad"));
        
        server1.saveUsers("./TestPaJavi.m3z");
        
    }
    
    @Test
    public void testLoadUsers(){
        
        server1.setFile("./TestPaJavi.m3z");
        server1.loadUsers();
    }
    
    /**
     * Hay que descomentar los Setters creados para el test
     */
    @Test
    public void testDisconnect(){
        
        server1.addUser(u1);
        server1.addUser(u2);
        
        server1.setOnOff(true);
        try {
            server1.setSsEntry(new ServerSocket() );
            server1.setsSender(new Socket() );
        } catch (IOException ex) {
            Logger.getLogger(JTestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        server1.disconnect("./TestUsers.m3z");
    }
    
    
//    @Test
//    public void testThreadsClientSocket(){
//        
//        try {
//            Socket tCliSock = new Socket("127.0.0.1", 3333);
//            
//            Socket tCliSock2 = new Socket("127.0.0.2", 3333);
//            
//            Socket tCliSock3 = new Socket("127.0.0.3", 3333);
//            
//            Socket tCliSock4 = new Socket("127.0.0.4", 3333);
//            
//            
//            
//        } catch (IOException ex) {
//            Logger.getLogger(JTestServer.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    @Test
    public void testD_IO(){
        
        try {
            Socket tCliSock = new Socket("127.0.0.1", 3333);
            
            DataOutputStream dos = new DataOutputStream(tCliSock.getOutputStream());
            DataInputStream dis = new DataInputStream(tCliSock.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(tCliSock.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(tCliSock.getInputStream());
            
            assertNotNull(dos);
            assertNotNull(dis);
            assertNotNull(oos);
            assertNotNull(ois);
            
            
        } catch (IOException ex) {
            Logger.getLogger(JTestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
