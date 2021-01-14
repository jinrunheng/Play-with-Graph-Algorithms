package chapter2.section4;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AdjMatrix {
    // 图的顶点
    private int V;
    // 图的边
    private int E;
    private int[][] adj;

    public AdjMatrix(String fileName) {
        File file = new File(fileName);
        try (Scanner scanner = new Scanner(file)) {
            V = scanner.nextInt();
            // 图中的顶点个数必须是非负数
            if (V < 0) {
                throw new IllegalArgumentException("V must be non-negative");
            }
            adj = new int[V][V];

            E = scanner.nextInt();
            // 图中的边的个数必须是非负数
            if (E < 0) {
                throw new IllegalArgumentException("E must be non-negative");
            }
            for (int i = 0; i < E; i++) {
                int a = scanner.nextInt();
                validateVertex(a);
                int b = scanner.nextInt();
                validateVertex(b);

                // 判断是否有自环边
                if (a == b) {
                    throw new IllegalArgumentException("Self Loop is Detected!");
                }
                // 判断是否有平行边
                if (adj[a][b] == 1) {
                    throw new IllegalArgumentException("Parallel Edges are Detected!");
                }
                adj[a][b] = 1;
                adj[b][a] = 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public boolean hasEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return adj[v][w] == 1;
    }

    // 和v这个顶点相邻的顶点
    public ArrayList<Integer> adj(int v) {
        validateVertex(v);
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            if (adj[v][i] == 1) {
                res.add(i);
            }
        }
        return res;
    }

    // 返回一个顶点的度
    public int degree(int v) {
        return adj(v).size();
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex" + V + "is invalid");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("V = %d, E = %d\n", V, E));
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                sb.append(String.format("%d ", adj[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        chapter2.section3.AdjMatrix adjMatrix = new chapter2.section3.AdjMatrix("g.txt");
        System.out.println(adjMatrix);
    }
}
