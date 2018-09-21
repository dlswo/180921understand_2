package honeyrock.dao;

import honeyrock.domain.AnswerVO;
import honeyrock.domain.MemberVO;

import java.util.ArrayList;
import java.util.List;

public class MemberDAO {

    private static final String sql = "select * from tbl_unmember";
    private static final String SIGNUP = "insert into tbl_unmember (uno, uname, userid, userpw)\n" +
            "values(seq_unmember.nextval, ?, ?, ?)";

    public List<MemberVO> checkLogin(){

        final List<MemberVO> list = new ArrayList<MemberVO>();


        new QueryExecutor() {
            @Override
            public void doJob() throws Exception {

                stmt=con.prepareStatement(sql);
                rs = stmt.executeQuery();

                while (rs.next()){
                    MemberVO vo = new MemberVO();
                    vo.setUno(rs.getInt("uno"));
                    vo.setUserid(rs.getString("userid"));
                    vo.setUrole(rs.getString("urole"));
                    vo.setRegdate(rs.getDate("regdate"));
                    vo.setUname(rs.getString("uname"));
                    vo.setUserpw(rs.getString("userpw"));
                    vo.setUserusing(rs.getString("userusing"));
                    list.add(vo);
                }
            }
        }.executeAll();


        return list;
    }

    public MemberVO getInfo(final int uno){
        final List<MemberVO> list = new ArrayList<>();
        new QueryExecutor() {
            @Override
            public void doJob() throws Exception {
                stmt = con.prepareStatement(sql);
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

    public void createUser(final MemberVO vo) {

        new QueryExecutor() {
            public void doJob() throws Exception {
                stmt = con.prepareStatement(SIGNUP);
                stmt.setString(1,vo.getUname());
                stmt.setString(2,vo.getUserid());
                stmt.setString(3,vo.getUserpw());
                stmt.executeUpdate();
            }
        }.executeAll();
    }

}
