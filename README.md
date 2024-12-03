<p align = "center">
  <img src = "Trashformed Logo.png" width = "1000" height = "400" alt="LogoInsert"> 
</p>

<br>

## Table of Contents
-  [1. A brief project overview](#proj-overview)
-  [2. Explanation of how OOP principles were applied](#explanation-oop)
-  [3. Details of the chosen SDG and its integration into the project](#chosen-sdg)
-  [4. Instructions for running the program](#instruc-run) 

## <a id = "proj-overview"> 📖I. Project Overview</a> 
<b>TrashFormed</b> is a Java-based console application aimed at promoting responsible recycling habits and sustainable waste management. The project supports two types of users:
- <b>Regular Users</b>: They can register, log in, and track their recycling efforts by adding waste items categorized into types (plastic, organic, metal, glass, and electronic). Users can also view a summary of their recycling contributions and access helpful recycling tips to ensure proper disposal.
- <b>Admins</b>: They have the ability to log in and manage recycling tips in the system, allowing them to add or delete tips tailored to each waste type.
<br>
The application integrates Object-Oriented Programming (OOP) principles and uses a MySQL database to store information, including user details, recycling tips, waste items, and recycling logs. TrashFormed aligns with Sustainable Development Goal (SDG) 12: Responsible Consumption and Production, by encouraging users to adopt sustainable waste practices.
<br>

## <a id = "explanation-oop"> 📌II. Explanation of How OOP Principles Were Applied</a>
1. <b>Encapsulation:</b>
- Data and methods are bundled together into cohesive classes, ensuring that implementation details are hidden and accessed only through defined methods.
  - For example, the WasteItem class encapsulates properties like itemId, userId, itemName, quantity, and wasteType. These fields are private and accessed via getter and setter methods.
  - The DatabaseManager class encapsulates all database-related operations, exposing only essential methods like insertUser, validateLogin, and insertWasteItem to other parts of the program.
2. <b>Inheritance:</b>
- The WasteItem class serves as a base class for specific waste categories like Plastic, Organic, Metal, Glass, and Electronic. Each derived class inherits the common properties and behaviors of WasteItem while adding unique recycling tips via the getRecycleTip() method.
3. <b>Polymorphism:</b>
- Polymorphism is used to provide flexibility and extensibility in handling different waste types.
  - For instance, the getRecycleTip() method is defined abstractly in the WasteItem class and implemented differently in each subclass (e.g., Plastic, Organic).
  - This allows the program to handle waste items generically while ensuring that each type delivers its own specific recycling advice.
4. <b>Encapsulation:</b>
- Abstract classes and methods are used to define high-level behavior without committing to specific implementations.
  - The WasteItem class is abstract, serving as a blueprint for creating various waste item subclasses. It defines the structure and enforces implementation of the getRecycleTip() method, ensuring that every subclass provides recycling tips relevant to its category.
5. <b>Modularity:</b>
- Different classes such as User, WasteItem (and its subclasses), DatabaseManager, and Main are responsible for specific aspects of the program, ensuring that functionality is divided into manageable modules. This promotes clean code and makes maintenance easier.
<br>

## <a id = "chosen-sdg"> ✔️III. Details of the chosen SDG and its integration into the project</a>
Chosen SDG: <b>Sustainable Development Goal 12: Responsible Consumption and Production</b>
- This goal emphasizes the need to ensure sustainable consumption and production patterns, focusing on reducing waste generation, promoting recycling, and adopting environmentally conscious practices.
<b>Integration to TrashFormed</b>:
1. <b>Encouraging Recycling Behavior</b>: TrashFormed motivates users to adopt sustainable habits by allowing them to log and categorize waste items they recycle. This feature raises awareness about the importance of segregating and managing waste responsibly.
2. <b>Educational Component via Recycling Tips</b>: users learn actionable methods to recycle better and reduce their environmental footprint.
3. <b>Recycling Summary for Users</b>: This visualization reinforces the positive impact of their efforts and encourages continued responsible behavior.
4. <b>Administrative Features to Drive Awareness</b>: This functionality ensures that users always have access to relevant and effective recycling guidance.
5. <b>Promoting Accountability Through Personal Logs</b>: This approach not only fosters accountability but also builds a culture of personal responsibility towards the environment.
6. <b>Alignment with the SDG Goals</b>: By empowering users to manage and reduce waste, TrashFormed directly supports SDG 12.5: “Substantially reduce waste generation through prevention, reduction, recycling, and reuse.”
<br>

## <a id = "instruc-run"> ▶️IV. Instructions for running the program</a>

 




  
  

