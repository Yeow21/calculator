function createContainer(operator) {
    const operatorContainer = document.createElement('span');
    operatorContainer.textContent = operator.letter;
    if (operator.coverRejected) {
        
        operatorContainer.classList.add('rejected');
    }

    if (operator.coverConfirmed) { 
        operatorContainer.classList.add('confirmed');
    }

    return operatorContainer;
}
function sortShiftsByDate(shifts) {
    return shifts.sort((a, b) => new Date(a.date) - new Date(b.date));
}



async function fetchOvertimeShifts() {
    try {
        const response = await fetch('http://localhost:8082/overtime');
        const data = await response.json();

        let overtimeShifts = data._embedded.overtimeShiftList;
        let crossDeskLetter = '';

        // Clear existing table rows
        const operatorTableBody = document.querySelector('#operator-table tbody');
        const officerTableBody = document.querySelector('#officer-table tbody');
        operatorTableBody.innerHTML = '';
        officerTableBody.innerHTML = '';
        overtimeShifts = sortShiftsByDate(overtimeShifts);
        overtimeShifts.forEach(shift => {
            console.log(shift);
            const row = document.createElement('tr');

            const idCell = document.createElement('td');
            idCell.textContent = shift.id;
            row.appendChild(idCell);

            const dateCell = document.createElement('td');
            dateCell.textContent = shift.date;
            row.appendChild(dateCell);

            const deskSideCell = document.createElement('td');
            deskSideCell.textContent = shift.deskSide;
            row.appendChild(deskSideCell);

            const letterCell = document.createElement('td');
            letterCell.textContent = shift.letter;
            row.appendChild(letterCell);
            

            const operatorsListCell = document.createElement('td');
            const officersListCell = document.createElement('td');
            const mordoListCell = document.createElement('td');
            const crossDeskCell = document.createElement('td');

            // adding mordos -----------------------------------------------------------
            shift.operatorList.forEach(operator => {
                if(operator.mordo && !operator.crossDesk) {
                    mordoListCell.appendChild(createContainer(operator))
                    mordoListCell.appendChild(document.createTextNode(', '))
                }
            });
            if (mordoListCell.lastChild) {
                mordoListCell.removeChild(mordoListCell.lastChild);
            }
            
            // adding operators ---------------------------------------------------------
            shift.operatorList.forEach(operator => {
                operatorsListCell.appendChild(createContainer(operator));
                operatorsListCell.appendChild(document.createTextNode(', '));
            });
            if (operatorsListCell.lastChild) {
                operatorsListCell.removeChild(operatorsListCell.lastChild);
            }



            // Filtering the cross desk --------------------------------------------------
            shift.operatorList.forEach(operator => {
                if (operator.crossDesk) {
                    crossDeskCell.appendChild(createContainer(operator))
                    console.log("Cross desk! " + operator.crossDesk);

                    //used later to filter the cross desk operator from operatorsListCell
                    crossDeskLetter = operator.letter
                    console.log("Cross desk letter = " + crossDeskLetter)
                }
            })
            

            // adding officers ------------------------------------------------------------
            shift.officerList.forEach(officer => {
                officersListCell.appendChild(createContainer(officer));
                officersListCell.appendChild(document.createTextNode(', '))
            });
            if (officersListCell.lastChild) {
                officersListCell.removeChild(officersListCell.lastChild);
            }


  
            
            // add each container to the row ----------------------------------------------
            if (shift.deskSide === 'operator') {
                row.appendChild(operatorsListCell);
                row.appendChild(officersListCell);
                operatorTableBody.appendChild(row);
            }







            // todo: Work here
            if (shift.deskSide === 'officer') {                
                mordoListCell.childNodes.forEach(mordoNode => {
                    if (mordoNode.textContent !== ', ') {
                        let previousWasRemoved = false;
                        const nodesToRemove = [];
                        let lastValidNode = null;
                        
                        // First pass - mark nodes for removal
                        operatorsListCell.childNodes.forEach((operatorNode, index) => {
                            console.log("OPERATOR NODE: " + operatorNode.textContent)
                            if (operatorNode.textContent.includes(mordoNode.textContent) || operatorNode.textContent.includes(crossDeskLetter)) {
                                console.log("YES - OPERATOR NODE " + operatorNode.textContent);
                                nodesToRemove.push(operatorNode);
                                previousWasRemoved = true;
                            } else if (operatorNode.textContent === ', ' && previousWasRemoved) {
                                nodesToRemove.push(operatorNode);
                            } else {
                                previousWasRemoved = false;
                                if (operatorNode.textContent !== ', ') {
                                    lastValidNode = operatorNode;
                                }
                            }
                        });
                        
                        // Remove marked nodes
                        nodesToRemove.forEach(node => console.log("Deleting node " + node.textContent));
                        nodesToRemove.forEach(node => operatorsListCell.removeChild(node));
                        
                        // Clean up trailing commas after all removals
                        let lastNode = operatorsListCell.lastChild;
                        while (lastNode && lastNode.textContent === ', ') {
                            operatorsListCell.removeChild(lastNode);
                            lastNode = operatorsListCell.lastChild;
                        }
                    }
                });

                row.appendChild(officersListCell);
                row.appendChild(crossDeskCell);
                row.appendChild(mordoListCell);
                row.appendChild(operatorsListCell);

                officerTableBody.appendChild(row);                 
            }
            
            // add the confirm availability cell functionality
            const confirmListCell = document.createElement('td');
            confirmListCell.appendChild(addConfirmAvailabilityForm(shift.operatorList, shift.officerList, shift.id));
            row.appendChild(confirmListCell);
        });
    } catch (error) {
        console.error('Error fetching overtime shifts:', error);
    }
}
async function addOvertimeShift(event) {
    event.preventDefault();

    const date = document.querySelector('#date').value;
    const deskSide = document.querySelector('#deskSide').value;
    const letter = document.querySelector('#letter').value;
    console.log(date, deskSide, letter);

    const newShift = {
        date: date,
        deskSide: deskSide,
        letter: letter,
        officerList: [],
        operatorList: []
    };

    try {
        const response = await fetch('http://localhost:8082/overtime', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newShift)
        });

        if (response.ok) {
            const jsonResponse = await response.json(); // Parse the JSON response
            console.log("json response " + jsonResponse);
            fetchOvertimeShifts(); // Refresh the tables
            document.querySelector('#overtime-form').reset(); // Clear form fields
        } else {
            console.error('Error adding overtime shift:', response.statusText);
        }
    } catch (error) {
        console.error('Error adding overtime shift:', error);
    }
}

