package honeyrock.domain;

import lombok.Data;

import java.util.Date;

@Data
public class AnswerVO {
    private Integer uno, unqno, anqty;
    private String unqtitle, anqtitle;
    private Date anqregdate;
}
