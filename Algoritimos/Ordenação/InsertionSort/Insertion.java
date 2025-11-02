package Algoritimos.Ordenação.InsertionSort;
public class Insertion {
    public static void main(String[] args) {
        int a[] = { 8, 2, 7, 3, 1, 9, 4, 11, 10, 0 };

        insertion(a);

        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
    }

    public static void swap(int a[], int j, int i) {
        int tmp = a[j];
        a[j] = a[i];
        a[i] = tmp;
    }

    public static void insertion(int a[]) {
        for (int i = 1; i < a.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (a[j + 1] < a[j]) {
                    swap(a, j, j + 1);
                }
            }
        }
    }

    public static void insertionSort(int a[]) {
        for (int i = 1; i < a.length; i++) {
            int tmp = a[i];
            int j = i - 1;
            while (j >= 0 && a[j] > tmp) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = tmp;
        }
    }
}
