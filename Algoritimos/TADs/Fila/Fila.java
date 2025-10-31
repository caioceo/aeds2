package TADs.Fila;
import java.util.NoSuchElementException;

public class Fila {
    private Celula head;
    private Celula tail;
    private int len;

    Fila(int x) {
        this.head = new Celula(x);
        this.tail = head;
        this.len = 1;
    }

    // com tail O(1)
    public void InserirFim(int x) {
        tail.setProx(new Celula(x));
        tail = tail.getProx();
        len++;
    }

    // sem tail O(n)
    public void InserirFim2(int x) {
        Celula tail = AchaUltima();

        tail.setProx(new Celula(x));
        tail = tail.getProx();
        len++;
        return;
    }

    public Celula AchaUltima() {
        Celula procurada = head;
        while (procurada.getProx() != null) {
            procurada = procurada.getProx();
        }

        return procurada;
    }

    public int RemoverInicio() {
        if (head != null) {
            int valorRemovido = head.getX();
            if (head == tail) {
                head = null;
                tail = head;
                return valorRemovido;
            }

            head = head.getProx();
            len--;

            return valorRemovido;
        }

        throw new NoSuchElementException("Fila vazia");
    }
}
