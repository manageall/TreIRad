
package treirad;


public class Board {
    
    private int boardSize;
    public String[][] boardMultiArray;
    private String startValueForArray = " ";
   
    
    public Board(int startBoardSize){
        boardSize = startBoardSize;
        boardMultiArray = new String [boardSize][boardSize];
        setUppBoardFromStart();
    }
    
    public int getBoardSize(){
        return boardSize;
    }
    
    public String[][] getBoardMultiArray(){
        return boardMultiArray;
    }
    
    public void setUppBoardFromStart(){
        /*
        *sätter alla värden i boardMultiArray till " ". för att den ska vissa rätt i spelbräddet innan man har börjat spela.
        */
        for (int i = 0; i < boardMultiArray.length; i++) {
            
            for (int j = 0; j < boardMultiArray[i].length; j++) {
                boardMultiArray[i][j] = startValueForArray;
            } 
        }
    }
    
    /*
    *ritar spelplanen
    */
    public void drawBoard(){
        
        /*
        *Loppar i y-ledes
        */
        for (int i = 0; i < boardSize + 1; i++) {
           
            /*
            *Ritar den första bokstvs raden och den första sträck raden
            */
            if(i == 0){
                
                /*
                *den första bokstavs raden               
                */
                for (int j = 0; j < boardSize; j++) {                           
                    char tabelLetter = (char) (97 + j);
                    if(j == 0){                                                 //om det är den första columen i raden. Skriver den uts med ett spcielt utseende.
                        System.out.print("\n    " + (Character.toString (tabelLetter)) + " ");
                    }else{
                        System.out.print("  " + (Character.toString (tabelLetter)) + " ");
                    }
                }
                
                /*
                *den första sträck raden               
                */
                for (int j = 0; j < boardSize; j++) { 
                    if(j == 0){
                        System.out.print("\n  +---+");
                    }else{
                        System.out.print("---+");
                    }
                } 
            }else{                                                              //andra raden och mer.
                
                for (int j = 0; j < boardSize; j++) {   
                    if(j == 0){                                                 //om det är den första columen i raden. Skriver den uts med ett spcielt utseende.
                        if(i < 10){
                            System.out.print("\n " + (i) + "| " + boardMultiArray[i - 1][j] + " |");
                        }else{
                            System.out.print("\n" + (i) + "| " + boardMultiArray[i - 1][j] + " |");
                        }
                    }else{
                        System.out.print(" " + boardMultiArray[i - 1][j] + " |");
                    }      
                }
                
                for (int j = 0; j < boardSize; j++) { 
                    if(j == 0){
                        System.out.print("\n  +---+");
                    }else if(i == boardSize && j == boardSize - 1){             //om det är den sista raden och den sista columen. Ska nästa text skrivas på ny rad skrivas på ny rad.
                        System.out.print("---+");
                        System.out.print("\n\n");
                    }else{
                        System.out.print("---+");
                    }
                } 
            }
        }
    }
    
