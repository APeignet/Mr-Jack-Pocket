import javax.swing.*;

public class ActionToken {

    // Attributes

    private final String mName;
    private final ImageIcon mImageToken;

    // Constructor

    public ActionToken(String name, ImageIcon imageToken){
        this.mName = name;
        this.mImageToken = imageToken;
    }

    // Getters et Setters

    public String getmName(){
        return mName;
    }

    public ImageIcon getmImageToken(){
        return mImageToken;
    }

}
