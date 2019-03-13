package graphkosarajuapp;

public class Graph {

    private DArray<DArray<Integer>> G; // граф
    private DArray<DArray<Integer>> H; // обратный граф
    private DArray<Boolean> visited; // массив помеченных вершин
    private DArray<Integer> order; // массив найденных вершин - очередь для DFS1
    private int orderSize; // размер массива посещенных вершин DFS1
    private DArray<Integer> component;
    private int componentInd; // индекс компонента

    Graph() {
        G = new DArray<>();
    }

    public void set(int g_i, int el_i, int r) { // установка для матрицы вектора смежности (g_i - вершина, el_i индекс массива вершин куда уходят ребра, r - вершины на которые можно уйти с g_i)
        DArray<Integer> tmp;
        if (el_i == 0) {
            tmp = new DArray<>();
        } else {
            tmp = G.get(g_i);
        }
        tmp.add(el_i, r);
        G.add(g_i, tmp);
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

    public int get(int g_i, int el_i) { // получение вершины из матрицы вектора смежности (g_i - вершина с которой идет связь на нашу вершину, el_i индекс массива вершин для вершины g_i)
        DArray<Integer> tmp = G.get(g_i);
        return tmp.get(el_i);
    }

    private int getR(int g_i, int el_i) { // тоже для обращенного графа
        DArray<Integer> tmp = H.get(g_i);
        return tmp.get(el_i);
    }

    private int sizeV() { // число вершин графа
        return G.size();
    }

    private int sizeS(int v) { // число вершин, на которые есть связь с вершины v
        DArray<Integer> tmp = G.get(v);
        return tmp.size();
    }

    private int sizeSR(int v) { // тоже для обращенного графа
        DArray<Integer> tmp = H.get(v);
        return tmp.size();
    }

    private void reverse() { // инвертирование графа -> граф H
        H = new DArray<>();
        for (int i = 0; i < sizeV(); i++) { // поиск вершины i
            setR(i, 0, -1);
            int x = sizeSR(i); // индекс вставки в i-м векторе в новом графе
            for (int j = 0; j < sizeV(); j++) { // двойной цикл поиска вершины i в исходном графе
                DArray<Integer> tmp = G.get(j);
                for (int k = 0; k < sizeS(j); k++) {
                    if (tmp.get(k) == i) {
                        setR(i, x - 1, j);
                        x++;
                    }
                }
            }
        }
    }

    public void reverseGraph() { // как выше, но с заменой исходного графа на обращенный
        H = new DArray<>();
        for (int i = 0; i < sizeV(); i++) { // поиск вершины i
            setR(i, 0, -1);
            int x = sizeSR(i); // индекс вставки в i-м векторе в новом графе
            for (int j = 0; j < sizeV(); j++) { // двойной цикл поиска вершины i в исходном графе
                DArray<Integer> tmp = G.get(j);
                for (int k = 0; k < sizeS(j); k++) {
                    if (tmp.get(k) == i) {
                        setR(i, x - 1, j);
                        x++;
                    }
                }
            }
        }
        G = H; // замена исходного графа на обращенный
    }

    public void displayGraph() { // вывод графа
        System.out.println("Graph");
        for (int i = 0; i < sizeV(); i++) {
            System.out.print("i-" + i);
            for (int j = 0; j < sizeS(i); j++) {
                if (get(i, j) != -1) {
                    System.out.print(" > " + get(i, j));
                }
            }
            System.out.println("");
        }
    }

    public void displayGraphR() { // вывод обращенного графа
        reverse();
        System.out.println("Revers Graph");
        for (int i = 0; i < sizeV(); i++) {
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
        for (int i = 0; i < sizeV(); i++) {
            System.out.print("for " + i + "  ");
            System.out.print(component.get(i));
//            for (int j = 0; j < sizeS(i); j++) {
//                if (get(i, j) != -1) {
//                    System.out.print(" > " + get(i, j));
//                }
//            }
            System.out.println("");
        }
    }

// обычный рекурсивный поиск в глубину
//    public void dfs(int v) {
//        visited = new DArray<>();
//        for (int i = 0; i < sizeV(); i++) { // вначале все вершины не просмотрены 
//            visited.add(i, Boolean.FALSE);
//        }
//        dfs_req(v);
//    }
//
//    private void dfs_req(int v) {
//        visited.set(v, Boolean.TRUE);
//        System.out.println("--- " + v);
//        if (get(v, 0) == -1) { // нет выходов из вершины - ничего не ищем
//        } else {
//            for (int i = 0; i < sizeS(v); i++) { // проходим по вектору, ищем не посещенные вершины
//                int nextV = get(v, i);
//                if (!visited.get(nextV)) // вершина не просматривалась
//                {
//                    dfs_req(nextV);
//                }
//            }
//        }
//    }
    public void kosaraju() {

        visited = new DArray<>(); // посещенные узлы
        component = new DArray<>(); // массив вершин со значением компоненты
        order = new DArray<>(); // очередь для заполнения DFS1
        orderSize = 0; // масто вставки в очередь для заполнения DFS1

        for (int i = 0; i < sizeV(); i++) { // вначале посещенных нет и компоненты у вершин не известны
            visited.add(i, Boolean.FALSE);
            component.add(i, -1);
        }

        reverse(); // считаем обратный граф из исходного (dfs_1 работает с H)

        for (int i = 0; i < sizeV(); i++) { // формируем очередь выходов у обращенного графа
            if (!visited.get(i)) {
                dfs_1(i);
            }
        }
//        for (int i = 0; i < orderSize; i++) { // очередь после dfs_1
//            System.out.print(order.get(i) + " ");
//        }

        componentInd = 1;
        for (int i = orderSize - 1; i >= 0; i--) { // присваиваем компоненты
            if (component.get(order.get(i)) < 0) {
                dfs_2(order.get(i));
                componentInd++;
            }
        }
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
        component.set(v, componentInd);
        if (get(v, 0) == -1) {
        } else {
            for (int i = 0; i < sizeS(v); i++) {
                int nextV = get(v, i);
                if (component.get(nextV) < 0) {
                    dfs_2(nextV);
                }
            }
        }
    }

}
