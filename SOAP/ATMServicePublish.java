package hw6;

/*

# SOAP Web Service Publish :
   This is a class where we define how the service is published. 
   The publish methods are used to start publishing the endpoint which will 
   let the service to start accepting incoming requests. 
 
# The publish method takes two parameters: 
  Address - A URI specifying the address and transport/protocol to use, Port Number
           , Web service name, WSDL request for particular remote interface.
  Implementation Object – is the class object which has implemented the interface 
           for the service.
           
 NOTE : Temporary Bank information are already stored in the ATMService.java. Server port can be assigned 
         on time of creating registry or starting the server.           

 */
import javax.xml.ws.Endpoint;

public class ATMServicePublish {

    public static void main(String[] args) {

    	
        System.out.print("\nPublishing ATM Service ...please wait ...");
        
        // Remote Interface Implementation Object.
        ATMService atmService = new ATMService();

        // Publish Web service with specified name 
        Endpoint endpoint = Endpoint.publish("http://localhost:8080/WebServices/Assignment6?WSDL", atmService);

        // check status of Web service. publshed Or not?
        System.out.print(endpoint.isPublished()
                ? "success.\n"
                : "failure.\n");
    }
}
