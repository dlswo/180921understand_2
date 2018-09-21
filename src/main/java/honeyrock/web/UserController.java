package honeyrock.web;

import honeyrock.dao.UserDAO;
import honeyrock.domain.*;
import honeyrock.web.util.Converter;
import honeyrock.web.util.QuestionList;
import lombok.extern.log4j.Log4j;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@WebServlet(urlPatterns = "/user/*")
@Log4j
public class UserController extends AbstractController {

    private UserDAO dao = new UserDAO();
    private QuestionList qList = new QuestionList();

    public String questionGET(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        System.out.println("questionGET.......................");
        int uno = Converter.getInt(req.getParameter("uno"),1);
        System.out.println(uno);
        PageDTO dto = PageDTO.of()
                .setPage(Converter.getInt(req.getParameter("page"), 1))
                .setSize(Converter.getInt(req.getParameter("size"), 10));
        PageMaker pageMaker = new PageMaker(dao.getMaxRno(), dto);
        req.setAttribute("pageMaker", pageMaker);
        req.setAttribute("list", dao.getList(dto));
        req.setAttribute("userinfo", dao.quesInfo(uno));


        return "question";
    }

    public String understandGET(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        System.out.println("understandGET.......................");
        MemberVO vo = (MemberVO) session.getAttribute("member");
        int uno = vo.getUno();
        PageDTO dto = PageDTO.of()
                .setPage(Converter.getInt(req.getParameter("page"), 1))
                .setSize(Converter.getInt(req.getParameter("size"), 10));
        List<Integer> list = qList.getResultList(dao.getList(dto), dao.quesInfo(uno),uno);
        System.out.println(list);

        String unqnoStr = req.getParameter("unqno");
        int unqno = Converter.getInt(unqnoStr, -1);

        if (unqno == -1) {
            throw new Exception(("Invalid data"));
        }

        for (int i = 0; i < list.size(); i++){
            if (unqno==list.get(i)){
                return "redirect/question";
            }
        }

       String dq = dao. readUnqtitle(unqno);

        req.setAttribute("qus", dq);
        req.setAttribute("unqno", unqno);


        return "understand";
    }

    public String understandPOST(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.setCharacterEncoding("UTF-8");

        String unoStr = req.getParameter("uno");
        String unqnoStr = req.getParameter("unqno");
        String anqtitle = req.getParameter("anqtitle");
        String anqtyStr = req.getParameter("anqty");

        log.info("=============================");
        log.info(unoStr);
        log.info(unqnoStr);
        log.info(anqtyStr);
        log.info("=============================");

        AnswerVO vo = new AnswerVO();
        vo.setUno(Integer.parseInt(unoStr));
        vo.setUnqno(Integer.parseInt(unqnoStr));
        vo.setAnqtitle(anqtitle);
        vo.setAnqty(Integer.parseInt(anqtyStr));

        dao.addAnswer(vo);

        return "redirect/question";

    }

    public String infoGET(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        System.out.println("infoGET.......................");


        String unoStr = req.getParameter("uno");
        int uno = Converter.getInt(unoStr, -1);

        if (uno == -1) {
            throw new Exception(("Invalid data"));
        }
        MemberVO vo = dao.readMyinfo(uno);

        req.setAttribute("info", vo);
        req.setAttribute("infoList", dao.quesInfo(uno));


        return "info";
    }

    public String infoPOST(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        req.setCharacterEncoding("UTF-8");


        String userpw = req.getParameter("userpw");
        String uname = req.getParameter("uname");
        int uno = Integer.parseInt(req.getParameter("uno"));
//        int page = Integer.parseInt(req.getParameter("page"));
//        int qno = Integer.parseInt(qnoStr);

        MemberVO vo = new MemberVO();
        vo.setUno(uno);
        vo.setUserpw(userpw);
        vo.setUname(uname);

        dao.updateMyinfo(vo);

        return "redirect/question";
    }

    public String removePOST(HttpServletRequest req, HttpServletResponse resp) throws Exception{

        int uno = Integer.parseInt(req.getParameter("uno"));
        dao.deleteMyinfo(uno);

        return "redirect/../login";
    }


//    public String logoutPOST(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//        HttpSession session = req.getSession();
//        session.invalidate();
//        return "redirect/login";
//    }

    @Override
    public String getBasic() {
        return "/user/";
    }
}
