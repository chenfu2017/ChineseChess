package com.chenfu.alogrithm;


import com.chenfu.DefaultSet;
import com.chenfu.pojo.ChessBoard;
import com.chenfu.pojo.ChessPiece;
import com.chenfu.control.GameController;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class SearchModel {
    private static int DEPTH;
    private ChessBoard chessBoard;
    private GameController controller;

    public SearchModel(GameController controller) {
        this.controller = controller;
    }

    public AlphaBetaNode search(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
        DEPTH = DefaultSet.DEPTH;
        if (chessBoard.stringChessPieceMap.size() < 20)
            DEPTH = DefaultSet.DEPTH + 1;
        if (chessBoard.stringChessPieceMap.size() < 12)
            DEPTH = DefaultSet.DEPTH + 2;
        if (chessBoard.stringChessPieceMap.size() < 6)
            DEPTH = DefaultSet.DEPTH + 3;
        if (chessBoard.stringChessPieceMap.size() < 4)
            DEPTH = DefaultSet.DEPTH + 4;
        System.out.println("棋子数量：" + chessBoard.stringChessPieceMap.size());
        System.out.println("递归深度：" + DEPTH*2);
        long startTime = System.currentTimeMillis();
        AlphaBetaNode best = null;
        ArrayList<AlphaBetaNode> moves = generateMovesForAll(true);
        for (AlphaBetaNode alphaBetaNode : moves) {
            /* Move*/
            ChessPiece eaten = chessBoard.updatePiece(alphaBetaNode.piece, alphaBetaNode.to);
            alphaBetaNode.value = alphaBeta(DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
            /* Select a best move during searching to save time*/
            if (best == null || alphaBetaNode.value >= best.value)
                best = alphaBetaNode;
            /* Back move*/
            chessBoard.updatePiece(alphaBetaNode.piece, alphaBetaNode.from);
            if (eaten != null) {
                chessBoard.stringChessPieceMap.put(eaten.key, eaten);
                chessBoard.backPiece(eaten.key);
            }
        }
        long finishTime = System.currentTimeMillis();
        System.out.println("搜索时间" + (finishTime - startTime) / 1000 + "秒");
        return best;
    }


    private int alphaBeta(int depth, int alpha, int beta, boolean isMax) {
        /* Return evaluation if reaching leaf node or any side won.*/
        if (depth == 0 || controller.hasWin(chessBoard) != 'x')
            return new EvalModel().eval(chessBoard, 'b');
        ArrayList<AlphaBetaNode> moves = generateMovesForAll(isMax);

        synchronized (this) {
            for (final AlphaBetaNode n : moves) {
                ChessPiece eaten = chessBoard.updatePiece(n.piece, n.to);
                /* Is maximizing player? */
                final int finalBeta = beta;
                final int finalAlpha = alpha;
                final int finalDepth = depth;
                final int[] temp = new int[1];
                /* Only adopt multi threading strategy in depth 2. To avoid conjunction.*/
                if (depth == 2) {
                    if (isMax) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                temp[0] = Math.max(finalAlpha, alphaBeta(finalDepth - 1, finalAlpha, finalBeta, false));
                            }
                        }).run();
                        alpha = temp[0];
                    } else {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                temp[0] = Math.min(finalBeta, alphaBeta(finalDepth - 1, finalAlpha, finalBeta, true));
                            }
                        }).run();
                        beta = temp[0];
                    }
                } else {
                    if (isMax) alpha = Math.max(alpha, alphaBeta(depth - 1, alpha, beta, false));
                    else beta = Math.min(beta, alphaBeta(depth - 1, alpha, beta, true));
                }
                chessBoard.updatePiece(n.piece, n.from);
                if (eaten != null) {
                    chessBoard.stringChessPieceMap.put(eaten.key, eaten);
                    chessBoard.backPiece(eaten.key);
                }
                /* Cut-off */
                if (beta <= alpha) break;
            }
        }
        return isMax ? alpha : beta;
    }

    private ArrayList<AlphaBetaNode> generateMovesForAll(boolean isMax) {
        ArrayList<AlphaBetaNode> moves = new ArrayList<AlphaBetaNode>();
        for (Map.Entry<String, ChessPiece> stringPieceEntry : chessBoard.stringChessPieceMap.entrySet()) {
            ChessPiece chessPiece = stringPieceEntry.getValue();
            if (isMax && chessPiece.color == 'r') continue;
            if (!isMax && chessPiece.color == 'b') continue;
            for (int[] nxt : Objects.requireNonNull(Rules.getNextMove(chessPiece.key, chessPiece.position, chessBoard)))
                moves.add(new AlphaBetaNode(chessPiece.key, chessPiece.position, nxt));
        }
        return moves;
    }

}