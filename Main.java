import java.util.Scanner;

class Question {

    int id;
    String text;
    String optionA;
    String optionB;
    String optionC;
    String optionD;
    char correctAnswer;
    String difficulty;
    char selectedAnswer;

    Question(int id, String text, String optionA, String optionB,
             String optionC, String optionD, char correctAnswer, String difficulty) {
        this.id = id;
        this.text = text;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
        this.difficulty = difficulty;
        this.selectedAnswer = '\0';
    }

}

class Node {

    Question question;
    Node prev;
    Node next;

    Node(Question question) {
        this.question = question;
        this.prev = null;
        this.next = null;
    }

}

class DoublyLinkedList {

    Node head;
    Node tail;
    int size;

    DoublyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    void addQuestion(Question q) {
        Node newNode = new Node(q);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    Node getHead() {
        return head;
    }

}

class AnswerRecord {

    int questionId;
    char previousAnswer;

    AnswerRecord(int questionId, char previousAnswer) {
        this.questionId = questionId;
        this.previousAnswer = previousAnswer;
    }

}

class AnswerStack {

    AnswerRecord[] data;
    int top;
    int capacity;

    AnswerStack(int capacity) {
        this.capacity = capacity;
        data = new AnswerRecord[capacity];
        top = -1;
    }

    void push(AnswerRecord record) {
        if (top < capacity - 1) {
            top++;
            data[top] = record;
        }
    }

    AnswerRecord pop() {
        if (top == -1) {
            return null;
        }
        AnswerRecord record = data[top];
        top--;
        return record;
    }

    boolean isEmpty() {
        return top == -1;
    }

}

class BookmarkList {

    int[] ids;
    int size;
    int capacity;

    BookmarkList() {
        capacity = 10;
        ids = new int[capacity];
        size = 0;
    }

    void grow() {
        capacity = capacity * 2;
        int[] newIds = new int[capacity];
        for (int i = 0; i < size; i++) {
            newIds[i] = ids[i];
        }
        ids = newIds;
    }

    void add(int questionId) {
        if (size == capacity) {
            grow();
        }
        ids[size] = questionId;
        size++;
    }

