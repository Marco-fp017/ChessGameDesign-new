package boardgame;

// Classe que cuida da criação, alocação e possíveis movimentos das peças
public abstract class Piece {
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
    
    //Métodos a serem sobrescritos por cada peça de acordo com os seus próprios movimentos possíveis
    public abstract boolean[][] possibleMoves();
    
    public boolean possibleMove(Position position){
        return possibleMoves()[position.getRow()][position.getColumn()];
        //Método concreto utilizando um método abstrato(hook methods).
    }
    public boolean isThereAnyPossibleMove(){
        boolean[][] mat = possibleMoves();
        for (int i = 0; i < mat.length; i++){
            for(int j = 0; j < mat.length; j++){
                if(mat[i][j]) return true;
            }
        }
        return false;
    }
} 