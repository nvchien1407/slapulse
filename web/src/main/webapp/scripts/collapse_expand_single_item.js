// determine whether to show OR hide funtion 
// depends on if element is shown or hidden
// - performed on the server
function showDiv(id, otherId) { 
	var enlargeOther = false;
	var enlargeCurrent = false;

	if (document.getElementById) { // DOM3 = IE5, NS6
		if (document.getElementById(id).style.display == "none" && 
			document.getElementById(otherId).style.display == "none"){
				enlargeCurrent = true;
		} 
		if(document.getElementById(id).style.display == "block" &&
		   document.getElementById(otherId).style.display == "block") {
				enlargeOther = true;
		}	
	} else { 
		if (document.layers) {	
			if(document.id.display == "none" && 
				document.otherId.display == "none"){
				enlargeCurrent = true;
			}
			if(document.id.display == "block" &&
				document.otherId.display == "block") {
					enlargeOther = true;
			}
		} else {
			if(document.all.id.style.visibility == "none" &&
				document.all.otherId.style.display == "none"){
					enlargeCurrnet = true;
			} 
			if(document.all.id.style.visibility == "block" &&
				document.all.otherId.style.display == "block") {
					enlargeOther = true;
			}
		}
	}
	if(true == enlargeCurrent) {
		window.location="mainPage.html?idToEnlarge=" + id + "&displayAction=" + true;
	} else if(true == enlargeOther) {
		window.location="mainPage.html?idToEnlarge=" + otherId + "&displayAction=" + true;
	} else {
		window.location="mainPage.html?displayAction=" + true;
	}
}