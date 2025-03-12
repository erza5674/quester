package telbot.quester.bot;

import telbot.quester.bot.stats.Action;
import telbot.quester.bot.stats.Language;
import telbot.quester.bot.stats.UserState;

import java.util.UUID;

public class Chat {

    private Long chatId;
    private UserState userState;
    private Language language;
    private Action lastAction;
    private UUID lastQuestUUId;

    public Chat(Long chatId, Language language) {
        this.chatId = chatId;
        this.language = language;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public UserState getUserState() {
        return userState;
    }

    public void setUserState(UserState userState) {
        this.userState = userState;
    }

    public UUID getLastQuestUUId() {
        return lastQuestUUId;
    }

    public void setLastQuestUUId(UUID lastQuestUUId) {
        this.lastQuestUUId = lastQuestUUId;
    }

    public Action getLastAction() {
        return lastAction;
    }

    public void setLastAction(Action lastAction) {
        this.lastAction = lastAction;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
