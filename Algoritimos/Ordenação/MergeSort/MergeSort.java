public class MergeSort {
    int[] arr = { 3, 5, 2, 1, 6, 7, 8, 5, 4, 3, 2, 1, 1, 20, 30, 2, 1, 5, 6, 7, 5, 4, 32, 2, 4, 6, 34, 3, 2, 1, 2 };

    public static void main(String[] args) {
        MergeSort merge = new MergeSort();
        merge.mergesort();
    }

    public void mergesort() {
        mergesort(0, arr.length - 1);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

    private void mergesort(int esq, int dir) {
        if (esq < dir) {
            int meio = (esq + dir) / 2;
            mergesort(esq, meio);
            mergesort(meio + 1, dir);
            aux(esq, meio, dir);
        }
    }

    public void aux(int esq, int meio, int dir) {

        int[] v1 = new int[meio - esq + 1];
        int[] v2 = new int[dir - meio];

        for (int i = 0; i < v1.length; i++) {
            v1[i] = arr[esq + i];
        }

        for (int i = 0; i < v2.length; i++) {
            v2[i] = arr[meio + 1 + i];
        }

        int k = 0;
        int l = 0;
        int i = esq;

        while (k < v1.length && l < v2.length) {
            if (v1[k] < v2[l]) {
                arr[i] = v1[k];
                i++;
                k++;
            } else {
                arr[i] = v2[l];
                l++;
                i++;
            }
        }

        if (k == v1.length) {
            while (i <= dir) {
                arr[i] = v2[l];
                i++;
                l++;
            }
        } else if (l == v2.length) {
            while (i <= dir) {
                arr[i] = v1[k];
                i++;
                k++;
            }
        }
    }
}
