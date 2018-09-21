package honeyrock.domain;

import lombok.Data;

import java.util.Date;

@Data
public class MemberVO {
    private Integer uno;
    private String userid, urole, uname, userpw, userusing;
    private Date regdate;
}
