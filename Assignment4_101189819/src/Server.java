import java.rmi.Naming;
import java.rmi.RemoteException;

public class Server implements Runnable {

    private HostInterface host;
    private Packet sendPacket;
    private Packet receivePacket;

    public Server(HostInterface host) {
        this.host = host;
    }

    public void receive() {
        System.out.println("Receiving");
        try {
            sendPacket = new Packet(host.getAddress(), host.getServerPort());
            receivePacket = host.rpc(sendPacket);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        System.out.println("Received: " + receivePacket);
    }

    public void send() {
        try {
            sendPacket = new Packet(receivePacket.getResponse(), host.getAddress(), host.getServerPort());
            System.out.println("Sending: " + sendPacket);
            host.rpc(sendPacket);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        System.out.println("Sent");
    }

    @Override
    public void run() {
        receive();
        send();
    }

    public static void main(String[] args) {
        try {
            HostInterface host = (HostInterface) Naming.lookup("rmi://localhost/host");
            Server server = new Server(host);
            Thread thread = new Thread(server);
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
