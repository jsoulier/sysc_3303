import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * The server application.
 */
public class Server implements Runnable {

    /** The RMI interface for the host. */
    private HostInterface host;

    /** The packet to send to the host. */
    private Packet sendPacket;

    /** The packet received from the host. */
    private Packet receivePacket;

    /**
     * Create a new server.
     * @param host The RMI interface for the host.
     */
    public Server(HostInterface host) {
        this.host = host;
    }

    /**
     * Receive a packet from the host.
     */
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

    /**
     * Send a packet to the host.
     */
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

    /**
     * The server thread entrypoint.
     */
    @Override
    public void run() {
        while (true) {
            receive();
            send();
            System.out.println();
        }
    }

    /**
     * The server entrypoint.
     * @param args Unused.
     */
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
