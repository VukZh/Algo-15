package graphkosarajuapp;

public class GraphKosarajuApp {

    public static void main(String[] args) {

        // инициализация G - исходного графа
//        Graph G = new Graph();
//
//        G.set(0, 0, -1); // ввод графа (небезопасный метод - вершины с 0, связи с 0, без проверки связей на несуществующие вершины; -1 - нет исходящих ребер)
//        G.set(1, 0, 2);
//        G.set(2, 0, 3);
//        G.set(3, 0, 4);
//        G.set(4, 0, 1);
//        G.set(5, 0, 3);
//        G.set(5, 1, 6);
//        G.set(6, 0, 8);
//        G.set(7, 0, 5);
//        G.set(7, 1, 6);
//        G.set(7, 2, 9);
//        G.set(8, 0, -1);
//        G.set(9, 0, -1);
//
//        G.displayGraph(); // вывод графа
//        G.displayGraphR(); // вывод обращенного графа
//        G.kosaraju(); // Алгоритм Косарайю
//        G.displayComponent(); // вывод компонент (вершина - номер ее компоненты)
//        
//        // проверка для обращенного графа
//        System.out.println("---");
//        G.reverseGraph();
//        G.kosaraju();
//        G.displayComponent();
//        
//        
//        System.out.println("-------");


/////   добавлен конструктор с параметрами и улучшен метод заполения графа

        Graph G1 = new Graph(10);        
//      заполняем граф
        G1.setArr(1,2); // 1 вершина уходит на 2
        G1.setArr(2,3);
        G1.setArr(3,4);
        G1.setArr(4,1);
        G1.setArr(5,3,6); // 5 вершина уходит на 3 и 6
        G1.setArr(6,8);
        G1.setArr(7,5,6,9);
        G1.displayGraph(); // вывод массива векторов смежности
        G1.kosaraju(); // Алгоритм Косарайю
        G1.displayComponent(); // вывод компонент (вершина - номер ее компоненты)
    }

}
