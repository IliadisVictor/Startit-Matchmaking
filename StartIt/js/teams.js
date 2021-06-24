let clicked = false;
let clicked1 = false;
function showDiv() {
  if (!clicked) {
    document.getElementById("filters").style.display = "flex";
    document.getElementById("filters-button").style.background = "#5de2cc";
    document.getElementById("filters-button").style.color = "#FAFAFA";
    document.getElementById("filter-img").src = "images/filters-icon1.svg";

    document.getElementById("matchmaking-popup").style.display = "none";
    clicked1 = false;
    clicked = true;
  } else {
    document.getElementById("filters").style.display = "none";
    document.getElementById("filters-button").style.background = "#13131a";
    document.getElementById("filters-button").style.color = "#5de2cc";
    document.getElementById("filter-img").src = "images/filters-icon.svg";
    clicked = false;
  }
}

function matchmaking() {
  if (!clicked1) {
    document.getElementById("matchmaking-popup").style.display = "flex";

    document.getElementById("filters").style.display = "none";
    document.getElementById("filters-button").style.background = "#13131a";
    document.getElementById("filters-button").style.color = "#5de2cc";
    document.getElementById("filter-img").src = "images/filters-icon.svg";

    clicked = false;
    clicked1 = true;
  } else {
    document.getElementById("matchmaking-popup").style.display = "none";

    clicked1 = false;
  }
}

$(document).ready(function () {
  $(".filter").change(function () {
    filter_function();

    //calling filter function each select box value change
  });
});

// $(".main .grid .card").show(); //intially all rows will be shown

