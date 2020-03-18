package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece{
    
    private ChessMatch chessMatch;

    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(color, board);
        this.chessMatch = chessMatch;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean mat[][] = new boolean[getBoard().getRows()][getBoard().getColumns()];
        
        Position p = new Position(0,0);
        
        //Movimentos possiveis do peão
        if(getColor() == Color.WHITE){
            p.setValues(position.getRow()-1, position.getColumn());
            if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
                if(getMoveCount() == 0){
                    p.setValues(position.getRow()-1, position.getColumn());
                    if(!getBoard().thereIsAPiece(p))
                    mat[p.getRow()-1][p.getColumn()] = true;
                }
            }
            p.setValues(position.getRow()-1, position.getColumn()-1);
            if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }
            p.setValues(position.getRow()-1, position.getColumn()+1);
            if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }
            
            //movimento especial 'enPassant'
            if(position.getRow() == 3){
                //verificação p/ direita
                Position left = new Position(position.getRow(), position.getColumn() -1); // possível posição de um oponente vulneravel ao 'enPassant'
                //verificando se existe uma posição, se há um oponente nessa peça e se essa peça está vulneravel ao 'enPassant'
                if(getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()){
                    mat[left.getRow()-1][left.getColumn()] = true;
                }
                //verificação p/ direita
                Position right = new Position(position.getRow(), position.getColumn() +1); 
                if(getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()){
                    mat[right.getRow()-1][right.getColumn()] = true;
                }
            }
        }        
        else{
            p.setValues(position.getRow()+1, position.getColumn());
            if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
                if(getMoveCount() == 0){
                    p.setValues(position.getRow()+1, position.getColumn());
                    if(!getBoard().thereIsAPiece(p))
                    mat[p.getRow()+1][p.getColumn()] = true;
                }
            }
            p.setValues(position.getRow()+1, position.getColumn()+1);
            if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }
            p.setValues(position.getRow()+1, position.getColumn()-1);
            if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }
            
            //movimento especial 'enPassant'
            if(position.getRow() == 4){
                //verificação p/ direita
                Position left = new Position(position.getRow(), position.getColumn() -1); // possível posição de um oponente vulneravel ao 'enPassant'
                //verificando se existe uma posição, se há um oponente nessa peça e se essa peça está vulneravel ao 'enPassant'
                if(getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()){
                    mat[left.getRow()+1][left.getColumn()] = true;
                }
                //verificação p/ direita
                Position right = new Position(position.getRow(), position.getColumn() +1); 
                if(getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()){
                    mat[right.getRow()+1][right.getColumn()] = true;
                }
            }
        }
        return mat;
    }
    
        @Override
    public String toString() {
        return "P";
    }
    
}
