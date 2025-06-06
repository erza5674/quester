package telbot.quester.chat;

import org.checkerframework.checker.units.qual.A;
import org.telegram.abilitybots.api.db.DBContext;
import telbot.quester.stats.Action;
import telbot.quester.stats.Language;
import telbot.quester.stats.UserState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class ChatHandler {

    private static ChatHandler instanse;
    private static ArrayList<Chat> chatArrayList;
    private static Map<Long,Chat> chatMap;

    public ChatHandler(){
    }

    public ChatHandler(ChatHandler chatHandler) {
        instanse= chatHandler;
    }

    public static ChatHandler getInstanse(DBContext db){
        if (instanse == null){ instanse = new ChatHandler(); }
        if (chatArrayList == null){ chatArrayList = new ArrayList<>(); }

        if (chatMap == null){
            fillChatMapFromDB(db);
        }

        return instanse;
    }

    private static void fillChatMapFromDB(DBContext db){
        if (chatMap == null){
            chatMap = new HashMap<>();
        }


        Map<Object, Object> userDBMap = db.getMap("USER_ID");

        for (Map.Entry<Object, Object> entry : userDBMap.entrySet()) {
            Chat chat = new Chat();
            chat.setChatId((Long) entry.getValue());
            chat.setLanguage(Language.RUSSIAN);

            chatMap.put(chat.getChatId(), chat);
        }
    }

    public void onStartMessage(Long id) {
        // Check if the chat exists
        if (chatMap.containsKey(id)) {
            // Handle existing chat
            System.out.println("Chat allready exist");
        } else {
            // Handle new chat
            Chat newChat = createNewChat(id);
            chatMap.put(newChat.getChatId(),newChat);
        }

        addAction(id, Action.START);
    }

    public boolean doesChatExistById(Long id) {
        if (chatMap.containsKey(id)){
            return true;
        }
        return false; // Chat with the specified ID does not exist
    }

    public Chat createNewChat(Long id){
        return new Chat().setChatId(id);
    }

    //Метод что бы вернуть чат по ИД
    public Chat getChatById(Long id){
        if (chatArrayList == null){
            return null; //pizda rulu
        }

        return chatMap.get(id);
    }

    public void addAction(Long id, Action action){
        chatMap.get(id).setLastAction(action);
    }

    public void  rememberLastQuest(Long chatID, UUID questId){
        chatMap.get(chatID).setLastAction(Action.QUEST_REQUEST).setLastQuestUUId(questId);
    }
}
