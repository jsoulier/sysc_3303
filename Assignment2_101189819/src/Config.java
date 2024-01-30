import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Holds common definitions.
 */
public class Config {

    /** The number of packets the client sends. */
    public static final int PACKETS = 11;

    /** The max length of a client packet. */
    public static final int CLIENT_PACKET_LENGTH = 1024;

    /** The max length of a server packet. */
    public static final int SERVER_PACKET_LENGTH = 4;

    /** The port for the host the listen on. */
    public static final int HOST_PORT = 23;

    /** The port for the server the listen on. */
    public static final int SERVER_PORT = 69;

    /** The host IP (localhost now). */
    public static final InetAddress HOST_IP;

    /** The server IP (localhost now). */
    public static final InetAddress SERVER_IP;

    // Assign the client, host, and server addresses
    static {
        try {
            InetAddress host = InetAddress.getLocalHost();
            HOST_IP = host;
            SERVER_IP = host;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
