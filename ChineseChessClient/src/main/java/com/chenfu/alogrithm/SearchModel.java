package com.chenfu.alogrithm;



import com.chenfu.chess.ChessBoard;
import com.chenfu.chess.ChessPiece;
import com.chenfu.chess.Rules;
import com.chenfu.control.GameController;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Tong on 12.08.
 * Alpha beta search.
 */
public class SearchModel {
    private static int DEPTH = 2;
    private ChessBoard chessBoard;
    private GameController controller = new GameController();

    public AlphaBetaNode search(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
        if (chessBoard.pieces.size() < 28)
            DEPTH = 3;
        if (chessBoard.pieces.size() < 16)
            DEPTH = 4;
        if (chessBoard.pieces.size() < 6)
            DEPTH = 5;
        if (chessBoard.pieces.size() < 4)
            DEPTH = 6;
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
                chessBoard.pieces.put(eaten.key, eaten);
                chessBoard.backPiece(eaten.key);
            }
        }
        long finishTime = System.currentTimeMillis();
        System.out.println(finishTime - startTime);
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
                }
                else {
                    if (isMax) alpha = Math.max(alpha, alphaBeta(depth - 1, alpha, beta, false));
                    else beta = Math.min(beta, alphaBeta(depth - 1, alpha, beta, true));
                }
                chessBoard.updatePiece(n.piece, n.from);
                if (eaten != null) {
                    chessBoard.pieces.put(eaten.key, eaten);
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
        for (Map.Entry<String, ChessPiece> stringPieceEntry : chessBoard.pieces.entrySet()) {
            ChessPiece chessPiece = stringPieceEntry.getValue();
            if (isMax && chessPiece.color == 'r') continue;
            if (!isMax && chessPiece.color == 'b') continue;
            for (int[] nxt : Rules.getNextMove(chessPiece.key, chessPiece.position, chessBoard))
                moves.add(new AlphaBetaNode(chessPiece.key, chessPiece.position, nxt));
        }
        return moves;
    }

}