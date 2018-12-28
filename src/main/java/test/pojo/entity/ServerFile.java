package test.pojo.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
public class ServerFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String type;
    @Column(nullable = false)
    private String path;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
