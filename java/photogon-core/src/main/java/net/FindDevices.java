package net;

import utils.Config;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import static java.lang.Thread.interrupted;

public class FindDevices implements Callable<Object> {
    private int broadcastPort;
    private int listenPort;
    private String baseNetwork;
    private List<Device> devices;
    private DatagramSocket s;
    private int msg_length;
    private int deviceTimeout = 2000;

    /**
     *
     * @param broadcastPort The port on which pings will be sent to the network.
     * @param listenPort The port on which responses will be recieved from the network.
     * @param baseNetwork The base network on which to scan. (eg 192.168)
     */
    public FindDevices(int broadcastPort, int listenPort, String baseNetwork) {
        System.out.println("Find Devices Constructor");
        this.broadcastPort = broadcastPort;
        this.listenPort = listenPort;
        this.baseNetwork = baseNetwork;
        this.msg_length = 100;
    }

    /**
     *
     * @param cfg The configuration file which contains the desired broadcast port, listen port, and
     *            base network.
     */
    public FindDevices(Config cfg){
        this.broadcastPort = cfg.getDiscoveryPort();
        this.listenPort = cfg.getDiscoveryResponsePort();
        this.baseNetwork = cfg.getBaseNetwork();
        this.msg_length = 100;
        this.deviceTimeout = cfg.getFindDeviceTimeout();
    }

    public List<Device> call() {
        System.out.println("Find Devices call");
        devices = new ArrayList<Device>();

        /* Begin broadcasting */
        Callable<Object> bcaster = null;
        try {
            bcaster = new Broadcast(broadcastPort);
            FutureTask<Object> broadcastTask = new FutureTask<Object>(bcaster);
            Thread t_broadcast = new Thread(broadcastTask);
            System.out.println(t_broadcast.getId());
            t_broadcast.start();

            /* Listen for responses */
            s = new DatagramSocket(listenPort);
            s.setSoTimeout(deviceTimeout);
            ArrayList<String> addresses = new ArrayList<String>();
            byte[] recvBuffer = new byte[msg_length];
            DatagramPacket packet;
            int repeats = 0;
            int timeouts = 0;

            /* Main Loop - packets from discoverable devices received */
            while(repeats < 10 && timeouts < 2 && !interrupted()) {
                try {
                    packet = new DatagramPacket(recvBuffer, msg_length);
                    s.receive(packet);
                    String receiveStr = new String(recvBuffer);
                    String address = receiveStr.substring(0, receiveStr.indexOf("="));
                    String hostname = receiveStr.substring(receiveStr.indexOf("=") + 1);

                    if(addresses.contains(address) == false) {
                        repeats = 0;
                        System.out.println(hostname);
                        devices.add(new Device(hostname, address));
                        addresses.add(address);
                    } else {
                        repeats++;
                    }
                } catch(SocketTimeoutException e) {
                    System.out.println(devices.size() + " devices found after "
                            + deviceTimeout + "ms.");
                    timeouts++;
                }
            }
            t_broadcast.interrupt();
            t_broadcast.join();
        } catch (UnknownHostException e) {
            System.out.println("UnknownHostException in Find Devices");
            e.printStackTrace();
        } catch (SocketException e) {
            System.out.println("SocketException in Find Devices");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("InterruptedException in Find Devices");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException in Find Devices");
            e.printStackTrace();
        }

        s.close();
        return devices;
    }
}
