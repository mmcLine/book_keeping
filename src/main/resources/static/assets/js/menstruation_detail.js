$(function(){
    // ==========================
    // 百度图表A http://echarts.baidu.com/
    // ==========================

    var echart_mens= echarts.init(document.getElementById('menstruation_detail_chart'));

option = {
    title: {
        text: '大姨妈折线图'
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data:['时间间隔']
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    toolbox: {
        feature: {
            saveAsImage: {}
        }
    },
    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: []
    },
    yAxis: {
        type: 'value',
        min: 20,
        max: 35,
        interval: 5
    },
    series: [

    ]

};


    echart_mens.setOption(option);

var dates = [];    //金额数组（实际用来盛放Y坐标值）
var cycles=[];

$.ajax({
    type: 'post',
    url: '/book/menstruation/list',//请求数据的地址
    dataType: "json",        //返回数据形式为json
    success: function (result) {
        //请求成功时执行该函数内容，result即为服务器返回的json对象
        for(var i=0;i<result.result.length;i++){
           var list=result.result;
            dates.push(list[i].mensDate);
            cycles.push(list[i].cycle);
        }

        echart_mens.setOption({        //加载数据图
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: dates
            },
            series: [
                {
                    // 根据名字对应到相应的系列
                    stack: '总量',
                    type: 'line',
                    name: '时间间隔',  //显示在上部的标题
                    data: cycles
                }]
        });
    },
    error: function (errorMsg) {
        //请求失败时执行该函数
        alert("图表请求数据失败!");
        echart_mens.hideLoading();
    }
});

})
