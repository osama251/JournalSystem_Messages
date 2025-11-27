package fullstack2.journalsystem_message.Persistence.Reposetories;

import fullstack2.journalsystem_message.Persistence.Models.ConversationDb;
import fullstack2.journalsystem_message.Persistence.Models.MessageDb;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageDb, Long> {
    List<MessageDb> findMessagesByConversationId_Id(long conversationId);
}
