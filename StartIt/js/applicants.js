let j = 0;
$("tbody tr").each(function () {
  j++;
});

document.getElementById("numberOfApplicants").innerHTML =
  "Showing " + j + " applicants";

let clicked = false;
function showDiv() {
  if (!clicked) {
    document.getElementById("filters").style.display = "flex";
    document.getElementById("filters-button").style.background = "#5de2cc";
    document.getElementById("filters-button").style.color = "#FAFAFA";
    document.getElementById("filter-img").src = "images/filters-icon1.svg";

    clicked = true;
  } else {
    document.getElementById("filters").style.display = "none";
    document.getElementById("filters-button").style.background = "#13131a";
    document.getElementById("filters-button").style.color = "#5de2cc";
    document.getElementById("filter-img").src = "images/filters-icon.svg";
    clicked = false;
  }
}

$(".filter").change(function () {
  filter_function();

  //calling filter function each select box value change
});

$("table tbody tr").show(); //intially all rows will be shown

function filter_function() {
  let i = 0;
  $("table tbody tr").hide(); //hide all rows
  var studyfieldFlag = 0;
  var studyfieldValue = $("#filter-Study").val();
  var educationFlag = 0;
  var educationValue = $("#filter-Education").val();
  var ageFlag = 0;
  var ageValue = $("#filter-Age").val();
  var experienceFlag = 0;
  var experienceValue = $("#filter-Experience").val();
  var statusFlag = 0;
  var statusValue = $("#filter-Status").val();

  //setting intial values and flags needed

  //traversing each row one by one
  $("tbody tr").each(function () {
    if (studyfieldValue == 0) {
      //if no value then display row
      studyfieldFlag = 1;
    } else if (studyfieldValue == $(this).find("td.Study").data("study")) {
      studyfieldFlag = 1; //if value is same display row
    } else {
      studyfieldFlag = 0;
    }

    if (educationValue == 0) {
      //if no value then display row
      educationFlag = 1;
    } else if (
      educationValue == $(this).find("td.Education").data("education")
    ) {
      educationFlag = 1; //if value is same display row
    } else {
      educationFlag = 0;
    }

    if (ageValue == 0) {
      //if no value then display row
      ageFlag = 1;
    } else if (ageValue == $(this).find("td.Age").data("age")) {
      ageFlag = 1; //if value is same display row
    } else {
      ageFlag = 0;
    }

    if (experienceValue == -1) {
      //if no value then display row
      experienceFlag = 1;
    } else if (
      experienceValue == $(this).find("td.Experience").data("experience")
    ) {
      experienceFlag = 1; //if value is same display row
    } else {
      experienceFlag = 0;
    }

    if (statusValue == 0) {
      //if no value then display row
      statusFlag = 1;
    } else if (statusValue == $(this).find("td.Status").data("status")) {
      statusFlag = 1; //if value is same display row
    } else {
      statusFlag = 0;
    }

    if (
      studyfieldFlag &&
      educationFlag &&
      ageFlag &&
      experienceFlag &&
      statusFlag
    ) {
      console.log("wtfffff");
      i++;
      $(this).show(); //displaying row which satisfies all conditions
    }
  });
  document.getElementById("numberOfApplicants").innerHTML =
    "Showing " + i + " applicants";
}
