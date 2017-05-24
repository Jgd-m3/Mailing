/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsgn.m3z.msg.model;

import dsgn.m3z.msg.persistence.ObjReader;
import dsgn.m3z.msg.persistence.ObjWriter;
import dsgn.m3z.msg.util.ListaEnlazada;
import java.io.DataInputStream;
import java.io.DataOutputStream;
//import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that represents a Server.
 * @author M3z
 */
public class Server {
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">

    /**
     * Port by default.
     */
    private static final int PORT_DEF = 3333;
    
    /**
     * Port of the server.
     */
    private int port;
    
    /**
     * File where save and load the users and mails.
     */
    private String file;
    
    /**
     * ServerSocket to listen clients. 
     */
    private ServerSocket ssEntry;
    
    /**
     * Socket to send at client.
     */
    private Socket sSender;
    
    /**
     * ListaEnlazada that containshe registered users.
     */
    private final ListaEnlazada<User> userList;
    
    /**
     * Boolean to power off and power on the server functions.
     */
    private boolean onOff;

    /**
     * Array of Strings that contains the basics client's proccess.
     */
    private static final String[] ORDER = { "BYE", "LOGIN", "MSG", "SEND", "NEW", "DEL" };
//</editor-fold>



    //<editor-fold defaultstate="collapsed" desc="Metodos">

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    
    
    /**
     * Constructor of the server object.
     */
    public Server(){
        
        userList = new ListaEnlazada<>();
        sSender = null;
        ssEntry = null;
        file = "./UserList_DO_NOT_DELETE_THIS_FILE.m3";
        
    }

//</editor-fold>


    //<editor-fold defaultstate="collapsed" desc="Getters">

    @Override
    public String toString() {
        return "Server{" + "port=" + port + ", file=" + file + ", ssEntry=" 
                + ssEntry + ", sSender=" + sSender + ", userList=" 
                    + userList + ", onOff=" + onOff + '}';
    }
    

//</editor-fold>


    //<editor-fold defaultstate="collapsed" desc="Setters">

    /**
     * Method that add new user to the Userlist.
     * @param in new User to the server.
     * @return boolean that say if the user had been aggree successful or not.
     */
    public boolean addUser(User in){
        
        if (!this.isRegisterUser(in)){
            userList.add(in);
            return true;
        }
        return false;
    }

    /**
     * Method that add new user to the Userlist.
     * @param nick Username of the new user.
     * @param psw password of the new user.
     * @return boolean that say if the user had been aggree successful or not.
     */
    public boolean addUser(String nick, String psw){
        
        return this.addUser(new User(nick, psw));
    }
    
    /**
     * Method that removes an User from the Userlist.
     * @param in delete User from the server.
     * @return boolean that say if the user had been delete successful or not.
     */
    public boolean removeUser(User in){
        
        int pos = userList.indexOf(in);
        
        if ( pos > -1) {
            
            userList.remove(pos);
            return true;
        }
        return false;
    }
    
    
    /**
     * Method that removes an User from the Userlist.
     * @param nick String of Username to remove.
     * @return boolean that say if the user had been delete successful or not.
     */
    public boolean removeUser(String nick){
       
        return this.removeUser(new User(nick)) ;
    }
    
//</editor-fold>


    //<editor-fold defaultstate="collapsed" desc="Otros Metodos">

    /**
     * Method that returns if the User exists.
     * @param in User to check.
     * @return boolean, true if exists.
     */
    public boolean isRegisterUser(User in){
        
        return this.isRegisterUser(in.getNickname());
    }
    
    /**
     * method that returns if the Username exists.
     * @param nick String of Username to check.
     * @return boolean, true if exists.
     */
    public boolean isRegisterUser(String nick){
        
        boolean rtn = false;
        
        for (int i = 0; i < userList.size(); i++) {
            
            if (nick.equals(userList.get(i).getNickname())) rtn = true;
        }
        return rtn;
    }   
    
    /**
     * Method that returns if the User is valid.
     * @param in User to check.
     * @return boolean, true if the username and the password is correct.
     */
    public boolean isValidKeyUser(User in){
        
        return this.isValidKeyUser(in.getNickname(), in.getPass());
    }
    
    /**
     * Method that returns if the Username and the password are valid.
     * @param nick String: Username of the user to check.
     * @param psw String: Password of the user to check.
     * @return boolean, true if the username and the password is correct.
     */
    public boolean isValidKeyUser(String nick, String psw){
        
        boolean rtn = false;
        
        
        for (int i = 0; i < userList.size(); i++) {

            if (nick.equals(userList.get(i).getNickname()) 
                    && psw.equals(userList.get(i).getPass())){
                
                rtn = true;
            }
        }
        return rtn;        
    }
    
    
    /**
     * Method to connect the server and listent by port by default.
     */
    public void connect(){
        
        this.connect(PORT_DEF); 
    }
    
