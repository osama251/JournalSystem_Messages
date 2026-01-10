package fullstack2.journalsystem_message.Persistence.Service;

import fullstack2.journalsystem_message.Persistence.Models.ConversationDb;
import fullstack2.journalsystem_message.Persistence.Reposetories.ConversationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;

    public ConversationService(ConversationRepository conversationRepository){
        this.conversationRepository = conversationRepository;
    }

    @Transactional
    public ConversationDb createConversation(ConversationDb conversationDb){
        ConversationDb existingConvo = getConversationFromParticipants(conversationDb.getFirstParticipantId(), conversationDb.getSecondParticipantId());
        if(existingConvo != null){
            return existingConvo;
        }
        return conversationRepository.save(conversationDb);
    }

    public ConversationDb getConversation(long id){
        return conversationRepository.getReferenceById(id);
    }

    public ConversationDb getConversationFromParticipants(String firstUserId, String secondUserId){
        ConversationDb firstTry = conversationRepository.findByFirstParticipantIdAndSecondParticipantId(firstUserId, secondUserId);
        if(firstTry != null) return firstTry;
        return conversationRepository.findByFirstParticipantIdAndSecondParticipantId(secondUserId, firstUserId);
    }

    public List<ConversationDb> getAllConversations(String userId){
        List<ConversationDb> conversations = conversationRepository.findAllByFirstParticipantId(userId);
        conversations.addAll(conversationRepository.findAllBySecondParticipantId(userId));
        return conversations;
    }


}
