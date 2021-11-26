import javax.swing.*;

public class BoardCharacter
{
    // Attributes

    private final ImageIcon mImageAlibi;
    private ImageIcon mImageDistrict;
    private String mStatus;
    private String mColor;
    private final int mNbHourglass;
    private boolean mRecto;

    // Constructor

    public BoardCharacter(String color, int nbHourglass, ImageIcon imageDistrict, ImageIcon imageAlibi)
    {
        this.mColor = color;
        this.mStatus = "Rien";
        this.mNbHourglass = nbHourglass;
        this.mRecto = true;
        this.mImageDistrict = imageDistrict;
        this.mImageAlibi = imageAlibi;
    }

    // Getters et Setters

    public ImageIcon getmImageDistrict() {
        return this.mImageDistrict;
    }
    public void setmImageDistrict(ImageIcon imageDistrict){this.mImageDistrict = imageDistrict;}

    public ImageIcon getmImageAlibi() {
        return this.mImageAlibi;
    }

    public String getmStatus()
    {
        return this.mStatus;
    }
    public void setmStatus(String status)
    {
        this.mStatus = status;
    }

    public String getmColor()
    {
        return this.mColor;
    }
    public void setmColor(String color)
    {
        this.mColor = color;
    }

    public int getmNbHourglass()
    {
        return this.mNbHourglass;
    }

    public boolean getmRecto()
    {
        return this.mRecto;
    }
    public void setmRecto(boolean recto)
    {
        this.mRecto = recto;
    }

}
