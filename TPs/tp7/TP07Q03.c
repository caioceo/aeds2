#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

typedef struct
{
    int id;
    char name[200];
    char releaseDate[20];
    int estimatedOwners;
    float price;
    char supportedLanguages[500];
    int metacriticScore;
    float userScore;
    int achievements;
    char publishers[200];
    char developers[200];
    char categories[200];
    char genres[200];
    char tags[500];
} Game;

typedef struct No
{
    Game game;
    struct No *esq;
    struct No *dir;
    int altura;
} No;

int comparacoes = 0;

char *trimManual(char *str)
{
    char *inicio = str;
    char *fim;

    while (*inicio == ' ' || *inicio == '\t' || *inicio == '\n' || *inicio == '\r')
    {
        inicio++;
    }

    if (*inicio == 0)
    {
        return inicio;
    }

    fim = inicio + strlen(inicio) - 1;
    while (fim > inicio && (*fim == ' ' || *fim == '\t' || *fim == '\n' || *fim == '\r'))
    {
        fim--;
    }

    *(fim + 1) = 0;
    return inicio;
}

void parseLinha(char *linha, Game *jogo)
{
    char *campos[14];
    int numCampos = 0;
    int dentroAspas = 0;
    char *inicio = linha;

    for (int i = 0; linha[i] != '\0'; i++)
    {
        if (linha[i] == '"')
        {
            dentroAspas = !dentroAspas;
        }
        else if (linha[i] == ',' && !dentroAspas)
        {
            linha[i] = '\0';
            campos[numCampos++] = inicio;
            inicio = &linha[i + 1];
        }
    }
    campos[numCampos++] = inicio;

    jogo->id = atoi(trimManual(campos[0]));
    strcpy(jogo->name, trimManual(campos[1]));
    strcpy(jogo->releaseDate, trimManual(campos[2]));
    jogo->estimatedOwners = atoi(trimManual(campos[3]));
    jogo->price = atof(trimManual(campos[4]));
    strcpy(jogo->supportedLanguages, trimManual(campos[5]));
    jogo->metacriticScore = atoi(trimManual(campos[6]));
    jogo->userScore = atof(trimManual(campos[7]));
    jogo->achievements = atoi(trimManual(campos[8]));
    strcpy(jogo->publishers, trimManual(campos[9]));
    strcpy(jogo->developers, trimManual(campos[10]));
    strcpy(jogo->categories, trimManual(campos[11]));
    strcpy(jogo->genres, trimManual(campos[12]));
    strcpy(jogo->tags, trimManual(campos[13]));
}

Game *buscarPorId(Game *jogos, int numJogos, int id)
{
    for (int i = 0; i < numJogos; i++)
    {
        if (jogos[i].id == id)
        {
            return &jogos[i];
        }
    }
    return NULL;
}

int altura(No *no)
{
    if (no == NULL)
    {
        return 0;
    }
    return no->altura;
}

int max(int a, int b)
{
    if (a > b)
    {
        return a;
    }
    return b;
}

int fatorBalanceamento(No *no)
{
    if (no == NULL)
    {
        return 0;
    }
    return altura(no->esq) - altura(no->dir);
}

No *criarNo(Game game)
{
    No *novo = (No *)malloc(sizeof(No));
    novo->game = game;
    novo->esq = NULL;
    novo->dir = NULL;
    novo->altura = 1;
    return novo;
}

No *rotacaoDireita(No *y)
{
    No *x = y->esq;
    No *T2 = x->dir;

    x->dir = y;
    y->esq = T2;

    y->altura = max(altura(y->esq), altura(y->dir)) + 1;
    x->altura = max(altura(x->esq), altura(x->dir)) + 1;

    return x;
}

No *rotacaoEsquerda(No *x)
{
    No *y = x->dir;
    No *T2 = y->esq;

    y->esq = x;
    x->dir = T2;

    x->altura = max(altura(x->esq), altura(x->dir)) + 1;
    y->altura = max(altura(y->esq), altura(y->dir)) + 1;

    return y;
}

No *inserir(No *no, Game game)
{
    if (no == NULL)
    {
        return criarNo(game);
    }

    int cmp = strcmp(game.name, no->game.name);

    if (cmp < 0)
    {
        no->esq = inserir(no->esq, game);
    }
    else if (cmp > 0)
    {
        no->dir = inserir(no->dir, game);
    }
    else
    {
        return no;
    }

    no->altura = 1 + max(altura(no->esq), altura(no->dir));

    int balanco = fatorBalanceamento(no);

    if (balanco > 1 && strcmp(game.name, no->esq->game.name) < 0)
    {
        return rotacaoDireita(no);
    }

    if (balanco < -1 && strcmp(game.name, no->dir->game.name) > 0)
    {
        return rotacaoEsquerda(no);
    }

    if (balanco > 1 && strcmp(game.name, no->esq->game.name) > 0)
    {
        no->esq = rotacaoEsquerda(no->esq);
        return rotacaoDireita(no);
    }

    if (balanco < -1 && strcmp(game.name, no->dir->game.name) < 0)
    {
        no->dir = rotacaoDireita(no->dir);
        return rotacaoEsquerda(no);
    }

    return no;
}

int pesquisarRecursivo(No *no, char *nome)
{
    if (no == NULL)
    {
        return 0;
    }

    comparacoes++;

    int cmp = strcmp(nome, no->game.name);

    if (cmp == 0)
    {
        return 1;
    }
    else if (cmp < 0)
    {
        printf(" esq");
        return pesquisarRecursivo(no->esq, nome);
    }
    else
    {
        printf(" dir");
        return pesquisarRecursivo(no->dir, nome);
    }
}

int pesquisar(No *raiz, char *nome)
{
    printf("raiz");
    return pesquisarRecursivo(raiz, nome);
}

int main()
{
    char *matricula = "caioceo";
    char *caminhoCSV = "/tmp/games.csv";

    FILE *arquivo = fopen(caminhoCSV, "r");
    if (arquivo == NULL)
    {
        return 1;
    }

    Game *todosJogos = (Game *)malloc(50000 * sizeof(Game));
    int numJogos = 0;

    char linha[2000];
    fgets(linha, sizeof(linha), arquivo);

    while (fgets(linha, sizeof(linha), arquivo) != NULL)
    {
        parseLinha(linha, &todosJogos[numJogos]);
        numJogos++;
    }
    fclose(arquivo);

    No *raiz = NULL;
    char entrada[200];

    while (fgets(entrada, sizeof(entrada), stdin) != NULL)
    {
        char *limpa = trimManual(entrada);
        if (strcmp(limpa, "FIM") == 0)
        {
            break;
        }

        int id = atoi(limpa);
        Game *jogo = buscarPorId(todosJogos, numJogos, id);
        if (jogo != NULL)
        {
            raiz = inserir(raiz, *jogo);
        }
    }

    clock_t inicio = clock();

    while (fgets(entrada, sizeof(entrada), stdin) != NULL)
    {
        char *limpa = trimManual(entrada);
        if (strcmp(limpa, "FIM") == 0)
        {
            break;
        }

        printf("%s: ", limpa);
        int resultado = pesquisar(raiz, limpa);

        if (resultado)
        {
            printf(" SIM\n");
        }
        else
        {
            printf(" NAO\n");
        }
    }

    clock_t fim = clock();
    double tempoExecucao = ((double)(fim - inicio)) / CLOCKS_PER_SEC * 1000;

    FILE *log = fopen("caioceo_arvoreAVL.txt", "w");
    fputs(matricula, log);
    fputs("\t", log);
    fputs("tempo aqui", log);
    fclose(log);

    return 0;
}