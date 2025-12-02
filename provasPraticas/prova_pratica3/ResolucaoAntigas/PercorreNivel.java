package provasPraticas.prova_pratica3.ResolucaoAntigas;
public class PercorreNivel {
    class Arvore {
        No raiz;

        public void inserir(int x) {
            inserir(raiz, x);
        }

        private No inserir(No i, int x) {
            if (i == null) {
                return new No(x);
            }

            if (x > i.valor) {
                i.dir = inserir(i.dir, x);
            } else if (x < i.valor) {
                i.esq = inserir(i.esq, x);
            }
            return i;

        }

        public void caminharNivel() {
            if (raiz == null)
                return;

            Fila f = new Fila();
            f.inserir(raiz);

            while (f.head != null) {
                No atual = f.pop(); 
                System.out.print(atual.valor + " ");

                if (atual.esq != null)
                    f.inserir(atual.esq);
                if (atual.dir != null)
                    f.inserir(atual.dir);
            }
        }

    }

    class No {
        No esq, dir;
        int valor;

        No(int x) {
            valor = x;
        }
    }

    class Fila {
        Celula head;

        Fila(No x) {
            head = new Celula(x);
        }

        Fila() {
            head = null;
        }

        public void inserir(No x) {
            if (head == null) {
                head = new Celula(x);
                return;
            }
            Celula aux = head;
            while (aux.prox != null) {
                aux = aux.prox;
            }
            aux.prox = new Celula(x);
        }

        public No pop() {
            if (head == null) {
                return null;
            }
            No valor = head.valor;
            head = head.prox;
            return valor;
        }

    }

    class Celula {
        Celula prox;
        No valor;

        Celula(No x) {
            prox = null;
            valor = x;
        }
    }

}
