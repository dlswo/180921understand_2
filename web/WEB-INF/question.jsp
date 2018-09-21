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
            <h1 class="page-header">이해했니 게시판</h1>
        </div>
        <!-- /.col-lg-12 -->
    </div>
    <style>
        .form {
            display: inline;
        }
    </style>
    <script>
        function change(obj) {
            //alert("change")
            var sizeValue = obj.options[obj.selectedIndex].value;
            console.log(sizeValue);
            self.location = "question?page=1&size="+sizeValue;
        }
    </script>
    <!-- /.row -->
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    페이지 단위
                    <select onchange="change(this)" style="color: #0c0c0c">
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
                            <th>질문</th>
                            <th>등록일</th>
                            <th>제한시간</th>
                        </tr>
                        </thead>
                        <tbody>


                        <c:forEach var="question" items="${list}" >

                            <fmt:parseNumber var="startTime_H" value="${(question.unqregdate.time/(1000*60))}" integerOnly="true"/>
                            <fmt:parseNumber var="endTime_H" value="${(question.unqlimit.time/(1000*60))}" integerOnly="true"/>
                            <fmt:parseNumber var="startTime_M" value="${(question.unqregdate.time/(1000*60))%60}" integerOnly="true"/>
                            <fmt:parseNumber var="endTime_M" value="${(question.unqlimit.time/(1000*60))%60}" integerOnly="true"/>
                            <tr class="odd gradeX">
                                <td>${question.unqno}</td>
                                <td><a href="understand?page=${pageMaker.pageDTO.page}&size=${pageMaker.pageDTO.size}&unqno=${question.unqno}" onclick=${infoList.unqno==question.unqno ? "return false":""}>${question.uncmt}</a></td>
                                <td>${question.unqregdate}</td>
                                <td>${(endTime_H-startTime_H)*60+(endTime_M-startTime_M)}분</td>
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
                    <br>
                    <form action="info" method="get" class="form">
                        <input type="hidden" name="page" value="${pageMaker.pageDTO.page}">
                        <input type="hidden" name="size" value="${pageMaker.pageDTO.size}">
                        <input type="hidden" name="uno" value="${member.uno}">

                        <button class="btn btn-primary">내 정보</button>
                    </form>
                    <form action="/qus/list" method="get" class="form">
                        <input type="hidden" name="page" value="${pageMaker.pageDTO.page}">
                        <input type="hidden" name="size" value="${pageMaker.pageDTO.size}">

                        <button class="btn btn-primary">질문게시판</button>
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
