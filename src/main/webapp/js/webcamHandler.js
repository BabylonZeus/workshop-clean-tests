var WebcamHandler = {
	webcam : null,
    
	init : function (type) {
        navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia;
		if (navigator.getUserMedia) {
				navigator.getUserMedia({audio: false, video: true}, 
						this.onSuccess , 
						this.onError);
		} else {
			this.onError();
		};
		return this;
		
    },
    
    onSuccess : function(stream) {
    	WebcamHandler.webcam = WebcamLive.init(stream);
    }, 
    onError : function(error) {
        console.log("navigator.getUserMedia error: ", error);
        WebcamHandler.webcam = IPWebcamLive.init();
    },
    
    snapshot : function(){
    	this.webcam.snapshot();
    },
    
    savePicture : function(command){
	    console.log(this.webcam.restUrlForSave);
	    console.log(this.webcam.dataToSend);
	    data = {"format":"PORTRAIT", "colorimetry":"COLOR", "money":"0.0", "stringFile":this.webcam.dataToSend};

        postData(this.webcam.restUrlForSave, data)
            .done(function (data) {
                document.querySelector('#savedLink').setAttribute('href', data);
            })
            .fail(function (jqXHR) {
                console.log("an error has occured", jqXHR);
            });
    }
};
