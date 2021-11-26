import javax.swing.ImageIcon;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class MrJackGraphics {

    private final String[] nListPosition = {"a1", "b1", "c1", "d1", "e1", "a2", "e2", "a3", "e3", "a4", "e4", "a5","b5", "c5", "d5","e5"};
    private final Game game;
    private final JFrame frame;
    private JPanel southPanel;
    private JPanel northPanel;
    private JPanel centerPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private static JButton action1;
    private static JButton action2;
    private static JButton action3;
    private static JButton action4;
    private JLabel label1 = new JLabel("Jetons d'action");
    private JLabel label2 = new JLabel(" ");

    public MrJackGraphics(Game game) {
        this.game = game ;
        this.frame = new JFrame("MrJackPocket");
        this.southPanel = new JPanel();
        this.northPanel = new JPanel();
        this.centerPanel = new JPanel();
        this.leftPanel = new JPanel();
        this.rightPanel = new JPanel();
        action1 = new JButton();
        action2 = new JButton();
        action3 = new JButton();
        action4 = new JButton();
    }

    public static JButton getAction1(){ return action1; }
    public static JButton getAction2(){ return action2; }
    public static JButton getAction3(){ return action3; }
    public static JButton getAction4(){ return action4; }

    public JFrame getFrame(){ return this.frame; }

    public void InitialisationPanel() {

        northPanel();
        leftPanel();
        rightPanel();
        centerPanel();
        southPanel();
        frame.repaint();
        frame.add(northPanel, BorderLayout.NORTH);
        frame.add(leftPanel, BorderLayout.LINE_START);
        frame.add(rightPanel, BorderLayout.LINE_END);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(southPanel, BorderLayout.SOUTH);

        frame.setSize(1200,950);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void updatePanel(){

        northPanel();
        leftPanel();
        rightPanel();
        centerPanel();
        southPanel();
        frame.remove(northPanel);
        frame.remove(leftPanel);
        frame.remove(rightPanel);
        frame.remove(centerPanel);
        frame.remove(southPanel);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(northPanel, BorderLayout.NORTH);
        frame.add(leftPanel, BorderLayout.LINE_START);
        frame.add(rightPanel, BorderLayout.LINE_END);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(southPanel, BorderLayout.SOUTH);
        frame.setVisible(true);

    }

    public void southPanel(){

        southPanel.removeAll();
        southPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        southPanel.setPreferredSize(new Dimension(1550, 150));
        southPanel.add(new Label("Sabliers"));
        southPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        ImageIcon imageSablier = new ImageIcon(getClass().getResource("/images/hourglass.png"));

        for(int i = 0; i < MrJack.getmNbHourglass(); i++) {
            JLabel sablier = new JLabel(imageSablier);
            southPanel.add(sablier);
        }
        southPanel.repaint();
    }

    public void centerPanel(){
        centerPanel.removeAll();
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        centerPanel.setPreferredSize(new Dimension(750, 750));
        centerPanel.setLayout(new GridLayout(5, 5));


        JButton tobby = new JButton(game.getmListAllActionToken().get(3).getmImageToken());
        JButton watson = new JButton(game.getmListAllActionToken().get(2).getmImageToken());
        JButton sherlock = new JButton(game.getmListAllActionToken().get(1).getmImageToken());

        JButton bouton1 = new JButton(rotateImageIcon(game.getmBoard()[0][0].getmCharacter().getmImageDistrict(),game.getmBoard()[0][0].getmOrientation()));
        JButton bouton2 = new JButton(rotateImageIcon(game.getmBoard()[0][1].getmCharacter().getmImageDistrict(),game.getmBoard()[0][1].getmOrientation()));
        JButton bouton3 = new JButton(rotateImageIcon(game.getmBoard()[0][2].getmCharacter().getmImageDistrict(),game.getmBoard()[0][2].getmOrientation()));
        JButton bouton4 = new JButton(rotateImageIcon(game.getmBoard()[1][0].getmCharacter().getmImageDistrict(),game.getmBoard()[1][0].getmOrientation()));
        JButton bouton5 = new JButton(rotateImageIcon(game.getmBoard()[1][1].getmCharacter().getmImageDistrict(),game.getmBoard()[1][1].getmOrientation()));
        JButton bouton6 = new JButton(rotateImageIcon(game.getmBoard()[1][2].getmCharacter().getmImageDistrict(),game.getmBoard()[1][2].getmOrientation()));
        JButton bouton7 = new JButton(rotateImageIcon(game.getmBoard()[2][0].getmCharacter().getmImageDistrict(),game.getmBoard()[2][0].getmOrientation()));
        JButton bouton8 = new JButton(rotateImageIcon(game.getmBoard()[2][1].getmCharacter().getmImageDistrict(),game.getmBoard()[2][1].getmOrientation()));
        JButton bouton9 = new JButton(rotateImageIcon(game.getmBoard()[2][2].getmCharacter().getmImageDistrict(),game.getmBoard()[2][2].getmOrientation()));


        for (int i = 0; i < 6; i++) {
            if (game.getmTableauDetective()[0].getmPosition().equals(nListPosition[i])) {
                centerPanel.add(watson);
            }
            else if (game.getmTableauDetective()[1].getmPosition().equals(nListPosition[i])) {
                centerPanel.add(tobby);
            }
            else if (game.getmTableauDetective()[2].getmPosition().equals(nListPosition[i])) {
                centerPanel.add(sherlock);
            }
            else {
                JLabel caseVide = new JLabel();
                centerPanel.add(caseVide);
            }
        }

        centerPanel.add(bouton1);
        centerPanel.add(bouton2);
        centerPanel.add(bouton3);

        for (int i = 6; i < 8; i++) {
            if (game.getmTableauDetective()[0].getmPosition().equals(nListPosition[i])) {
                centerPanel.add(watson);
            }
            else if (game.getmTableauDetective()[1].getmPosition().equals(nListPosition[i])) {
                centerPanel.add(tobby);
            }
            else if (game.getmTableauDetective()[2].getmPosition().equals(nListPosition[i])) {
                centerPanel.add(sherlock);
            }
            else {
                JLabel caseVide = new JLabel();
                centerPanel.add(caseVide);
            }
        }

        centerPanel.add(bouton4);
        centerPanel.add(bouton5);
        centerPanel.add(bouton6);

        for (int i = 8; i < 10; i++) {
            if (game.getmTableauDetective()[0].getmPosition().equals(nListPosition[i])) {
                centerPanel.add(watson);
            }
            else if (game.getmTableauDetective()[1].getmPosition().equals(nListPosition[i])) {
                centerPanel.add(tobby);
            }
            else if (game.getmTableauDetective()[2].getmPosition().equals(nListPosition[i])) {
                centerPanel.add(sherlock);
            }
            else {
                JLabel caseVide = new JLabel();
                centerPanel.add(caseVide);
            }
        }

        centerPanel.add(bouton7);
        centerPanel.add(bouton8);
        centerPanel.add(bouton9);

        for (int i = 10; i < 16; i++) {
            if (game.getmTableauDetective()[0].getmPosition().equals(nListPosition[i])) {
                centerPanel.add(watson);
            }
            else if (game.getmTableauDetective()[1].getmPosition().equals(nListPosition[i])) {
                centerPanel.add(tobby);
            }
            else if (game.getmTableauDetective()[2].getmPosition().equals(nListPosition[i])) {
                centerPanel.add(sherlock);
            }
            else {
                JLabel caseVide = new JLabel();
                centerPanel.add(caseVide);
            }
        }
        centerPanel.repaint();
    }

    public void northPanel(){
        northPanel.removeAll();
        JLabel tour = new JLabel(" TOUR " + String.valueOf(game.getTurn()));
        JLabel sousTour = new JLabel(" Sous-tour " + String.valueOf(game.getSousTurn()));
        String whoTurnName;
        if (game.whoTurn().equals("Mr Jack"))
            whoTurnName = MrJack.getmName();
        else
            whoTurnName = Investigator.getmName();
        JLabel role = new JLabel(" C'est à " + whoTurnName + " (" + game.whoTurn() + ") de jouer ! :)");
        northPanel.setLayout(new GridLayout(3,1));
        role.setHorizontalAlignment(SwingConstants.CENTER);
        sousTour.setHorizontalAlignment(SwingConstants.CENTER);
        tour.setHorizontalAlignment(SwingConstants.CENTER);
        northPanel.add(tour);
        northPanel.add(sousTour);
        northPanel.add(role);

        northPanel.repaint();
    }

    public void leftPanel()
    {
        leftPanel.removeAll();
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        leftPanel.setPreferredSize(new Dimension(200, 750));
        leftPanel.add(new Label("Cartes Alibi")); //ajoute un texte pour décrire le panneau

        leftPanel.setLayout(new GridLayout(5, 2));

        if (Investigator.getmAlibiCards() == null) {
            JLabel label = new JLabel();
        } else {
            for (int i = 0; i < Investigator.getmAlibiCards().size(); i++) {
                JLabel label = new JLabel(Investigator.getmAlibiCards().get(i).getmImageAlibi());
                leftPanel.add(label);
            }
        }

        leftPanel.repaint();

    }

    public void rightPanel()
    {

        rightPanel.remove(label1);
        rightPanel.remove(label2);

        rightPanel.remove(action1);
        rightPanel.remove(action2);
        rightPanel.remove(action3);
        rightPanel.remove(action4);

        rightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        rightPanel.setPreferredSize(new Dimension(250, 750));
        rightPanel.setLayout(new GridLayout(6, 1));

        rightPanel.add(label1); //ajoute un texte pour décrire le panneau
        rightPanel.add(label2);


        if (game.getTurn() % 2 != 0) {
            action1.setIcon(game.getmSousListAction1().get(0).getmImageToken());
            action2.setIcon(game.getmSousListAction1().get(1).getmImageToken());
            action3.setIcon(game.getmSousListAction1().get(2).getmImageToken());
            action4.setIcon(game.getmSousListAction1().get(3).getmImageToken());


        }
        else {
            action1.setIcon(game.getmSousListAction2().get(0).getmImageToken());
            action2.setIcon(game.getmSousListAction2().get(1).getmImageToken());
            action3.setIcon(game.getmSousListAction2().get(2).getmImageToken());
            action4.setIcon(game.getmSousListAction2().get(3).getmImageToken());

        }

        action1.addActionListener(MrJackActionListener.createActionListener(game,action1,this));
        action2.addActionListener(MrJackActionListener.createActionListener(game,action2,this));
        action3.addActionListener(MrJackActionListener.createActionListener(game,action3,this));
        action4.addActionListener(MrJackActionListener.createActionListener(game,action4,this));



        rightPanel.add(action1);
        rightPanel.add(action2);
        rightPanel.add(action3);
        rightPanel.add(action4);

        if (MrJackActionListener.test.contains("bouton1Clique")) {rightPanel.remove(action1);}
        if (MrJackActionListener.test.contains("bouton2Clique")) {rightPanel.remove(action2);}
        if (MrJackActionListener.test.contains("bouton3Clique")) {rightPanel.remove(action3);}
        if (MrJackActionListener.test.contains("bouton4Clique")) {rightPanel.remove(action4);}

        rightPanel.repaint();
    }

    public ImageIcon rotateImageIcon(ImageIcon picture, String orientation) {
        double angle = switch (orientation) {
            case "NORD" -> 0;
            case "EST" -> Math.PI / 2;
            case "SUD" -> Math.PI;
            default -> 3 * Math.PI/2;
        };
        int w = picture.getIconWidth();
        int h = picture.getIconHeight();
        int type = BufferedImage.TYPE_INT_RGB;
        BufferedImage image = new BufferedImage(h, w, type);
        Graphics2D g2 = image.createGraphics();
        double x = (h - w)/2.0;
        double y = (w - h)/2.0;
        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
        at.rotate(angle, w/2.0, h/2.0);
        g2.drawImage(picture.getImage(), at, null);
        g2.dispose();
        picture = new ImageIcon(image);

        return picture;
    }


}
