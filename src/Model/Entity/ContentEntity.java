package Model.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="content")
public class ContentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="content_completion")
    private Boolean contentCompletion;

    @Column(name="intention_content")
    private String intentionContent;

    @ManyToOne(cascade={ CascadeType.ALL })
    @JoinColumn(name="intention_id")
    private IntentionEntity intention;


    public ContentEntity() {
    }

    public ContentEntity(Boolean contentCompletion, String intentionContent, IntentionEntity intention) {
        this.contentCompletion = contentCompletion;
        this.intentionContent = intentionContent;
        this.intention = intention;
    }

    public ContentEntity(Integer id, Boolean contentCompletion, String intentionContent, IntentionEntity intention) {
        this.id = id;
        this.contentCompletion = contentCompletion;
        this.intentionContent = intentionContent;
        this.intention = intention;
    }

    public ContentEntity(Integer id, Boolean contentCompletion, String intentionContent) {
        this.id = id;
        this.contentCompletion = contentCompletion;
        this.intentionContent = intentionContent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean isContentCompletion() {
        return contentCompletion;
    }

    public void setContentCompletion(Boolean contentCompletion) {
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

    @Override
    public String toString() {
        return "ContentEntity{" +
                "id=" + id +
                ", contentCompletion=" + contentCompletion +
                ", intentionContent='" + intentionContent + '\'' +
                '}';
    }
}
