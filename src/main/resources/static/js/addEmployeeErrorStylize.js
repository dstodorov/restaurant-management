const isHidden = elem => {
    const styles = window.getComputedStyle(elem)
    return styles.display === 'none' || styles.visibility === 'hidden'
}

//Error style for firstName input field
let firstNameErrorMessage = document.querySelector('#firstNameError');
if(!isHidden(firstNameErrorMessage)) {
    let firstNameInputField = document.querySelector('#firstName');
    firstNameInputField.style.border = '2px solid red';
}

//Error style for lastName input field
let lastNameErrorMessage = document.querySelector('#lastNameError');
if(!isHidden(lastNameErrorMessage)) {
    let lastNameInputField = document.querySelector('#lastName');
    lastNameInputField.style.border = '2px solid red';
}

//Error style for username input field
let usernameErrorMessage = document.querySelector('#usernameError');
if(!isHidden(usernameErrorMessage)) {
    let lastNameInputField = document.querySelector('#username');
    lastNameInputField.style.border = '2px solid red';
}

//Error style for password input field
let passwordErrorMessage = document.querySelector('#passwordError');
if(!isHidden(passwordErrorMessage)) {
    let passwordInputField = document.querySelector('#password');
    passwordInputField.style.border = '2px solid red';
}

//Error style for hireDate input field
let hireDateErrorMessage = document.querySelector('#hireDateError');
if(!isHidden(hireDateErrorMessage)) {
    let hireDateInputField = document.querySelector('#hireDate');
    hireDateInputField.style.border = '2px solid red';
}

//Error style for role input field
let roleErrorMessage = document.querySelector('#roleError');
if(!isHidden(roleErrorMessage)) {
    let roleInputField = document.querySelector('#role');
    roleInputField.style.border = '2px solid red';
}

//Error style for phoneNumber input field
let phoneNumberErrorMessage = document.querySelector('#phoneNumberError');
if(!isHidden(phoneNumberErrorMessage)) {
    let phoneNumberInputField = document.querySelector('#phoneNumber');
    phoneNumberInputField.style.border = '2px solid red';
}