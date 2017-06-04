<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8"/>
    <title>Coder</title>
    <link rel="stylesheet" href="/static/editor/css/style.css"/>
    <link rel="stylesheet" href="/static/editor/css/editormd.css"/>
    <link rel="shortcut icon" href="https://pandao.github.io/editor.md/favicon.ico" type="image/x-icon"/>
    <style>
        #codes textarea {
            display: none;
        }
    </style>
</head>
<body>
<div id="layout">
    <div id="editormd">
        <textarea style="display:none;">
            import com.jfinal.aop.Before;
            import com.jfinal.core.Controller;

            @Before(BlogInterceptor.class)
            public class BlogController extends Controller {
                public void index() {
                    setAttr("blogPage", Blog.me.paginate(getParaToInt(0, 1), 10));
                    render("blog.html");
                }

                public void add() {
                }

                @Before(BlogValidator.class)
                public void save() {
                    getModel(Blog.class).save();
                    redirect("/blog");
                }

                public void edit() {
                    setAttr("blog", Blog.me.findById(getParaToInt()));
                }

                @Before(BlogValidator.class)
                public void update() {
                    getModel(Blog.class).update();
                    redirect("/blog");
                }

                public void delete() {
                    Blog.me.deleteById(getParaToInt());
                    redirect("/blog");
                }
            }
        </textarea>
    </div>
</div>
<script src="/static/editor/js/jquery.min.js"></script>
<script src="/static/editor/editormd.js"></script>
<script type="text/javascript">
    var testEditor;

    $(function () {
        testEditor = editormd("editormd", {
            width: "100%",
            height: "20570px",
            watch: false,
            toolbar: false,
            codeFold: true,
            searchReplace: true,
            placeholder: "Enjoy coding!",
            theme: (localStorage.theme) ? localStorage.theme : "mdn-like",
            mode: (localStorage.mode) ? localStorage.mode : "text/x-java",
            path: '/static/editor/lib/'
        });
    });
</script>
</body>
</html>