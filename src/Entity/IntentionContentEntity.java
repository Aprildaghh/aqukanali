package Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="intentioncontent")
public class IntentionContentEntity {

    @Id
    @Column(name="IntentionId")
    private int intentionId;
    @Column(name="isCompleted")
    private boolean isCompleted;
    @Column(name="IntentionContent")
    private String intentionContent;

    public IntentionContentEntity() {
    }

    public IntentionContentEntity(boolean isCompleted, String intentionContent) {
        this.isCompleted = isCompleted;
        this.intentionContent = intentionContent;
    }

    public IntentionContentEntity(int intentionId, boolean isCompleted, String intentionContent) {
        this.intentionId = intentionId;
        this.isCompleted = isCompleted;
        this.intentionContent = intentionContent;
    }

    public int getIntentionId() {
        return intentionId;
    }

    public void setIntentionId(int intentionId) {
        this.intentionId = intentionId;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getIntentionContent() {
        return intentionContent;
    }

    public void setIntentionContent(String intentionContent) {
        this.intentionContent = intentionContent;
    }
}
