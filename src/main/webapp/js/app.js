var videoMode = "webcam";
var command = null;

var snapshotBtn = document.querySelector(".snapshot");
snapshotBtn.addEventListener('click', takeAPicture, false);
var snapshotResult = document.querySelector('.snapshotResult');

document.querySelector(".btn-cancel").addEventListener('click', cancelPicture, false);
document.querySelector(".btn-confirm").addEventListener('click', savePicture, false);

//the user has chosen a command
var cmdButtons = document.querySelectorAll(".btn-command");
[].forEach.call(cmdButtons, function(btn) {
    btn.addEventListener('click', function(){
        chooseCmd(btn.classList.item(2));
    }, false);
});

WebcamHandler.init();

function takeAPicture(){
	WebcamHandler.snapshot();
}


function cancelPicture(){
	document.querySelector('.snapshotResult').src = "";
}
function savePicture () {
	console.log("saving picture");
	WebcamHandler.savePicture();
}

function chooseCmd(cmd){
    command = cmd;
    document.querySelector(".order-cmd").classList.remove("hidden");
    document.querySelector(".photobooth").classList.remove("hidden");
}

function postData(url, data){
		return $.ajax({
			url: url,
			type: 'POST',
			contentType: "application/json; charset=utf-8",
			data: data
		});
}
function getData(url){
		return $.ajax({
			url: url,
			type: 'GET',
			dataType: 'json',
			contentType: "application/json; charset=utf-8"
		});

}



