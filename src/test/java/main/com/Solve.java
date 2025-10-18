package main.com;

import java.util.*;
import main.com.Move;
public class Solve {

    public List<Move> solve(int[][] initialTubes) {
        Queue<State> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        State initialState = new State(initialTubes, new ArrayList<>());
        queue.offer(initialState);
        visited.add(initialState.getKey());

        while (!queue.isEmpty()) {
            State current = queue.poll();
            if (current.isGoal()) {
                return current.moves;
            }
            for (int from = 0; from < current.tubes.length; from++) {
                for (int to = 0; to < current.tubes.length; to++) {
                    if (from == to) continue; // нельза в ту же пробирку

                    Move move = new Move(from, to);
                    State nextState = current.addMove(move);
                    if (nextState == null) {
                        continue;
                    }

                    String key = nextState.getKey();
                    if (!visited.contains(key)) {
                        visited.add(key);
                        queue.offer(nextState);
                    }
                }
            }
        }

        return null;
    }
}