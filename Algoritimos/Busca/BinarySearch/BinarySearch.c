#include <stdio.h>

int buscaBinaria(int arr[], int esq, int dir, int x){
    if(esq>dir){
        return -1;
    }
    int meio = esq + (dir-esq)/2;
    if (arr[meio] == x){
        return meio;
    }
    if (x > arr[meio]){
        return buscaBinaria(arr, meio+1, dir, x);
    }
    return buscaBinaria(arr, esq, meio-1, x);
}

int main(){
    int a[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
    int len = sizeof(a)/sizeof(a[0]);

    int x;
    scanf ("%d", &x);

    int busca = buscaBinaria(a, 0, len-1, x);

    if (busca==-1){
        printf("%d nao foi encontrado", x);
    }
    else{
        printf ("%d foi encontrado em %d", x, busca);
    }
    return 0;
}
