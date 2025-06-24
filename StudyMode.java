import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class StudyMode extends JFrame {
    private JLabel cardContent;
    private JLabel progressLabel;
    private JButton prevBtn;
    private JButton flipBtn;
    private JButton nextBtn;
    private JButton backBtn;
    private JLabel title;
    private JPanel topBarPanel;
    private JPanel cardPanel;
    private JPanel bottomPanel;
    private JPanel buttonPanel;

    private Image icon;

    private int cardIndex = 0;
    private int cardNumber = 1;
    private int totalCards;
    private HashMap<String, AnswerList<String>> deck;
    private ArrayList<String> questions;
    private boolean isFlipped;

    public StudyMode(HashMap<String, AnswerList<String>> deck) {
        this.deck = deck;
        totalCards = deck.size();

        setTitle("FlashFocus - Study Mode");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Background panel with image
        JPanel backgroundPanel = new JPanel() {
            Image bg = new ImageIcon("FlashFocus\\Background image.jpg").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);

        icon = new ImageIcon("FlashFocus\\Image Icon.png").getImage();
        setIconImage(icon);

        // Top bar panel with title and back button
        topBarPanel = new JPanel(new BorderLayout());
        topBarPanel.setOpaque(false);
        topBarPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        // Back button (top-right)
        backBtn = createStyledButton("Back");
        backBtn.setPreferredSize(new Dimension(100, 40));
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backBtn.setBackground(new Color(204, 51, 51)); // red for visibility

        // Title (centered)
        title = new JLabel("Study Mode", SwingConstants.CENTER);
        title.setFont(new Font("Poppins", Font.BOLD, 36));
        title.setForeground(Color.WHITE);

        topBarPanel.add(title, BorderLayout.CENTER);
        topBarPanel.add(backBtn, BorderLayout.EAST);
        topBarPanel.add(Box.createHorizontalStrut(100), BorderLayout.WEST);

        backgroundPanel.add(topBarPanel, BorderLayout.NORTH);

        // Flashcard Panel (semi-transparent)
        cardPanel = new RoundedPanel();
        cardPanel.setPreferredSize(new Dimension(600, 300));
        cardPanel.setBackground(new Color(255, 255, 255, 180)); // semi-transparent white
        cardPanel.setLayout(new GridBagLayout());

        cardContent = new JLabel("", SwingConstants.CENTER);
        cardContent.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        cardContent.setForeground(Color.DARK_GRAY);
        cardContent.setPreferredSize(new Dimension(550, 250));
        cardPanel.add(cardContent);

        backgroundPanel.add(cardPanel, BorderLayout.CENTER);

        // Buttons
        prevBtn = createStyledButton("⟵ Prev");
        flipBtn = createStyledButton("Flip");
        nextBtn = createStyledButton("Next ⟶");

        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.add(prevBtn);
        buttonPanel.add(flipBtn);
        buttonPanel.add(nextBtn);

        bottomPanel.add(buttonPanel, BorderLayout.CENTER);

        progressLabel = new JLabel("Card " + (cardNumber) + " of " + totalCards, SwingConstants.CENTER);
        progressLabel.setFont(new Font("SansSerif", Font.ITALIC, 16));
        progressLabel.setForeground(Color.WHITE);
        bottomPanel.add(progressLabel, BorderLayout.SOUTH);

        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Event listeners
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == flipBtn) {
                    if (!isFlipped) {
                        showAnswer(questions.get(cardIndex));
                    } else {
                        showQuestion(questions.get(cardIndex));
                    }
                } else if (e.getSource() == nextBtn) {
                    if (cardIndex < questions.size() - 1) {
                        cardIndex++;
                        cardNumber = cardIndex + 1;
                        progressLabel.setText("Card " + cardNumber + " of " + totalCards);
                        showQuestion(questions.get(cardIndex));

                    }
                } else if (e.getSource() == prevBtn) {
                    if (cardIndex >= 1) {
                        cardIndex--;
                        cardNumber = cardIndex + 1;
                        progressLabel.setText("Card " + cardNumber + " of " + totalCards);
                        showQuestion(questions.get(cardIndex));

                    }
                } else if (e.getSource() == backBtn) {
                    new HomeScreen();
                    StudyMode.this.dispose();
                }
            }
        };

        flipBtn.addActionListener(listener);
        nextBtn.addActionListener(listener);
        prevBtn.addActionListener(listener);
        backBtn.addActionListener(listener);

        initializeDeck();
        setVisible(true);
    }

    // Storing questions into a seperate arraylist
    public void initializeDeck() {
        questions = new ArrayList<>(deck.keySet());
        if (!questions.isEmpty()) {
            cardIndex = 0;
            showQuestion(questions.get(cardIndex));
        }
    }

    // Displays question with new line formatting
    public void showQuestion(String s) {
        cardContent.setText("<html><div style='text-align: center;'>" + s + "</div></html>");
        isFlipped = false;
    }

    // Displays answer of the question
    public void showAnswer(String s) {
        AnswerList<String> list = deck.get(s);

        if (list == null || list.getAnswers().isEmpty()) {
            cardContent.setText("No answer available.");
            return;
        }

        int correctAnswerIndex = list.getCorrectAnswerIndex();

        if (correctAnswerIndex < 0 || correctAnswerIndex >= list.getAnswers().size()) {
            cardContent.setText("Invalid answer index.");
            return;
        }

        cardContent.setText("<html><div style='text-align: center;'>" +
                list.getAnswers().get(correctAnswerIndex) + "</div></html>");
        isFlipped = true;
    }

    // Button designs
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

    // Inner class for rounded panel
    class RoundedPanel extends JPanel {
        public RoundedPanel() {
            super();
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            g2.dispose();
            super.paintComponent(g);
        }
    }
}
