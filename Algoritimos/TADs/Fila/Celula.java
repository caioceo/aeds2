package TADs.Fila;
public class Celula {
    private Celula prox;
    private int x;

    Celula(int x){
        this.x = x;
        this.prox = null;
    }

    public Celula getProx() {
        return prox;
    }
    public int getX() {
        return x;
    }
    public void setProx(Celula prox) {
        this.prox = prox;
    }
    public void setX(int x) {
        this.x = x;
    }
}
