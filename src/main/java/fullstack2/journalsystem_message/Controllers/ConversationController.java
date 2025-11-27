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

    @PostMapping("/createConversation")
    public ConversationDb createConversation(@RequestBody ConversationDb conversation){
        return conversationService.createConversation(conversation);
    }

    @GetMapping("/getMyConversations/{userId}")
    public List<ConversationDb> getMyConversations(@PathVariable Long userId) {
        return conversationService.getAllConversations(userId);
    }

    @GetMapping("/getConversationByUsers/{firstUserId}/{secondUserId}")
    public ConversationDb getConversationByUsers(@PathVariable Long firstUserId, @PathVariable Long secondUserId){
        return conversationService.getConversationFromParticipants(firstUserId, secondUserId);
    }

    @GetMapping("/getConversationWithMessages/{conversationId}")
    public ConversationDb getConversationWithMessages(@PathVariable Long conversationId){
        ConversationDb conversationDb = conversationService.getConversation(conversationId);
        conversationDb.setMessageDbs(messageService.getMessagesFromConversation(conversationId));
        return conversationDb;
    }


}
