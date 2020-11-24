package listas;

public class Iterador<T> {

    private Nodo<T> ini;
    private Nodo<T> actual;





    public Iterador(Lista e){
        ini=e.getInicio();
        actual=e.getInicio();
    }



    public void inicializar(){
        actual=ini;
    }

    public void next(){
        actual=actual.getSig();
    }
    public boolean hayElto(){
        return actual != null;
    }
    public T valor(){
        return actual.getInfo();
    }

}

