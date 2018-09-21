<%--
  Created by IntelliJ IDEA.
  User: zzz
  Date: 2018-09-12
  Time: 오후 12:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="includes/header.jsp"%>

<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">질문 내역</h1>
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
                    한 페이지당 표시할 질문 수
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
                            <th>질문번호</th>
                            <th>이해했니 질문</th>
                            <th>이름</th>
                            <th>등록일</th>
                            <th>제한시간(분)</th>

                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="understand" items="${question}" >
                            <fmt:parseNumber var="startTime_H" value="${(understand.unqlimitDate.time/(1000*60))/60}" integerOnly="true"/>
                            <fmt:parseNumber var="endTime_H" value="${(understand.unqregdate.time/(1000*60))/60}" integerOnly="true"/>
                            <fmt:parseNumber var="startTime_M" value="${(understand.unqlimitDate.time/(1000*60))%60}" integerOnly="true"/>
                            <fmt:parseNumber var="endTime_M" value="${(understand.unqregdate.time/(1000*60))%60}" integerOnly="true"/>

                            <tr class="odd gradeX">
                                <td>${understand.unqno}</td>
                                <td><a href="result?page=${pageMaker.pageDTO.page}&size=${pageMaker.pageDTO.size}&unqno=${understand.unqno}">${understand.unqtitle}</a></td>
                                <td>${understand.uname}</td>
                                <td>${understand.unqregdate}</td>
                                <td>${(startTime_H-endTime_H)*60+(startTime_M-endTime_M)}min</td>
                            </tr>
                        </c:forEach>


                        </tbody>

                    </table>
                    <ul class="pagination">

                        <c:if test="${pageMaker.prev}">
                            <li><a href="question?page=${pageMaker.start - 1}&size=${pageMaker.pageDTO.size}" >Prev</a></li>
                        </c:if>

                        <c:forEach begin="${pageMaker.start}" end="${pageMaker.end}" var="num">
                            <li ${pageMaker.pageDTO.page == num ?"class='active'":""} ><a href="question?page=${num}&size=${pageMaker.pageDTO.size}" >${num}</a></li>
                        </c:forEach>

                        <c:if test="${pageMaker.next}">
                            <li><a href="question?page=${pageMaker.end + 1}&size=${pageMaker.pageDTO.size}" >Next</a></li>
                        </c:if>

                    </ul>
                    <br>
                    <a href="board"><button class="btn btn-primary">첫페이지</button></a>
                    <%--<form action="/movie/write" method="get">--%>
                        <%--<input type="hidden" name="page" value="${pageMaker.pageDTO.page}">--%>
                        <%--<input type="hidden" name="size" value="${pageMaker.pageDTO.size}">--%>

                        <%--<button class="btn btn-primary btn-lg btn-block">등록</button>--%>
                    <%--</form>--%>
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
