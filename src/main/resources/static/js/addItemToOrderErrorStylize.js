const isHidden = elem => {
    const styles = window.getComputedStyle(elem)
    return styles.display === 'none' || styles.visibility === 'hidden'
}

//Error style for firstName input field
let itemIdErrorMessage = document.querySelector('#itemIdError');
if(!isHidden(itemIdErrorMessage)) {
    let itemIdInputField = document.querySelector('#itemId');
    itemIdInputField.style.border = '2px solid red';
}