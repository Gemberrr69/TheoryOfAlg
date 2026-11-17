package Lab_3;

// Дулгер-Шевченко Артьом Олексійович, Кн-31. Практична робота №3, варіант 7
// Граф: матриця суміжності, список суміжності, BFS, DFS, Дейкстра.

/*
 Матриця суміжності
       A   B   C   D   E   F
   A   0   4   2   0   0   0
   B   4   0   0   1   0   0
   C   2   0   0   0   3   0
   D   0   1   0   0   2   6
   E   0   0   3   2   0   1
   F   0   0   0   6   1   0

Список суміжності
A: B(4) C(2) 
B: A(4) D(1) 
C: A(2) E(3) 
D: B(1) E(2) F(6) 
E: C(3) D(2) F(1) 
F: D(6) E(1) 

BFS
Крок 1: беремо A, черга [B, C], відвідані [A, B, C]
Крок 2: беремо B, черга [C, D], відвідані [A, B, C, D]
Крок 3: беремо C, черга [D, E], відвідані [A, B, C, D, E]
Крок 4: беремо D, черга [E, F], відвідані [A, B, C, D, E, F]
Крок 5: беремо E, черга [F], відвідані [A, B, C, D, E, F]
Крок 6: беремо F, черга [], відвідані [A, B, C, D, E, F]
Порядок BFS: [A, B, C, D, E, F]
Відстані в ребрах від A: A=0 B=1 C=1 D=2 E=2 F=3

DFS
Вхід у вершину: A
Вхід у вершину: B
Вхід у вершину: D
Вхід у вершину: E
Вхід у вершину: C
Вхід у вершину: F
Порядок DFS: [A, B, D, E, C, F]

Дейкстра
Крок 1: обрали A (0), оновлено: B=4 C=2
Крок 2: обрали C (2), оновлено: E=5
Крок 3: обрали B (4), оновлено: D=5
Крок 4: обрали D (5), оновлено: F=11
Крок 5: обрали E (5), оновлено: F=6
Крок 6: обрали F (6), оновлено: -
Найкоротша відстань до F: 6
Шлях: [A, C, E, F]
*/

import java.util.*;

public class Main {

    //UTF-8 текст
    static void setupUtf8Console() {
        if (System.getProperty("os.name", "").toLowerCase().contains("win")) {
            try {
                new ProcessBuilder("cmd", "/c", "chcp 65001 >nul").inheritIO().start().waitFor();
            } catch (Exception ignored) {
            }
        }
        System.setOut(new java.io.PrintStream(new java.io.FileOutputStream(java.io.FileDescriptor.out), true, java.nio.charset.StandardCharsets.UTF_8));
        System.setErr(new java.io.PrintStream(new java.io.FileOutputStream(java.io.FileDescriptor.err), true, java.nio.charset.StandardCharsets.UTF_8));
    }


    private static final int N = 6;   // вершини A..F

    public static void main(String[] args) {
        setupUtf8Console();

        int[][] matrix = new int[N][N];

        // Ребра варіанта 7 (A=0, B=1, C=2, D=3, E=4, F=5)
        addEdge(matrix, 0, 1, 4);   // A-B 4
        addEdge(matrix, 0, 2, 2);   // A-C 2
        addEdge(matrix, 1, 3, 1);   // B-D 1
        addEdge(matrix, 2, 4, 3);   // C-E 3
        addEdge(matrix, 3, 4, 2);   // D-E 2
        addEdge(matrix, 3, 5, 6);   // D-F 6
        addEdge(matrix, 4, 5, 1);   // E-F 1

        printMatrix(matrix);
        printList(matrix);

        System.out.println("\nBFS");
        bfs(matrix, 0);

        System.out.println("\nDFS");
        dfs(matrix, 0);

        System.out.println("\nДейкстра");
        dijkstra(matrix, 0, 5);
    }

    static char name(int v) {
        return (char) ('A' + v);
    }

    // Неорієнтований граф: матриця симетрична
    public static void addEdge(int[][] matrix, int a, int b, int weight) {
        matrix[a][b] = weight;
        matrix[b][a] = weight;
    }

    public static void printMatrix(int[][] matrix) {
        System.out.println("\nМатриця суміжності");
        System.out.print("    ");
        for (int i = 0; i < N; i++) {
            System.out.printf("%4c", name(i));
        }
        System.out.println();
        for (int i = 0; i < N; i++) {
            System.out.printf("%4c", name(i));
            for (int j = 0; j < N; j++) {
                System.out.printf("%4d", matrix[i][j]);
            }
            System.out.println();
        }
    }