    public boolean checkCorrectPlaceGamePice(String placeGamePice, int sizeOfBoard){
        boolean checkAnswer = true;
        int partX = 0;
        int partY = 0;
       
        /*
        *Kollar om alla täcken i den inmatatade stäng är rätt. Om första är bokstav och resten siffror.
        */
        for (int j = 0; j < placeGamePice.length(); j++) {

            if(j == 0){                                                         //Kollar om bokstaven är rätt
                int chackThisInt = placeGamePice.charAt(j) - 97;
                if(chackThisInt >= 0 && chackThisInt < 122){
                    checkAnswer = false;
                }else{
                    System.out.println("\nFel! Du måste börja med en bokstav.");
                    checkAnswer = true;
                    break;
                }
            }else{                                                              //Kollar om siffrorna är rätt
                int chackThisInt = placeGamePice.charAt(j) - 48;
                if(chackThisInt >= 0 && chackThisInt <= 9){
                    checkAnswer = false;
                }else{
                    System.out.println("\nFel! Du måste avsluta med en siffra.");
                    checkAnswer = true;
                    break;
                } 
            }  
        }
        
        if(checkAnswer == false){
            
            /*
            *Kollar om inmatad sträng är för kort.
            */
            if(placeGamePice.length() < 2){
                System.out.println("\nFel! Du skrev för få tecken.");
                checkAnswer = true;
            }
        }   
        
        if(checkAnswer == false){
            
            String[] positionGamePice = placeGamePice.split("", 2);
            partX = positionGamePice[0].charAt(0) - 97;                         //-97, ascii värdet
            partY = Integer.parseInt(positionGamePice[1]) - 1;
            
            /*
            *Kollar om värdena är utanför tabellen
            */
            if(partX >= sizeOfBoard){         
                System.out.println("\nFel! Bokstaven finns inte med i tabellen.");
                checkAnswer = true;
            }
            if(partY >= sizeOfBoard){         
                System.out.println("\nFel! Siffran finns inte med i tabellen.");
                checkAnswer = true;
            }    
        }
        
        if(checkAnswer == false){
            
            /*
            *Kollar om den valda rutan är ledig
            */
            if(!boardMultiArray[partY][partX].equals(" ")){                //Obs! Den multidimenstionella arraylisten slänger runt x och y axeln          
                System.out.println("\nFel! Denna ruta är upptagen.");
                checkAnswer = true;
            } 
        }
        
        return checkAnswer;
    }
    
    public void placeSymbolOnBoard(int positionX, int positionY, Person thisTurnPlayer) {
        
        boardMultiArray[positionY][positionX] = thisTurnPlayer.getSymbol();      //Obs! Den multidimenstionella arraylisten slänger runt x och y axeln
    }
    
    public boolean checkForWinner(int positionX, int positionY, int winnerRowSize, Person thisTurnPlayer, boolean checkIfNewGame){

        String personSymbol = boardMultiArray[positionY][positionX];

        int eventYPlus = 0;
        int eventXPlus = 0;
        int eventYMinus = 0;
        int eventXMinus = 0;
        
        boolean case1 = true;
        boolean case2 = true;    
        
        for (int i = 0; i < 4; i++) {
            
            int toAddToInRow = 1;                                               //startar som 1 (den som man utgår ifrån)
            
            boolean ifEmtyBox = true;                                           
            for (int j = 0; j < winnerRowSize - 1; j++) {
                
                switch (i){
                    /*
                    *neråt
                    */
                    case 0:  
                        eventYPlus = positionY + (j + 1);
                        eventXPlus = positionX;
                        
                        case1 = positionY + (j + 1) < boardSize;
                        case2 = true;
                        break;
                    /*
                    *höger
                    */
                    case 1:
                        eventYPlus = positionY;
                        eventXPlus = positionX + (j + 1);
                        
                        case1 = positionX + (j + 1) < boardSize;
                        case2 = true;
                        break;
                    /*
                    *snett höger-ner (backslash \)
                    */
                    case 2:
                        eventYPlus = positionY + (j + 1);
                        eventXPlus = positionX + (j + 1);
                        
                        case1 = positionY + (j + 1) < boardSize;
                        case2 = positionX + (j + 1) < boardSize;
                        break;
                    /*
                    *snett höger-upp (slash /)
                    */    
                    case 3:
                        eventYPlus = positionY + (j + 1);
                        eventXPlus = positionX - (j + 1);
                        
                        case1 = positionY + (j + 1) < boardSize;
                        case2 = positionX - (j + 1) >= 0;
                        break;
                }
                
                if(ifEmtyBox && case1 && case2){
                    if(boardMultiArray[eventYPlus][eventXPlus].equals(personSymbol)){
                        toAddToInRow++;
                        checkIfNewGame = addToCountRow(j, i, toAddToInRow, winnerRowSize, thisTurnPlayer, checkIfNewGame);
                    }else{
                       ifEmtyBox = false;
                    }
                }
            }
            ifEmtyBox = true; 
            for (int j = 0; j < winnerRowSize - 1; j++) {
                
                 switch (i){
                    /*
                    *uppåt
                    */
                    case 0: 
                        eventYMinus = positionY - (j + 1);
                        eventXMinus = positionX;
                        
                        case1 = positionY - (j + 1) >= 0;
                        case2 = true;
                        break;
                    /*
                    *vänster
                    */
                    case 1:
                        eventYMinus = positionY;
                        eventXMinus = positionX - (j + 1); 
                        
                        case1 = positionX - (j + 1) >= 0;
                        case2 = true;
                        break;
                    /*
                    *snett vänster-upp (backslash \)
                    */
                    case 2:
                        eventYMinus = positionY - (j + 1);
                        eventXMinus = positionX - (j + 1);
                        
                        case1 = positionY - (j + 1) >= 0;
                        case2 = positionX - (j + 1) >= 0;
                        break;
                    /*
                    *snett vänster-ner (slash /)
                    */
                    case 3:
                        eventYMinus = positionY - (j + 1);
                        eventXMinus = positionX + (j + 1);
                        
                        case1 = positionY - (j + 1) >= 0;
                        case2 = positionX + (j + 1) < boardSize;
                        break;
                }
                 
                if(ifEmtyBox && case1 && case2){
                    if(boardMultiArray[eventYMinus][eventXMinus].equals(personSymbol)){
                        toAddToInRow++;
                        checkIfNewGame = addToCountRow(j, i, toAddToInRow, winnerRowSize, thisTurnPlayer, checkIfNewGame);
                    }else{
                        ifEmtyBox = false;
                    }
                }
            }
        }
        
        if(checkIfNewGame == false){
            thisTurnPlayer.addPoints();
        }
        
        return checkIfNewGame;
    }
     
    
    
