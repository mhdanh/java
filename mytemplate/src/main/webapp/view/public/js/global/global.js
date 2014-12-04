function removeFileExtension(fileName){
	var lastDot = fileName.lastIndexOf(".");
	if(lastDot == -1){
		return fileName;
	}else{
		console.log(fileName.substring(0,lastDot));
		return fileName.substring(0,lastDot);
	}
}