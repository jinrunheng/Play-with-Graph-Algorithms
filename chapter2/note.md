## 《玩转图论算法》第二章学习笔记

#### 2-1 图的分类

<img src="https://tva1.sinaimg.cn/large/008eGmZEgy1gmlcepullwj30wo0r4dm4.jpg" alt="image-20210112234558310" style="zoom:33%;" align="left"/>

##### 图的组成：

- 顶点：Vertex

-  边：Edge

图实际上就是由顶点和边组成的

##### 图的分类：

- 无向图：Undirected Graph

  <img src="https://tva1.sinaimg.cn/large/008eGmZEgy1gmlcepullwj30wo0r4dm4.jpg" alt="image-20210112234558310" style="zoom:33%;" align="left"/>

- 有向图：Directed Graph

  <img src="https://tva1.sinaimg.cn/large/008eGmZEgy1gmlcorfkroj30wo0redmb.jpg" alt="image-20210112235342920" style="zoom:33%;" align="left"/>

图除了分为无向图和有向图，还可以按照边上是否带有权重信息来分为无权图和有权图

- 无权图

  <img src="https://tva1.sinaimg.cn/large/008eGmZEgy1gmlcorfkroj30wo0redmb.jpg" alt="image-20210112235342920" style="zoom:33%;" align="left"/>

- 有权图

  <img src="https://tva1.sinaimg.cn/large/008eGmZEgy1gmlcw0sn12j30xk0s6jyl.jpg" alt="image-20210113000216968" style="zoom:33%;" align="left"/>

所以综上述分析，图可以分为四类

1. 无向无权图
2. 有向无权图
3. 无向有权图
4. 有向有权图

#### 2-2 图的基本概念

无向无权图

- 两点相邻

- 点的邻边

- 路径 Path

- 环 Loop

- 自环边（自己到自己）

- 平行边

  在图论中，一般将没有自环边，没有平行边的图叫做**简单图**

- 联通分量

  图论中还有一个非常重要的概念：图中的所有节点不一定是全部相连的，一个图可能有多个**联通分量**

  联通分量指的是路径互相可达的点构成的集合

  <img src="https://tva1.sinaimg.cn/large/008eGmZEly1gmnjas0etrj313e0u0k5a.jpg" alt="image-20210114211512079" style="zoom:33%;" align="left"/>

  比如上面这一张图中有两个联通分量。

- 联通图的生成树

  联通图的生成树是指一个有环图去掉一些边以后生成的无环图，当然，树也是一种无环图

  <img src="https://tva1.sinaimg.cn/large/008eGmZEly1gmnjoq4ecyj31sy0u0k7x.jpg" alt="image-20210114212835121" style="zoom:33%;" align="left"/>

  并且如果图有`V`个顶点，那么联通图的生成树最少的边数则为`V - 1`  

- 一个顶点的度 degree

  对于无向图来说，一个顶点的度，就是这个顶点相邻的边数

#### 2-3 图的基本表示：邻接矩阵

无向无权图

<img src="https://tva1.sinaimg.cn/large/008eGmZEly1gmnk9yanxoj30q40ludk4.jpg" alt="image-20210114214848605" style="zoom:33%;" align="left"/>

对于这个无向无权图，我们可以用一个矩阵(方阵)来表示

<img src="https://tva1.sinaimg.cn/large/008eGmZEly1gmnkedripij30ly0m278r.jpg" alt="image-20210114215255303" style="zoom:33%;" align="left"/>

对于无向图来说，矩阵是关于主对角线对称的，并且简单图中不包含自环边，所以主对角线上的数字均为`0`

#### 2-4 更多图的方法

```java
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

```

#### 2-5 邻接表

回顾邻接矩阵

邻接矩阵

- 空间复杂度`O(V ^ 2)`
- 时间复杂度
  - 建图：`O(E)`
  - 查看两点是否相邻：`O(1)`
  - 求一个点的相邻节点：`O(V)`

改进空间：

1.  空间复杂度`O(V ^ 2)`
2. 求一个点的相邻节点：`O(V)`

##### 稀疏图与稠密图

<img src="https://tva1.sinaimg.cn/large/008eGmZEly1gmnmlnn2h2j30ha0mogww.jpg" alt="image-20210114230927442" style="zoom:33%;" align="left"/>

<img src="https://tva1.sinaimg.cn/large/008eGmZEly1gmnmmh2g56j30hy0kmh4t.jpg" alt="image-20210114231015319" style="zoom:33%;" align="left"/>



对于示例中的稀疏图来说，每个顶点的度最多可能只有6，7个；而第二个图，每个顶点都和其他的顶点相连，

第二个图不仅是稠密图也是一个**完全图**（完全图指的就是每个顶点都和其他顶点相连的图）

而稀疏图和稠密图的定义是没有固定的标准的

介绍稀疏图和稠密图的目的就是，我们可以清晰看出邻接矩阵表示一张图的缺点

对于一个稀疏图来说(实际上我们大部分处理的都是稀疏图)

我们如果使用邻接矩阵的方式来表示这个图

那么我们使用的空间复杂度会比较高，假设我有`3000`个顶点，那么就要耗费`9000000`的空间

而且求某一个节点的相邻节点还需要将所有的节点都遍历一边，即使有可能该节点的度只有`2`,`3`这样子

为了改进邻接矩阵这种表示方法的一些不足

我们会使用图的第二种表示方法：**邻接表**

<img src="https://tva1.sinaimg.cn/large/008eGmZEly1gmnn1re48tj31560hm0xb.jpg" alt="image-20210114232500858" style="zoom:33%;" align="left"/>



