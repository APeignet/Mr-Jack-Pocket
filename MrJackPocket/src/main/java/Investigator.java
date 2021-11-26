import java.util.ArrayList;
import java.util.Scanner;

public class Investigator
{
    // Attributes

    private static ArrayList<BoardCharacter> mAlibiCards;
    private static String mName;
    private static String mRole;


    // Constructor

    public Investigator()
    {
        mName = "player2";
        mRole = "Enquêteur";
    }

    public static String getmName(){ return mName; }

    public static String getmRole(){ return mRole; }
    public static void setmRole(String role){ mRole = role;}

    public static ArrayList<BoardCharacter> getmAlibiCards(){ return mAlibiCards; }
    public static void setmAlibiCards(ArrayList<BoardCharacter> alibiCards){ mAlibiCards = alibiCards; }


    // Methods

    // Défini qui est Enquêteur.
    public static void choosePlayerInvestigator() {
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Qui veut être Enquêteur ?");
        mName = sc1.nextLine();
    }

}