    public static void printList(int[][] matrix) {
        System.out.println("\nСписок суміжності");
        for (int i = 0; i < N; i++) {
            System.out.print(name(i) + ": ");
            for (int j = 0; j < N; j++) {
                if (matrix[i][j] > 0) {
                    System.out.print(name(j) + "(" + matrix[i][j] + ") ");
                }
            }
            System.out.println();
        }
    }

    // BFS: показує чергу і відвідані після кожного кроку
    public static void bfs(int[][] matrix, int start) {
        boolean[] visited = new boolean[N];
        int[] distance = new int[N];
        Arrays.fill(distance, -1);

        Queue<Integer> queue = new LinkedList<>();
        List<Character> order = new ArrayList<>();

        visited[start] = true;
        distance[start] = 0;
        queue.add(start);

        int step = 0;
        while (!queue.isEmpty()) {
            int current = queue.poll();
            order.add(name(current));

            for (int i = 0; i < N; i++) {
                if (matrix[current][i] > 0 && !visited[i]) {
                    visited[i] = true;
                    distance[i] = distance[current] + 1;
                    queue.add(i);
                }
            }

            step++;
            List<Character> q = new ArrayList<>();
            for (int v : queue) q.add(name(v));
            List<Character> vis = new ArrayList<>();
            for (int v = 0; v < N; v++) if (visited[v]) vis.add(name(v));
            System.out.println("Крок " + step + ": беремо " + name(current)
                    + ", черга " + q + ", відвідані " + vis);
        }

        System.out.println("Порядок BFS: " + order);
        StringBuilder sb = new StringBuilder();
        for (int v = 0; v < N; v++) {
            sb.append(name(v)).append('=').append(distance[v]).append(' ');
        }
        System.out.println("Відстані в ребрах від " + name(start) + ": " + sb.toString().trim());
    }

    // DFS: сусіди за алфавітом (за зростанням індексу)
    public static void dfs(int[][] matrix, int start) {
        boolean[] visited = new boolean[N];
        List<Character> order = new ArrayList<>();
        dfsRecursive(matrix, start, visited, order);
        System.out.println("Порядок DFS: " + order);
    }

    public static void dfsRecursive(int[][] matrix, int current, boolean[] visited, List<Character> order) {
        visited[current] = true;
        order.add(name(current));
        System.out.println("Вхід у вершину: " + name(current));
        for (int i = 0; i < N; i++) {
            if (matrix[current][i] > 0 && !visited[i]) {
                dfsRecursive(matrix, i, visited, order);
            }
        }
    }

    // Дейкстра: на кожному кроці показує обрану вершину і оновлення
    public static void dijkstra(int[][] matrix, int start, int target) {
        int[] dist = new int[N];
        int[] prev = new int[N];
        boolean[] visited = new boolean[N];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);
        dist[start] = 0;

        for (int step = 1; step <= N; step++) {
            int current = -1;
            int minDist = Integer.MAX_VALUE;
            for (int j = 0; j < N; j++) {
                if (!visited[j] && dist[j] < minDist) {
                    minDist = dist[j];
                    current = j;
                }
            }
            if (current == -1) break;
            visited[current] = true;

            StringBuilder updates = new StringBuilder();
            for (int k = 0; k < N; k++) {
                if (matrix[current][k] > 0 && !visited[k]
                        && dist[current] + matrix[current][k] < dist[k]) {
                    dist[k] = dist[current] + matrix[current][k];
                    prev[k] = current;
                    updates.append(name(k)).append('=').append(dist[k]).append(' ');
                }
            }
            System.out.println("Крок " + step + ": обрали " + name(current)
                    + " (" + dist[current] + "), оновлено: "
                    + (updates.length() == 0 ? "-" : updates.toString().trim()));
        }

        if (dist[target] == Integer.MAX_VALUE) {
            System.out.println("Вершина " + name(target) + " недосяжна з " + name(start));
            return;
        }

        // Відновлення шляху
        List<Character> path = new ArrayList<>();
        for (int at = target; at != -1; at = prev[at]) {
            path.add(name(at));
        }
        Collections.reverse(path);

        System.out.println("Найкоротша відстань до " + name(target) + ": " + dist[target]);
        System.out.println("Шлях: " + path);
    }
}
