package com.chenfu.control;
import com.chenfu.alogrithm.AlphaBetaNode;
import com.chenfu.alogrithm.SearchModel;
import com.chenfu.chess.ChessBoard;
import com.chenfu.chess.ChessPiece;
import com.chenfu.view.GameView;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tong on 12.04.
 * GameController, dealing with logic along game process.
 */
public class GameController {

    private Map<String, ChessPiece> initPieces() {
        Map<String, ChessPiece> pieces = new HashMap<String, ChessPiece>();
        pieces.put("bj0", new ChessPiece("bj0", new int[]{0, 0}));
        pieces.put("bm0", new ChessPiece("bm0", new int[]{0, 1}));
        pieces.put("bx0", new ChessPiece("bx0", new int[]{0, 2}));
        pieces.put("bs0", new ChessPiece("bs0", new int[]{0, 3}));
        pieces.put("bb0", new ChessPiece("bb0", new int[]{0, 4}));
        pieces.put("bs1", new ChessPiece("bs1", new int[]{0, 5}));
        pieces.put("bx1", new ChessPiece("bx1", new int[]{0, 6}));
        pieces.put("bm1", new ChessPiece("bm1", new int[]{0, 7}));
        pieces.put("bj1", new ChessPiece("bj1", new int[]{0, 8}));
        pieces.put("bp0", new ChessPiece("bp0", new int[]{2, 1}));
        pieces.put("bp1", new ChessPiece("bp1", new int[]{2, 7}));
        pieces.put("bz0", new ChessPiece("bz0", new int[]{3, 0}));
        pieces.put("bz1", new ChessPiece("bz1", new int[]{3, 2}));
        pieces.put("bz2", new ChessPiece("bz2", new int[]{3, 4}));
        pieces.put("bz3", new ChessPiece("bz3", new int[]{3, 6}));
        pieces.put("bz4", new ChessPiece("bz4", new int[]{3, 8}));

        pieces.put("rj0", new ChessPiece("rj0", new int[]{9, 0}));
        pieces.put("rm0", new ChessPiece("rm0", new int[]{9, 1}));
        pieces.put("rx0", new ChessPiece("rx0", new int[]{9, 2}));
        pieces.put("rs0", new ChessPiece("rs0", new int[]{9, 3}));
        pieces.put("rb0", new ChessPiece("rb0", new int[]{9, 4}));
        pieces.put("rs1", new ChessPiece("rs1", new int[]{9, 5}));
        pieces.put("rx1", new ChessPiece("rx1", new int[]{9, 6}));
        pieces.put("rm1", new ChessPiece("rm1", new int[]{9, 7}));
        pieces.put("rj1", new ChessPiece("rj1", new int[]{9, 8}));
        pieces.put("rp0", new ChessPiece("rp0", new int[]{7, 1}));
        pieces.put("rp1", new ChessPiece("rp1", new int[]{7, 7}));
        pieces.put("rz0", new ChessPiece("rz0", new int[]{6, 0}));
        pieces.put("rz1", new ChessPiece("rz1", new int[]{6, 2}));
        pieces.put("rz2", new ChessPiece("rz2", new int[]{6, 4}));
        pieces.put("rz3", new ChessPiece("rz3", new int[]{6, 6}));
        pieces.put("rz4", new ChessPiece("rz4", new int[]{6, 8}));
        return pieces;
    }

    private ChessBoard initBoard() {
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.pieces = initPieces();
        for (Map.Entry<String, ChessPiece> stringPieceEntry : initPieces().entrySet()) chessBoard.update(stringPieceEntry.getValue());
        return chessBoard;
    }


    public ChessBoard playChess() {
        initPieces();
        return initBoard();
    }


    public void moveChess(String key, int[] position, ChessBoard chessBoard) {

        chessBoard.updatePiece(key, position);
    }


    public void responseMoveChess(ChessBoard chessBoard, GameView view) {

        SearchModel searchModel = new SearchModel();
        AlphaBetaNode result = searchModel.search(chessBoard);
        view.movePieceFromAI(result.piece, result.to);
        chessBoard.updatePiece(result.piece, result.to);
    }


    public void printBoard(ChessBoard chessBoard) {

        Map<String, ChessPiece> pieces = chessBoard.pieces;
        for (Map.Entry<String, ChessPiece> stringPieceEntry : pieces.entrySet()) {
            ChessPiece chessPiece = stringPieceEntry.getValue();
            System.out.println(stringPieceEntry.getKey() + ":" + (char) (chessPiece.position[1] + 'A') + chessPiece.position[0]);
        }

        System.out.println();
    }

    public char hasWin(ChessBoard chessBoard) {

        boolean isRedWin = chessBoard.pieces.get("bb0") == null;
        boolean isBlackWin = chessBoard.pieces.get("rb0") == null;
        if (isRedWin) return 'r';
        else if (isBlackWin) return 'b';
        else return 'x';
    }


}
