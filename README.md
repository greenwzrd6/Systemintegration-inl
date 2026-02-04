# Systemintegration-inl

This project is a servlet that contains some data about schedules for substitute teachers. It gets the information from a database and formats it to either XML or Json. It also handles various errors and sends the appropriate status codes. To check the status codes of a certain url you can always use curl or lwp-request.

There are four scripts, one for compiling and starting the servlet, one for cleaning, one for tests that my teacher made and one with two tests that I made. The scripts are only made for unix systems (macos/linux).

To download use git clone https://github.com/greenwzrd6/Systemintegration-inl.git

To run a script:

1. chmod +x <name_of_script>.sh (to make it executable)
2. ./<name_of_script>.sh

When you run the ./compile_servlet_and_start_winstone.sh it starts a servlet that runs inside that terminal window.
If you want to run more scripts or use commands then you need to open a new terminal window.

To debug you can see Error messages in the terminal that is running the servlet. 
And you can use lwp-request -m HEAD <url> to check the HTML status codes. (or lwp-request -m GET -e <url> to get both the body and head)
