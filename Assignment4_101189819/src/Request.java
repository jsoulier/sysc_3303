/**
 * Represents a request.
 */
public enum Request {
    /** Represents an invalid request. */
    INVALID(new byte[] { 0, 0 }),

    /** Represents a read request. */
    READ(new byte[] { 0, 1 }),

    /** Represents a write request. */
    WRITE(new byte[] { 0, 2 }),

    READ_RESPONSE(new byte[] { 0, 3, 0, 1 }),
    WRITE_RESPONSE(new byte[] { 0, 4, 0, 0 });

    /** The request response. */
    private final byte[] bytes;

    Request(byte bytes[]) {
        this.bytes = bytes;
    }

    public byte[] toBytes() {
        return bytes;
    }

    public static Request fromBytes(byte byte1, byte byte2) {
        for (Request request : Request.values()) {
            if (request.toBytes()[0] != byte1) {
                continue;
            }
            if (request.toBytes()[1] != byte2) {
                continue;
            }
            return request;
        }
        throw new RuntimeException();
    }

    /**
     * Get the request response.
     * @return The request response.
     */
    public Request getResponse() {
        if (this == READ) {
            return READ_RESPONSE;
        }
        if (this == WRITE) {
            return WRITE_RESPONSE;
        }
        throw new RuntimeException();
    }

    public boolean isResponse() {
        return this == READ_RESPONSE || this == WRITE_RESPONSE;
    }

    /**
     * Get the request as a string.
     * @return The request as a string.
     */
    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
