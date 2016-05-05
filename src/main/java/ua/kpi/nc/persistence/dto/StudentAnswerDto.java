package ua.kpi.nc.persistence.dto;

/**
 * @author Korzh
 */
public class StudentAnswerDto {
    private String answer;

    public StudentAnswerDto() {
    }

    public StudentAnswerDto(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answer='" + answer + '\'' +
                '}';
    }
}
