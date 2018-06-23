$(function () {
    $.ajax({
        type: 'post',
        url: '/book/story/listStory',//请求数据的地址
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            var tobodyHtml='';
            for (var i=0;i<result.length;i++){
                var item=result[i];
                console.info(item.imgUrl);

                tobodyHtml+=
                    '<div class="wthree">'
                    +' <div class="col-md-6 wthree-left wow fadeInDown"  data-wow-duration=".8s" data-wow-delay=".2s">'
                    +' <div class="tch-img">'
                    +' <img src="/book/sotry/getImag/'+item.imgUrl+'" class="img-responsive" alt="">'
                    +'</div>'
                    +'</div>'
                    +'<div class="col-md-6 wthree-right wow fadeInDown"  data-wow-duration=".8s" data-wow-delay=".2s">'
                    +'<h3><a href="#">'+item.title+'</a></h3>'
                    +' <h6>BY <a href="singlepage.html">'+item.user.name+'</a>'+item.createDate+'</h6>'
                    +' <p>'+item.content+'</p>'
                    +'<div class="bht1">\n' +
                    '<a href="singlepage.html">Read More</a>\n' +
                    '</div>\n' +
                    '<div class="soci">\n' +
                    '<ul>\n' +
                    '\n' +
                    '<li class="hvr-rectangle-out"><a class="twit" href="#"></a></li>\n' +
                    '<li class="hvr-rectangle-out"><a class="pin" href="#"></a></li>\n' +
                    '</ul>\n' +
                    '</div>\n' +
                    '<div class="clearfix"></div>\n' +
                    '\n' +
                    '</div>\n' +
                    '<div class="clearfix"></div>\n' +
                    '</div>'
            }
            $("#story_custom_list_id").append(tobodyHtml);
        }, error: function (errorMsg) {
            $("#story_custom_list_id").append("数据读取失败！");
        }
    })
})
