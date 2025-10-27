package Pilha;
public class PilhaLinear{
    private int arr[];
    private int topo;

    PilhaLinear(int x){
        this.topo=-1;
        this.arr = new int[x];
    }

    public boolean vazia(){
        if (topo == -1){
            return true;
        }
        return false;
    }

    public boolean cheia(){
        if (topo == arr.length-1){
            return true;
        }
        return false;
    }

    public void InserirFim(int x){
        if(!cheia()){
            topo++;
            arr[topo] = x;
        }
        else{
            System.out.println("Pilha esta cheia, nao foi possivel inserir");
        }
    }

    public void RemoverFim(){
        if(!vazia()){
            arr[topo] = 0;
            topo--;
        }
        else{
            System.out.println("Pilha esta vazia, nao foi possivel remover");
        }
    }
}