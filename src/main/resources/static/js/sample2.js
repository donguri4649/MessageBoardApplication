function returnFunc(){
	//操作区分(1:新規登録)(2:ページング)(3:削除)(4:参照)(5:編集)(6:検索)(7:前画面に戻る)
	document.getElementById('operCategory').value = "7";
	
	var target = document.getElementById("form02");
	document.model.action = "/main";
    target.method = "post";
    target.submit();
}

function switchButton(){
	
	var switchId = document.getElementsByName("switchId");
	var switchCount = 0;
	//var allSelectData = "";
	var username = document.getElementsByName("username");
	var userid = document.getElementsByName("userid");
	for(var i = 0;i < switchId.length; i++){
		if(switchId[i].checked){
			//alert(username[i].value);
			switchCount++;
			if(switchCount == 2){
				alert("切替先は2つ以上選択できません。");
				return;
			}
			document.getElementById("userName").value = username[i].value;
			document.getElementById("userId").value = userid[i].value;
		}
	}
	if(switchCount == 0){
		alert("切替先のユーザーを選択してください。。");
		return;
	}
	passwordCert(2);
}