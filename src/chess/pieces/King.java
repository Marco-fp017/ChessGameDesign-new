package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;


public class King extends ChessPiece {
    
    private ChessMatch chessMatch;

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(color, board);
        this.chessMatch = chessMatch;
    }
    
    private boolean canMove(Position position){
        ChessPiece p = (ChessPiece) getBoard().piece(position);
        return p == null || p.getColor() != getColor();
    }
    
    private boolean testRookCastling(Position position){
        ChessPiece p = (ChessPiece)getBoard().piece(position);
        return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
    }
    
    @Override
    public boolean[][] possibleMoves(){
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        
        Position p = new Position(0, 0);

        p.setValues(position.getRow() -1, position.getColumn());
        if(getBoard().positionExists(p) && canMove(p)) mat[p.getRow()][p.getColumn()] = true;
        
        p.setValues(position.getRow() +1, position.getColumn());
        if(getBoard().positionExists(p) && canMove(p)) mat[p.getRow()][p.getColumn()] = true;
        
        p.setValues(position.getRow(), position.getColumn() -1);
        if(getBoard().positionExists(p) && canMove(p)) mat[p.getRow()][p.getColumn()] = true;
        
        p.setValues(position.getRow(), position.getColumn() +1);
        if(getBoard().positionExists(p) && canMove(p)) mat[p.getRow()][p.getColumn()] = true;

        p.setValues(position.getRow() -1, position.getColumn() -1);
        if(getBoard().positionExists(p) && canMove(p)) mat[p.getRow()][p.getColumn()] = true;

        p.setValues(position.getRow() -1, position.getColumn() +1);
        if(getBoard().positionExists(p) && canMove(p)) mat[p.getRow()][p.getColumn()] = true;

        p.setValues(position.getRow() +1, position.getColumn() -1);
        if(getBoard().positionExists(p) && canMove(p)) mat[p.getRow()][p.getColumn()] = true;
        
        p.setValues(position.getRow() +1, position.getColumn() +1);
        if(getBoard().positionExists(p) && canMove(p)) mat[p.getRow()][p.getColumn()] = true;
        
        //Movimento especial 'roque' (onde é possível mover o rei e a torre num só movimento em determinada circunstância)
        if(getMoveCount() == 0 && !chessMatch.getCheck()){
        //roque quando o a torre está mais próxima do rei do que da rainha 
            Position pos1 = new Position(position.getRow(), position.getColumn()+3);
            if(testRookCastling(pos1)){
                Position p1 = new Position(position.getRow(), position.getColumn() + 1);
                Position p2 = new Position(position.getRow(), position.getColumn() + 2);
                
                if (getBoard().piece(p1) == null && getBoard().piece(p2) == null){ 
                    mat[position.getRow()][position.getColumn() + 2] = true;
                }
            }
        //roque quando o a torre está mais próxima da rainha do que do rei 
            Position pos2 = new Position(position.getRow(), position.getColumn() - 4);
            if(testRookCastling(pos2)){
                Position p1 = new Position(position.getRow(), position.getColumn()-1);
                Position p2 = new Position(position.getRow(), position.getColumn()-2);
                Position p3 = new Position(position.getRow(), position.getColumn()-3);
                if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null){
                    mat[position.getRow()][position.getColumn() -2] = true;
                }
            }
        }
        return mat;
    }
    
    //Nome que irá aparecer no tabuleiro para indicar essa peça 
    @Override
    public String toString(){
        return "K";
    }
}