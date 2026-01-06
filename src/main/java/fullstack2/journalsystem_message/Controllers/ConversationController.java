package fullstack2.journalsystem_message.Controllers;

import fullstack2.journalsystem_message.Persistence.Models.ConversationDb;
import fullstack2.journalsystem_message.Persistence.Service.ConversationService;
import fullstack2.journalsystem_message.Persistence.Service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversation")
public class ConversationController {

    private final ConversationService conversationService;
    private final MessageService messageService;

    public ConversationController(ConversationService conversationService, MessageService messageService) {
        this.conversationService = conversationService;
        this.messageService = messageService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/createConversation/{firstParticipantId}/{secondParticipantId}")
    public ConversationDb createConversation(@PathVariable String firstParticipantId, @PathVariable String secondParticipantId) {
        return conversationService.createConversation(new ConversationDb(firstParticipantId, secondParticipantId));
    }

    @GetMapping("/getMyConversations/{userId}")
    public List<ConversationDb> getMyConversations(@PathVariable String userId) {
        return conversationService.getAllConversations(userId);
    }

    @GetMapping("/getConversationByUsers/{firstUserId}/{secondUserId}")
    public ConversationDb getConversationByUsers(@PathVariable String firstUserId, @PathVariable String secondUserId){
        return conversationService.getConversationFromParticipants(firstUserId, secondUserId);
    }

    @GetMapping("/getConversationWithMessages/{conversationId}")
    public ConversationDb getConversationWithMessages(@PathVariable Long conversationId){
        ConversationDb conversationDb = conversationService.getConversation(conversationId);
        conversationDb.setMessageDbs(messageService.getMessagesFromConversation(conversationId));
        return conversationDb;
    }


}
