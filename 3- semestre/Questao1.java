import java.util.Scanner;

public class Questao1{

    public static void main (String[]args) {
    // 2
        Scanner sc = new Scanner(System.in);
        System.out.println("Insira um número");
        double number1 = sc.nextDouble();
        System.out.println("Insira um segundo número");
        double number2 = sc.nextDouble();    
        double soma = number1 + number2;
        double multi = number1 * number2;
        double subt = number1 - number2;
        double div = number1 / number2;
        System.out.println("Resuldado da soma:"+soma+
        "Resultado da Multiplicação"+multi+
        "Resultado da Subtração"+subt+
        "Resultado da Divisão"+div);
        sc.close();
    }
}
