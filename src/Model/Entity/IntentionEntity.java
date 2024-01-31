package Model.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name="intention")
public class IntentionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="intention_date")
    private Date intentionDate;

    @OneToMany(mappedBy = "intention",
                cascade={CascadeType.PERSIST, CascadeType.MERGE,
                CascadeType.DETACH, CascadeType.REFRESH})
    private List<ContentEntity> contents;

    public void add(ContentEntity content)
    {
        if(contents == null) contents = new ArrayList<ContentEntity>();

        contents.add(content);
        content.setIntention(this);
    }

    public IntentionEntity() {
    }

    public IntentionEntity(Date intentionDate) {
        this.intentionDate = intentionDate;
    }

    public IntentionEntity(Date intentionDate, List<ContentEntity> contents) {
        this.intentionDate = intentionDate;
        this.contents = contents;
    }

    public IntentionEntity(int id, Date intentionDate) {
        this.id = id;
        this.intentionDate = intentionDate;
    }

    public IntentionEntity(int id, Date intentionDate, List<ContentEntity> contents) {
        this.id = id;
        this.intentionDate = intentionDate;
        this.contents = contents;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getIntentionDate() {
        return intentionDate.toLocalDate();
    }

    public void setIntentionDate(LocalDate intentionDate) {
        this.intentionDate = java.sql.Date.valueOf(intentionDate);
    }

    public List<ContentEntity> getContents() {
        return contents;
    }

    public void setContents(List<ContentEntity> contents) {
        this.contents = contents;
    }
}
