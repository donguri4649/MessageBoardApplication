window.onload = function() {
	if(document.getElementById("submitFlg").value == "1"){
		window,close();
	}
}

function sousin(){
	
	if(document.getElementById("email").value == ""
		|| document.getElementById("phone").value == ""
		|| document.getElementById("inquircontent").value == ""
		|| document.getElementById("userName").value == ""){
			alert("未入力の項目があります。");
			return;	
		}
	document.getElementById("inquiryemail").value = document.getElementById("email").value;
	document.getElementById("inquiryphone").value = document.getElementById("phone").value;
	
	document.getElementById("submitFlg").value = "1";
	var form = document.getElementById("form04");
	document.model.action = `inquiry`;
    form.method = "post";
    form.submit();
    
	//window,close();
}