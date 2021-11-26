import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MrJackActionListener implements ActionListener {

    public static ArrayList<String> test = new ArrayList<>();
    private Game game;
    private JButton button;
    private MrJackGraphics mrGraphics;

    public MrJackActionListener(Game game, JButton button, MrJackGraphics mrGraphics) {
        this.game = game;
        this.button = button;
        this.mrGraphics = mrGraphics;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == MrJackGraphics.getAction1() && !(test.contains("bouton1Clique"))){
            if (game.getTurn() % 2 != 0) {
                System.out.println("------" + game.getmSousListAction1().get(0).getmName() + "------");
                game.chooseActionPlayer(game.getmSousListAction1().get(0).getmName(), game.whoTurn());
            } else {
                System.out.println("------" + game.getmSousListAction2().get(0).getmName() + "------");
                game.chooseActionPlayer(game.getmSousListAction2().get(0).getmName(), game.whoTurn());
            }
            game.turnOverDistrict();
            test.add("bouton1Clique");
            game.setSousTurn(game.getSousTurn()+1);
            if (game.getSousTurn() == 5)
                game.setSousTurn(1);
            mrGraphics.updatePanel();
        }

        else if (source == MrJackGraphics.getAction2() && !(test.contains("bouton2Clique"))) {
            if (game.getTurn() % 2 != 0) {
                System.out.println("------" + game.getmSousListAction1().get(1).getmName() + "------");
                game.chooseActionPlayer(game.getmSousListAction1().get(1).getmName(), game.whoTurn());
            } else {
                System.out.println("------" + game.getmSousListAction2().get(1).getmName() + "------");
                game.chooseActionPlayer(game.getmSousListAction2().get(1).getmName(), game.whoTurn());
            }
            game.turnOverDistrict();
            test.add("bouton2Clique");
            game.setSousTurn(game.getSousTurn()+1);
            if (game.getSousTurn() == 5)
                game.setSousTurn(1);
            mrGraphics.updatePanel();
        }
        else if (source == MrJackGraphics.getAction3() && !(test.contains("bouton3Clique"))) {
            if (game.getTurn() % 2 != 0) {
                System.out.println("------" + game.getmSousListAction1().get(2).getmName() + "------");
                game.chooseActionPlayer(game.getmSousListAction1().get(2).getmName(), game.whoTurn());
            } else {
                System.out.println("------" + game.getmSousListAction2().get(2).getmName() + "------");
                game.chooseActionPlayer(game.getmSousListAction2().get(2).getmName(), game.whoTurn());
            }
            game.turnOverDistrict();
            test.add("bouton3Clique");
            game.setSousTurn(game.getSousTurn()+1);
            if (game.getSousTurn() == 5)
                game.setSousTurn(1);
            mrGraphics.updatePanel();
        }
        else if (source == MrJackGraphics.getAction4() && !(test.contains("bouton4Clique"))) {
            if (game.getTurn() % 2 != 0) {
                System.out.println("------" + game.getmSousListAction1().get(3).getmName() + "------");
                game.chooseActionPlayer(game.getmSousListAction1().get(3).getmName(), game.whoTurn());
            } else {
                System.out.println("------" + game.getmSousListAction2().get(3).getmName() + "------");
                game.chooseActionPlayer(game.getmSousListAction2().get(3).getmName(), game.whoTurn());
            }
            game.turnOverDistrict();
            test.add("bouton4Clique");
            game.setSousTurn(game.getSousTurn()+1);
            if (game.getSousTurn() == 5)
                game.setSousTurn(1);
            mrGraphics.updatePanel();
        }
    }

    public static ActionListener createActionListener(Game game, JButton button, MrJackGraphics mrGraphics) {
        return new MrJackActionListener(game, button, mrGraphics);
    }

}