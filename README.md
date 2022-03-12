# Alumni Processor

#
How to run this program:

* Checkout the main branch from this repository.
* Build and run the project.
* Locally, access http://localhost:8082 (the port being used can be changed in the application.yml file).
* Use the endpoints(instructions on how to use the below):
    * http://localhost:8082/upload
    * http://localhost:8082/hosts
    * http://localhost:8082/hosts/hostId
* Enjoy.
#

This program is a spring boot project, with 2 distinct functionalities, they are:

Upload file data in the system:

    In order to upload a file to the system, you will need to make a post call to the endpoint /upload. in order to send
    a file to be proccessed, you need to send it as a param with the key "File".
    If you don't provide any file, the system will process an internal file (resources/input-file.csv).
    The system will process one file at a time, and will override data with the same host name.
    You will get a Http Status 201 (Created) if the file processing is OK.

Retrieve host information:
    
    In order to retrieve the host information that was previously updated, you will need to make a get call to the 
    endpoint /hosts. This will return all hosts stored in the database. along with a Http Status 200 (OK).
    You can also specifically search for a given host, calling endpoint /hosts/{hostName}, where the {host} is the 
    host name you are querying. you will get the host information along with a Http Status 200 (OK) response in case
    of success, or a Http Status 404 (Not found) if there is no host with that name.
    
