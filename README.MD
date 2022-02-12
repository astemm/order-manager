This is server-client application using Spring Boot and ReactJS.
This app allows to add, delete and browse Order Items, and to add, update, delete, and browse the Orders.
It uses basic authentification for app functionality access.
The ReactJS frontend project is located in: src/main/orders-app-reactjs. To restore the project node_modules folder dependencies run "npm install" command in npm cli from reactjs project directory.
Access the application in browser by https://localhost:8080,
after running spring boot maven plugin in project root:
mvn spring-boot:run.
ReactJS project is built and copied to Spring Boot server as static resources at compile maven phase during spring boot start-up.