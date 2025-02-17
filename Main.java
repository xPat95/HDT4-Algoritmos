import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

// Interface para la lista
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
class myStack {
    private List elements;

    // Constructor
    public myStack(List list) {
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
        selectStackType();
        selectListType();
        
        String infixExpression = "(1+2)*9";
        String postfixExpression = convertToPostfix(infixExpression);
        System.out.println("Expresión infix: " + infixExpression);
        System.out.println("Expresión postfix: " + postfixExpression);
        
        int result = evaluatePostfix(postfixExpression);
        displayResult(result);
    }

    private static void selectStackType() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Seleccione el tipo de pila (arrayList, vector, lista): ");
            String stackType = scanner.nextLine();
        }
    }

    private static void selectListType() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Seleccione el tipo de lista (simplemente encadenada, doblemente encadenada): ");
            if (scanner.hasNextLine()) {
                String listType = scanner.nextLine();
                // Aquí puedes hacer lo que necesites con el tipo de lista
            } else {
                System.out.println("No se proporcionó una entrada válida para el tipo de lista.");
                // Manejar el caso en el que no hay entrada disponible
            }
        }
    }
    private static String readExpressionFromFile(String filename) {
        StringBuilder expression = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                expression.append(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return expression.toString();
    }

    private static String convertToPostfix(String infixExpression) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < infixExpression.length(); i++) {
            char c = infixExpression.charAt(i);
            if (Character.isDigit(c)) {
                postfix.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop());
                }
                stack.pop(); // Discard '('
            } else { // Operator
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(c)) {
                    postfix.append(stack.pop());
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            postfix.append(stack.pop());
        }

        return postfix.toString();
    }

    private static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
        }
        return -1;
    }

    private static int evaluatePostfix(String postfixExpression) {
        // Implementación del algoritmo para evaluar la expresión postfix
        // Aquí debería ir la lógica para evaluar la expresión
        return 0;
    }

    private static void displayResult(int result) {
        System.out.println("El resultado de la expresión es: " + result);
    }
}