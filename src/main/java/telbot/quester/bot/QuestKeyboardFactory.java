package telbot.quester.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class QuestKeyboardFactory {

    public static ReplyKeyboard getQuestKeyboard(){

        //клавиатруа это по сути несколько строк с кнопками
        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        //в первой строке - лайк и дизлайк
        KeyboardRow row = new KeyboardRow();
        row.add("\uD83D\uDC4D"); // положительная реакция
        row.add("\uD83D\uDC4E"); // отрицательная реакция
        keyboardRowList.add(row);


        //во второй строке кнопка для нового квеста
        KeyboardRow row1 = new KeyboardRow();
        KeyboardButton button = new KeyboardButton();
        button.setText("Следующий квест!");
        row1.add(button);
        keyboardRowList.add(row1);

        ReplyKeyboard replyKeyboard = new ReplyKeyboardMarkup(keyboardRowList);
        return replyKeyboard;
    }
}
