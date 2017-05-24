/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsgn.m3z.msg.model;

import dsgn.m3z.msg.util.Utils;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.Objects;

/**
 *  Clase que implementa una clase Usuario que ademas se puede serializar.
 * @author M3z
 */
public class User implements Serializable{
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">

    /**
     * Serial de la version actual.
     */
     private static final long serialVersionUID = 1L;
     
    /**
     * Username of the user.
     */
    private String nickname;
    
    /**
     * Password of the user.
     */
    private String pass;
    
    /**
     * Real Name of the user.
     */
    private String name;
    
    /**
     * Registration date of the user.
     */
    private Date creation;
    
    /**
     * Mail list of the user.
     */
    private LinkedList<Mail> mailList;
    
    /**
     * Number of mails recived.
     */
    private int totMails;

    /**
     * Length for the random password, when de user do not choose one.
     */
    private static final int PW_LENGTH_DEF = 6;
    
    /**
     * "Realname" for users by default when they dont say their real name at registration.
     */
    private static final String NO_NAME = "Anonym";
//</editor-fold>



    //<editor-fold defaultstate="collapsed" desc="Metodos">


    //<editor-fold defaultstate="collapsed" desc="Constructor">
    
    /**
     * Constructor of User class.
     * @param nick (String) - Username to registration.
     */
    public User(String nick){
        
        this(nick,  Utils.passGenerator(PW_LENGTH_DEF), NO_NAME);
    }
    
    /**
     * Override constructor of User Class.
     * @param nick (String) - Username to registration.
     * @param psw (String) - Password to registration.
     */
    public User(String nick, String psw){
        
        this(nick, psw, NO_NAME);
    }

    /**
     * Override constructor of User Class.
     * @param nick (String) - Username to registration.
     * @param nam (String) - Realname to registration.
     * @param psw (String) - Password to registration.
     */
    public User(String nick, String psw,String nam ){
        
        nickname = nick;
        name = nam;
        creation = new Date();
        pass = psw;
        mailList = new LinkedList<>();
        totMails = 0;
    }
//</editor-fold>

    
    //<editor-fold defaultstate="collapsed" desc="Getters">
    
    /**
     * Getter to return the Nickname.
     * @return nickname - Username of this user.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Getter to return the Password.
     * @return Password of this user.
     */
    public String getPass() {
        return pass;
    }

    /**
     * Getter to return the Realname.
     * @return Realname of this user.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter to return the registration's date.
     * @return Date of the registration.
     */
    public Date getCreation() {
        return creation;
    }

    /**
     * Getter to return the amount of mails recived.
     * @return amount of mails.
     */
    public int getTotMails() {
        return totMails;
    }
    
    
    /**
     * Getter to return the Mail required.
     * @param n number of element of the list that you want to return.
     * @return mail from the position n.
     */
    public Mail getMail(int n) {
        return mailList.get(n);
    }
    
    
    /**
     * Return the mail list.
     * @return linkedlist that contains all the mails recived.
     */
    public LinkedList<Mail> getMailList() {
        return mailList;
    }

//</editor-fold>


    //<editor-fold defaultstate="collapsed" desc="Setters">

    /**
     * Setter that changes the pass.
     * @param pass - new password of the user.
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * setter that changes the real name.
     * @param name - new Realname of the user.
     */
    public void setName(String name) {
        this.name = name;
    }


//</editor-fold>


    //<editor-fold defaultstate="collapsed" desc="Otros Metodos">
    
    /**
     * Method to add mail to the Maillist of the user.
     * @param msg - Mail added to the list.
     */
    public void addMail(Mail msg){
        mailList.add(msg);
        totMails ++;
    }
    
    /**
     * Returns a representation of the object.
     * @return String with dates of the user.
     */
    @Override
    public String toString() {
        
        StringBuilder ts = new StringBuilder();
        
        ts.append("El usuario: ");
        ts.append(nickname);
        ts.append(" (");
        ts.append(name);
        ts.append(" ) tiene: ");
        ts.append(totMails);
        ts.append(", registrado: ");
        ts.append(creation);
        
        return ts.toString();
    }

    /**
     * Return if an object is equals at this user.
     * @param obj object to compare.
     * @return true if the object is an user and it's this user.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        return Objects.equals(this.nickname, other.nickname);
    }

//</editor-fold>

//</editor-fold>

       

    //Fin de Class
}
