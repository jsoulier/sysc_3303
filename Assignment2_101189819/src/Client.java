import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Client {

    private DatagramSocket socket;

    public Client() {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void send(Request request, String fileName, Mode mode) {
        ClientPacket packet = new ClientPacket(request, fileName, mode);

        System.out.println(packet.toString());

        // DatagramPacket datagram = ClientPacket.create(packet, Config.HOST_IP, Config.HOST_PORT);
    }

    public static void main(String[] args) {


        Client client = new Client();
        client.send(Request.WRITE, "myFile", Mode.NETASCII);
    }
}
