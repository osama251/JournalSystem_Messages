package fullstack2.journalsystem_message.Persistence.Service;

import fullstack2.journalsystem_message.Persistence.Models.ConversationDb;
import fullstack2.journalsystem_message.Persistence.Models.MessageDb;
import fullstack2.journalsystem_message.Persistence.Reposetories.ConversationRepository;
import fullstack2.journalsystem_message.Persistence.Reposetories.MessageRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.*;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;

    public MessageService(MessageRepository messageRepository, ConversationRepository conversationRepository) {
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
    }

    @Transactional
    public MessageDb createMessage(MessageDb messageDb, long conversationId) throws RuntimeException{
        ConversationDb conversationDb = conversationRepository
                .findById(conversationId)
                .orElseThrow(() ->
                        new RuntimeException("Conversation not found: " + conversationId)
                );
        messageDb.setConversationId(conversationDb);
        return messageRepository.save(messageDb);
    }

    public MessageDb getMessage(long id){
        return messageRepository.getReferenceById(id);
    }

    public List<MessageDb> getMessagesFromConversation(long conversationId){
        return messageRepository.findMessagesByConversationId_Id(conversationId);
    }

}
