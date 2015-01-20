<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <title>Товары</title>
  <link type="text/css" rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.0.0/css/bootstrap.min.css">
  <link type="text/css" rel="stylesheet" href="<c:url value='/resources/css/index.css'/>">
</head>
<body>
<div id="loading" style="display: none;">
  <img src="<c:url value='/resources/css/img/ajax-loader.gif'/>" alt="загружается..."/>
</div>
<div id="content">
  <a href="<c:url value='/logout'/>" style="float: right;">Выйти</a>
  <div class="title">Товары</div>
  <a href="<c:url value='/admin/orders'/>">перейти к списку заказов</a>
  <form id="delItemsFrm" action="delItems?" method="post">
  <div id='itemsCntr'>
    <c:if test="${not empty items}">
      <div class="titleRow">
        <div></div>
        <div class="cell4">Наименование</div>
        <div class="cell4">Ед. измерения</div>
        <div class="cell4">Цена (руб.)</div>
        <div class="cell4">Последнее изменение</div>
        <div style="clear: both"></div>
      </div>
      <c:forEach items="${items}" var="item">
        <div class="itemRow" data-itemid="${item.id}">
          <div class="ch"><input type="checkbox" name="itemId" data-itemid="${item.id}"></div>
          <div class="cell4 name editable" id="${item.id}">${item.name}</div>
          <div class="cell4 measure editable" id="${item.id}">${item.measure}</div>
          <div class="cell4 price editable" id="${item.id}">${item.price}</div>
          <div class="cell4">${item.updated}</div>
          <div style="clear: both"></div>
        </div>
      </c:forEach>
    </c:if>
  </div>
  <input id="delItemsCtl" type="button" value="Удалить">
  </form>
  <br/><br/>
  <div>Добавить товар(ы):</div>
  <form:form id="itemFrm" action="addItems" method="post" modelAttribute="itemsList">
    <span id="addItemRowCtl"><img src="<c:url value='/resources/css/img/add_new.gif'/>" width="10"/> товар</span><br/>
    <input id="addItemsCtl" type="button" value="Сохранить">
  </form:form>
</div>
<div id="templates" style="display:none;">
  <div class="itemRow">
    Наименование: <input type="text" class="name">
    Ед. измерения:
    <select class="measure">
      <c:forEach items="${measures}" var="measure">
        <option value="${measure}">${measure}</option>
      </c:forEach>
    </select>
    Цена (руб.): <input type="number" class="price">
    <span class="delItemRowCtl">x</span>
    <div style="clear: both"></div>
  </div>
  <div class="orderRow">
    <div class="titleRow">
      <div class="cell4 id"></div>
      <div class="cell4 totalPrice"></div>
      <div class="cell4">(последнее изменение <span class="updated"></span>)</div>
    </div>
  </div>
</div>
<script src="//cdn.jsdelivr.net/jquery/1.11.0/jquery.min.js"></script>
<script src="//cdn.jsdelivr.net/jquery.ui/1.10.4/jquery-ui.min.js"></script>
<script src="<c:url value='/resources/js/jquery.jeditable.js'/>"></script>
<script src="<c:url value='/resources/js/jquery.jeditable.inputs.js'/>"></script>
<script src="<c:url value='/resources/js/shop.js'/>"></script>
<script>
  var selectData = '{';
  <c:forEach items="${measures}" var="measure">
  selectData += "'${measure}':'${measure}',";
  </c:forEach>
  selectData = selectData.substr(0,selectData.length-1);
  selectData += '}';

  $(function(){
    doColorRows();
    doItemNameEditable($('.itemRow .name'));
    doItemMeasureEditable($('.itemRow .measure'));
    doItemPriceEditable($('.itemRow .price'));

    $('#delItemsCtl').click(function(event){
      var action = $('#delItemsFrm').attr('action');
      $.each($('input[name=itemId]:checked'), function(id, elem){
        action += '&ids=' + $(elem).data('itemid');
      });
      $('#delItemsFrm').attr('action',action);
      $('#delItemsFrm').submit();
    });
    $('#addItemsCtl').click(function(event){
      $.each($('#itemFrm .itemRow'), function(id, elem){
        $(elem).find('.name').attr('name','items['+id+'].name');
        $(elem).find('.measure').attr('name','items['+id+'].measure');
        $(elem).find('.price').attr('name','items['+id+'].price');
      });
      $('#itemFrm').submit();
    });
    function doItemNameEditable(elem) {
      elem.editable('<c:url value="/admin/updateItemName"/>', getEditableOptions('text','100px'));
    }
    function doItemMeasureEditable(elem) {
      elem.editable('<c:url value="/admin/updateItemMeasure"/>', getEditableOptions('select','150px'));
    }
    function doItemPriceEditable(elem) {
      elem.editable('<c:url value="/admin/updateItemPrice"/>', getEditableOptions('text','100px'));
    }
  });
</script>

</body>
</html>