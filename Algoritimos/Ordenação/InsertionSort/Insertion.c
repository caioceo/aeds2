#include <stdio.h>
void insertion(int a[], int len){
    for (int i = 1; i < len; i++)
    {
        int j = i-1;
        int aux = a[i];
        while(j >= 0 && a[j] > aux){
            a[j+1] = a[j];
            j--;
        }
        a[j+1] = aux;
    }
}

int main(){
    int a[] = { 8, 2, 7, 3, 1, 9, 4, 11, 10, 0 };
    int len = sizeof(a)/sizeof(a[0]);
    
    insertion (a, len);

    for (int i = 0; i < len; i++)
    {
        printf("%d ", a[i]);
    }
    
}