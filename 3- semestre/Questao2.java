import java.util.Scanner;

public class Questao2{

    public static void main (String[]args) {
    // 2
        Scanner sc = new Scanner(System.in);   

        System.out.println("Insira o primeiro número");
        double pri = sc.nextDouble();
        System.out.println("Insira o segundo número");
        double seg = sc.nextDouble();

        double media = (pri + seg)/2;

        System.out.println("A sua média é a soma do" +pri+ " mais " +seg+ " que resulta em: = " +media);
        sc.close();


    }
}
