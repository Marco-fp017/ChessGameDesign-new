package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece{
    
    public Rook(Board board, Color color){
        super(color, board);
    }

    //Método que assinala como verdadeiras as posições em que uma torre poderá se mexer no tabuleiro
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        //Possíveis movimentos pra cima no tabuleiro
        p.setValues(position.getRow() - 1, position.getColumn());
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
                p.setRow(p.getRow() - 1);
        } if (getBoard().positionExists(p) && isThereOpponentPiece(p)) mat[p.getRow()][p.getColumn()] = true;

        //Possíveis movimentos pra esquerda no tabuleiro
        p.setValues(position.getRow(), position.getColumn() - 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
                p.setColumn(p.getColumn() - 1);
        } if (getBoard().positionExists(p) && isThereOpponentPiece(p))mat[p.getRow()][p.getColumn()] = true;

        //Possíveis movimentos pra direita no tabuleiro
        p.setValues(position.getRow(), position.getColumn() + 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
                p.setColumn(p.getColumn() + 1);
        }if (getBoard().positionExists(p) && isThereOpponentPiece(p)) mat[p.getRow()][p.getColumn()] = true;

        //Possíveis movimentos pra baixo no tabuleiro
        p.setValues(position.getRow() + 1, position.getColumn());
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
                p.setRow(p.getRow() + 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) mat[p.getRow()][p.getColumn()] = true;

        return mat;
    }    
    //Nome que irá aparecer no tabuleiro para indicar essa peça
    @Override
    public String toString() {
        return "R";
    }


    
}