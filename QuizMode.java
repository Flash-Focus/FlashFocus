import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizMode extends JFrame {
    private JLabel cardContent;
    private JButton topLeft;
    private JButton topRight;
    private JButton bottomLeft;
    private JButton bottomRight;
    private JButton backBtn;
    private JButton nextBtn;
    

    private JPanel topBarPanel;


    private HashMap<String, AnswerList<String>> deck;
    private ArrayList<String> questions;
    private int cardIndex = 0;
    private boolean attempt;
    private String correctAnswer;
    private int correctIndex = 0;

    private int totalCards;
    private int correct;
    private int currentCard;
    
    private JLabel progressLabel;
    public QuizMode(HashMap<String, AnswerList<String>> flashCardDeck) {
        

        setTitle("FlashFocus");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        deck = flashCardDeck;
        // Quiz Mode

        // Background
        // Background panel with image
        JPanel backgroundPanel = new JPanel() {
            Image bg = new ImageIcon(
                    "FlashFocus\\Background quiz image.jpg")
                    .getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(null);
        setContentPane(backgroundPanel);

        Image icon = new ImageIcon("FlashFocus\\Image Icon.png").getImage();
        setIconImage(icon);


        // Title
        cardContent = new JLabel("", SwingConstants.CENTER);
        cardContent.setFont(new Font("Segoe UI", Font.BOLD, 20));
        cardContent.setForeground(Color.WHITE);
        cardContent.setBounds(200, 100, 500, 40);
        backgroundPanel.add(cardContent);

        // current card and total cards
        progressLabel = new JLabel("", SwingConstants.CENTER);
        progressLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        progressLabel.setForeground(Color.WHITE);
        progressLabel.setBounds(325, 420, 200, 30); 
        backgroundPanel.add(progressLabel);


        // Top Left Button
        topLeft = createStyledButton("");
        topLeft.setBounds(170, 250, 200, 50);
        backgroundPanel.add(topLeft);
        
        // Top right button
        topRight = createStyledButton("");
        topRight.setBounds(455, 250, 200, 50);
        backgroundPanel.add(topRight);

        // Bottom left button
        bottomLeft = createStyledButton("");
        bottomLeft.setBounds(170, 350, 200, 50);
        backgroundPanel.add(bottomLeft);

        // Bottom right button
        bottomRight = createStyledButton("");
        bottomRight.setBounds(455, 350, 200, 50);
        backgroundPanel.add(bottomRight);

        // Next Button
        nextBtn = createStyledButton("Next");
        nextBtn.setBounds(700,25,100,40);
        nextBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nextBtn.setBackground(new Color(0, 191, 255)); // Purple
        nextBtn.setVisible(false);
        backgroundPanel.add(nextBtn);

    
        // Title (centered)
        JLabel title = new JLabel("Quiz Mode", SwingConstants.CENTER);
        title.setFont(new Font("Poppins", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        title.setBounds(300, 300, 300, 30);

        // Top bar panel with title and back button
        JPanel topBarPanel = new JPanel(new BorderLayout());
        topBarPanel.setOpaque(false);
        topBarPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        // Back button (top-right)
        backBtn = createStyledButton("Back");
        backBtn.setBounds(25,25,100,40);
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backBtn.setBackground(new Color(204, 51, 51)); // red for visibility
        backgroundPanel.add(backBtn);

        // Questions and answers

        QuizMode.this.setVisible(true);

        ActionListener listener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == backBtn) {
                    new HomeScreen();
                    QuizMode.this.setVisible(false);
                } else if (e.getSource() == topLeft && !attempt) {
                    if (topLeft.getText() == correctAnswer){
                        topLeft.setBackground(new Color(50, 205, 50));
                        correct++;
                        attempt = true;
                        nextBtn.setVisible(true);
                    } else {
                        topLeft.setBackground(new Color(255, 69, 58));
                        correctAnswerButtonGreen(correctIndex);
                        attempt = true;
                        nextBtn.setVisible(true);
                    }
                    
                } else if (e.getSource() == topRight && !attempt){
                    if(topRight.getText() == correctAnswer){
                        topRight.setBackground(new Color(50, 205, 50));
                        correct++;
                        attempt = true;
                        nextBtn.setVisible(true);
                    } else {
                        topRight.setBackground(new Color(255, 69, 58));
                        correctAnswerButtonGreen(correctIndex);
                        attempt = true;
                        nextBtn.setVisible(true);
                    }
                    
                } else if (e.getSource() == bottomLeft && !attempt){
                    if (bottomLeft.getText() == correctAnswer) {
                        bottomLeft.setBackground(new Color(50, 205, 50));
                        correct++;
                        attempt = true;
                        nextBtn.setVisible(true);
                    } else {
                        bottomLeft.setBackground(new Color(255, 69, 58));
                        correctAnswerButtonGreen(correctIndex);
                        attempt = true;
                        nextBtn.setVisible(true);
                    }
                } else if (e.getSource() == bottomRight && !attempt){
                    if (bottomRight.getText() == correctAnswer) {
                        bottomRight.setBackground(new Color(50, 205, 50));
                        correct++;
                        attempt = true;
                        nextBtn.setVisible(true);
                    } else {
                        bottomRight.setBackground(new Color(255, 69, 58));
                        correctAnswerButtonGreen(correctIndex);
                        attempt = true;
                        nextBtn.setVisible(true);
                    }
                } else if (e.getSource() == nextBtn && attempt){
                    if (cardIndex < questions.size() - 1) {
                        cardIndex++;
                        attempt = false;
                        topLeft.setBackground(new Color(255, 188, 110));
                        topRight.setBackground(new Color(255, 188, 110));
                        bottomLeft.setBackground(new Color(255, 188, 110));
                        bottomRight.setBackground(new Color(255, 188, 110));
                        correctIndex = 0;
                        nextBtn.setVisible(false);
                        showQuestionAndOptions(questions.get(cardIndex));
                    } else if (cardIndex == totalCards - 1) {
                        QuizMode.this.setVisible(false);
                        new QuizModeEnd(correct, totalCards, deck);
                    }
                }
            }

        };
        backBtn.addActionListener(listener);
        topLeft.addActionListener(listener);
        topRight.addActionListener(listener);
        bottomLeft.addActionListener(listener);
        bottomRight.addActionListener(listener);
        nextBtn.addActionListener(listener);

        initializeDeck();

    }
    
    // Called only once
    public void initializeDeck() {
        questions = new ArrayList<>(deck.keySet());
        totalCards = questions.size();
        if (!questions.isEmpty()) {
            cardIndex = 0;
            attempt = false;
            showQuestionAndOptions(questions.get(cardIndex));
        }
    }

    public void showQuestionAndOptions(String s) {
        cardContent.setText(s);
        AnswerList<String> list = deck.get(s);
        topLeft.setText(list.get(0));
        topRight.setText(list.get(1));
        bottomLeft.setText(list.get(2));
        bottomRight.setText(list.get(3));
        correctIndex = list.getCorrectAnswerIndex();
        if (correctIndex == 0) {
            correctAnswer = topLeft.getText();
        } else if (correctIndex == 1) {
            correctAnswer = topRight.getText();
        } else if (correctIndex == 2) {
            correctAnswer = bottomLeft.getText();
        } else if (correctIndex == 3) {
            correctAnswer = bottomRight.getText();
        }
        updateProgressLabel();

    }

    private void updateProgressLabel(){
        currentCard = cardIndex + 1;
        progressLabel.setText("Card " + currentCard + " of " + totalCards);
    }

    public void correctAnswerButtonGreen(int correctIndex) {
        if (correctIndex == 0) {
            topLeft.setBackground(new Color(50, 205, 50));
        } else if (correctIndex == 1) {
            topRight.setBackground(new Color(50, 205, 50));
        } else if (correctIndex == 2) {
            bottomLeft.setBackground(new Color(50, 205, 50));
        } else if (correctIndex == 3) {
            bottomRight.setBackground(new Color(50, 205, 50));
        }
    }
               

    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(255,188,110));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(120, 45));
        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
