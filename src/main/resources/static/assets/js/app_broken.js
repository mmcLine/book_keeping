$(function () {
    var echartsA = echarts.init(document.getElementById('tpl-echarts-A'));
    var option = {

        tooltip: {
            trigger: 'axis',
        },
        legend: {
            data: ['本周', '上周', '大上周']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: [{
            type: 'category',
            boundaryGap: true,
            data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
        }],

        yAxis: [{
            type: 'value'
        }],
        series: [{
            name: '本周',
            type: 'line',
            areaStyle: {normal: {}},
            data: [],
            itemStyle: {
                normal: {
                    color: '#59aea2'
                },
                emphasis: {}
            }
        },
            {
                name: '上周',
                type: 'line',
                areaStyle: {normal: {}},
                data: [],
                itemStyle: {
                    normal: {
                        color: '#e7505a'
                    }
                }
            },
            {
                name: '大上周',
                type: 'line',
                areaStyle: {normal: {}},
                data: [],
                itemStyle: {
                    normal: {
                        color: '#32c5d2'
                    }
                }
            }
        ]
    };
    echartsA.setOption(option);
    // 异步加载数据
    var series1 = [];
    var series2 = [];
    var series3 = [];
    $.ajax({
        type: 'post',
        url: '/book/order/brokenOrder',//请求数据的地址
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            console.info(result)
            //请求成功时执行该函数内容，result即为服务器返回的json对象
            $.each(result.series0, function (index, item) {
                series1.push(item.amt);
            });
            $.each(result.series1, function (index, item) {
                series2.push(item.amt);
            });
            $.each(result.series2, function (index, item) {
                series3.push(item.amt);
            });
            echartsA.hideLoading();    //隐藏加载动画
            echartsA.setOption({        //加载数据图表
                series: [{
                        data: series1
                     },
                    {
                        data: series2
                    },{
                        data: series3
                    }
                ]
            });
        },
        error: function (errorMsg) {
            //请求失败时执行该函数
            alert("图表请求数据失败!");
            echartsA.hideLoading();
        }
    });
})


