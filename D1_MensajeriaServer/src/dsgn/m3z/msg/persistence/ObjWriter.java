/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsgn.m3z.msg.persistence;

import dsgn.m3z.msg.model.User;
import dsgn.m3z.msg.util.ListaEnlazada;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Object that implements an userlist writter to save in a file.
 * @author M3z
 */
public class ObjWriter {
    
    /**
     * file where the Users will be save.
     */
    private String file;
    
    
    /**
     * constructor of the WriterObject.
     * @param filename String: file where the users will be save.
     */
    public ObjWriter(String filename){
        file = filename;
    }
    
    
    /**
     * method to save one single User.
     * @param u User to save.
     * @return Number of Users saved. (1).
     */
    public int write(User u){
        
        ListaEnlazada<User> uL = new ListaEnlazada<>();
        
        uL.add(u);
        
        return this.write(uL);
    }
    
    /**
     * Method to save a Userlist into a file.
     * @param uList ListaEnlazada of Users to save into the file.
     * @return number of users saved.
     */
    public int write (ListaEnlazada uList){
        
        int rtn = 0;
        
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))){
            
            for (int i = 0; i < uList.size(); i++) {
                oos.writeObject(uList.get(i));
                rtn ++;
            }
        }
        catch(IOException e){
            System.out.println("Exception: " + e);
            rtn = -1;
        }
        
        return rtn;
    }
    
    
// fin del Class
}
