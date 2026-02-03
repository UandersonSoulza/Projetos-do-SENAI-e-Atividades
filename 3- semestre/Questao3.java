import java.util.Scanner;

public class Questao3{

    public static void main (String[]args) {
    // 2
        Scanner sc = new Scanner(System.in);       

        System.out.println("Insira o primeiro número");
        double pri = sc.nextDouble();

        System.out.println("Insira o segundo número");
        double seg = sc.nextDouble();

        System.out.println("Insira o terceiro número");
        double ter = sc.nextDouble();

        double media = (pri + seg + ter)/3;

        System.out.println("A media aritmética é:"+media);
        sc.close();


    }
}
