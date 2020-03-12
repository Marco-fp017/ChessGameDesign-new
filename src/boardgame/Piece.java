package boardgame;

// Classe que cuida da criação, alocação e possíveis movimentos das peças
public class Piece {
    /*Atributo protegido (essa não é uma posição do xadrez, é uma posição simples de matriz)
                                    Protegido pois esse atributo não pode ser acessado pela classe do xadrez
                                */
    protected Position position; 
    private Board board;
    
    public Piece (Board board){
        this.board = board;
        position = null;
    }
    
//Somente classes de uso interno do jogo de tabuleiro(tabuleiro e peça) e as peças específicas podem acessar essa clase
    protected Board getBoard(){ 
        return board;
    }
    
}