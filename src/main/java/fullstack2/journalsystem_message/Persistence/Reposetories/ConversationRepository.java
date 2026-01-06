package fullstack2.journalsystem_message.Persistence.Reposetories;

import fullstack2.journalsystem_message.Persistence.Models.ConversationDb;
import fullstack2.journalsystem_message.Persistence.Models.MessageDb;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversationRepository extends JpaRepository<ConversationDb, Long> {
    ConversationDb findByFirstParticipantId(String id);
    ConversationDb findBySecondParticipantId(String id);

    ConversationDb findByFirstParticipantIdAndSecondParticipantId(String firstParticipantId, String secondParticipantId);

    List<ConversationDb> findAllByFirstParticipantId(String id);
    List<ConversationDb> findAllBySecondParticipantId(String id);

}
