package fullstack2.journalsystem_message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import fullstack2.journalsystem_message.Controllers.ConversationController;
import fullstack2.journalsystem_message.Controllers.MessageController;
import fullstack2.journalsystem_message.Persistence.Models.ConversationDb;
import fullstack2.journalsystem_message.Persistence.Models.MessageDb;

@SpringBootTest
@Transactional
class ConversationTests {
     
    ConversationController controller;
    MessageController messageController;

    public ConversationTests(ConversationController controller, MessageController messageController){
      this.controller = controller;
      this.messageController = messageController;
    }

    @Test
    void shouldBeTrue() {
      assertTrue(true);
    }


    MessageDb testMessage;
    String firstParticipantId;
    String secondParticipantId;

    @BeforeEach
    void init(){
      firstParticipantId = "-1";
      secondParticipantId = "-2";
      testMessage = new MessageDb("-1", new ConversationDb(firstParticipantId, secondParticipantId), "Test message");
    }

    
    @Test
    @Rollback
    @DisplayName("Create conversation")
    void createConversation(){
      ConversationDb createdConvo = controller.createConversation(firstParticipantId, secondParticipantId);
      assertNotNull(createdConvo, "Conversation should not be null");
    }

    @Test
    @Rollback
    @DisplayName("Get my conversations")
    void getMyConversations(){
      ConversationDb createdConvo = controller.createConversation(firstParticipantId, secondParticipantId);
      assertNotNull(createdConvo, "Created conversation should not be null");
      
      List<ConversationDb> gottenConversations1 = controller.getMyConversations("-1");
      assertNotEquals(0, gottenConversations1.size(), "User with id -1 should have a conversation");

      List<ConversationDb> gottenConversations2 = controller.getMyConversations("-2");
      assertNotEquals(0, gottenConversations2.size(), "User with id -2 should have a conversation");
    
      ConversationDb convoByUsers1 = controller.getConversationByUsers("-1", "-2");
      assertNotNull(convoByUsers1, "Should be a conversation between user with id -1 and -2");

      ConversationDb convoByUsers2 = controller.getConversationByUsers("-2", "-1");
      assertNotNull(convoByUsers2, "Should be a conversation between user with id -2 and -1");

      assertEquals(convoByUsers1.getId(), convoByUsers2.getId(), "Conversation between user with id -1 and -2 should be the same as with id -2 and -1");
    }
      
    @Test
    @Rollback
    @DisplayName("Send message")
    void sendMessage(){
      ConversationDb createdConvo = controller.createConversation(firstParticipantId, secondParticipantId);
      assertNotNull(createdConvo, "Created conversation should not be null");

      MessageDb sentMessage = messageController.sendMessage(testMessage, createdConvo.getId());
      assertNotNull(sentMessage, "Sent message should not be null");
      
      List<MessageDb> gottenMessages = messageController.getMessages(createdConvo.getId());
      assertNotEquals(0, gottenMessages.size(), "A message should have been sent");
    }


}
