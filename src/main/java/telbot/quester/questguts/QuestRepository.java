package telbot.quester.questguts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class QuestRepository {

    private static Map<UUID, Quest> questMap = new HashMap<>();

    public QuestRepository() {
        if (questMap.isEmpty()){
            generate_test_quest();
        }
    }

    private void generate_test_quest(){
        addQuest("Полей растения");
        addQuest("Прогуляйся по району");
        addQuest("Поприседай");
        addQuest("Свари суп");
        addQuest("Почитай книгу");
    }

    public Quest get_random_quest(){
        if (questMap.isEmpty()) {
            return null; // or handle the case when there are no quests
        }

        // Create a Random object
        Random random = new Random();

        // Get a random index
        int randomIndex = random.nextInt(questMap.size());

        // Retrieve the quest using the random index
        return (Quest) questMap.values().toArray()[randomIndex];
    }

    public void addQuest(String questText) {
        int newId = questMap.size() + 1; // Generate a new ID
        Quest newQuest = new Quest();
        newQuest.setId(UUID.randomUUID());
        newQuest.setText(questText);
        questMap.put(newQuest.getId(), newQuest); // Add the new quest to the map
    }

    public String get_list_of_all_quests(){
        System.out.println("Getting list of all quests");
        StringBuilder result = new StringBuilder();
        result.append("List of all quests: " +"\n");

        questMap.forEach( (key, value) -> result.append("key: " + key + "  | ").append(value.getText()).append("\n") );

        return result.toString().trim();
    }

    public void delete_quest_by_id(UUID id){

        if (questMap.containsKey(id)){
            questMap.remove(id);
            System.out.println(id + " deleted from quest map");
        } else {
            System.out.println("Non valid key value was given ");
        }
    }

    public Quest getQuestByID(UUID id){
        if (!questMap.containsKey(id)){
            return null;
        }
        return questMap.get(id);
    }

}

