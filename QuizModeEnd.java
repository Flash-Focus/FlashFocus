import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class QuizModeEnd extends JFrame {

    private JPanel glowingPanel;
    private JLabel titleLabel;
    private JLabel scoreLabel;
    private JLabel subTextLabel;
    private JButton tryAgainButton;
    private JButton homeButton;

    private Font titleFont;
    private Font scoreFont;
    private Font subFont;
    private Font buttonFont;

    private Image icon;

    public QuizModeEnd(int correct, int totalCards, HashMap<String, AnswerList<String>> deck) {
        setTitle("FlashFocus - Quiz Complete");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Neon panel background
        glowingPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Dark background
                g2.setColor(new Color(10, 10, 30));
                g2.fillRect(0, 0, getWidth(), getHeight());

                // Glowing blue box
                Color neon = new Color(0, 180, 255, 180);
                for (int i = 0; i < 10; i++) {
                    g2.setColor(new Color(neon.getRed(), neon.getGreen(), neon.getBlue(), 25 - i * 2));
                    g2.drawRoundRect(120 - i, 100 - i, 660 + i * 2, 380 + i * 2, 40, 40);
                }
            }
        };
        glowingPanel.setBackground(Color.BLACK);
        setContentPane(glowingPanel);

        // Fonts
        titleFont = new Font("SansSerif", Font.BOLD, 36);
        scoreFont = new Font("SansSerif", Font.BOLD, 30);
        subFont = new Font("SansSerif", Font.PLAIN, 22);
        buttonFont = new Font("SansSerif", Font.BOLD, 18);

        // Title label
        titleLabel = new JLabel("QUIZ COMPLETE!");
        titleLabel.setBounds(0, 125, 900, 40);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(new Color(0, 220, 255));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        glowingPanel.add(titleLabel);

        // Score label
        scoreLabel = new JLabel("Your Score: " + correct + "/" + totalCards);
        scoreLabel.setBounds(0, 200, 900, 40);
        scoreLabel.setFont(scoreFont);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        glowingPanel.add(scoreLabel);

        // Subtext label
        subTextLabel = new JLabel("You answered " + correct + " out of " + totalCards + " flashcards correctly.");
        subTextLabel.setBounds(0, 275, 900, 30);
        subTextLabel.setFont(subFont);
        subTextLabel.setForeground(Color.LIGHT_GRAY);
        subTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        glowingPanel.add(subTextLabel);

        // Buttons
        tryAgainButton = createNeonButton("Try Again", buttonFont);
        tryAgainButton.setBounds(490, 400, 160, 50);
        glowingPanel.add(tryAgainButton);

        homeButton = createNeonButton("Home", buttonFont);
        homeButton.setBounds(250, 400, 160, 50);
        glowingPanel.add(homeButton);

        // Button listeners
        tryAgainButton.addActionListener(e -> {
            dispose();
            new QuizMode(deck);
        });

        homeButton.addActionListener(e -> {
            dispose();
            new HomeScreen();
        });

        // Icon (Optional)
        icon = new ImageIcon("FlashFocus\\Image Icon.png").getImage();
        setIconImage(icon);

        setVisible(true);
    }

    private JButton createNeonButton(String text, Font font) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setOpaque(false);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(0, 150, 255, 100));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        button.setFocusPainted(false);
        button.setFont(font);
        button.setBackground(new Color(20, 20, 40));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(new Color(0, 200, 255)));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        return button;
    }
}
