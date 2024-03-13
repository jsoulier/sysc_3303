import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

/**
 * Wrapper for client to server datagram packets.
 */
public class Packet implements Serializable {

    /** The server request. */
    private Request request = null;

    /** The file name. */
    private String fileName = null;

    /** The mode. */
    private String mode = null;

    private InetAddress address = null;

    private int port = 0;

    public Packet(DatagramPacket datagram) {
        request = Request.fromBytes(datagram.getData()[0], datagram.getData()[1]);
        if (!request.isResponse()) {
            fileName = readString(datagram.getData(), 2);
            mode = readString(datagram.getData(), fileName.getBytes().length + 3);
        }
        address = datagram.getAddress();
        port = datagram.getPort();
    }

    public Packet(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public Packet(Request request, InetAddress address, int port) {
        this.request = request;
        this.address = address;
        this.port = port;
    }

    /**
     * Create a new client packet.
     * @param request The server request.
     * @param fileName The file name.
     * @param mode The mode.
     */
    public Packet(Request request, String fileName, String mode, InetAddress address, int port) {
        this.request = request;
        this.fileName = fileName;
        this.mode = mode;
        this.address = address;
        this.port = port;
    }

    /**
     * Read a string from a byte array.
     * @param bytes The byte array.
     * @param start The starting index.
     * @return The string.
     */
    public static String readString(byte[] bytes, int start) {
        int i = 0;
        for (; start + i < bytes.length; i++) {
            if (bytes[start + i] == 0) {
                break;
            }
        }
        return new String(bytes, start, i);
    }

    public DatagramPacket create() {
        ByteBuffer buffer = ByteBuffer.allocate(getLength());
        if (request != null) {
            buffer.put(request.toBytes());
        }
        if (fileName != null) {
            assert mode == null;
            buffer.put(fileName.getBytes());
            buffer.put((byte) 0);
            buffer.put(mode.getBytes());
            buffer.put((byte) 0);
        }
        return new DatagramPacket(buffer.array(), buffer.capacity(), address, port);
    }

    public Request getResponse() {
        return request.getResponse();
    }

    /**
     * Get the packet length.
     * @return The packet length.
     */
    public int getLength() {
        int size = 4;
        if (fileName != null) {
            size += fileName.getBytes().length;
        }
        if (mode != null) {
            size += mode.getBytes().length;
        }
        return size;
    }

    /**
     * Get the client packet as a string.
     * @return The client packet as a string.
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        if (request != null) {
            string.append("Request=");
            string.append(request.toString());
            string.append(", ");
        }
        if (fileName != null) {
            string.append("FileName=");
            string.append(fileName);
            string.append(", ");
        }
        if (mode != null) {
            string.append("Mode=");
            string.append(mode.toString());
            string.append(", ");
        }
        if (address != null) {
            string.append("Address=");
            string.append(address);
            string.append(", ");
        }
        if (port != 0) {
            string.append("Port=");
            string.append(port);
            string.append(", ");
        }
        return string.toString();
    }
}
