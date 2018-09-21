package honeyrock.web;

import honeyrock.dao.MemberDAO;
import honeyrock.dao.QuestionDAO;
import honeyrock.domain.MemberVO;
import honeyrock.domain.PageDTO;
import honeyrock.domain.PageMaker;
import honeyrock.domain.QuestionVO;
import honeyrock.web.util.Converter;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@WebServlet(urlPatterns = "/qus/*")
@MultipartConfig(maxFileSize = 1024 * 1024 * 50, // 50 MB
        maxRequestSize = 1024 * 1024 * 100) // 100 MB
public class QuestionController extends AbstractController {

    private QuestionDAO dao = new QuestionDAO();
    private MemberDAO mdao = new MemberDAO();
    boolean updateable = false;


    public String listGET(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        System.out.println("listGET.......................");

        PageDTO dto = PageDTO.of()
                .setPage(Converter.getInt(req.getParameter("page"), 1))
                .setSize(Converter.getInt(req.getParameter("size"), 10));

        PageMaker pageMaker = new PageMaker(dao.getMaxRno(), dto);
        req.setAttribute("pageMaker", pageMaker);
        req.setAttribute("list", dao.getList(dto));

        return "list";
    }

    public String readGET(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        System.out.println("readGET.......................");

        String qnoStr = req.getParameter("qno");
        int qno = Converter.getInt(qnoStr, -1);

        if (qno == -1) {
            throw new Exception(("Invalid data"));
        }


        QuestionVO vo = dao.getQuestion(qno);

        req.setAttribute("qus", vo);


        return "read";
    }

    public String writeGET(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        return "write";
    }

    public String writePOST(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.setCharacterEncoding("UTF-8");

        String qtitle = req.getParameter("qtitle");
        String unoStr = req.getParameter("uno");
        String qcmt = req.getParameter("qcmt");
        String qfile = req.getParameter("qfile");
        QuestionVO vo = new QuestionVO();
        vo.setQtitle(qtitle);
        vo.setUno(Integer.parseInt(unoStr));
        vo.setQcmt(qcmt);
        vo.setQfile(qfile);

        dao.addQuestion(vo);

        return "redirect/list";

    }

    public String modifyGET(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        int qno = Integer.parseInt(req.getParameter("qno"));
        QuestionVO vo = dao.getQuestion(qno);
        req.setAttribute("qus", vo);

        return "modify";
    }

    public String modifyPOST(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        req.setCharacterEncoding("UTF-8");

        String qnoStr = req.getParameter("qno");
        String unoStr = req.getParameter("uno");
        String qtitle = req.getParameter("qtitle");
        String qcmt = req.getParameter("qcmt");
        String qfile = req.getParameter("qfile");
        int page = Integer.parseInt(req.getParameter("page"));
        int qno = Integer.parseInt(qnoStr);

//        MovieVO vo = new MovieVO();
        QuestionVO vo = dao.getQuestion(qno);
        vo.setQno(qno);
        vo.setUno(Integer.parseInt(unoStr));
        vo.setQtitle(qtitle);
        vo.setQcmt(qcmt);
        vo.setQfile(qfile);

        dao.updateQuestion(qno, vo);
        return "redirect/list?page=" + page;
    }

    public String removePOST(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int qno = Integer.parseInt(req.getParameter("qno"));
        dao.deleteQuestion(qno);           //수정해야함
        return "redirect/list";
    }




    @Override
    public String getBasic() {
        return "/qus/";
    }
}
