package listas;

import java.util.Objects;

public class Lista<T> {
    private Nodo<T> inicio;
    private Nodo<T> fin;
    private Integer nel=0;


    public Lista() {
        inicio=null;
        fin=null;
    }



    public void addFin(T x){
        Nodo n=new Nodo(x);

        if(inicio==null){
            inicio=n;
            fin=n;
        }else{
            fin.setSig(n);
            fin=n;
        }
        nel++;

    }
    public void add(T x) { //añade un elemento al principio de la lista
        Nodo n = new Nodo(x);

        if (inicio == null) {
            inicio = n;
            fin=n;
        } else {
            n.setSig(inicio);
            inicio = n;
        }
        nel++;
    }

    public static Lista conc(Lista l1,Lista l2){
        Lista r1=Lista.inverse(l1);
        Lista r2=Lista.inverse(l2);

        Lista l3=new Lista();

        Nodo n=r1.inicio;
        Nodo n2=r2.inicio;

        while(n!=null){
            l3.add(n.getInfo());
            n=n.getSig();
        }
        while(n2!=null){
            l3.add(n2.getInfo());
            n2=n2.getSig();
        }
        return l3;
    }

    public void mostrar() { //muestra todos los elementos de la lista
        Nodo n = inicio;
        System.out.print("-->(");
        while (n != null) {

            if (n.getSig() != null) {
                System.out.print(n.getInfo() + ", ");
            } else {
                System.out.print(n.getInfo() + ")");
            }
            n = n.getSig();
        }
        System.out.println("");
    }

    public static Lista inverse(Lista l) {//Invierte la lista
        Nodo n = l.getInicio();

        Lista l2 = new Lista();
        while (n != null) {
            l2.add(n.getInfo());
            n = n.getSig();
        }
        return l2;
    }



    public boolean search(T x) {//Busca un elemento en la lista
        Nodo n = inicio;
        while (n != null) {
            if (Objects.equals(n.getInfo(), x)) {
                return true;
            }
            n = n.getSig();
        }
        return false;
    }

    public boolean borrar(T x) {//Borra un elemento de la lista
        boolean borrado=false;
        if (search(x)) {
            Nodo<T> n = inicio;
            Nodo<T> aux=null;


            while (n != null) {
                if (Objects.equals(n.getInfo(), x)) {
                    if(n==inicio){
                        inicio=inicio.getSig();
                    }else
                    if(n==fin){
                        fin=aux;
                        aux.setSig(null);
                    }

                    else{

                        aux.setSig(n.getSig());
                    }

                    borrado= true;
                }
                n = n.getSig();
                if (aux == null) {
                    aux = inicio;
                } else {
                    aux = aux.getSig();
                }

            }
        }
        if(borrado){
            nel--;
        }
        return borrado ;

    }

    public Nodo <T> getInicio() {
        return inicio;
    }

    public void setInicio(Nodo<T> inicio) {
        this.inicio = inicio;
    }
    public void mostrart(){

        if(nel==0){
            System.out.println("");
            for(int f=0;f<=3;f++){
                System.out.println("  |");
            }
            System.out.println("");
        }else{
            System.out.println("");
            Iterador itr2=new Iterador(this);
            for(int e=nel-1;e>=0;e--){
                for (int i=e;i>=0;i--){
                    System.out.print(" ");
                }
                System.out.println(itr2.valor());
                itr2.next();
            }
        }
        System.out.println("");
    }

    public Nodo<T> getFin() {
        return fin;
    }

    public void setFin(Nodo<T> fin) {
        this.fin = fin;
    }

    public Integer getNel() {
        return nel;
    }

    public void setNel(Integer nel) {
        this.nel = nel;
    }

}
