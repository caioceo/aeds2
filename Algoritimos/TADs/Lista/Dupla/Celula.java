package TADs.Lista.Dupla;
public class Celula {
    private Celula prox;
    private Celula prev;
    private int x;

    Celula(int x){
        this.x = x;
        this.prox = null;
        this.prev = null;
    }
    public Celula getPrev() {
        return prev;
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
    public void setPrev(Celula prev) {
        this.prev = prev;
    }
    public void setX(int x) {
        this.x = x;
    }

}
