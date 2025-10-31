import java.util.NoSuchElementException;

public class Pilha {
    private Celula top;
    private int len;

    Pilha(int x) {
        top = new Celula(x);
        len = 1;
    }

    public int desempilhar() {
        if (top != null) {
            int valor = top.getX();
            top = top.getProx();
            len--;
            return valor;
        }
        throw new NoSuchElementException("Pilha vazia");
    }

    public void empilhar(int x) {
        Celula aux = new Celula(x);
        aux.setProx(top);
        top = aux;
        len++;
    }

    public Boolean buscaValor(int x) {
        Celula aux = top;
        while (aux != null) {
            if (aux.getX() == x) {
                return true;
            }
            aux = aux.getProx();
        }
        return false;
    }

    // se indice 0 = top
    public Celula buscaIndice(int x) {
        if(x<0 || x>=len){
            return null;
        }
        int i = 0;
        Celula aux = top;
        while (aux != null) {
            if (i == x) {
                return aux;
            }
            i++;
            aux = aux.getProx();

        }
        return null;
    }
}
