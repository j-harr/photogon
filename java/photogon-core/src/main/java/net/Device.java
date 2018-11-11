package net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Device {
    private String name;
    private String address;

    public Device(String name, String address) {this.name = name; this.address = address;}
    public void setName(String name) {this.name = name;}
    public String name() {return name;}
    public String address() {return address;}

    public void sendMessage(int port, String message) {
        Socket s;
        try {
            s = new Socket(address, port);
            PrintWriter out =
                    new PrintWriter(s.getOutputStream(), true);
            out.println(message);
            s.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
