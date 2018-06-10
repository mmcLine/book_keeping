$(function() {
    $.ajax({
        type: 'get',
        url: '/book/order/brokenOrder',//请求数据的地址
        dataType: "json",        //返回数据形式为json
        success: function (data) {
            console.info(data)
        },
        error: function (errorMsg) {
            //请求失败时执行该函数
            alert("图表请求数据失败!");
        }
    })
})