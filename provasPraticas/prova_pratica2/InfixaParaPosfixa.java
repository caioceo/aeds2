package provasPraticas.prova_pratica2;
import java.util.Scanner;
import java.util.Stack;

public class InfixaParaPosfixa {
    
    public static int precedencia(char operador) {
        switch(operador) {
            case '^':
                return 3;
            case '*':
            case '/':
                return 2;
            case '+':
            case '-':
            case '=':
                return 1;
            default:
                return 0;
        }
    }
    
    public static boolean ehOperador(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == '=';
    }
    
    public static String converterParaPosfixa(String infixa) {
        StringBuilder posfixa = new StringBuilder();
        Stack<Character> pilha = new Stack<>();
        
        for (int i = 0; i < infixa.length(); i++) {
            char c = infixa.charAt(i);
            
            if (Character.isLetterOrDigit(c)) {
                posfixa.append(c);
            }

            else if (c == '(') {
                pilha.push(c);
            }

            else if (c == ')') {
                while (!pilha.isEmpty() && pilha.peek() != '(') {
                    posfixa.append(pilha.pop());
                }
                if (!pilha.isEmpty()) {
                    pilha.pop();
                }
            }
            
            else if (ehOperador(c)) {
                while (!pilha.isEmpty() && pilha.peek() != '(' && precedencia(pilha.peek()) >= precedencia(c)) {
                    posfixa.append(pilha.pop());
                }
                pilha.push(c);
            }
        }
        
        while (!pilha.isEmpty()) {
            posfixa.append(pilha.pop());
        }
        
        return posfixa.toString();
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        
        for (int i = 0; i < n; i++) {
            String expressao = scanner.nextLine();
            System.out.println(converterParaPosfixa(expressao));
        }
        
        scanner.close();
    }
}