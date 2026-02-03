
import java.util.Scanner;

public class Atv1 {

    public static void main(String[] args) {
       
        Scanner sc = new Scanner(System.in);
        System.out.println("Informe o primeiro número");
        double pri = sc.nextDouble();
        System.out.println("Informe o segundo número");
        double seg = sc.nextDouble();
        System.out.println("A media do " +pri+ " mais " +seg+ " é = " +(pri + seg)/2);
        sc.close();


    }
    
}
