/**
 * Represents a mode.
 */
public enum Mode {
    /** Represents netascii mode. */
    NETASCII,

    /** Represents octet mode. */
    OCTET;

    /**
     * Get the mode as a string in bytes.
     * @return The mode as a string in bytes.
     */
    public byte[] getBytes() {
        return toString().getBytes();
    }

    /**
     * Get the mode from a string.
     * @param value The string.
     * @return The matching mode.
     */
    public static Mode fromString(String value) {
        for (Mode mode : Mode.values()) {
            if (value.equals(mode.toString())) {
                return mode;
            }
        }
        throw new RuntimeException();
    }

    /**
     * Get the mode as a string.
     * @return The mode as a string.
     */
    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
