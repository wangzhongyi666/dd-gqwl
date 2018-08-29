//图片上传预览
function setImagePreviews() {
    var docObj = document.getElementById("filename");
    var dd = document.getElementById("ImgPr");
    dd.innerHTML = "";
    var fileList = docObj.files;
    var i = 0;
    dd.innerHTML += "<div  style='float:left;border:3px solid #cccccc;margin:0%;' ><img id='img1' /> </div>";
    var imgObjPreview = document.getElementById("img1");
    if (docObj.files && docObj.files[i]) {
        //火狐下，直接设img属性
        imgObjPreview.style.display = 'block';
        imgObjPreview.style.width = 'auto';
        imgObjPreview.style.height = '180px';
        //imgObjPreview.src = docObj.files[0].getAsDataURL();
        //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
        imgObjPreview.src = window.URL.createObjectURL(docObj.files[i]);
    } else {
        //IE下，使用滤镜
        docObj.select();
        docObj.blur();
        var imgSrc = document.selection.createRange().text;
        alert(imgSrc);
        var localImagId = document.getElementById("img1");
        //必须设置初始大小
        localImagId.style.width = "auto";
        localImagId.style.height = "180px";
        localImagId.style.display = 'block';
        $(localImagId).attr("src", imgSrc);
        //图片异常的捕捉，防止用户修改后缀来伪造图片
        document.selection.empty();
    }
    return true;
}


function setImagePreviews2() {
    var docObj = document.getElementById("filename2");
    var dd = document.getElementById("ImgPr2");
    dd.innerHTML = "";
    var fileList = docObj.files;
    var i = 0;
    dd.innerHTML += "<div  style='float:left;border:3px solid #cccccc;margin:0%;' ><img id='img2' /> </div>";
    var imgObjPreview = document.getElementById("img2");
    if (docObj.files && docObj.files[i]) {
        //火狐下，直接设img属性
        imgObjPreview.style.display = 'block';
        imgObjPreview.style.width = 'auto';
        imgObjPreview.style.height = '180px';
        //imgObjPreview.src = docObj.files[0].getAsDataURL();
        //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
        imgObjPreview.src = window.URL.createObjectURL(docObj.files[i]);
    } else {
        //IE下，使用滤镜
        docObj.select();
        docObj.blur();
        var imgSrc = document.selection.createRange().text;
        alert(imgSrc);
        var localImagId = document.getElementById("img2");
        //必须设置初始大小
        localImagId.style.width = "auto";
        localImagId.style.height = "180px";
        localImagId.style.display = 'block';
        $(localImagId).attr("src", imgSrc);
        //图片异常的捕捉，防止用户修改后缀来伪造图片
        document.selection.empty();
    }
    /* $("#uploadfile").*/
    return true;
}
