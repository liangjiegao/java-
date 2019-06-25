<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@include file="common/tag.jsp"%>
<%@page import="com.superl.skyDriver.pojo.User" %>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <%@include file="common/head.jsp"%>
      <meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8" />

      <title>首页</title>
</head>
<body>
<div>
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <a class="navbar-brand" href="#">Brand</a>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <c:choose>
                        <c:when test="${user eq null}">
                        </c:when>
                        <c:otherwise>
                            <li><a href="#">${user.username},您好</a></li>
                            <li><a href="${pageContext.request.contextPath}/logout.do">退出登录</a></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>
</div>
	
	<div style="margin-top: 5rem">

		<div class="col-md-3" >
			<div class="list-group">
                <!--记录当前选择的分类，默认为all 首页-->
                <input type="text" id="type" value="all" style="display: none">
			  <a href="#" class="list-group-item active">
			    分类
			  </a>
              <a onclick="loadFile('${user.id}', 'user', '-1', 'all')" class="list-group-item">首页</a>
			  <a onclick="loadFile('${user.id}', 'user', '${user.id}', 'image')" class="list-group-item">照片</a>
			  <a onclick="loadFile('${user.id}', 'user', '${user.id}', 'video')" class="list-group-item">视频</a>
			  <a onclick="loadFile('${user.id}', 'user', '${user.id}', 'file')" class="list-group-item">文件</a>
			</div>
            <div class="list-group">
                <!--压缩-->
                <div class="list-group-item">
                    <h4>压缩</h4>
                    <label>生成压缩文件名：</label>
                    <input type="text" id="zipFileName" value="新建压缩文件" maxlength="20">
                    <input type="button" class="btn btn-primary col-md-12" id="zipBt" value="压缩选择的文件" onclick="zipFile()"></br>
                    <p></p>
                    <label>压缩情况：</label>
                    <label id="zipState">未提交压缩</label>
                    <!--生成的压缩文件-->
                    <div>
                        <ol class="list-group-item" id="content_newZip" style="list-style: none">
                        </ol>
                    </div>
                </div>
                <!--解压-->
                <div class="list-group-item">
                    <h4>解压</h4>
                    <input type="button" class="btn btn-primary col-md-12" id="unzipBt" value="解压选择的文件" onclick="unzipFile()"></br>
                    <p></p>
                    <label>压缩情况：</label>
                    <label id="unzipState">未提交解压</label>
                    <!--生成的压缩文件-->
                    <div class="list-group-item" >
                        <ol class="list-group" id="content_newUnZip" style="list-style: none">
                        </ol>
                    </div>
                </div>
                </div>
            </div>
		</div>
		<div class="col-md-9">
            <div>
                <div class="col-md-3">
                    <input type="button" class="btn btn-info" id="build_newdir" value="新建文件夹" onclick="buildNewDir()">
                </div>
                <div class="col-md-9">
                    <form id="fileForm" >
                        <input type="text" name="userId" id="userId" value="${user.id}" style="display: none">
                        <input type="text" name="linkId" id="linkId" value="${user.id}" style="display: none">
                        <input type="text" name="parent" id="parent" value="user" style="display: none">
                        <input type="file" name="file" id="uploadFile" value="上传文件">
                    </form>
                    <div>
                        <input type="button" class="btn btn-info" value="上传" onclick="uploadFile()"/>
                    </div>

                    <!--进度条-->
                    <div class="progress">
                        <div class="progress-bar" id="upload_percent" role="progressbar" aria-valuenow="0"
                        aria-valuemin="0" aria-valuemax="100" style="">
                            0%
                        </div>
                    </div>
                </div>
                <div>
                    <%-- 记录导航长度 不显示 --%>
                    <input name="index_len" id="index_len" value="1" style="display: none">
                    <ol class="breadcrumb" id="index_list">
                        <span>当前位置&#160;&#160;</span>
                        <li><a onclick="jumpIndex('${user.id}', 'user', '-1','首页', 'all', 1)" index="1">首页</a></li>
                    </ol>
                </div>
            </div>
