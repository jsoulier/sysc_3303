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
     * Create a new client on unspecified port.
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
     * Send a packet to the server.
     * @param request The server request.
     * @param fileName The filename.
     * @param mode The mode.
     */
    public void send(Request request, String fileName, Mode mode) {
        ClientPacket packet = new ClientPacket(request, fileName, mode);
        DatagramPacket datagram = ClientPacket.create(packet, Config.HOST_IP, Config.HOST_PORT);
        System.out.print("Sending: ");
        System.out.print(StringHelper.toString(datagram));
        System.out.print(", ");
        System.out.print(packet.toString());
        System.out.println();
        try {
            socket.send(datagram);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        System.out.println("Sent");
    }

    /**
     * Receive a packet from the server.
     */
    public void receive() {
        byte[] bytes = new byte[Config.PACKET_LENGTH];
        DatagramPacket datagram = new DatagramPacket(bytes, bytes.length);
        System.out.println("Receiving");
        try {
            socket.receive(datagram);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        System.out.print("Received: ");
        System.out.print(StringHelper.toString(datagram));
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

        client.send(Request.INVALID, "file", Mode.NETASCII);
        client.receive();

        client.close();
    }
}
