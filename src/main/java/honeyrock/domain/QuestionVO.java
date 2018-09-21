package honeyrock.domain;

import lombok.Data;

import java.util.Date;

@Data
public class QuestionVO {
    private Integer qno, uno;
    private String uname, qtitle, qcmt, qfile;
    private Date qregdate;
}
