
window.onload = function() {
  document.getElementById('operCategory').value = "0";
  //alert(document.getElementById('allSelect').value);
  //alert(document.getElementById('userName').value);
  if(document.getElementById('searchStatus').value == "1"){
		//document.getElementById("openStatus").setAttribute("details","open");
		var element = document.getElementById( "openStatus" ) ;
		element.open = true ;
	}
};

//ログアウト
function logout(){
	document.myform.action = "/logout";
	document.myform.submit();
}

//私の伝言,すべての伝言
function selectList(selectFlag){
	document.getElementById('searchStatus').value = "";
	document.getElementById('allSelect').value = selectFlag;
	document.getElementById("dispIndex").value = "1";
	var target = document.getElementById("form01");
	document.model.action = `main`;
    target.method = "post";
    target.submit();
}

//削除
function deleta(selectdeleData, kubun){
	
	if(kubun == "1"){
		if(!confirm("削除します。よろしいですか？")){
			return false;
		}
		document.getElementById('selectdeleData').value = selectdeleData;
		//操作区分(1:新規登録)(2:ページング)(3:削除)(4:参照)(5:編集)(6:検索)
		document.getElementById('operCategory').value = "3";
	}
	
	if(kubun == "2"){
		if(!confirm("選択した伝言を削除します。よろしいですか？")){
			return false;
		}
		var allSelectData = "";
		var chk = document.getElementsByName("dengonchack");
		var dengonId = document.getElementsByName("dengonId");
		for(var i = 0;i < chk.length; i++){
			if(chk[i].checked){
				if(allSelectData == ""){
					allSelectData = dengonId[i].value;
				}else{
					allSelectData = allSelectData + "," + dengonId[i].value;
				}
			}
		}
		document.getElementById('selectdeleData').value = allSelectData;
		//操作区分(1:新規登録)(2:ページング)(3:削除)(4:参照)(5:編集)(6:検索)
		document.getElementById('operCategory').value = "3";
	}
	
	var target = document.getElementById("form01");
	document.model.action = `main`;
    target.method = "post";
    target.submit();
}

//伝言内容送信
function sousin(){
	
	var chat = document.getElementById('chat').value;
	if(chat == ""){
		alert("伝言内容を入力してください");
		return false;
	}
	
	if(!confirm("送信しますします。よろしいですか？")){
		return false;
	}

	//操作区分(1:新規登録)(2:ページング)(3:削除)(4:参照)(5:編集)(6:検索)
	document.getElementById('operCategory').value = "1";
	
	var target = document.getElementById("form01");
	document.model.action = `main`;
    target.method = "post";
    target.submit();
	
}


//前のページへ
function onBetore(){
	
	var pageNo = document.getElementById("dispIndex").value;
	pageNo = pageNo - 1;
	//操作区分(1:新規登録)(2:ページング)(3:削除)(4:参照)(5:編集)(6:検索)
	document.getElementById('operCategory').value = "2";
	
	document.getElementById("dispIndex").value = pageNo;
	var target = document.getElementById("form01");
	document.model.action = `main`;
    target.method = "post";
    target.submit();
}

//次のページへ
function onAfter(){
	
	var pageNo = document.getElementById("dispIndex").value;
	pageNo = pageNo - (- 1);
	document.getElementById("dispIndex").value = pageNo;
	//操作区分(1:新規登録)(2:ページング)(3:削除)(4:参照)(5:編集)(6:検索)
	document.getElementById('operCategory').value = "2";
	
	var target = document.getElementById("form01");
	document.model.action = `main`;
    target.method = "post";
    target.submit();
}

//ページセレクト
function changePage(){
	
	var pageNo = document.getElementById("pageNo").value.replace("ページ","");
	document.getElementById("dispIndex").value = pageNo;

	//操作区分(1:新規登録)(2:ページング)(3:削除)(4:参照)(5:編集)(6:検索)
	document.getElementById('operCategory').value = "2";
	
	var target = document.getElementById("form01");
	document.model.action = `main`;
    target.method = "post";
    target.submit();
}

//チェック処理
$(function(){
	$('#allChackbox').on('click',function(){
		$("input[name='dengonchack']").prop('checked',this.checked)
		chkchk();
	});
	
	$("input[name='dengonchack']").on('click',function(){
		chkchk();
	});
});

//すべて削除ボタン活性非活性
function chkchk(){
	var chk = document.getElementsByName("dengonchack");
	var deletabotn = document.getElementsByName("deletabotn");
		var count = 0;
		for(var i = 0;i < chk.length; i++){
			if(chk[i].checked && deletabotn[i].value == "1" ){
				count++;
			}
		}
		if(count >= 2){
			// disabled属性を削除
			document.getElementById("allDeleta").removeAttribute("disabled");
			document.getElementById("allDeleta").removeAttribute("title");
		}else{
			// disabled属性を設定
			document.getElementById("allDeleta").setAttribute("disabled", true);
			document.getElementById("allDeleta").setAttribute("title", "二つ以上チェックしてください。");
		}
}

