package com.chenfu.chess;

import java.util.Map;

public class ChessBoard {
    public final int BOARD_WIDTH = 9, BOARD_HEIGHT = 10;
    public Map<String, ChessPiece> pieces;
    public char player = 'r';
    private ChessPiece[][] cells = new ChessPiece[BOARD_HEIGHT][BOARD_WIDTH];

    public boolean isInside(int[] position) {
        return isInside(position[0], position[1]);
    }

    public boolean isInside(int x, int y) {
        return !(x < 0 || x >= BOARD_HEIGHT
                || y < 0 || y >= BOARD_WIDTH);
    }

    public boolean isEmpty(int[] position) {
        return isEmpty(position[0], position[1]);
    }

    public boolean isEmpty(int x, int y) {
        return isInside(x, y) && cells[x][y] == null;
    }


    public boolean update(ChessPiece chessPiece) {
        int[] pos = chessPiece.position;
        cells[pos[0]][pos[1]] = chessPiece;
        return true;
    }

    public ChessPiece updatePiece(String key, int[] newPos) {
        ChessPiece orig = pieces.get(key);
        ChessPiece inNewPos = getPiece(newPos);
        /* If the new slot has been taken by another piece, then it will be killed.*/
        if (inNewPos != null)
            pieces.remove(inNewPos.key);
        /* Clear original slot and updatePiece new slot.*/
        int[] origPos = orig.position;
        cells[origPos[0]][origPos[1]] = null;
        cells[newPos[0]][newPos[1]] = orig;
        orig.position = newPos;
        player = (player == 'r') ? 'b' : 'r';
        return inNewPos;
    }

    public boolean backPiece(String key) {
        int[] origPos = pieces.get(key).position;
        cells[origPos[0]][origPos[1]] = pieces.get(key);
        return true;
    }

    public ChessPiece getPiece(int[] pos) {
        return getPiece(pos[0], pos[1]);
    }

    public ChessPiece getPiece(int x, int y) {
        return cells[x][y];
    }
}