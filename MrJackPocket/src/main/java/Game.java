import javax.swing.*;
import java.util.*;

public class Game {

    // Attributes

    private final ArrayList<BoardCharacter> mList;
    private ArrayList<BoardCharacter> listCharacterAlibi;
    private final ArrayList<BoardCharacter> mListCardAlibiDetective;
    private DetectiveCharacter[] mTableauDetective;
    private District[][] mBoard;
    private final String[] mListPosition = {"b1", "c1", "d1", "e2", "e3", "e4", "d5", "c5", "b5", "a4", "a3", "a2"};
    private ArrayList<String> mSousListActionString1;
    private ArrayList<String> mSousListActionString2;
    private ArrayList<ActionToken> mSousListAction1;
    private ArrayList<ActionToken> mSousListAction2;
    private final ArrayList<ActionToken> mListAllActionToken;
    private District mDistrictPivote;
    private int turn;
    private int sousTurn;
    private final MrJackGraphics graphics;

    // Constructor

    public Game()
    {
        this.mList = new ArrayList<>();
        this.listCharacterAlibi = new ArrayList<>();
        this.turn = 1;
        this.sousTurn = 1;
        this.mListAllActionToken = new ArrayList<>();
        this.mSousListAction1 = new ArrayList<>();
        this.mSousListAction2 = new ArrayList<>();
        this.mSousListActionString1 = new ArrayList<>();
        this.mSousListActionString2 = new ArrayList<>();
        this.mListCardAlibiDetective = new ArrayList<>();
        this.graphics = new MrJackGraphics(this);
    }


    // Getters et Setters

    public ArrayList<ActionToken> getmSousListAction1(){
        return this.mSousListAction1;
    }

    public ArrayList<ActionToken> getmSousListAction2(){
        return this.mSousListAction2;
    }

    public int getTurn(){
        return this.turn;
    }

    public int getSousTurn(){
        return this.sousTurn;
    }
    public void setSousTurn(int sousTurn) { this.sousTurn = sousTurn;}

    public DetectiveCharacter[] getmTableauDetective() { return this.mTableauDetective;}

    public ArrayList<ActionToken> getmListAllActionToken() { return this.mListAllActionToken;}

    public District[][] getmBoard() {return this.mBoard;}


    // Methods

    // Fonction qui décrit le déroulement du jeu avec interface graphique.
    public void playMrJackGraphics() {

        System.out.println("Bienvenue sur Mr Jack Pocket, bonne chance !");
        creationPlayers();
        creationAlibiCard();
        creationDetective();
        boardDistrictInitialization(mList);
        positionDetectiveInitialization(mTableauDetective);
        mDistrictPivote = null;
        //System.out.println(" ");
        if (turn == 1){
            throwToken();
            graphics.InitialisationPanel();}
        while (turn < 9) {
            if (turn % 2 != 0 && turn!=1)
                throwToken();
            //System.out.println("----------- TOUR " + turn + " -----------");
            System.out.println(sousTurn);
            if (sousTurn == 5) {
                System.out.println("CA MARCHE");
                MrJackActionListener.test = null;
                mDistrictPivote = null;
                turn++;
                if (!whoISee()) {
                    MrJack.setmNbHourglass(MrJack.getmNbHourglass() + 1);
                    System.out.println("Mr Jack a gagné un sablier.");
                }
                turnOverDistrict();
                graphics.updatePanel();
            }
            int cpt = 9;
            if (MrJack.getmNbHourglass() > 5 || turn == 8)
                isGameOver("Enquêteur");
            for (int i = 0; i < mBoard.length; i++)
                for (int j = 0; j < mBoard[i].length; j++)
                    if (!mBoard[i][j].getmCharacter().getmRecto())
                        cpt -= 1;
            if (cpt < 2)
                isGameOver("Mr Jack");
        }
    }

    // Fonction qui décrit le déroulement du jeu en console.
    public void playMrJackConsole() {

        System.out.println("Bienvenue sur Mr Jack Pocket, bonne chance !");
        creationPlayers();
        creationAlibiCard();
        creationDetective();
        boardDistrictInitialization(mList);
        positionDetectiveInitialization(mTableauDetective);

        while (turn < 9) {
            System.out.println("----------- TOUR " + turn + " -----------");
            if (turn % 2 != 0)
                throwToken();
            mDistrictPivote = null;
            while (sousTurn <= 4) {
                System.out.println("----------- Sous-tour " + sousTurn + " -----------");
                printWhoTurn();
                chooseActionPlayer(chooseListAction(), whoTurn());
                turnOverDistrict();
                updateBoardDistrict();
                updatePositionDetective(mTableauDetective);
                sousTurn++;
            }
            if (!whoISee()) {
                MrJack.setmNbHourglass(MrJack.getmNbHourglass() + 1);
                System.out.println("Mr Jack a gagné un sablier.");
            }
            turnOverDistrict();
            updateBoardDistrict();
            updatePositionDetective(mTableauDetective);
            int cpt = 9;
            if (MrJack.getmNbHourglass() > 5 || turn == 8)
                isGameOver("Enquêteur");
            turn++;
            sousTurn = 1;
            for (int i = 0; i < mBoard.length; i++)
                for (int j = 0; j < mBoard[i].length; j++)
                    if (!mBoard[i][j].getmCharacter().getmRecto())
                        cpt -= 1;
            if (cpt < 2)
                isGameOver("Mr Jack");
        }
    }

    // Création des deux joueurs et affectation de Mr Jack et Enquêteur.
    public void creationPlayers() {
        MrJack.choosePlayerMrJack();
        MrJack.setmRole("Mr Jack");
        Investigator.choosePlayerInvestigator();
        Investigator.setmRole("Enquêteur");
    }

