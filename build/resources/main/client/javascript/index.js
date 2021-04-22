console.log("linked");
window.onload = setupeventhandler;

function setupeventhandler(){
    let loginForm = document.getElementById("mainlogin");
    let submitForm = document.getElementById("loginform");
    submitForm.addEventListener("submit", login);
}

function test(){
    
    console.log("handler trigger");
}
 function login(form){
    form.preventDefault();
    //submitForm.addEventListener("submit", test)
    console.log("trying to access server");
    console.log(form);
    let username = document.getElementById("username");
    let password = document.getElementById("password");
    console.log(username.value, password.value);

    fetch("http://localhost:6969/login", {
        method : 'POST',
        body:  JSON.stringify({

            username : username.value,
            password : password.value
        })
    })
            .then(
                function(response){
                    console.log(response);
                    const responsePayload = response;
                    return responsePayload;
                }
            ).catch(
                (error) => {console.log("broken");}
            )

}

