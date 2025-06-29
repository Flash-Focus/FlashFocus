import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

public class HomeScreen extends JFrame {
    private String apiKey;

    private Image icon;

    private JLabel title;
    private JLabel subtitle;
    private JPanel searchPanel;
    private JLabel searchIcon;
    private JTextField searchField;
    private JPanel infoPanel;
    private JLabel infoTitle;
    private JLabel emoji;
    private JLabel text;
    private JPanel previewPanel;
    private JLabel previewLabel;
    private JLabel previewTitle;
    private JLabel previewContent;
    private JLabel footer;
    private JLabel generatingDeck;
    private JButton generateBtn;
    private JButton loadBtn;
    private JLabel loadDeck;

    private HashMap<String, AnswerList<String>> flashCardDeck;

    public HomeScreen() {
        setTitle("FlashFocus");
        setSize(1024, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        icon = new ImageIcon(getClass().getResource("/resources/Image Icon.png")).getImage();
        setIconImage(icon);

        GradientBackgroundPanel background = new GradientBackgroundPanel();
        background.setLayout(null);
        setContentPane(background);

        // Title
        title = new JLabel("FlashFocus", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 50)); // Modern and bold
        title.setForeground(new Color(30, 30, 30)); // Dark charcoal
        title.setBounds(0, 0, getWidth(), 80); // Full width, height adjusted
        background.add(title);

        // Subtitle
        subtitle = new JLabel(
                "<html><div style='text-align:center;'>AI-Powered flashcards.<br>Smarter studying starts here.</div></html>",
                SwingConstants.CENTER);
        subtitle.setFont(new Font("Poppins", Font.PLAIN, 26));
        subtitle.setForeground(new Color(70, 70, 70)); // medium dark gray
        subtitle.setBounds(50, 120, 520, 100); // reduced width a bit
        background.add(subtitle);

        // Search panel
        searchPanel = new JPanel(null);
        searchPanel.setBounds(65, 250, 470, 60);
        searchPanel.setBackground(new Color(255, 255, 255, 220));
        searchPanel.setOpaque(false);
        searchPanel.setBorder(new RoundedBorder(new Color(180, 180, 180), 25));
        addShadow(searchPanel);
        background.add(searchPanel);

        // Search icon
        searchIcon = new JLabel("🔍");
        searchIcon.setFont(new Font("SansSerif", Font.PLAIN, 20));
        searchIcon.setBounds(15, 15, 30, 30);
        searchPanel.add(searchIcon);

        // Beautiful Search Field
        searchField = new JTextField("Enter a topic...");
        searchField.setBounds(50, 15, 390, 30); // Slightly smaller to avoid overflowing
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        searchField.setForeground(new Color(130, 130, 130));
        searchField.setBackground(new Color(255, 255, 255, 180)); // Soft white
        searchField.setOpaque(false);
        searchField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding

        // Placeholder behavior
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchField.getText().equals("Enter a topic...")) {
                    searchField.setText("");
                    searchField.setForeground(new Color(50, 50, 50));
                }
            }

            // Searchbar text effect
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchField.getText().trim().isEmpty()) {
                    searchField.setText("Enter a topic...");
                    searchField.setForeground(new Color(130, 130, 130));
                }
            }
        });

        searchPanel.add(searchField);

        // Info Panel smaller and moved
        infoPanel = new JPanel(null);
        infoPanel.setBounds(600, 100, 350, 450); // smaller size and moved left
        infoPanel.setBackground(new Color(255, 255, 255, 230));
        infoPanel.setBorder(new RoundedBorder(new Color(200, 200, 200), 25));
        addShadow(infoPanel);
        background.add(infoPanel);

        infoTitle = new JLabel("What you can do:");
        infoTitle.setFont(new Font("Poppins", Font.BOLD, 22));
        infoTitle.setBounds(20, 20, 300, 30);
        infoTitle.setForeground(new Color(40, 40, 40));
        infoPanel.add(infoTitle);

        // Guide/Instructions
        String[] features = {
                "Generate flashcards from any topic",
                "Study or quiz yourself at your own pace",
                "Automatically saves decks for review",
                "Track your progress easily",
                "Supports multiple subjects"
        };
        String[] emojis = { "💡", "📝", "🗂️", "📈", "📚" };

        for (int i = 0; i < features.length; i++) {
            emoji = new JLabel(emojis[i]);
            emoji.setFont(new Font("Poppins", Font.PLAIN, 18)); // slightly smaller font
            emoji.setBounds(25, 65 + i * 40, 30, 30);
            infoPanel.add(emoji);

            text = new JLabel(features[i]);
            text.setFont(new Font("Poppins", Font.PLAIN, 14)); // smaller font
            text.setBounds(60, 65 + i * 40, 270, 30); // narrower width
            text.setForeground(new Color(90, 90, 90));
            infoPanel.add(text);
        }

        // Preview Panel inside Info Panel - smaller
        previewPanel = new JPanel(null);
        previewPanel.setBounds(20, 280, 310, 150); // smaller and fits
        previewPanel.setBackground(Color.WHITE);
        previewPanel.setBorder(new RoundedBorder(new Color(200, 200, 200), 20));
        addShadow(previewPanel);
        infoPanel.add(previewPanel);

        previewLabel = new JLabel("Preview");
        previewLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        previewLabel.setForeground(new Color(41, 128, 185));
        previewLabel.setBounds(20, 10, 200, 20);
        previewPanel.add(previewLabel);

        previewTitle = new JLabel("What is Calculus?");
        previewTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        previewTitle.setBounds(20, 40, 280, 25);
        previewPanel.add(previewTitle);

        previewContent = new JLabel("The study of continuous change.");
        previewContent.setFont(new Font("SansSerif", Font.PLAIN, 14));
        previewContent.setBounds(20, 70, 280, 20);
        previewPanel.add(previewContent);

        // Footer 
        footer = new JLabel("deepseek", SwingConstants.CENTER);
        footer.setFont(new Font("SansSerif", Font.BOLD, 30));
        footer.setForeground(new Color(102, 51, 153));
        footer.setBounds(150, 520, 200, 30);
        background.add(footer);

        // Generating label
        generatingDeck = new JLabel("Flashcard Deck is generating, Please wait patiently");
        generatingDeck.setBounds(65, 420, 500, 30);
        generatingDeck.setFont(new Font("SansSerif", Font.BOLD, 16));
        generatingDeck.setVisible(false);
        background.add(generatingDeck);

        // API key for chatbot
        apiKey = "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";

        loadDeck = new JLabel();
        
        // Buttons
        generateBtn = createButton("GENERATE FLASHCARDS", new Color(41, 128, 185), Color.WHITE);
        generateBtn.setBounds(65, 330, 225, 70); // smaller width
        background.add(generateBtn);

        generateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String topic = searchField.getText().trim();
                if (!topic.equals("") && !topic.equals("Enter a topic...")) {
                    loadDeck.setText("");
                    generatingDeck.setVisible(true);

                    // Use a short delay to allow UI to repaint before the heavy task
                    new Timer(10, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            ((Timer) evt.getSource()).stop(); // stop the timer after it fires

                            // Generate the flashcard deck

                            flashCardDeck = ChatbotFlashcardGenerator.flashCardDeck(topic, apiKey);
                            if (flashCardDeck == null) {
                                generatingDeck.setText("Unexpected Error in generating deck. Please try again");
                            }
                            generatingDeck.setVisible(false);
                            new ChooseYourMode(flashCardDeck);
                            HomeScreen.this.dispose();
                        }
                    }).start();
                }

            }
        });

        loadBtn = createButton("LOAD EXISTING DECK", new Color(220, 220, 220), new Color(80, 80, 80));
        loadBtn.setBounds(310, 330, 225, 70); // moved closer to generateBtn
        background.add(loadBtn);

        loadBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String topic = searchField.getText();
                if (!topic.equals("") && !topic.equals("Enter a specific topic to study, or a saved topic deck")) {
                    flashCardDeck = ChatbotFlashcardGenerator.loadDeck(topic);
                    loadDeck.setBounds(65, 425, 550, 30);
                    background.add(loadDeck);
                    if (flashCardDeck == null) {
                        loadDeck.setText("Deck not been found, Please enter a valid saved flashcard deck");
                        loadDeck.setFont(new Font("Segoe UI", Font.BOLD, 16));
                    } else {
                        new ChooseYourMode(flashCardDeck);
                        HomeScreen.this.dispose();
                    }

                }
            }

        });

        setVisible(true);
    }

    // Button designs
    private JButton createButton(String text, Color bg, Color fg) {
        JButton button = new JButton(text);
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorder(new RoundBorder(bg));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addShadow(button);

        // Smooth hover effect with Swing Timer
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bg.darker());
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(bg);
            }
        });

        return button;
    }

    // Gradient background with subtle abstract circles for a modern look
    static class GradientBackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // Gradient background
            GradientPaint gp = new GradientPaint(0, 0, new Color(230, 245, 255),
                    0, getHeight(), new Color(200, 220, 245));
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());

            // Soft abstract circles
            g2d.setColor(new Color(180, 210, 255, 80));
            g2d.fill(new Ellipse2D.Double(100, 400, 300, 300));

            g2d.setColor(new Color(160, 190, 255, 100));
            g2d.fill(new Ellipse2D.Double(700, 350, 350, 350));

            g2d.setColor(new Color(190, 215, 255, 90));
            g2d.fill(new Ellipse2D.Double(400, 100, 250, 250));
        }
    }

    // Rounded border for buttons
    static class RoundBorder extends LineBorder {
        public RoundBorder(Color color) {
            super(color, 1, true);
        }
    }

    // Rounded border for panels with adjustable radius (ignored radius param here)
    static class RoundedBorder extends LineBorder {
        public RoundedBorder(Color color, int radius) {
            super(color, 2, true);
        }
    }

    // Adds soft drop shadow (visual effect) using border compound
    private void addShadow(JComponent component) {
        component.setBorder(BorderFactory.createCompoundBorder(
                component.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
    }
}
