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

import java.util.Map;
import java.util.UUID;

import static telbot.quester.bot.Constants.START_TEXT;

public class ResponseHandler {
    private final SilentSender sender;
    private final Map<Long, Chat> chatStates;
    private final ChatHandler chatHandler;

    @Autowired
    private QuestRepository questRepository;

    public ResponseHandler(SilentSender sender, DBContext db){
        this.sender = sender;
        chatStates = db.getMap(Constants.CHAT_STATES);
        questRepository = new QuestRepository();
        chatHandler = ChatHandler.getInstanse();
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
            chatHandler.addAction(chatId, Action.QUEST_REQUEST);
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
            chatHandler.addAction(chatId, Action.LIKE);
        }

        //dislike
        if (message.getText().contains("\uD83D\uDC4E")){
            System.out.println("Recived dislike");
            chatHandler.addAction(chatId, Action.DISLIKE);
        }
    }

    public boolean userIsActive(Long chatId) {
        return chatStates.containsKey(chatId);
    }

}
