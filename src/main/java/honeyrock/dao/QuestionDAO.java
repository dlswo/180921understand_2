package honeyrock.dao;

import honeyrock.domain.PageDTO;
import honeyrock.domain.QuestionVO;

import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
    String LIST =  "select * from (select/*+INDEX_DESC(tbl_question_board pk_question_board) */\n" +
            "ROWNUM rn, qno, uname, qtitle, qcmt, qfile, QREGDATE\n" +
            "from TBL_question_board,(select uno, uname from tbl_unmember) mem\n" +
            "where QNO > 0\n" +
            "AND ROWNUM <= (? * ?)\n" +
            "and mem.uno=tbl_question_board.uno)\n" +
            "where rn > ((? -1) * ?)";
    private static final String MAXRNO = "select count(*) from tbl_question_board";
    private static final String READ = "select qno, mem.uno, uname, qtitle, qregdate, qcmt, qfile \n" +
            "from (select uno, uname from tbl_unmember) mem, tbl_question_board \n" +
            "where qno = ? \n" +
            "and mem.uno=tbl_question_board.uno";
    private final String WRITE = "insert into tbl_question_board (qno, uno, qtitle, qcmt, qfile)\n" +
            "values (seq_question_board.nextval, ?, ?, ?, ?)\n";

    private final String DELETE = "delete from tbl_question_board where qno = ?";

    private final String UPDATE = "update tbl_question_board set qtitle = ?,uno = ?, qcmt = ?, qfile = ? where qno= ?";


    public List<QuestionVO> getList(final PageDTO pageDTO) throws Exception{

        final List<QuestionVO> list = new ArrayList<>();
        new QueryExecutor() {
            public void doJob() throws Exception {
                stmt = con.prepareStatement(LIST);
                int i = 1;
                stmt.setInt(i++, pageDTO.getPage());
                stmt.setInt(i++, pageDTO.getSize());
                stmt.setInt(i++, pageDTO.getPage());
                stmt.setInt(i++, pageDTO.getSize());
                rs = stmt.executeQuery();

                while (rs.next()) {
                    QuestionVO vo = new QuestionVO();
                    int idx = 2;
                    vo.setQno(rs.getInt(idx++));
                    vo.setUname(rs.getString(idx++));
                    vo.setQtitle(rs.getString(idx++));
                    vo.setQcmt(rs.getString(idx++));
                    vo.setQfile(rs.getString(idx++));
                    vo.setQregdate(rs.getDate(idx++));

                    list.add(vo);
                }
            }
        }.executeAll();
        return list;
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

    public QuestionVO getQuestion(final int qno) {
        final QuestionVO vo = new QuestionVO();

        new QueryExecutor() {
            public void doJob() throws Exception {
                stmt = con.prepareStatement(READ);
                stmt.setInt(1,qno);
                rs = stmt.executeQuery();
                while(rs.next()) {
                    int idx = 1;
                    vo.setQno(rs.getInt(idx++));
                    vo.setUno(rs.getInt(idx++));
                    vo.setUname(rs.getString(idx++));
                    vo.setQtitle(rs.getString(idx++));
                    vo.setQregdate(rs.getDate(idx++));
                    vo.setQcmt(rs.getString(idx++));
                    vo.setQfile(rs.getString(idx++));
                }
            }
        }.executeAll();

        //code
        return vo;
    }

    public void addQuestion(final QuestionVO vo) {

        //uno, qtitle, qcmt, qfile
        new QueryExecutor() {
            public void doJob() throws Exception {
                stmt = con.prepareStatement(WRITE);
                stmt.setInt(1, vo.getUno());
                stmt.setString(2,vo.getQtitle());
                stmt.setString(3,vo.getQcmt());
                stmt.setString(4,vo.getQfile());
                stmt.executeUpdate();
            }
        }.executeAll();
    }

    public void deleteQuestion(final int qno) {


        new QueryExecutor() {
            public void doJob() throws Exception {
                stmt = con.prepareStatement(DELETE);
                stmt.setInt(1,qno);
                stmt.executeUpdate();
            }
        }.executeAll();
    }

    public void updateQuestion(final int qno,final QuestionVO vo) {


        new QueryExecutor() {
            public void doJob() throws Exception {
                stmt = con.prepareStatement(UPDATE);
                //qtitle = ?,uno = ?, qcmt = ?, qfile = ? where qno= ?
                stmt.setString(1,vo.getQtitle());
                stmt.setInt(2,vo.getUno());
                stmt.setString(3,vo.getQcmt());
                stmt.setString(4,vo.getQfile());
                stmt.setInt(5,vo.getQno());

                stmt.executeUpdate();
            }
        }.executeAll();
    }
}
