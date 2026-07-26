# Perfect Pantry 
A fun passion project with the goal of being a cool tool to help in the kitchen

### Project Goals
Repository for recipes where your comments and rating are saved  
Create your own recipes!  
Input your pantry and it tells you when you can make  
Save images of the recipe so I can display what meals are in my fridge like a fast food restaurant  
Ultimately I want to learn some cool stuff about full stack development as well make a useful tool for my friends and I!

### Tech Stack
- Kotlin Spring backend
- postgreSQL database
- Ubuntu server hosting locally
- Front end? idk yet


## Environment
For Python I know the standard is an .env file, but for this I am less sure.  
Here I edited the Execution Configuration's environment to say `URL=jdbc:postgresql:postgres_url;PASSWORD=super_secret_password`  

---

Ok now I want to optimize the process of creating a recipe.  
The first milestone of this project is simply a storage place to keep my recipes, so lets do that well.  
Hell I think it would be fun if my first UI implementation is using my KotlinTUI project on my server.  
Also a fun part about this project is I have enough cooking minded friends that I think I can send them this site and have them use it.  

## Brainstorm time
When I am thinking about a recipe what is **everything** I want to know  
I am also including what datatype this could be saved as in a database
- Name of dish: String
- source of dish: String 
- Description of dish/ other comments related to the dish: String
- Instructions to prepare dish: Json 
- Time to prepare: Time 
- Cost: Double
- Amount it prepares: ... difficult because it could be an integer, but it could also be a weight or a portion size
- Calories + other macros: Json 
- Country/region of origin (I like to take deep dives into cultures' dishes so I think this is fun)
  - Tagging. A similar not to above. Just a list of tags on a recipe to help with searching. Things like "Vegan" or "Spicy" or "Mexican": Enum? With a separate table holding all the tags
- Review? (I am not sure I want this to be anything even resembling a social media): Json if it includes descriptions or Int if it does not  


## Updates
It has been a while since I updated this readme and a lot has happened in the project  

I have been enjoying churning away at the backend of this project. I have never given a backend so much love and attention before working on frontend. 
I think doing it this way will be good for my sanity since I know the front end will decay my sanity.  

I think I have all the basic direct repository actions I want to take, now I need to focus on more of the abstracted viewpoint.  
When the User goes to add a recipe to their account, they are not going to know the ID of the recipe or even themselves.   
I need to sketch out the workflow of interacting with recipes 


## Known Rough Points
When creating new tables in postgres using the PGAdmin GUI, make sure you edit the constraints of the id for the table if it is auto generated  