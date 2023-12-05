# CS-360-Mobile-Development
Mobile Architecture and Programming

# Briefly summarize the requirements and goals of the app you developed. What user needs was this app designed to address?
The client requested that we make an application capable of managing an inventory. The main requirements were that it contained:
- Login screen:
This consisted of user authentication through the database, keeping with security standards.
- Inventory management screen: 
  A screen where users can easily see what items are available, and have the option to edit, add, or delete items as needed. This screen would also have to connect to a back end and have live counts of current items.
- Notification system: 
  A screen where users can opt into recieving SMS notifications on their device when a certain item falls beneath a threshhold. This would help the user better manage inventory and know when to reorder items.
- Develop a back end: 
  Create a database to hold the users credentials and items information. Then connect your application to that database for use.

# What screens and features were necessary to support user needs and produce a user-centered UI for the app? How did your UI designs keep users in mind? Why were your designs successful?
As mentioned above, I needed a login screen, Inventory screen, Inventory item screen, and a Settings screen. The login screen contained a logo for my application, and also made it easy to create an account if needed. The inventory item screen was kept very simple, it displayed an add button and current items, there count, and when clicked would take you to the inventory item screen where you can edit or delete the item. The settings screen was created to hold the SMS notification options, and was designed so that future programmers could add more settings easily. I kept the user in mind consistently by keeping to best design practices, and making it simple and easy to navigate. 

# How did you approach the process of coding your app? What techniques or strategies did you use? How could those be applied in the future?
I was new to using Java for mobile development. I made lots of practice applications when learning the content of this course to reinforce what I had learned. I also would work on the final project little by little throughout the course so that I wouldnt have a huge amount of work due the week of. I broke up each screen into parts to ensure the modularity of my code, and to also make it easy to read for myself as well. All of these things I am sure I will be using in the future as a software engineer. 

# How did you test to ensure your code was functional? Why is this process important and what did it reveal?
Android Studio has great emulators that are fantastic for testing functionality. Anytime a change was made I could easily see the changes and make any edits I needed to. This process is absolutely crucial to development. If we cant test code as we go we may end up with a giant mess of problems at the end that could have easily been avoided with consistent testing. It reveals errors, or any other issues that will most definitely arise during development. Finally, with mobile development, the UI is so important. To ensure that our design is aesthetically pleasing, constant testing is necessary. 

# Considering the full app design and development process, from initial planning to finalization, where did you have to innovate to overcome a challenge?
While it wasnt necessary, I created a seperate screen for managing the items in your inventory. I wanted to ensure that each item had its own space on the main screen, and that editing it was done on a seperate sceen to avoid errors. This also allows for adding photos of the items in the future easily without taking up any extra screen space.

# In what specific component from your mobile app were you particularly successful in demonstrating your knowledge, skills, and experience?
I created a custom object for displaying my items in a gridview. I also created my own recycler view adapter so that I could effentially display items on my grid view. 
