public class Quicksort {
    public static void main(String[] args) {
        int a[] = { 8, 2, 7, 3, 1, 9, 4, 11, 10, 0};
        
        quicksort(a , 0, a.length-1) ;
        
        for(int i=0; i<a.length; i++){
            System.out.print(a[i] + " ");
        }
    }
    
        public static int partition(int a[], int esq, int dir){
            int j = esq;
            int i = j-1;
            for (; j < dir; j++){
                if(a[j] < a[dir]){
                    i++;
                    swap(a, j, i);
                }
            }
            i++;
            swap(a, dir, i);
            return i;
        }
    
    public static void swap(int a[], int j, int i){
        int tmp = a[j];
        a[j] = a[i];
        a[i] = tmp;
    }
    public static void quicksort(int a[], int esq, int dir){
        if(esq < dir){
            int pivo = partition(a, esq, dir);
            quicksort(a, pivo+1, dir);
            quicksort(a, esq, pivo-1);
        }
    }

}
