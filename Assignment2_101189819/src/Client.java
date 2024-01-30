import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Represents the client.
 */
public class Client {

    /** Socket for sending and receiving. */
    private DatagramSocket socket;

    /**
     * Create a new client on an unspecified port.
     */
    public Client() {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * Send a packet to the server/host.
     * @param request The server request.
     * @param fileName The file name.
     * @param mode The mode.
     */
    public void send(Request request, String fileName, String mode) {

        // Format the packet
        ClientPacket packet = new ClientPacket(request, fileName, mode);
        DatagramPacket datagram = ClientPacket.create(packet, Config.HOST_IP, Config.HOST_PORT);

        // Print the packet
        System.out.print("Sending: ");
        System.out.print(StringHelper.toString(datagram));
        System.out.print(", ");
        System.out.print(packet.toString());
        System.out.println();

        // Send the packet
        try {
            socket.send(datagram);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        System.out.println("Sent");
    }

    /**
     * Receive a packet from the server/host.
     */
    public void receive() {

        // Create the packet
        byte[] bytes = new byte[Config.SERVER_PACKET_LENGTH];
        DatagramPacket datagram = new DatagramPacket(bytes, bytes.length);

        System.out.println("Receiving");

        // Receive the packet
        try {
            socket.receive(datagram);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        // Print the packet
        System.out.print("Received: ");
        System.out.print(StringHelper.toString(datagram));
        System.out.print(", ");
        System.out.print("Data(String)=");
        System.out.print(StringHelper.toString(datagram.getData()));
        System.out.println();
    }

    /**
     * Close all sockets.
     */
    public void close() {
        socket.close();
    }

    /**
     * Start the client.
     * @param args Unused.
     */
    public static void main(String[] args) {
        Client client = new Client();

        for (int i = 0; i < Config.PACKETS - 1; i++) {

            // Format the file name
            StringBuilder string = new StringBuilder();
            string.append("file");
            string.append(i);
            string.append(".txt");

            // Alternative between reads and writes
            if (i % 2 == 0) {
                client.send(Request.READ, string.toString(), "netascii");
            } else {
                client.send(Request.WRITE, string.toString(), "octet");
            }

            client.receive();

            System.out.println();
        }

        // Close server and socket
        client.send(Request.INVALID, "invalid", "netascii");
        client.close();
    }
}
