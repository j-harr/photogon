package net;

import utils.Config;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.Callable;

public class Responder implements Callable<Object> {
    private int broadcastPort;
    private int listenPort;
    private String localhost;
    private String hostname;
    private String message;
    private DatagramSocket r;
    private DatagramSocket s;
    private int msg_length;
    private int deviceTimeout = 2000;

    public Responder(int broadcastPort, int listenPort) throws SocketException, UnknownHostException {
        this.broadcastPort = broadcastPort;
        this.listenPort = listenPort;
        this.msg_length = 100;
        localhost = Broadcast.getLocalHost();
        hostname = InetAddress.getLocalHost().getHostName();
        message = localhost + "=" + hostname;
    }

    public Responder(Config cfg) throws SocketException, UnknownHostException {
        this.broadcastPort = cfg.getDiscoveryResponsePort();
        this.listenPort = cfg.getDiscoveryPort();
        this.msg_length = 100;
        this.deviceTimeout = cfg.getFindDeviceTimeout();
        localhost = Broadcast.getLocalHost();
        hostname = InetAddress.getLocalHost().getHostName();
        message = localhost + "=" + hostname;
    }

    public Object call() throws IOException {
        r = new DatagramSocket(listenPort);
        r.setSoTimeout(deviceTimeout);
        byte[] recvBuffer = new byte[msg_length];
        DatagramPacket recvPacket;

        s = new DatagramSocket(broadcastPort);
        String ip = "";
        /* Listen */
        while(true){
            try{
                recvPacket = new DatagramPacket(recvBuffer, msg_length);
                r.receive(recvPacket);
                String receiveStr = new String(recvBuffer);
                String address = receiveStr.substring(0, receiveStr.indexOf("="));
                /* Broadcast */
                try{
                    ip = address;
                    InetAddress receiver = InetAddress.getByName(ip);
                    byte[] sendBuffer = message.getBytes();
                    DatagramPacket packet = new DatagramPacket(sendBuffer, sendBuffer.length, receiver, broadcastPort);
                    s.send(packet);
                } catch (IOException io){
                    System.out.println("Packet not sent to " + ip);
                }
            } catch (SocketTimeoutException e) {
            }
        }
    }
}
