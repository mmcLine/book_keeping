$(function () {
    $.ajax({
        type:"post",
        url:"/book/tradeType/getAllType",
        dataType:'json',
        success: function (result) {
            var typeHtml="<option value=\"option1\">所有类别</option>";
            for(var i=0;i<result.length;i++){
                var item=result[i];
                typeHtml+='<option value='+item.pkey+'>'+item.typeName+'</option>';
            }
            $("#trade_type").append(typeHtml);
        },error:function (error) {
            console.info("获取类别失败")
        }
    })
})