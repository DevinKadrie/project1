window.onload = async () => {
    const response = await fetch("/api/reimbursements");
    const data = await response.json();
    console.log(data);
    // DOM manipulate
    logoutButton = document.getElementById("logout");
    logoutButton.addEventListener("click", async () =>{
        result = await fetch("/logout");
        window.location.href = "/index.html";
    });
    let tablebody = document.getElementById("rTableBody");
    let cell;
    let text;
    let acceptButton;
    let rejectButton;
    data.forEach(element => {
        console.log(element);
        let newRow = tablebody.insertRow(-1);

        cell = newRow.insertCell(-1);
        text = document.createTextNode(element.amount);
        cell.appendChild(text);

        cell = newRow.insertCell(-1);
        text = document.createTextNode(element.submitted);
        cell.appendChild(text);

        if(element.resolved == null){
            cell = newRow.insertCell(-1);
            text = document.createTextNode("unresolved");
            cell.appendChild(text);
        }else{
            cell = newRow.insertCell(-1);
            text = document.createTextNode(element.resolved);
            cell.appendChild(text);
        }

        cell = newRow.insertCell(-1);
        text = document.createTextNode(element.status);
        cell.appendChild(text);

        cell = newRow.insertCell(-1);
        text = document.createTextNode(element.type);
        cell.appendChild(text);

        cell = newRow.insertCell(-1);
        text = document.createTextNode(element.description);
        cell.appendChild(text);

        if(element.status == "pending"){

            //accept button
            cell = newRow.insertCell(-1);
            // let container = document.createElement("form");
            // container.setAttribute("name", "container");
            // container.setAttribute("id", "buttonContainer");
            // container.setAttribute("method", "post");
            // container.setAttribute("action", "/api/reimbursements");

            let input = document.createElement("input");
            input.setAttribute("type", "button");
            input.setAttribute("id", "accept");
            input.setAttribute("class", "btn btn-success");
            input.value = "Accept";
            input.addEventListener("click", async () =>{
                console.log("accepted");
                console.log(element);
    //ajax to server for a reject
                const response = await fetch("/api/reimbursements", {
                    method: "put",
                    body: JSON.stringify({
                        r_id : `${element.r_id}`,
                        amount : `${element.amount}`,
                        description : `${element.description}`,
                        r_author : null,
                        submitted : null/*`${reimbursement.submitted}`*/,
                        type : `${element.type}`,
                        status : "accepted",
                        resolved : null,
                        r_resolver : null
                    })
                });
                window.location.reload();
            });
            // container.appendChild(input);
            cell.appendChild(input);

            //reject button
            cell = newRow.insertCell(-1);
            // container = document.createElement("form");
            // container.setAttribute("name", "container2");
            // container.setAttribute("id", "buttonContainer2");
            // container.setAttribute("method", "put");
            // container.setAttribute("action", "/api/reimbursements");

            input = document.createElement("input");
            input.setAttribute('type', "button");
            input.setAttribute("id", "reject");
            input.setAttribute("class", "btn btn-danger");
            input.value = "Reject";
            input.addEventListener("click", async () => {
                console.log("rejected");
                console.log(element);
    //ajax to server for a reject
                const response = await fetch("/api/reimbursements", {
                    method: "put",
                    body: JSON.stringify({
                        r_id : `${element.r_id}`,
                        amount : `${element.amount}`,
                        description : `${element.description}`,
                        r_author : null,
                        submitted : null/*`${reimbursement.submitted}`*/,
                        type : `${element.type}`,
                        status : "rejected",
                        resolved : null,
                        r_resolver : null
                    })
                });
                window.location.reload();
            });
            // container.appendChild(input);
            cell.appendChild(input);



            /*
                TODO:
                super losing code brain, this is what needs to happen here:
                1. create an input
                2. set it's type to be submit
                3. put it in the form
                4. make the form action to redirect to the middle page(needs to be created)
                
                -> fix the timestamp display(try converting to string before it is sent out)
                    -could be a bit of a bitch(warning)

                5. do the same for the reject button
                6. do all of this for employee, but for creating a reimbursement request.
                
                ->allow filtering by pending for managers(try just a checkbox)
                
                7. then that is MVP

                --------optional(if you actually run out of time)--------
                8. test
                9. style()
                
                --------optional(if you are too tired/ dont have enough time)--------
                10. unit tests
                11. done

                
            */
        }
    });
}

