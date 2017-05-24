package dsgn.m3z.msg.util;



/**
 * Clase que implementa una Lista Enlazada del Objeto que queramos.
 * @version - Validada.
 * @author M3z
 * @param <M>
 */


public class ListaEnlazada <M> {
    
    
    //<editor-fold defaultstate="collapsed" desc="Clase Nodo">

    private class Nodo{
        
        /**
         * Atributo que contiene el Objeto que almacena el nodo.
         */
        private M info;
        
        /**
         * Atributo que contiene la referencia al siguiente Nodo; null by default.
         */
        private Nodo sig;

        
        /**
         * Constructor de la clase Nodo. Crea Nodos dentro de una ListaEnlazada.
         * @param obj - objeto ingresado por parámetro para ser almacenado en el nodo.
         */
        private Nodo(M obj){
            
            this.info = obj;
            this.sig = null;
        }
        
        
    }
    
        
//</editor-fold>

    
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">

    /**
     * Atributo que contiene el Nodo que inicia la lista. <br> Null by default.
     */
    private Nodo inicial;
    
    

//</editor-fold>



    //<editor-fold defaultstate="collapsed" desc="Metodos">


    //<editor-fold defaultstate="collapsed" desc="Constructor">

    /**
     * Constructor que inicia la ListaEnlazada. El atributo inicial se inicializa a null.
     */
    public ListaEnlazada(){
        this.inicial = null;
    }
    

//</editor-fold>


    //<editor-fold defaultstate="collapsed" desc="Getters">

    /**
     * Getter booleano que devuelve si la lista está vacía o no.
     * @return true: si la lista está vacía.<br> false: si hay algun elemento.
     */
    public boolean isEmpty(){
        
        return (this.inicial == null);
    }
    
    
    /**
     * Getter que devuelve el tamaño de la lista.
     * @return cont (int) - Numero de elementos que contiene.
     */
    public int size(){
        
        int cont = 0;
        
        if(this.inicial != null){
            
            cont ++;
            Nodo aux = this.inicial;
            
            while(aux.sig != null){
                cont ++;
                aux = aux.sig;
            }
            
        }
        
        return cont;
       
    }
    
    
    /**
     * Getter que devuelve el objeto de la lista que está en 
     * la posición designada por parámetro.
     * @param index (int) - Posición de la lista de la que se desea obtener el objeto.
     * @return obj (M) - objeto retornado exitosamente de la lista.
     *          <br> null - si en esa posición no hay ningun objeto.
     */
    public M get(int index){
        
        M rdo = null;
        
        if(index < 0 || index >= this.size()){
            throw new IndexOutOfBoundsException("no se puede gettear");
        }
        
        else if(index == 0){
            
            rdo = this.inicial.info;
            
        }
        else{
            Nodo nAux = this.inicial;
            int cont = 0;
            
            while(cont < index){
                cont++;
                nAux = nAux.sig;
            }
            
            rdo = nAux.info;
        }
        
        return rdo;
    }

    /**
     * Getter de la posición que ocupa un objeto ingresado por parametro.
     * @param obj (M) - Objeto del que se desea saber la posición.
     * @return cont (int) - Posición que ocupa dentro de la lista el objeto.
     */
    public int indexOf(M obj){
        
        if(this.inicial == null){
            return -1;
        }
        
        int cont = 0;
        Nodo aux = this.inicial;

        while(aux.sig != null && !aux.info.equals(obj)){
            
            cont ++;
            aux = aux.sig;
        }
        
        if (aux.info.equals(obj)){
            
            return cont;
        }
        
        return -1;

    }
    
//</editor-fold>


    //<editor-fold defaultstate="collapsed" desc="Setters">

    /**
     * Setter que agrega un Objeto (M) al ultimo lugar de la lista.
     * @param newObj (M) - Objeto que se agrega a la lista.
     * @return True si la agregacion ha sido correcta;
     */
    public boolean add(M newObj){
        /*
        Nodo niu = new Nodo(newObj);
        
        if(this.isEmpty()){
            this.inicial = niu;
            return true;
        }
        
        else{
           
            Nodo aux = this.inicial;
           
            while (aux.sig != null){
                aux = aux.sig;
            }
            
            aux.sig = niu;
            return true;            
        }
        */
        
        return this.add(newObj, this.size());
        
    }
    

