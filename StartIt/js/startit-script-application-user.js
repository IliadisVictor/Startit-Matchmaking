const slidePage = document.querySelector(".slidePage");
const firstNextBtn = document.querySelector(".nextBtn");
const prevBtnSec = document.querySelector(".prev-1");
const nextBtnSec = document.querySelector(".next-1");
const prevBtnThird = document.querySelector(".prev-2");
const submitBtn = document.querySelector(".Submit");
const progressText = document.querySelectorAll(".step p");
const progressCheck = document.querySelectorAll(".step .check");
const bullet = document.querySelectorAll(".step .bullet");
let max = 3;
let current = 1;



firstNextBtn.addEventListener("click", function(){
    slidePage.style.marginLeft = "-33%";
    bullet[current - 1].classList.add("active");
    progressText[current - 1].classList.add("active");
    progressCheck[current - 1].classList.add("active");
    current += 1;
    return false;
});

nextBtnSec.addEventListener("click", function(){
    slidePage.style.marginLeft = "-66%";
    bullet[current - 1].classList.add("active");
    progressText[current - 1].classList.add("active");
    progressCheck[current - 1].classList.add("active");
    current += 1;
    return false;
});


submitBtn.addEventListener("click", function(){
    bullet[current - 1].classList.add("active");
    progressText[current - 1].classList.add("active");
    progressCheck[current - 1].classList.add("active");
    current += 1;
});

prevBtnSec.addEventListener("click", function(){
    slidePage.style.marginLeft = "0%";
    bullet[current - 2].classList.remove("active");
    progressText[current - 2].classList.remove("active");
    progressCheck[current - 2].classList.remove("active");
    current -= 1;
    return false;
});

prevBtnThird.addEventListener("click", function(){
    slidePage.style.marginLeft = "-33%";
    bullet[current - 2].classList.remove("active");
    progressText[current - 2].classList.remove("active");
    progressCheck[current - 2].classList.remove("active");
    current -= 1;
    return false;
});


function runScript(e) {
    if (e.keyCode == 13) {
        return false;
    }
}

function runScript1(e) {
     return false;
}


$('#languages').select2({
    placeholder: "Select your languages"
});
$('#hobbies').select2({
    placeholder: "Select your hobbies"
});
$('#skills').select2({
    placeholder: "Select your skills"
});

$(document).ready(function() {
    $('#select-gender').css('color','gray');
    $('#select-gender').change(function() {
       var current1 = $('#select-gender').val();
       if (current1 != 'null') {
           $('#select-gender').css('color','black');
       } else {
           $('#select-gender').css('color','blue');
       }
    }); 
 });
$(document).ready(function() {
    $('#select-education').css('color','gray');
    $('#select-education').change(function() {
       var current2 = $('#select-education').val();
       if (current2 != 'null') {
           $('#select-education').css('color','black');
       } else {
           $('#select-education').css('color','blue');
       }
    }); 
 });
$(document).ready(function() {
    $('#select-occupation').css('color','gray');
    $('#select-occupation').change(function() {
       var current3 = $('#select-occupation').val();
       if (current3 != 'null') {
           $('#select-occupation').css('color','black');
       } else {
           $('#select-occupation').css('color','blue');
       }
    }); 
 });

