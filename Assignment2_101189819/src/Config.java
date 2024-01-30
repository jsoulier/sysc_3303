import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * TODO
 */
public class Config {

    /** TODO */
    public static final int PACKETS = 11;

    /** TODO */
    public static final int PACKET_LENGTH = 1024;

    /** TODO */
    public static final int HOST_PORT = 23;

    /** TODO */
    public static final int SERVER_PORT = 69;

    /** TODO */
    public static final InetAddress CLIENT_IP;

    /** TODO */
    public static final InetAddress HOST_IP;

    /** TODO */
    public static final InetAddress SERVER_IP;

    static {
        try {
            InetAddress host = InetAddress.getLocalHost();
            CLIENT_IP = host;
            HOST_IP = host;
            SERVER_IP = host;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
