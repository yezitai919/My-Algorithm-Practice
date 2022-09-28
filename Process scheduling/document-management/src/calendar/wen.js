//题目轮播
var swiper = new Swiper(´.swiper-container´, {

    pagination: ´.swiper-pagination´,

    prevButton:´.swiper-button-prev´,
    nextButton:´.swiper-button-next´,

    paginationClickable: true,



        paginationBulletRender: function (index, className) {
        $("#totnum").text(index 1);//总页码
        return ´<span class="´   className   ´">´   (index   1)   ´</span>´;


    },

    onSlideChangeEnd: function(swiper){
        $("#curnum").text(swiper.activeIndex 1);//当前页
    }

});

//点击底部出现题目数
$("#showall").click(function(){
    $(".maskwhite").toggle();
    $(".swiper-pagination").toggle();
});

//选择答案
$("dl.swiper-slide dd").click(function(){
    $(this).parent("dl").find("dd").removeClass("chance");
    $(this).addClass("chance");
    var indexnum = $(this).parent("dl").index();
    $(".swiper-pagination span").eq(indexnum).addClass("curr");
});

//交卷
$("#numok").click(function(){

    $(".swiper-pagination").hide();

    var allnum = $("#totnum").text();
    $("#subnum").text(allnum);
    var lengths = $(".swiper-pagination span.curr").length;

    if(lengths==allnum){
        $(".maskwhite").show();
        $(".subjuan").show();
        $("#subno").click(function(){//取消
            $(".maskwhite").hide();
            $(".subjuan").hide();
        });
        $("#subyes").click(function(){//取消
            $(".maskwhite").hide();
            $(".subjuan").hide();
            window.location.href="#"
        });
    }

    else{
        $(".maskwhite").hide();
    }

});