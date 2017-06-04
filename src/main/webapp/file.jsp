<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>FileManager 文档管理器</title>


    <script src="/static/scripts/boot.js" type="text/javascript"></script>


    <link href="filemanager.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div style="width:700px;border:solid 1px #9da0aa;font-size:12px;font-family:Verdana;color:#201F35;overflow:hidden;">
    <div class="mini-toolbar" style="border:0;border-bottom:solid 1px #9da0aa;padding:5px;">
        <table style="width:100%;">
            <tr>
                <td style="width:auto;">
                    <a class="mini-button" iconCls="icon-addfolder" plain="true">增加</a>
                    <a class="mini-button" iconCls="icon-edit" plain="true">修改</a>
                    <a class="mini-button" iconCls="icon-remove" plain="true">删除</a>
                    <span class="separator"></span>
                    <a class="mini-button" iconCls="icon-reload" plain="true">刷新</a>
                    <a class="mini-button" iconCls="icon-download" plain="true">下载</a>
                </td>
                <td style="white-space:nowrap;width:220px;"><label>Filter by: </label><input class="mini-textbox" /></td>
            </tr>
        </table>
    </div>
    <div class="mini-splitter" handlerSize="2" showHandleButton="false" style="width:100%;height:300px;" borderStyle="border:0">
        <div size="205" maxSize="350" minSize="150" style="border:0;">
            <ul id="folderTree" class="mini-tree" style="width:100%;height:100%;" showTreeIcon="true" leafIcon="mini-tree-folder"
                textField="name" idField="id" parentField="pid" resultAsTree="false"
                ondataload="onTreeDataLoad" onnodeselect="onSelectNode"
            >
            </ul>
        </div>
        <div >
            <div id="filesDataView" style="width:100%;height:100%;overflow:auto;display:none;">
                <div id="filesView" class="filesView">
                </div>
            </div>
            <div id="filesListBox" textField="name" class="mini-listbox"
                 style="width:100%;height:100%;overflow:auto;display:none;" borderStyle="border:0;">
                <div property="columns">
                    <div field="name" width="auto">名称</div>
                    <div field="updatedate" width="100">修改日期</div>
                    <div field="type" width="100">类型</div>
                    <div field="size" width="100">大小</div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
    mini.parse();

    var ViewType = "image"; //list, image

    ////////////////////////////////////////
    function refreshFiles(data) {
        if (!data) data = [];

        var filesListBox = mini.get("filesListBox");
        var filesDataView = document.getElementById("filesDataView");
        filesListBox.setVisible(ViewType != "image");
        filesDataView.style.display = ViewType == "image" ? "block" : "none";

        if (ViewType == "image") {

            var sb = [];
            for (var i = 0, l = data.length; i < l; i++) {
                var file = data[i];
                sb[sb.length] = '<a class="file" href="javascript:void(0)" onclick="return false;" hideFocus>';
                sb[sb.length] = '<image class="file-image" ';
                var src = "filetype/file.png";
                sb[sb.length] = ' src="' + src + '"';
                sb[sb.length] = ' />';
                sb[sb.length] = '<span class="file-text" >';
                sb[sb.length] = file.name;
                sb[sb.length] = '</span>';
                sb[sb.length] = '</a>';
            }
            sb.push('<div style="clear:both;"></div>');

            document.getElementById("filesView").innerHTML = sb.join('');
        } else {

            filesListBox.load(data);
        }
    }

    var folderTree = mini.get("folderTree");

    function showFiles(folderId) {
        //var messageid = mini.loading("Loading, Please wait ...", "Loading");
        $.ajax({
            url: "../data/FileService.aspx?method=LoadFiles&folderId=" + folderId,
            success: function (text) {
                var files = mini.decode(text);
                refreshFiles(files);
            }
        });
    }

    function onSelectNode(e) {
        if (e.node) showFiles(e.node.id);
    }

    function onTreeDataLoad(e) {
        var node = folderTree.getNode(1);
        folderTree.selectNode(node);
        showFiles(node.id);
    }


    folderTree.load("../data/FileService.aspx?method=LoadFolders");
</script>