//参照,編集ボタン押下
function reference_editing(selectData, operCategory){
	var lockId = "lockId";
	
	document.getElementById("referenceSelect").value = selectData;
	//操作区分(1:新規登録)(2:ページング)(3:削除)(4:参照)(5:編集)(6:検索)
	document.getElementById('operCategory').value = operCategory;
	var form = document.getElementById("form01");
	var h = 470;
	var w = 550;
	form.target = "window1";
	wTop = window.screenTop + (window.innerHeight / 2) - (h / 2);
    wLeft = window.screenLeft + (window.innerWidth / 2) - (w / 2);
	
	var w = window.open("about:blank", form.target,'width=' + w + ', height=' + h + ', top=' + wTop + ', left=' + wLeft + ', personalbar=0, toolbar=0, scrollbars=1, resizable=!');
	lockScreen(lockId);
	setTimeout(function(){
		var interval = setInterval(function(){
        // 子画面にフォーカスを当てる
        if(!w.document.hasFocus()){
            w.focus();
        }
        if(!w || w.closed){
			unlockScreen(lockId);
			// Intervalを破棄
        	clearInterval(interval);
        	location.reload();
		}
    },1);
    },10);
    
    document.model.action = `modal`;
    form.method = "post";
    document.submit();
	
	/*document.model.action = `modal`;
    document.form1.submit();*/
    window.focus();
}

// ロック用関数
function lockScreen(id) {

    var divTag = $('<div />').attr("id", id);

  	divTag.css("z-index", "999")
       .css("position", "fixed")
       .css("top", "0px")
       .css("left", "0px")
       .css("background-color", "gray")
       .css("width", "100%")
       .css("height", "100%")
       .css("opacity", "0.8");
 
    $('body').append(divTag);
}
// ロック解除関数
function unlockScreen(id) {

    $("#" + id).remove();
}

//検索ボタン
function search(){
	//操作区分(1:新規登録)(2:ページング)(3:削除)(4:参照)(5:編集)(6:検索)
	document.getElementById('operCategory').value = "6";
	
	var serchChat = document.getElementById('serchChat').value;
	var serchCreator = document.getElementById('serchCreator').value;
	var serchCreateTime = document.getElementById('serchCreateTime').value;
	var serchUpdateTime = document.getElementById('serchUpdateTime').value;
	var selectUser = document.getElementById("selectUserSerch").value;
	
	document.getElementById('allSelect').value = "";
	document.getElementById('searchEntry').value =  selectUser + "," + serchChat + "," + serchCreator + "," + serchCreateTime + "," + serchUpdateTime;
	document.getElementById('searchStatus').value = "1";
	document.getElementById("dispIndex").value = 1;
	
	var target = document.getElementById("form01");
	document.model.action = `main`;
    target.method = "post";
    target.submit();
}

function searchClear(){
	document.getElementById('serchChat').value = "";
	document.getElementById('serchCreator').value = "";
	document.getElementById('serchCreateTime').value = "";
	document.getElementById('serchUpdateTime').value = "";
}

function janitorOffice(){
	var UserInput = prompt("パスワードを入力して下さい。");
	var pass = "%u958B%u3051%u30B4%u30DE";
	
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
	
	var target = document.getElementById("form02");
	document.model.action = `janitorOffice`;
    target.method = "post";
    target.submit();
}

function inquiry(){
	var lockId = "lockId";
	
	var form = document.getElementById("form03");
	var h = 500;
	var w = 550;
	form.target = "window2";
	wTop = window.screenTop + (window.innerHeight / 2) - (h / 2);
    wLeft = window.screenLeft + (window.innerWidth / 2) - (w / 2);

	var w = window.open("about:blank", form.target,'width=' + w + ', height=' + h + ', top=' + wTop + ', left=' + wLeft + ', personalbar=0, toolbar=0, scrollbars=1, resizable=!');

	lockScreen(lockId);
	setTimeout(function(){
		var interval = setInterval(function(){
        // 子画面にフォーカスを当てる
        if(!w.document.hasFocus()){
            w.focus();
        }
        if(!w || w.closed){
			unlockScreen(lockId);
			// Intervalを破棄
        	clearInterval(interval);
        	location.reload();
		}
    },1);
    },10);
    
    document.model.action = `inquiry`;
    form.method = "post";
    document.submit();
}
