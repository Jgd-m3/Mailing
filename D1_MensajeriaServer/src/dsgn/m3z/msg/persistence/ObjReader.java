/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsgn.m3z.msg.persistence;

import dsgn.m3z.msg.model.Server;
import dsgn.m3z.msg.model.User;
import dsgn.m3z.msg.util.ListaEnlazada;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Class to represent an object to load an userlist.
 * @author M3z
 */
public class ObjReader {
    
    /**
     * Filename where the objects will load.
     */
    private String file;
    
    
    /**
     * constructor of the Reader.
     * @param filename 
     */
    public ObjReader(String filename){
        file = filename;
    }
    
    /**
     * method to comprove if the file exists.
     * @return true if exists. false if not.
     */
    public boolean fExists(){
        
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
            
        }
        catch (FileNotFoundException e){
            System.out.println("File not found");
            return false;
        }
        catch (IOException e){
            System.out.println("Another exception");
            return false;
        }

        return true;
    }
    
    
    /**
     * Method to return the number of the users loaded.
     * @param n Userlist to insert the loaded users.
     * @return amount of users loaded.
     */
    public int read(ListaEnlazada<User> n){
        
        int rtn = 0;
        User readed;
        
        try (ObjectInputStream ois = new ObjectInputStream (new FileInputStream(file))){
            
            while (true){
                readed = (User) ois.readObject();
                n.add(readed);
                rtn ++;                
            }
        }
        catch (FileNotFoundException e){
            rtn = -1;
            System.out.println("File not found");
        }
        catch (ClassNotFoundException e){
            System.out.println("cacota gorda");
            rtn = -1;
        }
        catch (EOFException e){
            System.out.println("file ends");
        }
        catch(IOException e){
            rtn = -1;
            System.out.println("Another exception");
        }
        
        return rtn;
    }
    
}
