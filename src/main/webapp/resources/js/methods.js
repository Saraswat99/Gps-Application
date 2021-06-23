function ajaxGetCallApi(url){
    return $.ajax({
        type:'GET',
        url:url,
        error:function(){
            alert('error');
        }
    });
}

function ajaxPostCallApi(url,data){
    return  $.ajax({
        type: 'POST',
        url: url,
        dataType:'json',
        contentType:'application/json',
        data:JSON.stringify(data),
        error: function () {
            alert('error');
        }
    });
}

function ajaxPutCallApi(url,data){
    return  $.ajax({
        type: 'PUT',
        url: url,
        dataType:'json',
        contentType:'application/json',
        data:JSON.stringify(data),
        error: function () {
            alert('error');
        }
    });
}

function ajaxEnableCallApi(url){
    return $.ajax({
        type:'PUT',
        url:url,
        error:function(){
            alert("Error");
        }
    });
}

function ajaxDeleteCallApi(url){
    return $.ajax({
        type:'DELETE',
        url:url,
        error:function(){
            alert("error");
        }
    });
}
