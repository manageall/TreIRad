
package treirad;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;

public class TreIRad {

    public static void main(String[] args) {
        
        ArrayList<Person> personList = new ArrayList<>();
        Player player = null;
        Ai ai = null;
        Board board = null;
        
        String player1Symbol = "X";
        String player2Symbol = "O";
        
        int winnerRowSize = 3;
        int sizeOfBoard = 0;
        int gameSessions = 0;
        
        boolean checkIfNewGame = true;
        boolean checkIfTie = true;
        
        
        /*
        *Spelare 1
        */
        System.out.println("Vad heter spelare 1?");
        String nameOfPlayer = scannerFunctionString();
        player = new Player(nameOfPlayer, player1Symbol);
        personList.add(player);
        
        /*
        *Spelare 2 eller AI
        */
        boolean correctSelectMenuPlayer = true;
        while (correctSelectMenuPlayer) {            
            System.out.println("Välej ett av alternativen för spelare 2.\n\n"
                    + "1. För en till spelare.\n\n"
                    + "2. För att spela mot datorn.");
            int selectMenuPlayer = scannerFunction();
            
            switch (selectMenuPlayer){
                case 1: 
                    System.out.println("Vad heter spelare 2?");
                    nameOfPlayer = scannerFunctionString();
                    player = new Player(nameOfPlayer, player2Symbol);
                    personList.add(player);
                    correctSelectMenuPlayer = false;
                    break;
                case 2:
                    String nameOfAi = "Dator";
                    System.out.println("Du spelar nu mot " + nameOfAi);
                    ai = new Ai(nameOfAi, player2Symbol);
                    personList.add(ai);
                    correctSelectMenuPlayer = false;
                    break;
                default: 
                    System.out.println("Fel nummer! Välj 1 eller 2.");
                    break;
            }
        }
        
        /*
        *Välj storlek på spelplanen, samt kollar så det blir rätt inmatade siffror
        */
        boolean correctBoardSize = true;
        while (correctBoardSize) {            
            System.out.println("Hur stor ska spelplanen vara? min 3, max 26");
            sizeOfBoard = scannerFunction();
            
            if(sizeOfBoard >= 3 && sizeOfBoard <= 26){
                board = new Board(sizeOfBoard);
                correctBoardSize = false;
                
                if(sizeOfBoard > 5){
                    
                    boolean correctWinnRowSize = true;
                    while (correctWinnRowSize) {  
                        System.out.println("Hur många i rad för vinnst vill du spela med? min 3, max " + sizeOfBoard);
                        int winnRowSize = scannerFunction();
                        
                        if(winnRowSize >= 3 && winnRowSize <= sizeOfBoard){
                            winnerRowSize = winnRowSize;
                            correctWinnRowSize = false;
                        }else{
                            System.out.println("Ej en valbar storlek på vinnstrad.");
                        }
                    }
                }    
            }else{
                System.out.println("Ej en valbar storlek på spelplanen.");
            }
        }
        
        /*
        *whileloop för spelomgången. Har inget stopp.
        */
        while (true){
            gameSessions++;
            System.out.println("\nSpelomgång - " + gameSessions + "\n"
                    + personList.get(0).getName() 
                    + "  " 
                    + personList.get(0).getPoints() 
                    + "  -  " 
                    + personList.get(1).getPoints() 
                    + "  " 
                    + personList.get(1).getName());
            
            board.setUppBoardFromStart();
            board.drawBoard();
            
            /*
            *Placerar spelarna i spelordnig i person arraylist. Den med minst poäng först. om det är lika händer inget.
            */
            for (int i = 0; i < personList.size()- 1; i++) {
                
                int lastInArray = personList.get(personList.size() - 1).getPoints();
                int thisIndexPoints = personList.get(i).getPoints();
                if(lastInArray < thisIndexPoints){
                    Person thisIndexPerson = personList.get(i);
                    personList.remove(i);
                    personList.add(thisIndexPerson);
                    
                }
            }
            
            checkIfNewGame = true;
            checkIfTie = true;
            /*
            *whileloop för ett speldarag för alla spelare
            */
            while (checkIfNewGame && checkIfTie) {              
                
                for (int i = 0; i < personList.size(); i++) {
                    Person thisTurnPlayer = personList.get(i);

                    /*
                    *Spelarens drag
                    */
                    thisTurnPlayer.gemeTurn(board, sizeOfBoard);

                    /*
                    *Lägger till en spelpjäs hos spelaren
                    */
                    personList.get(i).addGamePiece(thisTurnPlayer.getPartX(), thisTurnPlayer.getPartY());

                    /*
                    *Hämtar den sista tillagda spelpjäsens x och y position.
                    *Placerar ut merkering på den platsen på spelplanen
                    */
                    int lastGamePiecePositionX = thisTurnPlayer.getLastGamePiecePositionX();
                    int lastGamePiecePositionY = thisTurnPlayer.getLastGamePiecePositionY();
                    board.placeSymbolOnBoard(lastGamePiecePositionX, lastGamePiecePositionY, thisTurnPlayer);
                    
                    board.drawBoard();

                    /*
                    *Kontrolerar om spelomgåmgen ska fortsätta
                    */
                    checkIfNewGame = board.checkForWinner(lastGamePiecePositionX, lastGamePiecePositionY, winnerRowSize, thisTurnPlayer, checkIfNewGame);
                    if(checkIfNewGame == false){
                        break;
                    }
                    
                    /*
                    *kollar om spelplanen är full, men bara när ingen har vunnit
                    */
                    if(checkIfNewGame){
                        checkIfTie = board.checkForTie();
                        if(checkIfTie == false){
                            break;
                        }
                    }
                }
            }  
        }  
    }
    
    public static int scannerFunction(){
        Scanner sc = new Scanner(System.in);
        //int svar = sc.nextInt();
        int svar;
        /*
        *Kontrorlerar att det är en siffta som matas in
        */
        do{ 
            try {
                String s = sc.nextLine();
                svar = Integer.parseInt(s);
                break;
            }
            catch (Exception e)
            {
                System.out.println("Fel format! Du ska skirva en siffra.");
            }
        }
        while (true);
        return svar;
    }
    
    public static String scannerFunctionString(){
        Scanner sc = new Scanner(System.in);
        String svar = sc.nextLine();
        return svar;
    } 
    
}
