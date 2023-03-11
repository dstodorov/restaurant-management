const isHidden = elem => {
    const styles = window.getComputedStyle(elem)
    return styles.display === 'none' || styles.visibility === 'hidden'
}

//Error style for firstName input field
let seatsErrorMessage = document.querySelector('#seatsError');
if(!isHidden(seatsErrorMessage)) {
    let seatsInputField = document.querySelector('#seats');
    seatsInputField.style.border = '2px solid red';
}