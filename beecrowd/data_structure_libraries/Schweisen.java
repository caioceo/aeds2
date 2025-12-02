import java.util.Scanner;

public class Schweisen {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            int price = sc.nextInt();

            if (x == 0 && y == 0 && price == 0) {
                return;
            }

            if (x <= 1000 && y <= 1000 && price <= 10) {
                int q = sc.nextInt();
                sc.nextLine();

                if (q > 10000 || q < 1) {
                    return;
                }

                Achados a = new Achados();

                for (int i = 0; i < q; i++) {
                    String s2 = sc.nextLine();

                    if (s2.charAt(0) == 'A') {

                        String[] splitedS2 = s2.split(" ");

                        int qnt = Integer.parseInt(splitedS2[1]);
                        int xy[] = new int[2];

                        for (int j = 0; j < 2; j++) {
                            xy[j] = Integer.parseInt(splitedS2[2 + j]);
                        }

                        a.Inserir(qnt, xy);

                    } else if (s2.charAt(0) == 'P') {
                        String[] splitedS2 = s2.split(" ");
                        int xy[] = new int[2];
                        int zw[] = new int[2];

                        for (int j = 0; j < 2; j++) {
                            xy[j] = Integer.parseInt(splitedS2[1 + j]);
                            zw[j] = Integer.parseInt(splitedS2[3 + j]);
                        }

                        a.Pesquisa(xy, zw, price);
                    } else {
                        return;
                    }

                }
            }
        }
    }
}

class Achados {
    Celula head;

    Achados(int qnt, int[] xy) {
        head = new Celula(qnt, xy);
    }

    Achados() {
        head = null;
    }

    public void Inserir(int qnt, int[] xy) {
        if (head == null) {
            head = new Celula(qnt, xy);
        } else {
            Celula aux = head;
            while (aux.next != null) {
                aux = aux.next;
            }
            aux.next = new Celula(qnt, xy);
        }
    }

    public void Pesquisa(int xy[], int zw[], int price) {
        Celula aux = head;
        int count = 0;
        while (aux != null) {
            /*
             * y 1 2 w
             * 1 1 2 3
             * x 1 2 z
             */
            int x = aux.cord[0];
            int y = aux.cord[1];

            if (x >= xy[0] && x <= zw[0] && y <= xy[1] && y >= zw[1]) {
                count += aux.qnt;
            }

            aux = aux.next;
        }
        System.out.println(price * count);
    }
}

class Celula {
    int qnt;
    int[] cord;
    Celula next;

    Celula(int qnt, int[] xy) {
        this.qnt = qnt;
        this.cord = new int[2];
        cord[0] = xy[0];
        cord[1] = xy[1];
    }
}
