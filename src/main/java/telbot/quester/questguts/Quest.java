package telbot.quester.questguts;

public class Quest {

    private Integer id;
    private String text;
    private Integer like;

    public Quest(Integer id, String text) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
