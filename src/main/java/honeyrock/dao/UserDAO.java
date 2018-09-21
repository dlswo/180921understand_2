package honeyrock.dao;

import honeyrock.domain.AnswerVO;
import honeyrock.domain.MemberVO;
import honeyrock.domain.PageDTO;
import honeyrock.domain.UnderstandVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAO {

    private static Map<Integer, String> undMap = new HashMap<>();


    private static String UQM = "select * from (select/*+INDEX_DESC(tbl_understand_board pk_understand_board) */\n" +
            "ROWNUM rn, unqno, mem.uno,  unqtitle, unqgubun, UNQREGDATE, unqlimit\n" +
            "from TBL_understand_board,(select uno, uname from tbl_unmember) mem\n" +
            "where UNQNO > 0\n" +
            "AND ROWNUM <= (? * ?)\n" +
            "and mem.uno=tbl_understand_board.uno)\n" +
            "where rn > ((? - 1) * ?) and unqlimit - sysdate > 0";
    private static String DQN = "select unqtitle from tbl_understand_board\n" +
            "where unqno = ?";
    private static String UUD = "insert into tbl_answer_board(uno, unqno, anqty, anqtitle) \n" +
            "values(?, ?, ?, ?)";
    private static final String MAXRNO = "select count(*) from tbl_understand_board  where unqlimit - sysdate > 0";


    private static final String INFO = "select * from tbl_unmember where uno = ?";

    private static final String UMY = "update tbl_unmember set userpw = ?, uname = ? where uno = ?";

    private static final String TAL =  "delete from tbl_unmember\n" +
            "where uno = ?";

    private static final String QCNT = "select\n" +
            "answer.unqno, answer.uno, unqtitle, anqtitle, anqregdate \n" +
            "from TBL_understand_board,(select * from tbl_answer_board where uno=?) answer\n" +
            "where TBL_understand_board.unqno=answer.unqno\n" +
            "order by answer.unqno desc";

    public List<UnderstandVO> getList(final PageDTO pageDTO) throws Exception{

        undMap.put(0,"이해했니?");
        undMap.put(1,"만들수 있겠어요?");
        undMap.put(2,"쉬고 싶니?");
        undMap.put(3,"평생 쉬어");

        final List<UnderstandVO> list = new ArrayList<>();
        new QueryExecutor() {
            public void doJob() throws Exception {
                stmt = con.prepareStatement(UQM);
                int i = 1;
                stmt.setInt(i++, pageDTO.getPage());
                stmt.setInt(i++, pageDTO.getSize());
                stmt.setInt(i++, pageDTO.getPage());
                stmt.setInt(i++, pageDTO.getSize());
                rs = stmt.executeQuery();

                while (rs.next()) {
                    UnderstandVO vo = new UnderstandVO();
                    // unqno, mem.uno,  unqtitle, unqgubun, UNQREGDATE, unqlimit\
                    int idx = 2;
                    vo.setUnqno(rs.getInt(idx++));
                    vo.setUno(rs.getInt(idx++));
                    vo.setUnqtitle(rs.getString(idx++));
                    vo.setUnqgubun(rs.getInt(idx++));
                    vo.setUnqregdate(rs.getDate(idx++));
                    vo.setUnqlimit(rs.getDate(idx++));

                    vo.setUncmt(undMap.get(vo.getUnqgubun()));
                    list.add(vo);
                }
            }
        }.executeAll();
        return list;
    }



    public String readUnqtitle(final int unqno){
        final String[] dqn = {""};
        new QueryExecutor() {
            @Override
            public void doJob() throws Exception {
                stmt = con.prepareStatement(DQN);
                stmt.setInt(1,unqno);
                rs = stmt.executeQuery();
                rs.next();
                dqn[0] = rs.getString(1);

            }
        }.executeAll();

        return dqn[0];
    }

    public void addAnswer(final AnswerVO vo) {

        new QueryExecutor() {
            public void doJob() throws Exception {
                stmt = con.prepareStatement(UUD);
                stmt.setInt(1, vo.getUno());
                stmt.setInt(2,vo.getUnqno());
                stmt.setInt(3,vo.getAnqty());
                stmt.setString(4,vo.getAnqtitle());
                stmt.executeUpdate();
            }
        }.executeAll();
    }

    public int getMaxRno(){
        final int[] maxRno = {0};
        new QueryExecutor() {
            @Override
            public void doJob() throws Exception {
                stmt = con.prepareStatement(MAXRNO);
                rs = stmt.executeQuery();
                rs.next();
                maxRno[0] = rs.getInt(1);

            }
        }.executeAll();

        return maxRno[0];
    }

    public MemberVO readMyinfo(final int uno){
        final List<MemberVO> list = new ArrayList<>();
        new QueryExecutor() {
            @Override
            public void doJob() throws Exception {
                stmt = con.prepareStatement(INFO);
                stmt.setInt(1,uno);
                rs = stmt.executeQuery();
                rs.next();
                MemberVO vo = new MemberVO();
                int idx = 1;
                vo.setUno(rs.getInt(idx++));
                vo.setUserid(rs.getString(idx++));
                vo.setUrole(rs.getString(idx++));
                vo.setRegdate(rs.getDate(idx++));
                vo.setUname(rs.getString(idx++));
                vo.setUserpw(rs.getString(idx++));

                list.add(vo);


            }
        }.executeAll();

        return list.get(0);

    }

    public List<AnswerVO> quesInfo(final int uno){
        final List<AnswerVO> list = new ArrayList<>();
        new QueryExecutor() {
            public void doJob() throws Exception {
                stmt = con.prepareStatement(QCNT);


                stmt.setInt(1,uno);
                rs = stmt.executeQuery();

                while (rs.next()) {
                    AnswerVO vo = new AnswerVO();
                    int idx = 1;
                    vo.setUnqno(rs.getInt(idx++));
                    vo.setUno(rs.getInt(idx++));
                    vo.setUnqtitle(rs.getString(idx++));
                    vo.setAnqtitle(rs.getString(idx++));
                    vo.setAnqregdate(rs.getDate(idx++));

                    list.add(vo);
                }
            }
        }.executeAll();
        return list;
    }

    public void updateMyinfo(final MemberVO vo){
        new QueryExecutor() {
            public void doJob() throws Exception {
                stmt = con.prepareStatement(UMY);
                stmt.setString(1,vo.getUserpw());
                stmt.setString(2,vo.getUname());
                stmt.setInt(3,vo.getUno());
                stmt.executeUpdate();
            }
        }.executeAll();
    }

    public void deleteMyinfo(final int uno){
        new QueryExecutor() {
            public void doJob() throws Exception {
                stmt = con.prepareStatement(TAL);
                stmt.setInt(1,uno);
                stmt.executeUpdate();
            }
        }.executeAll();

    }

}
