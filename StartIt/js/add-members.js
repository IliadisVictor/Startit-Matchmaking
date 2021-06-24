
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
      $(this).show(); //displaying row which satisfies all conditions
    }
  });
}


$("tbody tr").each(function () {
  if (jQuery(this).find("td.Diversity")[0].outerText < 0) {
    jQuery(this).find("td.Diversity img")[0].src =
      "images/NegativeDiversity.svg";
  }
});

function sortTable() {
  var table, rows, switching, i, x, y, shouldSwitch;
  table = document.getElementById("tableData");
  switching = true;
  /*Make a loop that will continue until
  no switching has been done:*/
  while (switching) {
    //start by saying: no switching is done:
    switching = false;
    rows = table.rows;
    /*Loop through all table rows (except the
    first, which contains table headers):*/
    for (i = 0; i < rows.length - 1; i++) {
      //start by saying there should be no switching:
      shouldSwitch = false;
      /*Get the two elements you want to compare,
      one from current row and one from the next:*/
      x = jQuery(rows[i]).find("td.Diversity")[0].outerText;
      y = jQuery(rows[i + 1]).find("td.Diversity")[0].outerText;
      //check if the two rows should switch place:
      if (x < y) {
        //if so, mark as a switch and break the loop:
        shouldSwitch = true;
        break;
      }
    }
    if (shouldSwitch) {
      /*If a switch has been marked, make the switch
      and mark that a switch has been done:*/
      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
      switching = true;
    }
  }
}

sortTable();


