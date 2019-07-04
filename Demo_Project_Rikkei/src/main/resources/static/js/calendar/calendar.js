//xét thời gian chạy liên tục
setInterval(function timenow() {
  var timenow = new Date();
  document.getElementById("timenow").innerHTML = timenow.getHours() + ":"
      + timenow.getMinutes() + ":" + timenow.getSeconds();
  ;
}, 1000);

$(document).ready(function() {
  // console.log('ok');
  $("#week").val(0);
  console.log($('input:hidden[name=week]').val());
  $.ajax({
    type : "GET",
    url : "/task/alltask/0/0",
    cache : false,
    success : function(data) {
      // console.log(data);
      // truyen data vao id editTask
      $("#listtask").html(data);

    }
  });
});

addTask = function() {
  $('#addTask').modal();
}

checkAllDay = function() {
  var checkBox = document.getElementById("check_all_day");
  if (checkBox.checked == true) {
    document.getElementById('start_time').type = 'date';
    document.getElementById("div_end_time").style.display = "none";

    var field = document.querySelector('#start_time');
    var date = new Date();

    // Set the date
    field.value = date.getFullYear().toString() + '-' + (date.getMonth() + 1).toString().padStart(2, 0) +
        '-' + date.getDate().toString().padStart(2, 0);
  } else {
    document.getElementById('start_time').type = 'datetime-local';
    document.getElementById("div_end_time").style.display = "block";
    $('#start_time').val(new Date().toJSON().slice(0, 19));
  }
}
editTask = function(id) {
  //console.log('id ' + id);

  // goi den 1 duong dan tra ve data
  $.ajax({
    type : "GET",
    url : "/task/getdataform/" + id,
    cache : false,
    success : function(data) {
      console.log(data);
      // truyen data vao id editTask
      $("#editTask").html(data);

    }
  });
  $('#editTask').modal();
}

count = function(count) {
  var week = 0;
  if (count == 0) {
    $("#week").val(week);
  } else {
    var number = $('input:hidden[name=week]').val()
    week = parseInt(count) + parseInt(number);
    $("#week").val(week);
  }

  // goi den 1 duong dan tra ve data
  $.ajax({
    type : "GET",
    url : "/task/alltask/0/" + week,
    cache : false,
    success : function(data) {
      // console.log(data);
      // truyen data vao id editTask
      $("#listtask").html(data);

    }
  });
}

// bật tắt input kết thúc lặp
function loopCheck(that) {
  if (that.value == "none") {
    document.getElementById("endLoopTask").disabled = true;
    document.getElementById("loopWeek").style.display = "none";
    document.getElementById('endLoopTask').type = 'date';
  } else {
    if (that.value == "week") {
      document.getElementById("endLoopTask").disabled = false;
      document.getElementById("loopWeek").style.display = "block";
    } else {
      document.getElementById("endLoopTask").disabled = false;
      document.getElementById("loopWeek").style.display = "none";
    }
  }
}

// bật tắt input người tham dự
function disabledOption1() {
  var lengthOption2 = document.getElementById("participants2").length;
  var idOption = document.getElementById("participants1").selectedIndex;
  if (idOption != 0) {

    for (i = 0; i < lengthOption2; i++) {
      document.getElementById("participants2").options[i].disabled = false;
    }

    document.getElementById("participants2").disabled = false;
    var disabled = document.getElementById("participants2").options[idOption].disabled = true;
  } else {
    document.getElementById("participants2").selectedIndex = "0";
    document.getElementById("participants3").selectedIndex = "0";
    document.getElementById("participants2").disabled = true;
    document.getElementById("participants3").disabled = true;
  }
}

// bật tắt input người tham dự
function disabledOption2() {
  var lengthOption3 = document.getElementById("participants3").length;
  var idOption1 = document.getElementById("participants1").selectedIndex;
  var idOption2 = document.getElementById("participants2").selectedIndex;
  if (idOption2 != 0) {

    for (i = 0; i < lengthOption3; i++) {
      document.getElementById("participants3").options[i].disabled = false;
    }

    document.getElementById("participants3").disabled = false;
    var disabled = document.getElementById("participants3").options[idOption1].disabled = true;
    var disabled = document.getElementById("participants3").options[idOption2].disabled = true;
  } else {
    document.getElementById("participants3").selectedIndex = "0";
    document.getElementById("participants3").disabled = true;
  }
}
