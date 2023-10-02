package general;

import java.util.Scanner;

public class menu {

    public static void main(String[] args) {
        muestraMenu();
    }

    public static void muestraMenu(){
        Scanner miScanner = new Scanner(System.in);
        int opcion =-1;

        System.out.println("**********************");
        System.out.println("*****Encriptathon*****");
        System.out.println("**********************");
        System.out.println("Bienvenido a Enriptathon, la aplicación para encriptar o desencriptar archivos.");
        System.out.println("==========================================================");


        do{
            do{
                System.out.println("¿Qué deseas hacer?");
                System.out.println("1. Encriptar.");
                System.out.println("2. Desencriptar.");
                System.out.println("3. Salir.");
                System.out.println("==========================================================");
                if(miScanner.hasNextInt()){
                    opcion = miScanner.nextInt();
                }else{
                    System.out.println("Escoge una opción correcta, por favor.");
                    System.out.println("==========================================================");
                    miScanner.next();
                }
            }while(opcion < 1 || opcion > 3);


            switch (opcion){
                case 1:
                    System.out.println("Has elegido encriptar un archivo");
                    break;
                case 2:
                    System.out.println("Has elegido desencriptar un archivo");
                    break;
                case 3:
                    System.out.println("Has elegido salir. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Aquí no se puede llegar");
                    break;
            }
        }while (opcion != 3);
    }
}
