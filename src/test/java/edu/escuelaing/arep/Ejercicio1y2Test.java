package edu.escuelaing.arep;
import  java.io.IOException;
import java.net.MalformedURLException;
import static org.junit.Assert.*;
import org.junit.Test;

public class Ejercicio1y2Test {
    @Test
    public void test(){

    }

    @Test
    public void informacionURL() throws MalformedURLException {
        Ejercicio1y2 x = new Ejercicio1y2();
        System.out.println("Protocol: " + x.getProtocol());
        System.out.println("Authority: " + x.getAuthority());
        System.out.println("Host: " + x.getHost());
        System.out.println("Port: " + x.getPort());
        System.out.println("Path: " + x.getPath());
        System.out.println("Query: " + x.getQuery());
        System.out.println("File: " + x.getFile());
        System.out.println("Ref: " + x.getRef());
    }

    @Test
    public void deberiaDarInformacionURL() throws MalformedURLException,IOException{
       Ejercicio1y2 x = new Ejercicio1y2();
        x.leerURLUser();
    }


}


