---
layout: page
title: User Guide
---

NUSContacts is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, NUSContacts can get your contact management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

2. Download the latest `NUSContacts.jar` from [here](https://github.com/AY2324S2-CS2103T-T11-2/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your NUSContacts application.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar NUSContacts.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list`: Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com r/STUDENT a/PGPR c/CS2103T`: Adds a contact named `John Doe` to the Address Book.

   * `delete 3`: Deletes the 3rd contact shown in the current list.

   * `clear`: Deletes all contacts.

   * `exit`: Exits the app.

6. Refer to the [list of all commands](#list-of-all-commands) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

### General features

#### Command history

You can navigate between past successful commands by pressing the `UP` and `DOWN` arrow keys.

#### Highlighting erroneous part of the command

When a command contains an error, the program will try to detect the part of the command that causes the error, and
selects it in the command box.

#### Saving the data

NUSContacts data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

#### Editing the data file

NUSContacts data are saved automatically as a JSON file `[JAR file location]/data/nuscontacts.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, NUSContacts will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the NUSContacts to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### List of all commands

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g. `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…` after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* If the command you have typed is a prefix of only one existing command, it will be interpreted as that existing command.<br>
  e.g. if the command is `ad ...`, then it will be interpreted as an `add ...` command.

* Parameters can be in any order, except specified otherwise.<br>
  e.g. if the command specifies `n/NAME p/PHONE`, `p/PHONE n/NAME` is also acceptable.

* For commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`), specifying any extraneous parameters for them
  will result in an `Invalid command format!` error message. Make sure to just enter these commands as it is. 

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

* The command name and tag is case-sensitive, following tradition of `bash` shell commands.
  Nevertheless, all command names are in lowercase, and typing lowercase characters is faster than typing uppercase
  characters.
</div>

#### Viewing help: `help`

Shows a message listing out all the available commands and their purpose.

Format: `help`

For more information regarding the command formats and examples, press `F1` to open up a help window (as shown in the picture below).

To close the help window, you can simply press `esc` on your keyboard. Windows users may also use 
`alt`+`F4` or click on the `X` in the top right corner of the window.

![help message](images/helpMessage.png)

The [project website](https://ay2324s2-cs2103t-t11-2.github.io/tp/) includes the NUSContacts user guide, which contains a more detailed description of each command.

#### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME e/EMAIL r/ROLE c/COURSE [a/ADDRESS] [p/PHONE] [t/TAG]… [f/]`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0).
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com r/student a/PGPR c/CS2101`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com r/TA a/COM2-0102 c/ST2334`

This is mostly sufficient for you to know how to use the command. Here are some more details:

* **The `a/ADDRESS` field**: You can keep a professor or TA's office address here.
  However, TA or student may not have an office (and you may not know their home address),
  as such, this field is optional for those roles.

* **The `r/ROLE` field**: The allowed roles are `Student`, `TA`, or `Professor`.
  The input is case-insensitive, and you can type an unambiguous prefix to specify the role.
  For example, you can type `r/s` instead of `r/student` as shown above.

* **The `t/TAG` field**: Unlike the `edit` command, `t/` with an empty tag is not supported.
  If you want to not include any tag, leave out `t/TAG` entirely.

* **The `p/PHONE` field**: Similarly, `p/` without any phone number is not supported,
  if you want to not specify the phone number, leave out `p/PHONE` entirely.

* **The `f/` field**: Several fields have some validation rules (for example, you cannot use `ABCD` as a course code,
  since it does not conform to NUS course code format). Nevertheless, if you enter such a course code as input, the
  program will allow you to bypass the validation by adding `f/` to the end of the command.

  Note that `f/` must come at the end, or immediately before a tag. For example, `add f/ n/Alice …` is allowed, but `add
  n/ f/ Alice …` is not allowed.

#### Editing a person: `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [r/ROLE] [a/ADDRESS] [c/COURSE] [t/TAG]… [f/]`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e. adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.
* You can remove a person's phone number by typing `p/` without specifying any phone number after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.
*  `edit 3 p/ t/` Deletes the phone number of the 3rd person and clears all existing tags.

<div markdown="block" class="alert alert-info">
:bulb: If an invalid field value is specified, the program will inform you of the error.
You can fix the error, or use `f/` to bypass it if you want to.

Refer to [Adding a person: `add`](#adding-a-person-add) for more information about `f/`.
</div>

#### Locating persons by name: `find`

Finds persons whose names or course contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive.  
  e.g `hans` will match `Hans`, `cs2103t` will match `CS2103T`, `student` will match `STUDENT`.
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`.
* Only the name, course and role are searched.
* Only full words will be matched.  
  e.g. `Han` will not match `Hans`.  
  e.g. `cs2103` will not match `CS2103T`.  
  e.g. `stu` will not match `STUDENT`.
* Persons matching at least one keyword will be returned (i.e. `OR` search).  
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`.  
  e.g. `Hans CS2103T` will return `Hans Gruber`, `Bo Yang` whose course is CS2103T.  
  e.g. `Hans TA` will return `Hans Gruber`, `Bo Yang` whose role is TA.
  

Examples:
* `find John` returns `john` and `John Doe`.

* `find yang timothy` returns `Yang Heebeom`, `Timothy`.<br>
  ![result for 'find alex david'](images/findYangTimothyResult.png)

* `find yang cs2109s` returns `Yang Heebeom`, `Reet`.<br>
  ![result for 'find alex david'](images/findYangCS2109SResult.png)

* `find yang professor` returns `Yang Heebeom`, `HongDuc`.<br>
    ![result for 'find alex david'](images/findYangProfessorResult.png)

##### Note on returning to the original view
* After using `find`, the list may no longer display all saved contacts. Use `list` to revert the list to the full list of contacts in the order they were added.

#### Listing all persons: `list`

Shows a list of all persons in the address book.

Format: `list`

<div markdown="block" class="alert alert-info">
:bulb: See [note](#note-on-returning-to-the-original-view) under [Locating Persons by Name](#locating-persons-by-name-find) to understand how the `list` command can be useful.
</div>


#### Deleting a person: `delete`

Deletes the specified person(s) from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.


You can also delete multiple people by chaining the indices using a comma.

Format: `delete INDEX1, INDEX2, INDEX3, …`

* A comma (`,`) must be used to separate each pair of Indices.
* The indices do not need to be listed in order. (i.e. `2, 4, 6` is the same as `6, 2, 4`)
* Listing the same index more than once will result in an error message being displayed.
* Each `INDEX` must still adhere to the points listed above.

Example:
* `delete 3, 1, 7, 8` deletes the first, third, seventh, and eighth person in the address book.

#### Copying a person's email to clipboard: `copy`

Copies the email of the specified person from the address book to the system clipboard.

Format: `copy INDEX`

* Copies the email of the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …

Examples:
* `list` followed by `copy 2` copies the email of the 2nd person in the address book to the clipboard.
* `find Betsy` followed by `copy 1` copies the email of the 1st person in the results of the `find` command to the clipboard.

#### Clearing all entries: `clear`

Clears all entries from the address book.

Format: `clear`

#### Undo last command : `undo`

Revert the last change to the contact list.

Since you can only undo the most recent change, entering `undo` consecutively
will only show an error message (`No new changes has been made to the data`).
Likewise, entering `undo` with no prior changes will also display the same error message.

**Note:** `undo` does not affect commands that do not modify the contact list
(`help`, `list`, `find` etc.). 

#### Exiting the program: `exit`

Exits the program.

Format: `exit`


--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous NUSContacts home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action     | Format, Examples                                                                                                                                                                |
|------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**    | `add n/NAME e/EMAIL r/ROLE a/ADDRESS c/COURSE [t/TAG]… [p/PHONE]` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com r/STUDENT a/PGPR c/CS2103T t/friend t/colleague` |
| **Clear**  | `clear`                                                                                                                                                                         |
| **Delete** | `delete INDEX`<br> e.g., `delete 3`                                                                                                                                             |
| **Edit**   | `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [r/ROLE] [a/ADDRESS] [c/COURSE] [t/TAG]…`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`                                        |
| **Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake CS2103T STUDENT`                                                                                                      |
| **List**   | `list`                                                                                                                                                                          |
| **Help**   | `help`                                                                                                                                                                          |
| **Exit**   | `exit`                                                                                                                                                                          |
| **Copy**   | `copy INDEX`                                                                                                                                                                          |
| **Undo**   | `undo`                                                                                                                                                                          |
