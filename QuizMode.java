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

    private JPanel topBarPanel;


    private HashMap<String, AnswerList<String>> deck;
    private ArrayList<String> questions;
    private int cardIndex = 0;
    private boolean answersShown;
    private String correctAnswer;
    
    public QuizMode(HashMap<String, AnswerList<String>> flashCardDeck) {
        deck = flashCardDeck;
        cardContent = new JLabel();
        topLeft = new JButton();
        topRight = new JButton();
        bottomLeft = new JButton();
        bottomRight = new JButton(); 
            
        setTitle("FlashFocus");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Quiz Mode

        // Background
        // Background panel with image
        JPanel backgroundPanel = new JPanel() {
            Image bg = new ImageIcon("C:\\Users\\sarim\\OneDrive\\Desktop\\Projects\\FlashFocus\\FlashFocus\\Background quiz image.jpg").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);
        
        // Title (centered)
        JLabel title = new JLabel("Quiz Mode", SwingConstants.CENTER);
        title.setFont(new Font("Poppins", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        title.setBounds(300,300,300,30);

        // Top bar panel with title and back button
        JPanel topBarPanel = new JPanel(new BorderLayout());
        topBarPanel.setOpaque(false);
        topBarPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));
        
        // Back button (top-right)
        backBtn = createStyledButton("Back");
        backBtn.setPreferredSize(new Dimension(100, 40));
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backBtn.setBackground(new Color(204, 51, 51)); // red for visibility

        topBarPanel.add(title);
        topBarPanel.add(backBtn, BorderLayout.EAST);

        backgroundPanel.add(topBarPanel, BorderLayout.NORTH);

        // Questions and answers

        //HashMap<String, AnswerList<String>> flashCards = deck;

        QuizMode.this.setVisible(true);

        ActionListener listener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == backBtn){
                    new HomeScreen();
                    QuizMode.this.setVisible(false);
                }
            }

        };
        backBtn.addActionListener(listener);
    }
    
    public void initializeDeck() {
        questions = new ArrayList<>(deck.keySet());
        if (!questions.isEmpty()) {
            cardIndex = 0;
            showQuestion(questions.get(cardIndex));
        }
    }

    public void showQuestion(String s) {
        cardContent.setText(s);
        answersShown = false;
        
    }

    public void showAnswers(String s) {
        AnswerList<String> list = deck.get(s);
        topLeft.setText(list.get(0));
        topRight.setText(list.get(1));
        bottomLeft.setText(list.get(2));
        bottomRight.setText(list.get(3));
        if (list.getCorrectAnswerIndex() == 0) {
            correctAnswer = topLeft.getText();
        } else if (list.getCorrectAnswerIndex() == 1) {
            correctAnswer = topRight.getText();
        } else if (list.getCorrectAnswerIndex() == 2){
            correctAnswer = bottomLeft.getText();
        } else if (list.getCorrectAnswerIndex() == 3) {
            correctAnswer = bottomRight.getText();
        }
        answersShown = true;
    }

    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(0, 102, 204));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(120, 45));
        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
