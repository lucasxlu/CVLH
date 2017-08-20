$('.btn').hover(function(){
    $(this).find('i').addClass('animated wobble');
    setTimeout(function(){
        $(this).find('i').removeClass('animated wobble');
    }, 1000);
},function(){
    $(this).find('i').removeClass('animated wobble');
});