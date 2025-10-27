package Quicksort;
public class QuickSort {

    public static void swap(int arr[], int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static int partition(int arr[], int esq, int dir){
        int j=esq;
        int i = j-1;
        for(;j<dir; j++){
            if(arr[j]<arr[dir]){
                i++;
                swap(arr, i, j);
            }
        }
        i++;
        swap(arr, i, j);

        return i;
    }

    public static void quickSort(int arr[], int esq, int dir){
        if(esq<dir){
            int pivo = partition(arr, esq, dir);
            quickSort(arr, esq, pivo-1);
            quickSort(arr, pivo+1, dir);
        }
    }

    public static void main(String[] args){
        int[] arr = {42, 7, 89, 13, 56, 3, 95, 61, 27, 74, 18, 90, 34, 50, 66, 25, 80, 9, 47, 100};
        quickSort(arr, 0, arr.length-1);
        for(int i=0; i<arr.length; i++){
            System.out.print(arr[i] + " ");
        }
    }
    
}
