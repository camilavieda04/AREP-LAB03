package edu.escuelaing.arep;
import java.io.*;
import java.net.MalformedURLException;
import java.net.*;
import java.net.URL;

public class Ejercicio1y2 {

        private URL google2 = new URL("http://campusvirtual.escuelaing.edu.co/moodle/mod/assign/view.php?id=34731");


        public Ejercicio1y2() throws MalformedURLException{

        }

        public void leerURLUser() throws IOException {
            URL u = new URL ("http://google.com");
            PrintWriter writer = new PrintWriter("resultado.html");
            BufferedReader reader = new BufferedReader(new InputStreamReader(u.openStream()));
            String inputLine = null;
            while ((inputLine = reader.readLine()) != null) {
                writer.write(inputLine);
                System.out.println(inputLine);
            }
            writer.flush();

        }

        public String getProtocol(){
            System.out.println(google2);
            return google2.getProtocol();
        }

        public String getAuthority(){return google2.getAuthority();}

        public String getHost(){
            return google2.getHost();
        }

        public int getPort(){
            return google2.getPort();
        }

        public String getPath(){
            return google2.getPath();
        }

        public String getQuery(){
            return google2.getQuery();
        }

        public String getFile(){
            return google2.getFile();
        }

        public String getRef(){
            return google2.getRef();
        }


    }
