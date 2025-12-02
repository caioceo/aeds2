import java.util.Scanner;

public class Drought {
    // numero de casos -> pessoas e consumo total -> pessoa e consumo medio e ordem
    // ascendente

    public static void Sort(int pess[], int cons[], int len) {

        for (int i = 0; i < len - 1; i++) {
            int menor = i;
            for (int j = 1 + i; j < len; j++) {
                if (cons[j] < cons[menor]) {
                    menor = j;
                }
            }
            // swap menor com i;
            int aux = cons[i];
            cons[i] = cons[menor];
            cons[menor] = aux;

            aux = pess[i];
            pess[i] = pess[menor];
            pess[menor] = aux;
        }

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int Cidade = 1;
        while (true) {
            int casos = sc.nextInt();
            if (casos == 0) {
                sc.close();
                return;
            }
            if (casos < 1 || casos > 1000000) {
                continue;
            }

            int pessoas[] = new int[casos];
            int consumoT[] = new int[casos];
            int consumoM[] = new int[casos];
            ;
            float totalL = 0;
            int totalP = 0;

            for (int i = 0; i < casos; i++) {
                pessoas[i] = sc.nextInt();
                consumoT[i] = sc.nextInt();
                if (pessoas[i] < 1 || pessoas[i] > 10 || consumoT[i] < 1 || consumoT[i] > 200) {
                    i -= 1;
                    continue;
                }
                consumoM[i] = consumoT[i] / pessoas[i];
                totalL += consumoT[i];
                totalP += pessoas[i];
            }

            int len = pessoas.length;

            Sort(pessoas, consumoM, len);

            System.out.println("Cidade# " + Cidade + ":");
            for (int i = 0; i < len; i++) {
                System.out.printf("%d-%d ", pessoas[i], consumoM[i]);
            }
            System.out.println();
            System.out.printf("Consumo medio: %.2f m3.\n", totalL / totalP);

            Cidade++;
        }

    }
}