    // Création des personnages.
    public void creationAlibiCard() {

        //Création des images
        ImageIcon noir = new ImageIcon(getClass().getResource("/images/noir.png"));
        ImageIcon orange = new ImageIcon(getClass().getResource("/images/orange.png"));
        ImageIcon jaune = new ImageIcon(getClass().getResource("/images/jaune.png"));
        ImageIcon marron = new ImageIcon(getClass().getResource("/images/gris.png"));
        ImageIcon bleu = new ImageIcon(getClass().getResource("/images/bleu.png"));
        ImageIcon violet = new ImageIcon(getClass().getResource("/images/violet.png"));
        ImageIcon vert = new ImageIcon(getClass().getResource("/images/vert.png"));
        ImageIcon rose = new ImageIcon(getClass().getResource("/images/rose.png"));
        ImageIcon blanc = new ImageIcon(getClass().getResource("/images/blanc.png"));

        ImageIcon noirAlibi = new ImageIcon(getClass().getResource("/images/a_noir.png"));
        ImageIcon orangeAlibi = new ImageIcon(getClass().getResource("/images/a_orange.png"));
        ImageIcon jauneAlibi = new ImageIcon(getClass().getResource("/images/a_jaune.png"));
        ImageIcon marronAlibi = new ImageIcon(getClass().getResource("/images/a_marron.png"));
        ImageIcon bleuAlibi = new ImageIcon(getClass().getResource("/images/a_bleu.png"));
        ImageIcon violetAlibi = new ImageIcon(getClass().getResource("/images/a_violet.png"));
        ImageIcon vertAlibi = new ImageIcon(getClass().getResource("/images/a_vert.png"));
        ImageIcon roseAlibi = new ImageIcon(getClass().getResource("/images/a_rose.png"));
        ImageIcon blancAlibi = new ImageIcon(getClass().getResource("/images/a_blanc.png"));

        BoardCharacter pinkCharacter = new BoardCharacter("ROSE", 2, rose, roseAlibi);
        BoardCharacter blackCharacter = new BoardCharacter("NOIR", 0, noir, noirAlibi);
        BoardCharacter orangeCharacter = new BoardCharacter("ORANGE", 1, orange, orangeAlibi);
        BoardCharacter purpleCharacter = new BoardCharacter("VIOLET", 1, violet, violetAlibi);
        BoardCharacter greenCharacter = new BoardCharacter("VERT", 1, vert, vertAlibi);
        BoardCharacter yellowCharacter = new BoardCharacter("JAUNE", 1, jaune, jauneAlibi);
        BoardCharacter blueCharacter = new BoardCharacter("BLEU", 0, bleu, bleuAlibi);
        BoardCharacter whiteCharacter = new BoardCharacter("BLANC", 1, blanc, blancAlibi);
        BoardCharacter brownCharacter = new BoardCharacter("MARRON", 1, marron, marronAlibi);
        //ArrayList<BoardCharacter> listCharacter = new ArrayList<>();
        mList.add(pinkCharacter);
        mList.add(blackCharacter);
        mList.add(orangeCharacter);
        mList.add(purpleCharacter);
        mList.add(greenCharacter);
        mList.add(yellowCharacter);
        mList.add(blueCharacter);
        mList.add(whiteCharacter);
        mList.add(brownCharacter);

        Random rand = new Random();
        int randomIndex = rand.nextInt(mList.size());
        BoardCharacter randomElement = mList.get(randomIndex);
        randomElement.setmStatus("Mr Jack");
        System.out.println("Mr Jack est le personnage " + randomElement.getmColor() + ".");
    }

    // Création des détectives.
    public void creationDetective() {

        DetectiveCharacter watson = new DetectiveCharacter("Watson");
        DetectiveCharacter toby = new DetectiveCharacter("Toby");
        DetectiveCharacter holmes = new DetectiveCharacter("Holmes");
        DetectiveCharacter[] listDetective = new DetectiveCharacter[3];
        listDetective[0] = watson;
        listDetective[1] = toby;
        listDetective[2] = holmes;
        mTableauDetective = listDetective;
    }

    // Création des districts avec affectation à un personnage + change l'orientation des district en face des détectives.
    public void boardDistrictInitialization(ArrayList<BoardCharacter> listCharacter) {
        ArrayList<BoardCharacter> characterDistrict = listCharacter;
        Collections.shuffle(characterDistrict);
        mBoard = new District[3][3];
        int cpt = 0;
        int cpt1 = 1;
        for (int i = 0; i < mBoard.length; i++) {
            for (int j = 0; j < mBoard[i].length; j++) {
                mBoard[i][j] = new District(characterDistrict.get(cpt), cpt1);
                cpt++;
                cpt1++;
            }
        }
        int cpt2 = 1;
        System.out.println(" ");
        System.out.println("PLATEAU :");
        System.out.println(" ");
        for (int i = 0; i < mBoard.length; i++) {
            for (int j = 0; j < mBoard[i].length; j++) {
                if (cpt2 == 1) {
                    mBoard[i][j].setmOrientation("EST");
                    mBoard[i][j].setmWall("OUEST");
                    System.out.println("Le district " + mBoard[i][j].getmNumber() + " (ligne : " + i + ", colonne : " + j + ") est associé au personnage " + mBoard[i][j].getmCharacter().getmColor() + " et est orienté vers " + mBoard[i][j].getmOrientation() + " et le mur est vers " + mBoard[i][j].getmWall() + ".");
                } else if (cpt2 == 3) {
                    mBoard[i][j].setmOrientation("OUEST");
                    mBoard[i][j].setmWall("EST");
                    System.out.println("Le district " + mBoard[i][j].getmNumber() + " (ligne : " + i + ", colonne : " + j + ") est associé au personnage " + mBoard[i][j].getmCharacter().getmColor() + " et est orienté vers " + mBoard[i][j].getmOrientation() + " et le mur est vers " + mBoard[i][j].getmWall() + ".");
                } else if (cpt2 == 8) {
                    mBoard[i][j].setmOrientation("NORD");
                    mBoard[i][j].setmWall("SUD");
                    System.out.println("Le district " + mBoard[i][j].getmNumber() + " (ligne : " + i + ", colonne : " + j + ") est associé au personnage " + mBoard[i][j].getmCharacter().getmColor() + " et est orienté vers " + mBoard[i][j].getmOrientation() + " et le mur est vers " + mBoard[i][j].getmWall() + ".");
                } else
                    System.out.println("Le district " + mBoard[i][j].getmNumber() + " (ligne : " + i + ", colonne : " + j + ") est associé au personnage " + mBoard[i][j].getmCharacter().getmColor() + " et est orienté vers " + mBoard[i][j].getmOrientation() + " et le mur est vers " + mBoard[i][j].getmWall() + ".");
                cpt2++;
            }
        }
    }

    // Initialise la position des détectives.
    public void positionDetectiveInitialization(DetectiveCharacter[] tableauDetective) {

        tableauDetective[0].setmPosition(DetectiveCharacter.toString('e', 2));
        tableauDetective[1].setmPosition(DetectiveCharacter.toString('c', 5));
        tableauDetective[2].setmPosition(DetectiveCharacter.toString('a', 2));
        System.out.println(" ");
        System.out.println(tableauDetective[0].getmName() + " est sur la case " + tableauDetective[0].getmPosition());
        System.out.println(tableauDetective[1].getmName() + " est sur la case " + tableauDetective[1].getmPosition());
        System.out.println(tableauDetective[2].getmName() + " est sur la case " + tableauDetective[2].getmPosition());
        System.out.println(" ");

    }

