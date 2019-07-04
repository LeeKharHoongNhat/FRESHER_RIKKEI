function loadSinglePage(maDuAn) {
//goi den 1 duong dan tra ve data
	$.ajax({
		type : "GET",
		url : "/getDataTable?maDuAn=" + maDuAn,
		cache : false,
		success : function(data) {
			// console.log(data);
			// truyen data vao id content-table
			$("#content-table").html(data);

		}
	});
}
// khi nao trang load xong se goi ham nay
$(document).ready(function() {
	// xu ly khi ma du an dc truyen xuong
	if(mda != ''){
		$("#maDuAnselect").val(mda);
	}
	// goi ma du an
	var maDuAn = $("#maDuAnselect").val();
	// load trang khi chua chon listbox
	loadSinglePage(maDuAn);
	// truyen ma du an cho input hiden
	$("#maDuAnHiden").val(maDuAn);
	// su kien khi list box dc chon
	$("#maDuAnselect").change(function() {
		// truyen lai ma du an khi chon lai list box
		$("#maDuAnHiden").val(this.value);
		// load lai page khi chon list box
		loadSinglePage(this.value);
	});
	
	
});