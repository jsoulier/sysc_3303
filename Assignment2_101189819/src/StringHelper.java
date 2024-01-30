import java.net.DatagramPacket;

/**
 * Helper for string conversions.
 */
public class StringHelper {
    
    /**
     * Convert a datagram packet to a string.
     * @param datagram The datagram packet.
     * @return The string.
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
     * Convert a byte array to a string.
     * @param bytes The byte array.
     * @return The string.
     */
    static String toString(byte[] bytes) {
        StringBuilder string = new StringBuilder();
        for (byte i : bytes) {
            string.append(i);
        }
        return string.toString();
    }
}
