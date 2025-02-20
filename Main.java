import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

// Interfaz para la lista
interface List {
    boolean isEmpty();
    void insertFirst(Object data);
    Object deleteFirst();
    Object getFirst();
}

// Clase para la lista simplemente enlazada
class SinglyLinkedList implements List {
    private Node head;

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
    
    public Object getFirst() {
        return isEmpty() ? null : head.getData();
    }
}

// Clase para el nodo de la lista
class Node {
    private Object data;
    private Node next;

    public Node(Object data) {
        this.data = data;
        this.next = null;
    }

    public Object getData() {
        return data;
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
        return elements.isEmpty() ? null : elements.deleteFirst();
    }

    public Object peek() {
        return elements.isEmpty() ? null : elements.getFirst();
    }
}

// Clase principal
public class Main {
    public static void main(String[] args) {
        String infixExpression = readExpressionFromFile("datos.txt");
        if (infixExpression == null) {
            System.out.println("Error al leer el archivo.");
            return;
        }

        MyStack stack = new MyStack(new SinglyLinkedList());
        String postfixExpression = convertToPostfix(infixExpression, stack);
        
        System.out.println("Expresión infix: " + infixExpression);
        System.out.println("Expresión postfix: " + postfixExpression);
        
        int result = evaluatePostfix(postfixExpression, stack);
        displayResult(result);
    }

    private static String readExpressionFromFile(String filename) {
        try {
            Scanner scanner = new Scanner(new File(filename));
            String expression = scanner.nextLine().replaceAll("\\s+", ""); // Elimina espacios innecesarios
            scanner.close();
            return expression;
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + filename);
            return null;
        }
    }

    private static String convertToPostfix(String infixExpression, MyStack stack) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> operatorStack = new Stack<>();

        for (int i = 0; i < infixExpression.length(); i++) {
            char c = infixExpression.charAt(i);

            if (Character.isDigit(c)) { 
                // Capturar números completos
                while (i < infixExpression.length() && Character.isDigit(infixExpression.charAt(i))) {
                    postfix.append(infixExpression.charAt(i));
                    i++;
                }
                postfix.append(" "); // Agrega espacio después del número
                i--; 
            } else if (c == '(') {
                operatorStack.push(c);
            } else if (c == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    postfix.append(operatorStack.pop()).append(" ");
                }
                operatorStack.pop(); // Eliminar '('
            } else {
                while (!operatorStack.isEmpty() && precedence(operatorStack.peek()) >= precedence(c)) {
                    postfix.append(operatorStack.pop()).append(" ");
                }
                operatorStack.push(c);
            }
        }

        while (!operatorStack.isEmpty()) {
            postfix.append(operatorStack.pop()).append(" ");
        }

        return postfix.toString().trim();
    }

    private static int evaluatePostfix(String postfixExpression, MyStack stack) {
        Scanner scanner = new Scanner(postfixExpression);
        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                stack.push(scanner.nextInt());
            } else {
                char operator = scanner.next().charAt(0);
                Integer b = (Integer) stack.pop();
                Integer a = (Integer) stack.pop();

                if (a == null || b == null) {
                    System.out.println("Error: Expresión mal formada.");
                    return 0;
                }

                int result = 0;
                switch (operator) {
                    case '+': result = a + b; break;
                    case '-': result = a - b; break;
                    case '*': result = a * b; break;
                    case '/': result = a / b; break;
                }
                stack.push(result);
            }
        }
        scanner.close();

        return (int) stack.pop();
    }

    private static void displayResult(int result) {
        System.out.println("El resultado de la expresión es: " + result);
    }

    private static int precedence(char operator) {
        switch (operator) {
            case '+': case '-': return 1;
            case '*': case '/': return 2;
        }
        return -1;
    }
}
