<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>Docer</title>

    <!-- Bootstrap -->
    <link href="/static/bootstrap/css/bootstrap.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" href="/static/editor/css/style.css" />
    <link rel="stylesheet" href="/static/editor/css/editormd.css" />
    <link rel="shortcut icon" href="https://pandao.github.io/editor.md/favicon.ico" type="image/x-icon" />
</head>
<body>
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Cube</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">代码 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">代码生成</a></li>
                        <li><a href="#">代码库搜索</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#">压力测试</a></li>
                    </ul>
                </li>
                <li class="active"><a href="#">文档</a></li>
                <li><a href="#">数据库 </a></li>
                <li><a href="#">文件</a></li>
            </ul>
            <form class="navbar-form navbar-right">
                <div class="input-group">
                    <input id="search" class="form-control" type="text" data-provide="typeahead" autocomplete="off" style="margin: 0 auto;" data-items="4">
                    <%--<input type="text" class="form-control" placeholder="keyword" style="margin: 0 auto;" data-provide="typeahead" data-items="4" data-source="[&quot;Ahmedabad&quot;,&quot;Akola&quot;,&quot;Asansol&quot;,&quot;Aurangabad&quot;,&quot;Bangaluru&quot;,&quot;Baroda&quot;,&quot;Belgaon&quot;,&quot;Berhumpur&quot;,&quot;Calicut&quot;,&quot;Chennai&quot;,&quot;Chapra&quot;,&quot;Cherapunji&quot;]">--%>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></span>
                </div>
            </form>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#"><span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span></a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">用户 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">个人中心</a></li>
                        <li><a href="#">设置</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#">登出</a></li>
                    </ul>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>


<div id="layout" style="margin-top: 50px">
    <div id="editormd"></div>
    <input type="hidden" id="dockerid" />
