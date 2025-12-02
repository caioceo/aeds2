import No;

package Algoritimos.TADs.Arvores.AVL;
public class ArvoreAVL {
    public No raiz;

    ArvoreAVL(int x) {
        raiz = new No(x);
    }

    // Inserir;
    // CalculaFB;
    // Rotações;
    // Altura

    public int CaclularFB(No i) {
        if (i == null)
            return 0;
        return altura(i.dir) - altura(i.esq);
    }

    public int altura(No i) {
        if (i == null) {
            return 0;
        }
        return i.altura;
    }

    public void Inserir(int x) {
        raiz = Inserir(x, raiz);
    }

    private No Inserir(int x, No i) {
        if (i == null) {
            return new No(x);
        }

        if (i.valor > x) {
            i.esq = Inserir(x, i.esq);
        } else if (i.valor < x) {
            i.dir = Inserir(x, i.dir);
        } else
            return i;

        i.altura = 1 + Math.max(altura(i.esq), altura(i.dir));

        return Rotaciona(i);

    }

    private No Rotaciona(No i) {
        int fb = CaclularFB(i);

        if (fb == 2) {
            int fbDir = CaclularFB(i.dir);
            if (fbDir == 1 || fbDir == 0) {
                return RotacaoEsq(i);
            } else if (fbDir == -1) {
                return RotacaoDirEsq(i);
            }
        } else if (fb == -2) {
            int fbEsq = CaclularFB(i.esq);
            if (fbEsq == 1) {
                return RotacaoEsqDir(i);
            } else if (fbEsq == 0 || fbEsq == -1) {
                return RotacaoDir(i);
            }
        }
        return i;
    }

    private No RotacaoDir(No i) {
        No noEsq = i.esq;
        No noEsqDir = noEsq.dir;

        noEsq.dir = i;
        i.esq = noEsqDir;

        noEsq.altura = 1+Math.max(altura(noEsq.dir), altura(noEsq.esq));
        i.altura = 1+Math.max(altura(i.esq), altura(i.dir));

        return noEsq;

    }

    
    private No RotacaoEsq(No i) {
        No noDir = i.dir;
        No noDirEsq = noDir.esq;
        
        noDir.esq = i;
        i.dir = noDirEsq;

        noDir.altura = 1+Math.max(altura(noDir.dir), altura(noDir.esq));
        i.altura = 1+Math.max(altura(i.esq), altura(i.dir));
        
        return noDir;
        
    }
    
    private No RotacaoDirEsq(No i) {
        i.dir = RotacaoDir(i.dir);
        return RotacaoEsq(i);
    }
    private No RotacaoEsqDir(No i) {
        i.esq = RotacaoEsq(i.esq);
        return RotacaoDir(i);
    }

    // Caminhar

    public void Caminhar() {
        System.out.print("[ ");
        Caminhar(raiz);
        System.out.print(" ]");
    }

    private void Caminhar(No i){
        if(i!=null){
            System.out.print(i.valor + " 1  | ");
            
        }
    }


    public void CaminharPre() {
        System.out.print("[ ");
        CaminharPre(raiz);
        System.out.print(" ]");
    }

    private void CaminharPre(No i) {
        if (i != null) {
            System.out.print(i.valor + " ");
            CaminharPre(i.esq);
            CaminharPre(i.dir);
        }
    }

    public void CaminharPos() {
        System.out.print("[ ");
        CaminharPos(raiz);
        System.out.print(" ]");
    }

    private void CaminharPos(No i) {
        if (i != null) {
            CaminharPos(i.esq);
            CaminharPos(i.dir);
            System.out.print(i.valor + " ");
        }
    }

    public void CaminharCentral() {
        System.out.print("[ ");
        CaminharCentral(raiz);
        System.out.print(" ]");
    }

    private void CaminharCentral(No i) {
        if (i != null) {
            CaminharCentral(i.esq);
            System.out.print(i.valor + " ");
            CaminharCentral(i.dir);
        }
    }

}

class No {
    public No esq, dir;
    public int valor;
    public int altura;

    No(int x) {
        valor = x;
        altura = 1;
    }
}