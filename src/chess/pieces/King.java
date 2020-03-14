package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;


public class King extends ChessPiece {

    public King(Board board, Color color) {
        super(color, board);
    }
    
    @Override
    public boolean[][] possibleMoves(){
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        
        
        return mat;
    }
    
    //Nome que irá aparecer no tabuleiro para indicar essa peça 
    @Override
    public String toString(){
        return "K";
    }
}