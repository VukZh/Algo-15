package graphkosarajuapp;

public class Graph {

    private final int[][] G; // граф        
    private DArray<DArray<Integer>> H; // обратный граф
    private DArray<Boolean> visited; // массив помеченных вершин
    private DArray<Integer> order; // массив найденных вершин - очередь для DFS1
    private int orderSize; // размер массива посещенных вершин DFS1
    private DArray<Integer> component;
    private int componentInd; // индекс компонента

    Graph(int arr[][]) {
        G = arr;
    }

    private void setR(int g_i, int el_i, int r) { // тоже для обращенного графа
        DArray<Integer> tmp;
        if (el_i == 0) {
            tmp = new DArray<>();
        } else {
            tmp = H.get(g_i);
        }
        tmp.add(el_i, r);
        H.add(g_i, tmp);
    }

    private int getR(int g_i, int el_i) { // тоже для обращенного графа
        DArray<Integer> tmp = H.get(g_i);
        return tmp.get(el_i);
    }

    private int sizeSR(int v) { // тоже для обращенного графа
        DArray<Integer> tmp = H.get(v);
        return tmp.size();
    }

    private void reverse() { // инвертирование графа -> граф H
        H = new DArray<>();
        for (int i = 0; i < G.length; i++) { // поиск вершины i
            setR(i, 0, -1);
            int x = sizeSR(i); // индекс вставки в i-м векторе в новом графе
            for (int j = 0; j < G.length; j++) { // двойной цикл поиска вершины i в исходном графе
                DArray<Integer> tmp = new DArray<>();
                for (int k = 0; k < G[j].length; k++) {
                    tmp.add(k, G[j][k]);
                }

                for (int k = 0; k < G[j].length; k++) {
                    if (tmp.get(k) == i) {
                        setR(i, x - 1, j);
                        x++;
                    }
                }
            }
        }
    }

    public void displayGraph() { // вывод графа
        System.out.println("Graph");
        for (int i = 0; i < G.length; i++) {
            System.out.print("i-" + i);
            for (int j = 0; j < G[i].length; j++) {
                System.out.print(" > " + G[i][j]);
            }
            System.out.println("");
        }
    }

    public void displayGraphR() { // вывод обращенного графа
        reverse();
        System.out.println("Revers Graph");
        for (int i = 0; i < G.length; i++) {
            System.out.print("i-" + i);
            for (int j = 0; j < sizeSR(i); j++) {
                if (getR(i, j) != -1) {
                    System.out.print(" > " + getR(i, j));
                }
            }
            System.out.println("");
        }
    }

    public void displayComponent() { // вывод компонент (вершина - номер ее компоненты)
        System.out.println("");
        System.out.println("Component");
        for (int i = 0; i < G.length; i++) {
            System.out.print("for " + i + "  ");
            System.out.print(component.get(i));
            System.out.println("");
        }
    }

    public int[] kosaraju() {
        int[] res = new int[G.length];
        visited = new DArray<>(); // посещенные узлы
        component = new DArray<>(); // массив вершин со значением компоненты
        order = new DArray<>(); // очередь для заполнения DFS1
        orderSize = 0; // масто вставки в очередь для заполнения DFS1

        for (int i = 0; i < G.length; i++) { // вначале посещенных нет и компоненты у вершин не известны
            visited.add(i, Boolean.FALSE);
            component.add(i, -1);
        }

        reverse(); // считаем обратный граф из исходного (dfs_1 работает с H)

        for (int i = 0; i < G.length; i++) { // формируем очередь выходов у обращенного графа
            if (!visited.get(i)) {
                dfs_1(i);
            }
        }

        componentInd = 1;
        for (int i = orderSize - 1; i >= 0; i--) { // присваиваем компоненты
            if (component.get(order.get(i)) < 0) {
                dfs_2(order.get(i));
                componentInd++;
            }
        }

        for (int i = 0; i < G.length; i++) {
            res[i] = component.get(i);
            System.out.println("--- " + component.get(i));
        }
        return res;
    }

    private void dfs_1(int v) {
        visited.set(v, Boolean.TRUE);
        if (getR(v, 0) == -1) { // нет выходов из вершины - ничего не ищем
        } else {
            for (int i = 0; i < sizeSR(v); i++) { // проходим по вектору, ищем не посещенные вершины
                int nextV = getR(v, i); // nextV - вершина из списка связанных с v
                if (!visited.get(nextV)) // вершина не просматривалась
                {
                    dfs_1(nextV);
                }
            }
        }
        order.add(orderSize, v);
        orderSize++;
    }

    private void dfs_2(int v) { // поиск DFS в очереди order из dfs_1, присвоение одинаковых компонент для связанных вершин
        component.add(v, componentInd);
        if (G[v].length != 0) {

            for (int i = 0; i < G[v].length; i++) {
                int nextV = G[v][i];
                if (component.get(nextV) < 0) {
                    dfs_2(nextV);
                }
            }
        }
    }
}