    /**
     * Method to connect the server and listen by the port of the param.
     * @param port (int): port to connect.
     */
    public void connect(int port) {
        
        this.port = port;
        onOff = true;
        
        if (file != null) this.loadUsers();
        
        try{
            
            System.out.printf("ha llegado al while, port: %d\n", this.port);
            ssEntry = new ServerSocket(this.port);
            
            while (onOff){
                
                
                sSender = ssEntry.accept();
                System.out.println("Cliente escuchado");
                
//                this.processClient(sSender);
                Runnable a = new HiloCliente(sSender);
                Thread hilo = new Thread(a);
                hilo.start();
            }
           
        }
        catch(IllegalArgumentException e){
            System.out.println("Connect's illegal port number");
        }
        catch(IOException e){
            System.out.println("Connect's IOException");
        }   
    }
    
    /**
     * Method to disconnect the server listening and save the Users in a file.
     */
    public void disconnect(){
        
        this.disconnect(file);
    }
    
    /**
     * Method to disconnect the server listening and save the Users in a file.
     * @param file File to save the Users and Mails.
     */
    public void disconnect(String file){
        
        port = -1;
        this.file = file;
        
        
        if(userList.size() > 0) this.saveUsers(file);
        
        if (onOff){
            
            try{
            
                ssEntry.close();
                if (sSender != null) sSender.close();
            }
            catch(IOException e){
                System.out.println("Disconnect's IOException");
            }
        }
        onOff = false;
    }
    
    /**
     * Method to save the users in file without params.
     */
    public void saveUsers(){
        ObjWriter save = new ObjWriter(file);
        int nS = save.write(userList); 
        
        System.out.printf("%d Users saved.\n", nS);
    }
    
    /**
     * Method to save the users in file.
     * @param path String: name of the path + filename where users will be saved.
     */
    public void saveUsers(String path){
        file = path;
        ObjWriter save = new ObjWriter(file);
        int nS = save.write(userList); 
        
        System.out.printf("%d Users saved.\n", nS);
    }
    
    /**
     * Method to load Users of the current saved file.
     */
    public void loadUsers(){
        
        int nL = 0;
        if (file != null){
            ObjReader load = new ObjReader(file);
            
            if (load.fExists()){
                nL = load.read(userList);
                System.out.printf("%d Users loaded.\n", nL);
            }
            else{
                System.out.println("There is not a User recovery file...");
            }
        }
    }
    
