function passwordCert(flag){
	var UserInput = prompt("パスワードを入力して下さい。");
	//var pass = "%u958B%u3051%u30B4%u30DE";
	if(!UserInput){
		return;
	}
	
	var data = UserInput + "," + flag;
	
	$.ajax({
      url: "/janitor",
      type: "POST",
      data: {
        note: data,
        _csrf: $("*[name=_csrf]").val()  // CSRFトークンを送信
      }
    })
    .done(function(data) {
	
		if(UserInput == ""){
			alert("パスワードを入力してください。");
			return;
		}
		
		if(!(UserInput == unescape(data))){
			alert("パスワードが間違っています。");
			return;
		}
		
		if(flag == 2){
			document.getElementById("kirikaeFlag").value = "1";
			var target = document.getElementById("form02");
			document.model.action = `main`;
    		target.method = "post";
    		target.submit();
		}else　if(flag == 1){
			var target = document.getElementById("form02");
			document.model.action = `janitorOffice`;
    		target.method = "post";
    		target.submit();
		}
    	
    })
    .fail(function() {
      alert("error!");
    })
}
