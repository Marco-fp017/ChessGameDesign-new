package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;


public class King extends ChessPiece {

    public King(Color color, Board board) {
        super(color, board);
    }
    
    //Nome que irá aparecer no tabuleiro para indicar essa peça 
    @Override
    public String toString(){
        return "K";
    }
}