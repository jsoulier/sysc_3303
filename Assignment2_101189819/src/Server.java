import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Represents the server.
 */
public class Server {

    /** Socket for receiving. */
    private DatagramSocket socket;

    /** Datagram for sending and receiving. */
    private DatagramPacket datagram;

    /** Packet for receiving. */
    private ClientPacket packet;

    /**
     * Create a new server on a specific port.
     */
    public Server() {
        try {
            socket = new DatagramSocket(Config.SERVER_PORT);
        } catch (SocketException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * Send a packet to the client/host.
     */
    public void send() {

        // Format the packet
        byte[] bytes = packet.getRequest().getResponse();
        datagram = new DatagramPacket(bytes, bytes.length, datagram.getAddress(), datagram.getPort());

        // Print the packet
        System.out.print("Sending: ");
        System.out.print(StringHelper.toString(datagram));
        System.out.print(", ");
        System.out.print("Data(String)=");
        System.out.print(StringHelper.toString(datagram.getData()));
        System.out.println();

        // Create a new socket for the response
        DatagramSocket socket;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        // Send the packet
        try {
            socket.send(datagram);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            socket.close();
        }

        System.out.println("Sent");
    }

    /**
     * Receive a packet from the client/host.
     */
    public void receive() {

        // Create the packet
        byte[] bytes = new byte[Config.CLIENT_PACKET_LENGTH];

        System.out.println("Receiving");

        // Receive the packet
        datagram = new DatagramPacket(bytes, bytes.length);
        try {
            socket.receive(datagram);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        packet = ClientPacket.create(datagram);

        // Print the packet
        System.out.print("Received: ");
        System.out.print(StringHelper.toString(datagram));
        System.out.print(", ");
        System.out.print(packet.toString());
        System.out.println();

        // Check valid packet
        if (packet.getRequest() == Request.INVALID) {
            throw new RuntimeException();
        }
    }

    /**
     * Start the server.
     * @param args Unused.
     */
    public static void main(String[] args) {
        Server server = new Server();

        // Loop forever
        while (true) {

            // Response to host
            server.receive();
            server.send();

            System.out.println();
        }
    }
}
