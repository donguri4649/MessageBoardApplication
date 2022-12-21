window.onload = function() {
  var operCategory = document.getElementById('operCategory').value;
  var updateFlg = document.getElementById('updateFlg').value;
  if(operCategory == "4"){
		document.getElementById('update').style.visibility = 'hidden'
		document.getElementById('chat').disabled = "disabled";
		document.getElementById('selectUser').disabled = "disabled";
	}else if(updateFlg == "1"){
		window,close();
	}
	//checkfunction();
};

//キャンセル
function Cancel(){
	window.close();
}

//クリア
function clear(){
	
}

//更新処理
function Update(){
	
	document.getElementById('operCategory').value = "55";
	
	var target = document.getElementById("form01");
	document.model.action = `modal`;
    target.method = "post";
    target.submit();
	
}
//******janitorOffice******//
/*function checkfunction() {
	var chk = document.getElementsByName("switchcheckbox");
	var manager = document.getElementById("manager");
	var guests = document.getElementById("guests");
	for(var i = 0;i < chk.length; i++){
		if(chk[0].checked == true){
			alert(i);
			guests.style.visibility = 'hidden';
			manager.style.visibility = 'visible';
		}else {
       		manager.style.visibility = 'hidden';
       		guests.style.visibility = 'visible';
    	}
	}
}*/

function janitorOffice(flagnumber){
	var UserInput = prompt("パスワードを入力して下さい。");
	var pass = "password%u30B3%u30D4%u30FC";
	if(flagnumber == "1"){
		
	}
	
	if(!UserInput){
		return;
	}
	
	if(UserInput == ""){
		alert("パスワードを入力してください。");
		return;
	}
	
	if(!(UserInput == unescape(pass))){
		alert("パスワードが間違っています。");
		return;
	}
	
}