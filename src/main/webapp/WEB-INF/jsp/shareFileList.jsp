<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/6/23 0023
  Time: 下午 4:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@include file="common/tag.jsp"%>
<%@page import="com.superl.skyDriver.pojo.User" %>
<html>
<head>
    <%@include file="common/head.jsp"%>
    <title>分享资源</title>
</head>
<body>
<div class="container">
    <div class="col-md-8 col-md-offset-2">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <c:choose>
                    <c:when test="${pageContext.request.getSession().getAttribute('account') eq null}">
                        <a href="#" id="userAccount"></a>未登录
                    </c:when>
                    <c:otherwise>
                        用户：<a href="#" id="userAccount" style="color: white">${pageContext.request.getSession().getAttribute('account')}</a>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="panel-body">
                <ul class="list-group" id="file_list" style="list-style: none">
                    <li>
                        <div id="hide_div" style="display:none;">
                            <input type="text" id="user_id" value="${shareFile.user.id}">
                            <input type="text" id="file_id" value="${shareFile.file.id}">
                            <input type="text" id="parent" value="${shareFile.parent}">
                            <input type="text" id="linkId" value="${shareFile.linkId}">
                        </div>
                        <a class="list-group-item col-md-8">
                            <input type='checkbox' name='selectFile'/>&nbsp;&nbsp;
                            <span id="filename">${shareFile.filename}</span>
                        </a>
                        <button class='btn btn-info col-md-2' data-toggle="modal" data-target="#pathSelect">保存</button>
                        <a class='btn btn-info col-md-2' href='${pageContext.request.contextPath}/downloadFile.do?fileId=${shareFile.id}'>下载</a>
                    </li>
                </ul>
            </div>
            <div class="panel-footer">
            </div>
        </div>
    </div>
    <!--选择保存路径弹窗口-->
    <div class="modal fade" id="pathSelect" tabindex="-1" role="dialog" aria-labelledby="pathSelectLable" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">选择保存路径</h4>
                </div>
                <div class="modal-body">
                    <ul style="list-style: none" id="dirListUl">
                        <li id="dirList"></li>

                    </ul>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" onclick="saveSource()">确定</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>

    $(document).ready(function () {

        // 当模态框显示时加载文件夹
        $("#pathSelect").on('shown.bs.modal', function () {
            loadDir("dirList");
        })
        // 当模态框关闭时清除文件夹显示
        $("#pathSelect").on('hide.bs.modal', function () {
            $("#dirListUl>li").not(":eq(0)").remove()
        })
    });

    function loadDir(root) {
        var linkId;
        if (root == "dirList")
            linkId = -1;
        else
            linkId = root;
        console.log(root)
        // 如果当前文件夹已经展开，再次点击则收起来
        var count = $("[linkid="+root+"]").length;
        console.log(count)
        if (count > 0) {
            removeEm(root);
            return;
        }
        var userAccount = $("#userAccount").text();
        // 如果当前Session有用户登录，
        if(userAccount != ""){
            var url = "${pageContext.request.contextPath}/loadUserDir.do";
            $.ajax({
                url: url,
                type: 'GET',
                data:{
                    account: userAccount,
                    linkId: linkId
                },
                success:function (data) {
                    var jsonobj = eval(data);
                    console.log(data)
                    if (jsonobj["code"] == "200"){
                        // 加载成功
                        var userFileList = jsonobj["object"]
                        $.each(userFileList, function (idx, obj) {
                            $("#"+root).after("<li id='"+obj["id"]+"' linkid='"+root+"'><input type='radio' name='selectDir' value='"+obj["id"]+"'>&nbsp;&nbsp;<a onclick='loadDir("+obj["id"]+")'><strong>+</strong>&nbsp;"+obj["filename"]+"</a></li>")
                        });
                    }
                },
                complete:function () {

                }
            })
        }
    }
    // 递归收起展开的子文件夹
    function removeEm(linkId) {
        if($("li[linkid="+$("li[linkid="+linkId+"]").attr("id")+"]").length > 0) {
            removeEm($("li[linkid="+linkId+"]").attr("id"));
        }
        $("li[linkid="+linkId+"]").remove();
    }

    // 保存文件到指定目录
    function saveSource() {
        var linkId = $("input[name='selectDir']:checked").val();
        if (linkId == undefined){
            alert("请选择保存的路劲！")
            return;
        }
        var userAccount = $("#userAccount").text();
        var fileId = $("#file_id").val();
        var filename = $("#filename").text();

        var p = $("#"+linkId).attr("linkid");
        console.log(p);
        var parent;
        if (p == "dirList"){ // 根目录
            parent = 'user';
        }else{
            parent = 'dir';
        }
        var url = "${pageContext.request.contextPath}/saveShare.do";
        $.ajax({
            url:url,
            type:'GET',
            data:{
                account:userAccount,
                fileId:fileId,
                fileName:filename,
                parent:parent,
                linkId:linkId
            },
            success:function (data) {
                var jsonobj = eval(data);
                if(jsonobj["code"] == "200"){
                    // 保存成功，关闭模态框，提示成功
                    $("#pathSelect").modal("hide");
                }
                alert(jsonobj["message"])
            }
        })
    }

</script>
</html>
