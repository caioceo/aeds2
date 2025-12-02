import java.util.Scanner;

public class TDA_Rational {

    public static void Simplifica(int num, int den) {
        if (num % 2 == 0 && den % 2 == 0) {
            Simplifica(num / 2, den / 2);
        } else if (num % 3 == 0 && den % 3 == 0) {
            Simplifica(num / 3, den / 3);
        } else if (num % 5 == 0 && den % 5 == 0) {
            Simplifica(num / 5, den / 5);
        } else if (num % 7 == 0 && den % 7 == 0) {
            Simplifica(num / 7, den / 7);
        } else {
            if(num == den){
                System.out.println("1/1 ");
            }
            System.out.println(num + "/" + den);
        }
    }

    public static void Operaciona(String line[]) {

        int denComum = 0, numFinal = 0;

        switch (line[3].trim()) {
            case "/":
                // (0, "n1") (1, "/"") (2, "d1") (3, "'sinal'") (4, "n2") (5, "/"") (6, "d2")

                numFinal = Integer.parseInt(line[0]) * Integer.parseInt(line[6]);
                denComum = Integer.parseInt(line[4]) * Integer.parseInt(line[2]);

                break;

            case "+":

                numFinal = (Integer.parseInt(line[0]) * Integer.parseInt(line[6]))
                        + (Integer.parseInt(line[4]) * Integer.parseInt(line[2]));
                denComum = Integer.parseInt(line[2]) * Integer.parseInt(line[6]);

                break;

            case "-":
                numFinal = (Integer.parseInt(line[0]) * Integer.parseInt(line[6]))
                        - (Integer.parseInt(line[4]) * Integer.parseInt(line[2]));
                denComum = Integer.parseInt(line[2]) * Integer.parseInt(line[6]);

                break;

            case "*":
                numFinal = Integer.parseInt(line[0]) * Integer.parseInt(line[4]);
                denComum = Integer.parseInt(line[2]) * Integer.parseInt(line[6]);

                break;

            default:
                System.out.println("Operador inválido");
                return;
        }

        System.out.print(numFinal + "/" + denComum + " = ");
        Simplifica(numFinal, denComum);

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        sc.nextLine();

        if (n > 10000 || n < 1) {
            sc.close();
            return;
        }

        for (int i = 0; i < n; i++) {
            String line = sc.nextLine();
            String arrString[] = line.split(" ");

            // (0, "n1") (1, "/"") (2, "d1") (3, "'sinal'") (4, "n2") (5, "/"") (6, "d2")
            Operaciona(arrString);

        }
        sc.close();
    }

}