package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece{
    
    public Rook(Board board, Color color){
        super(color, board);
    }
    
    //Nome que irá aparecer no tabuleiro para indicar essa peça

    @Override
    public String toString() {
        return "R";
    }
    
}