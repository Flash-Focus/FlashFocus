import java.util.*;

public class AnswerList<E> extends ArrayList<E> {
    private List<String> answers;
    private int correctAnswerIndex;

    public AnswerList(List<String> answers, int correctAnswerIndex) {
        this.answers = new ArrayList<>(answers);
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public List<String> getAnswers() {
        return this.answers;
    }
    
    public int getCorrectAnswerIndex() {
        return this.correctAnswerIndex;
    }

    public boolean isCorrect(String answer) {
        if (answer.equals(answers.get(correctAnswerIndex))) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return "Answer Options: " + this.answers + "\nCorrect Answer Index: " + this.correctAnswerIndex;
    }
}
