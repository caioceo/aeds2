#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct
{
    char nome[1000]
} Pokemon;

int find(Pokemon a[], int n, int i)
{
        for (int j = 0; j < n; j++)
        {
            if (j == i)
            {
                continue;
            }
            if (strcmp(a[j].name, a[i].name) == 0)
            {
                return 1;
            }
        }
    return 0;
}

int main()
{
    int n;
    scanf("%d", &n);

    if (n > 0 && n <= 1000)
    {
        Pokemon a[n];
        int pok = 151;
        for (int i = 0; i < n; i++)
        {
            fgets(a[i].nome, 1000, stdin);
            a.[i].nome[strcspn(a.[i].nome, '\n')] = '\0';
            if (find(a, n, i) == 0)
            {
                pok--;
            }
        }
        printf("Falta(m) %d pokemon(s).");
    }
}