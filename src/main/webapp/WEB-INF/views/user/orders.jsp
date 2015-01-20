<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <title>Мои заказы</title>
  <link type="text/css" rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.0.0/css/bootstrap.min.css">
  <link type="text/css" rel="stylesheet" href="<c:url value='/resources/css/index.css'/>">
</head>
<body>
<div id="loading" style="display: none;">
  <img src="<c:url value='/resources/css/img/ajax-loader.gif'/>" alt="загружается..."/>
</div>
<div id="content">
  <a href="<c:url value='/logout'/>" style="float: right;">Выйти</a>
  <div class="title">Мои заказы</div>
  <div id='ordersCntr'>
  <c:if test="${not empty orders}">
    <div class="titleRow">
      <div class="cell3">Номер заказа</div>
      <div class="cell3">Стоимость заказа (руб.)</div>
      <div class="cell3">Последнее изменение</div>
      <div></div>
      <div style="clear: both"></div>
    </div>
    <c:forEach items="${orders}" var="order">
    <div class="orderRow">
      <div class="orderRowCtl" data-orderid="${order.id}">
        <div class="cell3">№${order.id}</div>
        <div class="cell3 totalPrice">${order.totalPrice}</div>
        <div class="cell3 updated">${order.updated}</div>
        <div class="delOrderCtl" data-orderid="${order.id}">x</div>
        <div style="clear: both"></div>
      </div>
    </div>
  </c:forEach>
  </c:if>
  </div>
  <br/><br/>
  <div>Оформить новый заказ:</div>
  <form id="oderFrm">
    <span id="addItemRowCtl"><img src="<c:url value='/resources/css/img/add_new.gif'/>" width="10"/> товар</span><br/>
    <input id="addOrderCtl" type="button" value="Сохранить">
  </form>
</div>
<div id="templates" style="display:none;">
  <div class="itemRow">
    Товар:
    <select class="itemId">
      <c:forEach items="${items}" var="item">
        <option value='${item.id}'>${item.name}, ${item.measure} - ${item.price} руб.</option>
      </c:forEach>
    </select>
    Количество: <input type="number" class="num">
    <span class="delItemRowCtl">x</span>
    <div style="clear: both"></div>
  </div>
  <div class="orderRow">
    <div class="orderRowCtl">
      <div class="cell3 id"></div>
      <div class="cell3 totalPrice"></div>
      <div class="cell3 updated"><span class="updated"></span></div>
      <div class="delOrderCtl">x</div>
      <div style="clear: both"></div>
    </div>
  </div>
  <div class="itemsCntr">
    <div class="addNewItemCtl"><img src="<c:url value='/resources/css/img/add_new.gif'/>" width="10"/> товар</div>
    <a class="addItemsCtl">Сохранить</a>
    <div class="cntr"></div>
    <a class="delItemsCtl">Удалить выбранные товары</a>
  </div>
  <div class="itemOrderRow">
    <div class="ch"><input type="checkbox" name="itemId"></div>
    <div class="cell4 name"></div>
    <div class="cell4">
      <span class="num editable"></span>
      <span class="measure"></span>
    </div>
    <div class="cell4 price"></div>
    <div style="clear: both"></div>
  </div>
</div>
<script src="//cdn.jsdelivr.net/jquery/1.11.0/jquery.min.js"></script>
<script src="//cdn.jsdelivr.net/jquery.ui/1.10.4/jquery-ui.min.js"></script>
<script src="<c:url value='/resources/js/jquery.jeditable.js'/>"></script>
<script src="<c:url value='/resources/js/jquery.jeditable.inputs.js'/>"></script>
<script src="<c:url value='/resources/js/shop.js'/>"></script>
<script>
  $(function(){
    doColorRows();
    doOrderRowClickable($('.orderRowCtl'));
    doDelOrderCtlClickable($('.delOrderCtl'));

    function doOrderRowClickable(orderRowCtlEls) {
      orderRowCtlEls.click(function(event) {
        var target = $(event.currentTarget);
        if (!target.hasClass('active')) {
          var orderId = target.data('orderid');
          $.post('itemOrderInfos', {orderId: orderId}, function (items) {
            var el = $('#templates .itemsCntr').clone();
            el.data('orderid', orderId);
            el.find('.addNewItemCtl').data('orderid', orderId);
            el.find('.delItemsCtl').data('orderid', orderId);
            doAddItemRowCtlClickable(el.find('.addNewItemCtl'));
            el.find('.addItemsCtl').click(function(event){
              var data = {};
              $.each(target.parent('.orderRow').find('.itemRow'), function(id, itemRowEl){
                data[$(itemRowEl).find('.itemId').val()] = $(itemRowEl).find('.num').val();
              });
              $.post('<c:url value="/user/addItems"/>?orderId='+orderId, data, function (order) {
                rewriteItems(target, order);
              });
            });
            el.find('.delItemsCtl').click(function(event){
              var delItemsTarget = $(event.currentTarget);
              var url = '<c:url value="/user/delItem"/>?orderId='+delItemsTarget.data('orderid');
              $.each(el.find('input[name=itemId]:checked'), function(id, chEl){
                url += '&ids=' + $(chEl).data('itemid');
              });
              $.post(url, function(order) {
                el.find('input[name=itemId]:checked').parent('.itemOrderRow').remove();
                rewriteItems(target, order);
              });
            });
            el.find('.cntr').attr('id', 'items' + orderId);
            $.each(items, function (id, item) {
              el.find('.cntr').append(itemOrderRowTemplate(item, true, function(orderRowCtlEl) {
                var orderId = orderRowCtlEl.data('orderid');
                $.get('<c:url value="/user/getOrder"/>?id='+orderId, function(order) {
                  rewriteItems(orderRowCtlEl, order);
                });
              }, target));
            });
            el.insertAfter(target);
            target.addClass('active');
            doColorRows();
          });
        } else {
          target.removeClass('active');
          target.parent('.orderRow').find('.itemsCntr').remove();
        }
      });
    }
    function doDelOrderCtlClickable(elem) {
      elem.click(function (event) {
        var target = $(event.currentTarget);
        $.post('<c:url value="/user/delOrder"/>', {id: target.data('orderid')}, function () {
          target.parents('.orderRow').remove();
        });
      });
    }
    $('#addOrderCtl').click(function(event){
      var data = {};
      $.each($('#oderFrm .itemRow'), function(id, elem){
        data[$(elem).find('.itemId').val()] = $(elem).find('.num').val();
      });
      $.post('<c:url value="/user/createOrder"/>', data, function (order) {
        $('#oderFrm .itemRow').remove();
        var elem = orderRowTemplate(order);
        elem.insertAfter($('#ordersCntr .titleRow'));
        elem.find('.orderRowCtl').click();
      });
    });
    function orderRowTemplate(order) {
      var el = $('#templates .orderRow').clone();
      el.find('.orderRowCtl').data('orderid', order.id);
      el.find('.id').text('№'+order.id);
      el.find('.totalPrice').text(order.totalPrice);
      el.find('.updated').text(order.updated);
      doOrderRowClickable(el.find('.orderRowCtl'));
      el.find('.delOrderCtl').data('orderid', order.id);
      doDelOrderCtlClickable(el.find('.delOrderCtl'));
      return el;
    }
  });
</script>

</body>
</html>