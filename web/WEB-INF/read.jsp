<%--
  Created by IntelliJ IDEA.
  User: 5CLASS-184
  Date: 2018-09-12
  Time: 오후 5:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="includes/header.jsp"%>

<style>
    .poster {
        width:300px;
    }
</style>
<style>
    .panel-title{
        font-size: smaller;
    }
</style>


<div id="wrapper">



    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">게시글 읽기</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->

        <div class="row">
            <div class="col-lg-4">
                <div class="panel panel-success">
                    <div class="panel-heading">
                        <h1>${qus.qtitle}</h1>
                    </div>
                    <div class="panel-body">
                        <div class="panel-group" id="accordion">
                            <div class="panel panel-info">
                                <div class="panel-heading">
                                    <h5 class="panel-title">
                                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">게시글 정보</a>
                                    </h5>
                                </div>
                                <div id="collapseOne" class="panel-collapse collapse in">
                                    <div style="font-size: smaller" class="panel-body">
                                        글쓴이 : ${qus.uname} / 작성시간 : ${qus.qregdate} / 게시물번호 : ${qus.qno}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="panel-body">


                        <p>${qus.qcmt}</p>
                    </div>
                    <div class="panel-footer">
                        <img src="${qus.qfile}" class="poster">
                    </div>
                </div>
            </div>

        </div>
        <!-- /.row -->
        <button type="button" onclick="location.href='modify?page=${param.page}&qno=${qus.qno}'" class="btn btn-primary">수정/삭제</button>
        <a href="list?page=${param.page}"><button  class="btn btn-warning">목록으로</button></a>
    </div>

</div>




<%@include file="includes/footer.jsp"%>