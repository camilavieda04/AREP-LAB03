package edu.escuelaing.arep;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Desktop;
import java.nio.file.Path;

public class Reto1 {

    public static void main(String[] args) throws IOException {
        //System.out.println("hola");
        ServerSocket serverSocket = null;
        while (true) {
            try {
                serverSocket = new ServerSocket(4567);
            } catch (IOException e) {
                System.err.println("No se pudo escuchar el puerto: " + getPort());
                System.exit(1);
            }
            Socket clienteSocket = null;
            try {
                clienteSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Acepto fallo");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(clienteSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clienteSocket.getInputStream()
                    )
            );
            String inputLine, outpuLine;
            //file = "/";
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.contains("GET")) {
                    String[] url1 = inputLine.split("/");
                    String[] url2 = url1[1].split(" ");
                    //file  = inputLine.substring(inputLine.indexOf("/")+1,inputLine.indexOf(" ", inputLine.indexOf(" ")+1));
                    //break;}
                    System.out.println(url2[0]);
                    if (url2[0].contains("JPG") || url2[0].contains("jpg")) {
                        getImagen("/src/main/resources/img/" + url2[0], clienteSocket.getOutputStream(), out);
                    }
                    if (url2[0].contains("html")) {
                        getArchivoHTML("/src/main/resource/html/" + url2[0], clienteSocket.getOutputStream());
                    }
                    if (url2[0].contains("js")) {
                        getArchivoJS("/src/main/resource/js/" + url2[0], clienteSocket.getOutputStream());
                    } else {
                        getNotFound(clienteSocket.getOutputStream());

                    }
                }
                if (!in.ready()) {
                        break;
                }

            }
            out.close();
            in.close();
            clienteSocket.close();
            serverSocket.close();    
            }

        }
    

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

    public static void getImagen(String tipo, OutputStream clienteOutput, PrintWriter out) throws IOException {
        try {
            BufferedImage image = ImageIO.read(new File(System.getProperty("user.dir") + tipo));
            ByteArrayOutputStream ArrBytes = new ByteArrayOutputStream();
            DataOutputStream writeImg = new DataOutputStream(clienteOutput);
            String img = "HTTP /1.1 404 NOT FOUND \r\n"
                    + "Content-Type: text/html; charset=\"UTF-8\" \r\n"
                    + "\r\n";
            ImageIO.write(image, "JPG", ArrBytes);
            writeImg.writeBytes("HTTP/1.1 200 OK \r\n" + "Content-Type: image/jpg \r\n");
            writeImg.write(ArrBytes.toByteArray());
            System.out.println(System.getProperty("user.dir") + tipo);
        } catch (IOException e) {
            System.out.println("r" + e.getMessage());
        }
    }

    private static void getArchivoHTML(String ruta, OutputStream outputStream) throws IOException {
        /*
         String temp = "";*/
        try {
            String text = "";
            String temp;
            BufferedReader t = new BufferedReader(new FileReader(System.getProperty("user.dir") + ruta));
            while ((temp = t.readLine()) != null) {
                //System.out.println(temp);
                text = text + temp;
            }
            outputStream.write(("HTTP/1.1 404 NOT FOUND  \r\n"
                    + "Content-Type: text/html; charset=\"UTF-8\" \r\n"
                    + "\r\n"
                    + text).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void getArchivoJS(String ruta, OutputStream outputStream) throws IOException {
        /*
         String temp = "";*/
        try {
            String text = "";
            String temp;
            BufferedReader t = new BufferedReader(new FileReader(System.getProperty("user.dir") + ruta));
            while ((temp = t.readLine()) != null) {
                //System.out.println(temp);
                text = text + temp;
            }
            outputStream.write(("HTTP/1.1 404 NOT FOUND  \r\n"
                    + "Content-Type: text/html; charset=\"UTF-8\" \r\n"
                    + "\r\n"
                    + text).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void getNotFound(OutputStream outputStream) {
        try {
            ByteArrayOutputStream ArrBytes = new ByteArrayOutputStream();
            DataOutputStream writenF = new DataOutputStream(outputStream);
            writenF.writeBytes("HTTP/1.1 404 NOT FOUND \r\n");
        } catch (IOException ex) {
            Logger.getLogger(Reto1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