    /**
     * Setter que limpia la lista dejandola vacia de objetos.
     */
    public void clear(){
        
        Nodo aux = this.inicial;
        Nodo aux2 = null;
        int tamanyo = this.size();
        
        
        for (int i = 0; i < tamanyo; i++) {
            aux2 = aux;
            aux = aux.sig;
            aux2.sig = null;
        }
        System.out.println("está vacia y deslinkeada");
        this.inicial = null;
    }
    
    
    /**
     * Setter que borra el objeto de la lista en la posición indicada por parámetro.
     * @param index (int) - posición de la lista cuyo objeto queremos eliminar.
     * @return obj (M) - objeto (M) que ha sido eliminado para poder operar con el si quisieramos.
     */
    public M remove(int index){
        
        if (index < 0 || index >= this.size()){
            throw new IndexOutOfBoundsException("No se puede remover, compra nesquik");
        }
        
        else if(index == 0){
            Nodo aux = this.inicial;
            this.inicial = this.inicial.sig;
            return aux.info;
        }
        
        else{
            int cont = 0;
            Nodo aux = this.inicial;
            Nodo aux2;
            
            while(cont < index && aux.sig != null){
                
                cont ++;
                
                if (cont == index){
                    aux2 = aux.sig;
                    aux.sig = aux.sig.sig;
                    return aux2.info;
                }
                aux = aux.sig;
            }
            
            throw new IndexOutOfBoundsException("No se puede remover compra nesquik; segunda salida");
        }
        
    }
    
//</editor-fold>


    //<editor-fold defaultstate="collapsed" desc="Otros Metodos">

    
    /**
     * Metodo que imprime el tipo de objetos que contiene la lista y el 
     * número de elementos que contiene.
     */
    public void print(){
        
        StringBuffer rdo = new StringBuffer("");
        
        if(this.isEmpty()){
            System.out.println("Está vacía!");
            return;
        }
        
        else{
            rdo.append("es una lista que contiene: \"");
            rdo.append(this.inicial.info.getClass().getName());
            rdo.append("\" con ");
            rdo.append(this.size());
            rdo.append(" Elementos..");
            
        }
        
        System.out.println(rdo.toString());
        
        /* Para comprobacion de orden en la lista */
        
        int lengt = this.size();
        Nodo aux = null;
        
        for (int i = 0; i < lengt; i++) {
            
            if ( aux == null ){
                aux = this.inicial;
            }
            
            System.out.println(aux.info.toString());
            aux=aux.sig;
           
        }
        
        
    }
    
       
    
       
        /**
         * Getter que devuelve el primer elemento de la lista;
         * @return obj (M) - objeto que ocupa la primera posicion en la lista.
         */
        public M getFirst(){
            
            return this.get(0);
            
        }
    
    
             
        /**
         * Metodo que devuelve el último elemento de la lista.
         * @return obj (M) - objeto que ocupa la última posicion en la lista.
         */
        public M getLast(){
            
            return this.get((this.size() - 1));
        }

    
       /**
        * Metodo que añade un objeto al primer elemento de la lista desplazando todos los demás.
        * @param newObj (M) - objeto que queremos agregar.
        * @return true si se ha conseguido agregar con exito.
        */
        public boolean addFirst(M newObj){
            
            /*
            Nodo niu = new Nodo(newObj);
            
            if(this.isEmpty()){
                
                this.inicial = niu;
                return true;
            }
            
            else{
                
                niu.sig = this.inicial;
                this.inicial = niu;
                return true;
                
            }*/
            
            return this.add(newObj, 0);
            
        }
    
        
       /**
        * Metodo que elimina el primer elemento de la lista.
        * @return obj (M) - objeto que ocupaba el primer elemento de la lista
        *   por si queremos operar con el.
        */
   
        public M removeFirst(){
            
            return this.remove(0);
        }
    
        
        /**
         * Metodo que elimina el último elemento de la lista.
         * @return obj (M) - objeto que ocupaba el ultimo elemento de la lista
         * por si queremos operar con el.
         */

        public M removeLast(){
            
            return this.remove((this.size() - 1));
            
        }
        
        
        
        /**
         * Metodo que agrega un objeto a la lista, en la posición de la lista deseada.
         * En caso de requerir una posición mayor que el tamaño de la lista, lo agrega al ultimo lugar.
         * @param newObj (M) - objeto que queremos agregar a la lista.
         * @param pos (int) - posición en el que queremos agregarlo a la lista.
         * @return True si se ha añadido correctamente
         */
        public boolean add( M newObj, int pos){
            
            Nodo niu = new Nodo(newObj);
            
            
            if(this.isEmpty()){
                
                this.inicial = niu;
                return true;
            }
            
            else if (pos == 0){
                
                niu.sig = this.inicial;
                this.inicial = niu;
                return true;
                
            }
            
            else{
                int cont = 1;  
                Nodo aux = this.inicial;
                
                while(pos <= this.size() && cont < pos /* && aux.sig != null */){
                    
                    aux = aux.sig;
                    cont ++;
                }
                
                niu.sig =  aux.sig;
                aux.sig = niu;
                return true;
            }
            
            
            
        }

//</editor-fold>


//</editor-fold>
    
    

    //Fin de Class
}
