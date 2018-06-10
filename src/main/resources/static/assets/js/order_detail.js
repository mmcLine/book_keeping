$(function () {
    queryBydate("2016-01-01","2020-01-01",0);
})

function queryBydate(startDate,endDate,pageNo){
    $.ajax({
        type: 'post',
        url: '/book/order/orderDetail',//请求数据的地址
        dataType: "json",        //返回数据形式为json
        data:{'pageNo':pageNo,'startDate':startDate,'endDate':endDate},
        success: function (result) {
            $("#orderDetail_tbody").empty();
            var tobodyHtml='';
            var list=result.data;
            for (var i=0;i<list.length;i++){
                var stringItem=list[i];
                var item=eval('(' + stringItem+ ')');
                tobodyHtml+='<tr>' +
                    '<td><input type="checkbox"></td>' +
                    '<td>'+item.pkey+'</td>' +
                    '<td>'+item.name+'</td>' +
                    '<td>'+item.tradeType.typeName+'</td>' +
                    '<td>'+item.amt+'</td>' +
                    '<td>'+item.trade_date+'</td>' +
                    '<td>'+item.content+'</td>' +
                    '<td>' +
                    '<div class="am-btn-toolbar">' +
                    '<div class="am-btn-group am-btn-group-xs">' +
                    '<a class="am-btn am-btn-default am-btn-xs am-text-secondary" href="/book/order/upd/'+item.pkey+'"><span class="am-icon-pencil-square-o"></span> 编辑</a>' +
                    '<a class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only" href="/book/order/del/'+item.pkey+'"><span class="am-icon-trash-o"></span> 删除</a>' +
                    '</div>' +
                    '</div>' +
                    '</td>';
            }
            $("#orderDetail_tbody").append(tobodyHtml);
        }, error: function (errorMsg) {
            $("#orderDetail_tbody").append("数据读取失败！");
        }
    })
};

function queryDate(pageNo) {
    var startDate=$("#orderdetail_start_date").val();
    var endDate=$("#orderdetail_end_date").val();
    queryBydate(startDate,endDate,pageNo);
}

function exportOrder() {
    var startDate=$("#orderdetail_start_date").val();
    var endDate=$("#orderdetail_end_date").val();
    window.open("/book/order/exportOrder?start="+startDate+"&end="+endDate);
}