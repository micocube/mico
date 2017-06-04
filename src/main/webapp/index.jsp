<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017-03-03
  Time: 15:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>系统界面 OutlookTree</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <script src="/static/scripts/boot.js" type="text/javascript"></script>
    <link charset="utf-8" rel="stylesheet" type="text/css" href="/static/scripts/miniui/themes/pure/skin.css"/>

    <style type="text/css">
        body{
            margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
        }
    </style>
</head>
<body>

<!--Layout-->
<div id="layout1" class="mini-layout" style="width:100%;height:100%;">
    <div class="header" region="north" height="35" showSplit="false" showHeader="false">
        <div id="toolbar" class="mini-toolbar" style="padding:2px;">
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
                        <a class="mini-menubutton" plain="true" menu="#popupMenu">文件</a>
                        <a class="mini-button" iconCls="icon-addfolder" plain="true" enabled="false">增加</a>
                        <a class="mini-button" iconCls="icon-edit" plain="true">修改</a>
                        <a class="mini-button" iconCls="icon-remove" plain="true">删除</a>
                        <span class="separator"></span>
                        <a class="mini-button" iconCls="icon-reload" plain="true">刷新</a>
                        <a class="mini-button" iconCls="icon-download" plain="true">下载</a>
                    </td>
                    <td style="white-space:nowrap;" id="filter"><label style="font-family:Verdana;">Filter by: </label>
                        <input name="term" class="mini-textbox"/>
                    </td>
                </tr>
            </table>
        </div>
        <!--menu-->
        <ul id="popupMenu" class="mini-menu" style="display:none;">
            <li>
                <span >操作</span>
                <ul>
                    <li iconCls="icon-new" >新建</li>
                    <li class="separator"></li>
                    <li iconCls="icon-add" >代码生成</li>
                    <li iconCls="icon-edit" >公用代码搜索</li>
                    <li iconCls="icon-remove" >压力测试</li>
                </ul>
            </li>
            <li class="separator"></li>
            <li iconCls="icon-open" >打开</li>
            <li iconCls="icon-remove" >关闭</li>
        </ul>
    </div>
    <div title="south" region="south" showSplit="false" showHeader="false" height="10" >
        <div style="line-height:10px;text-align:center;cursor:default">Copyright © MicoCube版权所有 </div>
    </div>
    <div title="center" region="center" style="border:0;" bodyStyle="overflow:hidden;">
                <!--Tabs-->
                <div id="mainTabs" class="mini-tabs" activeIndex="2" style="width:100%;height:100%;"
                     plain="false" onactivechanged="onTabsActiveChanged">
                    <div title="Editor" url="/code.jsp" >
                    </div>
                    <div title="Terminal" url="/code.jsp" >
                    </div>
                    <div title="Docker" url="/docer.jsp" >
                    </div>
                    <div title="Database" url="/database.jsp" >
                    </div>
                    <div title="FileManager" url="/file.jsp" >
                    </div>
                    <div title="PressureTest" url="/file.jsp" >
                    </div>
                </div>
            <%--</div>--%>
        </div>
    </div>
</div>



<script type="text/javascript">
    mini.parse();

    var tree = mini.get("leftTree");

    function showTab(node) {
        var tabs = mini.get("mainTabs");

        var id = "tab$" + node.id;
        var tab = tabs.getTab(id);
        if (!tab) {
            tab = {};
            tab._nodeid = node.id;
            tab.name = id;
            tab.title = node.text;
            tab.showCloseButton = true;

            //这里拼接了url，实际项目，应该从后台直接获得完整的url地址
            tab.url = mini_JSPath + "../../docs/api/" + node.id + ".html";

            tabs.addTab(tab);
        }
        tabs.activeTab(tab);
    }

    function onNodeSelect(e) {
        var node = e.node;
        var isLeaf = e.isLeaf;

        if (isLeaf) {
            showTab(node);
        }
    }

    function onClick(e) {
        var text = this.getText();
        alert(text);
    }
    function onQuickClick(e) {
        tree.expandPath("datagrid");
        tree.selectNode("datagrid");
    }

    function onTabsActiveChanged(e) {
        var tabs = e.sender;
        var tab = tabs.getActiveTab();
        if (tab && tab._nodeid) {

            var node = tree.getNode(tab._nodeid);
            if (node && !tree.isSelectedNode(node)) {
                tree.selectNode(node);
            }
        }
    }



    $('#filter').keydown(function(e){
        if(e.keyCode==13){
            var form = new mini.Form("#filter");
            var data = form.getData();
            var json = mini.encode(data);

            $.ajax({
                type: "POST",
                dataType: "json",
                contentType:"application/json",
                url: "/docker/vague",
                data: json,
                success: function (result) {
                    alert(result);
                },
                error: function(data) {
                    alert("error:"+data);
                }
            });
        }
    });



</script>

</body>
</html>