#include <stdio.h>
#include <string.h>
#include <stdlib.h>

void sort(int arr[], int n, int k){
    for(int i=0; i<k; i++){
        int maior = i;
        for(int j = i+1; j<n; j++) {
            if(arr[j]>arr[maior]){
                maior = j;
            }
        }   
        int tmp = arr[i];
        arr[i] = arr[maior];
        arr[maior] = tmp;
    }
}

int main(){
    int n;
        while(scanf("%d", &n) != EOF){
            int k;
            scanf("%d",&k);

            if(n<= 1000000 && k>0 && k<=n && n>0){
                int arr[n];
                for(int i=0; i<n; i++){
                    int tmp;
                    scanf("%d", &tmp);
                    if(n<=100000){
                        arr[i] = tmp;   
                    }
                    else return 1;
                }

                sort(arr, n, k);
                int sum=0;
                for(int i =0; i<k; i++){
                    sum+= arr[i];
                }
                printf("%d\n", sum);
            }
        }
    return 0;
}
