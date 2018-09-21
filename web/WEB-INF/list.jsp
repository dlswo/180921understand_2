<%--
  Created by IntelliJ IDEA.
  User: zzz
  Date: 2018-09-12
  Time: 오후 12:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@include file="includes/header.jsp"%>

<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">질문 게시판</h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <script>
        function change(obj) {
            //alert("change")
            var sizeValue = obj.options[obj.selectedIndex].value;
            console.log(sizeValue);
            self.location = "list?page=1&size="+sizeValue;
        }
    </script>
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    DataTables Advanced Tables
                    <select onchange="change(this)">
                        <option value="10" ${pageMaker.pageDTO.size==10?"selected":""}>10</option>
                        <option value="20" ${pageMaker.pageDTO.size==20?"selected":""}>20</option>
                        <option value="50" ${pageMaker.pageDTO.size==50?"selected":""}>50</option>
                        <option value="100" ${pageMaker.pageDTO.size==100?"selected":""}>100</option>
                    </select>
                </div>
                <!-- /.panel-heading -->
                <div class="panel-body">
                    <table width="100%" class="table table-striped table-bordered table-hover" >
                        <thead>
                        <tr>
                            <th>글번호</th>
                            <th>이름</th>
                            <th>질문제목</th>
                            <th>등록날짜</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="question" items="${list}" >
                            <tr class="odd gradeX">
                                <td>${question.qno}</td>
                                <td>${question.uname}</td>
                                <td><a href="read?page=${pageMaker.pageDTO.page}&size=${pageMaker.pageDTO.size}&qno=${question.qno}">${question.qtitle}</a></td>
                                <td>${question.qregdate}</td>
                            </tr>
                        </c:forEach>


                        </tbody>

                    </table>
                    <ul class="pagination">

                        <c:if test="${pageMaker.prev}">
                            <li><a href="list?page=${pageMaker.start - 1}&size=${pageMaker.pageDTO.size}" >Prev</a></li>
                        </c:if>

                        <c:forEach begin="${pageMaker.start}" end="${pageMaker.end}" var="num">
                            <li ${pageMaker.pageDTO.page == num ?"class='active'":""} ><a href="list?page=${num}&size=${pageMaker.pageDTO.size}" >${num}</a></li>
                        </c:forEach>

                        <c:if test="${pageMaker.next}">
                            <li><a href="list?page=${pageMaker.end + 1}&size=${pageMaker.pageDTO.size}" >Next</a></li>
                        </c:if>

                    </ul>
                    <form action="/qus/write" method="get">
                        <input type="hidden" name="page" value="${pageMaker.pageDTO.page}">
                        <input type="hidden" name="size" value="${pageMaker.pageDTO.size}">

                        <button class="btn btn-primary btn-lg btn-block">등록</button>
                    </form>
                    <!-- /.table-responsive -->
                </div>
                <!-- /.panel-body -->
            </div>
            <!-- /.panel -->
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <!-- /.row -->
</div>
<!-- /#page-wrapper -->

<%@include file="includes/footer.jsp"%>
