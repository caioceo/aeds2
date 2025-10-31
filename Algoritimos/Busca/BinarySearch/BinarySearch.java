package Busca.BinarySearch;
import java.util.Scanner;

public class BinarySearch {

    public static void Search(int arr[], int x, int esq, int dir) {
        int meio = (esq + dir) / 2;
        if (esq > dir) {
            System.out.println("Número não encontrado.");
            return;
        }
        if (arr[meio] == x) {
            System.out.println("O numero " + x + " esta no indice " + meio);
            return;
        }
        if (x > arr[meio]) {
            esq = meio + 1;
            Search(arr, x, esq, dir);
        } else if (x < arr[meio]) {
            dir = meio - 1;
            Search(arr, x, esq, dir);
        }

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int arr[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        int dir = arr.length - 1;
        int esq = 0;

        int x = sc.nextInt();

        Search(arr, x, esq, dir);

        sc.close();
    }
}

// Dividir e conquistar
// Condição de parada é esq < dir
// esq = meio + 1 || dir = meio - 1 (para chamada recursiva)