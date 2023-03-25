const isHidden = elem => {
    const styles = window.getComputedStyle(elem)
    return styles.display === 'none' || styles.visibility === 'hidden'
}

//Error style for firstName input field
let nameErrorMessage = document.querySelector('#nameError');
if(!isHidden(nameErrorMessage)) {
    let nameInputField = document.querySelector('#name');
    nameInputField.style.border = '2px solid red';
}

//Error style for lastName input field
let typeErrorMessage = document.querySelector('#typeError');
if(!isHidden(typeErrorMessage)) {
    let typeInputField = document.querySelector('#type');
    typeInputField.style.border = '2px solid red';
}

//Error style for username input field
let purchasePriceErrorMessage = document.querySelector('#purchasePriceError');
if(!isHidden(purchasePriceErrorMessage)) {
    let purchasePriceInputField = document.querySelector('#purchasePrice');
    purchasePriceInputField.style.border = '2px solid red';
}

//Error style for password input field
let salePriceErrorMessage = document.querySelector('#salePriceError');
if(!isHidden(salePriceErrorMessage)) {
    let salePriceInputField = document.querySelector('#salePrice');
    salePriceInputField.style.border = '2px solid red';
}

//Error style for hireDate input field
let purchasedQuantityErrorMessage = document.querySelector('#purchasedQuantityError');
if(!isHidden(purchasedQuantityErrorMessage)) {
    let purchasedQuantityInputField = document.querySelector('#purchasedQuantity');
    purchasedQuantityInputField.style.border = '2px solid red';
}

//Error style for role input field
let expiryDateErrorMessage = document.querySelector('#expiryDateError');
if(!isHidden(expiryDateErrorMessage)) {
    let expiryDateInputField = document.querySelector('#expiryDate');
    expiryDateInputField.style.border = '2px solid red';
}

//Error style for phoneNumber input field
let phoneNumberErrorMessage = document.querySelector('#phoneNumberError');
if(!isHidden(phoneNumberErrorMessage)) {
    let phoneNumberInputField = document.querySelector('#phoneNumber');
    phoneNumberInputField.style.border = '2px solid red';
}