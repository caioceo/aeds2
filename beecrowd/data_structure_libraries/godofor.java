
import java.util.Scanner;

public class godofor {

    public static boolean menorQue(Person a, Person b) {
        // criterios poder, kills, mortes, nome

        if (a.atributos[0] != b.atributos[0]) {
            return a.atributos[0] > b.atributos[0];
        } else if (a.atributos[1] != b.atributos[1]) {
            return a.atributos[1] > b.atributos[1];
        } else if (a.atributos[2] != b.atributos[2]) {
            return a.atributos[2] < b.atributos[2];
        } else {
            int comp = a.nome.compareTo(b.nome);
            if (comp < 0) {
                return true;
            }
            return false;
        }
    }

    public static void Sort(Person a[]) {
        int menor =0;
        for (int i = 0; i < a.length - 1; i++) {
            menor = i;
            for (int j = i + 1; j < a.length; j++) {
                if (menorQue(a[j], a[menor])) {
                    menor = j;
                }
            }
        }

        System.out.println(a[menor].nome);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        sc.nextLine();
        if (n >= 1 && n <= 100) {
            Person a[] = new Person[n];
            for (int i = 0; i < n; i++) {
                a[i] = new Person();
                String all = sc.nextLine();
                String[] allSplit = all.split(" ");
                a[i].nome = allSplit[0];
                for (int j = 0; j < 3; j++) {
                    a[i].atributos[j] = Integer.parseInt(allSplit[j+1]);
                }
            }

            Sort(a);
        }
    }
}

class Person {
    public String nome;
    public int[] atributos;

    Person(String nome, int atributos[]) {
        this.nome = nome;
        this.atributos = new int[3];
        this.atributos[0] = atributos[0];
        this.atributos[1] = atributos[1];
        this.atributos[2] = atributos[2];
    }

    Person() {
        this.nome = "";
        atributos = new int[3];
        atributos[0] = 0;
        atributos[1] = 0;
        atributos[2] = 0;

    }
}
