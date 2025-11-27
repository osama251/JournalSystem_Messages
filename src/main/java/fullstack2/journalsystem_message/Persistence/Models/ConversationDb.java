package fullstack2.journalsystem_message.Persistence.Models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="conversation")
public class ConversationDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long firstParticipantId;

    @Column(nullable=false)
    private long secondParticipantId;

    // Inverse side
    @OneToMany(mappedBy = "conversationId", cascade = CascadeType.ALL)
    private List<MessageDb> messageDbs = new ArrayList<>();

    public ConversationDb(){}

    public ConversationDb(long firstParticipantId, long secondParticipantId) {
        this.firstParticipantId = firstParticipantId;
        this.secondParticipantId = secondParticipantId;
    }

    public long getId() {
        return id;
    }

    public void addMessage(MessageDb messageDb){
        messageDbs.add(messageDb);
    }

    public long getFirstParticipantId() {
        return firstParticipantId;
    }

    public long getSecondParticipantId() {
        return secondParticipantId;
    }

    public List<MessageDb> getMessageDbs() {
        return messageDbs;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFirstParticipantId(long firstParticipantId) {
        this.firstParticipantId = firstParticipantId;
    }

    public void setSecondParticipantId(long secondParticipantId) {
        this.secondParticipantId = secondParticipantId;
    }

    public void setMessageDbs(List<MessageDb> messageDbs) {
        this.messageDbs = messageDbs;
    }

    @Override
    public String toString() {
        return "ConversationDb{" +
                "id=" + id +
                ", firstParticipantId=" + firstParticipantId +
                ", secondParticipantId=" + secondParticipantId +
                ", messageDbs=" + messageDbs +
                '}';
    }
}
