package com.chenfu.chess;

import java.util.ArrayList;

public class Rules {

    private static int[] pos;
    private static ChessBoard chessBoard;
    private static char player;

    public static ArrayList<int[]> getNextMove(String piece, int[] pos, ChessBoard chessBoard) {
        Rules.pos = pos;
        Rules.chessBoard = chessBoard;
        Rules.player = piece.charAt(0);
        switch (piece.charAt(1)) {
            case 'j':
                return jRules();
            case 'm':
                return mRules();
            case 'p':
                return pRules();
            case 'x':
                return xRules();
            case 's':
                return sRules();
            case 'b':
                return bRules();
            case 'z':
                return zRules();
            default:
                return null;
        }
    }

    private static ArrayList<int[]> mRules() {
        ArrayList<int[]> moves = new ArrayList<int[]>();
        int[][] target = new int[][]{{1, -2}, {2, -1}, {2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}};
        int[][] obstacle = new int[][]{{0, -1}, {1, 0}, {1, 0}, {0, 1}, {0, 1}, {-1, 0}, {-1, 0}, {0, -1}};
        for (int i = 0; i < target.length; i++) {
            int[] e = new int[]{pos[0] + target[i][0], pos[1] + target[i][1]};
            int[] f = new int[]{pos[0] + obstacle[i][0], pos[1] + obstacle[i][1]};
            if (!chessBoard.isInside(e)) continue;
            if (chessBoard.isEmpty(f)) {
                if (chessBoard.isEmpty(e)) moves.add(e);
                else if (chessBoard.getPiece(e).color != player) moves.add(e);
            }
        }
        return moves;
    }

