<%--
  Created by IntelliJ IDEA.
  User: mico
  Date: 17-4-27
  Time: 下午9:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>数据验证</title>
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
<h1>数据验证</h1>


<div id="form1">
    <table>
        <tr>
            <td><label for="username$text">帐号：</label></td>
            <td>
                <input name="username" vtype="email" class="mini-textbox" required="true" requiredErrorText="帐号不能为空"/>

            </td>
        </tr>
        <tr>
            <td><label for="pwd$text">密码：</label></td>
            <td>
                <input name="pwd" class="mini-password" vtype="minLength:5" required="true" minLengthErrorText="密码不能少于5个字符" />

            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input value="Login" type="button" onclick="submitForm()" />
            </td>
        </tr>
    </table>
</div>


<script type="text/javascript">
    mini.parse();

    function submitForm() {

        var form = new mini.Form("#form1");

        form.validate();
        if (form.isValid() == false) return;

        //提交数据
        var data = form.getData();
        var json = mini.encode(data);
        alert("提交成功");
    }

</script>

<div class="description">
    <h3>Description</h3>
    <p>
        监听处理表单控件的"validation"事件。在此验证事件函数中，可以对控件值做任意逻辑规则的验证。
    </p>
    <p>使用Form控件的validate方法，可以对表单元素之内的所有控件进行批量验证。</p>
</div>
</body>
</html>
