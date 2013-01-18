var swfu;
$(function(){							
	swfu = new SWFUpload({
		// Backend settings
		upload_url: base+"/effect/uploadfile.jspx",
		file_post_name: "upload",

		// Flash file settings
		file_size_limit : "5 MB",
		file_types : "*.zip",
		file_types_description : "Zip 文件",
		file_upload_limit : "0",
		file_queue_limit : "1",

		// Event handler settings
		swfupload_loaded_handler : swfUploadLoaded,
		
		file_dialog_start_handler: fileDialogStart,
		file_queued_handler : fileQueued,
		file_queue_error_handler : fileQueueError,
		file_dialog_complete_handler : fileDialogComplete,
		
		//upload_start_handler : uploadStart,	// I could do some client/JavaScript validation here, but I don't need to.
		upload_progress_handler : uploadProgress,
		upload_error_handler : uploadError,
		upload_success_handler : uploadSuccess,
		upload_complete_handler : uploadComplete,

		// Button Settings
//		button_image_url : resPath+"/swfupload/XPButtonUploadText_61x22.png",
		button_placeholder_id : "spanButtonPlaceholder",
		button_width: 61,
		button_height: 22,
        button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
        button_cursor: SWFUpload.CURSOR.HAND,
		
		// Flash Settings
		flash_url : resPath+"/swfupload/swfupload.swf",

		custom_settings : {
			progress_target : "fsUploadProgress",
			upload_successful : false
		},
		
		// Debug settings
		debug: false
	});
	
	var v = $("#editForm").validate({
		rules:{
			"effect.category_id": {required:true},
			"effect.title": {required:true, maxlength:200},
			"effect.author": {required:true, maxlength:200},
			"effect.description": {required:true}
		},
		messages:{
			"effect.category_id": {required:"请选择所属分类"},
			"effect.title": {required:"请输入特效标题", maxlength:"不能超过200个字符"},
			"effect.author": {required:"请输入特效作者", maxlength:"不能超过200个字符"},
			"effect.description": {required:"请填写特效使用说明"}
		},
		errorPlacement: function(label, element) {
			label.appendTo(element.parent());
		},
		submitHandler:function(){
			var fileName = document.getElementById("fileName");
			if (fileName.value == ""){
				uploadDone();
				return false;
			}
			swfu.startUpload();
		}
	});	
});

var formChecker = null;
function swfUploadLoaded() {
	
}
 // Called by the queue complete handler to submit the form
function uploadDone() {
	try {             
		$("#editForm").ajaxSubmit(function(data){
	        if(data=="success"){
	            alert('修改成功！');
	        	window.location.reload();
	        }else{
	        	alert('修改失败！');
	        	$('#message').html(data);
	        }
	    });
	} catch (ex) {
		alert("提交表单失败！"+ex);
	}
}

function fileDialogStart() {
//	var fileName = document.getElementById("fileName");
//	fileName.value = "";
	this.cancelUpload();
}

function fileQueueError(file, errorCode, message)  {
	try {
		// Handle this error separately because we don't want to create a FileProgress element for it.
		switch (errorCode) {
		case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
			alert("已经添加队列中太多的文件.\n" + (message === 0 ? " 你已经达到上传限制." : (message > 1 ? "你可以选择 " + message + " 个文件." : "")));
			return;
		case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
			progress.setStatus("上传文件过大");
			this.debug("Error Code: File too big, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			return;
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
			progress.setStatus("不能上载 0 字节的文件");
			this.debug("Error Code: Zero byte file, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			return;
		case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
			progress.setStatus("无效的文件类型");
			this.debug("Error Code: Invalid File Type, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			return;
		default:
			progress.setStatus("未知错误");
			this.debug("Error Code: " + errorCode + ", File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			return;
		}
	} catch (e) {
	}
}

function fileQueued(file) {
	try {
		var fileName = document.getElementById("fileName");
		fileName.value = file.name;
	} catch (e) {
	}

}
function fileDialogComplete(numFilesSelected, numFilesQueued) {
//	validateForm();
}

function uploadProgress(file, bytesLoaded, bytesTotal) {

	try {
		var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);

		file.id = "singlefile";	// This makes it so FileProgress only makes a single UI element, instead of one for each file
		var progress = new FileProgress(file, this.customSettings.progress_target);
		progress.setProgress(percent);
		progress.setStatus("文件上传中...");
	} catch (e) {
	}
}

function uploadSuccess(file, serverData) {
	try {
		file.id = "singlefile";	// This makes it so FileProgress only makes a single UI element, instead of one for each file
		var progress = new FileProgress(file, this.customSettings.progress_target);
		progress.setComplete();
		progress.setStatus("文件上传完成");
		progress.toggleCancel(false);
		
		if(!/[^0-9]+/.test(serverData)){
			this.customSettings.upload_successful = true;
			document.getElementById("fileId").value = serverData;
		}else{
			alert(serverData);
			this.customSettings.upload_successful = false;
		}
		
	} catch (e) {
	}
}

function uploadComplete(file) {
	try {
		if (this.customSettings.upload_successful) {
			this.setButtonDisabled(true);
			uploadDone();
		} else {
			file.id = "singlefile";	// This makes it so FileProgress only makes a single UI element, instead of one for each file
			var progress = new FileProgress(file, this.customSettings.progress_target);
			progress.setError();
			progress.setStatus("文件上传失败！");
			progress.toggleCancel(false);
			
			var fileName = document.getElementById("fileName");
			fileName.value = "";
			
		}
	} catch (e) {
	}
}

function uploadError(file, errorCode, message) {
	try {
		
		if (errorCode === SWFUpload.UPLOAD_ERROR.FILE_CANCELLED) {
			// Don't show cancelled error boxes
			return;
		}
		
		var fileName = document.getElementById("fileName");
		fileName.value = "";

		file.id = "singlefile";	// This makes it so FileProgress only makes a single UI element, instead of one for each file
		var progress = new FileProgress(file, this.customSettings.progress_target);
		progress.setError();
		progress.toggleCancel(false);

		switch (errorCode) {
		case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
			progress.setStatus("HTTP 错误: " + message);
			this.debug("Error Code: HTTP Error, File name: " + file.name + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
			progress.setStatus("上传失败");
			this.debug("Error Code: Upload Failed, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.IO_ERROR:
			progress.setStatus("服务器 (IO) 错误");
			this.debug("Error Code: IO Error, File name: " + file.name + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
			progress.setStatus("安全错误");
			this.debug("Error Code: Security Error, File name: " + file.name + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
			progress.setStatus("已取消");
			this.debug("Error Code: Upload Cancelled, File name: " + file.name + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
			progress.setStatus("已停止");
			this.debug("Error Code: Upload Stopped, File name: " + file.name + ", Message: " + message);
			break;			
		case SWFUpload.UPLOAD_ERROR.MISSING_UPLOAD_URL:
			alert("配置错误！");
			this.debug("Error Code: No backend file, File name: " + file.name + ", Message: " + message);
			return;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
			alert("您只能上传一个文件！");
			this.debug("Error Code: Upload Limit Exceeded, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			return;
		case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
		case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
			break;
		default:
			alert("上传失败，请稍候再试！");
			this.debug("Error Code: " + errorCode + ", File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			return;
		}
	} catch (ex) {
	}
}
