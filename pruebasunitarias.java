/**
 * Clase de prueba JUnit para probar las funcionalidades de la lista enlazada, la pila y la calculadora.
 */
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Clase de pruebas para las estructuras de datos y la conversión/evaluación de expresiones.
 */
class MainTest {
    private SinglyLinkedList list;
    private MyStack stack;

    /**
     * Configura las estructuras antes de cada prueba.
     */
    @BeforeEach
    void setUp() {
        list = new SinglyLinkedList();
        stack = new MyStack(new SinglyLinkedList());
    }

    /**
     * Prueba las operaciones básicas de la lista enlazada.
     */
    @Test
    void testListOperations() {
        assertTrue(list.isEmpty(), "La lista debe estar vacía al inicio");
        list.insertFirst(10);
        assertFalse(list.isEmpty(), "La lista no debe estar vacía después de una inserción");
        assertEquals(10, list.getFirst(), "El primer elemento debe ser 10");
        assertEquals(10, list.deleteFirst(), "Eliminación debe devolver 10");
        assertTrue(list.isEmpty(), "La lista debe estar vacía después de eliminar el único elemento");
    }

    /**
     * Prueba las operaciones básicas de la pila.
     */
    @Test
    void testStackOperations() {
        assertTrue(stack.isEmpty(), "La pila debe estar vacía al inicio");
        stack.push(5);
        assertFalse(stack.isEmpty(), "La pila no debe estar vacía después de una inserción");
        assertEquals(5, stack.peek(), "El tope de la pila debe ser 5");
        assertEquals(5, stack.pop(), "Pop debe devolver 5");
        assertTrue(stack.isEmpty(), "La pila debe estar vacía después de eliminar el único elemento");
    }

    /**
     * Prueba la conversión de una expresión infija a postfija.
     */
    @Test
    void testConvertToPostfix() {
        String infix = "3+5*2";
        String expectedPostfix = "3 5 2 * +";
        assertEquals(expectedPostfix, Main.convertToPostfix(infix, new MyStack(new SinglyLinkedList())), "Conversión a postfijo incorrecta");
    }

    /**
     * Prueba la evaluación de una expresión en notación postfija.
     */
    @Test
    void testEvaluatePostfix() {
        String postfix = "3 5 2 * +";
        int expectedResult = 13;
        assertEquals(expectedResult, Main.evaluatePostfix(postfix, new MyStack(new SinglyLinkedList())), "Evaluación de postfijo incorrecta");
    }
}
