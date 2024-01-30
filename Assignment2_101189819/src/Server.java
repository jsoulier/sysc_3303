import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

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
     * Send a packet to the client.
     */
    public void send() {

        // Create a new socket for the response
        DatagramSocket socket;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

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
        
        // Send the packet
        try {
            socket.send(datagram);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        socket.close();

        System.out.println("Sent");
    }

    /**
     * Receive a packet from the client.
     * @return If the packet was invalid.
     */
    public boolean receive() {

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
        return packet.getRequest() != Request.INVALID;
    }

    /**
     * Close all sockets.
     */
    public void close() {
        socket.close();
    }

    /**
     * Start the server.
     * @param args Unused.
     */
    public static void main(String[] args) {
        Server server = new Server();

        // Loop until invalid packet
        while (server.receive()) {
            server.send();
            System.out.println();
        }

        // Close and throw exception
        server.close();
        System.out.println();
        throw new RuntimeException("Invalid Packet");
    }
}
