/*
pratice project as I was following the LinkedIn learning course.
HTML and CSS were provided and not written by me.
*/

const HOURHAND = document.querySelector("#hour");
const MINUTEHAND = document.querySelector("#minute");
const SECONDHAND = document.querySelector("#second");

function runClock() {
    //secPos = secPos + 6;
    //minPos = minPos + 1/10;
    //hrPos = hrPos + 1/12;

    var date = new Date();
    var hrs = date.getHours();
    var min = date.getMinutes();
    var sec = date.getSeconds();
    
    let hrPos = hrs*360/12 + (min*360/60)/12 + (sec*360/60)/60;
    let minPos = (min * 360 / 60) + (sec*360/60)/60;
    let secPos = sec * 360 / 60;
    
    HOURHAND.style.transform = "rotate(" + hrPos + "deg)";
    MINUTEHAND.style.transform = "rotate(" + minPos + "deg)";
    SECONDHAND.style.transform = "rotate(" + secPos + "deg)";
}

var interval = setInterval(runClock, 1000);



