# AI Reflection

1. **Which AI tools or models did you use?**  
   I used Cursor Composer/Auto. It helped me with architecture suggestions, implementation, review feedback, and drafting documentation.

2. **Which prompt was the most useful? Why?**  
 The architecture prompt was the most useful. It helped me plan out the model and service layers before I started coding, which saved a lot of time and avoided messy code.

3. **Which AI-generated suggestion was wrong, incomplete, or misleading?**  
   Early on, AI told me to put DataInitializer in the wrong package, and it also gave me invalid switch-arrow return syntax in Main.java. Both caused compile errors that I had to fix.

4. **How did you check whether AI-generated code was correct?**  
   I compiled with javac, ran the program, tested login and search features manually, I also double-checked everything against the PDF checklist with the help of ai work.

5. **What bugs did you fix yourself instead of asking AI to fix?**  
   I checked deletion consistency myself, fixed package declarations, and made sure file reloading properly re-registered admin accounts after restart.

6. **What Java concept did you understand better after using AI?**  
   Interfaces and polymorphism. I got a better sense of how to keep user handling generic while still supporting role‑specific menus like admin vs player.

7. **What Java concept are you still unsure about?**  
   About how to choose between simple text persistence and more robust formats like JSON or databases, that's still unsure.

8. **Did AI make the project easier, harder, or both? Explain.**  
   Both. It made boilerplate code and service classes faster to write, but I had to be careful and review everything to avoid design shortcuts or syntax mistakes.
   However, the process through AI need to be double-checked, to find the place of  the file.

9. **Which parts of the final project were mainly written by you?**  
  I handled requirement interpretation, final design approval, test verification, reflection, Git process planning, and some business rules like the ranking formula.

10. **Which parts were mainly generated or heavily assisted by AI?**  
   AI helped a lot with initial class skeletons, service method implementations, documentation templates, and setting up the sample data.
