// 获取弹窗
$('#showWeChat').click(() => {
    $('#weChatModal').show();
    $('#weChatImg').attr('src', '/images/wechat.png');

})

// 获取 <span> 元素，设置关闭按钮
var span = document.getElementsByClassName("close")[0];

// 当点击 (x), 关闭弹窗
span.onclick = function() {
    $('#weChatModal').hide();
}