    /**
     * Method that proccess clients.
     * @param a Socket: of the current client.
     */
    public void processClient(Socket a){
        
        Socket client = a;
        String nick;
        String pass;
        User current = null;
        LinkedList<Mail> mails = null;
System.out.println("TRAZA: llega al procesar;");


        DataInputStream dis;
        DataOutputStream dos;
        ObjectInputStream ois;
        ObjectOutputStream oos;
                
        try{
            
            dis = new DataInputStream (client.getInputStream());
            dos = new DataOutputStream(client.getOutputStream());
            ois = new ObjectInputStream(client.getInputStream());
            oos = new ObjectOutputStream(client.getOutputStream());
            
            
            boolean stop = false;
            String order;
            boolean find;
            int op;
            
            
            while(!stop){
                
                order = dis.readUTF();
                System.out.printf("TRAZA: RECIBE ORDEN: %s\n", order);
                find = false;
                op = -1;

                for (int i = 0; i < ORDER.length && !find; i++) {
                    if (ORDER[i].equals(order)){
                        op = i;
                        find = true;
                    }
                }

                switch (op){
                    
                    case -1:
                    case 0:  
                        System.out.println("TRAZA serv: Salida correcta del switch");
                        dos.writeUTF("Sale bien");
                        stop = true;
                        break;
                        
                    case 1:
                        
                        System.out.println("TRAZA serv: Entra al login");
                        nick = dis.readUTF();
                        pass = dis.readUTF();
                        
                        if (!this.isRegisterUser(nick)){
                            dos.writeUTF("Unknown User");
                            break;
                        }
                        
                        if (!this.isValidKeyUser(nick, pass)){
                            dos.writeUTF("Pass error");
                            break; 
                        }
                        else{
                            int pos = userList.indexOf(new User(nick,pass));
                            current = userList.get(pos);
                            dos.writeUTF("OK");
                        }
                        break;
                    
                    case 2:
                        System.out.printf("User: %s\n", current.getNickname());
                        System.out.println("TRAZA serv: entra en MSG");
                        if (current != null) mails = current.getMailList();
                  
                        if (mails != null){
                            int size = mails.size();
                            boolean clientRet;
                            dos.writeInt(size);
                            System.out.println("Entra al MailList != null");
                            Mail actu;
                            Object obj;
//                            oos.writeObject(mails);
                            for (int i = 0; i < size; i++) {
                                System.out.printf("Manda %d\n", i);
                                actu = mails.get(i);
                                System.out.println("TRAZA: " + actu.toString());
                                obj = (Object) actu;
                                oos.writeObject(obj);
//                                clientRet = ois.readBoolean();
//                                System.out.printf("TRAZA: respuesta %b\n", clientRet);
                            }
                        }
                        else dos.write(0);
                        
                        break;

                    case 3: 
                        System.out.println("TRAZA serv: entra en SEND");
                        Object in;
                        Mail entry;
                        User modif;
                        String nickRec;
                        
                        try{
                            
                            in = ois.readObject();
                            if (!(in instanceof Mail)){
                                // throws ClassNotFoundException;
                                System.out.println("That's not an Email.");
                                break;
                            }
                            
                            entry = (Mail) in;
                            System.out.println("TRAZA Mail: " + entry);
                            nickRec = entry.getReceptor();
                            System.out.println("TRAZA UserReceptor: " + nickRec);
                            if (this.isRegisterUser(nickRec)){
                                System.out.println("TRAZA: entra al if isRegisterUser");
                                int pos = userList.indexOf(new User(nickRec));
                                modif = userList.get(pos);
                                modif.addMail(entry);
                                System.out.println("MailAgregado");
//                                dos.writeUTF("Correo Guardado");
                            }
                            else{
                                System.out.println("TRAZA: User no registrado");
                            }
                            
                        }
                        catch(ClassNotFoundException e){
                            System.out.println("SEND exception [ClassNotFound]");
                        }
                        
                        break;
                    
                    case 4:
                        
                        try{
                          //  System.out.println("TRAZA serv: Entra al NEW USER");
                            in = ois.readObject();
                            if (!(in instanceof User)){
                                // throws ClassNotFoundException;
                                System.out.println("That's not an User.");
                                break;
                            }
                            
                            current = (User) in;
                           
                            if (!this.isRegisterUser(current)){
                                this.addUser(current);
                                dos.writeUTF("User created");
                            }
                            else{
                                dos.writeUTF("User exists, choose another");
                            }
                        }
                        catch(ClassNotFoundException e){
                            System.out.println("NEW exception [ClassNotFound]");
                        }
                        break;
                        
                    case 5:
                        
                        try{
                            System.out.println("TRAZA serv: Entra al DELETE USER");
                            in = ois.readObject();
                            if (!(in instanceof User)){
                                // throws ClassNotFoundException;
                                System.out.println("That's not an User.");
                                break;
                            }
                            
                            current = (User) in;
                           
                            if (this.isRegisterUser(current)){
                                this.removeUser(current);
                            }
                            
                        }
                        catch(ClassNotFoundException e){
                            System.out.println("NEW exception [ClassNotFound]");
                        }
                        break;     
                }
                System.out.println("TRAZA: sale del case ------------------");
                order = null;
            }
            
            dis.close();
            dos.close();
            ois.close();
            oos.close();
            client.close();
        }
        catch(IOException e){
            System.out.println("Cacho exception - [ProcessClient]");
            e.printStackTrace();
        }
    }
//</editor-fold>


//</editor-fold>

    
    
    //<editor-fold defaultstate="collapsed" desc="Setters Para darle funcionalidad a los JTests">
    
    /**
     * Test's setters to comprove the differents methods by the tests Packages, 
     * Usually commented,Uncomment to use them.
     * @param file --
     */
    public void setFile(String file) {
        this.file = file;
    }
    /**
     * Test's setters to comprove the differents methods by the tests Packages, 
     * Usually commented, Uncomment to use them.
     * @param file --
     */
    public void setOnOff(boolean onOff) {
        this.onOff = onOff;
    }

    /**
     * Test's setters to comprove the differents methods by the tests Packages, 
     * Usually commented, Uncomment to use them.
     * @param file --
     */
    public void setSsEntry(ServerSocket ssEntry) {
        this.ssEntry = ssEntry;
    }

    /**
     * Test's setters to comprove the differents methods by the tests Packages, 
     * Usually commented, Uncomment to use them.
     * @param file --
     */
    public void setsSender(Socket sSender) {
        this.sSender = sSender;
    }
    
    
//</editor-fold>

    
    
    //<editor-fold defaultstate="collapsed" desc="Runnable">

    /**
     * Runnable class to give service at different clients at time.
     */
    private class HiloCliente implements Runnable{
        
        /**
         * Socket of the current thread client.
         */
        private final Socket client;

        /**
         * Constructor of the class runnable.
         * @param a 
         */
        public HiloCliente(Socket a){

            client = a;
        }

        /**
         * Method run, to call at the method proccessClient().
         */
        @Override
        public void run() { 
            System.out.println("Llega al run!");
            processClient(client);
        }
    }
//</editor-fold>
    
    //Fin del Class  
}