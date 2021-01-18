package chapter2.section8;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeSet;

public class AdjSet {
    // 图的顶点
    private int V;
    // 图的边
    private int E;
    private TreeSet<Integer>[] adj;

    public AdjSet(String fileName) {
        File file = new File(fileName);
        try (Scanner scanner = new Scanner(file)) {
            V = scanner.nextInt();
            // 图中的顶点个数必须是非负数
            if (V < 0) {
                throw new IllegalArgumentException("V must be non-negative");
            }
            adj = new TreeSet[V];
            for (int i = 0; i < V; i++) {
                adj[i] = new TreeSet<>();
            }
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
                // 判断是否有平行边,判断是否有平行边需要遍历整个链表
                if (adj[a].contains(b)) {
                    throw new IllegalArgumentException("Parallel Edges are Detected!");
                }
                adj[a].add(b);
                adj[b].add(a);
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
        return adj[v].contains(w);
    }

    // 和v这个顶点相邻的顶点
    // 屏蔽实现细节，返回Iterable接口
    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    // 返回一个顶点的度
    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
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
            sb.append(String.format("%d : ",i));
            for (int w : adj[i]) {
                sb.append(String.format("%d ", w));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        AdjSet adjSet = new AdjSet("g.txt");
        System.out.println(adjSet);
    }
}

