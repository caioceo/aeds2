package TADs.Lista.Dupla;
public class ListaDupla {
    Celula head;
    Celula tail;
    int len;

    ListaDupla(int x){
        head = new Celula(x);
        tail = head;
        len = 1;
    }

    public void inserirFim(int x) {
        if (tail == null) {
            head = new Celula(x);
            tail = head;
            len++;
            return;
        }
        Celula newTail = new Celula(x);
        newTail.setPrev(tail);
        tail.setProx(newTail);
        tail = newTail;
        len++;
    }

    public void inserirInicio(int x) {
        if (head == null) {
            head = new Celula(x);
            tail = head;
            len++;
            return;
        }
        Celula newHead = new Celula(x);
        head.setPrev(newHead);
        newHead.setProx(head);
        head = newHead;
        len++;
    }

    public int removerFim() {
        if (tail == null) {
            return 0;
        }
        int valorRemovido = tail.getX();
        if (tail == head){
            head = null;
            tail = null;
            len--;
            return valorRemovido;
        }
        Celula newTail = tail.getPrev();
        tail = newTail;
        tail.setProx(null);
        len--;
        return valorRemovido;
    }

    public int removerInicio() {
        if(head == null){
            return 0;
        }
        int valorRemovido = head.getX();    
        if (head == tail){
            head = null;
            tail = null;
            return valorRemovido;
        }
        Celula newHead = head.getProx();
        head = newHead;
        head.setPrev(null);
        return valorRemovido;
    }

    public Celula removerValor(int x) {
        if (head == null){
            return null;
        }
        Celula aux = head;

        while (aux != null) {
            if (aux.getX() == x) {
                Celula removida = aux;
                if (aux == tail) {
                    removerFim();
                    return removida;
                }
                if (aux == head) {
                    removerInicio();
                    return removida;
                }
                aux.getPrev().setProx(aux.getProx());
                aux.getProx().setPrev(aux.getPrev());
                len--;
                return removida;
            }
            aux = aux.getProx();
        }
        return null;
    }
}
