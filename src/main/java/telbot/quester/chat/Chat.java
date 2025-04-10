package telbot.quester.chat;

import jakarta.persistence.Entity;
import org.springframework.stereotype.Component;
import telbot.quester.stats.Action;
import telbot.quester.stats.Language;
import telbot.quester.stats.UserState;

import java.util.UUID;

public class Chat {

    private Long chatId;
    private UserState userState;
    private Language language;
    private Action lastAction;
    private UUID lastQuestUUId;

    public Chat() {
        this.chatId = chatId;
        this.language = language;
    }

    public Long getChatId() {
        return chatId;
    }

    public Chat setChatId(Long chatId) {
        this.chatId = chatId;
        return this;
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

    public Chat setLastAction(Action lastAction) {
        this.lastAction = lastAction;
        return this;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
