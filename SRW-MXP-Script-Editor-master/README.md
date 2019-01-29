SRW MX Portable Script Editor
---------------------------------------

This program allows you to edit the contents of SRWL files extracted from MAP_ADD.BIN using the Script Extractor application.


How to use this:

I think it's pretty straightforward, but here's some basics:

1) Go to File -> Open and browse for one of the SRWL files.

2) Once you open the file, the Navigation panel (top-left) will be activated and will display which message out of the total you're viewing (you start at the first). Use the controls in this panel and the options in the Navigate menu to move through the different messages inside the file.

3) You have two text areas displaying the script messages. The one on the left shows the text read originally, the one on the right is where you write your translation (initially has the same original text).

* If you're not comfortable using the program to translate, you can go to "File -> Export lines to txt file..." to generate a TXT file with the script lines that you can edit in Notepad++ (for example). This method is recommended, since the application doesn't have an "Undo" option. What you translate in these TXT files can be imported into the editor with "File -> Import lines from txt file...".

4) When writing your translation, keep an eye on the top-right, where you can see how much screen width will be used by the quote. If it turns red, you wrote too much. The game does shrink the lines that are too big to display onscreen, so it's not really a problem, but it's better if you avoid these situations (the shrinked text won't look nice).

5) You can ignore the Tools menu and the "Convert keystrokes to SJIS" option, those are there just in case somebody decides to use VWF in the future.

6) The "Don't convert current dialogue to letter pair" option is needed for some special messages that typically appear at the end of some SRWL files. These messages are a short code written in ASCII, and is presumably something the game uses to trigger events or decide what the next stage will be. These messages MUST stay unaltered, for obvious reasons. You can see an example of this kind of message in 0190.SRWL: the last message is "i104a". If you're going to use the "Convert and Save as..." option, make sure these messages have this option checked!! (if you're just going to use "Save as...", it's not needed)

7) Once you're done editing, go to "File -> Save as..." to save your modifications as another SRWL file. The file saved through this CAN'T BE USED BY THE GAME. Well, it can, but the game will probably crash. The reason I tell you to save in this format is because if you open your saved file again, you'll be able to read the quotes the way you wrote them. The other save option saves the text after converting every letter pair into kanji, which you most probably won't understand. ALWAYS make a save of your edits with this method.

8) To save your edits as a file that the game can read, you have to use "File -> Convert and Save as...". You should only do this when you want to create files to insert into the game.


IMPORTANT NOTES:

* The keys assigned to the options in the Navigate menu won't work if you're inside a text area. Try clicking into the "Jump to" field if you want to use these keys.

* Some SRWL files are empty, don't be scared by error messages in pop-up windows. In fact, the first two SRWL files (0000 and 0001) are empty.

* 0003.SRWL contains the messages used when suspending the game after saving in the middle of a stage. The script for the different variants of the first stage is inside 0004.SRWL.

* The original characters and names use special codes starting with a # character in ASCII (set the encoding of the document to SJIS if you can't read these):
	- #íjê©ñº is the male character's name.
	- #èóê©ñº is the female character's name.
	- #íjà§èÃ is the male character's nickname.
	- #èóà§èÃ is the female character's nickname.
	- #ã@ëÃñº is the original mech's name.

So, if you run into anything starting with a #, leave the following three characters untouched (consult this little list first). Of course, there could be more codes, if something else is named.

* If you're editing the script using txt files, you won't run into the previous codes. They'll be shown as names between brackets, like "[male-name]".