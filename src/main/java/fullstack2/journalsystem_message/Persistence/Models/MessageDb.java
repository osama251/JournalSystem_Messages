package fullstack2.journalsystem_message.Persistence.Models;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="message")
public class MessageDb {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(nullable=false)
    private LocalDateTime sendDate;

    @Column(nullable=false)
    private String senderId;

    @ManyToOne()
    @JoinColumn(name = "conversationId", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private ConversationDb conversationId;

    @Column(nullable=false)
    private String message;

    public MessageDb(){}

    public MessageDb(LocalDateTime sendDate, String senderId, ConversationDb conversationId, String message) {
        this.sendDate = sendDate;
        this.senderId = senderId;
        this.conversationId = conversationId;
        this.message = message;
    }

    public MessageDb(String senderId, ConversationDb conversationId, String message) {
        this.sendDate = LocalDateTime.now();
        this.senderId = senderId;
        this.conversationId = conversationId;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getSendDate() {
        return sendDate;
    }

    public String getSenderId() {
        return senderId;
    }

    public ConversationDb getConversationId() {
        return conversationId;
    }

    public String getMessage() {
        return message;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSendDate(LocalDateTime sendDate) {
        this.sendDate = sendDate;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setConversationId(ConversationDb conversationId) {
        this.conversationId = conversationId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MessageDb{" +
                "id=" + id +
                ", sendDate=" + sendDate +
                ", senderId=" + senderId +
                ", conversationId=" + conversationId +
                ", message='" + message + '\'' +
                '}';
    }
}
