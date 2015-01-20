<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <title>Заказы</title>
  <link type="text/css" rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.0.0/css/bootstrap.min.css">
  <link type="text/css" rel="stylesheet" href="<c:url value='/resources/css/index.css'/>">
  <style>
  </style>
</head>
<body>
<div id="loading" style="display: none;">
  <img src="<c:url value='/resources/css/img/ajax-loader.gif'/>" alt="загружается..."/>
</div>
<div id="content">
  <a href="<c:url value='/logout'/>" style="float: right;">Выйти</a>
  <div class="title">Заказы</div>
  <a href="<c:url value='/admin/items'/>">перейти к списку товаров</a>
  <div id='ordersCntr'>
  <c:if test="${not empty orders}">
    <div class="titleRow">
      <div class="cell4">Номер заказа</div>
      <div class="cell4">Заказчик</div>
      <div class="cell4">Стоимость заказа (руб.)</div>
      <div class="cell4">Последнее изменение</div>
      <div style="clear: both"></div>
    </div>
    <c:forEach items="${orders}" var="order">
    <div class="orderRow">
      <div class="orderRowCtl" data-orderid="${order.id}">
        <div class="cell4">№${order.id}</div>
        <div class="cell4">${order.userLastName} ${order.userFistName} / ${order.userEmail}</div>
        <div class="cell4">${order.totalPrice}</div>
        <div class="cell4">${order.updated}</div>
        <div style="clear: both"></div>
      </div>
    </div>
    </c:forEach>
  </c:if>
  </div>
  <br/><br/>
</div>
<div id="templates" style="display: none;">
  <div class="itemOrderRow">
    <div class="cell3 name"></div>
    <div class="cell3">
      <span class="num"></span>
      <span class="measure"></span>
    </div>
    <div class="cell3 price"></div>
    <div style="clear: both"></div>
  </div>
</div>
<script src="//cdn.jsdelivr.net/jquery/1.11.0/jquery.min.js"></script>
<script src="//cdn.jsdelivr.net/jquery.ui/1.10.4/jquery-ui.min.js"></script>
<script src="<c:url value='/resources/js/shop.js'/>"></script>
<script>
  $(function(){
    $('.orderRowCtl').click(function(event){
      var target = $(event.currentTarget);
      if (!target.hasClass('active')) {
        var orderId = target.data('orderid');
        $.post('itemOrderInfos', {orderId: orderId}, function (items) {
          var elem = $('<div class="itemsCntr">');
          $.each(items, function (id, item) {
            elem.append(itemOrderRowTemplate(item, false));
          });
          elem.insertAfter(target);
          target.addClass('active');
          doColorRows();
        });
      } else {
        target.removeClass('active');
        target.parent('.orderRow').find('.itemsCntr').remove();
      }
    });
  });
</script>

</body>
</html>