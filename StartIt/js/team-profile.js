var elements = document.getElementById("member-name");
var names = "";
if(elements){
for (var i = 0; i < elements.length; i++) {
  names += elements[i].name;
}
}

var initials_list = [];
$(".main ul li ").each(function () {
  var fullname = this.textContent;
  var name = fullname.split(" ").slice(0, -1).join(" ");
  var lastname = fullname.split(" ").slice(-1).join(" ");
  var initials = name.charAt(0) + "" + lastname.charAt(0);
  initials_list.push(initials);
});

let j = 1;
$(".main ul .image-name .memberImage").each(function () {
  $(this).html(initials_list[j]);
  j++;
});

console.log(j);

if (j >= 6) {
  document.getElementById("add-member").src = "images/Component 5 – 4.svg";
  document.getElementById("add-member-text").style.color = "#707070";
  document.getElementById("add-member").style.cursor = "default";
}

let clicked = false;
function edit() {
  var del = document.getElementsByClassName("delete");
  if (!clicked) {
    for (i = 0; i < del.length; i++) {
      del[i].style.display = "inline";
      clicked = true;
    }
  } else {
    for (i = 0; i < del.length; i++) {
      del[i].style.display = "none";
      clicked = false;
    }
  }
}

let clicked1 = false;
function popUp() {
  var del = document.getElementsByClassName("pop-up");
  var blur = document.getElementsByClassName("blur");
  if (clicked1) {
    for (i = 0; i < blur.length; i++) {
      blur[i].style.filter = "brightness(100%)";
    }
    for (i = 0; i < del.length; i++) {
      del[i].style.display = "none";
      clicked1 = false;
    }
  } else {
    for (i = 0; i < blur.length; i++) {
      blur[i].style.filter = "brightness(50%)";
    }
    for (i = 0; i < del.length; i++) {
      del[i].style.display = "inline";
      clicked1 = true;
    }
  }
}

$(document).ready(function () {
  $(".progress").each(function () {
    var $bar = $(this).find(".bar");
    var $val = $(this).find("span");
    var perc = parseInt($val.text(), 10);

    $({ p: 0 }).animate(
      { p: perc },
      {
        duration: 3000,
        easing: "swing",
        step: function (p) {
          $bar.css({
            transform: "rotate(" + (45 + p * 1.8) + "deg)", // 100%=180° so: ° = % * 1.8
            // 45 is to add the needed rotation to have the green borders at the bottom
          });
          $val.text(p | 0);
        },
      }
    );
  });
});

clicked2 = false;
function userprofile() {
  var blur = document.getElementsByClassName("blur");
  if (!clicked2) {
    document.getElementById("user-profile").style.display = "flex";

    for (i = 0; i < blur.length; i++) {
      blur[i].style.filter = "brightness(50%)";
    }
    clicked2 = true;
  } else {
    document.getElementById("user-profile").style.display = "none";

    for (i = 0; i < blur.length; i++) {
      blur[i].style.filter = "brightness(100%)";
    }
    clicked2 = false;
  }
}

var userprofile_fullname =
  document.getElementById("user-profile-name").textContent;
var userprofile_name = userprofile_fullname.split(" ").slice(0, -1).join(" ");
var userprofile_lastname = userprofile_fullname.split(" ").slice(-1).join(" ");
var userprofile_initials =
  userprofile_name.charAt(0) + "" + userprofile_lastname.charAt(0);
$("#user-profile-memberImage").html(userprofile_initials);
