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