package utils;

public class Config {
    private String baseNetwork = "000.000.000";
    private int discoveryPort = 9030;
    private int discoveryResponsePort = 9031;
    private int commandsPort = 9060;
    private int commandsResponsePort = 9061;
    private int findDeviceTimeout = 2000;
    private int ftpServerPort  = 9020;

    /* Accessors */
    public String getBaseNetwork(){return baseNetwork;}
    public int getDiscoveryPort(){return discoveryPort;}
    public int getDiscoveryResponsePort(){return discoveryResponsePort;}
    public int getCommandsPort(){return commandsPort;}
    public int getCommandsResponsePort(){return commandsResponsePort;}
    public int getFindDeviceTimeout(){return findDeviceTimeout;}
    public int getFtpServerPort(){return ftpServerPort;}

    /* Mutators */
    public void setBaseNetwork(String base_network){this.baseNetwork = base_network;}
    public void setDiscoveryPort(int discovery_port) throws Exception{
        checkValidPort(discoveryPort);
        discoveryPort = discovery_port;
    }
    public void setDiscoveryResponsePort(int discovery_response_port) throws Exception{
        checkValidPort(discovery_response_port);
        discoveryResponsePort = discovery_response_port;
    }
    public void setCommandsPort(int commands_port) throws Exception{
        checkValidPort(commands_port);
        commandsPort = commands_port;
    }
    public void setCommandsResponsePort(int commands_response_port) throws Exception{
        checkValidPort(commands_response_port);
        commandsResponsePort = commands_response_port;
    }
    public void setFindDeviceTimeout(int find_device_timeout) throws Exception{
        if(find_device_timeout < 0){
            throw new Exception("Cannot set device timeout to negative value.");
        }
        findDeviceTimeout = find_device_timeout;
    }
    private boolean checkValidPort(int port) throws Exception{
        if(port < 0 || port > 65535){
            throw new Exception(port + " not a valid port number");
        }
        return true;
    }
}
