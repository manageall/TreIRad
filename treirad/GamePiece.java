
package treirad;

public class GamePiece {
    private int positionX;
    private int positionY;
    
    
    public GamePiece(int startPositionX, int startPositionY){
        positionX = startPositionX;
        positionY = startPositionY;
    }
    
    public int getpositionX(){
        return positionX;
    }
    public int getpositionY(){
        return positionY;
    }
}
