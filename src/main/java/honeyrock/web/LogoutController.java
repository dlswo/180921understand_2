package honeyrock.web;

import honeyrock.dao.MemberDAO;
import honeyrock.domain.MemberVO;
import honeyrock.domain.PageDTO;
import honeyrock.domain.PageMaker;
import honeyrock.web.util.Converter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/logout")
public class LogoutController extends  AbstractController {

    @Override
    public String getBasic() {
        return "/";
    }



    public String logoutGET(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        session.invalidate();
        return "redirect/login";
    }




}
