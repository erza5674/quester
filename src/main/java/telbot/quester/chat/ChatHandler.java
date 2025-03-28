package telbot.quester.chat;

import java.util.ArrayList;

public final class ChatHandler {

    private static ChatHandler instanse;
    private static ArrayList<Chat> chatArrayList;

    public ChatHandler(){
    }

    public ChatHandler(ChatHandler chatHandler) {
        instanse= chatHandler;
    }

    public static ChatHandler getInstanse(){
        if (instanse == null){
            instanse = new ChatHandler();
        }
        if (chatArrayList == null){
            chatArrayList = new ArrayList<>();
        }

        return instanse;
    }

    public void onStartMessage(Long id) {
        // Check if the chat exists
        if (doesChatExistById(id)) {
            // Handle existing chat
        } else {
            // Handle new chat
            chatArrayList.add(createNewChat(id));
        }
    }

    public boolean doesChatExistById(Long id) {
        if (chatArrayList == null) {
            return false; // If the list is not initialized, return false
        }

        for (Chat chat : chatArrayList) {
            if (chat.getChatId().equals(id)) {
                return true; // Chat with the specified ID exists
            }
        }
        return false; // Chat with the specified ID does not exist
    }

    public Chat createNewChat(Long id){
        return new Chat().setChatId(id);
    }


    //Метод что бы вернуть чат по ИД
    public Chat getCurrentChat(Long id){
        Chat chat;

        if (chatArrayList == null){
            //pizda rulu
            return null;
        }

        for (Chat chat1 : chatArrayList){
            if (chat1.getChatId().equals(id)){
                return chat1;
            }
        }

        //pizda rulu!
        return null;
    }
}
