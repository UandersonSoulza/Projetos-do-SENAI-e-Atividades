import java.util.Scanner;

public class Questao5{

    public static void main (String[]args) {
    // 2
        Scanner sc = new Scanner(System.in);       

        System.out.println("Insira o seu peso");
        double peso = sc.nextDouble();

        System.out.println("Insira sua altura");
        double altura = sc.nextDouble();


        double imc = peso / (altura*altura);

        System.out.println("O resultado do imc é:"+imc);
        sc.close();


    }
}
