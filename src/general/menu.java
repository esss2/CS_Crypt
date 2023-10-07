package general;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

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
                    opcionCrypt();
                    break;
                case 2:
                    System.out.println("Has elegido desencriptar un archivo");
                    opcionDecrypt();
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

    public static void opcionCrypt(){
        //el usuario introduce el path por teclado
        Scanner miScanner = new Scanner(System.in);
        System.out.println("==========================================================");
        System.out.println("Para realizar la encriptación de un archivo, introduce la dirección completa del archivo sin comillas dobles:");
        String rutaArchivo = miScanner.nextLine();

        try{
            //creamos la ruta de la carpeta EncryptedFiles
            File encrFiles = new File("../EncryptedFiles");
            String rutaCarpeta = encrFiles.getCanonicalPath();

            System.out.println("La ruta del archivo a copiar es: " + rutaArchivo + " y la ruta donde se va a copiar es: " + rutaCarpeta);

            //llamo a la función para comenzar con el encriptado:
            System.out.println("==========================================================");
            System.out.println("Encriptando...");

            //creo la clave y llamo a la función de encriptado
            /*
            encryptImage(rutaArchivo, rutaCarpeta, llave);
            */

        }catch(IOException e){
            System.out.println("Parece que algo ha fallado copiando el archivo:");
            System.out.println(e);
            System.out.println("Por favor, inténtelo de nuevo.");
        }
    }

    public static void opcionDecrypt(){
        ArrayList<String> archivos = new ArrayList<String>();
        String separador = "#";
        try{
            String path = "../../keys.txt";
            BufferedReader lector = new BufferedReader(new FileReader(path));
            String linea;
            while((linea = lector.readLine()) != null){
                String archivo =  linea.split(separador)[0];
                archivos.add(archivo);
            }
        }catch(IOException e){
            System.out.println("Ha habido un problema al recuperar los archivos.");
            e.printStackTrace();
        }

        System.out.println("==========================================================");
        System.out.println("Estos son los archivos que tienes encriptados en el momento:");
        for(int i = 0; i<archivos.size(); i++){
            System.out.println(i + ". " + archivos.get(i));
        }
    }
}
