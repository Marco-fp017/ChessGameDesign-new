package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece{

    public Knight(Board board, Color color) {
        super(color, board);
    }

    private void testMoves(Position p, boolean[][] mat){
        if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) mat[p.getRow()][p.getColumn()] = true;
        else if(getBoard().positionExists(p) && isThereOpponentPiece(p)) mat[p.getRow()][p.getColumn()] = true;
    }
    
    @Override
    public boolean[][] possibleMoves() {
        boolean mat[][] = new boolean[getBoard().getRows()][getBoard().getColumns()];
        
        Position p = new Position(0,0);
        
        p.setValues(position.getRow()-2, position.getColumn()-1);
        testMoves(p, mat);

        p.setValues(position.getRow()-2, position.getColumn()+1);
        testMoves(p, mat);
        
        p.setValues(position.getRow()-1, position.getColumn()-2);
        testMoves(p, mat);

        p.setValues(position.getRow()-1, position.getColumn()+2);
        testMoves(p, mat);

        p.setValues(position.getRow()+1, position.getColumn()-2);
        testMoves(p, mat);

        p.setValues(position.getRow()+1, position.getColumn()+2);
        testMoves(p, mat);
        
        p.setValues(position.getRow()+2, position.getColumn()-1);
        testMoves(p, mat);

        p.setValues(position.getRow()+2, position.getColumn()+1);
        testMoves(p, mat);
        
        return mat;
    }
    
    @Override
    public String toString() {
        return "N";
    }
}
