#include <stdio.h>

// O(n²)

void SelectionSort(int arr[], int len){
    for(int i=0; i<len-1; i++){
        int menor = i;
        for(int j = i+1; j<len; j++){
             if (arr[j] < arr[menor]){
                menor = j;
             }
        }
        int temp = arr[i];
        arr[i] = arr[menor];
        arr[menor] = temp;
    }
}

int main(){
    int array[] = {8,2,7,3,1,9,4,11,10, 0};
    int len = sizeof(array)/sizeof(array[1]);

    SelectionSort(array, len);

    for (int i =0; i <len; i++){
        printf("%d ", array[i]);
    }

    return 0;
}