    // Lance les jetons actions.
    public void throwToken() {

        ImageIcon imageAlibi = new ImageIcon(getClass().getResource("/images/ALIBI.png"),"Alibi");
        ImageIcon imageHolmes = new ImageIcon(getClass().getResource("/images/SHERLOCK.png"),"Holmes");
        ImageIcon imageWatson = new ImageIcon(getClass().getResource("/images/WATSON.png"),"Watson");
        ImageIcon imageToby = new ImageIcon(getClass().getResource("/images/TOBBY.png"),"Tobby");
        ImageIcon imageEchanger = new ImageIcon(getClass().getResource("/images/SWAP_DISTRICT.png"),"Echanger");
        ImageIcon imagePivoter = new ImageIcon(getClass().getResource("/images/ROTATE.png"),"Pivoter");
        ImageIcon imageJoker = new ImageIcon(getClass().getResource("/images/JOKER.png"),"Joker");

        ActionToken alibi = new ActionToken("Alibi", imageAlibi);
        ActionToken holmes = new ActionToken("Holmes", imageHolmes);
        ActionToken watson = new ActionToken("Watson", imageWatson);
        ActionToken toby = new ActionToken("Toby", imageToby);
        ActionToken echanger = new ActionToken("Echanger", imageEchanger);
        ActionToken pivoter1 = new ActionToken("Pivoter", imagePivoter);
        ActionToken pivoter2 = new ActionToken("Pivoter", imagePivoter);
        ActionToken joker = new ActionToken("Joker", imageJoker);

        ArrayList<ActionToken> listAll = new ArrayList<ActionToken>();
        listAll.add(alibi);
        listAll.add(holmes);
        listAll.add(watson);
        listAll.add(toby);
        listAll.add(echanger);
        listAll.add(pivoter1);
        listAll.add(pivoter2);
        listAll.add(joker);

        mListAllActionToken.add(alibi);
        mListAllActionToken.add(holmes);
        mListAllActionToken.add(watson);
        mListAllActionToken.add(toby);
        mListAllActionToken.add(echanger);
        mListAllActionToken.add(pivoter1);
        mListAllActionToken.add(pivoter2);
        mListAllActionToken.add(joker);

        ArrayList<ActionToken> listbis = new ArrayList<ActionToken>();
        int numberOfElements = 4;
        Random rand = new Random();

        for (int i = 0; i < numberOfElements; i++) {
            int randomIndex = rand.nextInt(listAll.size());
            ActionToken randomElement = listAll.get(randomIndex);
            listAll.remove(randomIndex);
            listbis.add(randomElement);
        }

        ArrayList<String> listName1 = new ArrayList<String>();
        listName1.add(listAll.get(0).getmName());
        listName1.add(listAll.get(1).getmName());
        listName1.add(listAll.get(2).getmName());
        listName1.add(listAll.get(3).getmName());

        ArrayList<String> listName2 = new ArrayList<String>();
        listName2.add(listbis.get(0).getmName());
        listName2.add(listbis.get(1).getmName());
        listName2.add(listbis.get(2).getmName());
        listName2.add(listbis.get(3).getmName());

        mSousListAction1 = listAll;
        mSousListAction2 = listbis;

        mSousListActionString1 = listName1;
        mSousListActionString2 = listName2;
    }

    // Print à qui est le tour.
    public void printWhoTurn() {

        if (turn % 2 != 0) {
            if (sousTurn == 1 || sousTurn == 4)
                System.out.println("C'est à " + Investigator.getmName() + " (" + Investigator.getmRole() + ") de jouer !");
            else
                System.out.println("C'est à " + MrJack.getmName() + " (" + MrJack.getmRole() + ") de jouer !");
        } else {
            if (sousTurn == 1 || sousTurn == 4)
                System.out.println("C'est à " + MrJack.getmName() + " (" + MrJack.getmRole() + ") de jouer !");
            else
                System.out.println("C'est à " + Investigator.getmName() + " (" + Investigator.getmRole() + ") de jouer !");
        }
    }

    // Détermine qui doit jouer en fonction du tour et des sous-tours.
    public String whoTurn() {

        if (turn % 2 != 0) {
            if (sousTurn == 1 || sousTurn == 4)
                return Investigator.getmRole();
            else
                return MrJack.getmRole();
        } else {
            if (sousTurn == 1 || sousTurn == 4)
                return MrJack.getmRole();
            else
                return Investigator.getmRole();
        }
    }

    // Choisi une action parmi une liste d'action.
    public String chooseListAction() {

        if (turn % 2 != 0) { // Si tour impair
            Scanner sc3 = new Scanner(System.in);
            System.out.println("Choisissez une action dans la liste suivante: " + mSousListActionString1);
            String action = sc3.nextLine();
            while (!mSousListActionString1.contains(action)) {
                System.out.println("ERREUR !");
                System.out.println("Choisissez une action dans la liste suivante: "+ mSousListActionString1);
                action = sc3.nextLine();
            }
            mSousListActionString1.remove(action);
            return action;
        }
        else {// Si tour pair
            Scanner sc3 = new Scanner(System.in);
            System.out.println("Choisissez une action dans la liste suivante: " + mSousListActionString2);
            String action = sc3.nextLine();
            while (!mSousListActionString2.contains(action)) {
                System.out.println("ERREUR !");
                System.out.print("Choisissez une action dans la liste suivante: " + mSousListActionString2);
                action = sc3.nextLine();
            }
            mSousListActionString2.remove(action);
            return action;
        }
    }
    /*
    public String chooseListAction2() {

        if (MrJackActionListener.createActionListener(this,MrJackGraphics.getAction1()).equals(MrJackGraphics.getAction1().getIcon().toString())){
            System.out.println("CA MARCHE");
            return "Alibi";}
        else{
            System.out.println("echec");
            return "Oups";}

    }
    */


