    package fullstack2.journalsystem_message;

    import fullstack2.journalsystem_message.Persistence.Models.ConversationDb;
    import fullstack2.journalsystem_message.Persistence.Reposetories.ConversationRepository;
    import fullstack2.journalsystem_message.Persistence.Service.ConversationService;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.DisplayName;
    import org.junit.jupiter.api.Test;
    import org.mockito.Mockito;
    import org.springframework.test.context.bean.override.mockito.MockitoBean;

    import java.util.ArrayList;
    import java.util.List;

    import static org.junit.jupiter.api.Assertions.*;
    import static org.mockito.Mockito.*;

    class ConversationServiceTest {


        private ConversationRepository repo;

        private ConversationService service;

        @BeforeEach
        void setup() {
            repo = Mockito.mock(ConversationRepository.class);
            service = new ConversationService(repo); // assumes constructor injection
        }

        @Test
        @DisplayName("Create conversation saves when not existing")
        void createConversation_saves_whenNotExisting() {
            String a = "userA";
            String b = "userB";

            when(repo.findByFirstParticipantIdAndSecondParticipantId(a, b)).thenReturn(null);
            when(repo.findByFirstParticipantIdAndSecondParticipantId(b, a)).thenReturn(null);

            ConversationDb saved = new ConversationDb(a, b);
            saved.setId(123L);
            when(repo.save(any(ConversationDb.class))).thenReturn(saved);

            ConversationDb result = service.createConversation(new ConversationDb(a, b));

            assertNotNull(result);
            assertEquals(123L, result.getId());

            verify(repo, times(1)).save(any(ConversationDb.class));
        }

        @Test
        @DisplayName("Create conversation only happens once when flipping participants")
        void createConversation_savesOnce_thenReturnsExisting_whenFlippedExists() {
            String a = "userA";
            String b = "userB";

            ConversationDb saved = new ConversationDb(a, b);
            saved.setId(99L);

            when(repo.findByFirstParticipantIdAndSecondParticipantId(a, b)).thenReturn(null);
            when(repo.findByFirstParticipantIdAndSecondParticipantId(b, a)).thenReturn(null);

            when(repo.save(any(ConversationDb.class))).thenReturn(saved);

            ConversationDb result1 = service.createConversation(new ConversationDb(a, b));
            assertNotNull(result1);
            assertEquals(99L, result1.getId());

            when(repo.findByFirstParticipantIdAndSecondParticipantId(b, a)).thenReturn(saved);

            ConversationDb result2 = service.createConversation(new ConversationDb(b, a));
            assertNotNull(result2);
            assertEquals(99L, result2.getId());

            verify(repo, times(1)).save(any(ConversationDb.class));
        }

        @Test
        @DisplayName("Get all conversations")
        void getMyConversations_mergesFirstAndSecondLists() {
            String me = "userX";

            List<ConversationDb> c1 = new ArrayList<ConversationDb>();
            c1.add(new ConversationDb(me, "u1"));
            c1.get(0).setId(1);

            List<ConversationDb> c2 = new ArrayList<ConversationDb>();
            c2.add(new ConversationDb("u2", me));
            c2.get(0).setId(2);

            when(repo.findAllByFirstParticipantId(me)).thenReturn(c1);
            when(repo.findAllBySecondParticipantId(me)).thenReturn(c2);

            List<ConversationDb> result = service.getAllConversations(me);

            assertEquals(2, result.size());
            assertTrue(result.stream().anyMatch(c -> c.getId() == 1));
            assertTrue(result.stream().anyMatch(c -> c.getId() == 2));
        }
    }