//Project 2
//Submitted By: Oshan Upreti
//IDE used: IntelliJ IDEA, OS: MacOS
package com.company;
//following libraries were imported
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws IOException{
            //Creating the listener for the socket in the port 9000 of the local host

            ServerSocket listener =new ServerSocket (9000);
            try{
                while(true)
                {
                    //Creating the socket fot the connection
                    Socket socket=listener.accept();
                    try{

                        System.out.println("The server has started successfully on the host "+ InetAddress.getLocalHost ().getHostAddress ()+" and the port number: 9000");
                        //Writing the response to the HTTP request from the client
                        PrintWriter response=new PrintWriter(socket.getOutputStream (),true);
                        response.print ("HTTP/1.1 200 OK\r\n");
                        response.print("Keep-Alive: timeout =10, max=100\r\n");
                        response.print("Content-Type: html\r\n");
                        response.print("\r\n");
                        response.println("<HTML>");
                        response.println("<HEAD>");
                        response.println("</HEAD>");
                        response.println("<BODY>");
                        //This will be displayed on the client's screen
                        response.println("The connection to the Web Server was made on "+new Date ().toString());
                        response.println("</BODY>");
                        response.println("</HTML>");
                        /*Taking the httpRequest from the client. It takes the inputStream to show the request sent by the client
                        in the console. BufferedReader is used and it reads line by line from the request and outputs it into the console
                        */

                        BufferedReader get= new BufferedReader (new InputStreamReader (socket.getInputStream ()));
                        String s=null;
                        s=get.readLine ();
                        while(s!=null)
                        {
                            System.out.println (s);
                            s=get.readLine ();

                        }
                        //System.out.println (s);

                    }
                    finally {//Closes the socket
                        socket.close ();
                    }


                }
            }
            //For catching the Input-Output exception
            catch (IOException e)
            {
                e.printStackTrace ();
            }
            finally {
                //closes the listener
                listener.close ();
            }



    }
}
