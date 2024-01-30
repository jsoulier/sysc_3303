import java.net.DatagramPacket;

/**
 * TODO
 */
public class StringHelper {
    
    /**
     * TODO
     * @param datagram
     * @return
     */
    static String toString(DatagramPacket datagram) {
        StringBuilder string = new StringBuilder();
        string.append("Address=");
        string.append(datagram.getAddress().toString());
        string.append(", ");
        string.append("Port=");
        string.append(datagram.getPort());
        string.append(", ");
        string.append("Data=");
        string.append(datagram.getData());
        string.append(", ");
        string.append("Length=");
        string.append(datagram.getLength());
        return string.toString();
    }

    /**
     * TODO
     * @param bytes
     * @return
     */
    static String toString(byte[] bytes) {
        StringBuilder string = new StringBuilder();
        for (byte i : bytes) {
            string.append(i);
        }
        return string.toString();
    }
}