async function updateOvertimeShift(event) {
    event.preventDefault();
    const form = event.target;
    const submitter = event.submitter;
    const roleSelect = form.querySelector('#roleSelect').value;
    const letter = form.querySelector('#letterSelect').value;
    const shiftId = form.querySelector('#shiftId').value;

    const updateShiftDetails = {
        shiftId: shiftId, 
        submitter: submitter.id,
        deskSide: roleSelect,
        letter: letter,
    }

    try {
        const response = await fetch(`http://localhost:8082/overtime/update/${shiftId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updateShiftDetails)
        });
        if (response.ok) {
            const jsonResponse = await response.json(); // Parse the JSON response
            console.log("json response " + jsonResponse); // Log the JSON response
            fetchOvertimeShifts();
            form.reset();
            console.log("THE RESPONSE IS " + response);
        } else {
            console.error('Error updating overtime shift:', response.statusText);
        }
    } catch (error) {
        console.error('error updating overtime shift', error);
    }
}

async function deleteOvertimeShift(event) {
    event.preventDefault();

    const deleteId = document.querySelector('#deleteId').value;

    try {
        const response = await fetch(`http://localhost:8082/overtime/${deleteId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            fetchOvertimeShifts(); // Refresh the tables
            document.querySelector('#delete-overtime-form').reset(); // Clear form fields
        } else {
            console.error('Error deleting overtime shift:', response.statusText);
        }
    } catch (error) {
        console.error('Error deleting overtime shift:', error);
    }
}

