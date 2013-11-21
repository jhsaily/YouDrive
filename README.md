YouDrive
========

An online-based car rental system for Software Engineering CSCI 4050/6050 at the University of Georgia.


WTF Do I Do?
========
To add the project to Eclipse, first make sure you have the latest version of Eclipse for Java EE installed. Done? Good.

Now what you need to do is make sure you have EGit installed. To do this, go to the Help menu in Eclipse and click on "Install New Software...".
In the field that lets you choose which site to install from, add this url:

http://download.eclipse.org/egit/updates

Then in the main content box, expand "Eclipse Git Team Provider" and select everything but the source code. Unless you really want to look at it.

Click next to go forward and install the software. Eventually Eclipse will want to restart, do so.

Next, we want to import this project. So go to File -> Import and select Git -> Projects from Git.

Then click on "Clone URI" and hit next. A window will pop up asking for the clone URI. Choose this one:

https://github.com/jhsaily/YouDrive.git

Under authentication insert your github username and password combo, then click next and next again until you reach the import directory screen.
Either leave it alone or choose a custom directory. Regardless of what you do, REMEMBER the directory path.

Click Finish, and choose to import the project using the New Project wizard.

Select a new Web -> Dynamic Web Project, and name it Youdrive. For the project location use the directory path that was used for the git import.
Set up your runtime shit as per usual, and click Finish.

Congratulations, you should have the YouDrive project ready for editing.


Okay, Now What?
========
Whenever you want to submit the changes to gitHub, right click the project on your explorer and head on over to Team.

Pull lets you retrieve the latest remote project files from github, and commit lets you save changes to your local repository.

Anyways, after pulling then selecting commit to update the remote repo, you'll need to type up a commit message then select "Commit" or "Commit and Push".

Commit and Push immediately uploads everything to the remote repo, while only commit saves changes locally.
If you select Commit, you can do it several times before pushing to the remote repo using Team -> Push to Upstream.

But remember, pull before push. It'll help to avoid collisions.
