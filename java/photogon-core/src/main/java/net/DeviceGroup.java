package net;


import utils.Config;

import java.util.ArrayList;
import java.util.List;

public class DeviceGroup {
    private List<Device> devices;
    private Config cfg;

    public DeviceGroup(List<Device> deviceList) {
        devices = deviceList;
        cfg = new Config();
    }

    public DeviceGroup(List<Device> deviceList, Config configuration){
        devices = deviceList;
        cfg = configuration;
    }

    /**
     *
     * @param message The message sent to each device in the device group.
     */
    public void sendToAll(String message) {
        for(Device d : devices) {
            System.out.println("Sending message to " + d.name() + " at " + d.address());
            d.sendMessage(cfg.getCommandsPort(), message);
            System.out.println("Message sent");
        }
    }

    public void setDevices(List<Device> deviceList){
        devices.clear();
        devices = new ArrayList<Device>(deviceList);
    }

    public List<Device> getDevices(){return devices;}
}
