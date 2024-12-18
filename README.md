In target directory you will find .jar file that you can run.

App runs on localhost:8080

Get available items:
GET http://localhost:8080/available_items

Scan an item (e.g., ID = 1):
POST http://localhost:8080/scan?itemId=1

Get total price:
GET http://localhost:8080/total_sum

Get receipt (total price + description + clear shopping cart):
GET http://localhost:8080/receipt
