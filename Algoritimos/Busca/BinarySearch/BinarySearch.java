import java.util.Scanner;

public class BinarySearch {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        int x = sc.nextInt();
        int indice = binarySearch(a, 0, a.length - 1, x);
        if (indice == -1) {
            System.out.printf("numero %d nao foi encontrado", x);
        } else {
            System.out.printf("numero %d encontrado em indice: %d ", x, indice);
        }
        sc.close();
    }

    public static int binarySearch(int arr[], int esq, int dir, int x) {
        if (esq > dir) {
            return -1;
        }
        int meio = (esq + dir) / 2;
        if (arr[meio] == x) {
            return meio;
        }
        if (x > arr[meio]) {
            return binarySearch(arr, meio + 1, dir, x);
        }
        return binarySearch(arr, esq, meio - 1, x);
    }
}
