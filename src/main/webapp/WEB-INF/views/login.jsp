<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <title>Вход</title>
  <%--<link type="text/css" rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.0.0/css/bootstrap.min.css">--%>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
  <link type="text/css" rel="stylesheet" href="<c:url value='/resources/css/index.css'/>">
  <style>
    #content{font-size: 14px;}
    .loginForm {
      width: 288px;
      display: table;
      margin: 0 auto;
    }
    .loginForm div.label{width: 60px;}

    .label{float:left; text-align: right; line-height: 26px; margin-right: 4px;}
    .note{color: gray; font-size: 12px; text-align: right}
    .buttons{text-align: right}
    input[type='text'], input[type='password']{width: 220px; vertical-align: top;}
    input[type='button']{margin-right: 0px;}

    .registerForm {
      padding: 5px 20px;
    }
    .registerTitle {
      text-align: center;
      background-color: #E2EEE3;
      color: gray;
      line-height: 40px;
      font-size: 20px;
    }
    .registerForm div.row{line-height: 45px;}
    .registerForm div.label{width: 126px; font-size: 12px}

    .ui-widget-overlay{
      background: #aaaaaa;
      opacity: 0.3;
    }
    .ui-widget-header{
      background: #ee4845;
      color: #333232;
      border: 1px solid #ee4845;
    }
    .ui-widget-content .ui-state-default, .ui-widget-content .ui-state-hover {
      border: 1px solid #d3d3d3;
      background: #dadada;
      color: #333232;
    }
    button.ui-dialog-titlebar-close{
      background: #ee4845 !important;
      border: none !important;
    }
    button.ui-dialog-titlebar-close:focus {outline: none}
    .ui-dialog {padding: 0; border: 2px solid #ee4845}
    .ui-dialog .ui-dialog-content {padding: 0;}
    .ui-dialog .ui-dialog-buttonpane{margin-top: 0; border: none; padding-top: 0}
    div.ui-corner-all {
      border-bottom-right-radius: 0;
      border-bottom-left-radius: 0;
      border-top-right-radius: 0;
      border-top-left-radius: 0;
    }
    .ui-widget input, .ui-widget select{font-size: 12px; height: 20px; vertical-align: top}
    .ui-widget select{height: 26px;}
    .ui-dialog .ui-dialog-buttonpane{padding: 0 0 15px 0; text-align: center}
    .ui-widget button span{font-size: 12px; padding: 6px 12px;}
    .ui-dialog .ui-dialog-buttonpane .ui-dialog-buttonset{float: none}
    .ui-widget-header .ui-icon{background: url("<c:url value='/resources/css/img/close.png'/>") no-repeat;}

    div.warning{font-size: 10px; color: red; width: 90px;}
    label.error{
      display: block;
      font-size: 10px;
      color: red;
      line-height: 25px;
      margin-left: 130px;
      margin-top: -25px;
    }
  </style>
</head>
<body>
<div id="content">
<div class="loginForm">
<c:if test="${fail}"><div class="warning"><nobr>Неправильно введены логин или пароль, попробуйте снова.</nobr></div></c:if>
<form action="<c:url value='/j_spring_security_check'/>" method="post">
  <div class="label">Email:</div><input type="text" name="j_username" value="${email}" id="j_username"><br/>
  <div class="label">Пароль:</div><input type="password" name="j_password"><br/>
  <input type="hidden" name="_spring_security_remember_me" value="0">
  <div class="buttons">
  <input type="submit" value="Войти"/>
  <input id="showRegisterDlg" type="button" value="Зарегистрироваться"/>
  </div>
</form>
</div>

<div id="registerWin" style="display: none;">
<div class="registerForm">
<form:form method="POST" modelAttribute="account" id="registerFormFrm">
  <form:hidden path="id"/>
  <div class="row"><div class="label">Email:</div><form:input path="email"/></div>
  <div class="row"><div class="label">Имя:</div><form:input path="fistName"/></div>
  <div class="row"><div class="label">Фамилия:</div><form:input path="lastName"/></div>
  <div class="row"><div class="label">Пароль:</div><input type="password" id="f_password"/></div>
  <div class="row"><div class="label">Повторите пароль:</div><form:password path="password"/></div>
</form:form>
</div>
</div>
</div>

<script src="//cdn.jsdelivr.net/jquery/1.11.0/jquery.min.js"></script>
<script src="//cdn.jsdelivr.net/jquery.ui/1.10.4/jquery-ui.min.js"></script>
<script src="//cdn.jsdelivr.net/jquery.validation/1.12.0/jquery.validate.min.js"></script>

<script>
  $(function(){
    var registerFormFrm = $("#registerFormFrm").validate({
      rules: {
        email: {
          required: true,
          email: true,
          remote: {url: '<c:url value="/auth/checkEmail"/>', type: 'post'}
        },
        fistName: {
          required: true
        },
        lastName: {
          required: true
        },
        password: {
          required: true,
          equalTo: '#f_password'
        }
      },
      messages: {
        email: {
          required: "Введите email",
          email: "Введите корректный email",
          remote: "Этот email уже зарегистрирован"
        },
        fistName: {
          required: "Введите имя"
        },
        lastName: {
          required: "Введите фамилию"
        },
        password: {
          required: "Повторите пароль",
          equalTo: "Пароли не совпадают"
        }
      }
    });
    $("#f_password").rules("add", {required: true,
      minlength: 6, messages: { required: "Введите пароль",
        minlength: "Используйте не менее 6 символов"}});
    $("#registerWin").dialog({
      autoOpen: false,
      height: 384,
      width: 402,
      modal: true,
      resizable: false,
      draggable: false,
      title: "Регистрация",
      buttons: {
        "Зарегистрироваться": function () {
          if (registerFormFrm.form()) {
//            $('#registerFormFrm').submit();
            $.post('<c:url value="/auth/register"/>', $('#registerFormFrm').serialize(), function (registered) {
              if (registered) {
                var email = $('#email').val();
                alert('Регистрация прошла успешно!');
                $('#registerWin').dialog("close");
                $('#j_username').val(email);
              }
            });
          }
        },
        "Отмена": function () {
          $('#registerWin').dialog("close");
        }
      },
      open: function () {
        $('.ui-widget-overlay').bind('click', function() {
          $('#registerWin').dialog('close');
        })
      },
      close: function() {
        registerFormFrm.resetForm();
        $('#registerFormFrm')[0].reset();
      }
    });
    $('#showRegisterDlg').click(function () {
      $("#registerWin").dialog("open");
    });
  });
</script>
</body>
</html>