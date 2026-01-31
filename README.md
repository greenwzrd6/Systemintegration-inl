# Systemintegration-inl

This project is a servlet that contains some data about schedules for substitute teachers. It gets the information from a database and formats it to either XML or Json. It also handles various errors and sends the appropriate status codes. To check the status codes of a certain url you can always use curl or lwp-request.

There are three scripts, one for compiling and starting the servlet, one for tests that my teacher made and one with two tests that I made. The scripts are only made for unix systems (macos/linux).

To run a script:

1. chmod +x <name_of_script>.sh (to make it executable)
2. ./<name_of_script>.sh
