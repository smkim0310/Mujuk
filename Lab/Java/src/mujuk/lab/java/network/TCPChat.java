package mujuk.lab.java.network;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPChat {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        String hi = s.nextLine();
        System.out.println(hi);

        TCPChat chat = new TCPChat();
        int selectedOption;
        while(true){
            selectedOption = chat.start();
            System.out.println("You selected: " + selectedOption);
            if(selectedOption == 0) return;
            
            if(selectedOption == 1){
                chat.openServer();
            }
            else if(selectedOption == 2) {
                chat.openClient();
            }
                        
        }
    }

    public int start() {
        System.out.println("Welcome to Mujuk!");
        System.out.println("1. Host a server (1)");
        System.out.println("2. Join a server (2)");
        System.out.print("Choose from above options(pressing any other key will exit program): ");
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();
        // s.close();
        if(input.equals("1") || input.equals("2")) {
            return Integer.parseInt(input);
        }
        return 0;
    }

    public void openServer() {
        try{
            String line, newLine;
            ServerSocket ss=new ServerSocket(6789);
            // Communication Endpoint for the client and server.
            while(true) {
                // Waiting for socket connection
                Socket s=ss.accept();
                System.out.println("Server Started...");

                // DataInputStream to read data from input stream
                BufferedReader inp = new BufferedReader(new InputStreamReader(s.getInputStream()));

                // DataOutputStream to write data on outut stream
                DataOutputStream out = new DataOutputStream(s.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

                // Reads a line text
                while(true) {
                    System.out.println("Press 'q' if you want to exit server");
                    line = inp.readLine();
                    // Writes in output stream as bytes
                    //out.writeBytes(line +'\n');
                    System.out.println("Received from client: " + line);
                    newLine = in.readLine();
                    if (newLine.equals("q")) {
                        out.writeBytes("Server is down..." +'\n');
                        ss.close();
                        return;
                    } else {
                        out.writeBytes(newLine + '\n');
                    }
                }
            }
        } catch(Exception e) {}
    }

    public void openClient() {
        String line, newLine;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            // Communication Endpoint for client and server
            Socket cs=new Socket("LocalHost",6789);
            System.out.println("Client Started...");

            // DataInputStream to read data from input stream
            BufferedReader inp = new BufferedReader(new InputStreamReader(cs.getInputStream()));

            // DataOutputStream to write data on outut stream
            DataOutputStream out=new DataOutputStream(cs.getOutputStream());

            while(true) {
                newLine = in.readLine();
                if (newLine.equals("q")) {
                    out.writeBytes("Client is down..." +'\n');
                    cs.close();
                    return;
                } else {
                    out.writeBytes(newLine + '\n');
                }
                line = inp.readLine();
                System.out.println("Received from server: "+line);
            }
        } catch(Exception e) {}
    }
}
