
package treirad;


public class Ai  extends Person{
    
    int randomX = 0;
    int randomY = 0;
    
    public Ai(String startName, String startSymbol){
        super(startName, startSymbol);
    }
    
    public int getRandomX(){
        return randomX;
    }
    
    public int getRandomY(){
        return randomY;
    }
    
    public void getRandomAiPlaceGemaPiece(int sizeOfBoard){
        randomX = 0 + (int)(Math.random() * sizeOfBoard);
        randomY = 0 + (int)(Math.random() * sizeOfBoard);  
    }

    @Override
    public void gemeTurn(Board board, int sizeOfBoard){
        System.out.println(getName() + " plasera ut en pjäs?");
            
        boolean ifCorrectPlaceGamePice = true;
        while (ifCorrectPlaceGamePice) {               

            /*
            *random ai
            */
            getRandomAiPlaceGemaPiece(sizeOfBoard);

            partX = (char) getRandomX();
            partY = getRandomY();          
            
            if(board.boardMultiArray[partY][partX].equals(" ")){                      
                ifCorrectPlaceGamePice = false;
            } 
        } 
    }
    
    @Override
    public void addGamePiece(char positionX, int positionY){
        
        GamePiece gamePiece = new GamePiece(positionX, positionY);
        gamePieceList.add(gamePiece);
    }
}
