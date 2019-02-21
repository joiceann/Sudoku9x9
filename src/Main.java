import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main( String[] args ){

        // Print the string "Hello, " on screen
        System.out.println("Iniciando... ");


        // Check if a command line argument exists
        if(args.length!=0 ){
            String input = args[0];
            //
            // System.out.println(input.length());
            if (input.length() == 81){
                System.out.println("Prueba");
                AlgoritmoA algoritmoA = new AlgoritmoA();
                algoritmoA.crear_EstadoInicial(input);
                algoritmoA.A_Star();
            }
            else {
                System.out.println("Sudoku invalido");
                System.exit(0);
            }
        }

        else{
            System.out.println("Prueba");
            AlgoritmoA algoritmoA = new AlgoritmoA();
            algoritmoA.crear_EstadoInicial("53..7....6..195....98....6.8...6...34..8.3..17...2...6.6....28....419..5....8..79");
            algoritmoA.A_Star();
        }

        /*System.out.println("Sudoku invalido");
        System.exit(0);
*/
    }
}
