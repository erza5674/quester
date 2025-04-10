package telbot.quester.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import telbot.quester.chat.Chat;
import telbot.quester.chat.ChatHandler;
import telbot.quester.stats.Action;
import telbot.quester.stats.Language;
import telbot.quester.stats.UserState;
import telbot.quester.questguts.Quest;
import telbot.quester.questguts.QuestRepository;

import java.util.IllegalFormatCodePointException;
import java.util.Map;
import java.util.UUID;

import static telbot.quester.bot.Constants.START_TEXT;

public class ResponseHandler {
    private final SilentSender sender;
    private final ChatHandler chatHandler;

    @Autowired
    private QuestRepository questRepository;

    public ResponseHandler(SilentSender sender, DBContext db){
        this.sender = sender;
        questRepository = new QuestRepository();
        chatHandler = ChatHandler.getInstanse(db);
    }

    public void replyToStart(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(START_TEXT);
        message.setReplyMarkup(QuestKeyboardFactory.getQuestKeyboard());
        sender.execute(message);

        //Запихнем данные о новом чате
        chatHandler.onStartMessage(chatId);
        chatHandler.addAction(chatId, Action.START);
    }

    public void replyToButtons(long chatId, Message message){
        if (message.getText().equalsIgnoreCase("/stop")){
            System.out.println("stop maybe?");
            chatHandler.addAction(chatId,Action.STOP);
        }

        if (message.getText().equalsIgnoreCase("/start")){
            System.out.println("start maybe?");
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);

            sendMessage.setReplyMarkup(QuestKeyboardFactory.getQuestKeyboard());

            sender.execute(sendMessage);
        }

        if (message.getText().equalsIgnoreCase("/quest") || message.getText().equalsIgnoreCase("Следующий квест!")) {
            System.out.println("New quest request");

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);

            Quest quest = this.questRepository.get_random_quest();

            sendMessage.setText(quest.getText());
            sendMessage.setReplyMarkup(QuestKeyboardFactory.getQuestKeyboard());
            sender.execute(sendMessage);

            System.out.println("Sended quest :" + sendMessage.getText().toString());
            chatHandler.rememberLastQuest(chatId, quest.getId());
        }

        if (message.getText().contains("add")) {
            //вырезать часть с add
            String input_text = new String();

            input_text = message.getText().replace("add","").trim();

            //добавить новый текст
            this.questRepository.addQuest(input_text);
            chatHandler.addAction(chatId, Action.ADD_QUEST);
        }

        if (message.getText().contains("/alllist")){

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);

            sendMessage.setText(this.questRepository.get_list_of_all_quests());

            sender.execute(sendMessage);
            chatHandler.addAction(chatId, Action.OTHER);

        }

        if (message.getText().contains("/delete")){
            String input = message.getText().replace("/delete", "").trim();
            this.questRepository.delete_quest_by_id(UUID.fromString(input));
            chatHandler.addAction(chatId, Action.DELETION);
        }

        //like
        if (message.getText().contains("\uD83D\uDC4D")){
            System.out.println("Recived like");
//            likeHandler(chatId)
            likeDislikeHandler(chatId, Action.LIKE);
            chatHandler.addAction(chatId, Action.LIKE);
        }

        //dislike
        if (message.getText().contains("\uD83D\uDC4E")){
            System.out.println("Recived dislike");
//            dislikeHandler(chatId);
            likeDislikeHandler(chatId, Action.DISLIKE);
            chatHandler.addAction(chatId, Action.DISLIKE);
        }
    }

    public boolean userIsActive(Long chatId) {
        return chatHandler.doesChatExistById(chatId);
    }

    private void likeHandler(Long chatId){
        //проверить есть ли ююид квеста
        UUID questUUID = chatHandler.getChatById(chatId).getLastQuestUUId();

        if (questUUID == null){ return; }

        //Проверить что было предыдущим действием
        Action action = chatHandler.getChatById(chatId).getLastAction();
        if (action != Action.QUEST_REQUEST){ return; }

        //Если все ок - поставить лайк
        questRepository.getQuestByID(questUUID).like();
    }

    private void dislikeHandler(Long chatID){
        //проверить есть ли ююид квеста
        UUID questUUID = chatHandler.getChatById(chatID).getLastQuestUUId();

        if (questUUID == null){ return; }

        //Проверить что было предыдущим действием
        Action action = chatHandler.getChatById(chatID).getLastAction();
        if (action != Action.QUEST_REQUEST){ return; }

        //Если все ок - поставить лайк
        questRepository.getQuestByID(questUUID).dislike();
    }

    private void likeDislikeHandler(Long chatID, Action action) {
        String text = new String();
        //проверить есть ли ююид квеста
        UUID questUUID = chatHandler.getChatById(chatID).getLastQuestUUId();

        if (questUUID == null) {
            return;
        }

        //Проверить что было предыдущим действием
        Action lastAction = chatHandler.getChatById(chatID).getLastAction();
        if (lastAction != Action.QUEST_REQUEST ){
            return;
        }

        //Если все ок - поставить лайк
        switch (action){
            case LIKE:
                Integer like = questRepository.getQuestByID(questUUID).like();
                text = "Отлично! Задание -  "+ questRepository.getQuestByID(questUUID).getText() + " теперь имеет рейтинг " + like.toString();
                break;
            case DISLIKE:
                Integer dislike = questRepository.getQuestByID(questUUID).dislike();
                text = "Ну вот :( Задание -  "+ questRepository.getQuestByID(questUUID).getText() + " теперь имеет рейтинг " + dislike.toString();
                break;
        }

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatID);

        sendMessage.setText(text);

        sender.execute(sendMessage);

    }
}