    public boolean addToCountRow(int j, int i, int toAddToInRow, int winnerRowSize, Person thisTurnPlayer, boolean checkIfNewGame){
        
        String winnstReason = null;
        
        switch (i){
            case 0: 
                winnstReason = " vann på vertikal rad.";
                break;
            case 1:
                winnstReason = " vann på horesontal rad.";
                break;
            case 2:
                winnstReason = " vann på diagonal backslash rad.";
                break;
            case 3:
                winnstReason = " vann på diagonal rad.";
                break;
        }
        
        if(toAddToInRow >= winnerRowSize){
            System.out.println("Vinnst! " + thisTurnPlayer.getName() + winnstReason);
            checkIfNewGame = false;
        }
        return checkIfNewGame;
    }

    public boolean checkForTie(){
        boolean checkIfTie= false;
        
        outerloop:
        for (int i = 0; i < boardMultiArray.length; i++) {
            
            for (int j = 0; j < boardMultiArray[i].length; j++) {
                
                if(boardMultiArray[i][j].equals(" ")){
                    checkIfTie = true;
                    break outerloop;
                }     
            } 
        }
        
        if(checkIfTie == false){
            System.out.println("Oavgort!");
        }
        
        return checkIfTie;
    }    
                
                
                
                
                
                
                
                
                
                
        /*
        *
        *Fungerande lång kod  
        *        
        */        
                
        
        /*int inRowVertically = 1;
        int inRowHorizontal = 1;
        int inRowDiagonalBackslash = 1;
        int inRowDiagonal = 1;
        
        boolean ifEmtyBox = true;
        for (int i = 0; i < winnerRowSize - 1; i++) {
            /*
            *Kollar neråt
            */
            /*if(ifEmtyBox && positionY + (i + 1) < boardSize){
                if(boardMultiArray[positionY + (i + 1)][positionX].equals(personSymbol)){
                    inRowVertically++;
                }else{
                   ifEmtyBox = false;
                }
            }
        }
        ifEmtyBox = true; 
        for (int i = 0; i < winnerRowSize - 1; i++) {
            /*
            *Kollar uppåt
            */
            /*if(ifEmtyBox && positionY - (i + 1) >= 0){
                if(boardMultiArray[positionY - (i + 1)][positionX].equals(personSymbol)){
                    inRowVertically++;
                }else{
                    ifEmtyBox = false;
                }
            }
        }

        ifEmtyBox = true;
        for (int i = 0; i < winnerRowSize - 1; i++) {
            /*
            *Kollar höger
            */
            /*if(ifEmtyBox && positionX + (i + 1) < boardSize){
                if(boardMultiArray[positionY][positionX + (i + 1)].equals(personSymbol)){
                    inRowHorizontal++;
                }else{
                    ifEmtyBox = false;
                }
            }
        }
        ifEmtyBox = true;
        for (int i = 0; i < winnerRowSize - 1; i++) {
            /*
            *Kollar vänster
            */
            /*if(ifEmtyBox && positionX - (i + 1) >= 0){
                if(boardMultiArray[positionY][positionX - (i + 1)].equals(personSymbol)){
                    inRowHorizontal++;
                }else{
                    ifEmtyBox = false;
                }
            }
        }

        ifEmtyBox = true;
        for (int i = 0; i < winnerRowSize - 1; i++) {
            /*
            *Kollar snett höger-ner (backslash \)
            */
            /*if((positionY + (i + 1) < boardSize) && positionX + (i + 1) < boardSize){
                if(ifEmtyBox && boardMultiArray[positionX + (i + 1)][positionY + (i + 1)].equals(personSymbol)){
                    inRowDiagonalBackslash++;
                }else{
                    ifEmtyBox = false;
                }
            }
        }
        ifEmtyBox = true;
        for (int i = 0; i < winnerRowSize - 1; i++) {
            /*
            *Kollar snett vänster-upp (backslash \)
            */
            /*if(positionY - (i + 1) >= 0 && positionX - (i + 1) >= 0){
                if(ifEmtyBox && boardMultiArray[positionY - (i + 1)][positionX - (i + 1)].equals(personSymbol)){
                    inRowDiagonalBackslash++;
                }else{
                    ifEmtyBox = false;
                }
            }
        }

        ifEmtyBox = true;
        for (int i = 0; i < winnerRowSize - 1; i++) {
            /*
            *Kollar snett höger-upp (slash /)
            */
            /*if((positionY + (i + 1)) < boardSize && positionX - (i + 1) >= 0){
                if(ifEmtyBox && boardMultiArray[positionY + (i + 1)][positionX - (i + 1)].equals(personSymbol)){
                    inRowDiagonal++;
                }else{
                    ifEmtyBox = false;
                }
            }
        }
        ifEmtyBox = true;
        for (int i = 0; i < winnerRowSize - 1; i++) {
            /*
            *Kollar snett vänster-ner (slash /)
            */
            /*if((positionY - (i + 1)) >= 0 && positionX + (i + 1) < boardSize){
                if(ifEmtyBox && boardMultiArray[positionY - (i + 1)][positionX + (i + 1)].equals(personSymbol)){
                    inRowDiagonal++;
                }else{
                    ifEmtyBox = false;
                }
            }
        }
        
        if(inRowVertically >= winnerRowSize){
            System.out.println("Vinnst! " + thisTurnPlayer.getName() + " vann på vertikal rad.");
            checkIfNewGame = false;
        }
        if(inRowHorizontal == winnerRowSize){
            System.out.println("Vinnst! " + thisTurnPlayer.getName() + " vann på horesontal rad.");
            checkIfNewGame = false;
        }
        if(inRowDiagonalBackslash == winnerRowSize){
            System.out.println("Vinnst! " + thisTurnPlayer.getName() + " vann på diagonal backslash rad.");
            checkIfNewGame  = false;
        }
        if(inRowDiagonal == winnerRowSize){
            System.out.println("Vinnst! " + thisTurnPlayer.getName() + " vann på diagonal rad.");
            checkIfNewGame = false;
        }*/
       
}

