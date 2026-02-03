import java.util.Scanner;

public class Questao4{

    public static void main (String[]args) {
    // 2
        Scanner sc = new Scanner(System.in);       

        System.out.println("Insira o valor da força");
        double nu1 = sc.nextDouble();
        System.out.println("Insira o valor da distância");
        double nu2 = sc.nextDouble();

        double trabalho = nu1 * nu2 ;

        System.out.println("Resultado dá formula T=F*D:"+trabalho);
        sc.close();


    }
}
