$(function(){


       // ==========================
       // 百度图表A http://echarts.baidu.com/
       // ==========================

       var echartsC = echarts.init(document.getElementById('tpl-echarts-C'));


       optionC = {
           title: {
               text: '月消费走势图'
           },
           tooltip: {
               trigger: 'axis'
           },
           toolbox: {
               top: '0',
               feature: {
                   dataView: {show: true, readOnly: false},
                   magicType: {show: true, type: ['line', 'bar']},
                   restore: {show: true},
                   saveAsImage: {show: true}
               }
           },
           legend: {
               data: ['柱状消费额', '折线消费额']
           },
           xAxis: [{
               type: 'category',
               data: []
           }],
           yAxis: [{
               type: 'value',
               name: '消费',
               min: 0,
               max: 3000,
               interval: 600,
               axisLabel: {
                   formatter: '￥{value}'
               }
           },
               {
                   type: 'value',
                   name: '消费',
                   min: 0,
                   max: 3000,
                   interval: 600,
                   axisLabel: {
                       formatter: '￥{value}'
                   }
               }
           ],
           series: [{
               name: '柱状消费额',
               type: 'bar',
               data: []
           },
               {
                   name: '折线消费额',
                   type: 'line',
                   yAxisIndex: 1,
                   data: []
               }
           ]
       };

       echartsC.setOption(optionC);



    var names = [];    //月份数组（实际用来盛放X轴坐标值）
    var amts = [];    //金额数组（实际用来盛放Y坐标值）
    var amts2=[];

    $.ajax({
        type: 'get',
        url: '/book/order/order_month',//请求数据的地址
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            //请求成功时执行该函数内容，result即为服务器返回的json对象
            $.each(result.result, function (index, item) {
                names.push(item.month);    //挨个取出类别并填入类别数组
                amts.push(item.sumamt);    //挨个取出销量并填入销量数组
                amts2.push(item.sumamt*1+300)
            });


            echartsC.setOption({        //加载数据图表
                xAxis: {
                    data: names
                },
                series: [{
                    // 根据名字对应到相应的系列
                    type: 'bar',
                    name: '消费额',  //显示在上部的标题
                    data: amts
                },
              {
            // 根据名字对应到相应的系列
                 type: 'line',
                name: '消费额',  //显示在上部的标题
                data: amts2
             }]
            });
        },
        error: function (errorMsg) {
            //请求失败时执行该函数
            alert("图表请求数据失败!");
            echartsC.hideLoading();
        }
    });

})