package Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Date;

@Entity
@Table(name="intention")
public class IntentionEntity {

    @Id
    @Column(name="theDate")
    private Date theDate;

    @Column(name="IntentionId")
    private int intentionId;

    public IntentionEntity() {
    }

    public IntentionEntity(Date theDate, int intentionId) {
        this.theDate = theDate;
        this.intentionId = intentionId;
    }

    public Date getTheDate() {
        return theDate;
    }

    public void setTheDate(Date theDate) {
        this.theDate = theDate;
    }

    public int getIntentionId() {
        return intentionId;
    }

    public void setIntentionId(int intentionId) {
        this.intentionId = intentionId;
    }
}
