# AI Reflection

1. **Which AI tools or models did you use?**  
   Cursor Composer/Auto for architecture suggestions, implementation help, review feedback, and documentation drafting.

2. **Which prompt was the most useful? Why?**  
   The architecture prompt was most useful because it helped organize the coursework into model/service layers before coding.

3. **Which AI-generated suggestion was wrong, incomplete, or misleading?**  
   An early suggestion placed `DataInitializer` in the wrong package and used invalid switch-arrow `return` syntax in `Main.java`.

4. **How did you check whether AI-generated code was correct?**  
   By compiling with `javac`, running the console app, testing login and lookup flows, and comparing behavior against the PDF checklist.

5. **What bugs did you fix yourself instead of asking AI to fix?**  
   Verified delete consistency, corrected package declarations, and checked that file reload re-register admin after load.

6. **What Java concept did you understand better after using AI?**  
   Using interfaces plus polymorphism to keep user handling generic while still supporting role-specific menus.

7. **What Java concept are you still unsure about?**  
   Balancing simple text persistence formats with more robust serialization approaches such as JSON libraries.

8. **Did AI make the project easier, harder, or both? Explain.**  
   Both. It accelerated boilerplate and service creation, but required careful review to avoid design shortcuts and syntax mistakes.

9. **Which parts of the final project were mainly written by you?**  
   Requirement interpretation, final design approval, test verification, reflection, Git process planning, and several business rules such as ranking formulas.

10. **Which parts were mainly generated or heavily assisted by AI?**  
   Initial class skeletons, service method implementations, documentation templates, and sample data bootstrapping.
