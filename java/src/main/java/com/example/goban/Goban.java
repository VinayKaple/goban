package com.example.goban;

import java.util.List;

public class Goban {
    private final List<String> goban;

    public Goban(List<String> goban) {
        this.goban = goban;
    }

    private static final int[][] DIRECTIONS = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1}
    };

    public Status getStatus(int x, int y) {
        if (goban == null || goban.isEmpty() || x < 0 || y < 0 || y >= goban.size() || x >= goban.getFirst().length()) {
            return Status.OUT;
        }
        char stone = goban.get(y).charAt(x);
        return switch (stone) {
            case '.' -> Status.EMPTY;
            case 'o' -> Status.WHITE;
            case '#' -> Status.BLACK;
            default -> throw new IllegalArgumentException("Unknown goban value " + stone);
        };
    }

    public boolean isTaken(int x, int y) {
        Status color = getStatus(x, y);

        if (color == Status.EMPTY || color == Status.OUT) return false;

        boolean[][] visited = new boolean[goban.size()][goban.getFirst().length()];
        return !groupHasLiberty(x, y, color, visited);
    }

    private boolean groupHasLiberty(int x, int y, Status color, boolean[][] visited) {
        if (getStatus(x, y) != color || visited[y][x]) {
            return false;
        }

        visited[y][x] = true;



        for (int[] d : DIRECTIONS) {
            int nx = x + d[0];
            int ny = y + d[1];

            Status neighbor = getStatus(nx, ny);

            if (neighbor == Status.EMPTY) {
                return true; // liberty found
            }

            if (neighbor == color) {
                if (groupHasLiberty(nx, ny, color, visited)) {
                    return true;
                }
            }
        }

        return false;
    }
}