    // Choix du jeton action.
    public void chooseActionPlayer(String action, String player) {

        switch (action) {
            case "Pivoter" -> {
                Scanner sc1 = new Scanner(System.in);
                System.out.println("Entre le numéro de ligne du district.");
                int districtColonne = sc1.nextInt();
                while (districtColonne > 2) {
                    System.out.println("ERREUR !");
                    System.out.println("Entre le numéro de ligne du district.");
                    districtColonne = sc1.nextInt();
                }
                Scanner sc2 = new Scanner(System.in);
                System.out.println("Entre le numéro de colonne du district.");
                int districtLigne = sc2.nextInt();
                while (districtLigne > 2) {
                    System.out.println("ERREUR !");
                    System.out.println("Entre le numéro de colonne du district.");
                    districtLigne = sc2.nextInt();
                }
                District a = mBoard[districtColonne][districtLigne];
                while (a == mDistrictPivote) {
                    Scanner sc17 = new Scanner(System.in);
                    System.out.println("ERREUR ! Ce district a déjà été pivoté dans ce tour.");
                    System.out.println("Entre le numéro de ligne du district.");
                    int districtColonne1 = sc17.nextInt();
                    while (districtColonne1 > 2) {
                        System.out.println("ERREUR !");
                        System.out.println("Entre le numéro de ligne du district.");
                        districtColonne1 = sc17.nextInt();
                    }
                    Scanner sc18 = new Scanner(System.in);
                    System.out.println("Entre le numéro de colonne du district.");
                    int districtLigne1 = sc18.nextInt();
                    while (districtLigne1 > 2) {
                        System.out.println("ERREUR !");
                        System.out.println("Entre le numéro de colonne du district.");
                        districtLigne1 = sc18.nextInt();
                    }
                    a = mBoard[districtColonne1][districtLigne1];
                }
                Scanner sc4 = new Scanner(System.in);
                System.out.println("Faire pivoter le district vers le SUD, le NORD, l'EST ou l'OUEST ?");
                String orientationDistrict = sc4.nextLine();
                while (mBoard[districtColonne][districtLigne].getmOrientation().equals(orientationDistrict)) {
                    System.out.println("ERREUR ! Le district est déjà orienté vers " + orientationDistrict);
                    System.out.println("Faire pivoter le district vers le SUD, le NORD, l'EST ou l'OUEST ?");
                    orientationDistrict = sc4.nextLine();
                }
                while (!(orientationDistrict.equals("NORD")) && !(orientationDistrict.equals("SUD")) && !(orientationDistrict.equals("EST")) && !(orientationDistrict.equals("OUEST"))) {
                    System.out.println("ERREUR !");
                    System.out.println("Faire pivoter le district vers le SUD, le NORD, l'EST ou l'OUEST ?");
                    orientationDistrict = sc4.nextLine();
                }
                mBoard[districtColonne][districtLigne].setmOrientation(orientationDistrict);
                mDistrictPivote = mBoard[districtColonne][districtLigne];
                System.out.println("Le district " + mBoard[districtColonne][districtLigne].getmNumber() + " est maintenant orienté vers " + mBoard[districtColonne][districtLigne].getmOrientation() + ".");
            }
            case "Joker" -> {
                if (player.equals(Investigator.getmRole())) {
                    Scanner sc5 = new Scanner(System.in);
                    System.out.println("Déplacer Toby, Watson ou Holmes d'une case ?");
                    String deplacementDetective = sc5.nextLine();
                    for (int i = 0; i < mListPosition.length; i++) {
                        if (deplacementDetective.equals(mTableauDetective[1].getmName())) {
                            int a = 1;
                            if ((i == 11) && (mListPosition[i].equals(mTableauDetective[1].getmPosition()))) {
                                mTableauDetective[1].setmPosition(mListPosition[0]);
                                System.out.println(mTableauDetective[a].getmName() + " est maintenant en " + mTableauDetective[a].getmPosition());
                                break;
                            }
                            if (mListPosition[i].equals(mTableauDetective[1].getmPosition())) {
                                mTableauDetective[1].setmPosition(mListPosition[i + 1]);
                                System.out.println(mTableauDetective[a].getmName() + " est maintenant en " + mTableauDetective[a].getmPosition());
                                break;
                            }
                        }
                        if (deplacementDetective.equals(mTableauDetective[0].getmName())) {
                            int a = 0;
                            if (i == 11 && (mListPosition[i].equals(mTableauDetective[0].getmPosition()))) {
                                mTableauDetective[0].setmPosition(mListPosition[0]);
                                System.out.println(mTableauDetective[a].getmName() + " est maintenant en " + mTableauDetective[a].getmPosition());
                                break;
                            }
                            if (mListPosition[i].equals(mTableauDetective[0].getmPosition())) {
                                mTableauDetective[0].setmPosition(mListPosition[i + 1]);
                                System.out.println(mTableauDetective[a].getmName() + " est maintenant en " + mTableauDetective[a].getmPosition());
                                break;
                            }
                        }
                        if (deplacementDetective.equals(mTableauDetective[2].getmName())) {
                            int a = 2;
                            if ((i == 11) && (mListPosition[i].equals(mTableauDetective[2].getmPosition()))) {
                                mTableauDetective[2].setmPosition(mListPosition[0]);
                                System.out.println(mTableauDetective[a].getmName() + " est maintenant en " + mTableauDetective[a].getmPosition());
                                break;
                            }
                            if (mListPosition[i].equals(mTableauDetective[2].getmPosition())) {
                                mTableauDetective[2].setmPosition(mListPosition[i + 1]);
                                System.out.println(mTableauDetective[a].getmName() + " est maintenant en " + mTableauDetective[a].getmPosition());
                                break;
                            }
                        }
                    }
                }
                if (player.equals(MrJack.getmRole())) {
                    Scanner sc16 = new Scanner(System.in);
                    System.out.println("Déplacer un détective d'une case (1) OU ne rien faire (2) ?");
                    int choixJoker = sc16.nextInt();
                    while ((choixJoker != 1) && (choixJoker != 2)) {
                        System.out.println("ERREUR !");
                        System.out.println("Déplacement de 1 ou 2 cases ?");
                        choixJoker = sc16.nextInt();
                    }
                    if (choixJoker == 1) {
                        Scanner sc17 = new Scanner(System.in);
                        System.out.println("Déplacer Toby, Watson ou Holmes d'une case ?");
                        String deplacementDetective = sc17.nextLine();
                        while (!(deplacementDetective.equals("Toby")) && !(deplacementDetective.equals("Watson")) && !(deplacementDetective.equals("Holmes"))) {
                            System.out.println("ERREUR !");
                            System.out.println("Déplacement de 1 ou 2 cases ?");
                            choixJoker = sc16.nextInt();
                        }
                        for (int i = 0; i < mListPosition.length; i++) {
                            if (deplacementDetective.equals(mTableauDetective[1].getmName())) {
                                int a = 1;
                                if ((i == 11) && (mListPosition[i].equals(mTableauDetective[1].getmPosition()))) {
                                    mTableauDetective[1].setmPosition(mListPosition[0]);
                                    System.out.println(mTableauDetective[a].getmName() + " est maintenant en " + mTableauDetective[a].getmPosition());
                                    break;
                                }
                                if (mListPosition[i].equals(mTableauDetective[1].getmPosition())) {
                                    mTableauDetective[1].setmPosition(mListPosition[i + 1]);
                                    System.out.println(mTableauDetective[a].getmName() + " est maintenant en " + mTableauDetective[a].getmPosition());
                                    break;
                                }
                            }
                            if (deplacementDetective.equals(mTableauDetective[0].getmName())) {
                                int a = 0;
                                if (i == 11 && (mListPosition[i].equals(mTableauDetective[0].getmPosition()))) {
                                    mTableauDetective[0].setmPosition(mListPosition[0]);
                                    System.out.println(mTableauDetective[a].getmName() + " est maintenant en " + mTableauDetective[a].getmPosition());
                                    break;
                                }
                                if (mListPosition[i].equals(mTableauDetective[0].getmPosition())) {
                                    mTableauDetective[0].setmPosition(mListPosition[i + 1]);
                                    System.out.println(mTableauDetective[a].getmName() + " est maintenant en " + mTableauDetective[a].getmPosition());
                                    break;
                                }
                            }
                            if (deplacementDetective.equals(mTableauDetective[2].getmName())) {
                                int a = 2;
                                if ((i == 11) && (mListPosition[i].equals(mTableauDetective[2].getmPosition()))) {
                                    mTableauDetective[2].setmPosition(mListPosition[0]);
                                    System.out.println(mTableauDetective[a].getmName() + " est maintenant en " + mTableauDetective[a].getmPosition());
                                    break;
                                }
                                if (mListPosition[i].equals(mTableauDetective[2].getmPosition())) {
                                    mTableauDetective[2].setmPosition(mListPosition[i + 1]);
                                    System.out.println(mTableauDetective[a].getmName() + " est maintenant en " + mTableauDetective[a].getmPosition());
                                    break;
                                }
                            }
                        }
                    } else if (choixJoker == 2) {
                        System.out.println("Mr Jack n'a rien fait !");
                    }

                }
            }
            case "Toby" -> {
                Scanner sc14 = new Scanner(System.in);
                System.out.println("Déplacement de 1 ou 2 cases ?");
                int deplacement = sc14.nextInt();
                while ((deplacement != 1) && (deplacement != 2)) {
                    System.out.println("ERREUR !");
                    System.out.println("Déplacement de 1 ou 2 cases ?");
                    deplacement = sc14.nextInt();
                }
                for (int i = 0; i < mListPosition.length; i++) {
                    if (deplacement == 1) {
                        if ((i == 11) && (mListPosition[i].equals(mTableauDetective[1].getmPosition()))) {
                            mTableauDetective[1].setmPosition(mListPosition[0]);
                            break;
                        }
                        if (mListPosition[i].equals(mTableauDetective[1].getmPosition())) {
                            mTableauDetective[1].setmPosition(mListPosition[i + deplacement]);
                            break;
                        }
                    }
                    if (deplacement == 2) {
                        if ((i == 11) && (mListPosition[i].equals(mTableauDetective[1].getmPosition()))) {
                            mTableauDetective[1].setmPosition(mListPosition[1]);
                            break;
                        }
                        if ((i == 10) && (mListPosition[i].equals(mTableauDetective[1].getmPosition()))) {
                            mTableauDetective[1].setmPosition(mListPosition[0]);
                            break;
                        }
                        if (mListPosition[i].equals(mTableauDetective[1].getmPosition())) {
                            mTableauDetective[1].setmPosition(mListPosition[i + deplacement]);
                            break;
                        }
                    }
                }
                System.out.println("Toby est maintenant en " + mTableauDetective[1].getmPosition());
            }
            case "Watson" -> {
                Scanner sc10 = new Scanner(System.in);
                System.out.println("Déplacement de 1 ou 2 cases ?");
                int deplacement1 = sc10.nextInt();
                while ((deplacement1 != 1) && (deplacement1 != 2)) {
                    System.out.println("ERREUR !");
                    System.out.println("Déplacement de 1 ou 2 cases ?");
                    deplacement1 = sc10.nextInt();
                }
                for (int i = 0; i < mListPosition.length; i++) {
                    if (deplacement1 == 1) {
                        if ((i == 11) && (mListPosition[i].equals(mTableauDetective[0].getmPosition()))) {
                            mTableauDetective[0].setmPosition(mListPosition[0]);
                            break;
                        }
                        if (mListPosition[i].equals(mTableauDetective[0].getmPosition())) {
                            mTableauDetective[0].setmPosition(mListPosition[i + deplacement1]);
                            break;
                        }
                    }
                    if (deplacement1 == 2) {
                        if ((i == 11) && (mListPosition[i].equals(mTableauDetective[0].getmPosition()))) {
                            mTableauDetective[0].setmPosition(mListPosition[1]);
                            break;
                        }
                        if ((i == 10) && (mListPosition[i].equals(mTableauDetective[0].getmPosition()))) {
                            mTableauDetective[0].setmPosition(mListPosition[0]);
                            break;
                        }
                        if (mListPosition[i].equals(mTableauDetective[0].getmPosition())) {
                            mTableauDetective[0].setmPosition(mListPosition[i + deplacement1]);
                            break;
                        }
                    }
                }
                System.out.println("Watson est maintenant en " + mTableauDetective[0].getmPosition());
            }
            case "Holmes" -> {
                Scanner sc11 = new Scanner(System.in);
                System.out.println("Déplacement de 1 ou 2 cases ?");
                int deplacement2 = sc11.nextInt();
                while ((deplacement2 != 1) && (deplacement2 != 2)) {
                    System.out.println("ERREUR !");
                    System.out.println("Déplacement de 1 ou 2 cases ?");
                    deplacement2 = sc11.nextInt();
                }
                for (int i = 0; i < mListPosition.length; i++) {
                    if (deplacement2 == 1) {
                        if ((i == 11) && (mListPosition[i].equals(mTableauDetective[2].getmPosition()))) {
                            mTableauDetective[2].setmPosition(mListPosition[0]);
                            break;
                        }
                        if (mListPosition[i].equals(mTableauDetective[2].getmPosition())) {
                            mTableauDetective[2].setmPosition(mListPosition[i + deplacement2]);
                            break;
                        }
                    }
                    if (deplacement2 == 2) {
                        if ((i == 11) && (mListPosition[i].equals(mTableauDetective[2].getmPosition()))) {
                            mTableauDetective[2].setmPosition(mListPosition[1]);
                            break;
                        }
                        if ((i == 10) && (mListPosition[i].equals(mTableauDetective[2].getmPosition()))) {
                            mTableauDetective[2].setmPosition(mListPosition[0]);
                            break;
                        }
                        if (mListPosition[i].equals(mTableauDetective[2].getmPosition())) {
                            mTableauDetective[2].setmPosition(mListPosition[i + deplacement2]);
                            break;
                        }
                    }
                }
                System.out.println("Holmes est maintenant en " + mTableauDetective[2].getmPosition());
            }
            case "Alibi" -> {
                Random rand = new Random();
                listCharacterAlibi = new ArrayList<>(mList);
                listCharacterAlibi.removeIf(el -> el.getmStatus().equals("Mr Jack"));
                int randomIndex = rand.nextInt(listCharacterAlibi.size());
                BoardCharacter randomElement = listCharacterAlibi.get(randomIndex);
                listCharacterAlibi.remove(randomIndex);
                if (player.equals(MrJack.getmRole())) {
                    MrJack.setmNbHourglass(MrJack.getmNbHourglass() + randomElement.getmNbHourglass());
                    System.out.println("Alibi Card : " + randomElement.getmColor() + ". Tu as " + MrJack.getmNbHourglass() + " sablier(s).");
                } else if (player.equals(Investigator.getmRole())) {
                    mListCardAlibiDetective.add(randomElement);
                    Investigator.setmAlibiCards(mListCardAlibiDetective);
                    Investigator.getmAlibiCards().get(mListCardAlibiDetective.size()-1).setmStatus("Pas Mr Jack");
                    System.out.println("Alibi Card : " + Investigator.getmAlibiCards().get(mListCardAlibiDetective.size()-1).getmColor() + ".");
                    randomElement.setmRecto(false);
                }
            }
            case "Echanger" -> {
                Scanner sc6 = new Scanner(System.in);
                System.out.println("Entre le numéro de ligne du 1er district à échanger.");
                int ligneExchange1 = sc6.nextInt();
                while (ligneExchange1 > 2) {
                    System.out.println("ERREUR !");
                    System.out.println("Entre le numéro de ligne du 1er district à échanger.");
                    ligneExchange1 = sc6.nextInt();
                }
                Scanner sc7 = new Scanner(System.in);
                System.out.println("Entre le numéro de colonne du 1er district à échanger.");
                int columnExchange1 = sc7.nextInt();
                while (columnExchange1 > 2) {
                    System.out.println("ERREUR !");
                    System.out.println("Entre le numéro de colonne du 1er district à échanger.");
                    columnExchange1 = sc7.nextInt();
                }
                Scanner sc8 = new Scanner(System.in);
                System.out.println("Entre le numéro de ligne du 2eme district à échanger.");
                int ligneExchange2 = sc8.nextInt();
                while (ligneExchange2 > 2) {
                    System.out.println("ERREUR !");
                    System.out.println("Entre le numéro de ligne du 2eme district à échanger.");
                    ligneExchange2 = sc8.nextInt();
                }
                Scanner sc9 = new Scanner(System.in);
                System.out.println("Entre le numéro de colonne du 2eme district à échanger.");
                int columnExchange2 = sc9.nextInt();
                while (columnExchange2 > 2) {
                    System.out.println("ERREUR !");
                    System.out.println("Entre le numéro de colonne du 2eme district à échanger.");
                    columnExchange2 = sc9.nextInt();
                }
                while (mBoard[ligneExchange1][columnExchange1] == mBoard[ligneExchange2][columnExchange2]){
                    System.out.println("ERREUR ! District déjà sélectionné.");
                    Scanner sc20 = new Scanner(System.in);
                    System.out.println("Entre le numéro de ligne du 2eme district à échanger.");
                    int ligneExchange3 = sc20.nextInt();
                    while (ligneExchange3 > 2) {
                        System.out.println("ERREUR !");
                        System.out.println("Entre le numéro de ligne du 2eme district à échanger.");
                        ligneExchange3 = sc20.nextInt();
                    }
                    Scanner sc21 = new Scanner(System.in);
                    System.out.println("Entre le numéro de colonne du 2eme district à échanger.");
                    int columnExchange3 = sc21.nextInt();
                    while (columnExchange3 > 2) {
                        System.out.println("ERREUR !");
                        System.out.println("Entre le numéro de colonne du 2eme district à échanger.");
                        columnExchange3 = sc21.nextInt();
                    }
                    ligneExchange1 = ligneExchange3;
                    columnExchange1 = columnExchange3;
                }
                District a = mBoard[ligneExchange1][columnExchange1];
                mBoard[ligneExchange1][columnExchange1] = mBoard[ligneExchange2][columnExchange2];
                mBoard[ligneExchange2][columnExchange2] = a;
                int nb = mBoard[ligneExchange1][ligneExchange1].getmNumber();
                mBoard[ligneExchange1][columnExchange1].setmNumber(mBoard[ligneExchange2][columnExchange2].getmNumber());
                mBoard[ligneExchange2][columnExchange2].setmNumber(nb);
            }
        }
    }