function filter_function() {
  let i = 0;
  $(".main .grid .card").hide(); //hide all rows
  var educationFlag = 0;
  var educationValue = $("#filter-Education").val();
  var ageFlag = 0;
  var ageValue = $("#filter-Age").val();
  var experienceFlag = 0;
  var experienceValue = $("#filter-Experience").val();
  var statusFlag = 0;
  var statusValue = $("#filter-Status").val();
  if (statusValue == null) {
    statusValue = 0;
  }
  var ageValueFinal = 0;
  var experienceValueFinal = 0;
  var educationValueFinal = 0;

  if (educationValue) {
    if (educationValue.length > 1) {
      if (
        educationValue[0] != educationValue[educationValue.length - 1] &&
        educationValue.length > 1
      ) {
        if (
          educationValue[0] == "Bachelor" &&
          educationValue[educationValue.length - 1] == "Master"
        ) {
          educationValueFinal = "Bachelor-Master";
        } else if (
          educationValue[0] == "Bachelor" &&
          educationValue[educationValue.length - 1] == "Phd"
        ) {
          educationValueFinal = "Bachelor-Phd";
        } else if (
          educationValue[0] == "Master" &&
          educationValue[educationValue.length - 1] == "Phd"
        ) {
          educationValueFinal = "Master-Phd";
        }
      }
    } else {
      educationValueFinal = educationValue[0];
    }
  }
  if (ageValue) {
    if (ageValue.length > 1) {
      if (ageValue[0] != ageValue[ageValue.length - 1] && ageValue.length > 1) {
        if (
          ageValue[0] == "18-24" &&
          ageValue[ageValue.length - 1] == "25-35"
        ) {
          ageValueFinal = "18-35";
        } else if (
          ageValue[0] == "18-24" &&
          ageValue[ageValue.length - 1] == "35+"
        ) {
          ageValueFinal = "18-35+";
        } else if (
          ageValue[0] == "25-35" &&
          ageValue[ageValue.length - 1] == "35+"
        ) {
          ageValueFinal = "25-35+";
        }
      }
    } else {
      ageValueFinal = ageValue[0];
    }
  }

  if (experienceValue) {
    if (experienceValue.length > 1) {
      if (
        experienceValue[0] != experienceValue[experienceValue.length - 1] &&
        experienceValue.length > 1
      ) {
        if (experienceValue[0] == "0 years exp.") {
          if (experienceValue[experienceValue.length - 1] == "1-2 years exp.") {
            experienceValueFinal = "0-2 years exp.";
          } else if (
            experienceValue[experienceValue.length - 1] == "3-4 years exp."
          ) {
            experienceValueFinal = "0-4 years exp.";
          } else if (
            experienceValue[experienceValue.length - 1] == "5-10 years exp."
          ) {
            experienceValueFinal = "0-10 years exp.";
          } else if (
            experienceValue[experienceValue.length - 1] == "10+ years exp."
          ) {
            experienceValueFinal = "0-10+ years exp.";
          }
        } else if (experienceValue[0] == "1-2 years") {
          if (experienceValue[experienceValue.length - 1] == "3-4 years exp.") {
            experienceValueFinal = "1-4 years exp.";
          } else if (
            experienceValue[experienceValue.length - 1] == "5-10 years exp."
          ) {
            experienceValueFinal = "1-10 years exp.";
          } else if (ageValue[experienceValue.length - 1] == "10+ years exp.") {
            experienceValueFinal = "1-10+ years exp.";
          }
        } else if (experienceValue[0] == "3-4 years exp.") {
          if (
            experienceValue[experienceValue.length - 1] == "5-10 years exp."
          ) {
            experienceValueFinal = "3-10 years exp.";
          } else if (
            experienceValue[experienceValue.length - 1] == "10+ years exp."
          ) {
            experienceValueFinal = "3-10+ years exp.";
          }
        } else if (experienceValue[0] == "5-10 years exp.") {
          if (experienceValue[experienceValue.length - 1] == "10+ years exp.") {
            experienceValueFinal = "5-10+ years exp.";
          }
        }
      }
    } else {
      experienceValueFinal = experienceValue[0];
    }
  }

  //setting intial values and flags needed

  //traversing each row one by one
  $(".card").each(function () {
    if (educationValueFinal == 0) {
      //if no value then display row
      educationFlag = 1;
    } else if (
      educationValueFinal == $(this).find(".education").data("education")
    ) {
      educationFlag = 1; //if value is same display row
    } else {
      educationFlag = 0;
    }
    if (ageValueFinal == 0) {
      //if no value then display row
      ageFlag = 1;
    } else if (ageValueFinal == $(this).find(".age").data("age")) {
      ageFlag = 1; //if value is same display row
    } else {
      ageFlag = 0;
    }
    if (experienceValueFinal == 0) {
      //if no value then display row
      experienceFlag = 1;
    } else if (
      experienceValueFinal == $(this).find(".experience").data("experience")
    ) {
      experienceFlag = 1; //if value is same display row
    } else {
      experienceFlag = 0;
    }

    if (statusValue == 0) {
      //if no value then display row
      statusFlag = 1;
    } else if (
      statusValue == $(this).find(".status").data("status") ||
      statusValue.length > 1
    ) {
      statusFlag = 1; //if value is same display row
    } else {
      statusFlag = 0;
    }

    if (educationFlag && experienceFlag && ageFlag && statusFlag) {
      $(this).show(); //displaying row which satisfies all conditions
    }
  });
}

$(document).ready(function () {
  $(".js-example-basic-multiple").select2();
  $(".js-example-basic-single").select2();
  $(".js-example-placeholder-multiple-Age").select2({
    placeholder: " Age",
    maximumSelectionLength: 2,
  });
  $(".js-example-placeholder-multiple-Education").select2({
    placeholder: " Education",
    maximumSelectionLength: 2,
  });
  $(".js-example-placeholder-multiple-Experience").select2({
    placeholder: " Experience",
    maximumSelectionLength: 2,
  });
  $(".js-example-placeholder-multiple-Status").select2({
    placeholder: " Status",
    maximumSelectionLength: 2,
  });
  $(".card .top").each(function () {
    if (jQuery(this).find("#members").text() == "0 Members") {
      console.log(jQuery(this).find(".age")[0].outerText);
      jQuery(this).find(".age")[0].outerText = " ";
      jQuery(this).find(".education")[0].outerText = " ";
      jQuery(this).find(".experience")[0].outerText = " ";
      this.style.margin = "0 0 38px 0";
    }
  });  
});
