package edu.escuelaing.arep;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Reto1 {

    public static void main(String[] args) throws IOException {
        //System.out.println("hola");
        ServerSocket serverSocket = null;
        while (true) {
            try {
                serverSocket = new ServerSocket(40000);
            } catch (IOException e) {
                System.err.println("No se pudó escuchar el puerto: " + getPort());
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
                    String resource = inputLine.split(" ")[1];
                    System.out.println("InputLine:" + resource);
                }
                if (!in.ready()) {
                    break;
                }
            }
            throw new IOException("No es posible obtener la URL");

        }
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 40000;
    }

    public static void getResources(String path, OutputStream outputStream) throws IOException {
        if (path.equals("/paisaje.html")) {
            getPaisaje(outputStream);
        }  else {
            notFound(outputStream);
        }
    }

    public static void getPaisaje(OutputStream outputStream){
        PrintWriter resp = new PrintWriter(outputStream,true);
        resp.println("HTTP/1.1 200 OK");
        inicializar(resp);
        resp.println("<title>Paisaje Colombiano</title>\r\n");
        resp.println("</head>\r\n");
        resp.println("<body>\r\n");
        resp.println("\"Pagina dedicada a los paisajes colombianos\"");
        resp.println("</body>\r\n");
        resp.println("</html>\r\n");
        resp.flush();
        resp.close();
    }

    private static void notFound(OutputStream outputStream) {
        PrintWriter resp = new PrintWriter(outputStream, true);
        resp.println("HTTP/1.1 404 Not Found");
        inicializar(resp);
        resp.println("<title>Not FOUND</title>\r\n");
        resp.flush();
        resp.close();
    }

    private static void inicializar(PrintWriter resp){
        resp.print("\"Content-Type: text/html\\r\\n\"");
        resp.println("<!DOCTYPE html>\r\n");
        resp.println("<html lang=\"en\">\r\n");
        resp.println("<head>\r\n");
        resp.println("<meta charset=\"UTF-8\">\r\n");

}






}
