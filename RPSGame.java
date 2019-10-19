public class RPSGame {

    // Track of wins, losses, and ties
    private static int computerWins;
    private static int userWins;
    private static int ties;
    // Hands that may be played
    enum Choices {ROCK, PAPER, SCISSORS}
    // Outcome of the game
    enum Status {WON, LOST, TIE}
    private Status userStatus;
    // How much money is being bet and current amount won or lost
    private int wallet;
    private int betAmount;

    // Default constructor, a game with no stakes
    protected RPSGame() {
        computerWins = 0;
        userWins = 0;
        ties = 0;
    }

    // Game with stakes
    protected RPSGame(int bet) {
        this();
        betAmount = bet;
    }

    // Even distribution to roll rock, paper, or scissors
    protected Choices generateComputerPlay() {
        int random = (int) (Math.random() * 3);
        switch (random) {
            case 0:
                return Choices.ROCK;
            case 1:
                return Choices.PAPER;
        }
        return Choices.SCISSORS; // Random generated 2
    }

    // Verbose logic to find outcome of the match
    protected void findWinner(Choices computerChoice, Choices userChoice) {
        if (computerChoice == Choices.ROCK) {
            if (userChoice == Choices.ROCK)
                userStatus = Status.TIE;
            if (userChoice == Choices.PAPER)
                userStatus = Status.WON;
            if (userChoice == Choices.SCISSORS)
                userStatus = Status.LOST;
        } else if (computerChoice == Choices.PAPER) {
            if (userChoice == Choices.ROCK)
                userStatus = Status.LOST;
            if (userChoice == Choices.PAPER)
                userStatus = Status.TIE;
            if (userChoice == Choices.SCISSORS)
                userStatus = Status.WON;
        } else if (computerChoice == Choices.SCISSORS) {
            if (userChoice == Choices.ROCK)
                userStatus = Status.WON;
            if (userChoice == Choices.PAPER)
                userStatus = Status.LOST;
            if (userChoice == Choices.SCISSORS)
                userStatus = Status.TIE;
        }
        switch (userStatus) {
            case WON:
                userWins++;
                break;
            case LOST:
                computerWins++;
                break;
            case TIE:
                ties++;
                break;
        }
    }

    // Overloaded above method with bets considered
    protected void findWinner(Choices computerChoice, Choices userChoice, int betAmount) {
        findWinner(computerChoice, userChoice);
        switch (userStatus) {
            case WON:
                wallet += betAmount;
                break;
            case LOST:
                wallet -= betAmount;
                break;
            case TIE:
                break;
        }
    }

    protected Status getUserStatus() {
        return userStatus;
    }

    protected int getUserWins() {
        return userWins;
    }

    protected int getComputerWins() {
        return computerWins;
    }

    protected int getTies() {
        return ties;
    }

    protected int getWallet() {
        return wallet;
    }

    protected int getBetAmount() {
        return betAmount;
    }
}
