import java.util.Scanner;

public class MrJack
{
    // Attributes

    private static String mRole;
    private static String mName;
    private static int mNbHourglass;

    // Constructor

    public MrJack()
    {
        mRole = "Mr Jack";
        mName = "player1";
    }

    public static String getmRole(){ return mRole;}
    public static void setmRole(String role){ mRole = role;}

    public static String getmName(){ return mName;}

    public static int getmNbHourglass(){ return mNbHourglass;}
    public static void setmNbHourglass(int nbHourglass){ mNbHourglass = nbHourglass;}

    // Methods

    // Défini qui est Mr Jack.
    public static void choosePlayerMrJack()  {
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Qui veut être Mr Jack ?");
        mName = sc1.nextLine();
    }

}
