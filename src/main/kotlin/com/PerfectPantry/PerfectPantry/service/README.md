# Service Layer

In the age of AI programming I want to be extra deliberate about what I do.
Why am I creating a service layer and what does it mean.  

The service layer handles all the business logic at a higher level.
It handles whole operations top to bottom where the controller layer handles individual HTTP requests.  

The controller layer handles actually creating a recipe entry in the database, but the service
layer handles creating the recipe, any new ingredients, and connecting them all with the junctions tables needed.  

Not that I am doing this **yet**, but this separation also helps with testing.