package node;

import net.Device;
import net.DeviceGroup;
import net.FindDevices;
import utils.Config;

import java.util.List;
import java.util.Scanner;

public class Master {
    private List<Device> devices;
    private DeviceGroup deviceGroup;
    private Config cfg;

    public Master(){
        cfg = new Config();
    }

    public Master(Config cfg){
        this.cfg = cfg;
    }

    public void execute() throws Exception {
        System.out.println("Executing as MASTER");
        System.out.println("Type 'exit' to quit");
        FindDevices deviceFinder = new FindDevices(cfg);

        devices = deviceFinder.call();
        deviceGroup = new DeviceGroup(devices);

        Scanner scan = new Scanner(System.in);
        System.out.println("Starting loop in Master");
        while(true) {
            String line = scan.nextLine();
            if(line.equalsIgnoreCase("EXIT"))
                break;
            else {
                System.out.println("Sending message");
                deviceGroup.sendToAll(line);
            }

        }
        scan.close();
    }
}
