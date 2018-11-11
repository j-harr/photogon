package node;

import utils.Config;

public class Main {
    public static void main(String[] args) throws Exception {
        Config cfg = new Config();
        if(args.length == 0) {
            System.out.println("Please use the format : "
                    + "java PlantCam -master  OR  java PlantCam -node");
            Node node = new Node(cfg);
            node.call();
        }
        else {
            if(args[0].equals("-master")) {
                Master master = new Master(cfg);
                master.execute();
            } else if(args[0].equals("-node")) {
                Node node = new Node(cfg);
                node.call();
            }
        }
    }
}
