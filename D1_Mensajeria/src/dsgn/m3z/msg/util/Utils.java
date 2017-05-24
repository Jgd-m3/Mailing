/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsgn.m3z.msg.util;

/**
 * Class that implements diferent utilities.
 * @author M3z
 */
public class Utils {
    
    /**
     * Method to Generate a random password with the size choosed.
     * @param size int: size of the new password.
     * @return String: new random password.
     */
    public static String passGenerator(int size){
        
        int gen;
        StringBuilder pw = new StringBuilder();
        
        for (int i = 0; i < size; i++) {
            gen = (int) (Math.random()*(123-48))+48;
            pw.append((char) gen);
        }
        
        return pw.toString();
    }
    
    
}
