#include <stdio.h>

void swap(int a[], int i, int j){
    int tmp = a[i];
    a[i] = a[j];
    a[j] = tmp;
}

int partition(int a[], int esq, int dir){
    
        int j = esq;
        int i = j-1;

        for(; j<dir; j++){
            if(a[j]<a[dir]){
                i++;
                swap(a, i, j);
            }
        }
        i++;
        swap(a, i, j);

        return i;
    
}

void quicksort(int a[], int esq, int dir){
    if(esq<dir){
    int pivot = partition(a, esq, dir);
    quicksort(a, pivot+1, dir);
    quicksort(a, esq, pivot-1);
    }
}

int main(){
    int a[] = { 8, 2, 7, 3, 1, 9, 4, 11, 10, 0};
    int len = sizeof(a)/sizeof(a[0]);
    quicksort(a, 0, len-1);
    for (int i = 0; i < len; i++)
    {
        printf ("%d ", a[i]);
    }
    
}