    // Appel à témoin.
    public boolean whoISee()
    {
        int a = 0;
        ArrayList<BoardCharacter> characterISee = new ArrayList<BoardCharacter>();
        ArrayList<Integer> b = new ArrayList<Integer>();
        ArrayList<BoardCharacter> characterIDontSee = new ArrayList<BoardCharacter>();
        for (int k = 0; k < mTableauDetective.length; k++) {
            switch (mTableauDetective[k].getmPosition()) {
                case "b1":
                    if (!(mBoard[0][0].getmWall().equals("NORD"))) {
                        characterISee.add(mBoard[0][0].getmCharacter());
                    }
                    if (!(mBoard[0][0].getmWall().equals("NORD")) && !(mBoard[0][0].getmWall().equals("SUD")) && !(mBoard[1][0].getmWall().equals("NORD"))) {
                        characterISee.add(mBoard[1][0].getmCharacter());
                    }
                    if (!(mBoard[0][0].getmWall().equals("NORD")) && !(mBoard[0][0].getmWall().equals("SUD")) && !(mBoard[1][0].getmWall().equals("NORD")) && !(mBoard[1][0].getmWall().equals("SUD")) && !(mBoard[2][0].getmWall().equals("NORD"))) {
                        characterISee.add(mBoard[2][0].getmCharacter());
                    }
                    else{
                        for (int i = 0; i < mBoard.length; i++)
                            for (int j = 0; j < mBoard[i].length; j++)
                                characterIDontSee.add(mBoard[i][j].getmCharacter());
                    }
                    break;
                case "c1":
                    if (!(mBoard[0][1].getmWall().equals("NORD"))) {
                        characterISee.add(mBoard[0][1].getmCharacter());
                    }
                    if (!(mBoard[0][1].getmWall().equals("NORD")) && !(mBoard[0][1].getmWall().equals("SUD")) && !(mBoard[1][1].getmWall().equals("NORD"))) {
                        characterISee.add(mBoard[1][1].getmCharacter());
                    }
                    if (!(mBoard[0][1].getmWall().equals("NORD")) && !(mBoard[0][1].getmWall().equals("SUD")) && !(mBoard[1][1].getmWall().equals("NORD")) && !(mBoard[1][1].getmWall().equals("SUD")) && !(mBoard[2][1].getmWall().equals("NORD"))) {
                        characterISee.add(mBoard[2][1].getmCharacter());
                    }
                    else{
                        for (int i = 0; i < mBoard.length; i++)
                            for (int j = 0; j < mBoard[i].length; j++)
                                characterIDontSee.add(mBoard[i][j].getmCharacter());
                    }
                    break;
                case "d1":
                    if (!(mBoard[0][2].getmWall().equals("NORD"))) {
                        characterISee.add(mBoard[0][2].getmCharacter());
                    }
                    if (!(mBoard[0][2].getmWall().equals("NORD")) && !(mBoard[0][2].getmWall().equals("SUD")) && !(mBoard[1][2].getmWall().equals("NORD"))){
                        characterISee.add(mBoard[1][2].getmCharacter());
                    }
                    if (!(mBoard[0][2].getmWall().equals("NORD")) && !(mBoard[0][2].getmWall().equals("SUD")) && !(mBoard[1][2].getmWall().equals("NORD")) && !(mBoard[1][2].getmWall().equals("SUD")) && !(mBoard[2][2].getmWall().equals("NORD"))) {
                        characterISee.add(mBoard[2][2].getmCharacter());
                    }
                    else{
                        for (int i = 0; i < mBoard.length; i++)
                            for (int j = 0; j < mBoard[i].length; j++)
                                characterIDontSee.add(mBoard[i][j].getmCharacter());
                    }
                    break;
                case "e2":
                    if (!(mBoard[0][2].getmWall().equals("EST"))) {
                        characterISee.add(mBoard[0][2].getmCharacter());
                    }
                    if (!(mBoard[0][2].getmWall().equals("EST")) && !(mBoard[0][2].getmWall().equals("OUEST")) && !(mBoard[0][1].getmWall().equals("EST"))) {
                        characterISee.add(mBoard[0][1].getmCharacter());
                    }
                    if (!(mBoard[0][2].getmWall().equals("EST")) && !(mBoard[0][2].getmWall().equals("OUEST")) && !(mBoard[0][1].getmWall().equals("EST")) && !(mBoard[0][1].getmWall().equals("OUEST")) && !(mBoard[0][0].getmWall().equals("EST"))) {
                        characterISee.add(mBoard[0][0].getmCharacter());
                    }
                    else{
                        for (int i = 0; i < mBoard.length; i++)
                            for (int j = 0; j < mBoard[i].length; j++)
                                characterIDontSee.add(mBoard[i][j].getmCharacter());
                    }
                    break;
                case "e3":
                    if (!(mBoard[1][2].getmWall().equals("EST"))) {
                        characterISee.add(mBoard[1][2].getmCharacter());
                    }
                    if (!(mBoard[1][2].getmWall().equals("EST")) && !(mBoard[1][2].getmWall().equals("OUEST")) && !(mBoard[1][1].getmWall().equals("EST"))) {
                        characterISee.add(mBoard[1][1].getmCharacter());
                    }
                    if (!(mBoard[1][2].getmWall().equals("EST")) && !(mBoard[1][2].getmWall().equals("OUEST")) && !(mBoard[1][1].getmWall().equals("EST")) && !(mBoard[1][1].getmWall().equals("OUEST")) && !(mBoard[1][0].getmWall().equals("EST"))) {
                        characterISee.add(mBoard[1][0].getmCharacter());
                    }
                    else{
                        for (int i = 0; i < mBoard.length; i++)
                            for (int j = 0; j < mBoard[i].length; j++)
                                characterIDontSee.add(mBoard[i][j].getmCharacter());
                    }
                    break;
                case "e4":
                    if (!(mBoard[2][2].getmWall().equals("EST"))) {
                        characterISee.add(mBoard[2][2].getmCharacter());
                    }
                    if (!(mBoard[2][2].getmWall().equals("EST")) && !(mBoard[2][2].getmWall().equals("OUEST")) && !(mBoard[2][1].getmWall().equals("EST"))) {
                        characterISee.add(mBoard[2][1].getmCharacter());
                    }
                    if (!(mBoard[2][2].getmWall().equals("EST")) && !(mBoard[2][2].getmWall().equals("OUEST")) && !(mBoard[2][1].getmWall().equals("EST")) && !(mBoard[2][1].getmWall().equals("OUEST")) && !(mBoard[2][0].getmWall().equals("EST"))) {
                        characterISee.add(mBoard[2][0].getmCharacter());
                    }
                    else{
                        for (int i = 0; i < mBoard.length; i++)
                            for (int j = 0; j < mBoard[i].length; j++)
                                characterIDontSee.add(mBoard[i][j].getmCharacter());
                    }
                    break;
                case "d5":
                    if (!(mBoard[2][2].getmWall().equals("SUD"))) {
                        characterISee.add(mBoard[2][2].getmCharacter());
                    }
                    if (!(mBoard[2][2].getmWall().equals("SUD")) && !(mBoard[2][2].getmWall().equals("NORD")) && !(mBoard[1][2].getmWall().equals("SUD"))) {
                        characterISee.add(mBoard[1][2].getmCharacter());
                    }
                    if (!(mBoard[2][2].getmWall().equals("SUD")) && !(mBoard[2][2].getmWall().equals("NORD")) && !(mBoard[1][2].getmWall().equals("SUD")) && !(mBoard[1][2].getmWall().equals("NORD")) && !(mBoard[0][2].getmWall().equals("SUD"))) {
                        characterISee.add(mBoard[0][2].getmCharacter());
                    }
                    else{
                        for (int i = 0; i < mBoard.length; i++)
                            for (int j = 0; j < mBoard[i].length; j++)
                                characterIDontSee.add(mBoard[i][j].getmCharacter());
                    }
                    break;
                case "c5":
                    if (!(mBoard[2][1].getmWall().equals("SUD"))) {
                        characterISee.add(mBoard[2][1].getmCharacter());
                    }
                    if (!(mBoard[2][1].getmWall().equals("SUD")) && !(mBoard[2][1].getmWall().equals("NORD")) && !(mBoard[1][1].getmWall().equals("SUD"))) {
                        characterISee.add(mBoard[1][1].getmCharacter());
                    }
                    if (!(mBoard[2][1].getmWall().equals("SUD")) && !(mBoard[2][1].getmWall().equals("NORD")) && !(mBoard[1][1].getmWall().equals("SUD")) && !(mBoard[1][1].getmWall().equals("NORD")) && !(mBoard[0][1].getmWall().equals("SUD"))) {
                        characterISee.add(mBoard[0][1].getmCharacter());
                    }
                    else{
                        for (int i = 0; i < mBoard.length; i++)
                            for (int j = 0; j < mBoard[i].length; j++)
                                characterIDontSee.add(mBoard[i][j].getmCharacter());
                    }
                    break;
                case "b5":
                    if (!(mBoard[2][0].getmWall().equals("SUD"))) {
                        characterISee.add(mBoard[2][0].getmCharacter());
                    }
                    if (!(mBoard[2][0].getmWall().equals("SUD")) && !(mBoard[2][0].getmWall().equals("NORD")) && !(mBoard[1][0].getmWall().equals("SUD"))) {
                        characterISee.add(mBoard[1][0].getmCharacter());
                    }
                    if (!(mBoard[2][0].getmWall().equals("SUD")) && !(mBoard[2][0].getmWall().equals("NORD")) && !(mBoard[1][0].getmWall().equals("SUD")) && !(mBoard[1][0].getmWall().equals("NORD")) && !(mBoard[0][0].getmWall().equals("SUD"))) {
                        characterISee.add(mBoard[0][0].getmCharacter());
                    }
                    else{
                        for (int i = 0; i < mBoard.length; i++)
                            for (int j = 0; j < mBoard[i].length; j++)
                                characterIDontSee.add(mBoard[i][j].getmCharacter());
                    }
                    break;
                case "a4":
                    if (!(mBoard[2][0].getmWall().equals("OUEST"))) {
                        characterISee.add(mBoard[2][0].getmCharacter());
                    }
                    if (!(mBoard[2][0].getmWall().equals("OUEST")) && !(mBoard[2][0].getmWall().equals("EST")) && !(mBoard[2][1].getmWall().equals("OUEST"))) {
                        characterISee.add(mBoard[2][1].getmCharacter());
                    }
                    if (!(mBoard[2][0].getmWall().equals("OUEST")) && !(mBoard[2][0].getmWall().equals("EST")) && !(mBoard[2][1].getmWall().equals("OUEST")) && !(mBoard[2][1].getmWall().equals("EST")) && !(mBoard[2][2].getmWall().equals("OUEST"))) {
                        characterISee.add(mBoard[2][2].getmCharacter());
                    }
                    else{
                        for (int i = 0; i < mBoard.length; i++)
                            for (int j = 0; j < mBoard[i].length; j++)
                                characterIDontSee.add(mBoard[i][j].getmCharacter());
                    }
                    break;
                case "a3":
                    if (!(mBoard[1][0].getmWall().equals("OUEST"))) {
                        characterISee.add(mBoard[1][0].getmCharacter());
                    }
                    if (!(mBoard[1][0].getmWall().equals("OUEST")) && !(mBoard[1][0].getmWall().equals("EST")) && !(mBoard[1][1].getmWall().equals("OUEST"))) {
                        characterISee.add(mBoard[1][1].getmCharacter());
                    }
                    if (!(mBoard[1][0].getmWall().equals("OUEST")) && !(mBoard[1][0].getmWall().equals("EST")) && !(mBoard[1][1].getmWall().equals("OUEST")) && !(mBoard[1][1].getmWall().equals("EST")) && !(mBoard[1][2].getmWall().equals("OUEST"))) {
                        characterISee.add(mBoard[1][2].getmCharacter());
                    }
                    else{
                        for (int i = 0; i < mBoard.length; i++)
                            for (int j = 0; j < mBoard[i].length; j++)
                                characterIDontSee.add(mBoard[i][j].getmCharacter());
                    }
                    break;
                case "a2":
                    if (!(mBoard[0][0].getmWall().equals("OUEST"))) {
                        characterISee.add(mBoard[0][0].getmCharacter());
                    }
                    if (!(mBoard[0][0].getmWall().equals("OUEST")) && !(mBoard[0][0].getmWall().equals("EST")) && !(mBoard[0][1].getmWall().equals("OUEST"))) {
                        characterISee.add(mBoard[0][1].getmCharacter());
                    }
                    if (!(mBoard[0][0].getmWall().equals("OUEST")) && !(mBoard[0][0].getmWall().equals("EST")) && !(mBoard[0][1].getmWall().equals("OUEST")) && !(mBoard[0][1].getmWall().equals("EST")) && !(mBoard[0][2].getmWall().equals("OUEST"))) {
                        characterISee.add(mBoard[0][2].getmCharacter());
                    }
                    else{
                        for (int i = 0; i < mBoard.length; i++)
                            for (int j = 0; j < mBoard[i].length; j++)
                                characterIDontSee.add(mBoard[i][j].getmCharacter());
                    }
                    break;

            }
        }
        // Enlève les doublons de la liste characterIDontSee
        Set set = new HashSet() ;
        set.addAll(characterIDontSee) ;
        ArrayList<BoardCharacter> characterIDontSee2 = new ArrayList<BoardCharacter>(set) ;

        // Enlève Mr Jack de la liste characterIDontSee2 s'il est présent
        for(int i = 0 ; i < characterIDontSee2.size(); i++) {
            if (characterIDontSee2.get(i).getmStatus().equals("Mr Jack")) {
                characterIDontSee2.remove(i);
            }
        }
        // Enlève Mr Jack de la liste characterISee s'il est présent
        for(int i = 0 ; i < characterISee.size(); i++) {
            if (characterISee.get(i).getmStatus().equals("Mr Jack")) {
                characterISee.remove(i);
                b.add(1);
            }
            else {
                b.add(0);
            }
        }
        // Enlève les district de characterIDontSee2, vue par un autre détective
        for (int i = 0 ; i < characterISee.size() ; i ++) {
            for (int j = 0 ; j < characterIDontSee2.size() ; j++) {
                if (characterIDontSee2.get(j) == characterISee.get(i)) {
                    characterIDontSee2.remove(j);
                }
            }
        }

        if (b.contains(1)) {
            System.out.println("Les détectives voient Mr Jack :)");
            for(int i = 0 ; i < characterIDontSee2.size(); i++){
                characterIDontSee2.get(i).setmRecto(false);
            }
            return true;
        } else {
            System.out.println("Le détective ne voient pas Mr Jack.");
            for(int i = 0 ; i < characterISee.size(); i++){
                characterISee.get(i).setmRecto(false);
            }
            return false;
        }
    }

