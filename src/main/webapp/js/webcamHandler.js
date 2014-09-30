var WebcamHandler = {
    webcam: null,

    init: function () {
        navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia;
        if (navigator.getUserMedia) {
            navigator.getUserMedia({audio: false, video: true}, this.onSuccess, this.onError);
        } else {
            this.onError();
        }
        return this;

    },

    onSuccess: function (stream) {
        WebcamHandler.webcam = WebcamLive.init(stream);
    },
    onError: function (error) {
        console.log("navigator.getUserMedia error: ", error);
        WebcamHandler.webcam = IPWebcamLive.init();
    },

    snapshot: function () {
        this.webcam.snapshot();
    },
    
    savePicture : function(command){
	    console.log(this.webcam.restUrlForSave);
	    console.log(this.webcam.dataToSend);
	    command["money"] = "0.0";
	    command["stringFile"] = this.webcam.dataToSend ;
	    //data = {"format":"PORTRAIT", "colorimetry":"COLOR", "money":"0.0", "stringFile":this.webcam.dataToSend};
	    var data = command;
	    console.log(command);

        postData(this.webcam.restUrlForSave, data)
            .done(function (data) {
                var savedLink = document.querySelector('#savedLink');
                savedLink.classList.remove("hidden");
                savedLink.setAttribute('href', data);
            })
            .fail(function (jqXHR) {
                console.log("an error has occured", jqXHR);
            });
    }
};
