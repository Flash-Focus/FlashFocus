import java.util.*;

public class AnswerList<E> {
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

     public String get(int index) {
        return answers.get(index);
    }

    public int getSize() {
        return answers.size();
    }

    public String toString() {
        return "Answer Options: " + this.answers + "\nCorrect Answer Index: " + this.correctAnswerIndex;
    }
}
