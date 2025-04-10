package telbot.quester.questguts;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Entity
@Table(name = "Quests")
public class Quest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String text;
    private Integer like;

    public Quest(UUID id, String text) {
        this.id = id;
        this.text = text;
        this.like = 0;
    }

    public Quest() {
        this.like = 0;
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
