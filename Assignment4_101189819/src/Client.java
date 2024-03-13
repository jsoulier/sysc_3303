import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * The client application.
 */
public class Client implements Runnable {

    /** The RMI interface for the host. */
    private HostInterface host;

    /**
     * Create a new client.
     * @param host The RMI interface for the host.
     */
    public Client(HostInterface host) {
        this.host = host;
    }

    /**
     * Send a packet to the host.
     * @param request The request in the packet.
     * @param fileName The fileName in the packet.
     * @param mode The mode in the packet.
     */
    public void send(Request request, String fileName, String mode) {
        try {
            Packet sendPacket = new Packet(request, fileName, mode, host.getAddress(), host.getClientPort());
            System.out.println("Sending: " + sendPacket);
            host.rpc(sendPacket);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        System.out.println("Sent");
    }

    /**
     * Receive a packet from the host.
     */
    public void receive() {
        Packet receivePacket;
        System.out.println("Receiving");
        try {
            Packet sendPacket = new Packet(host.getAddress(), host.getClientPort());
            receivePacket = host.rpc(sendPacket);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        System.out.println("Received: " + receivePacket);
    }

    /**
     * The client thread entrypoint.
     */
    @Override
    public void run() {
        for (int i = 0; i < Config.PACKETS - 1; i++) {
            StringBuilder string = new StringBuilder();
            string.append("file");
            string.append(i);
            string.append(".txt");
            if (i % 2 == 0) {
                send(Request.READ, string.toString(), "netascii");
            } else {
                send(Request.WRITE, string.toString(), "octet");
            }
            receive();
            System.out.println();
        }
        send(Request.INVALID, "invalid", "netascii");
    }

    /**
     * The client entrypoint.
     * @param args Unused.
     */
    public static void main(String[] args) {
        try {
            HostInterface host = (HostInterface) Naming.lookup("rmi://localhost/host");
            Client client = new Client(host);
            Thread thread = new Thread(client);
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
