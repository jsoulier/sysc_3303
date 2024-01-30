/**
 * Represents a request.
 */
public enum Request {
    /** Represents an invalid request. */
    INVALID ((byte) 0, null),

    /** Represents a read request. */
    READ    ((byte) 1, new byte[] { 0, 3, 0, 1 }),

    /** Represents a write request. */
    WRITE   ((byte) 2, new byte[] { 0, 4, 0, 0 });

    /** The byte representation. */
    private final byte value;

    /** The request response. */
    private final byte[] response;

    /**
     * Create a new request.
     * @param value The byte representation.
     * @param response The request response.
     */
    Request(byte value, byte[] response) {
        this.value = value;
        this.response = response;
    }

    /**
     * Get the byte representation.
     * @return The byte representation.
     */
    public byte toByte() {
        return value;
    }

    /**
     * Create request from a byte representation.
     * @param value The byte representation.
     * @return The matching request.
     */
    public static Request fromByte(byte value) {
        for (Request request : Request.values()) {
            if (request.toByte() == value) {
                return request;
            }
        }
        throw new RuntimeException();
    }

    /**
     * Get the request response.
     * @return The request response.
     */
    public byte[] getResponse() {
        return this.response;
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
