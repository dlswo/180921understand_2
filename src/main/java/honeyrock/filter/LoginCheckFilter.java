package honeyrock.filter;

import honeyrock.domain.MemberVO;
import lombok.extern.log4j.Log4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Log4j
@WebFilter(urlPatterns = {"/user/*","/qus/*"})
public class LoginCheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        log.info("----------------------------------");
        log.info("LOGIN CHECK FILTER");
        log.info("----------------------------------");

        HttpServletRequest req = (HttpServletRequest)servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();
        MemberVO vo = new MemberVO();
        vo = (MemberVO)session.getAttribute("member");
        System.out.println(vo);

//        Cookie[] cks = req.getCookies();
//
        if(vo == null){
            resp.sendRedirect("/login");
        }
//
//        boolean check = false;
//
//        for (Cookie ck:cks) {
//            if(ck.getName().equals("login")){
//                check = true;
//                break;
//            }
//        }//end for
//
//        if(!check){
//            resp.sendRedirect("/main");
//        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
