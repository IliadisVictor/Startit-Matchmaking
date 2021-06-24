//declearing html elements

const imgDiv = document.querySelector('.profile-pic-div');
const img = document.querySelector('#photo');
const file = document.querySelector('#file');
const submit = document.querySelector('#submit')
const uploadBtn = document.querySelector('#uploadBtn');
const uploadBtn1 = document.querySelector('#submit');
const flag = false
//if user hover on img div 

imgDiv.addEventListener('mouseenter', function(){
    
    uploadBtn.style.display = "block";
    
});

//if we hover out from img div

imgDiv.addEventListener('mouseleave', function(){
    uploadBtn.style.display = "none";
});

//when we choose a foto to upload

file.addEventListener('change', function(){
    const choosedFile = this.files[0];
    if (choosedFile) {

        const reader = new FileReader(); 

        reader.addEventListener('load', function(){
            img.setAttribute('src', reader.result);
        });

        reader.readAsDataURL(choosedFile);
        
    }
});


imgDiv.addEventListener('mouseenter', function(){
    
    uploadBtn1.style.display = "block";
    
});

//if we hover out from img div

imgDiv.addEventListener('mouseleave', function(){
    uploadBtn1.style.display = "none";
});


