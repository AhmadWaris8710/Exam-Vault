# Exam-Vault
Data Structures &amp; Algorithms - Phase 2 - ExamVault

ExamVault is a data-structure-driven online examination system built entirely in Java, designed to simulate the complete lifecycle of a real-world digital exam environment. Without relying on any external databases, third-party APIs, or backend frameworks, it delivers an experience comparable to large-scale platforms such as JEE, NEET, and SAT — powered purely by core algorithmic principles and hand-implemented data structures.
Every user-facing feature in ExamVault maps directly to a carefully selected data structure, making it not only a fully functional product but also a rigorous academic demonstration of when and why specific structures are chosen over alternatives.

Data Structures Implemented
Doubly Linked List — Question Navigation

All exam questions are stored as bidirectional nodes in a doubly linked list. Each node maintains pointers to both the preceding and succeeding question, enabling constant-time O(1) forward and backward traversal. Students can move freely through the exam in either direction without any performance penalty, regardless of their current position in the sequence.

Stack (LIFO) — Answer Management & Undo
Every answer selection is pushed onto a stack the moment it is recorded. When a student triggers an undo, the top entry is popped and the previous answer is instantly restored. This mirrors the undo behavior found in professional software and guarantees that no answer history is permanently lost during an active session.

ArrayList (Dynamic Array) — Bookmark Management
Bookmarked question IDs are stored in a hand-built dynamic array that doubles in capacity whenever it reaches its limit. Because the number of questions a student may wish to bookmark is unpredictable at runtime, a fixed-size array would be inappropriate. The ArrayList grows on demand and supports O(1) amortized insertion, making it ideal for fluid, user-driven bookmark management.

Binary Search Tree — Difficulty-Based Filtering
Questions are organized in a BST where easier questions branch left and harder questions branch right. This structural property allows ExamVault to retrieve all questions of a given difficulty level in O(log n) time, vastly outperforming the O(n) linear scan that a plain array or list would require. Students can filter by Easy, Medium, or Hard at any point during the exam with near-instant results.

Recursion — Score Calculation & Performance Analytics
Score calculation is handled entirely through recursive traversal of the question chain, with no iterative loops involved. The recursive function accumulates correct answers and computes accuracy metrics, demonstrating that recursion is a natural fit for data traversal tasks. Final statistics including total score and accuracy percentage are presented immediately upon submission.

Key Design Principle
ExamVault validates a fundamental principle of software engineering — that real-world systems demand the right data structure for each distinct problem rather than forcing a single structure to serve every purpose. Each of the five structures above was chosen based on the specific time-complexity requirements of the feature it powers.
