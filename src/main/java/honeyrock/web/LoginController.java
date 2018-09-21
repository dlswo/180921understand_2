package honeyrock.web;

import honeyrock.dao.MemberDAO;
import honeyrock.domain.MemberVO;
import honeyrock.domain.QuestionVO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@WebServlet(urlPatterns = {"/login","/signup"})
public class LoginController extends  AbstractController {

    MemberDAO dao = new MemberDAO();
    @Override
    public String getBasic() {
        return "/";
    }

    public String loginGET(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        return "login";
    }

    public String loginPOST(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        List<MemberVO> memberVOList = dao.checkLogin();
        String id = req.getParameter("id");
        String pw = req.getParameter("pw");
//        System.out.println(id + ":" + pw);

        MemberVO vo = new MemberVO();
        vo.setUserid(id);
        vo.setUserpw(pw);
        for (MemberVO vol : memberVOList) {
            if (id.equals(vol.getUserid()) && pw.equals(vol.getUserpw())) {
                vo.setUno(vol.getUno());
                vo.setUname(vol.getUname());
                String userusing = vol.getUserusing();

                HttpSession session = req.getSession();
                if (vo != null) {
                    session.setAttribute("member", vo);
                }
                if (userusing.equals("Y")){
                    return "redirect/user/question?uno=" + vol.getUno();
                }else {
                    return "redirect/login";
                }


            }
        }
        return "redirect/login";
    }

    public String signupGET(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        return "signup";
    }

    public String signupPOST(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.setCharacterEncoding("UTF-8");

        String uname = req.getParameter("uname");
        String userid = req.getParameter("userid");
        String userpw = req.getParameter("userpw");

        MemberVO vo = new MemberVO();
        vo.setUname(uname);
        vo.setUserid(userid);
        vo.setUserpw(userpw);

        System.out.println(vo);
        dao.createUser(vo);

        return "redirect/login";

    }




}