</div>
<script src="/static/layer/jquery-3.2.1.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/static/bootstrap/js/bootstrap.min.js"></script>
<script src="/static/bootstrap/js/bootstrap.typeahead.js"></script>
<script src="/static/layer/layer.min.js"></script>
<script src="/static/editor/editormd.js"></script>
<script type="text/javascript">
    var testEditor;
    $(function() {
            testEditor = editormd("editormd", {
                width: "100%",
                height: "3000px",
                path : './static/editor/lib/',
                theme : "default",
                previewTheme : "default",
                editorTheme : "mdn-like",
                codeFold : true,
//                syncScrolling : false,
                saveHTMLToTextarea : true,    // 保存 HTML 到 Textarea
                searchReplace : true,
                watch : true,                // 关闭实时预览
                htmlDecode : "style,script,iframe|on*",            // 开启 HTML 标签解析，为了安全性，默认不开启
                toolbar  : true,             //关闭工具栏
                //previewCodeHighlight : false, // 关闭预览 HTML 的代码块高亮，默认开启
                emoji : true,
                taskList : true,
                tocm            : true,         // Using [TOCM]
                tex : true,                   // 开启科学公式TeX语言支持，默认关闭
                flowChart : true,             // 开启流程图支持，默认关闭
                sequenceDiagram : true,       // 开启时序/序列图支持，默认关闭,
                //dialogLockScreen : false,   // 设置弹出层对话框不锁屏，全局通用，默认为true
                //dialogShowMask : false,     // 设置弹出层对话框显示透明遮罩层，全局通用，默认为true
                //dialogDraggable : false,    // 设置弹出层对话框不可拖动，全局通用，默认为true
                //dialogMaskOpacity : 0.4,    // 设置透明遮罩层的透明度，全局通用，默认值为0.1
                //dialogMaskBgColor : "#000", // 设置透明遮罩层的背景颜色，全局通用，默认为#fff
                imageUpload : false,
//                imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
//                imageUploadURL : "./php/upload.php",
                onload : function() {
                    //this.fullscreen();



                    var keyMap = {
                        "Ctrl-S": function(cm) {
                            var html = testEditor.getMarkdown();
                            var dockerid = $("#dockerid").val();
                            var docker = dockerid || '-1';
                            console.log("dockerid:"+(docker == '-1'));
                            if(docker == '-1'){
                                console.log("新文件保存！");
                                $.ajax({
                                    type: "post",
                                    dataType: "text",
                                    url: '/docker/add',
                                    data: {content:html},
                                    success: function (data) {
                                        console.log("save file success");
                                    }
                                });
                            }else{
                                var doc = $("#dockerid").val();
                                console.log("更新文件，id："+doc);
                                $.ajax({
                                    type: "post",
                                    dataType: "text",
                                    url: '/docker/update',
                                    data: {id:doc,content:html},
                                    success: function (data) {
                                        console.log("update file success");
                                    }
                                });
                            }


                        },
                        "Ctrl-A": function(cm) { // default Ctrl-A selectAll
                            // custom
                            alert("Ctrl+A");
                            cm.execCommand("selectAll");
                        },
                        "Ctrl-W": function(cm){
                            testEditor.setValue("");
                            $("#dockerid").val('-1');
                        },
                        "Ctrl-E": function(cm){
                            testEditor.previewing();
                        },
                        "Ctrl-D": function (cm) {
                            console.log(testEditor.getHTML());
                        }
                    };


                    this.addKeyMap(keyMap);


                    this.setValue("##流程图画法\n\n+ 操作块\n\t+ start: st\u003d\u003estart: 开始\n\t+ end: ed\u003d\u003eend:结束\n\t+ inputoutput: io\u003d\u003einputoutput: io操作\n\t+ condition: cd\u003d\u003econdition: 条件判断\n\t+ operation: op\u003d\u003eoperation: 操作\n\t+ 记得每个操作符后带空格\n+ 流程符\n\t+ -\u003e\n+ 注意事项\n\t+ 把理想条件放在condition判断的yes流程里图会更漂亮,流畅\n\n##压力测试流程图\n\n```flow\n\nst\u003d\u003estart: 压测流程\ned\u003d\u003eend: 结束\nipt\u003d\u003einputoutput: 用户上传客户证件号excel，url，token，key\nexistcond\u003d\u003econdition: 用户未申请\ninputcond\u003d\u003econdition: 输入参数不为空\nreadexcel\u003d\u003eoperation: 从excel读取数据到username.data\nerrorop\u003d\u003eoperation: 返回错误信息\ndataexists\u003d\u003econdition: username.data不存在\ndeletedata\u003d\u003eoperation: 删除username.data\ncreatedata\u003d\u003eoperation: 创建username.data\nsavedata\u003d\u003eoperation: 压测参数入库,入队列\n\nst-\u003eipt-\u003eexistcond\nexistcond(no)-\u003eerrorop-\u003eed\nexistcond(yes)-\u003einputcond\ninputcond(no)-\u003eerrorop-\u003eed\ninputcond(yes)-\u003edataexists\ndataexists(no)-\u003edeletedata-\u003ecreatedata(right)-\u003ereadexcel-\u003esavedata-\u003eed\ndataexists(yes)-\u003ecreatedata-\u003ereadexcel-\u003esavedata-\u003eed\n\n\n```\n\n\n\n\n")

                    //this.unwatch();
                    //this.watch().fullscreen();

                    //this.setMarkdown("#PHP");
                    //this.width("100%");
                    //this.height(480);
                    //this.resize("100%", 640);
                }
            });

    });
    var qresult;
    $("#search").typeahead({
        source: function (query, process) {
            return $.ajax({
                url: '/docker/vague',
                type: 'post',
                data: {term: query},
                success: function (result) {
                    console.log(typeof result);
                    var re = JSON.parse(result);
                    qresult = re;
                    var arr = [];
                    for (var key in re)
                    {
                        arr.push(re[key]);
                    }
                    var are = process(arr)
                    return are;
                },
            });
        },
        updater: function (item) {
            for (var key in qresult)
            {
                if(qresult[key] == item ){
                    console.log(key);
                    $.ajax({
                        url: '/docker/fetch',
                        type: 'post',
                        data: {id: key},
                        success: function (result) {
                            var obj = JSON.parse(result);
                            console.log(obj.content);
                            testEditor.setValue(obj.content);
                            $("#dockerid").val(obj.id);
                            console.log("当前文档ID："+$("#dockerid").val())
                        },
                    });
                }
            }

            return item;
        }
    });
</script>
</body>
</html>
