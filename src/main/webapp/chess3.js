var isSelected = false;
function selectPiece(td, pos) {
  if (!isSelected) {
    document.getElementById('from').value = pos;
    td.className = "chosen";
  }
  isSelected = true;
}
function selectDest(pos) {
  if (isSelected) {
    document.getElementById('to').value = pos;
    document.forms[0].submit();
  }
}

function reloadIfOpponentMoved(oldColor) {
  $.get("/nextColor.jsp", function(data) {
    if (oldColor != data) {
      location.href = "/";
    }
  });
}

function showTime() {
  time = parseInt($('#time').text());
  $('#time').text(''+(time+1));
}

var myVar = setInterval(function(){showTime()}, 1000);


