//
//  Main.java
//  HTTP Server
//
//  Created by Oshan Upreti on 9/21/18.
//  Copyright © 2018 Oshan Upreti. All rights reserved.
//


package com.company;

//importing the APIs from com.sun.net.httpserver
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
//import javax.swing.JOptionPane;


public class Main {



    public static void handle(HttpExchange exchange,InetSocketAddress add1) throws IOException{
            //Getting the address of the server
            String address=InetAddress.getLocalHost ().toString ();
            //getting the port number of the server
            int port=add1.getPort ();


            String hello ="Port no: "+port+" Address: "+address+ " This is Working";

        exchange.sendResponseHeaders (300, hello.getBytes ().length);

        OutputStream out = exchange.getResponseBody ();
        //Writing the output to the server
        out.write (hello.getBytes ());

        out.close ();



    }

    public static void main(String[] args) throws IOException {

       try {
           //Initializing the address and the port number for the server
           InetSocketAddress add1=new InetSocketAddress(9000);
           //HttpServer Initialization
           HttpServer server = HttpServer.create(add1,5);
           HttpContext context=server.createContext("/");
            //sethandling using exchanges and address of the socket
           context.setHandler(exchange->handle(exchange,add1));
           //Initiation of the server
           server.start ();
           //Delay set to 30 seconds after which the server stops
          // server.stop (30);
           System.out.println (InetAddress.getLocalHost ());


        } catch (IOException e) {

           e.printStackTrace();

       }




    }



}

