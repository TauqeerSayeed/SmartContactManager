console.log("Script loaded ");

let currentTheme=getTheme();

document.addEventListener("DOMContentLoaded", () => {
  changeTheme();
});

//todoj
function changeTheme(){
    //set webpage
    changePageTheme(currentTheme,"");

    //set lsitener to change theme buttom
    const changeThemeButton= document.querySelector("#theme_change_button");
//    changeThemeButton.querySelector('span').textContent =
//         currentTheme == "light" ? "Dark" : "Light";

    changeThemeButton.addEventListener('click', (event)=>{
    let oldTheme=currentTheme;
    console.log("button pressed");
    if(currentTheme === "dark"){
        currentTheme="light";
    }else{
        currentTheme="dark";
    }
    changePageTheme(currentTheme,oldTheme);
   });
}


//set theme to the localStorage
function setTheme(theme){
    localStorage.setItem("theme",theme);
}

//get theme from localStorage

function getTheme(){
let theme= localStorage.getItem("theme");
return theme ? theme : "light";
}

function changePageTheme(theme,oldTheme){
    //update theme in  localstorage
        setTheme(currentTheme);

        //to remove current theme
        if(oldTheme){
        document.querySelector("html").classList.remove(oldTheme);
        }
        //set the current theme
        document.querySelector("html").classList.add(theme);

        //change the text of button
            document
            .querySelector("#theme_change_button")
            .querySelector('span').textContent = theme == "light" ? "Dark" : "Light";
}