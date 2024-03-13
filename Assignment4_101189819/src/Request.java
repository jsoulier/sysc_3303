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

    /** Represents a response to a read request. */
    READ_RESPONSE(new byte[] { 0, 3, 0, 1 }),

    /** Represents a response to a write request. */
    WRITE_RESPONSE(new byte[] { 0, 4, 0, 0 });

    /** The request response. */
    private final byte[] bytes;

    /**
     * Create a new request.
     * @param bytes The byte representation.
     */
    Request(byte bytes[]) {
        this.bytes = bytes;
    }

    /**
     * Get the byte representation.
     * @return The byte representation.
     */
    public byte[] toBytes() {
        return bytes;
    }

    /**
     * Create a request from bytes.
     * @param byte1 The first byte.
     * @param byte2 The second byte.
     * @return The request.
     */
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

    /**
     * Check if the request is a response.
     * @return If the request is a response.
     */
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
