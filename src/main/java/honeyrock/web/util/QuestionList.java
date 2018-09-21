package honeyrock.web.util;

import honeyrock.dao.UserDAO;
import honeyrock.domain.AnswerVO;
import honeyrock.domain.PageDTO;
import honeyrock.domain.PageMaker;
import honeyrock.domain.UnderstandVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class QuestionList {


    public List<Integer> getResultList(List<UnderstandVO> list,List<AnswerVO> anslist, int uno) throws Exception {

        UserDAO dao = new UserDAO();
        PageDTO dto = new PageDTO();
//        System.out.println(dao.getList(dto));
//        System.out.println(dao.quesInfo(41, dto));
        list = dao.getList(dto);
        anslist = dao.quesInfo(uno);
        List<Integer> resultList = new ArrayList<>();

        for (int i = 0; i < anslist.size(); i++) {
            System.out.println(anslist.get(i));
            for (int j = 0; j < list.size(); j++) {
                System.out.println(list.get(j));
                if (list.get(j).getUnqno() == anslist.get(i).getUnqno()) {
                    resultList.add(list.get(j).getUnqno());
                }

            }

        }

        return resultList;
    }

    public static void main(String[] args) throws Exception {
        UserDAO dao = new UserDAO();
        PageDTO dto = new PageDTO();
//        System.out.println(dao.getList(dto));
//        System.out.println(dao.quesInfo(41, dto));
        List<UnderstandVO> list = dao.getList(dto);
        List<AnswerVO> anslist = dao.quesInfo(41);
        List<Integer> resultList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            System.out.println(" i 루프 시작");
            System.out.println(list.get(i));
            System.out.println("===================");
            for (int j = 0; j < anslist.size(); j++) {
                System.out.println(" j 루프 시작");
                System.out.println(anslist.get(j));
                if (list.get(i).getUnqno() == anslist.get(j).getUnqno()) {
                    resultList.add(list.get(i).getUnqno());
                }

            }

        }
        System.out.println("======================================================");
        System.out.println(resultList);
    }


}
