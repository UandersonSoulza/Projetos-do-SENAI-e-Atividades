
import java.util.Scanner;

public class EntradaDados {
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
        System.out.println("Informe o nome");
        String nome = sc.next();
        System.out.println("informe a idade");
        int idade = sc.nextInt();

            System.out.println("Olá "+nome+ " Sua idade é "+idade);
        sc.close();
    }
}
