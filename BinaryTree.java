import java.io.File;
import java.io.IOException;
import java.util.Scanner;

class Node {
    int number;
    String data;
    Node left, right;

    public Node(int number, String data) {
        this.number = number;
        this.data = data;
        left = right = null;
    }
}

public class BinaryTree {
    Node root;

    // Function to insert a new node with the given data
    private Node insert(Node root, int number, String data) {
        if (root == null) {
            return new Node(number, data);
        }

        if (number < root.number) {
            root.left = insert(root.left, number, data);
        } else if (number > root.number) {
            root.right = insert(root.right, number, data);
        }

        return root;
    }

    // Function to build the binary tree from a file
    public void buildTreeFromFile(String fileName) {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(" ", 2);
                int number = Integer.parseInt(data[0]);
                String value = data[1];
                root = insert(root, number, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Function to print the inorder traversal of the tree
    private void displayBinaryTree(Node root, String prefix, boolean isLeft) {
        if (root != null) {
            System.out.println(prefix + (isLeft ? "├── " : "└── ") + root.data);
            displayBinaryTree(root.left, prefix + (isLeft ? "│   " : "    "), true);
            displayBinaryTree(root.right, prefix + (isLeft ? "│   " : "    "), false);
        }
    }

    public void displayTree() {
        displayBinaryTree(root, "", false);
    }

    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();
        binaryTree.buildTreeFromFile("questions1.txt");

        Scanner scanner = new Scanner(System.in);
        Node currentNode = binaryTree.root;

        while (true) {
            System.out.println("Please select an option:");
            System.out.println("P: Play the game");
            System.out.println("L: Load another game file");
            System.out.println("D: Display the binary tree");
            System.out.println("H: Help information");
            System.out.println("X: Exit the program");

            String option = scanner.nextLine().toUpperCase();

            switch (option) {
                case "P":
                    playGame(currentNode, scanner);
                    break;
                case "L":
                    System.out.println("Please enter the path to the new game file:");
                    String fileName = scanner.nextLine();
                    binaryTree.buildTreeFromFile(fileName);
                    currentNode = binaryTree.root;
                    break;
                case "D":
                    binaryTree.displayTree();
                    break;
                case "H":
                    System.out.println("This is a guessing game where you think of a number and the computer tries to guess it.");
                    System.out.println("You will be asked a series of yes or no questions to help the computer guess your number.");
                    System.out.println("To play the game, select the 'P' option from the menu.");
                    break;
                case "X":
                    System.exit(0);
                default:
                    System.out.println("Invalid option, please try again.");
                    break;
            }
        }
    }

    private static void playGame(Node currentNode, Scanner scanner) {
        while (currentNode != null) {
            System.out.println(currentNode.data);
            if (currentNode.left == null && currentNode.right == null) {
                break;
            }
            String answer = scanner.nextLine().toLowerCase();
            if (answer.equals("yes") && currentNode.left != null) {
                currentNode = currentNode.left;
            } else if (answer.equals("no") && currentNode.right != null) {
                currentNode = currentNode.right;
            } else {
                System.out.println("Invalid answer, please try again.");
            }
        }
    }
}
