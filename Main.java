import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

// Interfaz para la lista
interface List {
    boolean isEmpty();
    void insertFirst(Object data);
    Object deleteFirst();
}

// Clase para la lista simplemente enlazada
class SinglyLinkedList implements List {
    private Node head;

    // Constructor
    public SinglyLinkedList() {
        this.head = null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void insertFirst(Object data) {
        Node newNode = new Node(data);
        newNode.setNext(head);
        head = newNode;
    }

    public Object deleteFirst() {
        if (isEmpty()) {
            return null;
        }
        Node temp = head;
        head = head.getNext();
        return temp.getData();
    }
}

// Clase para el nodo de la lista
class Node {
    private Object data;
    private Node next;

    // Constructor
    public Node(Object data) {
        this.data = data;
        this.next = null;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}

// Clase para la pila
class MyStack {
    private List elements;

    // Constructor
    public MyStack(List list) {
        this.elements = list;
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public void push(Object item) {
        elements.insertFirst(item);
    }

    public Object pop() {
        return elements.deleteFirst();
    }

    public Object peek() {
        if (isEmpty()) {
            return null;
        }
        return ((Node) elements).getData();
    }
}

// Clase principal
public class Main {
    public static void main(String[] args) {
        // Selección de tipo de lista y pila
        MyStack stack = selectStackType();
        
        // Expresión infija
        String infixExpression = "(1+2)*9";
        String postfixExpression = convertToPostfix(infixExpression, stack);
        
        System.out.println("Expresión infix: " + infixExpression);
        System.out.println("Expresión postfix: " + postfixExpression);
        
        // Evaluar la expresión postfija
        int result = evaluatePostfix(postfixExpression, stack);
        displayResult(result);
    }

    // Método para seleccionar el tipo de pila
    private static MyStack selectStackType() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Seleccione el tipo de pila (lista): ");
        String stackType = scanner.nextLine();
        if (stackType.equals("lista")) {
            return new MyStack(new SinglyLinkedList());
        } else {
            System.out.println("Tipo de pila no soportado. Usando lista.");
            return new MyStack(new SinglyLinkedList());
        }
    }

    // Método para convertir la expresión infija a postfija
    private static String convertToPostfix(String infixExpression, MyStack stack) {
        StringBuilder postfix = new StringBuilder();
        
        for (int i = 0; i < infixExpression.length(); i++) {
            char c = infixExpression.charAt(i);
            if (Character.isDigit(c)) {
                postfix.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && (char) stack.peek() != '(') {
                    postfix.append(stack.pop());
                }
                stack.pop(); // Descartar '('
            } else { // Operador
                while (!stack.isEmpty() && precedence((char) stack.peek()) >= precedence(c)) {
                    postfix.append(stack.pop());
                }
                stack.push(c);
            }
        }

        // Vaciar la pila
        while (!stack.isEmpty()) {
            postfix.append(stack.pop());
        }

        return postfix.toString();
    }

    // Método para evaluar la expresión postfija
    private static int evaluatePostfix(String postfixExpression, MyStack stack) {
        for (int i = 0; i < postfixExpression.length(); i++) {
            char c = postfixExpression.charAt(i);
            if (Character.isDigit(c)) {
                stack.push(c - '0');  // Convertir char a int y apilar
            } else {
                int b = (int) stack.pop(); // Operando 2
                int a = (int) stack.pop(); // Operando 1
                int result = 0;
                switch (c) {
                    case '+': result = a + b; break;
                    case '-': result = a - b; break;
                    case '*': result = a * b; break;
                    case '/': result = a / b; break;
                }
                stack.push(result); // Resultado de la operación
            }
        }
        return (int) stack.pop(); // El resultado final
    }

    // Método para mostrar el resultado
    private static void displayResult(int result) {
        System.out.println("El resultado de la expresión es: " + result);
    }

    // Método para determinar la precedencia de los operadores
    private static int precedence(char operator) {
        switch (operator) {
            case '+': case '-': return 1;
            case '*': case '/': return 2;
        }
        return -1;
    }
}
