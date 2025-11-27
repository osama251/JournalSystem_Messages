package fullstack2.journalsystem_message.Persistence.Reposetories;

import fullstack2.journalsystem_message.Persistence.Models.ConversationDb;
import fullstack2.journalsystem_message.Persistence.Models.MessageDb;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversationRepository extends JpaRepository<ConversationDb, Long> {
    ConversationDb findByFirstParticipantId(long id);
    ConversationDb findBySecondParticipantId(long id);

    ConversationDb findByFirstParticipantIdAndSecondParticipantId(Long firstParticipantId, Long secondParticipantId);

    List<ConversationDb> findAllByFirstParticipantId(Long id);
    List<ConversationDb> findAllBySecondParticipantId(Long id);

}
