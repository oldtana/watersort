package main.com;
import main.com.Move;
import java.util.*;


public class State {
    int[][] tubes;
    List<Move> moves;
    public String getKey() {
        StringBuilder key = new StringBuilder();
        for (int[] tube : tubes) {
            for (int color : tube) {
                key.append(color).append(",");
            }
            key.append("|");
        }
        return key.toString();
    }
    public State(int[][] tubes,List<Move> moves){
        this.moves=moves;
        this.tubes=tubes;
    }
    public State addMove(Move move) {
        if (!canPour(tubes[move.fromTube], tubes[move.toTube])) {
            return null;
        }
        int[][] newTubes = new int[tubes.length][];
        for (int i = 0; i < tubes.length; i++) {
            newTubes[i] = tubes[i].clone();
        }

        List<Move> newMoves = new ArrayList<>(moves);
        newMoves.add(move);

        // ВЫПОЛНЯЕМ ПЕРЕЛИВАНИЕ
        performPour(newTubes[move.fromTube], newTubes[move.toTube]);

        return new State(newTubes, newMoves);
    }

    private void performPour(int[] from, int[] to) {
        int fromIndex = -1;
        int color = 0;
        for (int i = from.length - 1; i >= 0; i--) {
            if (from[i] != 0) {
                color = from[i];
                fromIndex = i;
                break;
            }
        }
        int toIndex = -1;
        for (int i = 0; i < to.length; i++) {
            if (to[i] == 0) {
                toIndex = i;
                break;
            }
        }
        from[fromIndex] = 0;
        to[toIndex] = color;
    }

    private boolean canPour(int[] from, int[] to) {
        boolean fromEmpty = true;
        for (int color : from) {
            if (color != 0) {
                fromEmpty = false;
                break;
            }
        }
        if (fromEmpty) return false;

        boolean toFull = true;
        for (int color : to) {
            if (color == 0) {
                toFull = false;
                break;
            }
        }
        if (toFull) return false;

        int fromColor = 0;
        for (int i = from.length - 1; i >= 0; i--) {
            if (from[i] != 0) {
                fromColor = from[i];
                break;
            }
        }

        int toColor = 0;
        for (int i = to.length - 1; i >= 0; i--) {
            if (to[i] != 0) {
                toColor = to[i];
                break;
            }
        }
        return toColor == 0 || fromColor == toColor;
    }
    public boolean isGoal() {
        for (int[] tube : tubes) {
            if (!isTubeComplete(tube)) {
                return false;
            }
        }
        return true;
    }
    private boolean isTubeComplete(int[] tube) {
        // Пробирка завершена если:
        // 1. Полностью пустая
        // 2. Полностью заполнена одним цветом

        if (tube[0] == 0 && tube[1] == 0) return true; // [0,0] - ок

        // Если есть хоть один цвет - проверяем что ВСЕ цвета одинаковые
        int firstColor = 0;
        for (int color : tube) {
            if (color != 0) {
                if (firstColor == 0) {
                    firstColor = color;
                } else if (color != firstColor) {
                    return false; // нашли другой цвет!
                }
            }
        }

        // Проверяем что пробирка либо пустая, либо полная
        boolean hasZero = false;
        boolean hasColor = false;
        for (int color : tube) {
            if (color == 0) hasZero = true;
            else hasColor = true;
        }

        // Нельзя чтобы были и нули и цвета одновременно!
        return !(hasZero && hasColor);
    }
}
