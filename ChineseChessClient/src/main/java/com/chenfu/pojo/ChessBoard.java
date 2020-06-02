package com.chenfu.pojo;

import com.chenfu.utils.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChessBoard {

    public final int BOARD_WIDTH = 9, BOARD_HEIGHT = 10;
    public ChessPiece[][] chessPieceArray;
    public Map<String, ChessPiece> stringChessPieceMap;
    public char player;
    public boolean inverse = false;
    public boolean wait;

    public ChessBoard(char c) {
        initChessBoard(c);
    }

    public void initChessBoard(char c) {
        chessPieceArray = new ChessPiece[BOARD_HEIGHT][BOARD_WIDTH];
        player = c;
        stringChessPieceMap = new HashMap<>(16);
        stringChessPieceMap.put("bj0", new ChessPiece("bj0", new int[]{0, 0}));
        stringChessPieceMap.put("bm0", new ChessPiece("bm0", new int[]{0, 1}));
        stringChessPieceMap.put("bx0", new ChessPiece("bx0", new int[]{0, 2}));
        stringChessPieceMap.put("bs0", new ChessPiece("bs0", new int[]{0, 3}));
        stringChessPieceMap.put("bb0", new ChessPiece("bb0", new int[]{0, 4}));
        stringChessPieceMap.put("bs1", new ChessPiece("bs1", new int[]{0, 5}));
        stringChessPieceMap.put("bx1", new ChessPiece("bx1", new int[]{0, 6}));
        stringChessPieceMap.put("bm1", new ChessPiece("bm1", new int[]{0, 7}));
        stringChessPieceMap.put("bj1", new ChessPiece("bj1", new int[]{0, 8}));
        stringChessPieceMap.put("bp0", new ChessPiece("bp0", new int[]{2, 1}));
        stringChessPieceMap.put("bp1", new ChessPiece("bp1", new int[]{2, 7}));
        stringChessPieceMap.put("bz0", new ChessPiece("bz0", new int[]{3, 0}));
        stringChessPieceMap.put("bz1", new ChessPiece("bz1", new int[]{3, 2}));
        stringChessPieceMap.put("bz2", new ChessPiece("bz2", new int[]{3, 4}));
        stringChessPieceMap.put("bz3", new ChessPiece("bz3", new int[]{3, 6}));
        stringChessPieceMap.put("bz4", new ChessPiece("bz4", new int[]{3, 8}));
        stringChessPieceMap.put("rj0", new ChessPiece("rj0", new int[]{9, 0}));
        stringChessPieceMap.put("rm0", new ChessPiece("rm0", new int[]{9, 1}));
        stringChessPieceMap.put("rx0", new ChessPiece("rx0", new int[]{9, 2}));
        stringChessPieceMap.put("rs0", new ChessPiece("rs0", new int[]{9, 3}));
        stringChessPieceMap.put("rb0", new ChessPiece("rb0", new int[]{9, 4}));
        stringChessPieceMap.put("rs1", new ChessPiece("rs1", new int[]{9, 5}));
        stringChessPieceMap.put("rx1", new ChessPiece("rx1", new int[]{9, 6}));
        stringChessPieceMap.put("rm1", new ChessPiece("rm1", new int[]{9, 7}));
        stringChessPieceMap.put("rj1", new ChessPiece("rj1", new int[]{9, 8}));
        stringChessPieceMap.put("rp0", new ChessPiece("rp0", new int[]{7, 1}));
        stringChessPieceMap.put("rp1", new ChessPiece("rp1", new int[]{7, 7}));
        stringChessPieceMap.put("rz0", new ChessPiece("rz0", new int[]{6, 0}));
        stringChessPieceMap.put("rz1", new ChessPiece("rz1", new int[]{6, 2}));
        stringChessPieceMap.put("rz2", new ChessPiece("rz2", new int[]{6, 4}));
        stringChessPieceMap.put("rz3", new ChessPiece("rz3", new int[]{6, 6}));
        stringChessPieceMap.put("rz4", new ChessPiece("rz4", new int[]{6, 8}));
        if (c == 'r') {
            wait = false;
            updateAll(stringChessPieceMap);
        } else {
            wait = true;
            inverse = true;
            inverseBoard();
        }

    }

    public void clearchessBoard(){
        for (int i = 0; i <BOARD_HEIGHT; i++) {
            for (int j = 0; j <BOARD_WIDTH; j++) {
                chessPieceArray[i][j]=null;
            }
        }
        stringChessPieceMap.clear();
    }

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
        return isInside(x, y) && chessPieceArray[x][y] == null;
    }


    public boolean update(ChessPiece chessPiece) {
        int[] pos = chessPiece.position;
        chessPieceArray[pos[0]][pos[1]] = chessPiece;
        return true;
    }

    public void updateAll(Map<String, ChessPiece> stringChessPieceMap) {
        for (Map.Entry<String, ChessPiece> stringPieceEntry : stringChessPieceMap.entrySet())
            update(stringPieceEntry.getValue());
    }

    public ChessPiece updatePiece(String key, int[] newPos) {
        ChessPiece orig = stringChessPieceMap.get(key);
        ChessPiece inNewPos = getPiece(newPos);
        if (inNewPos != null) {
            stringChessPieceMap.remove(inNewPos.key);
        }
        int[] origPos = orig.position;
        chessPieceArray[origPos[0]][origPos[1]] = null;
        chessPieceArray[newPos[0]][newPos[1]] = orig;
        orig.position = newPos;
        return inNewPos;
    }

    public boolean backPiece(String key) {
        int[] origPos = stringChessPieceMap.get(key).position;
        chessPieceArray[origPos[0]][origPos[1]] = stringChessPieceMap.get(key);
        return true;
    }


    public void inverseBoard() {
        for (Map.Entry<String, ChessPiece> stringPieceEntry : stringChessPieceMap.entrySet()) {
            ChessPiece chessPiece = stringPieceEntry.getValue();
            int[] position = chessPiece.position;
            int[] inversePosition = {BOARD_HEIGHT - 1 - position[0], BOARD_WIDTH - 1 - position[1]};
            chessPiece.position = inversePosition;
            update(chessPiece);
        }
    }


 /*   public String saveBoard() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (chessPieceArray[i][j] != null) {
                    ChessPiece chessPiece = chessPieceArray[i][j];
                    String key = chessPiece.key;
                    switch (key) {
                        case "bj0":
                            stringBuilder.append(Integer.toHexString(1));
                            break;
                        case "bm0":
                            stringBuilder.append(Integer.toHexString(2));
                            break;
                        case "bx0":
                            stringBuilder.append(Integer.toHexString(3));
                            break;
                        case "bs0":
                            stringBuilder.append(Integer.toHexString(4));
                            break;
                        case "bb0":
                            stringBuilder.append(Integer.toHexString(5));
                            break;
                        case "bs1":
                            stringBuilder.append(Integer.toHexString(4));
                            break;
                        case "bx1":
                            stringBuilder.append(Integer.toHexString(3));
                            break;
                        case "bm1":
                            stringBuilder.append(Integer.toHexString(2));
                            break;
                        case "bj1":
                            stringBuilder.append(Integer.toHexString(1));
                            break;
                        case "bp0":
                            stringBuilder.append(Integer.toHexString(6));
                            break;
                        case "bp1":
                            stringBuilder.append(Integer.toHexString(6));
                            break;
                        case "bz0":
                            stringBuilder.append(Integer.toHexString(7));
                            break;
                        case "bz1":
                            stringBuilder.append(Integer.toHexString(7));
                            break;
                        case "bz2":
                            stringBuilder.append(Integer.toHexString(7));
                            break;
                        case "bz3":
                            stringBuilder.append(Integer.toHexString(7));
                            break;
                        case "bz4":
                            stringBuilder.append(Integer.toHexString(7));
                            break;
                        case "rj0":
                            stringBuilder.append(Integer.toHexString(8));
                            break;
                        case "rm0":
                            stringBuilder.append(Integer.toHexString(9));
                            break;
                        case "rx0":
                            stringBuilder.append(Integer.toHexString(10));
                            break;
                        case "rs0":
                            stringBuilder.append(Integer.toHexString(11));
                            break;
                        case "rb0":
                            stringBuilder.append(Integer.toHexString(12));
                            break;
                        case "rs1":
                            stringBuilder.append(Integer.toHexString(11));
                            break;
                        case "rx1":
                            stringBuilder.append(Integer.toHexString(10));
                            break;
                        case "rm1":
                            stringBuilder.append(Integer.toHexString(9));
                            break;
                        case "rj1":
                            stringBuilder.append(Integer.toHexString(8));
                            break;
                        case "rp0":
                            stringBuilder.append(Integer.toHexString(13));
                            break;
                        case "rp1":
                            stringBuilder.append(Integer.toHexString(13));
                            break;
                        case "rz0":
                            stringBuilder.append(Integer.toHexString(14));
                            break;
                        case "rz1":
                            stringBuilder.append(Integer.toHexString(14));
                            break;
                        case "rz2":
                            stringBuilder.append(Integer.toHexString(14));
                            break;
                        case "rz3":
                            stringBuilder.append(Integer.toHexString(14));
                            break;
                        case "rz4":
                            stringBuilder.append(Integer.toHexString(14));
                            break;
                    }
                } else {
                    stringBuilder.append(Integer.toHexString(0));
                }
            }
        }
        return stringBuilder.toString();
    }*/

    public ChessPiece getPiece(int[] pos) {
        return getPiece(pos[0], pos[1]);
    }

    public ChessPiece getPiece(int x, int y) {
        return chessPieceArray[x][y];
    }
}