import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ChooseYourMode extends JFrame {
    private JLabel title;
    private JLabel subtitle;
    private JButton studyBtn;
    private JButton quizBtn;
    private HashMap<String, AnswerList<String>> deck;

    private Image icon;
    
    public ChooseYourMode(HashMap<String, AnswerList<String>> deck) {
        setTitle("FlashFocus");
        setSize(920, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        icon = new ImageIcon("/resources/Image Icon.png").getImage();
        setIconImage(icon);

        DarkBackgroundPanel background = new DarkBackgroundPanel();
        background.setLayout(null);
        setContentPane(background);
        this.deck = deck;

        // Initlized action Listener
        buttonlistener b1 = new buttonlistener();

        // Title
        title = new JLabel("⏰ Choose Your Mode", SwingConstants.CENTER);
        title.setFont(new Font("Poppins", Font.BOLD, 42));
        title.setForeground(Color.WHITE);
        title.setBounds(0, 80, getWidth(), 50);
        background.add(title);

        // Subtitle
        subtitle = new JLabel("What do you want to focus on today?", SwingConstants.CENTER);
        subtitle.setFont(new Font("Poppins", Font.PLAIN, 18));
        subtitle.setForeground(new Color(200, 200, 200));
        subtitle.setBounds(0, 175, getWidth(), 30);
        background.add(subtitle);

        // STUDY Button
        studyBtn = createDarkButton("📘 STUDY MODE", "Review cards at your pace", new Color(72, 133, 237));
        studyBtn.setBounds(160, 250, 280, 120);
        background.add(studyBtn);

        // QUIZ Button
        quizBtn = createDarkButton("📝 QUIZ MODE", "Test yourself quickly", new Color(244, 81, 108));
        quizBtn.setBounds(480, 250, 280, 120);
        background.add(quizBtn);

        // Button functionality
        studyBtn.addMouseListener(b1);
        quizBtn.addMouseListener(b1);

        setVisible(true);
    }

    // Shade hover effect on buttons
    private JButton createDarkButton(String title, String subtitle, Color bgColor) {
        JButton button = new JButton("<html><div style='text-align:center;'><b>" + title + "</b><br><span style='font-size:10px;'>" + subtitle + "</span></div></html>");
        button.setFont(new Font("Poppins", Font.PLAIN, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);

        // Rounded edges
        button.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 40), 1, true));

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    // Dark gradient background with glow accents
    static class DarkBackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();

            // Gradient background
            GradientPaint gp = new GradientPaint(
                0, 0, new Color(25, 25, 40),
                getWidth(), getHeight(), new Color(10, 10, 30)
            );
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());

            // Soft glows
            g2d.setColor(new Color(72, 133, 237, 60));
            g2d.fillOval(80, 450, 300, 300);

            g2d.setColor(new Color(244, 81, 108, 60));
            g2d.fillOval(600, 100, 300, 300);

            g2d.dispose();
        }
    }

    
    // Button listener when a button is clicked
    public class buttonlistener implements MouseListener {
        public buttonlistener(){}

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == studyBtn) {
                new StudyMode(deck);
                ChooseYourMode.this.dispose();
            } else if (e.getSource() == quizBtn) {
                new QuizMode(deck);
                ChooseYourMode.this.dispose();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

    }
}