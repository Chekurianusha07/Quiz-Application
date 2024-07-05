import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizApp extends JFrame {
    private Quiz quiz;
    private JLabel questionLabel;
    private JRadioButton[] optionButtons;
    private ButtonGroup optionsGroup;
    private JButton nextButton;

    public QuizApp(Quiz quiz) {
        this.quiz = quiz;
        setTitle("Quiz Application");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        questionLabel = new JLabel("Question");
        add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(4, 1));
        optionButtons = new JRadioButton[4];
        optionsGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionsGroup.add(optionButtons[i]);
            optionsPanel.add(optionButtons[i]);
        }
        add(optionsPanel, BorderLayout.CENTER);

        nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleNextButton();
            }
        });
        add(nextButton, BorderLayout.SOUTH);

        displayNextQuestion();
    }

    private void displayNextQuestion() {
        Question question = quiz.getNextQuestion();
        if (question != null) {
            questionLabel.setText(question.getQuestionText());
            String[] options = question.getOptions();
            for (int i = 0; i < options.length; i++) {
                optionButtons[i].setText(options[i]);
            }
            optionsGroup.clearSelection();
        } else {
            displayScore();
        }
    }

    private void handleNextButton() {
        for (JRadioButton button : optionButtons) {
            if (button.isSelected()) {
                quiz.checkAnswer(button.getText());
                break;
            }
        }
        displayNextQuestion();
    }

    private void displayScore() {
        JOptionPane.showMessageDialog(this, "Quiz Over! Your Score: " + quiz.getScore() + "/" + quiz.getTotalQuestions());
        System.exit(0);
    }

    public static void main(String[] args) {
        Quiz quiz = new Quiz();
        quiz.addQuestion(new Question("What is the capital of France?", new String[]{"Paris", "London", "Berlin", "Madrid"}, "Paris"));
        quiz.addQuestion(new Question("What is 2 + 2?", new String[]{"3", "4", "5", "6"}, "4"));
        quiz.addQuestion(new Question("What is the color of the sky?", new String[]{"Blue", "Green", "Red", "Yellow"}, "Blue"));

        SwingUtilities.invokeLater(() -> {
            QuizApp app = new QuizApp(quiz);
            app.setVisible(true);
        });
    }
}

