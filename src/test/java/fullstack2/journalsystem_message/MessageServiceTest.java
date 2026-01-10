package fullstack2.journalsystem_message;

import fullstack2.journalsystem_message.Persistence.Models.ConversationDb;
import fullstack2.journalsystem_message.Persistence.Models.MessageDb;
import fullstack2.journalsystem_message.Persistence.Reposetories.ConversationRepository;
import fullstack2.journalsystem_message.Persistence.Reposetories.MessageRepository;
import fullstack2.journalsystem_message.Persistence.Service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageServiceTest {

    private MessageRepository messageRepo;
    private ConversationRepository convoRepo;
    private MessageService service;

    @BeforeEach
    void setup() {
        messageRepo = mock(MessageRepository.class);
        convoRepo = mock(ConversationRepository.class);
        service = new MessageService(messageRepo, convoRepo); // constructor injection
    }

    @Test
    @DisplayName("Cannot create message if conversation does not exist")
    void sendMessage_throws_ifConversationMissing() {
        long convoId = 5L;

        when(convoRepo.findById(convoId)).thenReturn(Optional.empty());

        MessageDb msg = new MessageDb("sender", null, "hello");

        assertThrows(RuntimeException.class, () -> service.createMessage(msg, convoId));

        verify(messageRepo, never()).save(any());
    }

    @Test
    @DisplayName("Successfully create message")
    void sendMessage_saves_whenConversationExists() {
        long convoId = 5L;
        ConversationDb convo = new ConversationDb("a", "b");
        convo.setId(convoId);

        when(convoRepo.findById(convoId)).thenReturn(Optional.of(convo));

        MessageDb msg = new MessageDb("sender", null, "hello");
        MessageDb saved = new MessageDb("sender", convo, "hello");
        saved.setId(10L);

        when(messageRepo.save(any(MessageDb.class))).thenReturn(saved);

        MessageDb result = service.createMessage(msg, convoId);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        verify(messageRepo, times(1)).save(any(MessageDb.class));
    }
}