# <h1 align="center">Restaurant Manager</h1>

**Project Description** – Restaurant Management is a web application, built on top of Spring Boot and Thymeleaf. It is created as final
project for Java Web course in Software University (Softuni).

## Application roles:

* **Host**
* **Manager**
* **Waiter**
* **Cook**
* **Warehouse worker**
* **Admin**

## Customer service process:

In order for the customer service to begin, employees, restaurant tables and menu items must be added to in the application:
1. **Admin** user role is responsible for adding users (employees)
2. The user, which is responsible for adding tables is employee with **Manager** role
3. The user, which is responsible for adding menu items is employee with **Warehouse Worker** role

Where we have all prerequisites for the restaurant to operate. This process includes following steps:

1. Employees with **Host** role accommodate clients to the preferred table, based on number of seats. (chosen table becomes to 'pending state'')
2. **Waiter** take this table from 'pending tables' section in his profile. After that, the order has been open (order status 'open') and this tables becomes 'used'
3. **Waiter** add menu items to the order
4. After the product is added to the order, its status becomes 'ordered' if its dish, dessert, appetizer or salad. If the items is drink, its status becomes 'cooked' for the sake of simplicity of the cooking process.
5. 'Ordered' items go to 'pending' items in **Cook** role employee's page. Cook starts to cook the items and when is finished, he/she change the status to 'cooked'
6. **Waiter** can see every 'cooked' items and has opportunity to serve them to the according table.
7. If the table has no dishes that have not been served, the order can be 'closed'. When click on 'Finish order', the **waiter** can preview the bill with all ordered products and then finally close the order.
8. Closing order, frees the table.

## Sub-processes

1. Every change of status, no matter of the object (order, ordered item, table), generates event for 'change status'. Those status changes published as events and observed by event listener, which write log in the database. Those logs are previewed in **Admin** page trough async fetch request
2. Every ordered product, reduces the quantity this product in the warehouse. When there is no available units in the warehouse, the item will not appear in the menu.
3. **Warehouse worker** can track the expired items in the inventory and if there is any, those items will not appear in the menu, and employee have the functionality to waste the product, which will mark the product in the database as waisted.
4. Everyday in 23:59 automatic report is generated, where **Manager** can check 'top sold product', 'most often used table', 'employee of the day', 'daily turnover' and 'daily pure profit'
5. ... TODO