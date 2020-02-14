package edu.escuelaing.arep;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Reto1 {

    public static void main(String[] args) throws IOException {
        //System.out.println("hola");
        ServerSocket serverSocket = null;
        while (true) {
            try {
                serverSocket = new ServerSocket(40000);
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
            String inputLine, file;
            file = "/";
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.contains("GET")) {
                    file  = inputLine.substring(inputLine.indexOf("/")+1,inputLine.indexOf(" ", inputLine.indexOf(" ")+1));

                    break;
                }
                 if (!in.ready()) {
                    break;
                }
               
            }

        String [] tipoArchivo = getResource(file);
        
        if(tipoArchivo[1]=="html" || tipoArchivo[1]=="js"){
            getArchivo(tipoArchivo[0],out);
        }
        else if(tipoArchivo[1]=="img"){
            getImagen(tipoArchivo[0],clienteSocket.getOutputStream());
        }
       /* else{
            getNotFound(clienteSocket.getOutputStream());
        }*/
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
        return 40000;
    }
    

    public static String[] getResource(String archivo){
    String ruta = "src/main/resources/";
    String[] ans = new String[2];
        if (archivo.endsWith(".html")){
            ruta+="html/"+archivo;
            ans[1]="html";
        }
        else if(archivo.endsWith(".img") || archivo.endsWith(".jpeg") || archivo.endsWith(".png") || archivo.endsWith(".gif")){
            ruta+="img/"+archivo;
            ans[1]="img";
        }
        else if(archivo.endsWith(".js")){
            ruta+="js/"+archivo;
            ans[1]="js";
        }
        ans[0]=ruta;
        return ans;
    }
    
    public static void getImagen(String tipo, OutputStream clienteOutput) throws IOException {
        try {
            BufferedImage image = ImageIO.read(new File(tipo));
            ByteArrayOutputStream ArrBytes = new ByteArrayOutputStream();
            DataOutputStream writeImg = new DataOutputStream(clienteOutput);
            ImageIO.write(image, "PNG", ArrBytes);
            writeImg.writeBytes("HTTP/1.1 200 OK \r\n" + "Content-Type: image/png \r\n");
            writeImg.write(ArrBytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getArchivo(String ruta, PrintWriter out) throws IOException {
        File archivo = new File(ruta);
        String text = "";
        String temp = "";
        if (archivo.exists()) {
            try {
                BufferedReader t = new BufferedReader(new FileReader(archivo));
                while ((temp = t.readLine()) != null) {
                    System.out.println(temp);
                    text += temp;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }
            out.println(text);

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