    private static ArrayList<int[]> jRules() {
        ArrayList<int[]> moves = new ArrayList<int[]>();
        int[] yOffsets = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        int[] xOffsets = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int offset : yOffsets) {
            int[] rMove = new int[]{pos[0], pos[1] + offset};
            if (chessBoard.isEmpty(rMove)) moves.add(rMove);
            else if (chessBoard.isInside(rMove) && chessBoard.getPiece(rMove).color != player) {
                moves.add(rMove);
                break;
            } else break;
        }
        for (int offset : yOffsets) {
            int[] lMove = new int[]{pos[0], pos[1] - offset};
            if (chessBoard.isEmpty(lMove)) moves.add(lMove);
            else if (chessBoard.isInside(lMove) && chessBoard.getPiece(lMove).color != player) {
                moves.add(lMove);
                break;
            } else break;
        }
        for (int offset : xOffsets) {
            int[] uMove = new int[]{pos[0] - offset, pos[1]};
            if (chessBoard.isEmpty(uMove)) moves.add(uMove);
            else if (chessBoard.isInside(uMove) && chessBoard.getPiece(uMove).color != player) {
                moves.add(uMove);
                break;
            } else break;
        }
        for (int offset : xOffsets) {
            int[] dMove = new int[]{pos[0] + offset, pos[1]};
            if (chessBoard.isEmpty(dMove)) moves.add(dMove);
            else if (chessBoard.isInside(dMove) && chessBoard.getPiece(dMove).color != player) {
                moves.add(dMove);
                break;
            } else break;
        }
        return moves;
    }

    private static ArrayList<int[]> pRules() {
        ArrayList<int[]> moves = new ArrayList<int[]>();
        int[] yOffsets = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        int[] xOffsets = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        boolean rr = false, ll = false, uu = false, dd = false;
        for (int offset : yOffsets) {
            int[] rMove = new int[]{pos[0], pos[1] + offset};
            if (!chessBoard.isInside(rMove)) break;
            boolean e = chessBoard.isEmpty(rMove);
            if (!rr) {
                if (e) moves.add(rMove);
                else rr = true;
            } else if (!e) {
                if (chessBoard.getPiece(rMove).color != player) moves.add(rMove);
                break;
            }
        }
        for (int offset : yOffsets) {
            int[] lMove = new int[]{pos[0], pos[1] - offset};
            if (!chessBoard.isInside(lMove)) break;
            boolean e = chessBoard.isEmpty(lMove);
            if (!ll) {
                if (e) moves.add(lMove);
                else ll = true;
            } else if (!e) {
                if (chessBoard.getPiece(lMove).color != player) {
                    moves.add(lMove);
                }
                break;
            }
        }
        for (int offset : xOffsets) {
            int[] uMove = new int[]{pos[0] - offset, pos[1]};
            if (!chessBoard.isInside(uMove)) break;
            boolean e = chessBoard.isEmpty(uMove);
            if (!uu) {
                if (e) moves.add(uMove);
                else uu = true;
            } else if (!e) {
                if (chessBoard.getPiece(uMove).color != player) moves.add(uMove);
                break;
            }
        }
        for (int offset : xOffsets) {
            int[] dMove = new int[]{pos[0] + offset, pos[1]};
            if (!chessBoard.isInside(dMove)) break;
            boolean e = chessBoard.isEmpty(dMove);
            if (!dd) {
                if (e) moves.add(dMove);
                else dd = true;
            } else if (!e) {
                if (chessBoard.getPiece(dMove).color != player) moves.add(dMove);
                break;
            }
        }
        return moves;
    }

    private static ArrayList<int[]> xRules() {
        ArrayList<int[]> moves = new ArrayList<int[]>();
        int[][] target = new int[][]{{-2, -2}, {2, -2}, {-2, 2}, {2, 2}};
        int[][] obstacle = new int[][]{{-1, -1}, {1, -1}, {-1, 1}, {1, 1}};
        for (int i = 0; i < target.length; i++) {
            int[] e = new int[]{pos[0] + target[i][0], pos[1] + target[i][1]};
            int[] f = new int[]{pos[0] + obstacle[i][0], pos[1] + obstacle[i][1]};
            if (!chessBoard.isInside(e) || (e[0] > 4 && player == 'b') || (e[0] < 5 && player == 'r')) continue;
            if (chessBoard.isEmpty(f)) {
                if (chessBoard.isEmpty(e)) moves.add(e);
                else if (chessBoard.getPiece(e).color != player) moves.add(e);
            }
        }
        return moves;
    }

    private static ArrayList<int[]> sRules() {
        ArrayList<int[]> moves = new ArrayList<int[]>();
        int[][] target = new int[][]{{-1, -1}, {1, 1}, {-1, 1}, {1, -1}};
        for (int[] aTarget : target) {
            int[] e = new int[]{pos[0] + aTarget[0], pos[1] + aTarget[1]};
            if (!chessBoard.isInside(e) || ((e[0] > 2 || e[1] < 3 || e[1] > 5) && player == 'b') || ((e[0] < 7 || e[1] < 3 || e[1] > 5) && player == 'r'))
                continue;
            if (chessBoard.isEmpty(e)) moves.add(e);
            else if (chessBoard.getPiece(e).color != player) moves.add(e);
        }
        return moves;
    }

    private static ArrayList<int[]> bRules() {
        ArrayList<int[]> moves = new ArrayList<int[]>();
        /* 3*3 block */
        int[][] target = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (int[] aTarget : target) {
            int[] e = new int[]{pos[0] + aTarget[0], pos[1] + aTarget[1]};
            if (!chessBoard.isInside(e) || ((e[0] > 2 || e[1] < 3 || e[1] > 5) && player == 'b') || ((e[0] < 7 || e[1] < 3 || e[1] > 5) && player == 'r'))
                continue;
            if (chessBoard.isEmpty(e)) moves.add(e);
            else if (chessBoard.getPiece(e).color != player) moves.add(e);
        }
        /* opposite 'b' */
        boolean flag = true;
        int[] oppoBoss = (player == 'r') ? chessBoard.pieces.get("bb0").position : chessBoard.pieces.get("rb0").position;
        if (oppoBoss[1] == pos[1]) {
            for (int i = Math.min(oppoBoss[0], pos[0]) + 1; i < Math.max(oppoBoss[0], pos[0]); i++) {
                if (chessBoard.getPiece(i, pos[1]) != null) {
                    flag = false;
                    break;
                }
            }
            if (flag) moves.add(oppoBoss);
        }
        return moves;
    }

    private static ArrayList<int[]> zRules() {
        ArrayList<int[]> moves = new ArrayList<int[]>();
        int[][] targetU = new int[][]{{0, 1}, {0, -1}, {-1, 0}};
        int[][] targetD = new int[][]{{0, 1}, {0, -1}, {1, 0}};
        if (player == 'r') {
            if (pos[0] > 4) {
                int[] e = new int[]{pos[0] - 1, pos[1]};
                if (chessBoard.isEmpty(e)) moves.add(e);
                else if (chessBoard.getPiece(e).color != player) moves.add(e);
            } else {
                for (int[] aTarget : targetU) {
                    int[] e = new int[]{pos[0] + aTarget[0], pos[1] + aTarget[1]};
                    if (!chessBoard.isInside(e)) continue;
                    if (chessBoard.isEmpty(e)) moves.add(e);
                    else if (chessBoard.getPiece(e).color != player) moves.add(e);
                }
            }
        }
        if (player == 'b') {
            if (pos[0] < 5) {
                int[] e = new int[]{pos[0] + 1, pos[1]};
                if (chessBoard.isEmpty(e)) moves.add(e);
                else if (chessBoard.getPiece(e).color != player) moves.add(e);
            } else {
                for (int[] aTarget : targetD) {
                    int[] e = new int[]{pos[0] + aTarget[0], pos[1] + aTarget[1]};
                    if (!chessBoard.isInside(e)) continue;
                    if (chessBoard.isEmpty(e)) moves.add(e);
                    else if (chessBoard.getPiece(e).color != player) moves.add(e);
                }
            }
        }
        return moves;
    }
}
