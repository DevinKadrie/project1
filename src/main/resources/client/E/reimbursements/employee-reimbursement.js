window.onload = async () => {
    const response = await fetch("/api/reimbursements");
    const data = await response.json();
    console.log(data);
    // DOM manipulate
    let tablebody = document.getElementById("rTableBody");
    let cell;
    let text;

    logoutButton = document.getElementById("logout");
    logoutButton.addEventListener("click", async () =>{
        result = await fetch("/logout");
        window.location.href = "/index.html";
    });
    data.forEach(element => {
        console.log(element);
        let newRow = tablebody.insertRow(-1);
        //console.log(positionMap);

        cell = newRow.insertCell(-1);
        text = document.createTextNode(element.amount);
        cell.appendChild(text);

        cell = newRow.insertCell(-1);
        text = document.createTextNode(element.submitted);
        cell.appendChild(text);

        cell = newRow.insertCell(-1);
        text = document.createTextNode(element.resolved);
        cell.appendChild(text);

        cell = newRow.insertCell(-1);
        text = document.createTextNode(element.status);
        cell.appendChild(text);

        cell = newRow.insertCell(-1);
        text = document.createTextNode(element.type);
        cell.appendChild(text);

        cell = newRow.insertCell(-1);
        text = document.createTextNode(element.description);
        cell.appendChild(text);
        

    });
    let newButton = document.getElementById("newReimButton");
    newButton.addEventListener("click", () =>{

        newButton.style.visibility = "hidden";
        //dom manip a form that when submitted reloads the page and adds the reimbursement to the DB
        let newDiv = document.getElementById("newReimDiv");
        let newForm = document.createElement("form");
        newForm.setAttribute('method', "post");
        newForm.setAttribute('action', "/api/reimbursements");

        //amount
            //div
            let innerDiv = document.createElement("div");
            innerDiv.setAttribute("class", "mb-3");
            
            //label
            let newLabel = document.createElement("label");
            newLabel.setAttribute("class", "form-label");
            newLabel.setAttribute("for", "amount");
            newLabel.innerText = "amount";
            //input
            let newTextInput = document.createElement("input");
            newTextInput.setAttribute("type", "text");
            newTextInput.setAttribute("name", "amount");
            newTextInput.setAttribute("class", "form-control");
            newTextInput.setAttribute("id", "amount");

            innerDiv.appendChild(newLabel);
            innerDiv.appendChild(newTextInput);

            newForm.appendChild(innerDiv);

            newDiv.appendChild(newForm);
        //type
            //div
            innerDiv = document.createElement("div");
            innerDiv.setAttribute("class", "mb-3");
            
            //label
            newLabel = document.createElement("label");
            newLabel.setAttribute("class", "form-label");
            newLabel.setAttribute("for", "type");
            newLabel.innerText = "type";
            //input
            newTextInput = document.createElement("input");
            newTextInput.setAttribute("type", "text");
            newTextInput.setAttribute("name", "type");
            newTextInput.setAttribute("class", "form-control");
            newTextInput.setAttribute("id", "type");

            innerDiv.appendChild(newLabel);
            innerDiv.appendChild(newTextInput);

            newForm.appendChild(innerDiv);

            newDiv.appendChild(newForm);
        //description
            //div
            innerDiv = document.createElement("div");
            innerDiv.setAttribute("class", "mb-3");
            
            //label
            newLabel = document.createElement("label");
            newLabel.setAttribute("class", "form-label");
            newLabel.setAttribute("for", "description");
            newLabel.innerText = "description";
            //input
            newTextInput = document.createElement("input");
            newTextInput.setAttribute("type", "text");
            newTextInput.setAttribute("name", "description");
            newTextInput.setAttribute("class", "form-control");
            newTextInput.setAttribute("id", "description");

            innerDiv.appendChild(newLabel);
            innerDiv.appendChild(newTextInput);
            newForm.appendChild(innerDiv);

            


        let submitButton = document.createElement("button");
        submitButton.setAttribute("type", "submit");
        submitButton.setAttribute("class", "btn btn-primary");
        submitButton.setAttribute("id", "newSubmitButton");
        submitButton.setAttribute("value", "submit request");
        submitButton.innerText = "submit request";
        newForm.appendChild(submitButton);
        newDiv.appendChild(newForm);
    });

    
    
}
