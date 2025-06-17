import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class QuizModeEnd extends JFrame {

    private JLabel resultLabel;
    private double percent;
    private JLabel percentResult;
    private JLabel quizCompleted;
    private JLabel score;
    
    private JButton tryAgainButton;
    private JButton homeButton;

    private HashMap<String, AnswerList<String>> deck;

    public QuizModeEnd(int correct, int totalCards, HashMap<String, AnswerList<String>> deck) {
        this.deck = deck;

        setTitle("FlashFocus - Quiz End Screen");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel backgroundPanel = new JPanel() {
            Image bg = new ImageIcon(
                    "FlashFocus\\Quiz Mode End Screen.jpg")
                    .getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        Image icon = new ImageIcon("FlashFocus\\Image Icon.png").getImage();
        setIconImage(icon);

        backgroundPanel.setLayout(null);
        setContentPane(backgroundPanel);

        quizCompleted = new JLabel("QUIZ COMPLETED");
        // Font and stuff do it 

        score = new JLabel("Your Score: " + score + "/" + totalCards);
        // Font and stuff do it

        resultLabel = new JLabel("You answered" + correct + " out of " + totalCards + " flashcards correctly.");
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        resultLabel.setForeground(Color.WHITE);
        resultLabel.setBounds(200, 150, 600, 50);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        backgroundPanel.add(resultLabel);

        // Try Again Button
        tryAgainButton = new JButton("Try Again");
        styleButton(tryAgainButton);
        tryAgainButton.setBounds(300, 300, 140, 50);
        backgroundPanel.add(tryAgainButton);
        
        // Home Button
        homeButton = new JButton("Go to Home");
        styleButton(homeButton);
        homeButton.setBounds(460, 300, 140, 50);
        backgroundPanel.add(homeButton);

        ActionListener listener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == tryAgainButton){
                    new QuizMode(deck);
                } else if (e.getSource() == homeButton){
                    new HomeScreen();
                }
            }
        };

        tryAgainButton.addActionListener(listener);
        homeButton.addActionListener(listener);

        percent = ((int) (correct/totalCards)) * 100;
        percentResult = new JLabel("You got an " + percent + "%");


        QuizModeEnd.this.setVisible(true);

    }

     private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setBackground(new Color(255, 255, 255, 200));
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
    }
    
    public static void main(String[] args) {
        // new QuizModeEnd(7, 10); // Test
    }

}
