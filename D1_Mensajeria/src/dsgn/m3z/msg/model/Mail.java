/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsgn.m3z.msg.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author M3z
 */
public class Mail implements Serializable{
    
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">

    /**
     * SerialVersionUID de la clase Mail.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Creation date.
     */
    private Date creation;
    
    /**
     * Sender user nickname.
     */
    private String sender;
    
    /**
     * Receptor user nickname.
     */
    private String receptor;
    
    /**
     * Text of Email.
     */
    private String mailText;
    
    /**
     * True if the mail has an encrypt text.
     */
    private boolean encrypt;
    
    /**
     * Password to decrypt the text.
     */
    private String passCode;
    
    /**
     * Subject of the Email.
     */
    private String subject;
    
//</editor-fold>



    //<editor-fold defaultstate="collapsed" desc="Metodos">


    //<editor-fold defaultstate="collapsed" desc="Constructor">

    /**
     * Constructor de la clase Mail.
     * @param recept - Receptor's Nickname.
     * @param send - Sender's Nickname.
     * @param text  - Text of Email.
     * @param subj - Subject of Email.
     */
    public Mail(String recept, String send, String text, String subj){
        
        sender = send;
        receptor = recept;
        mailText = text;
        creation = new Date();
        subject = subj;
        encrypt = false;
        passCode = null;
    }
    
    /**
     * Constructor de la clase Mail.
     * @param recept - Receptor's Nickname.
     * @param send - Sender's Nickname.
     * @param text  - Text of Email.
     * @param subj - Subject of Email.
     * @param psw - Encryption password.
     */
    public Mail (String recept, String send, String text, String subj, String psw){
        
        this(recept, send, text, subj);
        encrypt = true;
        passCode = psw;
//        encrypt(text);
        encrypt();
    }
//</editor-fold>


    //<editor-fold defaultstate="collapsed" desc="Getters">
    
    /**
     * Return the email creation date.
     * @return Date.
     */
    public Date getCreation() {
        return creation;
    }

    /**
     * Return the Sender's nickname.
     * @return String.
     */
    public String getSender() {
        return sender;
    }

    /**
     * Return the Receptor's nickname.
     * @return String.
     */
    public String getReceptor() {
        return receptor;
    }

    /**
     * Return the text of the email.
     * @return String.
     */
    public String getMailText() {
        return mailText;
    }

    /**
     * Return if the Email is encrypted (true) or not (false).
     * @return boolean.
     */
    public boolean isEncrypt() {
        return encrypt;
    }
    
    /**
     * Return the subject of the email.
     * @return String.
     */
    public String getSubject(){
        return subject;
    }
//</editor-fold>


    //<editor-fold defaultstate="collapsed" desc="Setters">

    /**
     * Method to encrypt the text of the email.
     * @param txt (String) - email text not encrypted.
     */
//    public void encrypt(String txt){
//        
//        StringBuilder txtBuff = new StringBuilder(txt);
//        int curr;
//        
//        for (int i = 0; i < mailText.length(); i++) {
//            
//            curr = (int) txtBuff.charAt(i);
//            
//            if (i % 2 == 0){
//                txtBuff.setCharAt(i, (char)(curr + 23));
//            }
//            else if (curr >= 32) {
//                txtBuff.setCharAt(i, (char)(curr - 11));
//            }
//        }
//        
//        this.setMailText(txtBuff.toString());
//    }
    
    
    /**
     * Method to encrypt the text of the email.
     */
    public void encrypt(){
        
        StringBuilder txtBuff = new StringBuilder(mailText);
        int curr;
        
        for (int i = 0; i < mailText.length(); i++) {
            
            curr = (int) txtBuff.charAt(i);
            
            if (i % 2 == 0){
                txtBuff.setCharAt(i, (char)(curr + 23));
            }
            else if (curr >= 32) {
                txtBuff.setCharAt(i, (char)(curr - 11));
            }
        }
        
        this.setMailText(txtBuff.toString());
    }
    
    /**
     * Method to decrypt the email text.
     * @param pw (String) - password input by the Receptor.
     * @return boolean - true if decryption was successful.
     */
    public boolean decrypt(String pw){
        
        if ( pw.equals(passCode)){
            
            StringBuilder txtBuff = new StringBuilder(mailText);
            int curr;

            for (int i = 0; i < mailText.length(); i++) {

                curr = (int) txtBuff.charAt(i);

                if (i % 2 == 0){
                    txtBuff.setCharAt(i, (char)(curr - 23));
                }
                else if (curr >= 32) {
                    txtBuff.setCharAt(i, (char)(curr + 11));
                }
            }
            this.setMailText(txtBuff.toString());
            encrypt = false;
        }
        return !encrypt;
    }
    
    /**
     * Setter to edit the text of the email.
     * @param in (String) - new text of the email.
     */
    private void setMailText(String in){
        mailText = in;
    }
    
//</editor-fold>


    //<editor-fold defaultstate="collapsed" desc="Otros Metodos">
    
    /**
     * Method toString() of mail class.
     * @return String - Mail data.
     */
    @Override
    public String toString() {
        StringBuilder tStr = new StringBuilder();
        
        tStr.append("E-Mail de : ");
        tStr.append(sender);
        tStr.append(" enviado a: ");
        tStr.append(receptor);
        if (encrypt){
            tStr.append(" [cifrado]");
        }
        tStr.append(" - ");
        tStr.append(creation.toString());
        tStr.append(".");
        
        return tStr.toString();
    }
    

//</editor-fold>


//</editor-fold>
    
    

    //Fin de Class
}
