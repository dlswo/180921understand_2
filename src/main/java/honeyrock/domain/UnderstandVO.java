package honeyrock.domain;

import lombok.Data;

import java.util.Date;

@Data
public class UnderstandVO {
    private Integer unqno, uno, unqgubun;
    private String unqtitle, uncmt;
    private Date unqregdate, unqlimit;
}
