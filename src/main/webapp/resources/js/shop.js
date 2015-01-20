$(function(){
  $(document).on('ajaxSend', function() {
    $('#loading').show()
  });
  $(document).on('ajaxComplete', function() {
    $('#loading').hide()
  });
  doAddItemRowCtlClickable($('#addItemRowCtl'));
});

function doAddItemRowCtlClickable(elem) {
  elem.click(function (event) {
    var target = $(event.currentTarget);
    itemRowTemplate().insertBefore(target);
  });
}
function doColorRows() {
  $("div.itemOrderRow:odd").css( "background-color", "#E2EEE3" );
  $("div.itemOrderRow:even").css( "background-color", "#fff" );
}
function itemRowTemplate() {
  var itemRowEl = $('#templates .itemRow').clone();
  doDelItemRowCtlClickable(itemRowEl.find('.delItemRowCtl'));
  return itemRowEl;
}
function doDelItemRowCtlClickable(elem) {
  elem.click(function (event) {
    var target = $(event.currentTarget);
    target.parent('.itemRow').remove();
  });
}
function itemOrderRowTemplate(item, editable, cb, orderRowCtlEl) {
  var el = $('#templates .itemOrderRow').clone();
  el.find('.name').text(item.name);
  el.find('.num').text(item.num);
  el.find('.measure').text(item.measure);
  el.find('.price').text(item.price + ' руб');
  if (editable) {
    el.find('input').data('itemid', item.id);
    el.find('.num').attr('id',item.id);
    el.find('.num').addClass('editable');
    doItemOrderInfoNumEditable(el.find('.num'), cb, orderRowCtlEl);
  }
  return el;
}
function doItemOrderInfoNumEditable(elem, cb, orderRowCtlEl) {
  elem.editable('updateItemInfoNum', getEditableOptions('text','100px'), function(value, settings) {
    if (cb && orderRowCtlEl) {
      cb(orderRowCtlEl);
    }
  });
}
function rewriteItems(orderRowCtlEl, order) {
  orderRowCtlEl.find('.totalPrice').text(order.totalPrice);
  orderRowCtlEl.find('.updated').text(order.updated);
  orderRowCtlEl.removeClass('active');
  orderRowCtlEl.parent('.orderRow').find('.itemsCntr').remove();
  orderRowCtlEl.click();
}
function getEditableOptions(type, width){
  type = (type == undefined ? 'text' : type);
  width = (width == undefined ? '650px' : width);
  return {
    event: 'dblclick',
    type: type,
    submit: 'Сохранить',
    cancel: 'Отмена',
    width: width,
    height: '26px',
    data: function(value, settings) {
       if (type == 'select') {
         return selectData;
       } else {
         return value;
       }
    }
  }
}



