package telbot.quester.questguts;

import java.util.UUID;

public class Quest {

    private UUID id;
    private String text;
    private Integer like;

    public Quest(UUID id, String text) {
        this.id = id;
        this.text = text;
    }

    public Quest() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer like(){
        this.like += 1;
        return  this.like;
    }

    public Integer dislike(){
        this.like -= 1;
        return this.like;
    }
}
