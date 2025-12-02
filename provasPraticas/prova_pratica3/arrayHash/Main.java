import java.util.Scanner;

public class Main {

    public static int Calcula(String s1[]){
        int len = s1.length;
        int sum =0;
        for(int i=0; i<len; i++){
            int lenS1 = s1[i].length();
            for(int j=0; j<lenS1; j++){
                sum+= s1[i].charAt(j) - 65 + i + j;
            }
        }
        return sum;
    }
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        for (int m = 0; m < n; m++) {
            // n = entrada inical (numero de casos)
            int k = sc.nextInt();
            sc.nextLine();
            if(k<0){
                return;
            }

            String s1[] = new String[k];
            
            // k = numero de linhas para cada caso;
            for (int i = 0; i < k; i++) {

                s1[i] = sc.nextLine();
                int lenS = s1[i].length();
            }

            int sum = Calcula(s1);
            System.out.println(sum);

        }

        sc.close();
    }
}