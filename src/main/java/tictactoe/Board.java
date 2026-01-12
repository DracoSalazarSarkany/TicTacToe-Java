package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board extends JPanel {

    // Botones del tablero (parte visual)
    private JButton[][] buttons = new JButton[3][3];

    // Estado lógico del tablero
    private char[][] board = new char[3][3];

    // Turno actual
    private char currentPlayer = 'X';

    // Indica si la partida ha terminado
    private boolean gameOver = false;

    // Generador de números aleatorios para la máquina
    private Random random = new Random();

    private GameMode gameMode;
    private TicTacToe parent;

    public Board(GameMode gameMode, TicTacToe parent) {

        this.gameMode = gameMode;
        this.parent = parent;

        // Layout 3x3
        setLayout(new GridLayout(3, 3));

        // Inicializamos el tablero lógico con espacios
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col] = ' ';
            }
        }

        // Creamos botones
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {

                JButton button = new JButton("");
                button.setFont(new Font("Arial", Font.BOLD, 60));

                int r = row;
                int c = col;

                // Acción al pulsar un botón
                button.addActionListener(e -> handleMove(r, c));

                buttons[row][col] = button;
                add(button);
            }
        }
    }

    /**
     * Gestiona una jugada en la posición (row, col)
     */
    private void handleMove(int row, int col) {

        // Si la partida terminó o la casilla está ocupada, no hacemos nada
        if (gameOver || board[row][col] != ' ') {
            return;
        }

        // Actualizamos tablero lógico
        board[row][col] = currentPlayer;

        // Actualizamos botón
        buttons[row][col].setText(String.valueOf(currentPlayer));

        // Comprobamos si hay victoria
        if (checkWin(currentPlayer)) {
            gameOver = true;

            parent.showWinner(currentPlayer);
            return;
        }

        // Comprobamos empate
        if (checkDraw()) {
            gameOver = true;
            JOptionPane.showMessageDialog(this,
                    "Empate");
            return;
        }

        // Cambiamos de turno
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        parent.updateTurn(currentPlayer);

        // Si estamos en PVE y ahora le toca a la máquina
        if (gameMode == GameMode.PVE && currentPlayer == 'O') {
            machineMove();
        }
    }

    /**
     * Movimiento automático de la máquina (aleatorio)
     */
    private void machineMove() {

        if (gameOver) {
            return;
        }

        // Lista de casillas libres
        List<int[]> freeCells = new ArrayList<>();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == ' ') {
                    freeCells.add(new int[]{row, col});
                }
            }
        }

        // Elegimos una al azar
        if (!freeCells.isEmpty()) {
            int[] move = freeCells.get(random.nextInt(freeCells.size()));
            handleMove(move[0], move[1]);
        }
    }

    /**
     * Comprueba si el jugador ha ganado
     */
    private boolean checkWin(char player) {

        // Filas
        for (int row = 0; row < 3; row++) {
            if (board[row][0] == player &&
                    board[row][1] == player &&
                    board[row][2] == player) {
                return true;
            }
        }

        // Columnas
        for (int col = 0; col < 3; col++) {
            if (board[0][col] == player &&
                    board[1][col] == player &&
                    board[2][col] == player) {
                return true;
            }
        }

        // Diagonal principal
        if (board[0][0] == player &&
                board[1][1] == player &&
                board[2][2] == player) {
            return true;
        }

        // Diagonal secundaria
        if (board[0][2] == player &&
                board[1][1] == player &&
                board[2][0] == player) {
            return true;
        }

        return false;
    }

    /**
     * Comprueba si todas las casillas están ocupadas
     */
    private boolean checkDraw() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == ' ') {
                    return false;
                }
            }
        }
        return true;

    }
}
