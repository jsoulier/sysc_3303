import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyClassTest {
    @Test
    void testAdd() {
        assertEquals(5, MyClass.add(2, 3));
        assertNotEquals(4, MyClass.add(2, 3));
    }

    @Test
    void testSubtract() {
        assertEquals(3, MyClass.subtract(5, 2));
        assertNotEquals(2, MyClass.subtract(5, 2));
    }
}