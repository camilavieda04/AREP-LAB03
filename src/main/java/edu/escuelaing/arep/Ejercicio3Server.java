package edu.escuelaing.arep;

import java.net.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Ejercicio3Server {

    public static void main(String[]args)throws IOException{
        //System.out.println("hola");
        ServerSocket serverSocket = null;
        int puerto = getPort();
        try{
            serverSocket = new ServerSocket(40000);
        }
        catch (IOException e){
            System.err.println("No se pudó escuchar el puerto: "+ getPort());
            System.exit(1);
        }

        Socket clienteSocket = null;
        try{
            clienteSocket=serverSocket.accept();
        }
        catch (IOException e){
            System.err.println("Acepto fallo");
            System.exit(1);
        }
        PrintWriter out = new PrintWriter(clienteSocket.getOutputStream(),true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        clienteSocket.getInputStream()
                )
        );
        String inputLine,outputLine;

        Double respTotal = 0.0;
        while ((inputLine=in.readLine())!=null) {
            if (inputLine.contains("?")) {
                String[] value = inputLine.split("=");
                //System.out.println(value[1]);
                String[] valor = value[1].split(" ");
                //System.out.println(valor[1]);
                respTotal = Math.pow(Double.parseDouble(valor[0]), 2);
                //System.out.println(respTotal);
            }
            if(!in.ready()){
                break;
            }
        }


        outputLine = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<meta charset=\"UTF-8\">\n"
                + "<title>Title of the document</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "<h1>El cuadrado del numero ingresado es: </h1>\n"
                + respTotal
                + "</body>\n"
                + "</html>\n" + inputLine;


        out.println(outputLine);
        out.close();
        in.close();
        clienteSocket.close();
        serverSocket.close();
    }

    static int getPort(){
        if(System.getenv("PORT")!=null){
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 40000;
    }
}