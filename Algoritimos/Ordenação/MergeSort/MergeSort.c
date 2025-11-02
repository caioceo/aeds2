#include <stdio.h>

void intercalar(int a[], int esq, int meio, int dir){
    int bLen = meio - esq + 1;
    int cLen = dir - meio;
    int b[bLen];
    int c[cLen];

    for (int i = 0; i < bLen; i++){
        b[i] = a[i+esq];
    }

    for (int i = 0; i < cLen; i++){
        c[i] = a[i+meio+1];
    }
    int i = 0;
    int j = 0;
    int k = esq;
    while(i<bLen && j<cLen){
        if(b[i] < c[j]){
            a[k] = b[i];
            k++;
            i++;
        }
        else{
            a[k] = c[j];
            k++;
            j++;
        }
    }

    if(i == bLen){
      while(k<=dir){
        a[k] = c[j];
        j++;
        k++;
      }
        
    }
    else if (j==cLen){
        while(k<=dir){
            a[k] = b[i];
            i++;
            k++;
      }
    }
    
}

void mergeSort(int a[], int esq, int dir){
    if(esq<dir){
        int meio = (esq+dir)/2;
        mergeSort(a, esq, meio);
        mergeSort(a, meio+1, dir);
        intercalar(a, esq, meio, dir);
    }
}

int main(){
    int arr[] = { 3, 5, 2, 1, 6, 7, 8, 5, 4, 3, 2, 1, 1, 20, 30, 2, 1, 5, 6, 7, 5, 4, 32, 2, 4, 6, 34, 3, 2, 1, 2 };
    mergeSort(arr, 0, sizeof(arr)/sizeof(arr[0])-1);
    for (int i = 0; i < sizeof(arr)/sizeof(arr[0])-1; i++)
    {
        printf("%d ", arr[i] );
    }
    

}
