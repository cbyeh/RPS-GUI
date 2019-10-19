import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RPSGUIGame extends JFrame {

    // Buttons for the user to enter their move
    private JButton rockButton, paperButton, scissorsButton;
    // Labels to display the number of wins/losses/ties
    private JLabel statusC, statusU, statusT, statusW;
    // Images and labels to display the computer and user's moves and the outcome of a match-up
    private ImageIcon rockImage, paperImage, scissorsImage;
    private JLabel compPlay, userPlay;
    private JLabel outcome;
    // The game object
    private RPSGame game;
    private int reply;

    // Game logic
    public RPSGUIGame() {
        // Initializes the window, place bets
        super("Rock, Paper, Scissors, Go!");
        setSize(500, 500);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(Color.black);
        setResizable(false);
        reply = JOptionPane.showConfirmDialog(null,
                "Do you want to place a bet?", null, JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            boolean flag = true;
            while (flag) {
                String bet = JOptionPane.showInputDialog("What are your stakes?");
                try {
                    int betNum = Integer.parseInt(bet);
                    game = new RPSGame(betNum);
                    flag = false;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid. Use an integer");
                }
            }
        } else // Default constructor, no bets
            game = new RPSGame();
        // Init images
        rockImage = new ImageIcon("../assets/rock.jpg");
        paperImage = new ImageIcon("../assets/paper.jpg");
        scissorsImage = new ImageIcon("../assets/scissors.jpg");
        // Start game
        userPlay = new JLabel();
        userPlay.setVerticalTextPosition(SwingConstants.BOTTOM);
        userPlay.setHorizontalTextPosition(SwingConstants.CENTER);
        userPlay.setBorder(BorderFactory.createLineBorder(Color.black, 5));
        userPlay.setForeground(Color.cyan);
        compPlay = new JLabel();
        compPlay.setVerticalTextPosition(SwingConstants.BOTTOM);
        compPlay.setHorizontalTextPosition(SwingConstants.CENTER);
        compPlay.setBorder(BorderFactory.createLineBorder(Color.black, 5));
        compPlay.setForeground(Color.cyan);
        // Create outcome
        outcome = new JLabel();
        outcome.setHorizontalTextPosition(SwingConstants.CENTER);
        outcome.setForeground(Color.pink);
        // Set outcome panel
        JPanel imageOutcomePanel = new JPanel();
        imageOutcomePanel.setBackground(Color.black);
        imageOutcomePanel.setLayout(new BorderLayout());
        imageOutcomePanel.add(compPlay, BorderLayout.EAST);
        imageOutcomePanel.add(userPlay, BorderLayout.WEST);
        imageOutcomePanel.add(outcome, BorderLayout.SOUTH);
        // Creates the labels for the status of the game (number of wins, losses, and ties);
        // The status labels will be displayed in a single panel
        statusC = new JLabel("Computer Wins: " + game.getComputerWins());
        statusU = new JLabel("User Wins: " + game.getUserWins());
        statusT = new JLabel("Ties: " + game.getTies());
        statusC.setForeground(Color.white);
        statusU.setForeground(Color.white);
        statusT.setForeground(Color.white);
        JPanel statusPanel = new JPanel();
        statusPanel.setBackground(Color.black);
        statusPanel.add(statusC);
        statusPanel.add(statusU);
        statusPanel.add(statusT);
        if (reply == JOptionPane.YES_OPTION) {
            statusW = new JLabel("Your Balance: $" + game.getWallet());
            statusW.setForeground(Color.green);
            statusPanel.add(statusW);
        }
        // The play and status panels are nested in a single panel
        JPanel gamePanel = new JPanel();
        gamePanel.setPreferredSize(new Dimension(250, 250));
        gamePanel.setBackground(Color.black);
        gamePanel.add(imageOutcomePanel);
        gamePanel.add(statusPanel);
        // Creates the buttons and displays them in a control panel;
        // One listener is used for all three buttons
        rockButton = new JButton("Play Rock");
        rockButton.addActionListener(new GameListener());
        paperButton = new JButton("Play Paper");
        paperButton.addActionListener(new GameListener());
        scissorsButton = new JButton("Play Scissors");
        scissorsButton.addActionListener(new GameListener());
        JPanel controlPanel = new JPanel();
        controlPanel.add(rockButton);
        controlPanel.add(paperButton);
        controlPanel.add(scissorsButton);
        controlPanel.setBackground(Color.black);
        // The gaming and control panel are added to the window
        contentPane.add(gamePanel, BorderLayout.CENTER);
        contentPane.add(controlPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    /* Determines which button was clicked and updates the game accordingly */
    private class GameListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            RPSGame.Choices compChoice = game.generateComputerPlay();
            if (event.getSource() == rockButton) {
                if (reply == JOptionPane.YES_OPTION)
                    game.findWinner(compChoice, RPSGame.Choices.ROCK, game.getBetAmount());
                else
                    game.findWinner(compChoice, RPSGame.Choices.ROCK);
                userPlay.setIcon(rockImage);
                if (game.getUserStatus() == RPSGame.Status.WON)
                    outcome.setText("Rock beats scissors. You win!");
                else if (game.getUserStatus() == RPSGame.Status.LOST)
                    outcome.setText("Paper beats rock. You lose!");
                else outcome.setText("It's a tie!");
            }
            if (event.getSource() == paperButton) {
                if (reply == JOptionPane.YES_OPTION)
                    game.findWinner(compChoice, RPSGame.Choices.PAPER, game.getBetAmount());
                else
                    game.findWinner(compChoice, RPSGame.Choices.PAPER);
                userPlay.setIcon(paperImage);
                if (game.getUserStatus() == RPSGame.Status.WON)
                    outcome.setText("Paper beats rock. You win!");
                else if (game.getUserStatus() == RPSGame.Status.LOST)
                    outcome.setText("Scissors beats paper. You lose!");
                else outcome.setText("It's a tie!");
            }
            if (event.getSource() == scissorsButton) {
                if (reply == JOptionPane.YES_OPTION)
                    game.findWinner(compChoice, RPSGame.Choices.SCISSORS, game.getBetAmount());
                else
                    game.findWinner(compChoice, RPSGame.Choices.SCISSORS);
                userPlay.setIcon(scissorsImage);
                if (game.getUserStatus() == RPSGame.Status.WON)
                    outcome.setText("Scissors beats paper. You win!");
                else if (game.getUserStatus() == RPSGame.Status.LOST)
                    outcome.setText("Rock beats scissors. You lose!");
                else outcome.setText("It's a tie!");
            }
            // Deciding computer's choice image
            if (compChoice == RPSGame.Choices.ROCK)
                compPlay.setIcon(rockImage);
            else if (compChoice == RPSGame.Choices.PAPER)
                compPlay.setIcon(paperImage);
            else compPlay.setIcon(scissorsImage);
            statusC.setText("Computer Wins: " + game.getComputerWins());
            statusU.setText("User Wins: " + game.getUserWins());
            statusT.setText("Ties: " + game.getTies());
            if (reply == JOptionPane.YES_OPTION)
                statusW.setText("Your Balance: $" + game.getWallet());
            userPlay.setText("Your move");
            compPlay.setText("Computer's move");
            outcome.setHorizontalTextPosition(SwingConstants.CENTER);
        }
    }

    // Runner
    public static void main(String args[]) {
        RPSGUIGame frame = new RPSGUIGame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
