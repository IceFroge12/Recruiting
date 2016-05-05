package ua.kpi.nc.persistence.dto;

/**
 * @author Korzh
 */
public class QuestionVariantDto {
    private String variant;

    public QuestionVariantDto() {
    }

    public QuestionVariantDto(String variant) {
        this.variant = variant;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    @Override
    public String toString() {
        return "Variant{" +
                "variant='" + variant + '\'' +
                '}';
    }
}
