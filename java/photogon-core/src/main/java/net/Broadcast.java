package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.Callable;

public class Broadcast implements Callable<Object>{
    private int portNum;
    private String localhost;
    private String hostname;
    private String baseNetwork;
    private DatagramSocket s;
    private String message;

    /**
     * This constructor will only broadcast on the 256 IP addresses on the localhost's base network.
     * To specify a different base network, please use alternate constructor.
     * @param broadcastPort The port on which pings will be sent to the network.
     * @throws UnknownHostException The localhost cannot be found.
     * @throws SocketException Could not get the NetworkInterfaces.
     */
    public Broadcast(int broadcastPort) throws UnknownHostException, SocketException {
        portNum = broadcastPort;
        localhost = getLocalHost();
        hostname = InetAddress.getLocalHost().getHostName();
        baseNetwork = baseNetworkFromFullAddress(localhost);
        message = localhost + "=" + hostname;
    }

    /**
     * This constructor will broadcast on the 256 IP addresses on the base network specified.
     * @param broadcastPort The port on which pings will be sent to the network.
     * @param baseNetwork The base network which should be pinged.
     * @throws SocketException Could not get NetworkInterfaces.
     * @throws UnknownHostException The localhost cannot be found.
     */
    public Broadcast(int broadcastPort, String baseNetwork) throws UnknownHostException, SocketException {
        this.portNum = broadcastPort;
        if(baseNetwork.equals("000.000.000") == false)
            this.baseNetwork = baseNetwork;
        localhost = getLocalHost();
        hostname = InetAddress.getLocalHost().getHostName();
        message = localhost + "=" + hostname;
    }

    /**
     * image.Main functionality. Continuously pings each IP address on the base network until
     * the program is terminated.
     * @return Blank object.
     * @throws SocketException Failed to create DatagramSocket.
     * @throws UnknownHostException Could not find host address.
     * @throws IOException Failed to send packet.
     * @throws InterruptedException Could not put thread to sleep.
     */
    public Object call() {
        System.out.println("Calling Broadcast call");

        /* Sending a non-blocking UDP message */
        try {
            s = new DatagramSocket(portNum);
            System.out.println("Socket");
            /* Broadcast Continuously */
            System.out.println("Broadcast main loop" + Thread.currentThread().getId());
            while (!Thread.currentThread().isInterrupted()) {

                System.out.println("Broadcasting...");
                /* Send device name and address to every address on LAN */
                for (int i = 1; i <= 255; i++) {
                    String ip = baseNetwork + "." + Integer.toString(i);
                    InetAddress receiver = InetAddress.getByName(ip);
                    byte[] sendBuffer = message.getBytes();
                    DatagramPacket packet = new DatagramPacket(sendBuffer,
                            sendBuffer.length, receiver, portNum);
                    s.send(packet);
                }
                Thread.sleep(500);
            }
        } catch(IOException ie){
            ie.printStackTrace();
            s.close();
        } catch(InterruptedException p){
            System.out.println("Interrupted exception on Broadcast");
            s.close();
        }
        s.close();
        return null;
    }

    /**
     * Computes the base network from the full address
     * @param address The address from which the base network is to be extracted.
     * @return base network as a string.
     */
    public static String baseNetworkFromFullAddress(String address){
        return address.substring(0, address.lastIndexOf("."));
    }

    /**
     * Determines the localhost. Will only use localhost address beginning with 127 or 172 if
     * no other addresses are found.
     * @return localhost address.
     * @throws SocketException Could not get network interfaces.
     */
    public static String getLocalHost() throws SocketException {
        List<String> hosts = new ArrayList<String>();

        Enumeration<NetworkInterface> net;
        net = NetworkInterface.getNetworkInterfaces();
        while(net.hasMoreElements()) {
            NetworkInterface ni = net.nextElement();
            Enumeration<InetAddress> adr = ni.getInetAddresses();
            while(adr.hasMoreElements()) {
                InetAddress ia = adr.nextElement();
                String a = ia.getHostAddress();
                if((a.length() - a.replace(".","").length()) == 3) {
                    String base = a.substring(0, 3);
                    if(base.equals("127") == false && base.equals("172") == false)
                        return ia.getHostAddress();
                    else hosts.add(ia.getHostAddress());
                }
            }
        }
        if(hosts.isEmpty())
            return "none";
        else {
            return hosts.get(0);
        }
    }
}
