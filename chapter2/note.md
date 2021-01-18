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



#### 2-6 邻接表的实现

邻接表的本质是一个链表数组，为图中每个顶点都开辟一个链表，链表中存储的是与其相邻的顶点。

邻接表的实现：

```java
package chapter2.section6;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class AdjList {
    // 图的顶点
    private int V;
    // 图的边
    private int E;
    private LinkedList<Integer>[] adj;

    public AdjList(String fileName) {
        File file = new File(fileName);
        try (Scanner scanner = new Scanner(file)) {
            V = scanner.nextInt();
            // 图中的顶点个数必须是非负数
            if (V < 0) {
                throw new IllegalArgumentException("V must be non-negative");
            }
            adj = new LinkedList[V];
            for (int i = 0; i < V; i++) {
                adj[i] = new LinkedList<>();
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
                // 判断是否有平行边
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
    public LinkedList<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
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
            sb.append(String.format("%d : ",i));
            for (int w : adj[i]) {
                sb.append(String.format("%d ", w));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        AdjList adjList = new AdjList("g.txt");
        System.out.println(adjList);
    }
}
```

程序运行输出结果为：

```
V = 7, E = 9
0 : 1 3 
1 : 0 2 6 
2 : 1 3 5 
3 : 0 2 4 
4 : 3 5 
5 : 2 4 6 
6 : 1 5 
```

不过我们的代码在性能和实现度上仍然有可以改进的地方

#### 2-7 邻接表的问题和改进

我们来分析下我们代码的时间复杂度与空间复杂度：

- 空间复杂度：`O(V + E)`

  因为我们为每个顶点都开辟了一个链表，消耗了`O(V)`的空间；每个链表都存储了与之相邻的顶点，这个空间复杂度为`O(2 * E)`,因为两个顶点都要存储对方各一次，所以要乘以系数`2`;不过最终的额外空间复杂度则忽略掉常数系数，所以空间复杂度为:`O(V + E)`

- 时间复杂度：

  - 建图：`O(E * V)`
  - 查看两点是否相邻：`O(degree(v))`
  - 求一个点的相邻节点：`O(degree(v))`

这样看来，邻接表也是有它的性能瓶颈的，主要体现在**建图**与**查看两点是否相邻**这两个操作

但是，这两点是可以进行优化的，本质上我们需要解决的问题是：**快速查看两点是否相邻**

这是一个典型的数据结构问题，我们不应该使用链表，替换链表的数据结构应该为：

- `HashSet`，使用哈希表那么快速查看两点是否相邻就会优化到`O(1)`的时间复杂度
- 或
- `TreeSet`，使用红黑树那么快速查看两点是否相邻就会优化到`O(logv)`到时间复杂度

#### 2-8 实现邻接表的改进

那么我们应该使用哈希表还是红黑树来代替链表这种数据结构呢？

通常，我们应该使用哈希表，原因是因为哈希表的增删改查操作均为`O(1)`，在性能上是首选。

不过，红黑树能够保证元素的**顺序性**，另外红黑树也要比哈希表更节省空间。

本课程使用的是红黑树来替换链表这种数据结构。

代码如下：

```java
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
```

#### 2-9 图的基本表示的比较

<img src="https://tva1.sinaimg.cn/large/008eGmZEgy1gmsitp0hmqj30wm0d2djr.jpg" alt="image-20210119044627845" style="zoom: 50%;" align="left"/>

可以看到使用红黑树作为底层实现的邻接表的实现大大优于前两者，无论是稀疏图还是稠密图，这种实现都有巨大的优势

 