    void remove(int questionId) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (ids[i] == questionId) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return;
        }
        for (int i = index; i < size - 1; i++) {
            ids[i] = ids[i + 1];
        }
        size--;
    }

    boolean contains(int questionId) {
        for (int i = 0; i < size; i++) {
            if (ids[i] == questionId) {
                return true;
            }
        }
        return false;
    }

    void print() {
        if (size == 0) {
            System.out.println("  No bookmarks.");
            return;
        }
        System.out.print("  Bookmarked question IDs: ");
        for (int i = 0; i < size; i++) {
            System.out.print(ids[i]);
            if (i < size - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }

}

class BSTNode {

    Question question;
    BSTNode left;
    BSTNode right;

    BSTNode(Question question) {
        this.question = question;
        left = null;
        right = null;
    }

}

class DifficultyBST {

    BSTNode root;

    DifficultyBST() {
        root = null;
    }

    int difficultyValue(String difficulty) {
        if (difficulty.equals("Easy")) {
            return 1;
        }
        if (difficulty.equals("Medium")) {
            return 2;
        }
        return 3;
    }

    BSTNode insert(BSTNode node, Question q) {
        if (node == null) {
            return new BSTNode(q);
        }
        int qVal = difficultyValue(q.difficulty);
        int nodeVal = difficultyValue(node.question.difficulty);
        if (qVal <= nodeVal) {
            node.left = insert(node.left, q);
        } else {
            node.right = insert(node.right, q);
        }
        return node;
    }

    void add(Question q) {
        root = insert(root, q);
    }

    void collectByDifficulty(BSTNode node, String difficulty, Question[] result, int[] count) {
        if (node == null) {
            return;
        }
        int target = difficultyValue(difficulty);
        int nodeVal = difficultyValue(node.question.difficulty);
        if (target <= nodeVal) {
            collectByDifficulty(node.left, difficulty, result, count);
        }
        if (node.question.difficulty.equals(difficulty)) {
            result[count[0]] = node.question;
            count[0]++;
        }
        if (target >= nodeVal) {
            collectByDifficulty(node.right, difficulty, result, count);
        }
    }

    Question[] filterByDifficulty(String difficulty, int totalQuestions) {
        Question[] result = new Question[totalQuestions];
        int[] count = {0};
        collectByDifficulty(root, difficulty, result, count);
        Question[] trimmed = new Question[count[0]];
        for (int i = 0; i < count[0]; i++) {
            trimmed[i] = result[i];
        }
        return trimmed;
    }

}

class ScoreCalculator {

    int calculateScore(Node node) {
        if (node == null) {
            return 0;
        }
        int thisQuestion = 0;
        if (node.question.selectedAnswer == node.question.correctAnswer
                && node.question.selectedAnswer != '\0') {
            thisQuestion = 1;
        }
        return thisQuestion + calculateScore(node.next);
    }

}

public class Main {

    static DoublyLinkedList questionList = new DoublyLinkedList();
    static AnswerStack answerStack = new AnswerStack(1000);
    static BookmarkList bookmarks = new BookmarkList();
    static DifficultyBST bst = new DifficultyBST();
    static ScoreCalculator scoreCalc = new ScoreCalculator();
    static Node currentNode;
    static Scanner scanner = new Scanner(System.in);

    static void loadQuestions() {
        Question[] questions = {
                new Question(1,
                        "What is the time complexity of binary search?",
                        "O(n)", "O(log n)", "O(n^2)", "O(1)",
                        'B', "Easy"),
                new Question(2,
                        "Which data structure uses LIFO order?",
                        "Queue", "Array", "Stack", "Linked List",
                        'C', "Easy"),
                new Question(3,
                        "Which traversal visits root first?",
                        "Inorder", "Postorder", "Preorder", "Level order",
                        'C', "Medium"),
                new Question(4,
                        "What does a BST guarantee about the left child?",
                        "Greater than root", "Equal to root",
                        "Less than root", "Any value",
                        'C', "Medium"),
                new Question(5,
                        "Which structure is best for undo operations?",
                        "Queue", "Stack", "Array", "BST",
                        'B', "Easy"),
                new Question(6,
                        "What is the worst-case complexity of quicksort?",
                        "O(n log n)", "O(n)", "O(n^2)", "O(log n)",
                        'C', "Hard"),
                new Question(7,
                        "A doubly linked list node has how many pointers?",
                        "One", "Two", "Three", "Zero",
                        'B', "Easy"),
                new Question(8,
                        "Which sorting algorithm is stable by default?",
                        "Quick Sort", "Heap Sort", "Merge Sort", "Selection Sort",
                        'C', "Medium"),
                new Question(9,
                        "Dijkstra's algorithm is used for?",
                        "Sorting", "Shortest path", "Tree traversal", "Hashing",
                        'B', "Hard"),
                new Question(10,
                        "What is a complete binary tree?",
                        "All nodes have two children",
                        "All levels full except possibly last",
                        "Root has no children",
                        "Leaves are at level 1",
                        'B', "Hard")
        };
        for (Question q : questions) {
            questionList.addQuestion(q);
            bst.add(q);
        }
        currentNode = questionList.getHead();
    }

    static void displayCurrentQuestion() {
        Question q = currentNode.question;
        System.out.println("\n========================================");
        System.out.println("  Question " + q.id + " of " + questionList.size
                + "  [" + q.difficulty + "]"
                + (bookmarks.contains(q.id) ? "  [BOOKMARKED]" : ""));
        System.out.println("========================================");
        System.out.println("  " + q.text);
        System.out.println();
        System.out.println("  A) " + q.optionA);
        System.out.println("  B) " + q.optionB);
        System.out.println("  C) " + q.optionC);
        System.out.println("  D) " + q.optionD);
        System.out.println();
        if (q.selectedAnswer == '\0') {
            System.out.println("  Your answer: Not answered");
        } else {
            System.out.println("  Your answer: " + q.selectedAnswer);
        }
        System.out.println("----------------------------------------");
        System.out.println("  [N] Next   [P] Previous   [A/B/C/D] Answer");
        System.out.println("  [U] Undo   [B] Bookmark   [F] Filter by Difficulty");
        System.out.println("  [S] Submit Exam");
        System.out.println("----------------------------------------");
    }

    static void answerQuestion(char answer) {
        Question q = currentNode.question;
        answerStack.push(new AnswerRecord(q.id, q.selectedAnswer));
        q.selectedAnswer = answer;
        System.out.println("  Answer recorded: " + answer);
    }

    static void undoAnswer() {
        if (answerStack.isEmpty()) {
            System.out.println("  Nothing to undo.");
            return;
        }
        AnswerRecord record = answerStack.pop();
        Node node = questionList.getHead();
        while (node != null) {
            if (node.question.id == record.questionId) {
                node.question.selectedAnswer = record.previousAnswer;
                System.out.println("  Undo done. Question " + record.questionId
                        + " restored to: "
                        + (record.previousAnswer == '\0' ? "Not answered" : "" + record.previousAnswer));
                return;
            }
            node = node.next;
        }
    }

    static void toggleBookmark() {
        int id = currentNode.question.id;
        if (bookmarks.contains(id)) {
            bookmarks.remove(id);
            System.out.println("  Bookmark removed for Question " + id);
        } else {
            bookmarks.add(id);
            System.out.println("  Question " + id + " bookmarked.");
        }
    }

    static void filterByDifficulty() {
        System.out.print("  Enter difficulty (Easy / Medium / Hard): ");
        String diff = scanner.nextLine().trim();
        if (!diff.equals("Easy") && !diff.equals("Medium") && !diff.equals("Hard")) {
            System.out.println("  Invalid input. Use Easy, Medium, or Hard.");
            return;
        }
        Question[] filtered = bst.filterByDifficulty(diff, questionList.size);
        System.out.println("\n  --- " + diff + " Questions ---");
        if (filtered.length == 0) {
            System.out.println("  No questions found.");
            return;
        }
        for (Question q : filtered) {
            String answered = q.selectedAnswer == '\0' ? "Not answered" : "" + q.selectedAnswer;
            System.out.println("  Q" + q.id + ": " + q.text + "  [Answer: " + answered + "]");
        }
    }

    static void submitExam() {
        int score = scoreCalc.calculateScore(questionList.getHead());
        int total = questionList.size;
        double accuracy = ((double) score / total) * 100;
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("           EXAM RESULTS");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println("  Total Questions : " + total);
        System.out.println("  Correct Answers : " + score);
        System.out.println("  Wrong / Skipped : " + (total - score));
        System.out.printf("  Accuracy        : %.1f%%%n", accuracy);
        System.out.println();
        System.out.print("  Bookmarked Questions: ");
        bookmarks.print();
        System.out.println("\n  Thank you for using ExamVault!");
    }

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("    Welcome to ExamVault");
        System.out.println("    Intelligent Online Examination");
        System.out.println("╚══════════════════════════════════════╝");
        loadQuestions();
        boolean running = true;
        while (running) {
            displayCurrentQuestion();
            System.out.print("  Enter choice: ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("N")) {
                if (currentNode.next != null) {
                    currentNode = currentNode.next;
                } else {
                    System.out.println("  You are on the last question.");
                }
            } else if (input.equals("P")) {
                if (currentNode.prev != null) {
                    currentNode = currentNode.prev;
                } else {
                    System.out.println("  You are on the first question.");
                }
            } else if (input.equals("A") || input.equals("B")
                    || input.equals("C") || input.equals("D")) {
                answerQuestion(input.charAt(0));
            } else if (input.equals("U")) {
                undoAnswer();
            } else if (input.equals("B")) {
                toggleBookmark();
            } else if (input.equals("F")) {
                filterByDifficulty();
            } else if (input.equals("S")) {
                submitExam();
                running = false;
            } else {
                System.out.println("  Invalid choice. Try again.");
            }
        }
        scanner.close();
    }

}