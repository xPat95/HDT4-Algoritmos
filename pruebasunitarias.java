import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MainTest {
    private SinglyLinkedList list;
    private MyStack stack;

    @BeforeEach
    void setUp() {
        list = new SinglyLinkedList();
        stack = new MyStack(new SinglyLinkedList());
    }

    @Test
    void testListOperations() {
        assertTrue(list.isEmpty());
        list.insertFirst(10);
        assertFalse(list.isEmpty());
        assertEquals(10, list.getFirst());
        assertEquals(10, list.deleteFirst());
        assertTrue(list.isEmpty());
    }

    @Test
    void testStackOperations() {
        assertTrue(stack.isEmpty());
        stack.push(5);
        assertFalse(stack.isEmpty());
        assertEquals(5, stack.peek());
        assertEquals(5, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void testConvertToPostfix() {
        String infix = "3+5*2";
        String expectedPostfix = "3 5 2 * +";
        assertEquals(expectedPostfix, Main.convertToPostfix(infix, new MyStack(new SinglyLinkedList())));
    }

    @Test
    void testEvaluatePostfix() {
        String postfix = "3 5 2 * +";
        int expectedResult = 13;
        assertEquals(expectedResult, Main.evaluatePostfix(postfix, new MyStack(new SinglyLinkedList())));
    }
}
