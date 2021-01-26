package main;

public class Options {
    private static String[] genListOptions = {"Backtracker", "Random Prim's", "Hunt and Kill", "Binary Tree", "Sidewinder"};
    private static String[] solveListOptions = {"DFS", "BFS", "Optimised DFS", "A* (Manhattan distance)", "A* (Euclidian distance)"};
    private static int solveTrueAt = 4;
    private static String[] genClassNames  = {
        "RecursiveBacktrackingAlgorithm",
        "RandomPrims",
        "HuntAndKill",
        "BinaryTreeGen",
        "Sidewinder"
    };
    private static String[] solveClassNames = {
        "DepthFirstSearch",
        "BFS",
        "OptimizedDfs",
        "AStar",
        "AStar"
    };

    public static String[] getGenListOptions() {
        return genListOptions;
    }

    public static String getGenClassFromIndex(int index) {
        return "generator." + genClassNames[index];
    }

    public static String[] getSolverListOptions() {
        return solveListOptions;
    }

    public static String getSolveClassFromIndex(int index) {
        return "solver." + solveClassNames[index];
    }

    public static boolean isConstructorValTrue(int index) {
        return index == solveTrueAt;
    }
}
