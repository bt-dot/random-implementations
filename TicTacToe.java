import java.util.*;

public class TicTacToe {

    private static void move(char[][] game, int pos, char player) {
        switch (pos) {
            case 1:
                game[0][0] = player;
                break;
            case 2:
                game[0][1] = player;
                break;
            case 3:
                game[0][2] = player;
                break;
            case 4:
                game[1][0] = player;
                break;
            case 5:
                game[1][1] = player;
                break;
            case 6:
                game[1][2] = player;
                break;
            case 7:
                game[2][0] = player;
                break;
            case 8:
                game[2][1] = player;
                break;
            case 9:
                game[2][2] = player;
                break;
            default:
                break;
        }
    }

    private static boolean hasWon(List<Integer> playerMov) {
        List<Integer> rowOne = Arrays.asList(1, 2, 3);
        List<Integer> rowTwo = Arrays.asList(4, 5, 6);
        List<Integer> rowTre = Arrays.asList(7, 8, 9);
        List<Integer> colOne = Arrays.asList(1, 4, 7);
        List<Integer> colTwo = Arrays.asList(2, 5, 8);
        List<Integer> colTre = Arrays.asList(3, 6, 9);
        List<Integer> crossOne = Arrays.asList(1, 5, 9);
        List<Integer> crossTwo = Arrays.asList(3, 5, 7);

        List<List> winningList = new ArrayList();
        winningList.add(rowOne);
        winningList.add(rowTwo);
        winningList.add(rowTre);
        winningList.add(colOne);
        winningList.add(colTwo);
        winningList.add(colTre);
        winningList.add(crossOne);
        winningList.add(crossTwo);

        for (List<Integer> wl : winningList) {
            if (playerMov.containsAll(wl))
                return true;
        }
        return false;

    }
    private static void print(char[][] game) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(game[i][j]);
                if (j != 2)
                    System.out.print(" | ");
            }
            System.out.println();
            if (i != 2)
                System.out.print("– + – + –");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        char[][] gameboard = {{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
        TicTacToe game = new TicTacToe();

        List<Integer> humanMoves = new ArrayList<>();
        List<Integer> compMoves = new ArrayList<>();

        game.print(gameboard);
        while (true) {
            //human player;
            System.out.println("Your turn: ");
            Scanner scan = new Scanner(System.in);
            System.out.println("where would you like to place?");
            int hmov = scan.nextInt();
            while (humanMoves.contains(hmov) || compMoves.contains(hmov)) {
                System.out.println("Spot is taken; choose a new move!");
                hmov = scan.nextInt();
            }
            humanMoves.add(hmov);
            game.move(gameboard, hmov, 'X');
            game.print(gameboard);

            if (game.hasWon(humanMoves)) {
                System.out.println("You win!");
                break;
            } else if (humanMoves.size() + compMoves.size() == 9) {
                System.out.println("Oops! Tie game.");
                break;
            }

            //computer player:
            System.out.println("Computer's turn: ");
            Random rand = new Random();
            int cmov = rand.nextInt(9) + 1;
            while (humanMoves.contains(cmov) || compMoves.contains(cmov)) {
                //keep looping until find a empty spot
                cmov = rand.nextInt(9) + 1;
            }
            compMoves.add(cmov);
            game.move(gameboard, cmov, 'O');
            game.print(gameboard);

            if (game.hasWon(compMoves)) {
                System.out.println("Computer wins.");
                break;
            } else if (humanMoves.size() + compMoves.size() == 9) {
                System.out.println("Oops! Tie game.");
                break;
            }
        }
    }
}