const consumables = document.getElementById("menuItems");
const dishes = document.getElementById("dishes");
const appetizers = document.getElementById("appetizers");
const desserts = document.getElementById("desserts");
const drinks = document.getElementById("drinks");
const salads = document.getElementById("salads");

consumables.addEventListener("change", () => {
    if (consumables.value === "dishes") {
        dishes.style.display = "block";
        appetizers.style.display = "none";
        desserts.style.display = "none";
        drinks.style.display = "none";
        salads.style.display = "none";
    } else if (consumables.value === "appetizers") {
        dishes.style.display = "none";
        appetizers.style.display = "block";
        desserts.style.display = "none";
        drinks.style.display = "none";
        salads.style.display = "none";
    } else if (consumables.value === "desserts") {
        dishes.style.display = "none";
        appetizers.style.display = "none";
        desserts.style.display = "block";
        drinks.style.display = "none";
        salads.style.display = "none";
    } else if (consumables.value === "drinks") {
        dishes.style.display = "none";
        appetizers.style.display = "none";
        desserts.style.display = "none";
        drinks.style.display = "block";
        salads.style.display = "none";
    } else if (consumables.value === "salads") {
        dishes.style.display = "none";
        appetizers.style.display = "none";
        desserts.style.display = "none";
        drinks.style.display = "none";
        salads.style.display = "block";
    }
});