function addConfirmAvailabilityForm(operatorShiftList, officerShiftList, shiftId) {
    const form = document.createElement('form');
    form.method = "POST";
    form.id = "update-overtime-shift";
    
    const formContainer = document.createElement('div')
    formContainer.id='update-shift-form';
    form.appendChild(formContainer);

    const hiddenId = document.createElement('input');
    hiddenId.type = 'hidden'; // Setting the type to 'hidden'
    hiddenId.id = 'shiftId';
    hiddenId.value = shiftId;
    formContainer.appendChild(hiddenId);

    // role select input --------------------------------------------
    const roleSelect = document.createElement('select');
    roleSelect.name = 'roleSelect';
    roleSelect.id = 'roleSelect';
    

    // Create and add the default option
    const defaultOption = document.createElement('option');
    defaultOption.value = '';
    defaultOption.textContent = 'select option';
    defaultOption.disabled = true;
    defaultOption.selected = true;
    roleSelect.appendChild(defaultOption);

    const operatorOption = document.createElement('option');
    operatorOption.value = 'operator';
    operatorOption.textContent = 'Operator';
    roleSelect.appendChild(operatorOption);

    const officerOption = document.createElement('option');
    officerOption.value = 'officer';
    officerOption.textContent = 'Officer';
    roleSelect.appendChild(officerOption);

    // Append role select element to the form
    formContainer.appendChild(roleSelect);

    // letter select input ------------------------------------------            
    // Create and append the letterSelect element to the form
    const letterSelect = document.createElement('select');
    letterSelect.name = 'letterSelect';
    letterSelect.id = 'letterSelect';
    formContainer.appendChild(letterSelect);

    // Event listener to handle role select changes
    roleSelect.addEventListener('change', function() {
        letterSelect.innerHTML = '';
        console.log(roleSelect.value);
        if (roleSelect.value === 'operator') {
            console.log("true");
            console.log(operatorShiftList);
            operatorShiftList.forEach(function(item) {
                console.log("item = " + item);
                const option = document.createElement('option');
                console.log(item)
                option.value = item.letter;
                option.textContent = item.letter;
                letterSelect.appendChild(option);
            });
        } else if (roleSelect.value === 'officer') {

            officerShiftList.forEach(function(item) {
                const option = document.createElement('option');
                option.value = item.letter;
                option.textContent = item.letter;
                letterSelect.appendChild(option);
            });
        }
    });

    const confirmButton = document.createElement('button');
    confirmButton.type = 'submit';
    confirmButton.name = 'confirm';
    confirmButton.id = 'confirmButton';
    confirmButton.value = 'confirm';
    confirmButton.textContent='Confirm';
    formContainer.appendChild(confirmButton);

    const rejectButton = document.createElement('button');
    rejectButton.type = 'submit';
    rejectButton.name = 'confirm';
    rejectButton.id = 'rejectButton';
    rejectButton.value = 'reject';
    rejectButton.textContent = 'Reject'; // Add text content
    formContainer.appendChild(rejectButton);

    const deleteButton = document.createElement('button');
    deleteButton.type = 'submit';
    deleteButton.name = 'delete';
    deleteButton.id = 'deleteButton';
    deleteButton.value = 'delete';
    deleteButton.textContent = 'Delete'; // Add text content
    formContainer.appendChild(deleteButton);

    return form;
}

document.querySelector('#overtime-form').addEventListener('submit', addOvertimeShift);
// Event delegation for update-overtime-shift form submission
document.addEventListener('submit', function(event) {
    if (event.target && event.target.id === 'update-overtime-shift') {
        updateOvertimeShift(event);
    }
});

document.addEventListener('DOMContentLoaded', (event) => {
    console.log("dom loaded...")
    document.querySelector('#overtime-form').addEventListener('submit', addOvertimeShift);
    document.querySelector('#delete-overtime-form').addEventListener('submit', deleteOvertimeShift);            
})

// Event listener to delete record
document.addEventListener('click', async function(event) {
    if (event.target && event.target.id === 'deleteButton') {
        event.preventDefault();  // Prevent form submission
        const form = event.target.closest('form');
        const shiftId = form.querySelector('#shiftId').value;
        
        try {
            const response = await fetch(`http://localhost:8082/overtime/${shiftId}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (response.ok) {
                fetchOvertimeShifts(); // Refresh the table
            } else {
                console.error('Error deleting shift:', response.statusText);
            }
        } catch (error) {
            console.error('Error deleting shift:', error);
        }
    }
});


fetchOvertimeShifts();