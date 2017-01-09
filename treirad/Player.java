
package treirad;

import static treirad.TreIRad.scannerFunctionString;


public class Player extends Person{
    
    public Player(String startName, String startSymbol) {
        super(startName, startSymbol);
    }
    
    @Override
    public void gemeTurn(Board board, int sizeOfBoard){
        
        String placeGamePice = null;
        
        boolean correctPlaceGamePice = true;
        while(correctPlaceGamePice){
            System.out.println(getName() + " tur att plasera ut en pjäs? Börja med bokstav och avsluta med siffra (tex. a3).");
            placeGamePice = scannerFunctionString();

            /*
            *Kollar om strängen är i rätt format.
            */
            boolean ifCorrectPlaceGamePice = board.checkCorrectPlaceGamePice(placeGamePice, sizeOfBoard);
            correctPlaceGamePice = ifCorrectPlaceGamePice;
        }

        /*
        *Splittar strängen i två delar, en för bokstavs axeln och en för siffer axeln
        */
        String[] positionGamePice = placeGamePice.split("", 2);
        partX = positionGamePice[0].charAt(0);
        partY = Integer.parseInt(positionGamePice[1]);
    }
    
    @Override
    public void addGamePiece(char positionX, int positionY){
        
        /*
        *positionX minus ascii nummer för a (97) för att hamna på index 0.
        *positionY minus ett för att hamna på index 0.
        */
        GamePiece gamePiece = new GamePiece(positionX - 97, positionY - 1);
        gamePieceList.add(gamePiece);
    }
}
