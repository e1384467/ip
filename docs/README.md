# Jerry User Guide
Jerry is a desktop app for managing tasks.
## Features
### Adding a todo type task
Adds a todo type task to your list of tasks.\
Jerry will output a confirmation message with the task added.\
\
Format: `todo <task description>`\
\
Example input: `todo eat an orange`\
Example output:
```
I have added '[T][] eat an orange' to your list!
Now you have 1 tasks in the list!
```
### Adding a deadline type task
Adds a deadline type task to your list of tasks.\
Jerry will output a confirmation message with the task added.\
\
Format: `deadline <task description> /by <ddmmyyyy hhmm (24-hour clock)>`\
\
Example input: `deadline submit report /by 06062002 0530`\
Example output:
```
I have added '[D][] submit report (by: 2002/06/06 05:30)' to your list!
Now you have 2 tasks in the list!
```
### Adding a event type task

Adds a event type task to your list of tasks.\
Jerry will output a confirmation message with the task added.\
\
Format: `event <task description> /from <ddmmyyyy hhmm (24-hour clock)> /to <ddmmyyyy hhmm (24-hour clock)>`\
\
Example input: `event project meeting /from 06062002 0530 /to 07062002 0500`\
Example output:
```
I have added '[E][] project meeting (from: 2002/06/06 05:30 to: 2002/06/07 05:00)' to your list!
Now you have 3 tasks in the list!
```
### Listing your tasks
Displays all your tasks.\
\
Format: `list`\
\
Example input: `list`\
Example output: 
```
Your list:
1. [T][] eat an orange
2. [D][] submit report (by: 2002/06/06 05:30)
3. [E][] project meeting (from: 2002/06/06 05:30 to: 2002/06/07 05:00)
```
### Marking a task
Marks a task you deemed to be done.\
Jerry will output a confirmation message with the task marked as done.\
\
Format: `mark <index>`\
\
Example input: `mark 1`\
Example output: 
```
Nice! I've marked this task as done -> [T][X] eat an orange
```
### Unmarking a task
Unmarks a task you deemed to be not done.\
Jerry will output a confirmation message with the task marked as not done.\
\
Format: `unmark <index>`\
\
Example input: `unmark 1`\
Example output: 
```
Okiee! I've unmarked this task as not done yet -> [T][] eat an orange
```
### Deleting a task
Removes a task from your task list.\
Jerry will out a confirmation message with the deleted task and number of remaining tasks.\
\
Format: `delete <index>`\
\
Example input: `delete 1`\
Example output: 
```
Got it! I've removed [T][] eat an orange. You now have 2 task(s) left
```
### Finding a task
Finds based on task description.\
Jerry returns a list of the results.\
\
Format: `find <search query>`
\
Example input: `find submit`\
Example output: 
```
Here are the matching tasks in your list:
1. [D][] submit report (by: 2002/06/06 05:30)
```
### Exiting the application 
Exits the application\
\
Format: `bye`\
\
Example input: `bye`
Example output:
```
Bye. Hope to see you again soon!
```
### Saving the data
Jerry automatically saves your data to the hard disk after any command except `list`.\
Your data file is located in the ***data*** folder.\
### Duplicated task handling
Jerry checks the list for any duplicate before performing any add task commands to ensure no duplicates are in the list.\
## Command Summary

| Action | Formats | Examples |
| --- | --- | --- |
| todo | `todo <task description>` | e.g., `todo eat an orange`|
| deadline | `deadline <task description> /by <ddmmyyyy hhmm (24-hour clock)>` | e.g., `deadline submit report /by 06062002 0530` |
| event | `event <task description> /from <ddmmyyyy hhmm (24-hour clock)> /to <ddmmyyyy hhmm (24-hour clock)>` | e.g., `event project meeting /from 06062002 0530 /to 07062002 0500` |
| list | `list` |
| mark |  `mark <index>` | e.g., `mark 1`|
| unmark | `unmark <index>` | e.g., `unmark 1` |
| delete | `delete <index>` | e.g., `delete 1` |
| find | `find <search query>` | e.g., `find submit`|
| bye | `bye`|