    // Retourne le district.
    public void turnOverDistrict() {

        ImageIcon videDistrict = new ImageIcon(getClass().getResource("/images/vide.png"));
        ImageIcon videCrossDistrict = new ImageIcon(getClass().getResource("/images/vide_cross.png"));
        for (int i = 0; i < mBoard.length; i++)
            for (int j = 0; j < mBoard[i].length; j++)
                if (!(mBoard[i][j].getmCharacter().getmRecto()) && (!(mBoard[i][j].getmCharacter().getmColor().equals("vide")) && !(mBoard[i][j].getmCharacter().getmColor().equals("videm")))) {
                    if (!(mBoard[i][j].getmCharacter().getmColor().equals("MARRON"))){
                        mBoard[i][j].getmCharacter().setmImageDistrict(videDistrict);
                        mBoard[i][j].getmCharacter().setmColor("vide");
                    }

                }


    }

    // Met à jour le plateau.
    public void updateBoardDistrict() {

        System.out.println(" ");
        System.out.println("PLATEAU :");
        System.out.println(" ");
        for (int i = 0; i < mBoard.length; i++)
            for (int j = 0; j < mBoard[i].length; j++)
                if (!mBoard[i][j].getmCharacter().getmColor().equals("vide"))
                    System.out.println("Le district " + mBoard[i][j].getmNumber() + " (ligne : " + i + ", colonne : " + j + ") est associé au personnage " + mBoard[i][j].getmCharacter().getmColor() + " et est orienté vers " + mBoard[i][j].getmOrientation() + " et le mur est vers " + mBoard[i][j].orientationWall() + ".");
                else
                    System.out.println("Le district " + mBoard[i][j].getmNumber() + " (ligne : " + i + ", colonne : " + j + ") est " + mBoard[i][j].getmCharacter().getmColor() + " et est orienté vers " + mBoard[i][j].getmOrientation() + " et le mur est vers " + mBoard[i][j].orientationWall() + ".");

    }

