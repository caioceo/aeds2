package Ordenação.SelectionSort;
public class Selection {
    public static void main(String[] args){
        int array[] = {8,2,7,3,1,9,4,11,10, 0};
        int len = array.length;

        SelectionSort(array, len);

        for(int i=0; i<len; i++){
            System.out.print(array[i]+" ");
        }
    }

    public static void SelectionSort(int arr[], int len){
        for(int i =0; i <len-1; i++){
            int menor = i;
            for(int j=i+1; j<=len-1; j++){
                if(arr[j]<arr[menor]){
                    menor = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[menor];
            arr[menor] = temp;
        }
    }
}
