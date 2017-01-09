
package treirad;

import java.util.ArrayList;


abstract class Person{
    
    protected String name;
    protected String symbol;
    ArrayList<GamePiece> gamePieceList = new ArrayList<>();
    protected int LastPositionX;
    protected int LastPositionY;
    protected int points;
    protected char partX;
    protected int partY;
    
    public Person(String startName, String startSymbol){
        name = startName;
        symbol = startSymbol; 
    }
    
    public String getName(){
        return name;
    }
    
    public String getSymbol(){
        return symbol;
    }
    
    public ArrayList getGamePieceList(){
        return gamePieceList;
    }
    
    public int getLastGamePiecePositionX(){
        LastPositionX = gamePieceList.get(gamePieceList.size() - 1).getpositionX();
        return LastPositionX;
    }
    
    public int getLastGamePiecePositionY(){
        LastPositionY = gamePieceList.get(gamePieceList.size() - 1).getpositionY();
        return LastPositionY;
    }
 
    public void addPoints(){
        points++; 
    }
    
    public int getPoints(){
        return points;
    }
    
    public char getPartX(){
        return partX;
    }
    
    public int getPartY(){
        return partY;
    }
    
    abstract void gemeTurn(Board board, int sizeOfBoard);
    abstract void addGamePiece(char positionX, int positionY);
  
}
