package TADs.Lista;
public class FilaLinear {
    // first in first out;

    private int arr[];
    private int inicio;
    private int primeiro;

    FilaLinear(int x){
        this.arr = new int[x];
        this.inicio = 0;
        this.primeiro = -1;
    }

    public void InserirInicio(int x){
        if(Cheia()){
             System.out.println("Fila esta chiea, nao e possivel inserir");
        }
        if(inicio == arr.length-1){
            inicio = 0;
            primeiro++;
            return;
        }
        int temp = arr[inicio];
        arr[inicio] = x;
        inicio++;
        InserirInicio(temp);
    }

    public void RemoverFim(){
        if(!Vazia()){
            arr[primeiro] = 0;
            primeiro--;
        }
        else
            System.out.println("Fila esta vazia, nao e possivel remover");
    }

    public boolean Cheia(){
        if(primeiro==arr.length-1){
            return true;
        }
        return false;
    }

    public boolean Vazia(){
        if(primeiro == -1){
            return true;
        }
        return false;
    }


}