<%--            文件列表--%>
            <div>
                <ul class="list-group" id="file_list" style="list-style: none">

                </ul>
                <%--            分页--%>
                <div class="col-md-5 col-md-offset-2 pull-left">
                    <nav aria-label="Page navigation">

                        <ul class="pagination" id="paging">
                            <li>
                                <a href="#" aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                            <li>
                                <a href="#" aria-label="Next">
                                    <span aria-label="true">&raquo;</span>
                                </a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>
		</div>
        <!--分享资源弹窗-->
        <div class="modal fade" id="shareSource" tabindex="-1" role="dialog" aria-labelledby="shareSourceLable" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">分享链接</h4>
                    </div>
                    <div class="modal-body">
                        <div>
                            <label>资源名：</label>
                            <span>java基础.zip</span>
                            <input id="sourceId" value="-1" style="display: none;"/>
                        </div>
                        <div id="duration_div">
                            <label>有效时间:</label><br/>
                            <div ></div>
                            <input type="radio" name="duration" value="1"><span>&nbsp;&nbsp;1天</span>&nbsp;&nbsp;&nbsp;
                            <input type="radio" name="duration" value="3"><span>&nbsp;&nbsp;3天</span>&nbsp;&nbsp;&nbsp;
                            <input type="radio" name="duration" value="7" checked><span>&nbsp;&nbsp;7天</span>&nbsp;&nbsp;&nbsp;
                            <input type="radio" name="duration" value="100000"><span>&nbsp;&nbsp;永久有效</span>&nbsp;&nbsp;&nbsp;
                        </div>
                        <div id="private_state">
                            <br/>
                            <label>私密性：</label><br/>
                            <input type="radio" name="private" value="1" checked><span>&nbsp;&nbsp;加密</span>&nbsp;&nbsp;&nbsp;
                            <input type="radio" name="private" value="0"><span>&nbsp;&nbsp;不加密</span>&nbsp;&nbsp;&nbsp;
                        </div>
                        <div id="link_div" style="display: none;">
                            <br/>
                            <label>链接：</label>
                            <p id="link"></p>
                            <label>提取码：</label><label id="exPasswd"></label><br/>
                            <button class="btn btn-success" type="button">复制链接</button>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" class="btn btn-primary" onclick="buildLink(1)">生成链接</button>
                    </div>
                </div>
            </div>
        </div>
	</div>
</body>
</html>

