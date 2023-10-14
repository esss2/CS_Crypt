package general;

import crypt.crypt;
import decrypt.decrypt;


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
            crypt encriptar = new crypt();
            String rutaLlave = "../keys.txt";
            encriptar.encriptaArchivo(rutaArchivo, rutaCarpeta, rutaLlave);

        }catch(Exception e){
            System.out.println("Parece que algo ha fallado copiando o encriptando el archivo:");
            System.out.println(e);
            System.out.println("Por favor, inténtelo de nuevo.");
        }
    }

    /*public static void opcionDecrypt(){
        ArrayList<String> archivos = new ArrayList<String>();
        String separador = "#";
        Scanner miScanner = new Scanner(System.in);
        decrypt desencriptar = new decrypt();
        int opcion = 0;
        //recupero los archivos para mostrarlos
        try{

            String path = "../keys.txt";
            BufferedReader lector = new BufferedReader(new FileReader(path));
            System.out.println(lector);
            String linea;
            while((linea = lector.readLine()) != null){;
                archivos.add(linea);
            }
        }catch(IOException e){
            System.out.println("Ha habido un problema al recuperar los archivos.");
            e.printStackTrace();
        }

        try{
            System.out.println("==========================================================");
            do{
                System.out.println("Estos son los archivos que tienes encriptados en el momento:");
                for(int i = 0; i<archivos.size(); i++){
                    System.out.println(i+1 + ". " + archivos.get(i).split(separador)[0]);
                }
                System.out.println("==========================================================");
                System.out.println("Introduce el número del archivo que quieras encriptar:");
                if(miScanner.hasNextInt()){
                    opcion = miScanner.nextInt();
                    if(opcion < 1 || opcion > archivos.size()){
                        opcion = 0;
                        System.out.println("Escoge una opción correcta, por favor.");
                    }else{
                        System.out.println("Has elegido encriptar el archivo " + opcion + "->" + archivos.get(opcion-1).split(separador)[0] + ".");
                        desencriptar.desencriptar(archivos.get(opcion-1).split(separador)[1], archivos.get(opcion-1).split(separador)[0]);
                    }
                }else{
                    System.out.println("Escoge una opción correcta, por favor.");
                    miScanner.next();
                }
            }while(opcion < 1 || opcion > archivos.size());
        }catch(Exception e){
            System.out.println("Ha habido un problema al desencriptar el archivo.");
            e.printStackTrace();
        }
    }*/
    public static void opcionDecrypt() {
        ArrayList<String> archivos = new ArrayList<String>();
        String separador = "#";
        Scanner miScanner = new Scanner(System.in);
        decrypt desencriptar = new decrypt();
        int opcion = 0;

        // Recupera los archivos encriptados para mostrarlos
        try {
            String path = "../keys.txt";
            BufferedReader lector = new BufferedReader(new FileReader(path));
            String linea;
            while ((linea = lector.readLine()) != null) {
                archivos.add(linea);
                System.out.println(archivos);
            }
            lector.close(); // Cierra el lector después de usarlo
        } catch (IOException e) {
            System.out.println("Ha habido un problema al recuperar los archivos.");
            e.printStackTrace();
            return; // Sale del método si hay un problema
        }
        if(archivos.isEmpty()){
            System.out.println("no hay archivos encriptados");
        }else{
            try {
                System.out.println("==========================================================");
                System.out.println("Estos son los archivos que tienes encriptados en el momento:");
                for (int i = 0; i < archivos.size(); i++) {
                    String[] archivoInfo = archivos.get(i).split(separador);
                    System.out.println(i + 1 + ". Nombre del archivo: " + archivoInfo[i]);
                }
                System.out.println("==========================================================");

                do {
                    System.out.println("Introduce el número del archivo que quieras desencriptar (o 0 para salir):");
                    if (miScanner.hasNextInt()) {
                        opcion = miScanner.nextInt();
                        if (opcion == 0) {
                            System.out.println("Volviendo al menú principal...");
                            return; // Sale del método si el usuario selecciona 0
                        } else if (opcion < 1 || opcion > archivos.size()) {
                            opcion = 0;
                            System.out.println("Escoge una opción correcta, por favor.");
                        } else {
                            String[] archivoInfo = archivos.get(opcion - 1).split(separador);
                            System.out.println("Has elegido desencriptar el archivo " + opcion + "->" + archivoInfo[0] + ".");
                            desencriptar.desencriptar(archivoInfo[1], archivoInfo[0]);
                        }
                    } else {
                        System.out.println("Escoge una opción correcta, por favor.");
                        miScanner.next();
                    }
                } while (opcion < 1 || opcion > archivos.size());
            } catch (Exception e) {
                System.out.println("Ha habido un problema al desencriptar el archivo.");
                e.printStackTrace();
            }
        }
        }

}