    // Met à jour la position des détéctives.
    public void updatePositionDetective(DetectiveCharacter[] tableauDetective) {

        System.out.println(" ");
        System.out.println(tableauDetective[0].getmName() + " est sur la case " + tableauDetective[0].getmPosition());
        System.out.println(tableauDetective[1].getmName() + " est sur la case " + tableauDetective[1].getmPosition());
        System.out.println(tableauDetective[2].getmName() + " est sur la case " + tableauDetective[2].getmPosition());
        System.out.println(" ");

    }

    // Arrête le programme si le jeu est fini.
    public boolean isGameOver(String role)
    {
        if (role.equals("Mr Jack")){
            System.out.println("GAME OVER ! ");
            System.out.println("BRAVO ! " + Investigator.getmName() + " (" + Investigator.getmRole() + ") A GAGNÉ :)" );
            System.out.println("Bien tenté " + MrJack.getmName() + " (" + MrJack.getmRole() + "), mais tu as perdu..." );
            System.exit(1);
        }
        else if (role.equals("Enquêteur")){
            System.out.println("GAME OVER ! ");
            System.out.println("BRAVO ! " + MrJack.getmName() + " (" + MrJack.getmRole() + ") A GAGNÉ :)" );
            System.out.println("Bien tenté " + Investigator.getmName() + " (" + Investigator.getmRole() + "), mais tu as perdu..." );
            System.exit(1);
        }
        return true;
    }
}

