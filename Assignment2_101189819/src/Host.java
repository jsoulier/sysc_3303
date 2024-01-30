import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Represents the host.
 */
public class Host {

    /** Socket for receiving from client. */
    private DatagramSocket receiveSocket;

    /** Socket for sending and receiving to and from server. */
    private DatagramSocket sendReceiveSocket;

    /** Datagram for sending and receiving to and from client. */
    private DatagramPacket clientDatagram;

    /** Datagram for sending receiving to and from server. */
    private DatagramPacket serverDatagram;

    /** Packet for receiving from client and sending to server. */
    private ClientPacket packet;

    /**
     * Create new host on specific port.
     */
    public Host() {
        try {
            receiveSocket = new DatagramSocket(Config.HOST_PORT);
            sendReceiveSocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * Send a packet to the client.
     */
    public void sendClient() {

        // Format the packet
        byte[] bytes = serverDatagram.getData();
        clientDatagram = new DatagramPacket(bytes, bytes.length, clientDatagram.getAddress(), clientDatagram.getPort());

        // Print the packet
        System.out.print("Sending(Client): ");
        System.out.print(StringHelper.toString(clientDatagram));
        System.out.print(", ");
        System.out.print("Data(String)=");
        System.out.print(StringHelper.toString(clientDatagram.getData()));
        System.out.println();

       // Create a new socket for the response
        DatagramSocket sendSocket;
        try {
            sendSocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        // Send the packet
        try {
            sendSocket.send(clientDatagram);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            sendSocket.close();
        }

        System.out.println("Sent(Client)");
    }

    /**
     * Send a packet to the server.
     */
    public void sendServer() {

        // Format the packet
        serverDatagram = ClientPacket.create(packet, Config.SERVER_IP, Config.SERVER_PORT);

        // Print the packet
        System.out.print("Sending(Server): ");
        System.out.print(StringHelper.toString(serverDatagram));
        System.out.print(", ");
        System.out.print(packet.toString());
        System.out.println();

        // Send the packet
        try {
            sendReceiveSocket.send(serverDatagram);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        System.out.println("Sent(Server)");
    }

    /**
     * Receive a packet from the client.
     */
    public void receiveClient() {

        // Create the packet
        byte[] bytes = new byte[Config.CLIENT_PACKET_LENGTH];
        clientDatagram = new DatagramPacket(bytes, bytes.length);

        System.out.println("Receiving(Client)");

        // Receive the packet
        try {
            receiveSocket.receive(clientDatagram);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        packet = ClientPacket.create(clientDatagram);
        
        // Print the packet
        System.out.print("Received(Client): ");
        System.out.print(StringHelper.toString(clientDatagram));
        System.out.print(", ");
        System.out.print(packet.toString());
        System.out.println();
    }

    /**
     * Receive a packet from the server.
     */
    public void receiveServer() {

        // Create the packet
        byte[] bytes = new byte[Config.SERVER_PACKET_LENGTH];
        serverDatagram = new DatagramPacket(bytes, bytes.length);

        System.out.println("Receiving(Server)");

        // Receive the packet
        try {
            sendReceiveSocket.receive(serverDatagram);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        // Print the packet
        System.out.print("Received(Server): ");
        System.out.print(StringHelper.toString(serverDatagram));
        System.out.print(", ");
        System.out.print("Data(String)=");
        System.out.print(StringHelper.toString(serverDatagram.getData()));
        System.out.println();
    }

    /**
     * Start the host.
     * @param args Unused.
     */
    public static void main(String[] args) {
        Host host = new Host();

        // Loop forever
        while (true) {

            // Forward to server
            host.receiveClient();
            host.sendServer();

            // Forward to client
            host.receiveServer();
            host.sendClient();

            System.out.println();
        }
    }
}
