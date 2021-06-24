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
    console.log("yoooo");

    if (studyfieldFlag) {
      $(this).show(); //displaying row which satisfies all conditions
    }
  });
}
