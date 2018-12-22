package test.pojo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "message")
@Getter
@Setter
@ToString
public class Message implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String context;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
