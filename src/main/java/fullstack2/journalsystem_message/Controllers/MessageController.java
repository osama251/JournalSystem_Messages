package fullstack2.journalsystem_message.Controllers;

import fullstack2.journalsystem_message.Persistence.Models.MessageDb;
import fullstack2.journalsystem_message.Persistence.Service.ConversationService;
import fullstack2.journalsystem_message.Persistence.Service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    private final ConversationService conversationService;
    private final MessageService messageService;

    public MessageController(ConversationService conversationService, MessageService messageService){
        this.conversationService = conversationService;
        this.messageService = messageService;
    }

    @GetMapping("/getMessages/{conversationId}")
    public List<MessageDb> getMessages(@PathVariable Long conversationId){
        return messageService.getMessagesFromConversation(conversationId);
    }

    @PostMapping("/sendMessage/{conversationId}")
    public MessageDb sendMessage(@RequestBody MessageDb message, @PathVariable Long conversationId){
        return messageService.createMessage(message, conversationId);
    }

}
