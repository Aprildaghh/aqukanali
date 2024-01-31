package Model.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="content")
public class ContentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="content_completion")
    private boolean contentCompletion;

    @Column(name="intention_content")
    private String intentionContent;

    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="intention_id")
    private IntentionEntity intention;


    public ContentEntity() {
    }

    public ContentEntity(boolean contentCompletion, String intentionContent, IntentionEntity intention) {
        this.contentCompletion = contentCompletion;
        this.intentionContent = intentionContent;
        this.intention = intention;
    }

    public ContentEntity(int id, boolean contentCompletion, String intentionContent, IntentionEntity intention) {
        this.id = id;
        this.contentCompletion = contentCompletion;
        this.intentionContent = intentionContent;
        this.intention = intention;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isContentCompletion() {
        return contentCompletion;
    }

    public void setContentCompletion(boolean contentCompletion) {
        this.contentCompletion = contentCompletion;
    }

    public String getIntentionContent() {
        return intentionContent;
    }

    public void setIntentionContent(String intentionContent) {
        this.intentionContent = intentionContent;
    }

    public IntentionEntity getIntention() {
        return intention;
    }

    public void setIntention(IntentionEntity intention) {
        this.intention = intention;
    }
}
