//表单提交
document.querySelector('#comment-form').addEventListener('submit',function(e){
    let fields = $("#comment-form").serializeArray();
    let data = {}
    $.each(fields, function (index, field) {
        data[field.name] = field.value;
    })
    $.ajax({
        type: 'post',
        contentType: 'application/json;charset=utf-8',
        url: '/api/comment',
        data: JSON.stringify(data),
        success: (res) => {
            console.log(res)
            window.location.reload();
        },
        error: (res) => {
            console.log(res)
        }
    })
    e.preventDefault();
},false);

// 回复
function reply(pId, cName) {
    $("#pId").val(pId);
    $("#forWho").val(cName);
    $('#response-name').text("@" + cName);
    $("#cancel-comment-reply-link").show();
    $("#comments")[0].scrollIntoView();
}
// 取消回复
function cancelReply() {
    $("#cancel-comment-reply-link").hide();
    $('#response-name').text("");
    $("#pId").val("");
    $("#forWho").val("");
}
window.onload = function () {
    const url = window.location.href;
    if (url.indexOf("page") != -1) {
        $("#comments")[0].scrollIntoView();
    }
}