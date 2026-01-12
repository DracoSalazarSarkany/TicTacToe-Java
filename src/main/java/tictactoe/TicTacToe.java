package tictactoe;

import javax.swing.*;
import java.awt.*;

public class TicTacToe extends JFrame {

    private GameMode gameMode;
    private Board board;

    private JLabel turnLabel;

    public TicTacToe(){
        selectGameMode();
        initUI();

    }

    /**
     * Diálogo para elegir modo de juego
     */
    private void selectGameMode(){

        String[] options = {"Player Versus Player","Player Versus Enviroment"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Select Game Mode",
                "Tres en Raya",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]
        );
        gameMode = (choice==1) ? GameMode.PVE : GameMode.PVP;
    }

    /**
     * Inicializa la interfaz gráfica
     */
    private void initUI(){
        setTitle("Tres en Raya");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Label de turno
        turnLabel = new JLabel("Turn: Player 1 (X)", SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(turnLabel, BorderLayout.NORTH);

        // Tablero
        board = new Board(gameMode, this);
        add(board, BorderLayout.CENTER);

        // Botón reiniciar
        JButton resetButton = new JButton("Restart");
        resetButton.setFont(new Font("Arial", Font.BOLD, 14));
        resetButton.addActionListener(e -> resetGame());
        add(resetButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * Reinicia el juego
     */

    private void resetGame() {
        getContentPane().removeAll();
        selectGameMode();
        initUI();
        revalidate();
        repaint();
    }

    /**
     * Actualiza el texto del turno
     */

    public void updateTurn(char currentPlayer){
        String text = (currentPlayer == 'X')
                ?"Turn: Player 1 (X)"
                :"Turn: Player 2 (O)";
        turnLabel.setText(text);
    }

    /**
     * Muestra el ganador
     */
    public void showWinner(char player) {
        String winner = (player == 'X') ? "Player 1" : "Player 2";
        JOptionPane.showMessageDialog(this, winner + " wins!");
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}