<script>
    $(document).ready(function () {
        // 异步加载用户的根文件
        var linkId = $("#linkId").val();
        var parent = $("#parent").val();
        loadFile(linkId, parent, -1, "all");

        // 模态框状态变换时执行
        $("#shareSource").on("hide.bs.modal", function () {
            // 隐藏链接div
            $("#link_div").css("display", "none")
            $("#link").text("");
            $("#exPasswd").text("");
        })

    });
    // 异步加载文件
    function loadFile(linkId, parent, userId, type, indexName, start, end) {
        console.log(start);
        console.log(end);
        // 重置当前位置
        $("#linkId").val(linkId);
        $("#parent").val(parent);
        // 记录当前选择的分类
        $("#type").val(type);
        console.log(start);
        if (start == undefined || end == undefined){
            start = 0;
            end = 20;
        }
        // 改变导航条
        if (indexName != undefined) {
            appendIndex(linkId, indexName);
        }
        $.ajax({
            type:"GET",
            url:"${pageContext.request.contextPath}/loadFile.do?linkId="+linkId+"&parent="+parent+"&userId="+userId+"&type="+type+"&start="+start+"&end="+end,
            dataType:"json",
            success:function (data) {
                console.log(data);
                $("#file_list").html("");
                var jsondata = eval(data["obj"]);
                $.each(jsondata, function (idx, obj) {
                    appendList(obj)
                });
                var total = data["total"]
                var nowPage = data["nowPage"];
                paging(total, nowPage);

            }
        });
    }
    // 拼接导航栏
    function appendIndex(linkId, indexName) {
        // 增加程度
        $("#index_len").val(parseInt($("#index_len").val()) + 1);
        $("#index_list").append("<li><a onclick='jumpIndex("+linkId+",\"dir\",-1, -1,\""+indexName+"\","+$("#index_len").val()+")' index=\""+$("#index_len").val()+"\">"+indexName+"</a></li>")
    }
    // 导航栏跳转
    function jumpIndex(linkId, parent, userId, type, indexName, index) {
        loadFile(linkId, parent, userId, type, indexName);
        // 删除导航条点击位置及后面部分
        var len = $("#index_len").val();
        var sub = len - index;
        console.log(len);
        console.log(sub);
        for (var i = 0; i < sub; i++) {
            $("#index_list li:last").remove();
            $("#index_len").val(parseInt($("#index_len").val()) - 1);
        }
    }

    // 异步上传文件
    function uploadFile() {
        if ($('#uploadFile').val() == ""){
            alert("请选择上传的文件！");
            return;
        }
        var formdata = new FormData($("#fileForm")[0], $("#fileForm")[1], $("#fileForm")[2], $("#fileForm")[3]);
        // 将文件选择按钮设置为不可选
        $("#uploadFile").attr("disabled", "disabled");

        // 设置进度条从1开始，防止上传大文件没有响应
        $("#upload_percent").text("1%");
        $("#upload_percent").attr("style", "width:1%");
        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/uploadFile.do",
            enctype:"multipart/form-data",
            contentType:false,
            processData:false,
            data:formdata,
            xhr:xhrOnProgress(function (e) {
                var percent = e.loaded/e.total; // 文件上传百分比
                $("#upload_percent").attr("style", "width:"+parseInt(percent*100)+"%");
                $("#upload_percent").text(parseInt(percent*100) + "%");
            }),
            success:function (data) {
                var jsondata = eval(data);
                if (jsondata["code"] == "200"){
                    // 拼接li
                    var obj = jsondata["object"];
                    // 如果当前页面的linkId与上传文件的linkId相等时
                    // 即用户在上传结束时没有切换到别的文件夹，才进行html拼接，否则不拼接
                    if (obj["linkId"] == $("#linkId").val()) {
                        appendList(obj);
                    }
                }
                alert(jsondata["message"]);
            },
            complete:function () {
                // 上传结束，清理文件和进度
                $("#upload_percent").attr("style", "width:0%");
                $("#upload_percent").text("");
                $("#uploadFile").val("");
                // 将文件选择按钮恢复为可选择
                $("#uploadFile").removeAttr("disabled");
            },
            error:function () {
                // 提示错误
                alert("文件上传失败！");
            }
        })
    }
    // 用于文件监听上传进度
    var xhrOnProgress=function (fun) {
        xhrOnProgress.onprogress = fun; // 绑定监听
        // 使用闭包实现监听绑定
        return function () {
            // 通过 $.ajaxSetting.xhr(); 获取XMLHttpRequest对象
            var xhr = $.ajaxSettings.xhr();
            // 绑定监听函数是否为函数
            if (typeof xhrOnProgress.onprogress !== 'function')
                return xhr;
            // 如果有监听函数并且xhr对象支持绑定时把监听函数绑定上去
            if (xhrOnProgress.onprogress && xhr.upload){
                xhr.upload.onprogress = xhrOnProgress.onprogress;
            }
            return xhr;
        }
    };
    // 新建文件夹
    function buildNewDir() {
        // 如果有还没保存的新建文件夹，将它保存再新建
        if ($("#new_dir")[0] != undefined){
            alert("请先保存您新建的文件!");
        }else {
            $("#file_list").append("<li><a class=\"list-group-item col-md-9\" ><input type='text' class=\"new_dir\" id='new_dir' value='新建文件夹'><input type='button' onclick='doBuildNewDir()' class=\"btn btn-info\" value='保存'/></a></li>")
        }


    }
    // 发送请求创建文件夹
    function doBuildNewDir() {
        var linkId = $("#linkId").val();
        var parent = $("#parent").val();
        var userId = $("#userId").val();
        var filename = $("#new_dir").val();
        $.ajax({
            type:"GET",
            url:"${pageContext.request.contextPath}/buildNewDir.do?linkId="+linkId+"&parent="+parent+"&userId="+userId+"&filename="+filename,
            dataType:"json",
            success:function (data) {
                var jsondata = eval(data);
                if (jsondata["code"] == "200"){
                    // 创建成功
                    var obj = jsondata["object"];
                    // 改变样式
                    $("#file_list li:last").html("" +
                        "<li fileId='"+obj["id"]+"'>" +
                        "<a onclick='loadFile("+obj["id"]+",\"dir\",-1, -1,\""+obj["filename"]+"\")' class=\"list-group-item col-md-8\" >" +
                        "<input type='checkbox' disabled/>&nbsp;&nbsp;"+obj["filename"]+"</a>" +
                        "<input type='button' class='btn btn-default col-md-1' disabled value='压缩分享'/>"+
                        "<input type='button' class='btn btn-default col-md-1' value='下载' disabled/>" +
                        "<input type='button' class='btn btn-info col-md-1' value='重命名' onclick='rename(\""+obj["filename"]+"\","+obj["id"]+")'/>" +
                        "<input type='button' class='btn btn-info col-md-1' onclick='deleteFile("+obj["id"]+")' value='删除' /></li>");
                }
                alert(jsondata["message"]);
            }
        })
    }
    function deleteFile(fileId) {
        ajaxGet("${pageContext.request.contextPath}/deleteFile.do?fileId="+fileId, fileId, "delete");
    }
    function rename(oldName, fileId) {
        if ($("#new_dir")[0] != undefined){
            alert("请先保存您新建的文件！")
        }else {
            $("li[fileid='"+fileId+"']").html("<li class=\"list-group-item col-md-9\">" +
                "<input type='text' class=\"new_dir\" id='new_dir' value='"+oldName+"'>" +
                "<input type='button' onclick='doRename("+fileId+")' class=\"btn btn-info\" value='保存'/>" +
                "</li>")
        }
    }
    function doRename(fileId) {
        var filename = $("#new_dir").val();
        ajaxGet("${pageContext.request.contextPath}/rename.do?fileId="+fileId+"&newFilename="+filename, fileId, "rename");
    }
    function downloadFile(fileId) {
        var url = "${pageContext.request.contextPath}/downloadFile.do?fileId="+fileId;
        $.ajax({
            url:url,
            type:'GET',
            success:function (data) {
                console.log("data:"+typeof data);
                alert("success");
            },
            error:function (XMLHttpRequest, textStatus, errorThrown) {
                alert("失败"+errorThrown)
            },
            complete:function () {
                alert("结束")
            }
        });
    }
    function ajaxGet(url,fileId, dowhat) {
        $.ajax({
            type:"GET",
            url:url,
            dataType:"json",
            success:function (data) {
                var jsondata = eval(data);
                if (jsondata["code"] == "200"){
                    if (dowhat == "delete"){
                        $("li[fileid='"+fileId+"']").remove()
                    } else if (dowhat == "rename"){
                        // $("li[fileid='"+fileId+"'] a").text();
                        console.log(jsondata["object"])
                        var obj = jsondata["object"];
                        changeList(obj);
                    }else if (dowhat == "download") {
                    }
                }
                alert(jsondata["message"])
                // alert("成功")
            },
            error:function (XMLHttpRequest, textStatus, errorThrown) {
                alert("失败"+errorThrown)
            },
            complete:function () {
                // alert("结束")
            }
        })
    }
    function appendList(obj) {
        if (obj["file"]["type"] == "dir"){
            $("#file_list").append("" +
                "<li fileId='"+obj["id"]+"'>" +
                "<a class=\"list-group-item col-md-8\" onclick='loadFile("+obj["id"]+",\"dir\",-1, -1,\""+obj["filename"]+"\")'>" +
                "<input type='checkbox' name='selectFile' value='"+obj["id"]+"'/>&nbsp;&nbsp;"+obj["filename"]+"</a>" +
                "<input type='button' class='btn btn-default col-md-1' value='压缩分享' disabled/>"+
                "<input type='button' class='btn btn-default col-md-1' value='下载' disabled/>" +
                "<input type='button' class='btn btn-info col-md-1' value='重命名' onclick='rename(\""+obj["filename"]+"\","+obj["id"]+")'/>" +
                "<input type='button' class='btn btn-info col-md-1' onclick='deleteFile("+obj["id"]+")' value='删除' /></li>");
        } else{
            $("#file_list").append("<li fileId='"+obj["id"]+"'>" +
                "<a class=\"list-group-item col-md-8\" ><input type='checkbox' name='selectFile' value='"+obj["id"]+"'/>&nbsp;&nbsp;"+obj["filename"]+"</a>" +
                "<input type='button' class='btn btn-success col-md-1' value='分享' onclick='share("+obj["id"]+")'/>"+
                "<a class='btn btn-info col-md-1' href='${pageContext.request.contextPath}/downloadFile.do?fileId="+obj["id"]+"'>下载</a>"+
                "<input type='button' class='btn btn-info col-md-1' value='重命名' onclick='rename(\""+obj["filename"]+"\","+obj["id"]+")'/>" +
                "<input type='button' class='btn btn-info col-md-1' onclick='deleteFile("+obj["id"]+")' value='删除' /></li>");
        }
    }
    function changeList(obj) {
        if (obj["file"]["type"] == "dir"){
            $("li[fileid='"+obj["id"]+"']").html(
                "<a onclick='loadFile("+obj["id"]+",\"dir\",-1, -1,\""+obj["filename"]+"\")' class=\"list-group-item col-md-8\" >" +
                "<input type='checkbox'/>&nbsp;&nbsp;"+obj["filename"]+"</a>" +
                "<input type='button' class='btn btn-default col-md-1' value='压缩分享' disabled/>"+
                "<input type='button' class='btn btn-default col-md-1' value='下载' disabled/>" +
                "<input type='button' class='btn btn-info col-md-1' value='重命名' onclick='rename(\""+obj["filename"]+"\","+obj["id"]+")'/>" +
                "<input type='button' class='btn btn-info col-md-1' onclick='deleteFile("+obj["id"]+")' value='删除' />");
        } else {
            $("li[fileid='"+obj["id"]+"']").html(
                "<a class=\"list-group-item col-md-8\" ><input type='checkbox' name='selectFile' value='"+obj["id"]+"'/>&nbsp;&nbsp;"+obj["filename"]+"</a>" +
                "<input type='button' class='btn btn-success col-md-1' value='分享' onclick='share("+obj["id"]+")'/>"+
                "<a class='btn btn-info col-md-1' href='${pageContext.request.contextPath}/downloadFile.do?fileId="+obj["id"]+"'>下载</a>"+
                "<input type='button' class='btn btn-info col-md-1' value='重命名' onclick='rename(\""+obj["filename"]+"\","+obj["id"]+")'/>" +
                "<input type='button' class='btn btn-info col-md-1' onclick='deleteFile("+obj["id"]+")' value='删除' />");
        }
    }
    function paging(total, nowPage) {
        // 获取分页信息
        var linkId = $("#linkId").val();
        var parent = $("#parent").val();
        // 当前所在的分类
        var type = $("#type").val();

        var userId = -1;
        if (type != "all")
            userId = $("#userId").val();
        // 计算分页数据
        var pageCount = parseInt(total / 20);
        var mod = total % 20;
        if (mod > 0) pageCount += 1;
        console.log("type="+type)
        // 清除元素从新装配
        $("#paging").html("");
        if (nowPage <= 1){  // 如果是第一页 上一页按钮不可用
            $("#paging").append("<li class='disabled'><a aria-label=\"Previous\">\n" +
                "<span aria-hidden=\"true\">&laquo;</span></a></li>")
        } else {
            $("#paging").append("<li><a onclick='loadFile("+linkId+",\""+ parent+"\","+userId+",\""+type+"\", undefined,"+((nowPage-2)*20)+",20)' aria-label=\"Previous\">\n" +
                "<span aria-hidden=\"true\">&laquo;</span></a></li>")
        }
        var start = 0;
        var left = nowPage - 3;
        var right = nowPage + 3;
        var end = pageCount;

        // 遍历显示分页按钮
        for (var i = 0; i < pageCount; i++) {
            // 限制显示条数
            if ((i == start | i == end - 1) | (i >= left && i < right - 1)){
                if (i+1 == nowPage){  // 将当前页置为深色
                    $("#paging").append("<li class='active'><a onclick='loadFile("+linkId+",\""+ parent+"\","+userId+",\""+type+"\", undefined,"+(i*20)+",20)'>"+(i+1)+"</a></li>")
                } else {
                    $("#paging").append("<li><a onclick='loadFile("+linkId+",\""+ parent+"\","+userId+",\""+type+"\", undefined,"+(i*20)+",20)'>"+(i+1)+"</a></li>")
                }
            }
        }
        // 从新拼接下一页按钮
        // 如果是最后一页，下一页不可用
        if (nowPage >= pageCount){
            $("#paging").append("<li class='disabled'><a aria-label=\"Next\">" +
                "<span aria-label=\"true\">&raquo;</span></a></li>")
        } else {
            $("#paging").append("<li><a onclick='loadFile("+linkId+",\""+ parent+"\","+userId+",\""+type+"\", undefined,"+(nowPage*20)+",20)' aria-label=\"Next\">" +
                "<span aria-label=\"true\">&raquo;</span></a></li>")
        }
    }
    function zipFile() {
        var array = new Array();
        var arrChk = $("input[name='selectFile']:checked");

        if (arrChk.length <= 0){
            alert("请选择要压缩的文件");
        }else {
            // 有选择文件才压缩
            $(arrChk).each(function () {
                array.push(this.value)
            });
            console.log(typeof array)
            var linkId = $("#linkId").val();
            var userId = $("#userId").val();
            var zipName = $("#zipFileName").val();

            // 清除原来显示的压缩文件
            $("#content_newZip").html("");
            // 显示正在压缩
            $("#zipState").text("正在压缩...");
            // 警用压缩按钮
            $("#zipBt").attr("disabled", "disabled")
            // 异步发送要压缩的fileId到服务器上
            var url = "${pageContext.request.contextPath}/zipFile.do";
            $.ajax({
                url:url,
                type:'GET',
                data:{
                    "fileIds":array,
                    "linkId":linkId,
                    "zipName":zipName,
                    "userId":userId
                },
                traditional:true,
                success:function (data) {
                    var jsondata = eval(data);
                    if (jsondata["code"] == "200"){
                        var obj = jsondata["object"];
                        $("#content_newZip").html("<li fileId='"+obj["id"]+"'><label>"+obj["filename"].substring(0, 10)+"...</label>\n" +
                            "<input type=\"button\" class=\"btn btn-info pull-right\" value=\"删除\" onclick='deleteFile("+obj["file"]["id"]+")'>\n" +
                            "<a href='${pageContext.request.contextPath}/downloadFile.do?fileId="+obj["id"]+"' class=\"btn btn-info pull-right\"'>下载</a>"+
                            "<input type='button' class='btn btn-success pull-right' value='分享' onclick='share("+obj["id"]+")'/>");
                        $("#zipState").text("压缩成功！");
                        // 清除复选框
                        $("input[name='selectFile']").prop("checked", false);
                    }else{
                        $("#zipState").text("压缩异常！");
                    }
                },
                error:function (data) {
                    $("#zipState").text("压缩失败！");
                },
                complete:function () {
                    // 恢复压缩按钮
                    $("#zipBt").removeAttr("disabled");
                }
            })
        }
    }
    function unzipFile() {
        var arrChk = $("input[name='selectFile']:checked");
        if (arrChk.length <= 0){
            alert("请选择一个要解压的文件！");
        }else if (arrChk.length > 1) {
            alert("每一只能解压一个压缩文件！");
        }else {
            var url = "${pageContext.request.contextPath}/unzipFile.do";
            var linkId = $("#linkId").val();
            var userId = $("#userId").val();
            var parent = $("#parent").val();
            var fileId = arrChk[0].value;
            $("#unzipState").text("正在解压！");
            $.ajax({
                url:url,
                type:'GET',
                data:{
                    fileId: fileId,
                    linkId: linkId,
                    parent: parent,
                    userId: userId
                },
                success:function (data) {
                    var jsondata = eval(data);
                    if (jsondata["code"] == "200"){
                        var objs = jsondata["object"];
                        // 如果返回的文件列表没有文件，表示解压的不是一个压缩文件，提示错误
                        if (objs.length <= 0){
                            alert("请选择一个压缩文件解压！");
                            $("#unzipState").text("");
                            return;
                        }
                        // 清空旧列表显示
                        $("#content_newUnZip").html("");
                        $.each(objs, function (idx, obj) {
                            $("#content_newUnZip").append("<li class='list-group-item' fileId='"+obj["id"]+"'><label>"+obj["filename"].substring(0, 10)+"...</label>\n" +
                                "<input type=\"button\" class=\"btn btn-info pull-right\" value=\"删除\" onclick='deleteFile("+obj["id"]+")'>\n" +
                                "<a href='${pageContext.request.contextPath}/downloadFile.do?fileId="+obj["id"]+"' class=\"btn btn-info pull-right\"'>下载</a>");
                        });
                        $("#unzipState").text("解压成功！");
                        // 清除复选框
                        $("input[name='selectFile']").prop("checked", false);
                    }else{
                        $("#unzipState").text("解压异常！");
                    }
                },
                error:function () {
                    $("#unzipState").text("解压失败！");
                },
                complete:function () {

                }
            })
        }
    }
    function share(sourceId) {
        // 打开弹窗
        $("#shareSource").modal("toggle");
        $("#sourceId").val(sourceId);
    }
    function buildLink() {
        var arr = [0,1,2,3,4,5,6,7,8,9,
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
        var exPasswd = "";
        var isPrivate = $("#private_state input[name='private']:checked").val();
        if (isPrivate == 1) {
            var index = -1;
            for (var i = 0; i < 4; i++) {
                index = parseInt(Math.random() * 62);
                exPasswd += arr[index];
            }
        }
        var userId = $("#userId").val();
        var duration = $("#duration_div input[name='duration']:checked").val();
        var sourceId = $("#sourceId").val();
        var url = "${pageContext.request.contextPath}/buildShareLink.do";
        $.ajax({
            url:url,
            type:"GET",
            data:{
                userId: userId,
                sourceId: sourceId,
                exPasswd: exPasswd,
                duration: duration
            },
            success:function (data) {
                var jsondata = eval(data);
                if (jsondata["code"] == "200"){
                    var link = jsondata["object"];
                    // 显示链接div
                    $("#link_div").css("display", "block")
                    // 主机信息
                    var hostname = location.hostname;
                    var port = location.port;
                    $("#link").text((hostname)+":"+(port)+"${pageContext.request.contextPath}/getShareSource.do?link="+link);
                    if (isPrivate == 1)
                        $("#exPasswd").text(exPasswd);
                }
            }
        });

    }

</script>
